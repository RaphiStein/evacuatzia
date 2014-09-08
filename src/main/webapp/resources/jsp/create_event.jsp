<!doctype html>
<html>

<%@ include file="../includes/head.jsp"%>

<body>
	<%@ include file="../includes/header.jsp"%>
	<script src="https://maps.googleapis.com/maps/api/js"></script>
	<script type="text/javascript" src="/evacuatzia/resources/js/initializeMap.js"></script>

	
	<div class="container">
		<form class="form form-inline form-multiline">
			<div class="col-lg-6 col-lg-offset-3">
				<div class="row">
					<h1 class="text-center">Add Event</h1>
				</div>
				<div class="row" style="padding: 10px;">
					<div class="">
						<input id="geocodeInput" type="text" class="form-control" placeholder="Geocode">
						<div id="map-canvas" style="height: 200px;"></div>
					</div>
				</div>
				<div class="row" style="padding: 10px;">
					<div class="col-lg-6">
						<input type="date" class="form-control"
							placeholder="Estimate Date">
					</div>
					<div class="col-lg-6">
						<input type="time" class="form-control"
							placeholder="Estimate Time">
					</div>
				</div>
				<div class="row" style="padding: 10px;">
					<div class="">
						<input type="text" class="form-control"
							placeholder="Means of Evacuation"></input>
					</div>
				</div>
				<div class="row" style="padding: 10px;">
					<div class="">
						<input type="number" class="form-control" id="capacity"
							placeholder="Capacity" />
					</div>
				</div>
				<div class="row" style="padding: 10px;">
					<div class="">
						<button type="button" class="btn btn-default">Add Event</button>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>

</html>