package com.wxlh.sptas.wmapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public abstract class BuActyListener implements OnTouchListener {

	protected abstract void onPause();

	protected abstract void onResume();

	protected abstract void onStop();

	protected abstract void onDestroy();

	protected abstract void onNewIntent(Intent intent);
	
	protected abstract void onSaveInstanceState(Bundle outState);

	protected abstract void onRestoreInstanceState(Bundle savedInstanceState);

	protected abstract void onActivityResult(int requestCode, int resultCode, Intent data);

	public static interface DelayDoListener {
		void delayDo(long delayMillis);
	}

	public static interface PostDoListener {
		void post();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}

	public void mHandlePostDelayed(DelayDoListener delayDoListener, long delayMillis) {
		
	}

}
