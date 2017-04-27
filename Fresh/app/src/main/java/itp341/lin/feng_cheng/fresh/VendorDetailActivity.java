package itp341.lin.feng_cheng.fresh;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by fredlin on 4/2/17.
 */

public class VendorDetailActivity extends AppCompatActivity{


    public static final String TAG = VendorDetailActivity.class.getSimpleName();


    public static final String CLICK_POSITION = "navigating...";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create fragment
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragment_container);

        //*modify fragment creation
        int position = getIntent().getIntExtra(CLICK_POSITION,-1);
        if (f == null ) {
            f = VendorDetailFragment.newInstance(position);
        }
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, f);
        fragmentTransaction.commit();

    }
}
