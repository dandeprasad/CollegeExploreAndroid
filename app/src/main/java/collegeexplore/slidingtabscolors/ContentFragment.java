/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package collegeexplore.slidingtabscolors;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
public class ContentFragment extends ListFragment {
    int mNum;
    String[] strtext={"wgerg","r3tret","egegg"};
    /**
     * Create a new instance of CountingFragment, providing "num"
     * as an argument.
     */
    public static ContentFragment newInstance() {
        ContentFragment f = new ContentFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", 1);
        f.setArguments(args);

        return f;
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pager_item, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
           /* setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, strtext));*/
        String stringUrl ="http://10.0.2.2:8085/androiddandeCheck/Hello3check";
        new GetDataTaskdude().execute(stringUrl);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentList", "Item clicked: " + id);
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
                String[]  stringArray = stringArrayList.toArray(new String[stringArrayList.size()]);
                if (getActivity() != null) {
                    setListAdapter(new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1, stringArray));
                }

                //mInfo.setText(str);
                // mViewPager.getAdapter().getItemPosition(Object object)

                //  mViewPager.findViewWithTag("xyz").invalidate();
                // mViewPager.findViewWithTag("xyz");


            } catch (JSONException e) {
                e.printStackTrace();
            }



            // String[]   stringArray = stringArrayList.toArray(new String[stringArrayList.size()]);



            //mInfo.setText(str);
            // mViewPager.getAdapter().getItemPosition(Object object)

            //  mViewPager.findViewWithTag("xyz").invalidate();
            // mViewPager.findViewWithTag("xyz");




            // value.checkImageshow(ing);
            // ImageView  imageView=(ImageView)findViewById(R.id.imageView4);
            //  textView.setText(result);

        }


    }
}
