package itp341.lin.feng_cheng.fresh.Model.Client;

import android.graphics.Bitmap;

import java.util.ArrayList;

import itp341.lin.feng_cheng.fresh.Model.Inventory;
import itp341.lin.feng_cheng.fresh.Model.Market;
import itp341.lin.feng_cheng.fresh.Model.Review;

/**
 * Created by fredlin on 4/1/17.
 */

public class Vendor extends User{
    private Inventory mInventory;
    private Transaction mTrasactions;



    private Market mMarket;
    private ArrayList<Review> mReviews;
    private String bio;
    private Bitmap vendorPic;

    public Vendor(String username, String password, Market mMarket) {
        super(username, password);
        this.mMarket = mMarket;
        mTrasactions = new Transaction();
        mReviews = new ArrayList<Review>();
        mInventory = new Inventory();
    }

    public Vendor(String username, boolean premiumUser, String password, Market mMarket) {
        super(username, premiumUser, password);
        this.mMarket = mMarket;

    }

    public void setPic(Bitmap pic){
        vendorPic = pic;
    }


    public void setBio(String b){
        bio = b;
    }

    public Inventory getmInventory() {
        return mInventory;
    }

    public Transaction getmTrasactions() {
        return mTrasactions;
    }

    public Market getmMarket() {
        return mMarket;
    }

    public String getBio() {
        return bio;
    }


}
