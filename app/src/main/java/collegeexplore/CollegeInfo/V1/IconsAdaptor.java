package collegeexplore.CollegeInfo.V1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import collegeexplore.CollegeInfo.AboutUsClgEplore;
import collegeexplore.CollegeInfo.CollegesAtMaps.homeMapsActivity;
import collegeexplore.CollegeInfo.CutoffsWorkspace.CutoffFirstFragment;
import collegeexplore.CollegeInfo.ExamsWorkspace.HomeExamsFragment;
import collegeexplore.CollegeInfo.FestsCollegesWorkspace.FestNewsNotifications;
import collegeexplore.CollegeInfo.HomeActivity;
import collegeexplore.CollegeInfo.LoginManagement.UserLogin;
import collegeexplore.CollegeInfo.NewsWorkspace.NewsFragment;
import collegeexplore.CollegeInfo.PlacementsWorkspace.PlaceFirstFragment;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.RankingsWorkspace.RankingsFragment;
import collegeexplore.CollegeInfo.SpacesItemDecorHomeActivity1;
import collegeexplore.CollegeInfo.UserNotifications.UserNotifications;
import collegeexplore.CollegeInfo.Util;
import static collegeexplore.CollegeInfo.V1.IconsFragment.iconAdapter;
import static com.facebook.FacebookSdk.getApplicationContext;

