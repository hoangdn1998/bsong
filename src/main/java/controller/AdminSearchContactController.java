package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Contact;
import model.dao.ContactDAO;
import util.DefineUtil;

public class AdminSearchContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ContactDAO contactDAO;
       
    public AdminSearchContactController() {
        super();
        contactDAO = new ContactDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		String name = request.getParameter("search_contact");
		int numberOfItems = contactDAO.getNumberOfItems(name);
		if(numberOfItems == 0) {
			response.sendRedirect(request.getContextPath()+ "/admin/contact/search.jsp?err=1");
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
		ArrayList<Contact> contacts = contactDAO.getItemsPaginations(offset,name);
		
		request.setAttribute("name", name);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("numberOfItems", numberOfItems);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("contacts", contacts);
		
		RequestDispatcher rd = request.getRequestDispatcher("/admin/contact/search.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
