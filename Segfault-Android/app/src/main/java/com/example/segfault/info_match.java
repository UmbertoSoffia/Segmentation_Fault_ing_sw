package com.example.segfault;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Objects;

public class info_match extends AppCompatActivity {
    Match match;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        match=MainActivity.match;


        setContentView(R.layout.info_match);
        Objects.requireNonNull(getSupportActionBar()).setTitle("informazioni evento "+match.nome);
        TextView range= findViewById(R.id.range_info_match);
        range.setText(match.age_range);
        TextView name= findViewById(R.id.name_info_match);
        name.setText(match.nome);
        TextView number= findViewById(R.id.number_info_match);
        number.setText(match.number);
        TextView time= findViewById(R.id.time_info_match);
        String bin=match.start_time+" - "+match.stop_time;
        time.setText(bin);
        TextView data= findViewById(R.id.data_info_match);
        String str=match.date.get(Calendar.DAY_OF_MONTH) + "-" + (match.date.get(Calendar.MONTH)+1) + "-" + match.date.get(Calendar.YEAR);
        range.setText(str);
        TextView stru= findViewById(R.id.struct_nam_info_match);
        stru.setText(match.struttura);
        TextView desc= findViewById(R.id.desc_info_match);
        desc.setText(match.desc);

        Button button=findViewById(R.id.button_info_match);

        if(MainActivity.match.creatoreid.equals(MainActivity.utente_log.getCod_id())){
            String s="elimina evento";
            button.setText(s);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(info_match.this);
                    builder.setMessage("eliminare l'incontro?").setPositiveButton("Sì", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ////////elimina incontro
                            ///invia mail agli altri x elimnazione incontro

                            finish();

                        }

                    }).setNegativeButton("anulla", null);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                });


        }
        else{

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(info_match.this);
                    builder.setMessage("Confermi?").setPositiveButton("Sì", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ////////disiscrivi utente_log
                            finish();

                        }

                    }).setNegativeButton("anulla", null);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }




    }
}
