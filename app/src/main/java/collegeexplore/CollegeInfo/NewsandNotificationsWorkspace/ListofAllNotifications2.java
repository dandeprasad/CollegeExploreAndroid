package collegeexplore.CollegeInfo.NewsandNotificationsWorkspace;

/**
 * Created by DANDE on 03-09-2016.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import collegeexplore.CollegeInfo.R;


public class ListofAllNotifications2 extends RecyclerView.Adapter<ListofAllNotifications2.ViewHolder> {
    private ArrayList<HashMap> mDataset;
    private HashMap<String,String> imagedata;
    private TransitionDrawable td1;
    private  Context contexthere;
    private OnItemClickListener mListener;
    final String UniqueKey = "NEWS_NOTIFY_ALLNOTIFYS";



    public interface OnItemClickListener {
        void onClick(View view, int position, String U);

        void onClick(View view, String examid,int position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        TextView examname,examdate,examdesc,posteddate_news;
        ImageView imageView,examsbackfround;
        CardView view2;
        public ViewHolder(View v) {
            super(v);
            // label = (TextView) v.findViewById(R.id.dandeking);
            //  mTextView = (TextView) v;
            posteddate_news = v.findViewById(R.id.posteddate_news);

            examname = v.findViewById(R.id.examname);
            examdate = v.findViewById(R.id.examdate);
            examdesc = v.findViewById(R.id.examdesc);
            view2 = v.findViewById(R.id.view2);
            examsbackfround= v.findViewById(R.id.examsbackfround);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListofAllNotifications2(ArrayList<HashMap> maincontents, OnItemClickListener listener, Context contexthere) {
        mDataset = maincontents;
        mListener = listener;

        this.contexthere=contexthere;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListofAllNotifications2.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_notifications2, parent, false);

        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder1, final int position) {


        if (!(mDataset.isEmpty())) {
            if(mDataset.get(position)  !=null) {
                HashMap<String, String> values = mDataset.get(position);

                holder1.posteddate_news.setText("Posted Date: "+values.get("POSTED_DATE"));

                holder1.examname.setText(values.get("CLG_NAME"));
                holder1.examdate.setText(values.get("EXAM_DATE"));
                holder1.examdesc.setText(Html.fromHtml(values.get("EXAM_DETAILS").toString()));
if(position %5==0){

    final int sdk = android.os.Build.VERSION.SDK_INT;
    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
        holder1.examsbackfround.setBackgroundDrawable(ContextCompat.getDrawable(contexthere, R.drawable.gradientexams1) );
    } else {
        holder1.examsbackfround.setBackground(ContextCompat.getDrawable(contexthere, R.drawable.gradientexams1));
    }


}
                if(position %5==1){
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        holder1.examsbackfround.setBackgroundDrawable(ContextCompat.getDrawable(contexthere, R.drawable.gradientexams) );
                    } else {
                        holder1.examsbackfround.setBackground(ContextCompat.getDrawable(contexthere, R.drawable.gradientexams));
                    }


                }
                if(position %5==2){

                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        holder1.examsbackfround.setBackgroundDrawable(ContextCompat.getDrawable(contexthere, R.drawable.gradientexams2) );
                    } else {
                        holder1.examsbackfround.setBackground(ContextCompat.getDrawable(contexthere, R.drawable.gradientexams2));
                    }
                }
                if(position %5==3){

                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        holder1.examsbackfround.setBackgroundDrawable(ContextCompat.getDrawable(contexthere, R.drawable.gradientexams3) );
                    } else {
                        holder1.examsbackfround.setBackground(ContextCompat.getDrawable(contexthere, R.drawable.gradientexams3));
                    }


                }
                if(position %5==4){

                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        holder1.examsbackfround.setBackgroundDrawable(ContextCompat.getDrawable(contexthere, R.drawable.gradientexams4) );
                    } else {
                        holder1.examsbackfround.setBackground(ContextCompat.getDrawable(contexthere, R.drawable.gradientexams4));
                    }

                }
                //examsbackfround

              //  Picasso.with(contexthere).load(values.get("NEWS_IMAGE")).into(holder1.imageView);
                //   Picasso.with(contextForFragmentadp).load(values.get("NEWS_IMAGE")).into( ((NewsViewHolder) holder).newsimgtagy);


                //   holder1.imageView.setImageBitmap(x);


                // holder1.imageView.setImageResource(imagedata[position]);

                // holder1.imageView.setLayoutParams(new FrameLayout.LayoutParams(240, 240));
                //holder.itemView.setLayoutParams(params);
//                holder1.imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            }

        }


/*        holder1.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClick(view, position,UniqueKey);
            }
        });*/


     /*   holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClick(view, position);
            }
        });*/


        holder1.examname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(mDataset.isEmpty())) {
                HashMap<String, String> values = mDataset.get(position);
                    if(mDataset.get(position)  !=null) {

                        mListener.onClick(view, (String) values.get("EXAMID"), position);
                }
            }}
        });

        holder1.examdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(mDataset.isEmpty())) {
                HashMap<String, String> values = mDataset.get(position);
                    if(mDataset.get(position)  !=null) {

                        mListener.onClick(view, (String) values.get("EXAMID"), position);
                }}
            }
        });
        holder1.examdesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(mDataset.isEmpty())) {
                    if(mDataset.get(position)  !=null) {

                        HashMap<String, String> values = mDataset.get(position);
                    mListener.onClick(view, (String) values.get("EXAMID"), position);
                }
            }}
        });
        holder1.view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(mDataset.isEmpty())) {
                    if(mDataset.get(position)  !=null) {
                    HashMap<String, String> values = mDataset.get(position);
                    mListener.onClick(view, (String) values.get("EXAMID"), position);
                }  }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}