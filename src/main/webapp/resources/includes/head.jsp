<head>
  <meta charset="utf-8">
  <title>My Website</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="resources/css/custom.css" rel="stylesheet">
  <!-- Bootstrap -->
  <link href="resources/css/bootstrap.min.css" rel="stylesheet">
  <script src="js/respond.js"></script>
  <script type="text/javascript" src="js/jquery-1.11.0.min.js"></script>
  <script type="text/javascript" src="js/bootstrap.js"></script> <!-- Must be after jquery -->
  <!--Map Related -->
  <script type="text/javascript"src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAVsACwE8b0ut5AdSkfTzNMWQ7nxQYCFwU"></script>
  <script type="text/javascript">
  function initialize() {
    var mapOptions = {
      center: new google.maps.LatLng(-34.397, 150.644),
      zoom: 8
    };
    var map = new google.maps.Map(document.getElementById("map-canvas"),
      mapOptions);
  }
  google.maps.event.addDomListener(window, 'load', initialize);
  </script>
</head>