(function () {
    "use strict";

    async function init() {

        let onUserLogIn = new EventEmitter();

        onUserLogIn.subscribe(function () {
            showStartInfo();
        });

        function showStartInfo() {
            booksView.browsePage();
            notificationsView.loadHistoryBar();
        }

        let notificationsModel = new NotificationsModel();
        let booksModel = new BooksModel();

        let controller = new Controller(booksModel, notificationsModel);

        let notificationsView = new NotificationsView(controller,
            notificationsModel);
        let booksView = new BooksView(controller, booksModel);

        new LoginView(controller, onUserLogIn);

        await booksModel.initModel();
        await notificationsModel.initModel();

        showStartInfo();

    }

    window.addEventListener("load", init);

}());