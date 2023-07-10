package jp.ken.tweet.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginModel implements Serializable {
	@NotEmpty(message = "idを入力してください")
	private String id;
	@NotEmpty(message = "passwordを入力してください")
	private String password;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
