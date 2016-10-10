package com.cricketbuzz.Activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.cricketbuzz.R;
import com.cricketbuzzsdk.DataHolder.Data;
import com.cricketbuzzsdk.DataHolder.c;
import com.cricketbuzzsdk.NewsData.NewsData;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class DetailNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailnews);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.titleview, null);

        ((TextView) v.findViewById(R.id.title)).setText(Data.getDetail_news()
                .getHline());

        this.getSupportActionBar().setCustomView(v);

        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        NetworkImageView news = (NetworkImageView) findViewById(R.id.img_news);

        ImageLoader mImageLoader = c.getInstance(this)
                .getImageLoader();

        Bundle b = getIntent().getExtras();

        news.setImageUrl(b.getString("detail_img"), mImageLoader);

        TextView detail = (TextView) findViewById(R.id.tv_news);

        // Get Detail News
        NewsData load = new NewsData();
        String detailnews = load.getNewss(DetailNewsActivity.this);

        detail.setText(detailnews);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
