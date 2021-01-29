package collegeexplore.CollegeInfo.CollegesAtMaps.CollegesOnclick;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import collegeexplore.CollegeInfo.CollegesAtMaps.CollegesOnclick.Fests.FestsMapsFragment;
import collegeexplore.CollegeInfo.CollegesAtMaps.CollegesOnclick.Reviews.ReviewFragment;
import collegeexplore.CollegeInfo.CollegesAtMaps.StillWorkingOn;
import collegeexplore.CollegeInfo.CutoffsWorkspace.CutoffThridFragment;
import collegeexplore.CollegeInfo.CutoffsWorkspace.CutoffThridFragmentForIIIT;
import collegeexplore.CollegeInfo.CutoffsWorkspace.CutoffThridFragmentForIIT;
import collegeexplore.CollegeInfo.NewsWorkspace.NewsFilter.NewsFragmentcollegeFilter;
import collegeexplore.CollegeInfo.PlacementsWorkspace.MainPlacementsFragment;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.Util;
import collegeexplore.CollegeInfo.UtilProperty;
import collegeexplore.logger.Log;
import collegeexplore.view.SlidingTabLayout;

import static android.content.ContentValues.TAG;


public class collegesView1 extends Fragment {


    public static ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout1;

    private Context contextForFragment;
    private WebView webView;

    SlidingImage_Adapter1 viewpageAdaptor;
    private String COLLEGE_IMAGES = "";
    ViewPager mPager;

    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private BottomSheetBehavior mBehavior;
    String COLLEGE_NAME;
    String COLLEGE_WEBSITE;
    String COLLEGE_PLACE;
    String COLLEGE_LOGO;
    String COLLEGE_LAT;
    String COLLEGE_LNG;
    TextView tv2;
    TextView college_name;
    String ABOUT_US="";
    Boolean isMoreDataAvailableads = true;
    public static String COLLEGEID_TO_PASS,CLG_NUM;

    public static collegesView1 newInstance(HashMap<String, HashMap> collegeDetails, String collegeID) {
        collegesView1 f = new collegesView1();

        Bundle args = new Bundle();
        HashMap<String, String> data = collegeDetails.get(collegeID);
        args.putString("COLLEGE_ID", collegeID);
        args.putString("COLLEGE_NAME", data.get("COLLEGE_NAME"));
        args.putString("COLLEGE_WEBSITE", data.get("COLLEGE_WEBSITE"));
        args.putString("COLLEGE_PLACE", data.get("COLLEGE_PLACE"));
        args.putString("COLLEGE_LOGO", data.get("COLLEGE_LOGO"));
        args.putString("COLLEGE_LAT", data.get("COLLEGE_LAT"));
        args.putString("COLLEGE_LNG", data.get("COLLEGE_LNG"));
        args.putString("CLG_IMAGES", data.get("COLLEGE_IMAGES"));
        args.putString("ABOUT_US", data.get("ABOUT_US"));
        args.putString("CLG_NUM", data.get("CLG_NUM"));

        f.setArguments(args);
        return f;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        contextForFragment = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            COLLEGE_NAME = Util.Nullcheck(getArguments().getString("COLLEGE_NAME"));

            COLLEGE_WEBSITE = Util.Nullcheck(getArguments().getString("COLLEGE_WEBSITE"));
            COLLEGE_PLACE = Util.Nullcheck(getArguments().getString("COLLEGE_PLACE"));
            COLLEGE_LOGO = Util.Nullcheck(getArguments().getString("COLLEGE_LOGO"));
            COLLEGE_LAT = Util.Nullcheck(getArguments().getString("COLLEGE_LAT"));
            COLLEGE_LNG = Util.Nullcheck(getArguments().getString("COLLEGE_LNG"));
            COLLEGE_IMAGES = Util.Nullcheck(getArguments().getString("CLG_IMAGES"));
            ABOUT_US=Util.Nullcheck(getArguments().getString("ABOUT_US"));
            COLLEGEID_TO_PASS = Util.Nullcheck(getArguments().getString("COLLEGE_ID"));
            CLG_NUM = Util.Nullcheck(getArguments().getString("CLG_NUM"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View x  =inflater.inflate(R.layout.home_data_layout, container, false);
        return inflater.inflate(R.layout.map_data_viewpager, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {


        super.onActivityCreated(savedInstanceState);

        View v = getView();
        final Toolbar toolbar = (Toolbar) v.findViewById(R.id.htab_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity)getActivity()). getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();
            }

        });

        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);


        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(viewPager);

