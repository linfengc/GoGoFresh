package itp341.lin.feng_cheng.fresh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by fredlin on 5/6/17.
 */

public class CreateUserActivity extends AppCompatActivity{

    private EditText newUserName;
    private Button createButton;
    private RadioGroup userTypeGroup;
    private Fragment f;
    int userIdx = 0;
    String userUID ;
    public final static String NEWUSERKEY = "this is for me to create new user blah blah";

    //TODO get user instance from intent when moving on
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_user);

        Intent data = getIntent();
        userUID = data.getStringExtra("KeyUID");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("CreateUserActivity", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("CreateUserActivity", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        initializeComponents();
        setRadioButListener();


    }

    private void initializeComponents(){
        userTypeGroup = (RadioGroup) findViewById(R.id.userTypeRadio);
        userTypeGroup.check(0);
        newUserName  = (EditText) findViewById(R.id.createName);
        newUserName.setText("");
        newUserName.setHint("Please Enter Your Name");
        createButton = (Button) findViewById(R.id.createNewUserBut);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                if(userIdx == 0){
                    NewVendorFragment vendorF = (NewVendorFragment) f;
                    vendorF.createUserProfile(userUID);

                    i.putExtra(NEWUSERKEY, 0);
                }
                else{
                    NewCustomerFragment customerF = (NewCustomerFragment) f;
                    System.out.println("passing in uid to fragment: " + userUID);
                    customerF.createUserProfile(userUID);

                    i.putExtra(NEWUSERKEY,1);
                }
                setResult(Activity.RESULT_OK,i);
                finish();


            }
        });
    }
    private void updateFragment(){
        FragmentManager fm = getSupportFragmentManager();
        f = fm.findFragmentById(R.id.createUserDetailFrag_container);
        if(userIdx == 0){ //0 means
            f = NewVendorFragment.newInstance();

        }
        else{
            f = NewCustomerFragment.newInstance();

        }


        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.createUserDetailFrag_container, f);
        ft.commit();
    }


    public void setRadioButListener() {

        userTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup userTypeGroup, int checkedId) {
                // checkedId is the RadioButton selected
                userIdx = checkedId - 1;
                System.out.println("getIndex: " + checkedId);
                updateFragment();

            }
        });

    }


    public EditText getNewUserName(){
        return newUserName;
    }





}
