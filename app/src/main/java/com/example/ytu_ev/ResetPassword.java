package com.example.ytu_ev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResetPassword extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    TextInputEditText editTextEmail;
    Button buttonReset;
    FirebaseAuth mAuth;
    TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        editTextEmail = findViewById(R.id.mail);
        buttonReset = findViewById(R.id.btn_reset);
        textView = findViewById(R.id.login_click);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = String.valueOf(editTextEmail.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(ResetPassword.this, "Please enter your email?????????", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ResetPassword.this, "Şifre yenileme bağlantısı gönderildi", Toast.LENGTH_SHORT).show();


                            }
                            else{
                                Toast.makeText(ResetPassword.this, "HATA", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}