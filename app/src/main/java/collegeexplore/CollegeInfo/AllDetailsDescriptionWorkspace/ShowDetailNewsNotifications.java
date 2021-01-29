package collegeexplore.CollegeInfo.AllDetailsDescriptionWorkspace;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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

import collegeexplore.CollegeInfo.HomeActivity;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.ShowNewsDetail;
import collegeexplore.CollegeInfo.SpacesItemDecorHomeActivity;
import collegeexplore.CollegeInfo.Util;


public class ShowDetailNewsNotifications extends Fragment implements ShowNewsNotificationsAdaptor.OnItemClickListener {
    int mNum_position;
    ProgressDialog progresdialoglistview ;
    private RecyclerView newsRecyclerView;
    private String[] maincontents1;
    PopupWindow popupMessage;
    LinearLayout layoutOfPopup;
    TextView popupText;
    View xtest;
    private RecyclerView carrerGridList;
    private GridLayoutManager GridLayoutManager1;
    private static final int SPAN_COUNT = 3;
    Context contextForFragment;
    private LruCache<String, Bitmap> mMemoryCache;
    String serverUrl = null;
    ShowNewsNotificationsAdaptor mAdapter;
    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    String UniqueTosend,position_ID_toSend;
    String extradata=null;
    CollapsingToolbarLayout collapsingToolbar;
    ArrayList<String> detail_string_data=new ArrayList<String>();
    public static Bitmap[] arrayOfBitmap= new Bitmap[1];

    /**
     * used for initiating the variables
     * @param position
     * @param uniqueKey
     */
    public static ShowDetailNewsNotifications newInstance(int position, String uniqueKey) {
        ShowDetailNewsNotifications f = new ShowDetailNewsNotifications();


        Bundle args = new Bundle();
        args.putInt("position_num", position);
        args.putString("UniqueKeyValue",uniqueKey);
        f.setArguments(args);

        return  f;
    }
    public static ShowDetailNewsNotifications newInstance(String id, String uniqueKey) {
        ShowDetailNewsNotifications f = new ShowDetailNewsNotifications();


        Bundle args = new Bundle();
        args.putString("position_ID", id);
        args.putString("UniqueKeyValue",uniqueKey);
        f.setArguments(args);

        return  f;
    }
    public static ShowDetailNewsNotifications newInstance(int position, String uniqueKey,ArrayList extradata) {
        ShowDetailNewsNotifications f = new ShowDetailNewsNotifications();


        Bundle args = new Bundle();
        args.putString("EXTRA_DATA", (String) extradata.get(0));
        args.putInt("position_num", position);
        args.putString("UniqueKeyValue",uniqueKey);
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
/*        RelativeLayout tv = (RelativeLayout) ((HomeActivity) getActivity()).findViewById(R.id.news_relativelayout);
        tv.setVisibility(View.GONE);
        ((HomeActivity)getActivity()).getSupportActionBar().hide();*/

        mNum_position = getArguments() != null ? getArguments().getInt("position_num") : 100;
        UniqueTosend = getArguments() != null ? getArguments().getString("UniqueKeyValue") : "";
        extradata = getArguments() != null ? getArguments().getString("EXTRA_DATA") : "";
        position_ID_toSend = getArguments() != null ? getArguments().getString("position_ID") : "";



    }

    /**
     * This is called soon after oncreate and expects a view return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.showdetail_carrerguid, container, false);
    }





    public void onViewCreated(View view, Bundle savedInstanceState) {
        xtest = view;




        ((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar) xtest.findViewById(R.id.toolbar_collaspe));
       ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       collapsingToolbar = xtest.findViewById(R.id.collapsing_toolbar);
       collapsingToolbar.setTitle(" ");
      //  collapsingToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_nav_back));
       // Toolbar dfd = xtest.findViewById(R.id.toolbar_collaspe);
        final Toolbar HomeToolbarfrag = xtest.findViewById(R.id.toolbar_collaspe);
        HomeToolbarfrag.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
                }

        });
      //  collapsingToolbar.setExpandedTitleColor(Color.parseColor("#00FFFFFF"));
       final CollapsingToolbarLayout collapsingToolbarLayout = xtest.findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = xtest.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {

                   /*BitmapDrawable ob = new BitmapDrawable(getResources(), arrayOfBitmap[0]);

                    collapsingToolbar.setBackground(ob);*/
                        //collapsingToolbar.setBackgroundColor(Color.parseColor("#ff0000"));}

