package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.User;
import model.dao.UserDAO;
import util.AuthUtil;
import util.StringUtil;

public class AdminEditUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private UserDAO userDAO;   
       
    public AdminEditUserController() {
        super();
        userDAO = new UserDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		//check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() +"/login");
			return;
		}
		
		int id =0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() +"/admin/users?err=2");
			return;
		}
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		//Chỉ admin mới được sửa người dùng
				if("admin".equals(userDAO.getItem(userLogin.getId()).getUserName()) || (id == userLogin.getId())) {
					User user = userDAO.getItem(id);
					if(user != null) {
						//dữ liệu đúng
						request.setAttribute("user", user);
						RequestDispatcher rd = request.getRequestDispatcher("/admin/user/edit.jsp");
						rd.forward(request, response);
					}else {
						//dữ liệu không tồn tại
						response.sendRedirect(request.getContextPath() + "/admin/users?err=1");
						return;
					}
				}else {
					//Không được phép
					response.sendRedirect(request.getContextPath() + "/admin/users?err=5");
					return;
					
				}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		//check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() +"/admin/index");
			return;
		}
		
		//get category
		int id =0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() +"/admin/users?err=1");
			return;
		}
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		//Chỉ admin mới được sửa tất cả người dùng
		if("admin".equals(userDAO.getItem(userLogin.getId()).getUserName()) || (id == userLogin.getId())) {
			//lấy lại dl cũ
			User item = userDAO.getItem(id);
			if(item == null) {
				response.sendRedirect(request.getContextPath() +"/admin/users?err=1");
				return;
			}
			//get data
			String password = request.getParameter("password");
			if(password.isEmpty()) {
			password = item.getPassWord();
			}else {
			password = StringUtil.md5(password);	
			}
			
			String fullname = request.getParameter("fullname");
			
			//Tạo đối tượng mới
			item.setPassWord(password);
			item.setFullName(fullname);
			if(userDAO.editItem(item) >0) {
				//Thành công
				response.sendRedirect(request.getContextPath() + "/admin/users?msg=2");
				return;
			}else {
				//Thất bại
				RequestDispatcher rd = request.getRequestDispatcher("/admin/user/edit.jsp?err=1");
				rd.forward(request, response);
				return;

			}
		}else {
			//Không được phép
			response.sendRedirect(request.getContextPath() + "/admin/users?err=5");
			return;
		}
		
	}

}
