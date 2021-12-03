package com.example.segfault;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class all_structure_user extends AppCompatActivity {
     private LinearLayout layoutList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_structure);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Tutte le strutture ");
        layoutList = findViewById(R.id.layout_list_allstruct);

        //come aggiungere riga alla pagina con la stringa come testo della casella
        //il numero serve come id della struttura per poi dare valore al pulsante
        addView("top",1);
        addView("es",2);








    }
    private void addView( String s,Integer id_struct) {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_structure,null,false);

        TextView editText = (TextView)cricketerView.findViewById(R.id.nome_struct);
        editText.setText(s);
/*sto bottone fa fallire non so xk
       Button goInfo = findViewById(R.id.info_structure_user);
       goInfo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              /* //invio alla pagina delle info della struttura
               Intent i = new Intent(all_structure_user.this, info_struct.class);
               ///qua bisogna capire come trovare id della struttura per passarlo in input di la
               i.putExtra("id_struct", id_struct.toString());
               i.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
               startActivity(i);
               Toast toast = Toast.makeText(getApplicationContext(), "Nessun evento per questa gionata", Toast.LENGTH_SHORT);
               toast.show();
           }
       });
*/

        layoutList.addView(cricketerView);

    }



}
