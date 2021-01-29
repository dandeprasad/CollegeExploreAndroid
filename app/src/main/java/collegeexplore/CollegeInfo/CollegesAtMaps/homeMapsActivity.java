package collegeexplore.CollegeInfo.CollegesAtMaps;

/**
 * Created by DANDE on 18-09-2016.
 */

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;

import collegeexplore.CollegeInfo.CollegesAtMaps.CollegesOnclick.collegesView1;
import collegeexplore.CollegeInfo.DividerItemDecoration;
import collegeexplore.CollegeInfo.LoginManagement.UserLoginManager;
import collegeexplore.CollegeInfo.PermissionUtils;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.SpacesItemDecorHomeActivity;
import collegeexplore.CollegeInfo.Util;
import collegeexplore.CollegeInfo.UtilProperty;

import static collegeexplore.CollegeInfo.CollegesAtMaps.ExpandableListAdapter.filtersSelected;
import static com.google.android.gms.location.LocationSettingsStatusCodes.RESOLUTION_REQUIRED;
import static com.google.android.gms.location.LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE;
import static com.google.android.gms.location.LocationSettingsStatusCodes.SUCCESS;


public class homeMapsActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, HomeMapsActivityAdaptor.OnItemClickListener, NitsNewsNotifications.OnItemClickListener, NitsNewsNotifications1.OnItemClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnInfoWindowLongClickListener, GoogleMap.OnInfoWindowCloseListener, HomeSearchActivityAdaptor.OnItemClickListener, LocationListener, View.OnClickListener {
    private static final Object TAG = "VOICE SEARCH";
    int VOICE_INPUT_REQUEST_CODE = 12345;
    private Menu menu;
    private GoogleMap mMap;
    private UiSettings mUiSettings;
    Fragment myBottomSheet;
    private Properties properties;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
    private TextView textFavorites;
    private TextView textSchedules;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private TextView textMusic;
    HashMap CollegeName = new HashMap();
    HashMap CollegeWebsite = new HashMap();
    HashMap<String, HashMap> collegeDetails = new HashMap();
    String serverUrl;
    String from_Activity;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

    protected static final String TAG1 = "homeMapsActivity";
    HashMap<String, Marker> hashmap = new HashMap();
    String COLLEGE_ID, collegnameFordirection;
    private LocationRequest mLocationRequest;
    // The desired interval for location updates. Inexact. Updates may be more or less frequent.
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    // The fastest rate for active location updates. Exact. Updates will never be more frequent
    // than this value.
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    Location mLastLocation;
    private boolean mLocationPermissionGranted;
    Bundle bundle;

    EditText searchbar;
    private String[] maincontents1;
    HomeMapsActivityAdaptor mAdapter;
    HomeSearchActivityAdaptor mAdaptersearch;
    private ProgressDialog progressDialog;
    GoogleApiClient mGoogleApiClient;
    double mLatitude;
    double mLongitude;
    public static Marker markerFordirection;
    private ImageView colsebutton;
    private static final int SPAN_COUNT = 1;
    View mapView;
    ImageButton searchMic, searchCancel;
    ArrayList navClgid = new ArrayList();
    ArrayList navClgname = new ArrayList();
    private OnInfoWindowElemTouchListener infoButtonListener;
    private ArrayList drawerIcons;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mDrawerList;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView nitnewsRecyclerView;
    private RecyclerView nitnewsRecyclerView1;
    private NitsNewsNotifications nitsNewsNotifications;
    private NitsNewsNotifications1 nitsNewsNotifications1;
    private int type = 0;
    Button infoButton;
    MapWrapperLayout mapWrapperLayout;
    private View mLayout;
    private PopupWindow mPopupWindow;
    private static final int PERMISSION_REQUEST_LOCATION = 0;
    private LinearLayout mRelativeLayout;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    ArrayList<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setTheme(R.style.AppThemeLight);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_maps_layout);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
        mLayout = findViewById(R.id.drawer_layoutmap);
        mRelativeLayout = (LinearLayout) findViewById(R.id.horizontal_dropdown_icon_menu_items);


        //near by click colors end
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupWindowAnimations();
        }
        RelativeLayout dande = (RelativeLayout) findViewById(R.id.relativenearby);
        RelativeLayout dande1 = (RelativeLayout) findViewById(R.id.relativeuseractivity);
        RelativeLayout dande2 = (RelativeLayout) findViewById(R.id.relativefilter);
        //   RelativeLayout dande3 = (RelativeLayout) findViewById(R.id.relativemapenv);


        // Set a click listener for the text view
        dande1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize a new instance of LayoutInflater service



                ImageView nearby = (ImageView) findViewById(R.id.nearby);
                ((ImageView) findViewById(R.id.nearby)).clearColorFilter();
                ((ImageView) findViewById(R.id.filter)).clearColorFilter();
                //      ((ImageView) findViewById(R.id.mapenv)).setImageTintList(myListnotpress);
                ((ImageView) findViewById(R.id.recent)).setColorFilter(getResources().getColor(R.color.bottombar));
                ((TextView) findViewById(R.id.nearbytext)).setTextColor(getResources().getColor(R.color.blackoptac));
                ((TextView) findViewById(R.id.filtertext)).setTextColor(getResources().getColor(R.color.blackoptac));
                //    ( (TextView)findViewById(R.id.mapenvtext)).setTextColor(Color.parseColor("#000000"));
                ((TextView) findViewById(R.id.recenttext)).setTextColor(getResources().getColor(R.color.bottombar));

                //click colors end


                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);

                // Inflate the custom layout/view
                View customView = inflater.inflate(R.layout.useractivity, null);

                mPopupWindow = new PopupWindow(
                        customView,
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );
                LinearLayout layout = (LinearLayout) customView.findViewById(R.id.useract_linear);
                try {
                    File f = new File(getDir("user_colleges", MODE_PRIVATE), "user_colleges_info");
                    ObjectInputStream objectInputStream =
                            new ObjectInputStream(new FileInputStream(f));

                    LinkedHashMap<String, String> object = (LinkedHashMap<String, String>) objectInputStream.readObject();
//etc.
                    // ListIterator<Map.Entry<Integer, String>> iterator = new ArrayList<Map.Entry<Integer, String>>((Collection<? extends Map.Entry<Integer, String>>) object.entrySet()).listIterator(object.size());
                    ListIterator<Map.Entry<String, String>> iterator = new ArrayList<Map.Entry<String, String>>(object.entrySet()).listIterator(object.size());
                    while (iterator.hasPrevious()) {
                        Map.Entry<String, String> entry = iterator.previous();
                        entry.getValue().toString();
                        entry.getKey().toString();

                        Button textimage = new Button(homeMapsActivity.this);
                        textimage.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.recent), null, null, null);

                        textimage.setText(entry.getValue().toString());
                        textimage.setPadding(3, 5, 3, 5);
                        textimage.setTag(entry.getKey().toString());
                        textimage.setAllCaps(false);
                        textimage.setClickable(true);
                        int[] attrs = new int[]{R.attr.selectableItemBackground};
                        TypedArray typedArray = obtainStyledAttributes(attrs);
                        int backgroundResource = typedArray.getResourceId(0, 0);
                        textimage.setBackgroundResource(backgroundResource);
                        typedArray.recycle();
                        textimage.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                // Toast.makeText(homeMapsActivity.this,v.getTag().toString(), Toast.LENGTH_SHORT).show();


                                try {
                                    File f = new File(getDir("user_colleges", MODE_PRIVATE), "user_colleges_info");
                                    ObjectInputStream objectInputStream = null;

                                    objectInputStream = new ObjectInputStream(new FileInputStream(f));


                                    LinkedHashMap<String, String> object = (LinkedHashMap<String, String>) objectInputStream.readObject();

                                    if (object.containsKey(v.getTag().toString())) {
                                        object.remove(v.getTag().toString());


                                    }
                                    Button b = (Button) v;
                                    String buttonText = b.getText().toString();
                                    object.put(v.getTag().toString(), buttonText);
                                    f.delete();

                                    objectInputStream.close();


                                    ObjectOutputStream outputStream = null;
                                    outputStream = new ObjectOutputStream(new FileOutputStream(f));

                                    outputStream.writeObject(object);

                                    outputStream.flush();

                                    outputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }


                                String serverUrl = null;
                                try {
                                    String server = Util.getProperty("name", getApplicationContext());
                                    serverUrl = server.concat("/CollegesStartServlet");

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                // String stringUrl ="http://192.168.0.103:9544/NitStartServlet";
                                //192.168.0.101
                                String FunctionID = "USERSACTIVITY_CLG_REQUEST";
                                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                                if (networkInfo != null && networkInfo.isConnected()) {
                                    new GetMapsDataTask(FunctionID, homeMapsActivity.this, v.getTag().toString()).execute(serverUrl);
                                } else {
                                    // textView.setText("No network connection available.");
                                    Toast.makeText(homeMapsActivity.this, R.string.please_check_internet, Toast.LENGTH_SHORT).show();
                                }
                                mPopupWindow.dismiss();
                            }

                        });

                        // Adds the view to the layout
                        layout.addView(textimage);

                    }

