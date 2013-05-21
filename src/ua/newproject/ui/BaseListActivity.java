package ua.newproject.ui;

import java.util.HashMap;

import ua.newproject.Constants;
import ua.newproject.R;
import ua.newproject.Util;
import ua.newproject.ui.ListViewFragment.setDataListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.slidingmenu.lib.SlidingMenu;

public class BaseListActivity extends BaseActivity implements setDataListener, OnClickListener {

	private final String	LOG_TAG		= getClass().getSimpleName();
	private Bitmap			picture		= null;

	private String			title		= "";
	private String			body		= "";
	private String			address		= "";
	private int				position	= 0;

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

			if (!title.equals("")) {
				tvTitleItem.setText("title : " + title);
			}
			if (!body.equals("")) {
				tvBodyItem.setText("body : " + body);
			}
			if (!address.equals("")) {
				tvAddressItem.setText("address : " + address);
			}
			if (picture != null) {
				imageItem.setImageBitmap(picture);
			}
		}
	}

	private void initUI() {
		tvTitleItem = (TextView) findViewById(R.id.tvTitleItem);
		tvBodyItem = (TextView) findViewById(R.id.tvBodyItem);
		tvAddressItem = (TextView) findViewById(R.id.tvAddressItem);
		imageItem = (ImageView) findViewById(R.id.imageItem);
		slidingMenu = getSlidingMenu();
		slidingMenu.setBehindOffset(getDisplayWidth() / 3);
		btnShowMenu = (Button) findViewById(R.id.btnShowList);
		btnShowMenu.setOnClickListener(this);
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

		if (pos == null) {
			return;
		} else {
			position = pos;
		}
		updateDetails(position);
	}

	private void updateDetails(int position) {
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
			imageLoader.displayImage(stringMap.get(Constants.PICTURE_URL), imageItem,
					new SimpleImageLoadingListener() {

						@Override
						public void onLoadingComplete(String arg0, View arg1, Bitmap bmp) {
							picture = bmp;
						}
					});
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		outState.putString(Constants.TITLE, title);
		outState.putString(Constants.BODY, body);
		outState.putString(Constants.ADDRESS, address);
		outState.putParcelable(Constants.PICTURE, picture);

		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.reload_list:
				if (position == 0) {
					getToast(Constants.DOWNLOAD_MESSAGE);
				} else {
					updateDetails(position);
				}
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}