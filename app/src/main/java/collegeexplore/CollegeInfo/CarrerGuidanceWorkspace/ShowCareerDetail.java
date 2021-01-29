

package collegeexplore.CollegeInfo.CarrerGuidanceWorkspace;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import collegeexplore.CollegeInfo.AllDetailsDescriptionWorkspace.ShowDetailNewsNotifications;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.Util;


public class ShowCareerDetail extends Fragment implements ShowCarrerAdaptor.OnItemClickListener {
    int mNum;
    ProgressDialog progresdialoglistview ;
    private RecyclerView newsRecyclerView;
    private String[] maincontents1;
    PopupWindow popupMessage;
    LinearLayout layoutOfPopup;
    TextView popupText;
    View xtest;
    private RecyclerView carrerGridList;
    private LinearLayoutManager GridLayoutManager1;
    private static final int SPAN_COUNT = 3;
    Context contextForFragment;
    private LruCache<String, Bitmap> mMemoryCache;
    String serverUrl = null;
    ShowCarrerAdaptor mAdapter;
    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    int mNum_position;
    ArrayList<String> detail_string_data=new ArrayList<String>();

    /**
     * used for initiating the variables
     * @param position
     */
    public static ShowCareerDetail newInstance(int position) {
        ShowCareerDetail f = new ShowCareerDetail();


        Bundle args = new Bundle();
        args.putInt("position_num", position);
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
        ((HomeActivity)getActivity()).getSupportActionBar(). setHomeAsUpIndicator(R.drawable.back_button);
       // ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Carrer Guidance");
                mNum_position = getArguments() != null ? getArguments().getInt("position_num") : 10;

        //memory cache management
/*        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };*/}

    /**
     * This is called soon after oncreate and expects a view return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        return inflater.inflate(R.layout.showdetail_carrerguid, container, false);
        return inflater.inflate(R.layout.carrer_guidance_layout, container, false);
    }





    public void onViewCreated(View view, Bundle savedInstanceState) {
        xtest = view;






        /*Previously used ip addresses*/
        //  String stringUrl ="http://192.168.43.60:9544/ImageUploadServlet";
        // String stringUrl ="http://10.0.2.2:8085/androiddandeCheck/HomeNewsFragmwent";
        //String stringUrl ="http://10.0.2.2:9544/ImageUploadServlet";

        //Loading recycling horizontal view
/*        GridLayoutManager1 = new LinearLayoutManager(getActivity());


        carrerGridList = (RecyclerView)xtest.findViewById(R.id.recyclerCarrerGuidance);
        // carrerGridList.setNestedScrollingEnabled(false);
        carrerGridList.setLayoutManager(GridLayoutManager1);
        carrerGridList.setHasFixedSize(true);

        mAdapter = new ShowCarrerAdaptor(this,contextForFragment,detail_string_data);
        carrerGridList.setAdapter(mAdapter);







        //mDrawerList.setAnimation();
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        carrerGridList.addItemDecoration(new SpacesItemDecorHomeActivity(spacingInPixels));
        carrerGridList.setItemAnimator(new DefaultItemAnimator());
        OnClickListener image_clicker = new OnClickListener(){
            public void onClick(View v){
                String x  = "tst";
            }
        };*/
        //All images to load from server
        //  imageView= (ImageView) xtest.findViewById(carrerGuidance_img0);

        //  imageView.setOnClickListener(image_clicker);

        //calling to test in cache whether images are availabe or not
        //  loadBitmap(carrerGuidance_img0, imageView);

