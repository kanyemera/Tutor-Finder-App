package com.example.tutorfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    //private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //remove app title from action bar
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_registration);

        firebaseAuth=FirebaseAuth.getInstance();

    }


    //put logout codes inside a function
    private void Logout(){
        firebaseAuth.signOut();
        //finish();
        startActivity(new Intent(RegistrationActivity.this,MainActivity.class));

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
        //signup=findViewById(R.id.btnRegister);
        //email=findViewById(R.id.edtEmail);
       // password=findViewById(R.id.edtSignupPassword);
        //goToLogin=findViewById(R.id.tvGoBack);
    }
//
}