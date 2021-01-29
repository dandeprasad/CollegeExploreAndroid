package collegeexplore.CollegeInfo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class HomeActivityAdaptor extends RecyclerView.Adapter<HomeActivityAdaptor.ViewHolder> {
    private String[] mDataset;
private Integer[] imagedata;

    private OnItemClickListener mListener;




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
    public HomeActivityAdaptor(String[] maincontents, Integer[] imageId, OnItemClickListener listener) {
        mDataset = maincontents;
        mListener = listener;
        imagedata = imageId;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public HomeActivityAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drawer_list_item, parent, false);

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
        holder.label.setText(mDataset[position]);
        //holder.label.setTextColor(Color.BLACK);
    //    holder.label.setText(mDataset[position]);


       holder.imageView.setImageResource(imagedata[position]);
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
      // holder.itemView.setPadding(8, 8, 8, 8);

       // holder.mTextView.setText(mDataset[position]);
      //  holder.mTextView.setTextColor(Color.BLACK);

        holder.label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClick(view, position);
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClick(view, position);
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