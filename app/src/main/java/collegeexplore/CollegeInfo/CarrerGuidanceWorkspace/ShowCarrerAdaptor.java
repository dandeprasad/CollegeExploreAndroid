package collegeexplore.CollegeInfo.CarrerGuidanceWorkspace;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import collegeexplore.CollegeInfo.R;


public class ShowCarrerAdaptor extends RecyclerView.Adapter<ShowCarrerAdaptor.carrerGuidanceViewHolder> {
    private String[] mDataset;
    private Integer[] imagedata;
    private LruCache<String, Bitmap> mMemoryCache;
    private OnItemClickListener mListener;
    public TextView mTextView;

    Context contextAdaptor;
    String serverUrl;
    Bitmap[] arrayOfBitmaptest;
    carrerGuidanceViewHolder  holderForPast;
    ArrayList<String> detail_string_data1;
    final String UniqueKey = "TOTAL_DATA_CARRERGUIDANCE";

  //  final String UniqueKey = "MAIN_HEADER_ADS";

    public interface OnItemClickListener {
        void onClick(View view, int position, String U);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class carrerGuidanceViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView label;
        ImageView imageView;
        ImageView imageView1;
        private TextView Stringdata;
        public carrerGuidanceViewHolder(View v) {
            super(v);

            Stringdata = v.findViewById(R.id.carrer_guidance_onclicktxt);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ShowCarrerAdaptor(OnItemClickListener listener, Context contextForFragment, ArrayList<String> detail_string_data) {
        contextAdaptor = contextForFragment;
        mListener = listener;

        detail_string_data1=detail_string_data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ShowCarrerAdaptor.carrerGuidanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        View v=null;

        //memory cache management
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };

        // create a new view


            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.carrer_guidance_mainclick, parent, false);



        // set the view's size, margins, paddings and layout parameters

        carrerGuidanceViewHolder vh = new carrerGuidanceViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder( carrerGuidanceViewHolder holder, final int position) {














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

        if (!detail_string_data1.isEmpty()){
            holder.Stringdata.setText(detail_string_data1.get(0));
            holder.Stringdata.setTextSize(15);
        }
        else
            holder.Stringdata.setText("COLLEGE EXPLORE");
        // holder.label.setText(mDataset[position]+'\n');
        // holder.label.setTextColor(Color.BLACK);
        //    holder.label.setText(mDataset[position]);


        //   holder.imageView.setImageResource(imagedata[position]);
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
        // holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        // holder.itemView.setPadding(8, 8, 8, 8);

        // holder.mTextView.setText(mDataset[position]);
        //  holder.mTextView.setTextColor(Color.BLACK);

      /*  holder.label.setOnClickListener(new View.OnClickListener() {
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
        });*/
/*        if(position!=0){
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                  //  mListener.onClick(view, position);
                    mListener.onClick(view, position,UniqueKey);
                }
            });}
        if(position==0) {
            holder.imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onClick(view, position,UniqueKey);
                   // mListener.onClick(view, position);
                }
            });
        }*/
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
   /* public int getItemCount() {
        return mDataset.length;
    }*/
    public int getItemCount() {
        return detail_string_data1.size();}

    @Override
    public int getItemViewType(int position) {


            return 1;

    }





}