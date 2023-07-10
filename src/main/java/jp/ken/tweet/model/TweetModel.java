package jp.ken.tweet.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class TweetModel implements Serializable{
	@NotEmpty(message = "名前は必須項目です")
	private String name;
	@NotEmpty(message = "コメントは必須項目です")
	private String comment;
	private int id;
	private String title;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}


}
