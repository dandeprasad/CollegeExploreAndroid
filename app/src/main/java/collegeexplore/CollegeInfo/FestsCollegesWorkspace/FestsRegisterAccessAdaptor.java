package collegeexplore.CollegeInfo.FestsCollegesWorkspace;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import collegeexplore.CollegeInfo.CollegesAtMaps.NitsNewsNotifications;
import collegeexplore.CollegeInfo.NewsandNotificationsWorkspace.MainHeaderAdsAdaptor;
import collegeexplore.CollegeInfo.R;


public class FestsRegisterAccessAdaptor extends RecyclerView.Adapter<FestsRegisterAccessAdaptor.carrerGuidanceViewHolder>  {

    private OnItemClickListener mListener;
    public TextView mTextView;
    private String[] maincontents1;
    private FestsListofAllNotifications ListofAllNotificationsAdaptor;
    private MainHeaderAdsAdaptor MainAdsAdaptor;
    private NitsNewsNotifications nitsNewsNotifications;
    Context contextAdaptor;
    String serverUrl;
    Bitmap[] arrayOfBitmaptest;
    FestsSlidingImage_Adapter viewpageAdaptor;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
  //  private static final Integer[] IMAGES= {R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.five};
  public  ArrayList<HashMap> ImagesArray=new ArrayList<HashMap>();
    //public  Bitmap[] arrayForAllNotifications= new Bitmap[8];
    public  Bitmap[] ArrayListdata= new Bitmap[10];

    ArrayList<HashMap<String, String>> stringDataAdaptor;
    View v=null;


    public interface OnItemClickListener {
        void onClick(View view, int position, String clg_id);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class carrerGuidanceViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView label;

        private TextView fest_data_regis_url,fest_data_Regis_fees,fest_data_dead_registration,fest_data_sponsors,fest_data_guest;
        public carrerGuidanceViewHolder(View v) {
            super(v);
            // label = (TextView) v.findViewById(R.id.carrer_cardview_text);
            //  mTextView = (TextView) v;

            fest_data_regis_url= v.findViewById(R.id.fest_data_regis_url);
            fest_data_Regis_fees= v.findViewById(R.id.fest_data_Regis_fees);
            fest_data_dead_registration= v.findViewById(R.id.fest_data_dead_registration);
            fest_data_sponsors= v.findViewById(R.id.fest_data_sponsors);
            fest_data_guest= v.findViewById(R.id.fest_data_guest);

    /*        festinfo = (TextView) v.findViewById(R.id.fest_info);
            festdate = (TextView) v.findViewById(R.id.Fest_date);
            festlocation = (TextView) v.findViewById(R.id.Fest_loc);
            festView = (TextView) v.findViewById(R.id.Fest_viewdetails);*/



        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FestsRegisterAccessAdaptor(OnItemClickListener listener, Context contextForFragment, Bitmap[] arrayOfBitmap, ArrayList<HashMap<String, String>> stringData) {
        contextAdaptor = contextForFragment;
        mListener = listener;
        arrayOfBitmaptest=arrayOfBitmap;
        stringDataAdaptor=stringData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FestsRegisterAccessAdaptor.carrerGuidanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {






        if (viewType ==3) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fests_regis_access_adaptor, parent, false);

        }



/*        for(int i =0;i<=4;i++)
        {
            new AllNotifications(i,linearLayoutID,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);
           *//* ImagesArray[i] = BitmapFactory.decodeResource(contextAdaptor.getResources(),
                    R.drawable.load_mask);*//*
        }*/


           /*if(pos>1){
            SharedPreferences prefs = contextAdaptor.getSharedPreferences(PREFS_NAME, 0);
            String pathtoget =prefs.getString("imagepath"+Integer.toString(i),null);
            loadImageFromStorage( pathtoget,Integer.toString(i));}
           else {*/


        // set the view's size, margins, paddings and layout parameters

        carrerGuidanceViewHolder vh = new carrerGuidanceViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder( carrerGuidanceViewHolder holder, final int position) {

       // adapter.setActiveUserPosition(position);
        // holderForPast = holder;
        //All images to load from server
        //  imageView= (ImageView) xtest.findViewById(carrerGuidance_img0);

        //  imageView.setOnClickListener(image_clicker);


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


if (!(stringDataAdaptor.isEmpty())){



   HashMap <String,String> dandeval= stringDataAdaptor.get(position);




  //  String sourceString0 = dandeval.get("fest_college_name");


   // String sourceString3 = "<b>" + "Fest Info: " + "</b> " + dandeval.get("CLG_FEST_INFO");

    // mytextview.setText(Html.fromHtml(sourceString));


    holder.fest_data_regis_url.setText(Html.fromHtml(dandeval.get("fest_regis_url")));
    holder.fest_data_Regis_fees.setText(Html.fromHtml(dandeval.get("fest_Regis_fees")));
    holder.fest_data_dead_registration.setText(Html.fromHtml(dandeval.get("fest_dead_registration")));
    holder.fest_data_sponsors.setText(Html.fromHtml(dandeval.get("fest_sponsors")));
    holder.fest_data_guest.setText(Html.fromHtml(dandeval.get("fest_guest")));





/*
    Picasso.with(contextAdaptor).load(dandeval.get("STREAM_IMAGE")).into( holder.imageView);*/
}
/*        if (arrayOfBitmaptest!=null){
            *//*if ( arrayOfBitmaptest[0]!=null)
            {
                if(position==1){
                    Bitmap x = arrayOfBitmaptest[position-1];
                    holder.imageView1.setImageBitmap(x);
                }}*//*
               *//* else if(position==1) {
                    LinearLayoutManager newslayoutManager = new LinearLayoutManager(contextAdaptor, LinearLayoutManager.HORIZONTAL, false);

                    holder.newsRecyclerView.setLayoutManager(newslayoutManager);
                    holder.newsRecyclerView.setAdapter(new ListOfNitsNews(maincontents1, mThumbIds,  this));
                    int spacingInPixels = contextAdaptor.getResources().getDimensionPixelSize(R.dimen.spacing);
                    holder.newsRecyclerView.addItemDecoration(new SpacesItemDecorHomeActivity(spacingInPixels));
                }*//*
 *//*               if ( arrayOfBitmaptest[5]!=null)
                {
                if(position>=4 &&position<=12 ){
                    Bitmap x = arrayOfBitmaptest[position-4];
                    holder.imageView.setImageBitmap(x);}

            }*//*

        }*/



   /*         holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap <String,String> dandeval1= stringDataAdaptor.get(position);
                    final String clg_id = dandeval1.get("STREAM_ID");
                    mListener.onClick(view, position,clg_id);
                }
            });*/

      /*      holder.festname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap <String,String> dandeval1= stringDataAdaptor.get(position);
                    final String clg_id = dandeval1.get("CLGID");
                    mListener.onClick(view, position-4,clg_id);
                }
            });*/

        holder.fest_data_regis_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap <String,String> dandeval1= stringDataAdaptor.get(position);
                String url =dandeval1.get("fest_regis_url");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                Toast.makeText(contextAdaptor,"You are redirecting to fest page of particular college" , Toast.LENGTH_LONG).show();
                contextAdaptor.startActivity(i);
            }
/*
    Picasso.with(contextAdaptor).load(dandeval.get("STREAM_IMAGE")).into( holder.imageView);*/
        });
        }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
   /* public int getItemCount() {
        return mDataset.length;
    }*/
    public int getItemCount() {
        return stringDataAdaptor.size();}

    @Override
    public int getItemViewType(int position) {

        if(true) {
            return 3;
        }


        return super.getItemViewType(position);
    }







}