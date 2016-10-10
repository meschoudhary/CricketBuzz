package com.cricketbuzz.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.cricketbuzz.R;

import java.util.List;

import com.cricketbuzzsdk.FixturesData.FixturesAdapterData;
import com.cricketbuzzsdk.DataHolder.c;
import com.cricketbuzzsdk.MatchesData.fixtures.Matches;

public class FixturesAdapter extends
        RecyclerView.Adapter<FixturesAdapter.ContactViewHolder> {

    public Activity mactivity;
    List<Matches> list;
    ImageLoader mImageLoader;

    public FixturesAdapter(Activity act, RecyclerView recList,
                           List<Matches> fixture_list) {
        // TODO Auto-generated constructor stub
        this.mactivity = act;
        this.list = fixture_list;
        mImageLoader = c.getInstance(act).getImageLoader();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder,
                                 int i) {

        FixturesAdapterData load = new FixturesAdapterData();

        // Load Team Flag
        String team1_flag = load.loadTeamFlag1(list.get(i));

        // Load Team Flag
        String team2_flag = load.loadTeamFlag2(list.get(i));


        contactViewHolder.flag1.setImageUrl(team1_flag, mImageLoader);
        contactViewHolder.flag2.setImageUrl(team2_flag, mImageLoader);

        contactViewHolder.date.setText(list.get(i).getStrtdt() + " "
                + list.get(i).getStrttm());

        if (list.get(i).getDesc().contains("Day 1")) {
            contactViewHolder.header.setText(list.get(i).getDesc()
                    .replace(", Day 1", ""));
        } else {
            contactViewHolder.header.setText(list.get(i).getDesc());
        }

        contactViewHolder.team1score.setVisibility(View.GONE);
        contactViewHolder.team2score.setVisibility(View.GONE);

        //Load Country Names
        String team1, team2;
        team1 = load.loadCountryName1(list.get(i));
        team2 = load.loadCountryName2(list.get(i));

        try {

            team1.equals("");
            team2.equals("");

            contactViewHolder.team1.setText(team1);
            contactViewHolder.team2.setText(team2);

        } catch (Exception e) {
            // TODO: handle exception

            String[] teams = list.get(i).getDesc().split(",");

            String[] team = teams[0].split("vs");

            contactViewHolder.team1.setText(" " + team[0]);
            contactViewHolder.team2.setText(team[1]);

        }

        contactViewHolder.status.setText(list.get(i).getVnu());

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.fixture_card, viewGroup, false);

        return new ContactViewHolder(itemView);
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
