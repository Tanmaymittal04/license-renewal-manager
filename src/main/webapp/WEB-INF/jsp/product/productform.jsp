<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.yourorg.licenserenewalmanager.license.dto.ProductDto" %>
<!DOCTYPE html>
<html lang="en">
<%
    ProductDto form = (ProductDto) request.getAttribute("form");
    String mode = (String) request.getAttribute("mode");
    Long productId = (Long) request.getAttribute("productId");

    String ctx = request.getContextPath();
    boolean editMode = "edit".equalsIgnoreCase(mode);

    String pageTitle = editMode ? "Edit Product" : "New Product";

    String formAction = ctx + "/ui/products";
    if (editMode && productId != null) {
        formAction = ctx + "/ui/products/" + productId;
    }

    String nameVal = form != null && form.getName() != null ? form.getName() : "";
    String descVal = form != null && form.getDescription() != null ? form.getDescription() : "";
%>

<jsp:include page="/WEB-INF/jsp/layout/header.jsp">
    <jsp:param name="pageTitle" value="<%= pageTitle %>"/>
    <jsp:param name="pageCss" value="/css/product.css"/>
</jsp:include>

<body>
<canvas id="bgCanvas"></canvas>

<div class="lm-shell">
    <%
        request.setAttribute("activeMenu", "products");
    %>
    <jsp:include page="/WEB-INF/jsp/layout/sidebar.jsp" />

    <div class="lm-main">
        <jsp:include page="/WEB-INF/jsp/layout/topbar.jsp">
            <jsp:param name="pageTitle" value="<%= pageTitle %>"/>
        </jsp:include>

        <main class="lm-content">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <div>
                    <h1 class="h4 mb-1"><%= editMode ? "Edit Product" : "Create Product" %></h1>
                    <div class="text-muted small">
                        Configure product metadata and vendor details.
                    </div>
                </div>
                <a href="<%= ctx %>/ui/products"
                   class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left me-1"></i> Back to list
                </a>
            </div>

            <div class="lm-card p-4">
                <form method="post" action="<%= formAction %>">

                    <!-- ✅ CSRF token — required by Spring Security for all POST requests -->
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <div class="mb-3">
                        <label class="form-label">Name</label>
                        <input type="text" name="name"
                               value="<%= nameVal %>"
                               class="form-control" required />
                    </div>

                    <div class="mb-4">
                        <label class="form-label">Description</label>
                        <textarea name="description" rows="3"
                                  class="form-control"><%= descVal %></textarea>
                    </div>

                    <div class="d-flex justify-content-end gap-2">
                        <button type="submit" class="btn btn-primary">
                            <%= editMode ? "Save Changes" : "Create Product" %>
                        </button>
                    </div>
                </form>
            </div>
        </main>

        <jsp:include page="/WEB-INF/jsp/layout/footer.jsp" />
    </div>
</div>

<!-- Scripts: Bootstrap, global + product JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>

<script src="<%= ctx %>/js/layout.js"></script>
<script src="<%= ctx %>/js/product.js"></script>
</body>
</html>