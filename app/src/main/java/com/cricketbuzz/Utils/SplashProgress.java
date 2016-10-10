package com.cricketbuzz.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.cricketbuzz.R;

public class SplashProgress extends Dialog {
	private Context context;
	private ImageView imgLogo;

	public SplashProgress(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_dialog);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		imgLogo = (ImageView) findViewById(R.id.loading);
	}

	@Override
	protected void onStart() {
		imgLogo.startAnimation(AnimationUtils.loadAnimation(context,
				R.anim.loading));

	}

	@Override
	protected void onStop() {
		super.onStop();
	}
}
