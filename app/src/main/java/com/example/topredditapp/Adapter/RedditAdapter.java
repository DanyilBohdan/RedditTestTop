package com.example.topredditapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.topredditapp.Activity.ImageActivity;
import com.example.topredditapp.Image;
import com.example.topredditapp.Model.Dat;
import com.example.topredditapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RedditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private Context par;
    private List<Dat> redditList;
    private Dat reddit;

    public RedditAdapter (Context parent, List<Dat> List){
        this.redditList = List;
        par = parent;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new RedditViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof RedditViewHolder) {
            populateItemRows((RedditViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
        }
    }

    @Override
    public int getItemCount() {
        return redditList.size();
    }

    public String convertUnix(Long Un){
        String date = new java.text.SimpleDateFormat("HH").format(new java.util.Date(System.currentTimeMillis()/1000 - Un*1000));
        return date;
    }

class RedditViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView tv_subreddit;
    TextView tv_author;
    TextView tv_time;
    TextView tv_title;
    TextView tv_comment;

        public RedditViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_subreddit = itemView.findViewById(R.id.tv_subreddit);
            tv_author = itemView.findViewById(R.id.tv_author);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_comment = itemView.findViewById(R.id.tv_coment);
            imageView = itemView.findViewById(R.id.imv_title);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int positionIndex = getAdapterPosition();
                    Dat inID = redditList.get(positionIndex);
                    Intent intent = new Intent(par, ImageActivity.class);
                    Image.im = inID;
                    par.startActivity(intent);
                }
            });
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void populateItemRows(RedditViewHolder holder, int position) {
        reddit = redditList.get(position);
        holder.tv_title.setText(reddit.getDat().getTitle());
        holder.tv_comment.setText(String.valueOf(reddit.getDat().getNum_comments())+" Comments");
        holder.tv_author.setText("Posted by u/"+reddit.getDat().getAuthor());
        holder.tv_subreddit.setText(reddit.getDat().getSubreddit());
        holder.tv_time.setText(convertUnix(reddit.getDat().getCreated()) + " hours ago");
        Picasso.get()
                .load(reddit.getDat().getThumbnail())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .resize(reddit.getDat().getThumbnail_width()+250, reddit.getDat().getThumbnail_height()+250)
                .into(holder.imageView);
    }
}


