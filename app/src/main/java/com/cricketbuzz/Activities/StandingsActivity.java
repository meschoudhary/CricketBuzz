package com.cricketbuzz.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cricketbuzz.R;
import com.cricketbuzz.Fragments.StandingFragment;
import com.cricketbuzz.Tabs.SlidingTabLayout;
import com.cricketbuzz.Utils.SplashProgress;
import com.cricketbuzzsdk.DataHolder.Data;
import com.cricketbuzzsdk.Listeners.StandigsFullDataListener;
import com.cricketbuzzsdk.StandigsData.StandigsFullData;
import com.cricketbuzzsdk.StandigsData.StandingsData;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;


public class StandingsActivity extends AppCompatActivity implements StandigsFullDataListener {

    public static ViewPager mViewPager;
    private InterstitialAd interstitial;
    private Spinner sp_series;
    private SplashProgress progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);

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

        ((TextView) v.findViewById(R.id.title)).setText("STANDINGS");

        this.getSupportActionBar().setCustomView(v);

        interstitial = new InterstitialAd(this);
        interstitial
                .setAdUnitId(getString(R.string.Admob_Intrestial));
        AdRequest adRequest1 = new AdRequest.Builder().build();

        interstitial.loadAd(adRequest1);

        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

                if (interstitial.isLoaded()) {
                    interstitial.show();
                } else {
                    Log.d("errorrrrrrrrr",
                            "Interstitial ad was not ready to be shown.");
                }
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }
        });

        mViewPager = (ViewPager) findViewById(R.id.pager);

        sp_series = (Spinner) findViewById(R.id.sp_sch);


        // Get spinner data

        final StandingsData standingsDataLoad = new StandingsData();
        ArrayList<String> series_names = new ArrayList<String>(standingsDataLoad.loadSeriesNames());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                StandingsActivity.this,
                android.R.layout.simple_spinner_item, series_names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_series.setAdapter(adapter);

        sp_series.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // Get Selected Series Data

                progress = new SplashProgress(
                        StandingsActivity.this);
                progress.setCancelable(false);
                progress.show();

                // Get Point Data

                StandigsFullData standingsDataLoad = new StandigsFullData();
                standingsDataLoad.loadPointUrl(StandingsActivity.this, position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

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

    @Override
    public void receiveData(int status, String response) {
        Toast.makeText(this, response, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void receiveData(int status) {
        progress.dismiss();
        mViewPager.setAdapter(new SectionsPagerAdapter());
        try {
            mViewPager.getAdapter().notifyDataSetChanged();
        } catch (Exception e) {
            // TODO: handle exception
        }
        SlidingTabLayout SlidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        SlidingTabLayout.setCustomTabView(R.layout.tab_indicator, R.id.text1);
        SlidingTabLayout
                .setSelectedIndicatorColors(getResources()
                        .getColor(R.color.indicator));
        SlidingTabLayout.setDistributeEvenly(true);
        SlidingTabLayout.setViewPager(mViewPager);
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {

            return StandingFragment.create(position + 1);
        }

        @Override
        public int getCount() {
            return Data.getPoint_sublist().size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            // Get Standing Titles
            
            StandigsFullData standingActivityDataLoad = new StandigsFullData();
            return standingActivityDataLoad.loadPageTitle(position);
        }
    }

}
