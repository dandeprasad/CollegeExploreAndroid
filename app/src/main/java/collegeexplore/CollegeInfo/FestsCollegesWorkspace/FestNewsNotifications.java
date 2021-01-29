package collegeexplore.CollegeInfo.FestsCollegesWorkspace;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.SpacesItemDecorHomeActivity;
import collegeexplore.CollegeInfo.Util;
import collegeexplore.logger.Log;


public class FestNewsNotifications extends Fragment implements FestsNewsNotificationsAdaptor.OnItemClickListener, OnClickListener {
    int mNum;
    ProgressDialog progresdialoglistview ;
    private RecyclerView newsRecyclerView;
    private String[] maincontents1;
    PopupWindow popupMessage;
    LinearLayout layoutOfPopup;
    TextView popupText;
    View xtest;
    private RecyclerView carrerGridList;
    private GridLayoutManager GridLayoutManager1;
    private static final int SPAN_COUNT = 2;
    Context contextForFragment;
    private LruCache<String, Bitmap> mMemoryCache;
    String serverUrl = null;
    FestsNewsNotificationsAdaptor mAdapter;
    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    String jsonurl;

    static final String LOG_TAG = "HomeActivity";
    public static Bitmap[] arrayOfBitmap= new Bitmap[10];
   // String StringData[]=new  String[7];
    SwipeRefreshLayout mySwipeRefreshLayout;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab;
    private CardView fests_register;
    ArrayList<HashMap<String, String>> StringData = new ArrayList<HashMap<String, String>>();
    /**
     * used for initiating the variables
     */
    public static FestNewsNotifications newInstance() {
        FestNewsNotifications f = new FestNewsNotifications();


        Bundle args = new Bundle();
        args.putInt("num", 1);
        f.setArguments(args);

        return  f;
    }
    //for getting the bundle data in fragments here we are getting the context.
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        contextForFragment  = context;
    }


    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mNum = getArguments() != null ? getArguments().getInt("num") : 1;

        //memory cache management
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };}

    /**
     * This is called soon after oncreate and expects a view return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.home_fests_notify_layout, container, false);
    }





    public void onViewCreated(View view, Bundle savedInstanceState) {
        xtest = view;
        fab = (FloatingActionButton)xtest.findViewById(R.id.fab);

        RelativeLayout   fests_register1=       (RelativeLayout) xtest.findViewById(R.id.forregisterferstvisible);
        fests_register1.setVisibility(View.VISIBLE);
        fests_register=(CardView) xtest.findViewById(R.id.fests_register);
        fab_open = AnimationUtils.loadAnimation(contextForFragment, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(contextForFragment,R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(contextForFragment,R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(contextForFragment,R.anim.rotate_backward);
        fab.setOnClickListener(this);

        fests_register.setOnClickListener(this);


        Toolbar myToolbar = xtest.findViewById(R.id.PurenewsToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.fest);

        // Get a support ActionBar corresponding to this toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //final Toolbar HomeToolbarfrag = (Toolbar) findViewById(R.id.toolbar_collaspe);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
            }

        });

      /*  ProgressBar spinner = new android.widget.ProgressBar(
                contextForFragment,
                null,
                android.R.attr.progressBarStyle);

        spinner.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);*/
        mySwipeRefreshLayout = xtest.findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setColorSchemeResources(
                R.color.app_theme
                /*R.color.refresh_progress_2,
                R.color.refresh_progress_3*/);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        myUpdateOperation();
                    }


                }
        );


       //contextForFragment.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        /*Previously used ip addresses*/
        //  String stringUrl ="http://192.168.43.60:9544/ImageUploadServlet";
        // String stringUrl ="http://10.0.2.2:8085/androiddandeCheck/HomeNewsFragmwent";
        //String stringUrl ="http://10.0.2.2:9544/ImageUploadServlet";

        //Loading recycling horizontal view
        GridLayoutManager1 = new GridLayoutManager(getActivity(), SPAN_COUNT);


        carrerGridList = xtest.findViewById(R.id.recyclerNewsNotify);
        // carrerGridList.setNestedScrollingEnabled(false);
        carrerGridList.setLayoutManager(GridLayoutManager1);
        carrerGridList.setHasFixedSize(true);
        //StringData.clear();
        mAdapter = new FestsNewsNotificationsAdaptor(this,contextForFragment,arrayOfBitmap,StringData);
        carrerGridList.setAdapter(mAdapter);


        GridLayoutManager.SpanSizeLookup onSpanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(mAdapter.getItemViewType(position)){
                    case 2:
                        return 2;
                    case 1:
                        return 2;
                    case 3:
                        return 1;
                    case 4:
                        return 2;
                    case 5:
                        return 2;
                    case 0:
                        return 2;
                    default:
                        return -1;
                }
            }
        };

        GridLayoutManager1.setSpanSizeLookup(onSpanSizeLookup);






        //mDrawerList.setAnimation();
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing2);
        carrerGridList.addItemDecoration(new SpacesItemDecorHomeActivity(spacingInPixels));
        carrerGridList.setItemAnimator(new DefaultItemAnimator());
        OnClickListener image_clicker = new OnClickListener(){
            public void onClick(View v){
                String x  = "tst";
            }
        };
        //All images to load from server
        //  imageView= (ImageView) xtest.findViewById(carrerGuidance_img0);

        //  imageView.setOnClickListener(image_clicker);

        //calling to test in cache whether images are availabe or not
        //  loadBitmap(carrerGuidance_img0, imageView);
        //getting the server ip and port number

        try {
            String  server =  Util.getProperty("name", contextForFragment);
            serverUrl= server.concat("/FestsLatestUpdates");
            jsonurl= server.concat("/FestsHomeAllNotificationsString");
        } catch (IOException e) {
            e.printStackTrace();
        }
