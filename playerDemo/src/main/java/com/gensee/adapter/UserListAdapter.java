package com.gensee.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gensee.entity.UserInfo;
import com.gensee.playerdemo.R;

public class UserListAdapter extends BaseAdapter {
	public static final long PUBLIC_CHAT_TAG = -1000;
	private Context mContext;
	private TextView mTextView;
	private List<UserInfo> mList;

	public UserListAdapter(Context context) {

		this.mContext = context;
		mList = new ArrayList<UserInfo>();
	
		
		//构造公聊的UserInfo信息, 
	    UserInfo publicUserInfo = new UserInfo(PUBLIC_CHAT_TAG, context.getResources().getString(R.string.public_chat), -1, -1);
	    mList.add(publicUserInfo);
	}

	public void addInfo(UserInfo info) {

		int i = 0;
		for (i = 0; i < mList.size(); i++) {
			UserInfo user = mList.get(i);
			if (user.getUserId() == info.getUserId()) {
				user.update(info);
				notifyDataSetChanged();
				break;
			}
		}
		if (i >= mList.size()) {

			mList.add(info);
			notifyDataSetChanged();
		}

	}

	public void clear() {
		mList.clear();
		notifyDataSetChanged();
	}

	public void leaveInfo(UserInfo info) {

		mList.remove(info);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (mList == null)
			return 0;
		return mList.size();
	}

	@Override
	public Object getItem(int position) {

		return 0;
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null)
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.user_item, null);
		mTextView = (TextView) convertView
				.findViewById(R.id.text_useritem_nickname);
		mTextView.setText(mList.get(position).getName());
		return convertView;
	}

	public List<UserInfo> getmList() {
		return mList;
	}

}
