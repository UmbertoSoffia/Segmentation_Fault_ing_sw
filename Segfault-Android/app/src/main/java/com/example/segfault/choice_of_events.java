package com.example.segfault;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class choice_of_events extends AppCompatActivity {
    private LinearLayout layoutList;
    // capire come passare valori io credevo si passassero come qui sotto
    // private String date =  getIntent().getExtras().get("data").toString();
    // private String id_User=  getIntent().getExtras().get("id_user").toString();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serch_activity);

        Objects.requireNonNull(getSupportActionBar()).setTitle("unisciti a noi");
        layoutList = findViewById(R.id.list_elem_serch);

        for (int i = 0; i < 50; i++) {
           addView("pren"+i);
        }
        //bisogna mettere una ricerca perche senno poco usabile








    }
    private void addView( String s) {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_popup,null,false);

        TextView editText = cricketerView.findViewById(R.id.pop_actyvity);
        editText.setText(s);
        Button myButton1 = cricketerView.findViewById(R.id.pop_actyvity_button);
        myButton1.setText(R.string.partecipa);
        myButton1.setOnClickListener(view -> {


            // azione da fare per aggiungerre evento a utente
            Toast toast = Toast.makeText(getApplicationContext(), "evento confermato", Toast.LENGTH_SHORT);
            toast.show();
            //refresh qua (sottoa fatto già) per eliminare quello appena confermato
            Intent i = new Intent(choice_of_events.this, choice_of_events.class);
            i.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
            startActivity(i);
        });


        layoutList.addView(cricketerView);

    }
}
