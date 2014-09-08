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
				<c:choose>
					<c:when test="${isAdmin}">
						<div class="row">
							<button type="button" class="btn btn-default">Delete
								this Event (Admin)</button>
						</div>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${isRegisteredToEvent}">
								<div class="row">
									<h4>You are registered for this event</h4>
								</div>
								<div class="row">
									<button type="button" class="btn btn-default">Leave
										this Event</button>
								</div>
							</c:when>
							<c:otherwise>
								<div class="row">
									<h4>
										You are
										<mark>not</mark>
										registered for this event
									</h4>
								</div>
								<c:if test="${event.capacity < event.registrationCount}">
									<div class="row">
										<button type="button" class="btn btn-default">Join
											this Event</button>
									</div>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="col-lg-6">
				<!--       
       <div class="row border-bottom">
        <div class="row">
          <h4> Add Users to this Event (Admin) </h4>
        </div>
        <div class="row">
          <select class="form-control">
            <option value="one">User 1</option>
            <option value="two">User 2</option>
            <option value="three">User 3</option>
            <option value="four">User 4</option>
            <option value="five">User 5</option>
          </select>
        </div>
        <div class="row"><div class="buffer"></div></div>
        <div class="row">
          <button type="button" class="btn btn-default">Add User</button>
        </div>
        <div class="row"><div class="buffer"></div></div>
      </div> 
-->
				<div class="row">
					<div class="row">
						<h4>List of Users Registered for this Event</h4>
					</div>
					<div class="row">
						<div id="list" class="">
							<div id="" class="list-group">
								<c:forEach var="user" items="${registeredUsers}">
									<a href="#" id="user1" class="list-group-item user-list"> <img
										class="" style="float: left; padding-right: 20px;"
										src="/evacuatzia/resources/img/user2.png" />
										<h4 class="list-group-item-heading">
											$
											<user.username>
										</h4> <c:if test="${isAdmin}">
											<form action="" method=POST>
												<!--  If Admin clicks X, Submit a form that issues a POST to have this user removed -->
												<input type="hidden" name="username" value="User1" /> 
												<a href="javascript:void(0);" onclick="this.form.submit()"><span
													class="glyphicon glyphicon-remove float-right"></span></a>
											</form>
										</c:if> <!-- <p class="list-group-item-text">User since: 01/01/01 BC</p> -->
									</a>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>

</html>