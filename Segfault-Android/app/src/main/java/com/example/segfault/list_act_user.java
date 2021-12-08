package com.example.segfault;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class list_act_user extends AppCompatActivity {
    private LinearLayout layoutList;
    // capire come passare valori io credevo si passassero come qui sotto
   // private String date =  getIntent().getExtras().get("data").toString();
   // private String id_User=  getIntent().getExtras().get("id_user").toString();
    private String date="inserici data";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_act_user);

        Objects.requireNonNull(getSupportActionBar()).setTitle(date);
        layoutList = findViewById(R.id.list_act_user_scroll);

        for (int i = 0; i < 50; i++) {
            //id user lo hai come campo sopra
            addView("pren"+i,1);
        }









    }
    private void addView( String s,Integer id_pren) {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_popup,null,false);

        TextView editText = cricketerView.findViewById(R.id.pop_actyvity);
        editText.setText(s);
        Button myButton1 = cricketerView.findViewById(R.id.pop_actyvity_button);
        myButton1.setOnClickListener(view -> {


            // azione da fare per eliminare la prenotazione
            Toast toast = Toast.makeText(getApplicationContext(), "evento eliminato", Toast.LENGTH_SHORT);
            toast.show();
            //refresh qua (sottoa fatto già) per eliminare quello appena cancellato
            Intent i = new Intent(list_act_user.this, list_act_user.class);
            i.putExtra("data",getIntent().getExtras().get("data").toString());
            startActivity(i);
        });


        layoutList.addView(cricketerView);

    }
}