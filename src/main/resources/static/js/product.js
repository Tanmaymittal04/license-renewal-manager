// Product screens: light background + minor UX polish
document.addEventListener('DOMContentLoaded', function () {
    // 1) Soft background gradient
    const canvas = document.getElementById('bgCanvas');
    if (canvas && canvas.getContext) {
        function drawGradient() {
            const ctx = canvas.getContext('2d');
            const w = window.innerWidth;
            const h = window.innerHeight;
            canvas.width = w;
            canvas.height = h;

            const gradient = ctx.createRadialGradient(
                w * 0.15, h * 0.1, 0,
                w * 0.8, h * 0.9, Math.max(w, h)
            );
            gradient.addColorStop(0, '#dcfce7'); // light green
            gradient.addColorStop(0.5, '#e0f2fe'); // light blue
            gradient.addColorStop(1, '#f4f4f5'); // light gray

            ctx.fillStyle = gradient;
            ctx.fillRect(0, 0, w, h);
        }

        drawGradient();
        window.addEventListener('resize', drawGradient);
    }

    // 2) Subtle hover elevation on product cards (same pattern as dashboard/license if needed)
    document.querySelectorAll('.lm-card').forEach(card => {
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
});