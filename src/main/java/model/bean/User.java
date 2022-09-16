package model.bean;

public class User {
	private int id;
	private String userName;
	private String passWord;
	private String fullName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public User(int id, String userName, String passWord, String fullName) {
		super();
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.fullName = fullName;
	}
	public User() {
		super();
	}
	
	
}