/*                    for (Map.Entry m : object.entrySet()) {

                       // Toast.makeText(getBaseContext(), m.getKey() + " " + m.getValue(), Toast.LENGTH_LONG).show();
                        Button textimage = new Button(homeMapsActivity.this);
                        textimage.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.recent),null,null,null);

                        textimage.setText(m.getValue().toString());
                        textimage.setPadding(3,5,3,5);
                        textimage.setTag(m.getKey().toString());
                        textimage.setAllCaps(false);
                        textimage.setClickable(true);
                        int[] attrs = new int[]{R.attr.selectableItemBackground};
                        TypedArray typedArray = obtainStyledAttributes(attrs);
                        int backgroundResource = typedArray.getResourceId(0, 0);
                        textimage.setBackgroundResource(backgroundResource);
                        typedArray.recycle();
                        textimage.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                Toast.makeText(homeMapsActivity.this,v.getTag().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        // Adds the view to the layout
                        layout.addView(textimage);
                    }*/

                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                /*
                    public PopupWindow (View contentView, int width, int height)
                        Create a new non focusable popup window which can display the contentView.
                        The dimension of the window must be passed to this constructor.

                        The popup does not provide any background. This should be handled by
                        the content view.

                    Parameters
                        contentView : the popup's content
                        width : the popup's width
                        height : the popup's height
                */
                // Initialize a new instance of popup window


                // Set an elevation value for popup window
                // Call requires API level 21
                if (Build.VERSION.SDK_INT >= 21) {
                    mPopupWindow.setElevation(15.0f);
                }

                // Get a reference for the custom view close button
                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

                // Set a click listener for the popup window close button
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        mPopupWindow.dismiss();
                    }
                });

                /*
                    public void showAtLocation (View parent, int gravity, int x, int y)
                        Display the content view in a popup window at the specified location. If the
                        popup window cannot fit on screen, it will be clipped.
                        Learn WindowManager.LayoutParams for more information on how gravity and the x
                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                        to specifying Gravity.LEFT | Gravity.TOP.

                    Parameters
                        parent : a parent view to get the getWindowToken() token from
                        gravity : the gravity which controls the placement of the popup window
                        x : the popup's x location offset
                        y : the popup's y location offset
                */
                // Finally, show the popup window at the center location of root relative layout
                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER, 0, 0);
            }
        });

        //load time red color
        ((ImageView) findViewById(R.id.nearby)).setColorFilter(getResources().getColor(R.color.bottombar));
        ((TextView) findViewById(R.id.nearbytext)).setTextColor(getResources().getColor(R.color.bottombar));
        dande.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                ((ImageView) findViewById(R.id.nearby)).setColorFilter(getResources().getColor(R.color.bottombar));
                ((ImageView) findViewById(R.id.filter)).clearColorFilter();
                //   ((ImageView) findViewById(R.id.mapenv)).setImageTintList(myListnotpress);
                ((ImageView) findViewById(R.id.recent)).clearColorFilter();
                ((TextView) findViewById(R.id.nearbytext)).setTextColor(getResources().getColor(R.color.bottombar));
                ((TextView) findViewById(R.id.filtertext)).setTextColor(getResources().getColor(R.color.blackoptac));
                //  ( (TextView)findViewById(R.id.mapenvtext)).setTextColor(Color.parseColor("#000000"));
                ((TextView) findViewById(R.id.recenttext)).setTextColor(getResources().getColor(R.color.blackoptac));

                //click colors end

                //  nearby.setColorFilter(ContextCompat.getColor(homeMapsActivity.this, R.color.Red), android.graphics.PorterDuff.Mode.MULTIPLY);

                String serverUrl = null;
                try {
                    String server = Util.getProperty("name", getApplicationContext());
                    serverUrl = server.concat("/CurrentLocationClgsServlet");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                String FunctionID = "CURRENT_LOC_CLG_REQ";
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new GetMapsDataCurrentLocTask(FunctionID, homeMapsActivity.this).execute(serverUrl);
                } else {
                    // textView.setText("No network connection available.");
                    Toast.makeText(homeMapsActivity.this, R.string.please_check_internet, Toast.LENGTH_SHORT).show();
                }


            }
        });
 /*       dande3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //click colors start
                int[][] states = new int[][] {
                        new int[] { android.R.attr.state_enabled}, // enabled
                        new int[] {-android.R.attr.state_enabled}, // disabled
                        new int[] {-android.R.attr.state_checked}, // unchecked
                        new int[] { android.R.attr.state_pressed}  // pressed
                };

                int[] colors = new int[] {
                        Color.GREEN,
                        Color.GREEN,
                        Color.GREEN,
                        Color.GREEN
                };
                int[] colors1 = new int[] {
                        R.color.app_theme,
                        R.color.app_theme,
                        R.color.app_theme,
                        R.color.app_theme
                };

                ColorStateList click = new ColorStateList(states, colors);
                ColorStateList myListnotpress = new ColorStateList(states, colors1);

                ImageView nearby=(ImageView)findViewById(R.id.nearby);
                ((ImageView) findViewById(R.id.nearby)).setImageTintList(myListnotpress);
                ((ImageView) findViewById(R.id.filter)).setImageTintList(myListnotpress);
                ((ImageView) findViewById(R.id.mapenv)).setImageTintList(click);
                ((ImageView) findViewById(R.id.recent)).setImageTintList(myListnotpress);
                ( (TextView)findViewById(R.id.nearbytext)).setTextColor(Color.parseColor("#274686"));
                ( (TextView)findViewById(R.id.filtertext)).setTextColor(Color.parseColor("#274686"));
                ( (TextView)findViewById(R.id.mapenvtext)).setTextColor(Color.parseColor("#008000"));
                ( (TextView)findViewById(R.id.recenttext)).setTextColor(Color.parseColor("#274686"));

                //click colors end

                //  nearby.setColorFilter(ContextCompat.getColor(homeMapsActivity.this, R.color.Red), android.graphics.PorterDuff.Mode.MULTIPLY);



                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);

                // Inflate the custom layout/view
                final View customView = inflater.inflate(R.layout.environment_filter,null);

                mPopupWindow = new PopupWindow(
                        customView,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
                mPopupWindow.setOutsideTouchable(true);
                mPopupWindow.setFocusable(true);
                // Set an elevation value for popup window
                // Call requires API level 21
                if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow.setElevation(15.0f);
                }

                // Get a reference for the custom view close button
                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

                // Set a click listener for the popup window close button
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        mPopupWindow.dismiss();
                    }
                });
                Button apply = customView.findViewById(R.id.apply_filter);
                apply.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String serverUrl = null;
                        try {
                            String  server = Util.getProperty("name", getApplicationContext());
                            serverUrl= server.concat("/CollegesStartServlet");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                        String FunctionID = "ENVIRONMENT_FILTER";
                        if (networkInfo != null && networkInfo.isConnected()) {
                         CheckBox val = customView.findViewById(R.id.beaches);
                            CheckBox val2 = customView.findViewById(R.id.hills);
                            CheckBox val3 = customView.findViewById(R.id.temples);
                            CheckBox val4 = customView.findViewById(R.id.snow);

                            JSONArray valtosend = new JSONArray();
                            valtosend.put(val.getText());
                            valtosend.put(val2.getText());
                            valtosend.put(val.getText());
                            valtosend.put(val4.getText());

                            new GetMapsDataTask(FunctionID,valtosend.toString()).execute(serverUrl);
                            mPopupWindow.dismiss();
                        } else {
                            // textView.setText("No network connection available.");
                            Toast.makeText(homeMapsActivity.this, R.string.please_check_internet, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                *//*
                    public void showAtLocation (View parent, int gravity, int x, int y)
                        Display the content view in a popup window at the specified location. If the
                        popup window cannot fit on screen, it will be clipped.
                        Learn WindowManager.LayoutParams for more information on how gravity and the x
                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                        to specifying Gravity.LEFT | Gravity.TOP.

                    Parameters
                        parent : a parent view to get the getWindowToken() token from
                        gravity : the gravity which controls the placement of the popup window
                        x : the popup's x location offset
                        y : the popup's y location offset
                *//*
                // Finally, show the popup window at the center location of root relative layout
                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);
            }
        });*/
        dande2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ImageView nearby = (ImageView) findViewById(R.id.nearby);
                ((ImageView) findViewById(R.id.nearby)).clearColorFilter();
                ((ImageView) findViewById(R.id.filter)).setColorFilter(getResources().getColor(R.color.bottombar));
                //   ((ImageView) findViewById(R.id.mapenv)).setImageTintList(myListnotpress);
                ((ImageView) findViewById(R.id.recent)).clearColorFilter();
                ((TextView) findViewById(R.id.nearbytext)).setTextColor(getResources().getColor(R.color.blackoptac));
                ((TextView) findViewById(R.id.filtertext)).setTextColor(getResources().getColor(R.color.bottombar));
                //   ( (TextView)findViewById(R.id.mapenvtext)).setTextColor(Color.parseColor("#274686"));
                ((TextView) findViewById(R.id.recenttext)).setTextColor(getResources().getColor(R.color.blackoptac));

                //click colors end


                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);

                // Inflate the custom layout/view
                View customView = inflater.inflate(R.layout.expandable_view, null);

                mPopupWindow = new PopupWindow(
                        customView,
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );
                //expandable view start

                // get the listview
                expListView = (ExpandableListView) customView.findViewById(R.id.lvExp);

                // preparing list data
                prepareListData();

                listAdapter = new ExpandableListAdapter(homeMapsActivity.this, listDataHeader, listDataChild);

                // setting list adapter
                expListView.setAdapter(listAdapter);

                // Listview Group click listener
                expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v,
                                                int groupPosition, long id) {
                        // Toast.makeText(getApplicationContext(),
                        // "Group Clicked " + listDataHeader.get(groupPosition),
                        // Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
                // Listview Group expanded listener
                expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        /*Toast.makeText(getApplicationContext(),
                                listDataHeader.get(groupPosition) + " Expanded",
                                Toast.LENGTH_SHORT).show();*/
                    }
                });

                // Listview Group collasped listener
                expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                    @Override
                    public void onGroupCollapse(int groupPosition) {
                      /*  Toast.makeText(getApplicationContext(),
                                listDataHeader.get(groupPosition) + " Collapsed",
                                Toast.LENGTH_SHORT).show();*/

                    }
                });

                // Listview on child click listener
                expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v,
                                                int groupPosition, int childPosition, long id) {
                        // TODO Auto-generated method stub
                      /*  Toast.makeText(
                                getApplicationContext(),
                                listDataHeader.get(groupPosition)
                                        + " : "
                                        + listDataChild.get(
                                        listDataHeader.get(groupPosition)).get(
                                        childPosition), Toast.LENGTH_SHORT)
                                .show();*/
                        return false;
                    }
                });
                //expandable view ends

                // Set an elevation value for popup window
                // Call requires API level 21
                if (Build.VERSION.SDK_INT >= 21) {
                    mPopupWindow.setElevation(15.0f);
                }

                // Get a reference for the custom view close button
                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

                // Set a click listener for the popup window close button
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        mPopupWindow.dismiss();
                    }
                });

                /*
                    public void showAtLocation (View parent, int gravity, int x, int y)
                        Display the content view in a popup window at the specified location. If the
                        popup window cannot fit on screen, it will be clipped.
                        Learn WindowManager.LayoutParams for more information on how gravity and the x
                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                        to specifying Gravity.LEFT | Gravity.TOP.

                    Parameters
                        parent : a parent view to get the getWindowToken() token from
                        gravity : the gravity which controls the placement of the popup window
                        x : the popup's x location offset
                        y : the popup's y location offset
                */
                // Finally, show the popup window at the center location of root relative layout
                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER, 0, 0);

                String serverUrl = null;
                try {
                    String server = Util.getProperty("name", getApplicationContext());
                    serverUrl = server.concat("/CollegesStartServlet");

                } catch (IOException e) {
                    e.printStackTrace();
                }


                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new GetFilterDataTask("CLG_STATE_FILTER", homeMapsActivity.this).execute(serverUrl);
                    new GetFilterDataTask("CLG_CITIES_FILTER", homeMapsActivity.this).execute(serverUrl);
                } else {
                    // textView.setText("No network connection available.");
                    Toast.makeText(homeMapsActivity.this, R.string.please_check_internet, Toast.LENGTH_SHORT).show();
                }
                // String stringUrl ="http://192.168.0.103:9544/NitStartServlet";
                //192.168.0.101
                Button apply = customView.findViewById(R.id.apply_filter);
                apply.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String serverUrl = null;
                        try {
                            String server = Util.getProperty("name", getApplicationContext());
                            serverUrl = server.concat("/CollegesStartServlet");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                        String FunctionID = "USERS_FILTER_REQUEST";
                        if (networkInfo != null && networkInfo.isConnected()) {

                            new GetMapsDataTask(FunctionID, filtersSelected.toString()).execute(serverUrl);
                            mPopupWindow.dismiss();
                        } else {
                            // textView.setText("No network connection available.");
                            Toast.makeText(homeMapsActivity.this, R.string.please_check_internet, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        dande.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });
        dande1.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });
        dande2.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });
 /*       dande3.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });*/
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.maps_ToolBar);
        setSupportActionBar(myToolbar);
        // getSupportActionBar().setTitle("Colleges");
        //  myToolbar.setBackgroundColor(Color.parseColor("#E6ffffff"));
        // Get a support ActionBar corresponding to this toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Enable the Up but     ton
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View v123 = getCurrentFocus();
                if (v123 instanceof EditText) {
                    View view = homeMapsActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    searchbar.setText("");
                    searchCancel.setVisibility(View.GONE);
                    searchMic.setVisibility(View.VISIBLE);
                    CardView view1 = (CardView) findViewById(R.id.maps_recycle_cardview1);
                    view1.setVisibility(View.GONE);
                    searchbar.clearFocus();
                    int color = R.color.transparent;
                    FrameLayout dande = (FrameLayout) findViewById(R.id.map_framelayout);
               /* com.roughike.bottombar.BottomBar bottomBar = (   com.roughike.bottombar.BottomBar) findViewById(R.id.bottomBar);
                bottomBar.setForeground(new ColorDrawable(ContextCompat.getColor(homeMapsActivity.this, color)));*/
                    dande.setForeground(new ColorDrawable(ContextCompat.getColor(homeMapsActivity.this, color)));

                } else {
                    onBackPressed();
                }
            }

        });


        Integer[] mThumbIds = {
                R.drawable.nits_drawer,


        };
