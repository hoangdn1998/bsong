<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../templates/admin/inc/header.jsp" %>
<%@ include file="../../templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Đăng nhập</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        <%
        	String name = request.getParameter("name");
        	String err = request.getParameter("err");
        	if("1".equals(err)){
        		out.print("<span style=\"background: yellow; color: red; font-weight: bold; padding: 5px; \">Đăng nhập không thành công!</span>");
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
                                <form action="" role="form" method="post" id="form">
                                    <div class="form-group">
                                        <label for="username">Tên đăng nhập</label>
                                        <input type="text" id="name" name="username" class="form-control" placeholder="Nhập tài khoản" />
                                    </div>
                                     <div class="form-group" >
                                        <label for="password">Mật khẩu</label>
                                        <input  id="pass" type="password" name="password" class="form-control" placeholder="Nhập mật khẩu" />
                                        <i class="fa fa-eye icon" onclick="myfunction()"></i>
                                    </div>
                                    <button type="submit" name="submit" class="btn btn-success btn-md">Đăng nhập</button>
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
    var a = true;
    function myfunction() {
    	if(a){
			document.getElementById('pass').type = "text";
			a = false;
    	}else{
    		document.getElementById('pass').type = "password";
    		a = true;
    	}
	}
</script>
<!-- /. PAGE WRAPPER  -->
<%@ include file="../../templates/admin/inc/footer.jsp" %>