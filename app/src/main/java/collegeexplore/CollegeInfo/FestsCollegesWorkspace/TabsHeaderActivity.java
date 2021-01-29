package collegeexplore.CollegeInfo.FestsCollegesWorkspace;

/**
 * Created by DANDE on 14-01-2018.
 */

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.Visibility;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import collegeexplore.CollegeInfo.R;

public class TabsHeaderActivity extends AppCompatActivity {
    private static final String TAG = TabsHeaderActivity.class.getSimpleName();
   private String COLLEGE_ID,FESTIMAGE,COLLEGENAME,TRASITION_IMAGE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                COLLEGE_ID= null;
                FESTIMAGE=null;
            } else {
                COLLEGE_ID= extras.getString("clg_id");
                FESTIMAGE= extras.getString("festimage");
                COLLEGENAME=extras.getString("collegename");
                TRASITION_IMAGE=extras.getString("EXTRA_IMAGE_TRANSITION_NAME");
            }
        } else {
            COLLEGE_ID= (String) savedInstanceState.getSerializable("clg_id");
            FESTIMAGE= (String) savedInstanceState.getSerializable("festimage");
            COLLEGENAME=(String) savedInstanceState.getSerializable("collegename");
            TRASITION_IMAGE=(String) savedInstanceState.getSerializable("EXTRA_IMAGE_TRANSITION_NAME");
        }

        setContentView(R.layout.fests_data_viewpager);
/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupWindowAnimations();
        }*/
        supportPostponeEnterTransition();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.htab_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(COLLEGENAME);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }

        });
        final ViewPager viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(viewPager);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.htab_collapse_toolbar);

        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.b_home_icon);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @SuppressWarnings("ResourceType")
                @Override
                public void onGenerated(Palette palette) {

                    int vibrantColor = palette.getVibrantColor(R.color.DeepPink);
                    int vibrantDarkColor = palette.getDarkVibrantColor(R.color.DarkGreen);
                    collapsingToolbarLayout.setContentScrimColor(vibrantColor);
                    collapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor);
                }
            });

        } catch (Exception e) {
            // if Bitmap fetch fails, fallback to primary colors
            Log.e(TAG, "onCreate: failed to create bitmap from background", e.fillInStackTrace());
            collapsingToolbarLayout.setContentScrimColor(
                    ContextCompat.getColor(this, R.color.AliceBlue)
            );
            collapsingToolbarLayout.setStatusBarScrimColor(
                    ContextCompat.getColor(this, R.color.DarkOrchid)
            );
        }
        ImageView dande = (ImageView) findViewById(R.id.htab_header);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String imageTransitionName = TRASITION_IMAGE;
            dande.setTransitionName(imageTransitionName);
        }

        if(null!=FESTIMAGE) {
         //   Picasso.with(this).load(FESTIMAGE).into(dande);

            Picasso.with(this)
                    .load(FESTIMAGE)
                    .noFade()
                    .into(dande, new Callback() {
                        @Override
                        public void onSuccess() {
                            supportStartPostponedEnterTransition();
                        }

                        @Override
                        public void onError() {
                            supportStartPostponedEnterTransition();
                        }
                    });
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
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag( Fests_Overview.newInstance(COLLEGE_ID), "OverView");
        adapter.addFrag( Fests_EventDetails.newInstance(COLLEGE_ID), "Event Details");
        adapter.addFrag( FestsRegisterAccess.newInstance(COLLEGE_ID), "Registration");
        adapter.addFrag( Festslinks.newInstance(COLLEGE_ID), "Links");
        adapter.addFrag( FestsReachUs.newInstance(COLLEGE_ID), "How to Reach Us");
        viewPager.setAdapter(adapter);
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

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


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Transition transition;
        int type = 0;
        if (type == 0) {
            transition = buildEnterTransition();
        }  else {
            transition = TransitionInflater.from(this).inflateTransition(R.transition.explode);
        }
        getWindow().setEnterTransition(transition);
    }
    private Visibility buildEnterTransition() {
        Slide enterTransition = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            enterTransition = new Slide();

            enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
            enterTransition.setSlideEdge(Gravity.TOP);}
        return enterTransition;
    }
   /* public static class DummyFragment extends Fragment {
        int color;

        public DummyFragment() {
        }

        @SuppressLint("ValidFragment")
        public DummyFragment(int color) {
            this.color = color;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dummy_fragment, container, false);

            final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);
            frameLayout.setBackgroundColor(color);

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);

            DessertAdapter adapter = new DessertAdapter(getContext());
            recyclerView.setAdapter(adapter);

            return view;
        }
    }*/
}