package com.cricketbuzz.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cricketbuzz.R;
import com.cricketbuzz.Activities.FixturesActivity;
import com.cricketbuzz.Activities.NewsActivity;
import com.cricketbuzz.Activities.ResultActivity;
import com.cricketbuzz.Activities.StandingsActivity;
import com.cricketbuzz.Utils.SplashProgress;
import com.cricketbuzzsdk.FixturesData.FixturesData;
import com.cricketbuzzsdk.Listeners.FixturesDataListener;
import com.cricketbuzzsdk.Listeners.NewsDataListener;
import com.cricketbuzzsdk.Listeners.ResultsDataListener;
import com.cricketbuzzsdk.Listeners.StandigsDataListener;
import com.cricketbuzzsdk.NewsData.NewsData;
import com.cricketbuzzsdk.ResultsData.ResultsData;
import com.cricketbuzzsdk.StandigsData.StandingsData;

public class HomeAdapter extends
        RecyclerView.Adapter<HomeAdapter.ContactViewHolder> implements FixturesDataListener, ResultsDataListener, StandigsDataListener, NewsDataListener {

    Activity mactivity;
    SplashProgress progress;

    public HomeAdapter(Activity act) {
        // TODO Auto-generated constructor stub
        this.mactivity = act;
        progress = new SplashProgress(mactivity);
        progress.setCancelable(false);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder,
                                 int i) {

        if (i == 0) {

            contactViewHolder.tv1.setText("FIXTURES");
            contactViewHolder.img1
                    .setImageResource(R.drawable.schedule);

            contactViewHolder.ll1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    if (!progress.isShowing()) {

                        progress.show();
                    }

                    // Get Fixtures Data

                    FixturesData load = new FixturesData();
                    load.getfixturedata(mactivity);

                }
            });

        } else if (i == 1) {

            contactViewHolder.tv1.setText("RESULTS");
            contactViewHolder.img1
                    .setImageResource(R.drawable.results);

            contactViewHolder.ll1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    if (!progress.isShowing()) {
                        progress.show();
                    }

                    // Get Results Data

                    ResultsData load = new ResultsData();
                    load.getresultsdata(mactivity);


                }
            });
        } else if (i == 2) {

            contactViewHolder.tv1.setText("STANDINGS");
            contactViewHolder.img1
                    .setImageResource(R.drawable.pointtable);

            contactViewHolder.ll1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    if (!progress.isShowing()) {
                        progress.show();
                    }

                    // Get Standings Data

                    StandingsData load = new StandingsData();
                    load.getstandingsdata(mactivity);

                }
            });
        } else if (i == 3) {

            contactViewHolder.tv1.setText("NEWS");
            contactViewHolder.img1.setImageResource(R.drawable.news);

            contactViewHolder.ll1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    if (!progress.isShowing()) {
                        progress.show();
                    }

                    // Get News Data

                    NewsData load = new NewsData();
                    load.getnewsdata(mactivity);

                }
            });
        }

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.home_card, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    @Override
    public void GetFixturesData(Boolean data) {


        if (progress.isShowing()) {
            progress.dismiss();
        }
        if (data) {
            mactivity.startActivity(new Intent(mactivity,
                    FixturesActivity.class));
        } else {

            Toast.makeText(mactivity,
                    "Something Went Wrong. Please Try Again. !!!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void GetResultsData(Boolean data) {

        if (progress.isShowing()) {
            progress.dismiss();
        }

        if (data) {
            mactivity.startActivity(new Intent(mactivity,
                    ResultActivity.class));
        } else {
            Toast.makeText(mactivity,
                    "Something Went Wrong. Please Try Again. !!!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void GetStandingsData(Boolean data) {


        if (progress.isShowing()) {
            progress.dismiss();
        }


        if (data) {
            mactivity.startActivity(new Intent(mactivity,
                    StandingsActivity.class));
        } else {
            Toast.makeText(mactivity,
                    "Something Went Wrong. Please Try Again. !!!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void GetNewsData(Boolean data) {


        if (progress.isShowing()) {
            progress.dismiss();
        }


        if (data) {
            mactivity.startActivity(new Intent(mactivity,
                    NewsActivity.class));

        } else {
            Toast.makeText(mactivity,
                    "Something Went Wrong. Please Try Again. !!!", Toast.LENGTH_SHORT).show();
        }
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView tv1;
        protected ImageView img1;
        protected LinearLayout ll1;

        public ContactViewHolder(View v) {
            super(v);
            tv1 = (TextView) v.findViewById(R.id.tv_1);
            img1 = (ImageView) v.findViewById(R.id.img_1);
            ll1 = (LinearLayout) v.findViewById(R.id.ll1);

        }
    }

}
