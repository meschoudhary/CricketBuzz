package com.cricketbuzz.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.cricketbuzz.R;
import com.cricketbuzz.Activities.DetailNewsActivity;
import com.cricketbuzz.Utils.SplashProgress;

import java.util.List;

import com.cricketbuzzsdk.NewsData.NewsData;
import com.cricketbuzzsdk.DataHolder.a;
import com.cricketbuzzsdk.DataHolder.c;
import com.cricketbuzzsdk.Listeners.NewsFullDataListener;
import com.cricketbuzzsdk.MatchesData.news.stories;

public class NewsAdapter extends
        RecyclerView.Adapter<NewsAdapter.ContactViewHolder> implements NewsFullDataListener {

    Activity mactivity;
    RecyclerView recList;
    List<stories> list;
    ImageLoader mImageLoader;
    SplashProgress progress;
    int pos;


    public NewsAdapter(Activity context, RecyclerView recList,
                       List<stories> list2) {
        // TODO Auto-generated constructor stub
        this.mactivity = context;
        this.recList = recList;
        this.list = list2;
        mImageLoader = c.getInstance(context)
                .getImageLoader();
        progress = new SplashProgress(mactivity);
        progress.setCancelable(false);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder,
                                 int i) {

        contactViewHolder.hline.setText(list.get(i).getHline());

        contactViewHolder.news.setImageUrl(list.get(i).getImg().getIpath().replace("65x50", "200x200"),
                mImageLoader);

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.news_card, viewGroup, false);

        itemView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pos = recList.getChildPosition(v);

                if (!progress.isShowing()) {
                    progress.show();
                }


                NewsData load = new NewsData();
                load.fetchNews(mactivity, list.get(pos));
            }
        });

        return new ContactViewHolder(itemView);
    }

    @Override
    public void receiveData(int status) {

        if (progress.isShowing()) {
            progress.dismiss();
        }

        if (status == a.SUCCESS) {

            DisplayMetrics displaymetrics = new DisplayMetrics();
            mactivity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            int width = displaymetrics.widthPixels;

            mactivity.startActivity(new Intent(mactivity,
                    DetailNewsActivity.class).putExtra(
                    "detail_img", list.get(pos).getXimg().getIpath().replace("130x100", width + "x" + (height * 30 / 100))));

        }

    }

    @Override
    public void receiveData(int status, String response) {
        Toast.makeText(mactivity, response, Toast.LENGTH_LONG)
                .show();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView hline;
        protected NetworkImageView news;

        public ContactViewHolder(View v) {
            super(v);
            hline = (TextView) v.findViewById(R.id.tv_hline);
            news = (NetworkImageView) v.findViewById(R.id.img_news);

        }
    }

}
