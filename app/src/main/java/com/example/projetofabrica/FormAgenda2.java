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

public class FormAgenda2 extends AppCompatActivity {

    private EditText editTextName;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button buttonSave;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_agenda2);

        // Inicializar a instância do FirebaseDatabase
        databaseReference = FirebaseDatabase.getInstance().getReference("visits");

        editTextName = findViewById(R.id.edit_nome);
        datePicker = findViewById(R.id.date);
        timePicker = findViewById(R.id.time);
        buttonSave = findViewById(R.id.bt_salvar);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveVisit();
            }
        });
    }

    private void saveVisit() {
        String name = editTextName.getText().toString().trim();

        // Obter a data selecionada
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        // Obter a hora selecionada
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        String dateTime = String.valueOf(calendar.getTimeInMillis());

        String visitId = databaseReference.push().getKey();
        Visit visit = new Visit(visitId, name, dateTime);

        if (visitId != null) {
            databaseReference.child(visitId).setValue(visit).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(FormAgenda2.this, "Visita agendada com sucesso", Toast.LENGTH_SHORT).show();
                        clearFields();
                    } else {
                        Toast.makeText(FormAgenda2.this, "Erro ao agendar visita", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void clearFields() {
        editTextName.setText("");
        // Limpar o DatePicker e definir a data atual
        datePicker.updateDate(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        // Limpar o TimePicker e definir a hora atual
        timePicker.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(Calendar.getInstance().get(Calendar.MINUTE));
    }
}
