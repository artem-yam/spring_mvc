    function Controller(booksModel, notificationsModel) {
    "use strict";

    let controlledBooksModel = booksModel;
    let controlledNotificationsModel = notificationsModel;

    function updateRating(bookId, newRating) {
        controlledBooksModel.updateRating(bookId, newRating);

        controlledNotificationsModel.addNewRatingNotification(booksModel.findBook(bookId), newRating);
    }

    function addBook(title, author, bookImage) {
        let newBook = controlledBooksModel.addBook(title, author, bookImage);

        controlledNotificationsModel.addNewBookNotification(newBook);
    }

    function addBookTag(bookId, newTag) {
        controlledBooksModel.addBookTag(bookId, newTag);
    }

    function addSearchNotification(searchText, category) {
        controlledNotificationsModel.addSearchNotification(searchText, category);
    }

    return {
        updateRating,
        addBook,
        addBookTag,
        addSearchNotification
    };
}