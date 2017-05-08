package itp341.lin.feng_cheng.fresh.Model;


import java.io.Serializable;
import java.util.ArrayList;

import itp341.lin.feng_cheng.fresh.Model.Client.Vendor;

/**
 * Created by fredlin on 4/1/17.
 */

public class Market implements Serializable{
    private Location mLoc;
    private ArrayList<Vendor> vendorsList;
    private String strLoc;

    public Location getmLoc() {
        return mLoc;
    }

    public void setmLoc(Location mLoc) {
        this.mLoc = mLoc;
    }

    public ArrayList<Vendor> getVendorsList() {
        return vendorsList;
    }

    public void setVendorsList(ArrayList<Vendor> vendorsList) {
        this.vendorsList = vendorsList;
    }

    public Market(Location mLoc){
        this.mLoc = mLoc;
    }
    public Market(){

    }
    public Market(Location mLoc, ArrayList<Vendor> vendorsList) {
        this.mLoc = mLoc;
        this.vendorsList = vendorsList;
    }

    public String getStrLoc() {
        return strLoc;
    }

    public void setStrLoc(String strLoc) {
        this.strLoc = strLoc;
    }

    public Market(String loc){
        strLoc = loc;

    }
}
