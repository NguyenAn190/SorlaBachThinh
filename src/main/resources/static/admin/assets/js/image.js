function previewImage(event, imagePreviewId, dropTitleId, dropImageProductId) {
    const input = event.target;
    const imagePreview = document.getElementById(imagePreviewId);
    const dropTitle = document.getElementById(dropTitleId);
    const dropImageProduct = document.getElementById(dropImageProductId);

    if (input.files && input.files[0]) {
        const reader = new FileReader();

        reader.onload = function (e) {
            imagePreview.src = e.target.result;
            imagePreview.style.display = 'block'; // Show the image preview
            dropImageProduct.style.display = 'none'
            const fileName = input.files[0].name;
            if (fileName.length > 20) {
                dropTitle.textContent = fileName.substring(0, 20) + '...';
            } else {
                dropTitle.textContent = fileName;
            }
            dropTitle.style.fontSize = '15px';
        };

        reader.readAsDataURL(input.files[0]);
    } else {
        imagePreview.style.display = 'none';
        dropImageProduct.style.display = 'block';
    }
}

function redirectToProduct() {
    var productId = document.getElementById("productSelectImage").value;
    if (productId != null) {
        window.location.href = "/admin/them-hinh-anh/xem-hinh-anh/" + productId;
    }

}
