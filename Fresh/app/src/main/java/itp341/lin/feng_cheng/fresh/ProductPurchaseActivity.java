package itp341.lin.feng_cheng.fresh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import itp341.lin.feng_cheng.fresh.Model.Client.Vendor;
import itp341.lin.feng_cheng.fresh.Model.Product;
import itp341.lin.feng_cheng.fresh.Model.VendorSingleton;
import itp341.lin.feng_cheng.fresh.R;

/**
 * Created by fredlin on 4/2/17.
 */

public class ProductPurchaseActivity extends AppCompatActivity {
    public static final String PRODUCT_TAG = "thisisthekeytoproductInfo";
    public static final String IMG_TAG = "thisisthekeytoproductIMGGG";
    private SeekBar quantityBar;
    private TextView quantityLabel;
    private TextView priceLabel;
    private TextView totalCostLabel;
    private TextView productNameLabel;
    private Button addCartButton;
    private ImageView img;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
    private Product mProduct;


    String currentVendorName;


//firebase shit
    DatabaseReference dbVendor;
    int purchased;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_request);

        dbVendor = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.VENDORS);

        setVariables();

        setBarListener();


    }
    private void retrieveVendor(){
        Toast.makeText(this, "Modify Vendor data",
                Toast.LENGTH_SHORT).show();
        dbVendor.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    System.out.println("iterate " );
                    Vendor user = snapshot.getValue(Vendor.class);
                    int newQuan = 0 ;
                    System.out.println("currentVendorName: " + user.getUsername() + "and " + currentVendorName);
                    if(user.getUsername().equalsIgnoreCase(currentVendorName)){
                        ArrayList<Product> products = user.getmProducts();
                        for(Product p : products){
                            if(p.getName().equalsIgnoreCase(mProduct.getName())){
                                System.out.println("Found request Product" + p);

                                newQuan = p.getQuantity() - purchased;
                                p.setQuantity(newQuan);
                                int profit = (int) Math.round(purchased*unitPrice);
                                user.setmBalance(user.getmBalance()+ profit);
                                dbVendor.child(snapshot.getKey()).setValue(user);
                                System.out.println("modified user info? "+ user);
                                break;

                            }
                        }

                    }


                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setVariables(){
        Intent i = getIntent();
        int imgId = i.getIntExtra(IMG_TAG, R.drawable.p1);
        currentVendorName = i.getStringExtra("Key");
        mProduct = (Product) i.getSerializableExtra(PRODUCT_TAG);
        unitPrice = mProduct.getPrice();
        priceLabel = (TextView) findViewById(R.id.orderRequestUnitPrice) ;
        quantityLabel = (TextView)findViewById(R.id.orderRequestQuantity);
        totalCostLabel = (TextView) findViewById(R.id.orderRequestTotalCost) ;
        productNameLabel = (TextView) findViewById(R.id.orderRequestProductName);
        addCartButton = (Button) findViewById(R.id.orderRequestButton);

        addCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get vendor
                retrieveVendor();
                finish();
            }
        });



        productNameLabel.setText(mProduct.getName());
        img = (ImageView) findViewById(R.id.orderRequestImg) ;
        img.setImageResource(imgId);
        totalCostLabel.setText("0.00");
        priceLabel.setText(String.valueOf(mProduct.getPrice()));


        quantityBar = (SeekBar) findViewById(R.id.QuantityBar);
        quantityBar.setMax(mProduct.getQuantity());
    }


    private void setBarListener(){
        quantityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                quantity = progress;
                totalPrice = progress * unitPrice;
                purchased = progress;
                quantityLabel.setText(String.valueOf(quantity));
                totalCostLabel.setText(String.valueOf(totalPrice));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }





}