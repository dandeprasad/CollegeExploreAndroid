package collegeexplore.CollegeInfo.NewsandNotificationsWorkspace;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import collegeexplore.CollegeInfo.AllDetailsDescriptionWorkspace.ShowDetailNewsNotifications;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.TransitionHelper;
import collegeexplore.logger.Log;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;


public class HomeNewsNotifications extends Fragment implements HomeNewsNotificationsAdaptor.OnItemClickListener {
    int mNum;
    ProgressDialog progresdialoglistview ;
    private RecyclerView newsRecyclerView;
    private String[] maincontents1;
    PopupWindow popupMessage;
    LinearLayout layoutOfPopup;
    TextView popupText;
    View xtest;
    public static final String PREFS_NAME = "NewsNotifyPatch";


    SharedPreferences persistantData;

    public static Bitmap[] ArrayListdata= new Bitmap[8];
    public static Bitmap[] ArrayListdata1= new Bitmap[11];
    public  static Bitmap[] ArrayListdata2= new Bitmap[11];
    public static Bitmap[] ArrayListdata3= new Bitmap[11];

    private Boolean isFirstRun,isFirstRunCities,isFirstRunStates,isFirstRunStream,isFirstRunRating;
    private RecyclerView carrerGridList;
 private GridLayoutManager GridLayoutManager1;
    private static final int SPAN_COUNT = 3;
    Context contextForFragment;
    private LruCache<String, Bitmap> mMemoryCache;
    String serverUrl = null;
    public static HomeNewsNotificationsAdaptor mAdapter;

    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    String jsonurl;
    static final String LOG_TAG = "HomeActivity";
    public static Bitmap[] arrayOfBitmap= new Bitmap[8];
    String StringData[]=new  String[7];
    SwipeRefreshLayout mySwipeRefreshLayout;
    /**
     * used for initiating the variables
     */
    public static HomeNewsNotifications newInstance() {
        HomeNewsNotifications f = new HomeNewsNotifications();


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

        return inflater.inflate(R.layout.home_news_notify_layout, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        TextView search = getActivity().findViewById(R.id.search_home);
        search.setText(R.string.search_for_colleges_universities);}


    public void onViewCreated(View view, Bundle savedInstanceState) {
        xtest = view;
      /*  ProgressBar spinner = new android.widget.ProgressBar(
                contextForFragment,
                null,
                android.R.attr.progressBarStyle);

        spinner.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);*/


       // imageview.setOnClickListener();


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

        mAdapter = new HomeNewsNotificationsAdaptor(this,contextForFragment,arrayOfBitmap,StringData);
        carrerGridList.setAdapter(mAdapter);


        GridLayoutManager.SpanSizeLookup onSpanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(mAdapter.getItemViewType(position)){
                    case 2:
                        return 3;
                    case 1:
                        return 3;
                    case 3:
                        return 3;
                    case 4:
                        return 3;
                    case 5:
                        return 3;
                    case 0:
                        return 3;
                    case 6:
                        return 3;
                    case 8:
                        return 3;
                    case 9:
                        return 3;
                    default:
                        return -1;

                }
            }
        };

        GridLayoutManager1.setSpanSizeLookup(onSpanSizeLookup);


        SharedPreferences settings = contextForFragment.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

/*        editor.getString("imagepath"+Integer.toString(imagetest), path);
        persistantData.getString("imagepath"+)
        loadImageFromStorage( path,Integer.toString(imagetest));*/






