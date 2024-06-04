// Lấy danh sách các hình ảnh phía dưới
var thumbnailImages = document.querySelectorAll('.col-lg-3 img');

// Lặp qua từng ảnh và thêm sự kiện click
thumbnailImages.forEach(function (thumbnail) {
    thumbnail.addEventListener('click', function () {
        // Lấy đường dẫn của ảnh được click
        var imagePath = this.src;

        // Lấy phần tử overlay và ảnh overlay
        var overlay = document.getElementById('overlay');
        var overlayImage = document.getElementById('overlay-image');

        // Đặt đường dẫn của ảnh vào overlay
        overlayImage.src = imagePath;

        // Hiển thị overlay
        overlay.style.display = 'block';
    });
});

// Đóng overlay khi click vào nó
var overlay = document.getElementById('overlay');
overlay.addEventListener('click', function () {
    this.style.display = 'none';
});
