package collegeexplore.CollegeInfo.NitInformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;

import org.apache.commons.io.IOUtils;
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

import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.Util;

/**
 * Created by DANDE on 26-04-2017.
 */

public class NitsRankings  extends AppCompatActivity {
    String serverUrl = null;
    Context context ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nit_rankings);
 context =  this;
        init();
        String WORKSPACE_ID = "HOME_WORKSPACE";
        String FUNCTION_ID = "NIT_DATA";
        String ACTION_ID = "NIT_RANKINGS_DATA";
        try {
            String  server =  Util.getProperty("name", getApplicationContext());
            serverUrl= server.concat("/NitRankings");

        } catch (IOException e) {
            e.printStackTrace();
        }
        new GetStringShowDetailTask(WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);
      //  Toolbar myToolbar = (Toolbar) findViewById(R.id.nit_ranking_toolbar);
       // setSupportActionBar(myToolbar);
//
        // Get a support ActionBar corresponding to this toolbar
      //  ActionBar ab1 = getSupportActionBar();

        // Enable the Up button
      //  ab1.setDisplayHomeAsUpEnabled(true);


    }
    public void init() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);

/*
        TableRow tbrow1 = new TableRow(this);
        tbrow1.setBackgroundResource(android.R.drawable.gallery_thumb);
        TextView tv12 = new TextView(this);
        tv12.setText(" Top Engineering Colleges 2016 Ranking by MHRD ");
        tv12.setTextSize(24);

        tv12.setTextColor(Color.WHITE);
        tbrow1.addView(tv12);
        stk.addView(tbrow1);
*/

        TableRow tbrow0 = new TableRow(this);
        tbrow0.setBackgroundResource(android.R.drawable.gallery_thumb);
        TextView tv0 = new TextView(this);
        tv0.setText(" Rank ");
        tv0.setTextSize(20);
        tv0.setGravity(Gravity.CENTER);
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" College ");
        tv1.setTextColor(Color.WHITE);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextSize(20);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText("City");
        tv2.setTextColor(Color.WHITE);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextSize(20);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText("State");
        tv3.setTextColor(Color.WHITE);
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextSize(20);
        tbrow0.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setText("Score");
        tv4.setTextColor(Color.WHITE);
        tv4.setGravity(Gravity.CENTER);
        tv4.setTextSize(20);
        tbrow0.addView(tv4);
        stk.addView(tbrow0);
/*        for (int i = 0; i < 25; i++) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText("" + i);
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText("Product " + i);
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText("Rs." + i);
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText("" + i * 15 / 32 * 10);
            t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }*/

    }
    public void init1(String RANK, String COLLEGE_NAME, String CITY, String STATE, String SCORE) {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
    TableRow tbrow = new TableRow(this);
        tbrow.setBackgroundResource(android.R.drawable.gallery_thumb);
    TextView t1v = new TextView(this);
                        t1v.setText(RANK);
                        t1v.setTextColor(Color.WHITE);
        t1v.setGravity(Gravity.CENTER);
                        tbrow.addView(t1v);
    TextView t2v = new TextView(this);
                        t2v.setText(COLLEGE_NAME);
                        t2v.setTextColor(Color.WHITE);
        t2v.setGravity(Gravity.CENTER);
                        tbrow.addView(t2v);
    TextView t3v = new TextView(this);
                        t3v.setText(CITY);
                        t3v.setTextColor(Color.WHITE);
        t3v.setGravity(Gravity.CENTER);
                        tbrow.addView(t3v);
    TextView t4v = new TextView(this);
                        t4v.setText(STATE);
                        t4v.setTextColor(Color.WHITE);
        t4v.setGravity(Gravity.CENTER);
                        tbrow.addView(t4v);
        TextView t5v = new TextView(this);
        t5v.setText(SCORE);
        t5v.setTextColor(Color.WHITE);
        t5v.setGravity(Gravity.CENTER);
        tbrow.addView(t5v);

                        stk.addView(tbrow);
    }
    public class GetStringShowDetailTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        private int position_no;
        View objview;


        public GetStringShowDetailTask(String WorkID, String funtID, String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;

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
            int len = 50000;
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

            JSONObject json = new JSONObject();

            JSONObject Record = new JSONObject();
            try {
                Record.put( "ACTION_ID", ActionID);
                Record.put( "FUNCTION_ID", functionID);
                Record.put( "WORKSPACE_ID", WorkspaceID);

                json.put( "datatohost", Record);
            } catch (JSONException e) {
                e.printStackTrace();
            }



            // P toString()
            //  System.out.println( "JSON: " + json.toString() );


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write("ServerData="+json.toString());

            writer.flush();
            writer.close();
            os.close();

            conn.connect();


            int response = conn.getResponseCode();

            is = conn.getInputStream();


            // Convert the InputStream into a string
            contentAsString = readIt(is, len);
            is.close();



            return contentAsString;

        }


        public String readIt(InputStream stream, int len) throws IOException {

            String myString = IOUtils.toString(stream, "UTF-8");
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[5000];
            reader.read(buffer);
            return myString;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            String ing = result;

            if(result!=null) {
                try {
                    JSONObject obj = new JSONObject(ing);
                    Marker marker;
                    int lenghtl = obj.length();

                    for (int i = 0; i < lenghtl; i++) {
                        JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));

                        String  RANK = Integer.toString((Integer) clgdata.get("RANK"));
                        String COLLEGE_NAME = (String) clgdata.get("COLLEGE_NAME");
                        String CITY = (String) clgdata.get("CITY");
                        String STATE = (String) clgdata.get("STATE");
                       // int SCORE = (Integer) clgdata.get("SCORE");
                        String  SCORE = Integer.toString((Integer) clgdata.get("SCORE"));
init1(RANK,COLLEGE_NAME,CITY,STATE,SCORE);

                    //    detail_string_data.add(i, StringHeader);
                    //    detail_string_data.add(i + 1, StringNews);
                        // LatLng ClgLatLong = new LatLng(clgLat,clgLng);
                        // marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).title(clgname));
                        // mMap.setMinZoomPreference(4);
                        // marker.setTag(clgID);
//.icon(BitmapDescriptorFactory.fromResource(R.drawable.maps_pin))

                    }
                   // mAdapter.notifyDataSetChanged();


                    //   LatLng sydney = new LatLng(clgLat,80);
                    //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
