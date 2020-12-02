package com.example.tutorfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class SearchtutorActivity extends AppCompatActivity {

    private EditText mSearField;
    private ImageButton mSearchBtn;
    private RecyclerView mResultList;

    private DatabaseReference mTutorDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove app title from action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_searchtutor);

     //  mTutorDatabase= FirebaseDatabase.getInstance().getReference("tutors");

       /*


        mTutorDatabase= FirebaseDatabase.getInstance().getReference();
        mTutorDatabase.child("tutors");

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //mTutorDatabase= database.getReference("tutors");



        mSearField=findViewById(R.id.search_field);
        mSearchBtn=findViewById(R.id.searchBtn);
        mResultList=findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));


        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseTutorSearch();
            }
        });

        */

    }

    private void firebaseTutorSearch(){

        FirebaseRecyclerOptions<Tutors> options =
                new FirebaseRecyclerOptions.Builder<Tutors>()
                        .setQuery(mTutorDatabase, Tutors.class)
                        .build();


        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Tutors, TutorsViewHolder>(options) {
            @Override
            public TutorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_layout, parent, false);

                return new TutorsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(TutorsViewHolder holder, int position, Tutors model) {
                // Bind the Tutor object to the TutorsViewHolder
                // ...
                //holder.setDetails(getApplicationContext(), model.getName(), model.getDepartment(), model.getDescription());

                holder.setDetails(model.getFullname(),model.getPhonenumber(),model.getEmail(),model.getAddress(),model.getSubjects(),model.getPriceperhour(),model.getTutorphoto());
            }
        };
        /*

        FirebaseRecyclerAdapter<Tutors,TutorsViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Tutors, TutorsViewHolder>(
                Tutors.class,
                R.layout.list_layout,
                TutorsViewHolder.class,
                mTutorDatabase) {
            @Override
            protected void onBindViewHolder(@NonNull TutorsViewHolder tutorsViewHolder, int i, @NonNull Tutors tutors) {

            }

            @NonNull
            @Override
            public TutorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };

         */

        mResultList.setAdapter(adapter);

    }

    // View Holder Class
    public class TutorsViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public TutorsViewHolder(@NonNull View itemView) {
            super(itemView);

            mView=itemView;
        }

        public void setDetails(String tutorName, String tutorTel, String tutorEmail, String tutorAddress, String tutorSubject, String tutorPrice, String tutorImage){
            TextView tutor_name=(TextView)mView.findViewById(R.id.tvFullName);
            TextView tutor_tel=(TextView)mView.findViewById(R.id.tvPhoneNumber);
            TextView tutor_email=(TextView)mView.findViewById(R.id.tvEmail);
            TextView tutor_address=(TextView)mView.findViewById(R.id.tvAddress);
            TextView tutor_subject=(TextView)mView.findViewById(R.id.tvSubject);
            TextView tutor_price=(TextView)mView.findViewById(R.id.tvPrice);

            //TextView tutor_photo=(TextView)mView.findViewById(R.id.);
            CircleImageView tutor_photo=(CircleImageView)mView.findViewById(R.id.tutorPhoto);

            tutor_name.setText(tutorName);
            tutor_tel.setText(tutorTel);
            tutor_email.setText(tutorEmail);
            tutor_address.setText(tutorAddress);
            tutor_subject.setText(tutorSubject);
            tutor_price.setText(tutorPrice);

            Glide.with(getApplicationContext()).load(tutorImage).into(tutor_photo);


        }
    }
}