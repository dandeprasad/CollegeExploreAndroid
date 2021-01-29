package collegeexplore.CollegeInfo.NitInformation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.SpacesItemDecorHomeActivity;
import collegeexplore.CollegeInfo.Util;

/**
 * Created by DANDE on 26-07-2016.
 */

public class NitHomeActivity extends Fragment implements ListOfNits.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private String[] maincontents1;
    private String serverUrl;
    ListOfNits nitadaptor;
    View xtest;
    Context contextForFragment;
    List<String> arrayList_CLGID= new ArrayList<>();
    public  Bitmap[] ArrayImages= new Bitmap[31];
    public static NitHomeActivity newInstance() {
        NitHomeActivity f = new NitHomeActivity();


        Bundle args = new Bundle();
        args.putInt("num", 1);
        f.setArguments(args);

        return  f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        contextForFragment  = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.nits_frag_layout, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        xtest = view;
        GridView gridview = xtest.findViewById(R.id.gridview);
        gridview.setAdapter(new NitHomeactivityAdaptor(contextForFragment));
        gridview.setColumnWidth(4);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (position==0){
                    startActivity(new Intent(contextForFragment, CloseOpenRanks.class));
                    Toast.makeText(contextForFragment, "" + "Cut off's",
                            Toast.LENGTH_SHORT).show();
                }
                if (position==3){
                    startActivity(new Intent(contextForFragment, NitsRankings.class));
                    Toast.makeText(contextForFragment, "" + "Nit's Rankings",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
/*
        Integer[] mThumbIds = {
                R.drawable.nit_ag, R.drawable.nit_allahabad, R.drawable.nit_andhra,
                R.drawable.nit_ar_prdsh, R.drawable.nit_bhopal, R.drawable.nit_ca,
                R.drawable.nit_del, R.drawable.nit_dgpr, R.drawable.nit_goa,
                R.drawable.nit_hampr, R.drawable.nit_jaipur, R.drawable.nit_jal,
                R.drawable.nit_jamspr, R.drawable.nit_kuru, R.drawable.nit_mani,
                R.drawable.nit_mega, R.drawable.nit_miz, R.drawable.nit_naglnd,
                R.drawable.nit_nagpur, R.drawable.nit_patna, R.drawable.nit_py,
                R.drawable.nit_raipur, R.drawable.nit_rourk, R.drawable.nit_sikkim,
                R.drawable.nit_silchar, R.drawable.nit_sringr, R.drawable.nit_surat,
                R.drawable.nit_surutkal, R.drawable.nit_trichy, R.drawable.nit_uttarak,
                R.drawable.nit_wangl

        };*/
        arrayList_CLGID.add("NIT_AG");
        arrayList_CLGID.add("NIT_ALLAHABAD");
        arrayList_CLGID.add("NIT_ANDHRA");
        arrayList_CLGID.add("NIT_AR_PRDSH");
        arrayList_CLGID.add("NIT_BHOPAL");
        arrayList_CLGID.add("NIT_CA");
        arrayList_CLGID.add("NIT_DEL");
        arrayList_CLGID.add("NIT_DGPR");
        arrayList_CLGID.add("NIT_GOA");
        arrayList_CLGID.add("NIT_HAMPR");
        arrayList_CLGID.add("NIT_JAIPUR");
        arrayList_CLGID.add("NIT_JAL");
        arrayList_CLGID.add("NIT_JAMSPR");
        arrayList_CLGID.add("NIT_KURU");
        arrayList_CLGID.add("NIT_MANI");
        arrayList_CLGID.add("NIT_MEGA");
        arrayList_CLGID.add("NIT_MIZ");
        arrayList_CLGID.add("NIT_NAGLND");
        arrayList_CLGID.add("NIT_NAGPUR");
        arrayList_CLGID.add("NIT_PATNA");
        arrayList_CLGID.add("NIT_PY");
        arrayList_CLGID.add("NIT_RAIPUR");
        arrayList_CLGID.add("NIT_ROURK");
        arrayList_CLGID.add("NIT_SIKKIM");
        arrayList_CLGID.add("NIT_SILCHAR");
        arrayList_CLGID.add("NIT_SRINGR");
        arrayList_CLGID.add("NIT_SURAT");
        arrayList_CLGID.add("NIT_SURUTKAL");
        arrayList_CLGID.add("NIT_TRICHY");
        arrayList_CLGID.add("NIT_UTTARAK");
        arrayList_CLGID.add("NIT_WANGL");
        maincontents1 = getResources().getStringArray(R.array.Navigation_data_icons);
        LinearLayoutManager layoutManager= new LinearLayoutManager(contextForFragment, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView = xtest.findViewById(R.id.recyclerViewNits);
        mRecyclerView.setLayoutManager(layoutManager);
      nitadaptor =new ListOfNits(maincontents1,ArrayImages, this,contextForFragment,arrayList_CLGID);

        mRecyclerView.setAdapter(nitadaptor);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);

        mRecyclerView.addItemDecoration(new SpacesItemDecorHomeActivity(spacingInPixels));

        String WORKSPACE_ID = "HOME_WORKSPACE";
        String FUNCTION_ID = "NIT_LOGOS_DATA";
        String ACTION_ID = "GET_NIT_LOGOS_DATA";
        try {
            String  server =  Util.getProperty("name", contextForFragment);
            serverUrl= server.concat("/NitLogosData");

        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i =0;i<=30;i++)
        {
            new AllNotifications(i,WORKSPACE_ID, FUNCTION_ID, ACTION_ID).execute(serverUrl);

        }
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

        public AllNotifications(int position ,String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            imagetest = position;

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
                WindowManager wm = (WindowManager) contextForFragment.getSystemService(Context.WINDOW_SERVICE);
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


                    ArrayImages[imagetest]=result;
                    if(imagetest==30)
                        nitadaptor.notifyDataSetChanged();







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
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        // Handle action buttons
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onClick(View view, int position) {

        selectItem(position);
    }
    private void selectItem(int position) {

        switch (position) {
            case 1:
                Intent intent = new Intent(contextForFragment,
                        NitMainContentInfo.class);
                startActivity(intent);


        }
    }
}
