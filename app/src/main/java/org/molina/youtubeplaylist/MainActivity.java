package org.molina.youtubeplaylist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

import org.molina.youtubeplaylist.model.Video;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String YOUTUBE_API_KEY = "AIzaSyDQaNgIQOMx8xW_LuHLnworftOoVZzb4bo";
    private static final String PLAYLIST_RSS_URL = "https://www.youtube.com/feeds/videos.xml?playlist_id=PLOy0j9AvlVZPto6IkjKfpu0Scx--7PGTC";

    public Button btn_cargar_playlist;
    public ListView mPlayList;
    public Video video;
    public String video_id;

    private String mFileContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_cargar_playlist = findViewById(R.id.btn_cargar_playlist);
        mPlayList = findViewById(R.id.playListView);

        btn_cargar_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ParsePlaylist parsePlaylist = new ParsePlaylist(mFileContent);
                parsePlaylist.process();

                final AdapterItem adapterPlaylist = new AdapterItem(MainActivity.this, parsePlaylist.getVideos());

                mPlayList.setAdapter(adapterPlaylist);

                mPlayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        video = parsePlaylist.getVideo(i);
                        video_id = video.getContent();
                        Intent videoIntent = YouTubeStandalonePlayer.createVideoIntent(
                                MainActivity.this,
                                YOUTUBE_API_KEY,
                                video_id

                        );
                        startActivity(videoIntent);
                    }
                });
            }
        });


        DownloadData downloadData = new DownloadData();
        downloadData.execute(PLAYLIST_RSS_URL);
    }

    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadData";

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param strings The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected String doInBackground(String... strings) {
            mFileContent = downloadXmlFile(strings[0]);

            if( mFileContent == null ){
                Log.d(TAG, "Problema descargando el XML");
            }


            return mFileContent;
        }

        /**
         * Construyo un String con el contenido del archivo XML
         * indicado en la url que se pasa como parámetro.
         *
         * @param urlPath Url del recurso que se quiere parsear
         * @return
         */
        public String downloadXmlFile(String urlPath){

            //StringBuilder vs StringBuffer
            StringBuilder tempBuffer = new StringBuilder();

            try{
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "Response Code: " + response);

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int charsRead;
                char[] inputBuffer = new char[500];

                while( true ) {
                    charsRead = isr.read(inputBuffer);

                    if( charsRead <= 0 ) {
                        break;
                    }

                    tempBuffer.append(String.copyValueOf(inputBuffer, 0, charsRead));
                }

                return tempBuffer.toString();

            }catch (IOException e){
                Log.d(TAG, "Error: No se pudo descargar el RSS - " + e.getMessage());
            }

            return null;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param result The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            Log.d(TAG, "Resultado: " + result);
        }
    }
}
