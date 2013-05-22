package ua.newproject.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class ItemModel  implements Parcelable {

	private int	id;
	private String	title;
	private String	body;
	private String	pictureURL;

	public ItemModel() {
	}

	public ItemModel(int id, String title, String body, String pictureURL, Bitmap picture) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.pictureURL = pictureURL;
	}

	public ItemModel(JSONObject json) throws JSONException {
		this.id = json.getInt("id");
		this.title = json.getString("title");
		this.body = json.getString("body");
		this.pictureURL = json.getString("picture");
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public CharSequence getTitle() {
		return title;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public String getBody() {
		return body;
	}

	public int getID() {
		return id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(id);
		parcel.writeString(title);
		parcel.writeString(body);
		parcel.writeString(pictureURL);
	}

	public static final Creator<ItemModel>	CREATOR	= 
			new Parcelable.Creator<ItemModel>() {
			// data extract from Parcel
				public ItemModel createFromParcel(Parcel in) {
					return new ItemModel(in);
				}
				public ItemModel[] newArray(int size) {
					return new ItemModel[size];
				}
			};

	// constructor, data reads from the Parcel
	private ItemModel(Parcel parcel) {
		id = parcel.readInt();
		title = parcel.readString();
		body = parcel.readString();
		pictureURL = parcel.readString();
	}
	
}
