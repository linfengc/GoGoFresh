package itp341.lin.feng_cheng.fresh.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by fredlin on 4/1/17.
 */

public class Product implements Serializable{
    private double price;
    private ArrayList<String> categoryTags;
    private int quantity;
    private String name;



    public Product(double price, ArrayList<String> categoryTags, int quantity, String name) {

        this.price = price;
        this.categoryTags = categoryTags;
        this.quantity = quantity;
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<String> getCategoryTags() {
        return categoryTags;
    }

    public void setCategoryTags(ArrayList<String> categoryTags) {
        this.categoryTags = categoryTags;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "price=" + price +
                ", categoryTags=" + categoryTags +
                ", quantity=" + quantity +
                ", name='" + name + '\'' +
                '}';
    }
}
