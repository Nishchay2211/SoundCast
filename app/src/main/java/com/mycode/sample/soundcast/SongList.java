package com.mycode.sample.soundcast;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface SongList {
    @Headers({"X-Parse-Application-Id:VSPdpDKRMND382hqIRFIaiVLgbkhM0E1rL32l1SQ",
            "X-Parse-REST-API-Key:E4ZeObhQv3XoHaQ3Q6baHGgbDPOkuO9jPlY9gzgA",
            "Content-Type:application/json"
    })
    @GET("songs_library")
    Observable<GetSongDetails> getSongDetails();
    @Streaming
    @GET
    Observable<Response<ResponseBody>> downloadFileByUrlRx(@Url String fileUrl);

    @Streaming
    @GET
    Observable<Response<ResponseBody>> downloadFileImageByUrlRx(@Url String fileUrl);
}
