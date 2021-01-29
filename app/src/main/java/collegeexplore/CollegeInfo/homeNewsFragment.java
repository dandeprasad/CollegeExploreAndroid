

package collegeexplore.CollegeInfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import static collegeexplore.CollegeInfo.R.id.news_img;
import static collegeexplore.CollegeInfo.R.id.news_img1;
import static collegeexplore.CollegeInfo.R.id.news_img2;

public class homeNewsFragment extends Fragment implements ListOfNitsNews.OnItemClickListener {
    int mNum;
ProgressDialog progresdialoglistview ;
    private RecyclerView newsRecyclerView;
    private String[] maincontents1;
    PopupWindow popupMessage;
    LinearLayout layoutOfPopup;
    TextView popupText;
    View xtest;
    Context contextForFragment;
    private LruCache<String, Bitmap> mMemoryCache;
    String serverUrl = null;

    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;

    /**
     * used for initiating the variables
     */
    public static homeNewsFragment newInstance() {
        homeNewsFragment f = new homeNewsFragment();


        Bundle args = new Bundle();
        args.putInt("num", 1);
        f.setArguments(args);

        return  f;
    }
 //for getting the bundle data in fragments here we are getting the context.
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
         contextForFragment  = context;
    }


    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNum = getArguments() != null ? getArguments().getInt("num") : 1;

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
    };}

    /**
     * This is called soon after oncreate and expects a view return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.home_news_layout, container, false);
    }





    public void onViewCreated(View view, Bundle savedInstanceState) {
        xtest = view;
        Integer[] mThumbIds = {
                R.drawable.back_button, R.drawable.back_button,
                R.drawable.back_button, R.drawable.back_button,
                R.drawable.back_button, R.drawable.back_button,
                R.drawable.back_button, R.drawable.back_button,
                R.drawable.back_button, R.drawable.back_button,
                R.drawable.back_button, R.drawable.back_button
        };





//getting the server ip and port number
        try {
            String  server =  Util.getProperty("name", contextForFragment);
            serverUrl= server.concat("/ImageUploadServlet");

        } catch (IOException e) {
            e.printStackTrace();
        }
        /*Previously used ip addresses*/
        //  String stringUrl ="http://192.168.43.60:9544/ImageUploadServlet";
        // String stringUrl ="http://10.0.2.2:8085/androiddandeCheck/HomeNewsFragmwent";
        //String stringUrl ="http://10.0.2.2:9544/ImageUploadServlet";

      //Loading recycling horizontal view
        LinearLayoutManager newslayoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        newsRecyclerView = xtest.findViewById(R.id.recyclerNewsNits);
        newsRecyclerView.setLayoutManager(newslayoutManager);
        newsRecyclerView.setAdapter(new ListOfNitsNews(maincontents1,mThumbIds, this));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        newsRecyclerView.addItemDecoration(new SpacesItemDecorHomeActivity(spacingInPixels));

       OnClickListener image1_clicker = new OnClickListener(){
            public void onClick(View v){
String x  = "tst";
            }
        };
       //All images to load from server
        imageView= xtest.findViewById(news_img);
        imageView1= xtest.findViewById(news_img1);
         imageView2= xtest.findViewById(news_img2);
        imageView.setOnClickListener(image1_clicker);

        //calling to test in cache whether images are availabe or not
        loadBitmap(news_img1, imageView1);
        loadBitmap(news_img, imageView);
        loadBitmap(news_img2, imageView2);
    }

    //cache memory implementation
        public void loadBitmap(int resId, ImageView imageView) {
            final String imageKey = String.valueOf(resId);

            final Bitmap bitmap = getBitmapFromMemCache(imageKey);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            else {
                new GetDataNewsTask(imageView ,resId).execute(serverUrl);
            }
        }
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }







//async inner class
    public class GetDataNewsTask  extends AsyncTask<String, Void, Bitmap>

    {
        int imageResId;
        private  WeakReference<ImageView> imageViewReference;
        private int data = 0;
        public GetDataNewsTask(ImageView imageView) {


        }



        public GetDataNewsTask(ImageView imageView, int resId) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageResId = resId;
            imageViewReference = new WeakReference<ImageView>(imageView);
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


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write("dandeRequest="+imageResId);
            writer.flush();
            writer.close();
            os.close();

            conn.connect();




            int response = conn.getResponseCode();
            // Log.d(DEBUG_TAG, "The response is: " + response);
            if (response==200) {
                is = conn.getInputStream();
            }

            //for storing image data
            byte[] bytes = IOUtils.toByteArray(is);

            //degrading the image required to pur requirement
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
          BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
            WindowManager wm = (WindowManager) contextForFragment.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int reqWidth = size.x;
            int reqHeight = size.y;

            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight/4);
            options.inJustDecodeBounds = false;
            final Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
            addBitmapToMemoryCache(String.valueOf(imageResId), bitmap1);
            return bitmap1;



        }



        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Bitmap result) {
            Bitmap ing = result;
            // value.checkImageshow(ing);
            // ImageView  imageView=(ImageView)findViewById(R.id.imageView4);
            //  textView.setText(result);

            if (imageViewReference != null && result != null) {

                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(result);
                }
            }


           /* ImageView imageView= (ImageView) xtest.findViewById(R.id.news_img);
            imageView.setImageBitmap(ing);
            ImageView imageView1= (ImageView) xtest.findViewById(news_img1);
            imageView1.setImageBitmap(ing);
            ImageView imageView2= (ImageView) xtest.findViewById(news_img2);
            imageView2.setImageBitmap(ing);*/
           // imageView.setMaxWidth(400);


          //  progresdialoglistview.dismiss();
           // imageView.setLayoutParams(new LinearLayout.LayoutParams(200,500));
        }


    }
    public void onClick(View view, int position,String UniqueKey) {

        selectItem(position);
    }
    private void selectItem(int position) {


        switch (position) {
            case 1:
                Intent intent = new Intent(getActivity(),
                        collegeexplore.CollegeInfo.NitInformation.NitMainContentInfo.class);
                startActivity(intent);
            case 2:
              // ((HomeActivity)getActivity()).getSupportActionBar(). setHomeAsUpIndicator(R.drawable.back_button);
                android.support.v4.app.Fragment fr =  new ShowNewsDetail();

                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.sample_content_fragment,fr);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
        }
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

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

}
