document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM fully loaded and parsed');
    document.querySelector('.hamburger').addEventListener('click', () => {
        console.log('Hamburger button clicked');
        const navLinks = document.querySelector('.nav-links');
        navLinks.classList.toggle('active');
        console.log('Active class toggled');
        console.log(navLinks.classList);
    });
});