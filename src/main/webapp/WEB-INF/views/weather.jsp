<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${pageContext.request.contextPath}/js/weather.js" type="text/javascript"></script>

<div class="section weather-section">
    <div class="weather-card">
        쉽지 않지만
        그럴수도 있지 <br>
        .. 눈물..( ´•̥ ̫ •̥` )
    </div>
    <div class="weather-card">

    </div>
    <div class="weather-card">
        <p>${today.substring(0, 4)}년 ${today.substring(4, 6)}월 ${today.substring(6, 8)}일 (오늘)</p>
        <pre>${weather}</pre>
    </div>
    <div class="weather-card">

    </div>
    <div class="weather-card">

    </div>
</div>