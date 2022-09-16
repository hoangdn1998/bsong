package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Song;
import model.dao.SongDAO;

public class PublicDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SongDAO songDAO;
	
	public PublicDetailController() {
		super();
		songDAO = new SongDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/404");
			return;
		}
		
		HttpSession session = request.getSession();
		
		Song song = songDAO.getItem(id);
		if (song == null) {
			response.sendRedirect(request.getContextPath() + "/404");
			return;
		}
		// tăng view
		String hasVisited = (String) session.getAttribute("Hasvisted" + id);
		if (hasVisited == null) {
			session.setAttribute("Hasvisted" + id, "yes");
			session.setMaxInactiveInterval(1200);
			// increase page view
			songDAO.increaseView(id);
		}
		song = songDAO.getItem(id);
		if (song == null) {
			response.sendRedirect(request.getContextPath() + "/404");
			return;
		}
		ArrayList<Song> relatedSongs = songDAO.getRelatedItems(song, 3);
		request.setAttribute("song", song);
		request.setAttribute("relatedSongs", relatedSongs);
		RequestDispatcher rd = request.getRequestDispatcher("/public/detail.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	}

}
