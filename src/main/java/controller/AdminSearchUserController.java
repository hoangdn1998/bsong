package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.User;
import model.dao.UserDAO;
import util.DefineUtil;

public class AdminSearchUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
       
    public AdminSearchUserController() {
        super();
        userDAO = new UserDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		String name = request.getParameter("search_user");
		int numberOfItems = userDAO.getNumberOfItems(name);
		if(numberOfItems == 0) {
			response.sendRedirect(request.getContextPath()+ "/admin/user/search.jsp?err=1");
			return;
		}
		int numberOfPages = (int) Math.ceil((float)numberOfItems/DefineUtil.NUMBER_PER_PAGE) ; 
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			currentPage = 1;
		}
		if(currentPage < 1) {
			currentPage = 1;
		}
		if(currentPage > numberOfPages) {
			currentPage = currentPage -1;
		}
		int offset = (currentPage-1) * DefineUtil.NUMBER_PER_PAGE;
		ArrayList<User> users = userDAO.getItemsPagination(offset, name);
		
		request.setAttribute("name", name);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("numberOfItems", numberOfItems);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("users", users);
		
		RequestDispatcher rd = request.getRequestDispatcher("/admin/user/search.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
