package com.cricketbuzz.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.cricketbuzz.R;
import com.cricketbuzzsdk.DataHolder.c;
import com.cricketbuzzsdk.MatchesData.CommLines;

import java.util.List;

public class CommentryAdapter extends
        RecyclerView.Adapter<CommentryAdapter.ContactViewHolder> {

    public Activity mactivity;
    List<CommLines> list;
    ImageLoader mImageLoader;

    public CommentryAdapter(Activity act, RecyclerView recList,
                            List<CommLines> list2) {
        // TODO Auto-generated constructor stub
        this.mactivity = act;
        this.list = list2;
        mImageLoader = c.getInstance(act).getImageLoader();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return super.getItemId(position);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder,
                                 int i) {

        contactViewHolder.overs.setText(list.get(i).getBallno());

        if (list.get(i).getEvent().equalsIgnoreCase("wicket")) {
            contactViewHolder.card.setBackgroundColor(mactivity.getResources()
                    .getColor(R.color.action));
        } else if (list.get(i).getEvent().equalsIgnoreCase("six")) {
            contactViewHolder.card.setBackgroundColor(mactivity.getResources()
                    .getColor(R.color.up));
        } else if (list.get(i).getEvent().equalsIgnoreCase("four")) {
            contactViewHolder.card.setBackgroundColor(mactivity.getResources()
                    .getColor(R.color.up));
        } else {
            contactViewHolder.card.setBackgroundColor(mactivity.getResources()
                    .getColor(R.color.blue));
        }

        switch (list.get(i).getEvent()) {
            case "noball":
                contactViewHolder.state.setImageResource(R.drawable.nb);
                break;
            case "six":
                contactViewHolder.state.setImageResource(R.drawable.chago);
                break;
            case "four":
                contactViewHolder.state.setImageResource(R.drawable.choko);
                break;
            case "wicket":
                contactViewHolder.state.setImageResource(R.drawable.wicket);
                break;
            default:
                contactViewHolder.state.setImageResource(R.drawable.ic_lineup);
        }

        contactViewHolder.comments.setText(list.get(i).getCommtxt());

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.commentry_card, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView comments, overs;
        protected ImageView state;
        protected LinearLayout card;

        public ContactViewHolder(View v) {
            super(v);
            comments = (TextView) v.findViewById(R.id.tv_comments);
            overs = (TextView) v.findViewById(R.id.tv_over);
            state = (ImageView) v.findViewById(R.id.img_state);
            card = (LinearLayout) v.findViewById(R.id.ll_com);
        }
    }

}
