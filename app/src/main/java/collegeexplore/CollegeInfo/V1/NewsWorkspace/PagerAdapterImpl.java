package collegeexplore.CollegeInfo.V1.NewsWorkspace;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import collegeexplore.CollegeInfo.R;

public class PagerAdapterImpl extends PagerAdapter {

    private ArrayList<HashMap<String, String>> stringData;
    Context contextForFragment;
    private boolean isLoading = false, isMoreDataAvailable = true;
    private OnLoadMoreListener loadMoreListener;

    PagerAdapterImpl(ArrayList<HashMap<String, String>> stringData, Context contextForFragment) {
        this.stringData = stringData;
        this.contextForFragment = contextForFragment;
    }

    @Override
    public int getCount() {
        return stringData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        if(position>=getCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener!=null){
            isLoading = true;
            loadMoreListener.onLoadMore();
        }
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        final View view = inflater.inflate(R.layout.news_flip, container, false);


        if (!(stringData.isEmpty())) {
            final HashMap<String, String> ItemData = stringData.get(position);

            TextView newsDetail = view.findViewById(R.id.news_detail);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                newsDetail.setText(Html.fromHtml(ItemData.get("NEWS_DETAILS")));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                newsDetail.setText(Html.fromHtml(ItemData.get("NEWS_DETAILS"), Html.FROM_HTML_MODE_COMPACT));


            }

            TextView postedDate = view.findViewById(R.id.news_posted_date);
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date past = format.parse(ItemData.get("POSTED_DATE"));
                Date now = new Date();

if(TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime())<60) {
    postedDate.setText(TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime()) + " seconds ago");
}else if(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime())<60){
    postedDate.setText(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
                }
else if(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime())<24){
    postedDate.setText(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
}
else if(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime())<30){
    long val = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());
    postedDate.setText( val == 1?val+ " day ago":val+" days ago");
}
else{
    postedDate.setText(ItemData.get("POSTED_DATE"));
}

/*                System.out.println(TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime()) + " seconds ago");
                System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
                System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
                System.out.println(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago");*/
            }
            catch (Exception j){
                j.printStackTrace();
            }
            TextView newsHeader = view.findViewById(R.id.news_header);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                newsHeader.setText(Html.fromHtml(ItemData.get("NEWS_HEADER")));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                newsHeader.setText(Html.fromHtml(ItemData.get("NEWS_HEADER"), Html.FROM_HTML_MODE_COMPACT));
            }

            ImageView newsImage = view.findViewById(R.id.news_image);
            Picasso.with(container.getContext())
                    .load(ItemData.get("NEWS_IMAGE")).resize(600, 200)
                    .into(newsImage);

            TextView newsShare = view.findViewById(R.id.news_share);
            newsShare.setText("Share");
            newsShare.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "http://collegeexplore.in/NewsDetails/"+ItemData.get("NEWSID"));
                    sendIntent.setType("text/plain");
                    contextForFragment.startActivity(sendIntent);
                }
            });

        }

        container.addView(view);

        return view;
    }

    //needed for pagerAdaptor to refresh
    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }

    interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }
    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

}