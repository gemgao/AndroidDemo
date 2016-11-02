package com.gensee.playerdemo;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.gensee.common.ServiceType;
import com.gensee.entity.ChatMsg;
import com.gensee.entity.DocInfo;
import com.gensee.entity.InitParam;
import com.gensee.entity.QAMsg;
import com.gensee.entity.UserInfo;
import com.gensee.entity.VodObject;
import com.gensee.media.GSOLPlayer.OnOLPlayListener;
import com.gensee.media.VODPlayer;
import com.gensee.player.OnPlayListener;
import com.gensee.player.Player;
import com.gensee.view.GSVideoView;
import com.gensee.view.GSVideoView.RenderMode;
import com.gensee.vod.VodSite;
import com.gensee.vod.VodSite.OnVodListener;
import com.gensee.voddemo.VodActivity;

public class VideoActivity extends Activity {

	private GSVideoView videoView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		videoView = new GSVideoView(this);
		setContentView(videoView);
		videoView.setRenderMode(RenderMode.RM_FILL_CENTER_CROP);
		
		InitParam p = new InitParam();
//		p.setDomain("wx.gensee.com");
//		p.setNumber("61596074");
//		p.setJoinPwd("019772");
//		p.setNickName("sxh123");
//		p.setServiceType(ServiceType.TRAINING);
		
		p.setDomain("192.168.1.158");
		p.setNumber("27325634");
		p.setJoinPwd("333333");
		p.setNickName("");
		p.setServiceType(ServiceType.WEBCAST);
		
		castInit(p);
//		p.setDomain("192.168.1.164");
//		p.setNumber("61596074");
//		p.setLiveId("0dea6e2ea2334f1580446f6ec25f806b");
//		p.setJoinPwd("019772");
//		p.setNickName("sxh123");
//		p.setServiceType(ServiceType.WEBCAST);
//		vodInit(p);
	}
	
	private void castInit(InitParam initParam){
		Player player = new Player();
		player.setGSVideoView(videoView);
		player.join(this, initParam, new OnPlayListener() {
			
			@Override
			public void onVideoEnd() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onVideoBegin() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onUserUpdate(UserInfo arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onUserLeave(UserInfo arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onUserJoin(UserInfo arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSubject(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onRosterTotal(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onRollcall(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onReconnecting() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPublish(boolean arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPublicMsg(long arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageSize(int arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onMicNotify(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLottery(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLiveText(String arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLeave(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onJoin(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onInvite(int arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFileShareDl(int arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFileShare(int arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onErr(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDocSwitch(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCaching(boolean arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAudioLevel(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onVideoSize(int arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	private void vodInit(InitParam initParam){
		VodSite site = new VodSite(this);
		site.setVodListener(new OnVodListener() {
			
			@Override
			public void onVodObject(String arg0) {
				vod(arg0);
			}
			
			@Override
			public void onVodErr(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onVodDetail(VodObject arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onQaHistory(String arg0, List<QAMsg> arg1, int arg2,
					boolean arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChatHistory(String arg0, List<ChatMsg> arg1, int arg2,
					boolean arg3) {
				// TODO Auto-generated method stub
				
			}
		});

		site.getVodObject(initParam);
	}
	private void vod(String vodId){
		final VODPlayer player = new VODPlayer();
		player.setGSVideoView(videoView);
		player.play(vodId, new OnOLPlayListener() {
			
			@Override
			public void onVideoStart() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onVideoSize(int arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSeek(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPosition(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPlayStop() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPlayResume() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPlayPause() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageSize(int arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onInit(int arg0, boolean arg1, int arg2, List<DocInfo> arg3) {
				player.seekTo(108 * 60 * 1000);
			}
			
			@Override
			public void onError(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChat(List<ChatMsg> arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCaching(boolean arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAudioLevel(int arg0) {
				// TODO Auto-generated method stub
				
			}
		}, null);
	}
}
