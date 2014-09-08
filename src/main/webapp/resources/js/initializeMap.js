/**
 * Small file, just to eliminate repetitive identical initialization functions
 */
var map;
function initialize() {
	var mapCanvas = document.getElementById('map-canvas');
	var mapOptions = {
			center : new google.maps.LatLng(44.5403, -78.5463),
			zoom : 8,
			mapTypeId : google.maps.MapTypeId.ROADMAP
	}
	map = new google.maps.Map(mapCanvas, mapOptions);
	var myLatlng = new google.maps.LatLng(44.5403, -78.5463);

//	To add the marker to the map, use the 'map' property
	var marker = new google.maps.Marker({
		position: myLatlng,
		map: map,
		draggable:true,
		title:"Hello World!"
	});
	google.maps.event.addListener(marker, 'dragend', function(evt){
	   console.log('Marker dropped: Current Lat: ' + evt.latLng.lat().toFixed(3) + ' Current Lng: ' + evt.latLng.lng().toFixed(3));
	   document.getElementById("geocodeInput").value = evt.latLng.lat().toFixed(6) + ", " + evt.latLng.lng().toFixed(6);
	});
}
google.maps.event.addDomListener(window, 'load', initialize);
