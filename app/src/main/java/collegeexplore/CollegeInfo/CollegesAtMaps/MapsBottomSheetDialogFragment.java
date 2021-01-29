package collegeexplore.CollegeInfo.CollegesAtMaps;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

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
import java.net.HttpURLConnection;
import java.net.URL;

import collegeexplore.CollegeInfo.CollegesAtMaps.CollegesOnclick.collegebottomviewdrawer;
import collegeexplore.CollegeInfo.R;

/**
 * Created by MG on 17-07-2016.
 */
public class  MapsBottomSheetDialogFragment extends BottomSheetDialogFragment implements collegebottomviewdrawer.OnItemClickListener,CollegeQuestions.OnItemClickListener {

    String mString;
    String COLLEGE_ID;
    String CollegeString;
    String clglogo;
    View v;
    collegebottomviewdrawer madaptor;
    CollegeQuestions madaptor1;
    private String[] maincontents1;
    private RecyclerView newsRecyclerView;
    private RecyclerView newsRecyclerView1;
    public static Bitmap[] arrayOfBitmap1= new Bitmap[30];
    public static Bitmap[] arrayOfBitmap2= new Bitmap[5];
    Context contextForFragment;
    static MapsBottomSheetDialogFragment newInstance(String collegeID, String string, String collegeName, String logo) {
        MapsBottomSheetDialogFragment f = new MapsBottomSheetDialogFragment();
        Bundle args = new Bundle();
        args.putString("collegeID", collegeID);
        args.putString("string", string);
        args.putString("collegeName", collegeName);
        args.putString("collegeLogo", logo);
        f.setArguments(args);
        return f;
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        contextForFragment  = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mString = getArguments().getString("string");
        CollegeString = getArguments().getString("collegeName");
        COLLEGE_ID = getArguments().getString("collegeID");
        clglogo = getArguments().getString("collegeLogo");

    }


    private BottomSheetBehavior mBehavior;



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        v = View.inflate(getContext(), R.layout.bottom_sheet_modal, null);

       /* Toolbar appBar = (Toolbar) v.findViewById(R.id.app_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(appBar);

       // ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("dande");
        appBar.setTitle(CollegeString);
        TextView tv = (TextView) v.findViewById(R.id.text);
        tv.setText(mString);

        ImageRequester imageRequester = ImageRequester.getInstance(contextForFragment);
        NetworkImageView headerImage = (NetworkImageView) v.findViewById(R.id.app_bar_image);

        imageRequester.setImageFromUrl(headerImage,"https://s3.us-east-2.amazonaws.com/elasticbeanstalk-us-east-2-083183914236/CLGS_PROJECT/AllNewsImages/NIT-Puducherry-Logo.png");
      *//*  v = View.inflate(getContext(), R.layout.bottom_sheet_modal, null);
        TextView clg = (TextView) v.findViewById(R.id.bottomsheet_CollegeName);
        clg.setText(CollegeString);





        String serverUrl = null;
        try {
            String  server = Util.getProperty("name", contextForFragment);
            serverUrl= server.concat("/CollegeImages");

        } catch (IOException e) {
            e.printStackTrace();
        }
        String WORKSPACE_ID ="CLGS_WORKSPACE";
        String FUNCTION_ID = "GET_CLG_HEADER_IMG";
        String ACTION_ID = "GET_CLG_HEADER_NIT_"+COLLEGE_ID;
        new GetBottomSheetTask(WORKSPACE_ID,FUNCTION_ID,ACTION_ID).execute(serverUrl);
        String WORKSPACE_ID_DES ="CLGS_WORKSPACE";
         String FUNCTION_ID_DES = "GET_CLG_DESC_DATA";
         String ACTION_ID_DES = COLLEGE_ID;
         new GetStringShowDetailTask(WORKSPACE_ID_DES,FUNCTION_ID_DES,ACTION_ID_DES).execute(serverUrl);


        for(int i =0;i<30;i++)
        {
            String FUNCTION_ID_OVERRIDE = "GET_CLG_PLACEMENTS";
            String ACTION_ID_OVERRIDE = "GET_CLG"+COLLEGE_ID+"COMP"+i;
            new GetBottomSheetTask1(WORKSPACE_ID,FUNCTION_ID_OVERRIDE,ACTION_ID_OVERRIDE,i).execute(serverUrl);
        }
        for(int i =0;i<5;i++)
        {
            String FUNCTION_ID_OVERRIDE = "GET_CLG_QUESTIONS";
            String ACTION_ID_OVERRIDE = "GET_CLG"+COLLEGE_ID+"COMP"+i;
            new GetBottomSheetTask1(WORKSPACE_ID,FUNCTION_ID_OVERRIDE,ACTION_ID_OVERRIDE,i).execute(serverUrl);
        }
        String FUNCTION_ID_RANK = "GET_CLG_RANKS";
        String ACTION_ID_RANK = "GET_CLG_RANK_"+COLLEGE_ID;
        new GetBottomSheetTask(WORKSPACE_ID,FUNCTION_ID_RANK,ACTION_ID_RANK).execute(serverUrl);
*/


