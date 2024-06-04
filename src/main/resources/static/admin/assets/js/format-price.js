//format giá tiền khi nhập vào
function formatPrice(input) {
    let rawValue = input.value.replace(/[^0-9]/g, '');

    let price = parseInt(rawValue);

    if (!isNaN(price)) {
        let formattedPrice = price.toLocaleString('vi-VN');
        input.value = formattedPrice;
    }
}

// không cho người dùng nhập chữ vào
document.getElementById('priceInput').addEventListener('keydown', function (event) {
    let keyCode = event.keyCode;
    if (!((keyCode >= 48 && keyCode <= 57) || (keyCode >= 96 && keyCode <= 105) || keyCode == 8 || keyCode == 9 || keyCode == 37 || keyCode == 39 || keyCode == 46)) {
        event.preventDefault();
    }
});