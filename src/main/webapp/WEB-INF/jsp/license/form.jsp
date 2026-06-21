<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.yourorg.licenserenewalmanager.license.dto.LicenseRequestDto" %>
<%@ page import="com.yourorg.licenserenewalmanager.license.dto.LicenseResponseDto" %>
<%@ page import="com.yourorg.licenserenewalmanager.license.dto.ProductDto" %>
<%@ page import="com.yourorg.licenserenewalmanager.license.dto.DepartmentDto" %>
<%@ page import="com.yourorg.licenserenewalmanager.license.enums.BillingCycle" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<%
    LicenseRequestDto form =
            (LicenseRequestDto) request.getAttribute("form");
    String mode = (String) request.getAttribute("mode");
    Long licenseId = (Long) request.getAttribute("licenseId");
    LicenseResponseDto license =
            (LicenseResponseDto) request.getAttribute("license");

    List<ProductDto> products =
            (List<ProductDto>) request.getAttribute("products");
    List<DepartmentDto> departments =
            (List<DepartmentDto>) request.getAttribute("departments");
    List<BillingCycle> billingCycles =
            (List<BillingCycle>) request.getAttribute("billingCycles");

    String ctx = request.getContextPath();
    boolean editMode = "edit".equalsIgnoreCase(mode);

    String pageTitle = editMode ? "Edit License" : "New License";

    String formAction = ctx + "/ui/licenses";
    if (editMode && licenseId != null) {
        formAction = ctx + "/ui/licenses/" + licenseId;
    }

    Long selectedProductId = form != null ? form.getProductId() : null;
    Long selectedDeptId = form != null ? form.getDepartmentId() : null;
    BillingCycle selectedCycle = form != null ? form.getBillingCycle() : null;
    Integer seatsPurchased = form != null && form.getSeatsPurchased() != null ? form.getSeatsPurchased() : null;
    Integer seatsUsed = form != null && form.getSeatsUsed() != null ? form.getSeatsUsed() : null;
    String startDateVal = form != null && form.getStartDate() != null ? form.getStartDate().toString() : "";
    String expiryDateVal = form != null && form.getExpiryDate() != null ? form.getExpiryDate().toString() : "";
    String licenseKeyVal = form != null && form.getLicenseKeyOrContractId() != null ? form.getLicenseKeyOrContractId() : "";
    String costPerCycleVal = form != null && form.getCostPerCycle() != null ? form.getCostPerCycle().toString() : "";
    String currencyVal = form != null && form.getCurrency() != null ? form.getCurrency() : "INR";
    boolean autoRenewChecked = form == null
            || form.getAutoRenew() == null
            || Boolean.TRUE.equals(form.getAutoRenew());
    Integer tenureVal = form != null && form.getTenure() != null ? form.getTenure() : null;
%>

<jsp:include page="/WEB-INF/jsp/layout/header.jsp">
    <jsp:param name="pageTitle" value="<%= pageTitle %>"/>
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
            <jsp:param name="pageTitle" value="<%= pageTitle %>"/>
        </jsp:include>

        <main class="lm-content">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <div>
                    <h1 class="h4 mb-1"><%= editMode ? "Edit License" : "Create License" %></h1>
                    <div class="text-muted small">
                        Configure license metadata, seats, and renewal schedule.
                    </div>
                </div>
                <a href="<%= ctx %>/ui/licenses"
                   class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left me-1"></i> Back to list
                </a>
            </div>

            <div class="lm-card p-4">
                <form method="post" action="<%= formAction %>">
                    <!-- ✅ CSRF token — required by Spring Security for all POST requests -->
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <!-- product & department -->
                    <div class="row g-3 mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Product</label>
                            <select name="productId" class="form-select" required>
                                <option value="">Select product</option>
                                <%
                                    if (products != null) {
                                        for (ProductDto product : products) {
                                            Long pid = product.getId();
                                            boolean selected = selectedProductId != null && selectedProductId.equals(pid);
                                %>
                                    <option value="<%= pid %>" <%= selected ? "selected" : "" %>>
                                        <%= product.getName() %> – <%= product.getVendorName() != null ? product.getVendorName() : "-" %>
                                    </option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Department (optional)</label>
                            <select name="departmentId" class="form-select">
                                <option value="">Unassigned</option>
                                <%
                                    if (departments != null) {
                                        for (DepartmentDto dept : departments) {
                                            Long did = dept.getId();
                                            boolean selected = selectedDeptId != null && selectedDeptId.equals(did);
                                %>
                                    <option value="<%= did %>" <%= selected ? "selected" : "" %>>
                                        <%= dept.getName() %>
                                    </option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                    </div>

                    <!-- license key & seats -->
                    <div class="row g-3 mb-3">
                        <div class="col-md-6">
                            <label class="form-label">License Key / Contract ID</label>
                            <input type="text" name="licenseKeyOrContractId"
                                   value="<%= licenseKeyVal %>"
                                   class="form-control" />
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Seats Purchased</label>
                            <input type="number" name="seatsPurchased" min="0"
                                   value="<%= seatsPurchased != null ? seatsPurchased : "" %>"
                                   class="form-control"/>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Seats Used</label>
                            <input type="number" name="seatsUsed" min="0"
                                   value="<%= seatsUsed != null ? seatsUsed : "" %>"
                                   class="form-control"/>
                        </div>
                    </div>

                    <!-- dates, billing & tenure -->
                    <div class="row g-3 mb-3">
                        <div class="col-md-4">
                            <label class="form-label">Start Date</label>
                            <input type="date" name="startDate"
                                   value="<%= startDateVal %>"
                                   class="form-control" required />
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Expiry Date</label>
                            <input type="date" name="expiryDate"
                                   value="<%= expiryDateVal %>"
                                   class="form-control" required />
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Tenure (months)</label>
                            <input type="number" name="tenure" min="1"
                                   value="<%= tenureVal != null ? tenureVal : "" %>"
                                   class="form-control" />
                        </div>
                    </div>

                    <!-- billing cycle -->
                    <div class="row g-3 mb-3">
                        <div class="col-md-4">
                            <label class="form-label">Billing Cycle</label>
                            <select name="billingCycle" class="form-select">
                                <option value="">Select</option>
                                <%
                                    if (billingCycles != null) {
                                        for (BillingCycle cycle : billingCycles) {
                                            boolean selected = selectedCycle != null && selectedCycle == cycle;
                                %>
                                    <option value="<%= cycle.name() %>"
                                            <%= selected ? "selected" : "" %>>
                                        <%= cycle.name() %>
                                    </option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                    </div>

                    <!-- pricing & flags -->
                    <div class="row g-3 mb-4">
                        <div class="col-md-4">
                            <label class="form-label">Cost per Cycle</label>
                            <input type="number" step="0.01" min="0"
                                   name="costPerCycle"
                                   value="<%= costPerCycleVal %>"
                                   class="form-control" />
                        </div>
                        <div class="col-md-2">
                            <label class="form-label">Currency</label>
                            <input type="text" name="currency"
                                   value="<%= currencyVal %>"
                                   class="form-control" />
                        </div>
                        <div class="col-md-3 d-flex align-items-center">
                            <div class="form-check mt-4">
                                <input class="form-check-input" type="checkbox" value="true"
                                       id="autoRenewCheck" name="autoRenew"
                                       <%= autoRenewChecked ? "checked" : "" %>>
                                <label class="form-check-label" for="autoRenewCheck">
                                    Auto-renew
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="d-flex justify-content-end gap-2">
                        <button type="submit" class="btn btn-primary">
                            <%= editMode ? "Save Changes" : "Create License" %>
                        </button>
                    </div>
                </form>
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