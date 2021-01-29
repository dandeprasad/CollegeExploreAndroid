package collegeexplore.CollegeInfo.CollegesAtMaps.CollegesOnclick;

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
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Iterator;

import collegeexplore.CollegeInfo.AllDetailsDescriptionWorkspace.ShowDetailNewsNotifications;
import collegeexplore.CollegeInfo.QuestionsAnswersWorkspace.AnswerQuestion;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.SpacesItemDecorHomeActivity;
import collegeexplore.CollegeInfo.TransitionHelper;
import collegeexplore.CollegeInfo.Util;


public class DepartmentsFragment extends Fragment implements DeparmentsAdaptor.OnItemClickListener {
    String COLLEGEIDJSON;
    View xtest;
    private RecyclerView carrerGridList;
    private GridLayoutManager GridLayoutManager1;
    private static final int SPAN_COUNT = 1;
    Context contextForFragment;
    private LruCache<String, Bitmap> mMemoryCache;

    DeparmentsAdaptor mAdapter;
    ImageView imageView;

    TextView data;

    String jsonurl;
    public static Bitmap[] arrayOfBitmap= new Bitmap[20];
CardView depart;
LinearLayout linear_depart,cardview_linear;
    ArrayList dataToAdaptor = new ArrayList();

    /**
     * used for initiating the variables
     * @param collegeidToPass
     */
    public static DepartmentsFragment newInstance(String collegeidToPass) {
        DepartmentsFragment f = new DepartmentsFragment();


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

        return inflater.inflate(R.layout.departments_recycleview, container, false);
    }





    public void onViewCreated(View view, Bundle savedInstanceState) {
        xtest = view;

        GridLayoutManager1 = new GridLayoutManager(getActivity(), SPAN_COUNT);


        carrerGridList = xtest.findViewById(R.id.departrecycle);
        // carrerGridList.setNestedScrollingEnabled(false);
        carrerGridList.setLayoutManager(GridLayoutManager1);
        carrerGridList.setHasFixedSize(true);
        dataToAdaptor.clear();
        mAdapter = new DeparmentsAdaptor(this,contextForFragment,dataToAdaptor);
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
        try {
            String  server =  Util.getProperty("name", contextForFragment);
          //  serverUrl= server.concat("/ExamsImages");
            jsonurl= server.concat("/CollegesStartServlet");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String WORKSPACE_ID ="HOME_WORKSPACE";
        String FUNCTION_ID = "COLLEGE_DEPARTMENTS";
        String ACTION_ID = "COLLEGE_DEPARTMENTS";

        new GetStringNewsNotifyTask(WORKSPACE_ID,FUNCTION_ID,ACTION_ID,COLLEGEIDJSON).execute(jsonurl);




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

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
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

                            ArrayList valuestosend = new ArrayList();
                          //  valuestosend.add(0,"");
                          //  jObject = new JSONObject(contents.trim());
                            Iterator<?> keys = clgdata.keys();
int j =0;
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();

                                   String keydata= clgdata.names().getString(j).toString() ;
                                    String valuedata= clgdata.get(clgdata.names().getString(j)).toString();
                                    if(keydata.equalsIgnoreCase("CLG_STREAM")){
                                        valuestosend.add(0,valuedata);

                                    }
                                    else{
                                valuestosend.add(valuedata);}
                          /*          TextView tv=new TextView(contextForFragment);
                                    tv.setText(valuedata);
                                    linear_depart.addView(tv);*/
                                   // depart.addView(tv);
j++;
                            }
                            dataToAdaptor.add(valuestosend);


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
