<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html>

	<%@ include file="../includes/head.jsp" %>
	
<body>
	<%@ include file="../includes/header.jsp" %>
 
 <script src="https://maps.googleapis.com/maps/api/js"></script>
  <script>
  function initialize() {
    var mapCanvas = document.getElementById('map_canvas');
    var mapOptions = {
      center: new google.maps.LatLng(44.5403, -78.5463),
      zoom: 8,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    var map = new google.maps.Map(mapCanvas, mapOptions)
  }
  google.maps.event.addDomListener(window, 'load', initialize);
  </script>
  
  <div class="row">
    <div class="col-lg-6 col-lg-offset-3">
      <h2 class="text-center"> Reports and Events Map </h2>
    </div>
  </div>
  <div class="row top-buffer">
    <div class="row"> <!-- Location Form -->
      <div class="col-lg-6 col-lg-offset-3">
        <form class="form-horizontal">
          <div class="form-group">
            <label class="col-sm-3 control-label" for="locationInput">Your Location:</label>
            <div class="col-sm-6">
              <input id="locationInput" type="text" class="form-control">
            </div>
            <button type="submit" class="btn btn-default">Search</button>
          </div>
        </form>
      </div>
    </div>
    <div class="row">
      <div class="container">
        <div id="list" class="col-lg-6">
          <div id="" class="list-group">
            <a href="#" id="user1" class="list-group-item">
              <img class="" style="float: left; padding-right: 20px; "src="/evacuatzia/resources/img/clock.png"/>
              <h4 class="list-group-item-heading">Evacuation of the building</span></h4>
              <p class="list-group-item-text">01/01/01 12:01 AM </p>
            </a>
            <a href="#" id="user1" class="list-group-item">
              <img class="" style="float: left; padding-right: 20px; "src="/evacuatzia/resources/img/report.png"/>
              <h4 class="list-group-item-heading">Report about some danger</span></h4>
              <p class="list-group-item-text">01/01/01 12:01 AM </p>
            </a>
            <a href="#" id="user1" class="list-group-item">
              <img class="" style="float: left; padding-right: 20px; "src="/evacuatzia/resources/img/clock.png"/>
              <h4 class="list-group-item-heading">Evacuation of the building</span></h4>
              <p class="list-group-item-text">01/01/01 12:01 AM </p>
            </a>        
          </div>
        </div>
        <div id="list" class="col-lg-6">
          <div id="map_canvas" style="height: 500px; width: 100%;"></div>
        </div>
      </div>
    </div>
  </div>


</body>