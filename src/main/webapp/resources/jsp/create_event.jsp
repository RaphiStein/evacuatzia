<!doctype html>
<html>

	<%@ include file="../includes/head.jsp" %>
	
<body>
	<%@ include file="../includes/header.jsp" %>

  <div class="container">
    <form class="form form-inline form-multiline">
      <div class="col-lg-6 col-lg-offset-3">
        <div class="row">
          <h1 class="text-center"> Add Event </h1> 
        </div>
        <div class="row" style="padding: 10px;">
          <div class="">
            <input type="text" class="form-control" placeholder="Geocode">
            <div id="map-canvas" style="height: 200px;">
            </div>
          </div>
        </div>
        <div class="row" style="padding: 10px;">
          <div class="col-lg-6">
            <input type="date" class="form-control" placeholder="Estimate Date">
          </div>
          <div class="col-lg-6">
            <input type="time" class="form-control" placeholder="Estimate Time">
          </div>
        </div>
        <div class="row" style="padding: 10px;">
          <div class="">
            <input type="text" class="form-control" placeholder="Means of Evacuation"></textarea>
          </div>
        </div>
        <div class="row" style="padding: 10px;">
          <div class="">
            <input type="number" class="form-control" id="capacity" placeholder="Capacity"/>
          </div>
        </div>
        <div class="row" style="padding: 10px;">
          <div class="">
            <button type="button" class="btn btn-default">Add Event</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</body>

</html>