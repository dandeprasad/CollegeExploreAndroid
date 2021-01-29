package collegeexplore.CollegeInfo.other;

import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;

        import com.google.android.youtube.player.YouTubeBaseActivity;
        import com.google.android.youtube.player.YouTubeInitializationResult;
        import com.google.android.youtube.player.YouTubePlayer;
        import com.google.android.youtube.player.YouTubePlayerView;

import collegeexplore.CollegeInfo.R;

public class YoutubeTest extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_test);
        final EditText editTextId = findViewById(R.id.editTextId);
        Button buttonPlay = findViewById(R.id.buttonPlay);
        final YouTubePlayerView youtubePlayerView = findViewById(R.id.youtubePlayerView);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String videoId = editTextId.getText().toString();
                playVideo(videoId, youtubePlayerView);
            }
        });


    }

    public void playVideo(final String videoId, YouTubePlayerView youTubePlayerView) {
        //initialize youtube player view
        youTubePlayerView.initialize("AIzaSyDamR7QyrR3BCZ68ataFCZHS8P3rnzmr3Q",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(videoId);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }


}