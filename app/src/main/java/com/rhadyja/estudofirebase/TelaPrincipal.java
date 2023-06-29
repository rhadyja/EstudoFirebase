package com.rhadyja.estudofirebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class TelaPrincipal extends AppCompatActivity {

    private Button button_deslogar;
    private TextView textView_nome;
    private TextView textView_email;
    FirebaseFirestore bancoDeDados = FirebaseFirestore.getInstance();
    String nome_usuario_id;
    String email_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        getSupportActionBar().hide();
        IniciarComponentes();

        button_deslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(TelaPrincipal.this, FormLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void IniciarComponentes() {
        button_deslogar = findViewById(R.id.button_deslogar);
        textView_email = findViewById(R.id.text_email_usuario);
        textView_nome = findViewById(R.id.text_nome_usuario);
    }

    @Override
    protected void onStart() {
        super.onStart();

        email_usuario = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        nome_usuario_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = bancoDeDados.collection("Usuarios").document(nome_usuario_id);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot != null){
                    textView_nome.setText(documentSnapshot.getString("nome"));
                    textView_email.setText(email_usuario);
                }
            }
        });
    }
}