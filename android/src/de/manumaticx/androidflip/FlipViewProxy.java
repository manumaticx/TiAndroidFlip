package de.manumaticx.androidflip;

import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

import android.app.Activity;


@Kroll.proxy(creatableInModule=AndroidflipModule.class)
public class FlipViewProxy extends TiViewProxy {

	public FlipViewProxy() {
		super();
	}

	@Override
	public TiUIView createView(Activity activity) {
		
		TiUIView flipview = new TiFlipView(this);
		flipview.getLayoutParams().autoFillsHeight = true;
		flipview.getLayoutParams().autoFillsWidth = true;
		return flipview;
	}

}