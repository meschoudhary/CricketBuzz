package com.cricketbuzz.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.cricketbuzz.R;
import com.cricketbuzz.Activities.ScoresActivity;
import com.cricketbuzz.Utils.SplashProgress;
import com.cricketbuzzsdk.DataHolder.Data;
import com.cricketbuzzsdk.DataHolder.c;
import com.cricketbuzzsdk.Listeners.ScoresDataListener;
import com.cricketbuzzsdk.LiveMatchesData.LiveMatchesData;
import com.cricketbuzzsdk.MatchesData.CurrentMatches;
import com.cricketbuzzsdk.ScoresData.ScoresData;

import java.util.List;

public class LiveMatchesAdapter extends PagerAdapter implements ScoresDataListener {

    Context mContext;
    LayoutInflater mLayoutInflater;
    List<CurrentMatches> list;
    ImageLoader mImageLoader;
    SplashProgress progress;
    int itemposition;
    String batteam, bowlteam;

    public LiveMatchesAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageLoader = c.getInstance(mContext)
                .getImageLoader();
        progress = new SplashProgress(mContext);
        progress.setCancelable(false);
        // Set Matches Data
        list = Data.getMatchesdata();

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView = mLayoutInflater.inflate(R.layout.live_item,
                container, false);
        LiveMatchesData liveMatchesDataLoad = new LiveMatchesData();
        NetworkImageView flag1, flag2;
        TextView team1, team2, header, team1score, team2score, team1over, team2over, result, live, desc;
        flag1 = (NetworkImageView) itemView
                .findViewById(R.id.img_team1_flag);
        flag2 = (NetworkImageView) itemView
                .findViewById(R.id.img_team2_flag);
        team1 = (TextView) itemView.findViewById(R.id.tv_team1);
        desc = (TextView) itemView.findViewById(R.id.tv_desc);
        header = (TextView) itemView.findViewById(R.id.tv_header);
        team1score = (TextView) itemView
                .findViewById(R.id.tv_team1_score);
        team1over = (TextView) itemView
                .findViewById(R.id.tv_team1_overs);
        team2 = (TextView) itemView.findViewById(R.id.tv_team2);
        team2score = (TextView) itemView
                .findViewById(R.id.tv_team2_score);
        team2over = (TextView) itemView
                .findViewById(R.id.tv_team2_overs);
        result = (TextView) itemView
                .findViewById(R.id.tv_match_result);
        live = (TextView) itemView.findViewById(R.id.tv_live);

        result.setSelected(true);

        if (list.get(position).getHeader().getMchState()
                .equalsIgnoreCase("inprogress")) {
            live.setVisibility(View.VISIBLE);
        }

        desc.setText(list.get(position).getHeader().getMchDesc());

        try {

            if (list.get(position).getMiniscore().getBatteamid()
                    .equals(list.get(position).getTeam1().getId())) {

                batteam = list.get(position).getTeam1().getsName();
                bowlteam = list.get(position).getTeam2().getsName();

            } else {

                batteam = list.get(position).getTeam2().getsName();
                bowlteam = list.get(position).getTeam1().getsName();

            }

            team1.setText(batteam);
            team2.setText(bowlteam);

        } catch (Exception e) {
            // TODO: handle exception

            batteam = list.get(position).getTeam1().getsName();
            bowlteam = list.get(position).getTeam2().getsName();

            team1.setText(batteam);
            team2.setText(bowlteam);
        }

        header.setText(list.get(position).getSrs());

        result.setText(list.get(position).getHeader().getStatus());

        try {

            String team1_flag = liveMatchesDataLoad.getTeam1_flag(list.get(position));

            String team2_flag = liveMatchesDataLoad.getTeam2_flag(list.get(position));

            flag1.setImageUrl(team1_flag, mImageLoader);
            flag2.setImageUrl(team2_flag, mImageLoader);

        } catch (Exception e) {
            // TODO: handle exception

            String team1_flag = liveMatchesDataLoad.getTeam1e_flag(list.get(position));

            String team2_flag = liveMatchesDataLoad.getTeam2e_flag(list.get(position));

            flag1.setImageUrl(team1_flag, mImageLoader);
            flag2.setImageUrl(team2_flag, mImageLoader);
        }

        try {

            if (list.get(position).getMiniscore().getBatteamscore().equals("")) {
                team1score.setText("-/-");
            } else {
                team1score.setText(list.get(position).getMiniscore()
                        .getBatteamscore());
            }

            if (list.get(position).getMiniscore().getBowlteamscore().equals("")) {
                team2score.setText("-/-");
            } else {
                team2score.setText(list.get(position).getMiniscore()
                        .getBowlteamscore());
            }

            if (list.get(position).getMiniscore().getOvers().equals("")) {

                team1over.setText("-/-");

            } else {
                team1over.setText(list.get(position).getMiniscore().getOvers()

                        + " overs");
            }

            if (list.get(position).getMiniscore().getBowlteamovers().equals("")) {
                team2over.setText("-/-");
            } else {
                team2over.setText(list.get(position).getMiniscore()
                        .getBowlteamovers()
                        + " overs");
            }

        } catch (Exception e) {
            // TODO: handle exception

            team1score.setText("-/-");
            team2score.setText("-/-");
            team1over.setText("-/-");
            team2over.setText("-/-");
        }

        itemView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                itemposition = position;

                try {

                    if (list.get(itemposition).getMiniscore() != null) {

                        if (!progress.isShowing()) {
                            progress.show();
                        }


                        // Get FullScore Data

                        ScoresData load = new ScoresData();
                        load.getscoresdata(mContext, list.get(itemposition)
                                .getDatapath());

                    } else {

                        Toast.makeText(mContext,
                                "Full Score will be available shortly",
                                Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    Toast.makeText(mContext,
                            "Full Score will be available shortly",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public void GetScoresData(Boolean data) {

        if (progress.isShowing()) {
            progress.dismiss();
        }

        if (data) {

            Intent homeIntent = new Intent(mContext,
                    ScoresActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_NEW_TASK);

            if (list.get(itemposition).getHeader().getMchState()
                    .equalsIgnoreCase("inprogress") && !list.get(itemposition).getHeader().getType().equals("TEST")) {
                homeIntent.putExtra("live", true);
            } else {
                homeIntent.putExtra("live", false);
            }

            homeIntent.putExtra("datapath", list.get(itemposition)
                    .getDatapath());
            mContext.startActivity(homeIntent);

        } else {

            Toast.makeText(mContext,
                    "Something Went Wrong. Please Try Again. !!!", Toast.LENGTH_SHORT).show();

        }

    }


}
