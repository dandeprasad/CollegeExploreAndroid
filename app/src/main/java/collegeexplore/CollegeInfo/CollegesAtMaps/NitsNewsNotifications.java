package collegeexplore.CollegeInfo.CollegesAtMaps;

/**
 * Created by DANDE on 03-09-2016.
 */

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import collegeexplore.CollegeInfo.R;


public class NitsNewsNotifications extends RecyclerView.Adapter<NitsNewsNotifications.ViewHolder> {
    private String[] UniqueKey;
   // private Integer[] imagedata;
    private TransitionDrawable td1;
    private OnItemClickListener mListener;
   // private  String[] colors={"#3F51B5","#2f374c","#23A96E","#ff0000","#1b409f","#FF4081","#02D8B0"};
   private  String[] colors={"#ffffff","#ffffff","#ffffff","#ffffff","#ffffff","#ffffff","#ffffff"};
   // final String UniqueKey = "HOME_NITS_NEWS_NOTIFYS";
   // private ArrayList<Bitmap> imagedata=new ArrayList<Bitmap>();
    public static String[] imagedata;
    private int row_index=-1;
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
        TextView imageView;
        CardView carrer_cardview;
        public ViewHolder(View v) {
            super(v);
            // label = (TextView) v.findViewById(R.id.dandeking);
            //  mTextView = (TextView) v;
            imageView = v.findViewById(R.id.imgmaps);
            carrer_cardview= v.findViewById(R.id.carrer_cardview);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NitsNewsNotifications(String[] maincontents, String[] imageId, homeMapsActivity listener) {

        UniqueKey = maincontents;
        mListener = listener;
        imagedata = imageId;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public NitsNewsNotifications.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_nits_news_notifications, parent, false);

        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder1, final int position) {


        // holder1.label.setText("test dande");

            holder1.imageView.setText(imagedata[position]);

        holder1.imageView.setTypeface(Typeface.create("arial", Typeface.NORMAL));
        holder1.carrer_cardview.setCardBackgroundColor(Color.parseColor(colors[position]));
/*if(position==0)
{
    holder1.imageView.setAlpha((float) 0.5);
}*/
            // holder1.imageView.setLayoutParams(new FrameLayout.LayoutParams(240, 240));
            //holder.itemView.setLayoutParams(params);



        holder1.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   row_index=position;
              //  holder1.imageView.setAlpha((float) 0.3);
                mListener.onClick(view, position,UniqueKey[0]);
            }
        });
//        if(row_index==position){
//            holder1.imageView.setBackgroundColor(Color.parseColor("#567845"));
//            holder1.imageView.setTextColor(Color.parseColor("#ffffff"));
//        }
//        else
//        {
//            holder1.imageView.setBackgroundColor(Color.parseColor("#ffffff"));
//            holder1.imageView.setTextColor(Color.parseColor("#000000"));
//        }

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