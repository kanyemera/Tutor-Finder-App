package com.example.tutorfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UpdatetutorActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseFirestore firebaseFirestore;
    private String userID;

    private TextInputLayout tutorName, tutorAddress, tutorPhone, tutorEmail,tutorSpecialization,tutorSubject,tutorPrice,tutorDegree;
    private Button updateTutorInfo;
    private ImageView imageViewTutor;

    private Button btnSelect, btnUpload;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage;
    StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //remove app title from action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_updatetutor);

        setupUIViews();

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        FirebaseUser user=firebaseAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();





        // on pressing btnSelect SelectImage() is called
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SelectImage();
            }
        });


        // on pressing btnUpload uploadImage() is called
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                uploadImage();
            }
        });





        userID= firebaseAuth.getCurrentUser().getUid();

        DocumentReference dRTutor=firebaseFirestore.collection("tutors").document(userID);

        dRTutor.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        tutorName.getEditText().setText(document.get("fullname").toString());
                        tutorPhone.getEditText().setText(document.get("phonenumber").toString());
                        tutorEmail.getEditText().setText(document.get("email").toString());
                        tutorAddress.getEditText().setText(document.get("address").toString());


                        tutorDegree.getEditText().setText(document.get("highestdegree").toString());
                        tutorSubject.getEditText().setText(document.get("subjects").toString());
                      //  tutorSpecialization.getEditText().setText(document.get("specializationfield").toString());
                        tutorPrice.getEditText().setText(document.get("priceperhour").toString());
                    }
                }
            }
        });


        updateTutorInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DocumentReference documentReference=firebaseFirestore.collection("tutors").document(userID);
                Map<String,Object> user= new HashMap<>();

                user.put("fullname",  tutorName.getEditText().getText().toString());
                user.put("phonenumber",tutorPhone.getEditText().getText().toString());
                user.put("email",tutorEmail.getEditText().getText().toString());
                // user.put("membertype",MemberRole);

                user.put("address",tutorAddress.getEditText().getText().toString());
                //user.put("specializationfield",tutorSpecialization.getEditText().getText().toString());
                user.put("highestdegree",tutorDegree.getEditText().getText().toString());
              //  user.put("tutorcategory","NA");

                user.put("subjects",tutorSubject.getEditText().getText().toString());
               // user.put("tutorphoto","NA");
               // user.put("tutorcertificate","NA");
                user.put("priceperhour",tutorPrice.getEditText().getText().toString());

                //user.put("sex","NA");
              //  user.put("teachingmethod","NA");


                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        //firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(UpdatetutorActivity.this,TutorprofileActivity.class));
                        Toast.makeText(getApplicationContext(),"Data updated",Toast.LENGTH_SHORT).show();

                    }
                });



            }
        });

        StorageReference storage = FirebaseStorage.getInstance().getReference();
        StorageReference userStorage = storage.child("images").child(userID);

        userStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(UpdatetutorActivity.this).load(uri.toString()).into(imageViewTutor);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // failed
            }
        });





    }


    private void setupUIViews(){
        // get views by their ids

        tutorName=findViewById(R.id.edtCurrentName);
        tutorPhone=findViewById(R.id.edtCurrentPhone);
        tutorAddress=findViewById(R.id.edtCurrentAddress);
        tutorEmail=findViewById(R.id.edtCurrentEmail);

        //tutorSpecialization=findViewById(R.id.edtCurrentSpecialization);
        tutorSubject=findViewById(R.id.edtCurrentSubject);
        tutorDegree=findViewById(R.id.edtCurrentDegree);

        tutorPrice=findViewById(R.id.edtCurrentPrice);
        updateTutorInfo=findViewById(R.id.btnUpdateTutorInfo);

        btnSelect = findViewById(R.id.btnChooseImage);
        btnUpload = findViewById(R.id.btnUploadImage);
        imageViewTutor=findViewById(R.id.imgTutor);



    }


    // Select Image method
    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }


    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageViewTutor.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }


    // UploadImage method
    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
            //        .child(
             //               "images/"
             //                       + UUID.randomUUID().toString());

            .child(
                    "images/"
                            + userID.toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(UpdatetutorActivity.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(UpdatetutorActivity.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }

}