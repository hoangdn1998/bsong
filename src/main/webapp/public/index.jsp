<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../templates/public/inc/header.jsp" %>
<div class="content_resize">
  <div class="mainbar">
  	<%
  		@SuppressWarnings("unchecked")
  		ArrayList<Song> songs =  (ArrayList<Song>) request.getAttribute("songs");
  		if(songs != null && songs.size()>0){
  			int i =0;
  			for(Song item : songs){
  				i++;
  	%>
      <div class="article">
      <h2 class="title"><a class="title-word title-word-1" href="<%=request.getContextPath()%>/<%=StringUtil.makeSlug(item.getCategory().getName())%>/<%=StringUtil.makeSlug(item.getName())%>-<%=item.getId()%>.html" title="<%=item.getName()%>"><%=item.getName()%></a></h2>
      <p class="infopost">Ngày đăng: <%=item.getDate_create()%>  Lượt xem: <%=item.getCounter()%> <a href="#" class="com"><span><%=i%></span></a></p>
      <div class="clr"></div>
      <div class="img"><img src="<%=request.getContextPath()%>/upload/<%=item.getPicture()%>" width="177" height="213" alt="<%=item.getName()%>" class="fl" /></div>
      <div class="post_content">
        <p class="title-word title-word-2"><%=item.getPreview_text()%></p>
        <p class="spec"><a href="<%=request.getContextPath()%>/<%=StringUtil.makeSlug(item.getCategory().getName())%>/<%=StringUtil.makeSlug(item.getName())%>-<%=item.getId()%>.html" class="rm">Chi tiết &raquo;</a></p>
      </div>
      <div class="clr"></div>
    </div>
    <%}}else{ %>
    <div class="article">
   	 Chưa có bài hát nào!
    </div>
    <%}%>
    <%
   		int numberOfPages = (int) request.getAttribute("numberOfPages");
   		int currentPage = (int) request.getAttribute("currentPage");
   		if(songs != null && songs.size() >0){
    %>
    <p class="pages"><small>Trang <%=currentPage%> của <%=numberOfPages%></small>
    <a href="<%=request.getContextPath()%>/page/<%=currentPage -1%>">&laquo;</a>
    <% for(int i =1; i <= numberOfPages; i++){ %>
    <% if (currentPage == i){ %>
    <span><%=i%></span>
    <%}else{ %>
    <a href="<%=request.getContextPath()%>/page/<%=i%>"><%=i%></a>
    <%}}%>
    <a href="<%=request.getContextPath()%>/page/<%=currentPage + 1%>">&raquo;</a></p>
    <%} %>
  </div>
  <div class="sidebar">
      <%@ include file="../templates/public/inc/leftbar.jsp" %>
  </div>
  <div class="clr"></div>
</div>
<%@ include file="../templates/public/inc/footer.jsp" %>
