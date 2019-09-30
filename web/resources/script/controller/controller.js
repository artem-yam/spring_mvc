function Controller(booksModel, notificationsModel) {
    "use strict";

    let controlledBooksModel = booksModel;
    let controlledNotificationsModel = notificationsModel;

    function updateRating(bookId, newRating) {
        controlledBooksModel.updateRating(bookId, newRating)
            .then(function (book) {
                controlledNotificationsModel.addNewRatingNotification(book.id,
                    book.rating);
            })
    }

    function addBook(bookFormData) {
        controlledBooksModel.addBook(bookFormData)
            .then(function (newBookId) {
                controlledNotificationsModel.addNewBookNotification(newBookId);
            });
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