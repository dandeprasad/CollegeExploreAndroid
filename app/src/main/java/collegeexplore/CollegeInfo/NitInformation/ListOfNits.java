package collegeexplore.CollegeInfo.NitInformation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import collegeexplore.CollegeInfo.CollegesAtMaps.homeMapsActivity;
import collegeexplore.CollegeInfo.R;


public class ListOfNits extends RecyclerView.Adapter<ListOfNits.ViewHolder> {
    private String[] mDataset;
    private Bitmap[] imagedata;
    private TransitionDrawable td1;
    private OnItemClickListener mListener;
    private Context contextNits;
    List<String> mData_clg_id;


    public interface OnItemClickListener {
        void onClick(View view, int position);
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
            label = v.findViewById(R.id.dandeking);
            //  mTextView = (TextView) v;
            imageView = v.findViewById(R.id.img);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListOfNits(String[] maincontents, Bitmap[] imageId, NitHomeActivity listener, Context nitHomeActivity, List<String> arrayList_CLGID) {
        mDataset = maincontents;
        mListener = listener;
        imagedata = imageId;
        contextNits=nitHomeActivity;
        mData_clg_id=arrayList_CLGID;}

    // Create new views (invoked by the layout manager)
    @Override
    public ListOfNits.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nits_linear_items, parent, false);

        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder1, final int position) {



        holder1.imageView.setImageBitmap(imagedata[position]);
        holder1.imageView.setTag(mData_clg_id.get(position));



        holder1.label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClick(view, position);
            }
        });

        holder1.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contextNits,
                        homeMapsActivity.class);
                intent.putExtra("collegeID",(String) holder1.imageView.getTag());
                // intent.putExtra("collegeName",mData.get(position));
                intent.putExtra("FROM_ACTIVITY","SEARCH_ACTIVITY");
                contextNits.startActivity(intent);
                //mListener.onClick(view, position);
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