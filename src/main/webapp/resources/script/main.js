(function () {
    "use strict";

    async function init() {

        let notificationsModel = new NotificationsModel();
        let booksModel = new BooksModel();

        let controller = new Controller(booksModel, notificationsModel);

        let notificationsView = new NotificationsView(controller,
            notificationsModel);
        let booksView = new BooksView(controller, booksModel);

        new LoginView(controller);

        await booksModel.refreshModel();
        await notificationsModel.refreshModel();

        booksView.browsePage();
        notificationsView.loadHistoryBar();
    }

    window.addEventListener("load", init);

}());