package itp341.lin.feng_cheng.fresh;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import itp341.lin.feng_cheng.fresh.Model.Client.Vendor;
import itp341.lin.feng_cheng.fresh.Model.Market;

/**
 * Created by fredlin on 5/6/17.
 */

public class NewVendorFragment extends Fragment {


    private EditText bioEdit;
    private Spinner marketSpinner;
    private Spinner categorySpinner;
    private String mMarket;
    private String mBio;
    private String mCategory;


    private DatabaseReference dbVendor;


    private FirebaseDatabase database;

    public NewVendorFragment() {
        // Required empty public constructor
    }


    public static NewVendorFragment newInstance() {
        NewVendorFragment fragment = new NewVendorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //todo get database
        database = FirebaseDatabase.getInstance();

        //todo get database reference paths
        dbVendor = database.getReference(FirebaseReferences.VENDORS);


        Bundle args = getArguments();
        //todo get reference to note to be edited (if it exists)

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.create_vendor_detail, container, false);
        initializeComponents( v);
        setListeners();
        return v;

    }



    private void initializeComponents(View v){
        bioEdit = (EditText) v.findViewById(R.id.vendorDescription);
        bioEdit.setText("");  bioEdit.setHint("Enter a brief bio");
        marketSpinner = (Spinner) v.findViewById(R.id.locationSpinner);
        categorySpinner = (Spinner) v.findViewById(R.id.categorySpinner);

    }


    private  void setListeners(){
        marketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMarket = marketSpinner.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCategory = categorySpinner.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }



    public void createUserProfile(String userUID){
        mBio = bioEdit.getText().toString();
        //very hacky and bad practice?
        CreateUserActivity a = (CreateUserActivity) getActivity();
        String username = a.getNewUserName().getText().toString();
        Vendor v = new Vendor(username, "", new Market(mMarket) , mBio, mCategory);
//        dbRefCount.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                long noteCount = (Long) dataSnapshot.getValue();
//                dbRefCount.setValue(noteCount+1);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        dbVendor.child(userUID).setValue(v);

    }

}
