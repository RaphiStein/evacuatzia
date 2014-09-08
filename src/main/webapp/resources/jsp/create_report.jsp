<!doctype html>
<html>

<%@ include file="../includes/head.jsp"%>

<body>
	<%@ include file="../includes/header.jsp"%>

	<script src="https://maps.googleapis.com/maps/api/js"></script>
	<script type="text/javascript"
		src="/evacuatzia/resources/js/initializeMap.js"></script>
	<script>
	google.maps.event.addListener(map, 'click', function(event) {

	    marker = new google.maps.Marker({position: event.latLng, map: map});

	});
	</script>
		
	<div class="container">
		<div class="col-lg-6 col-lg-offset-3">
			<div class="row">
				<h1 class="text-center">Create Report</h1>
			</div>
			<div class="row" style="padding: 10px;">
				<div class="">
					<input type="text" class="form-control" placeholder="Title">
				</div>
			</div>
			<div class="row" style="padding: 10px;">
				<div class="">
					<textarea type="text" class="form-control" placeholder="Content"></textarea>
				</div>
			</div>
			<div class="row" style="padding: 10px;">
				<div class="">
					<input type="text" class="form-control" placeholder="Geocode">
					<div id="map-canvas" style="height: 200px;" /></div>
				</div>
			</div>
			<div class="row" style="padding: 10px;">
				<div class="">
					<button type="button" class="btn btn-default">Create
						Report</button>
				</div>
			</div>
		</div>
	</div>
</body>

</html>