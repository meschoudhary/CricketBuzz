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
import com.cricketbuzz.Adapters.ResultAdapter;
import com.cricketbuzzsdk.MatchesData.ResultMatches;
import com.cricketbuzzsdk.ResultsData.ResultsAdapterData;
import com.cricketbuzzsdk.ResultsData.ResultsFullData;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;


public class ResultFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private Context mContext;
    private Spinner sp_series;

    public static ResultFragment create(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ResultFragment fragment = new ResultFragment();
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

            // Get International Series
            final ResultsFullData load = new ResultsFullData();
            ArrayList<String> seriesname = new ArrayList<String>(load.loadResultSeriesNames(mPage));

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_spinner_item,
                    seriesname);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_series.setAdapter(adapter);

            // Get Selected International Matches
            List<ResultMatches> matches = new ArrayList<ResultMatches>(load.loadSelectedMatches(sp_series.getSelectedItem().toString()));

            ResultAdapter fa = new ResultAdapter(getActivity(),
                    recList, matches);

            // Initialize Listener;
            ResultsAdapterData.lis¢ºa = fa;

            recList.setAdapter(fa);

            sp_series.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    // TODO Auto-generated method stub

                    // Get Selected International Matches

                    List<ResultMatches> series_jd = new ArrayList<ResultMatches>(load.loadSelectedMatches(parent.getItemAtPosition(position)
                            .toString()));

                    ResultAdapter fa = new ResultAdapter(
                            getActivity(), recList, series_jd);

                    // Initialize Listener;
                    ResultsAdapterData.lis¢ºa = fa;

                    recList.setAdapter(fa);
                    fa.notifyDataSetChanged();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub

                }
            });

        } else if (mPage == 2) {

            // Get Domestic Series
            final ResultsFullData load = new ResultsFullData();
            ArrayList<String> seriesname = new ArrayList<String>(load.loadResultSeriesNames(mPage));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_spinner_item,
                    seriesname);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_series.setAdapter(adapter);

            // Get Selected Domestic Matches

            List<ResultMatches> matches = new ArrayList<ResultMatches>(load.loadSelectedMatches(sp_series.getSelectedItem().toString()));

            ResultAdapter fa = new ResultAdapter(getActivity(),
                    recList, matches);

            // Initialize Listener;
            ResultsAdapterData.lis¢ºa = fa;

            recList.setAdapter(fa);

            sp_series.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    // TODO Auto-generated method stub

                    // Get Selected Domestic Matches

                    List<ResultMatches> series_jd = new ArrayList<ResultMatches>(load.loadSelectedMatches(parent.getItemAtPosition(position).toString()));

                    ResultAdapter fa = new ResultAdapter(
                            getActivity(), recList, series_jd);

                    // Initialize Listener;
                    ResultsAdapterData.lis¢ºa = fa;

                    recList.setAdapter(fa);
                    fa.notifyDataSetChanged();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub

                }
            });

        } else if (mPage == 3) {

            //Get T-20 Series
            final ResultsFullData load = new ResultsFullData();
            ArrayList<String> seriesname = new ArrayList<String>(load.loadResultSeriesNames(mPage));

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_spinner_item,
                    seriesname);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_series.setAdapter(adapter);

            // Get Selected T-20 Matches
            List<ResultMatches> matches = new ArrayList<ResultMatches>(load.loadSelectedMatches(sp_series.getSelectedItem().toString()));

            ResultAdapter fa = new ResultAdapter(getActivity(),
                    recList, matches);

            // Initialize Listener;
            ResultsAdapterData.lis¢ºa = fa;

            recList.setAdapter(fa);

            sp_series.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    // TODO Auto-generated method stub

                    // Load Selected T-20 Matches
                    List<ResultMatches> series_jd = new ArrayList<ResultMatches>(load.loadSelectedMatches(parent.getItemAtPosition(position).toString()));
                    ResultAdapter fa = new ResultAdapter(
                            getActivity(), recList, series_jd);

                    // Initialize Listener;
                    ResultsAdapterData.lis¢ºa = fa;

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
