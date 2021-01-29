package collegeexplore.CollegeInfo.LoginManagement;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ToolTipPopup;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.ref.WeakReference;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import collegeexplore.CollegeInfo.HomeActivity;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.Util;
import collegeexplore.CollegeInfo.other.CircleTransform;

import static collegeexplore.CollegeInfo.BaseDetailActivity.TYPE_PROGRAMMATICALLY;
import static collegeexplore.CollegeInfo.homeNewsFragment.calculateInSampleSize;

/**
 * Created by DANDE on 04-07-2016.
 */
public class UserLogin extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG =null;

    EditText mEdit,mobileNo,emailID,password;
    String serverUrl;
    String USERNAME,MOBILE_NO,EMAIL_ID,PASSWORD;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private TextView mStatusTextView;
    private int type;
    private ProgressDialog mProgressDialog;
    private ImageView colsebutton;
    private Button fbbutton,manuallogoutbut;
    String encodedImage;
    LoginButton loginButton;
    Boolean backpressforlogin=true;
    CallbackManager callbackManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlogin);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
        type = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        setupWindowAnimations();}
        Toast.makeText(this, R.string.always_logged_in_to_access, Toast.LENGTH_LONG).show();
        mStatusTextView = (TextView) findViewById(R.id.status);
        fbbutton = (Button) findViewById(R.id.fb);
        mEdit   = (EditText)findViewById(R.id.name);
        password= (EditText)findViewById(R.id.user_password);
        mEdit.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        password.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        manuallogoutbut=(Button)findViewById(R.id.manual_sign_out_button) ;
        callbackManager = CallbackManager.Factory.create();


        Toolbar myToolbar = (Toolbar) findViewById(R.id.PurenewsToolbar);
        setSupportActionBar(myToolbar);
       getSupportActionBar().setTitle(R.string.user_login);
        // Get a support ActionBar corresponding to this toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             onBackPressed();
            }

        });

        //  getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Enable the Up but     ton
/*        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
            }

        });*/
        manuallogoutbut.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
UserLoginManager val = new UserLoginManager(UserLogin.this);
                val.logoutUser();
                finish();
            }});
        //tool Bar for userlogin
        colsebutton = (ImageView) findViewById(R.id.Closeactivitybutton);

                colsebutton.setOnClickListener(new View.OnClickListener() {


            public void onClick(View arg0) {
              //  LoginManager.getInstance().logOut();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();

                }
                else{finish();}

            }});

        Profile   profile = Profile.getCurrentProfile().getCurrentProfile();
        if (profile != null) {
            fbbutton.setText(R.string.sign_out);
            // user has logged in
        } else {
            fbbutton.setText(R.string.facebook);
            // user has not logged in
        }

        TextView fbcheck = (TextView)findViewById(R.id.fb_sign_out_button);
        fbcheck.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                loginButton.performClick();


            }});
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null){
                    //clearing the seccsion
                    UserLoginManager val = new UserLoginManager(UserLogin.this);
                    val.logoutUser();
                   finish();
                }
                else{
                    findViewById(R.id.LinearPackedData).setVisibility(View.GONE);
                    findViewById(R.id.sign_out_button).setVisibility(View.GONE);
                    findViewById(R.id.fb_sign_out_button).setVisibility(View.VISIBLE);
                    findViewById(R.id.manual_sign_out_button).setVisibility(View.GONE);
                    LinearLayout val =(LinearLayout) findViewById(R.id.sign_out_and_disconnect);
                    val.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            2.0f
                    );

                    val.setLayoutParams(param);

                    LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            3.0f
                    );
                    RelativeLayout val1 =(RelativeLayout) findViewById(R.id.RelativeLayout111);
                    val1.setLayoutParams(param1);
                }
            }
        };
        fbbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                    loginButton.performClick();
                Profile   profile = Profile.getCurrentProfile().getCurrentProfile();

            }});
        //loginButton.setText("Sign in with Facebook");
        loginButton.setReadPermissions("email");
        loginButton.setToolTipStyle(ToolTipPopup.Style.BLUE);
        AccessToken.getCurrentAccessToken();

        loginButton.setText(R.string.login_with_facebook);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                final LoginResult trsdt=loginResult;
                trsdt.getAccessToken();
