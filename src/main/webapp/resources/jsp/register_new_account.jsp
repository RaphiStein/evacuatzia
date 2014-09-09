<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html>

<%@ include file="../includes/head.jsp"%>

<body>
	<%@ include file="../includes/header.jsp"%>
	<div class="col-lg-6 col-lg-offset-3">
		<form action="" method="POST">
			<div class="row">
				<h1 class="col-lg-9 text-center">Register to Evacuatzia!</h1>
				<p class="text-center">${message}</p>
			</div>
			<div class="row" style="padding: 10px;">
				<div class="row">
					<div class="theLabel col-lg-4">
						<p>Name:</p>
					</div>
				</div>
				<div class="row">
					<input name="name" type="text" class="form-control" name="name"
						placeholder="Your name">
				</div>
			</div>
			<div class="row" style="padding: 10px;">
				<div class="row">
					<div class="theLabel col-lg-4">
						<p>Username:</p>
					</div>
				</div>
				<div class="row">
					<input name="username" type="text" class="form-control" name="username"
						placeholder="Desired username">
				</div>
			</div>
			<div class="row" style="padding: 10px;">
				<div class="row">
					<div class="theLabel col-lg-4">
						<p>Password:</p>
					</div>
				</div>
				<div class="row">
					<input name="password" type="password" class="form-control" name="password"
						placeholder="Password">
				</div>
			</div>
			<div class="row" style="padding: 10px;">
				<button type="submit" class="btn btn-default">Register now!</button>
			</div>
			<span class="error">${error}</span>
		</form>
	</div>
</body>