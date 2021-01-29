package collegeexplore.CollegeInfo.UserNotifications;

/**
 * Created by DANDE on 25-01-2018.
 */

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

import collegeexplore.CollegeInfo.AdmissionsWorkspace.CollegeAdmisFragment;
import collegeexplore.CollegeInfo.LoginManagement.UserLoginManager;
import collegeexplore.CollegeInfo.NewsandNotificationsWorkspace.HomeNewsNotifications;
import collegeexplore.CollegeInfo.R;


public class UserNotifications extends AppCompatActivity {

    Switch newswitchButton, admissionswitchButton,switchButtonExams,switchButtonfests,switchButtonrecommend;
    TextView textView, textView2;
    String switchOn = "Switch is ON";
    String switchOff = "Switch is OFF";
    Button user_notify;
    Button user_notify_settings;
    LinearLayout user_notify_settings_linear;
    LinearLayout Notifications_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_notify_alerts);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.alertsToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Notifications");
        // Get a support ActionBar corresponding to this toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }

        });

        android.support.v4.app.Fragment fr =  NotifyFragment.newInstance();

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.notify_content_fragment,fr, "Notifications");
        fragmentTransaction.commit();


        final UserLoginManager logincheck123 = new UserLoginManager(this);
        HashMap fetchemail =  logincheck123.getuseralerts();
         user_notify = findViewById(R.id.user_notify);
         user_notify_settings = findViewById(R.id.user_notify_settings);

        user_notify_settings_linear = findViewById(R.id.user_notify_settings_linear);
        Notifications_linear = findViewById(R.id.Notifications_linear);
        user_notify.setBackgroundColor(getResources().getColor(R.color.bottombar));
        user_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user_notify_settings_linear.setVisibility(View.GONE);
                Notifications_linear.setVisibility(View.VISIBLE);
                user_notify.setBackgroundColor(getResources().getColor(R.color.bottombar));
                user_notify_settings.setBackgroundColor(getResources().getColor(R.color.Black));
            }
        });
        user_notify_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notifications_linear.setVisibility(View.GONE);
                user_notify_settings_linear.setVisibility(View.VISIBLE);
                user_notify_settings.setBackgroundColor(getResources().getColor(R.color.bottombar));
                user_notify.setBackgroundColor(getResources().getColor(R.color.Black));

            }
        });

        // For first switch button
        newswitchButton = (Switch) findViewById(R.id.newswitchButton);
        textView = (TextView) findViewById(R.id.textView);

        newswitchButton.setChecked((Boolean)fetchemail.get("NEWS"));
        newswitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    logincheck123.updateuserchangealeart("NEWS",true);
                    FirebaseMessaging.getInstance().subscribeToTopic("NEWS");

                    newswitchButton.setChecked(true);
                  //  textView.setText(switchOn);
                } else {
                    logincheck123.updateuserchangealeart("NEWS",false);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("NEWS");
                    newswitchButton.setChecked(false);
                  //  textView.setText(switchOff);
                }
            }
        });



      //admissions alert
        // For first switch button
        admissionswitchButton = (Switch) findViewById(R.id.admissionswitchButton);
        admissionswitchButton.setChecked((Boolean)fetchemail.get("ADMISSIONS"));
        admissionswitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    logincheck123.updateuserchangealeart("ADMISSIONS",true);
                    FirebaseMessaging.getInstance().subscribeToTopic("ADMISSIONS");

                    admissionswitchButton.setChecked(true);
                } else {
                    logincheck123.updateuserchangealeart("ADMISSIONS",false);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("ADMISSIONS");
                    admissionswitchButton.setChecked(false);
                }
            }
        });

        //Exams alert
        // For first switch button
        switchButtonExams = (Switch) findViewById(R.id.switchButtonExams);
        switchButtonExams.setChecked((Boolean)fetchemail.get("EXAMS"));
        switchButtonExams.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {

                if (bChecked) {
                    logincheck123.updateuserchangealeart("EXAMS",true);
                    FirebaseMessaging.getInstance().subscribeToTopic("EXAMS");

                    switchButtonExams.setChecked(true);
                } else {
                    logincheck123.updateuserchangealeart("EXAMS",false);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("EXAMS");
                    switchButtonExams.setChecked(false);
                }

            }
        });

        //fests alerts
        switchButtonfests = (Switch) findViewById(R.id.switchButtonfests);
        switchButtonfests.setChecked((Boolean)fetchemail.get("FESTS"));
        switchButtonfests.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    logincheck123.updateuserchangealeart("FESTS",true);
                    FirebaseMessaging.getInstance().subscribeToTopic("FESTS");

                    switchButtonfests.setChecked(true);
                } else {
                    logincheck123.updateuserchangealeart("FESTS",false);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("FESTS");
                    switchButtonfests.setChecked(false);
                }

            }
        });
        //recommended  alerts
        switchButtonrecommend = (Switch) findViewById(R.id.switchButtonrecommend);
        switchButtonrecommend.setChecked((Boolean)fetchemail.get("RECOMMENDED"));
        switchButtonrecommend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    logincheck123.updateuserchangealeart("RECOMMENDED",true);
                    FirebaseMessaging.getInstance().subscribeToTopic("RECOMMENDED");
                    switchButtonrecommend.setChecked(true);
                } else {
                    logincheck123.updateuserchangealeart("RECOMMENDED",false);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("RECOMMENDED");
                    switchButtonrecommend.setChecked(false);
                }
            }
        });

    }

}