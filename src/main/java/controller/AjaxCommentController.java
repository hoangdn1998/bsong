package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Comment;
import model.dao.CommentDAO;

public class AjaxCommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CommentDAO commentDAO;
	
    public AjaxCommentController() {
        super();
        commentDAO = new CommentDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//b1: lưu comment vào db
		//bước 2: select conment từ db lại 
		//tạo file .jsp cho Controller comment đó
		//truyền ra view , view hiển thị như giao diện 
		request.setCharacterEncoding("UTF-8");
		String comment = request.getParameter("acmt");
		int id = Integer.parseInt(request.getParameter("song_id"));
		int limit = 1;
		commentDAO.addItems(comment,id);
		ArrayList<Comment> comments = commentDAO.getItems(id,limit);
		request.setAttribute("comments", comments);
		
		RequestDispatcher rd = request.getRequestDispatcher("/public/ajaxcomment.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
