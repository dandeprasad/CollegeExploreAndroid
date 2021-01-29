package collegeexplore.CollegeInfo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import collegeexplore.CollegeInfo.CarrerGuidanceWorkspace.homeCarrerGuidance;
import collegeexplore.CollegeInfo.ExamsWorkspace.ExamsFragment;
import collegeexplore.CollegeInfo.FestsCollegesWorkspace.FestNewsNotifications;
import collegeexplore.CollegeInfo.NewsWorkspace.NewsFragment;
import collegeexplore.CollegeInfo.NewsandNotificationsWorkspace.HomeNewsNotifications;
import collegeexplore.CollegeInfo.NitInformation.NitHomeActivity;
import collegeexplore.CollegeInfo.QuestionsAnswersWorkspace.QuestionsAnswersMainFragment;
import collegeexplore.view.SlidingTabLayout;

public class HomeData extends Fragment{



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



    public static ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout1;
    private List<DataPagerItem> mTabs = new ArrayList<DataPagerItem>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTabs.add(new DataPagerItem(
                getString(R.string.Home_News_Notifications), // Title
                Color.parseColor("#4a4f6c") , // Indicator color
                Color.parseColor("#4a4f6c") // Divider color
        ));

        mTabs.add(new DataPagerItem(
                getString(R.string.Home_Carrer_Guidance), // Title
                Color.parseColor("#4a4f6c"), // Indicator color
                Color.parseColor("#4a4f6c") // Divider color
        ));

       mTabs.add(new DataPagerItem(
                getString(R.string.nits_header), // Title
               Color.parseColor("#4a4f6c"), // Indicator color
               Color.parseColor("#4a4f6c") // Divider color
        ));
        mTabs.add(new DataPagerItem(
                getString(R.string.news_college), // Title
                Color.parseColor("#4a4f6c"), // Indicator color
                Color.parseColor("#4a4f6c") // Divider color
        ));
        mTabs.add(new DataPagerItem(
                getString(R.string.iits_header), // Title
                Color.parseColor("#4a4f6c"), // Indicator color
                Color.parseColor("#4a4f6c") // Divider color
        ));
        mTabs.add(new DataPagerItem(
                getString(R.string.deemed_univ), // Title
                Color.parseColor("#4a4f6c"), // Indicator color
                Color.parseColor("#4a4f6c") // Divider color
        ));

        mTabs.add(new DataPagerItem(
                getString(R.string.ques_answ), // Title
                Color.parseColor("#4a4f6c"), // Indicator color
                Color.parseColor("#4a4f6c") // Divider color
        ));
        mTabs.add(new DataPagerItem(
                getString(R.string.Fests_colleg), // Title
                Color.parseColor("#4a4f6c"), // Indicator color
                Color.parseColor("#4a4f6c") // Divider color
        ));
        mTabs.add(new DataPagerItem(
                getString(R.string.Exams), // Title
                Color.parseColor("#4a4f6c"), // Indicator color
                Color.parseColor("#4a4f6c") // Divider color
        ));
        mTabs.add(new DataPagerItem(
                getString(R.string.Admissions), // Title
                Color.parseColor("#4a4f6c"), // Indicator color
                Color.parseColor("#4a4f6c") // Divider color
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
        return inflater.inflate(R.layout.home_data_layout, container, false);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
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


    class HomeDataFragmentPagerAdapter extends FragmentPagerAdapter {

        HomeDataFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int i) {

            switch (i) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return HomeNewsNotifications.newInstance();
                            //mTabs.get(i).createFragment();
                // return FirstFragment.newInstance(0, "Page # 1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return homeCarrerGuidance.newInstance();
                case 2: // Fragment # 1 - This will show SecondFragment
                    return NitHomeActivity.newInstance();
                case 3: // Fragment # 1 - This will show SecondFragment
                    return NewsFragment.newInstance("T");
                case 4: // Fragment # 1 - This will show SecondFragment
                    return NitHomeActivity.newInstance();
                case 5: // Fragment # 1 - This will show SecondFragment
                    return NitHomeActivity.newInstance();
                case 6: // Fragment # 1 - This will show SecondFragment
                    return QuestionsAnswersMainFragment.newInstance();
                case 7: // Fragment # 1 - This will show SecondFragment
                    return FestNewsNotifications.newInstance();
                case 8: // Fragment # 1 - This will show SecondFragment
                    return ExamsFragment.newInstance();
                case 9: // Fragment # 1 - This will show SecondFragment
                    return homeCarrerGuidance.newInstance();
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