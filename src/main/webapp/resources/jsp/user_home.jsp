<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html>

<%@ include file="../includes/head.jsp"%>

<body>
	<%@ include file="../includes/header.jsp"%>

	<div class="container" id="main">
		<div class="col-lg-6">
			<div class="row">
				<h3 class="text-left">User information:</h3>
			</div>
			<div class="form-horizontal" role="show_user_info">
				<div class="form-group">
					<label for="username" class="col-lg-2 control-label">Username</label>
					<div class="col-lg-10">
						<label for="username_field" class="form-control ">${userProfile.username}</label>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-lg-2 control-label">Name</label>
					<div class="col-lg-10">
						<label for="name_field" class="form-control ">${userProfile.name}</label>
					</div>
				</div>
			</div>
			<div class="row">
				<h3 class="text-left">Listed to event:</h3>
			</div>
			<div class="row">
				<div id="" class="list-group">
					<c:if test="${not empty event}">
						<a href="#" id="user1" class="list-group-item"> <img class=""
							style="float: left; padding-right: 20px;"
							src="/evacuatzia/resources/img/event.png" />
							<h4 class="list-group-item-heading">${event.title}</span>
							</h4>
							<p class="list-group-item-text">${event.means}</p>
						</a>
					</c:if>
					<c:if test="${empty event}">
						<p>Not registered to any event</p>
					</c:if>
				</div>
			</div>
			<c:if test="${currentUserHomePage}">
				<div class="row">
					<h3 class="text-left">Options:</h3>
				</div>
				<div class="row">
					<button type="submit" class="btn btn-default">Delete My
						Account</button>
				</div>
			</c:if>
		</div>
		<div class="col-lg-6">
			<div class="row">
				<h3 class="text-left">Reports:</h3>
			</div>
			<div class="row">
				<div id="" class="list-group">
					<c:forEach var="report" items="${reports}">
						<a href="#" id="user1" class="list-group-item"> <img class=""
							style="float: left; padding-right: 20px;"
							src="/evacuatzia/resources/img/report.png" />
							<h4 class="list-group-item-heading">
								<span>${report.title}</span>
							</h4>
							<p class="list-group-item-text">Details</p>
						</a>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>

</html>