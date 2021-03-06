package org.molina.youtubeplaylist.model;

import java.io.Serializable;

/**
 * Created by Vikin on 06/02/2018.
 */

public class Video implements Serializable{
    private String thumbnail;
    private String title;
    private String description;
    private String videoId;
    private String statistics;

    public Video() {
    }

    public Video(String thumbnail, String title, String description, String content, String statistics) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.description = description;
        this.videoId = content;
        this.statistics = statistics;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getStatistics() {
        return statistics;
    }

    public void setStatistics(String statistics) {
        this.statistics = statistics;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Video{" +
                "thumbnail='" + thumbnail + '\'' +
                ", title='" + title + '\'' +
                ", videoId='" + videoId + '\'' +
                ", statistics='" + statistics + '\'' +
                '}';
    }
}
