package de.manumaticx.androidflip;

import java.util.ArrayList;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

import se.emilsjolander.flipview.FlipView;
import se.emilsjolander.flipview.OverFlipMode;
import android.app.Activity;


@Kroll.proxy(creatableInModule=AndroidflipModule.class)
public class FlipViewProxy extends TiViewProxy {

	private class TiFlipView extends TiUIView {
		
		public static final String PROPERTY_ORIENTATION = "orientation";
		public static final String PROPERTY_OVERFLIPMODE = "overFlipMode";
		
		private FlipView mFlipView;
		private final ArrayList<TiViewProxy> mViews;
		private final FlipViewAdapter mAdapter;
		
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
	}

	public FlipViewProxy()
	{
		super();
	}

	@Override
	public TiUIView createView(Activity activity)
	{
		TiUIView flipview = new TiFlipView(this);
		flipview.getLayoutParams().autoFillsHeight = true;
		flipview.getLayoutParams().autoFillsWidth = true;
		return flipview;
	}

}