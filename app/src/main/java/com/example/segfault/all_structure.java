package com.example.segfault;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.LinearLayout;


public class all_structure extends AppCompatActivity {
    private LinearLayout layoutlist;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //stampa il layout all_struct
        setContentView(R.layout.all_structure);
        //https://www.youtube.com/watch?v=EJrmgJT2NnI  tutorial su come fare a mettere tutti i n fila in all_structure (in row c'è la riga ma non è giusta in teoria)
        layoutlist=findViewById()
        Button backhome = findViewById(R.id.back);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //qua bisogna mettere la home page
                Intent i = new Intent(all_structure.this, homepage.class);
                startActivity(i);
            }
        });
        Button goInfo = findViewById(R.id.info_structure);
        goInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //invio alla pagina delle info della struttura
                Intent i = new Intent(all_structure.this, info_struct.class);
                startActivity(i);
            }
        });

    }


}
