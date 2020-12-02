package com.example.tutorfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private EditText username, password;
    private TextView forgotPassword,signupNote,signupLink;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseFirestore firebaseFirestore;
    private String userID;

    private String userEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //remove app title from action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        setupUIViews();

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        FirebaseUser user=firebaseAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=username.getText().toString();
                String pass=password.getText().toString();

                if ((name.isEmpty()||(pass.isEmpty()))){
                    Toast.makeText(MainActivity.this,"Please enter both username and password",Toast.LENGTH_SHORT).show();
                }

                else {

                    validate(username.getText().toString(), password.getText().toString());
                }

            }
        });



       //taking tutor to registration page

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignupActivity.class));

            }
        });


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,PasswordresetActivity.class));
               // startActivity(new Intent(MainActivity.this,SearchtutorActivity.class));

            }
        });

    }


    private void setupUIViews(){
        // get views by their ids

        login=(Button) findViewById(R.id.btnLogin);

        username=(EditText) findViewById(R.id.edtUsername);
        password=(EditText) findViewById(R.id.edtPassword);


        forgotPassword=(TextView) findViewById(R.id.tvForgotPwd);
        signupNote=(TextView) findViewById(R.id.tvSignupNote);
        signupLink=(TextView) findViewById(R.id.tvSignup);

    }


    private void validate(String usrName, String usrPwd){

        progressDialog.setMessage("Please wait, it takes seconds!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(usrName,usrPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // finish();
                    progressDialog.dismiss();

                    userID= firebaseAuth.getCurrentUser().getUid();


                    //DocumentReference documentReference=firebaseFirestore.collection("users").document(userID);

                    DocumentReference dRTutor=firebaseFirestore.collection("tutors").document(userID);
                    //DocumentReference dRStudent=firebaseFirestore.collection("students").document(userID);



                    dRTutor.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {

                                    startActivity(new Intent(getApplicationContext(),TutormainActivity.class));
                                    finish();

                                   // Log.d(TAG, "Document exists!");

                                } else {

                                    startActivity(new Intent(getApplicationContext(),StudentmainActivity.class));
                                    finish();

                                   // Log.d(TAG, "Document does not exist!");
                                }
                            } else {

                                firebaseAuth.signOut();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();

                                //Log.d(TAG, "Failed with: ", task.getException());
                            }
                        }
                    });






                }




                else{
                    Toast.makeText(MainActivity.this,"Login failed",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    private void checkEmailVerification(){
        FirebaseUser firebaseUser=firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag=firebaseUser.isEmailVerified();
        if(emailflag){
            finish();
            startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
        }else{
            Toast.makeText(this,"Verify your email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }



}