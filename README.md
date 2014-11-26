# TiAndroidFlip

This is a Titanium module built upon [FlipView](https://github.com/emilsjolander/android-FlipView) for Android. You can consider it as a replacement for Ti.UI.ScrollableView as it behaves similar. Both flipping directions, vertical and horizontal, are supported.

![](documentation/demo.gif)

## Get it [![gitTio](http://gitt.io/badge.png)](http://gitt.io/component/de.manumaticx.androidflip)
Download the latest distribution ZIP-file and consult the [Titanium Documentation](http://docs.appcelerator.com/titanium/latest/#!/guide/Using_a_Module) on how install it, or simply use the [gitTio CLI](http://gitt.io/cli):

`$ gittio install de.manumaticx.androidflip`

## Using it

Full example is [here](example/)

```javascript
// require the module
var Flip = require('de.manumaticx.androidflip');

// create the flipView
var flipView = Flip.createFlipView({
	orientation: Flip.ORIENTATION_HORIZONTAL,
	overFlipMode: Flip.OVERFLIPMODE_RUBBER_BAND,
	views: views
});

// add flip listener
flipView.addEventListener('flipped', function(e){
	Ti.API.info("flipped to page " + e.index);
});

```

__RTFM?__ - [Documentation](documentation/index.md)

## License
	The MIT License (MIT)

	Copyright (c) 2014 Manuel Lehner

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in
	all copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
	THE SOFTWARE.
