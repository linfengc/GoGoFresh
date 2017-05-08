package itp341.lin.feng_cheng.fresh.Model.Client;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import itp341.lin.feng_cheng.fresh.Model.Inventory;
import itp341.lin.feng_cheng.fresh.Model.Market;
import itp341.lin.feng_cheng.fresh.Model.Product;
import itp341.lin.feng_cheng.fresh.Model.Review;

/**
 * Created by fredlin on 4/1/17.
 */

public class Vendor extends User {
    private Inventory mInventory;
    private Transaction mTrasactions;


    private int mBalance;

    private Market mMarket;
    private ArrayList<Review> mReviews;
    private ArrayList<Product> mProducts;
    private String bio;
    private Bitmap vendorPic;
    private String mCategory;

    public ArrayList<Product> getmProducts() {
        return mProducts;
    }

    public void setmProducts(ArrayList<Product> mProducts) {
        this.mProducts = mProducts;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    private String decritpion;

    public String getDecritpion() {
        return decritpion;
    }

    public void setDecritpion(String decritpion) {
        this.decritpion = decritpion;
    }

    public int getmBalance() {
        return mBalance;
    }

    public void setmBalance(int mBalance) {
        this.mBalance = mBalance;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "mInventory=" + mInventory +
                ", mTrasactions=" + mTrasactions +
                ", mBalance=" + mBalance +
                ", mMarket=" + mMarket +
                ", mReviews=" + mReviews +
                ", mProducts=" + mProducts +
                ", bio='" + bio + '\'' +
                ", vendorPic=" + vendorPic +
                ", mCategory='" + mCategory + '\'' +
                ", decritpion='" + decritpion + '\'' +
                '}';
    }

    public Vendor(String username, String password, Market mMarket, String des, String Cat) {
        super(username, password);
        this.mMarket = mMarket;
        bio = des;

        mCategory = Cat;
        mTrasactions = new Transaction();
        mReviews = new ArrayList<Review>();
        mInventory = new Inventory();
        mBalance = 100;
        mProducts = new ArrayList<Product>();

    }

    public Vendor(String username, boolean premiumUser, String password, Market mMarket) {
        super(username, premiumUser, password);
        this.mMarket = mMarket;

    }

    public Vendor(){

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


    public void setmInventory(Inventory mInventory) {
        this.mInventory = mInventory;
    }

    public void setmTrasactions(Transaction mTrasactions) {
        this.mTrasactions = mTrasactions;
    }

    public void setmMarket(Market mMarket) {
        this.mMarket = mMarket;
    }

    public ArrayList<Review> getmReviews() {
        return mReviews;
    }

    public void setmReviews(ArrayList<Review> mReviews) {
        this.mReviews = mReviews;
    }

    public Bitmap getVendorPic() {
        return vendorPic;
    }

    public void setVendorPic(Bitmap vendorPic) {
        this.vendorPic = vendorPic;
    }
}
