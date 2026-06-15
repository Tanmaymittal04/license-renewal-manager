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

        <!-- User dropdown -->
        <div class="dropdown">
            <button class="btn btn-light btn-sm d-flex align-items-center gap-2"
                    type="button" data-bs-toggle="dropdown" aria-expanded="false">
                <span class="rounded-circle bg-primary text-white d-inline-flex align-items-center justify-content-center fw-semibold"
                      style="width: 32px; height: 32px; font-size: 0.75rem;">
                    <%= userInitials %>
                </span>
                <span class="d-none d-md-inline small"><%= userDisplay %></span>
                <i class="bi bi-chevron-down small"></i>
            </button>

            <ul class="dropdown-menu dropdown-menu-end shadow-sm">
                <li>
                    <div class="px-3 py-2 border-bottom">
                        <div class="small fw-semibold text-truncate" style="max-width: 180px;">
                            <%= userDisplay %>
                        </div>
                        <div class="text-muted" style="font-size: 0.75rem;">Signed in</div>
                    </div>
                </li>
                <li><a class="dropdown-item" href="#">
                    <i class="bi bi-person me-2"></i>Profile
                </a></li>
                <li><a class="dropdown-item" href="#">
                    <i class="bi bi-gear me-2"></i>Settings
                </a></li>
                <li><hr class="dropdown-divider"/></li>
                <li>
                    <!-- Logout via POST form with CSRF token (required by Spring Security) -->
                    <form method="post"
                          action="<%= ctx %>/ui/logout"
                          id="logoutForm"
                          style="margin: 0; padding: 0;">
                        <input type="hidden"
                               name="${_csrf.parameterName}"
                               value="${_csrf.token}"/>
                        <button type="submit"
                                class="dropdown-item text-danger w-100 text-start">
                            <i class="bi bi-box-arrow-right me-2"></i>Logout
                        </button>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>