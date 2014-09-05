<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html>

	<%@ include file="../includes/head.jsp" %>
	
<body>
	<%@ include file="../includes/header.jsp" %>

	<div class="container" id="main">
		<div class="col-lg-6">
			<div class="row">
				<h3 class="text-left">User information:</h3>
			</div>
			<form class="form-horizontal" role="show_user_info">
				<div class="form-group">
					<label for="username" class="col-lg-2 control-label">Username</label>
					<div class="col-lg-10">
						<label for="username_field" class="form-control ">${user.username}</label>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-lg-2 control-label">Name</label>
					<div class="col-lg-10">
						<label for="name_field" class="form-control ">${user.name}</label>
					</div>
				</div>
			</form>
			<div class="row">
				<h3 class="text-left">Listed to event:</h3>
			</div>
			<div id="list" class="col-lg-6 col-lg-offset-3">
				<div class="row">
					<div id="" class="list-group">
						<a href="#" id="user1" class="list-group-item"> <img class=""
							style="float: left; padding-right: 20px;" src="/evacuatzia/resources/img/report.png" />
							<h4 class="list-group-item-heading">
								Some event</span>
							</h4>
							<p class="list-group-item-text">Details</p>
						</a>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-6">
			<div class="row">
				<h3 class="text-left">Reports:</h3>
			</div>
			<div id="list" class="col-lg-6 col-lg-offset-3">
				<div class="row">
					<div id="" class="list-group">
					 <c:forEach var="report" items="${reports}"> 
						<a href="#" id="user1" class="list-group-item"> <img class="" style="float: left; padding-right: 20px;" src="/evacuatzia/resources/img/report.png" />
							<h4 class="list-group-item-heading"><span>${report.title}</span></h4>
							<p class="list-group-item-text">Details</p>
						</a> 
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>