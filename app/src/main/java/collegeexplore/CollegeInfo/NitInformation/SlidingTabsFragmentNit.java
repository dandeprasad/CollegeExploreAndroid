/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package collegeexplore.CollegeInfo.NitInformation;
/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
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
import java.util.ArrayList;

import collegeexplore.CollegeInfo.R;
import collegeexplore.logger.Log;
import collegeexplore.view.SlidingTabLayout;


public class SlidingTabsFragmentNit extends Fragment {

    static final String LOG_TAG = "SlidingTabsFragmentNit";
     static  String [] stringArray;
    int positionCheck;

    /**
     * A custom {@link ViewPager} title strip which looks much like Tabs present in Android v4.0 and
     * above, but is designed to give continuous feedback to the user when scrolling.
     */
    private SlidingTabLayout mSlidingTabLayout;

    /**
     * A {@link ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
     */
    private ViewPager mViewPager;

    /**
     * Inflates the {@link View} which will be displayed by this {@link Fragment}, from the app's
     * resources.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }

    // BEGIN_INCLUDE (fragment_onviewcreated)
    /**
     * This is called after the {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} has finished.
     * Here we can pick out the {@link View}s we need to configure from the content view.
     *
     * We set the {@link ViewPager}'s adapter to be an instance of {@link SamplePagerAdapter}. The
     * {@link SlidingTabLayout} is then given the {@link ViewPager} so that it can populate itself.
     *
     * @param view View created in {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter(getFragmentManager() ));
        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
        // END_INCLUDE (setup_slidingtablayout)
    }
    // END_INCLUDE (fragment_onviewcreated)

    /**
     * The {@link android.support.v4.view.PagerAdapter} used to display pages in this sample.
     * The individual pages are simple and just display two lines of text. The important section of
     * this class is the {@link #getPageTitle(int)} method which controls what is displayed in the
     * {@link SlidingTabLayout}.
     */
    public class SamplePagerAdapter extends FragmentPagerAdapter {
        public SamplePagerAdapter(FragmentManager fm) {
            super(fm);
        }



        /*public int getItemPosition(Object object) {
            return POSITION_NONE;
        }*/
        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            return 7;
        }

        /**
         * @return true if the value returned from {@link #instantiateItem(ViewGroup, int)} is the
         * same object as the {@link View} added to the {@link ViewPager}.
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        // BEGIN_INCLUDE (pageradapter_getpagetitle)
        /**
         * Return the title of the item at {@code position}. This is important as what this method
         * returns is what is displayed in the {@link SlidingTabLayout}.
         * <p>
         * Here we construct one using the position value, but for real application the title should
         * refer to the item's contents.
         */
        @Override
        public CharSequence getPageTitle(int position) {

            String  val = null;
            if (position == 0) {
                val =  "News";
            }
            if (position == 1) {
                val = "Social Networks Buzz";
            }
            if (position == 2) {
                val = "Placements";
            }
            if (position == 3) {
                val = "College Info";
            }
            if (position == 4) {
                val = "Publications And Patents";
            }
            if (position == 5) {
                val = "Project Reports and Thesis";
            }
            if (position == 6) {
                val = "Rules And Regulations";
            }

            return val;
        }
        //     return "Item " + (position + 1);

        // END_INCLUDE (pageradapter_getpagetitle)

