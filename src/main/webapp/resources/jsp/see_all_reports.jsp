<!doctype html>
<html>

	<%@ include file="../includes/head.jsp" %>
	
<body>
	<%@ include file="../includes/header.jsp" %>


  <div class="row">
    <div class="col-lg-6 col-lg-offset-3">
      <h3 class="text-center"> All Reports </h3>
    </div>
  </div>
  <div class="row">
    <div id="list" class="col-lg-6 col-lg-offset-3">
      <div id="" class="list-group">
      <c:forEach items="${reports}" var="report">
        <a href="#" id="user1" class="list-group-item">
            <img class="" style="float: left; padding-right: 20px; "src="/evacuatzia/resources/img/report.png"/>
            <h4 class="list-group-item-heading">${report.title}</span></h4>
            <p class="list-group-item-text">${report.content}</p>
        </a> 
        </c:forEach>
      </div>
    </div>
  </div>


</body>