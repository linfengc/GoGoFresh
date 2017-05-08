package itp341.lin.feng_cheng.fresh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import itp341.lin.feng_cheng.fresh.Model.Product;
import itp341.lin.feng_cheng.fresh.Model.VendorSingleton;

/**
 * Created by fredlin on 4/2/17.
 */

public class VendorDetailFragment extends Fragment {
    private static final String TAG = VendorDetailFragment.class.getSimpleName();

    //Bundle key
    public static final String ARGS_POSITION = "args_position";

    private ProductListAdapter productListAdapter;



    private ListView list;
    private String vendorName;

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

    //TODO read bundle argument
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.order_list, container, false);
        list = (ListView) v.findViewById(R.id.orderListView);
        int position = getArguments().getInt(ARGS_POSITION);
        ArrayList<Vendor> vendors = VendorSingleton.get(getActivity()).getVendorsList();
        products = VendorSingleton.get(getActivity()).getProductList(vendors.get(position).getUsername());
        vendorName = VendorSingleton.get(getActivity()).getVendorsList().get(position).getUsername();
        productListAdapter = new ProductListAdapter(products, this);

        list.setAdapter(productListAdapter);
        return v;

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
            System.out.println("*****************int vendorDetail Position is " + position);
            final Product p = mProducts.get(position);
            Log.d("Product to string " , p.toString());
            TextView productName = (TextView) convertView.findViewById(R.id.orderListProductName);
            productName.setText(p.getName());
            TextView price = (TextView) convertView.findViewById(R.id.orderListProductPrice);
            price.setText(String.valueOf(p.getPrice()));
            ImageView img=  (ImageView) convertView.findViewById(R.id.orderListProductImg);
            img.setImageResource(frontEndTestID(p.getName()));



            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(getActivity(), ProductPurchaseActivity.class);
                    System.out.println("pasing this product to purchaseAct: " + p);
                    i.putExtra(ProductPurchaseActivity.PRODUCT_TAG, p);
                    i.putExtra("Key", vendorName);
                    i.putExtra(ProductPurchaseActivity.IMG_TAG, frontEndTestID(p.getName()));
                    startActivityForResult(i, 0);
                }
            });


            return convertView;

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


    }


    }
