<%@page import="util.DefineUtil"%>
<%@page import="model.bean.Category"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../templates/admin/inc/header.jsp" %>
<%@ include file="../../templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Quản lý danh mục</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        <%
        	String msg = request.getParameter("msg");
	        String err = request.getParameter("err");
	    	if("1".equals(msg)){
	    		out.print("<span style=\"background: yellow; color: green; font-weight: bold; padding: 5px; \">Thêm danh mục thành công!</span>");
	    	}
	    	if("2".equals(msg)){
	    		out.print("<span style=\"background: yellow; color: green; font-weight: bold; padding: 5px; \">Sửa danh mục thành công!</span>");
	    	}
	    	if("3".equals(msg)){
	    		out.print("<span style=\"background: yellow; color: green; font-weight: bold; padding: 5px; \">Xóa danh mục thành công!</span>");
	    	}
        	if("1".equals(err)){
        		out.print("<span style=\"background: yellow; color: red; font-weight: bold; padding: 5px; \">Id không tồn tại!</span>");
        	}
        	if("2".equals(err)){
        		out.print("<span style=\"background: yellow; color: red; font-weight: bold; padding: 5px; \">Có lỗi xảy ra!</span>");
        	}
        	if("3".equals(err)){
        		out.print("<span style=\"background: yellow; color: red; font-weight: bold; padding: 5px; \">Không có quyền thêm!</span>");
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
                                    <a href="<%=request.getContextPath()%>/admin/cat/add" class="btn btn-success btn-md">Thêm</a>
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <form method="get" action="<%=request.getContextPath()%>/admin/cat/search">
                                        <input type="submit" name="search" value="Tìm kiếm" class="btn btn-warning btn-sm" style="float:right" />
                                        <input type="search" name="search_cat" class="form-control input-sm" value="" placeholder="Nhập tên danh mục" style="float:right; width: 300px;" />
                                        <div style="clear:both"></div>
                                    </form><br />
                                </div>
                            </div>

                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Tên danh mục</th>
                                        <th width="160px">Chức năng</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<%
                                	@SuppressWarnings("unchecked")
                                	ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
                                	if(categories != null && categories.size() >0){
                                		for(Category item:categories){
                                	%>
                                    <tr>
                                        <td><%=item.getId()%></td>
                                        <td class="center"><%=item.getName()%></td>
                                         <% if("admin".equals(userLogin.getUserName())){%>
                                        <td class="center">
                                            <a href="<%=request.getContextPath()%>/admin/cat/edit?id=<%=item.getId()%>" title="" class="btn btn-primary"><i class="fa fa-edit "></i> Sửa</a>
                                            <a href="<%=request.getContextPath()%>/admin/cat/del?id=<%=item.getId()%>" title="" class="btn btn-danger" onclick="return confirm('Bạn có chắc muốn xóa?')"><i class="fa fa-pencil"></i> Xóa</a>
                                        </td>
                                        <% }else{%>
                                        <td class="center">
                                            <a href="javascript:void(0)" title="" class="btn btn-primary" onclick="return alert('Bạn không có quyền sửa')"><i class="fa fa-edit "></i> Sửa</a>
                                            <a href="javascript:void(0)" title="" class="btn btn-danger" onclick="return alert('Bạn không có quyền xóa')"><i class="fa fa-pencil"></i> Xóa</a>
                                        </td>
                                        <%} %>
                                    </tr>
                                    <%}}else{ %>
                                    <tr><td colspan="3" align ="center">Chưa có danh mục</td></tr>
                                    <%} %>
                                </tbody>
                            </table>
                             <%
                            int numberOfPages = (int) request.getAttribute("numberOfPages");
                            int numberOfItems = (int) request.getAttribute("numberOfItems");
                            int currentPage = (int) request.getAttribute("currentPage");
                            if(categories != null && categories.size() >0){
                            int from =0;
                            int to =0;
                            for(int j =1; j <= currentPage; j++){
                            	if(j == 1 && categories.size() == 1 && currentPage ==1){
                            		from +=1;
                            		to +=1;
                            	}else if(j == 1){
	                            	from += numberOfItems ;
	                            	to = from - DefineUtil.NUMBER_PER_PAGE + 1;
	                            }else if(categories.size() < DefineUtil.NUMBER_PER_PAGE ){
                            		from -= DefineUtil.NUMBER_PER_PAGE;
                            		to = from - categories.size() + 1;
                            	}else{
                            		from -= DefineUtil.NUMBER_PER_PAGE;
                            		to = from - DefineUtil.NUMBER_PER_PAGE + 1;
                            	}
                            }
                            %>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="dataTables_info" id="dataTables-example_info" style="margin-top:27px">Hiển thị từ <%=from%> đến <%=to%> của <%=numberOfItems%> Danh mục</div>
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <div class="dataTables_paginate paging_simple_numbers" id="dataTables-example_paginate">
                                        <ul class="pagination">
                                            <li class="paginate_button previous disabled" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_previous"><a href="<%=request.getContextPath()%>/admin/cats?page=<%=currentPage-1%>">Trang trước</a></li>
                                             <% 
                                            String active = "";
                                            for(int i=1; i <= numberOfPages; i++){
                                            	if(currentPage == i){
                                            		active = "active";
                                            	}else{
                                            		active = "";
                                            	}
                                            %>
                                            <li class="paginate_button <%=active%>" aria-controls="dataTables-example" tabindex="0"><a href="<%=request.getContextPath()%>/admin/cats?page=<%=i%>"><%=i%></a></li>
											<%} %>
                                            <li class="paginate_button next" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_next"><a href="<%=request.getContextPath()%>/admin/cats?page=<%=currentPage+1%>">Trang tiếp</a></li>
                                        </ul>
                                    </div>
                              <%} %>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <!--End Advanced Tables -->
            </div>
        </div>
    </div>
</div>
<script>
    document.getElementById("category").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="../../templates/admin/inc/footer.jsp" %>