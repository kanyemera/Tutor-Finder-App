package com.example.tutorfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class TutormainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private CardView profile, students, requests, reviews, search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutormain);

        this.setTitle("Tutor Dashboard");

        firebaseAuth=FirebaseAuth.getInstance();

        setupUIViews();


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),TutorprofileActivity.class));
            }
        });

    }

    //put logout codes inside a function
    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logoutMenu:{
                Logout();

            }

        }
        return super.onOptionsItemSelected(item);
    }


    private void setupUIViews(){
        // get views by their ids
        profile=findViewById(R.id.cvTutorProfile);
        students=findViewById(R.id.cvTutorStudents);
        requests=findViewById(R.id.cvTutorRequests);
        reviews=findViewById(R.id.cvTutorReviews);
        search=findViewById(R.id.cvTutorSearch);


    }

}