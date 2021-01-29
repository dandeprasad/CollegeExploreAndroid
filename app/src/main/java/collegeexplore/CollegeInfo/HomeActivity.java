package collegeexplore.CollegeInfo;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.Visibility;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

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
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import collegeexplore.CollegeInfo.AdmissionsWorkspace.CollegeAdmisFragment;
import collegeexplore.CollegeInfo.CollegesAtMaps.homeMapsActivity;
import collegeexplore.CollegeInfo.CutoffsWorkspace.CutoffFirstFragment;
import collegeexplore.CollegeInfo.ExamsWorkspace.ExamsDetailsViewPager;
import collegeexplore.CollegeInfo.ExamsWorkspace.HomeExamsFragment;
import collegeexplore.CollegeInfo.FestsCollegesWorkspace.FestNewsNotifications;
import collegeexplore.CollegeInfo.LoginManagement.UserLogin;
import collegeexplore.CollegeInfo.LoginManagement.UserLoginManager;
import collegeexplore.CollegeInfo.NewsWorkspace.NewsFragment;
import collegeexplore.CollegeInfo.NewsandNotificationsWorkspace.HomeNewsNotifications;
import collegeexplore.CollegeInfo.NitInformation.NitHomeActivity;
import collegeexplore.CollegeInfo.PlacementsWorkspace.PlaceFirstFragment;
import collegeexplore.CollegeInfo.QuestionsAnswersWorkspace.PostQuestion;
import collegeexplore.CollegeInfo.RankingsWorkspace.RankingsFragment;
import collegeexplore.CollegeInfo.UserNotifications.NotifyDetailFragment;
import collegeexplore.CollegeInfo.UserNotifications.NotifyFragment;
import collegeexplore.CollegeInfo.UserNotifications.UserNotifications;
import collegeexplore.CollegeInfo.UserUploadsWorkspace.UploadDataforUs;
import collegeexplore.CollegeInfo.Utils.VerticalMainActivity;
import collegeexplore.CollegeInfo.V1.MainActivity;
import collegeexplore.CollegeInfo.other.CircleTransform;

import static collegeexplore.CollegeInfo.homeNewsFragment.calculateInSampleSize;

public class HomeActivity extends AppCompatActivity implements HomeActivityAdaptor.OnItemClickListener, GoogleApiClient.OnConnectionFailedListener   {
    private static final int VERTICAL_ITEM_SPACE = 30;
    private static final int SPAN_COUNT = 1;
    private static final String TAG = "HomeActivity";
    private DrawerLayout homeDrawerLayout;

    Toolbar HomeToolbar;
    private String[] activityTitles;
    private NavigationView navigationView;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite,useradd;


    //private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "Home";
    private static final String TAG_NEWS= "News";
    private static final String TAG_ADMINS = "Admissions";
    private static final String TAG_CLGS = "Colleges";
    private static final String TAG_EXAMS= "Exams";
    private static final String TAG_CARRERGUID= "Carrer Guidance";
    private static final String TAG_CHAT= "Chat";
    private static final String TAG_SHARE= "Share";
    private static final String TAG_ABOUT= "About Us";
    private static final String TAG_PROFILEEDIT= "Profile Edit";
    private static final String TAG_NOTIFY= "Notifications";
    private static final String TAG_FESTS = "Fests";
    private static final String TAG_QUEST_ANS = "QuestionsAnswers";
    private static final String TAG_CUTOFFS = "cutoffs";
    private static final String TAG_PLACEMENTS = "Placements";
    private static final String TAG_RANKINGS = "Rankings";
    public static String CURRENT_TAG = TAG_HOME;




 public      FragmentTransaction fragmentTransaction;


