package com.cricketbuzz.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.cricketbuzz.R;
import com.cricketbuzz.Activities.ScoresActivity;
import com.cricketbuzz.Utils.SplashProgress;
import com.cricketbuzzsdk.DataHolder.c;
import com.cricketbuzzsdk.Listeners.ResultsFullDataListener;
import com.cricketbuzzsdk.MatchesData.ResultMatches;
import com.cricketbuzzsdk.ResultsData.ResultsAdapterData;

import java.util.List;

public class ResultAdapter extends
        RecyclerView.Adapter<ResultAdapter.ContactViewHolder> implements ResultsFullDataListener {

    public Activity mactivity;
    List<ResultMatches> list;
    ImageLoader mImageLoader;
    int itemposition;
    private RecyclerView recList;
    SplashProgress progress;

    public ResultAdapter(Activity act, RecyclerView recList,
                         List<ResultMatches> series) {
        // TODO Auto-generated constructor stub
        this.mactivity = act;
        this.list = series;
        this.recList = recList;
        mImageLoader = c.getInstance(act).getImageLoader();
        progress = new SplashProgress(mactivity);
        progress.setCancelable(false);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder,
                                 int i) {
        ResultsAdapterData resultsAdapterDataLoad = new ResultsAdapterData();

        String team1_flag = resultsAdapterDataLoad.loadTeamFlag1(list.get(i));

        String team2_flag = resultsAdapterDataLoad.loadTeamFlag2(list.get(i));

        contactViewHolder.flag1.setImageUrl(team1_flag, mImageLoader);
        contactViewHolder.flag2.setImageUrl(team2_flag, mImageLoader);

        contactViewHolder.date.setText(list.get(i).getHeader().getStartdt()
                + " " + list.get(i).getHeader().getStTme());

        contactViewHolder.header.setText(list.get(i).getHeader().getMchDesc()
                + ", " + list.get(i).getHeader().getMnum());

        contactViewHolder.team1.setText(list.get(i).getTeam1().getfName());
        contactViewHolder.team2.setText(list.get(i).getTeam2().getfName());

        contactViewHolder.status.setText(list.get(i).getHeader().getStatus());

        try {

            if (resultsAdapterDataLoad.checkId(list.get(i))) {

                contactViewHolder.team1score.setText(list.get(i).getMiniscore()
                        .getBatteamscore());
                contactViewHolder.team2score.setText(list.get(i).getMiniscore()
                        .getBowlteamscore());

            } else {

                contactViewHolder.team1score.setText(list.get(i).getMiniscore()
                        .getBowlteamscore());
                contactViewHolder.team2score.setText(list.get(i).getMiniscore()
                        .getBatteamscore());

            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.fixture_card, viewGroup, false);

        itemView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (!progress.isShowing()) {
                    progress.show();
                }

                itemposition = recList.getChildPosition(v);

                // Get Full Result Data
                ResultsAdapterData load = new ResultsAdapterData();
                load.execute(mactivity, list.get(itemposition));
            }
        });

        return new ContactViewHolder(itemView);
    }

    @Override
    public void GetResultData(Boolean data) {

        if (progress.isShowing()) {

            progress.dismiss();
        }

        if (data) {

            Intent homeIntent = new Intent(mactivity,
                    ScoresActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_NEW_TASK);

            if (list.get(itemposition).getHeader().getMchState()
                    .equalsIgnoreCase("inprogress")) {
                homeIntent.putExtra("live", true);
            } else {
                homeIntent.putExtra("live", false);
            }

            homeIntent.putExtra("datapath", list.get(itemposition)
                    .getDatapath());
            mactivity.startActivity(homeIntent);

        }

    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView team1, team2, date, header, status, team1score,
                team2score;
        protected NetworkImageView flag1, flag2;

        public ContactViewHolder(View v) {
            super(v);
            date = (TextView) v.findViewById(R.id.tv_date);
            header = (TextView) v.findViewById(R.id.tv_header);
            team1 = (TextView) v.findViewById(R.id.tv_team1);
            team2 = (TextView) v.findViewById(R.id.tv_team2);
            status = (TextView) v.findViewById(R.id.tv_status);
            team1score = (TextView) v
                    .findViewById(R.id.tv_team1_score);
            team2score = (TextView) v
                    .findViewById(R.id.tv_team2_score);
            flag1 = (NetworkImageView) v
                    .findViewById(R.id.img_team1_flag);
            flag2 = (NetworkImageView) v
                    .findViewById(R.id.img_team2_flag);
        }
    }

}
