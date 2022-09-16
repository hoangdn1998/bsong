<%@page import="model.dao.SongDAO"%>
<%@page import="model.dao.CategoryDAO"%>
<%@page import="model.dao.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../templates/admin/inc/header.jsp" %>
<%@ include file="../templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>TRANG QUẢN TRỊ VIÊN</h2>
            </div>
        </div>
        <!-- /. ROW  -->
         <%
        String err = request.getParameter("err");
    	if("1".equals(err)){
    		out.print("<span style=\"background: yellow; color: red; font-weight: bold; padding: 5px; \">Chỉ admin mới có quyền quản lý liên hệ!</span>");
    	}
    	
        %>
        <hr />
        <%
        UserDAO userDAO = new UserDAO();
       	CategoryDAO categoryDAO = new CategoryDAO();
       	SongDAO songDAO = new SongDAO();
       	int numberOfCats = categoryDAO.getNumberOfItems();
       	int numberOfSongs = songDAO.getNumberOfItems();
       	int numberOfUsers = userDAO.getNumberOfItems();
        %>
        <div class="row">
            <div class="col-md-4 col-sm-4 col-xs-4">
                <div class="panel panel-back noti-box">
                    <span class="icon-box bg-color-green set-icon">
                    <i class="fa fa-bars"></i>
                </span>
                    <div class="text-box">
                        <p class="main-text"><a href="<%=request.getContextPath()%>/admin/cats" title="">Quản lý danh mục</a></p>
                        <p class="text-muted">Có <%=numberOfCats%> danh mục</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4 col-sm-4 col-xs-4">
                <div class="panel panel-back noti-box">
                    <span class="icon-box bg-color-blue set-icon">
                    <i class="fa fa-bell-o"></i>
                </span>
                    <div class="text-box">
                        <p class="main-text"><a href="<%=request.getContextPath()%>/admin/songs" title="">Quản lý bài hát</a></p>
                        <p class="text-muted">Có <%=numberOfSongs%> bài hát</p>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4 col-sm-4 col-xs-4">
                <div class="panel panel-back noti-box">
                    <span class="icon-box bg-color-brown set-icon">
                    <i class="fa fa-rocket"></i>
                </span>
                    <div class="text-box">
                        <p class="main-text"><a href="<%=request.getContextPath()%>/admin/users" title="">Quản lý người dùng</a></p>
                        <p class="text-muted">Có <%=numberOfUsers%> người dùng</p>
                    </div>
                </div>
            </div>
          
        </div>
    </div>
</div>
<script>
    document.getElementById("index").classList.add('active-menu');
</script>
<!-- /. PAGE WRAPPER  -->
<%@ include file="../templates/admin/inc/footer.jsp" %>