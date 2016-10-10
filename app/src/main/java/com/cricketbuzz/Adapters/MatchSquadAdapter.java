package com.cricketbuzz.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.cricketbuzz.R;

import java.util.List;

import com.cricketbuzzsdk.DataHolder.c;
import com.cricketbuzzsdk.MatchesData.Player;


public class MatchSquadAdapter extends
        RecyclerView.Adapter<MatchSquadAdapter.ContactViewHolder> {

    Context mactivity;
    RecyclerView recList;
    List<Player> list;
    ImageLoader mImageLoader;
    String playerimage;

    public MatchSquadAdapter(Context context, RecyclerView recList,
                             List<Player> list2, String mplayer) {
        // TODO Auto-generated constructor stub
        this.mactivity = context;
        this.recList = recList;
        this.playerimage = mplayer;
        this.list = list2;
        mImageLoader = c.getInstance(context).getImageLoader();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder,
                                 int i) {

        contactViewHolder.playername.setText(list.get(i).getfName());

        String player = playerimage.replace("myplayerid", list.get(i).getId());

        mImageLoader.get(player, new ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }

            @Override
            public void onResponse(ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    contactViewHolder.player
                            .setImageBitmap(getRoundedCornerBitmap(
                                    response.getBitmap(), 100));
                }
            }
        });

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.matchsquad_card, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView playername;
        protected ImageView player;

        public ContactViewHolder(View v) {
            super(v);
            playername = (TextView) v.findViewById(R.id.tv_playername);
            player = (ImageView) v.findViewById(R.id.img_player);

        }
    }

}
