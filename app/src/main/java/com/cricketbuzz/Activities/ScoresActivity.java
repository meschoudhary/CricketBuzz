package com.cricketbuzz.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cricketbuzz.R;
import com.cricketbuzz.Fragments.BallbyBallFragment;
import com.cricketbuzz.Fragments.ScoresFragment;
import com.cricketbuzz.Fragments.SquadsFragment;
import com.cricketbuzz.Tabs.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.cricketbuzzsdk.Listeners.RefreshListener;
import com.cricketbuzzsdk.ScoresData.ScoresFullData;

public class ScoresActivity extends AppCompatActivity implements com.cricketbuzz.Listeners.RefreshListener, RefreshListener {

    private ViewPager mViewPager;
    private Timer timer;
    private Context myContext;
    private SectionsPagerAdapter adapter;
    private TimerTask hourlyTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

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

        ((TextView) v.findViewById(R.id.title))
                .setText(getString(R.string.app_name));

        this.getSupportActionBar().setCustomView(v);

        myContext = getApplicationContext();

        mViewPager = (ViewPager) findViewById(R.id.pager);

        adapter = new SectionsPagerAdapter();

        adapter.addFragment(BallbyBallFragment.create());
        adapter.addFragment(ScoresFragment.create());
        adapter.addFragment(SquadsFragment.create());

        mViewPager.setAdapter(adapter);

        SlidingTabLayout SlidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        SlidingTabLayout.setCustomTabView(
                R.layout.tab_indicator, R.id.text1);
        SlidingTabLayout.setSelectedIndicatorColors(getResources()
                .getColor(R.color.indicator));
        SlidingTabLayout.setDistributeEvenly(true);
        SlidingTabLayout.setViewPager(mViewPager);

        final Bundle b = getIntent().getExtras();

        timer = new Timer();
        hourlyTask = new TimerTask() {

            @Override
            public void run() {

                ScoresFullData load = new ScoresFullData();
                load.loadData(myContext, b, ScoresActivity.this);

            }
        };

        // Auto refresh Scores
        if (b.getBoolean("live")) {

            // Every 1 Minute Refresh
            timer.schedule(hourlyTask, 60000, 60000);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            startActivity(new Intent(ScoresActivity.this,
                    MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_NEW_TASK));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        timer.cancel();
        hourlyTask.cancel();

        startActivity(new Intent(ScoresActivity.this,
                MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public void update() {

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void RefreshDone(Boolean data) {

        if (data) {
            update();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Something Went Wrong. Please Try Again. !!!", Toast.LENGTH_SHORT).show();
        }
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {


        private final List<Fragment> mFragments = new ArrayList<>();


        public SectionsPagerAdapter() {
            super(getSupportFragmentManager());
        }


        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            if (object instanceof com.cricketbuzz.Listeners.RefreshListener) {
                ((com.cricketbuzz.Listeners.RefreshListener) object).update();
            }
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Ball by Ball";
                case 1:
                    return "Scores";
                case 2:
                    return "Squads";
            }
            return null;
        }
    }

}
