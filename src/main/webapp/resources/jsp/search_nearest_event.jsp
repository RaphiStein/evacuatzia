<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html>

	<%@ include file="../includes/head.jsp" %>
	
<body>
	<%@ include file="../includes/header.jsp" %>
	
  <div class="row">
    <div class="col-lg-6 col-lg-offset-3">
      <h1 class="text-center"> Find Nearest Evacuation To You </h1>
      <p class="text-center red">${message}</p>
    </div>
  </div>
  <div class="row top-buffer">
    <div class="row"> <!-- Location Form -->
      <div class="col-lg-6 col-lg-offset-3">
        <form action="" method="POST" class="form-horizontal">
          <div class="form-group">
            <label class="col-sm-3 control-label" for="locationInput">Your Location:</label>
            <div class="col-sm-6">
              <input name="geocode" type="text" class="form-control">
            </div>
            <button type="submit" class="btn btn-default">Search</button>
          </div>
        </form>
      </div>
    </div>
    <div class="row">
      <div class="container">
        <h4>Result:</h4>
      </div>
    </div>
    <div class="row">
      <div class="container">
        <div class="container">
          <div class="container">
            <img class="" style="float: left; padding-right: 20px; "src="/evacuatzia/resources/img/clock.png"/>
            <h4 class="list-group-item-heading">Evacuation of the building</span></h4>
            <p class="list-group-item-text">01/01/01 12:01 AM </p>
          </div>
        </div>
      </div>
    </div>
  </div>


</body>