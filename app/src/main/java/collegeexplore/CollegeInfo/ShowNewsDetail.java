package collegeexplore.CollegeInfo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DANDE on 04-09-2016.
 */

public class ShowNewsDetail extends Fragment {
    View TransView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar myToolbar = getActivity().findViewById(R.id.my_toolbar);
      // getActivity().getActionBar().setDrawerIndicatorEnabled(false);
       // getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(true);
        ((HomeActivity)getActivity()).getSupportActionBar(). setHomeAsUpIndicator(R.drawable.back_button);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        TransView =  inflater.inflate(R.layout.show_news_detail, container, false);
        return inflater.inflate(R.layout.show_news_detail, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {


        //String stringUrl ="http://192.168.43.60:8085/androiddandeCheck/HomeNewsFragmwent";
        String stringUrl ="http://10.0.2.2:8085/androiddandeCheck/HomeNewsFragmwent";
    //   new GetShowNewsDetail().execute(stringUrl);

    }
    public class GetShowNewsDetail  extends AsyncTask<String, Void, Bitmap>

    {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap xy = null;
            try {
                xy = downloadUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return xy;
        }
        private Bitmap downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            int len = 500;
            String contentAsString=null;
            Bitmap bitmap=null;
            //for post request
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(1000000);
            conn.setConnectTimeout(1500000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            Map<String, String> obj=new HashMap<String, String>();
            obj.put("PRODUCT","NEWS_HOME");
            obj.put("SUB_PRODUCT","NITS_NEWS_HOME");
            obj.put("FUNCTION_CODE","NEWS_POS1");
            JSONObject jo = new JSONObject();
            JSONArray x1 = null;

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write("USERNAME="+"sgffsgf");
            writer.flush();
            writer.close();
            os.close();
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            Bitmap ing = result;
            // value.checkImageshow(ing);
            // ImageView  imageView=(ImageView)findViewById(R.id.imageView4);
            //  textView.setText(result);
            ImageView imageView= TransView.findViewById(R.id.news_img_detail);
            imageView.setImageBitmap(ing);



        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get item selected and deal with it
        boolean x = true;

        switch (item.getItemId()) {
            case android.R.id.home:
                //called when the up affordance/carat in actionbar is pressed
                getActivity().onBackPressed();
                return x;

        }
        return x;
    }
}