/*         bottomBar = (BottomBar) findViewById(R.id.bottomBar);

     //   bottomBar.selectTabAtPosition(5,true);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                if (tabId == R.id.map_environment) {
                    getDirectionsEnvUrl(markerFordirection.getPosition());
                }}});*/


        drawerIcons = new ArrayList();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layoutmap);
        mDrawerList = (RecyclerView) findViewById(R.id.left_draweridmap);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        //mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        mDrawerList.setLayoutManager(mLayoutManager);

        mDrawerList.setHasFixedSize(true);

        mAdapter = new HomeMapsActivityAdaptor(navClgname, mThumbIds, this);
        mDrawerList.setAdapter(mAdapter);
        //mDrawerList.setAnimation();
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        // mDrawerList.addItemDecoration(new SpacesItemDecorHomeActivity(spacingInPixels));
        mDrawerList.addItemDecoration(new DividerItemDecoration(this, R.drawable.rounded_corner1));
        //mDrawerList.addItemDecoration(new android.support.v7.widget.DividerItemDecoration(this,1));
        final TextView collegeListvalues = (TextView) findViewById(R.id.College_list);
        collegeListvalues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layoutmap);
                mDrawerLayout.openDrawer(Gravity.RIGHT);

            }
        });


        searchMic = (ImageButton) findViewById(R.id.iv_search_mic);
        searchCancel = (ImageButton) findViewById(R.id.iv_search_cancel);

        searchbar = (EditText) findViewById(R.id.ed_home_searchbar);
        searchbar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String data = searchbar.getText().toString();
                    String serverUrl = null;
                    try {
                        String server = Util.getProperty("name", getApplicationContext());
                        serverUrl = server.concat("/CollegesStartServlet");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // String stringUrl ="http://192.168.0.103:9544/NitStartServlet";
                    //192.168.0.101
                    String FunctionID = "CLG_SEARCH_REQUEST";
                    if (!(data.equalsIgnoreCase(""))) {
                        searchMic.setVisibility(View.GONE);
                        searchCancel.setVisibility(View.VISIBLE);
                        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {

                            new GetSearchData(FunctionID, homeMapsActivity.this, data).execute(serverUrl);

                        } else {
                            // textView.setText("No network connection available.");
                            Toast.makeText(homeMapsActivity.this, R.string.please_check_internet, Toast.LENGTH_SHORT).show();
                        }
                        // TODO Auto-generated method stub
                    }
                    return true;
                }
                return false;
            }
        });
        searchbar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean hasfocus) {
                if (hasfocus) {
                    CardView view = (CardView) findViewById(R.id.maps_recycle_cardview1);
                    view.setVisibility(View.VISIBLE);
                    //check later for background color
                    int color = R.color.black_trans_60;
                    FrameLayout dande = (FrameLayout) findViewById(R.id.map_framelayout);
                   /* com.roughike.bottombar.BottomBar bottomBar = (   com.roughike.bottombar.BottomBar) findViewById(R.id.bottomBar);
                    bottomBar.setForeground(new ColorDrawable(ContextCompat.getColor(homeMapsActivity.this, color)));*/
                    dande.setForeground(new ColorDrawable(ContextCompat.getColor(homeMapsActivity.this, color)));
                    //   dande.setForeground();Color(Color.parseColor("#000000"));
                } else {
                    CardView view = (CardView) findViewById(R.id.maps_recycle_cardview1);
                    view.setVisibility(View.GONE);
                }
            }
        });
        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

         /*       CardView view= (CardView) findViewById(R.id.maps_recycle_cardview1);
             view.setVisibility(View.VISIBLE);*/
                String data = searchbar.getText().toString();
                String serverUrl = null;
                try {
                    String server = Util.getProperty("name", getApplicationContext());
                    serverUrl = server.concat("/CollegesStartServlet");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                // String stringUrl ="http://192.168.0.103:9544/NitStartServlet";
                //192.168.0.101
                String FunctionID = "CLG_SEARCH_REQUEST";
                if (!(data.equalsIgnoreCase(""))) {
                    searchMic.setVisibility(View.GONE);
                    searchCancel.setVisibility(View.VISIBLE);
                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        new GetSearchData(FunctionID, homeMapsActivity.this, data).execute(serverUrl);

                    } else {
                        // textView.setText("No network connection available.");
                        Toast.makeText(homeMapsActivity.this, R.string.please_check_internet, Toast.LENGTH_SHORT).show();
                    }
                    // TODO Auto-generated method stub
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });
        searchMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // OnSwap.getInstance().trackEvent(TAG, "searchMic", "searchMic Clicked");
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Search Colleges");
                startActivityForResult(intent, VOICE_INPUT_REQUEST_CODE);
            }
        });
        searchCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if no view has focus:


                searchbar.setText("");

            }
        });


        // Getting the current location for directions
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */,
                            this /* OnConnectionFailedListener */)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .build();
        }
        createLocationRequest();
        mGoogleApiClient.connect();

//getting the reqest from which activity
        bundle = getIntent().getExtras();
        bundle = null;
        if (bundle != null) {
            from_Activity = bundle.getString("FROM_ACTIVITY");

            if (from_Activity.equalsIgnoreCase("SEARCH_ACTIVITY") && from_Activity != null) {
                // String COLLEGE_NAME =bundle.getString("collegeName");
                COLLEGE_ID = bundle.getString("collegeID");
            }
        }
        // textFavorites = (TextView) findViewById(R.id.text_favorites);
        //  textSchedules = (TextView) findViewById(R.id.text_schedules);
        //  textMusic = (TextView) findViewById(R.id.text_music);
     /*   BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        return false;
                    }
                });*/
     /*   Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myBottomSheet.show(getSupportFragmentManager(), myBottomSheet.getTag());
            }
        });*/


//serach button action
      /*  final TextView serachRef = (TextView) findViewById(R.id.search_home);
        final ImageView action_searchRef = (ImageView) findViewById(R.id.action_search);

        action_searchRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeMapsActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
        serachRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeMapsActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });*/
/*
        colsebutton = (ImageView) findViewById(R.id.backbutMaps);

        colsebutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                new Handler().post(new Runnable() {
                public void run() {
                    onBackPressed();
            };
                });
            }});
*/


        String[] mThumbIds1 = {
                getString(R.string.toprated),
                getString(R.string.nits_space), getString(R.string.iits_space),
                getString(R.string.deemed_univ_space),
                getString(R.string.iiits_space),
                getString(R.string.iim_space),


        };
        //GridLayoutManager1 = new GridLayoutManager(getApplicationContext(), SPAN_COUNT);
        maincontents1 = new String[mThumbIds1.length];
        maincontents1[0] = "UNIQ_UPMAPDATA";
        LinearLayoutManager x = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        //.setActiveUserPosition(position);
        nitnewsRecyclerView = (RecyclerView) findViewById(R.id.recyclenitNewsNotify);
        nitnewsRecyclerView.setLayoutManager(x);
        nitnewsRecyclerView.setNestedScrollingEnabled(false);
        nitsNewsNotifications = new NitsNewsNotifications(maincontents1, mThumbIds1, this);
        nitnewsRecyclerView.setAdapter(nitsNewsNotifications);
        int spacingInPixels1 = getApplicationContext().getResources().getDimensionPixelSize(R.dimen.spacing4);
        nitnewsRecyclerView.addItemDecoration(new SpacesItemDecorHomeActivity(spacingInPixels1));
        x.setSmoothScrollbarEnabled(true);


        LinearLayoutManager x1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        nitnewsRecyclerView1 = (RecyclerView) findViewById(R.id.recyclenitNewsNotify1);
        nitnewsRecyclerView1.setLayoutManager(x1);
        mAdaptersearch = new HomeSearchActivityAdaptor(navClgname, mThumbIds, this);
        nitnewsRecyclerView1.setAdapter(mAdaptersearch);

        nitnewsRecyclerView1.addItemDecoration(new SpacesItemDecorHomeActivity(5));
        nitnewsRecyclerView1.addItemDecoration(new DividerItemDecoration(this, R.drawable.rounded_corner12));

        // Get a support ActionBar corresponding to this toolbar
        // ActionBar ab = getSupportActionBar();

        // Enable the Up but     ton
        //   ab.setDisplayHomeAsUpEnabled(true);

        /*GoogleMapOptions options = new GoogleMapOptions();

        options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(true)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false);  */


