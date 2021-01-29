package collegeexplore.CollegeInfo.CollegesAtMaps.CollegesOnclick;

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
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import collegeexplore.CollegeInfo.AllDetailsDescriptionWorkspace.ShowDetailNewsNotifications;
import collegeexplore.CollegeInfo.ItemOffsetDecoration;
import collegeexplore.CollegeInfo.QuestionsAnswersWorkspace.AnswerQuestion;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.TransitionHelper;
import collegeexplore.CollegeInfo.Util;


public class CollegeNewsFragment extends Fragment implements CollegeNewsFragmentAdaptor.OnItemClickListener {
    String COLLEGEIDJSON;
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
    CollegeNewsFragmentAdaptor mAdapter;
    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    String jsonurl;
    private String[] drawerIcons;
    ArrayList<HashMap> StringData=new ArrayList<HashMap>();
    private ArrayList<Bitmap> arrayOfBitmap= new ArrayList();
    String TAG = "NewsFragement - ";

    /**
     * used for initiating the variables
     * @param collegeidToPass
     */
    public static CollegeNewsFragment newInstance(String collegeidToPass) {
        CollegeNewsFragment f = new CollegeNewsFragment();

        Bundle args = new Bundle();
        args.putString("COLLEGEID", collegeidToPass);
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

        COLLEGEIDJSON = getArguments() != null ? getArguments().getString("COLLEGEID") : "";
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

        return inflater.inflate(R.layout.clgclk_pure_news_layout, container, false);
    }





