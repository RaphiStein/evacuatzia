<!doctype html>
<html>
	<%@ include file="resources/includes/head.jsp" %>
<body>
	<%@ include file="resources/includes/header.jsp" %>

	<div class="container" id="main">
		<div class="col-lg-6">
			<div class="row">
				<h3 class="text-left">User information:</h3>
			</div>
			<form class="form-horizontal" role="show_user_info">
				<div class="form-group">
					<label for="username" class="col-lg-2 control-label">Username</label>
					<div class="col-lg-10">
						<lable for="username_field" class="form-control ">Shmulik123</lable>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-lg-2 control-label">Name</label>
					<div class="col-lg-10">
						<lable for="name_field" class="form-control ">Shmulik
						Ben-David</lable>
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
							style="float: left; padding-right: 20px;" src="../img/report.png" />
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
						<a href="#" id="user1" class="list-group-item"> <img class=""
							style="float: left; padding-right: 20px;" src="../img/report.png" />
							<h4 class="list-group-item-heading">
								Some report</span>
							</h4>
							<p class="list-group-item-text">Details</p>
						</a> <a href="#" id="user1" class="list-group-item"> <img class=""
							style="float: left; padding-right: 20px;" src="../img/report.png" />
							<h4 class="list-group-item-heading">
								Some report</span>
							</h4>
							<p class="list-group-item-text">Details</p>
						</a> <a href="#" id="user1" class="list-group-item"> <img class=""
							style="float: left; padding-right: 20px;" src="../img/report.png" />
							<h4 class="list-group-item-heading">
								Some report</span>
							</h4>
							<p class="list-group-item-text">Details</p>
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>