package itp341.lin.feng_cheng.fresh;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by fredlin on 4/24/17.
 */

public class UserAuthentication extends Fragment {

    private EditText userName;
    private EditText password;
    private Button loginButton;
    private Button createAccount;
    private String TAG = "firebase error";

    //Auth variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    public UserAuthentication(){


    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        initializeComponents(v);
        setListeners();
        return v;
    }



    public static UserAuthentication newInstance() {
        UserAuthentication fragment = new UserAuthentication();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    private void initializeComponents(View v){
        loginButton = (Button) v.findViewById(R.id.loginButton);
        createAccount = (Button) v.findViewById(R.id.creatAccountButton);
    }


    private void setListeners(){

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        //authentication process
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    private void login(){
        System.out.println("in login func");
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragment_container);
        f = MainListFragment.newInstance();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, f);
        fragmentTransaction.commit();
    }


    //firebase methods
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






}
