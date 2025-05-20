<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>

<script type="text/javascript">
function show(){
	var value = document.getElementById("btn").innerText;
	alert(value);
}
</script>

<button id="btn" onclick="show();"> Already Clicked!</button>
</body>
</html>