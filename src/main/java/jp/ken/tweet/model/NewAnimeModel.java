package jp.ken.tweet.model;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class NewAnimeModel implements Serializable {
	private String title;
	private String profile;
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
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
