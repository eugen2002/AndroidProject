package ua.newproject.ui;

import java.util.HashMap;

import ua.newproject.Constants;
import ua.newproject.R;
import ua.newproject.Util;
import ua.newproject.ui.ListViewFragment.setDataListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.slidingmenu.lib.SlidingMenu;

public class BaseListActivity extends BaseActivity implements setDataListener, OnClickListener {

	private final String	LOG_TAG		= getClass().getSimpleName();
	private Bitmap			picture		= null;

	private String			title		= "";
	private String			body		= "";
	private String			address		= "";

	private ImageView		imageItem	= null;
	private SlidingMenu		slidingMenu	= null;
	private Button			btnShowMenu	= null;
	private TextView		tvTitleItem, tvBodyItem, tvAddressItem;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(LOG_TAG, "onCreate");
		setBehindContentView(R.layout.activity_menu);
		initUI();

		if (savedInstanceState != null) {
			Log.d(LOG_TAG, "savedInstanceState != null");
			title = savedInstanceState.getString(Constants.TITLE);
			body = savedInstanceState.getString(Constants.BODY);
			address = savedInstanceState.getString(Constants.ADDRESS);
			picture = savedInstanceState.getParcelable(Constants.PICTURE);

			tvTitleItem.setText("title : " + title);
			tvBodyItem.setText("body : " + body);
			tvAddressItem.setText("address : " + address);
			imageItem.setImageBitmap(picture);
		}
	}

	private void initUI() {
		tvTitleItem = (TextView) findViewById(R.id.tvTitleItem);
		tvBodyItem = (TextView) findViewById(R.id.tvBodyItem);
		tvAddressItem = (TextView) findViewById(R.id.tvAddressItem);
		imageItem = (ImageView) findViewById(R.id.imageItem);
		slidingMenu = getSlidingMenu();
		slidingMenu.setBehindOffset(getDisplayWidth() / 3);
		btnShowMenu = (Button) findViewById(R.id.btnShowMenu);
		btnShowMenu.setOnClickListener(this);
	}

	private int getDisplayWidth() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Log.d(LOG_TAG, "resolution: " + metrics.widthPixels + " x " + metrics.heightPixels);
		return metrics.widthPixels;
	}

	@Override
	public void onClick(View v) {
		Log.d(LOG_TAG, "showMenu");
		slidingMenu.showMenu();
	}

	@Override
	public void setData(Integer pos) {
		Log.d(LOG_TAG, "SetData");
		slidingMenu.showContent();

		int position = 0;
		if (pos == null) {
			return;
		} else {
			position = pos;
		}

		Object objectResult = new Util().getStringHashMap(position);
		String stringResult = objectResult.toString();

		if (stringResult.equals(Constants.NETWORK_CONNECTION_ERROR)) {
			Log.d(LOG_TAG, "stringResult : " + stringResult);
			getToast(Constants.NETWORK_CONNECTION_ERROR);
			return;
		} else {
			@SuppressWarnings("unchecked")
			HashMap<String, String> stringMap = (HashMap<String, String>) new Util()
					.getStringHashMap(position);
			title = stringMap.get(Constants.TITLE);
			body = stringMap.get(Constants.BODY);
			address = stringMap.get(Constants.ADDRESS);

			Log.d(LOG_TAG, "String stringMap = " + title);
			tvTitleItem.setText("title : " + title);
			tvBodyItem.setText("body : " + body);
			tvAddressItem.setText("address : " + address);
			// load image file
			imageLoader.displayImage(stringMap.get(Constants.PICTURE_URL), imageItem,
					new SimpleImageLoadingListener() {

						@Override
						public void onLoadingComplete(String arg0, View arg1, Bitmap bmp) {
							picture = bmp;
						}
					});
		}
	}

	private void getToast(String message) {
		Toast toast = Toast.makeText(BaseListActivity.this, message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		outState.putString(Constants.TITLE, title);
		outState.putString(Constants.BODY, body);
		outState.putString(Constants.ADDRESS, address);
		outState.putParcelable(Constants.PICTURE, picture);

		super.onSaveInstanceState(outState);
	}

// not working yet
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.activity_itemlist, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.reload_list:
				ListViewFragment fragment = (ListViewFragment) getSupportFragmentManager()
						.findFragmentById(R.id.listViewFragment);
				fragment.updateList();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
