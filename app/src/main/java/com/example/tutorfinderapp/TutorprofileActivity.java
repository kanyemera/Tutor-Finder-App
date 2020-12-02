package com.example.tutorfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TutorprofileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseFirestore firebaseFirestore;
    private String userID;


    private TextView tutorName, tutorAddress, tutorPhone, tutorEmail,tutorSex,tutorSpecialization,tutorSubject,tutorPrice,tutorDegree;
    private Button updateTutor, tutorDash;
    private CircleImageView circleImageView;

    FirebaseStorage storage;
    StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //remove app title from action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_tutorprofile);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        FirebaseUser user=firebaseAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();


        setupUIViews();

        userID= firebaseAuth.getCurrentUser().getUid();

         DocumentReference dRTutor=firebaseFirestore.collection("tutors").document(userID);

        dRTutor.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        tutorName.setText(document.get("fullname").toString());
                        tutorPhone.setText(document.get("phonenumber").toString());
                        tutorAddress.setText(document.get("address").toString());
                        tutorEmail.setText(document.get("email").toString());

                       // tutorSex.setText(document.get("sex").toString());
                        tutorDegree.setText(document.get("highestdegree").toString());
                        tutorSubject.setText(document.get("subjects").toString());
                        //tutorSpecialization.setText(document.get("specializationfield").toString());
                        tutorPrice.setText(document.get("priceperhour").toString());




                    } else {

                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });



       // StorageReference ref = FirebaseStorage.getInstance().getReference().child("images/"+userID+".jpeg");

      //Glide.with(TutorprofileActivity.this)
         //     .using(new FirebaseImageLoader())
           //   .load(ref)
           //   .into(circleImageView);

        //FirebaseStorage storage = FirebaseStorage.getInstance();
       // StorageReference storageRef = storage.getReferenceFromUrl("gs://tutorfinder-724b9.appspot.com");
       // StorageReference pathReference = storageRef.child("images/"+userID+".jpeg");

        //Glide.with(this)
        //        .load(pathReference)
         //       .into(circleImageView);

        StorageReference storage = FirebaseStorage.getInstance().getReference();
        StorageReference userStorage = storage.child("images").child(userID);

        userStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(TutorprofileActivity.this).load(uri.toString()).into(circleImageView);

              // trying to save image url to tutor collection
                DocumentReference documentReference=firebaseFirestore.collection("tutors").document(userID);

                Map<String,Object> user= new HashMap<>();
                user.put("tutorphoto",  uri.toString());
                documentReference.update(user);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // failed
            }
        });



        updateTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UpdatetutorActivity.class));
            }
        });

        tutorDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),TutormainActivity.class));
            }
        });



    }


    private void setupUIViews(){
        // get views by their ids

        tutorName=findViewById(R.id.tvTutorName);
        tutorPhone=findViewById(R.id.tvTutorPhone);
        tutorAddress=findViewById(R.id.tvTutorAddress);
        tutorEmail=findViewById(R.id.tvTutorEmail);

       // tutorSex=findViewById(R.id.tvTutorSex);
       // tutorSpecialization=findViewById(R.id.tvTutorSpecilization);
        tutorSubject=findViewById(R.id.tvTutorSubject);
        tutorDegree=findViewById(R.id.tvTutorDegree);

        tutorPrice=findViewById(R.id.tvTutorPrice);
        updateTutor=findViewById(R.id.btnUpdateTutor);
        circleImageView=findViewById(R.id.profile_image);
        tutorDash=findViewById(R.id.btnTutorDashboard);



    }



}