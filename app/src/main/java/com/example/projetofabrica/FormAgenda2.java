package com.example.projetofabrica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormAgenda2 extends AppCompatActivity {
    private EditText editNome;
    private EditText editData;
    private EditText editHora;
    private Button btSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_agenda2);
        getSupportActionBar().hide();

        editNome = findViewById(R.id.edit_nome);
        editData = findViewById(R.id.edit_data);
        editHora = findViewById(R.id.edit_hora);
        btSalvar = findViewById(R.id.bt_salvar);

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarVisita();
            }
        });
    }

    private void salvarVisita() {
        String nome = editNome.getText().toString().trim();
        String data = editData.getText().toString().trim();
        String hora = editHora.getText().toString().trim();

        if (!nome.isEmpty() && !data.isEmpty() && !hora.isEmpty()) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("visitas");
            String visitaId = databaseReference.push().getKey();

            Visita visita = new Visita(visitaId, nome, data, hora);
            databaseReference.child(visitaId).setValue(visita);

            // Limpar os campos após salvar a visita
            editNome.setText("");
            editData.setText("");
            editHora.setText("");
        }
    }
}

