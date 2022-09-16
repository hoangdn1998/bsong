<%@page import="java.util.ArrayList"%>
<%@page import="model.bean.Comment"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%	
	@SuppressWarnings("unchecked")
	ArrayList<Comment> comments = (ArrayList<Comment>) request.getAttribute("comments");
	
	if(comments != null && comments.size() >0 ){%>
		<h5>Bình luận của bạn:</h5>
<%for(Comment item:comments){%>	
	
	<p><span class="commented" > Bạn: <%=item.getComment()%></span></p>
<%}}%>