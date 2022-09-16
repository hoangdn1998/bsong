package model.bean;

public class Comment {
	private int id;
	private String comment;
	private int id_song;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getId_song() {
		return id_song;
	}
	public void setId_song(int id_song) {
		this.id_song = id_song;
	}
	public Comment(int id, String comment, int id_song) {
		super();
		this.id = id;
		this.comment = comment;
		this.id_song = id_song;
	}
	public Comment() {
		super();
	}
	
	
}
