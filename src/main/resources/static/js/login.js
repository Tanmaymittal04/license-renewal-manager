document.addEventListener('DOMContentLoaded', function () {

    // 1) Soft radial gradient on bgCanvas
    const canvas = document.getElementById('bgCanvas');
    if (canvas && canvas.getContext) {
        function drawGradient() {
            const ctx = canvas.getContext('2d');
            const w = window.innerWidth;
            const h = window.innerHeight;
            canvas.width = w;
            canvas.height = h;

            const g = ctx.createRadialGradient(
                w * 0.25, h * 0.2, 0,
                w * 0.75, h * 0.8, Math.max(w, h) * 0.9
            );
            g.addColorStop(0, '#dbeafe');   // blue-100
            g.addColorStop(0.4, '#eef2ff'); // indigo-50
            g.addColorStop(1, '#f4f5fb');   // off-white

            ctx.fillStyle = g;
            ctx.fillRect(0, 0, w, h);
        }

        drawGradient();
        window.addEventListener('resize', drawGradient);
    }

    // 2) Password show/hide toggle
    const toggleBtn = document.getElementById('togglePassword');
    const passwordInput = document.getElementById('password');
    const toggleIcon = document.getElementById('toggleIcon');

    if (toggleBtn && passwordInput) {
        toggleBtn.addEventListener('click', function () {
            const isPassword = passwordInput.type === 'password';
            passwordInput.type = isPassword ? 'text' : 'password';
            toggleIcon.className = isPassword ? 'bi bi-eye-slash' : 'bi bi-eye';
        });
    }

    // 3) Show loading spinner on submit
    const loginForm = document.getElementById('loginForm');
    const loginBtnText = document.getElementById('loginBtnText');
    const loginBtnSpinner = document.getElementById('loginBtnSpinner');
    const loginBtn = document.getElementById('loginBtn');

    if (loginForm) {
        loginForm.addEventListener('submit', function (e) {
            const email = document.getElementById('email').value.trim();
            const password = document.getElementById('password').value.trim();

            // Basic client-side validation
            if (!email || !password) {
                e.preventDefault();
                return;
            }

            // Show spinner
            loginBtnText.classList.add('d-none');
            loginBtnSpinner.classList.remove('d-none');
            loginBtn.disabled = true;
        });
    }
});