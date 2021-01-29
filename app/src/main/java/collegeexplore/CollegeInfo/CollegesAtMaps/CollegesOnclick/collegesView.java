package collegeexplore.CollegeInfo.CollegesAtMaps.CollegesOnclick;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import collegeexplore.CollegeInfo.NewsWorkspace.NewsFragment;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.Util;
import collegeexplore.CollegeInfo.UtilProperty;
import collegeexplore.CollegeInfo.homeNewsFragment;
import collegeexplore.view.SlidingTabLayout;



public class collegesView  extends Fragment {


    public static ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout1;
    private List<DataPagerItem> mTabs = new ArrayList<DataPagerItem>();
    private Context contextForFragment;
    private WebView webView;
    View view;
    SlidingImage_Adapter1 viewpageAdaptor;
    private String COLLEGE_IMAGES = null;
    ViewPager mPager;
    CirclePageIndicator indicator;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private BottomSheetBehavior mBehavior;
    String COLLEGE_NAME;
    String   COLLEGE_WEBSITE ;
    String  COLLEGE_PLACE ;
    String COLLEGE_LOGO ;
    String COLLEGE_LAT;
    String ABOUT_US="";
    String COLLEGE_LNG ,CLG_NUM;
public static String COLLEGEID_TO_PASS;
    public static collegesView newInstance(HashMap<String, HashMap> collegeDetails, String collegeID) {
        collegesView f = new collegesView();

        Bundle args = new Bundle();
      HashMap<String,String> data=  collegeDetails.get(collegeID);
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
    static class DataPagerItem {
        private final CharSequence mTitle;
        private final int mIndicatorColor;
        private final int mDividerColor;

        DataPagerItem(CharSequence title, int indicatorColor, int dividerColor) {
            mTitle = title;
            mIndicatorColor = indicatorColor;
            mDividerColor = dividerColor;
        }

        /**
         * @return A new {@link Fragment} to be displayed by a {@link ViewPager}
         */
        Fragment createFragment() {
            return homeNewsFragment.newInstance();
            // ArrayListFragment.newInstance(position);
        }

        /**
         * @return the title which represents this tab. In this sample this is used directly by
         * {@link android.support.v4.view.PagerAdapter#getPageTitle(int)}
         */
        CharSequence getTitle() {
            return mTitle;
        }


        int getIndicatorColor() {
            return mIndicatorColor;
        }


        int getDividerColor() {
            return mDividerColor;
        }
    }




    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        contextForFragment  = context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            COLLEGE_NAME = Util.Nullcheck(getArguments().getString("COLLEGE_NAME"));

        COLLEGE_WEBSITE = Util.Nullcheck(getArguments().getString("COLLEGE_WEBSITE"));
        COLLEGE_PLACE =Util.Nullcheck( getArguments().getString("COLLEGE_PLACE"));
        COLLEGE_LOGO = Util.Nullcheck(getArguments().getString("COLLEGE_LOGO"));
        COLLEGE_LAT = Util.Nullcheck(getArguments().getString("COLLEGE_LAT"));
        COLLEGE_LNG = Util.Nullcheck(getArguments().getString("COLLEGE_LNG"));
            ABOUT_US=Util.Nullcheck(getArguments().getString("ABOUT_US"));
        COLLEGE_IMAGES =Util.Nullcheck( getArguments().getString("CLG_IMAGES"));
            COLLEGEID_TO_PASS=Util.Nullcheck( getArguments().getString("COLLEGE_ID"));
            CLG_NUM = Util.Nullcheck(getArguments().getString("CLG_NUM"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        mTabs.add(new DataPagerItem(
                getString(R.string.about_us), // Title
                Color.parseColor("#000000"), // Indicator color
                Color.parseColor("#00000000") // Divider color
        ));

        mTabs.add(new DataPagerItem(
                getString(R.string.cutoff), // Title
                Color.parseColor("#000000"), // Indicator color
                Color.parseColor("#00000000") // Divider color
        ));

       mTabs.add(new DataPagerItem(
                getString(R.string.placements), // Title
               Color.parseColor("#000000"), // Indicator color
               Color.parseColor("#00000000") // Divider color
        ));
        mTabs.add(new DataPagerItem(
                getString(R.string.fest), // Title
                Color.parseColor("#000000"), // Indicator color
                Color.parseColor("#00000000") // Divider color
        ));
        mTabs.add(new DataPagerItem(
                getString(R.string.department), // Title
                Color.parseColor("#000000"), // Indicator color
                Color.parseColor("#00000000") // Divider color
        ));
/*        mTabs.add(new DataPagerItem(
                getString(R.string.facility), // Title
                Color.parseColor("#4a4f6c"), // Indicator color
                Color.parseColor("#4a4f6c") // Divider color
        ));*/

        mTabs.add(new DataPagerItem(
                getString(R.string.clg_news), // Title
                Color.parseColor("#000000"), // Indicator color
                Color.parseColor("#00000000") // Divider color
        ));
        mTabs.add(new DataPagerItem(
                getString(R.string.rank), // Title
                Color.parseColor("#000000"), // Indicator color
                Color.parseColor("#00000000") // Divider color
        ));
/*        mTabs.add(new DataPagerItem(
                getString(R.string.review), // Title
                Color.parseColor("#4a4f6c"), // Indicator color
                Color.parseColor("#4a4f6c") // Divider color
        ));*/

      /*  mTabs.add(new DataPagerItem(
                getString(R.string.tab_notifications), // Title
                Color.RED, // Indicator color
                Color.RED // Divider color
        ));
        mTabs.add(new DataPagerItem(
                getString(R.string.tab_notifications), // Title
                Color.RED, // Indicator color
                Color.RED // Divider color
        ));*/
    }
 @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // View x  =inflater.inflate(R.layout.home_data_layout, container, false);
        return inflater.inflate(R.layout.map_colleges_view, container, false);
    }
    @NonNull
    @Override
  /*  public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        view = View.inflate(contextForFragment, R.layout.map_colleges_view, null);
       // return inflater.inflate(R.layout.map_colleges_view, container, false);
        dialog.setContentView(view);
      *//*  CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
        View parent = (View) view.getParent();
        parent.setFitsSystemWindows(true);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parent);
        view.measure(0, 0);
        DisplayMetrics displaymetrics = new DisplayMetrics();        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        bottomSheetBehavior.setPeekHeight(screenHeight);

        if (params.getBehavior() instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior)params.getBehavior()).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        params.height = screenHeight;
        parent.setLayoutParams(params);
*//*
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
             //   Toast.makeText(contextForFragment, "Please check your Internet.", Toast.LENGTH_SHORT).show();
            }
        });

        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setState(BottomSheetBehavior.STATE_SETTLING);
      //  mBehavior.setPeekHeight(190);

        return dialog;}*/


    public void onViewCreated(View view, Bundle savedInstanceState) {
        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items

        NestedScrollView mgScrollView = view.findViewById(R.id.nestedmap);
        mgScrollView.setSmoothScrollingEnabled(true);

/*        Toolbar myToolbar = (Toolbar) view.findViewById(R.id.PurenewsToolbar1);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("College Name");
        // Get a support ActionBar corresponding to this toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //final Toolbar HomeToolbarfrag = (Toolbar) findViewById(R.id.toolbar_collaspe);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((AppCompatActivity)getActivity()).onBackPressed();
            }

        });*/
       // String rat_values = "Food 1 | Service 3 | Atmosphere 3 | Value for money 1 ";
        String[] value_split =  COLLEGE_IMAGES.split("\\|");
        viewpageAdaptor = new SlidingImage_Adapter1(contextForFragment,value_split);
        indicator = view.findViewById(R.id.viewindicator);
        mPager = view.findViewById(R.id.vpager);
       /* indicator = (CirclePageIndicator)
                v.findViewById(R.id.indicator);*/
        //mPager.setAdapter(viewpageAdaptor);

        init();
        if (!(COLLEGE_LOGO == null)) {
            String imageserver = null;
            try {
                imageserver = UtilProperty.getProperty("img_collegesView", contextForFragment, "imagepath.properties");
            } catch (IOException e) {
                e.printStackTrace();
            }

            TextView tv1 = view.findViewById(R.id.college_name);
            tv1.setText(COLLEGE_NAME);
            TextView tv2 = view.findViewById(R.id.college_website);
            tv2.setText(COLLEGE_WEBSITE);
            ImageView tv = view.findViewById(R.id.college_logoimg);
           // ImageView tv12 = (ImageView) view.findViewById(R.id.college_logobackgrd);

            Picasso.with(contextForFragment).load(imageserver + COLLEGE_LOGO).into(tv);
            //Picasso.with(contextForFragment).load(imageserver + "nitpybackground.JPG").into(tv12);
        }
            mViewPager = view.findViewById(R.id.CollegeViewpager);
            mViewPager.setOffscreenPageLimit(1);
            // ViewPager pager = (ViewPager) (ViewPager) findViewById(R.id.view_pager_full_photo);

            //used for setting the size of viewpager
      /* ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mViewPager.getLayoutParams();
        lp.setMargins(0,0,0,500);*/
            //lp.bottomMargin+=100;
            mViewPager.setAdapter(new HomeDataFragmentPagerAdapter(getChildFragmentManager()));


            mSlidingTabLayout1 = view.findViewById(R.id.sliding_tabs1);
            mSlidingTabLayout1.setViewPager(mViewPager);

            // BEGIN_INCLUDE (tab_colorizer)
            // Set a TabColorizer to customize the indicator and divider colors. Here we just retrieve
            // the tab at the position, and return it's set color
            mSlidingTabLayout1.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

                @Override
                public int getIndicatorColor(int position) {
                    return mTabs.get(position).getIndicatorColor();
                }

                @Override
                public int getDividerColor(int position) {
                    return mTabs.get(position).getDividerColor();
                }

            });
      /*  webView = (WebView) view.findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://en.wikipedia.org/wiki/National_Institute_of_Technology,_Puducherry");
        webView.setWebViewClient(new WebViewClient());*/
/*      ImageView tv = (ImageView)view.findViewById(R.id.mapimage);
        String latEiffelTower = COLLEGE_LAT;
        String lngEiffelTower = COLLEGE_LNG;
      Drawable xw = contextForFragment.getResources().getDrawable(R.drawable.iit_map_icon);
        String url = "http://maps.google.com/maps/api/staticmap?center=" + latEiffelTower + "," + lngEiffelTower + "&size=240x240&sensor=false&markers=icon:"+xw+"|"+latEiffelTower+","+lngEiffelTower;
        Picasso.with(contextForFragment).load(url).into( tv);*/
        }

    private void init() {



        // holder.mPager.invalidate();


       mPager.setAdapter(viewpageAdaptor);
       mPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=1;String UniqueKey="MAIN_HEADER_ADS";
               // selectItem(position,UniqueKey);
            }
        });



      indicator.setViewPager(mPager);

        final float density = contextForFragment.getResources().getDisplayMetrics().density;

        indicator.setRadius(5 * density);



        NUM_PAGES =4;



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
       Timer  swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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

    }
    class HomeDataFragmentPagerAdapter extends FragmentStatePagerAdapter {

        HomeDataFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int i) {

            switch (i) {
              case 0: // Fragment # 0 - This will show FirstFragment
                  return AboutUsFragment.newInstance(COLLEGEID_TO_PASS,COLLEGE_NAME,ABOUT_US,CLG_NUM);

                            //mTabs.get(i).createFragment();
                // return FirstFragment.newInstance(0, "Page # 1");
                 case 1: // Fragment # 0 - This will show FirstFragment different title
                     return CutoffsFragment.newInstance(COLLEGEID_TO_PASS);
              case 2: // Fragment # 1 - This will show SecondFragment
                  return PlacementsFragment.newInstance(COLLEGEID_TO_PASS);
                  case 3: // Fragment # 1 - This will show SecondFragment
                      return ClgFestFragment.newInstance(COLLEGEID_TO_PASS);

                case 4: // Fragment # 1 - This will show SecondFragment
                    return DepartmentsFragment.newInstance(COLLEGEID_TO_PASS);
                case 5: // Fragment # 1 - This will show SecondFragment
                    return CollegeNewsFragment.newInstance(COLLEGEID_TO_PASS);
                case 6: // Fragment # 1 - This will show SecondFragment
                    return ClgRankFragment.newInstance(COLLEGEID_TO_PASS);
               /* case 7: // Fragment # 1 - This will show SecondFragment
                    return CutoffThridFragment.newInstance();
                case 8: // Fragment # 1 - This will show SecondFragment
                    return ExamsFragment.newInstance();
                case 9: // Fragment # 1 - This will show SecondFragment
                    return homeCarrerGuidance.newInstance();*/
                // return FirstFragment.newInstance(1, "Page # 2");

                /*case 2: // Fragment # 1 - This will show SecondFragment
                    return QuestionsAnswersMainFragment.newInstance();*/
                //return SecondFragment.newInstance(2, "Page # 3");
             /*   case 3: // Fragment # 1 - This will show SecondFragment
                    return homeNewsFragment.newInstance();
                case 4: // Fragment # 1 - This will show SecondFragment
                    return NitSocialFragListView.newInstance();*/
                default:

                    return NewsFragment.newInstance("F");
            }


            // return mTabs.get(i).createFragment();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs.get(position).getTitle();
        }
        // END_INCLUDE (pageradapter_getpagetitle)

    }
}