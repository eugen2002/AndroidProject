package ua.newproject.ui;

import java.util.ArrayList;

import ua.newproject.Constants;
import ua.newproject.R;
import ua.newproject.Util;
import ua.newproject.model.ItemModel;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class ListViewFragment extends SherlockFragment implements OnItemClickListener,
		OnClickListener {

	private final String			LOG_TAG			= getClass().getSimpleName();

	private ArrayList<ItemModel>	itemModelArray	= new ArrayList<ItemModel>();
	private ItemListAdapter			itemListAdapter	= null;
	private Context					context			= null;
	private ListView				listView		= null;
	private TextView				menuTitle		= null;

	public ArrayList<ItemModel> getItemModelArray() {
		return itemModelArray;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		if (itemModelArray != null) {
			
			outState.putParcelableArrayList(Constants.ARRAY_LIST, itemModelArray);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		if (savedInstanceState != null) {
			Log.d(LOG_TAG, "savedInstanceState != null");
			itemModelArray = savedInstanceState.getParcelableArrayList(Constants.ARRAY_LIST);
		}
		context = getSherlockActivity();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.list, null);
		view.findViewById(R.id.btnUpdateList).setOnClickListener(this);
		listView = (ListView) view.findViewById(R.id.listView);
		menuTitle = (TextView) view.findViewById(R.id.titleMenu);
		if (savedInstanceState != null) {
			itemModelArray = savedInstanceState.getParcelableArrayList(Constants.ARRAY_LIST);
			Log.d(LOG_TAG, "itemModelArray from savedInstanceState = " + itemModelArray.size());
			if (itemModelArray != null && itemModelArray.size() != 0) {
				fillListView();
				checkListTitle();
			}
		} else {
			return view;
		}
		return view;
	}

	@SuppressWarnings("unchecked")
	private void listLoad() {

		Object objectResult = new Util().getItemModelArray(context);
		String stringResult = objectResult.toString();
		if (stringResult.equals(Constants.NETWORK_CONNECTION_ERROR)) {
			Log.d(LOG_TAG, Constants.NETWORK_CONNECTION_ERROR);
			getToast(Constants.NETWORK_CONNECTION_ERROR);
			return;
		}
		Log.d(LOG_TAG, "itemModelArray.size " + itemModelArray.size());
		itemModelArray = (ArrayList<ItemModel>) objectResult;
		fillListView();
		checkListTitle();
	}

	private void checkListTitle() {

		if (itemListAdapter == null) {
			menuTitle.setText(getString(R.string.list_empty_title));
		} else {
			menuTitle.setText(getString(R.string.list_title));
		}
	}

	private void fillListView() {

		itemListAdapter = new ItemListAdapter(context, itemModelArray);
		listView.setAdapter(itemListAdapter);
		listView.setOnItemClickListener(this);
		itemListAdapter.notifyDataSetChanged();
	}

	private void getToast(String message) {

		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

		setDataListener.setData(position + 1);
	}

	interface setDataListener {

		public void setData(Integer value);
	}

	setDataListener	setDataListener;

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
		try {
			setDataListener = (setDataListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement setDataListener");
		}
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.btnUpdateList) {
			listLoad();
		}
	}
}