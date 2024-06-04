// add mã giảm giá vào trong account
$(document).ready(function () {
    $('#discountForm').click(function (e) {
        e.preventDefault();
        // const urlParams = new URLSearchParams(window.location.search);
        // const tongGiaTien = urlParams.get('tong-gia-tien');
        const discountCode = $('#discountCode').val();
        const discountForm = $('#discountForm');

        $.ajax({
            type: 'POST', url: '/gio-hang/su-dung-giam-gia', data: {
                'ma-giam-gia': discountCode, 'tong-gia-tien': priceTotalPay,
            }, success: function (data) {
                const [status, message] = data.message.split(":");
                const priceBefore = data.price;
                const priceAfter = data.priceApplyCode;

                if (status === 'success') {
                    Swal.fire('Thành công !', message, 'success');

                    formatPriceShowOnViews(priceBefore, priceAfter);

                    const encodedPriceAfter = encodeURIComponent(priceAfter);

                    discountForm.prop('disabled', true); // Vô hiệu hóa

                    document.cookie = "tongGiaTien=" + JSON.stringify(encodedPriceAfter);


                    // Thay đổi URL
                    history.pushState(null, null, `/gio-hang/xac-nhan-thong-tin?tong-gia-tien=${encodedPriceAfter}&ma-giam-gia=${discountCode}`);

                } else if (status === 'error') {
                    Swal.fire('Lỗi !', message, 'error');

                } else if (status === 'warning') {
                    Swal.fire('Cảnh báo !', message, 'warning');
                }
            }, error: function (error) {
                console.error('Lỗi: ', error.text());
            }
        });
    });
});

// get qua controller
function formatPriceShowOnViews(priceBefore, priceAfter) {
    $('#discount').text(new Intl.NumberFormat('vi-VN', {
        style: 'currency', currency: 'VND'
    }).format(priceBefore));

    $('#totalPricePay').text(new Intl.NumberFormat('vi-VN', {
        style: 'currency', currency: 'VND'
    }).format(priceAfter));

    $('#totalPriceAfter').text(new Intl.NumberFormat('vi-VN', {
        style: 'currency', currency: 'VND'
    }).format(priceAfter));
}