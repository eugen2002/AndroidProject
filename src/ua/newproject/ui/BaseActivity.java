package ua.newproject.ui;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public abstract class BaseActivity extends SlidingFragmentActivity {

	protected ImageLoader	imageLoader	= ImageLoader.getInstance();
	private final String	LOG_TAG		= getClass().getSimpleName();

	protected void getToast(String message) {
		Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	protected int getDisplayWidth() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Log.d(LOG_TAG, "resolution: " + metrics.widthPixels + " x " + metrics.heightPixels);
		return metrics.widthPixels;
	}


}