    private RecyclerView mDrawerList;
    private LinearLayout DrawerContent;
    public static String User ="";
    public static String User_Email ="";
    public static String User_Name ="";
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] drawerIcons;
    private String[] HomeNotifications;
  //  private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private TextView textField;
    private TextView username;
    private ListView list;
    Boolean logincheck=false;
    private static Sample[] mSamples;
    public static  int Page_Position=0 ;
    SearchView search;
    private GoogleApiClient mGoogleApiClient;

    SwipeRefreshLayout mySwipeRefreshLayout;
    // Whether the Log Fragment is currently shown
    private boolean mLogShown;
    private AdView mAdView;
    private ProgressDialog mProgressDialog;
    String serverUrl = null,jsonurl;
    int backExitCheck=1;
    private Handler mHandler;
    static final String LOG_TAG = "HomeActivity";
    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private String app_to_open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);


   /*     UserLoginManager dand3 = new UserLoginManager(this);
        String languagepref= dand3.getpreferredLanguage();
        Locale locale2 = new Locale(languagepref);
        Locale.setDefault(locale2);*/


        setContentView(R.layout.activity_navigation_icons);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
        mHandler = new Handler();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupWindowAnimations();
        }



        mySwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setColorSchemeResources(
                R.color.app_theme
                /*R.color.refresh_progress_2,
                R.color.refresh_progress_3*/);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        collegeexplore.logger.Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        android.support.v4.app.Fragment fr =  HomeNewsNotifications.newInstance();

                        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.sample_content_fragment,fr, TAG_HOME);
                        fragmentTransaction.detach(fr);
                        fragmentTransaction.attach(fr);
                        fragmentTransaction.commit();

                        mySwipeRefreshLayout.setRefreshing(false);

                      //  myUpdateOperation();
                    }


                }
        );


        try {
                String  server =  Util.getProperty("name", this);
                //  serverUrl= server.concat("/ExamsImages");
                jsonurl= server.concat("/CollegesStartServlet");
            } catch (IOException e) {
                e.printStackTrace();
            }

            String WORKSPACE_ID ="HOME_WORKSPACE";
            String FUNCTION_ID = "COLLEGE_ABOUT_US";
            String ACTION_ID = "COLLEGE_ABOUT_US";
            String WORKSPACE_ID1 ="HOME_WORKSPACE";
            String FUNCTION_ID1 = "USER_VERSIONCODE_BETA";
            String ACTION_ID1= "USER_VERSIONCODE_BETA";

        int appupdateversion = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getInt("appupdateversion", 0);



        if (appupdateversion==0||appupdateversion==7||appupdateversion==15||appupdateversion==25) {
            new GetVersionTask(WORKSPACE_ID1,FUNCTION_ID1,ACTION_ID1).execute(jsonurl);
        }


        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putInt("feedbackformfirsttime", appupdateversion+1).commit();


        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.collegeexplore.app",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash dande", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        }  catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        //loginButton.setFragment(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


      LinearLayout  mBottomNavigationView =  findViewById(R.id.horizontal_dropdown_icon_menu_items);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mBottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());

        android.support.v4.app.Fragment fr =  HomeNewsNotifications.newInstance();

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.sample_content_fragment,fr, TAG_HOME);
        fragmentTransaction.commit();

        RelativeLayout relativehome =findViewById(R.id.relativehome);
        RelativeLayout relativenews =findViewById(R.id.relativenews);
        RelativeLayout relativenotify =findViewById(R.id.relativenotify);
        RelativeLayout relativeprofile =findViewById(R.id.relativeprofile);
        //start color enabing
        ImageView hom1=  findViewById(R.id.home_bottom_img);
        TextView hom=    findViewById(R.id.home_bottom_txt);
        hom1.setColorFilter(getResources().getColor(R.color.bottombar));
        hom.setTextColor(getResources().getColor(R.color.bottombar));
        relativehome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //color configuration
              ImageView hom1=  findViewById(R.id.home_bottom_img);
              TextView hom=    findViewById(R.id.home_bottom_txt);
                hom1.setColorFilter(getResources().getColor(R.color.bottombar));
                hom.setTextColor(getResources().getColor(R.color.bottombar));

                ImageView news1=  findViewById(R.id.news_bottom_img);
                TextView news=    findViewById(R.id.news_bottom_txt);
                news1.clearColorFilter();
                news.setTextColor(getResources().getColor(R.color.blackoptac));

                ImageView notify1=  findViewById(R.id.notify_bottom_img);
                TextView notify=    findViewById(R.id.notify_bottom_txt);
                notify1.clearColorFilter();
                notify.setTextColor(getResources().getColor(R.color.blackoptac));

                ImageView profile1=  findViewById(R.id.profile_bottom_img);
                TextView profile=    findViewById(R.id.profile_bottom_txt);
                profile1.clearColorFilter();
                profile.setTextColor(getResources().getColor(R.color.blackoptac));
                //color configuration ends here


            }});
        relativenews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //color configuration
                ImageView hom1=  findViewById(R.id.home_bottom_img);
                TextView hom=    findViewById(R.id.home_bottom_txt);
                hom1.clearColorFilter();
                hom.setTextColor(getResources().getColor(R.color.blackoptac));

                ImageView news1=  findViewById(R.id.news_bottom_img);
                TextView news=    findViewById(R.id.news_bottom_txt);
                news1.setColorFilter(getResources().getColor(R.color.bottombar));
                news.setTextColor(getResources().getColor(R.color.bottombar));


                ImageView notify1=  findViewById(R.id.notify_bottom_img);
                TextView notify=    findViewById(R.id.notify_bottom_txt);
                notify1.clearColorFilter();
                notify.setTextColor(getResources().getColor(R.color.blackoptac));

                ImageView profile1=  findViewById(R.id.profile_bottom_img);
                TextView profile=    findViewById(R.id.profile_bottom_txt);
                profile1.clearColorFilter();
                profile.setTextColor(getResources().getColor(R.color.blackoptac));
                //color configuration ends here
