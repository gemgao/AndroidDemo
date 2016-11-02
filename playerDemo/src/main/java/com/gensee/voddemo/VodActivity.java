package com.gensee.voddemo;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gensee.common.ServiceType;
import com.gensee.download.ErrorCode;
import com.gensee.download.VodDownLoadEntity;
import com.gensee.download.VodDownLoadStatus;
import com.gensee.download.VodDownLoader;
import com.gensee.download.VodDownLoader.OnDownloadListener;
import com.gensee.entity.ChatMsg;
import com.gensee.entity.InitParam;
import com.gensee.entity.QAMsg;
import com.gensee.entity.VodObject;
import com.gensee.playerdemo.LogCatService;
import com.gensee.playerdemo.R;
import com.gensee.taskret.OnTaskRet;
import com.gensee.utils.GenseeLog;
import com.gensee.utils.StringUtil;
import com.gensee.vod.VodSite;
import com.gensee.vod.VodSite.OnVodListener;

public class VodActivity extends Activity implements OnClickListener,OnLongClickListener,
		OnDownloadListener, OnItemClickListener, OnVodListener {

	private String TAG = "MainActivity";
	private EditText mEditVodDomin;
	private EditText mEditVodNumber;
	private EditText mEditNickname;
	private EditText mEditAccount;
	private EditText mLoginPassword;
	private EditText mVodPassword;
	private ListView mListView;
	private MyAdapter mMyAdapter;
	private VodDownLoader mVodDownLoader;

	private Spinner mSpinner, mSpinner2;
	private Button mInintButton;

	private VodSite vodSite;
	private String strDomin;
	private String strNumber;
	private String strNickName;
	private String strAccount;
	private String strLoginPassword;
	private String strVodPassword;
	private ServiceType serviceType = ServiceType.ST_CASTLINE;

	private View optionSelect;
	private View btnDownLoad, btnPlay;

	private SharedPreferences preferences;

	private String[] mServiceTypes = { ServiceType.ST_CASTLINE.getValue(),
			ServiceType.ST_TRAINING.getValue(),
			ServiceType.ST_MEETING.getValue() };
	private String[] mDownloadMode = { "是", "否" };
	private ArrayAdapter<String> serviceAdapter;
	private ArrayAdapter<String> downloadModeAdapter;

	// private List<VodDownLoadEntity> loadEntities;

	private static final int DURTIME = 2000;

	public interface RESULT {
		int DOWNLOAD_ERROR = 2;
		int DOWNLOAD_STOP = 3;
		int DOWNLOADER_INIT = 4;
		int DOWNLOAD_START = 5;
		int ON_GET_VODOBJ = 100;
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case RESULT.DOWNLOAD_ERROR:
				// notifyData();
				Toast.makeText(getApplicationContext(), "下载出错", DURTIME).show();
				break;
			case RESULT.DOWNLOAD_STOP:
				notifyData();
				Toast.makeText(getApplicationContext(), "下载停止", DURTIME).show();

				break;
			case RESULT.DOWNLOAD_START:
				notifyData();
				Toast.makeText(getApplicationContext(), "下载开始", DURTIME).show();
				break;
			case RESULT.ON_GET_VODOBJ:

				optionSelect.setVisibility(View.VISIBLE);
				final String vodId = (String) msg.obj;
				// download(vodId);
				// notifyData();
				// 在线播放
				btnPlay.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						optionSelect.setVisibility(View.GONE);
						Intent i = new Intent(VodActivity.this,
								PlayActivity.class);
						i.putExtra("play_param", vodId);
						startActivity(i);
					}
				});
				// 下载
				btnDownLoad.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						optionSelect.setVisibility(View.GONE);
						download(vodId);
					}
				});
				break;
			case RESULT.DOWNLOADER_INIT:
				if (mVodDownLoader != null) {
					mMyAdapter.notifyData(mVodDownLoader.getDownloadList());
				}
				break;
			default:
				break;
			}
		}

	};

	private void download(String vodId) {
		mVodDownLoader.setAutoDownloadNext(true);
		int ret = mVodDownLoader.download(vodId);
		switch (ret) {
		case ErrorCode.SUCCESS:
			Toast.makeText(this, "下载开始", DURTIME).show();
			// 调用成功，刷新列表
			notifyData();
			break;
		case ErrorCode.DOWNLOADING_HAVE_EXIST:
			Toast.makeText(this, "当前已有下载任务 。目前的机制是单任务下载", DURTIME).show();
			break;
		case ErrorCode.DOWNLOADING_URL_NULL:
			Toast.makeText(this, "下载地址为空", DURTIME).show();
			break;
		case ErrorCode.OBJECT_HAVE_EXIST:
			Toast.makeText(this, "录制件已在下载队列中", DURTIME).show();
			break;
		case ErrorCode.OBJECT_IS_NULL:
			Toast.makeText(this, "传入参数为空", DURTIME).show();
			break;
		case ErrorCode.OBJECT_NOT_EXIST:
			Toast.makeText(this, "目标不存在", DURTIME).show();
			break;
		case ErrorCode.SDCARD_ERROR:
			Toast.makeText(this, "SD卡异常", DURTIME).show();
			break;
		default:
			break;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.vod_layout);
		preferences = getPreferences(MODE_PRIVATE);
		startLogService();
		mMyAdapter = new MyAdapter(this);
		mInintButton = (Button) findViewById(R.id.mbutton);
		mListView = (ListView) findViewById(R.id.progressDoc);

		mSpinner = (Spinner) findViewById(R.id.Spinner01);
		mSpinner2 = (Spinner) findViewById(R.id.Spinner02);

		/*
		 * 代理使用，如果app有自己的代理，调用setTcpProxy， 然后在IGSOLProxy的ip和端口回调中返回相应的代理ip和代理端口，
		 * 没有代理则无需调用。此函数任何时候都可以调用。
		 */
		/*
		 * VodSite.setTcpProxy(new IProxy() {
		 * 
		 * @Override public int getProxyPort(int port) { // 返回代理端口 return port;
		 * }
		 * 
		 * @Override public String getProxyIP(String ip) { // 返回代理ip地址 return
		 * ip; } });
		 */
		
		/**
		 * 优先调用进行组件加载，有条件的情况下可以放到application启动时候的恰当时机调用
		 */
		VodSite.init(this, new OnTaskRet() {

			@Override
			public void onTaskRet(boolean arg0, int arg1, String arg2) {
				// TODO Auto-generated method stub

			}
		});

		//如果需要区分多用户，请使用带用户id的instance进行初始化，默认情况下用户id为0
		mVodDownLoader = VodDownLoader.instance(this, this, null);
		// 启动已存在且未完成的任务
		mVodDownLoader.download();
		// 刷新列表
		mHandler.sendEmptyMessage(RESULT.DOWNLOADER_INIT);

		serviceAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mServiceTypes);
		serviceAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setPrompt("选择站点类型（站点类型webcast、training、meeting）");
		mSpinner.setAdapter(serviceAdapter);

		downloadModeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mDownloadMode);
		downloadModeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner2.setPrompt("开启自动下载");
		mSpinner2.setAdapter(downloadModeAdapter);

		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int postion, long arg3) {
				switch (postion) {
				case 0:
					// 选择了webcast
					serviceType = ServiceType.ST_CASTLINE;
					break;
				case 1:
					// 选择了 training
					serviceType = ServiceType.ST_TRAINING;
					break;
				case 2:
					// 选择了meeting
					serviceType = ServiceType.ST_MEETING;
					break;
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// strStyle = mDominStyle[0];
			}
		});

		mSpinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (null != mVodDownLoader) {
					mVodDownLoader.setAutoDownloadNext(arg2 == 0);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		mInintButton.setOnClickListener(this);
		mInintButton.setOnLongClickListener(this);
		mListView.setAdapter(mMyAdapter);
		initView();
	}

	private void notifyData() {
		if (null != mMyAdapter) {
			mMyAdapter.notifyData(mVodDownLoader.getDownloadList());
		}
	}

	public void initView() {

		optionSelect = findViewById(R.id.optionSelect);
		btnDownLoad = optionSelect.findViewById(R.id.btnDownLoad);
		btnPlay = optionSelect.findViewById(R.id.btnDownPlay);

		mEditVodDomin = (EditText) findViewById(R.id.editvoddomin);
		mEditVodNumber = (EditText) findViewById(R.id.editvodnumble);
		mEditNickname = (EditText) findViewById(R.id.editvodnickname);
		mLoginPassword = (EditText) findViewById(R.id.editvodloginpassword);
		mVodPassword = (EditText) findViewById(R.id.editvodpassword);
		mEditAccount = (EditText) findViewById(R.id.editvodaccount);

		String domain = preferences.getString("domain", "");
		String number = preferences.getString("number", "");
		String account = preferences.getString("account", "");
		String accPwd = preferences.getString("accPwd", "");
		String nickName = preferences.getString("nickname", "");
		String vodPwd = preferences.getString("vodPwd", "");

		mEditVodDomin.setText(domain);
		mEditVodNumber.setText(number);
		mEditNickname.setText(account);
		mLoginPassword.setText(accPwd);
		mEditNickname.setText(nickName);
		mVodPassword.setText(vodPwd);

	}

	@Override
	public void onClick(View currenView) {
		if (currenView.getId() == R.id.mbutton) {
			// mEditVodDomin.setText("192.168.1.142");
			// mEditVodNumber.setText("74468401");
			// mEditAccount.setText("admin@gensee.com");
			// mLoginPassword.setText("888888");
			// mVodPassword.setText("333333");
			// mEditNickname.setText("test");
			// serviceType = ServiceType.ST_CASTLINE;

			strDomin = mEditVodDomin.getText().toString();
			strNumber = mEditVodNumber.getText().toString();
			strAccount = mEditAccount.getText().toString();
			strLoginPassword = mLoginPassword.getText().toString();
			strVodPassword = mVodPassword.getText().toString();
			strNickName = mEditNickname.getText().toString();

			// initParam的构造
			InitParam initParam = new InitParam();
			// domain 域名
			initParam.setDomain(strDomin);
			//8个数字的字符串为编号
			if (strNumber.length() == 8) {
				// 点播编号 （不是点播id）
				initParam.setNumber(strNumber);
			} else {
				// 设置点播id，和点播编号对应，两者至少要有一个有效才能保证成功
				String liveId = strNumber;
				initParam.setLiveId(liveId);
			}
			// 站点认证帐号 ，后台启用需要登录时必填，没启用时可以不填
			initParam.setLoginAccount(strAccount);
			// 站点认证密码，后台启用需要登录时必填
			initParam.setLoginPwd(strLoginPassword);
			// 点播口令，后台启用密码保护时必填且要正确填写
			initParam.setVodPwd(strVodPassword);
			// 昵称 用于统计和显示，必填
			initParam.setNickName(strNickName);
			// 服务类型（站点类型）
			// webcast - ST_CASTLINE
			// training - ST_TRAINING
			// meeting - ST_MEETING
			initParam.setServiceType(serviceType);

			vodSite = new VodSite(this);
			vodSite.setVodListener(this);
			vodSite.getVodObject(initParam);
		}
	}

	@Override
	public void onDLFinish(String downLoadId, String localPath) {
		GenseeLog.i(TAG, "onDLFinish downLoadId = " + downLoadId);
		preferences.edit().putString(downLoadId, localPath).commit();
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				mMyAdapter.notifyData(mVodDownLoader.getDownloadList());
			}
		});
	}

	@Override
	public void onDLPosition(String downLoadId, final int percent) {
		GenseeLog.i(TAG, "onDLPosition downLoadId = " + downLoadId
				+ " percent = " + percent);
		// 下载过程中
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				mMyAdapter.notifyData(mVodDownLoader.getDownloadList());
			}
		});
	}

	public void onDLPrepare(String downLoadId) {
		GenseeLog.i(TAG, "onDLPrepare downLoadId = " + downLoadId);
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				mMyAdapter.notifyData(mVodDownLoader.getDownloadList());
			}
		});
	}

	@Override
	public void onDLStart(String downLoadId) {
		GenseeLog.i(TAG, "onDLStart downLoadId = " + downLoadId);
		// 下载开始
		mHandler.sendEmptyMessage(RESULT.DOWNLOAD_START);
	}

	@Override
	public void onDLStop(String downLoadId) {
		// 下载停止
		GenseeLog.i(TAG, "onDLStop downLoadId = " + downLoadId);
		mHandler.sendEmptyMessage(RESULT.DOWNLOAD_STOP);

	}

	@Override
	public void onDLError(String downLoadId, int errorCode) {
		GenseeLog.i(TAG, "onDLError downLoadId = " + downLoadId
				+ " errorCode = " + errorCode);
		mHandler.sendEmptyMessage(RESULT.DOWNLOAD_ERROR);
	}

	class MyAdapter extends BaseAdapter {
		private List<VodDownLoadEntity> entities = null;
		private Context mContext;

		public MyAdapter(Context mContext) {
			this.mContext = mContext;
		}

		public void notifyData(List<VodDownLoadEntity> entities) {
			this.entities = entities;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return entities == null ? 0 : entities.size();
		}

		@Override
		public Object getItem(int position) {
			return entities.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			if (null == convertView) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.listitem, null);
			}
			final VodDownLoadEntity entity = (VodDownLoadEntity) getItem(position);
			TextView mTextView = (TextView) convertView
					.findViewById(R.id.downloadcontextid);
			TextView mProTextView = (TextView) convertView
					.findViewById(R.id.downloadprogressitem);
			ImageButton deleteButton = (ImageButton) convertView
					.findViewById(R.id.deleteDownload);
			ImageButton stopButton = (ImageButton) convertView
					.findViewById(R.id.stopdownload);
			final int status = entity.getnStatus();
			if (status == VodDownLoadStatus.FINISH.getStatus()) {
				stopButton.setVisibility(View.INVISIBLE);
			} else {
				stopButton.setVisibility(View.VISIBLE);
				if (status == VodDownLoadStatus.STOP.getStatus()) {
					stopButton.setImageResource(R.drawable.down_loading);
				} else if (status == VodDownLoadStatus.BEGIN.getStatus()
						|| status == VodDownLoadStatus.START.getStatus()) {
					stopButton.setImageResource(R.drawable.down_normal);
				} else {
					stopButton.setImageResource(R.drawable.down_loading);
				}
			}
			ImageButton playButton = (ImageButton) convertView
					.findViewById(R.id.palydownload);
			mTextView.setText(entity.getVodSubject() + " ");
			mProTextView.setText(entity.getnPercent() + "%");
			deleteButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mVodDownLoader.delete(entity.getDownLoadId());
					// 同时删除已经存放的本地路径
					preferences.edit().remove(entity.getDownLoadId());
					// 删除之后刷新
					notifyData(mVodDownLoader.getDownloadList());
				}
			});

			stopButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (status == VodDownLoadStatus.FAILED.getStatus()
							|| status == VodDownLoadStatus.STOP.getStatus()
							|| status == VodDownLoadStatus.DENY.getStatus()) {
						mVodDownLoader.download(entity.getDownLoadId());
					} else if (status == VodDownLoadStatus.BEGIN.getStatus()
							|| status == VodDownLoadStatus.START.getStatus()) {
						mVodDownLoader.stop(entity.getDownLoadId());
					}
				}
			});

			playButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String loacalPath = preferences.getString(
							entity.getDownLoadId(), "");
					// 离线播放
					if (!StringUtil.isEmpty(loacalPath)) {
						Intent intent = new Intent(VodActivity.this,
								PlayActivity.class);
						intent.putExtra("play_path", loacalPath);
						startActivity(intent);
					}
				}
			});
			return convertView;
		}
	}

	@Override
	public void onBackPressed() {
		// 退出下载相关的功能时，释放掉
		mVodDownLoader.release();
		mVodDownLoader = null;
		stopLogService();
		super.onBackPressed();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	}

	/********************* OnVodListener **************************/
	
	
	/**
	 * 聊天记录 getChatHistory 响应 vodId 点播id list 聊天记录
	 */
	@Override
	public void onChatHistory(String vodId, List<ChatMsg> list,int pageIndex,boolean more) {
		GenseeLog.d(TAG, "onChatHistory vodId = " + vodId + " " + list);
		// ChatMsg msg;
		// msg.getContent();//消息内容
		// msg.getSenderId();//发送者用户id
		// msg.getSender();//发送者昵称
		// msg.getTimeStamp();//发送时间，单位毫秒
	}

	/**
	 * 问答记录 getQaHistory 响应 list 问答记录 vodId 点播id
	 */
	@Override
	public void onQaHistory(String vodId, List<QAMsg> list,int pageIndex,boolean more) {
		GenseeLog.d(TAG, "onQaHistory vodId = " + vodId + " " + list);
		if (more) {
			// 如果还有更多的历史，还可以继续获取记录（pageIndex+1）页的记录
		}
		// QAMsg msg;
		// msg.getQuestion();//问题
		// msg.getQuestId();//问题id
		// msg.getQuestOwnerId();//提问人id
		// msg.getQuestOwnerName();//提问人昵称
		// msg.getQuestTimgstamp();//提问时间 单位毫秒
		//
		// msg.getAnswer();//回复的内容
		// msg.getAnswerId();//“本条回复”的id 不是回答者的用户id
		// msg.getAnswerOwner();//回复人的昵称
		// msg.getAnswerTimestamp();//回复时间 单位毫秒
	}
	

	/**
	 * 获取点播详情
	 */
	@Override
	public void onVodDetail(VodObject vodObj) {
		GenseeLog.d(TAG, "onVodDetail " + vodObj);
		if (vodObj != null) {
			vodObj.getDuration();// 时长
			vodObj.getEndTime();// 录制结束时间 始于1970的utc时间毫秒数
			vodObj.getStartTime();// 录制开始时间 始于1970的utc时间毫秒数
			vodObj.getStorage();// 大小 单位为Byte
		}
	}

	// int ERR_DOMAIN = -100; // ip(domain)不正确
	// int ERR_TIME_OUT = -101; // 超时
	// int ERR_UNKNOWN = -102; // 未知错误
	// int ERR_SITE_UNUSED = -103; // 站点不可用
	// int ERR_UN_NET = -104; // 无网络
	// int ERR_DATA_TIMEOUT = -105; // 数据过期
	// int ERR_SERVICE = -106; // 服务不正确
	// int ERR_PARAM = -107; // 参数不正确
	// int ERR_PARAM = -107; // 参数不正确
    // int ERR_THIRD_CERTIFICATION_AUTHORITY //第三方认证失败
	// int ERR_UN_INVOKE_GETOBJECT = -201; //没有调用getVodObject
	// int ERR_VOD_INTI_FAIL = 14; //点播getVodObject失败
	// int ERR_VOD_NUM_UNEXIST = 15; //点播编号不存在或点播不存在
	// int ERR_VOD_PWD_ERR = 16; //点播密码错误
	// int ERR_VOD_ACC_PWD_ERR = 17; //帐号或帐号密码错误
	// int ERR_UNSURPORT_MOBILE = 18; //不支持移动设备
	@Override
	public void onVodErr(final int errCode) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				String msg = getErrMsg(errCode);
				if (!"".equals(msg)) {
					Toast.makeText(VodActivity.this, msg, Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

	}

	/**
	 * getVodObject的响应，vodId 接下来可以下载后播放
	 */
	@Override
	public void onVodObject(String vodId) {
		GenseeLog.d(TAG, "onVodObject vodId = " + vodId);
		String domain = mEditVodDomin.getText().toString();
		String number = mEditVodNumber.getText().toString();
		String account = mEditAccount.getText().toString();
		String pwd = mLoginPassword.getText().toString();
		String nickName = mEditNickname.getText().toString();
		String vodPwd = mVodPassword.getText().toString();

		Editor editor = preferences.edit();
		editor.putString("domain", domain);
		editor.putString("number", number);
		editor.putString("account", account);
		editor.putString("accPwd", pwd);
		editor.putString("nickname", nickName);
		editor.putString("vodPwd", vodPwd);
		// 记住本次使用的参数 免得下次输入
		editor.commit();

		mHandler.sendMessage(mHandler
				.obtainMessage(RESULT.ON_GET_VODOBJ, vodId));
		
//		vodSite.getChatHistory(vodId, 1);//获取聊天历史记录，起始页码1
//		vodSite.getQaHistory(vodId, 1);//获取问答历史记录，起始页码1
	}

	/**
	 * 错误码处理
	 * 
	 * @param errCode
	 *            错误码
	 * @return 错误码文字表示内容
	 */
	private String getErrMsg(int errCode) {
		String msg = "";
		switch (errCode) {
		case ERR_DOMAIN:
			msg = "domain 不正确";
			break;
		case ERR_TIME_OUT:
			msg = "超时";
			break;
		case ERR_SITE_UNUSED:
			msg = "站点不可用";
			break;
		case ERR_UN_NET:
			msg = "无网络请检查网络连接";
			break;
		case ERR_DATA_TIMEOUT:
			msg = "数据过期";
			break;
		case ERR_SERVICE:
			msg = "请检查填写的serviceType";
			break;
		case ERR_PARAM:
			msg = "请检查参数";
			break;
		case ERR_UN_INVOKE_GETOBJECT:
			msg = "请先调用getVodObject";
			break;
		case ERR_VOD_INTI_FAIL:
			msg = "调用getVodObject失败";
			break;
		case ERR_VOD_NUM_UNEXIST:
			msg = "点播编号不存在或点播不存在";
			break;
		case ERR_VOD_PWD_ERR:
			msg = "点播密码错误";
			break;
		case ERR_VOD_ACC_PWD_ERR:
			msg = "登录帐号或登录密码错误";
			break;
		case ERR_UNSURPORT_MOBILE:
			msg = "不支持移动设备";
			break;

		default:
			break;
		}
		return msg;
	}

	
	//测试使用
	@Override
	public boolean onLongClick(View v) {
		mEditVodDomin.setText("live.roadshowchina.cn");
		mEditVodNumber.setText("7c369c9018a04c23ba0de7d23e4a069f");
		mEditAccount.setText("");
		mLoginPassword.setText("");
		mVodPassword.setText("123456");
		mEditNickname.setText("蔡先生-深圳市路演中网络科技有限公司");
		return true;
	}

	
	private void startLogService() {
		startService(new Intent(this, LogCatService.class));
	}

	private void stopLogService() {
			stopService(new Intent(this, LogCatService.class));
	}
}
