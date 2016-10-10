package com.cricketbuzz.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cricketbuzz.R;
import com.cricketbuzz.Adapters.StandingAdapter;

import com.cricketbuzzsdk.StandigsData.StandingsData;

public class StandingFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private Context mContext;
    private ListView lv;

    public static StandingFragment create(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        StandingFragment fragment = new StandingFragment();
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

        View view = inflater.inflate(R.layout.fragment_standing,
                container, false);

        mContext = getActivity().getApplicationContext();

        lv = (ListView) view.findViewById(R.id.list_standing);

        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rl_bottom);

        //Load Table
        StandingsData standingsDataLoad = new StandingsData();
        StandingAdapter fa = new StandingAdapter(mContext, standingsDataLoad.getStandingTable(mPage, rl, getActivity()));
        lv.setAdapter(fa);

        return view;

    }

}