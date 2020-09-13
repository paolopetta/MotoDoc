var borderOk = '2px solid #080';
var borderNo = '2px solid #f00';
var usernameOk = false;
var passwordOk = false;
var nomeOk = false;
var emailOk = false;

function validaUsername() {
    var input = document.forms['registration']['username'];
    if (input.value.length >= 6 && input.value.match(/^[0-9a-zA-Z]+$/)) {
        input.style.border = borderOk;
        usernameOk = true;
            } else {
                input.style.border = borderNo;
                usernameOk = false;
            }
            cambiaStatoRegistrami();
}

function validaPassword() {
    var inputpw = document.forms['registration']['password'];
    var inputpwconf = document.forms['registration']['passwordConferma'];
    var password = inputpw.value;
    if (password.length >= 8 && password.toUpperCase() != password
        && password.toLowerCase() != password && /[0-9]/.test(password)) {
        inputpw.style.border = borderOk;

        if (password == inputpwconf.value) {
            inputpwconf.style.border = borderOk;
            passwordOk = true;
        } else {
            inputpwconf.style.border = borderNo;
            passwordOk = false;
        }
    } else {
        inputpw.style.border = borderNo;
        inputpwconf.style.border = borderNo;
        passwordOk = false;
    }
    cambiaStatoRegistrami();
}

function validaNome() {
    var input = document.forms['registration']['nome'];
    if (input.value.trim().length > 0
        && input.value.match(/^[ a-zA-Z\u00C0-\u00ff]+$/)) {
        input.style.border = borderOk;
        nomeOk = true;
    } else {
        input.style.border = borderNo;
        nomeOk = false;
    }
    cambiaStatoRegistrami();
}

function validaEmail() {
    var input = document.forms['registration']['email'];
    if (input.value.match(/^\w+([\.-]?\w+)@\w+([\.-]?\w+)(\.\w+)+$/)) {
        input.style.border = borderOk;
        emailOk = true;
    } else {
        input.style.border = borderNo;
        emailOk = false;
    }
    cambiaStatoRegistrami();
}

function cambiaStatoRegistrami() {
    if (usernameOk && passwordOk && nomeOk && emailOk) {
        document.getElementById('registrami').disabled = false;
        document.getElementById('registramimessaggio').innerHTML = '';
    } else {
        document.getElementById('registrami').disabled = true;
        document.getElementById('registramimessaggio').innerHTML = 'Verifica che tutti i campi siano in verde.';
    }
}