package collegeexplore.CollegeInfo.NitInformation;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import collegeexplore.CollegeInfo.R;

/**
 * Created by DANDE on 27-07-2016.
 */

public class CloseOpenRanks extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nit_webview_ranks);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.web_view_toolbar);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab1 = getSupportActionBar();

        // Enable the Up button
        ab1.setDisplayHomeAsUpEnabled(true);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("http://csab.nic.in/ChoiceFilling/Result/ORCR_AllRounds.aspx");

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
    }
}
