package collegeexplore.CollegeInfo;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by DANDE on 13-08-2017.
 */

public class FirstTimeLoadAdaptor extends PagerAdapter {


    private ArrayList<Integer> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public FirstTimeLoadAdaptor(Context context,ArrayList<Integer> IMAGES) {
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
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout
                .findViewById(R.id.image);


        imageView.setImageResource(IMAGES.get(position));

        view.addView(imageLayout, 0);
        final int cPos = position;
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ImageView i = (ImageView)v;
                if(cPos == 0)
                {
                    //...
                }
                //...

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
