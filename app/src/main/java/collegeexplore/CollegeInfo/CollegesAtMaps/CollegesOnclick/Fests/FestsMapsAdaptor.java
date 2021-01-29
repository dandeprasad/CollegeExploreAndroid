package collegeexplore.CollegeInfo.CollegesAtMaps.CollegesOnclick.Fests;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import collegeexplore.CollegeInfo.CollegesAtMaps.NitsNewsNotifications;
import collegeexplore.CollegeInfo.FestsCollegesWorkspace.FestsListofAllNotifications;
import collegeexplore.CollegeInfo.FestsCollegesWorkspace.FestsSlidingImage_Adapter;
import collegeexplore.CollegeInfo.NewsandNotificationsWorkspace.MainHeaderAdsAdaptor;
import collegeexplore.CollegeInfo.R;


public class FestsMapsAdaptor extends RecyclerView.Adapter<FestsMapsAdaptor.carrerGuidanceViewHolder>  {

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
        Button moreinfo;
        private TextView festname,fest_static_name,fest_data_college_website,fest_data_fest_caption,fest_data_fest_name;

        private TextView fest_data_fest_type,fest_data_fest_depart,fest_data_event_dates,fest_data_fest_description;
        public carrerGuidanceViewHolder(View v) {
            super(v);
            // label = (TextView) v.findViewById(R.id.carrer_cardview_text);
            //  mTextView = (TextView) v;

            festname= v.findViewById(R.id.fest_data_view);
            fest_static_name= v.findViewById(R.id.fest_static_name);
            fest_data_college_website= v.findViewById(R.id.fest_data_college_website);
            fest_data_fest_caption= v.findViewById(R.id.fest_data_fest_caption);
            fest_data_fest_name= v.findViewById(R.id.fest_data_fest_name);

            fest_data_fest_type= v.findViewById(R.id.fest_data_fest_type);
            fest_data_fest_depart= v.findViewById(R.id.fest_data_fest_depart);
            fest_data_event_dates= v.findViewById(R.id.fest_data_event_dates);
            fest_data_fest_description= v.findViewById(R.id.fest_data_fest_description);
            moreinfo= v.findViewById(R.id.moreinfo);
    /*        festinfo = (TextView) v.findViewById(R.id.fest_info);
            festdate = (TextView) v.findViewById(R.id.Fest_date);
            festlocation = (TextView) v.findViewById(R.id.Fest_loc);
            festView = (TextView) v.findViewById(R.id.Fest_viewdetails);*/



        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FestsMapsAdaptor(OnItemClickListener listener, Context contextForFragment, Bitmap[] arrayOfBitmap, ArrayList<HashMap<String, String>> stringData) {
        contextAdaptor = contextForFragment;
        mListener = listener;
        arrayOfBitmaptest=arrayOfBitmap;
        stringDataAdaptor=stringData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FestsMapsAdaptor.carrerGuidanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {






        if (viewType ==3) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fests_overview_adaptor, parent, false);

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

        holder.festname.setText(Html.fromHtml(dandeval.get("fest_college_name")));
        holder.fest_data_college_website.setText(Html.fromHtml(dandeval.get("college_website")));
        holder.fest_data_fest_caption.setText(Html.fromHtml(dandeval.get("fest_caption")));
        holder.fest_data_fest_name.setText(Html.fromHtml(dandeval.get("fest_name")));

    holder.fest_data_fest_type.setText(Html.fromHtml(dandeval.get("fest_type")));
    holder.fest_data_fest_depart.setText(Html.fromHtml(dandeval.get("fest_depart")));
    holder.fest_data_event_dates.setText(Html.fromHtml(dandeval.get("start_date")+" - "+dandeval.get("end_date")));
    holder.fest_data_fest_description.setText(Html.fromHtml(dandeval.get("fest_description")));

    holder.moreinfo.setVisibility(View.VISIBLE);




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