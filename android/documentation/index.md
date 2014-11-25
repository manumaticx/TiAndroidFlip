# TiAndroidFlip API

## Properties

* __currentPage__ `Number` - Index of the active page
* __views__ `Ti.UI.View[]` - The pages within the flipView
* __orientation__ `String` - The flipping orientation (either _ORIENTATION_VERTICAL_ or _ORIENTATION_HORIZONTAL_)
* __overFlipMode__ `Number` - Same as OverScrollMode on ScrollableView (use _OVERFLIPMODE_GLOW_ to get the default android overscroll indicator or use  _OVERFLIPMODE_RUBBER_BAND_ to use a more iOS-like indication  )

## Methods

* __getViews( )__ - Gets the value of the __views__ property.
* __setViews( )__ - Sets the value of the __views__ property.

## Events

* __flipped__ - fired when page was flipped
  * `index` - index of the new page
