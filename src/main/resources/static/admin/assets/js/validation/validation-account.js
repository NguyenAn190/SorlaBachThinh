document.addEventListener('DOMContentLoaded', function () {
    const email = document.getElementById("email-edit-account");
    const fullName = document.getElementById("fullname-edit-account");
    const phoneInput = document.getElementById("phone-edit-account");
    const passwordInput = document.getElementById("password-edit-account");
    const dateInput = document.getElementById("date-edit-account");
    const activeAccount = document.getElementById("edit-account-type");

    const errorEmail = document.getElementById('invalid-email');
    const errorFull_name = document.getElementById('invalid-full-name');
    const errorPhone = document.getElementById('invalid-phone');
    const errorPassword = document.getElementById('invalid-password');
    const errorDate = document.getElementById('invalid-date');
    const errorActive = document.getElementById('invalid-active');

    const submittedButton = document.getElementById('submitted-button');

    let customerSubmitted = false;

    function checkInputsAccount() {
        const fields = [email, fullName, phoneInput];
        for (let i = 0; i < fields.length; i++) {
            if (fields[i].value.trim() === '') {
                submittedButton.disabled = true;
                return;
            }
        }
        submittedButton.disabled = customerSubmitted;
    }

    checkInputsAccount();

    email.addEventListener('blur', function () {
        const emailValue = email.value.trim();
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (emailValue === '') {
            email.classList.remove('is-valid');
            email.classList.add('is-invalid');
            errorEmail.textContent = 'Vui lòng nhập địa chỉ email.';
        } else if (!emailRegex.test(emailValue)) {
            email.classList.remove('is-valid');
            email.classList.add('is-invalid');
            errorEmail.textContent = 'Địa chỉ email không hợp lệ.';
        } else {
            email.classList.remove('is-invalid');
            email.classList.add('is-valid');
            errorEmail.textContent = '';
        }
        checkInputsAccount();
    });

    fullName.addEventListener('blur', function () {
        const fullnameValue = fullName.value.trim();
        const fullnameRegex = /^[\p{L}'][\p{L}'\s-]{0,}$/u;

        if (fullnameValue === '') {
            fullName.classList.remove('is-valid');
            fullName.classList.add('is-invalid');
            errorFull_name.textContent = 'Vui lòng nhập họ và tên.';
        } else if (!fullnameRegex.test(fullnameValue)) {
            fullName.classList.remove('is-valid');
            fullName.classList.add('is-invalid');
            errorFull_name.textContent = 'Họ và tên chỉ được nhập chữ cái.';
        } else {
            fullName.classList.remove('is-invalid');
            fullName.classList.add('is-valid');
            errorFull_name.textContent = '';
        }
        checkInputsAccount();
    });

    phoneInput.addEventListener('blur', function () {
        const phoneValue = phoneInput.value.trim();
        const phoneRegex = /^[0-9]{10}$/;

        if (phoneValue === '') {
            phoneInput.classList.remove('is-valid');
            phoneInput.classList.add('is-invalid');
            errorPhone.textContent = 'Vui lòng nhập số điện thoại.';
        } else if (!phoneRegex.test(phoneValue)) {
            phoneInput.classList.remove('is-valid');
            phoneInput.classList.add('is-invalid');
            errorPhone.textContent = 'Số điện thoại không hợp lệ.';
        } else {
            phoneInput.classList.remove('is-invalid');
            phoneInput.classList.add('is-valid');
            errorPhone.textContent = '';
        }
        checkInputsAccount();
    });

    passwordInput.addEventListener('blur', function () {
        const passwordValue = passwordInput.value.trim();

        if (passwordValue === '' || passwordValue.length < 6) {
            passwordInput.classList.remove('is-valid');
            passwordInput.classList.add('is-invalid');
            errorPassword.textContent = 'Mật khẩu phải có ít nhất 6 kí tự.';
        } else {
            passwordInput.classList.remove('is-invalid');
            passwordInput.classList.add('is-valid');
            errorPassword.textContent = '';
        }
        checkInputsAccount();
    });

    dateInput.addEventListener('blur', function () {
        const value = dateInput.value.trim();

        if (value === '') {
            dateInput.classList.remove('is-valid');
            dateInput.classList.add('is-invalid');
            errorDate.textContent = 'Vui lòng nhập ngày đăng ký !';
        } else {
            dateInput.classList.remove('is-invalid');
            dateInput.classList.add('is-valid');
            errorDate.textContent = '';
        }
        checkInputsAccount();
    });

    activeAccount.addEventListener('blur', function () {
        const value = activeAccount.value.trim();

        if (value === '') {
            activeAccount.classList.remove('is-valid');
            activeAccount.classList.add('is-invalid');
            errorActive.textContent = 'Vui lòng chọn trạng thái hoạt động !';
        } else {
            activeAccount.classList.remove('is-invalid');
            activeAccount.classList.add('is-valid');
            errorActive.textContent = '';
        }
        checkInputsAccount();
    });

    // Xử lý sự kiện click nút gửi (kiểm tra đã gửi hay chưa)
    submittedButton.addEventListener('click', function (event) {
        event.preventDefault();

        if (customerSubmitted) {
            return;
        }

        Swal.fire({
            title: 'Cảnh báo ?',
            text: "Bạn có chắc chắn muốn cập nhật không ?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Đồng ý !'
        }).then((result) => {
            if (result.isConfirmed) {
                const form = document.getElementById('edit-form');
                form.submit();
                customerSubmitted = true;
                checkInputsAccount();
            }
        });
    });
});