//closing after successfull login


                Bundle params = new Bundle();
                params.putString("fields", "id,email,name,gender,cover,picture.type(large)");
                new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                        new GraphRequest.Callback() {
                            @Override
                            public void onCompleted(GraphResponse response) {
                                if (response != null) {
                                    try {
                                        JSONObject data = response.getJSONObject();
                                        if(data!=null) {
                                            String User_Name="";
                                            String User_Email="";
                                            if (data.has("email")) {
                                            User_Email = Util.Nullcheck((String) data.get("email"));

                                                 User_Name = Util.Nullcheck((String) data.get("name"));
                                                mStatusTextView.setText(getString(R.string.signed_in_fmt, User_Name) + "\n" + User_Email);
                                            }
                                            String profilePicUrl = "";
                                            if (data.has("picture")) {
                                                profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                                ImageView pic = (ImageView) findViewById(R.id.carduserImageimg);
                                                Glide.with(UserLogin.this).load(profilePicUrl)
                                                        .crossFade()
                                                        .thumbnail(0.5f)
                                                        .bitmapTransform(new CircleTransform(UserLogin.this))
                                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                        .into(pic);
                                                //  Uri myUri = Uri.parse(profilePicUrl);
                                                //    URL url_value = new URL(profilePicUrl);
                                        /*    Bitmap bitmapa = null;
                                            Bitmap mIcon1= null;
                                            try
                                            {
                                                 mIcon1 = BitmapFactory.decodeStream(url_value.openConnection().getInputStream());
                                               // Bitmap bitmap = ((BitmapDrawable)pic.getDrawable()).getBitmap();
                                                // bitmapa =pic.getDrawable();
                                            }
                                            catch (Exception e)
                                            {
                                                String check="";
                                            }
                                            Bitmap bitmap =   mIcon1;
                                            //  Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                                             encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                                          //  getFacebookProfilePicture(profilePicUrl);
                                            // set profilePic bitmap to imageview*/
                                            } else {
                                                encodedImage = "";
                                            }
                                            UserLoginManager dan = new UserLoginManager(UserLogin.this);
                                            dan.UserImageUrl(profilePicUrl);
                                            JSONObject userdata = new JSONObject();
                                            String deviceName = Build.MODEL;
                                            String deviceMan = Build.MANUFACTURER;
                                            try {
                                                userdata.put("LOGIN_TYPE", "FACEBOOK_LOGIN");
                                                userdata.put("USERNAME_EMAIL", User_Email);
                                                userdata.put("PASSWORD", "");
                                                userdata.put("USERNAME", User_Name);
                                                // userdata.put("USER_IMAGE",profilePicUrl);
                                                userdata.put("USER_IMAGE", "");
                                                userdata.put("deviceName", deviceName);
                                                userdata.put("deviceMan", deviceMan);
                                                // userdata.put("USER_IMAGE",profilePicUrl);


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            // MOBILE_NO =  mobileNo.getText().toString();
                                            //  EMAIL_ID =  emailID.getText().toString();
                                            Boolean processFlag = true;
                                            if (processFlag) {
                                                try {
                                                    String server = Util.getProperty("name", getApplicationContext());
                                                    serverUrl = server.concat("/UserRegistrationPassWay");

                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }

                                                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                                                if (networkInfo != null && networkInfo.isConnected()) {
                                                    // new DownloadLoginData().execute(serverUrl);
                                                    String WORKSPACE_ID = "USER_LOGIN_WORKSPACE";
                                                    String FUNCTION_ID = "USER_LOGIN_FORALL";
                                                    String ACTION_ID = "LOGIN_MANUAL";

                                                    String usrdata = userdata.toString();
                                                    //int POSITION_NO=mNum_position;
                                                   backpressforlogin=false;
                                                    new DownloadLoginData(WORKSPACE_ID, FUNCTION_ID, ACTION_ID, usrdata).execute(serverUrl);
                                                }
                                            }


                                        }
                                    else{
                                            LoginManager.getInstance().logOut();
                                           // loginButton.performClick();
                                            Toast.makeText(getApplicationContext(), "Please check your Internet.Login Unsuccessfully,", Toast.LENGTH_LONG).show();

                                        }} catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).executeAsync();


/*
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                             JSONObject x = object;
                                JSONObject data = response.getJSONObject();
                                try {
                                    String profileImg = "https://graph.facebook.com/" + trsdt.getAccessToken().getUserId() + "/picture?type=large&width=1080";
                                    HomeActivity.User="Hi ! "+ (String)x.get("name");
                                    mStatusTextView.setText( (String)x.get("name")); ;
                                    if (data.has("picture")) {
                                        String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                        String sdfsd = "sdfgsdfg";
                                        //Bitmap profilePic= BitmapFactory.decodeStream(profilePicUrl.openConnection().getInputStream());
                                      //  mImageView.setBitmap(profilePic);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
               // Bundle parameters = new Bundle();
              //  parameters.putString("fields", "id,name,link");
              //  request.setParameters(parameters);
                request.executeAsync();*/

            }

            public void getFacebookProfilePicture(String url){

                new GetFBImage().execute(url);

            }

            @Override
            public void onCancel() {
                System.out.println("canceled");
            }

            @Override
            public void onError(FacebookException exception) {
                System.out.println("error"+exception);
            }
        });

        //loginButton.setFragment(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Button signInButton = (Button) findViewById(R.id.sign_in_button);
       // signInButton.setSize(SignInButton.SIZE_WIDE);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
       // findViewById(R.id.disconnect_button).setOnClickListener(this);
        TextView createmanaual = (TextView) findViewById(R.id.create_usr_man);
        createmanaual.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                UserManualRegister cdd=new UserManualRegister(UserLogin.this);
                cdd.show();
            }});
        Button btnSubmitlogin = (Button) findViewById(R.id.SubmitLogin);
        btnSubmitlogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                String deviceName = android.os.Build.MODEL;
                String deviceMan = android.os.Build.MANUFACTURER;
                USERNAME  =  mEdit.getText().toString();
                PASSWORD =  password.getText().toString();
                JSONObject userdata = new JSONObject();
                Boolean processFlag = true;
                boolean Emailvalid = isValidEmailAddress(USERNAME);
                if(USERNAME.length()==0 || !Emailvalid)
                {
                    processFlag = false;
                    Toast.makeText(getApplicationContext(), R.string.enter_valid_email, Toast.LENGTH_LONG).show();
                    mEdit.setError(getString(R.string.enter_valid_email1));
                    return;
                }
                if(PASSWORD.length()<8)
                {
                    processFlag = false;
                    Toast.makeText(getApplicationContext(), R.string.password_length, Toast.LENGTH_LONG).show();
                    password.setError(getString(R.string.password_length));
                    return;
                }
                //Starting a new Intent
                // Gets the URL from the UI's text field.


               // mButton = (Button)findViewById(R.id.button);

             //  mobileNo   = (EditText)findViewById(R.id.UserMobileNo);
             //   emailID   = (EditText)findViewById(R.id.EmailLogin);


                try {
                    userdata.put("LOGIN_TYPE","MANUAL_LOGIN");
                    userdata.put("USERNAME_EMAIL",USERNAME);
                    userdata.put("USERNAME",USERNAME);
                    userdata.put("USER_IMAGE","");
                    userdata.put("PASSWORD",PASSWORD);
                    userdata.put("deviceName",deviceName);
                    userdata.put("deviceMan",deviceMan);
                    UserLoginManager dan= new UserLoginManager(UserLogin.this);
                    dan.UserImageUrl("");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // MOBILE_NO =  mobileNo.getText().toString();
               //  EMAIL_ID =  emailID.getText().toString();
               if(processFlag)
                {   try {
                    String  server =  Util.getProperty("name", getApplicationContext());
                     serverUrl = server.concat("/UserRegistrationPassWay");

                } catch (IOException e) {
                    e.printStackTrace();
                }

                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                   // new DownloadLoginData().execute(serverUrl);
                    String WORKSPACE_ID = "USER_LOGIN_WORKSPACE";
                    String FUNCTION_ID = "USER_LOGIN_FORALL";
                    String ACTION_ID = "LOGIN_MANUAL";
                    String usrdata=userdata.toString();
                    backpressforlogin=false;
                    //int POSITION_NO=mNum_position;
                    new DownloadLoginData(WORKSPACE_ID, FUNCTION_ID, ACTION_ID,usrdata).execute(serverUrl);
                }}

            }
        });
    }
    @Override
    public void onBackPressed() {
        if (  !backpressforlogin) {
          //  doSomething();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public void onPause() {
        if (  !backpressforlogin) {
            signOut();
            LoginManager.getInstance().logOut();
        }
        else {
            super.onPause();
        }
    }

    private void UserLoginMethod() {
    }


    @Override
    public void onStart() {
        super.onStart();
UserLoginManager logincheck = new UserLoginManager(UserLogin.this);

        if(logincheck.getmanualLogin()){
            findViewById(R.id.LinearPackedData).setVisibility(View.GONE);
            findViewById(R.id.LinearLayout2).setVisibility(View.GONE);

            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
            findViewById(R.id.fb_sign_out_button).setVisibility(View.GONE);
            findViewById(R.id.manual_sign_out_button).setVisibility(View.VISIBLE);
            LinearLayout val =(LinearLayout) findViewById(R.id.sign_out_and_disconnect);
            val.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    2.0f
            );

            val.setLayoutParams(param);

            LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    3.0f
            );
            RelativeLayout val1 =(RelativeLayout) findViewById(R.id.RelativeLayout111);
            val1.setLayoutParams(param1);

            TextView val123 = (TextView) findViewById(R.id.status);
            HashMap fetchemail =  logincheck.getUserDetails();
            val123.setText((String)fetchemail.get("email"));

        }
        //facebook check

    else    if (AccessToken.getCurrentAccessToken() != null) {
            findViewById(R.id.LinearLayout2).setVisibility(View.GONE);
            findViewById(R.id.LinearPackedData).setVisibility(View.GONE);
            findViewById(R.id.LinearLayout2).setVisibility(View.GONE);

            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
            findViewById(R.id.fb_sign_out_button).setVisibility(View.VISIBLE);
            findViewById(R.id.manual_sign_out_button).setVisibility(View.GONE);
            LinearLayout val =(LinearLayout) findViewById(R.id.sign_out_and_disconnect);
            val.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    2.0f
            );

            val.setLayoutParams(param);

            LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    3.0f
            );
            RelativeLayout val1 =(RelativeLayout) findViewById(R.id.RelativeLayout111);
            val1.setLayoutParams(param1);
            Bundle params = new Bundle();
            params.putString("fields", "id,email,name,gender,cover,picture.type(large)");
            new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            if (response != null) {
                                try {
                                    JSONObject data = response.getJSONObject();

                                    String User_Name="";
                                    String User_Email="";
                                    if (data.has("email")) {
                                        User_Email = Util.Nullcheck((String) data.get("email"));

                                        User_Name = Util.Nullcheck((String) data.get("name"));
                                        mStatusTextView.setText(getString(R.string.signed_in_fmt, User_Name) + "\n" + User_Email);
                                    }

                                    mStatusTextView.setText(getString(R.string.signed_in_fmt,User_Name)+"\n"+User_Email);
                                    // TextView user1 = (TextView) findViewById(R.id.USERNAME);

                                    // user1.setText(User);
                                    if (data.has("picture")) {
                                        String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                        ImageView pic =(ImageView) findViewById(R.id.carduserImageimg) ;
                                        Glide.with(UserLogin.this).load(profilePicUrl)
                                                .crossFade()
                                                .thumbnail(0.5f)
                                                .bitmapTransform(new CircleTransform(UserLogin.this))
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(pic);
                                        //sending token to server

                                        // set profilePic bitmap to imageview
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).executeAsync();
            //LoginManager.getInstance().logOut();
            // loginButton.performClick();

        }

     else {   //google check
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptiosnalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }}


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            FirstTimeGoogleSignInResult(result);
            //google successfull close
         /*   if(result.isSuccess()){
                finish();
            }*/
        }
    }
    // Read bitmap
    public Bitmap readBitmap(Uri selectedImage) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 5;
        AssetFileDescriptor fileDescriptor =null;
        try {
            fileDescriptor = this.getContentResolver().openAssetFileDescriptor(selectedImage,"r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally{
            try {
                bm = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);
                fileDescriptor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bm;
    }
    private void handleSignInResult(GoogleSignInResult result) {
       // Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        String sucs="handleSignInResult:" + result.isSuccess();
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Uri photoUri = acct.getPhotoUrl();
            ImageView pic =(ImageView) findViewById(R.id.carduserImageimg) ;
            if(photoUri!=null)
            Glide.with(this).load(photoUri.toString())
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(pic);
            HomeActivity.User="Hi ! "+acct.getDisplayName()+"\n"+acct.getEmail();
            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName())+"\n"+acct.getEmail());
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }
    private void FirstTimeGoogleSignInResult(GoogleSignInResult result) {
        // Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        String sucs="handleSignInResult:" + result.isSuccess();

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Uri photoUri = acct.getPhotoUrl();
            ImageView pic =(ImageView) findViewById(R.id.carduserImageimg) ;
            String photoUristring = "";

            if(photoUri!=null)
                photoUristring=photoUri.toString();
                Glide.with(this).load(photoUristring)
                        .crossFade()
                        .thumbnail(0.5f)
                        .bitmapTransform(new CircleTransform(this))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(pic);
            HomeActivity.User="Hi ! "+acct.getDisplayName()+"\n"+acct.getEmail();
            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName())+"\n"+acct.getEmail());

            JSONObject userdata = new JSONObject();
            String deviceName = android.os.Build.MODEL;
            String deviceMan = android.os.Build.MANUFACTURER;
            try {
                userdata.put("LOGIN_TYPE","GOOGLE_LOGIN");
                userdata.put("USERNAME_EMAIL",acct.getEmail());
                userdata.put("USERNAME",acct.getDisplayName());
                userdata.put("PASSWORD","");

                userdata.put("deviceName",deviceName);
                userdata.put("deviceMan",deviceMan);
                if(null!=photoUri) {
                    userdata.put("USER_IMAGE", photoUri.toString());
                }
                else{

                    userdata.put("USER_IMAGE", "");
                }
                UserLoginManager dan= new UserLoginManager(UserLogin.this);
                dan.UserImageUrl(photoUristring);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // MOBILE_NO =  mobileNo.getText().toString();
            //  EMAIL_ID =  emailID.getText().toString();
            Boolean processFlag=true;
            if(processFlag)
            {   try {
                String  server =  Util.getProperty("name", getApplicationContext());
                serverUrl = server.concat("/UserRegistrationPassWay");

            } catch (IOException e) {
                e.printStackTrace();
            }

                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

                    // new DownloadLoginData().execute(serverUrl);
                    String WORKSPACE_ID = "USER_LOGIN_WORKSPACE";
                    String FUNCTION_ID = "USER_LOGIN_FORALL";
                    String ACTION_ID = "LOGIN_MANUAL";
                    String usrdata=userdata.toString();
                    //int POSITION_NO=mNum_position;
                backpressforlogin=false;
                    new DownloadLoginData(WORKSPACE_ID, FUNCTION_ID, ACTION_ID,usrdata).execute(serverUrl);
                }


            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(getApplicationContext(), "Please check your Internet.Login Unsuccessfully,", Toast.LENGTH_LONG).show();
            updateUI(false);
        }
    }
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }
    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.LinearPackedData).setVisibility(View.GONE);

            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
            findViewById(R.id.fb_sign_out_button).setVisibility(View.GONE);
            findViewById(R.id.manual_sign_out_button).setVisibility(View.GONE);
            LinearLayout val =(LinearLayout) findViewById(R.id.sign_out_and_disconnect);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    2.0f
            );

            val.setLayoutParams(param);

            LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    3.0f
            );
            RelativeLayout val1 =(RelativeLayout) findViewById(R.id.RelativeLayout111);
            val1.setLayoutParams(param1);

        } else {
           // mStatusTextView.setText(R.string.signed_out);
            findViewById(R.id.LinearLayout2).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);

        }
        //success google login

    }
    @Override
    public void onClick(View view) {

            switch (view.getId()) {
                case R.id.sign_in_button:
                    signIn();
                    break;
                case R.id.sign_out_button:
                    signOut();

                    break;
              /*  case R.id.disconnect_button:
                    revokeAccess();
                    break;*/

        }
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [START signOut]
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
        //clearing the session
        UserLoginManager val = new UserLoginManager(UserLogin.this);
        val.logoutUser();
    }
    // [END signOut]

    // [START revokeAccess]
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setupWindowAnimations() {
        Transition transition;

        if (type == TYPE_PROGRAMMATICALLY) {
            transition = buildEnterTransition();
        }  else {
            transition = TransitionInflater.from(this).inflateTransition(R.transition.explode);
        }
        getWindow().setEnterTransition(transition);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Transition buildEnterTransition() {
        Explode enterTransition = new Explode();
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        return enterTransition;
    }
    public class GetFBImage  extends AsyncTask<String, Void, Bitmap>

    {
        int imageResId;
        int imagetest;
        private WeakReference<ImageView> imageViewReference;
        private int data = 0;
        public GetFBImage() {


        }



        public GetFBImage(ImageView imageView, int resId) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageResId = resId;
            imageViewReference = new WeakReference<ImageView>(imageView);
        }


        @Override
        protected  void onPreExecute()
        {
            // progresdialoglistview=ProgressDialog.show(getActivity(), "","Loading");
            // Log.e("onPreExecutive","called"+progresdialoglistview);
          //  xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
        }



        @Override
        protected Bitmap doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.

            Bitmap xy = null;
            Bitmap BITMAPEXP = null;
            try {
                xy = downloadUrl(urls[0]);
                return xy;
            } catch (IOException e) {
                e.printStackTrace();
                return BITMAPEXP;
            }

        }


        private Bitmap downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;
            String contentAsString=null;
            Bitmap bitmap=null;

            HttpURLConnection urlConnection = null;
            CookieHandler.setDefault( new CookieManager( null, CookiePolicy.ACCEPT_ALL ) );
        /* for get request
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 );
            conn.setConnectTimeout(15000 );
            conn.setRequestMethod("POST");
        conn.setRequestProperty("dande", "fdfdf");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();   */
            //for post request

            URL url = new URL(myurl);
            urlConnection = (HttpURLConnection) url.openConnection();



            int response = urlConnection.getResponseCode();
            // Log.d(DEBUG_TAG, "The response is: " + response);
            if (response==200) {
                is = urlConnection.getInputStream();
            }
            Bitmap bitmap1 = null;
            if(is!=null) {
                //for storing image data
                byte[] bytes = IOUtils.toByteArray(is);

                //degrading the image required to pur requirement
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int reqWidth = size.x;
                int reqHeight = size.y;

                options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight / 4);
                options.inJustDecodeBounds = false;
                bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                // addBitmapToMemoryCache(String.valueOf(imageResId), bitmap1);
            }
            return bitmap1;



        }



        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Bitmap result) {
        /*   ImageView imageView = (ImageView) findViewById(R.id.headerimage);
            imageView.setImageBitmap(result);*/
            }




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
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                // int x = positionCheck;
                String xy = downloadUrl(urls[0]);
                return xy;
            } catch (IOException e) {
                return "UNABLE_TO_REACH_WEB";
            }}
        @Override
        protected  void onPreExecute()
        {
            // progresdialoglistview=ProgressDialog.show(getActivity(), "","Loading");
            // Log.e("onPreExecutive","called"+progresdialoglistview);
            findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }


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
            String key;

            for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
                System.out.println(key + ":" + conn.getHeaderField(i));
            }
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
//if in case of data is null
            if (result == null) {
                signOut();
                Toast.makeText(getApplicationContext(), "Please check your Internet.Login Unsuccessful,", Toast.LENGTH_LONG).show();
                LoginManager.getInstance().logOut();
                result="";}
                //happens when internet for the app is not available
            if(result.equalsIgnoreCase("UNABLE_TO_REACH_WEB"))
            {
                signOut();
                LoginManager.getInstance().logOut();
                Toast.makeText(getApplicationContext(), "Please check your Internet.Login Unsuccessful,", Toast.LENGTH_LONG).show();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(getApplicationContext(), R.string.not_able_con_serv, Toast.LENGTH_LONG).show();
                findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else {
                //happens when server execption and response will be this
                if (!result.equalsIgnoreCase("")) {
                try {
                    JSONObject obj = new JSONObject(ing);

                    int lenghtl = obj.length();
                    //StringData=new  String[8];
                    for (int i = 0; i < lenghtl; i++) {
                        JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));

                        String collegedesc = (String) clgdata.get("RESULTCODE");
                        if (collegedesc.equalsIgnoreCase("000000")) {
                            backpressforlogin=true;
                            String userSessionEmail = (String) clgdata.get("USER_EMAIL_RETURN");
                            String userSessionDataID = (String) clgdata.get("userSessionDataID");
                            String username = (String) clgdata.get("USER_USERNAME");

                                    UserLoginManager usersess = new UserLoginManager(UserLogin.this);

                            usersess.createLoginSession(userSessionDataID,userSessionEmail,username);
                            //user all alerts enabling
                            usersess.useralerts();
                            FirebaseMessaging.getInstance().subscribeToTopic("NEWS");
                            FirebaseMessaging.getInstance().subscribeToTopic("ADMISSIONS");
                            FirebaseMessaging.getInstance().subscribeToTopic("EXAMS");
                            FirebaseMessaging.getInstance().subscribeToTopic("FESTS");
                            FirebaseMessaging.getInstance().subscribeToTopic("RECOMMENDED");

                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            JSONObject logintypechcek = new JSONObject(usr_info);
                            if(logintypechcek.get("LOGIN_TYPE").equals("MANUAL_LOGIN")){
                                TextView val = (TextView) findViewById(R.id.status);
                               HashMap fetchemail =  usersess.getUserDetails();
                                val.setText((String)fetchemail.get("email"));

                                usersess.manualLogin(true);

                            }
                            Toast.makeText(getApplicationContext(), R.string.successfully_loged_in, Toast.LENGTH_LONG).show();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            finish();

                        } else if (collegedesc.equalsIgnoreCase("999999")){
                            signOut();
                            LoginManager.getInstance().logOut();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(getApplicationContext(), R.string.enter_correct_pass, Toast.LENGTH_LONG).show();
                        }
                        else if (collegedesc.equalsIgnoreCase("000012")){
                            signOut();
                            LoginManager.getInstance().logOut();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(getApplicationContext(), R.string.register_before, Toast.LENGTH_LONG).show();
                        }
                        else if (collegedesc.equalsIgnoreCase("12345")){
                            signOut();
                            LoginManager.getInstance().logOut();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(getApplicationContext(), "Please check your Internet.Login Unsuccessful,", Toast.LENGTH_LONG).show();
                        }

                    }



                    //   LatLng sydney = new LatLng(clgLat,80);
                    //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                    findViewById(R.id.marker_progress).setVisibility(View.GONE);
            }else{
                    Toast.makeText(getApplicationContext(), "Please check your Internet.Login Unsuccessful,", Toast.LENGTH_LONG).show();
                    signOut();
                    LoginManager.getInstance().logOut();
                }}

        }
    }
}