package collegeexplore.CollegeInfo.NewsandNotificationsWorkspace;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import collegeexplore.CollegeInfo.R;


public class HomeDetailedDataAdaptor extends RecyclerView.Adapter<HomeDetailedDataAdaptor.ViewHolder> {
    private ArrayList<HashMap> mDataset;
    private Bitmap[] imagedata=new Bitmap[29];

    private OnItemClickListener mListener;




    public interface OnItemClickListener {
        void onClick(View view, String position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        TextView label;
        TextView labelId,PostedTime;
        ImageView imageView;
        public ViewHolder(View v) {
            super(v);
            label = v.findViewById(R.id.newstext);
            labelId = v.findViewById(R.id.newsid);
            PostedTime = v.findViewById(R.id.PostedTime);

            //  mTextView = (TextView) v;
            imageView = v.findViewById(R.id.newsimg);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeDetailedDataAdaptor(ArrayList<HashMap> maincontents, Bitmap[] imageId, OnItemClickListener listener) {
        mDataset = maincontents;
        mListener = listener;
        imagedata = imageId;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public HomeDetailedDataAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_detailed_inputdata, parent, false);

        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
     /*   int i;
        View dande = null;
        for (i=0;i<mDataset.length;i++)
        {
           String king = mDataset[position];
            holder.mTextView.setText(king);
            holder.mTextView.setTextColor(Color.BLACK);

            if (dande != null) {
                holder.seperat = dande.findViewById(R.id.separate);
            }
            // seperat =  seperat.findViewById(R.id.separate);

        }
        */
        int val = position;
        //
        //

        if ( !(mDataset.isEmpty())) {

            HashMap<String, String> values = mDataset.get(position);

            holder.label.setText(values.get("EXAMS_HEADER"));
            holder.labelId.setText(values.get("EXAMSID"));
           // holder.PostedTime.setText(values.get("POSTED_DATE"));


            //holder.label.setTextColor(Color.BLACK);
            //    holder.label.setText(mDataset[position]);


                    holder.imageView.setImageBitmap(imagedata[position]);


//used for image trasition
   /*     final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i=position;
            public void run() {
                holder.imageView.setImageResource(imagedata[i]);
                i++;
                if(i>position+1)
                {
                    i=position;
                }
                handler.postDelayed(this, 2000) ;
            }
        };
        handler.postDelayed(runnable, 2000); //for initial delay..*/



      /*  holder.imageView.setImageDrawable(td1);
        td1.startTransition(10000);
        // and
        td1.reverseTransition(10000); */

                // RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.view_title.getLayoutParams();
                //RecyclerView.LayoutParams params = ( RecyclerView.LayoutParams) holder.imageView.getLayoutParams();
                //  params.setMargins(50,50,50,50);
                // holder.imageView.setLayoutParams(params);

                //holder.itemView.setLayoutParams(params);
                holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        }
        // holder.itemView.setPadding(8, 8, 8, 8);

        // holder.mTextView.setText(mDataset[position]);
        //  holder.mTextView.setTextColor(Color.BLACK);

        holder.label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //holder.labelId.getText();
                mListener.onClick(view,(String) holder.labelId.getText());
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClick(view,(String) holder.labelId.getText());
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
        return mDataset.size();
    }
}