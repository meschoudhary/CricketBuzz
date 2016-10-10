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
import android.widget.TextView;

import com.cricketbuzz.R;
import com.cricketbuzz.Fragments.FixturesFragment;
import com.cricketbuzz.Tabs.SlidingTabLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class FixturesActivity extends AppCompatActivity {

    public static ViewPager mViewPager;
    private InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
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

        ((TextView) v.findViewById(R.id.title)).setText("FIXTURES");

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
        mViewPager.setAdapter(new SectionsPagerAdapter());

        SlidingTabLayout SlidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        SlidingTabLayout.setCustomTabView(
                R.layout.tab_indicator, R.id.text1);
        SlidingTabLayout.setSelectedIndicatorColors(getResources()
                .getColor(R.color.indicator));
        SlidingTabLayout.setDistributeEvenly(true);
        SlidingTabLayout.setViewPager(mViewPager);

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

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {

            return FixturesFragment.create(position + 1);
        }

        @Override
        public int getCount() {

            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "International Fixtures";
                case 1:
                    return "Domestic Fixtures";
                case 2:
                    return "T20 Leagues";
                default:
                    return "";
            }
        }
    }

}
