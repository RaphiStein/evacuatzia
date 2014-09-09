<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!doctype html>
<html>
	<%@ include file="../includes/head.jsp" %>
	
<body>
	<%@ include file="../includes/header.jsp" %>

  <div class="row">
    <div class="col-lg-6 col-lg-offset-3">
      <h3 class="text-center"> All Events </h3>
    </div>
  </div>
  <div class="row">
    <div id="list" class="col-lg-6 col-lg-offset-3">
      <div id="" class="list-group">
        <c:foreach items="${events}" var="event">
        <a href="#" id="${event.getEventId}" class="list-group-item">
            <img class="" style="float: left; padding-right: 20px; "src="/evacuatzia/resources/img/clock.png"/>
            <h4 class="list-group-item-heading">${event.getMeansOfEvacuation}</span></h4>
            <p class="list-group-item-text">${event.getEstimatedTime}</p>
        </a>
        </c:foreach> 
      </div>
    </div>
  </div>


</body>