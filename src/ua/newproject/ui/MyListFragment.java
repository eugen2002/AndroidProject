package ua.newproject.ui;

import ua.newproject.R;
import ua.newproject.Util;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.slidingmenu.lib.SlidingMenu;

public class MyListFragment extends Fragment implements OnItemClickListener {

	final String LOG_TAG = getClass().getSimpleName();
	SlidingMenu slidingMenu;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.list, null);

		slidingMenu = new SlidingMenu(getActivity());
		ListView listView = (ListView) view.findViewById(R.id.listView);
		ItemListAdapter itemListAdapter = new ItemListAdapter(getActivity(),
				new Util().getItemModelArray());
		listView.setAdapter(itemListAdapter);
		listView.setOnItemClickListener(this);
		return view;
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

}