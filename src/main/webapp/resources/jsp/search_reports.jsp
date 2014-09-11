<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!doctype html>
<html>

<%@ include file="../includes/head.jsp"%>

<body>
	<%@ include file="../includes/header.jsp"%>

	<div class="row">
		<div class="col-lg-6 col-lg-offset-3">
			<h2 class="text-center">Search for Reports by Name</h2>
		</div>
	</div>
	<div class="row top-buffer">
		<div class="row">
			<!-- Location Form -->
			<div class="col-lg-6 col-lg-offset-3">
				<form action="" method="POST" class="form-horizontal">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="titleSearch">Name:</label>
						<div class="col-sm-6">
							<input name="titleSearch" type="text" class="form-control">
						</div>
						<button type="submit" class="btn btn-default">Search</button>
					</div>
				</form>
			</div>
		</div>
		<div class="row">
			<div class="container">
				<h4>Result:</h4>
			</div>
		</div>
		<div class="row">
			<div class="container">
				<div class="container">
					<div id="list" class="col-lg-6 col-lg-offset-3">
						<div id	="" class="list-group">
							<c:forEach var="report" items="${reports}">
								<a href="/evacuatzia/report/${report.eventID}" class="list-group-item"> <img
									class="" style="float: left; padding-right: 20px;"
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
		</div>
	</div>
</body>