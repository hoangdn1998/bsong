package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.bean.User;
import util.DBConnectionUtil;
import util.DefineUtil;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pst;
	private Statement st;
	private ResultSet rs;
	
	public ArrayList<User> getItems() {
		ArrayList<User> items = new ArrayList<User>();
		conn = DBConnectionUtil.getConnection();
		String query ="SELECT * FROM users ";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()) {
				User item = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("fullname"));
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null && st != null && conn != null) {
				try {
					rs.close();
					st.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return items;
	}

	public int addItem(User item) {
		int result =0; 
		conn = DBConnectionUtil.getConnection();
		String query ="INSERT INTO users(username,password,fullname) VALUES(?,?,?)";
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, item.getUserName());
			pst.setString(2, item.getPassWord());
			pst.setString(3, item.getFullName());
			result = pst.executeUpdate();
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
		return result;
	}

	public boolean haveUser(String username) {
		conn = DBConnectionUtil.getConnection();
		String query ="SELECT * FROM users WHERE username = ?";
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1,username);
			rs = pst.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null && pst != null && conn != null) {
				try {
					rs.close();
					pst.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public User getItem(int id) {
		User item = null;
		conn = DBConnectionUtil.getConnection();
		String query ="SELECT * FROM users WHERE id = ?";
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if(rs.next()) {
				 item = new User(rs.getInt("id"), rs.getString("username"),rs.getString("password"),rs.getString("fullname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null && pst != null && conn != null) {
				try {
					rs.close();
					pst.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return item;
	}

	public int editItem(User item) {
		int result =0; 
		conn = DBConnectionUtil.getConnection();
		String query ="UPDATE users SET password =?, fullname =? WHERE id =?";
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, item.getPassWord());
			pst.setString(2, item.getFullName());
			pst.setInt(3, item.getId());
			result = pst.executeUpdate();
		} catch (NullPointerException |SQLException e ) {
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
		return result;
	}

	public int delItem(int id) {
		int result =0; 
		conn = DBConnectionUtil.getConnection();
		String query ="DELETE FROM users  WHERE id = ?";
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, id);
			result = pst.executeUpdate();
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
		return result;
	}

	public User existUser(String username, String password) {
		User item = null;
		conn = DBConnectionUtil.getConnection();
		String query ="SELECT * FROM users WHERE username = ? AND password =?";
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, username);
			pst.setString(2, password);
			rs = pst.executeQuery();
			if(rs.next()) {
				 item = new User(rs.getInt("id"), rs.getString("username"),rs.getString("password"),rs.getString("fullname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null && pst != null && conn != null) {
				try {
					rs.close();
					pst.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return item;
	}

	public int getNumberOfItems() {
		conn = DBConnectionUtil.getConnection();
		String query ="SELECT COUNT(*) AS count FROM users" ;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if(rs.next()) {
				int count = rs.getInt("count");
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null && st != null && conn != null) {
				try {
					rs.close();
					st.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	public ArrayList<User> getItemsPagination(int offset) {
		ArrayList<User> items = new ArrayList<User>();
		conn = DBConnectionUtil.getConnection();
		String query ="SELECT * FROM users LIMIT ?,?";
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while(rs.next()) {
				User item = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("fullname"));
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null && pst != null && conn != null) {
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

	public int getNumberOfItems(String name) {
		conn = DBConnectionUtil.getConnection();
		String query ="SELECT COUNT(*) AS count FROM users WHERE fullname LIKE ?" ;
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, "%" + name + "%");
			rs = pst.executeQuery();
			if(rs.next()) {
				int count = rs.getInt("count");
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null && pst != null && conn != null) {
				try {
					rs.close();
					pst.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	public ArrayList<User> getItemsPagination(int offset, String name) {
		ArrayList<User> items = new ArrayList<User>();
		conn = DBConnectionUtil.getConnection();
		String query ="SELECT * FROM users WHERE fullname LIKE ? ORDER BY id DESC LIMIT ?,? " ;
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, "%" + name + "%");
			pst.setInt(2, offset);
			pst.setInt(3, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while(rs.next()) {
				User item = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("fullname"));
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null && pst != null && conn != null) {
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
