package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.ContactDAO;
import util.AuthUtil;

public class AdminDelContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ContactDAO contactDAO;
    public AdminDelContactController() {
        super();
        contactDAO = new ContactDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() +"/login");
			return;
		}
		
		int id =0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() +"/admin/contacts?err=1");
			return;
		}
		if(contactDAO.delItem(id) >0) {
			//thành công
			response.sendRedirect(request.getContextPath() +"/admin/contacts?msg=1");
			return;
		}else {
			//dữ liệu không tồn tại
			response.sendRedirect(request.getContextPath() + "/admin/contacts?err=2");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
