<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html>

	<%@ include file="../includes/head.jsp" %>
	
<body>
	<%@ include file="../includes/header.jsp" %>

  <div class="container">
    <div class="row">
      <div class="row">
        <img class="" style="float: left; padding-right: 20px; "src="resources/img/report.png"/>
        <h3 class="border-bottom"> Report </h3>
      </div>
      <div class="col-lg-6">
        <div class="row">
          <div class="theLabel col-lg-4"><p> User </p></div>
          <div class="theData col-lg-8"><p> ${report.user.username} </p></div>
        </div>
        <div class="row">
          <div class="theLabel col-lg-4"><p> Location: </p></div>
          <div class="theData col-lg-8"><p> ${report.location} </p></div>
        </div>
        <div class="row">
          <div class="theLabel col-lg-4"><p> Title </p></div>
          <div class="theData col-lg-8"><p> ${report.title} </p></div>
        </div>
        <div class="row">
          <div class="theLabel col-lg-4"><p> Content </p></div>
          <div class="theData col-lg-8"><p> Bla Bla Bla </p></div>
        </div>
        <div class="row">
          <c:set var="reportDate" value="${report.reportTime}" />
          <div class="theLabel col-lg-4"><p> Report date </p></div>
          <div class="theData col-lg-8"><p><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${reportDate}" /></p></div>
        </div>
        <div class="row">
          <c:if test="${user.id.equals(report.user.id)}">
            <button type="button" class="btn btn-default">Edit report</button>
            <button type="button" class="btn btn-default">Delete report</button>
          </c:if>
       </div>
     </div>
     

   </div>

 </div>

</body>

</html>