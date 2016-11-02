package com.gensee.rtmpresourcelib;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RtmpSourceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rtmp_source);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rtmp_source, menu);
		return true;
	}

}
