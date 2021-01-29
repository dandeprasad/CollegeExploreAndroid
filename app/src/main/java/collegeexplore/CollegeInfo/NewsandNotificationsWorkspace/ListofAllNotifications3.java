package collegeexplore.CollegeInfo.NewsandNotificationsWorkspace;

/**
 * Created by DANDE on 03-09-2016.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.UtilProperty;


public class ListofAllNotifications3 extends RecyclerView.Adapter<ListofAllNotifications3.ViewHolder> {
    private ArrayList<HashMap> mDataset;
    private HashMap<String,String> imagedata;
    private TransitionDrawable td1;
    private  Context contexthere;
    private OnItemClickListener mListener;
    final String UniqueKey = "NEWS_NOTIFY_ALLNOTIFYS";


    public interface OnItemClickListener {
        void onClick(View view, int position, String U);

        void onClick(View view, int i, String clg_id, String festimage, String collegename, ImageView imageView);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        TextView examname,examdate,examdesc;
        ImageView imageView;
        private TextView festname;
        private TextView festinfo;
        private TextView festdate;
        private TextView festlocation;
        private TextView festView;
        CardView view2;
        public ViewHolder(View v) {
            super(v);
            // label = (TextView) v.findViewById(R.id.dandeking);
            //  mTextView = (TextView) v;
            imageView = v.findViewById(R.id.shownewsnotifydetail);
            festname= v.findViewById(R.id.fest_name);
            festinfo = v.findViewById(R.id.fest_info);
            festdate = v.findViewById(R.id.Fest_date);
            festlocation = v.findViewById(R.id.Fest_loc);
            festView = v.findViewById(R.id.Fest_viewdetails);
            view2 = v.findViewById(R.id.carrer_cardview);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListofAllNotifications3(ArrayList<HashMap> maincontents, OnItemClickListener listener, Context contexthere) {
        mDataset = maincontents;
        mListener = listener;

        this.contexthere=contexthere;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListofAllNotifications3.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_notifications3, parent, false);

        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder1, final int position) {
        if (!(mDataset.isEmpty())) {


                HashMap<String, String> dandeval = mDataset.get(position);
if(dandeval  !=null) {

    String sourceString = "<b>" + R.string.fest_info + "</b> " + dandeval.get("CLG_FEST_INFO");
    String sourceString0 = "<b>" + dandeval.get("COLLEGE_NAME") + "</b> ";
    String sourceString1 = "<b>" + dandeval.get("CLG_FEST_DATE") + "</b> ";
    String sourceString2 = "<b>" + dandeval.get("CLG_LOCATION") + "</b> ";

    // String sourceString3 = "<b>" + "Fest Info: " + "</b> " + dandeval.get("CLG_FEST_INFO");

    // mytextview.setText(Html.fromHtml(sourceString));

    holder1.festinfo.setText(Html.fromHtml(sourceString));
    holder1.festname.setText(Html.fromHtml(sourceString0));
    holder1.festdate.setText(Html.fromHtml(sourceString1));
    holder1.festlocation.setText(Html.fromHtml(sourceString2));
    holder1.festView.setText(Html.fromHtml("<b>" + R.string.view_details + "</b> "));
    String imageserver = null;
    try {
        imageserver = UtilProperty.getProperty("img_FestsNewsNotificationsAdaptor", contexthere, "imagepath.properties");
    } catch (IOException e) {
        e.printStackTrace();
    }
    Picasso.with(contexthere).load(dandeval.get("FEST_IMAGE")).into(holder1.imageView);

    ViewCompat.setTransitionName(holder1.imageView, dandeval.get("CLGID"));


}

        }
        holder1.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                    if (!(mDataset.isEmpty())) {
                        if(mDataset.get(position)  !=null) {
                        HashMap<String, String> dandeval1 = mDataset.get(position);
                        String clg_id = dandeval1.get("CLGID");
                        String festimage = dandeval1.get("FEST_IMAGE");
                        String collegename = dandeval1.get("COLLEGE_NAME");

                        mListener.onClick(view, position, clg_id, festimage, collegename, holder1.imageView);
                    }}
                }

        });
        holder1.festView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (!(mDataset.isEmpty())) {
                        if(mDataset.get(position)  !=null) {
                            HashMap<String, String> dandeval1 = mDataset.get(position);
                            final String clg_id = dandeval1.get("CLGID");
                            String festimage = dandeval1.get("FEST_IMAGE");
                            String collegename = dandeval1.get("COLLEGE_NAME");

                            mListener.onClick(view, position, clg_id, festimage, collegename, holder1.imageView);
                        } }
            }
        });

        holder1.festView.setText(Html.fromHtml("<b>" + R.string.view_details + "</b> "));
        holder1.festinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    if (!(mDataset.isEmpty())) {
                        if(mDataset.get(position)  !=null) {
                            HashMap<String, String> dandeval1 = mDataset.get(position);
                            final String clg_id = dandeval1.get("CLGID");
                            String festimage = dandeval1.get("FEST_IMAGE");
                            String collegename = dandeval1.get("COLLEGE_NAME");
                            mListener.onClick(view, position, clg_id, festimage, collegename, holder1.imageView);
                        } }
                }

        });
        holder1.festname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (!(mDataset.isEmpty())) {
                        if(mDataset.get(position)  !=null) {
                    HashMap <String,String> dandeval1= mDataset.get(position);
                    final String clg_id = dandeval1.get("CLGID");
                    String festimage = dandeval1.get("FEST_IMAGE");
                    String collegename = dandeval1.get("COLLEGE_NAME");
                    mListener.onClick(view, position - 4, clg_id, festimage, collegename, holder1.imageView);
                }}}

        });
        holder1.festdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (!(mDataset.isEmpty())) {
                        if(mDataset.get(position)  !=null) {
                    HashMap <String,String> dandeval1=  mDataset.get(position);
                    final String clg_id = dandeval1.get("CLGID");
                    String festimage = dandeval1.get("FEST_IMAGE");
                    String collegename = dandeval1.get("COLLEGE_NAME");
                    mListener.onClick(view, position, clg_id, festimage, collegename, holder1.imageView);
                }}
            }
        });
        holder1.festlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (!(mDataset.isEmpty())) {
                        if(mDataset.get(position)  !=null) {
                    HashMap <String,String> dandeval1=  mDataset.get(position);
                    final String clg_id = dandeval1.get("CLGID");
                    String festimage = dandeval1.get("FEST_IMAGE");
                    String collegename = dandeval1.get("COLLEGE_NAME");
                    mListener.onClick(view, position, clg_id, festimage, collegename, holder1.imageView);
                }}}

        });
        holder1.view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    if (!(mDataset.isEmpty())) {
                        if(mDataset.get(position)  !=null) {
                    HashMap <String,String> dandeval1=  mDataset.get(position);
                    final String clg_id = dandeval1.get("CLGID");
                    String festimage = dandeval1.get("FEST_IMAGE");
                    String collegename = dandeval1.get("COLLEGE_NAME");
                    mListener.onClick(view, position, clg_id, festimage, collegename, holder1.imageView);
                }}
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}