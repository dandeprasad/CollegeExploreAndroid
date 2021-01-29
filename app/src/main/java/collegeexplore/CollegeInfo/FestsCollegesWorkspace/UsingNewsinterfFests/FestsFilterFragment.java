package collegeexplore.CollegeInfo.FestsCollegesWorkspace.UsingNewsinterfFests;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import collegeexplore.CollegeInfo.AllDetailsDescriptionWorkspace.ShowDetailNewsNotifications;
import collegeexplore.CollegeInfo.FestsCollegesWorkspace.TabsHeaderActivity;
import collegeexplore.CollegeInfo.ItemOffsetDecoration;
import collegeexplore.CollegeInfo.NewsWorkspace.NewsDetailFragment;
import collegeexplore.CollegeInfo.QuestionsAnswersWorkspace.AnswerQuestion;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.TransitionHelper;
import collegeexplore.CollegeInfo.Util;


public class FestsFilterFragment extends Fragment implements FestsFilterFragmentAdaptor.OnItemClickListener {
    int mNum;
    ProgressDialog progresdialoglistview ;
    private RecyclerView newsRecyclerView;
    private String[] maincontents1;
    PopupWindow popupMessage;
    LinearLayout layoutOfPopup;
    private PopupWindow mPopupWindow;
    TextView popupText;
    View xtest;
    private RecyclerView carrerGridList;
    private GridLayoutManager GridLayoutManager1;
    private static final int SPAN_COUNT = 2;
    Context contextForFragment;
    private LruCache<String, Bitmap> mMemoryCache;
    String serverUrl = null;
    FestsFilterFragmentAdaptor mAdapter;
    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    String jsonurl,filterkey,title;
    SwipeRefreshLayout mySwipeRefreshLayout;
    String Filter;
    private String[] drawerIcons;
    ArrayList<HashMap> StringData=new ArrayList<HashMap>();
    private ArrayList<Bitmap> arrayOfBitmap= new ArrayList();
    String TAG = FestsFilterFragment.class.getSimpleName();

    /**
     * used for initiating the variables
     */
    public static FestsFilterFragment newInstance(String filterkey,String title) {
        FestsFilterFragment f = new FestsFilterFragment();


        Bundle args = new Bundle();
        args.putString("filterkey",filterkey);
        args.putString("title",title);
        f.setArguments(args);

        return  f;
    }
/*    public static FestsFilterFragment newInstance(String toolbarTorF, String collegeidfilter) {
        FestsFilterFragment f = new FestsFilterFragment();


        Bundle args = new Bundle();
        args.putString("toolbarTorF",toolbarTorF);

        f.setArguments(args);

        return  f;
    }*/
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

