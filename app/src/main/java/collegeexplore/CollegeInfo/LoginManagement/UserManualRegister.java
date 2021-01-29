package collegeexplore.CollegeInfo.LoginManagement;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by DANDE on 04-06-2017.
 */

public class UserManualRegister extends Dialog implements
        android.view.View.OnClickListener  {


    public Activity c;
    public Dialog d;
    public Button registerButton, submit;
    public EditText email_name,user_password,mobile_number,OTP_VALUE;
    String jsonurl;
    public UserManualRegister(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_manual_register);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);

       // no = (Button) findViewById(R.id.btn_no);

       // no.setOnClickListener(this);
        OTP_VALUE= findViewById(R.id.OTP_VALUE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.registerButton:
                Boolean processFlagreg = true;

                if(OTP_VALUE.getText().toString().length()!=6)
                {
                    processFlagreg = false;
                    Toast.makeText(getApplicationContext(), R.string.enter_otp_valid, Toast.LENGTH_LONG).show();
                    OTP_VALUE.setError("Enter a Valid OTP");
                    return;
                }
                if(processFlagreg) {


                    try {
                        String server = Util.getProperty("name", getApplicationContext());
                        //  serverUrl= server.concat("/ExamsImages");
                        jsonurl = server.concat("/UserRegistrationPassWay");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String WORKSPACE_ID = "HOME_WORKSPACE";
                    String FUNCTION_ID = "USER_MANUAL_REGISTER";
                    String ACTION_ID = "COLLEGE_REGISTER";
                    String MODE = "REGISTER_USER";
                    String USER_ENTRD_OTP=OTP_VALUE.getText().toString();

                    new GetStringNewsNotifyTask(USER_ENTRD_OTP,MODE,WORKSPACE_ID, FUNCTION_ID, ACTION_ID, email_name.getText().toString(),user_password.getText().toString(),mobile_number.getText().toString()).execute(jsonurl);
                }
                break;
            case R.id.submit:
                Boolean processFlag = true;
                email_name= findViewById(R.id.email_name);


                boolean Emailvalid =isValidEmailAddress(email_name.getText().toString());
                if(email_name.getText().toString().length()==0 || !Emailvalid)
                {
                    processFlag = false;
                    Toast.makeText(getApplicationContext(), R.string.enter_valid_email, Toast.LENGTH_LONG).show();
                    email_name.setError("Enter a Valid EmailID");
                    return;
                }

                user_password= findViewById(R.id.user_password);
                if(user_password.getText().toString().length()<8)
                {
                    processFlag = false;
                    Toast.makeText(getApplicationContext(),R.string.password_length, Toast.LENGTH_LONG).show();
                    email_name.setError("Password lenght should be atleast 8");
                    return;
                }
                mobile_number= findViewById(R.id.mobile_number);
                if(mobile_number.getText().toString().length()<10 || mobile_number.getText().toString().length()>10 )
                {
                    processFlag = false;
                    Toast.makeText(getApplicationContext(), R.string.enter_mobile_number, Toast.LENGTH_LONG).show();
                    email_name.setError("Enter a valid Mobile Number");
                    return;
                }
if(processFlag) {
    registerButton.setVisibility(View.VISIBLE);
    OTP_VALUE.setVisibility(View.VISIBLE);
    submit.setEnabled(false);
    mobile_number.setEnabled(false);
    user_password.setEnabled(false);
    email_name.setEnabled(false);

    try {
        String server = Util.getProperty("name", getApplicationContext());
        //  serverUrl= server.concat("/ExamsImages");
        jsonurl = server.concat("/UserRegistrationPassWay");
    } catch (IOException e) {
        e.printStackTrace();
    }

    String WORKSPACE_ID = "HOME_WORKSPACE";
    String FUNCTION_ID = "USER_MANUAL_REGISTER";
    String ACTION_ID = "COLLEGE_REGISTER";
    String MODE = "OTP_TRIGGER";

    new GetStringNewsNotifyTask(OTP_VALUE.getText().toString(),MODE,WORKSPACE_ID, FUNCTION_ID, ACTION_ID, email_name.getText().toString(),user_password.getText().toString(),mobile_number.getText().toString()).execute(jsonurl);
}
                //  dismiss();
                break;
            default:
                break;
        }

    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }



    public class GetStringNewsNotifyTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        private String emailtoPass;
        private String PasswordtoPass;
        private String mobiletoPass,modetopass,USER_ENTRD_OTP;
        View objview;
        private String COLLEGEID;
        public GetStringNewsNotifyTask(String USER_ENTRD_OTP, String MODE, String WORKSPACE_ID, String FUNCTION_ID, String ACTION_ID, String email_name, String user_password, String mobile_number) {
            WorkspaceID=WORKSPACE_ID;
            functionID = FUNCTION_ID;
            ActionID=ACTION_ID;
            emailtoPass =email_name;
                    PasswordtoPass=user_password;
            mobiletoPass=mobile_number;
            modetopass=MODE;
            try {
                this.USER_ENTRD_OTP=Util.Nullcheck(USER_ENTRD_OTP);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public GetStringNewsNotifyTask(String MODE, String WORKSPACE_ID, String FUNCTION_ID, String WorkID, String funtID, String ActID, String COLLEGEIDJSON) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            COLLEGEID=COLLEGEIDJSON;
            modetopass=MODE;
        }

        @Override
        protected  void onPreExecute()
        {
            // progresdialoglistview=ProgressDialog.show(getActivity(), "","Loading");
            // Log.e("onPreExecutive","called"+progresdialoglistview);
           findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                // int x = positionCheck;
                String xy = downloadUrl(urls[0]);
                return xy;
            } catch (IOException e) {
                return "UNABLE_TO_REACH_WEB";
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
                Record.put( "COLLEGE_ID", COLLEGEID);
                Record.put( "USER_EMAIL", emailtoPass);
                Record.put( "USER_PASSWORD", PasswordtoPass);
                Record.put( "USER_MOBILENO", mobiletoPass);
                Record.put( "MODE", modetopass);
                Record.put( "USER_ENTRD_OTP", USER_ENTRD_OTP);
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
            if (response==200) {
                is = conn.getInputStream();
            }

            if(is!=null) {

                // Convert the InputStream into a string
                contentAsString = readIt(is, len);
                is.close();

            }

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
            if (result == null) {
                result="";}
            if(result.equalsIgnoreCase("UNABLE_TO_REACH_WEB"))
            {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(getApplicationContext(), "Not Able to connect our Servers", Toast.LENGTH_LONG).show();
               findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else {
                if (result != "") {

                    try {
                        if (modetopass.equalsIgnoreCase("OTP_TRIGGER")) {
                            JSONObject obj = new JSONObject(ing);
                            Marker marker;
                            int lenghtl = obj.length();
                            //StringData=new  String[8];
                            for (int i = 0; i < lenghtl; i++) {
                                JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));

                                String collegedesc = (String) clgdata.get("RESULTCODE");
                                if (collegedesc.equalsIgnoreCase("000000")) {
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Not Able to connect our Servers", Toast.LENGTH_LONG).show();
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                }

                            }


                            //   LatLng sydney = new LatLng(clgLat,80);
                            //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                        }
                        if (modetopass.equalsIgnoreCase("REGISTER_USER")) {
                            JSONObject obj = new JSONObject(ing);
                            Marker marker;
                            int lenghtl = obj.length();
                            //StringData=new  String[8];
                            for (int i = 0; i < lenghtl; i++) {
                                JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));

                                String collegedesc = (String) clgdata.get("RESULTCODE");
                                if (collegedesc.equalsIgnoreCase("000000")) {
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    Toast.makeText(getApplicationContext(), R.string.successful_register, Toast.LENGTH_LONG).show();
                                    dismiss();
                                } else {
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    Toast.makeText(getApplicationContext(), "Not Able to connect our Servers", Toast.LENGTH_LONG).show();
                                }

                            }


                            //   LatLng sydney = new LatLng(clgLat,80);
                            //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                        } } catch (JSONException e) {
                        e.printStackTrace();
                    }

                   findViewById(R.id.marker_progress).setVisibility(View.GONE);
                }
            }
        }
    }
}