package ua.newproject.ui;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public abstract class BaseActivity extends SlidingFragmentActivity {

	protected ImageLoader	imageLoader	= ImageLoader.getInstance();
}