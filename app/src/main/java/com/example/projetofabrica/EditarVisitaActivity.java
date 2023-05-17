package com.example.projetofabrica;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditarVisitaActivity extends AppCompatActivity {

    private EditText editNomeVisita;
    private Button btnSalvarEdicao;

    private Visit visita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_visita);

        editNomeVisita = findViewById(R.id.editNomeVisita);
        btnSalvarEdicao = findViewById(R.id.btnSalvarEdicao);

        visita = getIntent().getParcelableExtra("visit");

        if (visita != null) {
            editNomeVisita.setText(visita.getName());
        }

        btnSalvarEdicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarEdicao();
            }
        });
    }

    private void salvarEdicao() {
        String novoNome = editNomeVisita.getText().toString();

        // Lógica para salvar a edição da visita no Firebase
        // Implemente o código necessário para atualizar o nome da visita no Firebase

        DatabaseReference visitaRef = FirebaseDatabase.getInstance().getReference().child("visitas").child(visita.getId());
        visitaRef.child("name").setValue(novoNome).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EditarVisitaActivity.this, "Edição salva com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditarVisitaActivity.this, "Falha ao salvar edição", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
