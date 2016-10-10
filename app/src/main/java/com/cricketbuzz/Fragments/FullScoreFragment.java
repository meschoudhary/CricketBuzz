package com.cricketbuzz.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cricketbuzz.R;
import com.cricketbuzz.Adapters.Batsman_Adapter;
import com.cricketbuzz.Adapters.Bowling_Adapter;
import com.cricketbuzz.Adapters.Fow_Adapter;
import com.cricketbuzz.Adapters.Power_Adapter;
import com.cricketbuzz.Listeners.RefreshListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import com.cricketbuzzsdk.FullScoreData.FullScoreData;
import com.cricketbuzzsdk.Listeners.FullDataListener;
import com.cricketbuzzsdk.MatchesData.First;
import com.cricketbuzzsdk.MatchesData.Fourth;
import com.cricketbuzzsdk.MatchesData.Fow;
import com.cricketbuzzsdk.MatchesData.PowerPlays;
import com.cricketbuzzsdk.MatchesData.Second;
import com.cricketbuzzsdk.MatchesData.Third;

public class FullScoreFragment extends Fragment implements RefreshListener, FullDataListener {

    public static final String ARG_PAGE = "ARG_PAGE";
    private static Batsman_Adapter batsman_adapter;
    private static Bowling_Adapter bowling_adapter;
    private static Fow_Adapter fow_adapter;
    private static Power_Adapter power_adapter;
    private int mPage;
    private Context mContext;
    private SwipeRefreshLayout mPullToRefreshLayout;
    private RefreshListener mRefreshListener;
    private View view;

    public static FullScoreFragment create(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FullScoreFragment fragment = new FullScoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static void getListViewSize(ListView myListView) {

        if (myListView.getAdapter() == null) {
            return;
        }
        int totalHeight = 0;
        for (int size = 0; size < myListView.getAdapter().getCount(); size++) {
            View listItem = myListView.getAdapter().getView(size, null,
                    myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight
                + (myListView.getDividerHeight() * (myListView.getAdapter()
                .getCount() - 1));
        myListView.setLayoutParams(params);
    }

    @Override
    public void update() {
        mydata();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof RefreshListener) {
            mRefreshListener = (RefreshListener) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_scores,
                container, false);

        mContext = getActivity().getApplicationContext();

        // Initialize Listener
        FullScoreData.¢ª¥ = this;

        mydata();


        return view;

    }

    private void mydata() {

        AdView adView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        mPullToRefreshLayout = (SwipeRefreshLayout) view
                .findViewById(R.id.ptr_layout);

        mPullToRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Refresh Data
                FullScoreData load = new FullScoreData();
                load.refreshData(mContext);

            }
        });

        TextView tvfow;

        tvfow = (TextView) view.findViewById(R.id.tv_fow);

        FullScoreData fullScoreDataLoad = new FullScoreData();

