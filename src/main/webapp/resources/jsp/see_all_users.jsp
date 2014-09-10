<!doctype html>
<html>

	<%@ include file="../includes/head.jsp" %>
	
<body>
	<%@ include file="../includes/header.jsp" %>

  <div class="row">
    <div class="col-lg-6 col-lg-offset-3">
      <h3 class="text-center"> All Users </h3>
    </div>
  </div>
  <div class="row">
    <div id="list" class="col-lg-6 col-lg-offset-3">
      <div id="" class="list-group">
        <c:forEach items="${users}" var="user">
        <a href="/evacuatzia/user/${user.username}" class="list-group-item">
            <img class="" style="float: left; padding-right: 20px; "src="/evacuatzia/resources/img/user2.png"/>
            <h4 class="list-group-item-heading">${user.name}</span></h4>
            <p class="list-group-item-text">User since: 01/01/01 BC</p>
        </a>
        </c:forEach>
        
      </div>
    </div>
  </div>


</body>