// Sidebar toggle for mobile - used on all pages
document.addEventListener('DOMContentLoaded', function () {
    const sidebar = document.querySelector('.lm-sidebar');
    const toggle = document.getElementById('lm-sidebar-toggle');
    if (sidebar && toggle) {
        toggle.addEventListener('click', () => {
            sidebar.classList.toggle('active');
        });
    }
});