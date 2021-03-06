package com.example.segfault;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class all_match_prom extends AppCompatActivity {
    private LinearLayout layoutList;
    private ArrayList<Structure>all_struct;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.all_structure);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Tutti gli incontri ");
        all_struct=new ArrayList<>();

        layoutList = findViewById(R.id.layout_list_allstruct);

        //structures' list request

        try{

            FSRequest req = new FSRequest("GET", MainActivity.utente_log.getToken(), "api/match", "", "id=" + MainActivity.utente_log.getCod_id()+"&type=promoter" + "&token=" + MainActivity.utente_log.getToken()+ "&type=promoter");
            String res = req.execute().get();
            FSRequest req2 = new FSRequest("GET", MainActivity.utente_log.getToken(), "api/structure", "", "promoter=" + MainActivity.utente_log.getCod_id() + "&token=" + MainActivity.utente_log.getToken());
            String res2 = req2.execute().get();

            //request done: draw structures' list
            if(res.equals("OK") && res2.equals("OK") ){
                JSONArray response2 = req2.array;
                for (int i = 0; i < response2.length() ; i++) {
                    JSONObject obj = (JSONObject) response2.get(i);
                    all_struct.add(new Structure(((JSONObject) response2.get(i)).get("name").toString(),
                            ((JSONObject) response2.get(i)).get("structure_id").toString(),
                            ((JSONObject) response2.get(i)).get("description").toString(),
                            ((JSONObject) response2.get(i)).getInt("number"),
                            ((JSONObject)(obj.get("address"))).get("street").toString(),
                            ((JSONObject) response2.get(i)).get("start_time").toString(),
                            ((JSONObject) response2.get(i)).get("stop_time").toString(),
                            ((JSONObject) response2.get(i)).get("working_days").toString(),
                            ((JSONObject) response2.get(i)).get("address_id").toString()

                    ));
                }
                JSONArray response = req.array;
                for (int i = 0; i < response.length() ; i++) {


                    Match m=new Match(((JSONObject) response.get(i)).get("match_id").toString(),
                            ((JSONObject) response.get(i)).get("name").toString(),
                            ((JSONObject) response.get(i)).get("structure_id").toString(),
                            ((JSONObject) response.get(i)).get("date").toString(),
                            ((JSONObject) response.get(i)).get("start_time").toString(),
                            ((JSONObject) response.get(i)).get("stop_time").toString(),
                            ((JSONObject) response.get(i)).get("creator_id").toString(),
                            ((JSONObject) response.get(i)).get("age_range").toString(),
                            ((JSONObject) response.get(i)).get("description").toString(),
                            ((JSONObject) response.get(i)).get("number").toString());
                    GregorianCalendar cal=new GregorianCalendar();
                    //tutte le date piu grandi di oggi
                    if((m.date.getTimeInMillis() >=cal.getTimeInMillis()))
                            addView(m);
                }




            }else{
                if( req.result.getInt("error_code") == 404){
                    AlertDialog.Builder builder=new AlertDialog.Builder(all_match_prom.this);
                    builder.setMessage("Nessun incontro presente").setPositiveButton("Ok", (dialog,which) -> {});
                    AlertDialog alert=builder.create();
                    alert.show();
                }
                else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(all_match_prom.this);
                    builder.setMessage("Errore richiesta 4000").setPositiveButton("Ok", (dialog,which) -> {});
                    AlertDialog alert=builder.create();
                    alert.show();
                }
            }
        } catch(Exception e){
            Log.println(Log.ERROR, "Errore connessione", e.getMessage());

            AlertDialog.Builder builder=new AlertDialog.Builder(all_match_prom.this);
            builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog,which) -> {});
            AlertDialog alert=builder.create();
            alert.show();
        }
    }

    private void addView(Match match) {

        @SuppressLint("InflateParams") final View cricketerView = getLayoutInflater().inflate(R.layout.row_popup,null,false);

        TextView editText = cricketerView.findViewById(R.id.pop_actyvity);
        String date=match.date.get(Calendar.DAY_OF_MONTH) + "-" + (match.date.get(Calendar.MONTH)+1) + "-" + match.date.get(Calendar.YEAR);
        String struttura="";
        for (Structure s:all_struct) {
            if(s.getId().equals(match.struttura)){
                struttura=s.getName();
            }
        }

        String str="nome= "+match.nome+"\ndata= "+date+"\nora= "+match.start_time+" - "+match.stop_time+"\nstruttura= "+struttura+"\nnumero persone= "+match.number;
        editText.setText(str);
        Button myButton1 = cricketerView.findViewById(R.id.pop_actyvity_button);
        myButton1.setText(R.string.elimina);
        myButton1.setOnClickListener(view -> {


            try {
                FSRequest req = new FSRequest("DELETE", MainActivity.utente_log.getToken(), "api/match/"+match.id, "","token=" + MainActivity.utente_log.getToken());
                String res = req.execute().get();

                if(!(res.equals("OK"))){
                    AlertDialog.Builder builder=new AlertDialog.Builder(all_match_prom.this);
                    builder.setMessage("").setPositiveButton("Errore durante eliminazione", (dialog,which) -> {});
                    AlertDialog alert=builder.create();
                    alert.show();
                }
                else {

                    Toast toast = Toast.makeText(getApplicationContext(), "Evento eliminato", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } catch (Exception e) {
                Log.println(Log.ERROR, "Errore connessione", e.getMessage());

                AlertDialog.Builder builder=new AlertDialog.Builder(all_match_prom.this);
                builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog,which) -> {});
                AlertDialog alert=builder.create();
                alert.show();
            }

            //page refresh
            Intent i = new Intent(all_match_prom.this, all_match_prom.class);

            startActivity(i);
            finish();
        });


        layoutList.addView(cricketerView);

    }

}
