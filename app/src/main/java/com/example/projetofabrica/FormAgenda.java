package com.example.projetofabrica;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

public class FormAgenda extends AppCompatActivity {
    private Button btAgendar, btEditar, btSair;
    private RecyclerView recyclerView;
    private VisitaAdapter visitaAdapter;
    private List<Visita> visitasList;
    private TextView nomeUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_agenda);
        getSupportActionBar().hide();

        btAgendar = findViewById(R.id.bt_agendar);
        btEditar = findViewById(R.id.bt_editar);
        btSair = findViewById(R.id.bt_sair);
        nomeUser = findViewById(R.id.textNomeUser);
        recyclerView = findViewById(R.id.recycler);

        visitasList = new ArrayList<>();
        visitaAdapter= new VisitaAdapter(visitasList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(visitaAdapter);

        btAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormAgenda.this, FormAgenda2.class);
                startActivity(intent);
            }
        });

        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormAgenda.this, EditarVisitaActivity.class);
                startActivity(intent);
            }
        });

        btSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(FormAgenda.this, FormLogin.class);
                startActivity(intent);
                finish();
            }
        });

        carregarVisitasAgendadas();
    }

    private void carregarVisitasAgendadas() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("visitas");

            Query query = databaseReference.orderByChild("userId").equalTo(userId);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    visitasList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Visita visita = snapshot.getValue(Visita.class);
                        visitasList.add(visita);
                    }
                    visitaAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    String errorMessage = databaseError.getMessage();
                    Toast.makeText(FormAgenda.this, "Erro ao carregar as visitas: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null){
                    nomeUser.setText(documentSnapshot.getString("nome"));
                }
            }
        });
    }
}
