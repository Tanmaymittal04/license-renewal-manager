document.addEventListener('DOMContentLoaded', function () {

    // ==========================================
    // 1) Three.js Particle Network Background
    // ==========================================
    const canvas = document.getElementById('bgCanvas');
    if (!canvas) return;

    const scene = new THREE.Scene();
    // Very subtle fog so particles fade gently at edges
    scene.fog = new THREE.FogExp2(0x0b1120, 0.015);

    const camera = new THREE.PerspectiveCamera(
        75,
        window.innerWidth / window.innerHeight,
        0.1,
        200
    );
    camera.position.z = 32;

    const renderer = new THREE.WebGLRenderer({
        canvas: canvas,
        antialias: true,
        alpha: false
    });
    renderer.setSize(window.innerWidth, window.innerHeight);
    renderer.setPixelRatio(Math.min(window.devicePixelRatio, 2));
    renderer.setClearColor(0x0b1120, 1);

    // ---- Soft glowing circular texture for particles ----
    function createParticleTexture() {
        const c = document.createElement('canvas');
        c.width = 64;
        c.height = 64;
        const ctx = c.getContext('2d');
        const grad = ctx.createRadialGradient(32, 32, 0, 32, 32, 32);
        grad.addColorStop(0, 'rgba(255, 255, 255, 1)');
        grad.addColorStop(0.2, 'rgba(255, 255, 255, 0.7)');
        grad.addColorStop(0.45, 'rgba(180, 220, 255, 0.25)');
        grad.addColorStop(1, 'rgba(180, 220, 255, 0)');
        ctx.fillStyle = grad;
        ctx.fillRect(0, 0, 64, 64);
        return new THREE.CanvasTexture(c);
    }

    const PARTICLE_COUNT = 140;
    const MAX_DISTANCE = 13;

    // ---- Particles ----
    const pGeometry = new THREE.BufferGeometry();
    const positions = new Float32Array(PARTICLE_COUNT * 3);
    const colors = new Float32Array(PARTICLE_COUNT * 3);
    const velocities = [];

    const cWhite = new THREE.Color(0xffffff);
    const cCyan = new THREE.Color(0x67e8f9);
    const cBlue = new THREE.Color(0x93c5fd);

    for (let i = 0; i < PARTICLE_COUNT; i++) {
        positions[i * 3] = (Math.random() - 0.5) * 65;
        positions[i * 3 + 1] = (Math.random() - 0.5) * 65;
        positions[i * 3 + 2] = (Math.random() - 0.5) * 45;

        const roll = Math.random();
        const c = roll < 0.35 ? cWhite : (roll < 0.7 ? cCyan : cBlue);
        colors[i * 3] = c.r;
        colors[i * 3 + 1] = c.g;
        colors[i * 3 + 2] = c.b;

        velocities.push({
            x: (Math.random() - 0.5) * 0.007,
            y: (Math.random() - 0.5) * 0.007,
            z: (Math.random() - 0.5) * 0.007
        });
    }

    pGeometry.setAttribute('position', new THREE.BufferAttribute(positions, 3));
    pGeometry.setAttribute('color', new THREE.BufferAttribute(colors, 3));

    const pMaterial = new THREE.PointsMaterial({
        size: 0.65,
        map: createParticleTexture(),
        transparent: true,
        vertexColors: true,
        blending: THREE.AdditiveBlending,
        depthWrite: false,
        sizeAttenuation: true
    });

    const particles = new THREE.Points(pGeometry, pMaterial);
    scene.add(particles);

    // ---- Connection lines ----
    const lineGeometry = new THREE.BufferGeometry();
    const linePositions = new Float32Array(PARTICLE_COUNT * PARTICLE_COUNT * 6);
    lineGeometry.setAttribute('position', new THREE.BufferAttribute(linePositions, 3));
    const lineMaterial = new THREE.LineBasicMaterial({
        color: 0x93c5fd,
        transparent: true,
        opacity: 0.15,
        blending: THREE.AdditiveBlending,
        depthWrite: false
    });
    const lines = new THREE.LineSegments(lineGeometry, lineMaterial);
    scene.add(lines);

    // ---- Mouse interaction ----
    let mouseX = 0, mouseY = 0;
    let targetCamX = 0, targetCamY = 0;

    document.addEventListener('mousemove', (e) => {
        mouseX = (e.clientX / window.innerWidth) * 2 - 1;
        mouseY = -(e.clientY / window.innerHeight) * 2 + 1;
    });

    // ---- Animation loop ----
    const clock = new THREE.Clock();
    const posArray = pGeometry.attributes.position.array;

    function animate() {
        requestAnimationFrame(animate);
        const elapsed = clock.getElapsedTime();

        // Gentle whole-system rotation
        particles.rotation.y = elapsed * 0.035;
        particles.rotation.x = Math.sin(elapsed * 0.018) * 0.08;

        let lineIdx = 0;

        for (let i = 0; i < PARTICLE_COUNT; i++) {
            const ix = i * 3;
            const iy = i * 3 + 1;
            const iz = i * 3 + 2;

            // Drift with organic noise
            posArray[ix] += velocities[i].x + Math.sin(elapsed * 0.25 + i * 0.5) * 0.0015;
            posArray[iy] += velocities[i].y + Math.cos(elapsed * 0.18 + i * 0.3) * 0.0015;
            posArray[iz] += velocities[i].z;

            // Wrap around
            if (posArray[ix] > 32) posArray[ix] = -32;
            if (posArray[ix] < -32) posArray[ix] = 32;
            if (posArray[iy] > 32) posArray[iy] = -32;
            if (posArray[iy] < -32) posArray[iy] = 32;
            if (posArray[iz] > 22) posArray[iz] = -22;
            if (posArray[iz] < -22) posArray[iz] = 22;

            // Connections
            for (let j = i + 1; j < PARTICLE_COUNT; j++) {
                const jx = j * 3;
                const jy = j * 3 + 1;
                const jz = j * 3 + 2;

                const dx = posArray[ix] - posArray[jx];
                const dy = posArray[iy] - posArray[jy];
                const dz = posArray[iz] - posArray[jz];
                const distSq = dx * dx + dy * dy + dz * dz;

                if (distSq < MAX_DISTANCE * MAX_DISTANCE) {
                    linePositions[lineIdx++] = posArray[ix];
                    linePositions[lineIdx++] = posArray[iy];
                    linePositions[lineIdx++] = posArray[iz];
                    linePositions[lineIdx++] = posArray[jx];
                    linePositions[lineIdx++] = posArray[jy];
                    linePositions[lineIdx++] = posArray[jz];
                }
            }
        }

        pGeometry.attributes.position.needsUpdate = true;
        lineGeometry.setDrawRange(0, lineIdx / 3);
        lineGeometry.attributes.position.needsUpdate = true;

        // Camera parallax
        targetCamX = mouseX * 2.5;
        targetCamY = mouseY * 2.5;
        camera.position.x += (targetCamX - camera.position.x) * 0.03;
        camera.position.y += (targetCamY - camera.position.y) * 0.03;
        camera.lookAt(0, 0, 0);

        renderer.render(scene, camera);
    }
    animate();

    // ---- Resize handler ----
    window.addEventListener('resize', () => {
        camera.aspect = window.innerWidth / window.innerHeight;
        camera.updateProjectionMatrix();
        renderer.setSize(window.innerWidth, window.innerHeight);
    });

    // ==========================================
    // 2) Password show/hide toggle
    // ==========================================
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

    // ==========================================
    // 3) Loading spinner on submit
    // ==========================================
    const loginForm = document.getElementById('loginForm');
    const loginBtnText = document.getElementById('loginBtnText');
    const loginBtnSpinner = document.getElementById('loginBtnSpinner');
    const loginBtn = document.getElementById('loginBtn');

    if (loginForm) {
        loginForm.addEventListener('submit', function (e) {
            const email = document.getElementById('email').value.trim();
            const password = document.getElementById('password').value.trim();

            if (!email || !password) {
                e.preventDefault();
                return;
            }

            loginBtnText.classList.add('d-none');
            loginBtnSpinner.classList.remove('d-none');
            loginBtn.disabled = true;
        });
    }
});