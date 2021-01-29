package collegeexplore.CollegeInfo.ExamsWorkspace;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import collegeexplore.CollegeInfo.ExamsWorkspace.OnclickViews.ExamsDates;
import collegeexplore.CollegeInfo.ExamsWorkspace.OnclickViews.ExamsOverview;
import collegeexplore.CollegeInfo.ExamsWorkspace.OnclickViews.ExamsPreparation;
import collegeexplore.CollegeInfo.ExamsWorkspace.OnclickViews.ExamsResults;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.homeNewsFragment;
import collegeexplore.view.SlidingTabLayout;

public class ExamsDetailsViewPager extends Fragment{




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
        Fragment createFragment(String idToPass) {
            return homeNewsFragment.newInstance();
            // ArrayListFragment.newInstance(position);
        }
        /**
         * used for initiating the variables
         * @param collegeidToPass
         */

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



    public static ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout1;
 private String   UniqueTosend;
    private List<DataPagerItem> mTabs = new ArrayList<DataPagerItem>();
    public static ExamsDetailsViewPager newInstance(String collegeidToPass) {
        ExamsDetailsViewPager f = new ExamsDetailsViewPager();


        Bundle args = new Bundle();
        args.putString("COLLEGEID", collegeidToPass);
        f.setArguments(args);

        return  f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UniqueTosend = getArguments() != null ? getArguments().getString("COLLEGEID") : "";
        mTabs.add(new DataPagerItem(
                getString(R.string.exam_overview), // Title
                Color.parseColor("#000000") , // Indicator color
                Color.parseColor("#00000000") // Divider color
        ));

        mTabs.add(new DataPagerItem(
                getString(R.string.exam_howtoprepare), // Title
                Color.parseColor("#000000"), // Indicator color
                Color.parseColor("#00000000") // Divider color
        ));

       mTabs.add(new DataPagerItem(
                getString(R.string.imp_dates), // Title
               Color.parseColor("#000000"), // Indicator color
               Color.parseColor("#00000000") // Divider color
        ));
        mTabs.add(new DataPagerItem(
                getString(R.string.Results), // Title
                Color.parseColor("#000000"), // Indicator color
                Color.parseColor("#00000000") // Divider color
        ));


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
        return inflater.inflate(R.layout.exams_viewpager_layout, container, false);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        Toolbar myToolbar = view.findViewById(R.id.PurenewsToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.exams);

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
        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = view.findViewById(R.id.HomeViewpager);
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
    }


    class HomeDataFragmentPagerAdapter extends FragmentStatePagerAdapter {

        HomeDataFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int i) {

            switch (i) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return   ExamsOverview.newInstance(UniqueTosend);
                            //mTabs.get(i).createFragment();
                // return FirstFragment.newInstance(0, "Page # 1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return ExamsPreparation.newInstance(UniqueTosend);
                case 2: // Fragment # 1 - This will show SecondFragment
                    return ExamsDates.newInstance(UniqueTosend);
                case 3: // Fragment # 1 - This will show SecondFragment
                    return ExamsResults.newInstance(UniqueTosend);

                // return FirstFragment.newInstance(1, "Page # 2");

                /*case 2: // Fragment # 1 - This will show SecondFragment
                    return QuestionsAnswersMainFragment.newInstance();*/
                //return SecondFragment.newInstance(2, "Page # 3");
             /*   case 3: // Fragment # 1 - This will show SecondFragment
                    return homeNewsFragment.newInstance();
                case 4: // Fragment # 1 - This will show SecondFragment
                    return NitSocialFragListView.newInstance();*/
                default:
                    return null;
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