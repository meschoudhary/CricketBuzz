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
import com.cricketbuzzsdk.MatchesData.Fow;


public class Fow_Adapter extends BaseAdapter {

    Context C;
    List<Fow> list;
    private LayoutInflater l_Inflater;

    public Fow_Adapter(Context context, List<Fow> fow) {
        C = context;
        l_Inflater = LayoutInflater.from(context);
        list = fow;
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
            convertView = l_Inflater.inflate(R.layout.fow_row, null);

            holder.wickets = (TextView) convertView
                    .findViewById(R.id.tv_wickets);
            holder.name = (TextView) convertView
                    .findViewById(R.id.tv_name);
            holder.overs = (TextView) convertView
                    .findViewById(R.id.tv_overs);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String player = null;
        for (int i = 0; i < Data.getFull_score().getPlayers().size(); i++) {

            if (list.get(position)
                    .getOutBatsmanId()
                    .equals(Data.getFull_score().getPlayers().get(i)
                            .getId())) {
                player = Data.getFull_score().getPlayers().get(i)
                        .getfName();

            }
        }

        holder.name.setText(player);
        holder.wickets.setText(list.get(position).getWicketnbr() + "-"
                + list.get(position).getRun());
        holder.overs.setText(list.get(position).getOvernbr() + "overs");

        return convertView;
    }

    static class ViewHolder {
        TextView wickets, name, overs;
    }

}
