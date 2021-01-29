package collegeexplore.CollegeInfo.FestsCollegesWorkspace;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.Marker;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.Util;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.GraphRequest.TAG;

/**
 * Created by DANDE on 04-06-2017.
 */

public class FestsRegistration extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {


    public Activity c;

    Context contextForFragment;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 125;
    View  xtest;
    public Button registerButton, submit;
    public EditText fest_college_name,college_location,user_password,college_website,end_date,OTP_VALUE,start_date;
    String jsonurl;
    String[] SPINNERLIST = {"Android Material Design", "Material Design Spinner", "Spinner Using Material Library", "Material Spinner Example"};


    private TextView messageText, noImage,imagetext;
    private Button uploadButton;
    private ImageButton btnselectpic;
    private EditText etxtUpload;
    private ProgressDialog dialog = null;
    LinearLayout layoutfirst ,layoutsecond ,layoutthird, layoutfourth;
    private JSONObject jsonObject;
    ArrayList<Uri> imagesUriList;
    ArrayList<String> encodedImageList;
    ArrayList<String> encodedImageList1;
    String imageURI;
    private EditText fest_name,fest_caption,fest_email_add,fest_description,fest_depart,fest_event,fest_workshop;

    private EditText  fest_paper,fest_guest,fest_sponsors,fest_dead_registration,fest_Regis_fees,fest_website,fest_regis_url,fest_links,fest_reach,Fest_contact_persons;

    private Spinner planets_spinner;
    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_VIDEO_CAPTURE = 2;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";

    private Uri fileUri; // file url to store image/video

