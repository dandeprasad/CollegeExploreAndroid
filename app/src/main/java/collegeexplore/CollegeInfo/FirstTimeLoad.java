package collegeexplore.CollegeInfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by DANDE on 13-08-2017.
 */


public class FirstTimeLoad extends AppCompatActivity {
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.back_button,R.drawable.back_button,R.drawable.back_button};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_time_load);
        init();
    }




    private void init() {


        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new FirstTimeLoadAdaptor(FirstTimeLoad.this,ImagesArray));


        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        indicator.setRadius(5 * density);



        NUM_PAGES =IMAGES.length;



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


        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
if(currentPage==2){

    TextView dande = (TextView) findViewById(R.id.NextButton);
    dande.setVisibility(View.VISIBLE);
   // dande.setOnClickListener();
    dande.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent i2 = new Intent(FirstTimeLoad.this,HomeActivity.class);
            startActivity(i2);
            FirstTimeLoad.this.finish();

        }
    });


}
else{
    TextView dande = (TextView) findViewById(R.id.NextButton);
    dande.setVisibility(View.GONE);
}
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }


}
