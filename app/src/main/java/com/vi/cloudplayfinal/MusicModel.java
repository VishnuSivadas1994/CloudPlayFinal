package com.vi.cloudplayfinal;

/**
 * Created by vi on 2018-02-19.
 */

public class MusicModel {

    public String sName,sArtist,sUrl;

    public MusicModel() {
    }

    public MusicModel(String sName, String sArtist, String sUrl) {
        this.sName = sName;
        this.sArtist = sArtist;
        this.sUrl = sUrl;
    }


    public String getsName() {
        return sName;
    }

    public String getsArtist() {
        return sArtist;
    }

    public String getsUrl() {
        return sUrl;
        //Toast.makeText(this,sUrl,Toast.LENGTH_SHORT).show();
    }
}
