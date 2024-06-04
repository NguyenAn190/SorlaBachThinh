document.addEventListener('DOMContentLoaded', function () {
    const productId = document.getElementById('productIdValue');
    const productName = document.getElementById('productName');
    const power = document.getElementById('power');
    const amount = document.getElementById('amount');
    const priceInput = document.getElementById('priceInput');
    const warranty = document.getElementById('warranty');
    const saleOff = document.getElementById('saleOff');

    const invalidProductName = document.getElementById('invalid-product-name');
    const invalidPower = document.getElementById('invalid-power');
    const invalidAmount = document.getElementById('invalid-amount');
    const invalidPrice = document.getElementById('invalid-price');
    const invalidWarranty = document.getElementById('invalid-warranty');
    const invalidSaleOff = document.getElementById('invalid-saleOff');

    const file01 = document.getElementById('file01');
    const file02 = document.getElementById('file02');
    const file03 = document.getElementById('file03');
    const file04 = document.getElementById('file04');

    const invalidFile = document.getElementById('invalid-file');

    const submittedButton = document.getElementById('submitted-button');

    const loginSubmitted = false;

    function checkInputsLogin() {
        const fields = [productId, productName, power, amount, priceInput, warranty, saleOff];
        for (let i = 0; i < fields.length; i++) {
            if (fields[i].value.trim() === '' || fields[i].classList.contains('is-invalid')) {
                submittedButton.disabled = true;
                return;
            }
        }
        submittedButton.disabled = loginSubmitted;
    }

    checkInputsLogin();

    let productSubmitted = false;

    function checkInputsProduct() {
        const fields = [productId, productName, power, amount, priceInput, warranty, saleOff];
        for (let i = 0; i < fields.length; i++) {
            if (fields[i].value.trim() === '') {
                submittedButton.disabled = true;
                return;
            }
        }
        submittedButton.disabled = productSubmitted;
    }

    checkInputsProduct();

    function checkAllFilesSelected() {
        const files = [file01, file02, file03, file04];
        let allFilesSelected = true;

        for (let i = 0; i < files.length; i++) {
            if (files[i].files.length === 0) {
                allFilesSelected = false;
                break;
            }
        }

        if (allFilesSelected) {
            invalidFile.textContent = '';
        } else {
            invalidFile.textContent = 'Vui lòng thêm ảnh vào tất cả các trường ảnh';
        }
    }

    file01.addEventListener('change', checkAllFilesSelected);
    file02.addEventListener('change', checkAllFilesSelected);
    file03.addEventListener('change', checkAllFilesSelected);
    file04.addEventListener('change', checkAllFilesSelected);

    // Bắt sự kiện blur cho từng trường
    productId.addEventListener('blur', function () {
        const value = productId.value.trim();
        if (value === '') {
            productId.classList.remove('is-valid');
            productId.classList.add('is-invalid');
        } else {
            productId.classList.remove('is-invalid');
            productId.classList.add('is-valid');
        }
        checkInputsProduct();
    });

    productName.addEventListener('blur', function () {
        const value = productName.value.trim();
        if (value === '') {
            productName.classList.remove('is-valid');
            productName.classList.add('is-invalid');
            invalidProductName.textContent = 'Vui lòng nhập tên sản phẩm';
        } else {
            productName.classList.remove('is-invalid');
            productName.classList.add('is-valid');
            invalidProductName.textContent = '';
        }
        checkInputsProduct();
    });

    power.addEventListener('blur', function () {
        const value = power.value.trim();
        const regex = /^\d+(\.\d+)?(W|KW)$/i;

        if (value === '') {
            power.classList.remove('is-valid');
            power.classList.add('is-invalid');
            invalidPower.textContent = 'Vui lòng nhập công suất';
        } else if (!regex.test(value)) {
            power.classList.remove('is-valid');
            power.classList.add('is-invalid');
            invalidPower.textContent = 'Công suất không hợp lệ. Ví dụ: 100W hoặc 2.5KW.';
        } else {
            power.classList.remove('is-invalid');
            power.classList.add('is-valid');
            invalidPower.textContent = '';
        }
        checkInputsProduct();
    });

    amount.addEventListener('blur', function () {
        const value = amount.value.trim();
        if (value === '' || isNaN(value)) {
            amount.classList.remove('is-valid');
            amount.classList.add('is-invalid');
            invalidAmount.textContent = 'Vui lòng nhập số lượng';
        } else if (isNaN(value) || value.includes('.') || value === '' || amount.value.trim() < 0) {
            amount.classList.remove('is-valid');
            amount.classList.add('is-invalid');
            invalidAmount.textContent = 'Số lượng không hợp lệ. Phải là số nguyên dương.';
        } else {
            amount.classList.remove('is-invalid');
            amount.classList.add('is-valid');
            invalidAmount.textContent = '';
        }
        checkInputsProduct();
    });

    priceInput.addEventListener('blur', function () {
        const value = priceInput.value.trim();
        const formattedValue = value.replace(/[^\d]/g, '');

        if (isNaN(formattedValue) || formattedValue === '' || parseInt(formattedValue) < 0) {
            priceInput.classList.remove('is-valid');
            priceInput.classList.add('is-invalid');
            invalidPrice.textContent = 'Giá sản phẩm không hợp lệ. Phải là số lớn hơn 0.';
        } else {
            priceInput.classList.remove('is-invalid');
            priceInput.classList.add('is-valid');
            invalidPrice.textContent = '';
        }
        checkInputsProduct();
    });

    warranty.addEventListener('blur', function () {
        const value = warranty.value.trim();
        const regex = /^\d+(\.\d+)? (Tháng|Năm)$/i;

        if (value === '') {
            warranty.classList.remove('is-valid');
            warranty.classList.add('is-invalid');
            invalidWarranty.textContent = 'Vui lòng nhập thời gian bảo hành';
        } else if (!regex.test(value)) {
            warranty.classList.remove('is-valid');
            warranty.classList.add('is-invalid');
            invalidWarranty.textContent = 'Thời gian bảo hành không hợp lệ. Ví dụ: 12 Tháng hoặc 1 Năm.';
        } else {
            warranty.classList.remove('is-invalid');
            warranty.classList.add('is-valid');
            invalidWarranty.textContent = '';
        }
        checkInputsProduct();
    });

    saleOff.addEventListener('blur', function () {
        const value = saleOff.value.trim();
        const formattedValue = value.replace(/[^\d]/g, '');

        const priceValue = parseFloat(priceInput.value.replace(/[^\d]/g, ''));
        const saleOffValue = parseFloat(saleOff.value.replace(/[^\d]/g, ''));

        if (isNaN(formattedValue) || parseInt(formattedValue) <= 0 || formattedValue === '') {
            saleOff.classList.remove('is-valid');
            saleOff.classList.add('is-invalid');
            invalidSaleOff.textContent = 'Giá giảm không hợp lệ. Phải là số lớn hơn 0.';
        } else if (isNaN(priceValue) || isNaN(saleOffValue) || saleOffValue >= priceValue) {
            saleOff.classList.remove('is-valid');
            saleOff.classList.add('is-invalid');
            invalidSaleOff.textContent = 'Giá giảm không thể lớn hơn giá sản phẩm';
        } else{
            saleOff.classList.remove('is-invalid');
            saleOff.classList.add('is-valid');
            invalidSaleOff.textContent = '';
        }
        checkInputsProduct();
    });

    // Xử lý sự kiện click nút gửi
    submittedButton.addEventListener('click', function (event) {
        if (productSubmitted) {
            event.preventDefault(); // Ngăn form gửi đi nếu đã gửi rồi
        } else {
            const form = document.getElementById('form-product');
            form.submit();
            productSubmitted = true;
            checkInputsProduct();
        }
    });
});