//for lodating the maps async
        // SupportMapFragment.newInstance();

    }

    /*
 * Preparing the list data
 */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("States");
        listDataHeader.add("Cities");
        //listDataHeader.add("Departments");
        listDataHeader.add("More will be added soon");

        // Adding child data
        List<String> States = new ArrayList<String>();


        List<String> Cities = new ArrayList<String>();
        List<String> Departments = new ArrayList<String>();
        Departments.add("CSE");
        Departments.add("ECE");
        Departments.add("EEE");
        Departments.add("MECH");
        Departments.add("CIVIL");
        List<String> More = new ArrayList<String>();


        listDataChild.put(listDataHeader.get(0), States); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Cities);
       // listDataChild.put(listDataHeader.get(2), Departments);
        listDataChild.put(listDataHeader.get(2), More);
    }
/*    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }*/

    /**
     * Sets up the location request.
     */
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        /*
         * Sets the desired interval for active location updates. This interval is
         * inexact. You may not receive updates at all if no location sources are available, or
         * you may receive them slower than requested. You may also receive updates faster than
         * requested if other applications are requesting location at a faster interval.
         */
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        /*
         * Sets the fastest rate for active location updates. This interval is exact, and your
         * application will never receive updates faster than this value.
         */
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:

                animateFAB();
                CardView xyz = (CardView) findViewById(R.id.cardfordirections);
                xyz.setVisibility(View.GONE);
                CardView xyz1 = (CardView) findViewById(R.id.cardforphnumber);
                xyz1.setVisibility(View.GONE);
                break;
            case R.id.fab1:
                //
                LatLng point = markerFordirection.getPosition();
                LatLng userloc = new LatLng(mLatitude, mLongitude);
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(markerFordirection.getPosition());
                builder.include(userloc);
                LatLngBounds bounds = builder.build();


                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
                Button dandeqq = (Button) findViewById(R.id.google_navigate);
                dandeqq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?saddr=" + mLatitude + "," + mLongitude + "&daddr=" + markerFordirection.getPosition().latitude + "," + markerFordirection.getPosition().longitude));
                        startActivity(intent);
                    }
                });
                // CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 10);
                String url = getDirectionsUrl(userloc, point);
                DownloadTask downloadTask = new DownloadTask();

                // Start downloading json data from Google Directions API
                downloadTask.execute(url);

                break;
            case R.id.fab2:
                String markerId = (String) markerFordirection.getTag();
                HashMap<String, String> values = collegeDetails.get(markerId);
                TextView cardforphnumbertxt = (TextView) findViewById(R.id.cardforphnumbertxt);
                cardforphnumbertxt.setText(values.get("CLG_NUM"));
                CardView xyz2 = (CardView) findViewById(R.id.cardforphnumber);
                xyz2.setVisibility(View.VISIBLE);
                cardforphnumbertxt.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View arg10) {
                        HashMap<String, String> values = collegeDetails.get((String) markerFordirection.getTag());
if(!(values.get("CLG_NUM").equalsIgnoreCase("")||values.get("CLG_NUM").equalsIgnoreCase("-"))) {
    Intent callIntent = new Intent(Intent.ACTION_DIAL);
    callIntent.setData(Uri.parse("tel:" + values.get("CLG_NUM").trim().toString()));

    startActivity(callIntent);
}}});
                break;
        }
    }

    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;


        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;


        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VOICE_INPUT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> matches = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                int matchSize = matches.size();
                for (int index = 0; index < matchSize; index++) {
                    Log.i((String) TAG, String.valueOf(index) + ": " + matches.get(index));
                    if (index == 0) {
                        searchbar.setText(matches.get(index));

                        searchMic.setVisibility(View.GONE);
                    }
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i((String) TAG, status.getStatusMessage());
                //   Snackbar.make(locationTextView, status.getStatusMessage(), Snackbar.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {
                //  Snackbar.make(locationTextView, "Canceled", Snackbar.LENGTH_LONG).show();
            }
        }
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG1, "User agreed to make required location settings changes.");
                        showLocationOnlyForSettings();
                        UpdateUserLocationUI();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG1, "User chose not to make required location settings changes.");
                        finish();
                        break;
                }
                break;

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mUiSettings = mMap.getUiSettings();

        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMapToolbarEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnInfoWindowLongClickListener(this);
        mMap.setOnInfoWindowCloseListener(this);
        mMap.setBuildingsEnabled(true);
        mMap.setOnMyLocationButtonClickListener( this);
        //enableMyLocation();
        //  Record.put( "FUNCTION_ID", "ALLNITS_REQUEST");
        // String stringUrl ="http://10.0.2.2:9544/NitStartServlet";
        // String stringUrl ="http://192.168.43.60:9544/NitStartServlet";

        //position of my location button
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 350);
        }
        LatLng IndiaCenterPoint = new LatLng(27.8913, 78.0792);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(IndiaCenterPoint));
        moveStartLocation(IndiaCenterPoint);
        // Setting a custom info window adapter for the google map
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoContents(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoWindow(Marker arg0) {


                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.windowlayout, null);
                v.setTranslationZ(12);
                v.setElevation(7);
                String markerId = (String) arg0.getTag();
                HashMap<String, String> values = collegeDetails.get(markerId);
                //  String clgName = (String)CollegeName.get(markerId);
                //String clgWeb = (String)CollegeWebsite.get(markerId);
                // Getting the position from the marker
                if((markerId.equalsIgnoreCase("DEVELPER_TAG"))) {
                    TextView val1 = v.findViewById(R.id.clg_name);
                    val1.setText(R.string.dev_map);
                    TextView val121 = v.findViewById(R.id.websitetext);
                    val121.setVisibility(View.GONE);
                    TextView val121s = v.findViewById(R.id.clg_placeinfo);
                    val121s.setVisibility(View.GONE);

                }
                else{
                    String imageserver = null;
                    try {
                        imageserver = UtilProperty.getProperty("img_homeMapsActivity", homeMapsActivity.this, "imagepath.properties");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    // Getting reference to the TextView to set latitude
                    TextView val1 = v.findViewById(R.id.clg_name);
                    TextView val2 = v.findViewById(R.id.clg_website);
                    TextView val3 = v.findViewById(R.id.clg_place);
                    ImageView val4 = v.findViewById(R.id.clg_logo);

                    TextView val121 = v.findViewById(R.id.websitetext);
                    val121.setVisibility(View.VISIBLE);
                    TextView val121s = v.findViewById(R.id.clg_placeinfo);
                    val121s.setVisibility(View.VISIBLE);
                    Picasso.with(homeMapsActivity.this).load(values.get("COLLEGE_LOGO")).into(val4);
                    val1.setText(values.get("COLLEGE_NAME"));
                    val2.setText(values.get("COLLEGE_WEBSITE"));
                    val3.setText(values.get("COLLEGE_PLACE"));
                }
                //  Picasso.with(homeMapsActivity.this).load("http://192.168.43.60:9544/CollegeGuideWorkSpace/WebUserRegistration/news0.jpg").into( ((NewsViewHolder) holder).newsimgtagy);


                // Returning the view containing InfoWindow contents
                return v;
            }
        });
        // Developer location
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        boolean success = mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.style_json));


        // Setting a custom info window adapter for the google map
      /*  googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(final Marker arg0) {

                // Getting view from the layout file info_window_layout
                ViewGroup v =(ViewGroup) getLayoutInflater().inflate(R.layout.info_window_layout, null);

               String markerId = (String) arg0.getTag();
                String clgName = (String)CollegeName.get(markerId);
                String clgWeb = (String)CollegeWebsite.get(markerId);
                // Getting the position from the marker
                LatLng latLng = arg0.getPosition();

                // Getting reference to the TextView to set latitude
                TextView tvLat = (TextView) v.findViewById(R.id.College_name_infowd);

                // Getting reference to the TextView to set longitude
                TextView tvLng = (TextView) v.findViewById(R.id.college_website_infowd);

                // Setting the latitude
                tvLat.setText(clgName);

                // Setting the longitude
                tvLng.setText(clgWeb);

                infoButton = (Button) v.findViewById(R.id.DirectionsInfo);
                //this.infoButton = (Button)infoWindow.findViewById(R.id.button);

                infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,
                        getResources().getDrawable(R.drawable.round_but_green_sel), //btn_default_normal_holo_light
                        getResources().getDrawable(R.drawable.round_but_red_sel)) //btn_default_pressed_holo_light
                {
                    @Override
                    protected void onClickConfirmed(View v, Marker marker) {
                        // Here we can perform some action triggered after clicking the button
                        Toast.makeText(homeMapsActivity.this, marker.getTitle() + "'s button clicked!", Toast.LENGTH_SHORT).show();

                        LatLng point=arg0.getPosition();
                        LatLng userloc= new LatLng(mLatitude,mLongitude);
                        String url = getDirectionsUrl(userloc, point);
                        DownloadTask downloadTask = new DownloadTask();

                        // Start downloading json data from Google Directions API
                        downloadTask.execute(url);

                    }

                };
                infoButton.setOnTouchListener(infoButtonListener);




                //request for directions
                Button btnSubmitlogin = (Button) v.findViewById(R.id.DirectionsInfo);
                btnSubmitlogin.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View arg10) {


                        LatLng point=arg0.getPosition();
                        LatLng userloc= new LatLng(mLatitude,mLongitude);
                        String url = getDirectionsUrl(userloc, point);
                        DownloadTask downloadTask = new DownloadTask();

                        // Start downloading json data from Google Directions API
                        downloadTask.execute(url);


                    }
                });

                // Returning the view containing InfoWindow contents
                return v;

            }

        });*/
        UpdateUserLocationUI();
    }

    //current locatrion of directions
    protected void onStart() {

        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, R.string.getting_your_loc, Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    /*    @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                               @NonNull int[] grantResults) {
            if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
                return;
            }

            if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Enable the my location layer if the permission has been granted.
                enableMyLocation();
            } else {
                // Display the missing permission error dialog when the fragments resume.
                mPermissionDenied = true;
            }
        }*/

    //result on click of permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                Snackbar  snackbar= Snackbar.make(mLayout, R.string.locattion_grant,
                        Snackbar.LENGTH_SHORT);

                View sbView = snackbar.getView();
                sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor( Color.parseColor("#000000"));
                snackbar.show();
                if (mMap != null) {
                    // Access to the location has been granted to the app.
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                    showLocationOnlyForSettings();
                // mMap.addCircle();

            }
             mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
                mLocationPermissionGranted = true;
                UpdateUserLocationUI();
        } else {
            // Permission request was denied.
                Snackbar   snackbar=Snackbar.make(mLayout, R.string.location_denied,
                    Snackbar.LENGTH_SHORT);

                View sbView = snackbar.getView();
                sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor( Color.parseColor("#000000"));
                snackbar.show();
            finish();
        }
    }
    // END_INCLUDE(onRequestPermissionsResult)
}
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    /*    getMenuInflater().inflate(R.menu.menu_googlemaps_workspace, menu);
        this.menu = menu;*/
        // Retrieve the SearchView and plug it into SearchManager
      /*  final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));*/
        return true;
    }
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view


        //  boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        // menu.findItem(R.id.student_login).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    //menu bar
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.

        // Handle action buttons

