package collegeexplore.CollegeInfo.V1;

/**
 * Created by DANDE on 03-09-2016.
 */

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

import collegeexplore.CollegeInfo.R;


public class MainIconsAdaptor extends RecyclerView.Adapter<MainIconsAdaptor.ViewHolder> {
    private String[] mDataset;
    private HashMap<String,String> imagedata;
    private TransitionDrawable td1;
    private  Context contexthere;
    private OnItemClickListener mListener;
    final String UniqueKey = "NEWS_NOTIFY_ALLNOTIFYS";



    public interface OnItemClickListener {
        void onClick(View view, int position, String U);

        void onClick(View view, int position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        TextView textviewtopclgs;
        ImageButton imageView;
        public ViewHolder(View v) {
            super(v);
           // label = (TextView) v.findViewById(R.id.dandeking);
            //  mTextView = (TextView) v;
            imageView = v.findViewById(R.id.imgallnotify);
            textviewtopclgs = v.findViewById(R.id.textviewtopclgs);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MainIconsAdaptor(String[] maincontents, HashMap imageId, OnItemClickListener listener, Context contexthere) {
        mDataset = maincontents;
        mListener = listener;
        imagedata = imageId;
        imageId=null;
        imageId=null;
        this.contexthere=contexthere;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MainIconsAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent,
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



        holder1.textviewtopclgs.setText(mDataset[position]);
if(mDataset[position].trim().equals("Colleges")) {
    String x = imagedata.get("icon_colleges");
    Picasso.with(contexthere).load(x).into(      holder1.imageView);
}
        if(mDataset[position].trim().equalsIgnoreCase("News")) {
            String x = imagedata.get("icon_news");
            Picasso.with(contexthere).load(x).into(      holder1.imageView);
        }
        if(mDataset[position].trim().equalsIgnoreCase("Fests")) {
            String x = imagedata.get("icon_fests");
            Picasso.with(contexthere).load(x).into(      holder1.imageView);
        }
        if(mDataset[position].trim().equalsIgnoreCase("Exams")) {
            String x = imagedata.get("icon_exams");
            Picasso.with(contexthere).load(x).into(      holder1.imageView);
        }
        if(mDataset[position].trim().equalsIgnoreCase("Admissions")) {
            String x = imagedata.get("icon_admissions");
            Picasso.with(contexthere).load(x).into(      holder1.imageView);
        }
        if(mDataset[position].trim().equalsIgnoreCase("Ranks")) {
            String x = imagedata.get("icon_ranks");
            Picasso.with(contexthere).load(x).into(      holder1.imageView);
        }
        if(mDataset[position].trim().equalsIgnoreCase("Cutoff's")) {
            String x = imagedata.get("icon_cutoff");
            Picasso.with(contexthere).load(x).into(      holder1.imageView);
        }
        if(mDataset[position].trim().equalsIgnoreCase("Placements")) {
            String x = imagedata.get("icon_placements");
            Picasso.with(contexthere).load(x).into(      holder1.imageView);
        }

     //   holder1.imageView.setImageBitmap(x);



       // holder1.imageView.setImageResource(imagedata[position]);

        // holder1.imageView.setLayoutParams(new FrameLayout.LayoutParams(240, 240));
        //holder.itemView.setLayoutParams(params);
        holder1.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);




        holder1.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClick(view, position);
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
        return mDataset.length;
    }

}