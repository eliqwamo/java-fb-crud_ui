package com.example.firebasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    Button signin_btn, signup_btn;
    EditText email_et, password_et;
    TextView message_tv;
    LinearLayout layout;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        signin_btn = findViewById(R.id.signin_btn);
        signup_btn = findViewById(R.id.signup_btn);
        email_et = findViewById(R.id.email_et);
        password_et = findViewById(R.id.password_et);
        message_tv = findViewById(R.id.message_tv);
        layout = findViewById(R.id.layout);

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = email_et.getText().toString();
                String password = password_et.getText().toString();

                mAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    message_tv.setText(user.getUid());
                                } else {
                                    Snackbar snackbar = Snackbar
                                            .make(layout, "Error: " + task.getException(), Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Snackbar snackbar = Snackbar
                                        .make(layout, "Error: " + e, Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        });
            }
        });





        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = email_et.getText().toString();
                String password = password_et.getText().toString();

                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    message_tv.setText(user.getUid());
                                } else {
                                    Snackbar snackbar = Snackbar
                                            .make(layout, "Error: " + task.getException(), Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Snackbar snackbar = Snackbar
                                        .make(layout, "Error: " + e, Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        });
            }
        });

    }
}