document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM fully loaded and parsed');

    const toggleNav = (event) => {
        event.preventDefault();
        console.log('Hamburger button clicked');
        const navLinks = document.querySelector('.nav-links');
        navLinks.classList.toggle('active');
        console.log('Active class toggled');
        console.log(navLinks.classList);
    };

    const hamburger = document.querySelector('.hamburger');
    hamburger.addEventListener('click', toggleNav);
    hamburger.addEventListener('touchstart', toggleNav);
});