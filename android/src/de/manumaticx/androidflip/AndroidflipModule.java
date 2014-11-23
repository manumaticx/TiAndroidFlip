package de.manumaticx.androidflip;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;


@Kroll.module(name="Androidflip", id="de.manumaticx.androidflip")
public class AndroidflipModule extends KrollModule
{
	public AndroidflipModule() {
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
		
	}
}

