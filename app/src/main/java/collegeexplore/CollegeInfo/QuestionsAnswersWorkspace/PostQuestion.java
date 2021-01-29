package collegeexplore.CollegeInfo.QuestionsAnswersWorkspace;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;

import collegeexplore.CollegeInfo.LoginManagement.UserLoginManager;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.Util;

/**
 * Created by DANDE on 17-06-2017.
 */

public class PostQuestion extends AppCompatActivity {
    String serverUrl;
    String QuestionAsked;
    EditText mEdit;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.post_question);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.postQuesToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.post_your_question);
        // Get a support ActionBar corresponding to this toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //final Toolbar HomeToolbarfrag = (Toolbar) findViewById(R.id.toolbar_collaspe);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }

        });
        UserLoginManager logincheck123 = new UserLoginManager(PostQuestion.this);
        HashMap fetchemail = logincheck123.getUserDetails();
        TextView user_email =findViewById(R.id.user_email);
        user_email.setText(fetchemail.get("email").toString());

        Button btnSubmitlogin = (Button)findViewById(R.id.PostQuestion);
        btnSubmitlogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                mEdit   = (EditText)findViewById(R.id.Askquestion);
                QuestionAsked  =  mEdit.getText().toString();
                if(QuestionAsked.equalsIgnoreCase("")){
                    mEdit.setError("Please enter a valid Question");
                    Toast.makeText(getApplicationContext(), "Please enter a valid Question", Toast.LENGTH_LONG).show();
                }
                else {
                    //  PASSWORD =  password.getText().toString();
                    JSONObject userdata = new JSONObject();
                    try {

                        UserLoginManager logincheck123 = new UserLoginManager(PostQuestion.this);
                        HashMap fetchemail = logincheck123.getUserDetails();
                        userdata.put("QUESTION", QuestionAsked);
                        userdata.put("USER_POSTED_MAIL", fetchemail.get("email"));
                        userdata.put("USER_POSTED_NAME", fetchemail.get("username"));

                        //userdata.put("PASSWORD",PASSWORD);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // MOBILE_NO =  mobileNo.getText().toString();
                    //  EMAIL_ID =  emailID.getText().toString();
                    try {
                        String server = Util.getProperty("name", getApplicationContext());
                        serverUrl = server.concat("/PostQuestionAnswer");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        // new DownloadLoginData().execute(serverUrl);
                        String WORKSPACE_ID = "QUEST_WORKSPACE";
                        String FUNCTION_ID = "USER_QUESTION";
                        String ACTION_ID = "POST_QUESTION";
                        String usrdata = userdata.toString();
                        //int POSITION_NO=mNum_position;
                        new DownloadLoginData(WORKSPACE_ID, FUNCTION_ID, ACTION_ID, usrdata).execute(serverUrl);
                    }
                }
            }
        });
    }
    public class DownloadLoginData  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        private String usr_info;
        View objview;


        public DownloadLoginData(String WorkID,String funtID,String ActID,String usrdata) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            usr_info=usrdata;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
/*            progressDialog = ProgressDialog.show(context,"Please Wait","Loading Colleges...", true);
          //  progressDialog.setCancelable(true);
           // progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.isIndeterminate();
            progressDialog.show();*/
            findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);

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
                Record.put( "user_info", usr_info);
                json.put( "datatohost", Record.toString());
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

                        String StringNews = (String) obj.get("UPDATE_INFO");
                        if(StringNews.equalsIgnoreCase("SUCCESS")){
                            Toast.makeText(getApplicationContext(), R.string.posted_success, Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(),"We will be sending Notification/Email shortly", Toast.LENGTH_LONG).show();
                            findViewById(R.id.marker_progress).setVisibility(View.GONE);
                            finish();
                        }

                        // LatLng ClgLatLong = new LatLng(clgLat,clgLng);
                        // marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).title(clgname));
                        // mMap.setMinZoomPreference(4);
                        // marker.setTag(clgID);
//.icon(BitmapDescriptorFactory.fromResource(R.drawable.maps_pin))





                    //   LatLng sydney = new LatLng(clgLat,80);
                    //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    }

