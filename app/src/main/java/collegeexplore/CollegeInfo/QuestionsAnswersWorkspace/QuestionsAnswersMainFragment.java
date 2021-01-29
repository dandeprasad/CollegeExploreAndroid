package collegeexplore.CollegeInfo.QuestionsAnswersWorkspace;

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
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.support.v4.util.Pair;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.HashMap;

import collegeexplore.CollegeInfo.AllDetailsDescriptionWorkspace.ShowDetailNewsNotifications;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.SpacesItemDecorHomeActivity;
import collegeexplore.CollegeInfo.TransitionHelper;
import collegeexplore.CollegeInfo.Util;


public class QuestionsAnswersMainFragment extends Fragment implements QuesAnsMainAdaptor.OnItemClickListener {
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
    private static final int SPAN_COUNT = 3;
    Context contextForFragment;
    private LruCache<String, Bitmap> mMemoryCache;
    String serverUrl = null;
    QuesAnsMainAdaptor mAdapter;
    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    String jsonurl;
    public static Bitmap[] arrayOfBitmap= new Bitmap[8];
    ArrayList<HashMap> StringData=new ArrayList<HashMap>();

    /**
     * used for initiating the variables
     */
    public static QuestionsAnswersMainFragment newInstance() {
        QuestionsAnswersMainFragment f = new QuestionsAnswersMainFragment();


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

        return inflater.inflate(R.layout.home_questions_layout, container, false);
    }





    public void onViewCreated(View view, Bundle savedInstanceState) {
        xtest = view;






        /*Previously used ip addresses*/
        //  String stringUrl ="http://192.168.43.60:9544/ImageUploadServlet";
        // String stringUrl ="http://10.0.2.2:8085/androiddandeCheck/HomeNewsFragmwent";
        //String stringUrl ="http://10.0.2.2:9544/ImageUploadServlet";

        //Loading recycling horizontal view
        GridLayoutManager1 = new GridLayoutManager(getActivity(), SPAN_COUNT);


        carrerGridList = xtest.findViewById(R.id.recyclerQuestions);
        // carrerGridList.setNestedScrollingEnabled(false);
        carrerGridList.setLayoutManager(GridLayoutManager1);
        carrerGridList.setHasFixedSize(true);

        mAdapter = new QuesAnsMainAdaptor(this,contextForFragment,arrayOfBitmap,StringData);
        carrerGridList.setAdapter(mAdapter);


        GridLayoutManager.SpanSizeLookup onSpanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(mAdapter.getItemViewType(position)){
                    case 1:
                        return 3;
                    case 0:
                        return 3;
                    case 2:
                        return 3;
                    case 3:
                        return 3;
                    case 4:
                        return 3;
                    default:
                        return -1;
                }
            }
        };

        GridLayoutManager1.setSpanSizeLookup(onSpanSizeLookup);






        //mDrawerList.setAnimation();
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing5);
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
            //serverUrl= server.concat("/HomeLatestNewsUpdates");
            jsonurl= server.concat("/QuestionsMainPageData");
        } catch (IOException e) {
            e.printStackTrace();
        }
/*        for(int i =0;i<=7;i++)
        {
            new GetDataNewsNotifyTask(i).execute(serverUrl);
        }*/
        String WORKSPACE_ID ="HOME_WORKSPACE";
        String FUNCTION_ID = "GET_DATA_HOME";
        String ACTION_ID = "GET_ALL_QUES_ANS";

        new GetQuesAnsTask(WORKSPACE_ID,FUNCTION_ID,ACTION_ID).execute(jsonurl);

        final TextView serachRef = xtest.findViewById(R.id.ask_us);
