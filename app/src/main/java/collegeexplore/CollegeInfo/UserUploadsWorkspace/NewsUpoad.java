package collegeexplore.CollegeInfo.UserUploadsWorkspace;

/**
 * Created by DANDE on 06-02-2018.
 */


import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.Marker;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import collegeexplore.CollegeInfo.AllDetailsDescriptionWorkspace.ShowDetailNewsNotifications;
import collegeexplore.CollegeInfo.LoginManagement.UserLoginManager;
import collegeexplore.CollegeInfo.QuestionsAnswersWorkspace.AnswerQuestion;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.TransitionHelper;
import collegeexplore.CollegeInfo.Util;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;


public class NewsUpoad extends Fragment  {
    String COLLEGEIDJSON,COLLEGE_NAMEJSON;
    View xtest;
    private RecyclerView carrerGridList;
    private GridLayoutManager GridLayoutManager1;
    private static final int SPAN_COUNT = 1;
    Context contextForFragment;
    private LruCache<String, Bitmap> mMemoryCache;
    private ImageButton btnCapturePicture, btnRecordVideo,review_from_galary;
    String jsonurl;
    private ProgressDialog dialog = null;
    public static Bitmap[] arrayOfBitmap= new Bitmap[20];
RatingBar ratingbar,    campusratingBar1,PlacementsratingBar1, EnvironmentratingBar1;
    ArrayList<Uri> imagesUriList;
    ArrayList<String> encodedImageList;
    ArrayList<String> encodedImageList1;
    String imageURI;
    private JSONObject jsonObject;
    EditText  riviewdata,reveiwhead,phnumberedit;
    private TextView messageText, noImage,imagetext;
    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_VIDEO_CAPTURE = 2;
Button button1;
    ArrayList dataToAdaptor = new ArrayList();

    /**
     * used for initiating the variables

     */
    public static NewsUpoad newInstance() {
        NewsUpoad f = new NewsUpoad();


        Bundle args = new Bundle();
   /*     args.putString("COLLEGEID", collegeidToPass);
        args.putString("COLLEGE_NAMEJSON", COLLEGE_NAMEJSON);*/
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

        /*COLLEGEIDJSON = getArguments() != null ? getArguments().getString("COLLEGEID") : "";
        COLLEGE_NAMEJSON = getArguments() != null ? getArguments().getString("COLLEGE_NAMEJSON") : "";*/

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

        return inflater.inflate(R.layout.news_upload, container, false);
    }