        //getting the server ip and port number
        if(mNum_position==1) {
          //  calldataforrequest("GET_10TH_INFO");
            calldataforrequestString("GET_AFTER_12TH_INFO_STRING");
        }
        if(mNum_position==2) {
           // calldataforrequest("GET_AFTER_12TH_INFO");
            calldataforrequestString("GET_AFTER_GRAD_ARTS_INFO_STRING");
        }
        if(mNum_position==3) {
           // calldataforrequest("GET_AFTER_ENG_INFO");
            calldataforrequestString("GET_AFTER_GRAD_COMMERCE_INFO_STRING");
        }
        if(mNum_position==4) {
            //calldataforrequest("GET_BANK_INFO");
            calldataforrequestString("GET_AFTER_GRAD_SCIENCE_INFO_STRING");
        }
        if(mNum_position==5) {
            //calldataforrequest("GET_CA_INFO");
            calldataforrequestString("GET_AFTER_MEDICAL_INFO_STRING");
        }
        if(mNum_position==6) {
            //calldataforrequest("GET_SPORTS_OPP_INFO");
            calldataforrequestString("GET_BANKING_INFO_STRING");
        }
        if(mNum_position==7) {
            //calldataforrequest("GET_MEDIC_OPP_INFO");
            calldataforrequestString("GET_ENGINEERING_INFO_STRING");
        }
        if(mNum_position==8) {
            //calldataforrequest("GET_MEDIC_OPP_INFO");
            calldataforrequestString("GET_LAW_INFO_STRING");
        }
        if(mNum_position==9) {
            //calldataforrequest("GET_MEDIC_OPP_INFO");
            calldataforrequestString("GET_MEDICAL_INFO_STRING");
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

    {
               private String functionID;
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
            Bitmap bitmap1 = null;
            if (response==200) {
                is = conn.getInputStream();


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
            bitmap1 = BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
            // addBitmapToMemoryCache(String.valueOf(imageResId), bitmap1);
            }
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

            //arrayOfBitmap[imagetest]=result;

                mAdapter.notifyDataSetChanged();
            Bitmap ing = result;
           /* ImageView imageView= (ImageView) xtest.findViewById(R.id.news_img);
            imageView.setImageBitmap(ing);
            ImageView imageView1= (ImageView) xtest.findViewById(news_img1);
            imageView1.setImageBitmap(ing);
            ImageView imageView2= (ImageView) xtest.findViewById(news_img2);
            imageView2.setImageBitmap(ing);*/
            // imageView.setMaxWidth(400);



            // imageView.setLayoutParams(new LinearLayout.LayoutParams(200,500));
        }


    }
    public class GetStringShowDetailTask1  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        private int position_no;
        View objview;


        public GetStringShowDetailTask1(int mNum_position,String WorkID,String funtID,String ActID) {
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
//                Record.put( "POSITION_NO", position_no);
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


            try {
                JSONObject obj = new JSONObject(ing);
                Marker marker;
                int  lenghtl =obj.length();

                for (int i=0;i<lenghtl;i++)
                {
                    JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));

                    String StringNews =(String)  clgdata.get("DATA_DETAILS") ;
                    UpdateAsyncdata(StringNews);
                    //detail_string_data.add(i,StringNews);
                    // LatLng ClgLatLong = new LatLng(clgLat,clgLng);
                    // marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).title(clgname));
                    // mMap.setMinZoomPreference(4);
                    // marker.setTag(clgID);
//.icon(BitmapDescriptorFactory.fromResource(R.drawable.maps_pin))

                }
                //mAdapter.notifyDataSetChanged();


                //   LatLng sydney = new LatLng(clgLat,80);
                //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
    @Override
    public void onClick(View view, int position,String UniqueKey) {
        selectItem(position,UniqueKey);
    }
    private void selectItem(int position,String UniqueKey) {



        // ((HomeActivity)getActivity()).getSupportActionBar(). setHomeAsUpIndicator(R.drawable.back_button);
        android.support.v4.app.Fragment fr =  ShowDetailNewsNotifications.newInstance(position,UniqueKey);

        android.support.v4.app.FragmentManager fm = ((HomeActivity)contextForFragment).getSupportFragmentManager();
        // FragmentManager fm12 = contextAdaptor.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.sample_content_fragment,fr);
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
    public void UpdateAsyncdata(String StringNews) {
        TableLayout stk = xtest.findViewById(R.id.table_main_carrer);
        TableRow tbrow = new TableRow(contextForFragment);
        TableRow.LayoutParams tlp = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.MATCH_PARENT);
        tbrow.setLayoutParams(tlp);
        tbrow.setBackgroundResource(android.R.drawable.gallery_thumb);
        TextView t1v = new TextView(contextForFragment);
        t1v.setText(StringNews);
        t1v.setTextColor(Color.WHITE);
        t1v.setGravity(Gravity.LEFT);
        Drawable xw = contextForFragment.getResources().getDrawable(R.drawable.textinputborder1);
        t1v.setBackground(xw);
        tbrow.addView(t1v);


        stk.addView(tbrow);
    }
    public void calldataforrequest( String actionid) {
        try {
            String server = Util.getProperty("name", contextForFragment);
            serverUrl = server.concat("/ShowDetailCarrerguidance");

        } catch (IOException e) {
            e.printStackTrace();
        }
        String WORKSPACE_ID = "HOME_WORKSPACE";
        String FUNCTION_ID = "CARRER_GUIDANCE";
        String ACTION_ID =actionid ;
        int POSITION_NO=mNum_position;

        for (int i = 0; i <= 7; i++) {
            new GetDataCarrerTask(i,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);
        }
    }
    public void calldataforrequestString( String actionid) {
        try {
            String server = Util.getProperty("name", contextForFragment);
            serverUrl = server.concat("/ShowDetailCarrerguidance");

        } catch (IOException e) {
            e.printStackTrace();
        }
        String WORKSPACE_ID = "HOME_WORKSPACE";
        String FUNCTION_ID = "CARRER_GUIDANCE";
        String ACTION_ID =actionid ;
        int POSITION_NO=mNum_position;


            new GetStringShowDetailTask1(0,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);
        }

}
