package controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.bean.Category;
import model.bean.Song;
import model.bean.User;
import model.dao.CategoryDAO;
import model.dao.SongDAO;
import util.AuthUtil;
import util.FileUtil;

@MultipartConfig
public class AdminAddSongController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryDAO categoryDAO;
	private SongDAO songDAO;
	
    public AdminAddSongController() {
        super();
        categoryDAO = new CategoryDAO();
        songDAO = new SongDAO();
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
		
		//Chỉ admin mới được thêm người dùng
		if(!"admin".equals(userLogin.getUserName())) {
			//Không được phép
			response.sendRedirect(request.getContextPath() + "/admin/songs?err=3");
			return;
		}
		
		ArrayList<Category> categories = categoryDAO.getItems();
		request.setAttribute("categories", categories);
		
		RequestDispatcher rd = request.getRequestDispatcher("/admin/song/add.jsp");
		rd.forward(request, response);
		return;
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
		//Chỉ admin mới được thêm người dùng
		if(!"admin".equals(userLogin.getUserName())) {
			//Không được phép
			response.sendRedirect(request.getContextPath() + "/admin/songs?err=3");
			return;
		}
		
		int cat_id =0;
		try {
			cat_id = Integer.parseInt(request.getParameter("category"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/songs?err=1");
		}
		
		
		
		//get data
		String name = request.getParameter("name");
		String preview = request.getParameter("preview");
		String detail = request.getParameter("detail");
		//có thể không lấy date_create
		Timestamp date_create = new Timestamp(new Date().getTime());

		
		Part filePart = request.getPart("picture");
		//tạo thư mục lưu ảnh
			final String dirPathName = request.getServletContext().getRealPath("/upload");
			File dirFile = new File(dirPathName);
			if(!dirFile.exists()) {
				dirFile.mkdirs();	
			}
		
		//Lấy tên file từ part
			String fileName = FileUtil.getName(filePart);
		//Đổi tên file
			String picture = FileUtil.rename(fileName);
		//đường dẫn file 
			String filePathName = dirPathName + File.separator + picture;
		
		Category category = new Category(cat_id, null);
		Song item = new Song(0, name, preview, detail, date_create, picture, 0, cat_id, category);
		if(songDAO.addItem(item) >0) {
			//Thành công
			//Ghi file
			if(!fileName.isEmpty()) {
				filePart.write(filePathName);
			}
			response.sendRedirect(request.getContextPath() + "/admin/songs?msg=1");
			return;
		} else {
			//Thất bại
			ArrayList<Category> categories = categoryDAO.getItems();
			request.setAttribute("categories", categories);
			
			RequestDispatcher rd = request.getRequestDispatcher("/admin/song/add.jsp?err=1");
			rd.forward(request, response);
			return;

		}
	}

}
