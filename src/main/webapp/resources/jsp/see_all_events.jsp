<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!doctype html>
<html>
<%@ include file="../includes/head.jsp"%>

<body>
	<%@ include file="../includes/header.jsp"%>

	<div class="row">
		<div class="col-lg-6 col-lg-offset-3">
			<h3 class="text-center">All Events</h3>
		</div>
	</div>
	<div class="row">
		<div id="list" class="col-lg-6 col-lg-offset-3">
			<div id="" class="list-group">
				<c:forEach items="${events}" var="event">
					<a href="/evacuatzia/event/${event.eventID}" id="${event.eventID}"
						class="list-group-item"> <img class=""
						style="float: left; padding-right: 20px;"
						src="/evacuatzia/resources/img/clock.png" />
						<h4 class="list-group-item-heading">${event.meansOfEvacuation}</span>
						</h4> <c:if test="${isAdmin}">
							<!--  If Admin clicks X, Submit a form that issues a POST to have this user removed -->
							<span id="${event.eventID}" class="x glyphicon glyphicon-remove float-right"></span>
						</c:if>
						<p class="list-group-item-text">${event.estimatedTime}</p>
					</a>
				</c:forEach>
			</div>
		</div>
	</div>

	<script>
$('.x').click(function(event){
    event.preventDefault();
    alert("Event " + event.target.id + " will be removed.");
    document.location = '/evacuatzia/event/remove/' + event.target.id;
});
</script>
</body>