/*        for(int i =0;i<=9;i++)
        {
            new GetDataNewsNotifyTask(i).execute(serverUrl);
        }*/
        String WORKSPACE_ID ="HOME_WORKSPACE";
        String FUNCTION_ID = "GET_FESTS_NOTIFICATIONS";
        String ACTION_ID = "GET_ALL_FESTNEWS_STRING";
        if(StringData.isEmpty()) {
            new GetStringNewsNotifyTask(WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(jsonurl);
        }

    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:

                animateFAB();

                break;
            case R.id.fab1:
                //

                break;
            case R.id.fab2:


                break;
            case R.id. fests_register:
              /*  FestsRegistration cdd=new FestsRegistration(getActivity());
                cdd.show();*/
                Fragment fr =  FestsRegistration.newInstance();

                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.view_onclick_framecutt1,fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
        }
    }
    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);

            fests_register.startAnimation(fab_close);

            fests_register.setClickable(false);
            isFabOpen = false;
            android.util.Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);

            fests_register.startAnimation(fab_open);

            fests_register.setClickable(true);
            isFabOpen = true;
            android.util.Log.d("Raj","open");

        }
    }
    //cache memory implementation
    public void loadBitmap(int resId, ImageView imageView) {
        final String imageKey = String.valueOf(resId);

        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
        else {
            new GetDataNewsNotifyTask(imageView ,resId).execute(serverUrl);
        }
    }
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }







    //async inner class
    public class GetDataNewsNotifyTask  extends AsyncTask<String, Void, Bitmap>

    {
        int imageResId;
        int imagetest;
        private  WeakReference<ImageView> imageViewReference;
        private int data = 0;
        public GetDataNewsNotifyTask(int  imageView) {

            imagetest = imageView;
        }



        public GetDataNewsNotifyTask(ImageView imageView, int resId) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageResId = resId;
            imageViewReference = new WeakReference<ImageView>(imageView);
        }


        @Override
        protected  void onPreExecute()
        {
            // progresdialoglistview=ProgressDialog.show(getActivity(), "","Loading");
            // Log.e("onPreExecutive","called"+progresdialoglistview);
            xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
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
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write("dandeRequest="+imagetest);
            writer.flush();
            writer.close();
            os.close();

            conn.connect();




            int response = conn.getResponseCode();
            // Log.d(DEBUG_TAG, "The response is: " + response);
            if (response==200) {
                is = conn.getInputStream();
            }
            Bitmap bitmap1 = null;
            if(is!=null) {
                //for storing image data
                byte[] bytes = IOUtils.toByteArray(is);

                //degrading the image required to pur requirement
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                WindowManager wm = (WindowManager) contextForFragment.getSystemService(Context.WINDOW_SERVICE);
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
            if(result==null)
            {
                xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else {

                arrayOfBitmap[imagetest] = result;

                //  mAdapter.notifyDataSetChanged();
                mAdapter.notifyItemChanged(4);
                mAdapter.notifyItemChanged(5);
                mAdapter.notifyItemChanged(6);
                mAdapter.notifyItemChanged(7);
                mAdapter.notifyItemChanged(8);
                mAdapter.notifyItemChanged(9);
                mAdapter.notifyItemChanged(10);
                mAdapter.notifyItemChanged(11);
                mAdapter.notifyItemChanged(12);
                Bitmap ing = result;
                xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
            }

        }


    }

    public class GetStringNewsNotifyTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        View objview;

        public GetStringNewsNotifyTask() {

        }
        public GetStringNewsNotifyTask(String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
        }

        @Override
        protected  void onPreExecute()
        {
            // progresdialoglistview=ProgressDialog.show(getActivity(), "","Loading");
            // Log.e("onPreExecutive","called"+progresdialoglistview);
            xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
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
            if(result.equalsIgnoreCase("UNABLE_TO_REACH_WEB"))
            {
                xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else {

                try {
                    JSONObject obj = new JSONObject(ing);
                    Marker marker;
                    int lenghtl = obj.length();
                    StringData.clear();
                    for (int i = 0; i < lenghtl; i++) {
                        JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));




                        String CLGID = (String) clgdata.get("CLGID");
                        String COLLEGE_NAME = (String) clgdata.get("COLLEGE_NAME");
                        String CLG_FEST_DATE = (String) clgdata.get("CLG_FEST_DATE");
                        String CLG_FEST_INFO = (String) clgdata.get("CLG_FEST_INFO");
                        String CLG_LOCATION = (String) clgdata.get("CLG_LOCATION");
                        String FEST_IMAGE = (String) clgdata.get("FEST_IMAGE");


                        HashMap<String, String> param_aux = new HashMap<String, String>();

                        param_aux.put("CLGID", CLGID);
                        param_aux.put("COLLEGE_NAME", COLLEGE_NAME);
                        param_aux.put("CLG_FEST_DATE", CLG_FEST_DATE);
                        param_aux.put("CLG_FEST_INFO", CLG_FEST_INFO);
                        param_aux.put("CLG_LOCATION", CLG_LOCATION);
                        param_aux.put("FEST_IMAGE", FEST_IMAGE);

                        StringData.add(param_aux);

                    }
                    mySwipeRefreshLayout.setRefreshing(false);
                    mAdapter.notifyDataSetChanged();

                    //   LatLng sydney = new LatLng(clgLat,80);
                    //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
            }

        }
    }
    public void onClick(View view, int position, String clg_id,String festimage,String collegename,ImageView imagetrasition ) {
        int x = view.getId();

        selectItem(position,clg_id,festimage,collegename, imagetrasition);
    }
    private void selectItem(int position,String clg_id,String festimage,String collegename,ImageView imagetrasition ){

/*        final String UniqueKey = "FESTS_LATEST_NOTIFY";
ArrayList<String> datatohost=new ArrayList<String>();
        datatohost.add(clg_id);
        // ((HomeActivity)getActivity()).getSupportActionBar(). setHomeAsUpIndicator(R.drawable.back_button);
        Fragment fr =  ShowDetailNewsNotifications.newInstance(position, UniqueKey,datatohost);

        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.view_onclick_framecutt1,fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
        Intent i2 = new Intent(contextForFragment,TabsHeaderActivity.class);
       i2.putExtra("clg_id",clg_id);
        i2.putExtra("festimage",festimage);
        i2.putExtra("EXTRA_IMAGE_TRANSITION_NAME", ViewCompat.getTransitionName(imagetrasition));

        i2.putExtra("collegename",collegename);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(),
                imagetrasition,
                ViewCompat.getTransitionName(imagetrasition));
        startActivity(i2,options.toBundle());

    }
/*    @SuppressWarnings("unchecked") void transitionTo(Intent i,Bundle values) {
        final Pair<View, String>[] pairs;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pairs = TransitionHelper.createSafeTransitionParticipants(getActivity(), true);

            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);

            startActivity(i, transitionActivityOptions.toBundle());
        }
        else{
            startActivity(i);
        }
    }*/
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    private void myUpdateOperation() {

/*      android.support.v4.app.Fragment fr =  new HomeNewsNotifications();

        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.sample_content_fragment,fr);
        fragmentTransaction.commit();*/

        try {
            String server = Util.getProperty("name", contextForFragment);
            serverUrl = server.concat("/FestsLatestUpdates");
            jsonurl = server.concat("/FestsHomeAllNotificationsString");
        } catch (IOException e) {
            e.printStackTrace();
        }
/*        for(int i =0;i<=9;i++)
        {
            new GetDataNewsNotifyTask(i).execute(serverUrl);
        }*/
        String WORKSPACE_ID = "HOME_WORKSPACE";
        String FUNCTION_ID = "GET_FESTS_NOTIFICATIONS";
        String ACTION_ID = "GET_ALL_FESTNEWS_STRING";

        new GetStringNewsNotifyTask(WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(jsonurl);

        //  mySwipeRefreshLayout.setRefreshing(false);
    }
}
