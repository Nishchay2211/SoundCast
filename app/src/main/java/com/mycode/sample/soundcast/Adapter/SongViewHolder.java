package com.mycode.sample.soundcast.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.mycode.sample.soundcast.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public  TextView tv_title;
    public  Button downloadSongButton;
    public CircleImageView imageViewSong;
    public SongViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_title = (TextView)itemView.findViewById(R.id.textViewTitle);
        imageViewSong = (CircleImageView) itemView.findViewById(R.id.circleView);
        downloadSongButton = (Button) itemView.findViewById(R.id.downloadSongButton);
        itemView.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
       // itemClickListner.onClick(view,getAdapterPosition(),false);
    }
}