        /**
         * Instantiate the {@link View} which should be displayed at {@code position}. Here we
         * inflate a layout from the apps resources and then change the text view to signify the position.
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return NitNewsFragListView.newInstance(position);
                 /*   Fragment fr = new NitNewsFragListView();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.sample_content_fragment1, fr);
                    fragmentTransaction.commit();*/
                case 1: // Fragment # 0 - This will show FirstFragment different title
                  //  return NitSocialFragListView.newInstance(position);
                  /*  Fragment fz = new NitSocialFragListView();
                    FragmentManager fm1 = getFragmentManager();
                    FragmentTransaction fragmentTransaction11 = fm1.beginTransaction();
                    fragmentTransaction11.replace(R.id.sample_content_fragment1, fz);
                    fragmentTransaction11.commit();*/
                default:
                    return null;
            }
        }

  /*    @Override
        public Object instantiateItem(ViewGroup container, int position) {
            positionCheck = position;
            // Inflate a new layout from our resources
            View view = getActivity().getLayoutInflater().inflate(R.layout.pager_item,
                    container, false);
            // Add the newly created View to the ViewPager
            container.addView(view);
            view.setTag("xyz");
            switch (position)
            {
                case 0 : {
                    Fragment fr = new NitNewsFragListView();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.sample_content_fragment1, fr);
                    fragmentTransaction.commit();
                }
                case 1 : {
                    Fragment fr = new NitSocialFragListView();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.sample_content_fragment1, fr);
                    fragmentTransaction.commit();
                }

            }
          /*  switch (position)
            {
                case 0 :{  */
                    // Gets the URL from the UI's text field.
                   // String stringUrl ="http://10.0.2.2:8085/androiddandeCheck/Hello3check";



           // new GetDataTask(view).execute(stringUrl);



             /*   }


            }*/

            // Retrieve a TextView from the inflated View, and update it's text
       /*     TextView title = (TextView) view.findViewById(R.id.item_title);
            title.setText(String.valueOf(position + 1));

            Log.i(LOG_TAG, "instantiateItem() [position: " + position + "]");*/

            // Return the View
         /*   return view;
        }*/

        /**
         * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
         * {@link View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            Log.i(LOG_TAG, "destroyItem() [position: " + position + "]");
        }

    }

    public class GetDataTask  extends AsyncTask<String, Void, String> {
        // SlidingTabsFragmentNit value;
        View objview;
        public GetDataTask(View viewx) {
            objview = viewx;
        }
        public GetDataTask() {

        }

        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                int x = positionCheck;
                String xy = downloadUrl(urls[0]);
                return xy;
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }}



        // Given a URL, establishes an HttpUrlConnection and retrieves
// the web page content as a InputStream, which it returns as
// a string.
        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;
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


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write("dande=king");
            writer.flush();
            writer.close();
            os.close();

            conn.connect();


            int response = conn.getResponseCode();
            //  Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();


            // Convert the InputStream into a string
            contentAsString = readIt(is, len);
            is.close();



            return contentAsString;

        }


        public String readIt(InputStream stream, int len) throws IOException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            String ing = result;
            try {
                JSONArray json_data = new JSONArray(result);
                Bundle bundle = new Bundle();
                ArrayList<String> stringArrayList = new ArrayList<String>();
                for(int i=0;i<json_data.length();i++) {
                    JSONObject json_data1 = (JSONObject) json_data.get(i);
                    stringArrayList.add(json_data1.getString("NEWS"));
                    String NIT_ID = (String) json_data1.get("NIT_ID");
                    String CLG = (String) json_data1.get("CLG");
                    String NEWS = (String) json_data1.get("NEWS");
                    String NEWS_HEAD = (String) json_data1.get("NEWS_HEAD");

                    //  bundle.putString("news", NEWS);
                }
              stringArray = stringArrayList.toArray(new String[stringArrayList.size()]);

                        //mInfo.setText(str);
               // mViewPager.getAdapter().getItemPosition(Object object)

              //  mViewPager.findViewWithTag("xyz").invalidate();
               // mViewPager.findViewWithTag("xyz");
                Fragment fr =  new NitNewsFragListView();
int y = positionCheck;
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.sample_content_fragment1,fr);
                fragmentTransaction.commit();
               // mViewPager.getAdapter().notifyDataSetChanged();
               // mViewPager.findViewWithTag("xyz");

              }catch (JSONException e) {
                e.printStackTrace();
            }

            // value.checkImageshow(ing);
            // ImageView  imageView=(ImageView)findViewById(R.id.imageView4);
            //  textView.setText(result);

        }


    }
    public static String[] returnArray()
    {
        return(stringArray);
    }
}
