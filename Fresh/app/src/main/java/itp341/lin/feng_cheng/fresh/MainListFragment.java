package itp341.lin.feng_cheng.fresh;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import itp341.lin.feng_cheng.fresh.Model.Client.User;
import itp341.lin.feng_cheng.fresh.Model.Client.Vendor;
import itp341.lin.feng_cheng.fresh.Model.Product;
import itp341.lin.feng_cheng.fresh.Model.VendorSingleton;

/**
 * Created by fredlin on 4/2/17.
 */

public class MainListFragment extends Fragment {


    //get references
    private TextView text;
    private ListView list;

    private ArrayList<Vendor> vendors;
    private ArrayList<Vendor> listFromFirebase;
    private DatabaseReference dbVendor;
    //create our adapter
//    private ArrayAdapter<String> adapter;
    private VendorListAdapter vendorAdapter;

    public MainListFragment() {
        // Required empty public constructor
    }


    public static MainListFragment newInstance() {
        MainListFragment fragment = new MainListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listFromFirebase = new ArrayList<Vendor>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.vendor_list, container, false);
        list = (ListView) v.findViewById(R.id.vendorListView);

        //Get singleton or create one if haven't created (factory method)

        //add firebase stuff to the exisitng hardcode stuff
        //combine();

        //create my adapter




        vendors = VendorSingleton.get(getActivity()).getVendorsList();
        vendorAdapter = new VendorListAdapter(vendors, this);
        list.setAdapter(vendorAdapter);
        dbVendor = FirebaseDatabase.getInstance().getReference(FirebaseReferences.VENDORS);

        dbVendor.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("looping thru current vendor");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    System.out.println("iterate " );
                    Vendor user = snapshot.getValue(Vendor.class);
                    System.out.println(user);
                    vendors.add(user);
                    HashMap<String, ArrayList<Product>> map = VendorSingleton.get(getActivity()).getProductListMap();
                    map.put(user.getUsername(), user.getmProducts());
                    vendorAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }


    public void combine(){
        for(int i = 0 ; i < listFromFirebase.size(); i++){
            System.out.println("added vendor" + listFromFirebase.get(i)+ "to list: " );
            vendors.add(listFromFirebase.get(i));
        }
    }


    //inner class aka my customized Movie adpater
    public class VendorListAdapter extends ArrayAdapter<Vendor> {
        ArrayList<Vendor> mVendors;
        Fragment currentFrag;

        public VendorListAdapter(ArrayList<Vendor> mList, Fragment f) {
            super(getActivity(), 0, mList);
            currentFrag = f;
            mVendors = mList;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            //for each row item    (our custom layout)
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.vendor_list_item, null);
            }
            //set item labels
            Vendor v = vendors.get(position);
            TextView vendorName = (TextView) convertView.findViewById(R.id.vendorName);
            vendorName.setText(v.getUsername());
            TextView location = (TextView) convertView.findViewById(R.id.vendorListLocation);
            location.setText(v.getmMarket().getStrLoc());
            ImageView vendorImg = (ImageView) convertView.findViewById(R.id.vendorListImg);
            vendorImg.setImageResource(frontEndTestID(position));
            TextView bio = (TextView) convertView.findViewById(R.id.vendorListBio);
            bio.setText(v.getBio());

            //set Listener
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(getActivity(), VendorDetailActivity.class);
                    i.putExtra(VendorDetailActivity.CLICK_POSITION, position);
                    startActivityForResult(i, 0);
                }
            });

            return convertView;


        }

        public int frontEndTestID(int position){
            if(position==0){
                return R.drawable.v1;
            }
            else if(position==1){
                return R.drawable.v2;
            }
            else if(position==2){
                return R.drawable.v3;
            }
            else if(position==3) {
                return R.drawable.v4;
            }
            else{
                return R.drawable.v5;
            }
        }

    }
}
