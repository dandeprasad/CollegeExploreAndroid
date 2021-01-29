package collegeexplore.CollegeInfo.NitInformation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import collegeexplore.CollegeInfo.R;
import collegeexplore.slidingtabscolors.SlidingTabsColorsFragment;

/**
 * Created by DANDE on 30-07-2016.
 */

public class NitMainContentInfo extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nit_main_content_info);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_1);

        setSupportActionBar(myToolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        // textField.setText("Acadamics");
        String urlcheck ="http://10.0.2.2:8085/androiddandeCheck/Hello3check";
        String stringUrl = urlcheck;
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SlidingTabsColorsFragment fragment = new SlidingTabsColorsFragment();
            transaction.replace(R.id.sample_content_fragment1, fragment);
            transaction.commit();
        } else {
            // textView.setText("No network connection available.");
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }

    }
}