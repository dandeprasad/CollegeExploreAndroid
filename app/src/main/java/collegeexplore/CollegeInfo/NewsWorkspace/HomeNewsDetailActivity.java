package collegeexplore.CollegeInfo.NewsWorkspace;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import collegeexplore.CollegeInfo.R;

/**
 * Created by DANDE on 14-01-2018.
 */



public class HomeNewsDetailActivity extends AppCompatActivity {
    private static final String TAG ="HomeNewsDetailActivity" ;
    HashMap HASHMAP_TOSEND;
String EXTRA_IMAGE_TRANSITION_NAME;
    CollapsingToolbarLayout collapsingToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            Intent intent = getIntent();
            if(extras == null) {
                HASHMAP_TOSEND= null;
                EXTRA_IMAGE_TRANSITION_NAME=null;
            } else {
                HASHMAP_TOSEND=     (HashMap<String, String>)intent.getSerializableExtra("HASHMAP_TOSEND");
             //   HASHMAP_TOSEND= extras.getParcelable("HASHMAP_TOSEND");
                EXTRA_IMAGE_TRANSITION_NAME= extras.getString("EXTRA_IMAGE_TRANSITION_NAME");

            }
        } else {
            HASHMAP_TOSEND= (HashMap<String, String>)savedInstanceState.getSerializable("HASHMAP_TOSEND");
            EXTRA_IMAGE_TRANSITION_NAME= (String) savedInstanceState.getSerializable("EXTRA_IMAGE_TRANSITION_NAME");
        }

        setContentView(R.layout.fragment_newswkspc_detail);
/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupWindowAnimations();
        }*/
        supportPostponeEnterTransition();

       Toolbar dan= findViewById(R.id.toolbar_collaspe);
        setSupportActionBar(dan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  dan.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        //  collapsingToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_nav_back));
        // Toolbar dfd = xtest.findViewById(R.id.toolbar_collaspe);
        final Toolbar HomeToolbarfrag = findViewById(R.id.toolbar_collaspe);
        HomeToolbarfrag.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                    ContextCompat.getColor(this, R.color.Black)
            );
            collapsingToolbar.setStatusBarScrimColor(
                    ContextCompat.getColor(this, R.color.White)
            );
        }



        AppBarLayout appBarLayout = findViewById(R.id.appbar);
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



        HashMap<String,String> animalItem =HASHMAP_TOSEND;
        String transitionName = EXTRA_IMAGE_TRANSITION_NAME;

        TextView detailTextView = findViewById(R.id.data_detail_text);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            detailTextView.setText(Html.fromHtml(animalItem.get("NEWS_DETAILS")));
        }
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.N) {
            detailTextView.setText(Html.fromHtml(animalItem.get("NEWS_DETAILS"),Html.FROM_HTML_MODE_COMPACT));


        }
        TextView data_header_text = findViewById(R.id.data_header_text);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data_header_text.setText(Html.fromHtml(animalItem.get("NEWS_HEADER")));
        }
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.N) {
            data_header_text.setText(Html.fromHtml(animalItem.get("NEWS_HEADER"),Html.FROM_HTML_MODE_COMPACT));
        }

        ImageView imageView = findViewById(R.id.news_detail_image_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(transitionName);
        }

        Picasso.with(this)
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


}}