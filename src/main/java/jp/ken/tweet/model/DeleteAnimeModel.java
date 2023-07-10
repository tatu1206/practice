package jp.ken.tweet.model;

import java.io.Serializable;

public class DeleteAnimeModel implements Serializable {
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	} 
}
