function Controller(booksModel, notificationsModel) {
    "use strict";

    let controlledBooksModel = booksModel;
    let controlledNotificationsModel = notificationsModel;
    const AJAX_USERS_URL = "users";
    const URL_SEPARATOR = "/";
    const AJAX_LOGIN_URL = "login";
    const AJAX_LOGOUT_URL = "logout";

    function updateRating(bookId, newRating) {
        controlledBooksModel.updateRating(bookId, newRating)
            .then(async function (book) {
                await controlledBooksModel.refreshModel();

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

    function loginUser(loginFormData) {
        return Utils.sendRequest(
            AJAX_USERS_URL + URL_SEPARATOR + AJAX_LOGIN_URL,
            loginFormData, requestType.POST)
            .catch(function (error) {
                alert("Can't login : " + error);
                throw error;
            });
    }

    function logoutUser() {
        return Utils.sendRequest(
            AJAX_USERS_URL + URL_SEPARATOR + AJAX_LOGOUT_URL,
            null, requestType.POST)
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
        logoutUser
    };
}