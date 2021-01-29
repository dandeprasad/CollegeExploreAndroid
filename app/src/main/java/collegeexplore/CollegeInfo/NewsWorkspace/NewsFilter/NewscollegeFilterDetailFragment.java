package collegeexplore.CollegeInfo.NewsWorkspace.NewsFilter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import collegeexplore.CollegeInfo.CollegesAtMaps.homeMapsActivity;
import collegeexplore.CollegeInfo.R;
import collegeexplore.logger.Log;

import static android.content.ContentValues.TAG;

public class NewscollegeFilterDetailFragment extends Fragment {

    private static final String EXTRA_ANIMAL_ITEM = "animal_item";
    private static final String EXTRA_TRANSITION_NAME = "transition_name";
    CollapsingToolbarLayout collapsingToolbar;
    public NewscollegeFilterDetailFragment() {
        // Required empty public constructor
    }

    public static NewscollegeFilterDetailFragment newInstance(HashMap animalItem, String transitionName) {
        NewscollegeFilterDetailFragment f = new NewscollegeFilterDetailFragment();
        Bundle bundle = new Bundle();

        bundle.putSerializable("HashMap",animalItem);
        bundle.putString(EXTRA_TRANSITION_NAME, transitionName);
        f.setArguments(bundle);

        return  f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_newswkspc_detail, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar_collaspe));
        ((homeMapsActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar = view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        //  collapsingToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_nav_back));
        // Toolbar dfd = xtest.findViewById(R.id.toolbar_collaspe);
        final Toolbar HomeToolbarfrag = view.findViewById(R.id.toolbar_collaspe);
        HomeToolbarfrag.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
            }

        });
        //  collapsingToolbar.setExpandedTitleColor(Color.parseColor("#00FFFFFF"));


        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.co1);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @SuppressWarnings("ResourceType")
                @Override
                public void onGenerated(Palette palette) {

                    int vibrantColor = palette.getVibrantColor(R.color.Black);
                    int vibrantDarkColor = palette.getDarkVibrantColor(R.color.Black);
                    collapsingToolbar.setContentScrimColor(vibrantColor);
                    collapsingToolbar.setStatusBarScrimColor(vibrantDarkColor);
                }
            });

        } catch (Exception e) {
            // if Bitmap fetch fails, fallback to primary colors
            Log.e(TAG, "onCreate: failed to create bitmap from background", e.fillInStackTrace());
            collapsingToolbar.setContentScrimColor(
                    ContextCompat.getColor(getContext(), R.color.Black)
            );
            collapsingToolbar.setStatusBarScrimColor(
                    ContextCompat.getColor(getContext(), R.color.White)
            );
        }



        AppBarLayout appBarLayout = view.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {

                   /*BitmapDrawable ob = new BitmapDrawable(getResources(), arrayOfBitmap[0]);

                    collapsingToolbar.setBackground(ob);*/
                    //collapsingToolbar.setBackgroundColor(Color.parseColor("#ff0000"));}

                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.White));

                    isShow = true;
                } else if(isShow) {
                    collapsingToolbar.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });





        HashMap<String,String> animalItem =(HashMap) getArguments().getSerializable("HashMap");
        String transitionName = getArguments().getString(EXTRA_TRANSITION_NAME);

        TextView detailTextView = view.findViewById(R.id.data_detail_text);
        detailTextView.setText(animalItem.get("NEWS_DETAILS"));
        TextView data_header_text = view.findViewById(R.id.data_header_text);
        data_header_text.setText(animalItem.get("NEWS_HEADER"));

        ImageView imageView = view.findViewById(R.id.news_detail_image_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(transitionName);
        }

        Picasso.with(getContext())
                .load(animalItem.get("NEWS_IMAGE"))
                .noFade()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        startPostponedEnterTransition();
                    }

                    @Override
                    public void onError() {
                        startPostponedEnterTransition();
                    }
                });
    }

}