<%@page import="util.StringUtil"%>
<%@page import="java.awt.event.ItemEvent"%>
<%@page import="model.bean.Song"%>
<%@page import="model.dao.SongDAO"%>
<%@page import="model.bean.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.dao.CategoryDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<style>
.active-category{
  color: green;
  font-weight:bold;
}
</style>
<div class="searchform">
  <form id="formsearch" name="formsearch" method="get" action="<%=request.getContextPath()%>/search">
    <span>
    <input name="editbox_search" class="editbox_search" id="editbox_search" maxlength="80" value ="" placeholder="Tìm kiếm bài hát" type="text" />
    </span>
    <input name="button_search" src="<%=request.getContextPath()%>/templates/public/images/search.jpg" class="button_search" type="image" />
  </form>
</div>
<div class="clr"></div>
<div class="gadget">
  <h2 class="star">Danh mục bài hát</h2>
  <div class="clr"></div>
  <ul class="sb_menu">
  <%
		CategoryDAO categoryDAO = new CategoryDAO();
		ArrayList<Category> categories = categoryDAO.getItems();
		if(categories.size()>0){
			for(Category item:categories){
  %>
    <li><a class="lef-cat" href="<%=request.getContextPath()%>/<%=StringUtil.makeSlug(item.getName())%>-<%=item.getId()%>" id="<%=item.getId()%>"><%=item.getName()%> </a></li>
    <%}} %>
  </ul>
</div>
	
<div class="gadget">
  <h2 class="star"><span>Bài hát mới</span></h2>
  <div class="clr"></div>
  <ul class="ex_menu">
  <%
		SongDAO songDAO = new SongDAO();
		ArrayList<Song> recentlySongs = songDAO.getItems(6);
		if(recentlySongs.size()>0){
			for(Song itemSong:recentlySongs){
  %>
    <li><a href="<%=request.getContextPath()%>/<%=StringUtil.makeSlug(itemSong.getCategory().getName())%>/<%=StringUtil.makeSlug(itemSong.getName())%>-<%=itemSong.getId()%>.html"><%=itemSong.getName()%></a><br />
      <%if(itemSong.getPreview_text().length() >50) out.print(itemSong.getPreview_text().substring(0, 50) +"..."); else out.print(itemSong.getPreview_text()); %></li>
      <%}} %>
  </ul>
</div>

<!-- <script>
$(".lef-cat").click(function() {             
	// when clicking any of these links
    $(".lef-cat").removeClass("active-category"); // remove highlight from all links
    $(this).addClass("active-category");          // add highlight to clicked link
})
</script> -->