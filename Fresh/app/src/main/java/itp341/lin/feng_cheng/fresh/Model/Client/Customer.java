package itp341.lin.feng_cheng.fresh.Model.Client;

import java.util.ArrayList;

import itp341.lin.feng_cheng.fresh.Model.Product;
import itp341.lin.feng_cheng.fresh.Model.Review;

/**
 * Created by fredlin on 4/2/17.
 */

public class Customer extends User{
    private ArrayList<Review> mReviews;
    private ArrayList<Vendor> favVendors;
    private ArrayList<String> favProduct;

    public Customer(String username, String password) {
        super(username, password);
        mReviews = new ArrayList<Review>();
        favVendors = new ArrayList<Vendor>();
        favProduct = new ArrayList<String>();
    }

    public Customer(String username, boolean premiumUser, String password) {
        super(username, premiumUser, password);
        mReviews = new ArrayList<Review>();
        favVendors = new ArrayList<Vendor>();
        favProduct = new ArrayList<String>();
    }
}
