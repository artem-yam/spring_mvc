(function () {
    "use strict";

    async function pageLoad() {
        let notificationsModel = new NotificationsModel();
        let booksModel = new BooksModel();

        let controller = new Controller(booksModel, notificationsModel);

        let notificationsView = new NotificationsView(controller,
            notificationsModel);
        let booksView = new BooksView(controller, booksModel);

        new LoginView();

        await booksModel.initModel();
        await notificationsModel.initModel();

        booksView.browsePage();
        notificationsView.loadHistoryBar();
    }

    window.addEventListener("load", pageLoad);

}());