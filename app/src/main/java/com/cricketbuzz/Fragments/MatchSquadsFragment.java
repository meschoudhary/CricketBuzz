package com.cricketbuzz.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cricketbuzz.R;
import com.cricketbuzz.Adapters.MatchSquadAdapter;

import com.cricketbuzzsdk.MatchSquadData.SquadsData;


public class MatchSquadsFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private Context mContext;

    public static MatchSquadsFragment create(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MatchSquadsFragment fragment = new MatchSquadsFragment();
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

        View view = inflater.inflate(R.layout.fragment_home,
                container, false);

        mContext = getActivity().getApplicationContext();

        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rl_top);

        RecyclerView recList = (RecyclerView) view
                .findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        // Get Squads Data
        SquadsData load = new SquadsData(rl, getActivity());

        if (mPage == 1) {

            MatchSquadAdapter sa = new MatchSquadAdapter(
                    mContext, recList, load.getMatchSquad(mPage), load.getPlayer());
            recList.setAdapter(sa);

        } else if (mPage == 2) {


            MatchSquadAdapter sa = new MatchSquadAdapter(
                    mContext, recList, load.getMatchSquad(mPage), load.getPlayer());
            recList.setAdapter(sa);

        }

        return view;

    }

}