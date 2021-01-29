package collegeexplore.CollegeInfo.CollegesAtMaps.CollegesOnclick.Reviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;

import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.other.CircleTransform;


public class ReviewFragmentAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<HashMap> mDataset;
private ArrayList<Bitmap> imagedata;
    public final int TYPE_NEWS = 0;
    public final int TYPE_LOAD = 1;
    private OnItemClickListener mListener;
    Context contextForFragmentadp;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;


    public interface OnItemClickListener {
        void onClick(String reviewid, String position);

        void onClick(int adapterPosition, HashMap hashMap, ImageView newsimgtagy);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
     static class NewsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        TextView fest_caption,newsmaintext;
        TextView maincontent,usernamerating,userdate,likecount,unlikecount;
        ImageView imageView;
       RatingBar fcBar1,envBar1,camBar1,placBar1;
       ImageView imgProfile,like_button,unlikebutton;;
       LinearLayout imageLayout;
        RelativeLayout nitsView;
        private View mViewGroup;
        public NewsViewHolder(View v) {
            super(v);
            fcBar1 = v.findViewById(R.id.fcBar1);
            like_button = v.findViewById(R.id.like_button);
            unlikebutton = v.findViewById(R.id.unlikebutton);

            imageLayout=v.findViewById(R.id.imageLayout);
            imgProfile = v.findViewById(R.id.carduserImageimg);
            camBar1 = v.findViewById(R.id.camBar1);
            envBar1 = v.findViewById(R.id.envBar1);
            placBar1 = v.findViewById(R.id.placBar1);
            fest_caption = v.findViewById(R.id.fest_caption);
            maincontent= v.findViewById(R.id.maincontent);
            usernamerating= v.findViewById(R.id.usernamerating);
            userdate= v.findViewById(R.id.userdate);
            likecount= v.findViewById(R.id.likecount);
                    unlikecount= v.findViewById(R.id.unlikecount);
        }
    }
    static class LoadHolder extends RecyclerView.ViewHolder{
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public ReviewFragmentAdaptor(Context contextForFragment, ArrayList<HashMap> maincontents, ArrayList<Bitmap> imageId, OnItemClickListener listener) {
        mDataset = maincontents;
        mListener = listener;
        imagedata = imageId;
        contextForFragmentadp=contextForFragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType==0){
            return new NewsViewHolder(inflater.inflate(R.layout.review_map_wholedata,parent,false));
        }else{
            return new LoadHolder(inflater.inflate(R.layout.row_load,parent,false));
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

        if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener!=null){
            isLoading = true;
            loadMoreListener.onLoadMore();
        }
        int val = position;
       //

        if(getItemViewType(position)==TYPE_NEWS) {
            if (!(mDataset.isEmpty())) {

                HashMap<String, String> values = mDataset.get(position);

/*            if (position==0){
                final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW, R.id.newsimg);
                holder.label.setLayoutParams(params);
            }*///maincontent,usernamerating,userdate,likecount,unlikecount;
                ((NewsViewHolder) holder).maincontent.setText(values.get("REVIEW_CONTENT"));
                ((NewsViewHolder) holder).usernamerating.setText(values.get("USERNAME"));
                ((NewsViewHolder) holder).userdate.setText(values.get("DATE"));
                ((NewsViewHolder) holder).likecount.setText(values.get("LIKE_COUNT"));
                ((NewsViewHolder) holder).unlikecount.setText(values.get("UNLIKE_COUNT"));
               // ((NewsViewHolder) holder).ratingbarsmall.setText(values.get("ENV_RATING"));
                if(!(values.get("REVIEW_CAPTION")==null || values.get("REVIEW_CAPTION").equalsIgnoreCase(""))) {
                    ((NewsViewHolder) holder).fest_caption.setText(values.get("REVIEW_CAPTION"));
                }
                ((NewsViewHolder) holder).fcBar1.setRating(Float.valueOf(values.get("FAC_RATING")));
                ((NewsViewHolder) holder).envBar1.setRating(Float.valueOf(values.get("ENV_RATING")));
                ((NewsViewHolder) holder).camBar1.setRating(Float.valueOf(values.get("CAMP_RATING")));
                ((NewsViewHolder) holder).placBar1.setRating(Float.valueOf(values.get("PLACE_RATING")));
               if(values.get("LIKE_COLOR").equalsIgnoreCase("Y")){
                   ((NewsViewHolder) holder).like_button.setColorFilter(contextForFragmentadp.getResources().getColor(R.color.green_dark));
                }
                else{
                   ((NewsViewHolder) holder).like_button .clearColorFilter();
                  // ((NewsViewHolder) holder).like_button.setColorFilter(contextForFragmentadp.getResources().getColor(R.color.White));
               }
                if(values.get("UNLIKE_COLOR").equalsIgnoreCase("Y")){
                    ((NewsViewHolder) holder).unlikebutton.setColorFilter(contextForFragmentadp.getResources().getColor(R.color.green_dark));
                }
                else{
                    ((NewsViewHolder) holder).unlikebutton.clearColorFilter();;//setColorFilter(contextForFragmentadp.getResources().getColor(R.color.White));
                }
                ((NewsViewHolder) holder).imgProfile.setImageResource(R.drawable.userlogo);

if(values.get("USER_IMAGE")!="") {
    Glide.with(contextForFragmentadp).load(values.get("USER_IMAGE"))
            .crossFade()
            .thumbnail(0.5f)
            .bitmapTransform(new CircleTransform(contextForFragmentadp))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(((NewsViewHolder) holder).imgProfile);
}
                ((NewsViewHolder) holder).like_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //holder.labelId.getText();
                        HashMap<String, String> values = mDataset.get(position);

                        mListener.onClick(values.get("REVIEW_ID").toString(), "LIKE");

                       String likedone= values.get("LIKE_COLOR");
                      if(likedone.equalsIgnoreCase("N")){
                          int count = Integer.parseInt(String.valueOf(((NewsViewHolder) holder).likecount.getText()));
                          int inc_count= count+1;
                          ((NewsViewHolder) holder).like_button.setColorFilter(contextForFragmentadp.getResources().getColor(R.color.green_dark));
                          values.put("LIKE_COLOR","Y");
                          values.put("LIKE_COUNT",Integer.toString(inc_count));
                          ((NewsViewHolder) holder).likecount.setText(Integer.toString(inc_count));}
                          String unlikedone= values.get("UNLIKE_COLOR");
                         if( unlikedone.equalsIgnoreCase("Y")){
                             int count1 = Integer.parseInt(String.valueOf(((NewsViewHolder) holder).unlikecount.getText()));
                             int inc_count1= count1-1;
                             ((NewsViewHolder) holder).unlikecount.setText(Integer.toString(inc_count1));
                             values.put("UNLIKE_COLOR","N");
                             values.put("UNLIKE_COUNT",Integer.toString(inc_count1));

                             ((NewsViewHolder) holder).unlikebutton.clearColorFilter();
                         }


      /*                  if(likedone.equalsIgnoreCase("Y")){
                            int count = Integer.parseInt(String.valueOf(((NewsViewHolder) holder).likecount.getText()));
                            int inc_count= count+1;
                            ((NewsViewHolder) holder).like_button.setColorFilter(contextForFragmentadp.getResources().getColor(R.color.green_dark));
                            values.put("LIKE_COLOR","Y");
                            values.put("LIKE_COUNT",Integer.toString(inc_count));
                            ((NewsViewHolder) holder).likecount.setText(Integer.toString(inc_count));
                        }*/
                   //     hashmap.put("LIKE_COLOR","N");
                   //     hashmap.put("UNLIKE_COLOR","N");

                    }
                });
                ((NewsViewHolder) holder).unlikebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        HashMap<String, String> values = mDataset.get(position);
                        String unlikedone= values.get("UNLIKE_COLOR");
                        mListener.onClick(values.get("REVIEW_ID").toString(), "UNLIKE");
                        if(unlikedone.equalsIgnoreCase("N")){

                        int count = Integer.parseInt(String.valueOf(((NewsViewHolder) holder).unlikecount.getText()));
                        int inc_count= count+1;
                        ((NewsViewHolder) holder).unlikecount.setText(Integer.toString(inc_count));
                            values.put("UNLIKE_COLOR","Y");
                        values.put("UNLIKE_COUNT",Integer.toString(inc_count));

                        ((NewsViewHolder) holder).unlikebutton.setColorFilter(contextForFragmentadp.getResources().getColor(R.color.green_dark));
                   //     mListener.onClick(holder.getAdapterPosition(),mDataset.get(position), ((NewsViewHolder) holder).imageView);
                    }
                        String likedone= values.get("LIKE_COLOR");
                        if( likedone.equalsIgnoreCase("Y")){
                            int count = Integer.parseInt(String.valueOf(((NewsViewHolder) holder).likecount.getText()));
                            int inc_count= count-1;
                            ((NewsViewHolder) holder).like_button.clearColorFilter();
                            values.put("LIKE_COLOR","N");
                            values.put("LIKE_COUNT",Integer.toString(inc_count));
                            ((NewsViewHolder) holder).likecount.setText(Integer.toString(inc_count));
                        }


                    }
                });
