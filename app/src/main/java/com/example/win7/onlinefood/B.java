package com.example.win7.onlinefood;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public class B extends AppCompatActivity {

    Button btnMH,btnGJ,btnMP;
    Bundle b = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        ActionBar actionBar = getSupportActionBar();
        //Actionbar title
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference fDatabaseRoot = database.getReference();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnMH = (Button) findViewById(R.id.btnMH);
        btnGJ = (Button) findViewById(R.id.btnGJ);
        btnMP = (Button) findViewById(R.id.btnMP);
        if (CheckNetwork.isInternetAvailable(getApplicationContext())) //returns true if internet available
        {
            /*fDatabaseRoot.child("States").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Is better to use a List, because you don't know the size
                    // of the iterator returned by dataSnapshot.getChildren() to
                    // initialize the array
                    final List<String> propertyAddressList = new ArrayList<String>();

                    for (DataSnapshot addressSnapshot: dataSnapshot.getChildren()) {
                        String propertyAddress = addressSnapshot.child("StateName").getValue(String.class);
                        if (propertyAddress!=null){
                            propertyAddressList.add(propertyAddress);
                        }
                    }

                    ArrayAdapter<String> addressAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, propertyAddressList);
                    addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(addressAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });*/
        btnMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),C.class);
                b.putString("Path","Maharashtra");
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        btnGJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),C.class);
                b.putString("Path","Gujarat");
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        btnMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),C.class);
                b.putString("Path","Madhya pradesh");
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        }
        else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();

        }
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
