<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../templates/public/inc/header.jsp" %>
<div class="content_resize">
  <div class="mainbar">
  	<%	
  		String err = request.getParameter("err");
	  	if("1".equals(err)){
			out.print("<p style=\"background: white; color: red; font-weight: bold; padding: 5px; margin-left: 20px; margin-top: 20px; \">Chưa có bài hát nào!</p>");
		}
  		@SuppressWarnings("unchecked")
  		ArrayList<Song> songs =  (ArrayList<Song>) request.getAttribute("songs");
  		if(songs != null && songs.size()>0){
  			int i =0;
  			for(Song item : songs){
  				i++;
  	%>
      <div class="article">
      <h2><a href="<%=request.getContextPath()%>/detail?id=<%=item.getId()%>" title="<%=item.getName()%>"><%=item.getName()%></a></h2>
      <p class="infopost">Ngày đăng: <%=item.getDate_create()%>  Lượt xem: <%=item.getCounter()%> <a href="#" class="com"><span><%=i%></span></a></p>
      <div class="clr"></div>
      <div class="img"><img src="<%=request.getContextPath()%>/upload/<%=item.getPicture()%>" width="177" height="213" alt="<%=item.getName()%>" class="fl" /></div>
      <div class="post_content">
        <p><%=item.getPreview_text()%></p>
        <p class="spec"><a href="<%=request.getContextPath()%>/detail?id=<%=item.getId()%>" class="rm">Chi tiết &raquo;</a></p>
      </div>
      <div class="clr"></div>
    </div>
    <%}} %>
    
    <%	
    	if(songs != null && songs.size()>0){
    	String name = (String) request.getAttribute("name");
   		int numberOfPages = (int) request.getAttribute("numberOfPages");
   		int currentPage = (int) request.getAttribute("currentPage");
   		
    %>
    <p class="pages"><small>Trang <%=currentPage%> của <%=numberOfPages%></small>
    <a href="<%=request.getContextPath()%>/search?page=<%=currentPage + 1%>&editbox_search=<%=name%>">&laquo;</a>
    <% for(int i =1; i <= numberOfPages; i++){ %>
    <% if (currentPage == i){ %>
    <span><%=i%></span>
    <%}else{ %>
    <a href="<%=request.getContextPath()%>/search?page=<%=i%>&editbox_search=<%=name%>"><%=i%></a>
    <%}}%>
    <a href="<%=request.getContextPath()%>/search?page=<%=currentPage + 1%>&editbox_search=<%=name%>">&raquo;</a></p>
    <%} %>
  </div>
  <div class="sidebar">
      <%@ include file="../templates/public/inc/leftbar.jsp" %>
  </div>
  <div class="clr"></div>
</div>
<%@ include file="../templates/public/inc/footer.jsp" %>
