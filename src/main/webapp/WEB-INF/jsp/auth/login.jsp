<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login – LicenseOps</title>

    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">

    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
          rel="stylesheet">

    <!-- Login page CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/login.css">
</head>
<body class="lm-login-body">

<canvas id="bgCanvas"></canvas>

<div class="lm-login-wrapper">
    <div class="lm-login-card">

        <!-- Logo / Brand -->
        <div class="lm-login-brand mb-4 text-center">
            <div class="lm-login-logo mb-2">
                <i class="bi bi-shield-lock-fill"></i>
            </div>
            <div class="fw-bold fs-5">LicenseOps</div>
            <div class="text-muted small">License Renewal Management</div>
        </div>

        <h1 class="h5 fw-semibold text-center mb-1">Sign in to your account</h1>
        <p class="text-muted small text-center mb-4">
            Enter your credentials to continue
        </p>

        <%-- Error message --%>
        <%
            String errorMsg = (String) request.getAttribute("errorMsg");
            String logoutMsg = (String) request.getAttribute("logoutMsg");

            if (errorMsg != null) {
        %>
        <div class="alert alert-danger d-flex align-items-center gap-2 py-2 mb-3" role="alert">
            <i class="bi bi-exclamation-circle-fill"></i>
            <span><%= errorMsg %></span>
        </div>
        <%
            }
            if (logoutMsg != null) {
        %>
        <div class="alert alert-success d-flex align-items-center gap-2 py-2 mb-3" role="alert">
            <i class="bi bi-check-circle-fill"></i>
            <span><%= logoutMsg %></span>
        </div>
        <%
            }
        %>

        <%-- Login form --%>
        <form method="post" action="<%= request.getContextPath() %>/ui/login"
              id="loginForm" novalidate>

            <%-- CSRF token (Spring Security requires this) --%>
            <input type="hidden"
                   name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>

            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="bi bi-envelope"></i>
                    </span>
                    <input type="email"
                           id="email"
                           name="email"
                           class="form-control"
                           placeholder="you@company.com"
                           autocomplete="email"
                           required
                           autofocus />
                </div>
            </div>

            <div class="mb-4">
                <label for="password" class="form-label">Password</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="bi bi-lock"></i>
                    </span>
                    <input type="password"
                           id="password"
                           name="password"
                           class="form-control"
                           placeholder="••••••••"
                           autocomplete="current-password"
                           required />
                    <button type="button"
                            class="input-group-text lm-pw-toggle"
                            id="togglePassword"
                            tabindex="-1"
                            aria-label="Show/hide password">
                        <i class="bi bi-eye" id="toggleIcon"></i>
                    </button>
                </div>
            </div>

            <button type="submit" class="btn btn-primary w-100 lm-login-btn" id="loginBtn">
                <span id="loginBtnText">
                    <i class="bi bi-box-arrow-in-right me-1"></i>Sign In
                </span>
                <span id="loginBtnSpinner" class="d-none">
                    <span class="spinner-border spinner-border-sm me-1" role="status"></span>
                    Signing in…
                </span>
            </button>
        </form>

        <div class="text-center mt-4 small text-muted">
            &copy; <%= java.time.Year.now() %> LicenseOps – Internal Use Only
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>

<!-- Login JS -->
<script src="<%= request.getContextPath() %>/js/login.js"></script>
</body>
</html>