//click calling

                UserLoginManager checkuserstatus = new UserLoginManager(getApplicationContext());
                if(checkuserstatus.isLoggedIn()){
                    Intent i = new Intent(HomeActivity.this,UploadDataforUs.class);
                    transitionTo(i);
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(),UserLogin.class);
                    startActivity(i);
                }

            }});
        relativenotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //color configuration
                ImageView hom1=  findViewById(R.id.home_bottom_img);
                TextView hom=    findViewById(R.id.home_bottom_txt);
                hom1.clearColorFilter();
                hom.setTextColor(getResources().getColor(R.color.blackoptac));

                ImageView news1=  findViewById(R.id.news_bottom_img);
                TextView news=    findViewById(R.id.news_bottom_txt);
                news1.clearColorFilter();
                news.setTextColor(getResources().getColor(R.color.blackoptac));

                ImageView notify1=  findViewById(R.id.notify_bottom_img);
                TextView notify=    findViewById(R.id.notify_bottom_txt);

                notify1.setColorFilter(getResources().getColor(R.color.bottombar));
                notify.setTextColor(getResources().getColor(R.color.bottombar));

                ImageView profile1=  findViewById(R.id.profile_bottom_img);
                TextView profile=    findViewById(R.id.profile_bottom_txt);
                profile1.clearColorFilter();
                profile.setTextColor(getResources().getColor(R.color.blackoptac));
                //color configuration ends here

                //calling clicks
                UserLoginManager checkuserstatus1 = new UserLoginManager(getApplicationContext());
                if(checkuserstatus1.isLoggedIn()){

                    Intent i = new Intent(HomeActivity.this,PostQuestion.class);
                    transitionTo(i);
                }
                else{
                    Toast.makeText(HomeActivity.this, R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(HomeActivity.this,UserLogin.class);
                    transitionTo(i);
                }

            }});
        relativeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //color configuration
                ImageView hom1=  findViewById(R.id.home_bottom_img);
                TextView hom=    findViewById(R.id.home_bottom_txt);
                hom1.clearColorFilter();
                hom.setTextColor(getResources().getColor(R.color.blackoptac));

                ImageView news1=  findViewById(R.id.news_bottom_img);
                TextView news=    findViewById(R.id.news_bottom_txt);
                news1.clearColorFilter();
                news.setTextColor(getResources().getColor(R.color.blackoptac));

                ImageView notify1=  findViewById(R.id.notify_bottom_img);
                TextView notify=    findViewById(R.id.notify_bottom_txt);
                notify1.clearColorFilter();
                notify.setTextColor(getResources().getColor(R.color.blackoptac));

                ImageView profile1=  findViewById(R.id.profile_bottom_img);
                TextView profile=    findViewById(R.id.profile_bottom_txt);

                profile1.setColorFilter(getResources().getColor(R.color.bottombar));
                profile.setTextColor(getResources().getColor(R.color.bottombar));

                //color configuration ends here

                //click enalbing
                Intent i = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(i);

/*                UserLoginManager checkuserstatus1 = new UserLoginManager(getApplicationContext());
                if(checkuserstatus1.isLoggedIn()){
                    Intent i = new Intent(HomeActivity.this,FeedBackForm.class);
                    transitionTo(i);
                }
                else{
                    Toast.makeText(HomeActivity.this, R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(HomeActivity.this,UserLogin.class);
                    transitionTo(i);
                }*/


            }});
       HomeToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        setSupportActionBar(HomeToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
       // HomeToolbar.setTitleTextColor(getColor(R.color.app_theme));
        HomeToolbar.setTitleMarginStart(30);
       /* HomeToolbar.setTitleTextColor(Color.WHITE);
        HomeToolbar.setSubtitleTextColor(Color.WHITE);*/


        final TextView serachRef = (TextView) findViewById(R.id.search_home);
        final ImageView action_searchRef = (ImageView) findViewById(R.id.action_search);

        action_searchRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLoginManager checkuserstatus1 = new UserLoginManager(getApplicationContext());
                if(checkuserstatus1.isLoggedIn()){
                    Intent i = new Intent(HomeActivity.this,homeMapsActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(HomeActivity.this, R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(HomeActivity.this,UserLogin.class);
                    transitionTo(i);
                }

            }
        });

       CardView  carrer_cardview =findViewById(R.id.carrer_cardview);
        carrer_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLoginManager checkuserstatus1 = new UserLoginManager(getApplicationContext());
                if(checkuserstatus1.isLoggedIn()){
                    Intent i = new Intent(HomeActivity.this,homeMapsActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(HomeActivity.this, R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(HomeActivity.this,UserLogin.class);
                    transitionTo(i);
                }

            }
        });
        serachRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLoginManager checkuserstatus1 = new UserLoginManager(getApplicationContext());
                if(checkuserstatus1.isLoggedIn()){
                    Intent i = new Intent(HomeActivity.this,homeMapsActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(HomeActivity.this, R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(HomeActivity.this,UserLogin.class);
                    transitionTo(i);
                }

            }
        });


        //android:text=collegesmainpackage.CollegeInfo.LoginManagement.UserLogin.user



        drawerIcons = getResources().getStringArray(R.array.Navigation_data_icons);

        homeDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layoutnit);

        //mDrawerList = (RecyclerView) findViewById(R.id.left_drawerid);
        DrawerContent = (LinearLayout) findViewById(R.id.sample_main_layout1);
        homeDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mLayoutManager = new GridLayoutManager(this, SPAN_COUNT);


        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = navHeader.findViewById(R.id.USERNAME);
        txtWebsite = navHeader.findViewById(R.id.website_email);
         useradd = navHeader.findViewById(R.id.USERADDRESS);
        imgNavHeaderBg = navHeader.findViewById(R.id.img_header_bg);
        imgProfile = navHeader.findViewById(R.id.carduserImageimg);
        loadNavHeader();
        // initializing navigation menu
        setUpNavigationView();
 /*       if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }*/
        //mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        //mDrawerList.setLayoutManager(mLayoutManager);
        // load nav menu header data

        //mDrawerList.setHasFixedSize(true);

       // mAdapter = new HomeActivityAdaptor(maincontents);
        //mDrawerList.setAdapter(new HomeActivityAdaptor(drawerIcons,mThumbIds, this));
        //mDrawerList.setAnimation();
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
      // mDrawerList.addItemDecoration(new SpacesItemDecorHomeActivity(spacingInPixels));
        //mDrawerList.addItemDecoration(new DividerItemDecoration(getApplicationContext()));

      // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       //getSupportActionBar(). setHomeAsUpIndicator(R.drawable.ic_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if(HomeToolbar != null) {
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                homeDrawerLayout,         /* DrawerLayout object */
                HomeToolbar,
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerOpened(view);
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {

                }
                else {
                    mDrawerToggle.syncState();
                }
               // getSupportActionBar().setIcon(R.drawable.ic_college_logo1);
               // getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle("CollegeExplore");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

        };

            homeDrawerLayout.addDrawerListener(mDrawerToggle);
           mDrawerToggle.syncState();
            getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {

                @Override
                public void onBackStackChanged() {
                    backExitCheck=0;
                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

                        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show back button
                        HomeToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onBackPressed();
                            }
                        });
                    }
                    else {
                        backExitCheck=1;
                        //show hamburger
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        mDrawerToggle.syncState();
                        HomeToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                homeDrawerLayout.openDrawer(GravityCompat.START);
                            }
                        });


                    }
                     }});
    }


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

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }
    @Override
    public void onBackPressed() {

        if (homeDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            homeDrawerLayout.closeDrawers();
            return;
        }

        //color configuration
        ImageView hom1=  findViewById(R.id.home_bottom_img);
        TextView hom=    findViewById(R.id.home_bottom_txt);
        hom1.setColorFilter(getResources().getColor(R.color.bottombar));
        hom.setTextColor(getResources().getColor(R.color.bottombar));

        ImageView news1=  findViewById(R.id.news_bottom_img);
        TextView news=    findViewById(R.id.news_bottom_txt);
        news1.clearColorFilter();
        news.setTextColor(getResources().getColor(R.color.blackoptac));

        ImageView notify1=  findViewById(R.id.notify_bottom_img);
        TextView notify=    findViewById(R.id.notify_bottom_txt);
        notify1.clearColorFilter();
        notify.setTextColor(getResources().getColor(R.color.blackoptac));

        ImageView profile1=  findViewById(R.id.profile_bottom_img);
        TextView profile=    findViewById(R.id.profile_bottom_txt);
        profile1.clearColorFilter();
        profile.setTextColor(getResources().getColor(R.color.blackoptac));
/*        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }*/

         if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();

            } else {
           //  bottomBar.selectTabAtPosition(0);
             getSupportActionBar().setDisplayHomeAsUpEnabled(false);
             mDrawerToggle.syncState();
             HomeToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     homeDrawerLayout.openDrawer(GravityCompat.START);
                 }
             });
