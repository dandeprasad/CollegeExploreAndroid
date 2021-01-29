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

import java.io.IOException;

import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.Util;

import static collegeexplore.CollegeInfo.R.id.carrerGuidance_img0;


public class homeCarrerGuidanceAdaptor extends RecyclerView.Adapter<homeCarrerGuidanceAdaptor.carrerGuidanceViewHolder> {
    private String[] mDataset;
    private Integer[] imagedata;
    private LruCache<String, Bitmap> mMemoryCache;
    private OnItemClickListener mListener;
    public TextView mTextView;

    Context contextAdaptor;
    String serverUrl;
    Bitmap[] arrayOfBitmaptest;
    carrerGuidanceViewHolder  holderForPast;
    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class carrerGuidanceViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView label;
        ImageView imageView;
        ImageView imageView1;
        public carrerGuidanceViewHolder(View v) {
            super(v);
           // label = (TextView) v.findViewById(R.id.carrer_cardview_text);
            //  mTextView = (TextView) v;
            imageView = v.findViewById(R.id.carrer_cardview_img1);
            imageView1 = v.findViewById(carrerGuidance_img0);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public homeCarrerGuidanceAdaptor(OnItemClickListener listener, Context contextForFragment, Bitmap[] arrayOfBitmap) {
 contextAdaptor = contextForFragment;
        mListener = listener;
        arrayOfBitmaptest=arrayOfBitmap;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public homeCarrerGuidanceAdaptor.carrerGuidanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



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

        if (viewType == 0){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.carrerheader_layout, parent, false);}
        else if (viewType != 0) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.home_carrer_contents, parent, false);

        }

        // set the view's size, margins, paddings and layout parameters

        carrerGuidanceViewHolder vh = new carrerGuidanceViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder( carrerGuidanceViewHolder holder, final int position) {

        // holderForPast = holder;
        //All images to load from server
        //  imageView= (ImageView) xtest.findViewById(carrerGuidance_img0);

        //  imageView.setOnClickListener(image_clicker);

        //calling to test in cache whether images are availabe or not
        try {
            String  server =  Util.getProperty("name", contextAdaptor);
            serverUrl = server.concat("/ImageUploadServlet");

        } catch (IOException e) {
            e.printStackTrace();
        }
       //    loadBitmap(carrerGuidance_img0, imageView);

     //   loadBitmap(carrerGuidance_img0,imageView1);













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
        if (arrayOfBitmaptest!=null){
            if ( arrayOfBitmaptest[0]!=null)
            {
                if(position==0){
                Bitmap x = arrayOfBitmaptest[position];
            holder.imageView1.setImageBitmap(x);}
                if(position!=0){
                    Bitmap x = arrayOfBitmaptest[position];
                    holder.imageView.setImageBitmap(x);}
            }
        }
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
        if(position!=0){
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClick(view, position);
            }
        });}
        if(position==0) {
            holder.imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mListener.onClick(view, position);
                }
            });
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
   /* public int getItemCount() {
        return mDataset.length;
    }*/
    public int getItemCount() {
        return arrayOfBitmaptest.length;}

    @Override
    public int getItemViewType(int position) {

       if (position ==0)
       {
           return 0;
       }
       if(position !=0) {
           return 1;
       }

        return super.getItemViewType(position);
    }





}