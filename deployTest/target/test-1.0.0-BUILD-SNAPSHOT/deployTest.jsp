<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

    <script>
        function show(){
            let value = document.getElementById("btn").innerText;
            alert(value);
        }
    </script>

    <button id="btn" onclick="show()">그냥 버튼</button>


</body>
</html>