/*        switch (item.getItemId()) {
            case R.id.action_search_map:
                Intent intent = new Intent(this,
                        SearchActivity.class);
                startActivity(intent);

                return true;

        }*/
/*        switch (item.getItemId()) {
            case R.id.action_search_map:
                Intent intent = new Intent(this,
                        SearchActivity.class);
                startActivity(intent);

                return true;

        }
        switch (item.getItemId()) {
            case R.id.MapsRecycleDrawer:

                CardView view= (CardView) findViewById(R.id.maps_recycle_cardview);
                if( view.getVisibility()==View.VISIBLE)
                {view.setVisibility(View.INVISIBLE);
                    menu.getItem(1).setIcon(R.drawable.ic_drawer);
                }
                else{
                    view.setVisibility(View.VISIBLE);
                    menu.getItem(1).setIcon(R.drawable.cancel);
                }
                return true;

        }*/
        return super.onOptionsItemSelected(item);
    }

    //marker click event
    public boolean onMarkerClick(final Marker marker) {



        // Retrieve the data from the marker.
        String clickItem = (String) marker.getTag();
        if (clickItem.equalsIgnoreCase("DEVELPER_TAG"))
        {
            moveToCurrentLocation( marker.getPosition() );
            marker.showInfoWindow();

        }
        else {
            collegnameFordirection = (String) CollegeName.get(clickItem);
            markerFordirection = marker;

            moveToCurrentLocation(marker.getPosition());
           // animateMarkerNew(new LatLng(mLatitude, mLongitude),marker.getPosition(),markerFordirection);
            marker.showInfoWindow();

            fab.setVisibility(View.VISIBLE);


            fab.callOnClick();
           // https://maps.googleapis.com/maps/api/place/radarsearch/json?location=51.503186,-0.126446&radius=5000&type=museum&key=YOUR_API_KEY
            //commented for future purpose
/*            if (mLocationPermissionGranted) {
                // Get the businesses and other points of interest located
                // nearest to the device's current location.
                @SuppressWarnings("MissingPermission")
                PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                        .getCurrentPlace(mGoogleApiClient, null);
                result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                    @Override
                    public void onResult(@NonNull PlaceLikelihoodBuffer likelyPlaces) {
                        for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                            // Add a marker for each place near the device's current location, with an
                            // info window showing place information.
                            String attributions = (String) placeLikelihood.getPlace().getAttributions();
                            String snippet = (String) placeLikelihood.getPlace().getAddress();
                            if (attributions != null) {
                                snippet = snippet + "\n" + attributions;
                            }

                            mMap.addMarker(new MarkerOptions()
                                    .position(placeLikelihood.getPlace().getLatLng())
                                    .title((String) placeLikelihood.getPlace().getName())
                                    .snippet(snippet));
                        }
                        // Release the place likelihood buffer.
                        likelyPlaces.release();
                    }
                });
            }*/

        }
        // LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        // Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        // double longitude = location.getLongitude();
        // double latitude = location.getLatitude();


        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed / duration), 0);
                marker.setAnchor(0.5f, 1.0f + 2 * t);

                if (t > 0.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });






        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }



    private void moveToCurrentLocation(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 1000, null);


    }
    private void moveStartLocation(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,8));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(3), 100000, null);


    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }
    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String alternatives="alternatives=true";
        String modes="driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor+"&"+alternatives;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }
    private String getDirectionsEnvUrl(LatLng userloc) {

        // Origin of route
        String str_origin = "origin=" + userloc.latitude + "," + userloc.longitude;

      //  https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=13.0913064,80.2900357&radius=500&type=movie_theater&key=AIzaSyBO64WLDcn516QY1VhOQzMTWjLTYPOQ85c
        // Sensor enabled
        String radius = "radius=500";
        String type="type="+"movie_theater";
        String keytopass="key=AIzaSyBO64WLDcn516QY1VhOQzMTWjLTYPOQ85c";

        // Building the parameters to the web service
        String parameters = radius + "&" + type + "&" + keytopass;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/" + output + "?" + parameters;

        return url;
    }
    private void showLocationPreview() {
        // BEGIN_INCLUDE(startCamera)
        // Check if the Camera permission has been granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            // Permission is already available, start camera preview
          Snackbar snackbar =   Snackbar.make(mLayout,
                    R.string.location_perm_avail,
                    Snackbar.LENGTH_SHORT);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor( Color.parseColor("#000000"));
            snackbar.show();
            if (mMap != null) {
                // Access to the location has been granted to the app.
                mMap.setMyLocationEnabled(true);
                // mMap.addCircle();

            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

            //startCamera();
        } else {
            // Permission is missing and must be requested.
            requestCameraPermission();
        }
        // END_INCLUDE(startCamera)
    }

    private void showLocationOnlyForSettings() {
        // BEGIN_INCLUDE(startCamera)
        // Check if the Camera permission has been granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            // Permission is already available, start camera preview
/*            Snackbar snackbar =   Snackbar.make(mLayout,
                    "Location permission is available.",
                    Snackbar.LENGTH_SHORT);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor( Color.parseColor("#274686"));
            snackbar.show();*/
            if (mMap != null) {
                // Access to the location has been granted to the app.
                mMap.setMyLocationEnabled(true);
                // mMap.addCircle();

            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            Toast.makeText(this, R.string.fetching_location, Toast.LENGTH_SHORT).show();
while(mLastLocation==null){
   // Toast.makeText(this, "Fetching your Location....", Toast.LENGTH_SHORT).show();
    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
            mGoogleApiClient);
}
            //startCamera();
        } else {
            // Permission is missing and must be requested.
            requestCameraPermission();
        }
        // END_INCLUDE(startCamera)
    }
    //updateUser location
    private void UpdateUserLocationUI(){
        if (mMap == null) {
            return;
        }

        if (mLocationPermissionGranted) {
        if (mLastLocation != null) {
            mLatitude=mLastLocation.getLatitude();
            mLongitude= mLastLocation.getLongitude();
            Geocoder gCoder = new Geocoder(this);
            List<android.location.Address> addresses = null;
            try {
                addresses = gCoder.getFromLocation(mLatitude, mLongitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses != null && addresses.size() > 0) {
                Toast.makeText(this, getString(R.string.at) + addresses.get(0).getAddressLine(0), Toast.LENGTH_LONG).show();

                UserLoginManager add = new UserLoginManager(this);
                add.Useraddress(addresses.get(0).getAddressLine(0));
            }
         /*   Circle circle = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(mLatitude, mLongitude))
                    .radius(10000)
                    .fillColor(Color.BLUE));*/
            moveToCurrentLocation(new LatLng(mLatitude, mLongitude));
            String serverUrl = null;
            try {
                String  server = Util.getProperty("name", getApplicationContext());
                serverUrl= server.concat("/CurrentLocationClgsServlet");

            } catch (IOException e) {
                e.printStackTrace();
            }
            String FunctionID = "CURRENT_LOC_CLG_REQ";
            ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new GetMapsDataCurrentLocTask(FunctionID,this).execute(serverUrl);
            } else {
                // textView.setText("No network connection available.");
                Toast.makeText(homeMapsActivity.this, R.string.please_check_internet, Toast.LENGTH_SHORT).show();
            }

        }}
    }
    /**
     * Requests the {@link android.Manifest.permission#CAMERA} permission.
     * If an additional rationale should be displayed, the user has to launch the request from
     * a SnackBar that includes additional information.
     */
    private void requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.



                    // Request the permission
                    ActivityCompat.requestPermissions(homeMapsActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSION_REQUEST_LOCATION);


        } else {
            Snackbar snackbar = Snackbar.make(mLayout,
                    R.string.permission_not_req,
                    Snackbar.LENGTH_SHORT);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor( Color.parseColor("#000000"));
            snackbar.show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_LOCATION);
        }
    }
    private void startCamera() {
       /* Intent intent = new Intent(this, CameraPreviewActivity.class);
        startActivity(intent);*/
    }
    private void displayLocationSettingsRequest(Context context) {


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case SUCCESS:
                        Log.i(TAG1, "All location settings are satisfied.");
                       // showLocationPreview();
                        showLocationOnlyForSettings();
                        UpdateUserLocationUI();
                        break;
                    case RESOLUTION_REQUIRED:
                        Log.i(TAG1, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(homeMapsActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG1, "PendingIntent unable to execute request.");
                        }
                        break;
                    case SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG1, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        displayLocationSettingsRequest(homeMapsActivity.this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //  mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);
        //positon of my location button
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);
/*        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            // mMap.addCircle();

        }*/
/*        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);*/

    }
    @Override
    public void onConnectionSuspended(int i) {

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setupWindowAnimations() {
        Transition transition;

        if (type == 0) {
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
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View view, int position) {
        selectItem(position);
    }
    private void selectItem(int position) {
        String Clgidsidedebar = (String)navClgid.get(position);
        onMarkerClick(hashmap.get(Clgidsidedebar));
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layoutmap);
        //  mDrawerLayout.openDrawer(mDrawerLayout);
        mDrawerLayout.closeDrawer(Gravity.RIGHT);
        CardView view1 = (CardView) findViewById(R.id.maps_recycle_cardview1);
        view1.setVisibility(View.GONE);

        View view = homeMapsActivity.this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        searchCancel.setVisibility(View.GONE);
        searchMic.setVisibility(View.VISIBLE);

        int color = R.color.transparent;

        FrameLayout dande = (FrameLayout) findViewById(R.id.map_framelayout);
    /*    com.roughike.bottombar.BottomBar bottomBar = (   com.roughike.bottombar.BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setForeground(new ColorDrawable(ContextCompat.getColor(homeMapsActivity.this, color)));*/
        dande.setForeground(new ColorDrawable(ContextCompat.getColor(homeMapsActivity.this, color)));
        searchbar.clearFocus();
    }

    @Override
    public void onClick(View view, int position, String UniqueKey) {

        if(position==0) {
            String serverUrl = null;
            try {
                String server = Util.getProperty("name", getApplicationContext());
                serverUrl = server.concat("/CurrentLocationClgsServlet");

            } catch (IOException e) {
                e.printStackTrace();
            }
            String FunctionID = "CURRENT_LOC_CLG_REQ";
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new GetMapsDataCurrentLocTask(FunctionID, this).execute(serverUrl);
            } else {
                // textView.setText("No network connection available.");
                Toast.makeText(homeMapsActivity.this, R.string.please_check_internet, Toast.LENGTH_SHORT).show();
            }

        }
        if(position==1){
            String serverUrl = null;
            try {
                String  server = Util.getProperty("name", getApplicationContext());
                serverUrl= server.concat("/CollegesStartServlet");

            } catch (IOException e) {
                e.printStackTrace();
            }
            // String stringUrl ="http://192.168.0.103:9544/NitStartServlet";
            //192.168.0.101
            String FunctionID = "ALLNITS_REQUEST";
            ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new GetMapsDataTask(FunctionID,this).execute(serverUrl);
            } else {
                // textView.setText("No network connection available.");
                Toast.makeText(homeMapsActivity.this, R.string.please_check_internet, Toast.LENGTH_SHORT).show();
            }
        }
        if(position==2){
            String serverUrl = null;
            try {
                String  server = Util.getProperty("name", getApplicationContext());
                serverUrl= server.concat("/CollegesStartServlet");

            } catch (IOException e) {
                e.printStackTrace();
            }
            // String stringUrl ="http://192.168.0.103:9544/NitStartServlet";
            //192.168.0.101
            String FunctionID = "ALLIITS_REQUEST";
            ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new GetMapsDataTask(FunctionID,this).execute(serverUrl);
            } else {
                // textView.setText("No network connection available.");
                Toast.makeText(homeMapsActivity.this, R.string.please_check_internet, Toast.LENGTH_SHORT).show();
            }
        }
        if(position==4){
            String serverUrl = null;
            try {
                String  server = Util.getProperty("name", getApplicationContext());
                serverUrl= server.concat("/CollegesStartServlet");

            } catch (IOException e) {
                e.printStackTrace();
            }
            // String stringUrl ="http://192.168.0.103:9544/NitStartServlet";
            //192.168.0.101
            String FunctionID = "ALLIIITS_REQUEST";
            ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new GetMapsDataTask(FunctionID,this).execute(serverUrl);
            } else {
                // textView.setText("No network connection available.");
                Toast.makeText(homeMapsActivity.this, R.string.please_check_internet, Toast.LENGTH_SHORT).show();
            }
        }
        if(position==3){
            String serverUrl = null;
            try {
                String  server = Util.getProperty("name", getApplicationContext());
                serverUrl= server.concat("/CollegesStartServlet");

            } catch (IOException e) {
                e.printStackTrace();
            }
            // String stringUrl ="http://192.168.0.103:9544/NitStartServlet";
            //192.168.0.101
            String FunctionID = "ALL_DEEMED_REQUEST";
            ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new GetMapsDataTask(FunctionID,this).execute(serverUrl);
            } else {
                // textView.setText("No network connection available.");
                Toast.makeText(homeMapsActivity.this, R.string.please_check_internet, Toast.LENGTH_SHORT).show();
            }
        }
        if(position==5){
            String serverUrl = null;
            try {
                String  server = Util.getProperty("name", getApplicationContext());
                serverUrl= server.concat("/CollegesStartServlet");

            } catch (IOException e) {
                e.printStackTrace();
            }
            // String stringUrl ="http://192.168.0.103:9544/NitStartServlet";
            //192.168.0.101
            String FunctionID = "ALL_IIMS_REQUEST";
            ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new GetMapsDataTask(FunctionID,this).execute(serverUrl);
            } else {
                // textView.setText("No network connection available.");
                Toast.makeText(homeMapsActivity.this, R.string.please_check_internet, Toast.LENGTH_SHORT).show();
            }
        }

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {

        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
        CardView view = (CardView) findViewById(R.id.maps_recycle_cardview1);
        view.setVisibility(View.GONE);


    }
