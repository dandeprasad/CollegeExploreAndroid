package collegeexplore.CollegeInfo.QuestionsAnswersWorkspace;

/**
 * Created by DANDE on 03-09-2016.
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


public class ListofFilters extends RecyclerView.Adapter<ListofFilters.ViewHolder> {
    private String[] mDataset;
    private Bitmap[] imagedata;
    private TransitionDrawable td1;
    private OnItemClickListener mListener;
    final String UniqueKey = "NEWS_NOTIFY_ALLNOTIFYS";



    public interface OnItemClickListener {
        void onClick(View view, int position, String U);
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
            imageView = v.findViewById(R.id.img);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListofFilters(String[] maincontents, Bitmap[] imageId, OnItemClickListener listener) {
        mDataset = maincontents;
        mListener = listener;
        imagedata = imageId;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListofFilters.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quest_ans_filters, parent, false);

        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder1, final int position) {


        // holder1.label.setText("test dande");
        Bitmap x = imagedata[position];
        holder1.imageView.setImageBitmap(x);
       // holder1.imageView.setImageResource(imagedata[position]);

        // holder1.imageView.setLayoutParams(new FrameLayout.LayoutParams(240, 240));
        //holder.itemView.setLayoutParams(params);
        holder1.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);




        holder1.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        return imagedata.length;
    }

}