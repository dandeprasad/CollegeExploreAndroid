package collegeexplore.CollegeInfo.QuestionsAnswersWorkspace;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.List;

import collegeexplore.CollegeInfo.AllDetailsDescriptionWorkspace.ShowDetailNewsNotifications;
import collegeexplore.CollegeInfo.CollegesAtMaps.NitsNewsNotifications;
import collegeexplore.CollegeInfo.HomeActivity;
import collegeexplore.CollegeInfo.ListOfNitsNews;
import collegeexplore.CollegeInfo.R;
import collegeexplore.CollegeInfo.TransitionHelper;


public class QuesAnsMainAdaptor extends RecyclerView.Adapter<QuesAnsMainAdaptor.InternalHolder> implements ListOfNitsNews.OnItemClickListener, ListofFilters.OnItemClickListener, NitsNewsNotifications.OnItemClickListener {
    private String[] mDataset;
    private Integer[] imagedata;
    private LruCache<String, Bitmap> mMemoryCache;
    private OnItemClickListener mListener;
    public TextView mTextView;
    private String[] maincontents1;
    private ListofFilters ListofAllNotificationsAdaptor;
    private NitsNewsNotifications nitsNewsNotifications;
    Context contextAdaptor;
    private final List<Sample> samples;
    String serverUrl;
    private final Activity activity = null;
    Bitmap[] arrayOfBitmaptest;
    public static Bitmap[] arrayForAllNotifications= new Bitmap[8];
    public static Bitmap[] ArrayListdata= new Bitmap[8];
    // ArrayList<Bitmap> ArrayListdata=new ArrayList<Bitmap>();
    ArrayList<HashMap> stringDataAdaptor;

    public interface OnItemClickListener {
        void onClick(View view, int position, String text, String aHolderMainQuestionText);

        void onClick(View view, int position, String text);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class InternalHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView QuestionSubjectTo,Tag1,Tag2,MainQuestion,FollowsViews,UserAnswersName,
                UserAnswersMail,MainAnswer,datePosted,countlike,countunlike,AnswerButton,followButton,QuestionID,ViewAllAnswers;

        ImageView likebutton,unlikebutton;
        private TextView StringHeader;
        ImageView ShareButton;



