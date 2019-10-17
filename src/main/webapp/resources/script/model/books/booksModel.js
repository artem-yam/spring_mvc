function BooksModel() {
    "use strict";

    const MAX_RATING = 5;
    const MOST_POPULAR_FILTER = "Most Popular";
    const TEXT_NOT_FOUND = -1;

    const AJAX_BOOKS_URL = "books";
    const AJAX_TAGS_URL = "tags";
    const URL_SEPARATOR = "/";

    let booksStorage = [];
    let availableTags = [];
    let onBookAdd = new EventEmitter();
    let onTagsChange = new EventEmitter();

    function search(text, filter) {

        let result = [];

        for (let i = 0; i < booksStorage.length; i++) {
            let book = booksStorage[i];

            if (checkWithFilter(book, filter) &&
                (substringSearch(book.title, text) ||
                    substringSearch(book.author, text))) {
                result.push(book);
            }
        }

        return result;
    }

    function substringSearch(string, substring) {
        return string.toString().toLowerCase()
            .indexOf(substring.toLowerCase()) !== TEXT_NOT_FOUND;
    }

    function checkWithFilter(book, filter) {
        let result = true;

        switch (filter.trim()) {
            case (MOST_POPULAR_FILTER):
                result = (book.rating === MAX_RATING);
                break;
        }

        return result;
    }

    function getMostPopular() {
        return search("", MOST_POPULAR_FILTER);
    }

    function updateRating(bookId, newRating) {
        let bookToUpdate = findBook(bookId);
        bookToUpdate.rating = newRating;

        return Utils.sendRequest(
            AJAX_BOOKS_URL + URL_SEPARATOR + bookId, bookToUpdate, requestType.POST);
    }

    function addBook(bookFormData) {
        return Utils.sendRequest(AJAX_BOOKS_URL, bookFormData, requestType.POST)
            .then(function (response) {
                onBookAdd.notify(response.id, response.title, response.author);
            });
    }

    function addBookTag(bookId, newTag) {
        if (!Utils.isEmpty(newTag)) {

            let book = findBook(bookId);

            if (book && !hasTag(book, newTag)) {

                book.tags.push(newTag);

                Utils.sendRequest(AJAX_TAGS_URL, //+ URL_SEPARATOR + newTag,
                    new BookTag(bookId, newTag), requestType.POST)
                    .then(function (bookTags) {
                        onTagsChange.notify(findBook(bookId),
                            addNewTagToTheList(bookTags[bookTags.length - 1]));
                    });
            }
        }
    }

    function unbindTag(bookId, tag) {
        let book = findBook(bookId);

        if (book && hasTag(book, tag)) {

            Utils.sendRequest(AJAX_TAGS_URL + URL_SEPARATOR + tag +
                URL_SEPARATOR + AJAX_BOOKS_URL + URL_SEPARATOR + bookId,
                null, requestType.DELETE)
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

    function addNewTagToTheList(newTag) {
        let isAdded = false;

        if (!availableTags.includes(newTag)) {
            availableTags.push(newTag);
            isAdded = true;
        }

        return isAdded;
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
        return Utils.sendRequest(AJAX_BOOKS_URL + URL_SEPARATOR + bookId,
            null, requestType.DELETE)
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
        getMostPopular,

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