        final net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout collapsingToolbarLayout = (net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout) v.findViewById(R.id.htab_collapse_toolbar);

        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.b_home_icon);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @SuppressWarnings("ResourceType")
                @Override
                public void onGenerated(Palette palette) {

                    int vibrantColor = palette.getVibrantColor(R.color.Black);
                    int vibrantDarkColor = palette.getDarkVibrantColor(R.color.Black);
                    collapsingToolbarLayout.setContentScrimColor(vibrantColor);
                    collapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor);
                }
            });

        } catch (Exception e) {
            // if Bitmap fetch fails, fallback to primary colors
            Log.e(TAG, "onCreate: failed to create bitmap from background", e.fillInStackTrace());
            collapsingToolbarLayout.setContentScrimColor(
                    ContextCompat.getColor(getContext(), R.color.AliceBlue)
            );
            collapsingToolbarLayout.setStatusBarScrimColor(
                    ContextCompat.getColor(getContext(), R.color.DarkOrchid)
            );
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
                Log.d(TAG, "onTabSelected: pos: " + tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        // TODO: 31/03/17
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        init();
        if (!(COLLEGE_LOGO == null)) {
            String imageserver = null;
            try {
                imageserver = UtilProperty.getProperty("img_collegesView", contextForFragment, "imagepath.properties");
            } catch (IOException e) {
                e.printStackTrace();
            }

        /*    TextView tv1 = v.findViewById(R.id.college_name);
            tv1.setText(CLG_NUM);*/
            net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout dande = v.findViewById(R.id.htab_collapse_toolbar);
            dande.setTitle(COLLEGE_NAME);

             tv2 = v.findViewById(R.id.college_website);
            tv2.setText(COLLEGE_WEBSITE);
            tv2.setOnClickListener(new View.OnClickListener() {

                public void onClick(View arg0) {
                    String url ="http://"+tv2.getText().toString();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }});
            ImageView tv = v.findViewById(R.id.college_logoimg);
            // ImageView tv12 = (ImageView) view.findViewById(R.id.college_logobackgrd);

            Picasso.with(contextForFragment).load(imageserver + COLLEGE_LOGO).resize(600, 200).into(tv);
            //Picasso.with(contextForFragment).load(imageserver + "nitpybackground.JPG").into(tv12);
        }
/*        college_name= v.findViewById(R.id.phonetxt);
        college_name.setOnClickListener(new View.OnClickListener() {


            public void onClick(View arg10) {


                String num= college_name.getText().toString();

                if(!num.equalsIgnoreCase("")) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + num.trim()));

                    startActivity(callIntent);
                }

            }});*/

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(AboutUsFragment.newInstance(COLLEGEID_TO_PASS,COLLEGE_NAME,ABOUT_US,CLG_NUM),  getString(R.string.about_us));
        if (COLLEGEID_TO_PASS.startsWith("NIT")){
            adapter.addFrag(CutoffThridFragment.newInstance(COLLEGEID_TO_PASS, COLLEGE_NAME, "F"),  getString(R.string.cutoff));
        }
        if (COLLEGEID_TO_PASS.startsWith("IIT")){
            adapter.addFrag(CutoffThridFragmentForIIT.newInstance(COLLEGEID_TO_PASS, COLLEGE_NAME, "F"),  getString(R.string.cutoff));
        }
        if (COLLEGEID_TO_PASS.startsWith("IIIT")){
            adapter.addFrag(CutoffThridFragmentForIIIT.newInstance(COLLEGEID_TO_PASS, COLLEGE_NAME, "F"),  getString(R.string.cutoff));
        }
        if (COLLEGEID_TO_PASS.startsWith("DEEM")){
            adapter.addFrag(new StillWorkingOn(),  getString(R.string.cutoff));
        }
        adapter.addFrag( MainPlacementsFragment.newInstance( COLLEGEID_TO_PASS,COLLEGE_NAME,"F"),  getString(R.string.placements));
        adapter.addFrag( FestsMapsFragment.newInstance(COLLEGEID_TO_PASS),  getString(R.string.fest));
        adapter.addFrag(DepartmentsFragment.newInstance(COLLEGEID_TO_PASS),  getString(R.string.department));
        adapter.addFrag(NewsFragmentcollegeFilter.newInstance(COLLEGEID_TO_PASS),  getString(R.string.clg_news));
        adapter.addFrag(ReviewFragment.newInstance(COLLEGEID_TO_PASS,COLLEGE_NAME), getString(R.string.review));
        viewPager.setAdapter(adapter);
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void init() {


        // holder.mPager.invalidate();

        String[] value_split =  COLLEGE_IMAGES.split("\\|");
        viewpageAdaptor = new SlidingImage_Adapter1(contextForFragment,value_split);
       // indicator = getView().findViewById(R.id.viewindicator);
        mPager = getView().findViewById(R.id.vpager);
        mPager.setAdapter(viewpageAdaptor);
        mPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = 1;
                String UniqueKey = "MAIN_HEADER_ADS";
                // selectItem(position,UniqueKey);
            }
        });


       // indicator.setViewPager(mPager);

        final float density = contextForFragment.getResources().getDisplayMetrics().density;

        //indicator.setRadius(5 * density);


        NUM_PAGES = value_split.length;

        if(isMoreDataAvailableads){
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 4000);

        // Pager listener over indicator
            isMoreDataAvailableads=false;
    }}
}