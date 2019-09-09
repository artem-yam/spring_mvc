function BooksModel(storage, tags) {
    "use strict";

    const MAX_RATING = 5;
    const MOST_POPULAR_FILTER = "Most Popular";
    const TEXT_NOT_FOUND = -1;

    let booksStorage = storage;
    let availableTags = tags;
    let onBookAdd = new EventEmitter();
    let onTagsChange = new EventEmitter();

    function search(text, filter) {
        let result = [];

        for (let i = 0; i < booksStorage.length; i++) {
            let book = booksStorage[i];
            if (checkWithFilter(book, filter) &&
                substringSearch(book.title, text) ||
                substringSearch(book.author, text)) {
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

        switch (filter) {
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
    }

    function addBook(title, author, bookImage) {
        let newBook = new Book(getNextId(), title, author, bookImage);

        booksStorage.push(newBook);
        onBookAdd.notify(title, author);

        return newBook;
    }

    function getNextId() {
        return booksStorage.length + 1;
    }

    function addBookTag(bookId, newTag) {
        if (!Utils.isEmpty(newTag)) {
            let book = findBook(bookId);
            if (book && !hasTag(book, newTag)) {
                book.tags.push(newTag);

                onTagsChange.notify(book, addNewTagToTheList(newTag));
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

        for (let i = 0; i < booksStorage.length && foundBook === undefined; i++) {
            if (booksStorage[i].id === bookId) {
                foundBook = booksStorage[i];
            }
        }
        return foundBook;
    }

    return {
        search,
        updateRating,
        addBook,
        addBookTag,
        findBook,
        getMostPopular,
        storage: booksStorage,
        tags: availableTags,
        onBookAdd,
        onTagsChange
    }
}