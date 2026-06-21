<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.yourorg.licenserenewalmanager.license.dto.ProductDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<!DOCTYPE html>
<html lang="en">
<%
    Object raw = request.getAttribute("products");
    List<ProductDto> products = raw != null
            ? (List<ProductDto>) raw
            : Collections.emptyList();

    String ctx = request.getContextPath();
%>

<jsp:include page="/WEB-INF/jsp/layout/header.jsp">
    <jsp:param name="pageTitle" value="Products"/>
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
            <jsp:param name="pageTitle" value="Products"/>
        </jsp:include>

        <main class="lm-content">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <div>
                    <h1 class="h4 mb-1">Products</h1>
                    <div class="text-muted small">
                        Define all licenseable products and vendors.
                    </div>
                </div>
                <div>
                    <a href="<%= ctx %>/ui/products/new"
                       class="btn btn-primary">
                        <i class="bi bi-plus-lg me-1"></i> New Product
                    </a>
                </div>
            </div>

            <div class="lm-card">
                <div class="lm-card-header p-3 d-flex justify-content-between align-items-center">
                    <div class="d-flex align-items-center gap-2">
                        <span class="small text-muted text-uppercase fw-semibold">All Products</span>
                        <span class="badge bg-light text-muted">
                            <%= products.size() %> total
                        </span>
                    </div>
                </div>
                <div class="p-3">
                    <div class="table-responsive">
                        <table class="table lm-table align-middle">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Description</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                if (!products.isEmpty()) {
                                    for (ProductDto p : products) {
                            %>
                                <tr class="product-row">
                                    <td class="fw-semibold"><%= p.getName() %></td>
                                    <td class="small text-muted product-desc-cell">
                                        <%= p.getDescription() != null ? p.getDescription() : "" %>
                                    </td>
                                    <td class="text-end">
                                        <a class="btn btn-sm btn-outline-secondary"
                                           href="<%= ctx %>/ui/products/<%= p.getId() %>/edit">
                                            Edit
                                        </a>
                                    </td>
                                </tr>
                            <%
                                    }
                                } else {
                            %>
                                <tr>
                                    <td colspan="3" class="text-center text-muted py-4">
                                        No products defined yet.
                                    </td>
                                </tr>
                            <%
                                }
                            %>
                            </tbody>
                        </table>
                    </div>
                </div>
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