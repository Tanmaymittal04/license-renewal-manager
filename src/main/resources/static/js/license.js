// License screens: simple UX helpers
document.addEventListener('DOMContentLoaded', function () {
    // 1) Soft background gradient on bgCanvas (same idea as dashboard, lightweight)
    const canvas = document.getElementById('bgCanvas');
    if (canvas && canvas.getContext) {
        function drawGradient() {
            const ctx = canvas.getContext('2d');
            const w = window.innerWidth;
            const h = window.innerHeight;
            canvas.width = w;
            canvas.height = h;

            const gradient = ctx.createLinearGradient(0, 0, w, h);
            gradient.addColorStop(0, '#eef2ff');
            gradient.addColorStop(0.5, '#e0f2fe');
            gradient.addColorStop(1, '#f4f4f5');

            ctx.fillStyle = gradient;
            ctx.fillRect(0, 0, w, h);
        }

        drawGradient();
        window.addEventListener('resize', drawGradient);
    }

    // 2) Cancel buttons: centralized confirm + redirect
    const cancelButtons = document.querySelectorAll('.js-license-cancel');
    cancelButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            const id = btn.getAttribute('data-license-id');
            if (!id) return;
            const ok = window.confirm('Cancel this license? This will mark it as cancelled.');
            if (ok) {
                // Your controller expects POST /ui/licenses/{id}/cancel
                const form = document.createElement('form');
                form.method = 'post';
                form.action = `${window.location.origin}${window.location.pathname.replace(/\/$/, '')}/${id}/cancel`;
                document.body.appendChild(form);
                form.submit();
            }
        });
    });
});