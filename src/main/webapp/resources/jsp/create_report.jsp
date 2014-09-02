<!doctype html>
<html>

	<%@ include file="../includes/head.jsp" %>
	
<body>
	<%@ include file="../includes/header.jsp" %>

  <div class="container">
    <div class="col-lg-6 col-lg-offset-3">
      <div class="row">
        <h1 class="text-center"> Create Report </h1> 
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
          <div id="map-canvas" style="height: 200px;"/></div>
        </div>
      </div>
      <div class="row" style="padding: 10px;">
        <div class="">
          <button type="button" class="btn btn-default">Create Report</button>
        </div>
      </div>
    </div>
  </div>
</body>

</html>