<%@page import="model.bean.Song"%>
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
                <h2>Sửa bài hát</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        <%
        @SuppressWarnings("unchecked")
        ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories"); 
        String name = request.getParameter("name");
        String cat_id = request.getParameter("category");
		String preview = request.getParameter("preview");
		String detail = request.getParameter("detail");
		String picture = null;
		Song song = (Song)request.getAttribute("song");
		if(song != null){
			name = song.getName();
	        cat_id = String.valueOf(song.getCat_id());
			preview = song.getPreview_text();
			detail = song.getDetail_text();
			picture = song.getPicture();
		}
        String err = request.getParameter("err");
        String msg = request.getParameter("msg");
    	if("1".equals(err)){
    		out.print("<span style=\"background: yellow; color: red; font-weight: bold; padding: 5px; \">Có lỗi khi sửa!</span>");
    	}
    	if("2".equals(err)){
    		out.print("<span style=\"background: yellow; color: red; font-weight: bold; padding: 5px; \">Có lỗi khi xóa ảnh!</span>");
    	}
    	if("1".equals(msg)){
    		out.print("<span style=\"background: yellow; color: red; font-weight: bold; padding: 5px; \">Xóa ảnh thành công!</span>");
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
                                <form action="<%=request.getContextPath()%>/admin/song/edit?id=<%=song.getId()%>" role="form" method="post" enctype="multipart/form-data" id="form">
                                    <div class="form-group">
                                        <label for="name">Tên bài hát</label>
                                        <input type="text" id="name" value="<%if(name != null) out.print(name);%>" name="name" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label for="category">Danh mục bài hát</label>
                                        <select id="category" name="category" class="form-control">
                                          <option value="">--Chọn Danh mục--</option>
                                        <%
                                      
                                       		if(categories != null){
                                       		for(Category item:categories){
                                        %>
	                                        <option <%if(cat_id != null && cat_id.equals(String.valueOf(item.getId()))) out.print(" selected");%> value="<%=item.getId()%>"><%=item.getName()%></option>
	                                    <%}} %>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="picture">Hình ảnh</label>
                                        <input type="file" name="picture" /><br /><br />
                                        <%if(picture != null && !"".equals(picture)){ %>
                                      	<br />
                                      	<label for="picture">Ảnh cũ</label><br />
                                       	<img width="200px" height="200px" src="<%=request.getContextPath()%>/upload/<%=picture%>" alt="<%=picture%>" /><br /><br />
                                       	<a  href="<%=request.getContextPath()%>/admin/pic/del?id=<%=song.getId()%>" title="" class="btn btn-danger" onclick="return confirm('Bạn có chắc muốn xóa?')"><i class="fa fa-pencil"></i> Xóa ảnh</a>
                                       <%}else if ("".equals(picture)){%>
                                       <label for="picture">Ảnh cũ không tồn tại!</label><br />
                                       <%}%>
                                       
                                    </div>
                                    <div class="form-group">
                                        <label for="preview">Mô tả</label>
                                        <textarea id="preview" class="form-control" rows="3" name="preview" <%if(preview != null) out.print(preview);%>><%if(preview != null) out.print(preview);%></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="detail">Chi tiết</label>
                                        <textarea id="detail" class="form-control" rows="5" name="detail"><%if(detail != null) out.print(detail);%></textarea>
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
<script>
 var editor = CKEDITOR.replace('detail');
 CKFinder.setupCKEditor(editor, '<%=request.getContextPath()%>/library/ckfinder/');
</script>
<script type="text/javascript">
$(document).ready(function() {
	$('#form').validate({
		ignore: [],
      	debug: false,
		rules : {
			name : {
				required:true,
				minlength: 2,
				maxlength: 100,
			},
			category : {
				required:true,
			},
			preview : {
				required:true,
				minlength: 10,
				maxlength: 10000,
			},
			detail: {
               required:true,
               minlength:20,
           },
			
		},
		messages:{
			name : {
				required:'Vui lòng nhập tên bài hát!',
				minlength:'Nhập tối thiểu 2 kí tự',
				maxlength:'Nhập tối đa 100 kí tự',
			},
			category : {
				required:'Vui lòng chọn danh mục!',
			},
			preview : {
				required:'Vui lòng nhập mô tả bài hát!',
				minlength:'Nhập tối thiểu 10 kí tự',
				maxlength:'Nhập tối đa 10000 kí tự',
			},
			detail: {
                required:"Vui lòng nhập chi tiết bài hát",
                minlength:"Nhập tối thiểu 20 kí tự",
         	},
		},
	});
});
</script>
<script>
    document.getElementById("song").classList.add('active-menu');
</script>
<!-- /. PAGE WRAPPER  -->
<%@ include file="../../templates/admin/inc/footer.jsp" %>