<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!doctype html>
<html>

<%@ include file="../includes/head.jsp"%>

<body>
	<%@ include file="../includes/header.jsp"%>


	<div class="container">
		<div class="row">
			<div class="row">
				<img class="" style="float: left; padding-right: 20px;"
					src="/evacuatzia/resources/img/clock.png" />
				<h3 class="border-bottom">Event</h3>
			</div>
			<div class="col-lg-6">
				<div class="row">
					<div class="theLabel col-lg-4">
						<p>Location:</p>
					</div>
					<div class="theData col-lg-8">
						<p>${event.location}</p>
					</div>
				</div>
				<div class="row">
					<c:set var="eventDate" value="${event.estimatedTime}" />
					<div class="theLabel col-lg-4">
						<p>Estimated Time:</p>
					</div>
					<div class="theData col-lg-8">
						<p>
							<fmt:formatDate type="both" dateStyle="short" timeStyle="short"
								value="${eventDate}" />
						</p>
					</div>
				</div>
				<div class="row">
					<div class="theLabel col-lg-4">
						<p>Means of Evacuation:</p>
					</div>
					<div class="theData col-lg-8">
						<p>${event.meansOfEvacuation}</p>
					</div>
				</div>
				<div class="row">
					<div class="theLabel col-lg-4">
						<p>Capacity:</p>
					</div>
					<div class="theData col-lg-8">
						<p>${event.capacity}</p>
					</div>
				</div>
				<div class="row">
					<div class="theLabel col-lg-4">
						<p>Participants:</p>
					</div>
					<div class="theData col-lg-8">
						<p>${event.registrationCount}</p>
					</div>
				</div>
				<div class="row">
					<c:choose>
						<c:when test="${isAdmin}">
							<div class="row">
								<button type="button" class="btn btn-default">Delete
									this Event </button>
							</div>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${userIsRegisteredToThisEvent}">
									<h4>You are registered for this event</h4>
									<div class="row">
										<button type="button" class="btn btn-default">Leave
											this Event</button>
									</div>
								</c:when>
								<c:when
									test="${(not userIsRegisteredToThisEvent) && (isLoggedIn)}">
									<h4>You are not registered for this event</h4>
									<c:if test="${(event.registrationCount < event.capacity)}">
										<div class="row">
											<form action="/evacuatzia/event/join/${event.eventID}"
												method="GET">
												<button type="submit" class="btn btn-default">Join
													this Event</button>
											</form>
										</div>
									</c:if>
								</c:when>
								<c:when
									test="${(not userIsRegisteredToThisEvent) && (not isLoggedIn)}">
									<h4>Log in to register for this event</h4>
								</c:when>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</div>

			</div>
			<div class="col-lg-6">
				<div class="row">
					<div class="row">
						<h4>List of Users Registered for this Event</h4>
					</div>
					<div class="row">
						<div id="list" class="">
							<div id="" class="list-group">
								<c:forEach var="user" items="${users}">
									<a href="/evacuatzia/user/${user.username}"
										class="list-group-item user-list"> <img class=""
										style="float: left; padding-right: 20px;"
										src="/evacuatzia/resources/img/user2.png" /> <c:if
											test="${isAdmin}">
											<!--  If Admin clicks X, Submit a form that issues a POST to have this user removed -->
											<span class="glyphicon glyphicon-remove float-right x"></span>
											<p class="list-group-item-text">User since: 01/01/01 BC</p>
										</c:if>
										<h4 class="list-group-item-heading">${user.username}</h4>
									</a>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<script>
		$('.x').click(function(id) {
			document.location = 'http://google.com/';
		});
	</script>
</body>

</html>