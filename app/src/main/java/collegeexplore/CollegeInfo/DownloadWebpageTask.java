package collegeexplore.CollegeInfo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class DownloadWebpageTask extends AsyncTask<String, Void, Bitmap> {
    HomeActivity value;
    public DownloadWebpageTask(HomeActivity value) {
      this.value= value;
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


    // Given a URL, establishes an HttpUrlConnection and retrieves
// the web page content as a InputStream, which it returns as
// a string.
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

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("firstParam", "paramValue1"));
        params.add(new BasicNameValuePair("secondParam", "paramValue2"));
        params.add(new BasicNameValuePair("thirdParam", "paramValue3"));

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

          //  InputStream is = null;
           bitmap = BitmapFactory.decodeStream(is);
          //  ImageView imageView = (ImageView)findViewById(R.id.image_view);
          //  imageView.setImageBitmap(bitmap);


            // Convert the InputStream into a string
         // contentAsString = readIt(is, len);

          /*  JSONObject id = new JSONObject(contentAsString);
            String name = (String) id.get("name");
            double salary = (Double) id.get("salary");
            int age = (int) id.get("age");


            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        }*/

       // return contentAsString;
        return bitmap;
    }

  /*  private static String readIt(InputStream is,int len) {

      //  another way to read string
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }*/
  public String readIt(InputStream stream, int len) throws IOException {
      Reader reader = null;
      reader = new InputStreamReader(stream, "UTF-8");
      char[] buffer = new char[len];
      reader.read(buffer);
      return new String(buffer);
  }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(Bitmap result) {
        Bitmap ing = result;
       // value.checkImageshow(ing);
     // ImageView  imageView=(ImageView)findViewById(R.id.imageView4);
        //  textView.setText(result);

    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}