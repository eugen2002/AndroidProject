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

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ItemListAdapter extends BaseAdapter {
	private DisplayImageOptions		options;
	final String					LOG_TAG	= getClass().getSimpleName();

	private ImageLoader				imageLoader;
	private final Context			context;
	private ArrayList<ItemModel>	itemModel;

	public ItemListAdapter(Context context, ArrayList<ItemModel> itemModel) {
		this.context = context;
		this.itemModel = itemModel;
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_launcher)
				.cacheInMemory().cacheOnDisc().build();
	}

	// For view recycling (optimization for listview)
	static class ViewHolder {
		TextView	tvTitle;
		TextView	tvBody;
		ImageView	picture;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
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

		ItemModel itemModel = ((ItemModel) getItem(position));

		if (itemModel != null) {
			holder = (ViewHolder) view.getTag();
			holder.tvTitle.setText(itemModel.getTitle());
			holder.tvBody.setText(itemModel.getBody());
			imageLoader = ImageLoader.getInstance();
			imageLoader.init(ImageLoaderConfiguration.createDefault(context));
			imageLoader.displayImage(itemModel.getPictureURL(), holder.picture, options);
		}
		return view;
	}

	@Override
	public int getCount() {
		return itemModel.size();
	}

	@Override
	public Object getItem(int position) {
		return itemModel.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}