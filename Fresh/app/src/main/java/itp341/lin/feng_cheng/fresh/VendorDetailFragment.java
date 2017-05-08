package itp341.lin.feng_cheng.fresh;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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

import itp341.lin.feng_cheng.fresh.Model.Client.Vendor;
import itp341.lin.feng_cheng.fresh.Model.Product;
import itp341.lin.feng_cheng.fresh.Model.VendorSingleton;

import static android.media.CamcorderProfile.get;
import static itp341.lin.feng_cheng.fresh.VendorDetailActivity.CLICK_POSITION;

/**
 * Created by fredlin on 4/2/17.
 */

public class VendorDetailFragment extends Fragment {
    private static final String TAG = VendorDetailFragment.class.getSimpleName();

    //Bundle key
    public static final String ARGS_POSITION = "args_position";

    private ProductListAdapter productListAdapter;


    double longi = 0.0;
    double lat =0.0 ;



    private ListView list;
    private String vendorName;
    DatabaseReference dbVendor;
    Typeface font;

    private ArrayList<Product> products;

    public VendorDetailFragment(){

    }

    //argument change after getting a database???
    public static VendorDetailFragment newInstance(int position) {
        VendorDetailFragment f = new VendorDetailFragment();
        Bundle args = new Bundle();
        args.putInt(VendorDetailFragment.ARGS_POSITION, position);

        f.setArguments(args);

        return f;
    }

    public void translateLoc(String loc){

        if(loc.equalsIgnoreCase("Santa Monica")){
           longi = 34.004792;
            lat = -118.48654;
        }
        else if(loc.equalsIgnoreCase("Hollywood")){
            longi = 34.099724;
            lat = -118.328758;
        }
        else{
            longi= 34.0458348958;
            lat = -118.450723684;

        }
    }
    //TODO read bundle argument
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);








    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.order_list, container, false);
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AmaticSC-Bold.ttf");
        int position = getArguments().getInt(ARGS_POSITION);
        vendorName = VendorSingleton.get(getActivity()).getVendorsList().get(position).getUsername();
        System.out.println("tyring to CREATMAP in deatial frag with name" + vendorName);
        //find userLocation
        dbVendor = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.VENDORS);
        dbVendor.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    System.out.println("MAP::::: iterate in the MAP ");
                    Vendor user = snapshot.getValue(Vendor.class);
                    System.out.println("current******VendorName: " + user.getUsername() + "and " + vendorName);
                    if (user.getUsername().equalsIgnoreCase(vendorName)) {

                        String location = user.getmMarket().getStrLoc();
                        translateLoc(location);
                        //load map
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        System.out.println("*start(***longi lat: " + longi + " " + lat);
                        Fragment f = MapViewFragment.newInstance(longi, lat);
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.pinPointMap, f);
                        fragmentTransaction.commit();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
        list = (ListView) v.findViewById(R.id.orderListView);

        ArrayList<Vendor> vendors = VendorSingleton.get(getActivity()).getVendorsList();
        products = VendorSingleton.get(getActivity()).getProductList(vendors.get(position).getUsername());

        productListAdapter = new ProductListAdapter(products, this);

        list.setAdapter(productListAdapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product pro = products.get(position);
                Intent i = new Intent(getActivity(), ProductPurchaseActivity.class);
                System.out.println("pasing this product to purchaseAct: " + pro);
                i.putExtra(ProductPurchaseActivity.PRODUCT_TAG, pro);
                i.putExtra("Key", vendorName);
                i.putExtra(ProductPurchaseActivity.IMG_TAG, frontEndTestID(pro.getName()));
                startActivityForResult(i, 0);
            }
        });


        return v;

    }

    public int frontEndTestID(String s){
        if(s.equalsIgnoreCase("Strawberry")){
            return R.drawable.p1;
        }
        else if(s.equalsIgnoreCase("bread")){
            return R.drawable.p2;
        }
        else if(s.equalsIgnoreCase("breakfast burrito")){
            return R.drawable.p3;
        }
        else if(s.equalsIgnoreCase("Banana")) {
            return R.drawable.p4;
        }
        else if(s.equalsIgnoreCase("Apple")){
            return  R.drawable.p6;
        }
        else{
            return R.drawable.p5;
        }
    }




    public class ProductListAdapter extends ArrayAdapter<Product> {
        ArrayList<Product> mProducts;
        Fragment currentFrag;

        public ProductListAdapter(ArrayList<Product> mList, Fragment f){
            super(getActivity(), 0, mList);
            currentFrag = f;
            mProducts = mList;


        }

        public View getView(int position, View convertView, ViewGroup parent) {
            //for each row item    (our custom layout)
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.order_list_item, null);
            }

            final Product p = mProducts.get(position);
            Log.d("Product to string " , p.toString());
            TextView productName = (TextView) convertView.findViewById(R.id.orderListProductName);
            productName.setText(p.getName());
            TextView price = (TextView) convertView.findViewById(R.id.orderListProductPrice);
            price.setText(String.valueOf(p.getPrice()));
            ImageView img=  (ImageView) convertView.findViewById(R.id.orderListProductImg);
            img.setImageResource(frontEndTestID(p.getName()));


            productName.setTypeface(font);
            price.setTypeface(font);

            TextView t1 = (TextView) convertView.findViewById(R.id.label4);
            TextView t2 = (TextView) convertView.findViewById(R.id.label5);

            t1.setTypeface(font);
            t2.setTypeface(font);


            return convertView;

        }








    }


    }
