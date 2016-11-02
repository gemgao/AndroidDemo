package com.gensee.view;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.gensee.rtmpresourcelib.R;
import com.gensee.view.AbsVotePopView.InterfaceSelectOther;

public class GSImplVoteView extends GSVoteView {

	public GSImplVoteView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public GSImplVoteView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GSImplVoteView(Context context) {
		super(context);
	}

	@Override
	protected View getView(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater.inflate(R.layout.vote_layout, null);
	}

	protected class VotePopView extends AbsVotePopView {

		public VotePopView(View parentView,
				InterfaceSelectOther interfaceSelectOther,
				List<String> searchList) {
			super(parentView, interfaceSelectOther, searchList);
		}

		@Override
		protected int getVotePopLayoutId() {
			return R.layout.pop_layout;
		}

		@Override
		protected int getVoteLvId() {
			return R.id.user_pop_list;
		}

		@Override
		protected int getVoteSelectTvId() {
			return R.id.select_tv;
		}

		@Override
		protected int getVotePopItemWidthResId() {
			return R.dimen.pop_list_pop_width;
		}

		@Override
		protected int getVotePopItemHeightResId() {
			return R.dimen.pop_list_item_height;
		}

		private class VotePopAdapter extends AbsVotePopAdapter {

			@Override
			protected int getVoteItemLayoutId() {
				return R.layout.pop_list_item;
			}

			@Override
			protected AbsVoteViewHolder createVoteViewHolder(View view) {
				return new VoteViewHolder(view);
			}

		}

		private class VoteViewHolder extends AbsVoteViewHolder {

			public VoteViewHolder(View view) {
				super(view);
			}

			@Override
			protected int getVoteNameTvId() {
				return R.id.tv_user_do_title;
			}

		}

		@Override
		protected AbsVotePopAdapter getVoteViewAdapter() {
			return new VotePopAdapter();
		}

	}

	@Override
	protected int getVoteReceiverColseIvId() {
		return R.id.vote_receiver_close_iv;
	}

	@Override
	protected int getVoteReceiverCommitBtnId() {
		return R.id.vote_receiver_commit_btn;
	}

	@Override
	protected int getVoteSelectTvId() {
		return R.id.select_tv;
	}

	@Override
	protected int getVoteCountTvId() {
		return R.id.vote_count_tv;
	}

	@Override
	protected int getVoteQiangZhiTvId() {
		return R.id.vote_qiangzhi_tv;
	}

	@Override
	protected int getVoteQiangZhiTipStrId() {
		return R.string.vote_qiangzhi_tip;
	}

	@Override
	protected int getVoteReceiverLvId() {
		return R.id.vote_receiver_lv;
	}

	@Override
	protected int getVoteReceiverItemLayoutId() {
		return R.layout.ly_vote_receive_item;
	}

	@Override
	protected int getQuestionNameTvId() {
		return R.id.vote_receive_question_name;
	}

	@Override
	protected int getAnswerItemLyId() {
		return R.id.vote_receive_answe_item_ly;
	}

	@Override
	protected int getQuestionTotalTvId() {
		return R.id.vote_receive_answer_question_total;
	}

	@Override
	protected int getSingleChoiceStrId() {
		return R.string.single_choice;
	}

	@Override
	protected int getMultiChoiceStrId() {
		return R.string.multi_choice;
	}

	@Override
	protected int getTextWdStrId() {
		return R.string.text_wd;
	}

	@Override
	protected int getVoteAnswerLayoutId() {
		return R.layout.ly_vote_receive_answers_item;
	}

	@Override
	protected int getVoteReceiverChoiceLyId() {
		return R.id.vote_receiver_choice_ly;
	}

	@Override
	protected int getVoteReceiverWdItemEdtId() {
		return R.id.vote_receive_wd_item_edt;
	}

	@Override
	protected int getVoteReceiverAnswerCbId() {
		return R.id.vote_receive_answer_cb;
	}

	@Override
	protected int getVoteReceiverAnswerRbId() {
		return R.id.vote_receive_answe_rb;
	}

	@Override
	protected int getVoteReceiverAnswerTvId() {
		return R.id.vote_receive_answer_tv;
	}

	@Override
	protected int getVoteReceiverAnswerIvId() {
		return R.id.vote_receive_answer_iv;
	}

	@Override
	protected int getVoteProgressbarSingleId() {
		return R.id.vote_progress_bar_single;
	}

	@Override
	protected int getVoteProgressbarMultiId() {
		return R.id.vote_progress_bar_multi;
	}

	@Override
	protected int getVoteAnswerCountTvId() {
		return R.id.vote_receive_answer_count_tv;
	}

	@Override
	protected int getVoteTotalPersonJoinStrId() {
		return R.string.vote_total_person_join;
	}

	@Override
	protected int getVoteQuestionFenShuStrId() {
		return R.string.vote_question_fenshu;
	}

	@Override
	protected int getVoteCountStrId() {
		return R.string.vote_count;
	}

	@Override
	protected int getVoteNotExistStrId() {
		return R.string.vote_not_exist;
	}

	@Override
	protected int getVoteHaveCommitStrId() {
		return R.string.vote_have_commit;
	}

	@Override
	protected int getVoteCommitStrId() {
		return R.string.commit;
	}

	@Override
	protected int getVoteDeadlineTipStrId() {
		return R.string.vote_deadline_tip;
	}
	
	@Override
	protected int getVoteAnswerAllQuestionsTip() {
		return R.string.vote_please_dawan_all;
	}
	
	@Override
	protected int getVoteAnswersTip() {
		return R.string.vote_please_dawan;
	}

	@Override
	protected AbsVotePopView getVotePopView(View parentView,
			InterfaceSelectOther interfaceSelectOther, List<String> searchList) {
		return new VotePopView(parentView, interfaceSelectOther, searchList);
	}

}