    public void onViewCreated(View view, Bundle savedInstanceState) {
        xtest = view;

/*
        Toolbar myToolbar = (Toolbar) xtest.findViewById(R.id.PurenewsToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("News");
        // Get a support ActionBar corresponding to this toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //final Toolbar HomeToolbarfrag = (Toolbar) findViewById(R.id.toolbar_collaspe);
        myToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                ((AppCompatActivity)getActivity()).onBackPressed();
            }

        });



*/

        /*Previously used ip addresses*/
        //  String stringUrl ="http://192.168.43.60:9544/ImageUploadServlet";
        // String stringUrl ="http://10.0.2.2:8085/androiddandeCheck/HomeNewsFragmwent";
        //String stringUrl ="http://10.0.2.2:9544/ImageUploadServlet";

        //Loading recycling horizontal view
        GridLayoutManager1 = new GridLayoutManager(getActivity(), SPAN_COUNT);


        carrerGridList = xtest.findViewById(R.id.news_recycleview);
        // carrerGridList.setNestedScrollingEnabled(false);
        carrerGridList.setLayoutManager(GridLayoutManager1);
        carrerGridList.setHasFixedSize(true);
       // carrerGridList.setNestedScrollingEnabled(false);
       // StringData = getResources().getStringArray(R.array.Navigation_data_icons);
        //StringData.clear();;
        mAdapter = new CollegeNewsFragmentAdaptor(contextForFragment,StringData,arrayOfBitmap, this);
        GridLayoutManager.SpanSizeLookup onSpanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(mAdapter.getItemViewType(position)){
                    case 0:
                        return 1;
                    case 1:
                        return 1;
                    case 3:
                        return 1;
                    default:
                        return -1;

                }
            }
        };
        GridLayoutManager1.setSpanSizeLookup(onSpanSizeLookup);
        mAdapter.setLoadMoreListener(new CollegeNewsFragmentAdaptor.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                carrerGridList.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = StringData.size() - 1;
                        loadMore(index);
                    }
                });
                //Calling loadMore function in Runnable to fix the
                // java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling error
            }
        });
        carrerGridList.setAdapter(mAdapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing6);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(contextForFragment,R.dimen.spacing41);
        carrerGridList.addItemDecoration(itemDecoration);
     //   carrerGridList.addItemDecoration(new SpacesItemDecorHomeActivity(spacingInPixels));
        //carrerGridList.addItemDecoration(new DividerItemDecoration(getApplicationContext(),R.drawable.com_facebook_button_login_logo));

        try {
            String  server =  Util.getProperty("name", contextForFragment);
            serverUrl= server.concat("/NewsImages");
            jsonurl= server.concat("/NewsStrings");

        int startIndex=0;
        int endDataindex=10;

                String WORKSPACE_ID1 = "HOME_WORKSPACE";
                String FUNCTION_ID1 = "GET_NEWS_ONLY";
                String ACTION_ID1 = "GET_ALL_NEWS_STRING";

/*        for(int i =0;i<=9;i++)
        {
            new GetDataNewsNotifyTask(WORKSPACE_ID1,FUNCTION_ID1,ACTION_ID1,i,startIndex,endDataindex).execute(serverUrl);
        }*/
        String WORKSPACE_ID ="HOME_WORKSPACE";
        String FUNCTION_ID = "GET_NEWS_ONLY";
        String ACTION_ID = "GET_CLG_NEWS_STRING";

            if(StringData.isEmpty()) {
                new GetStringNewsNotifyTask(WORKSPACE_ID, FUNCTION_ID, ACTION_ID, startIndex, endDataindex, COLLEGEIDJSON).execute(jsonurl);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadMore(int index){
        HashMap<String,String>hashmap= new HashMap();

        hashmap.put("LOAD_CHECK","notloaded");
        //add loading progress view
        StringData.add(hashmap);
        mAdapter.notifyItemInserted(StringData.size()-1);

        String WORKSPACE_ID ="HOME_WORKSPACE";
        String FUNCTION_ID = "GET_NEWS_ONLY";
      //  String ACTION_ID = "GET_ALL_NEWS_STRING";
        String ACTION_ID = "GET_CLG_NEWS_STRING";


      //  new GetStringNewsNotifyTask(WORKSPACE_ID,FUNCTION_ID,ACTION_ID,startIndex,endDataindex,COLLEGEIDJSON).execute(jsonurl);

        int startIndex=index+1;
        int endDataindex=index+10;

        new GetStringNewsLoadmore(WORKSPACE_ID,FUNCTION_ID,ACTION_ID,startIndex,endDataindex,COLLEGEIDJSON).execute(jsonurl);

        String WORKSPACE_ID1 = "HOME_WORKSPACE";
        String FUNCTION_ID1 = "GET_NEWS_ONLY";
        String ACTION_ID1 = "GET_ALL_NEWS_STRING";
/*
        for(int i =startIndex;i<=endDataindex;i++)
        {
            new GetDataNewsLoadmore(WORKSPACE_ID1,FUNCTION_ID1,ACTION_ID1,i,startIndex,endDataindex).execute(serverUrl);
        }*/
     /*   Call<List<MovieModel>> call = api.getMovies(index);
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {
                if(response.isSuccessful()){

                    //remove loading view
                    StringData.remove(StringData.size()-1);

                    List<MovieModel> result = response.body();
                    if(result.size()>0){
                        //add loaded data
                        movies.addAll(result);
                    }else{//result size 0 means there is no more data available at server
                        adapter.setMoreDataAvailable(false);
                        //telling adapter to stop calling load more as no more server data available
                        Toast.makeText(context,"No More Data Available",Toast.LENGTH_LONG).show();
                    }
                    mAdapter.notifyDataChanged();
                    //should call the custom method adapter.notifyDataChanged here to get the correct loading status
                }else{
                    Log.e(TAG," Load More Response Error "+String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {
                Log.e(TAG," Load More Response Error "+t.getMessage());
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

        final String UniqueKey = "ALL_NEWS_WORKSPACE";

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
        int SROW_INDEX;
        int EROW_INDEX;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        public GetDataNewsNotifyTask(String WorkID,String funtID,String ActID, int  imageView,int startIndex, int endDataindex) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            imagetest = imageView;
            SROW_INDEX=startIndex;
                    EROW_INDEX=  endDataindex;
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
           // xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
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
            JSONObject json = new JSONObject();

            JSONObject Record = new JSONObject();
            try {
                Record.put( "ACTION_ID", ActionID);
                Record.put( "FUNCTION_ID", functionID);
                Record.put( "WORKSPACE_ID", WorkspaceID);
                Record.put( "POSITION_NO", imagetest);
                Record.put( "SROW_INDEX", SROW_INDEX);
                Record.put( "EROW_INDEX", EROW_INDEX);
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
              //  xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else {

                arrayOfBitmap.add(imagetest,result);
                //  mAdapter.notifyDataSetChanged();
/*                mAdapter.notifyItemChanged(1);
                mAdapter.notifyItemChanged(2);
                mAdapter.notifyItemChanged(3);
                mAdapter.notifyItemChanged(4);
                mAdapter.notifyItemChanged(5);
                mAdapter.notifyItemChanged(6);*/


              //  xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
            //    mAdapter.notifyItemChanged(imagetest);
            }

        }


    }
    //async inner class
    public class GetDataNewsLoadmore  extends AsyncTask<String, Void, Bitmap>

    {
        int imageResId;
        int imagetest;
        private  WeakReference<ImageView> imageViewReference;
        private int data = 0;
        int SROW_INDEX;
        int EROW_INDEX;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        public GetDataNewsLoadmore(String WorkID,String funtID,String ActID, int  imageView,int startIndex, int endDataindex) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            imagetest = imageView;
            SROW_INDEX=startIndex;
            EROW_INDEX=  endDataindex;
        }


        public GetDataNewsLoadmore(ImageView imageView, int resId) {
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
                Record.put( "SROW_INDEX", SROW_INDEX);
                Record.put( "EROW_INDEX", EROW_INDEX);
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
               // xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else {

                arrayOfBitmap.add(imagetest,result);
                //  mAdapter.notifyDataSetChanged();
/*                mAdapter.notifyItemChanged(1);
                mAdapter.notifyItemChanged(2);
                mAdapter.notifyItemChanged(3);
                mAdapter.notifyItemChanged(4);
                mAdapter.notifyItemChanged(5);
                mAdapter.notifyItemChanged(6);*/


              //  xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);


                //notifyItemChanged(imagetest);
            }
            if(imagetest==EROW_INDEX) {
                //  mAdapter.notifyItemRangeChanged(SROW_INDEX,EROW_INDEX);
            }
        }


    }
    public class GetStringNewsNotifyTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        View objview;
        int SROW_INDEX;
        int EROW_INDEX;
        String COLLEGEID;

        public GetStringNewsNotifyTask(String WorkID, String funtID, String ActID, int startIndex, int endDataindex, String COLLEGEIDJSON) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            SROW_INDEX=startIndex;
            EROW_INDEX=endDataindex;
            COLLEGEID=COLLEGEIDJSON;
        }


        @Override
        protected  void onPreExecute()
        {
            // progresdialoglistview=ProgressDialog.show(getActivity(), "","Loading");
            // Log.e("onPreExecutive","called"+progresdialoglistview);
           // xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
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
                Record.put( "SROW_INDEX", SROW_INDEX);
                Record.put( "EROW_INDEX", EROW_INDEX);
                Record.put( "COLLEGE_ID", COLLEGEID);

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
              //  xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
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
                            String StringNews = (String) clgdata.get("NEWS_HEADER");
                            String NewsId = (String) clgdata.get("NEWSID");
                            String posteddate = (String) clgdata.get("POSTED_DATE");
                            String TAG_TYPE = (String) clgdata.get("TAG_TYPE");
                            String TAG_COLOR = (String) clgdata.get("TAG_COLOR");
                            String FULL_IMAGE = (String) clgdata.get("FULL_IMAGE");

                            hashmap.put("NEWS_HEADER",StringNews);
                            hashmap.put("NEWSID",NewsId);
                            hashmap.put("POSTED_DATE",posteddate);
                            hashmap.put("TAG_TYPE",TAG_TYPE);
                            hashmap.put("TAG_COLOR",TAG_COLOR);
                            hashmap.put("FULL_IMAGE",FULL_IMAGE);
                            hashmap.put("LOAD_CHECK","loaded");


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
                   // xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                }
            }
        }
    }

    public class GetStringNewsLoadmore  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        View objview;
        int SROW_INDEX;
        int EROW_INDEX;
        String COLLEGEID;

        public GetStringNewsLoadmore(String WorkID, String funtID, String ActID, int startIndex, int endDataindex, String COLLEGEIDJSON) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            SROW_INDEX=startIndex;
            EROW_INDEX=endDataindex;
            COLLEGEID=COLLEGEIDJSON;
        }


        @Override
        protected  void onPreExecute()
        {
            // progresdialoglistview=ProgressDialog.show(getActivity(), "","Loading");
            // Log.e("onPreExecutive","called"+progresdialoglistview);
         //   xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
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
                Record.put( "SROW_INDEX", SROW_INDEX);
                Record.put( "EROW_INDEX", EROW_INDEX);
                Record.put( "COLLEGE_ID", COLLEGEID);


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
            if (result == null || result.equals("{}")) {
                result="";}
            if(result.equalsIgnoreCase("UNABLE_TO_REACH_WEB"))
            {
               // xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else {
                if (result != "") {

                    try {
                        JSONObject obj = new JSONObject(ing);
                        //remove loading view
                        StringData.remove(StringData.size()-1);
                        Marker marker;
                        int lenghtl = obj.length();
                        //StringData=new  String[8];
                        for (int i = 0; i < lenghtl; i++) {
                            JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));
                            HashMap<String,String>hashmap= new HashMap();
                            String StringNews = (String) clgdata.get("NEWS_HEADER");
                            String NewsId = (String) clgdata.get("NEWSID");
                            String posteddate = (String) clgdata.get("POSTED_DATE");
                            String TAG_TYPE = (String) clgdata.get("TAG_TYPE");
                            String TAG_COLOR = (String) clgdata.get("TAG_COLOR");
                            String FULL_IMAGE = (String) clgdata.get("FULL_IMAGE");

                            hashmap.put("NEWS_HEADER",StringNews);
                            hashmap.put("NEWSID",NewsId);
                            hashmap.put("POSTED_DATE",posteddate);
                            hashmap.put("TAG_TYPE",TAG_TYPE);
                            hashmap.put("TAG_COLOR",TAG_COLOR);
                            hashmap.put("FULL_IMAGE",FULL_IMAGE);
                            hashmap.put("LOAD_CHECK","loaded");


                            StringData.add(hashmap);
                        }




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

                }
                else{//result size 0 means there is no more data available at server
                    mAdapter.setMoreDataAvailable(false);

                    StringData.remove(StringData.size()-1);

                   // xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                    //telling adapter to stop calling load more as no more server data available
                    Toast.makeText(xtest.getContext(), R.string.thats_all_for_now, Toast.LENGTH_LONG).show();
                }
                mAdapter.notifyDataChanged();

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
