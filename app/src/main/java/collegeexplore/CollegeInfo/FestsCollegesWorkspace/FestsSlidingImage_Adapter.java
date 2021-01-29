package collegeexplore.CollegeInfo.FestsCollegesWorkspace;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import collegeexplore.CollegeInfo.HomeActivity;
import collegeexplore.CollegeInfo.LoginManagement.UserLogin;
import collegeexplore.CollegeInfo.LoginManagement.UserLoginManager;
import collegeexplore.CollegeInfo.NewsWorkspace.HomeNewsDetailActivity;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.UtilProperty;

import static com.facebook.FacebookSdk.getApplicationContext;


public class FestsSlidingImage_Adapter extends PagerAdapter {


    private ArrayList<HashMap> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public FestsSlidingImage_Adapter(Context context, ArrayList<HashMap> IMAGES) {
        this.context = context;
        this.IMAGES=IMAGES;

        inflater = LayoutInflater.from(context);



    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);
        String  imageserver = null;
        try {
            imageserver =  UtilProperty.getProperty("img_FestsSlidingImage_Adapter", context,"imagepath.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert imageLayout != null;
        final ImageView imageView = imageLayout
                .findViewById(R.id.image);
        final TextView data = imageLayout
                .findViewById(R.id.headertext_notify);

        if(!IMAGES.isEmpty()) {
            HashMap<String,String> val=IMAGES.get(position);
            Picasso.with(context).load(val.get("NOTIFY_IMG")).into( imageView);
           imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            data.setText(val.get("NOTIFY_HEADER"));
        }

        view.addView(imageLayout, 0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = 1;
                String UniqueKey = "MAIN_HEADER_ADS";

            }
        });

        if(!IMAGES.isEmpty()) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String,String> val=IMAGES.get(position);

                        Intent i2 = null;
                        UserLoginManager checkuserstatus = new UserLoginManager(getApplicationContext());
                        if(checkuserstatus.isLoggedIn()){
                            i2 = new Intent(context, TabsHeaderActivity.class);
                            i2.putExtra("clg_id", val.get("NOTIFYS_ID"));
                            i2.putExtra("festimage", val.get("NOTIFY_IMG"));
                            i2.putExtra("collegename", val.get("NOTIFY_HEADER"));
                            context.startActivity(i2);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(), UserLogin.class);
                            context.startActivity(i);
                        }}


            });}



        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
