package collegeexplore.travel;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.Util;

public class DandeVideoFragment extends Fragment {
    ArrayList<HashMap<String, String>> StringData = new ArrayList<>();
    PagerAdapterImpl pagerAdapter;
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.vertical_activity_main, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        viewPager = view.findViewById(R.id.vertical_view_pager);
        pagerAdapter = new PagerAdapterImpl(StringData,getContext());
        pagerAdapter.setLoadMoreListener(new PagerAdapterImpl.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                viewPager.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = StringData.size() - 1;
                        loadMore(index);
                    }
                });
            }
        });
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        callingServer("ALL");
    }

    private void loadMore(int index) {

        callingserverloadmore(index, "ALL");


    }

    public void callingServer(String filterVal) {
        try {
            String server = Util.getProperty("name", Objects.requireNonNull(getContext()));

            String jsonurl = server.concat("/NewsStrings");


            String WORKSPACE_ID = "HOME_WORKSPACE";
            String FUNCTION_ID = "GET_NEWS_ONLY";
            String ACTION_ID = "GET_ALL_NEWS_STRING";


            new NewsService(WORKSPACE_ID, FUNCTION_ID, ACTION_ID, 0, 5, filterVal).execute(jsonurl);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void callingserverloadmore(int index, String filterval) {
        try {
            String server = Util.getProperty("name", Objects.requireNonNull(getContext()));

            String jsonUrl = server.concat("/NewsStrings");


            String WORKSPACE_ID = "HOME_WORKSPACE";
            String FUNCTION_ID = "GET_NEWS_ONLY";
            String ACTION_ID = "GET_ALL_NEWS_STRING";
            int startIndex = index + 1;
            int endDataIndex = index + 6;

            new NewsServiceLoadMore(WORKSPACE_ID, FUNCTION_ID, ACTION_ID, startIndex, endDataIndex, filterval).execute(jsonUrl);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
     class NewsServiceLoadMore extends AsyncTask<String, Void, String> {
        private String functionID;
        private String ActionID;
        private String WorkspaceID, filterKey;

        int startingRowIndex;
        int endingRowIndex;

        NewsServiceLoadMore(String WorkID, String funcId, String ActID, int startIndex, int endDataIndex, String filterKey) {
            WorkspaceID = WorkID;
            functionID = funcId;
            ActionID = ActID;
            startingRowIndex = startIndex;
            endingRowIndex = endDataIndex;
            this.filterKey = filterKey;
        }

        @Override
        protected String doInBackground(String... urls) {

            try {

                return downloadUrl(urls[0]);

            } catch (IOException e) {
                return "UNABLE_TO_REACH_WEB";
            }
        }


        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            String contentAsString = null;


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
                Record.put("ACTION_ID", ActionID);
                Record.put("FUNCTION_ID", functionID);
                Record.put("WORKSPACE_ID", WorkspaceID);
                Record.put("SROW_INDEX", startingRowIndex);
                Record.put("EROW_INDEX", endingRowIndex);
                Record.put("FILTER_KEY", filterKey);

                json.put("datatohost", Record);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            // P toString()
            //  System.out.println( "JSON: " + json.toString() );


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, StandardCharsets.UTF_8));
            writer.write("ServerData=" + json.toString());

            writer.flush();
            writer.close();
            os.close();

            conn.connect();


            int response = conn.getResponseCode();
            if (response == 200) {
                is = conn.getInputStream();
            }

            if (is != null) {

                // Convert the InputStream into a string
                contentAsString = readIt(is);
                is.close();

            }

            return contentAsString;

        }


        public String readIt(InputStream stream) throws IOException {

            return IOUtils.toString(stream, "UTF-8");

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            String ing = result;
            if (result == null || result.equals("{}")) {
                result = "";
            }
            if (result.equalsIgnoreCase("UNABLE_TO_REACH_WEB")) {
                Toast.makeText(getActivity(), "UNABLE_TO_REACH_WEB", Toast.LENGTH_SHORT).show();

            } else {
                if (!result.equals("")) {

                    try {

                        JSONObject obj = new JSONObject(ing);

                        StringData.remove(StringData.size() - 1);
                        int count = 3;
                        for (int i = 0; i < count; i++) {
                            JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));
                            HashMap<String, String> hashMap = new HashMap<>();
                            String StringNews = Util.Nullcheck((String) clgdata.get("NEWS_HEADER"));
                            String NewsId = Util.Nullcheck((String) clgdata.get("NEWSID"));
                            String postedDate = Util.Nullcheck((String) clgdata.get("POSTED_DATE"));
                            String TAG_TYPE = Util.Nullcheck((String) clgdata.get("TAG_TYPE"));
                            String TAG_COLOR = Util.Nullcheck((String) clgdata.get("TAG_COLOR"));
                            String FULL_IMAGE = Util.Nullcheck((String) clgdata.get("FULL_IMAGE"));
                            String NEWS_DETAILS = Util.Nullcheck((String) clgdata.get("NEWS_DETAILS"));
                            String NEWS_IMAGE = Util.Nullcheck((String) clgdata.get("NEWS_IMAGE"));
                            hashMap.put("NEWS_HEADER", StringNews);
                            hashMap.put("NEWSID", NewsId);
                            hashMap.put("POSTED_DATE", postedDate);
                            hashMap.put("TAG_TYPE", TAG_TYPE);
                            hashMap.put("TAG_COLOR", TAG_COLOR);
                            hashMap.put("FULL_IMAGE", FULL_IMAGE);
                            hashMap.put("NEWS_DETAILS", NEWS_DETAILS);
                            hashMap.put("NEWS_IMAGE", NEWS_IMAGE);
                            hashMap.put("LOAD_CHECK", "loaded");


                            StringData.add(hashMap);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else
                {
                    pagerAdapter.setMoreDataAvailable(false);
                    Toast.makeText(getContext(), "That's all for now", Toast.LENGTH_LONG).show();
                }
                pagerAdapter.notifyDataChanged();

            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    class NewsService extends AsyncTask<String, Void, String> {

        private String functionID;
        private String ActionID;
        private String WorkspaceID, filterKey;
        int startingRowIndex;
        int endingRowIndex;

        NewsService(String WorkID, String funcId, String ActID, int startIndex, int endDataIndex, String filterKey) {
            WorkspaceID = WorkID;
            functionID = funcId;
            ActionID = ActID;
            startingRowIndex = startIndex;
            endingRowIndex = endDataIndex;
            this.filterKey = filterKey;
        }


        @Override
        protected String doInBackground(String... urls) {


            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                Toast.makeText(getActivity(), "UNABLE_TO_REACH_WEB", Toast.LENGTH_SHORT).show();
                return "UNABLE_TO_REACH_WEB";
            }
        }


        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;

            String contentAsString = null;

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
                Record.put("ACTION_ID", ActionID);
                Record.put("FUNCTION_ID", functionID);
                Record.put("WORKSPACE_ID", WorkspaceID);
                Record.put("SROW_INDEX", startingRowIndex);
                Record.put("EROW_INDEX", endingRowIndex);
                Record.put("FILTER_KEY", filterKey);

                json.put("datatohost", Record);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, StandardCharsets.UTF_8));
            writer.write("ServerData=" + json.toString());

            writer.flush();
            writer.close();
            os.close();

            conn.connect();


            int response = conn.getResponseCode();
            if (response == 200) {
                is = conn.getInputStream();
            }

            if (is != null) {

                contentAsString = readIt(is);
                is.close();

            }

            return contentAsString;

        }


        public String readIt(InputStream stream) throws IOException {

            return IOUtils.toString(stream, "UTF-8");
        }

        @Override
        protected void onPostExecute(String result) {
            String ing = result;
            if (result == null) {
                result = "";
            }
            if (result.equalsIgnoreCase("UNABLE_TO_REACH_WEB")) {
                Toast.makeText(getActivity(), "UNABLE_TO_REACH_WEB", Toast.LENGTH_SHORT).show();
            } else {
                if (!result.equals("")) {

                    try {
                        JSONObject obj = new JSONObject(ing);
                        int lenghtl = obj.length();
                        StringData.clear();
                        for (int i = 0; i < lenghtl; i++) {
                            JSONObject clgdata = (JSONObject) obj.get(String.valueOf(i));
                            HashMap<String, String> hashmap = new HashMap<>();
                            String StringNews = Util.Nullcheck((String) clgdata.get("NEWS_HEADER"));
                            String NewsId = Util.Nullcheck((String) clgdata.get("NEWSID"));
                            String postedDate = Util.Nullcheck((String) clgdata.get("POSTED_DATE"));
                            String TAG_TYPE = Util.Nullcheck((String) clgdata.get("TAG_TYPE"));
                            String TAG_COLOR = Util.Nullcheck((String) clgdata.get("TAG_COLOR"));
                            String FULL_IMAGE = Util.Nullcheck((String) clgdata.get("FULL_IMAGE"));
                            String NEWS_DETAILS = Util.Nullcheck((String) clgdata.get("NEWS_DETAILS"));
                            String NEWS_IMAGE = Util.Nullcheck((String) clgdata.get("NEWS_IMAGE"));
                            hashmap.put("NEWS_HEADER", StringNews);
                            hashmap.put("NEWSID", NewsId);
                            hashmap.put("POSTED_DATE", postedDate);
                            hashmap.put("TAG_TYPE", TAG_TYPE);
                            hashmap.put("TAG_COLOR", TAG_COLOR);
                            hashmap.put("FULL_IMAGE", FULL_IMAGE);
                            hashmap.put("NEWS_DETAILS", NEWS_DETAILS);
                            hashmap.put("NEWS_IMAGE", NEWS_IMAGE);
                            hashmap.put("LOAD_CHECK", "loaded");


                            StringData.add(hashmap);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                pagerAdapter.notifyDataSetChanged();
            }
        }
    }


}

