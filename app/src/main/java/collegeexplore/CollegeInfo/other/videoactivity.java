package collegeexplore.CollegeInfo.other;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import static collegeexplore.CollegeInfo.R.id;
import static collegeexplore.CollegeInfo.R.layout;

/**
 * Created by DANDE on 09-02-2018.
 */

public class videoactivity extends AppCompatActivity {
    VideoView video;
    MediaController media;
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.videoactivity);
        video = (VideoView) findViewById(id.video_view);

        new BackgroundAsyncTask()
                .execute("https://s3.us-east-2.amazonaws.com/elasticbeanstalk-us-east-2-083183914236/ImagesLocal/oseyramulamma.mp4");



    }
    public class BackgroundAsyncTask extends AsyncTask<String, Uri, Void> {
        Integer track = 0;
        ProgressDialog dialog;


        protected void onPreExecute() {
            dialog = new ProgressDialog(videoactivity.this);
            dialog.setMessage("Loading, Please Wait...");
            dialog.setCancelable(true);
            dialog.show();
        }

        protected void onProgressUpdate(final Uri... uri) {

            try {
             /*   MediaController mediaController = new MediaController(this);
                mediaController.setAnchorView(videoView);
                videoView.setMediaController(mediaController);*/


                media=new MediaController(videoactivity.this);
                video.setMediaController(media);

                video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                            @Override
                            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                /*
                 * add media controller
                 */
                                media = new MediaController(videoactivity.this);
                                video.setMediaController(media);
                /*
                 * and set its position on screen
                 */
                                media.setAnchorView(video);
                            }
                        });
                    }
                });
                media.setPrevNextListeners(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // next button clicked

                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        finish();
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