        if (mPage == 1) {

            First first = fullScoreDataLoad.getFirst();

            try {

                String byes = first.getExtras().getByes();

                String legbyes = first.getExtras().getLegByes();

                String wides = first.getExtras().getWideBalls();

                String noballs = first.getExtras().getNoBalls();

                String penalty = first.getExtras().getPenalty();

                int total = Integer.parseInt(byes) + Integer.parseInt(legbyes)
                        + Integer.parseInt(wides) + Integer.parseInt(noballs)
                        + Integer.parseInt(penalty);

                TextView extras = (TextView) view
                        .findViewById(R.id.tv_extras);
                extras.setText("Extras(" + total + ")" + ":" + "Data-" + byes
                        + "," + "StandigsFullData-" + wides + "," + "nb-" + noballs + ","
                        + "lb-" + legbyes + "," + "penalty-" + penalty);

            } catch (Exception e) {
                // TODO: handle exception
            }

            TextView teamscore = (TextView) view
                    .findViewById(R.id.tv_teamscore);

            teamscore.setText(first.getBattingteam() + " "
                    + first.getTotal().getRuns() + "/"
                    + first.getTotal().getWickets() + "("
                    + first.getTotal().getOvers() + "overs" + ")");

            TextView totalscore = (TextView) view
                    .findViewById(R.id.tv_total);

            totalscore.setText("Total:" + first.getTotal().getRuns() + "/"
                    + first.getTotal().getWickets());
            TextView rrate = (TextView) view.findViewById(R.id.tv_rr);
            rrate.setText("RR:" + first.getTotal().getRurrate());

            ListView lv_batsman = (ListView) view
                    .findViewById(R.id.lv_batsman);
            batsman_adapter = new Batsman_Adapter(mContext,
                    first.getBatsmen());
            lv_batsman.setAdapter(batsman_adapter);
            getListViewSize(lv_batsman);

            ListView lv_bowling = (ListView) view
                    .findViewById(R.id.lv_bowling);
            bowling_adapter = new Bowling_Adapter(mContext,
                    first.getBowlers());
            lv_bowling.setAdapter(bowling_adapter);
            getListViewSize(lv_bowling);

            List<Fow> fow = first.getFow();

            try {

                int size = fow.size();

                if (size > 0) {

                    ListView lv_fow = (ListView) view
                            .findViewById(R.id.lv_fow);
                    fow_adapter = new Fow_Adapter(mContext, fow);
                    lv_fow.setAdapter(fow_adapter);
                    getListViewSize(lv_fow);

                }

            } catch (Exception e) {
                // TODO: handle exception

                tvfow.setVisibility(View.GONE);
            }


            LinearLayout ll_power = (LinearLayout) view
                    .findViewById(R.id.ll_powerplay);

            try {
                List<PowerPlays> powerplay = first.getPowerPlays();

                if (powerplay.size() > 0) {

                    ListView lv_powerplay = (ListView) view
                            .findViewById(R.id.lv_power);
                    power_adapter = new Power_Adapter(mContext, powerplay);
                    lv_powerplay.setAdapter(power_adapter);
                    getListViewSize(lv_powerplay);

                } else {
                    ll_power.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                ll_power.setVisibility(View.GONE);
            }
        } else if (mPage == 2) {

            Second second = fullScoreDataLoad.getSecond();

            try {

                String byes = second.getExtras().getByes();

                String legbyes = second.getExtras().getLegByes();

                String wides = second.getExtras().getWideBalls();

                String noballs = second.getExtras().getNoBalls();

                String penalty = second.getExtras().getPenalty();

                int total = Integer.parseInt(byes) + Integer.parseInt(legbyes)
                        + Integer.parseInt(wides) + Integer.parseInt(noballs)
                        + Integer.parseInt(penalty);

                TextView extras = (TextView) view
                        .findViewById(R.id.tv_extras);
                extras.setText("Extras(" + total + ")" + ":" + "Data-" + byes
                        + "," + "StandigsFullData-" + wides + "," + "nb-" + noballs + ","
                        + "lb-" + legbyes + "," + "penalty-" + penalty);

            } catch (Exception e) {
                // TODO: handle exception
            }

            try {


                TextView teamscore = (TextView) view
                        .findViewById(R.id.tv_teamscore);

                teamscore.setText(second.getBattingteam() + " "
                        + second.getTotal().getRuns() + "/"
                        + second.getTotal().getWickets() + "("
                        + second.getTotal().getOvers() + "overs" + ")");

                TextView totalscore = (TextView) view
                        .findViewById(R.id.tv_total);

                totalscore.setText("Total:" + second.getTotal().getRuns() + "/"
                        + second.getTotal().getWickets());
                TextView rrate = (TextView) view.findViewById(R.id.tv_rr);
                rrate.setText("RR:" + second.getTotal().getRurrate());

                ListView lv_batsman = (ListView) view
                        .findViewById(R.id.lv_batsman);
                batsman_adapter = new Batsman_Adapter(mContext,
                        second.getBatsmen());
                lv_batsman.setAdapter(batsman_adapter);
                getListViewSize(lv_batsman);

                ListView lv_bowling = (ListView) view
                        .findViewById(R.id.lv_bowling);
                bowling_adapter = new Bowling_Adapter(mContext,
                        second.getBowlers());
                lv_bowling.setAdapter(bowling_adapter);
                getListViewSize(lv_bowling);

            } catch (Exception e) {

            }

            try {

                List<Fow> fow = second.getFow();

                if (fow.size() > 0) {


                    ListView lv_fow = (ListView) view
                            .findViewById(R.id.lv_fow);
                    fow_adapter = new Fow_Adapter(mContext, fow);
                    lv_fow.setAdapter(fow_adapter);
                    getListViewSize(lv_fow);

                }

            } catch (Exception e) {
                // TODO: handle exception

                tvfow.setVisibility(View.GONE);
            }

            LinearLayout ll_power = (LinearLayout) view
                    .findViewById(R.id.ll_powerplay);

            try {

                List<PowerPlays> powerplay = second.getPowerPlays();

                if (powerplay.size() > 0) {

                    ListView lv_powerplay = (ListView) view
                            .findViewById(R.id.lv_power);
                    power_adapter = new Power_Adapter(mContext, powerplay);
                    lv_powerplay.setAdapter(power_adapter);
                    getListViewSize(lv_powerplay);

                } else {
                    ll_power.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                ll_power.setVisibility(View.GONE);
            }

        } else if (mPage == 3) {

            Third third = fullScoreDataLoad.getThird();

            try {

                String byes = third.getExtras().getByes();

                String legbyes = third.getExtras().getLegByes();

                String wides = third.getExtras().getWideBalls();

                String noballs = third.getExtras().getNoBalls();

                String penalty = third.getExtras().getPenalty();

                int total = Integer.parseInt(byes) + Integer.parseInt(legbyes)
                        + Integer.parseInt(wides) + Integer.parseInt(noballs)
                        + Integer.parseInt(penalty);

                TextView extras = (TextView) view
                        .findViewById(R.id.tv_extras);
                extras.setText("Extras(" + total + ")" + ":" + "Data-" + byes
                        + "," + "StandigsFullData-" + wides + "," + "nb-" + noballs + ","
                        + "lb-" + legbyes + "," + "penalty-" + penalty);

            } catch (Exception e) {
                // TODO: handle exception
            }

            try {


                TextView teamscore = (TextView) view
                        .findViewById(R.id.tv_teamscore);

                teamscore.setText(third.getBattingteam() + " "
                        + third.getTotal().getRuns() + "/"
                        + third.getTotal().getWickets() + "("
                        + third.getTotal().getOvers() + "overs" + ")");

                TextView totalscore = (TextView) view
                        .findViewById(R.id.tv_total);

                totalscore.setText("Total:" + third.getTotal().getRuns() + "/"
                        + third.getTotal().getWickets());
                TextView rrate = (TextView) view.findViewById(R.id.tv_rr);
                rrate.setText("RR:" + third.getTotal().getRurrate());

                ListView lv_batsman = (ListView) view
                        .findViewById(R.id.lv_batsman);
                batsman_adapter = new Batsman_Adapter(mContext,
                        third.getBatsmen());
                lv_batsman.setAdapter(batsman_adapter);
                getListViewSize(lv_batsman);

                ListView lv_bowling = (ListView) view
                        .findViewById(R.id.lv_bowling);
                bowling_adapter = new Bowling_Adapter(mContext,
                        third.getBowlers());
                lv_bowling.setAdapter(bowling_adapter);
                getListViewSize(lv_bowling);

            } catch (Exception e) {

            }

            try {

                List<Fow> fow = third.getFow();

                if (fow.size() == 0) {
                    tvfow.setVisibility(View.GONE);
                }

                if (fow.size() > 0) {


                    ListView lv_fow = (ListView) view
                            .findViewById(R.id.lv_fow);
                    fow_adapter = new Fow_Adapter(mContext, fow);
                    lv_fow.setAdapter(fow_adapter);
                    getListViewSize(lv_fow);

                }

            } catch (Exception e) {
                // TODO: handle exception
            }


            LinearLayout ll_power = (LinearLayout) view
                    .findViewById(R.id.ll_powerplay);

            try {

                List<PowerPlays> powerplay = third.getPowerPlays();

                if (powerplay.size() > 0) {

                    ListView lv_powerplay = (ListView) view
                            .findViewById(R.id.lv_power);
                    power_adapter = new Power_Adapter(mContext, powerplay);
                    lv_powerplay.setAdapter(power_adapter);
                    getListViewSize(lv_powerplay);

                } else {
                    ll_power.setVisibility(View.GONE);
                }

            } catch (Exception e) {

                ll_power.setVisibility(View.GONE);
            }
        } else if (mPage == 4) {

            Fourth fourth = fullScoreDataLoad.getFourth();

            try {

                String byes = fourth.getExtras().getByes();

                String legbyes = fourth.getExtras().getLegByes();

                String wides = fourth.getExtras().getWideBalls();

                String noballs = fourth.getExtras().getNoBalls();

                String penalty = fourth.getExtras().getPenalty();

                int total = Integer.parseInt(byes) + Integer.parseInt(legbyes)
                        + Integer.parseInt(wides) + Integer.parseInt(noballs)
                        + Integer.parseInt(penalty);

                TextView extras = (TextView) view
                        .findViewById(R.id.tv_extras);
                extras.setText("Extras(" + total + ")" + ":" + "Data-" + byes
                        + "," + "StandigsFullData-" + wides + "," + "nb-" + noballs + ","
                        + "lb-" + legbyes + "," + "penalty-" + penalty);

            } catch (Exception e) {
                // TODO: handle exception
            }


            try {

                TextView teamscore = (TextView) view
                        .findViewById(R.id.tv_teamscore);

                teamscore.setText(fourth.getBattingteam() + " "
                        + fourth.getTotal().getRuns() + "/"
                        + fourth.getTotal().getWickets() + "("
                        + fourth.getTotal().getOvers() + "overs" + ")");

                TextView totalscore = (TextView) view
                        .findViewById(R.id.tv_total);

                totalscore.setText("Total:" + fourth.getTotal().getRuns() + "/"
                        + fourth.getTotal().getWickets());
                TextView rrate = (TextView) view.findViewById(R.id.tv_rr);
                rrate.setText("RR:" + fourth.getTotal().getRurrate());

                ListView lv_batsman = (ListView) view
                        .findViewById(R.id.lv_batsman);
                batsman_adapter = new Batsman_Adapter(mContext,
                        fourth.getBatsmen());
                lv_batsman.setAdapter(batsman_adapter);
                getListViewSize(lv_batsman);

                ListView lv_bowling = (ListView) view
                        .findViewById(R.id.lv_bowling);
                bowling_adapter = new Bowling_Adapter(mContext,
                        fourth.getBowlers());
                lv_bowling.setAdapter(bowling_adapter);
                getListViewSize(lv_bowling);

            } catch (Exception e) {

            }

            try {

                List<Fow> fow = fourth.getFow();

                if (fow.size() == 0) {
                    tvfow.setVisibility(View.GONE);
                }

                if (fow.size() > 0) {


                    ListView lv_fow = (ListView) view
                            .findViewById(R.id.lv_fow);
                    fow_adapter = new Fow_Adapter(mContext, fow);
                    lv_fow.setAdapter(fow_adapter);
                    getListViewSize(lv_fow);

                }

            } catch (Exception e) {

                tvfow.setVisibility(View.GONE);
            }


            LinearLayout ll_power = (LinearLayout) view
                    .findViewById(R.id.ll_powerplay);


            try {

                List<PowerPlays> powerplay = fourth.getPowerPlays();

                if (powerplay.size() > 0) {

                    ListView lv_powerplay = (ListView) view
                            .findViewById(R.id.lv_power);
                    power_adapter = new Power_Adapter(mContext, powerplay);
                    lv_powerplay.setAdapter(power_adapter);
                    getListViewSize(lv_powerplay);

                } else {
                    ll_power.setVisibility(View.GONE);
                }

            } catch (Exception e) {

                ll_power.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void GetFullData(Boolean data) {

        mPullToRefreshLayout.setRefreshing(false);

        if (mRefreshListener != null) {
            mRefreshListener.update();
        }
    }
}