package com.gensee.view.xlistview;

import android.content.Context;
import android.util.AttributeSet;

import com.gensee.rtmpresourcelib.R;

public class CustomXListView extends XListView {

	public CustomXListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomXListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomXListView(Context context) {
		super(context);
	}

	@Override
	protected int getHeadContentTvId() {
		return R.id.xlistview_header_content;
	}

	@Override
	protected int getHeadTimeTvId() {
		return R.id.xlistview_header_time;
	}

	private class CustomXListViewFooter extends XListViewFooter {

		public CustomXListViewFooter(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public CustomXListViewFooter(Context context) {
			super(context);
		}

		@Override
		protected int getFooterHintReadyStrId() {
			return R.string.xlistview_footer_hint_ready;
		}

		@Override
		protected int getFooterHintNormalStrId() {
			return R.string.xlistview_footer_hint_normal;
		}

		@Override
		protected int getFooterContentTvId() {
			return R.id.xlistview_footer_content;
		}

		@Override
		protected int getFooterProgressbarId() {
			return R.id.xlistview_footer_progressbar;
		}

		@Override
		protected int getFooterHintTvId() {
			return R.id.xlistview_footer_hint_textview;
		}

		@Override
		protected int getFooterViewLayoutId() {
			return R.layout.xlistview_footer;
		}

	}

	private class CustomXListViewHeader extends XListViewHeader {

		public CustomXListViewHeader(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public CustomXListViewHeader(Context context) {
			super(context);
		}

		@Override
		protected int getHeaderViewLayoutId() {
			return R.layout.xlistview_header;
		}

		@Override
		protected int getHeaderHintNormalStrId() {
			return R.string.xlistview_header_hint_normal;
		}

		@Override
		protected int getHeaderHintReadyStrId() {
			return R.string.xlistview_header_hint_ready;
		}

		@Override
		protected int getHeaderHintLoadingStrId() {
			return R.string.xlistview_header_hint_loading;
		}

		@Override
		protected int getHeaderArrowIvId() {
			return R.id.xlistview_header_arrow;
		}

		@Override
		protected int getHeaderHintTvId() {
			return R.id.xlistview_header_hint_textview;
		}

		@Override
		protected int getHeaderProgressbarId() {
			return R.id.xlistview_header_progressbar;
		}

	}

	@Override
	protected XListViewFooter getFooterView(Context context) {
		return new CustomXListViewFooter(context);
	}

	@Override
	protected XListViewHeader getHeaderView(Context context) {
		return new CustomXListViewHeader(context);
	}

}
