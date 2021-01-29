package collegeexplore.CollegeInfo.V1.NewsWorkspace;


        import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
import android.widget.Button;


import collegeexplore.CollegeInfo.R;

        import static collegeexplore.CollegeInfo.V1.NewsWorkspace.NewsFragment.currentURL;

public class LoadWebUrl extends Fragment {

    boolean isFragmentLoaded = false;
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    Button btnBack;
    WebView webview;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.web_view, container, false);
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        webview=(WebView)view.findViewById(R.id.webview1);
        webview.setWebViewClient(new MyWebViewClient());

    }
    /** Opens the URL in a browser */
    private void openURL() {
        webview.loadUrl(currentURL);
        webview.requestFocus();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isFragmentLoaded) {
            //Load Your Data Here like.... new GetContacts().execute();
            openURL();
            //isFragmentLoaded = true;
        }
        else{
        }
    }
}