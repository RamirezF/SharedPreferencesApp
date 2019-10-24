package app.android.frisco.sharedpreferencesapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import app.android.frisco.sharedpreferencesapp.R;
import app.android.frisco.sharedpreferencesapp.models.User;
import app.android.frisco.sharedpreferencesapp.repositories.UserRepository;

public class EncuestaActivity extends AppCompatActivity {
    private TextView fullname;
    private EditText carrera;
    private RadioButton masculino, femenino;
    private CheckBox checkBox;
    private Button btn_enviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);
        fullname=findViewById(R.id.fullname);
        carrera=findViewById(R.id.carrera);
        masculino=findViewById(R.id.Masculino);
        femenino=findViewById(R.id.Femenino);
        checkBox=findViewById(R.id.checkBox);

        btn_enviar=findViewById(R.id.btn_enviar);

        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
        String username  = sp.getString("username",null);
        User user= UserRepository.findByUseername(username);

        if(user != null){
            fullname.setText(user.getFullname());
        }
        //------------------ Guardar la carrera ----------------------------
        String Carrera= sp.getString("carrerita",null);
        Boolean Hombre = sp.getBoolean("hombre",false);
        Boolean Mujer = sp.getBoolean("mujer",false);
        Boolean Check = sp.getBoolean("checkbox",false);

        if(Carrera != null){
            carrera.setText(Carrera);
        }
        if(Hombre != false){
            masculino.setChecked(true);
        }
        if(Mujer != false){
            femenino.setChecked(true);
        }
        if(Check != false){
            checkBox.setChecked(true);
        }

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EncuestaActivity.this,EncuestaActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(this);
        String Carer=carrera.getText().toString();
        Boolean sexo=false, sexo2=false;
        Boolean acepto=false;
        Boolean H=masculino.isChecked();

        if(masculino.isChecked()){
            sexo=true;
        }else{
            sexo=false;
        }
        if(femenino.isChecked()){
            sexo2=true;
        }else{
            sexo2=false;
        }


        if(checkBox.isChecked()){
            acepto=checkBox.isChecked();
        }



        sp.edit()
                .putString("carrerita",Carer)
                .putBoolean("hombre",sexo)
                .putBoolean("mujer",sexo2)
                .putBoolean("checkbox",acepto)
                .commit();

        super.onBackPressed();
    }

}
