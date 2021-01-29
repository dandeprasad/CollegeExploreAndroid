package collegeexplore.CollegeInfo.NewsandNotificationsWorkspace;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import collegeexplore.CollegeInfo.AllDetailsDescriptionWorkspace.ShowDetailNewsNotifications;
import collegeexplore.CollegeInfo.HomeActivity;
import collegeexplore.CollegeInfo.QuestionsAnswersWorkspace.AnswerQuestion;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.TransitionHelper;
import collegeexplore.CollegeInfo.Util;


public class HomeDetailedData extends Fragment implements HomeDetailedDataAdaptor.OnItemClickListener {
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
    private static final int SPAN_COUNT = 1;
    Context contextForFragment;
    private LruCache<String, Bitmap> mMemoryCache;
    String serverUrl = null;
    HomeDetailedDataAdaptor mAdapter;
    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    Toolbar HomeToolbar;
    String jsonurl;
    private String[] drawerIcons;
    ArrayList<HashMap> StringData=new ArrayList<HashMap>();
    public Bitmap[] arrayOfBitmap=  new Bitmap[29];

    /**
     * used for initiating the variables
     */
    public static HomeDetailedData newInstance() {
        HomeDetailedData f = new HomeDetailedData();


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

        return inflater.inflate(R.layout.home_detailed_data, container, false);
    }