/*    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }*/
    @Override
    public void onInfoWindowClick(Marker marker) {

        String clickItem = (String) marker.getTag();
        if(clickItem.equalsIgnoreCase("DEVELPER_TAG")){
            Toast.makeText(this, "Team CollegeExplore", Toast.LENGTH_LONG).show();
        }
        else{
        // Check if a click count was set, then display the click count.
        if (clickItem != null) {




            File f1=new File(getDir("user_colleges", MODE_PRIVATE), "user_colleges_info");

            if(!f1.exists()) {
                LinkedHashMap<String,String> hm=new LinkedHashMap<String,String>();
                hm.put(clickItem,CollegeName.get(clickItem).toString());

                File file = new File(getDir("user_colleges", MODE_PRIVATE), "user_colleges_info");
                ObjectOutputStream outputStream = null;
                try {
                    outputStream = new ObjectOutputStream(new FileOutputStream(file));

                    outputStream.writeObject(hm);

                    outputStream.flush();

                    outputStream.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    File f = new File(getDir("user_colleges", MODE_PRIVATE), "user_colleges_info");
                    ObjectInputStream objectInputStream = null;

                    objectInputStream = new ObjectInputStream(new FileInputStream(f));


                    LinkedHashMap<String, String> object = (LinkedHashMap<String, String>) objectInputStream.readObject();

                    if(  object.containsKey(clickItem)){
                        object.remove(clickItem);


                    }
                    object.put(clickItem,CollegeName.get(clickItem).toString());
                    f.delete();

                    objectInputStream.close();


                    ObjectOutputStream outputStream = null;
                        outputStream = new ObjectOutputStream(new FileOutputStream(f));

                        outputStream.writeObject(object);

                        outputStream.flush();

                        outputStream.close();
                }
                 catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }





            String serverUrl = null;
            try {
                String  server = Util.getProperty("name", getApplicationContext());
                serverUrl= server.concat("/CollegesStartServlet");

            } catch (IOException e) {
                e.printStackTrace();
            }
            // String stringUrl ="http://192.168.0.103:9544/NitStartServlet";
            //192.168.0.101
            new GetWholeClgDataTask(clickItem,(String)CollegeName.get(clickItem)).execute(serverUrl);
            //  clickCount = clickCount + 1;
            // marker.setTag(clickCount);
            Toast.makeText(this,
                    R.string.getting_info,
                    Toast.LENGTH_SHORT).show();
        }
    }}

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        Toast.makeText(getBaseContext(), R.string.closed, Toast.LENGTH_SHORT).show();
        marker.hideInfoWindow();
        LatLng location = marker.getPosition();
       // LatLng location= new LatLng(mLatitude,mLongitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.latitude, location.longitude), 9.0f));
        fab.callOnClick();
    }

    @Override
    public void onInfoWindowClose(Marker marker) {

    }
    private float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }
    private void animateMarkerNew(final LatLng startPosition, final LatLng destination, final Marker marker) {

        if (marker != null) {

            final LatLng endPosition = new LatLng(destination.latitude, destination.longitude);

            final float startRotation = marker.getRotation();
            final LatLngInterpolatorNew latLngInterpolator = new LatLngInterpolatorNew.LinearFixed();

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(2000); // duration 3 second
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    try {
                        float v = animation.getAnimatedFraction();
                        LatLng newPosition = latLngInterpolator.interpolate(v, startPosition, endPosition);
                        marker.setPosition(newPosition);
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                .target(newPosition)
                                .zoom(18f)
                                .build()));

                        marker.setRotation(getBearing(startPosition, new LatLng(destination.latitude, destination.longitude)));
                    } catch (Exception ex) {
                        //I don't care atm..
                    }
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    // if (mMarker != null) {
                    // mMarker.remove();
                    // }
                    // mMarker = googleMap.addMarker(new MarkerOptions().position(endPosition).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_car)));

                }
            });
            valueAnimator.start();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
private int dande=0;
    @Override
    public void onLocationChanged(Location location) {
/*        if (dande==0) {
            showLocationPreview();
            UpdateUserLocationUI();
            dande=2;
        }*/
    }

    public class GetMapsDataTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String Extracollegeid="";
        View objview;
        homeMapsActivity  context;


        public GetMapsDataTask() {

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
        public GetMapsDataTask(String funtID,homeMapsActivity con) {
            functionID = funtID;
            context =con;
        }
        public GetMapsDataTask(String funtID,String filtervalues) {
            functionID = funtID;
            Extracollegeid =filtervalues.toString();
        }
        public GetMapsDataTask(String functionID) {
            this.functionID = functionID;
        }
        public GetMapsDataTask(String funtID,homeMapsActivity con,String Extracollegeid) {
            functionID = funtID;
            context =con;
            this.Extracollegeid=Extracollegeid;
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
                //Record.put( "FUNCTION_ID", "ALLNITS_REQUEST");
                Record.put( "FUNCTION_ID", functionID);
                Record.put( "EXTRA_COLLEGE_ID", Extracollegeid);

                Record.put( "WORKSPACE_ID", "NITS_WORKSPACE");
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
            if (result == null || result.equals("{}")) {
                result="";}
            if(result.equalsIgnoreCase("UNABLE_TO_REACH_WEB"))
            {
                findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else {
                if (result != "") {
                    try {
                        JSONObject obj = new JSONObject(ing);

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        int lenghtl = obj.length();
                        CollegeName.clear();
                        hashmap.clear();
                        navClgid.clear();
                        navClgname.clear();
                        mMap.clear();
                        for (int i = 0; i < lenghtl; i++) {
                            Marker marker = null;
                            try {
                            JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));
                            String clgname = Util.Nullcheck((String) clgdata.get("CLG_NAME"));
                            float clgLat = Float.valueOf((String) clgdata.get("CLG_LAT"));
                            float clgLng = Float.valueOf((String) clgdata.get("CLG_LNG"));
                            // float clgLng=(float) clgdata.get("CLG_LNG");
                            String clgID = Util.Nullcheck((String) clgdata.get("CLG_ID"));
                            String clgWebsite =Util.Nullcheck((String) clgdata.get("CLG_WEBSITE"));
                            String clgPlace = Util.Nullcheck((String) clgdata.get("CLG_PLACE"));
                                String clgnumber = Util.Nullcheck((String) clgdata.get("CLG_NUM"));
                            LatLng ClgLatLong = new LatLng(clgLat, clgLng);
                            String clgLogo = null;

                                clgLogo = Util.Nullcheck((String) clgdata.get("CLG_LOGO"));


                            //marker.showInfoWindow();
                            HashMap<String,String> val = new HashMap<>();
                            val.put("COLLEGE_NAME",clgname);
                            val.put("COLLEGE_WEBSITE",clgWebsite);
                            val.put("COLLEGE_PLACE",clgPlace);
                            val.put("COLLEGE_LOGO",clgLogo);
                                val.put("CLG_NUM",clgnumber);
                            collegeDetails.put(clgID,val);
                            CollegeName.put(clgID, clgname);

                            CollegeWebsite.put(clgID, clgWebsite);
                            navClgid.add(i, clgID);
                            navClgname.add(i, clgname);


                            if (clgID.startsWith("NIT")){
                                marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).title(clgname).icon(BitmapDescriptorFactory.fromResource(R.drawable.nit_map_icon)));
                            }
                            if (clgID.startsWith("IIT")){
                                marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).title(clgname).icon(BitmapDescriptorFactory.fromResource(R.drawable.iit_map_icon)));
                            }
                                if (clgID.startsWith("IIIT")){
                                    marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).title(clgname).icon(BitmapDescriptorFactory.fromResource(R.drawable.iiit_map_icon)));
                                }
                                if (clgID.startsWith("DEEM")){
                                    marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).title(clgname).icon(BitmapDescriptorFactory.fromResource(R.drawable.deem_map_icon)));
                                }
                                if (clgID.startsWith("IIM")){
                                    marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).title(clgname).icon(BitmapDescriptorFactory.fromResource(R.drawable.deem_map_icon)));
                                }
                            marker.setTag(clgID);
                            // marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).title(clgname).icon(BitmapDescriptorFactory.fromResource(R.drawable.nit_map_icon)));
                            //marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).icon(BitmapDescriptorFactory.fromResource(R.drawable.nit_map_icon)));
                            builder.include(ClgLatLong);
                            mMap.setMinZoomPreference(4);

                            hashmap.put(clgID, marker);

                            DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layoutmap);
                            //  mDrawerLayout.openDrawer(mDrawerLayout);
                            //  mDrawerLayout.openDrawer(Gravity.RIGHT);
