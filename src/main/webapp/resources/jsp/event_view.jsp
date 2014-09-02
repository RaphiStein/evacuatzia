<!doctype html>
<html>

	<%@ include file="../includes/head.jsp" %>
	
<body>
	<%@ include file="../includes/header.jsp" %>


  <div class="container">
    <div class="row">
      <div class="row">
        <img class="" style="float: left; padding-right: 20px; "src="../img/clock.png"/>
        <h3 class="border-bottom"> Event </h3>
      </div>
      <div class="col-lg-6">
        <div class="row">
          <div class="theLabel col-lg-4"><p> Location: </p></div>
          <div class="theData col-lg-8"><p> Somewhere </p></div>
        </div>
        <div class="row">
          <div class="theLabel col-lg-4"><p> Estimated Time: </p></div>
          <div class="theData col-lg-8"><p> 01/01/01 12:01 </p></div>
        </div>
        <div class="row">
          <div class="theLabel col-lg-4"><p> Means of Evacuation: </p></div>
          <div class="theData col-lg-8"><p> Flying car </p></div>
        </div>
        <div class="row">
          <div class="theLabel col-lg-4"><p> Capacity: </p></div>
          <div class="theData col-lg-8"><p> 9 </p></div>
        </div>
        <div class="row">
          <div class="theLabel col-lg-4"><p> Participants: </p></div>
          <div class="theData col-lg-8"><p> 3 </p></div>
        </div>
        <div class="row">
          <h4> You are <mark>not</mark> registered for this event </h4>
        </div>
        <div class="row">
         <button type="button" class="btn btn-default">Join this Event</button>
         <button type="button" class="btn btn-default">Delete this Event (Admin)</button>
       </div>

     </div>
     <div class="col-lg-6">
      <div class="row border-bottom">
        <div class="row">
          <h4> Add Users to this Event (Admin) </h4>
        </div>
        <div class="row">
          <select class="form-control">
            <option value="one">User 1</option>
            <option value="two">User 2</option>
            <option value="three">User 3</option>
            <option value="four">User 4</option>
            <option value="five">User 5</option>
          </select>
        </div>
        <div class="row"><div class="buffer"></div></div>
        <div class="row">
          <button type="button" class="btn btn-default">Add User</button>
        </div>
        <div class="row"><div class="buffer"></div></div>
      </div>
      <div class="row">
        <div class="row">
          <h4> List of Users Registered for this Event  </h4>
        </div>
        <div class="row">
          <div id="list" class="">
            <div id="" class="list-group">
              <a href="#" id="user1" class="list-group-item">
                <img class="" style="float: left; padding-right: 20px; "src="../img/user2.png"/>
                <h4 class="list-group-item-heading">John Smith</span></h4>
                <span class="glyphicon glyphicon-remove float-right"></span>
                <p class="list-group-item-text">User since: 01/01/01 BC</p>
              </a>
              <a href="#" id="user1" class="list-group-item">
                <img class="" style="float: left; padding-right: 20px; "src="../img/user2.png"/>
                <h4 class="list-group-item-heading">John Smith</span></h4>
                <span class="glyphicon glyphicon-remove float-right"></span>
                <p class="list-group-item-text">User since: 01/01/01 BC</p>
              </a>
              <a href="#" id="user1" class="list-group-item">
                <img class="" style="float: left; padding-right: 20px; "src="../img/user2.png"/>
                <h4 class="list-group-item-heading">John Smith</span></h4>
                <span class="glyphicon glyphicon-remove float-right"></span>
                <p class="list-group-item-text">User since: 01/01/01 BC</p>
              </a>
              <a href="#" id="user1" class="list-group-item">
                <img class="" style="float: left; padding-right: 20px; "src="../img/user2.png"/>
                <h4 class="list-group-item-heading">John Smith</span></h4>
                <span class="glyphicon glyphicon-remove float-right"></span>
                <p class="list-group-item-text">User since: 01/01/01 BC</p>
              </a>
              <a href="#" id="user1" class="list-group-item">
                <img class="" style="float: left; padding-right: 20px; "src="../img/user2.png"/>
                <h4 class="list-group-item-heading">John Smith</span></h4>
                <span class="glyphicon glyphicon-remove float-right"></span>
                <p class="list-group-item-text">User since: 01/01/01 BC</p>
              </a>
              <a href="#" id="user1" class="list-group-item">
                <img class="" style="float: left; padding-right: 20px; "src="../img/user2.png"/>
                <h4 class="list-group-item-heading">John Smith</span></h4>
                <span class="glyphicon glyphicon-remove float-right"></span>
                <p class="list-group-item-text">User since: 01/01/01 BC</p>
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</div>
</body>

</html>