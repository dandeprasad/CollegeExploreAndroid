package collegeexplore.CollegeInfo.CollegesAtMaps.CollegesOnclick;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
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
import android.text.Html;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import collegeexplore.CollegeInfo.AllDetailsDescriptionWorkspace.ShowDetailNewsNotifications;
import collegeexplore.CollegeInfo.ExamsWorkspace.ExamsFragmentAdaptor;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.RatingsAndReviews.UserRatingEntry;
import collegeexplore.CollegeInfo.TransitionHelper;
import collegeexplore.CollegeInfo.Util;


public class AboutUsFragment extends Fragment implements ExamsFragmentAdaptor.OnItemClickListener {
    String COLLEGEIDJSON,COLLEGE_NAMEJSON,COLLEGE_ABOUT_US;
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
    ExamsFragmentAdaptor mAdapter;
    ImageView imageView;
    ImageView imageView1;
    TextView data,overallrat,  TextView, phno;
    Button write_review;
    ImageView imageView2;
    String jsonurl;
    private String[] drawerIcons;
    ArrayList<HashMap> StringData=new ArrayList<HashMap>();
    public static Bitmap[] arrayOfBitmap= new Bitmap[20];
    String collegedesc,  clg_url,CLG_NUMBER;

    /**
     * used for initiating the variables
     * @param collegeidToPass
     */
    public static AboutUsFragment newInstance(String collegeidToPass,String collegename,String aboutus,String clgnumber) {
        AboutUsFragment f = new AboutUsFragment();


        Bundle args = new Bundle();
        args.putString("COLLEGEID", collegeidToPass);
        args.putString("COLLEGE_NAME", collegename);
        args.putString("ABOUT_US", aboutus);
        args.putString("CLG_NUMBER", clgnumber);
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
        COLLEGE_NAMEJSON = getArguments() != null ? getArguments().getString("COLLEGE_NAME") : "";
        COLLEGE_ABOUT_US = getArguments() != null ? getArguments().getString("ABOUT_US") : "";
        CLG_NUMBER = getArguments() != null ? getArguments().getString("CLG_NUMBER") : "";

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

        return inflater.inflate(R.layout.college_about_us, container, false);
    }





    public void onViewCreated(View view, Bundle savedInstanceState) {
        xtest = view;
 data =xtest.findViewById(R.id.about_us_text);
        write_review =xtest.findViewById(R.id.write_review);
        write_review.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                UserRatingEntry userrat = UserRatingEntry.newInstance(COLLEGEIDJSON,COLLEGE_NAMEJSON);
                Slide slideTransition = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    slideTransition = new Slide(Gravity.LEFT);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
                }

