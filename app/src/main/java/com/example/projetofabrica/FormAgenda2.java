package com.example.projetofabrica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class FormAgenda2 extends AppCompatActivity {

    Button bt_sair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_agenda2);
        getSupportActionBar().hide();

        bt_sair = (Button) findViewById(R.id.bt_salvar);

        bt_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(FormAgenda2.this, FormLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }
}