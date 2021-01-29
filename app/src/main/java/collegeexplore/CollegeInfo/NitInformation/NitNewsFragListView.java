package collegeexplore.CollegeInfo.NitInformation;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import collegeexplore.CollegeInfo.R;

import static collegeexplore.CollegeInfo.NitInformation.SlidingTabsFragmentNit.stringArray;

public class NitNewsFragListView extends ListFragment implements AdapterView.OnItemClickListener {


    static NitNewsFragListView newInstance(int num) {
        NitNewsFragListView f = new NitNewsFragListView();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

   // String [] strtext = SlidingTabsFragmentNit.returnArray();;

   String[] strtext={"wgerg","r3tret","egegg"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     //  strtext= getArguments().getString("news");

        View view = inflater.inflate(R.layout.seperator, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       /* ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.myStringHome, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);*/
        String stringUrl ="http://10.0.2.2:8085/androiddandeCheck/Hello3check";
        new GetDataTaskdude().execute(stringUrl);

        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }
    public void onListSelected(String[]x){
        strtext = x;

    }



    public class GetDataTaskdude  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        View objview;
        public GetDataTaskdude(View viewx) {
            objview = viewx;
        }
        public GetDataTaskdude() {

        }

        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
               // int x = positionCheck;
                String xy = downloadUrl(urls[0]);
                return xy;
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }}



        // Given a URL, establishes an HttpUrlConnection and retrieves
// the web page content as a InputStream, which it returns as
// a string.
        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;
            String contentAsString=null;
            Bitmap bitmap=null;

            //for post request

            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write("dande=king");
            writer.flush();
            writer.close();
            os.close();

            conn.connect();


            int response = conn.getResponseCode();
            //  Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();


            // Convert the InputStream into a string
            contentAsString = readIt(is, len);
            is.close();



            return contentAsString;

        }


        public String readIt(InputStream stream, int len) throws IOException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            String ing = result;
            if(isAdded()) {
                try {
                    JSONArray json_data = new JSONArray(result);
                    Bundle bundle = new Bundle();
                    ArrayList<String> stringArrayList = new ArrayList<String>();
                    for (int i = 0; i < json_data.length(); i++) {
                        JSONObject json_data1 = (JSONObject) json_data.get(i);
                        stringArrayList.add(json_data1.getString("NEWS"));
                        String NIT_ID = (String) json_data1.get("NIT_ID");
                        String CLG = (String) json_data1.get("CLG");
                        String NEWS = (String) json_data1.get("NEWS");
                        String NEWS_HEAD = (String) json_data1.get("NEWS_HEAD");

                        //  bundle.putString("news", NEWS);
                    }
                    stringArray = stringArrayList.toArray(new String[stringArrayList.size()]);
                    if (getActivity() != null) {
                        setListAdapter(new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_list_item_1, strtext));
                    }

                    //mInfo.setText(str);
                    // mViewPager.getAdapter().getItemPosition(Object object)

                    //  mViewPager.findViewWithTag("xyz").invalidate();
                    // mViewPager.findViewWithTag("xyz");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // value.checkImageshow(ing);
                // ImageView  imageView=(ImageView)findViewById(R.id.imageView4);
                //  textView.setText(result);
            }
        }


    }

}