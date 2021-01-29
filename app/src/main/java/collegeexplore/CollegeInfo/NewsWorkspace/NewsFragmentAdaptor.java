package collegeexplore.CollegeInfo.NewsWorkspace;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.UtilProperty;


public class NewsFragmentAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<HashMap> mDataset;
private ArrayList<Bitmap> imagedata;
    public final int TYPE_NEWS = 0;
    public final int TYPE_LOAD = 1;
    private OnItemClickListener mListener;
    Context contextForFragmentadp;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;


    public interface OnItemClickListener {
        void onClick(View view, String position);

        void onClick(int adapterPosition, HashMap hashMap, ImageView newsimgtagy);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
     static class NewsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        TextView label,newsmaintext;
        TextView labelId,PostedTime;
        ImageView imageView;
       Button tag_type;
       ImageView newsimgtagy;
        RelativeLayout nitsView;
        private View mViewGroup;
        public NewsViewHolder(View v) {
            super(v);
            label = v.findViewById(R.id.newstext);
            newsmaintext = v.findViewById(R.id.newsmaintext);

            labelId = v.findViewById(R.id.newsid);
            PostedTime = v.findViewById(R.id.PostedTime);
            tag_type = v.findViewById(R.id.tag_type);
          //  mTextView = (TextView) v;
            imageView = v.findViewById(R.id.newsimg);
            newsimgtagy = v.findViewById(R.id.newsimgtagy);
           // nitsView=(RelativeLayout)RelativeLayout(nitsView);
            mViewGroup = v.findViewById(R.id.nitsView);
        }
    }
    static class LoadHolder extends RecyclerView.ViewHolder{
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public NewsFragmentAdaptor(Context contextForFragment, ArrayList<HashMap> maincontents, ArrayList<Bitmap> imageId, OnItemClickListener listener) {
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
            return new NewsViewHolder(inflater.inflate(R.layout.news_main_layout,parent,false));
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
            }*/




                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    ((NewsViewHolder) holder).label.setText(Html.fromHtml(values.get("NEWS_DETAILS")));
                    ((NewsViewHolder) holder).newsmaintext.setText(Html.fromHtml(values.get("NEWS_HEADER")));
                }
                if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.N) {
                    ((NewsViewHolder) holder).label.setText(Html.fromHtml(values.get("NEWS_DETAILS"),Html.FROM_HTML_MODE_COMPACT));
                    ((NewsViewHolder) holder).newsmaintext.setText(Html.fromHtml(values.get("NEWS_HEADER"),Html.FROM_HTML_MODE_COMPACT));
                }

                ((NewsViewHolder) holder).labelId.setText(values.get("NEWSID"));
                ((NewsViewHolder) holder).PostedTime.setText(values.get("POSTED_DATE"));
//                ((NewsViewHolder) holder).tag_type.setText(values.get("TAG_TYPE"));



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



                              //  ((NewsViewHolder) holder).imageView.setImageBitmap(imagedata.get(position));
                               // Picasso.with(contextForFragmentadp).load("https://s3.us-east-2.amazonaws.com/elasticbeanstalk-us-east-2-083183914236/CLGS_PROJECT/AllNewsImages/news4.jpg").into( ((NewsViewHolder) holder).imageView);
                                //Picasso.with(contextForFragmentadp).load("http://192.168.43.60:9544/CollegeGuideWorkSpace/WebUserRegistration/news0.jpg").into( ((NewsViewHolder) holder).imageView);
                                Picasso.with(contextForFragmentadp).load(values.get("NEWS_IMAGE")).resize(600, 200).into( ((NewsViewHolder) holder).imageView);
                                ViewCompat.setTransitionName(((NewsViewHolder) holder).imageView,values.get("NEWSID"));


                            ((NewsViewHolder) holder).imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
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



            // holder.itemView.setPadding(8, 8, 8, 8);

            // holder.mTextView.setText(mDataset[position]);
            //  holder.mTextView.setTextColor(Color.BLACK);

            ((NewsViewHolder) holder).label.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //holder.labelId.getText();
                    mListener.onClick(holder.getAdapterPosition(),mDataset.get(position), ((NewsViewHolder) holder).imageView);
                }
            });
                ((NewsViewHolder) holder).newsmaintext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //holder.labelId.getText();
                        mListener.onClick(holder.getAdapterPosition(),mDataset.get(position), ((NewsViewHolder) holder).imageView);
                    }
                });

/*                ((NewsViewHolder) holder).newsimgtagy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //holder.labelId.getText();
                        mListener.onClick(holder.getAdapterPosition(),mDataset.get(position), ((NewsViewHolder) holder).newsimgtagy);
                    }
                });*/
            ((NewsViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mListener.onClick(holder.getAdapterPosition(),mDataset.get(position), ((NewsViewHolder) holder).imageView);
                }
            });}

        }
     /*   holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClick(view, position);
            }
        });*/

    }
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