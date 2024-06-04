
// chuyển trang thì sẽ active ở vị trí trang đó
document.addEventListener('DOMContentLoaded', function () {
    var currentUrl = window.location.pathname;

    var navLinks = document.querySelectorAll('.navbar-nav .nav-link');
    navLinks.forEach(function (link) {
        var linkUrl = link.getAttribute('href');
        if (linkUrl && currentUrl.includes(linkUrl)) {
            link.classList.add('active');
        }
    });
});

// định dạng ngày giờ trên thanh topbar
function updateClock() {
    var now = new Date();
    var dayOfWeek = now.toLocaleString('vi-VN', {weekday: 'long'});
    var time = now.toLocaleString('en-US', {hour: '2-digit', minute: '2-digit', second: '2-digit'});
    var date = now.toLocaleDateString('en-US', {day: '2-digit', month: '2-digit', year: 'numeric'});

    document.getElementById("dayOfWeek").innerHTML = dayOfWeek;
    document.getElementById("currentTime").innerHTML = time;
    document.getElementById("currentDate").innerHTML = date;
}

setInterval(updateClock, 1000);
updateClock();

// backtop
$(document).ready(function () {
    $(window).scroll(function () {
        if ($(this).scrollTop()) {
            $('#backtop').fadeIn();
        } else {
            $('#backtop').fadeOut();
        }
    });
    $("#backtop").click(function () {
        $('html, body').animate({
            scrollTop: 0
        }, 0);
    });
});

// Form Đăng nhập, đăng ký, đổi mật khẩu
const passwordField1 = document.getElementById('password1');
const passwordField2 = document.getElementById('password2');
const passwordField0 = document.getElementById('password0');
const togglePasswordIcon1 = document.getElementById('togglePassword1');
const togglePasswordIcon2 = document.getElementById('togglePassword2');
const togglePasswordIcon0 = document.getElementById('togglePassword0');

togglePasswordIcon1.addEventListener('click', function () {
    if (passwordField1.type === 'password') {
        passwordField1.type = 'text'; // Hiển thị mật khẩu
        togglePasswordIcon1.innerHTML = '<i class="fa-solid fa-eye"></i>'; // Đổi biểu tượng thành mở khóa
    } else {
        passwordField1.type = 'password'; // Ẩn mật khẩu
        togglePasswordIcon1.innerHTML = '<i class="fa-solid fa-eye-slash"></i>'; // Đổi biểu tượng thành khóa
    }
});

togglePasswordIcon2.addEventListener('click', function () {
    if (passwordField2.type === 'password') {
        passwordField2.type = 'text'; // Hiển thị mật khẩu
        togglePasswordIcon2.innerHTML = '<i class="fa-solid fa-eye"></i>'; // Đổi biểu tượng thành mở khóa
    } else {
        passwordField2.type = 'password'; // Ẩn mật khẩu
        togglePasswordIcon2.innerHTML = '<i class="fa-solid fa-eye-slash"></i>'; // Đổi biểu tượng thành khóa
    }
});

togglePasswordIcon0.addEventListener('click', function () {
    if (passwordField0.type === 'password') {
        passwordField0.type = 'text'; // Hiển thị mật khẩu
        togglePasswordIcon0.innerHTML = '<i class="fa-solid fa-eye"></i>'; // Đổi biểu tượng thành mở khóa
    } else {
        passwordField0.type = 'password'; // Ẩn mật khẩu
        togglePasswordIcon0.innerHTML = '<i class="fa-solid fa-eye-slash"></i>'; // Đổi biểu tượng thành khóa
    }
});

