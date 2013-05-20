package ua.newproject.ui;

import java.util.ArrayList;

import ua.newproject.R;
import ua.newproject.model.ItemModel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ItemListAdapter extends BaseAdapter {

//	private final String			LOG_TAG		= getClass().getSimpleName();
	private Context					context;
	private ArrayList<ItemModel>	itemModelArray;
	private ItemModel				itemModel	= null;

	// For view recycling (optimization for listview)
	static class ViewHolder {
		TextView	tvTitle;
		TextView	tvBody;
		ImageView	picture;
	}

	public ItemListAdapter(Context context, ArrayList<ItemModel> itemModel) {
		this.context = context;
		this.itemModelArray = itemModel;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.base_menu_item, null);
			holder.tvTitle = (TextView) view.findViewById(R.id.tv_title);
			holder.tvBody = (TextView) view.findViewById(R.id.tv_body);
			holder.picture = (ImageView) view.findViewById(R.id.picture);
			view.setTag(holder);
		}
		if (position % 2 != 0) {
			view.setBackgroundResource(R.color.listview_item_2);
		} else {
			view.setBackgroundResource(R.color.listview_item_1);
		}

		itemModel = ((ItemModel) getItem(position));

		if (itemModel != null) {
			holder = (ViewHolder) view.getTag();
			holder.tvTitle.setText(itemModel.getTitle());
			holder.tvBody.setText(itemModel.getBody());

			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.displayImage(itemModel.getPictureURL(), holder.picture);
		}
		return view;
	}

	@Override
	public int getCount() {
		return itemModelArray.size();
	}

	@Override
	public Object getItem(int position) {
		return itemModelArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public ArrayList<ItemModel> getItemModelArray() {
		return itemModelArray;
	}
}