    public void onViewCreated(View view, Bundle savedInstanceState) {
        xtest = view;


        ((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar) xtest.findViewById(R.id.toolbar_home_detailed));
        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Toolbar HomeToolbarfrag = xtest.findViewById(R.id.toolbar_home_detailed);
        String dande=xtest.getResources().getString(R.string.clgs_in_topstates);
        HomeToolbarfrag.setTitle(dande);
        HomeToolbarfrag.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*RelativeLayout tv = (RelativeLayout) ((HomeActivity) getActivity()).findViewById(R.id.news_relativelayout);
                tv.setVisibility(View.VISIBLE);*/
                ((HomeActivity)getActivity()).getSupportActionBar().show();
                getActivity().onBackPressed();
            }

        });


        /*Previously used ip addresses*/
        //  String stringUrl ="http://192.168.43.60:9544/ImageUploadServlet";
        // String stringUrl ="http://10.0.2.2:8085/androiddandeCheck/HomeNewsFragmwent";
        //String stringUrl ="http://10.0.2.2:9544/ImageUploadServlet";

        //Loading recycling horizontal view
        GridLayoutManager1 = new GridLayoutManager(getActivity(), SPAN_COUNT);


        carrerGridList = xtest.findViewById(R.id.home_detailed_recycleview);
        // carrerGridList.setNestedScrollingEnabled(false);
        carrerGridList.setLayoutManager(GridLayoutManager1);
        carrerGridList.setHasFixedSize(true);

        // StringData = getResources().getStringArray(R.array.Navigation_data_icons);
        mAdapter = new HomeDetailedDataAdaptor(StringData,arrayOfBitmap, this);
        carrerGridList.setAdapter(mAdapter);
        /*int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        carrerGridList.addItemDecoration(new SpacesItemDecorHomeActivity(spacingInPixels));*/
        //carrerGridList.addItemDecoration(new DividerItemDecoration(getApplicationContext(),R.drawable.com_facebook_button_login_logo));

        try {
            String  server =  Util.getProperty("name", contextForFragment);
            serverUrl= server.concat("/HomeDetailedDataImages");
            jsonurl= server.concat("/HomeDetailedDataStrings");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String WORKSPACE_ID ="HOME_WORKSPACE";
        String FUNCTION_ID = "GET_STATES";
        String ACTION_ID = "GET_ALL_STATES_STRING";

        new GetStringNewsNotifyTask(WORKSPACE_ID,FUNCTION_ID,ACTION_ID).execute(jsonurl);

        for(int i =0;i<=28;i++)
        {
            new GetDataNewsNotifyTask(i).execute(serverUrl);
        }

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
/*
        try {
            String  server =  Util.getProperty("name", contextForFragment);
            //serverUrl= server.concat("/HomeLatestNewsUpdates");
            jsonurl= server.concat("/QuestionsMainPageData");
        } catch (IOException e) {
            e.printStackTrace();
        }
*//*        for(int i =0;i<=7;i++)
        {
            new GetDataNewsNotifyTask(i).execute(serverUrl);
        }*//*
        String WORKSPACE_ID ="HOME_WORKSPACE";
        String FUNCTION_ID = "GET_DATA_HOME";
        String ACTION_ID = "GET_ALL_QUES_ANS";

        new GetQuesAnsTask(WORKSPACE_ID,FUNCTION_ID,ACTION_ID).execute(jsonurl);

        final TextView serachRef = (TextView) xtest.findViewById(R.id.ask_us);
        final ImageView action_searchRef = (ImageView)  xtest.findViewById(R.id.action_search);

        action_searchRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  dandelogin();

            }
        });
        serachRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),PostQuestion.class);
                transitionTo(i);
                // dandelogin();

            }
        });*/
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

    @Override
    public void onClick(View view, String position) {
        selectItem(position);
    }
    private void selectItem(String Id) {

        final String UniqueKey = "ALL_EXAMS_WORKSPACE";

        // Transition for fragment1
        Slide slideTransition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            slideTransition = new Slide(Gravity.LEFT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        }

        // ((HomeActivity)getActivity()).getSupportActionBar(). setHomeAsUpIndicator(R.drawable.back_button);
        Fragment fr =  ShowDetailNewsNotifications.newInstance(Id, UniqueKey);
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

                arrayOfBitmap[imagetest]=result;

                //  mAdapter.notifyDataSetChanged();
/*                mAdapter.notifyItemChanged(1);
                mAdapter.notifyItemChanged(2);
                mAdapter.notifyItemChanged(3);
                mAdapter.notifyItemChanged(4);
                mAdapter.notifyItemChanged(5);
                mAdapter.notifyItemChanged(6);*/


                xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
            }
            mAdapter.notifyItemChanged(imagetest);
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
            if (result == null) {
                result="";}
            if(result.equalsIgnoreCase("UNABLE_TO_REACH_WEB"))
            {
                xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else {
                if (result != "") {

                    try {
                        JSONObject obj = new JSONObject(ing);
                        Marker marker;
                        int lenghtl = obj.length();
                        //StringData=new  String[8];
                        for (int i = 0; i < lenghtl; i++) {
                            JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));
                            HashMap<String,String>hashmap= new HashMap();
                            String StringExams = (String) clgdata.get("STATES_HEADER");
                            String ExamsId = (String) clgdata.get("STATESID");
                            String posteddate = (String) clgdata.get("POSTED_DATE");
                            hashmap.put("EXAMS_HEADER",StringExams);
                            hashmap.put("EXAMSID",ExamsId);
                            hashmap.put("POSTED_DATE",posteddate);


                            StringData.add(hashmap);
                        }
                        mAdapter.notifyDataSetChanged();
                        /*mAdapter.notifyItemChanged(2);
                        mAdapter.notifyItemChanged(3);
                        mAdapter.notifyItemChanged(4);
                        mAdapter.notifyItemChanged(5);
                        mAdapter.notifyItemChanged(6);
                        mAdapter.notifyItemChanged(7);*/


                        //   LatLng sydney = new LatLng(clgLat,80);
                        //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                }
            }
        }
    }
    public void onClick(View view, int position, String text, String aHolderMainQuestionText) {
        int x = view.getId();
        Intent i = new Intent(getContext(),AnswerQuestion.class);
        i.putExtra("QUES_ID",text);
        i.putExtra("QUESTION",aHolderMainQuestionText);
        transitionTo(i);
        // selectItem(position);
    }
    public void onClick(View view, int position, String text) {
        int x = view.getId();
        Intent i = new Intent(getContext(),AnswerQuestion.class);
        i.putExtra("QUES_ID",text);
        //   transitionToActivity(SharedElementActivity.class, viewHolder, sample);
        // i.putExtra("QUESTION",aHolderMainQuestionText);
        transitionTo(i);
        // selectItem(position);
    }
    private void selectItem(int position) {

        final String UniqueKey = "HOME_HEADER_NEWS";

        // ((HomeActivity)getActivity()).getSupportActionBar(). setHomeAsUpIndicator(R.drawable.back_button);
        Fragment fr =  ShowDetailNewsNotifications.newInstance(position, UniqueKey);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.view_onclick_frame,fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

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
