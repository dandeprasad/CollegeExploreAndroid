package collegeexplore.CollegeInfo.NewsandNotificationsWorkspace;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;
import com.viewpagerindicator.CirclePageIndicator;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import collegeexplore.CollegeInfo.AdmissionsWorkspace.CollegeAdmisFragment;
import collegeexplore.CollegeInfo.CollegesAtMaps.NitsNewsNotifications;
import collegeexplore.CollegeInfo.CollegesAtMaps.homeMapsActivity;
import collegeexplore.CollegeInfo.CutoffsWorkspace.CutoffFirstFragment;
import collegeexplore.CollegeInfo.ExamsWorkspace.ExamsDetailsViewPager;
import collegeexplore.CollegeInfo.ExamsWorkspace.HomeExamsFragment;
import collegeexplore.CollegeInfo.FestsCollegesWorkspace.FestNewsNotifications;
import collegeexplore.CollegeInfo.FestsCollegesWorkspace.TabsHeaderActivity;
import collegeexplore.CollegeInfo.HomeActivity;
import collegeexplore.CollegeInfo.ListOfNitsNews;
import collegeexplore.CollegeInfo.LoginManagement.UserLogin;
import collegeexplore.CollegeInfo.LoginManagement.UserLoginManager;
import collegeexplore.CollegeInfo.NewsWorkspace.HomeNewsDetailActivity;
import collegeexplore.CollegeInfo.NewsWorkspace.NewsFragment;
import collegeexplore.CollegeInfo.PlacementsWorkspace.PlaceFirstFragment;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.RankingsWorkspace.RankingsFragment;
import collegeexplore.CollegeInfo.SpacesItemDecorHomeActivity1;
import collegeexplore.CollegeInfo.Util;

import static collegeexplore.CollegeInfo.NewsandNotificationsWorkspace.HomeNewsNotifications.mAdapter;
import static com.facebook.FacebookSdk.getApplicationContext;


public class HomeNewsNotificationsAdaptor extends RecyclerView.Adapter<HomeNewsNotificationsAdaptor.carrerGuidanceViewHolder> implements ListOfNitsNews.OnItemClickListener, ListofAllNotifications.OnItemClickListener,MainHeaderAdsAdaptor.OnItemClickListener, ListofAllNotifications1.OnItemClickListener, ListofAllNotifications2.OnItemClickListener, ListofAllNotifications3.OnItemClickListener,ListofAllNotifications4.OnItemClickListener {

    private OnItemClickListener mListener;
    public TextView mTextView;
    private String[] maincontents1;
    public      FragmentTransaction fragmentTransaction;
    private ListofAllNotifications ListofAllNotificationsAdaptor;
    private ListofAllNotifications1 ListofAllNotificationsAdaptor1;
    private ListofAllNotifications2 ListofAllNotificationsAdaptor2;
    private ListofAllNotifications3 ListofAllNotificationsAdaptor3;
    private ListofAllNotifications4 ListofAllNotificationsAdaptor4;
    private MainHeaderAdsAdaptor MainAdsAdaptor;
    private NitsNewsNotifications nitsNewsNotifications;
    Handler handler;
    Runnable Update;
    Context contextAdaptor;
    public ArrayList<HashMap> ImagesArray=new ArrayList<HashMap>();

    String serverUrl;
    Bitmap[] arrayOfBitmaptest;
    HashMap dand = null;
    HashMap arrayForAllNotificationsnew= new HashMap();
    ArrayList<HashMap> StringData1=new ArrayList<HashMap>(Collections.nCopies(10, dand));
    ArrayList<HashMap> StringData=new ArrayList<HashMap>(Collections.nCopies(10, dand));

    ArrayList<HashMap> StringData2=new ArrayList<HashMap>(Collections.nCopies(10, dand));
    ArrayList<HashMap> StringData3=new ArrayList<HashMap>(Collections.nCopies(10, dand));
  public    SlidingImage_Adapter viewpageAdaptor;
    private  int currentPage = 0;
    private  int NUM_PAGES = 0;
Boolean isMoreDataAvailableads = true;
    Boolean isMoreDataAvailableappicons = true;
    Boolean isMoreDataAvailablenews = true;
    Boolean isMoreDataAvailableexams=true;
    Boolean isMoreDataAvailablefests=true;
    Boolean isMoreDataAvailableadmissions=true;
    String[] home_appicons;
  //  private static final Integer[] IMAGES= {R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.five};
  String TAG =HomeNewsNotificationsAdaptor.class.getSimpleName();

    String[] stringDataAdaptor;
    View v=null;


    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class carrerGuidanceViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView label;
        ImageView imageView;
        CardView home_colleges,home_admissions_card,home_exams_card,home_news_card,home_cutoff_card;
        private TextView StringHeader;
        GridView imageView1;
        private RecyclerView newsRecyclerView;
        private RecyclerView newsRecyclerView1;
        private RecyclerView newsRecyclerView2;
        private RecyclerView newsRecyclerView3,newsRecyclerView4;
        private RecyclerView nitnewsRecyclerView;
        private RecyclerView mainHeadAdsRecyclerView;
        private TextView headerforviews;
        private  ViewPager mPager;
        CirclePageIndicator indicator;
        public carrerGuidanceViewHolder(View v) {
            super(v);
            // label = (TextView) v.findViewById(R.id.carrer_cardview_text);
            //  mTextView = (TextView) v;

                    imageView = v.findViewById(R.id.shownewsnotifydetail);
/*            home_colleges = v.findViewById(R.id.home_colleges_card);
            home_admissions_card = v.findViewById(R.id.home_admissions_card);
            home_exams_card= v.findViewById(R.id.home_exams_card);
            home_news_card= v.findViewById(R.id.home_news_card);
            home_cutoff_card= v.findViewById(R.id.home_cutoff_card);
            StringHeader = v.findViewById(R.id.carrer_cardview_text);*/
           // imageView1 = (GridView) v.findViewById(R.id.gridviewsecondview);
            newsRecyclerView = v.findViewById(R.id.recyclerNewsAllnotify);
            newsRecyclerView1 = v.findViewById(R.id.recyclerNewsAllnotify1);
            newsRecyclerView2 = v.findViewById(R.id.recyclerNewsAllnotify2);
            newsRecyclerView3 = v.findViewById(R.id.recyclerNewsAllnotify3);
          //  newsRecyclerView4 = v.findViewById(R.id.recyclerNewsAllnotify4);
  /*

            nitnewsRecyclerView = v.findViewById(R.id.recyclenitNewsNotify);*/
    /*      //  mainHeadAdsRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerMainAds);
            headerforviews = v.findViewById(R.id.textview_forall);*/
            mPager = v.findViewById(R.id.pager);
            indicator = v.findViewById(R.id.indicator);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeNewsNotificationsAdaptor(OnItemClickListener listener, Context contextForFragment, Bitmap[] arrayOfBitmap, String[] stringData) {
        contextAdaptor = contextForFragment;
        mListener = listener;
        arrayOfBitmaptest=arrayOfBitmap;
        stringDataAdaptor=stringData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HomeNewsNotificationsAdaptor.carrerGuidanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {





        //memory cache management

        // create a new view
        if (viewType == 0) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.home_header_ads_layout, parent, false);

        }
        if (viewType == 1){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_notif_lineardata, parent, false);}
        if (viewType == 2){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_notif_lineardata1, parent, false);}
        if (viewType == 4) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_notif_lineardata2, parent, false);

        }
        if (viewType == 3) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_notif_lineardata3, parent, false);

        }
        //commenting for admissions
