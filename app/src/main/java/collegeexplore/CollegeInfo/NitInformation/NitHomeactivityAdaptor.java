package collegeexplore.CollegeInfo.NitInformation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import collegeexplore.CollegeInfo.R;

/**
 * Created by DANDE on 26-07-2016.
 */

public class NitHomeactivityAdaptor extends BaseAdapter {
    private Context mContext;
    public NitHomeactivityAdaptor(Context c) {
        mContext = c;
    }
    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
           imageView.setLayoutParams(new GridView.LayoutParams(400, 80));

        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.ic_placements, R.drawable.ic_placements,
            R.drawable.ic_placements, R.drawable.ic_placements,
            R.drawable.ic_placements, R.drawable.ic_placements,
            R.drawable.ic_placements

    };


}
