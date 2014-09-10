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
					<a href="#" id="${event.eventID}" class="list-group-item"> <img
						class="" style="float: left; padding-right: 20px;"
						src="/evacuatzia/resources/img/clock.png" />
						<h4 class="list-group-item-heading">${event.meansOfEvacuation}</span>
						</h4>
						<p class="list-group-item-text">${event.estimatedTime}</p>
					</a>
					<c:if test="${isAdmin}">
						<form action="" method=POST>
							<!--  If Admin clicks X, Submit a form that issues a POST to have this user removed -->
							<input type="hidden" name="delete" value="${event.eventID}" /> <a
								href="javascript:void(0);" onclick="this.form.submit()"><span
								class="glyphicon glyphicon-remove float-right"></span></a>
						</form>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</div>


</body>