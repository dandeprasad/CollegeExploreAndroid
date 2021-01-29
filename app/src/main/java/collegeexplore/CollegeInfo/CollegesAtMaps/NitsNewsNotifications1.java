package collegeexplore.CollegeInfo.CollegesAtMaps;

/**
 * Created by DANDE on 03-09-2016.
 */

import android.graphics.drawable.TransitionDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import collegeexplore.CollegeInfo.R;


public class NitsNewsNotifications1 extends RecyclerView.Adapter<NitsNewsNotifications1.ViewHolder> {
    private String[] UniqueKey;
   // private Integer[] imagedata;
    private TransitionDrawable td1;
    private OnItemClickListener mListener;
   // final String UniqueKey = "HOME_NITS_NEWS_NOTIFYS";
   // private ArrayList<Bitmap> imagedata=new ArrayList<Bitmap>();
    public static Integer[] imagedata;
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
            // label = (TextView) v.findViewById(R.id.dandeking);
            //  mTextView = (TextView) v;
            imageView = v.findViewById(R.id.imgmaps);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NitsNewsNotifications1(String[] maincontents, Integer[] imageId, homeMapsActivity listener) {
        imagedata= new Integer[imageId.length];
        UniqueKey = maincontents;
        mListener = listener;
        imagedata = imageId;
        imageId=null;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NitsNewsNotifications1.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_nits_news_notifications1, parent, false);

        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder1, final int position) {


        // holder1.label.setText("test dande");

            holder1.imageView.setImageResource(imagedata[position]);
/*if(position==0)
{
    holder1.imageView.setAlpha((float) 0.5);
}*/
            // holder1.imageView.setLayoutParams(new FrameLayout.LayoutParams(240, 240));
            //holder.itemView.setLayoutParams(params);
           holder1.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


        holder1.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  holder1.imageView.setAlpha((float) 0.3);
                mListener.onClick(view, position,UniqueKey[0]);
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
        return imagedata.length;
    }

}