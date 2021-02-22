package com.example.win7.onlinefood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

public class D extends AppCompatActivity {
    LinearLayoutManager mLayoutManager1; //for sorting
    SharedPreferences mSharedPref1; //for saving sort settings
    RecyclerView mRecyclerView1;
    FirebaseDatabase mFirebaseDatabase1;
    DatabaseReference mRef1;
    //progress dialog
    ProgressDialog progressDialog;
    String mSorting1;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);

        //Action bar
        ActionBar actionBar = getSupportActionBar();
        //Actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (CheckNetwork.isInternetAvailable(getApplicationContext())) //returns true if internet available
        {
        progressDialog = new ProgressDialog(this);
        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();


        //set title
        mSharedPref1 = getSharedPreferences("SortSettings", MODE_PRIVATE);
        mSorting1 = mSharedPref1.getString("Sort", "newest"); //where if no settingsis selected newest will be default
        mLayoutManager1 = new LinearLayoutManager(this);
        mLayoutManager1.setReverseLayout(true);
        mLayoutManager1.setStackFromEnd(true);

        //RecyclerView
        mRecyclerView1 = (RecyclerView) findViewById(R.id.recyclerView1);
        //gridLayoutManager = new GridLayoutManager(this, 2);

        //gridLayoutManager.setReverseLayout(true);

        //set layout as LinearLayout
        mRecyclerView1.setLayoutManager(mLayoutManager1);
        //mRecyclerView.setLayoutManager(gridLayoutManager);
        Bundle b2 = getIntent().getExtras();
        String DATABASE_PATH = b2.getString("Path");

        //send Query to FirebaseDatabase
        mFirebaseDatabase1 = FirebaseDatabase.getInstance();
        mRef1 = mFirebaseDatabase1.getReference().child(DATABASE_PATH);
        }
        else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();

        }
    }
    //load data into recycler view onStart
    @Override
    protected void onStart() {
        super.onStart();
        mRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    FirebaseRecyclerAdapter<Model1, ViewHolder1> firebaseRecyclerAdapter =
                            new FirebaseRecyclerAdapter<Model1, ViewHolder1>(
                                    Model1.class,
                                    R.layout.row1,
                                    ViewHolder1.class,
                                    mRef1
                            ) {
                                @Override
                                protected void populateViewHolder(ViewHolder1 viewHolder, Model1 model, int position) {
                                    viewHolder.setDetails(getApplicationContext(), model.getImage(),model.getName());
                                    progressDialog.dismiss();

                                }

                                @Override
                                public ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {

                                    ViewHolder1 viewHolder = super.onCreateViewHolder(parent, viewType);

                                    viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {

                                            ImageView mImageView = (ImageView) view.findViewById(R.id.rImageView1);
                                            TextView mNameTv = (TextView) view.findViewById(R.id.txtName);



                                            if (mImageView.getDrawable() == null) {
                                                Toast.makeText(getApplicationContext(), "Wait Data is Loading", Toast.LENGTH_SHORT).show();
                                            } else {
//get data from views
                                                String mName = mNameTv.getText().toString();


                                                Drawable mDrawable = mImageView.getDrawable();
                                                Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

                                                //pass this data to new activity



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
                    mRecyclerView1.setAdapter(firebaseRecyclerAdapter);


                }else{
                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(),"Not Available",Toast.LENGTH_LONG).show();
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
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
