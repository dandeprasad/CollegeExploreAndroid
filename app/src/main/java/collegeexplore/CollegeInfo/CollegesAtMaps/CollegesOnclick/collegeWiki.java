package collegeexplore.CollegeInfo.CollegesAtMaps.CollegesOnclick;

        import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
        import android.webkit.WebSettings;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;

        import collegeexplore.CollegeInfo.R;


public class collegeWiki extends Fragment  {



    /**
     * When creating, retrieve this instance's number from its arguments.
     */

    /**
     * This is called soon after oncreate and expects a view return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.wiki_webview, container, false);
    }





    public void onViewCreated(View view, Bundle savedInstanceState) {

        WebView myWebView = view.findViewById(R.id.webView112);
        myWebView.loadUrl("https://en.wikipedia.org/wiki/National_Institute_of_Technology,_Puducherry");

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
    }



}
