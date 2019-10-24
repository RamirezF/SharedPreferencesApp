package app.android.frisco.sharedpreferencesapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.android.frisco.sharedpreferencesapp.R;
import app.android.frisco.sharedpreferencesapp.models.User;
import app.android.frisco.sharedpreferencesapp.repositories.UserRepository;

public class MainActivity extends AppCompatActivity {
    private TextView fullnameText;
    private Button encuesta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullnameText=findViewById(R.id.fullname_text);
        encuesta=findViewById(R.id.encuestra_btn);
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
        String username  = sp.getString("username",null);
        User user= UserRepository.findByUseername(username);

        if(user != null){
            fullnameText.setText(user.getFullname());
        }

        encuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, EncuestaActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.logout_item:
                callLogout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void callLogout(){
        // Eliminar el estado islogged de la sp
        SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().remove("islogged").commit();
        // Finalizamos o además redireccionamos al LoginActivity
        finish();
        // redireccionamos
        startActivity(new Intent(this, LoginActivity.class));
    }
}
