<!doctype html>
<html>

	<%@ include file="../includes/head.jsp" %>
	
<body>
	<%@ include file="../includes/header.jsp" %>

	<div class="col-lg-6 col-lg-offset-3">
	<form action="" method="POST">
		<div class="row">
			<h1 class="col-lg-9 text-left">Log in</h1>
		</div>
		<div class="row" style="padding: 10px;">
			<input type="text" class="form-control" placeholder="Username" name="user">
		</div>
		<div class="row" style="padding: 10px;">
			<input type="password" class="form-control" placeholder="Password" name="password">
		</div>
		<div class="row" style="padding: 10px;">
			<button type="submit" class="btn btn-default">Log in</button>
		</div>
		<span class="error">${error}</span>
		</form>
	</div>
</body>

</html>