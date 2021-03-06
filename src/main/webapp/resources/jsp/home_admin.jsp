<!doctype html>
<html>

<%@ include file="../includes/head.jsp"%>

<body>
	<%@ include file="../includes/header.jsp"%>

	<div class="container">
		<div class="row">
			<div class="col-lg-6 col-lg-offset-3">
				<h2 class="text-center">Evacuatzia Administration</h2>
			</div>
		</div>
		<div class="row">
		  <div class="text-center">
                <a href="/evacuatzia/upload_init"><button type="submit" class="btn btn-info btn-lg">Upload initialization file (json format)</button></a>
            </div>
		</div>
		<div class="row top-buffer">
			<div class="col-lg-6 col-lg-offset-3">
				<h4 class="text-center">Evacuation Event Management</h4>
			</div>
		</div>
		<div class="row">
			<div class="text-center">
				<a href="/evacuatzia/admin/action/create_event"><button type="submit" class="btn btn-info btn-lg">Create
						Evacuation Event</button></a> 
						<a href="/evacuatzia/event/all"><button type="submit"
						class="btn btn-info btn-lg">Remove Evacuation Event</button></a>
			</div>
		</div>

		<div class="row top-buffer">
			<div class="col-lg-6 col-lg-offset-3">
				<h4 class="text-center">Evacuation Event Registration</h4>
			</div>
		</div>
		<div class="row">
			<div class="text-center">
				<a href="/evacuatzia/event/all"><button type="submit" class="btn btn-info btn-lg">Add
						User to Event</button></a> 
				<a href="/evacuatzia/event/all"><button type="submit"
						class="btn btn-info btn-lg">Remove User from Event</button></a>
			</div>
		</div>
	</div>
</body>
</html>