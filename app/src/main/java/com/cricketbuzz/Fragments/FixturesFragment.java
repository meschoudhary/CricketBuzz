package com.cricketbuzz.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.cricketbuzz.R;
import com.cricketbuzz.Adapters.FixturesAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import com.cricketbuzzsdk.FixturesData.FixturesFullData;
import com.cricketbuzzsdk.MatchesData.fixtures.Matches;


public class FixturesFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private Context mContext;
    private Spinner sp_series;
    private ArrayList<String> seriesname;

    public static FixturesFragment create(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FixturesFragment fragment = new FixturesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sch, container,
                false);

        view.setBackgroundColor(Color.parseColor("#00000000"));

        mContext = getActivity().getApplicationContext();

        AdView adView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        final RecyclerView recList = (RecyclerView) view
                .findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        sp_series = (Spinner) view.findViewById(R.id.sp_sch);

        if (mPage == 1) {


            seriesname = new ArrayList<String>();

            // Get Series Name

            final FixturesFullData load = new FixturesFullData();
            seriesname = load.loadSeriesName(mPage);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_spinner_item,
                    seriesname);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_series.setAdapter(adapter);

            // Get Scheduled Matches

            List<Matches> schedule_match = new ArrayList<Matches>(load.loadScheduleMatch(sp_series.getSelectedItem().toString()));
            FixturesAdapter fa = new FixturesAdapter(getActivity(),
                    recList, schedule_match);
            recList.setAdapter(fa);

            sp_series.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    // TODO Auto-generated method stub

                    // Get Schedule Matches For Selected Series

                    List<Matches> schedule_match1 = new ArrayList<Matches>(load.loadScheduleMatch(parent.getItemAtPosition(position).toString()));

                    FixturesAdapter fa = new FixturesAdapter(
                            getActivity(), recList, schedule_match1);
                    recList.setAdapter(fa);
                    fa.notifyDataSetChanged();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub

                }
            });

        } else if (mPage == 2) {


            seriesname = new ArrayList<String>();

            // Get Series Name

            final FixturesFullData load = new FixturesFullData();
            seriesname = load.loadSeriesName(mPage);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_spinner_item,
                    seriesname);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_series.setAdapter(adapter);

            // Get Scheduled Matches

            List<Matches> schedule_match = new ArrayList<Matches>(load.loadScheduleMatch(sp_series.getSelectedItem().toString()));

            FixturesAdapter fa = new FixturesAdapter(getActivity(),
                    recList, schedule_match);
            recList.setAdapter(fa);

            sp_series.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    // TODO Auto-generated method stub

                    // Get Scheduled Matches For Selected Series

                    List<Matches> schedule_match1 = new ArrayList<Matches>(load.loadScheduleMatch(parent.getItemAtPosition(position).toString()));
                    FixturesAdapter fa = new FixturesAdapter(
                            getActivity(), recList, schedule_match1);
                    recList.setAdapter(fa);
                    fa.notifyDataSetChanged();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub

                }
            });

        } else if (mPage == 3) {


            seriesname = new ArrayList<String>();

            // Get Series Name
            final FixturesFullData load = new FixturesFullData();
            seriesname = load.loadSeriesName(mPage);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_spinner_item,
                    seriesname);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_series.setAdapter(adapter);

            // Get Scheduled Matches

            List<Matches> schedule_match = new ArrayList<Matches>(load.loadScheduleMatch(sp_series.getSelectedItem().toString()));

            FixturesAdapter fa = new FixturesAdapter(getActivity(),
                    recList, schedule_match);
            recList.setAdapter(fa);

            sp_series.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    // TODO Auto-generated method stub

                    // Get Scheduled Matches For Selected Series

                    List<Matches> schedule_match1 = new ArrayList<Matches>(load.loadScheduleMatch(parent.getItemAtPosition(position).toString()));

                    FixturesAdapter fa = new FixturesAdapter(
                            getActivity(), recList, schedule_match1);
                    recList.setAdapter(fa);
                    fa.notifyDataSetChanged();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub

                }
            });

        }

        return view;

    }
}
