package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Category;
import model.bean.User;
import model.dao.CategoryDAO;
import util.AuthUtil;

public class AdminAddCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryDAO categoryDAO;
	
       
    public AdminAddCatController() {
        super();
        categoryDAO = new CategoryDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		//check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() +"/admin/index");
			return;
		}
		
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		
		//Chỉ admin mới được thêm người dùng
		if(!"admin".equals(userLogin.getUserName())) {
			//Không được phép
			response.sendRedirect(request.getContextPath() + "/admin/cats?err=3");
			return;
		}
		
				
		RequestDispatcher rd = request.getRequestDispatcher("/admin/cat/add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		//check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() +"/login");
			return;
		}
		
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		
		//Chỉ admin mới được thêm danh mục
		if(!"admin".equals(userLogin.getUserName())) {
			//Không được phép
			response.sendRedirect(request.getContextPath() + "/admin/cats?err=3");
			return;
		}
		
		//get data
		String name = request.getParameter("name");
		Category item = new Category(0, name);
		if(categoryDAO.addItem(item) >0) {
			//Thành công
			response.sendRedirect(request.getContextPath() + "/admin/cats?msg=1");
			return;

		} else {
			//Thất bại
			RequestDispatcher rd = request.getRequestDispatcher("/admin/cat/add.jsp?err=1");
			rd.forward(request, response);
			return;

		}
	}

}