      /*  //Colleges in Top States
        try {
            String linearLayoutID1="CLGS_TOP_STATES";
            String WORKSPACE_ID1= "HOME_WORKSPACE";
            String FUNCTION_ID1 = "GET_LINEAR_DATA";
            String ACTION_ID1 = "CLGS_TOP_STATES";

            String  server =  Util.getProperty("name", contextForFragment);
            serverUrl= server.concat("/HomeAllNotifications");

            for(int i =0;i<=9;i++)
            {

                isFirstRunStates = contextForFragment.getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                        .getBoolean("isFirstRunStates", true);



                if (isFirstRunStates) {
                    new AllNotifications(i,linearLayoutID1,WORKSPACE_ID1, FUNCTION_ID1, ACTION_ID1).execute(serverUrl);
                }
                else{

                    SharedPreferences prefs = contextForFragment.getSharedPreferences(PREFS_NAME, 0);
                    String pathtoget =prefs.getString("imagepath1"+Integer.toString(i),null);
                    loadImageFromStorage1( pathtoget,Integer.toString(i));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }




        //Stream wise colleges
        try {
            String linearLayoutID1="STREAM_WISE_CLGS";
            String WORKSPACE_ID1= "HOME_WORKSPACE";
            String FUNCTION_ID1 = "GET_LINEAR_DATA";
            String ACTION_ID1 = "STREAM_WISE_CLGS";

            String  server =  Util.getProperty("name", contextForFragment);
            serverUrl= server.concat("/HomeAllNotifications");

            for(int i =0;i<=9;i++)
            {


                isFirstRunStream = contextForFragment.getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                        .getBoolean("isFirstRunStream", true);

                if (isFirstRunStream) {
                    new AllNotifications(i,linearLayoutID1,WORKSPACE_ID1, FUNCTION_ID1, ACTION_ID1).execute(serverUrl);
                }
                else{

                    SharedPreferences prefs = contextForFragment.getSharedPreferences(PREFS_NAME, 0);
                    String pathtoget =prefs.getString("imagepath2"+Integer.toString(i),null);
                    loadImageFromStorage2( pathtoget,Integer.toString(i));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Rating wise colleges
        try {
            String linearLayoutID1="RATING_WISE_CLGS";
            String WORKSPACE_ID1= "HOME_WORKSPACE";
            String FUNCTION_ID1 = "GET_LINEAR_DATA";
            String ACTION_ID1 = "RATING_WISE_CLGS";

            String  server =  Util.getProperty("name", contextForFragment);
            serverUrl= server.concat("/HomeAllNotifications");

            for(int i =0;i<=9;i++)
            {



                isFirstRunRating = contextForFragment.getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                        .getBoolean("isFirstRunRating", true);


                if (isFirstRunRating) {
                    new AllNotifications(i,linearLayoutID1,WORKSPACE_ID1, FUNCTION_ID1, ACTION_ID1).execute(serverUrl);
                }
                else{

                    SharedPreferences prefs = contextForFragment.getSharedPreferences(PREFS_NAME, 0);
                    String pathtoget =prefs.getString("imagepath3"+Integer.toString(i),null);
                    loadImageFromStorage3( pathtoget,Integer.toString(i));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
*/

/*         final FloatingActionButton news_askus = xtest.findViewById(R.id.news_askus);
        final int[] change = new int[1];
        carrerGridList.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                change[0] =dy;
                if (dy > 0 && news_askus.isShown())
                    news_askus.hide();
                if (dy < 0)
                    news_askus.show();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    news_askus.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }


        });
        news_askus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),PostQuestion.class);
                transitionTo(i);
                // dandelogin();

            }
        });*/



        //mDrawerList.setAnimation();
 /*       int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing7);
        carrerGridList.addItemDecoration(new SpacesItemDecorHomeActivity(spacingInPixels));
        carrerGridList.setItemAnimator(new DefaultItemAnimator());*/
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

/*        try {
            String  server =  Util.getProperty("name", contextForFragment);
            serverUrl= server.concat("/HomeLatestNewsUpdates");
            jsonurl= server.concat("/HomeAllNotificationsString");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i =0;i<=7;i++)
        {
            new GetDataNewsNotifyTask(i).execute(serverUrl);
        }
        String WORKSPACE_ID ="HOME_WORKSPACE";
        String FUNCTION_ID = "GET_NEWS_NOTIFICATIONS";
        String ACTION_ID = "GET_ALL_NEWS_STRING";

        new GetStringNewsNotifyTask(WORKSPACE_ID,FUNCTION_ID,ACTION_ID).execute(jsonurl);*/


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





    public class AllNotifications  extends AsyncTask<String, Void, Bitmap>

    {
        int imageResId;
        int imagetest;
        private WeakReference<Bitmap> imageViewReference;
        private int data = 0;
        private String layoutid=null;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;

        public AllNotifications(int position ,String layoutID,String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            imagetest = position;
            layoutid=layoutID;
        }