                Fragment fr = userrat;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    fr.setSharedElementEnterTransition(new ChangeBounds());
                }
                fr.setReenterTransition(slideTransition);
                fr.setExitTransition(slideTransition);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.map_view_onclick_frame,fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }});
        try {
            String  server =  Util.getProperty("name", contextForFragment);
          //  serverUrl= server.concat("/ExamsImages");
            jsonurl= server.concat("/CollegesStartServlet");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String WORKSPACE_ID ="HOME_WORKSPACE";
        String FUNCTION_ID = "COLLEGE_ABOUT_US";
        String ACTION_ID = "COLLEGE_ABOUT_US";
        String WORKSPACE_ID1 ="HOME_WORKSPACE";
        String FUNCTION_ID1 = "COLLEGE_REVIEWS";
        String ACTION_ID1= "COLLEGE_REVIEWS";
        new GetReviewsTask(WORKSPACE_ID1,FUNCTION_ID1,ACTION_ID1,COLLEGEIDJSON).execute(jsonurl);
        new HttpGetRequest ().execute("https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles="+COLLEGE_ABOUT_US);
       // new GetStringNewsNotifyTask(WORKSPACE_ID,FUNCTION_ID,ACTION_ID,COLLEGEIDJSON).execute(jsonurl);

TextView dad= xtest.findViewById(R.id.textView6);
        dad.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                String url ="https://en.wikipedia.org/wiki/"+COLLEGE_ABOUT_US;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }});
        String sourceString0 = "<b>" + "Telephone No: "+CLG_NUMBER + "</b> ";


         phno= xtest.findViewById(R.id.about_phno);
        phno.setText(Html.fromHtml(sourceString0));
        phno.setOnClickListener(new View.OnClickListener() {


                public void onClick(View arg10) {


                   String num= phno.getText().toString();

                    if(!num.equalsIgnoreCase("")) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + num.trim()));

                        startActivity(callIntent);
            }

    }});}

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

    public class HttpGetRequest extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        @Override
        protected String doInBackground(String... params){
            String stringUrl = params[0];
            String result;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            }
            catch(IOException e){
                e.printStackTrace();
                result = null;
            }
            return result;
        }
        protected void onPostExecute(String result){
            try {
                JSONObject clgdata = new JSONObject(result);
                JSONObject clgdata1 = new JSONObject(clgdata.get("query").toString());
                JSONObject clgdata2 = (JSONObject) clgdata1.get("pages");
                JSONObject clgdata3 = (JSONObject)clgdata2.get(clgdata2.names().getString(0));
              String dan=   clgdata3.get("extract").toString();
                data.setText(dan);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public class GetStringNewsNotifyTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        View objview;
        private String COLLEGEID;
        public GetStringNewsNotifyTask(String WORKSPACE_ID, String FUNCTION_ID, String ACTION_ID) {

        }
        public GetStringNewsNotifyTask(String WorkID,String funtID,String ActID, String COLLEGEIDJSON) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
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
             //   xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
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

                             collegedesc = (String) clgdata.get("CLG_DESC");
                           clg_url=  "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles="+collegedesc;

                            new HttpGetRequest ().execute("https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles="+collegedesc);

                        }



                        //   LatLng sydney = new LatLng(clgLat,80);
                        //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                  //  xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                }
            }
        }
    }

    public class GetReviewsTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        View objview;
        private String COLLEGEID;
        public GetReviewsTask(String WORKSPACE_ID, String FUNCTION_ID, String ACTION_ID) {

        }
        public GetReviewsTask(String WorkID,String funtID,String ActID, String COLLEGEIDJSON) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            COLLEGEID=COLLEGEIDJSON;
        }

        @Override
        protected  void onPreExecute()
        {
            // progresdialoglistview=ProgressDialog.show(getActivity(), "","Loading");
            // Log.e("onPreExecutive","called"+progresdialoglistview);
          //  xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
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
        private  double round (double value, int precision) {
            int scale = (int) Math.pow(10, precision);
            return (double) Math.round(value * scale) / scale;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            String ing = result;
            if (result == null) {
                result="";}
            if(result.equalsIgnoreCase("UNABLE_TO_REACH_WEB"))
            {
             //   xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else {
                if (result != "") {

                    try {
                        JSONObject obj = new JSONObject(ing);
                     String overallreview= obj.get("overallrating").toString();
                     String facilityreview=  obj.get("fcoverall").toString();
                    String placementsrev=   obj.get("plaoverall").toString();
                    String    environmentrev= obj.get("envoverall").toString();
                       String campusrev=  obj.get("camoverall").toString();
                        String countoverall=  "Total Ratings & Reviews - "+ obj.get("noofratings").toString();
                        TextView overallrat = xtest.findViewById(R.id.overallrat);
                        overallrat.setText(overallreview);
                      RatingBar placBar1 = xtest.findViewById(R.id.placBar1);
                        RatingBar envBar1 = xtest.findViewById(R.id.envBar1);
                        RatingBar camBar1 = xtest.findViewById(R.id.camBar1);
                        RatingBar overallBar1 = xtest.findViewById(R.id.overallBar1);
                        RatingBar fcBar1 = xtest.findViewById(R.id.fcBar1);
                        TextView overallrat1 = xtest.findViewById(R.id.overallrat1);

                        float place=Float.valueOf( obj.get("plaoverall").toString());
                        float environment= Float.valueOf(obj.get("envoverall").toString());
                        float campus= Float.valueOf(obj.get("camoverall").toString());
                        float faculty= Float.valueOf(obj.get("fcoverall").toString());


if(place>=4)
{
    LayerDrawable stars = (LayerDrawable) placBar1.getProgressDrawable();
    stars.getDrawable(2).setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);

}
                        if(place>2 && place<4)
                        {
                            LayerDrawable stars = (LayerDrawable) placBar1.getProgressDrawable();
                            stars.getDrawable(2).setColorFilter(Color.parseColor("#ff7800"), PorterDuff.Mode.SRC_ATOP);
                        }
                        if(place<=2)
                        {
                            LayerDrawable stars = (LayerDrawable) placBar1.getProgressDrawable();
                            stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        }




                        if(environment>=4)
                        {
                            LayerDrawable stars = (LayerDrawable) envBar1.getProgressDrawable();
                            stars.getDrawable(2).setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                        }
                        if(environment>2 && environment<4)
                        {
                            LayerDrawable stars = (LayerDrawable) envBar1.getProgressDrawable();
                            stars.getDrawable(2).setColorFilter(Color.parseColor("#ff7800"), PorterDuff.Mode.SRC_ATOP);
                        }
                        if(environment<=2)
                        {
                            LayerDrawable stars = (LayerDrawable) envBar1.getProgressDrawable();
                            stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        }


                        if(campus>=4)
                        {
                            LayerDrawable stars = (LayerDrawable) camBar1.getProgressDrawable();
                            stars.getDrawable(2).setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                        }
                        if(campus>2 && campus<4)
                        {
                            LayerDrawable stars = (LayerDrawable) camBar1.getProgressDrawable();
                            stars.getDrawable(2).setColorFilter(Color.parseColor("#ff7800"), PorterDuff.Mode.SRC_ATOP);
                        }
                        if(campus<=2)
                        {
                            LayerDrawable stars = (LayerDrawable) camBar1.getProgressDrawable();
                            stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        }

                        if(faculty>=4)
                        {
                            LayerDrawable stars = (LayerDrawable) fcBar1.getProgressDrawable();
                            stars.getDrawable(2).setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                        }
                        if(faculty>2 && faculty<4)
                        {
                            LayerDrawable stars = (LayerDrawable) fcBar1.getProgressDrawable();
                            stars.getDrawable(2).setColorFilter(Color.parseColor("#ff7800"), PorterDuff.Mode.SRC_ATOP);
                        }
                        if(faculty<=2)
                        {
                            LayerDrawable stars = (LayerDrawable) fcBar1.getProgressDrawable();
                            stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        }

                      //  RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

               /*         int lenghtl = obj.length();
                        //StringData=new  String[8];
                        for (int i = 0; i < lenghtl; i++) {
                            JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));

                            collegedesc = (String) clgdata.get("CLG_DESC");
                            clg_url=  "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles="+collegedesc;

                            new HttpGetRequest ().execute("https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles="+collegedesc);

                        }*/

                        placBar1.setRating(place);
                        envBar1.setRating(environment);
                        camBar1.setRating(campus);
                        fcBar1.setRating(faculty);
                        overallrat1.setText(countoverall);
                        overallBar1.setRating(1);

                        //   LatLng sydney = new LatLng(clgLat,80);
                        //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                 //   xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                }
            }
        }
    }
}
