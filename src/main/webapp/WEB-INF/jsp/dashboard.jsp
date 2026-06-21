<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.yourorg.licenserenewalmanager.license.dto.DashboardMetricsDto" %>
<%@ page import="com.yourorg.licenserenewalmanager.license.dto.UpcomingRenewalViewDto" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<%
    DashboardMetricsDto metrics =
            (DashboardMetricsDto) request.getAttribute("metrics");

    List<?> upcomingRenewalsRaw =
            (List<?>) request.getAttribute("upcomingRenewals");
    @SuppressWarnings("unchecked")
    List<UpcomingRenewalViewDto> upcomingRenewals =
            (List<UpcomingRenewalViewDto>) upcomingRenewalsRaw;

    String ctx = request.getContextPath();
%>

<jsp:include page="/WEB-INF/jsp/layout/header.jsp">
    <jsp:param name="pageTitle" value="Dashboard Overview"/>
    <jsp:param name="pageCss" value="/css/app.css"/>
</jsp:include>

<body>

<div class="lm-shell">
    <jsp:include page="/WEB-INF/jsp/layout/sidebar.jsp" />

    <div class="lm-main">
        <jsp:include page="/WEB-INF/jsp/layout/topbar.jsp">
            <jsp:param name="pageTitle" value="Dashboard Overview"/>
        </jsp:include>

        <main class="lm-content">
            <!-- KPI cards -->
            <div class="row g-3 mb-3">
                <!-- Active Licenses -->
                <div class="col-md-3">
                    <div class="lm-card p-3 h-100 d-flex flex-column justify-content-between">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <div class="small text-muted text-uppercase fw-semibold mb-1">
                                    Active Licenses
                                </div>
                                <div class="fs-3 fw-bold">
                                    <%
                                        if (metrics != null) {
                                            out.print(metrics.getActiveLicenses());
                                        } else {
                                            out.print("0");
                                        }
                                    %>
                                </div>
                            </div>
                            <span class="lm-stat-badge">
                                <i class="bi bi-activity"></i>
                                <%
                                    if (metrics != null && metrics.getUtilizationPercent() != null) {
                                        out.print(metrics.getUtilizationPercent());
                                    } else {
                                        out.print("0");
                                    }
                                %>%
                            </span>
                        </div>
                        <div class="small text-muted mt-2">
                            Across all products and departments.
                        </div>
                    </div>
                </div>

                <!-- Expiring in 30 days -->
                <div class="col-md-3">
                    <div class="lm-card p-3 h-100">
                        <div class="small text-muted text-uppercase fw-semibold mb-1">
                            Expiring in 30 days
                        </div>
                        <div class="fs-3 fw-bold">
                            <%
                                if (metrics != null) {
                                    out.print(metrics.getExpiringSoon());
                                } else {
                                    out.print("0");
                                }
                            %>
                        </div>
                        <div class="small text-muted mt-2">
                            Review renewal pipeline and avoid service disruption.
                        </div>
                    </div>
                </div>

                <!-- Annual Spend -->
                <div class="col-md-3">
                    <div class="lm-card p-3 h-100">
                        <div class="small text-muted text-uppercase fw-semibold mb-1">
                            Spend (INR)
                        </div>
                        <div class="fs-3 fw-bold">
                            <%
                                if (metrics != null && metrics.getAnnualSpend() != null) {
                                    out.print(metrics.getAnnualSpend());
                                } else {
                                    out.print("0");
                                }
                            %>
                        </div>
                        <div class="small text-muted mt-2">
                            Based on latest contracts and renewal terms.
                        </div>
                    </div>
                </div>

                <!-- Utilization -->
                <div class="col-md-3">
                    <div class="lm-card p-3 h-100">
                        <div class="small text-muted text-uppercase fw-semibold mb-1">
                            Utilization
                        </div>
                        <div class="fs-3 fw-bold">
                            <%
                                if (metrics != null && metrics.getUtilizationPercent() != null) {
                                    out.print(metrics.getUtilizationPercent());
                                } else {
                                    out.print("0");
                                }
                            %>%
                        </div>
                        <div class="small text-muted mt-2">
                            Seats in use vs. purchased across all licenses.
                        </div>
                    </div>
                </div>
            </div>

            <!-- Upcoming Renewals (full width) -->
            <div class="row g-3">
                <div class="col-12">
                    <div class="lm-card">
                        <div class="lm-card-header p-3 d-flex justify-content-between align-items-center">
                            <div>
                                <div class="small text-muted text-uppercase fw-semibold mb-1">
                                    Upcoming Renewals
                                </div>
                                <div class="fw-semibold">Next 60 days</div>
                            </div>
                            <a href="<%= ctx %>/ui/licenses?filter=expiring"
                               class="btn btn-sm btn-outline-primary">
                                View all
                            </a>
                        </div>
                        <div class="p-3">
                            <div class="table-responsive">
                                <table class="table lm-table align-middle mb-0">
                                    <thead>
                                    <tr>
                                        <th>Product</th>
                                        <th>Customer</th>
                                        <th>Expiry</th>
                                        <th>Seats</th>
                                        <th>Amount</th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        if (upcomingRenewals != null && !upcomingRenewals.isEmpty()) {
                                            for (UpcomingRenewalViewDto item : upcomingRenewals) {
                                    %>
                                        <tr>
                                            <td>
                                                <div class="fw-semibold">
                                                    <%= item.getProductName() != null ? item.getProductName() : "-" %>
                                                </div>
                                            </td>
                                            <td>
                                                <%
                                                    if (item.getDepartmentName() != null) {
                                                        out.print(item.getDepartmentName());
                                                    } else {
                                                        out.print("-");
                                                    }
                                                %>
                                            </td>
                                            <td>
                                                <%= item.getExpiryDate() != null ? item.getExpiryDate() : "-" %>
                                            </td>
                                            <td>
                                                <%= item.getSeatsPurchased() != null ? item.getSeatsPurchased() : "-" %>
                                            </td>
                                            <td>
                                                <%= item.getCostPerCycle() != null ? item.getCostPerCycle() : "-" %>
                                                <%= item.getCurrency() != null ? item.getCurrency() : "" %>
                                            </td>
                                            <td class="text-end">
                                                <a class="btn btn-sm btn-outline-secondary"
                                                   href="<%= ctx %>/ui/licenses/<%= item.getId() %>/edit">
                                                    Manage
                                                </a>
                                            </td>
                                        </tr>
                                    <%
                                            }
                                        } else {
                                    %>
                                        <tr>
                                            <td colspan="6" class="text-center text-muted py-4">
                                                No renewals in the next 60 days.
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
                </div>
            </div>
        </main>

        <jsp:include page="/WEB-INF/jsp/layout/footer.jsp" />
    </div>
</div>

<!-- Scripts: Bootstrap, global + dashboard JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>

<!-- Global layout JS -->
<script src="<%= ctx %>/js/layout.js"></script>

<!-- Dashboard-only JS -->
<script src="<%= ctx %>/js/dashboard.js"></script>
</body>
</html>