<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TodaysDotori</title>
    <script src="${pageContext.request.contextPath}/js/common/jquery-3.7.1.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/common/bootstrap.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/common/popper.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
</head>
<body>
<div class="container">
    <!-- 접속 위치 -->
    <jsp:include page="location.jsp" />

    <!-- 날씨 -->
    <jsp:include page="weather.jsp" />

    <!-- 지하철 -->
    <jsp:include page="subway.jsp" />

    <!-- 버스 -->
    <jsp:include page="bus.jsp" />

    <!-- 따릉이 -->
    <jsp:include page="publicBike.jsp" />
</div>
</body>
</html>