//set toolbar title
              //  setToolbarTitle();
                super.onBackPressed();
            }



    }
    @Override
    public void onStart() {
        super.onStart();
        UserLoginManager logincheck123 = new UserLoginManager(this);

        HashMap userhash =  logincheck123.getUserDetails();
       if(userhash.get("address")!=null) {
           useradd.setText((String)userhash.get("address"));
       }
        TextView search = (TextView) findViewById(R.id.search_home);
        search.setText(R.string.search_for_colleges_universities);
        logincheck=false;


        if(logincheck123.getmanualLogin()){

                 imgProfile.setImageResource(R.drawable.userlogo);

            HashMap fetchemail =  logincheck123.getUserDetails();
            txtName.setText((String)fetchemail.get("email"));

            int feedbackformfirsttime = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .getInt("feedbackformfirsttime", 0);



            if (feedbackformfirsttime==0||feedbackformfirsttime==20||feedbackformfirsttime==40||feedbackformfirsttime==60) {
                mynotification("Use feed form to suggest us");
            }


            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putInt("feedbackformfirsttime", feedbackformfirsttime+1).commit();
            firebasetoken(HomeActivity.this);

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
                                    String User_Name="";
                                    String User_Email="";
                                    if (data.has("email")) {
                                        User_Email = Util.Nullcheck((String) data.get("email"));

                                     //   User_Name = Util.Nullcheck((String) data.get("name"));
                                       // mStatusTextView.setText(getString(R.string.signed_in_fmt, User_Name) + "\n" + User_Email);
                                    }

                                     User_Name= (String) data.get("name");
                                    HomeActivity.User=User_Name+" \n"+User_Email;
                                  // TextView user1 = (TextView) findViewById(R.id.USERNAME);
                                    txtName.setText(User_Name);
                                    txtWebsite.setText(User_Email);
                                /*    sendNotification("Use feed icon to suggest us");*/
                                    int feedbackformfirsttime = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                                            .getInt("feedbackformfirsttime", 0);



                                    if (feedbackformfirsttime==0||feedbackformfirsttime==20||feedbackformfirsttime==40||feedbackformfirsttime==60) {
                                        mynotification("Use feed form to suggest us");
                                    }


                                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                                            .putInt("feedbackformfirsttime", feedbackformfirsttime+1).commit();
                                    firebasetoken(HomeActivity.this);
                                   // user1.setText(User);
                                    if (data.has("picture")) {
                                        String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                        getProfilePicture(profilePicUrl);
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
else{


        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
          //  showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                   // hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }}

    //only to show the notification
    /*    private void mynotification(String messageBody) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_college_logo1)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

      //  Intent resultIntent = new Intent(this, UserNotifications.class);
        Intent notifyIntent =
                new Intent(Intent.makeMainActivity(new ComponentName(this, UserNotifications.class)));
// Sets the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);

      //  Intent resultIntent = new Intent(this, UserNotifications.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack
        stackBuilder.addParentStack(UserNotifications.class);
// Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(notifyIntent);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        // Sets an ID for the notification
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }*/
    private void mynotification(String messageBody) {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Welcome to CollegeExplore")
                        .setContentText("Give FeedBack to us")
                        .setAutoCancel(true)
                        .setSound(soundUri);
        Intent resultIntent = new Intent(this, FeedBackForm.class);
        resultIntent.putExtra("LOADING_KEY","NIT_PY");

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        // Sets an ID for the notification
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
/*    public int getNotification(HomeActivity view, String Data) {

        // NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        Context context =this;
        Intent intent = new Intent(context.getApplicationContext(), HomeActivity.class);
        intent.putExtra("Notification","1");
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intent, 0);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Intent for "Navigate" Button on Notification
        String lat = String.valueOf("80");
        String lon = String.valueOf("12");
        String format = "geo:0,0?q=" + lat + "," + lon + "Destination Point";
        Uri uri = Uri.parse(format);
        Intent intentNavigate = new Intent(Intent.ACTION_VIEW, uri);
        PendingIntent pIntentNavigate = PendingIntent.getActivity(context.getApplicationContext(), 0, intentNavigate, 0);

        if (Build.VERSION.SDK_INT < 16) {
            //getToast("API below 16 ...notification not supported");

        } else {
            //Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_car);
            Notification.Builder builder = new Notification.Builder(context);
            //builder.setLargeIcon(icon);
            builder.setSmallIcon(R.drawable.deem_map_icon);
            builder.setContentTitle("Car Rental Application");
            builder.setContentText(Data);
            builder.setOngoing(true);
            builder.setSound(soundUri);
            builder.setContentIntent(pendingIntent);
            builder.addAction(R.drawable.map_environment, "NAVIGATE ", pIntentNavigate);
            builder.addAction(R.drawable.map_filter, "STOP RIDE", pendingIntent);
            Notification notify = builder.build();

            notificationManager.notify(12, notify);

        }

        return 12;
    }

    public void removeNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(
                12);
        Toast.makeText(this, "Notification Removed", Toast.LENGTH_SHORT).show();
     //   getToast("Notification Removed");
    }
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//* Request code *//*, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle("Please do Suggest")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 *//* ID of notification *//*, notificationBuilder.build());
    }*/
    private void handleSignInResult(GoogleSignInResult result) {
        // Log.d(TAG, "handleSignInResult:" + result.isSuccess());'
        int feedbackformfirsttime = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getInt("feedbackformfirsttime", 0);



        if (feedbackformfirsttime==0||feedbackformfirsttime==20||feedbackformfirsttime==40||feedbackformfirsttime==60) {
            mynotification("Use feed form to suggest us");
        }


        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putInt("feedbackformfirsttime", feedbackformfirsttime+1).commit();
       /* getNotification(this,"sdfnksdfkn");
        sendNotification("Use feed icon to suggest us");*/
        String sucs="handleSignInResult:" + result.isSuccess();
        if (result.isSuccess()) {
            logincheck=true;
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Uri photoUri = acct.getPhotoUrl();
           if(photoUri!=null)
               getProfilePicture(photoUri.toString());

            HomeActivity.User="Hi ! "+acct.getDisplayName()+" \n"+acct.getEmail();
           // TextView user1 = (TextView) findViewById(R.id.USERNAME);
           // user1.setText(User);
            txtName.setText(acct.getDisplayName());
            txtWebsite.setText(acct.getEmail());
            firebasetoken(HomeActivity.this);
          //  mStatusTextView.setText(getString(R.string.signed_in_fmt, "Name: "+acct.getDisplayName())+"\n"+"Email:"+acct.getEmail());
          //  updateUI(true);
        } else {
            imgProfile.setImageResource(R.drawable.userlogo);
            txtName.setText(R.string.guest_user);
            // Signed out, show unauthenticated UI.
           // updateUI(false);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navigation_icons, menu);
        // Associate searchable configuration with the SearchView
      /*  SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search1).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setActivated(true);
         searchView.setQueryHint("Search Colleges");
          searchView.onActionViewExpanded();
           searchView.setIconified(false);
          searchView.clearFocus();*/
        return true;
    }


    public void setCount(Context context, String count ,Menu defaultMenu) {
        MenuItem menuItem = defaultMenu.findItem(R.id.notification);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();

        CountDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse != null && reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
    }
    @Override



    public boolean onPrepareOptionsMenu(Menu menu) {
        setCount(this, "1" ,menu);
        // If the nav drawer is open, hide action items related to the content view


      //  boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
       // menu.findItem(R.id.student_login).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {

            return true;
        }
        UserLoginManager dan = new UserLoginManager(this);
        // Handle action buttons
        switch (item.getItemId()) {



            case R.id.notification:
                UserLoginManager checkuserstatus1 = new UserLoginManager(getApplicationContext());
                if(checkuserstatus1.isLoggedIn()){
                    Intent i = new Intent(HomeActivity.this,UserNotifications.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(HomeActivity.this, R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(HomeActivity.this,UserLogin.class);
                    transitionTo(i);
                }

                break;

            case R.id.feedbackform:

                UserLoginManager checkuserstatus12 = new UserLoginManager(getApplicationContext());
                if(checkuserstatus12.isLoggedIn()){
                    Intent i = new Intent(HomeActivity.this,UserLogin.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(HomeActivity.this, R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(HomeActivity.this,UserLogin.class);
                    transitionTo(i);
                }
                break;

           /* case R.id.eng:
                String languageToLoad = "en"; // your language


                dan.preferredLanguage(languageToLoad);
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
             //   this.setContentView(R.layout.activity_navigation_icons);
                HomeActivity.this.finish();
                Intent intent = new Intent(this,
                        HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.hn:
                String languageToLoad1 = "hi"; // your language
                dan = new UserLoginManager(this);
                dan.preferredLanguage(languageToLoad1);
                Locale locale1 = new Locale(languageToLoad1);
                Locale.setDefault(locale1);
                Configuration config1 = new Configuration();
                config1.locale = locale1;
                getBaseContext().getResources().updateConfiguration(config1,
                        getBaseContext().getResources().getDisplayMetrics());
               // this.setContentView(R.layout.activity_navigation_icons);
                HomeActivity.this.finish();
                Intent intent1 = new Intent(this,
                        HomeActivity.class);
                startActivity(intent1);
                break;
            case R.id.tel:
                String languageToLoad2 = "te"; // your language
                dan = new UserLoginManager(this);
                dan.preferredLanguage(languageToLoad2);
                Locale locale2 = new Locale(languageToLoad2);
                Locale.setDefault(locale2);
                Configuration config2 = new Configuration();
                config2.locale = locale2;
                getBaseContext().getResources().updateConfiguration(config2,
                        getBaseContext().getResources().getDisplayMetrics());
                HomeActivity.this.finish();
                Intent intent2 = new Intent(this,
                        HomeActivity.class);
                startActivity(intent2);


               // this.setContentView(R.layout.activity_navigation_icons);
                break;
            case R.id.tamil:
                String languageToLoad3 = "ta"; // your language
                dan = new UserLoginManager(this);
                dan.preferredLanguage(languageToLoad3);
                Locale locale3 = new Locale(languageToLoad3);
                Locale.setDefault(locale3);
                Configuration config3 = new Configuration();
                config3.locale = locale3;
                getBaseContext().getResources().updateConfiguration(config3,
                        getBaseContext().getResources().getDisplayMetrics());
                HomeActivity.this.finish();
                Intent intent3 = new Intent(this,
                        HomeActivity.class);
                startActivity(intent3);
                //this.setContentView(R.layout.activity_navigation_icons);
                break;*/
           /* case R.id.LightTheme:
                new AlertDialog.Builder(
                        new ContextThemeWrapper(this, R.style.TextLabel));
                break;
            case R.id.DarkTheme:
                new AlertDialog.Builder(
                        new ContextThemeWrapper(this, R.style.TextLabel1));*/
                //this.setTheme(R.style.TextLabel);
                //this.setContentView(R.layout.activity_navigation_icons);

    /*        case R.id.student_login:

                Intent i = new Intent(HomeActivity.this, UserLogin.class);

                i.putExtra(EXTRA_TYPE, TYPE_PROGRAMMATICALLY);

           transitionTo(i);*/

           /*     return true;*/

        }

/*        switch (item.getItemId()) {
            case R.id.maps_all:
                Intent intent = new Intent(this,
                        NewsImageActivity.class);
                startActivity(intent);

                return true;

        }*/
        return super.onOptionsItemSelected(item);
    }


    public void onClick(View view, int position) {
        mDrawerToggle.syncState();

       // if(Page_Position!=position)
        selectItem(position);
       // Page_Position=position;
    }

    public void checkImageshow(Bitmap x ) {
        ImageView  imageView=(ImageView)findViewById(R.id.img);
        imageView.setImageBitmap(x);

       // imageView.set
    }

    private void selectItem(int position) {

       // textField = (TextView) findViewById(R.id.homeset);


            // Gets the URL from the UI's text field.
      /*      String urlcheck ="http://10.0.2.2:8085/androiddandeCheck/Hello3check";
            String stringUrl = urlcheck;
            ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
              //  new DownloadWebpageTask(this).execute(stringUrl);
            } else {
                // textView.setText("No network connection available.");
                Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show();

            }



            Toast.makeText(this, "Item: " + position, Toast.LENGTH_SHORT).show();



*/
      if (position==0)
          collegeexplore.CollegeInfo.HomeData.mViewPager.setCurrentItem(position, true);
        if(position==1) {
            dandelogin();
        }
        if (position>=2)
        collegeexplore.CollegeInfo.HomeData.mViewPager.setCurrentItem(position-1, true);



      /* if (position ==0) {
           collegesmainpackage.CollegeInfo.HomeData.mViewPager.setCurrentItem(position, true);
//for removing the fragmet completely
         *//*   android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.sample_content_fragment);
            if(fragment != null)
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();*//*

          //  textField.setText("Home");
          // mDrawerToggle.setDrawerIndicatorEnabled(false);
          // getSupportActionBar().setDisplayHomeAsUpEnabled(false);
         //  getSupportActionBar(). setHomeAsUpIndicator(R.drawable.back_button);
          // getSupportActionBar(). setHomeAsUpIndicator(R.drawable.ic_drawer);




       }

        if (position ==2) {
            collegesmainpackage.CollegeInfo.HomeData.mViewPager.setCurrentItem(position, true);
          //  startActivity(new Intent(this, mSamples[position].activityClass));
          *//*  textField.setText("About");
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SlidingTabsBasicFragment1 fragment = new SlidingTabsBasicFragment1();
        transaction.replace(R.id.sample_content_fragment, fragment);
        transaction.commit();*//*

        }
        if (position ==1) {
            collegesmainpackage.CollegeInfo.HomeData.mViewPager.setCurrentItem(position, true);}*/
        /*
        if (position ==2) {
            textField.setText("Administration");
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SlidingTabsBasicFragmentAdmin fragment = new SlidingTabsBasicFragmentAdmin();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
        if (position ==3) {
            textField.setText("Acadamics");
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SlidingTabsFragmentNit fragment = new SlidingTabsFragmentNit();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }  */

       // setTitle(drawerIcons[position]);


        homeDrawerLayout.closeDrawer(DrawerContent);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * This class describes an individual sample (the sample title, and the activity class that
     * demonstrates this sample).
     */
    private class Sample {
        private CharSequence title;
        private Class<NitHomeActivity> activityClass;

        public Sample(int titleResId, Class<NitHomeActivity> nitHomeActivityClass) {
            this.activityClass = nitHomeActivityClass;
            this.title = getResources().getString(titleResId);
        }



        @Override
        public String toString() {
            return title.toString();
        }
    }

    public void getProfilePicture(String url){

/*        // loading header background image
        Glide.with(this).load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);*/
        // Loading profile image
        Glide.with(this).load(url)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);
      //  new GetFBImage().execute(url);

    }
    private void loadNavHeader() {

/*
        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

*/


        // showing dot next to notifications label
       // navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        //setToolbarTitle();

/*        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            homeDrawerLayout.closeDrawers();

            // show or hide the fab button

            return;
        }*/
        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Slide slideTransition = null;
                Fragment fragment;
                if((CURRENT_TAG.equalsIgnoreCase("News"))) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        slideTransition = new Slide(Gravity.LEFT);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
                    }

                     fragment = getHomeFragment();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        fragment.setSharedElementEnterTransition(new ChangeBounds());
                    }
                    fragment.setReenterTransition(slideTransition);
                    fragment.setExitTransition(slideTransition);


                }
                else{

                    fragment = getHomeFragment();
                }


                 fragmentTransaction = getSupportFragmentManager().beginTransaction();

if(!(CURRENT_TAG.equalsIgnoreCase("Home"))) {

    fragmentTransaction.replace(R.id.view_onclick_frame, fragment, CURRENT_TAG);

}
else{
    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return;
}
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }



        //Closing drawer on item click
        homeDrawerLayout.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }
    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeNewsNotifications homeFragment = new HomeNewsNotifications();
                return homeFragment;
            case 1:
                // news
                String toolbarTorF ="T";
                NewsFragment newsFragment = NewsFragment.newInstance(toolbarTorF);
                return newsFragment;
            case 2:
                // admissions fragment
                CollegeAdmisFragment viewpagetest = new CollegeAdmisFragment();
                return viewpagetest;

            case 5:
                // Fests fragment

            Intent i = new Intent(HomeActivity.this,FestNewsNotifications.class);
            startActivity(i);

            case 4:
                // exams fragment
                HomeExamsFragment settingsFragment = new HomeExamsFragment();
                return settingsFragment;
            case 7:
                // cutoffs fragment
                CutoffFirstFragment cutoffsFragment = new CutoffFirstFragment();
                return cutoffsFragment;
            case 8:
                // placements fragment
                PlaceFirstFragment cutoffsFragment1 = new PlaceFirstFragment();
                return cutoffsFragment1;
            case 10:
                // about us fragment
                AboutUsClgEplore aboutus = new AboutUsClgEplore();
                return aboutus;
            case 9:
                //rankings
                RankingsFragment userrat = RankingsFragment.newInstance("", "", "");
                return  userrat;
            default:
                return null;
        }
    }
/*    private void setToolbarTitle() {
        HomeToolbar.setTitle(activityTitles[navItemIndex]);

    }*/

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }
    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;



                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;


                        break;
                    case R.id.nav_news:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_NEWS;

                        break;
                    case R.id.nav_admission:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_ADMINS;

                        break;
                    case R.id.nav_clgs:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_CLGS;
                        homeDrawerLayout.closeDrawers();
                        UserLoginManager checkuserstatus = new UserLoginManager(getApplicationContext());
                        if(checkuserstatus.isLoggedIn()){
                            Intent i = new Intent(HomeActivity.this,homeMapsActivity.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(HomeActivity.this, R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                            Intent i = new Intent(HomeActivity.this,UserLogin.class);
                            transitionTo(i);
                        }


                        return true;
                    case R.id.nav_exams:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_EXAMS;

                        break;
                    case R.id.nav_fests:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_FESTS;

                        break;
/*                    case R.id.nav_ques_ans:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_QUEST_ANS;
                        homeDrawerLayout.closeDrawers();
                        break;*/
                    case R.id.nav_cutoff_place:
                        navItemIndex = 7;
                        CURRENT_TAG = TAG_CUTOFFS;
                        homeDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_place:
                        navItemIndex = 8;
                        CURRENT_TAG = TAG_PLACEMENTS;
                        homeDrawerLayout.closeDrawers();
                        break;

                    case R.id.nav_rat_rank:
                        navItemIndex = 9;
                        CURRENT_TAG = TAG_RANKINGS;
                        homeDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_share:

                        CURRENT_TAG = TAG_SHARE;
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                "Download our CollegeExplore application at: https://play.google.com/store/apps/details?id=com.collegeexplore.app");
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                        homeDrawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_about:
                        navItemIndex = 10;
                        CURRENT_TAG = TAG_ABOUT;
                        break;
                    case R.id.nav_profileedit:
                        // launch new intent instead of loading fragment


                        homeDrawerLayout.closeDrawers();
                        Intent i1 = new Intent(HomeActivity.this,UserLogin.class);
                        transitionTo(i1);


                        return true;
                    case R.id.nav_notify:
                        // launch new intent instead of loading fragment
                        UserLoginManager checkuserstatus1 = new UserLoginManager(getApplicationContext());
                        if(checkuserstatus1.isLoggedIn()){
                            Intent i = new Intent(HomeActivity.this,UserNotifications.class);
                            transitionTo(i);
                        }
                        else{
                            Toast.makeText(HomeActivity.this, R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                            Intent i = new Intent(HomeActivity.this,UserLogin.class);
                            transitionTo(i);
                        }
                   //   startActivity(new Intent(HomeActivity.this, UserNotifications.class));
                    //    startActivity(new Intent(HomeActivity.this, videoactivity.class));
                      //  startActivity(new Intent(HomeActivity.this, awstestactivity.class));

                        homeDrawerLayout.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);
                UserLoginManager checkuserstatus = new UserLoginManager(getApplicationContext());
                if(checkuserstatus.isLoggedIn()){
                loadHomeFragment();}
                else{
                    Toast.makeText(HomeActivity.this, R.string.login_to_access_all, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(HomeActivity.this,UserLogin.class);
                    transitionTo(i);
                }

                return true;
            }
        });


    }


    public static class CardBackFragment extends Fragment {
        public CardBackFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_card_back, container, false);
        }
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
    void  firebasetoken(HomeActivity homeActivity){
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
    public void dandelogin() {


        if (AccessToken.getCurrentAccessToken() == null) {

        }
        else{
            logincheck=true;
        }
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.

            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {

                    handleSignInResult(googleSignInResult);
                }
            });
        }
        if(logincheck==true){
            Intent i = new Intent(HomeActivity.this,homeMapsActivity.class);



            transitionTo(i);

        }
        else{
            Intent i = new Intent(HomeActivity.this,UserLogin.class);



            transitionTo(i);

        }
    }

    @SuppressWarnings("unchecked") void transitionTo(Intent i) {
        final Pair<View, String>[] pairs;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pairs = TransitionHelper.createSafeTransitionParticipants(this, true);

        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);

            startActivity(i, transitionActivityOptions.toBundle());
        }
        else{
            startActivity(i);
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Transition transition;
int type = 0;
        if (type == 0) {
            transition = buildEnterTransition();
        }  else {
            transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_from_bottom);
        }
        getWindow().setEnterTransition(transition);
    }
    private Visibility buildEnterTransition() {
        Slide enterTransition = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            enterTransition = new Slide();

            enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
            enterTransition.setSlideEdge(Gravity.RIGHT);}
        return enterTransition;
    }

    public class GetVersionTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        View objview;
        private int versionno;


        public GetVersionTask(String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;

        }

        @Override
        protected  void onPreExecute()
        {
            // progresdialoglistview=ProgressDialog.show(getActivity(), "","Loading");
            // Log.e("onPreExecutive","called"+progresdialoglistview);
            //  xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
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
                //   xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else {
                if (result != "") {

                    try {
                        PackageInfo pInfo = HomeActivity.this.getPackageManager().getPackageInfo(getPackageName(), 0);
                        String version = pInfo.versionName;
                        int verCode = pInfo.versionCode;


                    JSONObject obj = null;

                        obj = new JSONObject(ing);

                    int ANDROID_VERSION= (int) obj.get("ANDROID_VERSION");
                   if(ANDROID_VERSION>verCode) {
                    /*   AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                       builder.setMessage("We have Upgraded,Please download latest version for better experience").setPositiveButton("Yes", dialogClickListener)
                               .setNegativeButton("No", dialogClickListener).show();*/

                       AlertDialog.Builder builder;
                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                           builder = new AlertDialog.Builder(HomeActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                       } else {
                           builder = new AlertDialog.Builder(HomeActivity.this);
                       }
                       builder.setTitle("App Update")
                               .setMessage("We have Upgraded,Please download latest version for better experience")
                               .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog, int which) {
                                       String url = "https://play.google.com/store/apps/details?id=com.collegeexplore.app";
                                       Intent i = new Intent(Intent.ACTION_VIEW);
                                       i.setData(Uri.parse(url));
                                       startActivity(i);

                                   }
                               })
                               .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog, int which) {
                                       // do nothing
                                   }
                               })
                               .setIcon(android.R.drawable.ic_dialog_info)
                               .show();
                   }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                }
            }
        }

    private void myUpdateOperation() {


    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };
}
