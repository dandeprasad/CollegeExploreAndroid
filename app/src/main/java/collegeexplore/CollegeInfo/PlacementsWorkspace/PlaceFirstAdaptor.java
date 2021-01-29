package collegeexplore.CollegeInfo.PlacementsWorkspace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import collegeexplore.CollegeInfo.AllDetailsDescriptionWorkspace.ShowDetailNewsNotifications;
import collegeexplore.CollegeInfo.CollegesAtMaps.NitsNewsNotifications;
import collegeexplore.CollegeInfo.FestsCollegesWorkspace.FestsListofAllNotifications;
import collegeexplore.CollegeInfo.FestsCollegesWorkspace.FestsSlidingImage_Adapter;
import collegeexplore.CollegeInfo.HomeActivity;
import collegeexplore.CollegeInfo.ListOfNitsNews;
import collegeexplore.CollegeInfo.NewsandNotificationsWorkspace.MainHeaderAdsAdaptor;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.UtilProperty;


public class PlaceFirstAdaptor extends RecyclerView.Adapter<PlaceFirstAdaptor.carrerGuidanceViewHolder> implements ListOfNitsNews.OnItemClickListener,MainHeaderAdsAdaptor.OnItemClickListener {

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
        ImageView imageView;
        private TextView festname;


        public carrerGuidanceViewHolder(View v) {
            super(v);
            // label = (TextView) v.findViewById(R.id.carrer_cardview_text);
            //  mTextView = (TextView) v;
            imageView = v.findViewById(R.id.shownewsnotifydetail);
            festname= v.findViewById(R.id.clg_name);
    /*        festinfo = (TextView) v.findViewById(R.id.fest_info);
            festdate = (TextView) v.findViewById(R.id.Fest_date);
            festlocation = (TextView) v.findViewById(R.id.Fest_loc);
            festView = (TextView) v.findViewById(R.id.Fest_viewdetails);*/



        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PlaceFirstAdaptor(OnItemClickListener listener, Context contextForFragment, Bitmap[] arrayOfBitmap, ArrayList<HashMap<String, String>> stringData) {
        contextAdaptor = contextForFragment;
        mListener = listener;
        arrayOfBitmaptest=arrayOfBitmap;
        stringDataAdaptor=stringData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PlaceFirstAdaptor.carrerGuidanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {






        if (viewType ==3) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cutoff_datacontent, parent, false);

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




    String sourceString0 = "<b>" + dandeval.get("STREAM") + "</b> ";


   // String sourceString3 = "<b>" + "Fest Info: " + "</b> " + dandeval.get("CLG_FEST_INFO");

    // mytextview.setText(Html.fromHtml(sourceString));


    holder.festname.setText(Html.fromHtml(sourceString0));

    String  imageserver = null;
    try {
        imageserver =  UtilProperty.getProperty("img_CutoffFirstAdaptor", contextAdaptor,"imagepath.properties");
    } catch (IOException e) {
        e.printStackTrace();
    }
    Picasso.with(contextAdaptor).load(dandeval.get("STREAM_IMAGE")).resize(600, 200).into( holder.imageView);
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



            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap <String,String> dandeval1= stringDataAdaptor.get(position);
                    final String clg_id = dandeval1.get("STREAM_ID");
                    mListener.onClick(view, position,clg_id);
                }
            });

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




    public class AllNotifications  extends AsyncTask<String, Void, Bitmap>

    {
        int imageResId;
        int imagetest;
        private WeakReference<Bitmap> imageViewReference;
        private int data = 0;
        private String layoutid=null;
               private String functionID;
            private String ActionID;
            private String WorkspaceID;

            public AllNotifications(int position ,String layoutID,String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            imagetest = position;
                layoutid=layoutID;
        }

        public AllNotifications(ImageView imageView, int resId) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageResId = resId;

        }


        @Override
        protected  void onPreExecute()
        {
            // progresdialoglistview=ProgressDialog.show(getActivity(), "","Loading");
            // Log.e("onPreExecutive","called"+progresdialoglistview);

        }



        @Override
        protected Bitmap doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.

            Bitmap xy = null;
            try {
                xy = downloadUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return xy;
        }


        private Bitmap downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;
            String contentAsString=null;
            Bitmap bitmap=null;

        /* for get request
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 );
            conn.setConnectTimeout(15000 );
            conn.setRequestMethod("POST");
        conn.setRequestProperty("dande", "fdfdf");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();   */
            //for post request
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JSONObject json = new JSONObject();

            JSONObject Record = new JSONObject();
            try {
                Record.put( "ACTION_ID", ActionID);
                Record.put( "FUNCTION_ID", functionID);
                Record.put( "WORKSPACE_ID", WorkspaceID);
                Record.put( "POSITION_NO", imagetest);
                json.put( "datatohost", Record);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write("ServerData="+json.toString());

            writer.flush();
            writer.close();
            os.close();

            conn.connect();


            int response = conn.getResponseCode();
            // Log.d(DEBUG_TAG, "The response is: " + response);
            if (response==200) {
                is = conn.getInputStream();
            }
             Bitmap bitmap1 = null;
            if(is!=null) {
                //for storing image data
                byte[] bytes = IOUtils.toByteArray(is);

                //degrading the image required to pur requirement
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                WindowManager wm = (WindowManager) contextAdaptor.getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int reqWidth = size.x;
                int reqHeight = size.y;

                options.inSampleSize = calculateInSampleSize(options, reqWidth / 4, reqHeight / 4);
                options.inJustDecodeBounds = false;
                bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                // addBitmapToMemoryCache(String.valueOf(imageResId), bitmap1);
            }
                return bitmap1;



        }



        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Bitmap result) {
            imageViewReference = new WeakReference<Bitmap>(result);
if(result!=null){
/*            if (layoutid.equalsIgnoreCase("ListofAllNotificationsID")) {
                arrayForAllNotifications[imagetest] = result;
if(imagetest==7)
                    ListofAllNotificationsAdaptor.notifyDataSetChanged();



            }*/
            if (layoutid.equalsIgnoreCase("nitsNewsNotificationsID")) {
                ArrayListdata[imagetest]=result;
                if(imagetest==7)
                    nitsNewsNotifications.notifyDataSetChanged();

            }
/*                if (layoutid.equalsIgnoreCase("LinearMainAdsID")) {

                    Drawable d = new BitmapDrawable(contextAdaptor.getResources(), result);
                    //IMAGES[imagetest]=d;
                    ImagesArray[imagetest]=result;
                    if (imagetest == 3) {
                        if(viewpageAdaptor!=null)
                        viewpageAdaptor.notifyDataSetChanged();
                    }
                    Bitmap ing = result;




                }*/




}
        }

    }
    public class GetStringNewsNotifyTask1  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        View objview;
        private String COLLEGEID;

        public GetStringNewsNotifyTask1(String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;

        }

        @Override
        protected  void onPreExecute()
        {
            // progresdialoglistview=ProgressDialog.show(getActivity(), "","Loading");
            // Log.e("onPreExecutive","called"+progresdialoglistview);

        }
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                // int x = positionCheck;
                String xy = downloadUrl(urls[0]);
                return xy;
            } catch (IOException e) {
                return "UNABLE_TO_REACH_WEB";
            }}



        // Given a URL, establishes an HttpUrlConnection and retrieves
