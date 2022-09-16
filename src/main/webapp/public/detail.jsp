<%@page import="model.dao.CommentDAO"%>
<%@page import="model.bean.Comment"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../templates/public/inc/header.jsp" %>
<div class="content_resize">
  <div class="mainbar">
    <div class="article">
    <%	
    	Song song = (Song) request.getAttribute("song");
    	if(song!= null){
    %>
      <h2 ><%=song.getName()%></h2>
      <div class="clr"></div>
      <p>Ngày đăng:  <%=song.getDate_create()%> Lượt xem: <%=song.getCounter()%></p>
      <div class="vnecontent">
      	<%=song.getDetail_text()%>
      </div>
    </div>
    <div class="article">
    	<form action="javascript:void(0)">
    		<label>Bình luận:</label><br />
    		<textarea id="cmt" class="form-control" rows="3" cols="50" name="comment" placeholder="Bình luận của bạn"></textarea><br />
    		<button class="submit"  id="clickBtn" >Bình luận</button>
    	</form>
    </div>
    <div class="info">
		   	<div id="divCmt">
		   	</div>
	   	</div>
    <%	
    CommentDAO commentDAO = new CommentDAO();
    int limit =3;
	ArrayList<Comment> comments = commentDAO.getItems(song.getId(),limit);
	if(comments != null && comments.size() >0 ){
	%>
		<h5>Bình luận liên quan:</h5>
	<%  for(Comment item:comments){%>	
		<p ><span class="commented" > Người dùng: <%=item.getComment()%></span></p>
	<%}}}%>
	
    <div class="article">
       <%
      	@SuppressWarnings("unchecked")
      	ArrayList<Song> relatedSongs = (ArrayList<Song> )request.getAttribute("relatedSongs");
      	if(relatedSongs != null && relatedSongs.size() > 0){
      %>
      	<h2>Bài viết liên quan</h2>
      <% 
    		for(Song item : relatedSongs){  
      %>
      <div class="clr"></div>
      <div class="comment"> <a href="<%=request.getContextPath()%>/detail?id=<%=item.getId()%>"><img src="<%=request.getContextPath()%>/upload/<%=item.getPicture()%>" width="40" height="40" alt="" class="userpic" /></a>
      		<div class="article">
	        <h2 class="title">
	        	<a class="title-word title-word-2" href="<%=request.getContextPath()%>/<%=StringUtil.makeSlug(item.getCategory().getName())%>/<%=StringUtil.makeSlug(item.getName())%>-<%=item.getId()%>.html"><%=item.getName()%></a>
	        </h2>
	       	<p class="title-word title-word-1"><%=item.getPreview_text()%></p>
        	</div>
      </div>
      <%}} %>
    </div>
  </div>
  <script type="text/javascript">
  $( document ).ready(function() {
	  $("#clickBtn").click(function() {
		  var cmt = $("#cmt").val();
			if(cmt == '') {
				alert('Vui lòng nhập bình luận');
				return;
			}
		  getComment(cmt);
	  });
	});
  
  function getComment(cmt){
		$.ajax({
			url: '<%=request.getContextPath()%>/ajax',
			type: 'GET',
			cache: false,
			data: {acmt:cmt, song_id:<%=song.getId()%>},
			success: function(data){
				$("#divCmt").html(data);
			},
			error: function (){
				alert('Có lỗi trong quá trình xử lý');
			}
		});
		return false;
	}
	  
  </script>
  <div class="sidebar">
  <%@ include file="../templates/public/inc/leftbar.jsp" %>
  </div>
  <div class="clr"></div>
</div>
<%@ include file="../templates/public/inc/footer.jsp" %>
