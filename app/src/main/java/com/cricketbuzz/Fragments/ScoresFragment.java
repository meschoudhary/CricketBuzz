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
import com.cricketbuzz.Listeners.RefreshListener;
import com.cricketbuzz.Tabs.SlidingTabLayout;

import com.cricketbuzzsdk.DataHolder.Data;

public class ScoresFragment extends Fragment implements RefreshListener {

    private ViewPager viewPager;
    private MatchFragmentPagerAdapter adapter;

    public static ScoresFragment create() {
        ScoresFragment fragment = new ScoresFragment();
        return fragment;
    }

    @Override
    public void update() {

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

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

        adapter = new MatchFragmentPagerAdapter(
                getChildFragmentManager());

        viewPager.setAdapter(adapter);

        SlidingTabLayout SlidingTabLayout = (SlidingTabLayout) view
                .findViewById(R.id.tabs);

        SlidingTabLayout.setCustomTabView(
                R.layout.tab_indicator, R.id.text1);
        SlidingTabLayout.setSelectedIndicatorColors(getResources()
                .getColor(R.color.indicator));
        SlidingTabLayout.setDistributeEvenly(true);
        SlidingTabLayout.setViewPager(viewPager);

        viewPager.setCurrentItem(Integer.parseInt(Data.getFull_score()
                .getHeader().getNoOfIngs()) - 1);

        return view;

    }

    public class MatchFragmentPagerAdapter extends FragmentStatePagerAdapter {

        int PAGE_COUNT;
        String team_2, score_2, team_3, score_3, team_4, score_4;

        public MatchFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

        }

        @Override
        public int getCount() {

            return PAGE_COUNT = Integer.parseInt(Data.getFull_score()
                    .getHeader().getNoOfIngs());
        }

        @Override
        public Fragment getItem(int position) {
            return FullScoreFragment.create(position + 1);
        }


        @Override
        public int getItemPosition(Object object) {
            if (object instanceof RefreshListener) {
                ((RefreshListener) object).update();
            }
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            String team_1 = Data.getFull_score().getInnings().getFirst()
                    .getBattingteam();
            String score_1 = Data.getFull_score().getInnings().getFirst()
                    .getTotal().getRuns()
                    + "/"
                    + Data.getFull_score().getInnings().getFirst()
                    .getTotal().getWickets()
                    + "("
                    + Data.getFull_score().getInnings().getFirst()
                    .getTotal().getOvers() + ")";

            try {

                team_2 = Data.getFull_score().getInnings().getSecond()
                        .getBattingteam();
                score_2 = Data.getFull_score().getInnings().getSecond()
                        .getTotal().getRuns()
                        + "/"
                        + Data.getFull_score().getInnings().getSecond()
                        .getTotal().getWickets()
                        + "("
                        + Data.getFull_score().getInnings().getSecond()
                        .getTotal().getOvers() + ")";

                team_3 = Data.getFull_score().getInnings().getThird()
                        .getBattingteam();
                score_3 = Data.getFull_score().getInnings().getThird()
                        .getTotal().getRuns()
                        + "/"
                        + Data.getFull_score().getInnings().getThird()
                        .getTotal().getWickets()
                        + "("
                        + Data.getFull_score().getInnings().getThird()
                        .getTotal().getOvers() + ")";

                team_4 = Data.getFull_score().getInnings().getFourth()
                        .getBattingteam();
                score_4 = Data.getFull_score().getInnings().getFourth()
                        .getTotal().getRuns()
                        + "/"
                        + Data.getFull_score().getInnings().getFourth()
                        .getTotal().getWickets()
                        + "("
                        + Data.getFull_score().getInnings().getFourth()
                        .getTotal().getOvers() + ")";

            } catch (Exception e) {
                // TODO: handle exception
            }
            if (PAGE_COUNT == 1) {
                if (position == 0) {

                    return team_1 + " " + score_1;
                }
            } else if (PAGE_COUNT == 2) {

                if (position == 0)
                    return team_1 + " " + score_1;
                else
                    return team_2 + " " + score_2;

            } else if (PAGE_COUNT == 3) {
                if (position == 0) {
                    return team_1 + " " + score_1;
                } else if (position == 1) {
                    return team_2 + " " + score_2;
                } else {
                    return team_3 + " " + score_3;
                }
            } else {
                if (position == 0) {
                    return team_1 + " " + score_1;
                } else if (position == 1) {
                    return team_2 + " " + score_2;
                } else if (position == 2) {
                    return team_3 + " " + score_3;
                } else {
                    return team_4 + " " + score_4;
                }
            }
            return null;

        }
    }

}