package com.mycode.sample.soundcast.Utils;


import com.mycode.sample.soundcast.RetrofitClient;
import com.mycode.sample.soundcast.SongList;


public class ApiUtils {
    public static final String BASE_URL = "https://parseapi.back4app.com/classes/";
    public static SongList getSongService(){
        return RetrofitClient.getClient(BASE_URL).create(SongList.class);
    }
}
