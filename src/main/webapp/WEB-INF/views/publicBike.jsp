<%--
 todo [chan]
 1. API키 숨기기
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    const lat = ${sessionScope.lat};
    const lon = ${sessionScope.lon};
</script>

<div class="section">
    <div class="bike-map" id="bike-map"></div>
</div>

<script type="text/javascript"
        src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3719f0720979ba047d6bcde2a9732fa1"></script>
<script src="${pageContext.request.contextPath}/js/publicBike.js" type="text/javascript"></script>
