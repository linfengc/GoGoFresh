package itp341.lin.feng_cheng.fresh;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.ArrayList;


import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import itp341.lin.feng_cheng.fresh.Model.Client.Vendor;
import itp341.lin.feng_cheng.fresh.Model.Product;
import itp341.lin.feng_cheng.fresh.Model.VendorSingleton;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.os.Build.VERSION_CODES.N;


/**
 * Created by fredlin on 5/7/17.
 */

public class VendorProfileFragment extends Fragment {


    //get references
    private TextView userName;
    private TextView balance;
    private Button addButton;

    private ListView list;
    private String userUID;


    FirebaseListAdapter mAdapter;


    //todo database references

    DatabaseReference dbProductEntry;
    DatabaseReference dbVendor;
    FirebaseDatabase database;

    public VendorProfileFragment() {
        // Required empty public constructor
    }


    public static VendorProfileFragment newInstance(String UID) {
        VendorProfileFragment fragment = new VendorProfileFragment();
        Bundle args = new Bundle();
        args.putString("Key", UID);
        fragment.setArguments(args);
        return fragment;
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        userUID = b.getString("Key");

        //todo get database
        database = FirebaseDatabase.getInstance();
        dbVendor = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.VENDORS);

        dbProductEntry = dbVendor.child(userUID).child(FirebaseReferences.PRODUCTLIST);
        //todo get database reference paths
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.vendor_profile, container, false);
        initializeComponents(v);
        list = (ListView) v.findViewById(R.id.vendorProfileProductList);


        //todo instantiate adapter
        mAdapter = new ProductAdpater(getActivity(), Product.class, R.layout.vendor_product_entry, dbProductEntry);
        //todo set adapter for listview
        list.setAdapter(mAdapter);



        return v;
    }


    public void initializeComponents(View v){
        userName = (TextView) v.findViewById(R.id.vendorProfileName);
        balance = (TextView) v.findViewById(R.id.vendorProfileBalanceLabel);
        addButton = (Button) v.findViewById(R.id.vendorProfileAddButton);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AmaticSC-Bold.ttf");
        userName.setTypeface(font);
        balance.setTypeface(font);
        addButton.setTypeface(font);

        System.out.println("chjeck dbvendor ref: " + dbVendor);
        dbVendor.child(userUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Vendor v =(Vendor) dataSnapshot.getValue(Vendor.class);
                userName.setText(v.getUsername());
                balance.setText("$" + String.valueOf(v.getmBalance()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), CreateNewProductAcitivity.class);
                i.putExtra("Key", userUID);
                startActivityForResult(i,1);
            }
        });
    }

    //TODO create custom FirebaseListAdapter
    private class ProductAdpater extends  FirebaseListAdapter<Product>{
        //build a constructor

        public ProductAdpater(Activity activity, Class<Product> modelClass, int modelLayout, DatabaseReference ref) {
            super(activity, modelClass, modelLayout, ref);
        }

        @Override
        protected void populateView(View v, Product model, int position) {
            //get references to row widgets
            //copy data from model to widgets
            TextView productName = (TextView) v.findViewById(R.id.listNoteTitle);
            TextView productNum = (TextView) v.findViewById(R.id.listNoteAuthor);
            TextView productInfo = (TextView) v.findViewById(R.id.listNoteContent);

            productName.setText(model.getName());
            productNum.setText("Quantity: " + String.valueOf(model.getQuantity()));
            productInfo.setText("Unit Price: "+ model.getPrice()+ "\n" +
                    model.getProductInfo());




            Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AmaticSC-Bold.ttf");
            productName.setTypeface(font);
            productNum.setTypeface(font);
            productInfo.setTypeface(font);



        }
    }



}
