package com.example.tutorfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordresetActivity extends AppCompatActivity {


    private EditText passwordEmail;
    private Button resetPassword;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //remove app title from action bar
        getSupportActionBar().hide();

        setContentView(R.layout.activity_passwordreset);

        setupUIViews();



        firebaseAuth= FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usrEmail=passwordEmail.getText().toString().trim();
                if(usrEmail.equals("")){
                    Toast.makeText(PasswordresetActivity.this,"Please enter your registered email ID",Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.sendPasswordResetEmail(usrEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PasswordresetActivity.this,"Password reset email sent",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(PasswordresetActivity.this,MainActivity.class));
                            }else{
                                Toast.makeText(PasswordresetActivity.this,"Error in sending password reset email",Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }

            }
        });



    }

    private void setupUIViews(){
        // get views by their ids

        passwordEmail=findViewById(R.id.edtUserEmail);
        resetPassword=findViewById(R.id.btnResetPassword);





    }
}