        public AllNotifications(ImageView imageView, int resId) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageResId = resId;

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
            try {
                xy = downloadUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return xy;
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

            JSONObject json = new JSONObject();

            JSONObject Record = new JSONObject();
            try {
                Record.put( "ACTION_ID", ActionID);
                Record.put( "FUNCTION_ID", functionID);
                Record.put( "WORKSPACE_ID", WorkspaceID);
                Record.put( "POSITION_NO", imagetest);
                json.put( "datatohost", Record);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write("ServerData="+json.toString());

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

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(is));
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

                options.inSampleSize = calculateInSampleSize(options, reqWidth / 4, reqHeight / 4);
                options.inJustDecodeBounds = false;
                bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                // addBitmapToMemoryCache(String.valueOf(imageResId), bitmap1);
            }
            return bitmap1;



        }



        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Bitmap result) {
            imageViewReference = new WeakReference<Bitmap>(result);
            if(result!=null){
                if (layoutid.equalsIgnoreCase("CLGS_IN_CITIES")) {
                    xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
                    //arrayForAllNotificationsnew[imagetest] = result;

                    if(imagetest==9) {
                        mAdapter.notifyItemChanged(4);
                        xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                    }

                    //  ListofAllNotificationsAdaptor.notifyItemChanged(imagetest);
                    String path=  saveToInternalStorage1(result,Integer.toString(imagetest));

                    persistantData = contextForFragment.getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = persistantData.edit();

                    editor.putString("imagepath0"+Integer.toString(imagetest), path);
                    editor.commit();
                    if(imagetest==9){

                        contextForFragment.getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                                .putBoolean("isFirstRunCities", false).commit();
                    }
               /* pos=2;*/
                }
                if (layoutid.equalsIgnoreCase("CLGS_TOP_STATES")) {
                    xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
                    ArrayListdata1[imagetest]=result;
                    if(imagetest==9) {
                        mAdapter.notifyItemChanged(6);
                        xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                    }
                    // ListofAllNotificationsAdaptor1.notifyItemChanged(imagetest);

                    String path=  saveToInternalStorage2(result,Integer.toString(imagetest));

                    persistantData = contextForFragment.getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = persistantData.edit();

                    editor.putString("imagepath1"+Integer.toString(imagetest), path);
                    editor.commit();
                    if(imagetest==9){

                        contextForFragment.getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                                .putBoolean("isFirstRunStates", false).commit();
                    }
                }
                if (layoutid.equalsIgnoreCase("STREAM_WISE_CLGS")) {
                    xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
                    ArrayListdata2[imagetest]=result;
                    if(imagetest==9) {
                        mAdapter.notifyItemChanged(8);
                        xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                    }
                    //  ListofAllNotificationsAdaptor2.notifyItemChanged(imagetest);

                    String path=  saveToInternalStorage3(result,Integer.toString(imagetest));

                    persistantData = contextForFragment.getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = persistantData.edit();

                    editor.putString("imagepath2"+Integer.toString(imagetest), path);
                    editor.commit();
                    if(imagetest==9){

                        contextForFragment.getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                                .putBoolean("isFirstRunStream", false).commit();
                    }
                }
                if (layoutid.equalsIgnoreCase("RATING_WISE_CLGS")) {
                    xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
                    ArrayListdata3[imagetest]=result;
                    if(imagetest==9) {
                        mAdapter.notifyItemChanged(9);
                        xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                    }
                    String path=  saveToInternalStorage4(result,Integer.toString(imagetest));

                    persistantData = contextForFragment.getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = persistantData.edit();

                    editor.putString("imagepath3"+Integer.toString(imagetest), path);
                    editor.commit();
                    if(imagetest==9){

                        contextForFragment.getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                                .putBoolean("isFirstRunRating", false).commit();
                    }
                }
                if (layoutid.equalsIgnoreCase("nitsNewsNotificationsID")) {
                    ArrayListdata[imagetest]=result;
                    if(imagetest==7){}
                      //  nitsNewsNotifications.notifyDataSetChanged();

                }
           /*     if (layoutid.equalsIgnoreCase("LinearMainAdsID")) {
                    xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
                    Drawable d = new BitmapDrawable(contextForFragment.getResources(), result);
                   //IMAGES[imagetest]=d;
                    ImagesArray[imagetest]=result;
                    if(imagetest==3){
                        mAdapter.notifyItemChanged(0);
                        xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                    }
                    // viewpageAdaptor.notifyDataSetChanged();

                    //   Bitmap ing = result;
*//*                  String path=  saveToInternalStorage(result,Integer.toString(imagetest));

                     persistantData = contextAdaptor.getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = persistantData.edit();

                    editor.putString("imagepath"+Integer.toString(imagetest), path);
                    editor.commit();*//*
                    //loadImageFromStorage( path,Integer.toString(imagetest));
                }*/




            }
        }

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

        //          mAdapter.notifyDataSetChanged();
