package itp341.lin.feng_cheng.fresh.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fredlin on 4/1/17.
 */

public class Inventory {
    private ArrayList<String> categoryTags;
    private HashMap<String, Product> productMap;
    private ArrayList<Product> productList;

    public Inventory() {
        categoryTags = new ArrayList<String>();
        productMap = new HashMap<String, Product>();
        productList = new ArrayList<Product>();
    }

    public ArrayList<String> getCategoryTags() {

        return categoryTags;
    }

    public void setCategoryTags(ArrayList<String> categoryTags) {
        this.categoryTags = categoryTags;
    }

    public HashMap<String, Product> getProductMap() {
        return productMap;
    }

    public void setProductMap(HashMap<String, Product> productMap) {
        this.productMap = productMap;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public Inventory(ArrayList<String> categoryTags, HashMap<String, Product> productMap, ArrayList<Product> productList) {

        this.categoryTags = categoryTags;
        this.productMap = productMap;
        this.productList = productList;
    }
}
