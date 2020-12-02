package com.example.tutorfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class StudentprofileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseFirestore firebaseFirestore;
    private String userID;


    private TextView studentName, studentAddress, studentPhone, studentEmail,studentParent,studentParentPhone;
    private Button updateStudent;
    private CircleImageView circleImageView;

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove app title from action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_studentprofile);


        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        FirebaseUser user=firebaseAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();


        setupUIViews();

        userID= firebaseAuth.getCurrentUser().getUid();

        DocumentReference dRTutor=firebaseFirestore.collection("students").document(userID);

        dRTutor.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        studentName.setText(document.get("fullname").toString());
                        studentPhone.setText(document.get("phonenumber").toString());
                        studentAddress.setText(document.get("address").toString());
                        studentEmail.setText(document.get("email").toString());

                        // tutorSex.setText(document.get("sex").toString());
                        studentParent.setText(document.get("parentfullname").toString());
                        studentParentPhone.setText(document.get("parentphone").toString());
                        //tutorSpecialization.setText(document.get("specializationfield").toString());
                       // tutorPrice.setText(document.get("priceperhour").toString());




                    } else {

                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });





    }


    private void setupUIViews(){
        // get views by their ids

        studentName=findViewById(R.id.tvStudentName);
        studentPhone=findViewById(R.id.tvTutorPhone);
        studentAddress=findViewById(R.id.tvTutorAddress);
        studentEmail=findViewById(R.id.tvTutorEmail);

        // tutorSex=findViewById(R.id.tvTutorSex);
        // tutorSpecialization=findViewById(R.id.tvTutorSpecilization);
        //tutorSubject=findViewById(R.id.tvTutorSubject);
       // tutorDegree=findViewById(R.id.tvTutorDegree);

        studentParent=findViewById(R.id.tvParentName);
        studentParentPhone=findViewById(R.id.tvParentPhone);
        circleImageView=findViewById(R.id.studentProfileImage);



    }
}