    public void onViewCreated(View view, Bundle savedInstanceState) {
        xtest = view;

        Toolbar myToolbar = xtest.findViewById(R.id.PurenewsToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("News Upload");

        // Get a support ActionBar corresponding to this toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //final Toolbar HomeToolbarfrag = (Toolbar) findViewById(R.id.toolbar_collaspe);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
            }

        });








        encodedImageList = new ArrayList<>();
        encodedImageList1 = new ArrayList<>();


        button1=(Button)xtest.findViewById(R.id.button1);
        reveiwhead=(EditText)xtest.findViewById(R.id.reveiwhead);
        riviewdata=(EditText)xtest.findViewById(R.id.riviewdata);
        phnumberedit=(EditText)xtest.findViewById(R.id.phnumberedit);
        noImage  = (TextView)xtest.findViewById(R.id.noImage);
         reveiwhead= xtest.findViewById(R.id.reveiwhead);
         riviewdata= xtest.findViewById(R.id.riviewdata);
        jsonObject = new JSONObject();
        //Performing action on Button Click
        button1.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View arg0) {


                JSONArray jsonArray = new JSONArray();
                JSONArray jsonArray1 = new JSONArray();
                dialog = new ProgressDialog(getContext());
                dialog.setMessage("Please wait...");
                dialog.setCancelable(false);
       /*         if (encodedImageList.isEmpty()){
                    Toast.makeText(getContext(), "Please select some images first.", Toast.LENGTH_SHORT).show();
                    return;
                }*/

String reveiwcpation = reveiwhead.getText().toString();
                String reveiwdesc = riviewdata.getText().toString();
                String dataphno = phnumberedit.getText().toString();
                for (String encoded: encodedImageList){
                    jsonArray.put(encoded);
                }

                for (String encoded: encodedImageList1){
                    jsonArray.put(encoded);
                }
                try {
                    UserLoginManager user = new UserLoginManager(contextForFragment);
                    HashMap userDetail= user.getUserDetails();
                    String usermail=  userDetail.get("email").toString();
                    String username=  userDetail.get("username").toString();

                    jsonObject.put("NEWS_HEADER", reveiwcpation);
                    jsonObject.put("NEWS_DESC", reveiwdesc);
                    jsonObject.put("USER_PHNO", dataphno);

                    jsonObject.put("imageList", jsonArray);

                    jsonObject.put("usermail", usermail);
                    jsonObject.put("username", username);
UserLoginManager dfd = new UserLoginManager(contextForFragment);
                   String UserImage = dfd.getUserImageUrl();
                    jsonObject.put("userimage", UserImage);

                    dialog.show();
                } catch (JSONException e) {
                    Log.e("JSONObject Here", e.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String serverUrl = null;
                try {
                    String  server = Util.getProperty("name", getContext());
                    serverUrl= server.concat("/UserNewsUpload");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, serverUrl, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                Log.e("Message from server", jsonObject.toString());


                                Toast.makeText(getContext(), "Your data uploaded successfully", Toast.LENGTH_LONG).show();
                                Toast.makeText(getContext(), "Thanks for choosing CollegeExplore", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                if (getFragmentManager().getBackStackEntryCount() > 0) {
                                    getFragmentManager().popBackStack();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Message from server", volleyError.toString());
                        Toast.makeText(getContext(), "Error Occurred while uploading the information,please try again", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy( 200*30000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
/*                //Getting the rating and displaying it on the toast
                String rating=String.valueOf(ratingbar.getRating());
                Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();

        try {
            String  server =  Util.getProperty("name", contextForFragment);
            //  serverUrl= server.concat("/ExamsImages");
            jsonurl= server.concat("/CollegesStartServlet");


        String WORKSPACE_ID ="HOME_WORKSPACE";
        String FUNCTION_ID = "USER_RATING_SUBMIT";
        String ACTION_ID = "USER_RATING_SUBMIT";

        new GetStringNewsNotifyTask(WORKSPACE_ID,FUNCTION_ID,ACTION_ID,COLLEGEIDJSON).execute(jsonurl);

        } catch (IOException e) {
            e.printStackTrace();
        }*/
            }

        });


        btnCapturePicture = (ImageButton) xtest.findViewById(R.id.btnCapturePicture);
        btnRecordVideo = (ImageButton)xtest. findViewById(R.id.btnCaptureVideo);
        review_from_galary= (ImageButton)xtest. findViewById(R.id.review_from_galary);

        review_from_galary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose application"), 100);
            }
        });
		/*
		 * Capture image button click event
		 */
        btnCapturePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                dispatchTakePictureIntent();
            }
        });

		/*
		 * Record video button click event
		 */
        btnRecordVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // record video
                dispatchTakeVideoIntent();
            }
        });

        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            getFragmentManager().popBackStack();
        }


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        try {
            // When an Image is picked
            if (requestCode == 100 && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesUriList = new ArrayList<Uri>();
                //encodedImageList.clear();
                if(data.getData()!=null){
                    LinearLayout layout = (LinearLayout)xtest.findViewById(R.id.imageLayout);
                    Uri mImageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = getActivity().getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageURI  = cursor.getString(columnIndex);

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mImageUri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                    String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    encodedImageList.add(encodedImage);
                    cursor.close();


                    ImageView image = new ImageView(getContext());
                    image.setLayoutParams(new ViewGroup.LayoutParams(250,250));
                    image.setMaxHeight(90);
                    image.setMaxWidth(90);
                    image.setImageBitmap(bitmap);
                    // Adds the view to the layout
                    layout.addView(image);


                    cursor.close();

                }else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        LinearLayout layout = (LinearLayout)xtest.findViewById(R.id.imageLayout);
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageURI  = cursor.getString(columnIndex);
                            Bitmap bitmap =    readBitmap(uri);
                          //  Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                            encodedImageList.add(encodedImage);
                            cursor.close();


                            ImageView image = new ImageView(getContext());
                            image.setLayoutParams(new ViewGroup.LayoutParams(120,120));
                            image.setMaxHeight(90);
                            image.setMaxWidth(90);
                            image.setImageBitmap(bitmap);
                            // Adds the view to the layout
                            layout.addView(image);

                        }
                        noImage.setText("Selected Images: " + mArrayUri.size());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && null != data) {
            LinearLayout layout = (LinearLayout)xtest.findViewById(R.id.imageLayout1);
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView image = new ImageView(getContext());
            image.setLayoutParams(new ViewGroup.LayoutParams(120,120));
            image.setMaxHeight(90);
            image.setMaxWidth(90);
            image.setPadding(5,5,5,5);
            image.setImageBitmap(imageBitmap);
            // Adds the view to the layout
            layout.addView(image);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            encodedImageList1.add(encodedImage);
            //  imgPreview.setImageBitmap(imageBitmap);
        }
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            LinearLayout layout = (LinearLayout)xtest.findViewById(R.id.videolayout);
            Uri videoUri = data.getData();
            //mVideoView.setVideoURI(videoUri);
            VideoView video = new VideoView(getContext());
            video.setLayoutParams(new ViewGroup.LayoutParams(120,120));
            video.setPadding(5,5,5,5);
            video.setVideoURI(videoUri);
            layout.addView(video);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
    @SuppressWarnings("unchecked") void transitionTo(Intent i) {
        final Pair<View, String>[] pairs;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pairs = TransitionHelper.createSafeTransitionParticipants(getActivity(), true);

            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);
            //PostQuestion.this.finish();
            startActivity(i, transitionActivityOptions.toBundle());

        }
        else{
            startActivity(i);
            //PostQuestion.this.finish();
        }
    }
    // Read bitmap
    public Bitmap readBitmap(Uri selectedImage) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 5;
        AssetFileDescriptor fileDescriptor =null;
        try {
            fileDescriptor = contextForFragment.getContentResolver().openAssetFileDescriptor(selectedImage,"r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally{
            try {
                bm = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);
                fileDescriptor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bm;
    }
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    private void selectItem(String Id) {

        final String UniqueKey = "ALL_EXAMS_WORKSPACE";

        // Transition for fragment1
        Slide slideTransition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            slideTransition = new Slide(Gravity.LEFT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        }

        // ((HomeActivity)getActivity()).getSupportActionBar(). setHomeAsUpIndicator(R.drawable.back_button);
        Fragment fr =  ShowDetailNewsNotifications.newInstance(Id, UniqueKey);
        fr.setReenterTransition(slideTransition);
        fr.setExitTransition(slideTransition);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            fr.setSharedElementEnterTransition(new ChangeBounds());
        }
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.view_onclick_frame,fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }



    public class GetStringNewsNotifyTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        View objview;
        private String COLLEGEID;
        public GetStringNewsNotifyTask(String WORKSPACE_ID, String FUNCTION_ID, String ACTION_ID) {

        }
        public GetStringNewsNotifyTask(String WorkID,String funtID,String ActID, String COLLEGEIDJSON) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            COLLEGEID=COLLEGEIDJSON;
        }

        @Override
        protected  void onPreExecute()
        {
            // progresdialoglistview=ProgressDialog.show(getActivity(), "","Loading");
            // Log.e("onPreExecutive","called"+progresdialoglistview);
            xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
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
                Record.put( "COLLEGE_ID", COLLEGEID);

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
                xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else {
                if (result != "") {

                    try {
                        JSONObject obj = new JSONObject(ing);
                        Marker marker;
                        int lenghtl = obj.length();
                        //StringData=new  String[8];
                        dataToAdaptor.clear();
                        for (int i = 0; i < lenghtl; i++) {
                            JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));

                            ArrayList valuestosend = new ArrayList();
                            //  jObject = new JSONObject(contents.trim());
                            Iterator<?> keys = clgdata.keys();
                            int j =0;
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();

                                String keydata= clgdata.names().getString(j).toString() ;
                                String valuedata= clgdata.get(clgdata.names().getString(j)).toString();
                                if(keydata.equalsIgnoreCase("CLG_STREAM")){
                                    valuestosend.add(0,valuedata);

                                }
                                else{
                                    valuestosend.add(valuedata);}
                          /*          TextView tv=new TextView(contextForFragment);
                                    tv.setText(valuedata);
                                    linear_depart.addView(tv);*/
                                // depart.addView(tv);
                                j++;
                            }
                            dataToAdaptor.add(valuestosend);


                        }


                        //   LatLng sydney = new LatLng(clgLat,80);
                        //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                }
            }
        }
    }
    public void onClick(View view, int position, String text, String aHolderMainQuestionText) {
        int x = view.getId();
        Intent i = new Intent(getContext(),AnswerQuestion.class);
        i.putExtra("QUES_ID",text);
        i.putExtra("QUESTION",aHolderMainQuestionText);
        transitionTo(i);
        // selectItem(position);
    }
    public void onClick(View view, int position, String text) {
        int x = view.getId();
        Intent i = new Intent(getContext(),AnswerQuestion.class);
        i.putExtra("QUES_ID",text);
        //   transitionToActivity(SharedElementActivity.class, viewHolder, sample);
        // i.putExtra("QUESTION",aHolderMainQuestionText);
        transitionTo(i);
        // selectItem(position);
    }
    private void selectItem(int position) {

        final String UniqueKey = "HOME_HEADER_NEWS";

        // ((HomeActivity)getActivity()).getSupportActionBar(). setHomeAsUpIndicator(R.drawable.back_button);
        Fragment fr =  ShowDetailNewsNotifications.newInstance(position, UniqueKey);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.view_onclick_frame,fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

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
