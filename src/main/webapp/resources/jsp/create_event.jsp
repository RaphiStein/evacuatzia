<!doctype html>
<html>

<%@ include file="../includes/head.jsp"%>

<body>
	<%@ include file="../includes/header.jsp"%>
	<script src="https://maps.googleapis.com/maps/api/js"></script>
	<script type="text/javascript" src="/evacuatzia/resources/js/initializeMap.js"></script>

	
	<div class="container">
		<form action="" method="POST" class="form form-inline form-multiline">
			<div class="col-lg-6 col-lg-offset-3">
				<div class="row">
					<h1 class="text-center">Create Event</h1>
					<p class="text-center red">${message}</p>
				</div>
				<div class="row" style="padding: 10px;">
					<div class="">
						<input name="geocode" id="geocodeInput" type="text" class="form-control" placeholder="Geocode">
						<div id="map-canvas" style="height: 200px;"></div>
					</div>
				</div>
				<div class="row" style="padding: 10px;">
					<div class="col-lg-6">
						<input name="date" type="date" class="form-control"
							placeholder="Estimate Date e.g. ">
					</div>
					<div class="col-lg-6">
						<input name="time" type="time" class="form-control"
							placeholder="Estimate Time">
					</div>
				</div>
				<div class="row" style="padding: 10px;">
					<div class="">
						<input name="means" type="text" class="form-control"
							placeholder="Means of Evacuation"></input>
					</div>
				</div>
				<div class="row" style="padding: 10px;">
					<div class="">
						<input name="capacity" type="number" class="form-control" id="capacity"
							placeholder="Capacity" />
					</div>
				</div>
				<div class="row" style="padding: 10px;">
					<div class="">
						<button type="submit" class="btn btn-default">Add Event</button>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>

</html>