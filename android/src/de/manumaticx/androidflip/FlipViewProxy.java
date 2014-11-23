package de.manumaticx.androidflip;

import java.util.ArrayList;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.util.Log;
import org.appcelerator.titanium.view.TiUIView;

import se.emilsjolander.flipview.FlipView;
import android.app.Activity;


@Kroll.proxy(creatableInModule=AndroidflipModule.class)
public class FlipViewProxy extends TiViewProxy
{
	private static final String TAG = "FlipViewProxy";

	private class TiFlipView extends TiUIView
	{
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
			
			setNativeView(mFlipView);
		}

		@Override
		public void processProperties(KrollDict d)
		{
			if (d.containsKey(TiC.PROPERTY_VIEWS)) {
				setViews(d.get(TiC.PROPERTY_VIEWS));
			}
			
			super.processProperties(d);
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


	// Constructor
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

	// Handle creation options
	@Override
	public void handleCreationDict(KrollDict options)
	{
		super.handleCreationDict(options);

		if (options.containsKey("message")) {
			Log.d(TAG, "example created with message: " + options.get("message"));
		}
	}

}