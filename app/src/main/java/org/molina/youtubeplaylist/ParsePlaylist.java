package org.molina.youtubeplaylist;

import android.util.Log;

import org.molina.youtubeplaylist.model.Video;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Vikin on 06/02/2018.
 */

class ParsePlaylist {

    private static final String TAG = "ParsePlaylist";

    private String xmlData;
    private ArrayList<Video> videos;

    public ParsePlaylist(String xmlData) {
        this.xmlData = xmlData;
        videos = new ArrayList<>();
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public boolean process(){
        boolean status = true;
        Video currenRecord = null;
        boolean inEntry = false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.xmlData));
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT){

                String tagName = xpp.getName();

                switch (eventType){
                    case XmlPullParser.START_TAG:
                        // Si entra en un tag entry significa que ha entrado en un video
                        if (tagName.equalsIgnoreCase("entry")){
                            inEntry = true;
                            currenRecord = new Video();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (inEntry){
                            if (tagName.equalsIgnoreCase("entry")){
                                videos.add(currenRecord);
                                inEntry = false;
                            } else if (tagName.equalsIgnoreCase("title")) {
                                currenRecord.setTitle(textValue);
                            }else if (tagName.equalsIgnoreCase("videoId")){
                                currenRecord.setVideoId(textValue);
                            } else if (tagName.equalsIgnoreCase("thumbnail")){
                                String url = xpp.getAttributeValue(null, "url");
                                currenRecord.setThumbnail(url);
                            } else if (tagName.equalsIgnoreCase("description")){
                                currenRecord.setDescription(textValue);
                            } else if (tagName.equalsIgnoreCase("statistics")){
                                int visitas = Integer.valueOf(xpp.getAttributeValue(null, "views"));
                                currenRecord.setStatistics("Visitas: " + visitas);
                            }
                        }
                        break;
                    default:
                        //
                }

                eventType = xpp.next();
            }
        } catch (Exception e) {
            Log.d(TAG, "Problema parseando el RSS" + e.getMessage());
            e.printStackTrace();
            status = false;
        }

        return status;
    }

    public Video getVideo(int i) {
        return videos.get(i);
    }
}
