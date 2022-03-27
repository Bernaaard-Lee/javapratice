<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file= "../layout/header.jsp" %>

<div class="container">
	<form action="/auth/loginProc" method="post">
	<div class="form-group">
	    <label for="username">아이디:</label>
	    <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
	  </div>
	  <div class="form-group">
	    <label for="password">비밀번호:</label>
	    <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
	  </div>	  <div class="form-group form-check">
	    <label class="form-check-label">
	      <input class="form-check-input" type="checkbox"> 아이디 비밀번호 저장
	    </label>
	  </div>
		<button id="btn-login" class="btn btn-primary">로그인</button>
	</form>

</div>
<%@ include file= "../layout/footer.jsp" %>