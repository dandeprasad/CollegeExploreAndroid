package collegeexplore.CollegeInfo.PlacementsWorkspace;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.UtilProperty;


public class MainPlacementsFragmentAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<HashMap> mDataset;
private ArrayList<Bitmap> imagedata;
    public final int TYPE_NEWS = 0;
    public final int TYPE_LOAD = 1;
    private OnItemClickListener mListener;
    Context contextForFragmentadp;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    ArrayList <Integer>yearCount;


    public interface OnItemClickListener {
        void onClick(View view, String position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
     static class NewsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        TextView label,placementamt;
        TextView labelId,PostedTime;
        ImageView imageView;
       Button tag_type;
       ImageView newsimgtagy;
        RelativeLayout nitsView;
        private View mViewGroup;
        public NewsViewHolder(View v) {
            super(v);
 /*           label = (TextView) v.findViewById(R.id.newstext);
            labelId = (TextView) v.findViewById(R.id.newsid);
            PostedTime = (TextView) v.findViewById(R.id.PostedTime);
            tag_type = (Button) v.findViewById(R.id.tag_type);
          //  mTextView = (TextView) v;
            imageView = (ImageView) v.findViewById(R.id.newsimg);*/
            newsimgtagy = v.findViewById(R.id.newsimgtagy);
            label = v.findViewById(R.id.textview_textAll);
            placementamt = v.findViewById(R.id.placementamt);


 /*          // nitsView=(RelativeLayout)RelativeLayout(nitsView);
            mViewGroup = v.findViewById(R.id.nitsView);*/
        }
    }
    static class LoadHolder extends RecyclerView.ViewHolder{
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public MainPlacementsFragmentAdaptor(ArrayList yearCount, Context contextForFragment, ArrayList<HashMap> maincontents, ArrayList<Bitmap> imageId, OnItemClickListener listener) {
        mDataset = maincontents;
        mListener = listener;
        imagedata = imageId;
        contextForFragmentadp=contextForFragment;
        this.yearCount=yearCount;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
if(viewType==TYPE_NEWS) {
    return new NewsViewHolder(inflater.inflate(R.layout.placements_main_layout, parent, false));
}else{
    return new NewsViewHolder(inflater.inflate(R.layout.placement_text, parent, false));
}

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
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

        if(getItemViewType(position)==TYPE_NEWS) {
            if (!(mDataset.isEmpty())) {

                HashMap<String, String> values = mDataset.get(position);

/*            if (position==0){
                final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW, R.id.newsimg);
                holder.label.setLayoutParams(params);
            }*/
      /*          ((NewsViewHolder) holder).label.setText(values.get("NEWS_HEADER"));
                ((NewsViewHolder) holder).labelId.setText(values.get("NEWSID"));
                ((NewsViewHolder) holder).PostedTime.setText(values.get("POSTED_DATE"));
                ((NewsViewHolder) holder).tag_type.setText(values.get("TAG_TYPE"));
                ((NewsViewHolder) holder).tag_type.setBackgroundColor(Color.parseColor("#" +  values.get("TAG_COLOR")));
*/


                //holder.label.setTextColor(Color.BLACK);
                //    holder.label.setText(mDataset[position]);


/*            if (position==0){
                holder.imageView.setLayoutParams((new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        500)));
                holder.imageView.setPadding(0,0,0,0);
            }*/
                        if(!values.isEmpty()){
                            String  imageserver = null;
                            try {
                                  imageserver =  UtilProperty.getProperty("img_NewsFragmentAdaptor", contextForFragmentadp,"imagepath.properties");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            ((NewsViewHolder) holder).placementamt.setText(values.get("PLACE_AMOUNT"));
                               // ((NewsViewHolder) holder).newsimgtagy.setImageBitmap(imagedata.get(position));
                              //  Picasso.with(contextForFragmentadp).load("https://s3.us-east-2.amazonaws.com/elasticbeanstalk-us-east-2-083183914236/CLGS_PROJECT/AllNewsImages/news4.jpg").into( ((NewsViewHolder) holder).newsimgtagy);
                               // Picasso.with(contextForFragmentadp).load("http://192.168.43.60:9544/CollegeGuideWorkSpace/WebUserRegistration/news0.jpg").into( ((NewsViewHolder) holder).newsimgtagy);
                                Picasso.with(contextForFragmentadp).load(values.get("PLACE_COMPANY")).resize(600, 200).into( ((NewsViewHolder) holder).newsimgtagy);

                           // ((NewsViewHolder) holder).imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            ((NewsViewHolder) holder).newsimgtagy.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Toast.makeText(contextForFragmentadp, contextForFragmentadp.getString(R.string.package_offered) + ((NewsViewHolder) holder).placementamt.getText(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

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


            }
            // holder.itemView.setPadding(8, 8, 8, 8);

            // holder.mTextView.setText(mDataset[position]);
            //  holder.mTextView.setTextColor(Color.BLACK);

/*            ((NewsViewHolder) holder).label.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //holder.labelId.getText();
                    mListener.onClick(view, (String) ((NewsViewHolder) holder).labelId.getText());
                }
            });

            ((NewsViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mListener.onClick(view, (String) ((NewsViewHolder) holder).labelId.getText());
                }
            });*/

        }
        if(getItemViewType(position)==TYPE_LOAD) {
            if (!(mDataset.isEmpty())) {

                HashMap<String, String> values = mDataset.get(position);
                ((NewsViewHolder) holder).label.setText(values.get("PLACE_YEAR"));

            }
            }


    }
    int i=0;
    @Override
    public int getItemViewType(int position) {
        if((i<yearCount.size() && ((position == (yearCount.get(0)))) || (position == (yearCount.get(1))))) {

//||((position == (yearCount.get(2))))
                return TYPE_LOAD;


        }
        else {
            return TYPE_NEWS;
        }


        }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
         */
    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }


    interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}