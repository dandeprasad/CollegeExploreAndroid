package collegeexplore.CollegeInfo.FestsCollegesWorkspace;

/**
 * Created by DANDE on 03-09-2016.
 */

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import collegeexplore.CollegeInfo.R;


public class FestsListofAllNotifications extends RecyclerView.Adapter<FestsListofAllNotifications.ViewHolder> {
    private String[] mDataset;

    private TransitionDrawable td1;
    private OnItemClickListener mListener;
    Context contextval;
    String UniqueKey = "NEWS_NOTIFY_ALLNOTIFYS";



    public interface OnItemClickListener {
        void onClick(View view, int position, String U);
        void onClick(View view, String  title, String U);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        TextView label;
        TextView textView;
        public ViewHolder(View v) {
            super(v);
           // label = (TextView) v.findViewById(R.id.dandeking);
            //  mTextView = (TextView) v;
            textView = v.findViewById(R.id.festfiltertext);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FestsListofAllNotifications(Context contextval ,String[] maincontents, OnItemClickListener listener) {
        mDataset = maincontents;
        mListener = listener;
        this.contextval=contextval;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public FestsListofAllNotifications.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fests_all_notifications, parent, false);

        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder1, final int position) {


        // holder1.label.setText("test dande");

        String  x = mDataset[position];
        holder1.textView.setText(x);


       // holder1.imageView.setImageResource(imagedata[position]);

        // holder1.imageView.setLayoutParams(new FrameLayout.LayoutParams(240, 240));
        //holder.itemView.setLayoutParams(params);
      //  holder1.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);




        holder1.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(position==0) {
                    UniqueKey="ALL";
                    mListener.onClick(view, mDataset[position], UniqueKey);
                }
                if(position==1) {
                    UniqueKey="TECHNICAL";
                    mListener.onClick(view, mDataset[position], UniqueKey);
                }
                if(position==2) {
                    UniqueKey="CULTURAL";
                    mListener.onClick(view, mDataset[position], UniqueKey);
                }
                if(position==3) {
                    UniqueKey="NIT";
                    mListener.onClick(view, mDataset[position], UniqueKey);
                }
                if(position==4) {
                    UniqueKey="IIT";
                    mListener.onClick(view, mDataset[position], UniqueKey);
                }
                if(position==5) {
                    UniqueKey="IIIT";
                    mListener.onClick(view, mDataset[position], UniqueKey);
                }
                if(position==6) {
                    UniqueKey="DEEMED";
                    mListener.onClick(view, mDataset[position], UniqueKey);
                }
                if(position==7) {
                    UniqueKey="CSE";
                    mListener.onClick(view, mDataset[position], UniqueKey);
                }
                if(position==8) {
                    UniqueKey="CIVIL";
                    mListener.onClick(view, mDataset[position], UniqueKey);
                }
                if(position==9) {
                    UniqueKey="ECE";
                    mListener.onClick(view, mDataset[position], UniqueKey);
                }
                if(position==10) {
                    UniqueKey="EEE";
                    mListener.onClick(view, mDataset[position], UniqueKey);
                }
                if(position==11) {
                    UniqueKey="MECH";
                    mListener.onClick(view, mDataset[position], UniqueKey);
                }
                if(position==12) {
                    Toast.makeText(contextval,"Will be added soon", Toast.LENGTH_LONG).show();
                }
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