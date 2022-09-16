package controller;

import java.io.File;
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

public class AdminDelPicController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SongDAO songDAO;
	private CategoryDAO categoryDAO;
	
       
    public AdminDelPicController() {
        super();
        songDAO = new SongDAO();
        categoryDAO = new CategoryDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id =0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			ArrayList<Category> categories = categoryDAO.getItems();
			request.setAttribute("categories", categories);
			RequestDispatcher rd = request.getRequestDispatcher("/admin/song/edit?err=2");
			rd.forward(request, response);
			return;
		}
		Song song = songDAO.getItem(id);
		if(song == null) {
			response.sendRedirect(request.getContextPath() + "/admin/song/edit?err=2");
			return;
		}
		final String dirPathName = request.getServletContext().getRealPath("/upload");
		File dirFile = new File(dirPathName);
		if(!dirFile.exists()) {
			dirFile.mkdirs();	
		}
		String fileName = song.getPicture();
		
		if(songDAO.delPicture(song) >0 ) {
			ArrayList<Category> categories = categoryDAO.getItems();
			request.setAttribute("categories", categories);
			if(!fileName.isEmpty()) {
				//xóa file cũ
				String oldPathName =  dirPathName + File.separator + fileName;
				File oldFile = new File(oldPathName);
				if(oldFile.exists()) {
					oldFile.delete();
				}
			}
			RequestDispatcher rd = request.getRequestDispatcher("/admin/song/edit?msg=1");
			rd.forward(request, response);
			return;
		}else {
			ArrayList<Category> categories = categoryDAO.getItems();
			request.setAttribute("categories", categories);
			
			RequestDispatcher rd = request.getRequestDispatcher("/admin/song/edit?err=2");
			rd.forward(request, response);
			return;
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
