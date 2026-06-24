<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sign In – LicenseOps</title>

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

        <!-- Brand -->
        <div class="lm-login-brand">
            <div class="lm-login-logo">
                <i class="bi bi-shield-lock-fill"></i>
            </div>
            <div class="lm-login-brand-title">LicenseOps</div>
            <div class="lm-login-brand-sub">License Renewal Management</div>
        </div>

        <div class="lm-login-divider"></div>

        <h1 class="lm-login-heading">Welcome back</h1>
        <p class="lm-login-subhead">Sign in to your workspace to continue</p>

        <%-- Error / Logout messages --%>
        <%
            String errorMsg = (String) request.getAttribute("errorMsg");
            String logoutMsg = (String) request.getAttribute("logoutMsg");

            if (errorMsg != null) {
        %>
        <div class="lm-alert lm-alert-error" role="alert">
            <i class="bi bi-exclamation-circle-fill"></i>
            <span><%= errorMsg %></span>
        </div>
        <%
            }
            if (logoutMsg != null) {
        %>
        <div class="lm-alert lm-alert-success" role="alert">
            <i class="bi bi-check-circle-fill"></i>
            <span><%= logoutMsg %></span>
        </div>
        <%
            }
        %>

        <%-- Login form --%>
        <form method="post" action="<%= request.getContextPath() %>/ui/login"
              id="loginForm" novalidate>

            <%-- CSRF token --%>
            <input type="hidden"
                   name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>

            <div class="lm-form-group">
                <label for="email" class="lm-form-label">Email address</label>
                <div class="lm-input-wrap">
                    <span class="lm-input-icon">
                        <i class="bi bi-envelope"></i>
                    </span>
                    <input type="email"
                           id="email"
                           name="email"
                           class="lm-form-control"
                           placeholder="name@company.com"
                           autocomplete="email"
                           required
                           autofocus />
                </div>
            </div>

            <div class="lm-form-group">
                <label for="password" class="lm-form-label">Password</label>
                <div class="lm-input-wrap">
                    <span class="lm-input-icon">
                        <i class="bi bi-lock"></i>
                    </span>
                    <input type="password"
                           id="password"
                           name="password"
                           class="lm-form-control"
                           placeholder="Enter your password"
                           autocomplete="current-password"
                           required />
                    <button type="button"
                            class="lm-input-toggle"
                            id="togglePassword"
                            tabindex="-1"
                            aria-label="Show/hide password">
                        <i class="bi bi-eye" id="toggleIcon"></i>
                    </button>
                </div>
            </div>

            <button type="submit" class="lm-login-btn" id="loginBtn">
                <span id="loginBtnText">
                    <i class="bi bi-box-arrow-in-right me-2"></i>Sign In
                </span>
                <span id="loginBtnSpinner" class="d-none">
                    <span class="spinner-border spinner-border-sm me-2" role="status"></span>
                    Authenticating…
                </span>
            </button>
        </form>

        <div class="lm-login-footer">
            &copy; <%= java.time.Year.now() %> LicenseOps. Internal Use Only.
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>

<!-- Three.js -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/three.js/r128/three.min.js"></script>

<!-- Login JS -->
<script src="<%= request.getContextPath() %>/js/login.js"></script>
</body>
</html>