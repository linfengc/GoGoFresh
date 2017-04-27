package itp341.lin.feng_cheng.fresh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import itp341.lin.feng_cheng.fresh.Model.Client.Vendor;
import itp341.lin.feng_cheng.fresh.Model.VendorSingleton;

/**
 * Created by fredlin on 4/2/17.
 */

public class MainListFragment extends Fragment {


    //get references
    private TextView text;
    private ListView list;

    private ArrayList<Vendor> vendors;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.vendor_list, container, false);
        list = (ListView) v.findViewById(R.id.vendorListView);

        //Get singleton or create one if haven't created (factory method)
        vendors = VendorSingleton.get(getActivity()).getVendorsList();
        //create my adapter
        vendorAdapter = new VendorListAdapter(vendors, this);
        list.setAdapter(vendorAdapter);

        return v;
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
            location.setText("Santa Monica");
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
