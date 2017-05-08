package itp341.lin.feng_cheng.fresh.Model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import itp341.lin.feng_cheng.fresh.Model.Client.Vendor;

/**
 * Created by fredlin on 4/2/17.
 */

public class VendorSingleton {

    private Context context;
    private static VendorSingleton mSingleton;
    private ArrayList<Vendor> vendorsList;
    public HashMap<String, ArrayList<Product>> productListMap;



    //singleton private constructor
    //singleton private constructor
    private VendorSingleton(Context c){
        //only start once;
        vendorsList = new ArrayList<Vendor>();
        context = c;
        productListMap = new HashMap<String, ArrayList<Product>>();



    }

    public HashMap<String, ArrayList<Product>> getProductListMap() {
        return productListMap;
    }

    public void setProductListMap(HashMap<String, ArrayList<Product>> productListMap) {
        this.productListMap = productListMap;
    }

    public static VendorSingleton getmSingleton() {
        return mSingleton;
    }

    public static void setmSingleton(VendorSingleton mSingleton) {
        VendorSingleton.mSingleton = mSingleton;
    }

    public static VendorSingleton get(Context c){

        if(mSingleton == null){ //if not yet created, create!
            mSingleton = new VendorSingleton(c.getApplicationContext());

            //hard code stuff to check front end
            Vendor v1 = new Vendor("usera", "password",new Market("Santa Monica") ,"","fruit");
            Vendor v2 = new Vendor("userb", "password",new Market("West Los Angeles"),"","Brunch/Lunch" );
            Vendor v3 = new Vendor("userc", "password",new Market("Hollywood") ,"","Drink");
            Vendor v4 = new Vendor("userd", "password",new Market("Santa Monica"),"", "Bread");
            Vendor v5 = new Vendor("usere", "password",new Market("West Los Angeles") ,"", "Vegetable");
            v1.setBio("i am vendor1 I sell fruit");
            v2.setBio("i am vendor2 I sell burrito");
            v3.setBio("i am vendor3 I sell drinks");
            v4.setBio("i am vendor4 I sell bread");
            v5.setBio("i am vendor5 I sell fruit");
            ArrayList<Product> sampleProductList = new ArrayList<Product>();
            Product p1 = new Product(3 , new ArrayList<String>(), 100, "Strawberry", "fresh Strawberry");
            Product p2= new Product(4 , new ArrayList<String>(), 100, "Bread", "fresh bread");
            Product p3 = new Product(9.50 , new ArrayList<String>(), 100, "Breakfast Burrito", "fresh fresh");
            Product p4 = new Product(0.99 , new ArrayList<String>(), 100, "Banana", "fresh fresh");
            Product p5 = new Product(3.5 , new ArrayList<String>(), 100, "Lemonade", "fresh fresh" );
            mSingleton.productListMap.put(v1.getUsername(), new ArrayList<Product>());
            mSingleton.productListMap.put(v2.getUsername(), new ArrayList<Product>());
            mSingleton.productListMap.put(v3.getUsername(), new ArrayList<Product>());
            mSingleton.productListMap.put(v4.getUsername(), new ArrayList<Product>());
            mSingleton.productListMap.put(v5.getUsername(), new ArrayList<Product>());
            //v1
            mSingleton.productListMap.get(v1.getUsername()).add(p1);
            mSingleton.productListMap.get(v1.getUsername()).add(p4);
            //v2
            mSingleton.productListMap.get(v2.getUsername()).add(p3);
            //v3
            mSingleton.productListMap.get(v3.getUsername()).add(p5);
            //v4
            mSingleton.productListMap.get(v4.getUsername()).add(p2);
            mSingleton.productListMap.get(v4.getUsername()).add(p3);
            //v5
            mSingleton.productListMap.get(v5.getUsername()).add(p1);
            mSingleton.productListMap.get(v5.getUsername()).add(p4);


            mSingleton.addVendor(v1);
            mSingleton.addVendor(v2);
            mSingleton.addVendor(v3);
            mSingleton.addVendor(v4);
            mSingleton.addVendor(v5);



        }
        return mSingleton;

    }



    public ArrayList<Vendor> getVendorsList() {
        return vendorsList;
    }

    public void setVendorsList(ArrayList<Vendor> vendorsList) {
        this.vendorsList = vendorsList;
    }


    public Vendor getVendor(int index){
        return vendorsList.get(index);
    }

    public int getNum(){
        return vendorsList.size();
    }

    public void addVendor(Vendor v){
        vendorsList.add(v);
    }



    public void removeVendor(int index){
        if(index>=0 && index<= vendorsList.size()-1)
            vendorsList.remove(index);

    }

    public void updateVendor(Vendor m, int position){
        if(position>=0 && position<=vendorsList.size()-1)
            vendorsList.set(position, m);
    }



    public ArrayList<Product> getProductList(String vendorName){
        Log.d("In singleton", "  returnning a list of product from vendorName" + vendorName);
        ArrayList<Product> p = productListMap.get(vendorName);
        for(int i = 0 ; i < p.size(); i++){
            Log.d("printing productlist: " , p.get(i).getName());
        }
        return productListMap.get(vendorName);
    }

}
