package com.cricketbuzz.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cricketbuzz.R;

import java.util.List;

import com.cricketbuzzsdk.DataHolder.Data;
import com.cricketbuzzsdk.MatchesData.Bowlers;


public class Bowling_Adapter extends BaseAdapter {

    Context C;
    List<Bowlers> list;
    private LayoutInflater l_Inflater;

    public Bowling_Adapter(Context context, List<Bowlers> list_bowler) {
        C = context;
        l_Inflater = LayoutInflater.from(context);
        list = list_bowler;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = new ViewHolder();

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.bowling_row, null);

            holder.bowler = (TextView) convertView
                    .findViewById(R.id.tv_bowler);
            holder.over = (TextView) convertView
                    .findViewById(R.id.tv_overs);
            holder.maiden = (TextView) convertView
                    .findViewById(R.id.tv_maiden);
            holder.run = (TextView) convertView.findViewById(R.id.tv_runs);
            holder.wicket = (TextView) convertView
                    .findViewById(R.id.tv_wickets);
            holder.wide = (TextView) convertView
                    .findViewById(R.id.tv_wide);
            holder.noball = (TextView) convertView
                    .findViewById(R.id.tv_noball);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String player = null;
        for (int i = 0; i < Data.getFull_score().getPlayers().size(); i++) {

            if (list.get(position)
                    .getBowlerId()
                    .equals(Data.getFull_score().getPlayers().get(i)
                            .getId())) {
                player = Data.getFull_score().getPlayers().get(i)
                        .getfName();

            }

        }

        holder.bowler.setText(player);
        holder.over.setText(list.get(position).getOver());
        holder.maiden.setText(list.get(position).getMaiden());
        holder.run.setText(list.get(position).getRun());
        holder.wicket.setText(list.get(position).getWicket());
        holder.wide.setText(list.get(position).getWideball());
        holder.noball.setText(list.get(position).getNoball());

        return convertView;
    }

    static class ViewHolder {
        TextView bowler, over, maiden, run, wicket, wide, noball;
    }

}
