<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String pageTitle = request.getParameter("pageTitle");
    if (pageTitle == null || pageTitle.isEmpty()) {
        pageTitle = "Dashboard";
    }
    String ctx = request.getContextPath();

    // Logged-in user display name from Spring Security
    String userDisplay = "User";
    String userInitials = "U";
    java.security.Principal principal = request.getUserPrincipal();
    if (principal != null) {
        // principal.getName() returns the email (username used in UserDetailsService)
        userDisplay = principal.getName();
        // Use first letter of email as initials fallback
        userInitials = userDisplay.substring(0, 1).toUpperCase();
    }
%>
<nav class="lm-topbar d-flex align-items-center px-3 px-lg-4">
    <div class="d-flex align-items-center gap-2">
        <button class="btn btn-outline-secondary d-lg-none" id="lm-sidebar-toggle">
            <i class="bi bi-list"></i>
        </button>
        <div class="d-none d-md-flex flex-column">
            <div class="small text-muted">License Renewal Management</div>
            <div class="fw-semibold">
                <%= pageTitle %>
            </div>
        </div>
    </div>

    <div class="ms-auto d-flex align-items-center gap-3">
        <!-- Search bar -->
        <div class="input-group input-group-sm d-none d-md-flex" style="max-width: 260px;">
            <span class="input-group-text border-0 bg-transparent">
                <i class="bi bi-search"></i>
            </span>
            <input type="text" class="form-control border-0"
                   placeholder="Search licenses, products..." />
        </div>

        <!-- User area: initials badge + logout button -->
        <div class="d-flex align-items-center gap-2">
            <!-- Initials badge (static, not a dropdown toggle) -->
            <span class="rounded-circle bg-primary text-white d-inline-flex align-items-center justify-content-center fw-semibold"
                  style="width: 32px; height: 32px; font-size: 0.75rem;">
                <%= userInitials %>
            </span>
            <!-- Logout form -->
            <form method="post"
                  action="<%= ctx %>/ui/logout"
                  id="logoutForm"
                  style="margin: 0; padding: 0;">
                <input type="hidden"
                       name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>
                <button type="submit"
                        class="btn btn-sm btn-outline-danger d-flex align-items-center gap-1">
                    <i class="bi bi-box-arrow-right"></i>
                    <span class="d-none d-md-inline">Logout</span>
                </button>
            </form>
        </div>
    </div>
</nav>