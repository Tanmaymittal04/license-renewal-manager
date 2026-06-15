<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="/WEB-INF/jsp/fragments/head.jspf" />
<body>
<canvas id="bgCanvas"></canvas>

<div class="lm-shell">
    <jsp:include page="/WEB-INF/jsp/fragments/sidebar.jspf" />
    <div class="lm-main">
        <jsp:include page="/WEB-INF/jsp/fragments/topbar.jspf" />
        <main class="lm-content">
            <!-- The actual page content will be inserted here by child JSPs -->
            <jsp:doBody />
        </main>
        <jsp:include page="/WEB-INF/jsp/fragments/footer.jspf" />
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/fragments/scripts.jspf" />
</body>
</html>