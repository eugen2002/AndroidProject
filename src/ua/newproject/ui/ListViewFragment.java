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
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;

public class ListViewFragment extends SherlockFragment implements OnItemClickListener, OnClickListener {

	private final String			LOG_TAG			= getClass().getSimpleName();

	private ArrayList<ItemModel>	itemModelArray	= new ArrayList<ItemModel>();
	private ItemListAdapter			itemListAdapter	= null;
	private Context					context			= null;
	private Bundle					bundle			= null;
	private ListView				listView		= null;
	private View					v				= null;

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
		context = getActivity();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list, null);
		view.findViewById(R.id.btnUpdateList).setOnClickListener(this);
		v = view;
		if (savedInstanceState != null) {
			updateList();
		}
		return view;
	}

	@SuppressWarnings("unchecked")
	public void updateList() {

		listView = (ListView) v.findViewById(R.id.listView);
		if (bundle == null) {
			Object objectResult = new Util().getItemModelArray(context);
			String stringResult = objectResult.toString();
			if (stringResult.equals(Constants.NETWORK_CONNECTION_ERROR)) {

				Log.d(LOG_TAG, Constants.NETWORK_CONNECTION_ERROR);
				getToast(Constants.NETWORK_CONNECTION_ERROR);
			} else {
				Log.d(LOG_TAG, "savedInstanceState = null " + itemModelArray.size());
				itemModelArray = (ArrayList<ItemModel>) objectResult;
			}
		} else {
			itemModelArray = bundle.getParcelableArrayList(Constants.ARRAY_LIST);
		}
		itemListAdapter = new ItemListAdapter(context, itemModelArray);
		listView.setAdapter(itemListAdapter);
		listView.setOnItemClickListener(this);
	}

	public void getToast(String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		setDataListener.setData(position + 1);
	}

	public interface setDataListener {
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
			updateList();
		}
	}

}