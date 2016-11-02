package com.gensee.view;

import com.gensee.rtmpresourcelib.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

public class GSImplQaView extends GSQaView {

	/**
	 * @param context
	 */
	public GSImplQaView(Context context) {
		super(context,null);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public GSImplQaView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public GSImplQaView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected View getView(Context context) {
		return inflate(context, R.layout.gs_qa_layout, null);
	}

	@Override
	protected int getQaSendBtnId() {
		return R.id.gsQaSend;
	}

	@Override
	protected int getQaListViewId() {
		return R.id.gsQaListView;
	}

	@Override
	protected int getQaEditId() {
		return R.id.gsQaEdit;
	}

	@Override
	protected int getLimitSelfQaId() {
		return R.id.looktaking_tv;
	}

	@Override
	protected AbsQaAdapter getQaAdapter(Context context) {
		return new QaAdapter(context);
	}

	@Override
	protected int getQaHeadViewLayout() {
		return R.layout.chat_view_header_layout;
	}
	
	
	class QaAdapter extends AbsQaAdapter{

		public QaAdapter(Context context) {
			super(context);
		}

		@Override
		protected View createView(LayoutInflater inflater) {
			return inflater.inflate(R.layout.gs_qa_item_layout, null);
		}

		@Override
		protected AbstractViewHolder createViewHolder(View view) {
			return new QAItemHolder(view);
		}
		
		class QAItemHolder extends AbsQaHolder{

			public QAItemHolder(View v) {
				super(v);
			}

			@Override
			protected int getQUserTextViewId() {
				return R.id.txtQUser;
			}

			@Override
			protected int getAUserTextViewId() {
				return R.id.txtAUser;
			}

			@Override
			protected int getQTextViewId() {
				return R.id.txtQContent;
			}

			@Override
			protected int getATextViewId() {
				return R.id.txtAContent;
			}

			@Override
			protected int getQTimeTextViewId() {
				return R.id.txtQTime;
			}

			@Override
			protected int getATimeTextViewId() {
				return R.id.txtAime;
			}

			@Override
			protected int getAnswerGroupViewId() {
				return R.id.relAnswer;
			}
		}
	}


	@Override
	protected int getQaShowOwnerTextRes() {
		return R.string.justlookmyqa;
	}

	@Override
	protected int getMeStrRes() {
		return R.string.chat_me;
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
}
