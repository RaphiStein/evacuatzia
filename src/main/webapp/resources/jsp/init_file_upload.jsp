<!doctype html>
<html>
<%@ include file="../includes/head.jsp"%>
<body>
	<%@ include file="../includes/header.jsp"%>
	<div class="container">
    	<div class="row">
	   	   <h2>File Upload:</h2>
	   </div>
		<div class="row">
			<div class="row">
    			<h3>Select initialization file to upload:</h3>
			</div>
			<div class="row">
				<form action="" method="POST" enctype="multipart/form-data">
					<input type="file" name="file"/>
					<input type="submit" value="Upload File" />
				</form>
			</div>
		</div>
	</div>
</body>
</html>