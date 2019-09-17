function BooksModel() {
    "use strict";

    const MAX_RATING = 5;
    const MOST_POPULAR_FILTER = "Most Popular";
    const TEXT_NOT_FOUND = -1;

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

        return $.ajax({
            url: "books/changeRating",
            method: 'POST',
            contentType: "application/json",
            data:
            /*JSON.stringify({
                title: title,
                author: author,
                image: bookImage
            }),*/
                JSON.stringify(bookToUpdate),
            dataType: "json"
        });
    }

    function addBook(title, author, bookImage) {
        let newBook = new Book(getNextId(), title, author, bookImage);

        return $.ajax({
            url: "books/add",
            method: 'POST',
            contentType: "application/json",
            data:
            /*JSON.stringify({
                title: title,
                author: author,
                image: bookImage
            }),*/
                JSON.stringify(newBook),
            dataType: "json"
        }).then(function (addedBookId) {
            onBookAdd.notify(title, author);

            return addedBookId;
        });

    }

    function getNextId() {
        return booksStorage.length + 1;
    }

    function addBookTag(bookId, newTag) {
        if (!Utils.isEmpty(newTag)) {

            let book = findBook(bookId);

            if (book && !hasTag(book, newTag)) {

                book.tags.push(newTag);

                $.ajax({
                    url: "tags/addToBook",
                    method: 'POST',
                    contentType: "application/json",
                    data:
                        JSON.stringify({
                            bookId: bookId,
                            tag: newTag
                        }),
                    dataType: "json"
                }).then(function (bookId) {
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
        return $.ajax({
            url: "books/getAll",
            dataType: "json",
        }).then(function (data) {
            booksStorage = data;
        });
    }

    function getAllTags() {
        return $.ajax({
            url: "tags/getAll",
            dataType: "json",
        }).then(function (data) {
            availableTags = data;
        });
    }

    async function initModel() {
        await getAllBooks()
            .then(function () {
                setInterval(getAllBooks, 1000);
            });
        await getAllTags()
            .then(function () {
                setInterval(getAllTags, 1000);
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