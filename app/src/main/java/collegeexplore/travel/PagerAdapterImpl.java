package collegeexplore.travel;


import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.HashMap;

import collegeexplore.CollegeInfo.R;

public class PagerAdapterImpl extends PagerAdapter {

    private  Context dandeVideoFragment;
    private ArrayList<HashMap<String, String>> stringData;
    private boolean isLoading = false, isMoreDataAvailable = true;
    private OnLoadMoreListener loadMoreListener;

    PagerAdapterImpl(ArrayList<HashMap<String, String>> stringData, Context dandeVideoFragment) {
        this.stringData = stringData;
        this.dandeVideoFragment =dandeVideoFragment;
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
         View view = inflater.inflate(R.layout.videoactivity, container, false);
        VideoView video;
        video = (VideoView) view.findViewById(R.id.video_view);

        if(position==1) {
            new BackgroundAsyncTask(video)
                    .execute("https://media.istockphoto.com/videos/riding-bicycle-on-asphalt-road-in-forest-on-sunny-day-wide-angle-video-id1250084924");
        }
        if(position==0) {
            new BackgroundAsyncTask(video)
                    .execute("https://media.istockphoto.com/videos/turtle-crawling-in-the-desert-video-id1217984638");
        }
        if(position==2) {
            new BackgroundAsyncTask(video)
                    .execute("https://media.istockphoto.com/videos/preparing-mix-salad-with-vegetables-at-home-a-part-of-stirring-the-video-id1216828127");
        }



       /* if (!(stringData.isEmpty()))*/ /*{
            HashMap<String, String> ItemData = stringData.get(position);

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

*//*                System.out.println(TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime()) + " seconds ago");
                System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
                System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
                System.out.println(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago");*//*
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
        }*/

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
    public class BackgroundAsyncTask extends AsyncTask<String, Uri, Void> {
        Integer track = 0;
        ProgressDialog dialog;
        MediaController  media;
        VideoView video;

        public BackgroundAsyncTask(VideoView video) {
            this.video = video;
        }


        protected void onPreExecute() {
            dialog = new ProgressDialog(dandeVideoFragment);
            dialog.setMessage("Loading, Please Wait...");

            dialog.show();
        }

        protected void onProgressUpdate(final Uri... uri) {

            try {
             /*   MediaController mediaController = new MediaController(this);
                mediaController.setAnchorView(videoView);
                videoView.setMediaController(mediaController);*/


                  media=new MediaController(dandeVideoFragment);
                video.setMediaController(media);

/*                video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                            @Override
                            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                                *//*
                                 * add media controller
                                 *//*
                                media = new MediaController(dandeVideoFragment);
                                video.setMediaController(media);
                                *//*
                                 * and set its position on screen
                                 *//*
                                media.setAnchorView(video);
                            }
                        });
                    }
                });*/
                media.setPrevNextListeners(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // next button clicked

                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //finish();
                    }
                });

                media.show(10000);
                media.setPadding(0, 0, 0, 0);

                video.setVideoURI(uri[0]);
                video.requestFocus();
                video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    public void onPrepared(MediaPlayer arg0) {
                        video.start();
                        dialog.dismiss();
                    }
                });

/*                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) video.getLayoutParams();
                params.width = metrics.widthPixels;
                params.height = metrics.heightPixels;
                params.leftMargin = 0;
                video.setLayoutParams(params);*/
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                Uri uri = Uri.parse(params[0]);

                publishProgress(uri);
            } catch (Exception e) {
                e.printStackTrace();

            }

            return null;
        }


    }
}