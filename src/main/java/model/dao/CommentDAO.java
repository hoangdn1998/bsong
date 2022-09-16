package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.bean.Comment;
import util.DBConnectionUtil;

public class CommentDAO {
	private Connection conn;
	private PreparedStatement pst;
	private Statement st;
	private ResultSet rs;
	
	public void addItems(String comment, int id) {
		conn = DBConnectionUtil.getConnection();
		String query ="INSERT INTO comments(comment,id_song) VALUES(?,?) ";
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1,comment);
			pst.setInt(2,id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pst != null && conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public ArrayList<Comment> getItems(int id, int limit) {
		ArrayList<Comment> items = new ArrayList<Comment>();
		conn = DBConnectionUtil.getConnection();
		String query ="SELECT * FROM comments WHERE id_song = ? ORDER BY id DESC LIMIT ?";
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, id);
			pst.setInt(2, limit);
			rs = pst.executeQuery();
			while(rs.next()) {
				Comment item = new Comment(rs.getInt("id"), rs.getString("comment"), rs.getInt("id_song"));
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null && pst != null && conn != null ) {
				try {
					rs.close();
					pst.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return items;
	}

}
