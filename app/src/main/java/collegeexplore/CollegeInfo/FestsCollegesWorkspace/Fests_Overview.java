package collegeexplore.CollegeInfo.FestsCollegesWorkspace;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v4.widget.SwipeRefreshLayout;
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

import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.SpacesItemDecorHomeActivity;
import collegeexplore.CollegeInfo.Util;


public class Fests_Overview extends Fragment implements FestsOverviewAdaptor.OnItemClickListener {
    String  COLLEGE_ID;
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
    FestsOverviewAdaptor mAdapter;
    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    String jsonurl;
    static final String LOG_TAG = "HomeActivity";
    public static Bitmap[] arrayOfBitmap= new Bitmap[10];
   // String StringData[]=new  String[7];
    SwipeRefreshLayout mySwipeRefreshLayout;
    ArrayList<HashMap<String, String>> StringData = new ArrayList<HashMap<String, String>>();
    /**
     * used for initiating the variables
     */
    public static Fests_Overview newInstance(String COLLEGE_ID) {
        Fests_Overview f = new Fests_Overview();


        Bundle args = new Bundle();
        args.putString("COLLEGE_ID", COLLEGE_ID);
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

        COLLEGE_ID = getArguments() != null ? getArguments().getString("COLLEGE_ID") : "";

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

        return inflater.inflate(R.layout.fests_overiew, container, false);
    }





    public void onViewCreated(View view, Bundle savedInstanceState) {
        xtest = view;


        GridLayoutManager1 = new GridLayoutManager(getActivity(), SPAN_COUNT);


        carrerGridList = xtest.findViewById(R.id.recyclefsetoverview);
        // carrerGridList.setNestedScrollingEnabled(false);
        carrerGridList.setLayoutManager(GridLayoutManager1);
        carrerGridList.setHasFixedSize(true);

        mAdapter = new FestsOverviewAdaptor(this,contextForFragment,arrayOfBitmap,StringData);
        carrerGridList.setAdapter(mAdapter);


        GridLayoutManager.SpanSizeLookup onSpanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(mAdapter.getItemViewType(position)){

                    case 3:
                        return 1;

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

            jsonurl= server.concat("/Festsdata");
        } catch (IOException e) {
            e.printStackTrace();
        }
/*        for(int i =0;i<=9;i++)
        {
            new GetDataNewsNotifyTask(i).execute(serverUrl);
        }*/
        String WORKSPACE_ID ="HOME_WORKSPACE";
        String FUNCTION_ID = "GET_FESTS_OVERVIEW";
        String ACTION_ID = "GET_FESTS_OVERVIEW";

        new GetStringFestsTask(WORKSPACE_ID,FUNCTION_ID,ACTION_ID,COLLEGE_ID).execute(jsonurl);


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

    public class GetStringFestsTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID,COLLEGE_ID;
        View objview;

        public GetStringFestsTask() {

        }
        public GetStringFestsTask(String WorkID,String funtID,String ActID,String CLG_ID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            COLLEGE_ID=CLG_ID;

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
                Record.put( "FEST_ID", COLLEGE_ID);

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



                        String fest_id = (String) clgdata.get("fest_id");
                        String fest_college_name = (String) clgdata.get("fest_college_name");
                        String college_website = (String) clgdata.get("college_website");

                        String fest_name = (String) clgdata.get("fest_name");
                        String fest_caption = (String) clgdata.get("fest_caption");
                        String fest_type = (String) clgdata.get("fest_type");

                        String fest_description = (String) clgdata.get("fest_description");
                        String fest_depart = (String) clgdata.get("fest_depart");
                        String start_date = (String) clgdata.get("start_date");
                        String end_date = (String) clgdata.get("end_date");
                        String banner_logos = (String) clgdata.get("banner_logos");
/*                        ImageView dande = xtest.findViewById(R.id.htab_header);
if(null!=banner_logos) {
    Picasso.with(contextForFragment).load(banner_logos).into(dande);
}*/
                        HashMap<String, String> param_aux = new HashMap<String, String>();

                        param_aux.put("fest_id", fest_id);
                        param_aux.put("fest_college_name", fest_college_name);

                        param_aux.put("college_website", college_website);
                        param_aux.put("fest_name", fest_name);
                        param_aux.put("fest_caption", fest_caption);
                        param_aux.put("fest_type", fest_type);

                        param_aux.put("fest_description", fest_description);
                        param_aux.put("fest_depart", fest_depart);
                        param_aux.put("start_date", start_date);
                        param_aux.put("end_date", end_date);
                        param_aux.put("banner_logos", banner_logos);

                        StringData.add(param_aux);

                    }
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
    public void onClick(View view, int position, String website) {
        int x = view.getId();

        selectItem(position,website);
    }
    private void selectItem(int position,String website ){
        String url =website;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);





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
    private void myUpdateOperation(){

/*      android.support.v4.app.Fragment fr =  new HomeNewsNotifications();

        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.sample_content_fragment,fr);
        fragmentTransaction.commit();*/
        mySwipeRefreshLayout.setRefreshing(false);}
}
