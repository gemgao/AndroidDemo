package com.gensee.view;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.gensee.adapter.AbsChatAdapter;
import com.gensee.adapter.AbsGridViewAvatarAdapter;
import com.gensee.adapter.SelectAvatarInterface;
import com.gensee.entity.UserInfo;
import com.gensee.holder.chat.ExpressionResource;
import com.gensee.rtmpresourcelib.R;
import com.gensee.view.AbsChatToPopView.InterfaceSelarctorName;

public class GSImplChatView extends GSChatView {

	public GSImplChatView(Context context) {
		super(context);
	}

	public GSImplChatView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GSImplChatView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected View getView(Context context) {
		ExpressionResource.initExpressionResource(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater.inflate(R.layout.chat_view_layout, null);
	}

	@Override
	protected int getSendBtnId() {
		return R.id.sendbutton;
	}

	@Override
	protected int getExpressionBtnId() {
		return R.id.expressionbuttton;
	}

	@Override
	protected int getChatEditId() {
		return R.id.edittalking;
	}

	@Override
	protected int getChatLvId() {
		return R.id.gs_talkingcontext;
	}

	@Override
	protected int getChatLvHeadViewId() {
		return R.layout.chat_view_header_layout;
	}

	@Override
	protected int getQuerySelfTvId() {
		return R.id.looktaking_tv;
	}

	@Override
	protected int getExpressionLyId() {
		return R.id.viewpageexpressionlinear;
	}

	@Override
	protected int getExpressionIndexLyId() {
		return R.id.chatexpressaddimg;
	}

	@Override
	protected int getExpressionPagerId() {
		return R.layout.chat_gridview_expression_layout;
	}

	@Override
	protected int getExpressionIndexSelectIvId() {
		return R.drawable.chat_viewpage_fource;
	}

	@Override
	protected int getExpressionIndexUnSelectIvId() {
		return R.drawable.chat_viewpage_unfource;
	}

	@Override
	protected int getExpressionGvId() {
		return R.id.allexpressionGrid;
	}

	@Override
	protected int getExpressionVpId() {
		return R.id.viewpager;
	}
	
	@Override
	protected int getPublishPauseStrId() {
		return R.string.live_pause;
	}
	
	@Override
	protected int getPublishPlayingStrId() {
		return R.string.live_playing;
	}
	
	@Override
	protected int getChatDisableStrId() {
		return R.string.chat_disable;
	}
	
	@Override
	protected int getChatEnableStrId() {
		return R.string.chat_enable;
	}

	@Override
	protected AbsChatAdapter getChatAdapter(Context context) {
		return new ChatImplAdapter(context);
	}

	@Override
	protected AbsGridViewAvatarAdapter getGvAvatarAdapter(Context context,
			SelectAvatarInterface avaterInterface, int start, int end) {
		return new ChatExpressionAdapter(context, avaterInterface, start, end);
	}

	@Override
	protected int getSendMsgNotNullId() {
		return R.string.chat_msg_not_null;
	}

	@Override
	protected int getSelfInfoNullId() {
		return R.string.chat_self_null;
	}

	@Override
	protected int getChatToSelfStrId() {
		return R.string.chat_not_to_self;
	}

	@Override
	protected int getRelTipId() {
		return R.id.rl_tip;
	}

	@Override
	protected int getRelTipStrId() {
		return R.string.query_self_tip;
	}

	@Override
	protected int getTvTipId() {
		return R.id.tv_tip;
	}

	@Override
	protected int getTvChatToId() {
		return R.id.chat_to_tv;
	}
	
	@Override
	protected int getChatPublicTvId() {
		return R.string.allname;
	}

	@Override
	protected AbsChatToPopView createChatToPopView(View view,
			InterfaceSelarctorName interfaceSelarctorName,
			List<com.gensee.entity.UserInfo> userList) {
		return new ChatToPopView(view, interfaceSelarctorName, userList);
	}
	
	protected class ChatToPopView extends AbsChatToPopView
	{

		public ChatToPopView(View parentView,
				InterfaceSelarctorName mInterfaceSelarctorName,
				List<UserInfo> mInfos) {
			super(parentView, mInterfaceSelarctorName, mInfos);
		}

		@Override
		protected int getPopChatLayoutId() {
			return R.layout.popchat_layout;
		}

		@Override
		protected int getPopChatLvId() {
			return R.id.chatlist_pop_title;
		}

		@Override
		protected int getPopChatWidthId() {
			return R.dimen.pop_list_pop_width;
		}

		@Override
		protected int getPopChatHeightId() {
			return R.dimen.pop_list_pop_height;
		}

		@Override
		protected int getPopChatItemHeightId() {
			return R.dimen.pop_list_item_height;
		}

		@Override
		protected int getPopChatListItemLayoutId() {
			return R.layout.popchat_list_item;
		}

		@Override
		protected int getPopChatUserTitleTvId() {
			return R.id.chat_use_title;
		}
		
	}
}
