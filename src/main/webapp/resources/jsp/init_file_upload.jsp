<!doctype html>
<html>
<%@ include file="../includes/head.jsp"%>
<body>
	<%@ include file="../includes/header.jsp"%>
	<div class="container">
		<h3>File Upload:</h3>
		<div>
			<div>
    			Select a file to upload:
			</div>
			<div>
				<form action="" method="POST" enctype="multipart/form-data">
					<input type="file" name="file"/>
					<input type="submit" value="Upload File" />
				</form>
			</div>
		</div>
	</div>
</body>
</html>