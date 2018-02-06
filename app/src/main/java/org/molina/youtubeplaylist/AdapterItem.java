package org.molina.youtubeplaylist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.molina.youtubeplaylist.model.Video;

import java.util.ArrayList;

/**
 * Created by Vikin on 06/02/2018.
 */

class AdapterItem extends BaseAdapter{

    private Activity activity;
    private ArrayList<Video> playlist;

    public AdapterItem(Activity activity, ArrayList<Video> playlist) {
        this.activity = activity;
        this.playlist = playlist;
    }

    @Override
    public int getCount() { return playlist.size(); }

    @Override
    public Object getItem(int i) { return playlist.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        View v = convertView;

        if ( convertView == null ){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.playlist_item, null);
        }

        Video video = playlist.get(i);

        ImageView thumbnail = v.findViewById(R.id.videoThumbnail);
        Picasso.with(v.getContext()).load(video.getThumbnail()).into(thumbnail);

        TextView title = v.findViewById(R.id.videoTitle);
        title.setText(video.getTitle());

        TextView description = v.findViewById(R.id.videoDescription);
        description.setText(video.getDescription());

        TextView statistics = v.findViewById(R.id.videoStatistics);
        statistics.setText(video.getStatistics());

        return v;
    }
}