/*if(!( values.get("REVIEW_IMAGES").equalsIgnoreCase("")||values.get("REVIEW_IMAGES")==null)) {
    String[] value_split = values.get("REVIEW_IMAGES").split("\\|");

    if (value_split.length != 0) {
        for(int i=0;i<value_split.length;i++) {
            ImageView image = new ImageView(contextForFragmentadp);
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(250, 250));
            image.setMaxHeight(90);
            image.setMaxWidth(90);

            Picasso.with(contextForFragmentadp).load(value_split[i]).into(image);
            // Adds the view to the layout
            ((NewsViewHolder) holder).imageLayout.addView(image);
        }
    }
}*/
/*                hashmap.put("USERNAME",USERNAME);
                hashmap.put("USERMAIL",USERMAIL);
                hashmap.put("REVIEW_CAPTION",REVIEW_CAPTION);
                hashmap.put("REVIEW_CONTENT",REVIEW_CONTENT);
                hashmap.put("USER_IMAGE",USER_IMAGE);
                hashmap.put("REVIEW_IMAGES",REVIEW_IMAGES);
                hashmap.put("REVIEW_ID",REVIEW_ID);
                hashmap.put("LIKE_COUNT",LIKE_COUNT);
                hashmap.put("UNLIKE_COUNT",UNLIKE_COUNT);
                hashmap.put("FAC_RATING",FAC_RATING);
                hashmap.put("PLACE_RATING",PLACE_RATING);
                hashmap.put("ENV_RATING",ENV_RATING);
                hashmap.put("CAMP_RATING",CAMP_RATING);*/


        }
     /*   holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClick(view, position);
            }
        });*/

    }}
    @Override
    public int getItemViewType(int position) {
        if(mDataset.get(position).get("LOAD_CHECK").equals("loaded")){
            return TYPE_NEWS;
        }       else{
        return TYPE_LOAD;
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