function Controller(booksModel, notificationsModel) {
    "use strict";

    let controlledBooksModel = booksModel;
    let controlledNotificationsModel = notificationsModel;
    const AJAX_USERS_URL = "userSession";

    function updateRating(bookId, newRating) {
        controlledBooksModel.updateRating(bookId, newRating)
            .then(async function (book) {
                await controlledBooksModel.refreshModel();

                controlledNotificationsModel.addNewRatingNotification(book.id,
                    book.rating);
            })
    }

    function addBook(bookFormData) {
        return controlledBooksModel.addBook(bookFormData);
    }

    function addNewBookNotification(newBookId) {
        controlledNotificationsModel.addNewBookNotification(newBookId);
    }

    function addBookTag(bookId, newTag) {
        controlledBooksModel.addBookTag(bookId, newTag);
    }

    function unbindTag(bookId, tag) {
        controlledBooksModel.unbindTag(bookId, tag);
    }

    function deleteBook(bookId) {
        return controlledBooksModel.deleteBook(bookId);
    }

    function addSearchNotification(searchText, category) {
        controlledNotificationsModel.addSearchNotification(searchText,
            category);
    }

    function filterBooks(text, filter) {
        return controlledBooksModel.search(text, filter)
            .then(function (result) {
                if (text.trim() !== '') {
                    addSearchNotification(text, filter);
                }
                return result;
            });
    }

    function getBookById(id) {
        return controlledBooksModel.findBook(id);
    }

    function loginUser(loginFormData) {
        return Utils.sendRequest(AJAX_USERS_URL, loginFormData,
            requestType.POST);
    }

    function logoutUser() {
        return Utils.sendRequest(AJAX_USERS_URL, null, requestType.DELETE)
            .catch(function (error) {
                alert("Can't logout : " + error);
                throw error;
            });
    }

    return {
        updateRating,
        addBook,
        addBookTag,
        addSearchNotification,
        getBookById,
        loginUser,
        logoutUser,
        deleteBook,
        addNewBookNotification,
        unbindTag,
        filterBooks
    };
}