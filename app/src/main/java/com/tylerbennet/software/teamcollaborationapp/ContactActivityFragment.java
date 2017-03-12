package com.tylerbennet.software.teamcollaborationapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.google.android.gms.internal.zzt.TAG;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContactActivityFragment extends Fragment {

    // Write a message to the database
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private DatabaseReference myRef;
    private static final String LOG_TAG = ContactActivityFragment.class.getSimpleName();
    User applicationUser;
    private String customerNumber;
    private String userNumber;

    public ContactActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("customers/1/users/1/cellNumber");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        customerNumber = "1";
        userNumber = "1";



        //Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                //Log.d(TAG, "Value is: " + value);

                User user = new User();
                user.setCellNumber(dataSnapshot.getValue().toString());
                Log.w(TAG, user.getCellNumber());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        return inflater.inflate(R.layout.fragment_contact, container, false);
    }



}
