package itp341.lin.feng_cheng.fresh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

import itp341.lin.feng_cheng.fresh.Model.Client.Customer;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by fredlin on 4/24/17.
 */

public class UserAuthentication extends Fragment {


    private TextView warningLabel;
    private EditText userName;
    private EditText userPassword;
    private Button loginButton;
    private Button createAccount;
    private String TAG = "firebase error";
    private boolean loggedIn = false;
    private boolean newUser = false;

    //Auth variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    private DatabaseReference dbUser;



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
        userName = (EditText) v.findViewById(R.id.userNameField);
        userPassword = (EditText) v.findViewById(R.id.passwordField);
        warningLabel = (TextView) v.findViewById(R.id.warningLabel);
        userName.setHint("Email");
        userPassword.setHint("Password");
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
                String email = userName.getText().toString();
                String password = userPassword.getText().toString();
                signInAccount(email, password);
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userName.getText().toString();
                String password = userPassword.getText().toString();
                createNewAccount(email, password);
            }
        });
    }


    private void login(){
        System.out.println("in login func");

        dbUser = FirebaseDatabase.getInstance().getReference();

        if(loggedIn){
            boolean customer = false;

            dbUser.child("customers").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Type object = (Type) dataSnapshot.child("keyname");
                    System.out.println("trying to retrieve data***********");
                    Customer c = dataSnapshot.getValue(Customer.class);
                    //String cardType = (String) dataSnapshot.child("cardNum");
                    System.out.println("userUID: "+ user.getUid());
                    System.out.println(c);
                    System.out.println("You are DONE printing");
                    boolean isCustomer = false;
                    if(c!= null){
                        isCustomer = true;
                    }

                    loadUser(isCustomer, user.getUid());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });





//            //test google map
//            Intent i = new Intent(getActivity().getApplicationContext(), MapsActivity.class);
//            startActivityForResult(i,2);
        }


    }

    public void loadUser(boolean customer, String UID){


        Fragment f ;
        if(customer){
            f = MainListFragment.newInstance();

        }
        else{

            f = VendorProfileFragment.newInstance(UID);
        }


        FragmentManager fm = getActivity().getSupportFragmentManager();
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


    private void createNewAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                        else {
                            newUser=true;
                            Toast.makeText(getActivity(), "yes", Toast.LENGTH_SHORT).show();
                            user = mAuth.getCurrentUser();
                            createNewUserProfile(user.getUid());
                        }


                    }
                });
    }
    //TODO check if the field and empty and handle it
    private void signInAccount(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            warningLabel.setText("Username or Password not correct");
                            return;
                        }
                        else {

                            user = FirebaseAuth.getInstance().getCurrentUser();
                            loggedIn = true;
                            login();
                        }
                    }
                });



    }

    private void createNewUserProfile(String userUID){
        Intent i = new Intent(getActivity().getApplicationContext(), CreateUserActivity.class);
        i.putExtra("KeyUID", userUID);
        startActivityForResult(i,1);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(getActivity(), "executing onActivityResult", Toast.LENGTH_SHORT).show();
        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFF****************");
        if(resultCode== Activity.RESULT_OK){
            Toast.makeText(getActivity(), "Just Got back", Toast.LENGTH_SHORT).show();
            System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDD****************");
            if(requestCode == 1) {
                Toast.makeText(getActivity(), "RequestValid", Toast.LENGTH_SHORT).show();
                int idx =data.getIntExtra(CreateUserActivity.NEWUSERKEY,0);
                //load stats if it's a vendor
                loggedIn = true;
                if(idx==0){
                    Toast.makeText(getActivity(), "A new Vendor has been created", Toast.LENGTH_SHORT).show();
                }
                else {//load map if it's a customer
                    Toast.makeText(getActivity(), "A new Customer has been created", Toast.LENGTH_SHORT).show();
                    login();

                }




            }



        }else{
            System.out.println("DSsssSSSSSSAAAAAAAAAAAAAAAAAAAAAAAD****************");
        }

    }






}
