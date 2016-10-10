package com.cricketbuzz.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cricketbuzz.R;
import com.cricketbuzz.Adapters.HomeAdapter;
import com.cricketbuzz.Adapters.LiveMatchesAdapter;
import com.cricketbuzzsdk.FixturesData.FixturesData;
import com.cricketbuzzsdk.NewsData.NewsData;
import com.cricketbuzzsdk.ResultsData.ResultsData;
import com.cricketbuzzsdk.ScoresData.ScoresData;
import com.cricketbuzzsdk.StandigsData.StandingsData;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class MainActivity extends AppCompatActivity {

    private ViewPager Viewpager_live;
    private PageIndicator mIndicator;
    private Context mContext;
    private RecyclerView recList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        Viewpager_live = (ViewPager) findViewById(R.id.pager_live);

        LiveMatchesAdapter adapter = new LiveMatchesAdapter(this);
        
        // Inialize Listener
        ScoresData.£Þæ = adapter;

        Viewpager_live.setAdapter(adapter);

        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(Viewpager_live);

        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        recList.setLayoutManager(new GridLayoutManager(mContext, 2));

        HomeAdapter ha = new HomeAdapter(this);

        // Iniatiaze Listeners
        FixturesData.¢ª¥ = ha;
        ResultsData.¢ª¥ = ha;
        StandingsData.¢ª¥ = ha;
        NewsData.¢ª¥ = ha;

        recList.setAdapter(ha);

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        MainActivity.this.finish();
    }

}
