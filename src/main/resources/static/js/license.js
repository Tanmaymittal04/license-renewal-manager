// License screens: simple UX helpers
document.addEventListener('DOMContentLoaded', function () {

    // 1) Soft background gradient on bgCanvas
    const canvas = document.getElementById('bgCanvas');
    if (canvas && canvas.getContext) {
        function drawGradient() {
            const ctx = canvas.getContext('2d');
            const w = window.innerWidth;
            const h = window.innerHeight;
            canvas.width  = w;
            canvas.height = h;

            const gradient = ctx.createLinearGradient(0, 0, w, h);
            gradient.addColorStop(0,   '#eef2ff');
            gradient.addColorStop(0.5, '#e0f2fe');
            gradient.addColorStop(1,   '#f4f4f5');

            ctx.fillStyle = gradient;
            ctx.fillRect(0, 0, w, h);
        }

        drawGradient();
        window.addEventListener('resize', drawGradient);
    }

    // 2) Read CSRF token + param name from the <meta> tags in header.jsp
    //    Make sure header.jsp has these two lines inside <head>:
    //    <meta name="_csrf"            content="${_csrf.token}"/>
    //    <meta name="_csrf_param_name" content="${_csrf.parameterName}"/>
    const csrfToken     = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
    const csrfParamName = document.querySelector('meta[name="_csrf_param_name"]')?.getAttribute('content') || '_csrf';

    // 3) Cancel buttons: confirm + POST with CSRF token
    const cancelButtons = document.querySelectorAll('.js-license-cancel');
    cancelButtons.forEach(function (btn) {
        btn.addEventListener('click', function () {
            const id = btn.getAttribute('data-license-id');
            if (!id) return;

            const ok = window.confirm('Cancel this license? This will mark it as cancelled.');
            if (!ok) return;

            // Build the base path up to /ui/licenses
            // Works regardless of current URL (list page, edit page, etc.)
            const contextPath = document.querySelector('meta[name="ctx"]')?.getAttribute('content') || '';
            const action = contextPath + '/ui/licenses/' + id + '/cancel';

            // Dynamically create a form with the CSRF hidden field
            const form = document.createElement('form');
            form.method = 'post';
            form.action = action;

            // ✅ CSRF hidden input — required by Spring Security for all POSTs
            const csrfInput = document.createElement('input');
            csrfInput.type  = 'hidden';
            csrfInput.name  = csrfParamName;
            csrfInput.value = csrfToken || '';
            form.appendChild(csrfInput);

            document.body.appendChild(form);
            form.submit();
        });
    });
});