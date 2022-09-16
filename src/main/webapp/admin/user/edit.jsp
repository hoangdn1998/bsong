<%@page import="model.bean.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../templates/admin/inc/header.jsp" %>
<%@ include file="../../templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Thêm Người Dùng</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        <%	
        	
        	String username = request.getParameter("username");
       	    String fullname = request.getParameter("fullname");
       	 	User user = (User) request.getAttribute("user");
       	 	if(user != null){
       	 		username = user.getUserName();
       	 		fullname = user.getFullName();
       	 	}
        	String err = request.getParameter("err");
        	if("1".equals(err)){
        		out.print("<span style=\"background: yellow; color: red; font-weight: bold; padding: 5px; \">Có lỗi xảy ra!</span>");
        	}
        	if("2".equals(err)){
        		out.print("<span style=\"background: yellow; color: red; font-weight: bold; padding: 5px; \">Tên đăng nhập đã tồn tại!</span>");
        	}
        %>
        <hr />
        <div class="row">
            <div class="col-md-12">
                <!-- Form Elements -->
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12">
                                <form action="" role="form" method="post" id="form" class="form">
                                    <div class="form-group" id="form-group">
                                        <label for="name">Tên Đăng Nhập</label>
                                        <input type="text" id="username" value="<%if(username != null) {out.print(username);}%>" name="username" class="form-control" disabled="disabled"/>
                                        <label for="name">Mật khẩu</label>
                                        <input type="text" id="password" value="" name="password" class="form-control" />
                                        <label for="name">Họ Tên</label>
                                        <input type="text" id="fullname" value="<%if(fullname != null) {out.print(fullname);}%>" name="fullname" class="form-control" />
                                    </div>
                                    <button type="submit" name="submit" class="btn btn-success btn-md">Sửa</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End Form Elements -->
            </div>
        </div>
        <!-- /. ROW  -->
    </div>
    <!-- /. PAGE INNER  -->
</div>
<script type="text/javascript">
$(document).ready(function() {
	$('#form').validate({
		rules : {
			username : {
				required:true,
			},
			password : {
				required:true,
			},
			fullname : {
				required:true,
			},
		},
		messages:{
			username : {
				required:'Vui lòng nhập username!',
			},
			password : {
				required:'Vui lòng nhập password!',
			},
			fullname : {
				required:'Vui lòng nhập Họ tên!',
			},
		},
	});
});
</script>
<script>
    document.getElementById("user").classList.add('active-menu');
</script>
<!-- /. PAGE WRAPPER  -->
<%@ include file="../../templates/admin/inc/footer.jsp" %>