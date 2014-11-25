package de.manumaticx.androidflip;

import java.util.ArrayList;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

import se.emilsjolander.flipview.FlipView;
import se.emilsjolander.flipview.OverFlipMode;
import android.app.Activity;

public class TiFlipView extends TiUIView {
	
	private static final String TAG = "FlipViewProxy";
	
	public static final String PROPERTY_ORIENTATION = "orientation";
	public static final String PROPERTY_OVERFLIPMODE = "overFlipMode";
	
	private FlipView mFlipView;
	private final ArrayList<TiViewProxy> mViews;
	private final FlipViewAdapter mAdapter;
	private int mCurIndex = 0;
	
	public TiFlipView(TiViewProxy proxy) {
		super(proxy);
		Activity activity = proxy.getActivity();
		mViews = new ArrayList<TiViewProxy>();
		
		mAdapter = new FlipViewAdapter(activity, mViews);
		mFlipView = new FlipView(activity);
		mFlipView.setAdapter(mAdapter);
		
	}

	@Override
	public void processProperties(KrollDict d)
	{
		if (d.containsKey(TiC.PROPERTY_VIEWS)) {
			setViews(d.get(TiC.PROPERTY_VIEWS));
		}
		
		if (d.containsKey(PROPERTY_ORIENTATION)) {
			mFlipView.setOrientation((String) d.get(PROPERTY_ORIENTATION));
		}
		
		if (d.containsKey(PROPERTY_OVERFLIPMODE)) {
			mFlipView.setOverFlipMode((OverFlipMode) d.get(PROPERTY_OVERFLIPMODE));
		}
		
		setNativeView(mFlipView);
		
		super.processProperties(d);
	}
	
	@Override
	public void propertyChanged(String key, Object oldValue, Object newValue, KrollProxy proxy) {
		
		if (key.equals(TiC.PROPERTY_VIEWS)) {
			setViews(newValue);
		}
		
		if (key.equals(PROPERTY_ORIENTATION)) {
			mFlipView.setOrientation((String) newValue);
		}
		
		if (key.equals(PROPERTY_OVERFLIPMODE)) {
			mFlipView.setOverFlipMode((OverFlipMode) newValue);
		}
	}
	
	private void clearViewsList()
	{
		if (mViews == null || mViews.size() == 0) {
			return;
		}
		for (TiViewProxy viewProxy : mViews) {
			viewProxy.releaseViews();
			viewProxy.setParent(null);
		}
		mViews.clear();
	}
	
	public void setViews(Object viewsObject)
	{
		boolean changed = false;
		clearViewsList();

		if (viewsObject instanceof Object[]) {
			Object[] views = (Object[])viewsObject;
			Activity activity = this.proxy.getActivity();
			for (int i = 0; i < views.length; i++) {
				if (views[i] instanceof TiViewProxy) {
					TiViewProxy tv = (TiViewProxy)views[i];
					tv.setActivity(activity);
					tv.setParent(this.proxy);
					mViews.add(tv);
					changed = true;
				}
			}
		}
		if (changed) {
			mAdapter.notifyDataSetChanged();
		}
	}
	
	public void addView(TiViewProxy proxy)
	{
		if (!mViews.contains(proxy)) {
			proxy.setActivity(this.proxy.getActivity());
			proxy.setParent(this.proxy);
			mViews.add(proxy);
			getProxy().setProperty(TiC.PROPERTY_VIEWS, mViews.toArray());
			mAdapter.notifyDataSetChanged();
		}
	}

	public void removeView(TiViewProxy proxy)
	{
		if (mViews.contains(proxy)) {
			mViews.remove(proxy);
			proxy.setParent(null);
			getProxy().setProperty(TiC.PROPERTY_VIEWS, mViews.toArray());
			mAdapter.notifyDataSetChanged();
		}
	}
	
	public void moveNext()
	{
		move(mCurIndex + 1, true);
	}

	public void movePrevious()
	{
		move(mCurIndex - 1, true);
	}

	private void move(int index, boolean smoothFlip)
	{
		if (index < 0 || index >= mViews.size()) {
			if (Log.isDebugModeEnabled()) {
				Log.w(TAG, "Request to move to index " + index+ " ignored, as it is out-of-bounds.", Log.DEBUG_MODE);
			}
			return;
		}
		mCurIndex = index;
		
		if (smoothFlip) {
			mFlipView.smoothFlipTo(index);
		} else {
			mFlipView.flipTo(index);
		}
	}
}