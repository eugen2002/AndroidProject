package ua.newproject.app;

import ua.newproject.R;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class BaseApplication extends Application {

	private final String	LOG_TAG	= getClass().getSimpleName();

	@Override
	public void onCreate() {
		Log.d(LOG_TAG, "--------------- Start Application --------------");
		super.onCreate();
		initImageLoader(getApplicationContext());
	}
// ImageLoader initialization for application
	public static void initImageLoader(Context context) {

//		File cacheDir = StorageUtils.getOwnCacheDirectory(context, "UniversalImageLoader/Cache");
		
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher)
				.resetViewBeforeLoading()
				.cacheInMemory()
//				.cacheOnDisc()
				.build();
// in reality the responsiveness of the GUI  is better when using memory-scache
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 3)
				.threadPoolSize(3) // no more than 5
//				.discCache(new UnlimitedDiscCache(cacheDir)) // default
//				.discCacheSize(2 * 1024 * 1024)
//				.discCacheFileCount(50)
//				.discCacheFileNameGenerator(new HashCodeFileNameGenerator())
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
//				.denyCacheImageMultipleSizesInMemory()
				.defaultDisplayImageOptions(options)
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}