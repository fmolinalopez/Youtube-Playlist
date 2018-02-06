package org.molina.youtubeplaylist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

import org.molina.youtubeplaylist.model.Video;

public class VideoActivity extends AppCompatActivity
    implements View.OnClickListener{

    private static final String YOUTUBE_API_KEY = "AIzaSyDQaNgIQOMx8xW_LuHLnworftOoVZzb4bo";

    Video video = new Video();
    String video_id;
    Button btn_reproducir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        btn_reproducir = findViewById(R.id.btn_reproducir);

        Intent intent = VideoActivity.this.getIntent();

        if (intent.hasExtra("video")){
            video = (Video) intent.getSerializableExtra("video");
        }

        video_id = video.getContent();

        btn_reproducir.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        Intent videoIntent = null;

        videoIntent = YouTubeStandalonePlayer.createVideoIntent(
                this,
                YOUTUBE_API_KEY,
                video_id
        );


        if (videoIntent != null){
            startActivity(videoIntent);
        }
    }
}
