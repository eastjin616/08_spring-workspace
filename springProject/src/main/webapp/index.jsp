​<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ajax</title>
<script src="https://code.jquery.com/jquery-3.4.0.js"></script>
</head>
<body>

	<input type="text" id="email">
	<span id="result"></span>

	<script>
		$("#email").on("input", function() {

			var email = $("#email").val();

			var regex = /\w+@\w\.\w+/;

			if (regex.exec(email) == null) {

				$("#result").html("이메일 형식에 맞지 않습니다");

				return;

			}

			$.ajax({
				url : "CheckEmail",
				data : {
					email : $("#email").val()
				},
				success : function(resp) {

					$("#result").html(resp);
				}
			})
		})
	</script>
</body>
</html>