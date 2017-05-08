package itp341.lin.feng_cheng.fresh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import itp341.lin.feng_cheng.fresh.Model.Product;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_request);


        setVariables();

        setBarListener();


    }

    private void setVariables(){
        Intent i = getIntent();
        int imgId = i.getIntExtra(IMG_TAG, R.drawable.p1);
        mProduct = (Product) i.getSerializableExtra(PRODUCT_TAG);
        unitPrice = mProduct.getPrice();
        priceLabel = (TextView) findViewById(R.id.orderRequestUnitPrice) ;
        quantityLabel = (TextView)findViewById(R.id.orderRequestQuantity);
        totalCostLabel = (TextView) findViewById(R.id.orderRequestTotalCost) ;
        productNameLabel = (TextView) findViewById(R.id.orderRequestProductName);
        addCartButton = (Button) findViewById(R.id.orderRequestButton);



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