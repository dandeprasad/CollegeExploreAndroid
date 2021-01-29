package collegeexplore.CollegeInfo;

/**
 * Created by DANDE on 17-01-2017.
 */

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import collegeexplore.CollegeInfo.V1.MainActivity;
import collegeexplore.ConnectionCheck.ConnectivityCheck;

public class MainSplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "MainSplashScreenActivity";
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3500;
    public static int pos=1;
private  int type = 0;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
        Configuration config2 = new Configuration();
        config2.locale = Locale.getDefault();
        getBaseContext().getResources().updateConfiguration(config2,
                getBaseContext().getResources().getDisplayMetrics());

/*        ImageView myImageView= (ImageView)findViewById(R.id.College_Logo);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        myImageView.startAnimation(myFadeInAnimation);

        Animation myFadeInAnimation1 = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        myImageView.startAnimation(myFadeInAnimation1);*/
        ImageView myImageView = (ImageView) findViewById(R.id.College_Logo);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(MainSplashScreenActivity.this, R.anim.tween);
        myImageView.startAnimation(myFadeInAnimation);
        // Manually checking internet connection
        boolean netconnected = checkConnection();

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        if(netconnected){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent i = new Intent(MainSplashScreenActivity.this, MainActivity.class);
                transitionTo(i);

            }
        }, SPLASH_DISPLAY_LENGTH);}
/*        else{
            while(checkConnection()==false) {
            }

                *//* New Handler to start the Menu-Activity
                 * and close this Splash-Screen after some seconds.*//*

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            *//* Create an Intent that will start the Menu-Activity. *//*
                            Intent i = new Intent(MainSplashScreenActivity.this, HomeActivity.class);
                            transitionTo(i);

                        }
                    }, SPLASH_DISPLAY_LENGTH);


        }*/
      }


    @SuppressWarnings("unchecked") void transitionTo(Intent i) {
        final Pair<View, String>[] pairs;
   /*     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pairs = TransitionHelper.createSafeTransitionParticipants(this, true);

        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
          //  MainSplashScreenActivity.this.finish();

            Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .getBoolean("isFirstRun", true);

            if (isFirstRun) {
                //show start activity
                Intent i1 = new Intent(MainSplashScreenActivity.this,FirstTimeLoad.class);
                startActivity(i1, transitionActivityOptions.toBundle());
                *//*Toast.makeText(this, "First Run", Toast.LENGTH_LONG)
                        .show();*//*
                MainSplashScreenActivity.this.finish();
            }


            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("isFirstRun", false).commit();

if(!isFirstRun){
    startActivity(i, transitionActivityOptions.toBundle());
    MainSplashScreenActivity.this.finish();
}


        }
        else{*/

            Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .getBoolean("isFirstRun", false);

            if (isFirstRun) {
                //show start activity
                Intent i2 = new Intent(MainSplashScreenActivity.this,FirstTimeLoad.class);
                startActivity(i2);
               /* Toast.makeText(this, "First Run", Toast.LENGTH_LONG)
                        .show();*/
                MainSplashScreenActivity.this.finish();
            }


            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("isFirstRun", false).commit();
            if(!isFirstRun){
            startActivity(i);
                MainSplashScreenActivity.this.finish();
            }
           // MainSplashScreenActivity.this.finish();
        }
   /* }*/
        private boolean checkConnection() {
            boolean isConnected = ConnectivityCheck.isConnected();
            showSnack(isConnected);
            return isConnected;
        }
    @Override
    public void onStart() {
        super.onStart();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;

        if (isConnected) {
            message = getString(R.string.welome_message);
            color = Color.WHITE;
        } else {
            message = getString(R.string.sorrt_not_connect_internet);
            color = Color.WHITE;

        }

        Snackbar snackbar = Snackbar.make(findViewById(R.id.fab), message, Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);

        textView.setGravity(Gravity.CENTER);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.app_theme));
        snackbar.show();
    }
}