/*        if (viewType == 5){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_notif_lineardata4, parent, false);}*/
/*
        if (viewType == 6) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_notif_lineardata1, parent, false);

        }
        if (viewType == 8) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_notif_lineardata2, parent, false);

        }
        if (viewType == 9) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_notif_lineardata3, parent, false);

        }
        if (viewType ==4) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.showdetail_newsnotify_datacontent, parent, false);

        }*/

/*         if (viewType == 5) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.home_nits_news_notify_recycleview, parent, false);

        }*/
        /* String[] stringdata={String.valueOf(R.string.hyderabad),String.valueOf(R.string.chennai),String.valueOf(R.string.mumbai),
                 String.valueOf(R.string.bangalore),String.valueOf(R.string.delhi),String.valueOf(R.string.kerela),
                 String.valueOf(R.string.west_bengal),String.valueOf(R.string.jaipur),String.valueOf(R.string.lucknow),String.valueOf(R.string.pune)};*/
     //   String[] stringdata = contextAdaptor.getResources().getStringArray(R.array.topcities);
/*        String[] stringdata1 = contextAdaptor.getResources().getStringArray(R.array.topstates);
        String[] stringdata2 = contextAdaptor.getResources().getStringArray(R.array.streamwiseclgs);
        String[] stringdata3 = contextAdaptor.getResources().getStringArray(R.array.streamwiseclgs);*/
         home_appicons = contextAdaptor.getResources().getStringArray(R.array.home_appicons);




        viewpageAdaptor = new SlidingImage_Adapter(contextAdaptor,ImagesArray);




        // set the view's size, margins, paddings and layout parameters

        carrerGuidanceViewHolder vh = new carrerGuidanceViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder( carrerGuidanceViewHolder holder, final int position) {

       // adapter.setActiveUserPosition(position);
        // holderForPast = holder;
        //All images to load from server
        //  imageView= (ImageView) xtest.findViewById(carrerGuidance_img0);

        //  imageView.setOnClickListener(image_clicker);


        //    loadBitmap(carrerGuidance_img0, imageView);

        //   loadBitmap(carrerGuidance_img0,imageView1);













        // - get element from your dataset at this position
        // - replace the contents of the view with that element
     /*   int i;
        View dande = null;
        for (i=0;i<mDataset.length;i++)
        {
           String king = mDataset[position];
            holder.mTextView.setText(king);
            holder.mTextView.setTextColor(Color.BLACK);

            if (dande != null) {
                holder.seperat = dande.findViewById(R.id.separate);
            }
            // seperat =  seperat.findViewById(R.id.separate);

        }
        */
        int val = position;

        if(position==0) {
            init(holder);

}
        if(position==1){
            ListofAllNotificationsAdaptor =new ListofAllNotifications(home_appicons, arrayForAllNotificationsnew,  this,contextAdaptor);
            LinearLayoutManager newslayoutManager = new LinearLayoutManager(contextAdaptor, LinearLayoutManager.HORIZONTAL, false);
            holder.newsRecyclerView.setNestedScrollingEnabled(false);
            holder.newsRecyclerView.setLayoutManager(newslayoutManager);

            holder.newsRecyclerView.setAdapter(ListofAllNotificationsAdaptor);
            int spacingInPixels = contextAdaptor.getResources().getDimensionPixelSize(R.dimen.spacing);
            holder.newsRecyclerView.addItemDecoration(new SpacesItemDecorHomeActivity1(spacingInPixels));
            newslayoutManager.setSmoothScrollbarEnabled(true);

            if(isMoreDataAvailableappicons) {

                //Colleges in cities
                try {

                    String WORKSPACE_ID1 = "HOME_WORKSPACE";
                    String FUNCTION_ID1 = "GET_LINEAR_DATA";
                    String ACTION_ID1 = "CLGS_ALL_APPS";

                    String server = Util.getProperty("name", contextAdaptor);
                    serverUrl = server.concat("/HomeAllNotifications");


                    new GetStringNewsNotifyTask2(WORKSPACE_ID1, FUNCTION_ID1, ACTION_ID1).execute(serverUrl);


           /* ImagesArray[i] = BitmapFactory.decodeResource(contextAdaptor.getResources(),
                    R.drawable.load_mask);*/


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if(position==2){
            ListofAllNotificationsAdaptor1 =new ListofAllNotifications1( StringData,  this,contextAdaptor);
            LinearLayoutManager newslayoutManager = new LinearLayoutManager(contextAdaptor, LinearLayoutManager.HORIZONTAL, false);
            holder.newsRecyclerView1.setNestedScrollingEnabled(false);
            holder.newsRecyclerView1.setLayoutManager(newslayoutManager);

            holder.newsRecyclerView1.setAdapter(ListofAllNotificationsAdaptor1);
            int spacingInPixels = contextAdaptor.getResources().getDimensionPixelSize(R.dimen.spacing);
            holder.newsRecyclerView1.addItemDecoration(new SpacesItemDecorHomeActivity1(spacingInPixels));
            newslayoutManager.setSmoothScrollbarEnabled(true);

            if(isMoreDataAvailablenews ) {

                //Colleges in cities
                try {

                    String WORKSPACE_ID1 = "HOME_WORKSPACE";
                    String FUNCTION_ID1 = "GET_NEWS_ONLY";
                    String ACTION_ID1 = "GET_ALL_NEWS_STRING";

                    String server = Util.getProperty("name", contextAdaptor);
                    serverUrl = server.concat("/HomeAllNotifications");


                    new GetStringNewsNotifyTask3(WORKSPACE_ID1, FUNCTION_ID1, ACTION_ID1).execute(serverUrl);


           /* ImagesArray[i] = BitmapFactory.decodeResource(contextAdaptor.getResources(),
                    R.drawable.load_mask);*/


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if(position==4){
            ListofAllNotificationsAdaptor2 =new ListofAllNotifications2( StringData1,  this,contextAdaptor);
            LinearLayoutManager newslayoutManager = new LinearLayoutManager(contextAdaptor, LinearLayoutManager.HORIZONTAL, false);
            holder.newsRecyclerView2.setNestedScrollingEnabled(false);
            holder.newsRecyclerView2.setLayoutManager(newslayoutManager);

            holder.newsRecyclerView2.setAdapter(ListofAllNotificationsAdaptor2);
            int spacingInPixels = contextAdaptor.getResources().getDimensionPixelSize(R.dimen.spacing);
            holder.newsRecyclerView2.addItemDecoration(new SpacesItemDecorHomeActivity1(spacingInPixels));
            newslayoutManager.setSmoothScrollbarEnabled(true);

            if(isMoreDataAvailableexams) {

                //Colleges in cities
                try {

                    String WORKSPACE_ID1 = "HOME_WORKSPACE";
                    String FUNCTION_ID1 = "GET_EXAMS_ONLY";
                    String ACTION_ID1 = "GET_CLG_EXAMS_STRING";

                    String server = Util.getProperty("name", contextAdaptor);
                    serverUrl = server.concat("/HomeAllNotifications");


                    new GetStringNewsNotifyTask4(WORKSPACE_ID1, FUNCTION_ID1, ACTION_ID1).execute(serverUrl);


           /* ImagesArray[i] = BitmapFactory.decodeResource(contextAdaptor.getResources(),
                    R.drawable.load_mask);*/


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if(position==3){
            ListofAllNotificationsAdaptor3 =new ListofAllNotifications3( StringData2,  this,contextAdaptor);
            LinearLayoutManager newslayoutManager = new LinearLayoutManager(contextAdaptor, LinearLayoutManager.HORIZONTAL, false);
            holder.newsRecyclerView3.setNestedScrollingEnabled(false);
            holder.newsRecyclerView3.setLayoutManager(newslayoutManager);

            holder.newsRecyclerView3.setAdapter(ListofAllNotificationsAdaptor3);
            int spacingInPixels = contextAdaptor.getResources().getDimensionPixelSize(R.dimen.spacing);
            holder.newsRecyclerView3.addItemDecoration(new SpacesItemDecorHomeActivity1(spacingInPixels));
            newslayoutManager.setSmoothScrollbarEnabled(true);

            if(isMoreDataAvailablefests) {
            try {
                String  server =  Util.getProperty("name", contextAdaptor);

                serverUrl= server.concat("/HomeAllNotifications");
            } catch (IOException e) {
                e.printStackTrace();
            }
/*        for(int i =0;i<=9;i++)
        {
            new GetDataNewsNotifyTask(i).execute(serverUrl);
        }*/
            String WORKSPACE_ID ="HOME_WORKSPACE";
            String FUNCTION_ID = "GET_FESTS_NOTIFICATIONS";
            String ACTION_ID = "GET_ALL_FESTNEWS_STRING";

            new GetStringNewsNotifyTask5(WORKSPACE_ID,FUNCTION_ID,ACTION_ID).execute(serverUrl);


        }}
        //commenting for admissions
    /*    if(position==5){
            ListofAllNotificationsAdaptor4 =new ListofAllNotifications4( StringData3,  this,contextAdaptor);
            LinearLayoutManager newslayoutManager = new LinearLayoutManager(contextAdaptor, LinearLayoutManager.HORIZONTAL, false);
            holder.newsRecyclerView4.setNestedScrollingEnabled(false);
            holder.newsRecyclerView4.setLayoutManager(newslayoutManager);

            holder.newsRecyclerView4.setAdapter(ListofAllNotificationsAdaptor4);
            int spacingInPixels = contextAdaptor.getResources().getDimensionPixelSize(R.dimen.spacing);
            holder.newsRecyclerView4.addItemDecoration(new SpacesItemDecorHomeActivity1(spacingInPixels));
            newslayoutManager.setSmoothScrollbarEnabled(true);

            if(isMoreDataAvailableadmissions ) {

                //Colleges in cities
                try {

                    String WORKSPACE_ID1 = "HOME_WORKSPACE";
                    String FUNCTION_ID1 = "GET_ADMIS_ONLY";
                    String ACTION_ID1 = "GET_CLG_ADMIS_STRING";

                    String server = Util.getProperty("name", contextAdaptor);
                    serverUrl = server.concat("/HomeAllNotifications");


                    new GetStringNewsNotifyTask6(WORKSPACE_ID1, FUNCTION_ID1, ACTION_ID1).execute(serverUrl);


           *//* ImagesArray[i] = BitmapFactory.decodeResource(contextAdaptor.getResources(),
                    R.drawable.load_mask);*//*


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }*/
        /*if(position==2) {
  *//*          //int spacingInPixels = contextAdaptor.getString(R.string.top_filters);
            holder.headerforviews.setText(contextAdaptor.getString(R.string.top_filters));
           // holder.headerforviews.setBackgroundResource(R.drawable.rounded_corner5);
            holder.headerforviews.setTextSize(15);
*//*
        }
        if(position==3) {
           // holder.headerforviews.setText(contextAdaptor.getString(R.string.clgs_in_cities));

        }
        if(position==4) {


            LinearLayoutManager newslayoutManager = new LinearLayoutManager(contextAdaptor, LinearLayoutManager.HORIZONTAL, false);
            holder.newsRecyclerView1.setNestedScrollingEnabled(false);
            holder.newsRecyclerView1.setLayoutManager(newslayoutManager);

            holder.newsRecyclerView1.setAdapter(ListofAllNotificationsAdaptor);

            if(isMoreDataAvailableappicons) {
                int spacingInPixels = contextAdaptor.getResources().getDimensionPixelSize(R.dimen.spacing);
                holder.newsRecyclerView.addItemDecoration(new SpacesItemDecorHomeActivity1(spacingInPixels));
                newslayoutManager.setSmoothScrollbarEnabled(true);
                //Colleges in cities
                try {

                    String WORKSPACE_ID1 = "HOME_WORKSPACE";
                    String FUNCTION_ID1 = "GET_LINEAR_DATA";
                    String ACTION_ID1 = "CLGS_ALL_APPS";

                    String server = Util.getProperty("name", contextAdaptor);
                    serverUrl = server.concat("/HomeAllNotifications");


                    new GetStringNewsNotifyTask2(WORKSPACE_ID1, FUNCTION_ID1, ACTION_ID1).execute(serverUrl);


           *//* ImagesArray[i] = BitmapFactory.decodeResource(contextAdaptor.getResources(),
                    R.drawable.load_mask);*//*


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if(position==5) {
           // holder.headerforviews.setText(contextAdaptor.getString(R.string.clgs_in_topstates));
        }
        if(position==6) {


            LinearLayoutManager newslayoutManager = new LinearLayoutManager(contextAdaptor, LinearLayoutManager.HORIZONTAL, false);
            holder.newsRecyclerView1.setNestedScrollingEnabled(false);
            holder.newsRecyclerView1.setLayoutManager(newslayoutManager);

            holder.newsRecyclerView1.setAdapter(ListofAllNotificationsAdaptor1);
            int spacingInPixels = contextAdaptor.getResources().getDimensionPixelSize(R.dimen.spacing);
            holder.newsRecyclerView1.addItemDecoration(new SpacesItemDecorHomeActivity(spacingInPixels));
            newslayoutManager.setSmoothScrollbarEnabled(true);
     *//*       if(!isFirstRunStates){

                mAdapter.notifyItemChanged(6);}*//*
            // RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.newsRecyclerView.getLayoutParams();
            //holder.newsRecyclerView.setLayoutParams(new LinearLayoutCompat.LayoutParams(240, 240));
        }
        if(position==7) {
           // holder.headerforviews.setText(contextAdaptor.getString(R.string.state_wise_clgs));
        }
        if(position==8) {


            LinearLayoutManager newslayoutManager = new LinearLayoutManager(contextAdaptor, LinearLayoutManager.HORIZONTAL, false);
            holder.newsRecyclerView2.setNestedScrollingEnabled(false);
            holder.newsRecyclerView2.setLayoutManager(newslayoutManager);

            holder.newsRecyclerView2.setAdapter(ListofAllNotificationsAdaptor2);
            int spacingInPixels = contextAdaptor.getResources().getDimensionPixelSize(R.dimen.spacing);
            holder.newsRecyclerView2.addItemDecoration(new SpacesItemDecorHomeActivity(spacingInPixels));
            newslayoutManager.setSmoothScrollbarEnabled(true);
           *//* if(!isFirstRunStream){

                mAdapter.notifyItemChanged(8);}*//*
            // RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.newsRecyclerView.getLayoutParams();
            //holder.newsRecyclerView.setLayoutParams(new LinearLayoutCompat.LayoutParams(240, 240));
        }

        if(position==9) {


            LinearLayoutManager newslayoutManager = new LinearLayoutManager(contextAdaptor, LinearLayoutManager.HORIZONTAL, false);
            holder.newsRecyclerView3.setNestedScrollingEnabled(false);
            holder.newsRecyclerView3.setLayoutManager(newslayoutManager);

            holder.newsRecyclerView3.setAdapter(ListofAllNotificationsAdaptor3);
            int spacingInPixels = contextAdaptor.getResources().getDimensionPixelSize(R.dimen.spacing);
            holder.newsRecyclerView3.addItemDecoration(new SpacesItemDecorHomeActivity(spacingInPixels));
            newslayoutManager.setSmoothScrollbarEnabled(true);
  *//*          if(!isFirstRunRating){

                mAdapter.notifyItemChanged(9);}*//*
            // RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.newsRecyclerView.getLayoutParams();
            //holder.newsRecyclerView.setLayoutParams(new LinearLayoutCompat.LayoutParams(240, 240));
        }
        if(position==10) {
            holder.headerforviews.setText("");
            holder.headerforviews.setPadding(0,0,0,20);
            //NIT's Notifications&News
        }

*//*       if(position==12) {
       String linearLayoutID="nitsNewsNotificationsID";
            String WORKSPACE_ID = "HOME_WORKSPACE";
            String FUNCTION_ID = "GET_LINEAR_DATA";
            String ACTION_ID = "HOME_NIT_NEWS_NOTIFY_ID";
            try {
                String  server =  Util.getProperty("name", contextAdaptor);
                serverUrl= server.concat("/HomeAllNotifications");

            } catch (IOException e) {
                e.printStackTrace();
            }
            for(int i =0;i<=7;i++)
            {
            new AllNotifications(i,linearLayoutID,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);
            }
           LinearLayoutManager x = new LinearLayoutManager(contextAdaptor, LinearLayoutManager.HORIZONTAL, false);
            //.setActiveUserPosition(position);
            holder.nitnewsRecyclerView.setLayoutManager(x);
            holder.nitnewsRecyclerView.setNestedScrollingEnabled(false);
            nitsNewsNotifications = new NitsNewsNotifications(maincontents1, ArrayListdata,  this);
            holder.nitnewsRecyclerView.setAdapter(nitsNewsNotifications);
            int spacingInPixels = contextAdaptor.getResources().getDimensionPixelSize(R.dimen.spacing);
            holder.nitnewsRecyclerView.addItemDecoration(new SpacesItemDecorHomeActivity(spacingInPixels));
            x.setSmoothScrollbarEnabled(true);
            // RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.newsRecyclerView.getLayoutParams();
            //holder.newsRecyclerView.setLayoutParams(new LinearLayoutCompat.LayoutParams(240, 240));
        }*//*
        // holder.label.setText(mDataset[position]+'\n');
        // holder.label.setTextColor(Color.BLACK);
        //    holder.label.setText(mDataset[position]);


        //   holder.imageView.setImageResource(imagedata[position]);
//used for image trasition
   *//*     final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i=position;
            public void run() {
                holder.imageView.setImageResource(imagedata[i]);
                i++;
                if(i>position+1)
                {
                    i=position;
                }
                handler.postDelayed(this, 2000) ;
            }
        };
        handler.postDelayed(runnable, 2000); //for initial delay..*//*



      *//*  holder.imageView.setImageDrawable(td1);
        td1.startTransition(10000);
        // and
        td1.reverseTransition(10000); *//*

        // RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.view_title.getLayoutParams();
        //RecyclerView.LayoutParams params = ( RecyclerView.LayoutParams) holder.imageView.getLayoutParams();
        //  params.setMargins(50,50,50,50);
        // holder.imageView.setLayoutParams(params);

        //holder.itemView.setLayoutParams(params);
        // holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        // holder.itemView.setPadding(8, 8, 8, 8);

        // holder.mTextView.setText(mDataset[position]);
        //  holder.mTextView.setTextColor(Color.BLACK);

      *//*  holder.label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClick(view, position);
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClick(view, position);
            }
        });*//*
      if(position==1){}
      *//*  if(position>=2 &&position<=7 ){
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mListener.onClick(view, position);
                }
            });}*//*
*//*        if(position==1) {
            holder.imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mListener.onClick(view, position-1);
                }
            });
        }*//*
*//*        if(position>=10 &&position<=15 ) {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mListener.onClick(view, position-9);
                }
            });
            holder.StringHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mListener.onClick(view, position-9);
                }
            });
        }*/
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
   /* public int getItemCount() {
        return mDataset.length;
    }*/
    public int getItemCount() {
        return 5;}

    @Override
    public int getItemViewType(int position) {
        if (position ==0)
        {
            return 0;
        }

        if (position ==1)
        {
            return 1;
        }
        if (position ==2)
        {
            return 2;
        }
        if(position ==3) {
            return 3;
        }
        if(position ==4) {
            return 4;
        }
    /*    if(position ==5) {
            return 5;
        }*/
/*        if(position ==2) {
            return 2;
        }
        if(position ==4) {
            return 3;
        }
        if(position ==5) {
            return 2;
        }
        if(position ==6) {
            return 6;
        }
        if(position ==7) {
            return 2;
        }
        if(position ==8) {
            return 8;
        }
        if(position ==9) {
            return 9;
        }
*//*        if(position >=10 && position<=15) {
            return 4;
        }*//*
        if(position ==10) {
            return 2;
        }

        if(position ==3) {
            return 2;
        }*/

        return super.getItemViewType(position);
    }






    private void init(final carrerGuidanceViewHolder holder) {



       // holder.mPager.invalidate();


        holder.mPager.setAdapter(viewpageAdaptor);
        holder.mPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=1;String UniqueKey="MAIN_HEADER_ADS";
                selectItem(position,UniqueKey);
            }
        });



        holder.indicator.setViewPager(holder.mPager);

        final float density = contextAdaptor.getResources().getDisplayMetrics().density;

        holder.indicator.setRadius(3* density);



        NUM_PAGES =5;



        holder.mPager.setCurrentItem(5,true);
      //  holder.indicator.setCurrentItem(4);
        // Pager listener over indicator
        holder.indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }
            public void onPageClick(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

if(isMoreDataAvailableads){
    // Auto start of viewpager
    handler = new Handler();
    Update = new Runnable() {
        public void run() {
            if (currentPage == NUM_PAGES) {
                currentPage = 0;
            }
            holder.mPager.setCurrentItem(currentPage++, true);
        }
    };
    Timer swipeTimer = new Timer();
    swipeTimer.schedule(new TimerTask() {
        @Override
        public void run() {
            handler.post(Update);
        }
    }, 3000, 3000);

        try {
            String WORKSPACE_ID = "HOME_WORKSPACE";
            String FUNCTION_ID = "GET_LINEAR_DATA";
            String ACTION_ID = "MAIN_ADS_HEADER_ID";

            String  server =  Util.getProperty("name", contextAdaptor);
            serverUrl= server.concat("/HomeAllNotifications");

           /*if(pos>1){
            SharedPreferences prefs = contextAdaptor.getSharedPreferences(PREFS_NAME, 0);
            String pathtoget =prefs.getString("imagepath"+Integer.toString(i),null);
            loadImageFromStorage( pathtoget,Integer.toString(i));}
           else {*/
            new GetStringNewsNotifyTask1(WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);


           /* ImagesArray[i] = BitmapFactory.decodeResource(contextAdaptor.getResources(),
                    R.drawable.load_mask);*/


        } catch (Exception e) {
            e.printStackTrace();
        }
    }}
    @Override
    public void onClick(View view, int position,String UniqueKey) {
        selectItem(position,UniqueKey);
    }

    @Override
    public void onClick(View view, int position) {
        if(position==0)
        {
            UserLoginManager checkuserstatus = new UserLoginManager(getApplicationContext());
            if(checkuserstatus.isLoggedIn()){
                Intent i = new Intent(getApplicationContext(),homeMapsActivity.class);
                contextAdaptor.startActivity(i);
            }
            else{
                Toast.makeText(getApplicationContext(), R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),UserLogin.class);
                contextAdaptor.startActivity(i);
            }
        }
if(position==1) {
    UserLoginManager checkuserstatus = new UserLoginManager(getApplicationContext());
    if(checkuserstatus.isLoggedIn()){
        Slide slideTransition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            slideTransition = new Slide(Gravity.LEFT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            slideTransition.setDuration(contextAdaptor.getResources().getInteger(R.integer.anim_duration_long));
        }
        String toolbarTorF = "T";
        NewsFragment newsFragment = NewsFragment.newInstance(toolbarTorF);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            newsFragment.setSharedElementEnterTransition(new ChangeBounds());
        }
        newsFragment.setReenterTransition(slideTransition);
        newsFragment.setExitTransition(slideTransition);


        android.support.v4.app.FragmentManager manager = ((AppCompatActivity) contextAdaptor).getSupportFragmentManager();
        manager.beginTransaction()

                .addToBackStack(TAG)
                .replace(R.id.view_onclick_frame, newsFragment)
                .commit();
    }
    else{
        Toast.makeText(getApplicationContext(), R.string.please_login_to_access, Toast.LENGTH_LONG).show();
        Intent i = new Intent(getApplicationContext(),UserLogin.class);
        contextAdaptor.startActivity(i);
    }






}

        if(position==2) {

            UserLoginManager checkuserstatus = new UserLoginManager(getApplicationContext());
            if(checkuserstatus.isLoggedIn()){
                Slide slideTransition = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    slideTransition = new Slide(Gravity.LEFT);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    slideTransition.setDuration(contextAdaptor.getResources().getInteger(R.integer.anim_duration_long));
                }
                FestNewsNotifications festFragment = new FestNewsNotifications();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    festFragment.setSharedElementEnterTransition(new ChangeBounds());
                }
                festFragment.setReenterTransition(slideTransition);
                festFragment.setExitTransition(slideTransition);


                android.support.v4.app.FragmentManager manager = ((AppCompatActivity) contextAdaptor).getSupportFragmentManager();
                manager.beginTransaction()

                        .addToBackStack(TAG)
                        .replace(R.id.view_onclick_frame, festFragment)
                        .commit();
            }
            else{
                Toast.makeText(getApplicationContext(), R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),UserLogin.class);
                contextAdaptor.startActivity(i);
            }



        }
        if(position==3) {

            UserLoginManager checkuserstatus = new UserLoginManager(getApplicationContext());
            if(checkuserstatus.isLoggedIn()){

                Slide slideTransition = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    slideTransition = new Slide(Gravity.LEFT);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    slideTransition.setDuration(contextAdaptor.getResources().getInteger(R.integer.anim_duration_long));
                }
                HomeExamsFragment examFragment = new HomeExamsFragment();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    examFragment.setSharedElementEnterTransition(new ChangeBounds());
                }
                examFragment.setReenterTransition(slideTransition);
                examFragment.setExitTransition(slideTransition);


                android.support.v4.app.FragmentManager manager = ((AppCompatActivity) contextAdaptor).getSupportFragmentManager();
                manager.beginTransaction()

                        .addToBackStack(TAG)
                        .replace(R.id.view_onclick_frame, examFragment)
                        .commit();
            }
            else{
                Toast.makeText(getApplicationContext(), R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),UserLogin.class);
                contextAdaptor.startActivity(i);
            }


        }
   /*     if(position==4) {

            UserLoginManager checkuserstatus = new UserLoginManager(getApplicationContext());
            if(checkuserstatus.isLoggedIn()){


                Slide slideTransition = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    slideTransition = new Slide(Gravity.LEFT);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    slideTransition.setDuration(contextAdaptor.getResources().getInteger(R.integer.anim_duration_long));
                }
                CollegeAdmisFragment admissionfrag = new CollegeAdmisFragment();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    admissionfrag.setSharedElementEnterTransition(new ChangeBounds());
                }
                admissionfrag.setReenterTransition(slideTransition);
                admissionfrag.setExitTransition(slideTransition);


                android.support.v4.app.FragmentManager manager = ((AppCompatActivity) contextAdaptor).getSupportFragmentManager();
                manager.beginTransaction()

                        .addToBackStack(TAG)
                        .replace(R.id.view_onclick_frame, admissionfrag)
                        .commit();
            }
            else{
                Toast.makeText(getApplicationContext(), R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),UserLogin.class);
                contextAdaptor.startActivity(i);
            }

        }*/
        if(position==4) {

            UserLoginManager checkuserstatus = new UserLoginManager(getApplicationContext());
            if(checkuserstatus.isLoggedIn()){

                Slide slideTransition = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    slideTransition = new Slide(Gravity.LEFT);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    slideTransition.setDuration(contextAdaptor.getResources().getInteger(R.integer.anim_duration_long));
                }
                RankingsFragment userrat = RankingsFragment.newInstance("", "", "");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    userrat.setSharedElementEnterTransition(new ChangeBounds());
                }
                userrat.setReenterTransition(slideTransition);
                userrat.setExitTransition(slideTransition);


                android.support.v4.app.FragmentManager manager = ((AppCompatActivity) contextAdaptor).getSupportFragmentManager();
                manager.beginTransaction()

                        .addToBackStack(TAG)
                        .replace(R.id.view_onclick_frame, userrat)
                        .commit();
            }
            else{
                Toast.makeText(getApplicationContext(), R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),UserLogin.class);
                contextAdaptor.startActivity(i);
            }


        }
        if(position==5) {


            UserLoginManager checkuserstatus = new UserLoginManager(getApplicationContext());
            if(checkuserstatus.isLoggedIn()){

                Slide slideTransition = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    slideTransition = new Slide(Gravity.LEFT);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    slideTransition.setDuration(contextAdaptor.getResources().getInteger(R.integer.anim_duration_long));
                }
                CutoffFirstFragment cutoffsFragment = new CutoffFirstFragment();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    cutoffsFragment.setSharedElementEnterTransition(new ChangeBounds());
                }
                cutoffsFragment.setReenterTransition(slideTransition);
                cutoffsFragment.setExitTransition(slideTransition);


                android.support.v4.app.FragmentManager manager = ((AppCompatActivity) contextAdaptor).getSupportFragmentManager();
                manager.beginTransaction()

                        .addToBackStack(TAG)
                        .replace(R.id.view_onclick_frame, cutoffsFragment)
                        .commit();
            }
            else{
                Toast.makeText(getApplicationContext(), R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),UserLogin.class);
                contextAdaptor.startActivity(i);
            }


        }
        if(position==6) {

            UserLoginManager checkuserstatus = new UserLoginManager(getApplicationContext());
            if(checkuserstatus.isLoggedIn()){
                Slide slideTransition = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    slideTransition = new Slide(Gravity.LEFT);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    slideTransition.setDuration(contextAdaptor.getResources().getInteger(R.integer.anim_duration_long));
                }
                PlaceFirstFragment placefrag = new PlaceFirstFragment();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    placefrag.setSharedElementEnterTransition(new ChangeBounds());
                }
                placefrag.setReenterTransition(slideTransition);
                placefrag.setExitTransition(slideTransition);


                android.support.v4.app.FragmentManager manager = ((AppCompatActivity) contextAdaptor).getSupportFragmentManager();
                manager.beginTransaction()

                        .addToBackStack(TAG)
                        .replace(R.id.view_onclick_frame, placefrag)
                        .commit();
            }
            else{
                Toast.makeText(getApplicationContext(), R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),UserLogin.class);
                contextAdaptor.startActivity(i);
            }




        }
    }

    @Override
    public void onClick(View view, int position, String clg_id,String festimage,String collegename,ImageView imagetrasition ) {
        int x = view.getId();

        selectItem(position,clg_id,festimage,collegename, imagetrasition);
    }
    @Override
    public void onClick(View view, String examid,int position) {
        selectItem(examid,position);
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
        UserLoginManager checkuserstatus = new UserLoginManager(getApplicationContext());
        if(checkuserstatus.isLoggedIn()){


            Intent i2 = new Intent(contextAdaptor,TabsHeaderActivity.class);
            i2.putExtra("clg_id",clg_id);
            i2.putExtra("festimage",festimage);
            i2.putExtra("EXTRA_IMAGE_TRANSITION_NAME", ViewCompat.getTransitionName(imagetrasition));

            i2.putExtra("collegename",collegename);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (HomeActivity) contextAdaptor,
                    imagetrasition,
                    ViewCompat.getTransitionName(imagetrasition));
            contextAdaptor.startActivity(i2,options.toBundle());
        }
        else{
            Toast.makeText(getApplicationContext(), R.string.please_login_to_access, Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(),UserLogin.class);
            contextAdaptor.startActivity(i);
        }


    }
    private void selectItem(String Id,int position) {





        UserLoginManager checkuserstatus = new UserLoginManager(getApplicationContext());
        if(checkuserstatus.isLoggedIn()){


                Slide slideTransition = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    slideTransition = new Slide(Gravity.LEFT);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    slideTransition.setDuration(contextAdaptor.getResources().getInteger(R.integer.anim_duration_long));
                }
                Fragment fr = ExamsDetailsViewPager.newInstance(Id);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    fr.setSharedElementEnterTransition(new ChangeBounds());
                }
                fr.setReenterTransition(slideTransition);
                fr.setExitTransition(slideTransition);


                android.support.v4.app.FragmentManager manager = ((AppCompatActivity) contextAdaptor).getSupportFragmentManager();
                manager.beginTransaction()

                        .addToBackStack(TAG)
                        .replace(R.id.view_onclick_frame, fr)
                        .commit();
            }
        else{
            Toast.makeText(getApplicationContext(), R.string.please_login_to_access, Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(),UserLogin.class);
            contextAdaptor.startActivity(i);
        }


        // Transition for fragment1
        Slide slideTransition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            slideTransition = new Slide(Gravity.LEFT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            slideTransition.setDuration(contextAdaptor.getResources().getInteger(R.integer.anim_duration_long));
        }

    }
    @Override
    public void onClick(int adapterPosition, HashMap hashMap, ImageView sharedImageView) {
/*
        Slide slideTransition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            slideTransition = new Slide(Gravity.LEFT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            slideTransition.setDuration(contextAdaptor.getResources().getInteger(R.integer.anim_duration_long));
        }

        Fragment DetailFragment = HomeNewsDetailActivity.newInstance(hashMap, ViewCompat.getTransitionName(sharedImageView));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            DetailFragment.setSharedElementEnterTransition(new ChangeBounds());
        }
        DetailFragment.setReenterTransition(slideTransition);
        DetailFragment.setExitTransition(slideTransition);


        android.support.v4.app.FragmentManager manager = ((AppCompatActivity)contextAdaptor).getSupportFragmentManager();
        manager.beginTransaction()
                .addSharedElement(sharedImageView, ViewCompat.getTransitionName(sharedImageView))
                .addToBackStack(TAG)
                .replace(R.id.sample_content_fragment, DetailFragment)
                .commit();
*/



        UserLoginManager checkuserstatus = new UserLoginManager(getApplicationContext());
        if(checkuserstatus.isLoggedIn()){


            Intent i2 = new Intent(contextAdaptor,HomeNewsDetailActivity.class);
            i2.putExtra("HASHMAP_TOSEND", hashMap);
            i2.putExtra("EXTRA_IMAGE_TRANSITION_NAME", ViewCompat.getTransitionName(sharedImageView));

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (HomeActivity) contextAdaptor,
                    sharedImageView,
                    ViewCompat.getTransitionName(sharedImageView));
            contextAdaptor.startActivity(i2,options.toBundle());
        }
        else{
            Toast.makeText(getApplicationContext(), R.string.please_login_to_access, Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(),UserLogin.class);
            contextAdaptor.startActivity(i);
        }

    }
    private void selectItem(int position,String UniqueKey) {


if(position==10) {
    // ((HomeActivity)getActivity()).getSupportActionBar(). setHomeAsUpIndicator(R.drawable.back_button);
    android.support.v4.app.Fragment fr = HomeDetailedData.newInstance();

    android.support.v4.app.FragmentManager fm = ((HomeActivity) contextAdaptor).getSupportFragmentManager();
    // FragmentManager fm12 = contextAdaptor.getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fm.beginTransaction();
    fragmentTransaction.replace(R.id.view_onclick_frame, fr);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
}

    }
    public class GetStringNewsNotifyTask1  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        View objview;
        private String COLLEGEID;

        public GetStringNewsNotifyTask1(String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;

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
                Record.put( "POSITION_NO", 1);

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
                        ImagesArray.clear();
                        for (int i = 0; i < lenghtl; i++) {
                            JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));
                            HashMap<String,String> hashmap= new HashMap();
                            String NOTIFYS_ID = (String) clgdata.get("NOTIFYS_ID");
                            String NOTIFY_HEADER = (String) clgdata.get("NOTIFY_HEADER");
                            String NOTIFY_DETAILS = (String) clgdata.get("NOTIFY_DETAILS");
                            String NOTIFY_IMG = (String) clgdata.get("NOTIFY_IMG");
                            String AD_TYPE = (String) clgdata.get("AD_TYPE");
                            String TYPE_ID = (String) clgdata.get("TYPE_ID");
                            String FEST_IMAGE = (String) clgdata.get("FEST_IMAGE");
                            String FEST_CLG_NAME = (String) clgdata.get("FEST_CLG_NAME");
                            String NEWS_HEADER = (String) clgdata.get("NEWS_HEADER");
                            String NEWS_DETAILS = (String) clgdata.get("NEWS_DETAILS");
                            String NEWS_IMAGE = (String) clgdata.get("NEWS_IMAGE");
                            hashmap.put("NOTIFYS_ID",NOTIFYS_ID);
                            hashmap.put("NOTIFY_HENOTIFY_IMGADER",NOTIFY_HEADER);
                            hashmap.put("NOTIFY_DETAILS",NOTIFY_DETAILS);
                            hashmap.put("NOTIFY_IMG",NOTIFY_IMG);
                            hashmap.put("AD_TYPE",AD_TYPE);
                            hashmap.put("TYPE_ID",TYPE_ID);
                            hashmap.put("FEST_IMAGE",FEST_IMAGE);
                            hashmap.put("FEST_CLG_NAME",FEST_CLG_NAME);
                            hashmap.put("NEWS_HEADER",NEWS_HEADER);
                            hashmap.put("NEWS_DETAILS",NEWS_DETAILS);
                            hashmap.put("NEWS_IMAGE",NEWS_IMAGE);

                            ImagesArray.add(hashmap);
                         /*   data.setText("College Name: "+festinfo);
                            String  imageserver = null;
                            try {
                                imageserver =  UtilProperty.getProperty("img_ClgFestFragment", contextForFragment,"imagepath.properties");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Picasso.with(contextForFragment).load(imageserver+imageinfo).into(data1);
*/

                        }
                        isMoreDataAvailableads=false;
                        viewpageAdaptor.notifyDataSetChanged();


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
    public class GetStringNewsNotifyTask2  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        View objview;
        private String COLLEGEID;

        public GetStringNewsNotifyTask2(String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;

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
                Record.put( "POSITION_NO", 1);

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

                            JSONObject clgdata =new JSONObject( result);
                        for(int i = 0; i<clgdata.names().length(); i++) {
                           // Log.v(TAG, "key = " + clgdata.names().getString(i) + " value = " + clgdata.get(clgdata.names().getString(i)));


                            arrayForAllNotificationsnew.put(clgdata.names().getString(i), clgdata.get(clgdata.names().getString(i)).toString());
                        }                         /*   data.setText("College Name: "+festinfo);
                            String  imageserver = null;
                            try {
                                imageserver =  UtilProperty.getProperty("img_ClgFestFragment", contextForFragment,"imagepath.properties");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Picasso.with(contextForFragment).load(imageserver+imageinfo).into(data1);
*/

                        } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    isMoreDataAvailableappicons=false;
                    mAdapter.notifyItemChanged(1);


                        //   LatLng sydney = new LatLng(clgLat,80);
                        //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                    }
                    // xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                }
            }
        }
    public class GetStringNewsNotifyTask3  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        View objview;
        private String COLLEGEID;

        public GetStringNewsNotifyTask3(String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;

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
                Record.put( "POSITION_NO", 1);

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
                 //   xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                    try {
                        JSONObject obj = new JSONObject(ing);
                        Marker marker;
                        int lenghtl = obj.length();
                        StringData.clear();
                        //StringData=new  String[8];
                        for (int i = 0; i < lenghtl; i++) {
                            JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));
                            HashMap<String,String>hashmap= new HashMap();
                            String StringNews = Util.Nullcheck((String) clgdata.get("NEWS_HEADER"));
                            String NewsId = Util.Nullcheck((String) clgdata.get("NEWSID"));
                            String posteddate = Util.Nullcheck((String) clgdata.get("POSTED_DATE"));
                            String TAG_TYPE = Util.Nullcheck((String) clgdata.get("TAG_TYPE"));
                            String TAG_COLOR = Util.Nullcheck((String) clgdata.get("TAG_COLOR"));
                            String FULL_IMAGE = Util.Nullcheck((String) clgdata.get("FULL_IMAGE"));
                            String NEWS_DETAILS = Util.Nullcheck((String) clgdata.get("NEWS_DETAILS"));
                            String NEWS_IMAGE = Util.Nullcheck((String) clgdata.get("NEWS_IMAGE"));
                            hashmap.put("NEWS_HEADER",StringNews);
                            hashmap.put("NEWSID",NewsId);
                            hashmap.put("NEWS_DETAILS",NEWS_DETAILS);
                            hashmap.put("NEWS_IMAGE",NEWS_IMAGE);
                            hashmap.put("POSTED_DATE",posteddate);
                            hashmap.put("TAG_TYPE",TAG_TYPE);
                            hashmap.put("TAG_COLOR",TAG_COLOR);
                            hashmap.put("FULL_IMAGE",FULL_IMAGE);




                            StringData.add(hashmap);
                        }

                        isMoreDataAvailablenews=false;
                        mAdapter.notifyItemChanged(2);
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
                  //  xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                }
            }
        }

    }
    public class GetStringNewsNotifyTask4  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        View objview;
        private String COLLEGEID;

        public GetStringNewsNotifyTask4(String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;

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
                Record.put( "POSITION_NO", 1);

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
                        StringData1.clear();
                        for (int i = 0; i < lenghtl; i++) {
                            JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));
                            HashMap<String,String>hashmap= new HashMap();



                            String EXAMID = Util.Nullcheck((String) clgdata.get("EXAMID"));
                            String CLG_NAME = Util.Nullcheck((String) clgdata.get("CLG_NAME"));
                            String EXAM_DETAILS = Util.Nullcheck((String) clgdata.get("EXAM_DETAILS"));
                            String POSTED_DATE =Util.Nullcheck( (String) clgdata.get("POSTED_DATE"));
                            String EXAM_NAME = Util.Nullcheck( (String) clgdata.get("EXAM_NAME"));
                            String EXAM_DATE = Util.Nullcheck((String) clgdata.get("EXAM_DATE"));
                            String OTHER_IMP_DETAILS =Util.Nullcheck( (String) clgdata.get("OTHER_IMP_DETAILS"));
                            String LOGO_IMAGE = Util.Nullcheck((String) clgdata.get("LOGO_IMAGE"));
                            String REG_START_DATE = Util.Nullcheck((String) clgdata.get("REG_START_DATE"));
                            String RESULT_DATE = Util.Nullcheck((String) clgdata.get("RESULT_DATE"));
                            String REG_END_DATE = Util.Nullcheck((String) clgdata.get("REG_END_DATE"));

                            hashmap.put("EXAMID",EXAMID);
                            hashmap.put("CLG_NAME",CLG_NAME);
                            hashmap.put("EXAM_DETAILS",EXAM_DETAILS);
                            hashmap.put("POSTED_DATE",POSTED_DATE);
                            hashmap.put("EXAM_NAME",EXAM_NAME);
                            hashmap.put("EXAM_DATE",EXAM_DATE);
                            hashmap.put("REG_START_DATE",REG_START_DATE);
                            hashmap.put("REG_END_DATE",REG_END_DATE);
                            hashmap.put("RESULT_DATE",RESULT_DATE);
                            hashmap.put("OTHER_IMP_DETAILS",OTHER_IMP_DETAILS);
                            hashmap.put("LOGO_IMAGE",LOGO_IMAGE);
                            hashmap.put("LOAD_CHECK","loaded");
                            StringData1.add(hashmap);
                        }
                        isMoreDataAvailableexams=false;
                        mAdapter.notifyItemChanged(4);
                        /*mAdapter.notifyItemChanged(2);
                        mAdapter.notifyItemChanged(3);
                        mAdapter.notifyItemChanged(4);
                        mAdapter.notifyItemChanged(5);
                        mAdapter.notifyItemChanged(6);
                        mAdapter.notifyItemChanged(7);*/


                        //   LatLng sydney = new LatLng(clgLat,80);
                        //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                }
            }
        }

    }
    public class GetStringNewsNotifyTask5  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        View objview;

        public GetStringNewsNotifyTask5() {

        }
        public GetStringNewsNotifyTask5(String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
        }

        @Override
        protected  void onPreExecute()
        {

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
                Record.put( "POSITION_NO", 1);
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

            }
            else {

                try {
                    JSONObject obj = new JSONObject(ing);
                    Marker marker;
                    int lenghtl = obj.length();
                    StringData2.clear();
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

                        StringData2.add(param_aux);

                    }
                    isMoreDataAvailablefests=false;
                    mAdapter.notifyItemChanged(3);

                    //   LatLng sydney = new LatLng(clgLat,80);
                    //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }
    public class GetStringNewsNotifyTask6  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        View objview;
        private String COLLEGEID;

        public GetStringNewsNotifyTask6(String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;

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
                Record.put( "POSITION_NO", 1);

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
        public void onPostExecute(String result) {
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
                        StringData3.clear();
                        for (int i = 0; i < lenghtl; i++) {
                            JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));
                            HashMap<String,String>hashmap= new HashMap();
                            String ADMISSIONID = (String) clgdata.get("ADMISSIONID");
                            String CLG_HEADER = (String) clgdata.get("CLG_HEADER");
                            String ADMISSION_DETAILS = (String) clgdata.get("ADMISSION_DETAILS");
                            String POSTED_DATE = (String) clgdata.get("POSTED_DATE");
                            String LOCATION = (String) clgdata.get("LOCATION");
                            String LOGO_IMAGE = (String) clgdata.get("LOGO_IMAGE");
                            String STREAM = (String) clgdata.get("STREAM");
                            String ADMISSION_IMAGE = (String) clgdata.get("ADMISSION_IMAGE");
                            String ADMISSION_LINK = (String) clgdata.get("ADMISSION_LINK");

                            hashmap.put("ADMISSIONID",ADMISSIONID);
                            hashmap.put("CLG_HEADER",CLG_HEADER);
                            hashmap.put("ADMISSION_DETAILS",ADMISSION_DETAILS);
                            hashmap.put("POSTED_DATE",POSTED_DATE);
                            hashmap.put("LOCATION",LOCATION);
                            hashmap.put("LOGO_IMAGE",LOGO_IMAGE);
                            hashmap.put("STREAM",STREAM);
                            hashmap.put("ADMISSION_IMAGE",ADMISSION_IMAGE);
                            hashmap.put("ADMISSION_LINK",ADMISSION_LINK);
                            hashmap.put("LOAD_CHECK","loaded");
                            StringData3.add(hashmap);
                        }
                        isMoreDataAvailableadmissions=false;
                        mAdapter.notifyItemChanged(5);
                       // mAdapter.notifyDataSetChanged();
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
    }
