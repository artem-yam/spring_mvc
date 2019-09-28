function BooksModel() {
    "use strict";

    const MAX_RATING = 5;
    const MOST_POPULAR_FILTER = "Most Popular";
    const TEXT_NOT_FOUND = -1;

    const AJAX_BOOKS_URL = "books";
    const AJAX_BOOK_RATING_URL = "/rating";
    const AJAX_TAGS_URL = "tags";

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
            AJAX_BOOKS_URL + "/" + bookId + AJAX_BOOK_RATING_URL,
            newRating, requestType.POST);
    }

    function addBook(bookFormData) {
        return Utils.sendRequest(AJAX_BOOKS_URL, bookFormData, requestType.POST)
            .then(function (addedBook) {
                onBookAdd.notify(addedBook.title, addedBook.author);

                return addedBook.id;
            }, function (error) {
                alert("Adding error : " + error);
                throw  new Error();
            });
    }

    function addBookTag(bookId, newTag) {
        if (!Utils.isEmpty(newTag)) {

            let book = findBook(bookId);

            if (book && !hasTag(book, newTag)) {

                book.tags.push(newTag);

                Utils.sendRequest(AJAX_TAGS_URL + "/" + newTag,
                    bookId, requestType.POST)
                    .then(function (bookId) {
                        onTagsChange.notify(findBook(bookId),
                            addNewTagToTheList(newTag));
                    });
            }
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

    async function initModel() {
        await getAllBooks()
            .then(function () {
                setInterval(getAllBooks, Utils.DATA_REFRESH_INTERVAL);
            });
        await getAllTags()
            .then(function () {
                setInterval(getAllTags, Utils.DATA_REFRESH_INTERVAL);
            });
    }

    function getBooksStorage() {
        return booksStorage;
    }

    function getTags() {
        return availableTags;
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

        onBookAdd,
        onTagsChange,
        initModel
    }
}