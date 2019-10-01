function LoginView(controller, loginListener) {
    "use strict";

    let mainController = controller;

    Utils.onLoginError.subscribe(function () {
        $("#user-authentication").modal({backdrop: "static"});
    });

    window.document.querySelector("#login_user")
        .addEventListener("click", function () {

            let loginForm = document.forms.namedItem("loginForm");
            let loginFormData = new FormData(loginForm);

            loginUser(loginFormData);
        });

    function loginUser(loginFormData) {
        if (!Utils.isEmpty(loginFormData.get("login")) &&
            !Utils.isEmpty(loginFormData.get("password"))) {

            Utils.resetValue(window.document.querySelector("#user_login"));
            Utils.resetValue(window.document.querySelector("#user_password"));

            mainController.loginUser(loginFormData).then(function () {
                let authModal = window.document.querySelector(
                    '#user-authentication');
                $(authModal).modal('hide');

                loginListener.notify();
            });
        } else {
            alert("Fill \"Login\" and \"Password\" fields to login");
        }
    }

}