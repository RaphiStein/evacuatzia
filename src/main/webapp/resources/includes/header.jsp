<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<header class="row">
    <nav class="navbar navbar-default" role="navigation">
      <div class="container-fluid middle">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/evacuatzia"><img src="/evacuatzia/resources/img/evacuatzia.png" alt="Evacuatzia"></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav">
            <li><a href="about">About</a></li>

          </ul>
          <ul class="nav navbar-nav navbar-right">
          <c:if test="${isLoggedIn}">
            <li><a href="/evacuatzia/user/${user.username}">${user.username}</a></li>
           </c:if>
           <c:if test="${isAdmin}">
            <li><a href="/evacuatzia/admin">Admin</a></li>
           </c:if>
            <c:if test="${(not isLoggedIn) && (not isAdmin)}">
            <li><a href="/evacuatzia/home">My Account</a></li>
           </c:if>
            

            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Options <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="/evacuatzia/home">Home</a></li>
                <li class="divider"></li>
                <li><a href="/evacuatzia/logout">Log Out</a></li>
              </ul>
            </li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </div><!-- /.container-fluid -->
    </nav>
  </header>
