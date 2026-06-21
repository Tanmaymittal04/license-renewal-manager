<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.yourorg.licenserenewalmanager.license.dto.LicenseResponseDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<!DOCTYPE html>
<html lang="en">
<%
    Object raw = request.getAttribute("licenses");
    List<LicenseResponseDto> licenses = raw != null
            ? (List<LicenseResponseDto>) raw
            : Collections.emptyList();

    String ctx = request.getContextPath();
    String q = (String) request.getAttribute("q");
%>

<jsp:include page="/WEB-INF/jsp/layout/header.jsp">
    <jsp:param name="pageTitle" value="Licenses"/>
    <jsp:param name="pageCss" value="/css/license.css"/>
</jsp:include>

<body>
<canvas id="bgCanvas"></canvas>

<div class="lm-shell">
    <%
        request.setAttribute("activeMenu", "licenses");
    %>
    <jsp:include page="/WEB-INF/jsp/layout/sidebar.jsp" />

    <div class="lm-main">
        <jsp:include page="/WEB-INF/jsp/layout/topbar.jsp">
            <jsp:param name="pageTitle" value="Licenses"/>
        </jsp:include>

        <main class="lm-content">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <div>
                    <h1 class="h4 mb-1">Licenses</h1>
                    <div class="text-muted small">
                        Manage license keys, contracts, and seat utilization.
                    </div>
                </div>
                <div class="d-flex gap-2">
                    <a href="<%= ctx %>/ui/licenses/export"
                       class="btn btn-success">
                        <i class="bi bi-download me-1"></i> Export Excel
                    </a>
                    <a href="<%= ctx %>/ui/licenses/new"
                       class="btn btn-primary">
                        <i class="bi bi-plus-lg me-1"></i> New License
                    </a>
                </div>
            </div>

            <div class="lm-card">
                <div class="lm-card-header p-3 d-flex justify-content-between align-items-center">
                    <div class="d-flex align-items-center gap-2">
                        <span class="small text-muted text-uppercase fw-semibold">All Licenses</span>
                        <span class="badge bg-light text-muted">
                            <%= licenses.size() %> total
                        </span>
                    </div>
                    <form class="d-flex gap-2" method="get" action="<%= ctx %>/ui/licenses">
                        <input type="text" name="q"
                               value="<%= q != null ? q : "" %>"
                               class="form-control form-control-sm"
                               placeholder="Search product, vendor, license key..." />
                        <button class="btn btn-outline-secondary btn-sm" type="submit">
                            <i class="bi bi-search"></i>
                        </button>
                    </form>
                </div>
                <div class="p-3">
                    <div class="table-responsive">
                        <table class="table lm-table align-middle">
                            <thead>
                            <tr>
                                <th>Product</th>
                                <th>Customer</th>
                                <th>License Key / Contract</th>
                                <th>Seats</th>
                                <th>Validity</th>
                                <th>Tenure</th>
                                <th>Billing</th>
                                <th>Status</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                if (!licenses.isEmpty()) {
                                    for (LicenseResponseDto license : licenses) {
                            %>
                                <tr class="license-row">
                                    <td>
                                        <div class="fw-semibold">
                                            <%= license.getProductName() != null ? license.getProductName() : "-" %>
                                        </div>
                                        <div class="small text-muted">
                                            <%= license.getVendorName() != null ? license.getVendorName() : "-" %>
                                        </div>
                                    </td>
                                    <td>
                                        <%
                                            String dept = license.getDepartmentName();
                                            out.print(dept != null ? dept : "-");
                                        %>
                                    </td>
                                    <td>
                                        <span class="small font-monospace license-key-pill">
                                            <%= license.getLicenseKeyOrContractId() != null ? license.getLicenseKeyOrContractId() : "-" %>
                                        </span>
                                    </td>
                                    <td>
                                        <div class="fw-semibold">
                                            <%= license.getSeatsUsed() != null ? license.getSeatsUsed() : "-" %> /
                                            <%= license.getSeatsPurchased() != null ? license.getSeatsPurchased() : "-" %>
                                        </div>
                                        <div class="small text-muted">Used / Purchased</div>
                                    </td>
                                    <td>
                                        <div class="small">
                                            <%= license.getStartDate() != null ? license.getStartDate() : "-" %> →
                                            <%= license.getExpiryDate() != null ? license.getExpiryDate() : "-" %>
                                        </div>
                                    </td>
                                    <td>
                                        <%
                                            Integer tenure = license.getTenure();
                                            out.print(tenure != null ? tenure + " mo" : "-");
                                        %>
                                    </td>
                                    <td>
                                        <span class="badge bg-light text-muted border">
                                            <%= license.getBillingCycle() != null ? license.getBillingCycle().name() : "-" %>
                                        </span>
                                    </td>
                                    <td>
                                        <%
                                            String status = license.getStatus() != null
                                                    ? license.getStatus().name()
                                                    : "UNKNOWN";
                                            String badgeClass;
                                            String label;
                                            if ("ACTIVE".equals(status)) {
                                                badgeClass = "badge bg-success-subtle text-success";
                                                label = "Active";
                                            } else if ("EXPIRED".equals(status)) {
                                                badgeClass = "badge bg-danger-subtle text-danger";
                                                label = "Expired";
                                            } else if ("GRACE_PERIOD".equals(status)) {
                                                badgeClass = "badge bg-warning-subtle text-warning";
                                                label = "Grace Period";
                                            } else {
                                                badgeClass = "badge bg-secondary-subtle text-secondary";
                                                label = status;
                                            }
                                        %>
                                        <span class="<%= badgeClass %>">
                                            <%= label %>
                                        </span>
                                    </td>
                                    <td class="text-end">
                                        <div class="btn-group btn-group-sm">
                                            <a class="btn btn-outline-secondary"
                                               href="<%= ctx %>/ui/licenses/<%= license.getId() %>/edit">
                                                Edit
                                            </a>
                                            <button type="button"
                                                    class="btn btn-outline-danger js-license-cancel"
                                                    data-license-id="<%= license.getId() %>">
                                                Cancel
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            <%
                                    }
                                } else {
                            %>
                                <tr>
                                    <td colspan="9" class="text-center text-muted py-4">
                                        No licenses found.
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

<!-- Scripts: Bootstrap, global + license JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>

<script src="<%= ctx %>/js/layout.js"></script>
<script src="<%= ctx %>/js/license.js"></script>
</body>
</html>