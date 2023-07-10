package jp.ken.tweet.entity;

import java.io.Serializable;

public class Anime implements Serializable {
	private String title;
	private String profile;
	private String file;

	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
}