        public InternalHolder(View v) {
            super(v);
            ViewAllAnswers = v.findViewById(R.id.ViewAllAnswers);
            QuestionID = v.findViewById(R.id.QuestionID);
            QuestionSubjectTo = v.findViewById(R.id.QuestionSubjectTo);
            Tag1 = v.findViewById(R.id.Tag1);
            Tag2 = v.findViewById(R.id.Tag2);
            MainQuestion= v.findViewById(R.id.MainQuestion);
            FollowsViews= v.findViewById(R.id.FollowsViews);
            UserAnswersName= v.findViewById(R.id.UserAnswersName);
            UserAnswersMail= v.findViewById(R.id.UserAnswersMail);
            MainAnswer= v.findViewById(R.id.MainAnswer);
            datePosted= v.findViewById(R.id.datePosted);
            countlike= v.findViewById(R.id.countlike);
            countunlike= v.findViewById(R.id.countunlike);
            AnswerButton= v.findViewById(R.id.AnswerButton);
            followButton= v.findViewById(R.id.followButton);
            //  mTextView = (TextView) v;
            likebutton =  v.findViewById(R.id.likebutton);
            unlikebutton =  v.findViewById(R.id.unlikebutton);

            ShareButton = v.findViewById(R.id.ShareButton);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public QuesAnsMainAdaptor(OnItemClickListener listener, Context contextForFragment, Bitmap[] arrayOfBitmap, ArrayList<HashMap> stringData) {
        contextAdaptor = contextForFragment;
        mListener = listener;
        arrayOfBitmaptest=arrayOfBitmap;
        stringDataAdaptor=stringData;
        samples = null;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public QuesAnsMainAdaptor.InternalHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        View v=null;

        //memory cache management

        // create a new view

        if (viewType == 0){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.questions_answers_cardview, parent, false);}

        // set the view's size, margins, paddings and layout parameters

        InternalHolder vh = new InternalHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final InternalHolder holder, final int position) {

        HashMap<String,String>datavalues = stringDataAdaptor.get(position);



        holder.QuestionID.setText(datavalues.get("QUESTION_ID"));
        //holder.Tag1.setText(datavalues.get(""));
        //holder.Tag2.setText(datavalues.get(""));  2 Answers  .  1 follower  .  12 Views
        holder.MainQuestion.setText(Html.fromHtml("<b>" + "Q: " + "</b> ")+datavalues.get("QUESTION"));
        holder.FollowsViews.setText( datavalues.get("ANSWERS_COUNT")+" Answers  .  "+ datavalues.get("FOLLOW_COUNT")+ "followers  .  "+datavalues.get("VIEWS_COUNT")+" Views");
        holder.UserAnswersName.setText("User: "+datavalues.get("USERANAME"));
        holder.UserAnswersMail.setText("Email: "+datavalues.get("USEREMAIL"));
        holder.Tag1.setText(datavalues.get("QUEST_TAGS"));
        holder.MainAnswer.setText(Html.fromHtml("<b>" + "A: " + "</b> ")+datavalues.get("ANSWER"));
        holder.datePosted.setText(datavalues.get("TIME_POSTED"));
      //  holder..setText(datavalues.get("LIKE"));
        holder.countlike.setText(datavalues.get("LIKE"));
        holder.countunlike.setText(datavalues.get("UNLIKE"));
     //  holder.AnswerButton.setText(datavalues.get("FollowsViews"));
       // holder.followButton.setText(datavalues.get("FollowsViews"));


        //holder.label.setTextColor(Color.BLACK);
        //    holder.label.setText(mDataset[position]);


       // holder.likebutton.setImageResource(imagedata[position]);
       // holder.unlikebutton.setImageResource(imagedata[position]);
        //final Sample sample = samples.get(holder.getAdapterPosition());
       holder.AnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClick(view, position,(String)holder.QuestionID.getText(),(String)holder.MainQuestion.getText());
            }
        });
/*        holder.ViewAllAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transitionToActivity(AllAnswersActivity.class, holder, sample);

               // mListener.onClick(view, position,(String)holder.QuestionID.getText());
            }
        });*/

       /* holder.likebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClick(view, position);
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


        return super.getItemViewType(0);
    }




    public class AllNotifications  extends AsyncTask<String, Void, Bitmap>

    {
        int imageResId;
        int imagetest;
        private WeakReference<ImageView> imageViewReference;
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
if(is!=null)
            //for storing image data

{
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

}
    // addBitmapToMemoryCache(String.valueOf(imageResId), bitmap1);
            return bitmap1;



        }



        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Bitmap result) {

            // value.checkImageshow(ing);
            // ImageView  imageView=(ImageView)findViewById(R.id.imageView4);
            //  textView.setText(result);

            // Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.sample_3);//assign your bitmap;
            // Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.sample_4);//assign your bitmap;
            if (layoutid.equalsIgnoreCase("ListofAllNotificationsID")) {
                arrayForAllNotifications[imagetest] = result;
                if (imagetest == 7) {
                    ListofAllNotificationsAdaptor.notifyDataSetChanged();
                }
                Bitmap ing = result;
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
            if (layoutid.equalsIgnoreCase("nitsNewsNotificationsID")) {
                ArrayListdata[imagetest]=result;
                if (imagetest == 7) {
                    nitsNewsNotifications.notifyDataSetChanged();
                }
                Bitmap ing = result;
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

    }
    private void transitionToActivity(Class target, InternalHolder viewHolder, Sample sample) {
        Pair<View, String>[] pairs=null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                    new Pair<>(viewHolder.MainQuestion, activity.getString(R.string.app_name)),
                    new Pair<>(viewHolder.MainAnswer, activity.getString(R.string.app_name)));
        }
        startActivity(target, pairs, sample);
    }
    private void startActivity(Class target, Pair<View, String>[] pairs, Sample sample) {
        Intent i = new Intent(activity, target);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);
        i.putExtra("sample", sample);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.startActivity(i, transitionActivityOptions.toBundle());
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
        selectItem(position,UniqueKey);
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