package com.cricketbuzz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cricketbuzz.R;
import com.cricketbuzzsdk.DataHolder.Data;
import com.cricketbuzzsdk.MatchesData.Batsman;

import java.util.List;


public class Batsman_Adapter extends BaseAdapter {

    private LayoutInflater l_Inflater;
    Context C;
    List<Batsman> list;

    public Batsman_Adapter(Context context, List<Batsman> list_batsman) {
        C = context;
        l_Inflater = LayoutInflater.from(context);
        list = list_batsman;
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
            convertView = l_Inflater.inflate(R.layout.batsman_row, null);

            holder.batsman = (TextView) convertView
                    .findViewById(R.id.tv_batsman);
            holder.out = (TextView) convertView.findViewById(R.id.tv_out);
            holder.run = (TextView) convertView.findViewById(R.id.tv_runs);
            holder.ball = (TextView) convertView
                    .findViewById(R.id.tv_ball);
            holder.fours = (TextView) convertView.findViewById(R.id.tv_4s);
            holder.sixes = (TextView) convertView.findViewById(R.id.tv_6s);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String player = null;
        for (int i = 0; i < Data.getFull_score().getPlayers().size(); i++) {

            if (list.get(position)
                    .getBatsmanId()
                    .equals(Data.getFull_score().getPlayers().get(i)
                            .getId())) {
                player = Data.getFull_score().getPlayers().get(i)
                        .getfName();

            }

        }

        holder.batsman.setText(player);
        holder.out.setText(list.get(position).getOutdescription());
        holder.run.setText(list.get(position).getRun());
        holder.ball.setText(list.get(position).getBall());
        holder.fours.setText(list.get(position).getFour());
        holder.sixes.setText(list.get(position).getSix());

        return convertView;
    }

    static class ViewHolder {
        TextView batsman, out, run, ball, fours, sixes;
    }

}
