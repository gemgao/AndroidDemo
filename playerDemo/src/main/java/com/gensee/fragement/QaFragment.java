package com.gensee.fragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gensee.player.Player;
import com.gensee.playerdemo.R;
import com.gensee.view.GSImplQaView;

public class QaFragment extends Fragment {

	private Player mPlayer;
	private View mView;
	private GSImplQaView mGSQaView;

	public QaFragment(Player player) {
		this.mPlayer = player;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mView = inflater.inflate(R.layout.imqa, null);
		mGSQaView = (GSImplQaView) mView.findViewById(R.id.impqaview);
		mPlayer.setGSQaView(mGSQaView);
		return mView;
	}

}