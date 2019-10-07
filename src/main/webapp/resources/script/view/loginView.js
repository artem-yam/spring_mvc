function LoginView(controller) {
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

    window.document.querySelector("#logout_user")
        .addEventListener("click", function () {
            if (confirm("Confirm logout?")) {
                mainController.logoutUser().then(function () {
                    alert("Successful logout");
                    window.document.location.reload(true);
                });
            }
        });

    function loginUser(loginFormData) {
        if (!Utils.isEmpty(loginFormData.get("login")) &&
            !Utils.isEmpty(loginFormData.get("password"))) {

            Utils.resetValue(window.document.querySelector("#user_login"));
            Utils.resetValue(window.document.querySelector("#user_password"));

            mainController.loginUser(loginFormData).then(function () {
                alert("Successful login");
                window.document.location.reload(true);
            });
        } else {
            alert("Fill \"Login\" and \"Password\" fields to login");
        }
    }

}