package ua.newproject.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

public class ItemModel {

	private Integer	id;
	private String	title;
	private String	body;
	private String	pictureURL;
	private Bitmap	picture	= null;

	public ItemModel() {
	}

	public ItemModel(int id, String title, String body, String pictureURL, Bitmap picture) {
		super();
		this.id = id;
		this.title = title;
		this.body = body;
		this.pictureURL = pictureURL;
		this.picture = picture;
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

	public void setPicture(Bitmap picture) {
		this.picture = picture;
	}

	public boolean hasDownloadedPicture() {
		return picture != null;
	}

	public Bitmap getPicture() {
		return picture;
	}

	public int getID() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		return id.equals(((ItemModel) o).id);
	}

}
