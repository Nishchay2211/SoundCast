package com.mycode.sample.soundcast.Adapter;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.mycode.sample.soundcast.R;
import com.mycode.sample.soundcast.SongList;
import com.mycode.sample.soundcast.model.Result;

import java.io.File;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;

import static com.mycode.sample.soundcast.Constants.isDownloaded;
import static com.mycode.sample.soundcast.RetrofitClient.createService;


public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {
    Context context;
    List<Result> getResult;
    public static String getSongNameToDisplay;
    File destinationFile;
    public static SongViewHolder myView;
    public static File file;
    public SongAdapter(Context context, List<Result> getResult) {
        this.context = context;
        this.getResult = getResult;
    }
    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.song_cardview_display,viewGroup,false);
        return new SongViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull SongViewHolder songViewHolder, final int i) {
        myView = songViewHolder;
        if (getResult != null){
            if (getResult.get(i).getTitle()!=null && getResult.get(i).getLink().contains(".mp3")){
                if (getResult.get(i).getThumbnail()!= null){
                    if (!getResult.get(i).getThumbnail().contains(".jpeg")&&!getResult.get(i).getThumbnail().contains(".jpg")){
                        Picasso.with(context).load(R.drawable.musicicon).into(songViewHolder.imageViewSong);
                    }else {
                        songViewHolder.tv_title.setText(getResult.get(i).getTitle());
                        Log.d("TAG !!!!!",""+getResult.get(i).getTitle());
                        Picasso.with(context).load(getResult.get(i).getThumbnail()).into(songViewHolder.imageViewSong);
                    }
                }

            }

            songViewHolder.downloadSongButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getSongNameToDisplay = getResult.get(i).getTitle();
                    SongList downloadService = createService(SongList.class, "https://static.talview.com/");
                    downloadService.downloadFileByUrlRx("hiring/android/soundcast/mp3/fast-and-furious.mp3")
                            .flatMap(processResponse())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(handleResult());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return getResult.size();
    }
    private Function<Response<ResponseBody>, io.reactivex.Observable<File>>processResponse(){
        return new Function<Response<ResponseBody>, io.reactivex.Observable<File>>() {
            @Override
            public io.reactivex.Observable<File> apply(Response<ResponseBody> responseBodyResponse) throws Exception {
                return saveToDiskRx(responseBodyResponse);
            }
        };
    }
    private io.reactivex.Observable<File> saveToDiskRx(final Response<ResponseBody> responseBodyResponse) {
        return io.reactivex.Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> subscriber) throws Exception {
                String header = responseBodyResponse.headers().get("Content-Disposition");
                //String filename = header.replace("attachment; filename=", "");
               File myFile =  new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Alarms/" + context.getPackageName()+ "/games/");
                if (!myFile.exists()){
                    myFile.mkdirs();
                    destinationFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+
                            "/Alarms/" + context.getPackageName() + "/games/"+getSongNameToDisplay+".mp3");
                    BufferedSink bufferedSink = Okio.buffer(Okio.sink(destinationFile));
                    bufferedSink.writeAll(responseBodyResponse.body().source());
                    bufferedSink.close();
                    subscriber.onNext(destinationFile);
                    subscriber.onComplete();
                }else {
                    destinationFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+
                            "/Alarms/" + context.getPackageName() + "/games/"+getSongNameToDisplay+".mp3");
                    BufferedSink bufferedSink = Okio.buffer(Okio.sink(destinationFile));
                    bufferedSink.writeAll(responseBodyResponse.body().source());
                    bufferedSink.close();
                    subscriber.onNext(destinationFile);
                    subscriber.onComplete();
                }

            }
        });
    }
    private Observer<? super File> handleResult() {
        return new Observer<File>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("TAG", "File downloaded to ");
            }
            @Override
            public void onNext(File file) {
                Log.d("TAG", "File downloaded to " + file.getAbsolutePath());
            }
            @Override
            public void onError(Throwable e) {
                Log.d("TAG", "Error " + e.getMessage());
            }
            @Override
            public void onComplete() {
                Log.d("TAG", "onCompleted");
                file = new File(String.valueOf(destinationFile));
                if (file.exists()){
                    if (!isDownloaded){
                        isDownloaded = true;
                        myView.downloadSongButton.setBackgroundResource(R.drawable.play_song_icon);
                        notifyDataSetChanged();
                    }

                }else {
                    isDownloaded = false;
                }

            }
        };
    }

}