/*        final ImageView action_searchRef = (ImageView)  xtest.findViewById(R.id.action_search);

        action_searchRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  dandelogin();

            }
        });*/
        serachRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),PostQuestion.class);
                transitionTo(i);
               // dandelogin();

            }
        });
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

            //for storing image data
            byte[] bytes = IOUtils.toByteArray(is);

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

            arrayOfBitmap[imagetest]=result;
            if (imagetest==7)
            {
                mAdapter.notifyDataSetChanged();}
            Bitmap ing = result;
           /* ImageView imageView= (ImageView) xtest.findViewById(R.id.news_img);
            imageView.setImageBitmap(ing);
            ImageView imageView1= (ImageView) xtest.findViewById(news_img1);
            imageView1.setImageBitmap(ing);
            ImageView imageView2= (ImageView) xtest.findViewById(news_img2);
            imageView2.setImageBitmap(ing);*/
            // imageView.setMaxWidth(400);


            //  progresdialoglistview.dismiss();
            // imageView.setLayoutParams(new LinearLayout.LayoutParams(200,500));
        }


    }

    public class GetQuesAnsTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        View objview;

        public GetQuesAnsTask() {

        }
        public GetQuesAnsTask(String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
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
            xtest.findViewById(R.id.marker_progress_ques).setVisibility(View.VISIBLE);

        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            String ing = result;


            try {
                JSONObject obj = new JSONObject(ing);
                Marker marker;
                int  lenghtl =obj.length();

                for (int i=0;i<lenghtl;i++)
                {
                    JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));
                    HashMap<String,String>hashmap= new HashMap();
   /*                 String QUESTION_ID = (String) clgdata.get("QUESTION_ID");
                    String USERANAME = (String) clgdata.get("USERANAME");
                    String USEREMAIL = (String) clgdata.get("USEREMAIL");
                    String QUESTION = (String) clgdata.get("QUESTION");
                    String QUEST_TAGS = (String) clgdata.get("QUEST_TAGS");
                    String TIME_POSTED = (String) clgdata.get("TIME_POSTED");
                    int ANSWERS_COUNT = (int) clgdata.get("ANSWERS_COUNT");
                    int FOLLOW_COUNT = (int) clgdata.get("FOLLOW_COUNT");
                    String ANSWER = (String) clgdata.get("ANSWER");
                    int LIKE = (int) clgdata.get("LIKE");
                    int UNLIKE = (int) clgdata.get("UNLIKE");*/
                    hashmap.put("QUESTION_ID",(String) clgdata.get("QUESTION_ID"));
                    hashmap.put("USERANAME",(String) clgdata.get("USERANAME"));
                    hashmap.put("USEREMAIL",(String) clgdata.get("USEREMAIL"));
                    hashmap.put("QUESTION",(String) clgdata.get("QUESTION"));
                    hashmap.put("QUEST_TAGS",(String) clgdata.get("QUEST_TAGS"));
                    hashmap.put("TIME_POSTED",(String) clgdata.get("TIME_POSTED"));
                    hashmap.put("ANSWERS_COUNT",String.valueOf(clgdata.get("ANSWERS_COUNT")));
                    hashmap.put("FOLLOW_COUNT",String.valueOf( clgdata.get("FOLLOW_COUNT")));
                    hashmap.put("VIEWS_COUNT",String.valueOf( clgdata.get("VIEWS_COUNT")));
                    hashmap.put("ANSWER",(String) clgdata.get("ANSWER"));
                    hashmap.put("LIKE",String.valueOf(clgdata.get("LIKE")));
                    hashmap.put("UNLIKE",String.valueOf(clgdata.get("UNLIKE")));
                    StringData.add(i,hashmap);
                    // LatLng ClgLatLong = new LatLng(clgLat,clgLng);
                    // marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).title(clgname));
                    // mMap.setMinZoomPreference(4);
                    // marker.setTag(clgID);
//.icon(BitmapDescriptorFactory.fromResource(R.drawable.maps_pin))

                }
                xtest.findViewById(R.id.marker_progress_ques).setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();


                //   LatLng sydney = new LatLng(clgLat,80);
                //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
            } catch (JSONException e) {
                e.printStackTrace();
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
        android.support.v4.app.Fragment fr =  ShowDetailNewsNotifications.newInstance(position, UniqueKey);

        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
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
