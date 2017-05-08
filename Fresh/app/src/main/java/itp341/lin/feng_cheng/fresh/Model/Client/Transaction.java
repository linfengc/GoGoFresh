package itp341.lin.feng_cheng.fresh.Model.Client;


import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by fredlin on 4/2/17.
 */

public class Transaction implements Serializable{
    private HashMap<String, Integer> productOrders;
    private int revenue;

    public HashMap<String, Integer> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(HashMap<String, Integer> productOrders) {
        this.productOrders = productOrders;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }
}
