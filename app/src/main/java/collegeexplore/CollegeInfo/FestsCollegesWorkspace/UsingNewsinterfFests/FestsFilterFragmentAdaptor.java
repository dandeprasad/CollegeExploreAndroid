package collegeexplore.CollegeInfo.FestsCollegesWorkspace.UsingNewsinterfFests;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewCompat;
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


public class FestsFilterFragmentAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<HashMap> mDataset;
private ArrayList<Bitmap> imagedata;
    public final int TYPE_FESTS = 0;
    public final int TYPE_LOAD = 1;
    private OnItemClickListener mListener;
    Context contextForFragmentadp;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;


    public interface OnItemClickListener {
        void onClick(View view, String position);

        void onClick(int adapterPosition, HashMap hashMap, ImageView newsimgtagy);
        void onClick(View view, int position, String clg_id, String festimage, String collegename, ImageView imagetras);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
      class FestViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView festname;
        private TextView festinfo;
        private TextView festdate;
        private TextView festlocation;
        ImageView imageView;
        private TextView festView;
        public FestViewHolder(View v) {
            super(v);
            festname= v.findViewById(R.id.fest_name);
            imageView = v.findViewById(R.id.shownewsnotifydetail);

            festinfo = v.findViewById(R.id.fest_info);
            festdate = v.findViewById(R.id.Fest_date);
            festlocation = v.findViewById(R.id.Fest_loc);
            festView = v.findViewById(R.id.Fest_viewdetails);
        }
    }
    static class LoadHolder extends RecyclerView.ViewHolder{
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public FestsFilterFragmentAdaptor(Context contextForFragment, ArrayList<HashMap> maincontents, ArrayList<Bitmap> imageId, OnItemClickListener listener) {
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
            return new FestViewHolder(inflater.inflate(R.layout.fests_datacontent,parent,false));
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

        if(getItemViewType(position)==TYPE_FESTS) {
            if (!(mDataset.isEmpty())){



                HashMap <String,String> dandeval= mDataset.get(position);



                String sourceString = "<b>" + R.string.fest_info + "</b> " + dandeval.get("CLG_FEST_INFO");
                String sourceString0 = "<b>" + dandeval.get("COLLEGE_NAME") + "</b> ";
                String sourceString1 = "<b>" + dandeval.get("CLG_FEST_DATE")+ "</b> " ;
                String sourceString2 = "<b>" + dandeval.get("CLG_LOCATION") + "</b> ";

                // String sourceString3 = "<b>" + "Fest Info: " + "</b> " + dandeval.get("CLG_FEST_INFO");

                // mytextview.setText(Html.fromHtml(sourceString));

                (( FestViewHolder )holder).festinfo.setText(Html.fromHtml(sourceString));
                (( FestViewHolder )holder).festname.setText(Html.fromHtml(sourceString0));
                (( FestViewHolder )holder).festdate.setText(Html.fromHtml(sourceString1));
                (( FestViewHolder )holder).festlocation.setText(Html.fromHtml(sourceString2));
                (( FestViewHolder )holder).festView.setText(Html.fromHtml("<b>" + R.string.view_details + "</b> "));
                String  imageserver = null;
                try {
                    imageserver =  UtilProperty.getProperty("img_FestsNewsNotificationsAdaptor", contextForFragmentadp,"imagepath.properties");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Picasso.with(contextForFragmentadp).load(dandeval.get("FEST_IMAGE")).resize(600, 200).into(  (( FestViewHolder )holder).imageView);
                ViewCompat.setTransitionName(  (( FestViewHolder )holder).imageView, dandeval.get("CLGID"));
            }

     /*   holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClick(view, position);
            }
        });*/
        (( FestViewHolder )holder).imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap <String,String> dandeval1= mDataset.get(position);
                String clg_id = dandeval1.get("CLGID");
                String festimage = dandeval1.get("FEST_IMAGE");
                String collegename = dandeval1.get("COLLEGE_NAME");

                mListener.onClick(view, position,clg_id,festimage,collegename,(( FestViewHolder )holder).imageView);
            }
        });
        (( FestViewHolder )holder).festView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap <String,String> dandeval1= mDataset.get(position);
                final String clg_id = dandeval1.get("CLGID");
                String festimage = dandeval1.get("FEST_IMAGE");
                String collegename = dandeval1.get("COLLEGE_NAME");

                mListener.onClick(view, position,clg_id,festimage,collegename,(( FestViewHolder )holder).imageView);
            }
        });

        (( FestViewHolder )holder).festView.setText(Html.fromHtml("<b>" + R.string.view_details + "</b> "));
        (( FestViewHolder )holder).festinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap <String,String> dandeval1= mDataset.get(position);
                final String clg_id = dandeval1.get("CLGID");
                String festimage = dandeval1.get("FEST_IMAGE");
                String collegename = dandeval1.get("COLLEGE_NAME");
                mListener.onClick(view, position,clg_id,festimage,collegename,(( FestViewHolder )holder).imageView);
            }
        });
        (( FestViewHolder )holder).festname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap <String,String> dandeval1= mDataset.get(position);
                final String clg_id = dandeval1.get("CLGID");
                String festimage = dandeval1.get("FEST_IMAGE");
                String collegename = dandeval1.get("COLLEGE_NAME");
                mListener.onClick(view, position,clg_id,festimage,collegename,(( FestViewHolder )holder).imageView);
            }
        });
        (( FestViewHolder )holder).festdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap <String,String> dandeval1= mDataset.get(position);
                final String clg_id = dandeval1.get("CLGID");
                String festimage = dandeval1.get("FEST_IMAGE");
                String collegename = dandeval1.get("COLLEGE_NAME");
                mListener.onClick(view, position,clg_id,festimage,collegename,(( FestViewHolder )holder).imageView);
            }
        });
        (( FestViewHolder )holder).festlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap <String,String> dandeval1= mDataset.get(position);
                final String clg_id = dandeval1.get("CLGID");
                String festimage = dandeval1.get("FEST_IMAGE");
                String collegename = dandeval1.get("COLLEGE_NAME");
                mListener.onClick(view, position,clg_id,festimage,collegename,(( FestViewHolder )holder).imageView);
            }
        });
    }}
    @Override
    public int getItemViewType(int position) {
        if(mDataset.get(position).get("LOAD_CHECK").equals("loaded")){
            return TYPE_FESTS;
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