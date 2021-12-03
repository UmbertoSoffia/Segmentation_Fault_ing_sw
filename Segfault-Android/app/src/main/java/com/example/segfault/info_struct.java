package com.example.segfault;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class info_struct extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id_struct=getIntent().getExtras().get("id_struct").toString();
        String Nome_user= getIntent().getExtras().get("id_user").toString();

        //passa info queri a sta struttura utilizzando id_struttura
        String name=id_struct;
        String desc="";
        String mail="";
        String address="";
        String phone="";
        String closing_time="";
        String opening_time="";
        TextView textView = findViewById(R.id.name_info_struct);
        textView.setText(name);
        textView = findViewById(R.id.desc_info_struct);
        textView.setText(desc);
        textView = findViewById(R.id.mail__info_struct);
        textView.setText(mail);
        textView = findViewById(R.id.phone_info_struct);
        textView.setText(phone);
        textView = findViewById(R.id.closing_time_info_struct);
        textView.setText(closing_time);
        textView = findViewById(R.id.opening_time__info_struct);
        textView.setText(opening_time);
        textView = findViewById(R.id.street_addr_info_struct);
        textView.setText(address);







    }
}
