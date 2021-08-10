package com.example.facecall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore database;
    EditText emailBox, passwordBox, nameBox;
    Button loginBtn, signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        emailBox = findViewById(R.id.emailBox1);
        nameBox = findViewById(R.id.nameBox);
        passwordBox = findViewById(R.id.passwordBox1);

        loginBtn = findViewById(R.id.LoginBtn1);
        signupBtn = findViewById(R.id.CreateBtn1);

       signupBtn.setOnClickListener(v -> {
           String email, pass, name;
           email = emailBox.getText().toString();
           pass = passwordBox.getText().toString();
           name = nameBox.getText().toString();

          final User user = new User();
           user.setEmail(email);
           user.setPass(pass);
           user.setName(name);

           auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    database.collection("Users")
                            .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                        }
                    });
                    Toast.makeText(SignupActivity.this,"Account Created",Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(SignupActivity.this, Objects.requireNonNull(task.getException())
                            .getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
               }
           }

           );
       });


    }
}