        filterkey = getArguments() != null ? getArguments().getString("filterkey") : "";
       title = getArguments() != null ? getArguments().getString("title") : "";
}

    /**
     * This is called soon after oncreate and expects a view return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.home_fests_notify_layout, container, false);
    }





    public void onViewCreated(View view, Bundle savedInstanceState) {
        xtest = view;

        Toolbar myToolbar = xtest.findViewById(R.id.PurenewsToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);

        // Get a support ActionBar corresponding to this toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //final Toolbar HomeToolbarfrag = (Toolbar) findViewById(R.id.toolbar_collaspe);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
            }

        });

        mySwipeRefreshLayout = xtest.findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setColorSchemeResources(
                R.color.app_theme
                /*R.color.refresh_progress_2,
                R.color.refresh_progress_3*/);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                       // Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                    //    myUpdateOperation();
                    }


                }
        );

  /*      AppBarLayout fs = xtest.findViewById(R.id.appbar);
        if(toolbarTorF.equalsIgnoreCase("F")) {
            fs.setVisibility(View.GONE);
        }*/

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
       // carrerGridList.setNestedScrollingEnabled(false);
       // StringData = getResources().getStringArray(R.array.Navigation_data_icons);
       // StringData.clear();
        mAdapter = new FestsFilterFragmentAdaptor(contextForFragment,StringData,arrayOfBitmap, this);
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
        mAdapter.setLoadMoreListener(new FestsFilterFragmentAdaptor.OnLoadMoreListener() {
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
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(contextForFragment,R.dimen.item_offset);
        carrerGridList.addItemDecoration(itemDecoration);


     //   carrerGridList.addItemDecoration(new SpacesItemDecorHomeActivity(spacingInPixels));
        //carrerGridList.addItemDecoration(new DividerItemDecoration(getApplicationContext(),R.drawable.com_facebook_button_login_logo));

        if(StringData.isEmpty()) {

            callingserver(filterkey);
        }



  }

    private void loadMore(int index){
        HashMap<String,String>hashmap= new HashMap();

        hashmap.put("LOAD_CHECK","notloaded");
        //add loading progress view
        StringData.add(hashmap);
        mAdapter.notifyItemInserted(StringData.size()-1);
        callingserverloadmore(index,filterkey);


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


    @Override
    public void onClick(View view, String position) {
        selectItem(position);
    }

    @Override
    public void onClick(int adapterPosition, HashMap hashMap, ImageView sharedImageView) {

        Fragment animalDetailFragment = NewsDetailFragment.newInstance(hashMap);
        getFragmentManager()
                .beginTransaction()
                .addSharedElement(sharedImageView, ViewCompat.getTransitionName(sharedImageView))
                .addToBackStack(TAG)
                .replace(R.id.view_onclick_frame, animalDetailFragment)
                .commit();
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



    public class GetStringNewsNotifyTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID,filterKey;
        View objview;
        int SROW_INDEX;
        int EROW_INDEX;

        public GetStringNewsNotifyTask(String WorkID,String funtID,String ActID, int startIndex, int endDataindex,String filterKey) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            SROW_INDEX=startIndex;
            EROW_INDEX=endDataindex;
            this.filterKey=filterKey;
        }


        @Override
        protected  void onPreExecute()
        {
            // progresdialoglistview=ProgressDialog.show(getActivity(), "","Loading");
            // Log.e("onPreExecutive","called"+progresdialoglistview);
            //xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
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
                Record.put( "FILTER_KEY", filterKey);

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
                    xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                    try {
                        JSONObject obj = new JSONObject(ing);
                        Marker marker;
                        int lenghtl = obj.length();
                        //StringData=new  String[8];
                        StringData.clear();
                        for (int i = 0; i < lenghtl; i++) {


                            JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));



                            String CLGID = (String) clgdata.get("CLGID");
                            String COLLEGE_NAME = (String) clgdata.get("COLLEGE_NAME");
                            String CLG_FEST_DATE = (String) clgdata.get("CLG_FEST_DATE");
                            String CLG_FEST_INFO = (String) clgdata.get("CLG_FEST_INFO");
                            String CLG_LOCATION = (String) clgdata.get("CLG_LOCATION");
                            String FEST_IMAGE = (String) clgdata.get("FEST_IMAGE");


                            HashMap<String, String> param_aux = new HashMap<String, String>();

                            param_aux.put("CLGID", CLGID);
                            param_aux.put("COLLEGE_NAME", COLLEGE_NAME);
                            param_aux.put("CLG_FEST_DATE", CLG_FEST_DATE);
                            param_aux.put("CLG_FEST_INFO", CLG_FEST_INFO);
                            param_aux.put("CLG_LOCATION", CLG_LOCATION);
                            param_aux.put("FEST_IMAGE", FEST_IMAGE);
                            param_aux.put("LOAD_CHECK","loaded");
                            StringData.add(param_aux);

                        }
                        //for swipe refesh set more data availabe shd be true here
                        mAdapter.setMoreDataAvailable(true);
                        mySwipeRefreshLayout.setRefreshing(false);
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                }
            }
        }
    }

    public class GetStringNewsLoadmore  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID,filterKey;
        View objview;
        int SROW_INDEX;
        int EROW_INDEX;

        public GetStringNewsLoadmore(String WorkID,String funtID,String ActID, int startIndex, int endDataindex,String filterKey) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            SROW_INDEX=startIndex;
            EROW_INDEX=endDataindex;
            this.filterKey=filterKey;
        }


        @Override
        protected  void onPreExecute()
        {
            // progresdialoglistview=ProgressDialog.show(getActivity(), "","Loading");
            // Log.e("onPreExecutive","called"+progresdialoglistview);
            //xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
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
                Record.put( "FILTER_KEY", filterKey);

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
                xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else {
                if (result != "") {

                    try {
                       // xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                        JSONObject obj = new JSONObject(ing);
                        //remove loading view
                        StringData.remove(StringData.size()-1);
                        Marker marker;
                        int lenghtl = obj.length();
                        //StringData=new  String[8];
                        for (int i = 0; i < lenghtl; i++) {
                            JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));



                            String CLGID = (String) clgdata.get("CLGID");
                            String COLLEGE_NAME = (String) clgdata.get("COLLEGE_NAME");
                            String CLG_FEST_DATE = (String) clgdata.get("CLG_FEST_DATE");
                            String CLG_FEST_INFO = (String) clgdata.get("CLG_FEST_INFO");
                            String CLG_LOCATION = (String) clgdata.get("CLG_LOCATION");
                            String FEST_IMAGE = (String) clgdata.get("FEST_IMAGE");


                            HashMap<String, String> param_aux = new HashMap<String, String>();

                            param_aux.put("CLGID", CLGID);
                            param_aux.put("COLLEGE_NAME", COLLEGE_NAME);
                            param_aux.put("CLG_FEST_DATE", CLG_FEST_DATE);
                            param_aux.put("CLG_FEST_INFO", CLG_FEST_INFO);
                            param_aux.put("CLG_LOCATION", CLG_LOCATION);
                            param_aux.put("FEST_IMAGE", FEST_IMAGE);
                            param_aux.put("LOAD_CHECK","loaded");



                            StringData.add(param_aux);
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                else{//result size 0 means there is no more data available at server
                    mAdapter.setMoreDataAvailable(false);

                    StringData.remove(StringData.size()-1);

                   // xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                    //telling adapter to stop calling load more as no more server data available
                    Toast.makeText(xtest.getContext(),"That's all for now", Toast.LENGTH_LONG).show();
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
public void callingserver(String filterval )
{
    try {
        String  server =  Util.getProperty("name", contextForFragment);



        int startIndex=0;
        int endDataindex=5;


            jsonurl= server.concat("/FestsHomeAllNotificationsString");

/*        for(int i =0;i<=9;i++)
        {
            new GetDataNewsNotifyTask(i).execute(serverUrl);
        }*/
        String WORKSPACE_ID ="HOME_WORKSPACE";
        String FUNCTION_ID = "GET_FESTS_NOTIFICATIONS";
        String ACTION_ID = "GET_FEST_FILTER_STRING";
        if(StringData.isEmpty()) {
            new GetStringNewsNotifyTask(WORKSPACE_ID, FUNCTION_ID, ACTION_ID,startIndex,endDataindex,filterval).execute(jsonurl);
        }


    } catch (IOException e) {
        e.printStackTrace();
    }
}
    public void callingserverloadmore(int index,String filterval )
    {
        try {
            String  server =  Util.getProperty("name", contextForFragment);

            jsonurl= server.concat("/FestsHomeAllNotificationsString");



            String WORKSPACE_ID ="HOME_WORKSPACE";
            String FUNCTION_ID = "GET_FESTS_NOTIFICATIONS";
            String ACTION_ID = "GET_FEST_FILTER_STRING";
            int startIndex=index+1;
            int endDataindex=index+6;

            new GetStringNewsLoadmore(WORKSPACE_ID,FUNCTION_ID,ACTION_ID,startIndex,endDataindex,filterval).execute(jsonurl);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view, int position, String clg_id,String festimage,String collegename,ImageView imagetrasition ) {
        int x = view.getId();

        selectItem(position,clg_id,festimage,collegename, imagetrasition);
    }
    private void selectItem(int position,String clg_id,String festimage,String collegename,ImageView imagetrasition ){

/*        final String UniqueKey = "FESTS_LATEST_NOTIFY";
ArrayList<String> datatohost=new ArrayList<String>();
        datatohost.add(clg_id);
        // ((HomeActivity)getActivity()).getSupportActionBar(). setHomeAsUpIndicator(R.drawable.back_button);
        Fragment fr =  ShowDetailNewsNotifications.newInstance(position, UniqueKey,datatohost);

        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.view_onclick_framecutt1,fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
        Intent i2 = new Intent(contextForFragment,TabsHeaderActivity.class);
        i2.putExtra("clg_id",clg_id);
        i2.putExtra("festimage",festimage);
        i2.putExtra("EXTRA_IMAGE_TRANSITION_NAME", ViewCompat.getTransitionName(imagetrasition));

        i2.putExtra("collegename",collegename);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(),
                imagetrasition,
                ViewCompat.getTransitionName(imagetrasition));
        startActivity(i2,options.toBundle());

    }

    private void myUpdateOperation() {

/*      android.support.v4.app.Fragment fr =  new HomeNewsNotifications();

        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.sample_content_fragment,fr);
        fragmentTransaction.commit();*/

        callingserver(Filter);

        //  mySwipeRefreshLayout.setRefreshing(false);
    }
}