      //  GridLayoutManager newslayoutManager= new GridLayoutManager(getActivity(), 2);

/*      LinearLayoutManager newslayoutManager=  new LinearLayoutManager(contextForFragment, LinearLayoutManager.VERTICAL, false);
        newsRecyclerView = (RecyclerView) v.findViewById(R.id.recycleClgPlacements);
        newsRecyclerView.setLayoutManager(newslayoutManager);
        madaptor=new collegebottomviewdrawer(arrayOfBitmap1,mThumbIds, this);
        newsRecyclerView.setAdapter(madaptor);
        newsRecyclerView.setHasFixedSize(true);*/
       // int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        //newsRecyclerView.addItemDecoration(new SpacesItemDecorHomeActivity(spacingInPixels));

 /*       // String stringUrl ="http://192.168.0.103:9544/ImageUploadServlet";
        //  new GetBottomSheetTask().execute(serverUrl);
        LinearLayoutManager newslayoutManager1= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        newsRecyclerView1 = (RecyclerView) v.findViewById(R.id.recycleClg_questions);
        newsRecyclerView1.setLayoutManager(newslayoutManager1);
        madaptor1=new CollegeQuestions(arrayOfBitmap2,mThumbIds, this);
        newsRecyclerView1.setAdapter(madaptor1);
        int spacingInPixels1 = getResources().getDimensionPixelSize(R.dimen.spacing);
        newsRecyclerView1.addItemDecoration(new SpacesItemDecorHomeActivity(spacingInPixels1));
        newsRecyclerView1.setNestedScrollingEnabled(false);
        TextView tv = (TextView) v.findViewById(R.id.text);
        tv.setText(mString);
        dialog.setContentView(v);*/

