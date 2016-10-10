package com.cricketbuzz.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketbuzz.R;
import com.cricketbuzz.Tabs.SlidingTabLayout;

import com.cricketbuzzsdk.DataHolder.Data;

public class SquadsFragment extends Fragment {

    static ViewPager viewPager;

    public static SquadsFragment create() {
        SquadsFragment fragment = new SquadsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_viewpager,
                container, false);

        viewPager = (ViewPager) view.findViewById(R.id.pager);
        viewPager.setAdapter(new MatchFragmentPagerAdapter(
                getChildFragmentManager()));

        SlidingTabLayout SlidingTabLayout = (SlidingTabLayout) view
                .findViewById(R.id.tabs);

        SlidingTabLayout.setCustomTabView(
                R.layout.tab_indicator, R.id.text1);
        SlidingTabLayout.setSelectedIndicatorColors(getResources()
                .getColor(R.color.indicator));
        SlidingTabLayout.setDistributeEvenly(true);
        SlidingTabLayout.setViewPager(viewPager);

        return view;

    }

    public class MatchFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public MatchFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {

            return 2;
        }

        @Override
        public Fragment getItem(int position) {

            return MatchSquadsFragment.create(position + 1);

        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (position == 0) {
                return Data.getFull_score().getTeam1().getsName();

            } else {
                return Data.getFull_score().getTeam2().getsName();
            }

        }
    }

}