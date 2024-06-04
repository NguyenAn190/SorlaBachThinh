document.addEventListener('DOMContentLoaded', function () {

    const discountCodeId = document.getElementById('discountCodeId');
    const discountPrice = document.getElementById('priceInput');
    const discountValue = document.getElementById('discountValue');
    const createdAt = document.getElementById('createdAt');
    const expirationDate = document.getElementById('expirationDate');

    const invalidDiscountCodeId = document.getElementById('invalid-discountCode-id');
    const invalidDiscountPrice = document.getElementById('invalid-discount-price');
    const invalidDiscountValue = document.getElementById('invalid-discount-value');
    const invalidCreatedAt = document.getElementById('invalid-createdAt');
    const invalidExpirationDate = document.getElementById('invalid-expirationDate');

    const submittedButton = document.getElementById('submitted-button');

    let discountSubmitted = false;

    function checkInputsDiscount() {
        const fields = [discountCodeId, discountPrice, discountValue, createdAt, expirationDate];
        for (let i = 0; i < fields.length; i++) {
            if (fields[i].value.trim() === '') {
                submittedButton.disabled = true;
                return;
            }
        }
        submittedButton.disabled = discountSubmitted;
    }

    checkInputsDiscount();

    discountCodeId.addEventListener('blur', function () {
        const value = discountCodeId.value.trim();

        if (value === '') {
            discountCodeId.classList.remove('is-valid');
            discountCodeId.classList.add('is-invalid');
            invalidDiscountCodeId.textContent = 'Vui lòng nhập mã giảm giá';
        } else {
            discountCodeId.classList.remove('is-invalid');
            discountCodeId.classList.add('is-valid');
            invalidDiscountCodeId.textContent = '';
        }
        checkInputsDiscount();
    });

    discountPrice.addEventListener('blur', function () {
        const value = discountPrice.value.trim();
        const formattedValue = value.replace(/[^\d]/g, '');

        if (isNaN(formattedValue) || formattedValue === '') {
            discountPrice.classList.remove('is-valid');
            discountPrice.classList.add('is-invalid');
            invalidDiscountPrice.textContent = 'Giá giảm không hợp lệ. Phải là số lớn hơn 0.';
        } else {
            discountPrice.classList.remove('is-invalid');
            discountPrice.classList.add('is-valid');
            invalidDiscountPrice.textContent = '';
        }
        checkInputsDiscount();
    });

    discountValue.addEventListener('blur', function () {
        const value = discountValue.value.trim();
        const formattedValue = value.replace(/[^\d]/g, '');

        if (isNaN(formattedValue) || formattedValue === '' || parseInt(formattedValue) <= 0 || discountValue.value.trim() < 0) {
            discountValue.classList.remove('is-valid');
            discountValue.classList.add('is-invalid');
            invalidDiscountValue.textContent = 'Giá trị giảm không hợp lệ. Phải là số lớn hơn 0.';
        } else {
            discountValue.classList.remove('is-invalid');
            discountValue.classList.add('is-valid');
            invalidDiscountValue.textContent = '';
        }
        checkInputsDiscount();
    });

    createdAt.addEventListener('blur', function () {
        const value = createdAt.value.trim();

        if (value === '') {
            createdAt.classList.remove('is-valid');
            createdAt.classList.add('is-invalid');
            invalidCreatedAt.textContent = 'Vui lòng nhập ngày tạo';
        } else {
            createdAt.classList.remove('is-invalid');
            createdAt.classList.add('is-valid');
            invalidCreatedAt.textContent = '';

            // Kiểm tra nếu expirationDate cũng đã có giá trị
            if (expirationDate.value.trim() !== '') {
                checkExpirationDate();
            }
        }
        checkInputsDiscount();
    });

    expirationDate.addEventListener('blur', function () {
        const value = expirationDate.value.trim();

        if (value === '') {
            expirationDate.classList.remove('is-valid');
            expirationDate.classList.add('is-invalid');
            invalidExpirationDate.textContent = 'Vui lòng nhập ngày hết hạn';
        } else {
            expirationDate.classList.remove('is-invalid');
            expirationDate.classList.add('is-valid');
            invalidExpirationDate.textContent = '';

            // Kiểm tra nếu createdAt cũng đã có giá trị
            if (createdAt.value.trim() !== '') {
                checkExpirationDate();
            }
        }
        checkInputsDiscount();
    });

    function checkExpirationDate() {
        const createdAtValue = new Date(createdAt.value.trim());
        const expirationDateValue = new Date(expirationDate.value.trim());

        if (expirationDateValue <= createdAtValue) {
            expirationDate.classList.remove('is-valid');
            expirationDate.classList.add('is-invalid');
            invalidExpirationDate.textContent = 'Ngày hết hạn phải lớn hơn ngày tạo';
        } else {
            expirationDate.classList.remove('is-invalid');
            expirationDate.classList.add('is-valid');
            invalidExpirationDate.textContent = '';
        }
    }

    submittedButton.addEventListener('click', function (event) {
        if (discountSubmitted) {
            event.preventDefault();
        } else {
            const form = document.getElementById('form-discount');
            form.submit();
            discountSubmitted = true;
            checkInputsDiscount();
        }
    });
});