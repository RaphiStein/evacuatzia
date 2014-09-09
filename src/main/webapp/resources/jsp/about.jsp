<!doctype html>
<html>

    <%@ include file="../includes/head.jsp" %>
    
<body>
    <%@ include file="../includes/header.jsp" %>

    <div class="col-lg-6 col-lg-offset-3">
    <div class="row">
       <h2>About us:</h2>
    </div>
    <div class="row">
       <h3>Personal information:</h3>
       <div class="row">
           <p> Raphi Stein raphistein@gmail.com </p>
       </div>
       <div class="row">
           <p> Gilad Bretter sbretter@t2.technion.ac.il </p>
       </div>
    </div>
    <div class="row">
        <h3>Didn't manage to get to the advance features part</h3>
    </div>
    <div class="row">
        <h3>Database management</h3>
        <div class="row">
           <p> 
                We used postgres database with extention to postgis. </br>
                We managed this database using Spring hibernate which allows us great modularity. 
                During the development we switched from MySQL to postgres, and using Hibernate realy 
                ease that transition.</br>
                To use our site, one should either register as a user, or use the admin account 
                (username and password will be submitted in the details.txt file). <br>
                Once logged in as an admin, you can use the system for uploading a json file, as requested.
            </p>
       </div>
    </div>

</body>
</html>