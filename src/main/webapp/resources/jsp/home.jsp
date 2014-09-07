<!doctype html>
<html>

<%@ include file="../includes/head.jsp"%>

<body>
	<%@ include file="../includes/header.jsp"%>

	<div class="container">
		<div class="row">
			<div class="col-lg-6 col-lg-offset-3">
				<h2 class="text-center">Evacuatzia Home</h2>
			</div>
		</div>
		<div class="row top-buffer">
			<div class="col-lg-6 col-lg-offset-3">
				<h4 class="text-center">Tour</h4>
			</div>
		</div>
		<div class="row">
			<div class="text-center">
				<a href="/evacuatzia/user/all"><button type="submit" class="btn btn-info btn-lg">See All Users</button></a>
				<a href="/evacuatzia/report/all"><button type="submit" class="btn btn-info btn-lg">See All Reports</button></a>
				<a href="/evacuatzia/event/all"><button type="submit" class="btn btn-info btn-lg">See All Events</button></a>
			</div>
		</div>

		<div class="row top-buffer">
			<div class="col-lg-6 col-lg-offset-3">
				<h4 class="text-center">Explore</h4>
			</div>
		</div>
		<div class="row">
			<div class="text-center">
				<a href="/evacuatzia/search"><button type="submit" class="btn btn-info btn-lg">Explore By Map</button></a>
				<a href="/evacuatzia/search/events"><button type="submit" class="btn btn-info btn-lg">Explore Reports</button></a>
				<a href="/evacuatzia/search/reports"><button type="submit" class="btn btn-info btn-lg">Find Nearest Evacuation</button></a>
			</div>
		</div>

		<div class="row top-buffer">
			<div class="col-lg-6 col-lg-offset-3">
				<h4 class="text-center">Contribute</h4>
			</div>
		</div>
		<div class="row">
			<div class="text-center">
				<a href="/evacuatzia/user/action/create_report"><button type="submit" class="btn btn-info btn-lg">Create Report</button></a>
			</div>
		</div>
    </div>
</body>
</html>