package com.example.segfault;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.Objects;

public class info_struct_promo extends AppCompatActivity {
    private void set_Wd(String[] day){
        CheckBox lun= findViewById(R.id.check_lun_info);
        lun.setChecked(!day[0].equals(" "));
        CheckBox mar= findViewById(R.id.check_mar_info);
        mar.setChecked(!day[1].equals(" "));
        CheckBox me= findViewById(R.id.check_me_info);
        me.setChecked(!day[2].equals(" "));
        CheckBox gio= findViewById(R.id.check_gi_info);
        gio.setChecked(!day[3].equals(" "));
        CheckBox ve= findViewById(R.id.check_ve_info);
        ve.setChecked(!day[4].equals(" "));
        CheckBox sa= findViewById(R.id.check_sa_info);
        sa.setChecked(!day[5].equals(" "));
        CheckBox dom= findViewById(R.id.check_dom_info);
        dom.setChecked(!day[6].equals(" "));
    }
    private String work_days(){
        String ret="";
        CheckBox lun= findViewById(R.id.check_lun_info);
        if (lun.isChecked()) ret+="lun-";else ret+=" -";
        CheckBox mar= findViewById(R.id.check_mar_info);
        if (mar.isChecked()) ret+="mar-";else ret+=" -";
        CheckBox me= findViewById(R.id.check_me_info);
        if (me.isChecked()) ret+="mer-";else ret+=" -";
        CheckBox gio= findViewById(R.id.check_gi_info);
        if (gio.isChecked()) ret+="gio-";else ret+=" -";
        CheckBox ve= findViewById(R.id.check_ve_info);
        if (ve.isChecked()) ret+="ven-";else ret+=" -";
        CheckBox sa= findViewById(R.id.check_sa_info);
        if (sa.isChecked()) ret+="sa-";else ret+=" -";
        CheckBox dom= findViewById(R.id.check_dom_info);
        if (dom.isChecked()) ret+="dom";else ret+=" ";

        return ret;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("informazioni /modifica struttura: "+MainActivity.struct.getName());
        setContentView(R.layout.info_struct_promo);

        //structure info
        String name=MainActivity.struct.getName();
        String desc=MainActivity.struct.getDesc();

        String address=MainActivity.struct.getAddress();
        String number=((Integer)MainActivity.struct.getNumber()).toString();
        String closing_time=MainActivity.struct.getStop_time();
        String opening_time=MainActivity.struct.getStart_time();

        //set fields
        Button confirm= findViewById(R.id.confirm_create_info_struct_promo);
        Button cancel = findViewById(R.id.cancel_create_info_struct_promo);
        Button delete = findViewById(R.id.delete_structure);
        TextView name_struct=findViewById(R.id.name_info_struct_promo);
        name_struct.setText(name);
        TextView addre_struct=findViewById(R.id.street_addr_info_struct_promo);
        addre_struct.setText(address);
        TextView opening= findViewById(R.id.opening_time__info_struct_promo);
        opening.setText(opening_time);
        TextView stop= findViewById(R.id.closing_time_info_struct_promo);
        stop.setText(closing_time);
        set_Wd( MainActivity.struct.getWorking_days());
        TextView description= findViewById(R.id.desc_info_struct_promo);
        description.setText(desc);
        TextView numberview= findViewById(R.id.number_info_struct_promo);
        numberview.setText(number);


        confirm.setOnClickListener(v -> {

            if(name_struct.getText().toString().equals("") || addre_struct.getText().toString().equals("") || stop.getText().toString().equals("") || opening.getText().toString().equals("")|| numberview.getText().toString().equals("")){
                AlertDialog.Builder builder=new AlertDialog.Builder(info_struct_promo.this);
                builder.setMessage("Riempire tutti i valori!").setPositiveButton("ok", null);
                AlertDialog alert=builder.create();
                alert.show();
            }else {
                if(new_structure.convert(numberview.getText().toString())==null || new_structure.convert(numberview.getText().toString())<0){
                    AlertDialog.Builder builder=new AlertDialog.Builder(info_struct_promo.this);
                    builder.setMessage("numero persone negativo!").setPositiveButton("ok", null);
                    AlertDialog alert=builder.create();
                    alert.show();
                }
                else {


                    if (!new_structure.control(opening.getText().toString(), stop.getText().toString())) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(info_struct_promo.this);
                        builder.setMessage("orario non corretto \nutilizzare hh:mm oppure controllare che il periodo sia corretto").setPositiveButton("ok", null);
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    else {
                            //add new structure
                            try {
                        JSONObject struct = new JSONObject();
                        struct.put("id", MainActivity.struct.getId());
                        struct.put("addr_id", MainActivity.struct.getAddress_id());
                        struct.put("description", description.getText().toString());
                        struct.put("start_time", opening.getText().toString());
                        struct.put("stop_time", stop.getText().toString());
                        struct.put("working_days", work_days());
                        struct.put("street", addre_struct.getText().toString());
                        struct.put("number", Integer.parseInt(numberview.getText().toString()));

                        FSRequest req = new FSRequest("POST", MainActivity.utente_log.getToken(), "api/structure/modify", struct.toString(), "");
                        String res = req.execute().get();

                        if(res.equals("OK")){
                            // structure added: refresh the page
                            AlertDialog.Builder builder=new AlertDialog.Builder(info_struct_promo.this);
                            builder.setMessage("Struttura modificata con successo").setPositiveButton("Ok", (dialog, which) -> {
                                MainActivity.utente_supp=MainActivity.utente_log;
                                Intent i = new Intent(info_struct_promo.this, All_structure_prom.class);
                                startActivity(i);
                                finish();
                            });
                            AlertDialog alert=builder.create();
                            alert.show();

                        }else{
                          //request body error
                                AlertDialog.Builder builder=new AlertDialog.Builder(info_struct_promo.this);
                                builder.setMessage("Errore durante la modifica!").setPositiveButton("Ok", (dialog, which) -> {});
                                AlertDialog alert=builder.create();
                                alert.show();

                        }

                        }
                            catch(Exception e){
                            Log.println(Log.ERROR, "Errore connessione", e.getMessage());

                            AlertDialog.Builder builder=new AlertDialog.Builder(info_struct_promo.this);
                            builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog,which) -> {});
                            AlertDialog alert=builder.create();
                            alert.show();
                            }
                    }
                }
            }

        });

        cancel.setOnClickListener(v -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(info_struct_promo.this);
            builder.setMessage("Annullare la modifica?").setPositiveButton("Si", (dialog,which) -> finish()).setNegativeButton("No", (dialog, which) ->{});
            AlertDialog alert=builder.create();
            alert.show();
        });

        delete.setOnClickListener(v -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(info_struct_promo.this);
            builder.setMessage("Eliminare la struttura?").setPositiveButton("Si", (dialog,which) -> {
                try{

                    FSRequest req = new FSRequest("DELETE", MainActivity.utente_log.getToken(), "api/structure/" + MainActivity.struct.getId(), "","token=" + MainActivity.utente_log.getToken());
                    String res = req.execute().get();
                    if(res.equals("OK")){
                        AlertDialog.Builder build=new AlertDialog.Builder(info_struct_promo.this);
                        build.setMessage("Struttura eliminata con successo").setPositiveButton("Ok", (dial, w) -> {
                            MainActivity.utente_supp=MainActivity.utente_log;
                            finish();
                        });
                        AlertDialog alert=build.create();
                        alert.show();
                    }
                    else{
                        AlertDialog.Builder build=new AlertDialog.Builder(info_struct_promo.this);
                        build.setMessage("Errore durante l'eliminazione!").setPositiveButton("Ok", null);
                        AlertDialog alert=build.create();
                        alert.show();
                    }

                }catch(Exception e){
                    Log.println(Log.ERROR, "Errore connessione", e.getMessage());

                    AlertDialog.Builder build=new AlertDialog.Builder(info_struct_promo.this);
                    build.setMessage("Errore di connessione").setPositiveButton("Ok", null);
                    AlertDialog alert=build.create();
                    alert.show();
                }

            }).setNegativeButton("No", (dialog,which) ->{});
            AlertDialog alert=builder.create();
            alert.show();
        });

    }


}