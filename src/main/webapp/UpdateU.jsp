<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Toggle Value</title>
    <script>
        function toggleValue() {
            document.getElementById("toggleForm").submit();
        }
    </script>
</head>
<body>
    <h1>Toggle Value</h1>
    <form id="toggleForm" action="UpdateUser" method="post">
        <input type="hidden" name="toggle" value="true">
        <button type="button" onclick="toggleValue()">Toggle Value</button>
    </form>
</body>
</html>
