package collegeexplore.CollegeInfo.V1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.firebase.iid.FirebaseInstanceId;

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

import collegeexplore.CollegeInfo.AdmissionsWorkspace.CollegeAdmisFragment;
import collegeexplore.CollegeInfo.CutoffsWorkspace.CutoffFirstFragment;
import collegeexplore.CollegeInfo.ExamsWorkspace.ExamsDetailsViewPager;
import collegeexplore.CollegeInfo.HomeActivity;
import collegeexplore.CollegeInfo.LoginManagement.UserLoginManager;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.UserNotifications.NotifyDetailFragment;
import collegeexplore.CollegeInfo.Util;
import collegeexplore.CollegeInfo.V1.NewsWorkspace.LoadWebUrl;
import collegeexplore.CollegeInfo.V1.NewsWorkspace.NewsFragment;
import collegeexplore.CollegeInfo.other.CircleTransform;
import collegeexplore.travel.DandeVideoFragment;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private String app_to_open;
    String serverUrl = null;
    Boolean logincheck=false;
    private GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v1_activity_main);
        ViewPager viewPager = findViewById(R.id.activity_viewpager);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(new fragmentPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(1);



//For app notifications
        if (getIntent().getExtras()!=null) {

            Intent intent = getIntent();
            String action = intent.getAction();
            Uri data = intent.getData();

            Bundle extras = getIntent().getExtras();
            if(extras.getString("app_to_open") == null) {
                app_to_open= null;
                Toast.makeText(this, "NULL data", Toast.LENGTH_SHORT).show();


            } else {
                app_to_open= extras.getString("app_to_open");
                if(app_to_open.equalsIgnoreCase("EXAMS")) {
                    // Transition for fragment1
                    Slide slideTransition = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        slideTransition = new Slide(Gravity.LEFT);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
                    }

                    // ((HomeActivity)getActivity()).getSupportActionBar(). setHomeAsUpIndicator(R.drawable.back_button);
                    Fragment fr1 = ExamsDetailsViewPager.newInstance(extras.getString("EXAM_ID"));
                    fr1.setReenterTransition(slideTransition);
                    fr1.setExitTransition(slideTransition);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        fr1.setSharedElementEnterTransition(new ChangeBounds());
                    }
                    FragmentManager fm1 = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
                    fragmentTransaction1.replace(R.id.view_onclick_frame, fr1);
                    fragmentTransaction1.addToBackStack(null);
                    fragmentTransaction1.commit();

                }
                else  if(app_to_open.equalsIgnoreCase("ADMISSIONS")) {
                    // Transition for fragment1
                    Slide slideTransition = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        slideTransition = new Slide(Gravity.LEFT);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
                    }

                    // ((HomeActivity)getActivity()).getSupportActionBar(). setHomeAsUpIndicator(R.drawable.back_button);
                    Fragment fr1 =new CollegeAdmisFragment();
                    fr1.setReenterTransition(slideTransition);
                    fr1.setExitTransition(slideTransition);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        fr1.setSharedElementEnterTransition(new ChangeBounds());
                    }
                    FragmentManager fm1 = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
                    fragmentTransaction1.replace(R.id.view_onclick_frame, fr1);
                    fragmentTransaction1.addToBackStack(null);
                    fragmentTransaction1.commit();

                }
                else  if(app_to_open.equalsIgnoreCase("NEWS_WORKSPACE")) {
                    // Transition for fragment1
                    Slide slideTransition = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        slideTransition = new Slide(Gravity.LEFT);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
                    }

                    // ((HomeActivity)getActivity()).getSupportActionBar(). setHomeAsUpIndicator(R.drawable.back_button);

                    HashMap datatosend  = new HashMap();
                    datatosend.put("DATA2",extras.getString("NEWS_HEADER")) ;
                    datatosend.put("DATA3",extras.getString("NEWS_DETAILS")) ;
                    datatosend.put("NOTIFY_IMAGE",extras.getString("NEWS_IMAGE")) ;
                    Fragment fr1 = NotifyDetailFragment.newInstance(datatosend,null);
                    fr1.setReenterTransition(slideTransition);
                    fr1.setExitTransition(slideTransition);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        fr1.setSharedElementEnterTransition(new ChangeBounds());
                    }
                    FragmentManager fm1 = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
                    fragmentTransaction1.replace(R.id.view_onclick_frame, fr1);
                    fragmentTransaction1.addToBackStack(null);
                    fragmentTransaction1.commit();

                }

            }
        }



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    //for sending user data for personal notifications
    @Override
    public void onStart() {
        super.onStart();
        UserLoginManager logincheck123 = new UserLoginManager(this);
        logincheck=false;


        if(logincheck123.getmanualLogin()){

            firebasetoken(this);

        }

        else  if (AccessToken.getCurrentAccessToken() != null) {
            Bundle params = new Bundle();
            params.putString("fields", "id,email,name,gender,cover,picture.type(large)");
            new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            if (response != null) {
                                try {
                                    JSONObject data = response.getJSONObject();
                                    firebasetoken(MainActivity.this);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).executeAsync();
        }
        else{


            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
            if (opr.isDone()) {

                GoogleSignInResult result = opr.get();
                handleSignInResult(result);
            } else {

                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(GoogleSignInResult googleSignInResult) {
                        handleSignInResult(googleSignInResult);
                    }
                });
            }
        }


    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            logincheck=true;
            firebasetoken(this);
        }
    }
    void  firebasetoken(MainActivity homeActivity){
        String token = FirebaseInstanceId.getInstance().getToken();
        try {
            String  server =  Util.getProperty("name",homeActivity);
            serverUrl= server.concat("/FirebaseTokenAccess");

        } catch (IOException e) {
            e.printStackTrace();
        }


        UserLoginManager logincheck123 = new UserLoginManager(this);
        HashMap fetchemail =  logincheck123.getUserDetails();

        String data = (String)fetchemail.get("username")+"|"+(String)fetchemail.get("email")+"|"+  (String)fetchemail.get("address");
        String WORKSPACE_ID = "HOME_WORKSPACE";
        String FUNCTION_ID = "TOKEN_FIREBASE";
        String ACTION_ID = "USER_TOKEN";
        String POSITION_NO=token;
        String UsertoSend = data;
        new GetStringShowDetailTask(UsertoSend,POSITION_NO,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public class GetStringShowDetailTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        private String tokentoSend;
        private String user;
        View objview;


        public GetStringShowDetailTask(String user ,String  token,String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            tokentoSend= token;
            this.user = user;
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
                Record.put( "token", tokentoSend);
                Record.put( "user", user);
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

        }
    }
    
    
    class fragmentPagerAdapter extends FragmentStatePagerAdapter {

        fragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int i) {

            switch (i) {
                case 0:
                    return new IconsFragment();

                case 1:
                    return new DandeVideoFragment();
                case 2:
                    return new LoadWebUrl();
                default:
                    return null;
            }


        }

        @Override
        public int getCount() {
            return 3;
        }


    }



}
