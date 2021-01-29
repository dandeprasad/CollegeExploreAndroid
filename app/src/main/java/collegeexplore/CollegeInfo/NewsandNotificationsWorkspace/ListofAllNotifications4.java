package collegeexplore.CollegeInfo.NewsandNotificationsWorkspace;

/**
 * Created by DANDE on 03-09-2016.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import collegeexplore.CollegeInfo.AdmissionsWorkspace.CollegeAdmisFragment;
import collegeexplore.CollegeInfo.LoginManagement.UserLogin;
import collegeexplore.CollegeInfo.LoginManagement.UserLoginManager;
import collegeexplore.CollegeInfo.R;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;


public class ListofAllNotifications4 extends RecyclerView.Adapter<ListofAllNotifications4.ViewHolder> {
    private ArrayList<HashMap> mDataset;
    private HashMap<String,String> imagedata;
    private TransitionDrawable td1;
    private  Context contexthere;
    private OnItemClickListener mListener;
    final String UniqueKey = "NEWS_NOTIFY_ALLNOTIFYS";



    public interface OnItemClickListener {
        void onClick(View view, int position, String U);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        TextView textviewtopclgs,posteddate_news;
        ImageView imageView;
        CardView view2;
        public ViewHolder(View v) {
            super(v);
            // label = (TextView) v.findViewById(R.id.dandeking);
            //  mTextView = (TextView) v;
            imageView = v.findViewById(R.id.imgallnotify);
            textviewtopclgs = v.findViewById(R.id.textviewtopclgs);
            posteddate_news = v.findViewById(R.id.posteddate_news);

            view2 = v.findViewById(R.id.view2);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListofAllNotifications4(ArrayList<HashMap> maincontents, OnItemClickListener listener, Context contexthere) {
        mDataset = maincontents;
        mListener = listener;

        this.contexthere=contexthere;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListofAllNotifications4.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_notifications1, parent, false);

        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder1, final int position) {


        if (!(mDataset.isEmpty())) {
            if(mDataset.get(position)  !=null) {
            HashMap<String, String> values = mDataset.get(position);
            holder1.textviewtopclgs.setText(values.get("CLG_HEADER"));
                holder1.posteddate_news.setText("Posted Date: "+values.get("POSTED_DATE"));


                Picasso.with(contexthere).load(values.get("ADMISSION_IMAGE")).resize(600, 200).into(holder1.imageView);
         //   Picasso.with(contextForFragmentadp).load(values.get("NEWS_IMAGE")).into( ((NewsViewHolder) holder).newsimgtagy);


            //   holder1.imageView.setImageBitmap(x);


            // holder1.imageView.setImageResource(imagedata[position]);

            // holder1.imageView.setLayoutParams(new FrameLayout.LayoutParams(240, 240));
            //holder.itemView.setLayoutParams(params);
            holder1.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        }

        }


/*        holder1.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClick(view, position,UniqueKey);
            }
        });*/


     /*   holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClick(view, position);
            }
        });*/
        holder1.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserLoginManager checkuserstatus = new UserLoginManager(getApplicationContext());
                if (checkuserstatus.isLoggedIn()) {

                        if (!(mDataset.isEmpty())) {
                        HashMap<String, String> values = mDataset.get(position);
                        String urltosend = values.get("ADMISSION_LINK");

                        String url = urltosend;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        Toast.makeText(contexthere, "You are redirecting to admissions page of respective college", Toast.LENGTH_LONG).show();
                        contexthere.startActivity(i);
                        // mListener.onClick(view, position);
                    }
                }       else{
                    Toast.makeText(getApplicationContext(), R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(),UserLogin.class);
                    contexthere.startActivity(i);
                }
            } });
        holder1.textviewtopclgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserLoginManager checkuserstatus = new UserLoginManager(getApplicationContext());
                if (checkuserstatus.isLoggedIn()) {

                    if (!(mDataset.isEmpty())) {
                HashMap<String, String> values = mDataset.get(position);
                String urltosend=   values.get("ADMISSION_LINK");

                String url =urltosend;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                Toast.makeText(contexthere,"You are redirecting to admissions page of particular college" , Toast.LENGTH_LONG).show();
                contexthere.startActivity(i);
                // mListener.onClick(view, position);
            }}
                else{
                    Toast.makeText(getApplicationContext(), R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(),UserLogin.class);
                    contexthere.startActivity(i);
                }
            }
        });
        holder1.view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserLoginManager checkuserstatus = new UserLoginManager(getApplicationContext());
                if (checkuserstatus.isLoggedIn()) {

                        if (!(mDataset.isEmpty())) {
                        HashMap<String, String> values = mDataset.get(position);
                        String urltosend=   values.get("ADMISSION_LINK");

                        String url =urltosend;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        Toast.makeText(contexthere,"You are redirecting to admissions page of particular college" , Toast.LENGTH_LONG).show();
                        contexthere.startActivity(i);
                        // mListener.onClick(view, position);
                    }}
                else{
                    Toast.makeText(getApplicationContext(), R.string.please_login_to_access, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(),UserLogin.class);
                    contexthere.startActivity(i);
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}