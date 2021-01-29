package collegeexplore.CollegeInfo.CollegesAtMaps.CollegesOnclick;


/**
 * Created by DANDE on 20-02-2017.
 */

import android.graphics.Bitmap;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import collegeexplore.CollegeInfo.R;


public class collegebottomviewdrawer extends RecyclerView.Adapter<collegebottomviewdrawer.ViewHolder> {
    private String[] mDataset;
    private Integer[] imagedata;
    private TransitionDrawable td1;
    private OnItemClickListener mListener;
    final String UniqueKey = "TEST_NEWS_NOTIFYS";
    Bitmap[] arrayOfBitmaptest;


    public interface OnItemClickListener {
        void onClick(View view, int position, String UniqueKey);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        TextView label;
        ImageView imageView;
        public ViewHolder(View v) {
            super(v);
            label = v.findViewById(R.id.dandeking);
            //  mTextView = (TextView) v;
            imageView = v.findViewById(R.id.imgallnotify);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public collegebottomviewdrawer(Bitmap[] arrayOfBitmap, Integer[] imageId, OnItemClickListener listener) {
        arrayOfBitmaptest=arrayOfBitmap;
        mListener = listener;
        imagedata = imageId;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public collegebottomviewdrawer.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_notifications, parent, false);

        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder1, final int position) {

        if (arrayOfBitmaptest!=null){
            if ( arrayOfBitmaptest[position]!=null)
            {


                Bitmap x = arrayOfBitmaptest[position];
                holder1.imageView.setImageBitmap(x);}
            holder1.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        // holder1.label.setText("test dande");
        // holder1.imageView.setImageResource(imagedata[position]);

        // holder1.imageView.setLayoutParams(new FrameLayout.LayoutParams(240, 240));
        //holder.itemView.setLayoutParams(params);





        holder1.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // int x =  View.ACCESSIBILITY_LIVE_REGION_ASSERTIVE;
                mListener.onClick(view, position,UniqueKey);
            }
        });


     /*   holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClick(view, position);
            }
        });*/

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return  30;
    }

}
