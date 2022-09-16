package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Category;
import model.bean.Song;
import model.dao.CategoryDAO;
import model.dao.SongDAO;
import util.DefineUtil;

public class PublicCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryDAO categoryDAO;
	private SongDAO songDAO;
       
    public PublicCatController() {
        super();
        categoryDAO = new CategoryDAO();
        songDAO = new SongDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = 0;
		
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/404");
			return;
		}
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			currentPage = 1;
		}
		
		Category category = categoryDAO.getItem(id);
		
		if(category == null) {
			response.sendRedirect(request.getContextPath() + "/404");
			return;
		}
		
		int numberOfItems = songDAO.getNumberOfItems(id);
		int numberOfPages = (int) Math.ceil((float)numberOfItems/DefineUtil.NUMBER_PER_PAGE); 
		if(currentPage <1) {
			currentPage = 1;
		}
		if(currentPage > numberOfPages ) {
			currentPage = currentPage -1;
		}
		int offset = (currentPage-1) * DefineUtil.NUMBER_PER_PAGE;
		ArrayList<Song> songs = songDAO.getItemsByCategoryPagination(offset, id);
		
		
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("category", category);
		request.setAttribute("songs", songs);
		RequestDispatcher rd = request.getRequestDispatcher("/public/cat.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