                        collapsingToolbar.setTitle(getString(R.string.app_name));

                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        /*Previously used ip addresses*/
        //  String stringUrl ="http://192.168.43.60:9544/ImageUploadServlet";
        // String stringUrl ="http://10.0.2.2:8085/androiddandeCheck/HomeNewsFragmwent";
        //String stringUrl ="http://10.0.2.2:9544/ImageUploadServlet";

        //Loading recycling horizontal view
        GridLayoutManager1 = new GridLayoutManager(getActivity(), SPAN_COUNT);


        carrerGridList = xtest.findViewById(R.id.recyclerCarrerGuidance);
        // carrerGridList.setNestedScrollingEnabled(false);
        carrerGridList.setLayoutManager(GridLayoutManager1);
        carrerGridList.setHasFixedSize(true);

        mAdapter = new ShowNewsNotificationsAdaptor(this,contextForFragment,detail_string_data);
        carrerGridList.setAdapter(mAdapter);


        GridLayoutManager.SpanSizeLookup onSpanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(mAdapter.getItemViewType(position)){

                    case 0:
                        return 3;
                    default:
                        return -1;
                }
            }
        };

        GridLayoutManager1.setSpanSizeLookup(onSpanSizeLookup);






        //mDrawerList.setAnimation();
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
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
        if(UniqueTosend!=""&&UniqueTosend.equalsIgnoreCase("ALL_NEWS_WORKSPACE")){

            //getting the server ip and port number
            try {
                String  server =  Util.getProperty("name", contextForFragment);
                serverUrl= server.concat("/HomeDataShowDetailsPositionID");

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!(position_ID_toSend.isEmpty())) {
                String WORKSPACE_ID = "HOME_WORKSPACE";
                String FUNCTION_ID = "GET_ALL_NEWS_WORKSPACE";
                String ACTION_ID = "GET_ALL_NEWS_WORKSPACE_IMG";
                String POSITION_NO=position_ID_toSend;
                new GetDataCarrerTaskWithID(POSITION_NO,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);

            }

            if (!(position_ID_toSend.isEmpty())) {
                String WORKSPACE_ID = "HOME_WORKSPACE";
                String FUNCTION_ID = "GET_ALL_NEWS_WORKSPACE";
                String ACTION_ID = "GET_ALL_NEWS_WORKSPACE_STRINGS";
                String POSITION_NO=position_ID_toSend;
                new GetStringShowDetailTaskWithID(POSITION_NO,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);
            }}
        if(UniqueTosend!=""&&UniqueTosend.equalsIgnoreCase("ALL_EXAMS_WORKSPACE")){

            //getting the server ip and port number
            try {
                String  server =  Util.getProperty("name", contextForFragment);
                serverUrl= server.concat("/HomeDataShowDetailsPositionID");

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!(position_ID_toSend.isEmpty())) {
                String WORKSPACE_ID = "HOME_WORKSPACE";
                String FUNCTION_ID = "GET_ALL_EXAMS_WORKSPACE";
                String ACTION_ID = "GET_ALL_EXAMS_WORKSPACE_IMG";
                String POSITION_NO=position_ID_toSend;
                new GetDataCarrerTaskWithID(POSITION_NO,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);

            }

            if (!(position_ID_toSend.isEmpty())) {
                String WORKSPACE_ID = "HOME_WORKSPACE";
                String FUNCTION_ID = "GET_ALL_EXAMS_WORKSPACE";
                String ACTION_ID = "GET_ALL_EXAMS_WORKSPACE_STRINGS";
                String POSITION_NO=position_ID_toSend;
                new GetStringShowDetailTaskWithID(POSITION_NO,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);
            }}
        if(UniqueTosend!=""&&UniqueTosend.equalsIgnoreCase("MAIN_HEADER_ADS")){

            //getting the server ip and port number
            try {
                String  server =  Util.getProperty("name", contextForFragment);
                serverUrl= server.concat("/HomeDataShowDetails");

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mNum_position!=10) {
                String WORKSPACE_ID = "HOME_WORKSPACE";
                String FUNCTION_ID = "GET_SHOW_DETAIL";
                String ACTION_ID = "GET_MAIN_HEADER_ADS_IMG";
                int POSITION_NO=mNum_position;
                new GetDataCarrerTask(POSITION_NO,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);

            }

            if (mNum_position!=10) {
                String WORKSPACE_ID = "HOME_WORKSPACE";
                String FUNCTION_ID = "GET_SHOW_DETAIL";
                String ACTION_ID = "GET_MAIN_HEADER_ADS_STRINGS";
                int POSITION_NO=mNum_position;
                new GetStringShowDetailTask(POSITION_NO,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);
            }}
        if(UniqueTosend!=""&&UniqueTosend.equalsIgnoreCase("FESTS_LATEST_NOTIFY")){

            //getting the server ip and port number
            try {
                String  server =  Util.getProperty("name", contextForFragment);
                serverUrl= server.concat("/HomeDataShowDetails");

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mNum_position!=15) {
                String WORKSPACE_ID = "HOME_WORKSPACE";
                String FUNCTION_ID = "GET_SHOW_DETAIL";
                String ACTION_ID = "GET_FESTS_LATEST_IMG";
                int POSITION_NO=mNum_position;
                new GetDataCarrerTask(POSITION_NO,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);

            }

            if (mNum_position!=15) {
                String WORKSPACE_ID = "HOME_WORKSPACE";
                String FUNCTION_ID = "GET_SHOW_DETAIL";
                String ACTION_ID = "GET_FESTS_LATEST_STRINGS";
                int POSITION_NO=mNum_position;
                new GetStringShowDetailTask(POSITION_NO,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);
            }}
        if(UniqueTosend!=""&&UniqueTosend.equalsIgnoreCase("HOME_HEADER_NEWS")){

            //getting the server ip and port number
            try {
                String  server =  Util.getProperty("name", contextForFragment);
                serverUrl= server.concat("/HomeDataShowDetails");

            } catch (IOException e) {
                e.printStackTrace();
            }
        if (mNum_position!=10) {
            String WORKSPACE_ID = "HOME_WORKSPACE";
            String FUNCTION_ID = "GET_SHOW_DETAIL";
            String ACTION_ID = "GET_ALL_NEWS_IMAGES";
            int POSITION_NO=mNum_position;
            new GetDataCarrerTask(POSITION_NO,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);

        }

         if (mNum_position!=10) {
             String WORKSPACE_ID = "HOME_WORKSPACE";
             String FUNCTION_ID = "GET_SHOW_DETAIL";
             String ACTION_ID = "GET_ALL_NEWS_STRINGS";
             int POSITION_NO=mNum_position;
            new GetStringShowDetailTask(POSITION_NO,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);
        }}
        if(UniqueTosend!=""&&UniqueTosend.equalsIgnoreCase("HOME_NITS_NEWS_NOTIFYS")){

            //getting the server ip and port number
            try {
                String  server =  Util.getProperty("name", contextForFragment);
                serverUrl= server.concat("/HomeDataShowDetails");

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mNum_position!=10) {
                String WORKSPACE_ID = "HOME_WORKSPACE";
                String FUNCTION_ID = "GET_SHOW_DETAIL";
                String ACTION_ID = "HOME_SEC_LINEARDATA";
                int POSITION_NO=mNum_position;
                new GetDataCarrerTask(POSITION_NO,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);

            }

            if (mNum_position!=10) {
                String WORKSPACE_ID = "HOME_WORKSPACE";
                String FUNCTION_ID = "GET_SHOW_DETAIL";
                String ACTION_ID = "HOME_SEC_LINEARDATA_STRINGS";
                int POSITION_NO=mNum_position;
                new GetStringShowDetailTask(POSITION_NO,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);
            }}
        if(UniqueTosend!=""&&UniqueTosend.equalsIgnoreCase("NEWS_NOTIFY_ALLNOTIFYS")){

            //getting the server ip and port number
            try {
                String  server =  Util.getProperty("name", contextForFragment);
                serverUrl= server.concat("/HomeDataShowDetails");

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mNum_position!=10) {
                String WORKSPACE_ID = "HOME_WORKSPACE";
                String FUNCTION_ID = "GET_SHOW_DETAIL";
                String ACTION_ID = "GET_FIRSTLINEAR_DATA";
                int POSITION_NO=mNum_position;
                new GetDataCarrerTask(POSITION_NO,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);

            }

            if (mNum_position!=10) {
                String WORKSPACE_ID = "HOME_WORKSPACE";
                String FUNCTION_ID = "GET_SHOW_DETAIL";
                String ACTION_ID = "GET_FIRSTLINEAR_DATA_STRINGS";
                int POSITION_NO=mNum_position;
                new GetStringShowDetailTask(POSITION_NO,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);
            }}
    }


    //cache memory implementation
    public void loadBitmap(int resId, ImageView imageView) {
        final String imageKey = String.valueOf(resId);

        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
        else {
            new GetDataCarrerTask(imageView ,resId).execute(serverUrl);
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
    public class GetDataCarrerTask  extends AsyncTask<String, Void, Bitmap>

    {        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        int imageResId;
        int imagetest;
        private  WeakReference<ImageView> imageViewReference;
        private int data = 0;
        public GetDataCarrerTask(int  imageView,String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            imagetest = imageView;
        }



        public GetDataCarrerTask(ImageView imageView, int resId) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageResId = resId;
            imageViewReference = new WeakReference<ImageView>(imageView);
        }


        @Override
        protected  void onPreExecute()
        {
            // progresdialoglistview=ProgressDialog.show(getActivity(), "","Loading");
            // Log.e("onPreExecutive","called"+progresdialoglistview);

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
           // getArguments() != null ? getArguments().getString("EXTRA_DATA") : ""
            JSONObject Record = new JSONObject();
            try {
                Record.put( "ACTION_ID", ActionID);
                Record.put( "FUNCTION_ID", functionID);
                Record.put( "WORKSPACE_ID", WorkspaceID);
                Record.put( "POSITION_NO", imagetest);
                Record.put( "EXTRA_DATA", extradata!= null ?extradata : "");
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
            // Log.d(DEBUG_TAG, "The response is: " + response);
            if (response==200) {
                is = conn.getInputStream();
            }
            byte[] bytes=null;
if(is!=null){
            //for storing image data
           bytes = IOUtils.toByteArray(is);}

            //degrading the image required to pur requirement
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
            WindowManager wm = (WindowManager) contextForFragment.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int reqWidth = size.x;
            int reqHeight = size.y;

            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight/4);
            options.inJustDecodeBounds = false;
            final Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
            // addBitmapToMemoryCache(String.valueOf(imageResId), bitmap1);
            return bitmap1;



        }



        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Bitmap result) {

            // value.checkImageshow(ing);
            // ImageView  imageView=(ImageView)findViewById(R.id.imageView4);
            //  textView.setText(result);

            // Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.sample_3);//assign your bitmap;
            // Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.sample_4);//assign your bitmap;

            arrayOfBitmap[0]=result;

                mAdapter.notifyDataSetChanged();
            Bitmap ing = result;
       ImageView imageView= xtest.findViewById(R.id.backdrop);
           imageView.setImageBitmap(arrayOfBitmap[0]);
            /* ImageView imageView1= (ImageView) xtest.findViewById(news_img1);
            imageView1.setImageBitmap(ing);
            ImageView imageView2= (ImageView) xtest.findViewById(news_img2);
            imageView2.setImageBitmap(ing);*/
            // imageView.setMaxWidth(400);


            //  progresdialoglistview.dismiss();
            // imageView.setLayoutParams(new LinearLayout.LayoutParams(200,500));
        }


    }
    public class GetDataCarrerTaskWithID  extends AsyncTask<String, Void, Bitmap>

    {        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        int imageResId;
        String imagetest;
        private  WeakReference<ImageView> imageViewReference;
        private int data = 0;
        public GetDataCarrerTaskWithID(String  imageView,String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            imagetest = imageView;
        }



        public GetDataCarrerTaskWithID(ImageView imageView, int resId) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageResId = resId;
            imageViewReference = new WeakReference<ImageView>(imageView);
        }


        @Override
        protected  void onPreExecute()
        {
            // progresdialoglistview=ProgressDialog.show(getActivity(), "","Loading");
            // Log.e("onPreExecutive","called"+progresdialoglistview);

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
            // getArguments() != null ? getArguments().getString("EXTRA_DATA") : ""
            JSONObject Record = new JSONObject();
            try {
                Record.put( "ACTION_ID", ActionID);
                Record.put( "FUNCTION_ID", functionID);
                Record.put( "WORKSPACE_ID", WorkspaceID);
                Record.put( "POSITION_ID", imagetest);
                Record.put( "EXTRA_DATA", extradata!= null ?extradata : "");
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
            // Log.d(DEBUG_TAG, "The response is: " + response);
            if (response==200) {
                is = conn.getInputStream();
            }
            byte[] bytes=null;
            if(is!=null){
                //for storing image data
                bytes = IOUtils.toByteArray(is);}

            //degrading the image required to pur requirement
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
            WindowManager wm = (WindowManager) contextForFragment.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int reqWidth = size.x;
            int reqHeight = size.y;

            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight/4);
            options.inJustDecodeBounds = false;
            final Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
            // addBitmapToMemoryCache(String.valueOf(imageResId), bitmap1);
            return bitmap1;



        }



        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Bitmap result) {

            // value.checkImageshow(ing);
            // ImageView  imageView=(ImageView)findViewById(R.id.imageView4);
            //  textView.setText(result);

            // Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.sample_3);//assign your bitmap;
            // Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.sample_4);//assign your bitmap;

            arrayOfBitmap[0]=result;

            mAdapter.notifyDataSetChanged();
            Bitmap ing = result;
            ImageView imageView= xtest.findViewById(R.id.backdrop);
            imageView.setImageBitmap(arrayOfBitmap[0]);
            /* ImageView imageView1= (ImageView) xtest.findViewById(news_img1);
            imageView1.setImageBitmap(ing);
            ImageView imageView2= (ImageView) xtest.findViewById(news_img2);
            imageView2.setImageBitmap(ing);*/
            // imageView.setMaxWidth(400);


            //  progresdialoglistview.dismiss();
            // imageView.setLayoutParams(new LinearLayout.LayoutParams(200,500));
        }


    }
    public class GetStringShowDetailTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        private int position_no;
        View objview;


        public GetStringShowDetailTask(int mNum_position,String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            position_no= mNum_position;
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
                Record.put( "POSITION_NO", position_no);
                Record.put( "EXTRA_DATA", extradata!= null ?extradata : "");
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

            String StringNews = (String) clgdata.get("DATA_DETAILS");
            String StringHeader = (String) clgdata.get("DATA_HEADER");
            detail_string_data.add(i, StringHeader);
            detail_string_data.add(i + 1, StringNews);

/*            TextView textView= (TextView) xtest.findViewById(R.id.AllHeader);
            textView.setText(StringHeader);
            textView.setTextSize(25);
            textView.setTextColor(Color.parseColor("#FF1ACA7B"));*/
           /* TextView textView1= (TextView) xtest.findViewById(R.id.product_details_title);
            textView1.setText(StringHeader);*/
           /* Toolbar  tooolbar = (Toolbar) xtest.findViewById(R.id.toolbar_collaspe);
            tooolbar.setSubtitle(StringHeader);*/
            // LatLng ClgLatLong = new LatLng(clgLat,clgLng);
            // marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).title(clgname));
            // mMap.setMinZoomPreference(4);
            // marker.setTag(clgID);
//.icon(BitmapDescriptorFactory.fromResource(R.drawable.maps_pin))

        }
        mAdapter.notifyDataSetChanged();


        //   LatLng sydney = new LatLng(clgLat,80);
        //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
    } catch (JSONException e) {
        e.printStackTrace();
    }

}
        }
    }
    public class GetStringShowDetailTaskWithID  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        private String position_no;
        View objview;


        public GetStringShowDetailTaskWithID(String mNum_position,String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            position_no= mNum_position;
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
                Record.put( "POSITION_ID", position_no);
                Record.put( "EXTRA_DATA", extradata!= null ?extradata : "");
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

                        String StringNews = (String) clgdata.get("DATA_DETAILS");
                        String StringHeader = (String) clgdata.get("DATA_HEADER");
                        detail_string_data.add(i, StringHeader);
                        detail_string_data.add(i + 1, StringNews);

/*            TextView textView= (TextView) xtest.findViewById(R.id.AllHeader);
            textView.setText(StringHeader);
            textView.setTextSize(25);
            textView.setTextColor(Color.parseColor("#FF1ACA7B"));*/
           /* TextView textView1= (TextView) xtest.findViewById(R.id.product_details_title);
            textView1.setText(StringHeader);*/
           /* Toolbar  tooolbar = (Toolbar) xtest.findViewById(R.id.toolbar_collaspe);
            tooolbar.setSubtitle(StringHeader);*/
                        // LatLng ClgLatLong = new LatLng(clgLat,clgLng);
                        // marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).title(clgname));
                        // mMap.setMinZoomPreference(4);
                        // marker.setTag(clgID);
//.icon(BitmapDescriptorFactory.fromResource(R.drawable.maps_pin))

                    }
                    mAdapter.notifyDataSetChanged();


                    //   LatLng sydney = new LatLng(clgLat,80);
                    //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public void onClick(View view, int position) {

        selectItem(position);
    }
    private void selectItem(int position) {


        switch (position) {
            case 1:
                Intent intent = new Intent(getActivity(),
                        collegeexplore.CollegeInfo.NitInformation.NitMainContentInfo.class);
                startActivity(intent);
            case 2:
                // ((HomeActivity)getActivity()).getSupportActionBar(). setHomeAsUpIndicator(R.drawable.back_button);
                android.support.v4.app.Fragment fr =  new ShowNewsDetail();

                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.sample_content_fragment,fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
        }
    }
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

}
