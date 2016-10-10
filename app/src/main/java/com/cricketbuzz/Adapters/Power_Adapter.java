package com.cricketbuzz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cricketbuzz.R;

import java.util.List;

import com.cricketbuzzsdk.MatchesData.PowerPlays;


public class Power_Adapter extends BaseAdapter {

    Context C;
    List<PowerPlays> list;
    private LayoutInflater l_Inflater;

    public Power_Adapter(Context context, List<PowerPlays> powerplay) {
        C = context;
        l_Inflater = LayoutInflater.from(context);
        list = powerplay;
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
            convertView = l_Inflater.inflate(R.layout.power_row, null);

            holder.overs = (TextView) convertView
                    .findViewById(R.id.tv_overs);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String overfrom = list.get(position).getOverFrom();
        String overto = list.get(position).getOverTo();
        String run = list.get(position).getRuns();

        holder.overs.setText(list.get(position).getType() + " (" + overfrom
                + "-" + overto + ") overs" + " - " + run + "runs");
        return convertView;
    }

    static class ViewHolder {
        TextView overs;
    }

}