/*                mAdapter.notifyItemChanged(1);
                mAdapter.notifyItemChanged(5);
                mAdapter.notifyItemChanged(6);
                mAdapter.notifyItemChanged(7);
                mAdapter.notifyItemChanged(8);
                mAdapter.notifyItemChanged(9);
                mAdapter.notifyItemChanged(10);*/
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
            if (result==null)
            {
                result="";
            }
            if(result.equalsIgnoreCase("UNABLE_TO_REACH_WEB"))
            {
                xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else {

                try {
                    JSONObject obj = new JSONObject(ing);
                    Marker marker;
                    int lenghtl = obj.length();

                    for (int i = 0; i < lenghtl; i++) {
                        JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));

                        String StringNews = (String) clgdata.get("NEWS_HEADER");
                        String NewsId = (String) clgdata.get("NEWSID");
                        if(NewsId.equalsIgnoreCase("newsHeader"))
                        {
                            StringData[0]= StringNews;
                        }
                        if(NewsId.equalsIgnoreCase("news1"))
                            StringData[1]= StringNews;
                        if(NewsId.equalsIgnoreCase("news2"))
                            StringData[2]= StringNews;
                        if(NewsId.equalsIgnoreCase("news3"))
                            StringData[3]= StringNews;
                        if(NewsId.equalsIgnoreCase("news4"))
                            StringData[4]= StringNews;
                        if(NewsId.equalsIgnoreCase("news5"))
                            StringData[5]= StringNews;
                        if(NewsId.equalsIgnoreCase("news6"))
                            StringData[6]= StringNews;


                    }
              /*      mAdapter.notifyItemChanged(1);
                    mAdapter.notifyItemChanged(5);
                    mAdapter.notifyItemChanged(6);
                    mAdapter.notifyItemChanged(7);
                    mAdapter.notifyItemChanged(8);
                    mAdapter.notifyItemChanged(9);
                    mAdapter.notifyItemChanged(10);*/


                    //   LatLng sydney = new LatLng(clgLat,80);
                    //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
            }

        }
    }
    public void onClick(View view, int position) {
        int x = view.getId();

        selectItem(position);
    }
    private void selectItem(int position) {

        final String UniqueKey = "HOME_HEADER_NEWS";

        // Transition for fragment1
        Slide slideTransition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            slideTransition = new Slide(Gravity.LEFT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        }

        // ((HomeActivity)getActivity()).getSupportActionBar(). setHomeAsUpIndicator(R.drawable.back_button);
        Fragment fr =  ShowDetailNewsNotifications.newInstance(position, UniqueKey);
        fr.setReenterTransition(slideTransition);
        fr.setExitTransition(slideTransition);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            fr.setSharedElementEnterTransition(new ChangeBounds());
        }
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.view_onclick_frame,fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @SuppressWarnings("unchecked") void transitionTo(Intent i) {
        final Pair<View, String>[] pairs;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pairs = TransitionHelper.createSafeTransitionParticipants(getActivity(), true);

            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);
            //PostQuestion.this.finish();
            startActivity(i, transitionActivityOptions.toBundle());

        }
        else{
            startActivity(i);
            //PostQuestion.this.finish();
        }
    }
    private void myUpdateOperation(){

/*      android.support.v4.app.Fragment fr =  new HomeNewsNotifications();

        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.sample_content_fragment,fr);
        fragmentTransaction.commit();*/
        }



/*    private void loadImageFromStorage(String path, String i)
    {

        try {
            File f=new File(path, "position"+i+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

            ImagesArray[Integer.parseInt(i)]=b;



        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }*/
/*    private void loadImageFromStorage0(String path, String i)
    {

        try {
            File f=new File(path, "position"+i+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

            arrayForAllNotificationsnew[Integer.parseInt(i)]=b;



        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }*/
    private void loadImageFromStorage1(String path, String i)
    {

        try {
            File f=new File(path, "position"+i+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

            ArrayListdata1[Integer.parseInt(i)]=b;



        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
    private void loadImageFromStorage2(String path, String i)
    {

        try {
            File f=new File(path, "position"+i+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

            ArrayListdata2[Integer.parseInt(i)]=b;



        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
    private void loadImageFromStorage3(String path, String i)
    {

        try {
            File f=new File(path, "position"+i+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

            ArrayListdata3[Integer.parseInt(i)]=b;



        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
    private String saveToInternalStorage(Bitmap bitmapImage, String s){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"position"+s+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    private String saveToInternalStorage1(Bitmap bitmapImage, String s){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("topcities", MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"position"+s+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
    private String saveToInternalStorage2(Bitmap bitmapImage, String s){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("topstates", MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"position"+s+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
    private String saveToInternalStorage3(Bitmap bitmapImage, String s){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("streamwise", MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"position"+s+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
    private String saveToInternalStorage4(Bitmap bitmapImage, String s){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("ratingwise", MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"position"+s+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 2;

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
}
