package itp341.lin.feng_cheng.fresh;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

import itp341.lin.feng_cheng.fresh.Model.Product;

/**
 * Created by fredlin on 5/7/17.
 */

public class CreateNewProductAcitivity extends AppCompatActivity{

    EditText productName;
    EditText productUnitPrice;
    EditText productDes;
    EditText productQuan;
    Button saveButt;
    DatabaseReference dbProductEntry;
    DatabaseReference dbVendor;
    FirebaseDatabase database;
    String userUID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_product);

        Intent data  = getIntent();
        userUID = (String ) data.getStringExtra("Key") ;

        initializeComponents();


    }



    public void initializeComponents(){
        productName = (EditText) findViewById(R.id.noteWriteName);
        productDes = (EditText) findViewById(R.id.noteWriteDes);
        productQuan = (EditText) findViewById(R.id.noteWriteQuan);
        productUnitPrice = (EditText) findViewById(R.id.noteWritePrice);
        saveButt = (Button) findViewById(R.id.noteWriteSave);




        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/MavenPro-Bold.ttf");

        productQuan.setTypeface(font);
        productName.setTypeface(font);
        productDes.setTypeface(font);
        productUnitPrice.setTypeface(font);
        saveButt.setTypeface(font);

        TextView t1= (TextView)findViewById(R.id.newProduct1) ;
        TextView t2= (TextView)findViewById(R.id.newProduct2) ;
        TextView t3= (TextView)findViewById(R.id.newProduct3) ;
        TextView t4= (TextView)findViewById(R.id.newProduct4) ;

        t1.setTypeface(font);
        t2.setTypeface(font);
        t3.setTypeface(font);
        t4.setTypeface(font);

        //todo get database
        database = FirebaseDatabase.getInstance();
        dbVendor = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.VENDORS);




        saveButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get array list  add new stuff and set back
                dbVendor.child(userUID).child(FirebaseReferences.PRODUCTLIST).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println("***** This is uid: " + userUID);
                        System.out.println(dataSnapshot);
                        ArrayList<Product> products = (ArrayList<Product>) dataSnapshot.getValue();
                        if(products==null){
                            products = new ArrayList<Product>();
                        }
                       // Product p1 = new Product(3 , new ArrayList<String>(), 100, "Strawberry");
                        Product p = new Product(Integer.valueOf(productUnitPrice.getText().toString()),
                                new ArrayList<String>(), Integer.valueOf(productQuan.getText().toString()),
                                    productName.getText().toString() , productDes.getText().toString());
                        products.add(p);

                        dbVendor.child(userUID).child(FirebaseReferences.PRODUCTLIST).setValue(products);
                        Intent i = getIntent();
                        setResult(Activity.RESULT_OK,i);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }




                });


            }



        });
    }

}