public class IconsAdaptor extends RecyclerView.Adapter<IconsAdaptor.IconViewHolder>implements MainIconsAdaptor.OnItemClickListener {
    private static final int SPAN_COUNT =3 ;
    View v=null;
    private MainIconsAdaptor iconsAdaptor;
    Context contextAdaptor;
    String[] home_appicons;
    Boolean loadedImagesData = true;
    HashMap loadedImagesDataMap= new HashMap();
    String serverUrl;
    String TAG = IconsAdaptor.class.getSimpleName();
    IconsAdaptor(Context contextForFragment){
        contextAdaptor = contextForFragment;

    }
    @NonNull
    @Override
    public IconViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0){
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.news_notif_lineardata, viewGroup, false);};
        IconViewHolder vh = new IconViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull IconViewHolder iconViewHolder, int position) {
        if(position==0){
            home_appicons = contextAdaptor.getResources().getStringArray(R.array.v1_home_appicons);
            iconsAdaptor =new MainIconsAdaptor(home_appicons, loadedImagesDataMap,  this,contextAdaptor);
            GridLayoutManager newslayoutManager = new GridLayoutManager(contextAdaptor, SPAN_COUNT);
            iconViewHolder.iconsRecyclerView.setNestedScrollingEnabled(false);
            iconViewHolder.iconsRecyclerView.setLayoutManager(newslayoutManager);

            iconViewHolder.iconsRecyclerView.setAdapter(iconsAdaptor);
            int spacingInPixels = contextAdaptor.getResources().getDimensionPixelSize(R.dimen.spacing);
            iconViewHolder.iconsRecyclerView.addItemDecoration(new SpacesItemDecorHomeActivity1(spacingInPixels));
            newslayoutManager.setSmoothScrollbarEnabled(true);

            if(loadedImagesData) {

                //Colleges in cities
                try {

                    String WORKSPACE_ID1 = "HOME_WORKSPACE";
                    String FUNCTION_ID1 = "GET_LINEAR_DATA";
                    String ACTION_ID1 = "CLGS_ALL_APPS";

                    String server = Util.getProperty("name", contextAdaptor);
                    serverUrl = server.concat("/HomeAllNotifications");


                    new GetIconData(WORKSPACE_ID1, FUNCTION_ID1, ACTION_ID1).execute(serverUrl);


           /* ImagesArray[i] = BitmapFactory.decodeResource(contextAdaptor.getResources(),
                    R.drawable.load_mask);*/


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
    @Override
    public int getItemViewType(int position) {
        if (position ==0)
        {
            return 0;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onClick(View view, int position, String U) {

    }

    @Override
    public void onClick(View view, int position) {
        if(position==0)
        {
                Intent i = new Intent(getApplicationContext(), homeMapsActivity.class);
                contextAdaptor.startActivity(i);

        }
        if(position==1) {

                Slide slideTransition = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    slideTransition = new Slide(Gravity.LEFT);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    slideTransition.setDuration(contextAdaptor.getResources().getInteger(R.integer.anim_duration_long));
                }
                String toolbarTorF = "T";
                NewsFragment newsFragment = NewsFragment.newInstance(toolbarTorF);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    newsFragment.setSharedElementEnterTransition(new ChangeBounds());
                }
                newsFragment.setReenterTransition(slideTransition);
                newsFragment.setExitTransition(slideTransition);


                android.support.v4.app.FragmentManager manager = ((AppCompatActivity) contextAdaptor).getSupportFragmentManager();
                manager.beginTransaction()

                        .addToBackStack(TAG)
                        .replace(R.id.view_onclick_frame, newsFragment)
                        .commit();







        }

        if(position==2) {


                Slide slideTransition = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    slideTransition = new Slide(Gravity.LEFT);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    slideTransition.setDuration(contextAdaptor.getResources().getInteger(R.integer.anim_duration_long));
                }
                FestNewsNotifications festFragment = new FestNewsNotifications();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    festFragment.setSharedElementEnterTransition(new ChangeBounds());
                }
                festFragment.setReenterTransition(slideTransition);
                festFragment.setExitTransition(slideTransition);


                android.support.v4.app.FragmentManager manager = ((AppCompatActivity) contextAdaptor).getSupportFragmentManager();
                manager.beginTransaction()

                        .addToBackStack(TAG)
                        .replace(R.id.view_onclick_frame, festFragment)
                        .commit();



        }
        if(position==3) {


                Slide slideTransition = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    slideTransition = new Slide(Gravity.LEFT);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    slideTransition.setDuration(contextAdaptor.getResources().getInteger(R.integer.anim_duration_long));
                }
                HomeExamsFragment examFragment = new HomeExamsFragment();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    examFragment.setSharedElementEnterTransition(new ChangeBounds());
                }
                examFragment.setReenterTransition(slideTransition);
                examFragment.setExitTransition(slideTransition);


                android.support.v4.app.FragmentManager manager = ((AppCompatActivity) contextAdaptor).getSupportFragmentManager();
                manager.beginTransaction()

                        .addToBackStack(TAG)
                        .replace(R.id.view_onclick_frame, examFragment)
                        .commit();



        }

        if(position==4) {


                Slide slideTransition = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    slideTransition = new Slide(Gravity.LEFT);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    slideTransition.setDuration(contextAdaptor.getResources().getInteger(R.integer.anim_duration_long));
                }
                RankingsFragment userrat = RankingsFragment.newInstance("", "", "");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    userrat.setSharedElementEnterTransition(new ChangeBounds());
                }
                userrat.setReenterTransition(slideTransition);
                userrat.setExitTransition(slideTransition);


                android.support.v4.app.FragmentManager manager = ((AppCompatActivity) contextAdaptor).getSupportFragmentManager();
                manager.beginTransaction()

                        .addToBackStack(TAG)
                        .replace(R.id.view_onclick_frame, userrat)
                        .commit();

        }
        if(position==5) {


                Slide slideTransition = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    slideTransition = new Slide(Gravity.LEFT);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    slideTransition.setDuration(contextAdaptor.getResources().getInteger(R.integer.anim_duration_long));
                }
                CutoffFirstFragment cutoffsFragment = new CutoffFirstFragment();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    cutoffsFragment.setSharedElementEnterTransition(new ChangeBounds());
                }
                cutoffsFragment.setReenterTransition(slideTransition);
                cutoffsFragment.setExitTransition(slideTransition);


                android.support.v4.app.FragmentManager manager = ((AppCompatActivity) contextAdaptor).getSupportFragmentManager();
                manager.beginTransaction()

                        .addToBackStack(TAG)
                        .replace(R.id.view_onclick_frame, cutoffsFragment)
                        .commit();


        }
        if(position==6) {

                Slide slideTransition = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    slideTransition = new Slide(Gravity.LEFT);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    slideTransition.setDuration(contextAdaptor.getResources().getInteger(R.integer.anim_duration_long));
                }
                PlaceFirstFragment placefrag = new PlaceFirstFragment();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    placefrag.setSharedElementEnterTransition(new ChangeBounds());
                }
                placefrag.setReenterTransition(slideTransition);
                placefrag.setExitTransition(slideTransition);


                android.support.v4.app.FragmentManager manager = ((AppCompatActivity) contextAdaptor).getSupportFragmentManager();
                manager.beginTransaction()

                        .addToBackStack(TAG)
                        .replace(R.id.view_onclick_frame, placefrag)
                        .commit();





        }
        if(position==8) {
            Intent i = new Intent(getApplicationContext(), UserLogin.class);
            contextAdaptor.startActivity(i);
        }
        if(position==7) {
            Intent i = new Intent(getApplicationContext(), UserNotifications.class);
            contextAdaptor.startActivity(i);
        }
        if(position==10) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Download our CollegeExplore application at: https://play.google.com/store/apps/details?id=com.collegeexplore.app");
            sendIntent.setType("text/plain");
            contextAdaptor.startActivity(sendIntent);
        }
        if(position==9) {


            AboutUsClgEplore placefrag = new AboutUsClgEplore();
            android.support.v4.app.FragmentManager manager = ((AppCompatActivity) contextAdaptor).getSupportFragmentManager();
            manager.beginTransaction()

                    .addToBackStack(TAG)
                    .replace(R.id.view_onclick_frame, placefrag)
                    .commit();


        }
        if(position==11) {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            contextAdaptor.startActivity(i);
        }

    }

    public class IconViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView iconsRecyclerView;
        public IconViewHolder(@NonNull View itemView) {
            super(itemView);
            iconsRecyclerView = v.findViewById(R.id.recyclerNewsAllnotify);
        }
    }

    public class GetIconData extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        private String functionID;
        private String ActionID;
        private String WorkspaceID;
        View objview;
        private String COLLEGEID;

        public GetIconData(String WorkID,String funtID,String ActID) {
            WorkspaceID=WorkID;
            functionID = funtID;
            ActionID=ActID;

        }

        @Override
        protected  void onPreExecute()
        {
            // progresdialoglistview=ProgressDialog.show(getActivity(), "","Loading");
            // Log.e("onPreExecutive","called"+progresdialoglistview);
            //   xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
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
                //  xtest.findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
            }
            else {
                if (result != "") {

                    try {

                        JSONObject clgdata =new JSONObject( result);
                        for(int i = 0; i<clgdata.names().length(); i++) {
                            // Log.v(TAG, "key = " + clgdata.names().getString(i) + " value = " + clgdata.get(clgdata.names().getString(i)));


                            loadedImagesDataMap.put(clgdata.names().getString(i), clgdata.get(clgdata.names().getString(i)).toString());
                        }                         /*   data.setText("College Name: "+festinfo);
                            String  imageserver = null;
                            try {
                                imageserver =  UtilProperty.getProperty("img_ClgFestFragment", contextForFragment,"imagepath.properties");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Picasso.with(contextForFragment).load(imageserver+imageinfo).into(data1);
*/

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    loadedImagesData=false;
                    iconAdapter.notifyItemChanged(0);


                    //   LatLng sydney = new LatLng(clgLat,80);
                    //mMap.addMarker(new MarkerOptions().position(sydney).title(clgname));
                }
                // xtest.findViewById(R.id.marker_progress).setVisibility(View.GONE);
            }
        }
    }
}