//.icon(BitmapDescriptorFactory.fromResource(R.drawable.maps_pin))
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        mAdapter.notifyDataSetChanged();
                        LatLngBounds bounds = builder.build();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
                        // CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, );
                        if (bundle != null) {
                            if (!from_Activity.equalsIgnoreCase("")) {
                                onMarkerClick(hashmap.get(COLLEGE_ID));
                            }
                        }
                        findViewById(R.id.marker_progress).setVisibility(View.GONE);
                        //   LatLng sydney = new LatLng(clgLat,80);
                        //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }}
                findViewById(R.id.marker_progress).setVisibility(View.GONE);
            }

        }
    }
    public class GetFilterDataTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String Extracollegeid="";
        View objview;
        homeMapsActivity  context;

        public GetFilterDataTask() {

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
        public GetFilterDataTask(String funtID,homeMapsActivity con) {
            functionID = funtID;
            context =con;
        }
        public GetFilterDataTask(String functionID) {
            this.functionID = functionID;
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
                //Record.put( "FUNCTION_ID", "ALLNITS_REQUEST");
                Record.put( "FUNCTION_ID", functionID);

                Record.put( "WORKSPACE_ID", "NITS_WORKSPACE");
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
            if (result == null) {
                result="";}
            if(result.equalsIgnoreCase("UNABLE_TO_REACH_WEB"))
            {
                findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else {
                if (result != "") {
                    try {
                        JSONObject obj = new JSONObject(ing);
                        JSONArray values = (JSONArray) obj.get("0");
                        ArrayList<String> listdata = new ArrayList<String>();
                        JSONArray jArray = (JSONArray)values;
                        if (jArray != null) {
                            for (int i=0;i<jArray.length();i++){
                                if(!(jArray.getString(i)).equalsIgnoreCase("null")){
                                listdata.add(jArray.getString(i));}
                            }
                        }
                        if(functionID.equalsIgnoreCase("CLG_STATE_FILTER")) {
                            listDataChild.put(listDataHeader.get(0), listdata);
                        }
                        if(functionID.equalsIgnoreCase("CLG_CITIES_FILTER")) {
                            listDataChild.put(listDataHeader.get(1), listdata);
                        }
                        listAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }}
            }

        }
    }
    public class GetSearchData  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID,toSendData;
        View objview;
        homeMapsActivity  context;

        public GetSearchData() {

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
          //  findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);

        }
        public GetSearchData(String funtID,homeMapsActivity con) {
            functionID = funtID;
            context =con;
        }
        public GetSearchData(String funtID,homeMapsActivity con,String toSendData) {
            functionID = funtID;
            context =con;
            this.toSendData=toSendData;
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
                //Record.put( "FUNCTION_ID", "ALLNITS_REQUEST");
                Record.put( "FUNCTION_ID", functionID);
                Record.put( "DATA_TO_SERVER", toSendData);

                Record.put( "WORKSPACE_ID", "NITS_WORKSPACE");
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
            if (result == null) {
                result="";}
            if(result.equalsIgnoreCase("UNABLE_TO_REACH_WEB"))
            {
                findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else {
                if (result != "") {
                    try {
                        JSONObject obj = new JSONObject(ing);

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        int lenghtl = obj.length();
                        CollegeName.clear();
                        hashmap.clear();
                        navClgid.clear();
                        navClgname.clear();
                        mMap.clear();
                         for (int i = 0; i < lenghtl; i++) {
                            Marker marker = null;
                            try {
                                JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));
                                String clgname = Util.Nullcheck((String) clgdata.get("CLG_NAME"));
                                float clgLat = Float.valueOf((String) clgdata.get("CLG_LAT"));
                                float clgLng = Float.valueOf((String) clgdata.get("CLG_LNG"));
                                // float clgLng=(float) clgdata.get("CLG_LNG");
                                String clgID = Util.Nullcheck((String) clgdata.get("CLG_ID"));
                                String clgWebsite =Util.Nullcheck((String) clgdata.get("CLG_WEBSITE"));
                                String clgPlace = Util.Nullcheck((String) clgdata.get("CLG_PLACE"));
                                String clgnumber = Util.Nullcheck((String) clgdata.get("CLG_NUM"));
                                LatLng ClgLatLong = new LatLng(clgLat, clgLng);
                                String clgLogo = null;

                                clgLogo = Util.Nullcheck((String) clgdata.get("CLG_LOGO"));


                                //marker.showInfoWindow();
                                HashMap<String,String> val = new HashMap<>();
                                val.put("COLLEGE_NAME",clgname);
                                val.put("COLLEGE_WEBSITE",clgWebsite);
                                val.put("COLLEGE_PLACE",clgPlace);
                                val.put("COLLEGE_LOGO",clgLogo);
                                val.put("CLG_NUM",clgnumber);
                                collegeDetails.put(clgID,val);
                                CollegeName.put(clgID, clgname);

                                CollegeWebsite.put(clgID, clgWebsite);
                                navClgid.add(i, clgID);
                                navClgname.add(i, clgname);


                                if (clgID.startsWith("NIT")){
                                    marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).title(clgname).icon(BitmapDescriptorFactory.fromResource(R.drawable.nit_map_icon)));
                                }
                                if (clgID.startsWith("IIT")){
                                    marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).title(clgname).icon(BitmapDescriptorFactory.fromResource(R.drawable.iit_map_icon)));
                                }
                                if (clgID.startsWith("IIIT")){
                                    marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).title(clgname).icon(BitmapDescriptorFactory.fromResource(R.drawable.iit_map_icon)));
                                }
                                if (clgID.startsWith("DEEM")){
                                    marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).flat(true).title(clgname).icon(BitmapDescriptorFactory.fromResource(R.drawable.iit_map_icon)));
                                }
                                marker.setTag(clgID);
                                // marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).title(clgname).icon(BitmapDescriptorFactory.fromResource(R.drawable.nit_map_icon)));
                                //marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).icon(BitmapDescriptorFactory.fromResource(R.drawable.nit_map_icon)));
                                builder.include(ClgLatLong);
                                mMap.setMinZoomPreference(4);

                                hashmap.put(clgID, marker);

                                DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layoutmap);
                                //  mDrawerLayout.openDrawer(mDrawerLayout);
                                //  mDrawerLayout.openDrawer(Gravity.RIGHT);
//.icon(BitmapDescriptorFactory.fromResource(R.drawable.maps_pin))
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        CardView view = (CardView) findViewById(R.id.maps_recycle_cardview1);
                        view.setVisibility(View.VISIBLE);
                        mAdaptersearch.notifyDataSetChanged();
                        /*       LatLngBounds bounds = builder.build();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));*/
                        // CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, );
                        if (bundle != null) {
                            if (!from_Activity.equalsIgnoreCase("")) {
                                onMarkerClick(hashmap.get(COLLEGE_ID));
                            }
                        }
                        findViewById(R.id.marker_progress).setVisibility(View.GONE);
                        //   LatLng sydney = new LatLng(clgLat,80);
                        //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }}
            }

        }
    }


    public class GetMapsDataCurrentLocTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        View objview;
        homeMapsActivity  context;
        public GetMapsDataCurrentLocTask() {

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
        public GetMapsDataCurrentLocTask(String funtID,homeMapsActivity con) {
            functionID = funtID;
            context =con;
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
                //Record.put( "FUNCTION_ID", "ALLNITS_REQUEST");
                Record.put( "FUNCTION_ID", functionID);
                Record.put( "WORKSPACE_ID", "CURR_CLGS_WORKSPACE");
                Record.put( "USER_LAT", mLatitude);
                Record.put( "USER_LNG", mLongitude);
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
            if (result.equalsIgnoreCase("{}")) {
                result="";}
            if(result.equalsIgnoreCase("UNABLE_TO_REACH_WEB"))
            {
                findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else if (result != "") {
                try {
                    JSONObject obj = new JSONObject(ing);

                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    int lenghtl = obj.length();
                    CollegeName.clear();
                    hashmap.clear();
                    navClgid.clear();
                    navClgname.clear();
                    mMap.clear();
                    LatLng Mylocation = new LatLng(12.839801, 80.223016);
                   Marker dandedev = mMap.addMarker(new MarkerOptions().position(Mylocation).title("Developers:Dande,Balaji,Damoji").icon(BitmapDescriptorFactory.fromResource(R.drawable.developers)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(Mylocation));
                    dandedev.setTag("DEVELPER_TAG");

                    for (int i = 0; i < lenghtl; i++) {
                        Marker marker = null;
                        JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));
                        String clgname = (String) clgdata.get("CLG_NAME");
                        float clgLat = Float.valueOf((String) clgdata.get("CLG_LAT"));
                        float clgLng = Float.valueOf((String) clgdata.get("CLG_LNG"));
                        String clgPlace = (String) clgdata.get("CLG_PLACE");
                        // float clgLng=(float) clgdata.get("CLG_LNG");
                        String clgID = (String) clgdata.get("CLG_ID");
                        String clgWebsite = (String) clgdata.get("CLG_WEBSITE");
                        String clgnumber = null;
                        try {
                            clgnumber = Util.Nullcheck((String) clgdata.get("CLG_NUM"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        String clgLogo = null;
                        try {
                            clgLogo = Util.Nullcheck((String) clgdata.get("CLG_LOGO"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        HashMap<String,String> val = new HashMap<>();
                        val.put("COLLEGE_NAME",clgname);
                        val.put("COLLEGE_WEBSITE",clgWebsite);
                        val.put("COLLEGE_PLACE",clgPlace);
                        val.put("COLLEGE_LOGO",clgLogo);
                        val.put("CLG_NUM",clgnumber);

                        collegeDetails.put(clgID,val);
                        CollegeName.put(clgID, clgname);

                        navClgid.add(i, clgID);
                        navClgname.add(i, clgname);
                        //  Validate.notNull(clgLogo,"ValidationHandler not Injected");

                        LatLng ClgLatLong = new LatLng(clgLat, clgLng);
                        //marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).title(clgname).icon(BitmapDescriptorFactory.fromResource(R.drawable.nit_map_icon)));
                        if (clgID.startsWith("NIT")){
                            marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).icon(BitmapDescriptorFactory.fromResource(R.drawable.nit_map_icon)));
                        }
                        if (clgID.startsWith("IIT")){
                            marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).icon(BitmapDescriptorFactory.fromResource(R.drawable.iit_map_icon)));
                        }
                        if (clgID.startsWith("IIM")){
                            marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).icon(BitmapDescriptorFactory.fromResource(R.drawable.iit_map_icon)));
                        }
                        if (clgID.startsWith("IIIT")){
                            marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).icon(BitmapDescriptorFactory.fromResource(R.drawable.iiit_map_icon)));
                        }
                        if (clgID.startsWith("DEEM")){
                            marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).icon(BitmapDescriptorFactory.fromResource(R.drawable.deem_map_icon)));
                        }
                        builder.include(ClgLatLong);
                        mMap.setMinZoomPreference(4);
                        marker.setTag(clgID);

                        hashmap.put(clgID, marker);

                        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layoutmap);
                        //  mDrawerLayout.openDrawer(mDrawerLayout);
                        //  mDrawerLayout.openDrawer(Gravity.RIGHT);
