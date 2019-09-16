function Controller(booksModel, notificationsModel) {
    "use strict";

    let controlledBooksModel = booksModel;
    let controlledNotificationsModel = notificationsModel;

    function updateRating(bookId, newRating) {
        controlledBooksModel.updateRating(bookId, newRating);

        controlledNotificationsModel.addNewRatingNotification(bookId, newRating);
    }

    function addBook(title, author, bookImage) {
        let newBookRequest = controlledBooksModel.addBook(title, author, bookImage);

        newBookRequest.then(function (newBookId) {
            controlledNotificationsModel.addNewBookNotification(newBookId);
        })
    }

    function addBookTag(bookId, newTag) {
        controlledBooksModel.addBookTag(bookId, newTag);
    }

    function addSearchNotification(searchText, category) {
        controlledNotificationsModel.addSearchNotification(searchText,
            category);
    }

    function getBookById(id) {
        return controlledBooksModel.findBook(id);
    }

    return {
        updateRating,
        addBook,
        addBookTag,
        addSearchNotification,
        getBookById
    };
}