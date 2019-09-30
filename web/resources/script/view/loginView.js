function LoginView() {
    "use strict";

    Utils.onLoginError.subscribe(function () {
        showLoginForm();
    });

    function showLoginForm() {
        let loginModal = window.document.querySelector("#user-authentication");

        $("#user-authentication").modal('toggle');

    }

    return {}
}