//.icon(BitmapDescriptorFactory.fromResource(R.drawable.maps_pin))

                    }

                    mAdapter.notifyDataSetChanged();
                    LatLngBounds bounds = builder.build();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
                    // CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, );
                    if (bundle != null) {
                        if (!from_Activity.equalsIgnoreCase("")) {
                            onMarkerClick(hashmap.get(COLLEGE_ID));
                        }
                    }
                    findViewById(R.id.marker_progress).setVisibility(View.GONE);
                    //   LatLng sydney = new LatLng(clgLat,80);
                    //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
else{
                findViewById(R.id.marker_progress).setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), R.string.no_colleges, Toast.LENGTH_LONG).show();
            }
        }
    }
    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {


        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            String distance = "";
            String duration = "";
            findViewById(R.id.marker_progress).setVisibility(View.GONE);
            if (result.size() < 1) {
                Toast.makeText(getBaseContext(), R.string.directions_not_avail, Toast.LENGTH_SHORT).show();
                return;
            }
            TextView directions1 = (TextView) findViewById(R.id.directionresult1);
            TextView directions2 = (TextView) findViewById(R.id.directionresult2);
            TextView directions3 = (TextView) findViewById(R.id.directionresult3);
           /* TextView directions4 = (TextView) findViewById(R.id.directionresult4);*/
         /*   directions1.setText("");
            directions2.setText("");
            directions3.setText("");
            directions4.setText("");*/
            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    if (j == 0) {    // Get distance from the list
                        distance = point.get("distance");
                        continue;
                    } else if (j == 1) { // Get duration from the list
                        duration = point.get("duration");
                        continue;
                    }

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                if (i == 0) {
                    lineOptions.color(Color.BLUE);
                    lineOptions.width(22);
                }
                else if (i == 1) {
                    lineOptions.color(getResources().getColor(R.color.LightSteelBlue));
                    lineOptions.width(20);
                }
                else if (i == 2) {
                    lineOptions.color(getResources().getColor(R.color.LightSteelBlue));
                    lineOptions.width(18);
                }
               else if (i == 3) {
                    lineOptions.color(getResources().getColor(R.color.LightSteelBlue));
                    lineOptions.width(16);
                }
                else{
                    lineOptions.color(getResources().getColor(R.color.LightSteelBlue));
                    lineOptions.width(15);
                }
                lineOptions.addAll(points);


                lineOptions.zIndex(3);
                mMap.addPolyline(lineOptions);
                if (i == 0) {
                    directions1.setText( getString(R.string.route)+i+ getString(R.string.distance) + distance + getString(R.string.duration) + duration);
                    directions1.setTextColor(Color.BLUE);
                }
                if (i == 1) {
                    directions2.setText( getString(R.string.route)+i+ getString(R.string.distance) + distance + getString(R.string.duration) + duration);
                    directions2.setTextColor(getResources().getColor(R.color.LightSteelBlue));
                }
                if (i == 2) {
                    directions3.setText( getString(R.string.route)+i+ getString(R.string.distance) + distance + getString(R.string.duration) + duration);
                    directions3.setTextColor(getResources().getColor(R.color.LightSteelBlue));
                }
            /*    if (i == 3) {
                    directions4.setText( getString(R.string.route)+i+ getString(R.string.distance) + distance + getString(R.string.duration) + duration);
                    directions4.setTextColor(getResources().getColor(R.color.LightSteelBlue));
                }*/

              //  Toast.makeText(getApplicationContext(), "Route:"+i+ " Distance:" + distance + " Duration:" + duration, Toast.LENGTH_SHORT).show();
            }
            CardView xyz =(CardView) findViewById(R.id.cardfordirections);
            xyz.setVisibility(View.VISIBLE);
            //distance comment as of now
         /*   final TextView collegeListvalues = (TextView) findViewById(R.id.College_list);
            collegeListvalues.setText(" Distance:" + distance + "\n Duration:" + duration);*/
          //  Toast.makeText(getApplicationContext(), " Distance:" + distance + "\n Duration:" + duration, Toast.LENGTH_SHORT).show();
            // markerFordirection.setTitle(collegnameFordirection+" Distance:" + distance + ", Duration:" + duration);
            //  tvDistanceDuration.setText("Distance:" + distance + ", Duration:" + duration);

            // Drawing polyline in the Google Map for the i-th route

        }
    }
    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {

        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    public class GetWholeClgDataTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String collegeID;
        private String collg;
        View objview;

        public GetWholeClgDataTask() {

        }
        public GetWholeClgDataTask(String funtID,String collg) {
            collegeID = funtID;
            this.collg=collg;
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
                Record.put( "COLLEGE_ID", collegeID);
                Record.put( "FUNCTION_ID", "ALLCLGS_REQUEST_BOTTOMSHT");
                Record.put( "WORKSPACE_ID", "CLGS_WORKSPACE");
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
        @Override
        protected void onPostExecute(String result) {
            String ing = result;
            if(result.equalsIgnoreCase("UNABLE_TO_REACH_WEB"))
            {
                findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else {

                try {
                    JSONObject obj = new JSONObject(ing);

                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    int lenghtl = obj.length();

                    for (int i = 0; i < lenghtl; i++) {
                        Marker marker = null;
                        HashMap<String,HashMap> valstoBottomSheet = null;
                        try {
                            JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));
                            String clgname = Util.Nullcheck((String) clgdata.get("CLG_NAME"));
                            float clgLat = Float.valueOf((String) clgdata.get("CLG_LAT"));
                            float clgLng = Float.valueOf((String) clgdata.get("CLG_LNG"));
                            String clgPlace = Util.Nullcheck((String) clgdata.get("CLG_PLACE"));
                            // float clgLng=(float) clgdata.get("CLG_LNG");
                            String clgID = Util.Nullcheck((String) clgdata.get("CLG_ID"));
                            String clgWebsite =Util.Nullcheck( (String) clgdata.get("CLG_WEBSITE"));

                            String clgLogo = Util.Nullcheck((String) clgdata.get("CLG_LOGO"));
                            String clgImages = Util.Nullcheck((String) clgdata.get("CLG_IMAGES"));
                            String ABOUT_US = Util.Nullcheck((String) clgdata.get("ABOUT_US"));
                            String clgnumber = Util.Nullcheck((String) clgdata.get("CLG_NUM"));

                            HashMap<String,String> val = new HashMap<>();
                            val.put("COLLEGE_NAME",clgname);
                            val.put("COLLEGE_WEBSITE",clgWebsite);
                            val.put("COLLEGE_PLACE",clgPlace);
                            val.put("COLLEGE_LOGO",clgLogo);
                            val.put("COLLEGE_LAT", String.valueOf(clgLat));
                            val.put("COLLEGE_IMAGES",clgImages);
                            val.put("COLLEGE_LNG",String.valueOf(clgLogo));
                            val.put("ABOUT_US",ABOUT_US);
                            val.put("CLG_NUM",clgnumber);

                            valstoBottomSheet= new HashMap<>();
                            valstoBottomSheet.put(clgID,val);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Slide slideTransition = null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            slideTransition = new Slide(Gravity.LEFT);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
                        }
                        //  Validate.notNull(clgLogo,"ValidationHandler not Injected");
                        Fragment fr = collegesView1.newInstance(valstoBottomSheet,collegeID);
                        fr.setReenterTransition(slideTransition);
                        fr.setExitTransition(slideTransition);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            fr.setSharedElementEnterTransition(new ChangeBounds());
                        }
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.map_view_onclick_frame,fr);
                        fragmentTransaction.addToBackStack(null);
                        int commit = fragmentTransaction.commit();

                        findViewById(R.id.marker_progress).setVisibility(View.GONE);
                    //    myBottomSheet = collegesView.newInstance(valstoBottomSheet,collegeID);
                     //   myBottomSheet.show(getSupportFragmentManager(), myBottomSheet.getTag());
                        //   LatLng sydney = new LatLng(clgLat,80);
                        //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                    }} catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        public String readIt(InputStream stream, int len) throws IOException {

            String myString = IOUtils.toString(stream, "UTF-8");
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[5000];
            reader.read(buffer);
            return myString;
        }
        /*
         * Preparing the list data
         */

        // onPostExecute displays the results of the AsyncTask.

    }
}


