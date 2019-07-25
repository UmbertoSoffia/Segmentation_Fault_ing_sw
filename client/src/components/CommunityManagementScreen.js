import React from "react";
import { Skeleton } from "antd";
import axios from "axios";
import PropTypes from "prop-types";
import Switch from "@material-ui/core/Switch";
import { createMuiTheme, MuiThemeProvider } from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import moment from "moment";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
  Legend
} from "recharts";
import Papa from "papaparse";
import BackNavigation from "./BackNavigation";
import Texts from "../Constants/Texts";
import withLanguage from "./LanguageContext";
import Log from "./Log";

const theme = createMuiTheme({
  typography: {
    useNextVariants: true
  },
  overrides: {
    MuiTable: {
      root: {
        border: "1px solid rgba(0,0,0,0.1)",
        fontFamily: "Roboto"
      }
    },
    MuiTableCell: {
      root: {
        texAlign: "center",
        fontSize: "1.6rem!important",
        border: "1px solid rgba(0,0,0,0.1)"
      }
    }
  }
});

class CommunityInterface extends React.Component {
  state = {
    fetchedData: false
  };

  async componentDidMount() {
    const metricsResponse = await axios.get("/api/community");
    const dataResponse = await axios.get("/api/community/data");
    const parsedData = this.parseAnalytics(dataResponse.data);
    const { analytics, configurations } = metricsResponse.data;
    this.setState({
      analytics,
      configurations,
      fetchedData: true,
      analyticsData: parsedData,
      usersChartMonth: moment().format("MMMM-YYYY")
    });
  }

  parseAnalytics = data => {
    const parsedData = Papa.parse(data, { delimiter: " " });
    parsedData.data.shift();
    parsedData.data.pop();
    return parsedData.data;
  };

  handleBackNav = () => {
    const { history } = this.props;
    history.goBack();
  };

  renderMetrics = () => {
    const { language } = this.props;
    const { analytics } = this.state;
    const metrics = Object.keys(analytics);
    const values = Object.values(analytics);
    const texts = Texts[language].communityInterface;

    return (
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>{texts.metricsColumn}</TableCell>
            <TableCell>{texts.valuesColumn}</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {metrics.map((metric, index) => (
            <TableRow key={metric}>
              <TableCell component="th" scope="row">
                {texts[metric]}
              </TableCell>
              <TableCell align="right">{values[index]}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    );
  };

  handleConfiguration = configuration => {
    const { configurations } = this.state;
    const updatedConfigurations = { ...configurations };
    updatedConfigurations[configuration] = !configurations[configuration];
    axios
      .patch("/api/community", { ...updatedConfigurations })
      .then(response => {
        Log.info(response);
        this.setState({ configurations: updatedConfigurations });
      })
      .catch(err => {
        Log.error(err);
      });
  };

  renderConfigurations = () => {
    const { language } = this.props;
    const { configurations: conf } = this.state;
    const configurations = Object.keys(conf);
    const values = Object.values(conf);
    const texts = Texts[language].communityInterface;
    return configurations.map((configuration, index) => {
      return (
        <div className="row no-gutters" key={configuration}>
          <div className="analytics-info">{texts[configuration]}</div>
          <Switch
            checked={values[index]}
            onChange={() => this.handleConfiguration(configuration)}
          />
        </div>
      );
    });
  };

  handleSelectChange = event => {
    const { name } = event.target;
    const { value } = event.target;
    this.setState({ [name]: value });
  };

  renderCharts = () => {
    const { analyticsData, usersChartMonth } = this.state;
    const { language } = this.props;
    const texts = Texts[language].communityInterface;
    const usersData = analyticsData.map(value => ({
      date: value[0],
      users: parseInt(value[1], 10)
    }));
    const monthlyUsers = usersData.filter(
      value => moment(value.date).format("MMMM-YYYY") === usersChartMonth
    );
    const min = Math.min(usersData.map(value => value.users));
    const max = Math.max(usersData.map(value => value.users));
    return (
      <div className="chartsContainer">
        <select
          className="chartsSelect"
          value={usersChartMonth}
          onChange={this.handleSelectChange}
          name="usersChartMonth"
        >
          {[
            ...new Set(
              usersData.map(data => moment(data.date).format("MMMM-YYYY"))
            )
          ].map(d => (
            <option key={d} value={d}>
              {d}
            </option>
          ))}
        </select>
        <ResponsiveContainer width="80%" height={300}>
          <LineChart data={monthlyUsers}>
            <Line
              type="monotone"
              dataKey="users"
              name={texts.usersChartTitle}
              stroke="#00838F"
            />
            <XAxis dataKey="date" name="Date" />
            <YAxis name="Users" type="number" domain={[min, max]} />
            <Legend verticalAlign="bottom" height={36} />
            <Tooltip />
          </LineChart>
        </ResponsiveContainer>
      </div>
    );
  };

  render() {
    const { language } = this.props;
    const texts = Texts[language].communityInterface;
    const { fetchedData } = this.state;
    return (
      <MuiThemeProvider theme={theme}>
        <BackNavigation
          title={texts.backNavTitle}
          onClick={this.handleBackNav}
        />
        <div className="interface-container">
          {fetchedData ? (
            <React.Fragment>
              <div className="analytics-header">{texts.analyticsHeader}</div>
              {this.renderMetrics()}
              <div className="analytics-header">
                {texts.configurationsHeader}
              </div>
              {this.renderConfigurations()}
              <div className="analytics-header">{texts.chartsHeader}</div>
              {this.renderCharts()}
            </React.Fragment>
          ) : (
            <Skeleton active paragraph={{ rows: 15 }} />
          )}
        </div>
      </MuiThemeProvider>
    );
  }
}

CommunityInterface.propTypes = {
  language: PropTypes.string,
  history: PropTypes.object
};

export default withLanguage(CommunityInterface);
