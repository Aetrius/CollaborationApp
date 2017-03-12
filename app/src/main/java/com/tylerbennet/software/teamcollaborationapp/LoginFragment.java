package com.tylerbennet.software.teamcollaborationapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by Tyler Bennet on 3/8/2017.
 */

public class LoginFragment extends Fragment{
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String LOG_TAG = LoginFragment.class.getSimpleName();
    public static final String PREFS_NAME = "MyPrefsFile";

    private String username;
    private String userPassword;

    public LoginFragment() {
        username = "";
        userPassword = "";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //user is signed in
                    Log.d(LOG_TAG, "onAuthStateChanged:signed_in: " + user.getUid());
                } else {
                    //User is signed out
                    Log.d(LOG_TAG, "onauthStateChanged:signed_out");
                }
            }
        };

    }

    public void writeToDatabase() {
        //Toast.makeText(getContext(), "Writing message to database", Toast.LENGTH_SHORT).show();
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        myRef.setValue("aspicer");
    }

      //Tries to sign in a user with the given email address and password.
    public void loginUser() {
        Toast.makeText(getContext(), "Logging user in", Toast.LENGTH_SHORT).show();
        mAuth.signInWithEmailAndPassword(this.username, this.userPassword)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(LOG_TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            Intent mainIntent = new Intent(getActivity(), PrimaryActivity.class);
                            startActivity(mainIntent);
                        }

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(LOG_TAG, "signInWithEmail", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed. " + task.getException(), Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }

    //Signs out the current user and clears it from the disk cache.
    public void logoutUser() {
        Toast.makeText(getContext(), "Successfully logged user out", Toast.LENGTH_SHORT).show();
        mAuth.signOut();


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view;
        view = inflater.inflate(R.layout.fragment_login, container, false);


        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        this.username = settings.getString("username", "username");
        this.userPassword = settings.getString("password", "password");


        final Button loginBtn = (Button) view.findViewById(R.id.btnLogin);
        final EditText usernameET = (EditText) view.findViewById(R.id.emailAddress);
        final EditText passwordET = (EditText) view.findViewById(R.id.password);
        final ImageView appLogo = (ImageView) view.findViewById(R.id.appLogo);

        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();

        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(displaymetrics.widthPixels/2, displaymetrics.heightPixels/4);

        //Set image view size based on screensizes
        //appLogo.setLayoutParams(layoutParams);
        appLogo.setImageResource(R.drawable.main);
        usernameET.setHintTextColor(ContextCompat.getColor(view.getContext(), R.color.colorWhite));
        passwordET.setHintTextColor(ContextCompat.getColor(view.getContext(), R.color.colorWhite));
        usernameET.setHint("Username");
        usernameET.setText(this.username);
        passwordET.setHint("Password");
        passwordET.setText(this.userPassword);

        if (usernameET != null && passwordET != null) {
            loginUser();
        }


        usernameET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    usernameET.setHint("");
                } else {
                    usernameET.setHint("Username");
                }
            }
        });

        passwordET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    passwordET.setHint("");
                } else {
                    passwordET.setHint("Username");
                }
            }
        });



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectLoginInformation();
                loginUser();
            }
        });

        return view;
    }

    public void collectLoginInformation() {
        EditText user = (EditText) getView().findViewById(R.id.emailAddress);
        EditText pass = (EditText) getView().findViewById(R.id.password);
        this.username = user.getText().toString();
        this.userPassword = pass.getText().toString();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("username", this.username);
        editor.putString("password", this.userPassword);
        // Commit the edits!
        editor.commit();
    }


}
