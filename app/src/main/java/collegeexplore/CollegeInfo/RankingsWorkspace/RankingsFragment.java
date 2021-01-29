package collegeexplore.CollegeInfo.RankingsWorkspace;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import collegeexplore.CollegeInfo.AllDetailsDescriptionWorkspace.ShowDetailNewsNotifications;
import collegeexplore.CollegeInfo.ItemOffsetDecoration;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.Util;


public class RankingsFragment extends Fragment implements RankingsFragmentAdaptor.OnItemClickListener {
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
    RankingsFragmentAdaptor mAdapter;
    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    String jsonurl,UniqueTosend,collegename,toolbarTorF;
    private String[] drawerIcons;
    ArrayList<ArrayList> StringData=new ArrayList<ArrayList>();
    private ArrayList<String> arrayOfBitmap= new ArrayList();
    String TAG = "NewsFragement - ";


    public static RankingsFragment newInstance(String uniqueKey, String clgname, String stream) {
        RankingsFragment f = new RankingsFragment();


        Bundle args = new Bundle();


        args.putString("UniqueKeyValue",uniqueKey);
        args.putString("collegeName",clgname);
        args.putString("Stream",stream);
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

        COLLEGEIDJSON = "NIT_PY";//getArguments() != null ? getArguments().getString("COLLEGEID") : "";
        //memory cache management
        UniqueTosend = getArguments() != null ? getArguments().getString("UniqueKeyValue") : "";
        collegename = getArguments() != null ? getArguments().getString("collegeName") : "";
        toolbarTorF = getArguments() != null ? getArguments().getString("Stream") : "";
        arrayOfBitmap.add(0,UniqueTosend);
        arrayOfBitmap.add(0,collegename);
        arrayOfBitmap.add(0,toolbarTorF);
}

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
        Toolbar myToolbar = xtest.findViewById(R.id.PurenewsToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Rankings");
        // Get a support ActionBar corresponding to this toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //final Toolbar HomeToolbarfrag = (Toolbar) findViewById(R.id.toolbar_collaspe);
        myToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
            }

        });
        if(toolbarTorF.equalsIgnoreCase("F")) {
            myToolbar.setVisibility(View.GONE);
        }

        GridLayoutManager1 = new GridLayoutManager(getActivity(), SPAN_COUNT);


        carrerGridList = xtest.findViewById(R.id.news_recycleview);
        // carrerGridList.setNestedScrollingEnabled(false);
        carrerGridList.setLayoutManager(GridLayoutManager1);
        carrerGridList.setHasFixedSize(true);
       // carrerGridList.setNestedScrollingEnabled(false);
       // StringData = getResources().getStringArray(R.array.Navigation_data_icons);
        //StringData.clear();
        mAdapter = new RankingsFragmentAdaptor(contextForFragment,StringData,arrayOfBitmap, this);
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
        carrerGridList.setAdapter(mAdapter);
       // int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing41);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(contextForFragment,R.dimen.spacing41);
        carrerGridList.addItemDecoration(itemDecoration);
     //   carrerGridList.addItemDecoration(new SpacesItemDecorHomeActivity(spacingInPixels));
        //carrerGridList.addItemDecoration(new DividerItemDecoration(getApplicationContext(),R.drawable.com_facebook_button_login_logo));

        try {
            String  server =  Util.getProperty("name", contextForFragment);

                jsonurl= server.concat("/CutoffStreams");

        int startIndex=0;
        int endDataindex=10;

        String WORKSPACE_ID ="HOME_WORKSPACE";
        String FUNCTION_ID = "GET_RANKING_DETAILS";
        String ACTION_ID = "GET_RANKINGS_MHRD";

            if(StringData.isEmpty()) {
                new GetStringNewsNotifyTask(WORKSPACE_ID, FUNCTION_ID, ACTION_ID, startIndex, endDataindex, UniqueTosend).execute(jsonurl);
            }


        OnClickListener image_clicker = new OnClickListener(){
            public void onClick(View v){
                String x  = "tst";
            }
        };

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onClick(View view, String position) {
        selectItem(position);
    }
    private void selectItem(String Id) {

        final String UniqueKey = Id;

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
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);

        fragmentTransaction.replace(R.id.view_onclick_cuttoffsec,fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    public class GetStringNewsNotifyTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID,uniqueTosend;
        View objview;
        int SROW_INDEX;
        int EROW_INDEX;



        public GetStringNewsNotifyTask(String WorkID, String funtID, String ActID, int startIndex, int endDataindex, String uniqueTosend) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            SROW_INDEX=startIndex;
            EROW_INDEX=endDataindex;
            this.uniqueTosend=uniqueTosend;
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
                Record.put( "uniqueTosend", uniqueTosend);


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
                        ArrayList<HashMap> StringData1=new ArrayList<HashMap>();

                        for (int i = 0; i < lenghtl; i++) {
                            JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));
                            HashMap<String,String>hashmap= new HashMap();
                            String CLG_ID =Util.Nullcheck( (String) clgdata.get("CLG_ID"));
                            String CLG_NAME = Util.Nullcheck((String) clgdata.get("CLG_NAME"));
                            String CLG_CITY = Util.Nullcheck((String) clgdata.get("CLG_CITY"));
                            String CLG_STATE =Util.Nullcheck( (String) clgdata.get("CLG_STATE"));
                            String CLG_SCORE = Util.Nullcheck((String) clgdata.get("CLG_SCORE"));
                            String CLG_RANK = Util.Nullcheck((String) clgdata.get("CLG_RANK"));
                            String CLG_YEAR =Util.Nullcheck( (String) clgdata.get("CLG_YEAR"));



                            hashmap.put("CLG_ID",CLG_ID);
                            hashmap.put("CLG_NAME",CLG_NAME);
                            hashmap.put("CLG_CITY",CLG_CITY);
                            hashmap.put("CLG_STATE",CLG_STATE);
                            hashmap.put("CLG_SCORE",CLG_SCORE);
                            hashmap.put("CLG_RANK",CLG_RANK);
                            hashmap.put("CLG_YEAR",CLG_YEAR);


    StringData1.add(hashmap);


                        }
                        StringData.add(StringData1);

                        mAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }




}
