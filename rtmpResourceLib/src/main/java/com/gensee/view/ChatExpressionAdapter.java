package com.gensee.view;

import android.content.Context;
import android.view.View;

import com.gensee.adapter.AbsGridViewAvatarAdapter;
import com.gensee.adapter.SelectAvatarInterface;
import com.gensee.rtmpresourcelib.R;

public class ChatExpressionAdapter extends AbsGridViewAvatarAdapter {

	public ChatExpressionAdapter(Context context,
			SelectAvatarInterface selectAvatarInterface, int num, int endSum) {
		super(context, selectAvatarInterface, num, endSum);
	}

	@Override
	protected int getChatGvItemLyId() {
		return R.layout.single_expression_layout;
	}

	@Override
	protected AbsGridViewHolder getGridViewHolder(View view) {
		return new GridViewHolder(view);
	}

	private class GridViewHolder extends AbsGridViewHolder {

		public GridViewHolder(View view) {
			super(view);
		}

		@Override
		protected int getChatExpressionIvId() {
			return R.id.image;
		}

	}

}
