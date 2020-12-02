package com.example.tutorfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private Button signup;
    private EditText email, password,phoneNunber,fullName;
    private TextView goToLogin;

    private RadioGroup radioGroupRole;
    private RadioButton buttonStudent,buttonTutor;
    String chosenRole;
    private String userID;


    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //remove app title from action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);
        setupUIViews();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();



        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this,MainActivity.class));
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //Upload data to the

                   String MemberRole=getChosenRole().toString();
                   // String MemberRole="Student";


                    String user_email=email.getText().toString().trim();
                    String user_password=password.getText().toString().trim();

                    String name=fullName.getText().toString().trim();
                    String tel=phoneNunber.getText().toString().trim();



                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                userID= firebaseAuth.getCurrentUser().getUid();



                                if (MemberRole.equals("Student")){

                                    DocumentReference documentReference=firebaseFirestore.collection("students").document(userID);
                                    Map<String,Object> user= new HashMap<>();
                                    user.put("fullname",name);
                                    user.put("phonenumber",tel);
                                    user.put("email",user_email);
                                    //user.put("membertype",MemberRole);

                                    user.put("sex","NA");
                                    user.put("photo","NA");
                                    user.put("address","NA");
                                    user.put("parentfullname","NA");
                                    user.put("parentphone","NA");
                                    user.put("parentphone","NA");

                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            firebaseAuth.signOut();
                                            startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                            Toast.makeText(getApplicationContext(),"Student Account is created",Toast.LENGTH_SHORT).show();

                                        }
                                    });




                                }else if(MemberRole.equals("Tutor")){

                                    DocumentReference documentReference=firebaseFirestore.collection("tutors").document(userID);
                                    Map<String,Object> user= new HashMap<>();
                                    user.put("fullname",name);
                                    user.put("phonenumber",tel);
                                    user.put("email",user_email);
                                    // user.put("membertype",MemberRole);

                                    user.put("address","NA");
                                    user.put("specializationfield","NA");
                                    user.put("highestdegree","NA");
                                    user.put("tutorcategory","NA");

                                    user.put("subjects","NA");
                                    user.put("tutorphoto","NA");
                                    user.put("tutorcertificate","NA");
                                    user.put("priceperhour","NA");

                                    user.put("sex","NA");
                                    user.put("teachingmethod","NA");


                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            firebaseAuth.signOut();
                                            startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                            Toast.makeText(getApplicationContext(),"Tutor Account is created",Toast.LENGTH_SHORT).show();

                                        }
                                    });



                                }





                            }
                            else{
                                Toast.makeText(SignupActivity.this,"Registration failed",Toast.LENGTH_LONG).show();
                            }



                        }
                    });
                }

            }
        });



    }


    private void setupUIViews(){
        // get views by their ids
        signup=findViewById(R.id.btnRegister);
        email=findViewById(R.id.edtEmail);
        password=findViewById(R.id.edtSignupPassword);
        goToLogin=findViewById(R.id.tvGoBack);
        fullName=findViewById(R.id.edtFullName);
        phoneNunber=findViewById(R.id.edtPhoneNumber);

        radioGroupRole=findViewById(R.id.rgChooseRole);
        buttonStudent=findViewById(R.id.rbStudent);
        buttonTutor=findViewById(R.id.rbTutor);
    }


    private Boolean validate(){
        Boolean result= false;
        String userEmail=email.getText().toString();
        String UserPassword=password.getText().toString();

        if(userEmail.isEmpty()||UserPassword.isEmpty()){
            Toast.makeText(this,"Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result=true;
        }

        return result;

    }


    


    private String getChosenRole(){

        String radiovalue="No Type";

        //RadioGroup rg = (RadioGroup)findViewById(R.id.youradio);
        //String radiovalue = ((RadioButton)findViewById(radioGroupRole.getCheckedRadioButtonId())).getText().toString();
        if(findViewById(radioGroupRole.getCheckedRadioButtonId()).equals(buttonStudent)){

            radiovalue="Student";

        }
        else if(findViewById(radioGroupRole.getCheckedRadioButtonId()).equals(buttonTutor)){
            radiovalue="Tutor";
        }


        


        return radiovalue;


    }





}