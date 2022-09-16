package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.bean.Category;
import util.DBConnectionUtil;
import util.DefineUtil;

public class CategoryDAO {
	private Connection conn;
	private PreparedStatement pst;
	private Statement st;
	private ResultSet rs;
	
	public ArrayList<Category> getItems() {
		ArrayList<Category> items = new ArrayList<Category>();
		conn = DBConnectionUtil.getConnection();
		String query ="SELECT * FROM categories ORDER BY id DESC";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()) {
				Category item = new Category(rs.getInt("id"), rs.getString("name"));
				items.add(item);
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
		
		return items;
	}

	public int addItem(Category item) {
		int result =0; 
		conn = DBConnectionUtil.getConnection();
		String query ="INSERT INTO categories(name) VALUES(?)";
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, item.getName());
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

	public Category getItem(int id) {
		Category item = null;
		conn = DBConnectionUtil.getConnection();
		String query ="SELECT * FROM categories WHERE id = ?";
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if(rs.next()) {
				 item = new Category(rs.getInt("id"), rs.getString("name"));
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
		
		return item;
	}

	public int editItem(Category item) {
		int result =0; 
		conn = DBConnectionUtil.getConnection();
		String query ="UPDATE categories SET name = ? WHERE id = ?";
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, item.getName());
			pst.setInt(2, item.getId());
			result = pst.executeUpdate();
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
		return result;
	}

	public int delItem(int id) {
		int result =0; 
		conn = DBConnectionUtil.getConnection();
		String query ="DELETE FROM categories  WHERE id = ?";
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, id);
			result = pst.executeUpdate();
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
		return result;
	}

	public int getNumberOfItems() {
		conn = DBConnectionUtil.getConnection();
		String query ="SELECT COUNT(*) AS count FROM categories" ;
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

	public ArrayList<Category> getItemsPagination(int offset) {
		ArrayList<Category> items = new ArrayList<Category>();
		conn = DBConnectionUtil.getConnection();
		String query ="SELECT * FROM categories ORDER BY id DESC LIMIT ?,? ";
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while(rs.next()) {
				Category item = new Category(rs.getInt("id"), rs.getString("name"));
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

	public int getNumberOfItems(String name) {
		conn = DBConnectionUtil.getConnection();
		String query ="SELECT COUNT(*) AS count FROM categories WHERE name LIKE ?" ;
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

	public ArrayList<Category> getItemsPagination(int offset, String cname) {
		ArrayList<Category> items = new ArrayList<Category>();
		conn = DBConnectionUtil.getConnection();
		String query ="SELECT * FROM categories WHERE name LIKE '%" + cname + "%' ORDER BY id DESC LIMIT " +offset+","+DefineUtil.NUMBER_PER_PAGE+ "" ;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()) {
				Category item = new Category(rs.getInt("id"), rs.getString("name"));
				items.add(item);
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
		
		return items;
	}
	
}
