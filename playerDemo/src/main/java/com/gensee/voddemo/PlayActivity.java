package com.gensee.voddemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.gensee.entity.ChatMsg;
import com.gensee.entity.DocInfo;
import com.gensee.entity.PageInfo;
import com.gensee.media.VODPlayer;
import com.gensee.media.VODPlayer.OnVodPlayListener;
import com.gensee.pdu.GSDocView;
import com.gensee.pdu.GSDocView.OnDocViewEventListener;
import com.gensee.playerdemo.LogCatService;
import com.gensee.playerdemo.R;
import com.gensee.utils.GenseeLog;
import com.gensee.utils.StringUtil;
import com.gensee.view.GSDocViewGx;
import com.gensee.view.GSVideoView;

public class PlayActivity extends Activity implements OnDocViewEventListener,
		OnVodPlayListener, OnClickListener, OnSeekBarChangeListener {

	private static final String TAG = "PlayActivity";
	private VODPlayer mVodPlayer;
	private GSVideoView mGSVideoView;
	//private GSDocView mDocView;
	private GSDocViewGx mGlDocView;
	private SeekBar mSeekBarPlayViedo;
	private Button btnDocList;
	private ListView lvChapterList;
	private ChapterListAdapter chapterListAdapter;

	private Button stopVeidoPlay;
	private Button replyVedioPlay;
	private TextView mNowTimeTextview;
	private TextView mAllTimeTextView;

	private ImageButton mPauseScreenplay;
	private boolean isTouch = false;

	private static final int DURITME = 2000;
	private static final String DURATION = "DURATION";

	private int VIEDOPAUSEPALY = 0;

	private List<ChapterInfo> chapterList;

	private int lastPostion = 0;
	interface MSG {
		int MSG_ON_INIT = 1;
		int MSG_ON_STOP = 2;
		int MSG_ON_POSITION = 3;
		int MSG_ON_VIDEOSIZE = 4;
		int MSG_ON_PAGE = 5;
		int MSG_ON_SEEK = 6;
		int MSG_ON_AUDIOLEVEL = 7;
		int MSG_ON_ERROR = 8;
		int MSG_ON_PAUSE = 9;
		int MSG_ON_RESUME = 10;
	}

	protected Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG.MSG_ON_INIT:

				int max = msg.getData().getInt(DURATION);
				mSeekBarPlayViedo.setMax(max);
				max = max / 1000;
				GenseeLog.i(TAG, "MSG_ON_INIT duration = " + max);
				mAllTimeTextView.setText(getTime(max));
				mVodPlayer.seekTo(lastPostion);
				mPauseScreenplay.setImageResource(R.drawable.icon_pause);

				if (null != chapterListAdapter) {
					chapterList.clear();
					if (null != msg.obj) {
						List<DocInfo> docInfoList = (List<DocInfo>) msg.obj;
						for (DocInfo docInfo : docInfoList) {
							List<PageInfo> pageInfoList = docInfo.getPages();
							if (null != pageInfoList && pageInfoList.size() > 0) {
								for (PageInfo pageInfo : pageInfoList) {

									ChapterInfo chapterInfo = new ChapterInfo();
									chapterInfo.setDocId(docInfo.getDocId());
									chapterInfo
											.setDocName(docInfo.getDocName());
									chapterInfo.setDocPageNum(docInfo
											.getPageNum());
									chapterInfo.setDocType(docInfo.getType());

									chapterInfo.setPageTimeStamp(pageInfo
											.getTimeStamp());
									chapterInfo.setPageTitle(pageInfo
											.getTitle());
									chapterList.add(chapterInfo);
								}
							}
						}

					}
					chapterListAdapter.notifyData(chapterList);
				}
				break;
			case MSG.MSG_ON_STOP:

				break;
			case MSG.MSG_ON_VIDEOSIZE:

				break;
			case MSG.MSG_ON_PAGE:
				int position = (Integer)msg.obj;
				int nSize = chapterList.size();
				for(int i = 0; i < nSize; i++)
				{
					ChapterInfo chapterInfo  = chapterList.get(i);
					if(chapterInfo.getPageTimeStamp() == position)
					{
						if(null != chapterListAdapter)
						{
							chapterListAdapter.setSelectedPosition(i);
						}
						break;
					}
				}
				break;
			case MSG.MSG_ON_PAUSE:
				VIEDOPAUSEPALY = 1;
				mPauseScreenplay.setImageResource(R.drawable.icon_play);
				break;
			case MSG.MSG_ON_RESUME:
				VIEDOPAUSEPALY = 0;
				mPauseScreenplay.setImageResource(R.drawable.icon_pause);
				break;
			case MSG.MSG_ON_POSITION:
				if (isTouch) {
					return;
				}
			case MSG.MSG_ON_SEEK:
				isTouch = false;
				int anyPosition = (Integer) msg.obj;
				mSeekBarPlayViedo.setProgress(anyPosition);
				anyPosition = anyPosition / 1000;
				mNowTimeTextview.setText(getTime(anyPosition));
				break;
			case MSG.MSG_ON_AUDIOLEVEL:

				break;
			case MSG.MSG_ON_ERROR:
				int errorCode = (Integer) msg.obj;
				switch (errorCode) {
				case ERR_PAUSE:
					Toast.makeText(getApplicationContext(), "暂停失败", DURITME)
							.show();
					break;
				case ERR_PLAY:
					Toast.makeText(getApplicationContext(), "播放失败", DURITME)
							.show();
					break;
				case ERR_RESUME:
					Toast.makeText(getApplicationContext(), "恢复失败", DURITME)
							.show();
					break;
				case ERR_SEEK:
					Toast.makeText(getApplicationContext(), "进度变化失败", DURITME)
							.show();
					break;
				case ERR_STOP:
					Toast.makeText(getApplicationContext(), "停止失败", DURITME)
							.show();
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	private String getTime(int time) {
		return String.format("%02d", time / 3600) + ":"
				+ String.format("%02d", time % 3600 / 60) + ":"
				+ String.format("%02d", time % 3600 % 60);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_play);
		lastPostion = getPreferences(MODE_PRIVATE).getInt("lastPos", 0);
		mGSVideoView = (GSVideoView) findViewById(R.id.gsvideoview);
		mGlDocView = (GSDocViewGx) findViewById(R.id.playGlDocView);
		mSeekBarPlayViedo = (SeekBar) findViewById(R.id.seekbarpalyviedo);
		stopVeidoPlay = (Button) findViewById(R.id.stopveidoplay);
		mPauseScreenplay = (ImageButton) findViewById(R.id.pauseresumeplay);
		replyVedioPlay = (Button) findViewById(R.id.replayvedioplay);
		mNowTimeTextview = (TextView) findViewById(R.id.palynowtime);
		mAllTimeTextView = (TextView) findViewById(R.id.palyalltime);
		btnDocList = (Button) findViewById(R.id.doc_list_btn);
		btnDocList.setOnClickListener(this);
		chapterListAdapter = new ChapterListAdapter();
		chapterList = new ArrayList<ChapterInfo>();
		lvChapterList = (ListView) findViewById(R.id.doc_lv);
		lvChapterList.setAdapter(chapterListAdapter);
		lvChapterList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ChapterInfo chapterInfo = chapterList.get(position);
				if (null != mVodPlayer) {
					mVodPlayer.seekTo(chapterInfo.getPageTimeStamp());
				}
			}
		});

		mSeekBarPlayViedo.setOnSeekBarChangeListener(this);
		//mDocView.setOnDocViewClickedListener(this);
		// mDocView.showAdaptViewWidth();

		stopVeidoPlay.setOnClickListener(this);
		replyVedioPlay.setOnClickListener(this);

		mPauseScreenplay.setOnClickListener(this);

		initPlayer();
	}

	private void initPlayer() {

		String vodIdOrLocalPath = getVodIdOrLocalPath();
		if (vodIdOrLocalPath == null) {
			Toast.makeText(this, "路径不对", Toast.LENGTH_SHORT).show();
			return;
		}
		if (mVodPlayer == null) {
			mVodPlayer = new VODPlayer();
			mVodPlayer.setGSVideoView(mGSVideoView);
			mVodPlayer.setGSDocViewGx(mGlDocView);
			mVodPlayer.play(vodIdOrLocalPath, this, "");
		}
	}

	private String getVodIdOrLocalPath() {
		String vodId = getIntent().getStringExtra("play_param");
		String localpath = getIntent().getStringExtra("play_path");
		GenseeLog.d(TAG, "path = " + localpath + " vodId = " + vodId);
		String vodIdOrLocalPath = null;
		if (!StringUtil.isEmpty(localpath)) {
			vodIdOrLocalPath = localpath;
		} else if (!StringUtil.isEmpty(vodId)) {
			vodIdOrLocalPath = vodId;
		}
		return vodIdOrLocalPath;
	}

	@Override
	public void onInit(int result, boolean haveVideo, int duration,
			List<DocInfo> docInfos) {
		if (lastPostion >= duration-1000) {
			lastPostion = 0;
		}
		Message message = new Message();
		message.what = MSG.MSG_ON_INIT;
		message.obj = docInfos;
		Bundle bundle = new Bundle();
		bundle.putInt(DURATION, duration);
		message.setData(bundle);
		myHandler.sendMessage(message);
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	public void onPlayStop() {
		myHandler.sendMessage(myHandler.obtainMessage(MSG.MSG_ON_STOP, 0));
	}

	@Override
	public void onPosition(int position) {
		GenseeLog.d(TAG, "onPosition pos = " + position);
		lastPostion = position;
		myHandler.sendMessage(myHandler.obtainMessage(MSG.MSG_ON_POSITION,
				position));
	}

	@Override
	public void onVideoSize(int position, int videoWidth, int videoHeight) {
		myHandler.sendMessage(myHandler.obtainMessage(MSG.MSG_ON_VIDEOSIZE, 0));
	}


	@Override
	public void onSeek(int position) {
		myHandler.sendMessage(myHandler
				.obtainMessage(MSG.MSG_ON_SEEK, position));
	}

	@Override
	public void onAudioLevel(int level) {
		myHandler.sendMessage(myHandler.obtainMessage(MSG.MSG_ON_AUDIOLEVEL,
				level));
	}

	@Override
	public void onError(int errCode) {
		myHandler.sendMessage(myHandler
				.obtainMessage(MSG.MSG_ON_ERROR, errCode));
	}

	@Override
	public void onPlayPause() {
		myHandler.sendMessage(myHandler.obtainMessage(MSG.MSG_ON_PAUSE, 0));
	}

	@Override
	public void onPlayResume() {
		myHandler.sendMessage(myHandler.obtainMessage(MSG.MSG_ON_RESUME, 0));
	}

	

	@Override
	public void onPageSize(int position, int w, int h) {
		//文档翻页切换，开始显示
		myHandler.sendMessage(myHandler
				.obtainMessage(MSG.MSG_ON_PAGE, position));
		
	}
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		isTouch = true;

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		if (null != mVodPlayer) {
			int pos = seekBar.getProgress();
			GenseeLog.d(TAG, "onStopTrackingTouch pos = " + pos);
			mVodPlayer.seekTo(pos);

		}

	}

	@Override
	public void onClick(View currenView) {
		if (currenView.getId() == R.id.stopveidoplay) {
			boolean ret = mVodPlayer.stop();
			mSeekBarPlayViedo.setMax(0);
			// if (ret) {
			// Toast.makeText(getApplicationContext(), "播放正常停止", DURITME)
			// .show();
			// } else {
			// Toast.makeText(getApplicationContext(), "播放非正常停止", DURITME)
			// .show();
			// }
			Toast.makeText(this, ret ? "操作成功" : "操作失败", DURITME).show();
		} else if (currenView.getId() == R.id.replayvedioplay) {

			isTouch = false;
			String vodIdOrLocalPath = getVodIdOrLocalPath();
			if (vodIdOrLocalPath == null) {
				Toast.makeText(this, "路径不对", Toast.LENGTH_SHORT).show();
				return;
			}
			mVodPlayer.play(vodIdOrLocalPath, this, "");

		} else if (currenView.getId() == R.id.pauseresumeplay) {

			// if (mGSOLPlayer.pause()) {
			// mPauseScreenplay.setImageResource(R.drawable.icon_play);
			// mGSOLPlayer.resume();
			// } else if (mGSOLPlayer.resume()) {
			// mPauseScreenplay.setImageResource(R.drawable.icon_pause);
			// mGSOLPlayer.pause();
			// }

			if (VIEDOPAUSEPALY == 0) {

				mVodPlayer.pause();

			} else if (VIEDOPAUSEPALY == 1) {

				mVodPlayer.resume();

			}
		} else if (currenView.getId() == R.id.doc_list_btn) {
			if (lvChapterList.getVisibility() == View.VISIBLE) {
				lvChapterList.setVisibility(View.GONE);
			} else {
				lvChapterList.setVisibility(View.VISIBLE);
			}
		}
	}

	private void stopPlay() {
		if (mVodPlayer != null) {
			mVodPlayer.stop();
		}
	}

	private void release() {
		stopPlay();
		if (mVodPlayer != null) {
			mVodPlayer.release();
		}
	}

	@Override
	public void onBackPressed() {
//		getPreferences(MODE_PRIVATE).edit().putInt("lastPos", lastPostion).commit();
		release();
		super.onBackPressed();
	}

	@Override
	public boolean onDoubleClicked(GSDocView arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onEndHDirection(GSDocView arg0, int arg1,int type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSingleClicked(GSDocView arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCaching(boolean isCatching) {
		// TODO Auto-generated method stub

	}

	private class ChapterListAdapter extends BaseAdapter {
		private List<ChapterInfo> pageList;
		private int selectedPosition = 0;

		public void setSelectedPosition(int position) {
			selectedPosition = position;
			notifyDataSetChanged();
			lvChapterList.setSelection(position);
		}

		public ChapterListAdapter() {
			pageList = new ArrayList<ChapterInfo>();
		}

		public void notifyData(List<ChapterInfo> pageList) {
			this.pageList.clear();
			this.pageList.addAll(pageList);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return pageList.size();
		}

		@Override
		public Object getItem(int position) {
			return pageList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (null == convertView) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(
						R.layout.doc_list_item_ly, null);
				viewHolder = new ViewHolder(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.init((ChapterInfo) getItem(position), position);
			return convertView;
		}

		private class ViewHolder {
			private TextView tvChapter;
			private TextView tvTitle;
			private TextView tvTime;
			private LinearLayout lyChapter;

			private String getChapterTime(long time) {
				return String.format("%02d", time / (3600 * 1000))
						+ ":"
						+ String.format("%02d", time % (3600 * 1000)
								/ (60 * 1000))
						+ ":"
						+ String.format("%02d", time % (3600 * 1000)
								% (60 * 1000) / 1000);
			}

			public ViewHolder(View view) {
				tvChapter = (TextView) view.findViewById(R.id.chapter_title);
				tvTitle = (TextView) view.findViewById(R.id.doc_title);
				tvTime = (TextView) view.findViewById(R.id.chapter_time);
				lyChapter = (LinearLayout) view.findViewById(R.id.chapter_ly);
			}

			public void init(ChapterInfo chapterInfo, int position) {
				tvChapter.setText(chapterInfo.getPageTitle());
				tvTime.setText(getChapterTime(chapterInfo.getPageTimeStamp()));
				tvTitle.setText(chapterInfo.getDocName());

				if (selectedPosition == position) {
					lyChapter.setBackgroundResource(R.color.red);
				} else {
					lyChapter.setBackgroundResource(R.color.transparent);
				}
			}
		}

	}

	@Override
	public void onVideoStart() {
		
	}

	@Override
	public void onChat(List<ChatMsg> arg0) {
		// TODO Auto-generated method stub
		
	}

}
