package com.cricketbuzz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cricketbuzz.R;

import java.util.List;

import com.cricketbuzzsdk.MatchesData.standing.QualifyingGroup;


public class StandingAdapter extends BaseAdapter {

    Context C;
    List<QualifyingGroup> list;
    private LayoutInflater l_Inflater;

    public StandingAdapter(Context context,
                           List<QualifyingGroup> standing_Star) {
        C = context;
        l_Inflater = LayoutInflater.from(context);
        list = standing_Star;
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

            convertView = l_Inflater.inflate(R.layout.standing_row, null);

            holder.name = (TextView) convertView
                    .findViewById(R.id.tv_team);
            holder.matches = (TextView) convertView
                    .findViewById(R.id.tv_matches);
            holder.won = (TextView) convertView.findViewById(R.id.tv_won);
            holder.lost = (TextView) convertView
                    .findViewById(R.id.tv_lost);
            holder.tied = (TextView) convertView
                    .findViewById(R.id.tv_tied);
            holder.points = (TextView) convertView
                    .findViewById(R.id.tv_points);
            holder.nrr = (TextView) convertView.findViewById(R.id.tv_nrr);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }


        holder.name.setText(list.get(position).getTeamName());
        holder.matches.setText(String.valueOf(list.get(position).getPlayed()));
        holder.won.setText(String.valueOf(list.get(position).getWon()));
        holder.lost.setText(String.valueOf(list.get(position).getLost()));
        holder.tied.setText(String.valueOf(list.get(position).getNoresults()));
        holder.points.setText(String.valueOf(list.get(position)
                .getPointsscored()));
        holder.nrr.setText(String.valueOf(list.get(position).getRunrate()));

        return convertView;
    }

    static class ViewHolder {
        TextView name, matches, won, lost, tied, points, nrr;
    }

}
