package com.example.win7.onlinefood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class A extends AppCompatActivity {
    int mFlipping = 0 ; // Initially flipping is off

    LinearLayoutManager mLayoutManager; //for sorting
    SharedPreferences mSharedPref; //for saving sort settings
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    //progress dialog
    ProgressDialog progressDialog;
    String mSorting;
    AlertDialog.Builder builder;
    TextView tv;
    String share;
    Button button2,button3,button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        //Action bar
        ActionBar actionBar = getSupportActionBar();
        //Actionbar title
        builder = new AlertDialog.Builder(this);

        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        tv = (TextView) findViewById(R.id.textId);
        tv.setSelected(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (CheckNetwork.isInternetAvailable(getApplicationContext())) //returns true if internet available
        {
            SharedPreferences spf = getSharedPreferences("share", Context.MODE_PRIVATE);
            share = spf.getString("share","");

            progressDialog = new ProgressDialog(this);
            //displaying progress dialog while fetching images
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            //set title
            mSharedPref = getSharedPreferences("SortSettings", MODE_PRIVATE);
            mSorting = mSharedPref.getString("Sort", "newest"); //where if no settingsis selected newest will be default
            mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),B.class);
                startActivity(intent);
            }
        });
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),E.class);
                    startActivity(intent);
                }
            });
            //RecyclerView
            mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            //gridLayoutManager = new GridLayoutManager(this, 2);

            //gridLayoutManager.setReverseLayout(true);

            //set layout as LinearLayout
            mRecyclerView.setLayoutManager(mLayoutManager);
            //mRecyclerView.setLayoutManager(gridLayoutManager);

            //send Query to FirebaseDatabase
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mRef = mFirebaseDatabase.getReference().child("Promotions");

            ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper1);
            if (mFlipping == 0) {
                /** Start Flipping */
                flipper.startFlipping();
                mFlipping = 1;
            } else {
                /** Stop Flipping */
                flipper.stopFlipping();
                mFlipping = 0;
            }

        }
        else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();

        }
    }
    //load data into recycler view onStart
    @Override
    protected void onStart() {
        super.onStart();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerAdapter =
                            new FirebaseRecyclerAdapter<Model, ViewHolder>(
                                    Model.class,
                                    R.layout.row,
                                    ViewHolder.class,
                                    mRef
                            ) {
                                @Override
                                protected void populateViewHolder(ViewHolder viewHolder, Model model, int position) {
                                    viewHolder.setDetails(getApplicationContext(), model.getImage());
                                    progressDialog.dismiss();

                                }

                                @Override
                                public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                                    ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

                                    viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {

                                            ImageView mImageView = (ImageView) view.findViewById(R.id.rImageView);

                                            if (mImageView.getDrawable() == null) {
                                                Toast.makeText(getApplicationContext(), "Wait Data is Loading", Toast.LENGTH_SHORT).show();
                                            } else {

                                            }

                                        }

                                        @Override
                                        public void onItemLongClick(View view, int position) {
                                            //TODO do your own implementaion on long item click
                                        }
                                    });


                                    return viewHolder;
                                }

                            };

                    //set adapter to recyclerview
                    mRecyclerView.setAdapter(firebaseRecyclerAdapter);


                }else{
                    progressDialog.dismiss();

                    //Toast.makeText(getApplicationContext(),"Product will Comming soon",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();

                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                System.exit(0);
                break;
        }
        return true;
    }
}
