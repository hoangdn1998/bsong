package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Contact;
import model.bean.User;
import model.dao.ContactDAO;
import util.AuthUtil;
import util.DefineUtil;

public class AdminIndexContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ContactDAO contactDAO;
       
    public AdminIndexContactController() {
        super();
        contactDAO = new ContactDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		//check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() +"/login");
			return;
		}
		
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		
		//Chỉ admin mới được xóa liên hệ
		if(!"admin".equals(userLogin.getUserName())) {
			//Không được phép
			response.sendRedirect(request.getContextPath() + "/admin/index?err=1");
			return;
		}
		//get number of users
		int numberOfItems = contactDAO.getNumberOfItems();
		int numberOfPages = (int) Math.ceil((float)numberOfItems/DefineUtil.NUMBER_PER_PAGE) ; 
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			currentPage = 1;
		}
		
		if(currentPage <1) {
			currentPage = 1;
		}
		if(currentPage > numberOfPages ) {
			currentPage = currentPage -1;
		}
		
		int offset = (currentPage-1) * DefineUtil.NUMBER_PER_PAGE;
		
		ArrayList<Contact> contacts = contactDAO.getItemsPaginations(offset);
		request.setAttribute("contacts", contacts);
		request.setAttribute("numberOfItems", numberOfItems);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/contact/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
