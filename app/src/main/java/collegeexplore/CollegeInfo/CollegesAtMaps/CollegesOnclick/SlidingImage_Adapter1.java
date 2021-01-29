package collegeexplore.CollegeInfo.CollegesAtMaps.CollegesOnclick;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.UtilProperty;

/**
 * Created by SONU on 29/08/15.
 */
public class SlidingImage_Adapter1 extends PagerAdapter {


    private String[] IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Adapter1(Context context, String[] IMAGES) {
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
        return IMAGES.length;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);
        String imageserver = null;
        try {
            imageserver = UtilProperty.getProperty("img_college_images", context, "imagepath.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert imageLayout != null;
        final ImageView imageView = imageLayout
                .findViewById(R.id.image);
        Picasso.with(context).load(imageserver+IMAGES[position]).resize(600, 200).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
/*        if(IMAGES[0]!=null) {


            imageView.setImageBitmap(imageView);
        }*/
            view.addView(imageLayout, 0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=1;String UniqueKey="MAIN_HEADER_ADS";

            }
        });

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