// the web page content as a InputStream, which it returns as
// a string.
        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 50000;
            String contentAsString=null;
            Bitmap bitmap=null;

            //for post request

            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JSONObject json = new JSONObject();

            JSONObject Record = new JSONObject();
            try {
                Record.put( "ACTION_ID", ActionID);
                Record.put( "FUNCTION_ID", functionID);
                Record.put( "WORKSPACE_ID", WorkspaceID);
                Record.put( "POSITION_NO", 1);

                json.put( "datatohost", Record);
            } catch (JSONException e) {
                e.printStackTrace();
            }



            // P toString()
            //  System.out.println( "JSON: " + json.toString() );


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write("ServerData="+json.toString());

            writer.flush();
            writer.close();
            os.close();

            conn.connect();


            int response = conn.getResponseCode();
            if (response==200) {
                is = conn.getInputStream();
            }

            if(is!=null) {

                // Convert the InputStream into a string
                contentAsString = readIt(is, len);
                is.close();

            }

            return contentAsString;

        }


        public String readIt(InputStream stream, int len) throws IOException {

            String myString = IOUtils.toString(stream, "UTF-8");
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[5000];
            reader.read(buffer);
            return myString;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            String ing = result;
            if (result == null) {
                result="";}
            if(result.equalsIgnoreCase("UNABLE_TO_REACH_WEB"))
            {

            }
            else {
                if (result != "") {

                    try {
                        JSONObject obj = new JSONObject(ing);
                        Marker marker;
                        int lenghtl = obj.length();
                        //StringData=new  String[8];
                        for (int i = 0; i < lenghtl; i++) {
                            JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));
                            HashMap<String,String>hashmap= new HashMap();
                            String NOTIFYS_ID = (String) clgdata.get("NOTIFYS_ID");
                            String NOTIFY_HEADER = (String) clgdata.get("NOTIFY_HEADER");
                            String NOTIFY_DETAILS = (String) clgdata.get("NOTIFY_DETAILS");
                            String NOTIFY_IMG = (String) clgdata.get("NOTIFY_IMG");
                            hashmap.put("NOTIFYS_ID",NOTIFYS_ID);
                            hashmap.put("NOTIFY_HEADER",NOTIFY_HEADER);
                            hashmap.put("NOTIFY_DETAILS",NOTIFY_DETAILS);
                            hashmap.put("NOTIFY_IMG",NOTIFY_IMG);
                            ImagesArray.add(hashmap);
                         /*   data.setText("College Name: "+festinfo);
                            String  imageserver = null;
                            try {
                                imageserver =  UtilProperty.getProperty("img_ClgFestFragment", contextForFragment,"imagepath.properties");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Picasso.with(contextForFragment).load(imageserver+imageinfo).into(data1);
*/

                        }
                        viewpageAdaptor.notifyDataSetChanged();


                        //   LatLng sydney = new LatLng(clgLat,80);
                        //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 2;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    @Override
    public void onClick(View view, int position,String UniqueKey) {
       // selectItem(position,UniqueKey);
    }
    private void selectItem(int position,String UniqueKey) {



        // ((HomeActivity)getActivity()).getSupportActionBar(). setHomeAsUpIndicator(R.drawable.back_button);
        android.support.v4.app.Fragment fr =  ShowDetailNewsNotifications.newInstance(position,UniqueKey);

        android.support.v4.app.FragmentManager fm = ((HomeActivity)contextAdaptor).getSupportFragmentManager();
       // FragmentManager fm12 = contextAdaptor.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.sample_content_fragment,fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}