package com.cricketbuzz.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.cricketbuzz.R;
import com.cricketbuzz.Adapters.CommentryAdapter;
import com.cricketbuzz.Listeners.RefreshListener;
import com.cricketbuzzsdk.DataHolder.Data;
import com.cricketbuzzsdk.DataHolder.c;
import com.cricketbuzzsdk.Listeners.BallDataListener;
import com.cricketbuzzsdk.MatchesData.MiniScoreCard;
import com.cricketbuzzsdk.ScoresData.BallbyBallData;

public class BallbyBallFragment extends Fragment implements RefreshListener, BallDataListener {

    ImageLoader mImageLoader;
    NetworkImageView flag1, flag2;
    TextView team1, team2, header, team1score, team2score, team1over, team2over, result, desc;
    TextView prevover, prev_over;
    LinearLayout over;
    RecyclerView recList;
    Context mContext;
    SwipeRefreshLayout mPullToRefreshLayout;
    RefreshListener mRefreshListener;
    BallbyBallData load;

    public static BallbyBallFragment create() {
        BallbyBallFragment fragment = new BallbyBallFragment();
        return fragment;
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
    }

    @Override
    public void update() {

        mydata();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ball, container, false);

        mContext = getActivity().getApplicationContext();

        load = new BallbyBallData(this);

        mPullToRefreshLayout = (SwipeRefreshLayout) view
                .findViewById(R.id.ptr_layout);

        over = (LinearLayout) view.findViewById(R.id.ll_over);

        flag1 = (NetworkImageView) view.findViewById(R.id.img_team1_flag);
        flag2 = (NetworkImageView) view.findViewById(R.id.img_team2_flag);
        team1 = (TextView) view.findViewById(R.id.tv_team1);
        header = (TextView) view.findViewById(R.id.tv_header);
        team1score = (TextView) view.findViewById(R.id.tv_team1_score);
        team1over = (TextView) view.findViewById(R.id.tv_team1_overs);
        team2 = (TextView) view.findViewById(R.id.tv_team2);
        team2score = (TextView) view.findViewById(R.id.tv_team2_score);
        team2over = (TextView) view.findViewById(R.id.tv_team2_overs);
        result = (TextView) view.findViewById(R.id.tv_match_result);
        desc = (TextView) view.findViewById(R.id.tv_desc);

        prevover = (TextView) view.findViewById(R.id.tv_over);
        prev_over = (TextView) view.findViewById(R.id.tv_prevover);

        recList = (RecyclerView) view.findViewById(R.id.cardList);

        mydata();

        return view;

    }

    private void mydata() {

        mPullToRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                //Pull To Refresh
                load.performTask();

            }
        });


        mImageLoader = c.getInstance(mContext).getImageLoader();

        result.setSelected(true);

        MiniScoreCard data = Data.getMini_score();

        try {
            if (data.getMiniscore().getPrevOvers().equals("")) {
                over.setVisibility(View.GONE);
            } else {
                prev_over.setText(data.getMiniscore().getPrevOvers());
            }
        } catch (Exception e) {
            // TODO: handle exception

        }

        String team1_flag = load.loadTeam1Flag(data);
        String team2_flag = load.loadTeam2Flag(data);

        flag1.setImageUrl(team1_flag, mImageLoader);
        flag2.setImageUrl(team2_flag, mImageLoader);

        String batteam, bowlteam;

        desc.setText(data.getHeader().getMchDesc());

        if (data.getMiniscore().getBatteamid().equals(data.getTeam1().getId())) {

            batteam = data.getTeam1().getsName();
            bowlteam = data.getTeam2().getsName();

        } else {

            batteam = data.getTeam2().getsName();
            bowlteam = data.getTeam1().getsName();

        }
        team1.setText(batteam);
        team2.setText(bowlteam);

        header.setText(data.getSrs());
        result.setText(data.getHeader().getStatus());

        team1score.setText(data.getMiniscore().getBatteamscore());

        team1over.setText(data.getMiniscore().getOvers() + " overs");

        team2score.setText(data.getMiniscore().getBowlteamscore());

        team2over.setText(data.getMiniscore().getBowlteamovers() + " overs");


        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        recList.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        CommentryAdapter ca = new CommentryAdapter(getActivity(), recList,
                load.getCommentry());
        recList.setAdapter(ca);


    }


    @Override
    public void GetBallData(Boolean data) {
        if (data) {

            mPullToRefreshLayout.setRefreshing(false);

            if (mRefreshListener != null) {

                mRefreshListener.update();
            }

        } else {

            Toast.makeText(getActivity(),
                    "Something Went Wrong. Please Try Again. !!!", Toast.LENGTH_SHORT).show();
        }
    }
}