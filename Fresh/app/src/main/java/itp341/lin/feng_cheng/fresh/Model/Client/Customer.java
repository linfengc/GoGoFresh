package itp341.lin.feng_cheng.fresh.Model.Client;

import java.io.Serializable;
import java.util.ArrayList;

import itp341.lin.feng_cheng.fresh.Model.Product;
import itp341.lin.feng_cheng.fresh.Model.Review;

/**
 * Created by fredlin on 4/2/17.
 */

public class Customer extends User implements Serializable{
    private ArrayList<Review> mReviews;
    private ArrayList<Vendor> favVendors;
    private ArrayList<String> favProduct;

    @Override
    public String toString() {
        return "Customer{" +
                "mReviews=" + mReviews +
                ", favVendors=" + favVendors +
                ", favProduct=" + favProduct +
                ", cardType='" + cardType + '\'' +
                ", cardNum='" + cardNum + '\'' +
                '}';
    }

    private String cardType;
    private String cardNum;

    public String getCardType() {

        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public Customer(String username, String password, String cardType, String cardNum) {
        super(username, password);

        mReviews = new ArrayList<Review>();
        favVendors = new ArrayList<Vendor>();
        favProduct = new ArrayList<String>();

        this.cardType = cardType;
        this.cardNum = cardNum;
    }






    public ArrayList<Review> getmReviews() {
        return mReviews;
    }

    public void setmReviews(ArrayList<Review> mReviews) {
        this.mReviews = mReviews;
    }

    public ArrayList<Vendor> getFavVendors() {
        return favVendors;
    }

    public void setFavVendors(ArrayList<Vendor> favVendors) {
        this.favVendors = favVendors;
    }

    public ArrayList<String> getFavProduct() {
        return favProduct;
    }

    public void setFavProduct(ArrayList<String> favProduct) {
        this.favProduct = favProduct;
    }


    public Customer(){

    }
    public Customer(String username, boolean premiumUser, String password) {
        super(username, premiumUser, password);
        mReviews = new ArrayList<Review>();
        favVendors = new ArrayList<Vendor>();
        favProduct = new ArrayList<String>();
    }
}
