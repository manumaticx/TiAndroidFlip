package de.manumaticx.androidflip;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;

import se.emilsjolander.flipview.OverFlipMode;


@Kroll.module(name="Androidflip", id="de.manumaticx.androidflip")
public class AndroidflipModule extends KrollModule
{
	@Kroll.constant public static final String ORIENTATION_VERTICAL = "vertical";
	@Kroll.constant public static final String ORIENTATION_HORIZONTAL = "horizontal";
	@Kroll.constant public static final OverFlipMode OVERFLIPMODE_GLOW = OverFlipMode.GLOW;
	@Kroll.constant public static final OverFlipMode OVERFLIPMODE_RUBBER_BAND = OverFlipMode.RUBBER_BAND;
	
	public AndroidflipModule() {
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
		
	}
}

