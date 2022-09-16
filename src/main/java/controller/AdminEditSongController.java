package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.bean.Category;
import model.bean.Song;
import model.dao.CategoryDAO;
import model.dao.SongDAO;
import util.AuthUtil;
import util.FileUtil;

@MultipartConfig
public class AdminEditSongController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SongDAO songDAO;
	private CategoryDAO categoryDAO;
       
    public AdminEditSongController() {
        super();
        songDAO = new SongDAO();
        categoryDAO = new CategoryDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		//check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() +"/login");
			return;
		}
		
		//get data
		int id =0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/songs?err=2");
			return;
		}
		
		ArrayList<Category> categories = categoryDAO.getItems();
		Song song = songDAO.getItem(id);
		
		if(song == null) {
			response.sendRedirect(request.getContextPath() + "/admin/songs?err=2");
			return;
		}
		
		request.setAttribute("categories", categories);
		request.setAttribute("song", song);
		
		RequestDispatcher rd = request.getRequestDispatcher("/admin/song/edit.jsp");
		rd.forward(request, response);
		return;
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
		
		int cat_id = 0;
		int id = 0;
		try {
			cat_id = Integer.parseInt(request.getParameter("category"));
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/songs?err=1");
		}
		
		//get data
		String name = request.getParameter("name");
		String preview = request.getParameter("preview");
		String detail = request.getParameter("detail");
		String picture ="";

		
		Part filePart = request.getPart("picture");
		//tạo thư mục lưu ảnh
		final String dirPathName = request.getServletContext().getRealPath("/upload");
		File dirFile = new File(dirPathName);
		if(!dirFile.exists()) {
			dirFile.mkdirs();	
		}
		//get dữ liệu cũ
		Song song = songDAO.getItem(id);
		if(song == null) {
			response.sendRedirect(request.getContextPath() + "/admin/songs?err=2");
			return;
		}
					
		
		//Lấy tên file từ part
			String fileName = FileUtil.getName(filePart);
		//Đổi tên file
			if(fileName.isEmpty()) {
				picture = song.getPicture();
			}else {
				picture = FileUtil.rename(fileName);
			}
		//đường dẫn file 
			String filePathName = dirPathName + File.separator + picture;
		
		//get dữ liệu cũ
		
		Category category = new Category(cat_id, null);
		Song item = new Song(id, name, preview, detail, null, picture, 0, cat_id, category);
		if(songDAO.editItem(item) >0) {
			//Thành công
			
			if(!fileName.isEmpty()) {
				String oldPathName =  dirPathName + File.separator + song.getPicture();
					//xóa file cũ
					File oldFile = new File(oldPathName);
					if(oldFile.exists()) {
						oldFile.delete();
					}
					//Ghi file
					filePart.write(filePathName);
			}
			response.sendRedirect(request.getContextPath() + "/admin/songs?msg=2");
			return;
			
		} else {
			//Thất bại
			ArrayList<Category> categories = categoryDAO.getItems();
			request.setAttribute("categories", categories);
			
			RequestDispatcher rd = request.getRequestDispatcher("/admin/song/edit.jsp?err=1");
			rd.forward(request, response);
			return;

		}
	}

}
