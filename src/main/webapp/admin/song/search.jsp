<%@page import="util.DefineUtil"%>
<%@page import="model.bean.Category"%>
<%@page import="model.bean.Song"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../templates/admin/inc/header.jsp" %>
<%@ include file="../../templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Quản lý bài hát</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        <%
    	String err = request.getParameter("err");
    	if("1".equals(err)){
    		out.print("<span style=\" color: red; font-weight: bold; padding: 5px; \">Không tìm thấy bài hát!</span>");
    	}
        %>
        <hr />
        <div class="row">
            <div class="col-md-12">
                <!-- Advanced Tables -->
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="table-responsive">
                            <div class="row">
                                <div class="col-sm-6">
                                    <a href="<%=request.getContextPath()%>/admin/song/search" class="btn btn-success btn-md">Thêm</a>
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <form method="get" action="<%=request.getContextPath()%>/admin/song/search">
                                    	<input type="submit" name="search" value="Tìm kiếm" class="btn btn-warning btn-sm" style="float:right" />
                                        <input type="search" name="search_song" class="form-control input-sm" value ="" placeholder="Nhập tên bài hát" style="float:right; width: 300px;" />
                                        <div style="clear:both"></div>
                                    </form><br />
                                </div>
                            </div>

                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Tên bài hát</th>
                                        <th>Danh mục</th>
                                        <th>Lượt đọc</th>
                                        <th>Hình ảnh</th>
                                        <th width="160px">Chức năng</th>
                                    </tr>
                                </thead>
                                <tbody>
                               		 <%
                                	@SuppressWarnings("unchecked")
                                	ArrayList<Song> songs = (ArrayList<Song>) request.getAttribute("songs");
                                	if(songs != null && songs.size() >0){
                                		for(Song item:songs){
                                	%>
                                    <tr>
                                        <td><%=item.getId()%></td>
                                        <td class="center"><%=item.getName()%></td>
                                        <td class="center"><%=item.getCategory().getName()%></td>
                                        <td class="center"><%=item.getCounter()%></td>
                                        <td class="center">
                                        	<%if(item.getPicture().isEmpty()){ %>
                                        	Chưa có hình ảnh
                                        	<%} else{%>
											<img width="200px" height="200px" src="<%=request.getContextPath()%>/upload/<%=item.getPicture()%>" alt="<%=item.getName()%>"/>
                                       		<%} %>
                                        </td>
                                        <% if("admin".equals(userLogin.getUserName())){%>
                                        <td class="center">
                                            <a  href="<%=request.getContextPath()%>/admin/song/edit?id=<%=item.getId()%>" title="" class="btn btn-primary"><i class="fa fa-edit "></i> Sửa</a>
                                            <a  href="<%=request.getContextPath()%>/admin/song/del?id=<%=item.getId()%>" title="" class="btn btn-danger" onclick="return confirm('Bạn có chắc muốn xóa?')"><i class="fa fa-pencil"></i> Xóa</a>
                                        </td>
                                        <% }else{%>
                                        <td class="center">
											<a  href="javascript:void(0)" title="" class="btn btn-primary" onclick="return alert('Bạn không có quyền sửa')"><i class="fa fa-edit "></i> Sửa</a>
                                            <a  href="jsavascript:void(0)" title="" class="btn btn-danger" onclick="return alert('Bạn không có quyền xóa!')"><i class="fa fa-pencil"></i> Xóa</a>                                        
                                        </td>
                                         <% }%>
                                    </tr>
                                    <%}}else{ %>
                                    <tr><td colspan="6" align ="center">Chưa có bài hát</td></tr>
                                    <%} %>
                                </tbody>
                            </table>
                            <%
                            if(songs != null && songs.size() > 0){
                            String name = (String) request.getAttribute("name");
                            int numberOfPages = (int) request.getAttribute("numberOfPages");
                            int numberOfItems = (int) request.getAttribute("numberOfItems");
                            int currentPage = (int) request.getAttribute("currentPage");
                            %>
                            <div class="row">
                                <div class="col-sm-6">
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <div class="dataTables_paginate paging_simple_numbers" id="dataTables-example_paginate">
                                        <ul class="pagination">
                                            <li class="paginate_button previous disabled" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_previous"><a href="<%=request.getContextPath()%>/admin/song/search?page=<%=currentPage - 1%>&search_song=<%=name%>">Trang trước</a></li>
                                            <% 
                                            String active = "";
                                            for(int i=1; i <= numberOfPages; i++){
                                            	if(currentPage == i){
                                            		active = "active";
                                            	}else{
                                            		active = "";
                                            	}
                                            %>
                                            <li class="paginate_button <%=active%>" aria-controls="dataTables-example" tabindex="0"><a href="<%=request.getContextPath()%>/admin/song/search?page=<%=i%>&search_song=<%=name%>"><%=i%></a></li>
											<%} %>
                                            <li class="paginate_button next" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_next"><a href="<%=request.getContextPath()%>/admin/song/search?page=<%=currentPage + 1%>&search_song=<%=name%>">Trang tiếp</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <%} %>
                        </div>

                    </div>
                </div>
                <!--End Advanced Tables -->
            </div>
        </div>
    </div>
</div>
<script>
    document.getElementById("song").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="../../templates/admin/inc/footer.jsp" %>