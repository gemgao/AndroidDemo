package com.gensee.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.gensee.adapter.AbsChatAdapter;
import com.gensee.rtmpresourcelib.R;

public class ChatImplAdapter extends AbsChatAdapter {

	public ChatImplAdapter(Context context) {
		super(context);
	}

	@Override
	protected AbstractViewHolder createViewHolder(View view) {

		ChatViewHolder mChatViewHolder = new ChatViewHolder(view);
		return mChatViewHolder;
	}

	@Override
	protected View createView(LayoutInflater inflater) {
		View convertView = inflater.inflate(R.layout.chat_listitem_layout,
				null);
		return convertView;

	}

	private class ChatViewHolder extends AbsChatViewHolder {

		public ChatViewHolder(View view) {
			super(view);
		}

		@Override
		protected int getChatNameEdtid() {
			return R.id.chatnametext;
		}

		@Override
		protected int getChatTimeTvid() {
			return R.id.chattimetext;
		}

		@Override
		protected int getChatContentTvId() {
			return R.id.chatcontexttextview;
		}

		@Override
		protected int getChatSysTvId() {
			return R.id.chat_listview_tex_context;
		}

		@Override
		protected int getChatSysDelIvId() {
			return R.id.chat_listview_bnt_delete_context;
		}

		@Override
		protected int getSysMsgTipId() {
			return R.string.chat_system_msg_colon;
		}

		@Override
		protected int getSysMsgColorId() {
			return R.color.chat_system_message;
		}

		@Override
		protected int getChatmeTipStrId() {
			return R.string.chat_me;
		}

		@Override
		protected int getChattoStrId() {
			return R.string.chat_to;
		}

		@Override
		protected int getChatsayStrId() {
			return R.string.chat_say;
		}

	}

}