class EventFormValidator {
    constructor() {
        this.form = document.querySelector('form');
        this.initializeValidation();
    }

    initializeValidation() {

        ['evtStart', 'evtDeadLine', 'evtDate'].forEach(fieldName => {
            document.querySelector(`[name="${fieldName}"]`).addEventListener('change',
                () => this.validateTimeSequence());
        });


        const attendInput = document.querySelector('[name="evtAttend"]');
        const maxInput = document.querySelector('[name="evtMax"]');
        attendInput.addEventListener('input', () => this.validateAttendance());
        maxInput.addEventListener('input', () => this.validateAttendance());


        this.form.addEventListener('submit', (e) => this.handleSubmit(e));


        this.validateAttendance();
    }

    validateTimeSequence() {
        const evtStart = new Date(document.querySelector('[name="evtStart"]').value);
        const evtDeadLine = new Date(document.querySelector('[name="evtDeadLine"]').value);
        const evtDate = new Date(document.querySelector('[name="evtDate"]').value);

        this.clearErrors();
        let isValid = true;

        // 驗證時間順序
        if (evtStart > evtDeadLine) {
            this.showError('evtStart', '開放報名時間不能大於報名截止時間');
            isValid = false;
        }

        if (evtDeadLine > evtDate) {
            this.showError('evtDeadLine', '報名截止時間不能大於活動日期');
            isValid = false;
        }

        // 驗證活動時間範圍8-11
        const eventHour = evtDate.getHours();
        const eventMinutes = evtDate.getMinutes();

        if (eventHour < 8 || (eventHour === 23 && eventMinutes > 0) || eventHour > 23) {
            this.showError('evtDate', '活動時間必須在早上 8:00 到晚上 11:00 之間');
            isValid = false;
        }

        return isValid;
    }

    validateAttendance() {
        const attend = parseInt(document.querySelector('[name="evtAttend"]').value);
        const max = parseInt(document.querySelector('[name="evtMax"]').value);

        this.clearError('evtAttend');
        let isValid = true;

        if (attend > max) {
            this.showError('evtAttend', '目前參加人數不能大於人數上限');
            isValid = false;
        }

        const oldCapacity = document.querySelector('.full-capacity');
        if (oldCapacity) oldCapacity.remove();

        if (attend === max) {
            const fullCapacity = document.createElement('span');
            fullCapacity.className = 'full-capacity';
            fullCapacity.textContent = '已額滿';
            document.querySelector('[name="evtAttend"]').parentElement.appendChild(fullCapacity);
        }

        return isValid;
    }

    showError(fieldName, message) {
        const field = document.querySelector(`[name="${fieldName}"]`);
        field.classList.add('error');

        let errorDiv = field.parentElement.querySelector('.error-message');
        if (!errorDiv) {
            errorDiv = document.createElement('div');
            errorDiv.className = 'error-message';
            field.parentElement.appendChild(errorDiv);
        }

        errorDiv.textContent = message;
        errorDiv.style.display = 'block';
    }

    clearError(fieldName) {
        const field = document.querySelector(`[name="${fieldName}"]`);
        field.classList.remove('error');

        const errorDiv = field.parentElement.querySelector('.error-message');
        if (errorDiv) {
            errorDiv.style.display = 'none';
        }
    }

    clearErrors() {
        document.querySelectorAll('.error-message').forEach(error => {
            error.style.display = 'none';
        });
        document.querySelectorAll('.form-control').forEach(field => {
            field.classList.remove('error');
        });
    }

    handleSubmit(event) {
        const isTimeValid = this.validateTimeSequence();
        const isAttendanceValid = this.validateAttendance();

        if (!isTimeValid || !isAttendanceValid) {
            event.preventDefault();
        }
    }
}

document.addEventListener('DOMContentLoaded', () => {
    new EventFormValidator();
});