    private ImageView imgPreview;
    private VideoView mVideoView;
    private ImageButton btnCapturePicture, btnRecordVideo;
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
         contextForFragment = context;
    }
    public static FestsRegistration newInstance() {
        FestsRegistration f = new FestsRegistration();


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fests_register, container, false);
    }
    public void onViewCreated(final View view, Bundle savedInstanceState) {
         xtest = view;
        {
            super.onCreate(savedInstanceState);
            //  requestWindowFeature(Window.FEATURE_NO_TITLE);
            //  setContentView(R.layout.fests_register);
            //getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

          //  user_password= (EditText) xtest.findViewById(R.id.user_password);
/*            view.findViewById(R.id.user_password).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar now = Calendar.getInstance();
                    new android.app.DatePickerDialog(
                            getActivity(),
                            new android.app.DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    Log.d("Orignal", "Got clicked");
                                }
                            },
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    ).show();
                }
            });*/
         //   imgPreview = (ImageView) xtest.findViewById(R.id.imgPreview);
          //  mVideoView = (VideoView) xtest.findViewById(R.id.videoPreview);
            btnCapturePicture = (ImageButton) xtest.findViewById(R.id.btnCapturePicture);
           btnRecordVideo = (ImageButton)xtest. findViewById(R.id.btnCaptureVideo);


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



//change listeners start
            fest_college_name=  xtest.findViewById(R.id.fest_college_name);
            college_website= xtest.findViewById(R.id.college_website);
            college_location= xtest.findViewById(R.id.college_location);
            fest_name= xtest.findViewById(R.id.fest_name);
            fest_caption=xtest.findViewById(R.id.fest_caption);
            planets_spinner=xtest.findViewById(R.id.planets_spinner);
            fest_email_add=xtest.findViewById(R.id.fest_email_add);
            fest_description=xtest.findViewById(R.id.fest_description);
            fest_depart=xtest.findViewById(R.id.fest_depart);
            start_date=xtest.findViewById(R.id.start_date);
            end_date=xtest.findViewById(R.id.end_date);
            fest_event=xtest.findViewById(R.id.fest_event);
            fest_workshop=xtest.findViewById(R.id.fest_workshop);
            fest_paper=xtest.findViewById(R.id.fest_paper);
            fest_guest=xtest.findViewById(R.id.fest_guest);
            fest_sponsors=xtest.findViewById(R.id.fest_sponsors);
            fest_dead_registration=xtest.findViewById(R.id.fest_dead_registration);
            fest_Regis_fees=xtest.findViewById(R.id.fest_Regis_fees);
            fest_website=xtest.findViewById(R.id.fest_website);
            fest_regis_url=xtest.findViewById(R.id.fest_regis_url);
            fest_links=xtest.findViewById(R.id.fest_links);
            fest_reach=xtest.findViewById(R.id.fest_reach);
            Fest_contact_persons=xtest.findViewById(R.id.Fest_contact_persons);

           xtest.findViewById(R.id.fest_college_name).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        //  Toast.makeText(getApplicationContext(), "unfocus", 2000).show();
                        try {
                            if(Util.Nullcheck(fest_college_name.getText().toString()).equalsIgnoreCase(""))
                            fest_college_name.setError("Enter a Valid College Name");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            college_website.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        //  Toast.makeText(getApplicationContext(), "unfocus", 2000).show();
                        try {
                            if(Util.Nullcheck(college_website.getText().toString()).equalsIgnoreCase(""))
                                college_website.setError("Enter a Valid email Id");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            xtest.findViewById(R.id.college_location).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        //  Toast.makeText(getApplicationContext(), "unfocus", 2000).show();
                        try {
                            if(Util.Nullcheck(college_website.getText().toString()).equalsIgnoreCase(""))
                                college_location.setError("Enter a Valid Location");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            xtest.findViewById(R.id.fest_name).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        //  Toast.makeText(getApplicationContext(), "unfocus", 2000).show();
                        try {
                            if(Util.Nullcheck(college_website.getText().toString()).equalsIgnoreCase(""))
                                college_website.setError("Enter a Valid email Id");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            xtest.findViewById(R.id.college_website).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        //  Toast.makeText(getApplicationContext(), "unfocus", 2000).show();
                        try {
                            if(Util.Nullcheck(college_website.getText().toString()).equalsIgnoreCase(""))
                                college_website.setError("Enter a Valid email Id");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            //change listeners end


             layoutfirst = (LinearLayout)xtest.findViewById(R.id.linearlay_first);
             layoutsecond = (LinearLayout)xtest.findViewById(R.id.linearlay_second);
             layoutthird = (LinearLayout)xtest.findViewById(R.id.linearlay_third);
             layoutfourth = (LinearLayout)xtest.findViewById(R.id.linearlay_fourth);

           Button button_first = (Button)xtest.findViewById(R.id.button_first);
            button_first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutfirst.setVisibility(View.GONE);
                    layoutsecond.setVisibility(View.VISIBLE);

                }});
            Button button_back_second = (Button)xtest.findViewById(R.id.button_back_second);
            button_back_second.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutfirst.setVisibility(View.VISIBLE);
                    layoutsecond.setVisibility(View.GONE);

                }});

            Button button_next_second = (Button)xtest.findViewById(R.id.button_next_second);
            button_next_second.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutthird.setVisibility(View.VISIBLE);
                    layoutsecond.setVisibility(View.GONE);

                }});

            Button button_back_third = (Button)xtest.findViewById(R.id.button_back_third);
            button_back_third.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutthird.setVisibility(View.GONE);
                    layoutsecond.setVisibility(View.VISIBLE);

                }});

            Button button_next_third = (Button)xtest.findViewById(R.id.button_next_third);
            button_next_third.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutthird.setVisibility(View.GONE);
                    layoutfourth.setVisibility(View.VISIBLE);

                }});

            Button button_back_fourth = (Button)xtest.findViewById(R.id.button_back_fourth);
            button_back_fourth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutthird.setVisibility(View.VISIBLE);
                    layoutfourth.setVisibility(View.GONE);

                }});

            Button button_next_fourth = (Button)xtest.findViewById(R.id.button_next_fourth);
            button_next_fourth.setOnClickListener(this);











            uploadButton = (Button)xtest.findViewById(R.id.uploadButton);
            btnselectpic = (ImageButton)xtest.findViewById(R.id.button_selectpic);
            messageText  = (TextView)xtest.findViewById(R.id.messageText);
            noImage  = (TextView)xtest.findViewById(R.id.noImage);
            etxtUpload = (EditText)xtest.findViewById(R.id.etxtUpload);
            imagetext = (TextView)xtest.findViewById(R.id.imagetext);
            imagetext.setOnClickListener(this);
            btnselectpic.setOnClickListener(this);
            uploadButton.setOnClickListener(this);

            dialog = new ProgressDialog(getContext());
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);

            jsonObject = new JSONObject();
            encodedImageList = new ArrayList<>();
            encodedImageList1 = new ArrayList<>();





            Spinner spinner = (Spinner) xtest.findViewById(R.id.planets_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.fest_types, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            spinner.setAdapter(adapter);
            start_date = (EditText) xtest.findViewById(R.id.start_date);
            end_date = (EditText) xtest.findViewById(R.id.end_date);
            start_date.setOnFocusChangeListener(new View.OnFocusChangeListener(){

                @Override
                public void onFocusChange(View view, boolean hasfocus) {
                    if(hasfocus){
                        try {
                            Calendar now = Calendar.getInstance();
                            new android.app.DatePickerDialog(
                                    getActivity(),
                                    new android.app.DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            Log.d("Orignal", "Got clicked");
                                            start_date.setText(Integer.toString(dayOfMonth)+"/"+Integer.toString(month+1)+"/"+Integer.toString(year));
                                        }
                                    },
                                    now.get(Calendar.YEAR),
                                    now.get(Calendar.MONTH),
                                    now.get(Calendar.DAY_OF_MONTH)
                            ).show();
                            start_date.clearFocus();
                        } catch (Exception e) {
                            // TODO: Handle the error.
                        }
                    }}


                {


                }
            });
            end_date.setOnFocusChangeListener(new View.OnFocusChangeListener(){

                @Override
                public void onFocusChange(View view, boolean hasfocus) {
                    if(hasfocus){
                        try {
                            Calendar now = Calendar.getInstance();
                            new android.app.DatePickerDialog(
                                    getActivity(),
                                    new android.app.DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            Log.d("Orignal", "Got clicked");
                                            end_date.setText(Integer.toString(dayOfMonth)+"/"+Integer.toString(month+1)+"/"+Integer.toString(year));
                                        }
                                    },
                                    now.get(Calendar.YEAR),
                                    now.get(Calendar.MONTH),
                                    now.get(Calendar.DAY_OF_MONTH)
                            ).show();
                            end_date.clearFocus();
                        } catch (Exception e) {
                            // TODO: Handle the error.
                        }
                    }}


                {


                }
            });
            fest_college_name = (EditText) xtest.findViewById(R.id.fest_college_name);
            college_website = (EditText) xtest.findViewById(R.id.college_website);
            college_location = (EditText) xtest.findViewById(R.id.college_location);
    /*          fest_college_name.setOnFocusChangeListener(new View.OnFocusChangeListener(){
//commented for place auto select using google search
              @Override
                public void onFocusChange(View view, boolean hasfocus) {
if(hasfocus){
                    try {
                        Intent intent =
                                new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                        .build(getActivity());
                        intent.putExtra(Intent.EXTRA_PROCESS_TEXT, "Search Colleges");
                        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                        fest_college_name.clearFocus();
                    } catch (GooglePlayServicesRepairableException e) {
                        // TODO: Handle the error.
                    } catch (GooglePlayServicesNotAvailableException e) {
                        // TODO: Handle the error.
                    }
                }}


                {


                }
            });*/


        }
    }

    /**
     * Checking device has camera hardware or not
     * */
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(contextForFragment, data);
                try {
                Log.i(TAG, "Place: " + place.getName());
                fest_college_name.setText(Util.Nullcheck(place.getName().toString()));

                    college_website.setText(Util.Nullcheck(place.getWebsiteUri().toString()));
                    college_location.setText(Util.Nullcheck(place.getAddress().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(contextForFragment, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

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
                    image.setLayoutParams(new android.view.ViewGroup.LayoutParams(250,250));
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
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                            encodedImageList.add(encodedImage);
                            cursor.close();


                            ImageView image = new ImageView(getContext());
                            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(120,120));
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
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(120,120));
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
            video.setLayoutParams(new android.view.ViewGroup.LayoutParams(120,120));
            video.setPadding(5,5,5,5);
            video.setVideoURI(videoUri);
            layout.addView(video);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: "+dayOfMonth+"/"+(++monthOfYear)+"/"+year;
        user_password.setText(date);
    }
    @Override
    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getActivity().getFragmentManager().findFragmentByTag("Datepickerdialog");
        if(dpd != null) dpd.setOnDateSetListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_selectpic:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose application"), 100);
                break;
            case R.id.imagetext:
                Intent intent1 = new Intent();
                intent1.setType("image/*");
                intent1.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1, "Choose application"), 100);
                break;

            case R.id.button_next_fourth:
                dialog.show();

                JSONArray jsonArray = new JSONArray();

       /*         if (encodedImageList.isEmpty()){
                    Toast.makeText(getContext(), "Please select some images first.", Toast.LENGTH_SHORT).show();
                    return;
                }*/
                fest_college_name=  xtest.findViewById(R.id.fest_college_name);
                college_website= xtest.findViewById(R.id.college_website);
                college_location= xtest.findViewById(R.id.college_location);
                fest_name= xtest.findViewById(R.id.fest_name);
                fest_caption=xtest.findViewById(R.id.fest_caption);
                planets_spinner=xtest.findViewById(R.id.planets_spinner);
                fest_email_add=xtest.findViewById(R.id.fest_email_add);
                fest_description=xtest.findViewById(R.id.fest_description);
                fest_depart=xtest.findViewById(R.id.fest_depart);
                start_date=xtest.findViewById(R.id.start_date);
                end_date=xtest.findViewById(R.id.end_date);
                fest_event=xtest.findViewById(R.id.fest_event);
                fest_workshop=xtest.findViewById(R.id.fest_workshop);
                fest_paper=xtest.findViewById(R.id.fest_paper);
                fest_guest=xtest.findViewById(R.id.fest_guest);
                fest_sponsors=xtest.findViewById(R.id.fest_sponsors);
                fest_dead_registration=xtest.findViewById(R.id.fest_dead_registration);
                fest_Regis_fees=xtest.findViewById(R.id.fest_Regis_fees);
                fest_website=xtest.findViewById(R.id.fest_website);
                fest_regis_url=xtest.findViewById(R.id.fest_regis_url);
                fest_links=xtest.findViewById(R.id.fest_links);
                fest_reach=xtest.findViewById(R.id.fest_reach);
                Fest_contact_persons=xtest.findViewById(R.id.Fest_contact_persons);
                for (String encoded: encodedImageList){
                    jsonArray.put(encoded);
                }

                try {
                    jsonObject.put("name", etxtUpload.getText().toString().trim());
                    jsonObject.put("imageList", jsonArray);
                    jsonObject.put("fest_college_name", Util.Nullcheck(fest_college_name.getText().toString()));
                    jsonObject.put("college_website", Util.Nullcheck(college_website.getText().toString()));
                    jsonObject.put("college_location", Util.Nullcheck(college_location.getText().toString()));
                    jsonObject.put("fest_name", Util.Nullcheck(fest_name.getText().toString()));
                    jsonObject.put("fest_caption", Util.Nullcheck(fest_caption.getText().toString()));
                    jsonObject.put("fest_email_add", Util.Nullcheck(fest_email_add.getText().toString()));

                    jsonObject.put("fest_type", Util.Nullcheck(planets_spinner.getSelectedItem().toString()));
                    jsonObject.put("fest_description", Util.Nullcheck(fest_description.getText().toString()));
                    jsonObject.put("fest_depart", Util.Nullcheck(fest_depart.getText().toString()));
                    jsonObject.put("start_date", Util.Nullcheck(start_date.getText().toString()));
                    jsonObject.put("end_date", Util.Nullcheck(end_date.getText().toString()));
                    jsonObject.put("fest_event", Util.Nullcheck(fest_event.getText().toString()));
                    jsonObject.put("fest_workshop", Util.Nullcheck(fest_workshop.getText().toString()));
                    jsonObject.put("fest_paper", Util.Nullcheck(fest_paper.getText().toString()));
                    jsonObject.put("fest_guest", Util.Nullcheck(fest_guest.getText().toString()));
                    jsonObject.put("fest_sponsors", Util.Nullcheck(fest_sponsors.getText().toString()));
                    jsonObject.put("fest_dead_registration", Util.Nullcheck(fest_dead_registration.getText().toString()));
                    jsonObject.put("fest_Regis_fees", Util.Nullcheck(fest_Regis_fees.getText().toString()));

                    jsonObject.put("fest_website", Util.Nullcheck(fest_website.getText().toString()));
                    jsonObject.put("fest_regis_url", Util.Nullcheck(fest_regis_url.getText().toString()));
                    jsonObject.put("fest_links", Util.Nullcheck(fest_links.getText().toString()));
                    jsonObject.put("fest_reach", Util.Nullcheck(fest_reach.getText().toString()));
                    jsonObject.put("Fest_contact_persons", Util.Nullcheck(Fest_contact_persons.getText().toString()));


                } catch (JSONException e) {
                    Log.e("JSONObject Here", e.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String serverUrl = null;
                try {
                    String  server = Util.getProperty("name", getContext());
                    serverUrl= server.concat("/FestsRegistration");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, serverUrl, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                Log.e("Message from server", jsonObject.toString());
                                dialog.dismiss();
                                messageText.setText("We have recevied your fest information ");
                                Toast.makeText(getContext(), "We have recevied your fest information ,We will contact you shortly", Toast.LENGTH_LONG).show();
                                Toast.makeText(getContext(), "Thanks for choosing CollegeExplore", Toast.LENGTH_SHORT).show();
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
                break;
        }
    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }



    public class GetStringNewsNotifyTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        private String emailtoPass;
        private String PasswordtoPass;
        private String mobiletoPass,modetopass,USER_ENTRD_OTP;
        View objview;
        private String COLLEGEID;
        public GetStringNewsNotifyTask(String USER_ENTRD_OTP, String MODE, String WORKSPACE_ID, String FUNCTION_ID, String ACTION_ID, String email_name, String user_password, String mobile_number) {
            WorkspaceID=WORKSPACE_ID;
            functionID = FUNCTION_ID;
            ActionID=ACTION_ID;
            emailtoPass =email_name;
                    PasswordtoPass=user_password;
            mobiletoPass=mobile_number;
            modetopass=MODE;
            try {
                this.USER_ENTRD_OTP=Util.Nullcheck(USER_ENTRD_OTP);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public GetStringNewsNotifyTask(String MODE, String WORKSPACE_ID, String FUNCTION_ID, String WorkID, String funtID, String ActID, String COLLEGEIDJSON) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;
            COLLEGEID=COLLEGEIDJSON;
            modetopass=MODE;
        }

        @Override
        protected  void onPreExecute()
        {
            // progresdialoglistview=ProgressDialog.show(getActivity(), "","Loading");
            // Log.e("onPreExecutive","called"+progresdialoglistview);
           xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);*/
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
                Record.put( "USER_EMAIL", emailtoPass);
                Record.put( "USER_PASSWORD", PasswordtoPass);
                Record.put( "USER_MOBILENO", mobiletoPass);
                Record.put( "MODE", modetopass);
                Record.put( "USER_ENTRD_OTP", USER_ENTRD_OTP);
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

                Toast.makeText(getContext(), "Not Able to connect our Servers", Toast.LENGTH_LONG).show();
               xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else {
                if (result != "") {

                    try {
                        if (modetopass.equalsIgnoreCase("OTP_TRIGGER")) {
                            JSONObject obj = new JSONObject(ing);
                            Marker marker;
                            int lenghtl = obj.length();
                            //StringData=new  String[8];
                            for (int i = 0; i < lenghtl; i++) {
                                JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));

                                String collegedesc = (String) clgdata.get("RESULTCODE");
                                if (collegedesc.equalsIgnoreCase("000000")) {
                                    //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                } else {
                                    Toast.makeText(getContext(), "Not Able to connect our Servers", Toast.LENGTH_LONG).show();
                                  //  getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                }

                            }


                            //   LatLng sydney = new LatLng(clgLat,80);
                            //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                        }
                        if (modetopass.equalsIgnoreCase("REGISTER_USER")) {
                            JSONObject obj = new JSONObject(ing);
                            Marker marker;
                            int lenghtl = obj.length();
                            //StringData=new  String[8];
                            for (int i = 0; i < lenghtl; i++) {
                                JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));

                                String collegedesc = (String) clgdata.get("RESULTCODE");
                                if (collegedesc.equalsIgnoreCase("000000")) {
                                   // getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    Toast.makeText(getContext(), R.string.successful_register, Toast.LENGTH_LONG).show();
                                   // dismiss();
                                } else {
                                   // getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    Toast.makeText(getContext(), "Not Able to connect our Servers", Toast.LENGTH_LONG).show();
                                }

                            }


                            //   LatLng sydney = new LatLng(clgLat,80);
                            //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                        } } catch (JSONException e) {
                        e.printStackTrace();
                    }

                   xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
                }
            }
        }
    }
}