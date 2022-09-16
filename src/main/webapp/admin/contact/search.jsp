<%@page import="util.DefineUtil"%>
<%@page import="model.bean.Contact"%>
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
                <h2>Quản lý liên hệ</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        <%
        String err = request.getParameter("err");
    	if("1".equals(err)){
    		out.print("<span style=\" color: red; font-weight: bold; padding: 5px; \">Liên hệ không tồn tại!</span>");
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
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <form method="get" action="<%=request.getContextPath()%>/admin/contact/search">
                                         <input type="submit" name="search" value="Tìm kiếm" class="btn btn-warning btn-sm" style="float:right" />
                                        <input type="search" name="search_contact" class="form-control input-sm" value="" placeholder="Nhập tên liên hệ" style="float:right; width: 300px;" />
                                        <div style="clear:both"></div>
                                    </form><br />
                                </div>
                            </div>

                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>name</th>
                                        <th>email</th>
                                        <th>website</th>
                                        <th>contact</th>
                                        <th width="160px">Chức năng</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<%
                                	@SuppressWarnings("unchecked")
                                	ArrayList<Contact> contacts = (ArrayList<Contact>) request.getAttribute("contacts");
                                	if(contacts != null && contacts.size() >0){
                                		for(Contact item:contacts){
                                	%>
                                    <tr>
                                        <td><%=item.getId()%></td>
                                        <td class="center"><%=item.getName()%></td>
                                        <td class="center"><%=item.getEmail()%></td>
                                        <td class="center"><%=item.getWebsite()%></td>
                                         <td class="center"><%=item.getMessage()%></td>
                                        <td class="center">
                                            <a href="<%=request.getContextPath()%>/admin/contact/del?id=<%=item.getId()%>" title="" class="btn btn-danger" onclick="return confirm('Bạn có chắc muốn xóa?')"><i class="fa fa-pencil"></i> Xóa</a>
                                        </td>
                                    </tr>
                                    <%}}else{ %>
                                    <tr><td colspan="3" align ="center">Chưa có liên hệ</td></tr>
                                    <%} %>
                                </tbody>
                            </table>
                            <%
                            if(contacts != null && contacts.size() >0){
                            int numberOfPages = (int) request.getAttribute("numberOfPages");
                            int numberOfItems = (int) request.getAttribute("numberOfItems");
                            int currentPage = (int) request.getAttribute("currentPage");
                            String name = (String) request.getAttribute("name");
                            %>
                            <div class="row">
                                <div class="col-sm-6">
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <div class="dataTables_paginate paging_simple_numbers" id="dataTables-example_paginate">
                                        <ul class="pagination">
                                            <li class="paginate_button previous disabled" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_previous"><a href="<%=request.getContextPath()%>/admin/contact/search?page=<%=currentPage - 1 %>&search_contact=<%=name%>">Trang trước</a></li>
                                             <% 
                                            String active = "";
                                            for(int i=1; i <= numberOfPages; i++){
                                            	if(currentPage == i){
                                            		active = "active";
                                            	}else{
                                            		active = "";
                                            	}
                                            %>
                                            <li class="paginate_button <%=active%>" aria-controls="dataTables-example" tabindex="0"><a href="<%=request.getContextPath()%>/admin/contact/search?page=<%=i%>&search_contact=<%=name%>"><%=i%></a></li>
                                             <%} %>
                                            <li class="paginate_button next" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_next"><a href="<%=request.getContextPath()%>/admin/contact/search?page=<%=currentPage +1%>&search_contact=<%=name%>">Trang tiếp</a></li>
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
    document.getElementById("contact").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="../../templates/admin/inc/footer.jsp" %>