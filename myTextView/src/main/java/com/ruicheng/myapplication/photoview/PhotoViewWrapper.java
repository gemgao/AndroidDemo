package com.ruicheng.myapplication.photoview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.ruicheng.myapplication.R;

public class PhotoViewWrapper extends RelativeLayout {

	protected View loadingDialog;

	protected PhotoView photoView;

	protected Context mContext;
	
	public PhotoViewWrapper(Context ctx) {
		super(ctx);
		mContext = ctx;
		init();
	}

	public PhotoViewWrapper(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
		mContext = ctx;
		init();
	}

	public PhotoView getImageView() {
		return photoView;
	}

	protected void init() {
		photoView = new PhotoView(mContext);
		photoView.enable();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		photoView.setLayoutParams(params);
		this.addView(photoView);
		photoView.setVisibility(GONE);

		loadingDialog = LayoutInflater.from(mContext).inflate(R.layout.photo_view_zoom_progress, null);
		loadingDialog.setLayoutParams(params);
		this.addView(loadingDialog);
	}

	public void setUrl(String imageUrl) {
		loadingDialog.setVisibility(View.GONE);
		photoView.setVisibility(VISIBLE);
		Glide.with(mContext).load(imageUrl).thumbnail(0.1f).into(photoView);
	}
}