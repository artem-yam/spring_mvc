function BooksModel() {
    "use strict";

    const AJAX_BOOKS_URL = "books";
    const AJAX_TAGS_URL = "tags";
    const URL_SEPARATOR = "/";

    let booksStorage = [];
    let availableTags = [];
    let onBookAdd = new EventEmitter();
    let onTagsChange = new EventEmitter();

    function search(text, filter) {
        return Utils.sendRequest(AJAX_BOOKS_URL, new Filter(filter, text),
            requestType.GET);
    }

    function updateRating(bookId, newRating) {
        let bookToUpdate = findBook(bookId);
        bookToUpdate.rating = newRating;

        return Utils.sendRequest(
            AJAX_BOOKS_URL + URL_SEPARATOR + bookId, bookToUpdate,
            requestType.PUT);
    }

    function addBook(bookFormData) {
        return Utils.sendRequest(AJAX_BOOKS_URL, bookFormData, requestType.POST)
            .then(function (response) {
                onBookAdd.notify(response.id, response.title, response.author);
            });
    }

    function addBookTag(bookId, tag) {
        if (!Utils.isEmpty(tag)) {

            let book = findBook(bookId);

            if (book && !hasTag(book, tag)) {

                book.tags.push(tag);

                if (isExists(tag)) {
                    Utils.sendRequest(AJAX_TAGS_URL + URL_SEPARATOR
                        + tag + URL_SEPARATOR + AJAX_BOOKS_URL,
                        bookId, requestType.POST)
                        .then(function (bookTags) {
                            onTagsChange.notify(findBook(bookId), false);
                        });
                } else {
                    Utils.sendRequest(AJAX_TAGS_URL,
                        new NewTagBinding(bookId, tag), requestType.POST)
                        .then(function (bookTags) {
                            onTagsChange.notify(findBook(bookId), true);
                        });
                }

            }
        }
    }

    function unbindTag(bookId, tag) {
        let book = findBook(bookId);

        if (book && hasTag(book, tag)) {

            Utils.sendRequest(AJAX_TAGS_URL + URL_SEPARATOR + tag +
                URL_SEPARATOR + AJAX_BOOKS_URL, bookId, requestType.DELETE)
                .then(function (bookTags) {
                    book = findBook(bookId);
                    book.tags = bookTags;

                    onTagsChange.notify(book);
                });
        }
    }

    function hasTag(book, tag) {
        let hasTag = false;

        for (let i = 0; i < book.tags.length && !hasTag; i++) {
            hasTag = (book.tags[i].toLowerCase() === tag.toLowerCase());
        }

        return hasTag;
    }

    function isExists(tag) {
        let isExists = false;

        if (availableTags.includes(tag)) {
            isExists = true;
        }

        return isExists;
    }

    function findBook(bookId) {
        let foundBook;

        for (let i = 0; i < booksStorage.length && foundBook === undefined;
             i++) {
            if (booksStorage[i].id === bookId) {
                foundBook = booksStorage[i];
            }
        }
        return foundBook;
    }

    function getAllBooks() {
        return Utils.sendRequest(AJAX_BOOKS_URL, null,
            requestType.GET)
            .then(function (data) {
                booksStorage = data;
            });
    }

    function getAllTags() {
        return Utils.sendRequest(AJAX_TAGS_URL, null,
            requestType.GET)
            .then(function (data) {
                availableTags = data;
            });
    }

    async function refreshModel() {
        await getAllBooks();
        await getAllTags();
    }

    function getBooksStorage() {
        return booksStorage;
    }

    function getTags() {
        return availableTags;
    }

    function deleteBook(bookId) {
        return Utils.sendRequest(AJAX_BOOKS_URL, bookId, requestType.DELETE)
            .then(async function () {
                await refreshModel();

                alert("Book " + bookId + " was deleted");

            });
    }

    return {
        search,
        updateRating,
        addBook,
        addBookTag,
        findBook,

        getBooksStorage,
        getTags,
        getAllBooks,
        getAllTags,

        onBookAdd,
        onTagsChange,
        refreshModel,
        deleteBook,
        unbindTag
    }
}