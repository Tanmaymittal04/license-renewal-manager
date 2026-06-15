// Dashboard-only UI polish (no Three.js, no heavy animation)
document.addEventListener('DOMContentLoaded', function () {
    // 1) Soft gradient background on the bgCanvas (2D canvas only)
    const canvas = document.getElementById('bgCanvas');
    if (canvas && canvas.getContext) {
        function drawGradient() {
            const ctx = canvas.getContext('2d');
            const w = window.innerWidth;
            const h = window.innerHeight;
            canvas.width = w;
            canvas.height = h;

            const gradient = ctx.createRadialGradient(
                w * 0.2, h * 0.1, 0,
                w * 0.7, h * 0.9, Math.max(w, h)
            );
            gradient.addColorStop(0, '#e0f2fe'); // light blue
            gradient.addColorStop(0.5, '#eef2ff'); // light indigo
            gradient.addColorStop(1, '#f4f4f5'); // light gray

            ctx.fillStyle = gradient;
            ctx.fillRect(0, 0, w, h);
        }

        drawGradient();
        window.addEventListener('resize', drawGradient);
    }

    // 2) Card hover elevation
    const cards = document.querySelectorAll('.lm-card');
    cards.forEach(card => {
        card.style.transition = 'transform .12s ease, box-shadow .12s ease';
        card.addEventListener('mouseenter', () => {
            card.style.transform = 'translateY(-2px)';
            card.style.boxShadow = '0 20px 45px rgba(15, 23, 42, 0.12)';
        });
        card.addEventListener('mouseleave', () => {
            card.style.transform = 'translateY(0)';
            card.style.boxShadow = '0 18px 45px rgba(15, 23, 42, 0.08)';
        });
    });

    // 3) Optional: Activate Bootstrap tooltips if used
    if (window.bootstrap && bootstrap.Tooltip) {
        document
            .querySelectorAll('[data-bs-toggle="tooltip"]')
            .forEach(el => new bootstrap.Tooltip(el));
    }
});