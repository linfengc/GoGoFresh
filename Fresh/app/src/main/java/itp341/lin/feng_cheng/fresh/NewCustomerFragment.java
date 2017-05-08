package itp341.lin.feng_cheng.fresh;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import itp341.lin.feng_cheng.fresh.Model.Client.Customer;

/**
 * Created by fredlin on 5/6/17.
 */

public class NewCustomerFragment extends Fragment {


    private EditText cardEdit;
    private EditText addressEdit;
    private Spinner cardTypeSpinner;

    private String mCardType;
    private String mCardNum;
    private String mAddress;




    private DatabaseReference dbCustomer;


    private FirebaseDatabase database;

    public NewCustomerFragment() {
        // Required empty public constructor

    }


    public static NewCustomerFragment newInstance() {
        NewCustomerFragment fragment = new NewCustomerFragment();
        Bundle args = new Bundle();
//        args.putString("Key", UID);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle b = getArguments();
//        userUID = b.getString("Key");

        //todo get database
        database = FirebaseDatabase.getInstance();

        //todo get database reference paths
        dbCustomer = database.getReference(FirebaseReferences.CUSTOMERS);


        Bundle args = getArguments();
        //todo get reference to note to be edited (if it exists)
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.create_customer_detail, container, false);
        initializeComponents( v);
        setListeners();
        return v;

    }

    private void initializeComponents(View v){
        cardEdit = (EditText) v.findViewById(R.id.customerCardNum);
        addressEdit = (EditText) v.findViewById(R.id.createCustomerAddress);
        cardTypeSpinner = (Spinner) v.findViewById(R.id.cardTypeSpinner);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MavenPro-Bold.ttf");
        cardEdit.setText(""); cardEdit.setHint("Enter your card numer");
        addressEdit.setText(""); addressEdit.setHint("Enter your address");
        cardEdit.setTypeface(font);
        addressEdit.setTypeface(font);
        TextView  lable = (TextView) v.findViewById(R.id.newCustomerLabel);
        lable.setTypeface(font);



    }


    private  void setListeners(){
        cardTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               mCardType  = cardTypeSpinner.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });



    }



    public void createUserProfile(String userUID){
        mCardNum = cardEdit.getText().toString();
        mAddress = addressEdit.getText().toString();
        CreateUserActivity a = (CreateUserActivity) getActivity();
        String userName = a.getNewUserName().getText().toString();
        Customer c = new Customer(userName,"", mCardType, mCardNum);

        dbCustomer.child(userUID).setValue(c);
        System.out.println("Write in database with UID: " + userUID);

        //TODO: THIS IS BAD.
//        DatabaseReference dbChild = dbCustomer.push();
//        dbChild.setValue(c);
    }
}