        dialog.setContentView(v);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) v.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
        View parent = (View) v.getParent();
        parent.setFitsSystemWindows(true);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parent);
        v.measure(0, 0);
        DisplayMetrics displaymetrics = new DisplayMetrics();        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        bottomSheetBehavior.setPeekHeight(screenHeight);

        if (params.getBehavior() instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior)params.getBehavior()).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        params.height = screenHeight;
        parent.setLayoutParams(params);


       mBehavior = BottomSheetBehavior.from((View) v.getParent());
        mBehavior.setState(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
       mBehavior.setPeekHeight(190);

        return dialog;
    }

        private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        };
    @Override
    public void onStart() {


        super.onStart();
    }

    @Override
    public void onClick(View view, int position, String UniqueKey) {

    }


    public class GetBottomSheetTask  extends AsyncTask<String, Void, Bitmap>

    {

        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        View objview;
        public GetBottomSheetTask() {

        }





        public GetBottomSheetTask(String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
        }

        @Override
        protected  void onPreExecute()
        {
            //   ProgressDialog progresdialoglistview = ProgressDialog.show(getActivity(), "", "Loading");
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

            //for storing image data
            Bitmap bitmap1 = null;
            if(is!=null) {
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



        public String readIt(InputStream stream, int len) throws IOException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Bitmap result) {
            Bitmap ing = result;
            // value.checkImageshow(ing);
            // ImageView  imageView=(ImageView)findViewById(R.id.imageView4);
            //  textView.setText(result);

           /* if(functionID=="GET_CLG_RANKS"){
                ImageView imageView= (ImageView) v.findViewById(R.id.bottom_img_sheet_ranks);
                imageView.setImageBitmap(ing);

                // imageView.setMaxWidth(400);
                DisplayMetrics displaymetrics = new DisplayMetrics();

                int height = displaymetrics.heightPixels;
                int width = displaymetrics.widthPixels;
            }



            if(functionID=="GET_CLG_HEADER_IMG"){
                ImageView imageView= (ImageView) v.findViewById(R.id.bottom_img_sheet);
                imageView.setImageBitmap(ing);

                // imageView.setMaxWidth(400);
                DisplayMetrics displaymetrics = new DisplayMetrics();

                int height = displaymetrics.heightPixels;
                int width = displaymetrics.widthPixels;
            }*/
            //  progresdialoglistview.dismiss();
            // imageView.setLayoutParams(new LinearLayout.LayoutParams(200,500));
        }


    }
    public class GetBottomSheetTask1  extends AsyncTask<String, Void, Bitmap>

    {

        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        View objview;
        int position;
        public GetBottomSheetTask1() {

        }





        public GetBottomSheetTask1(String WorkID,String funtID,String ActID,int pos) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            position = pos;
        }

        @Override
        protected  void onPreExecute()
        {
            //   ProgressDialog progresdialoglistview = ProgressDialog.show(getActivity(), "", "Loading");
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
            Bitmap bitmap1=null;
            if(is!=null){
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



        public String readIt(InputStream stream, int len) throws IOException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Bitmap result) {
            Bitmap ing = result;
            // value.checkImageshow(ing);
            // ImageView  imageView=(ImageView)findViewById(R.id.imageView4);
            //  textView.setText(result);

       /*     if(functionID=="GET_CLG_PLACEMENTS"){

                arrayOfBitmap1[position]=result;

                madaptor.notifyDataSetChanged();}
            if(functionID=="GET_CLG_QUESTIONS"){

                arrayOfBitmap2[position]=result;

                madaptor1.notifyDataSetChanged();
            }*/

//            ImageView imageView= (ImageView) v.findViewById(R.id.bottom_img_sheet);
            //          imageView.setImageBitmap(ing);

            // imageView.setMaxWidth(400);
            DisplayMetrics displaymetrics = new DisplayMetrics();

            int height = displaymetrics.heightPixels;
            int width = displaymetrics.widthPixels;

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


        public GetStringShowDetailTask(String WorkID,String funtID,String ActID) {
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

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            String ing = result;

            if(result!=null) {
                try {
                    JSONObject obj = new JSONObject(ing);

                    int lenghtl = obj.length();

                  /*  for (int i = 0; i < lenghtl; i++) {
                        JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));

                        String StringNews = (String) clgdata.get("DATA_DETAILS");
                        TextView clgdesc = (TextView) v.findViewById(R.id.about_college);
                        clgdesc.setText(StringNews);



                        // String StringHeader = (String) clgdata.get("DATA_HEADER");
                        //  detail_string_data.add(i, StringHeader);
                        // detail_string_data.add(i + 1, StringNews);
                        // LatLng ClgLatLong = new LatLng(clgLat,clgLng);
                        // marker = mMap.addMarker(new MarkerOptions().position(ClgLatLong).title(clgname));
                        // mMap.setMinZoomPreference(4);
                        // marker.setTag(clgID);
//.icon(BitmapDescriptorFactory.fromResource(R.drawable.maps_pin))

                    }*/
                    // mAdapter.notifyDataSetChanged();


                    //   LatLng sydney = new LatLng(clgLat,80);
                    //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
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
