function BooksView(controller, model) {
    "use strict";

    let mainController = controller;
    let booksModel = model;

    function createBlock(book) {
        let template = window.document.querySelector("#book_template");
        let booksPage = window.document.querySelector(".main_content");

        let bookBlock = template.content.cloneNode(true);

        bookBlock.querySelector("img").setAttribute("src",
            "books/" + book.id + "/image");

        bookBlock.querySelector(".delete-book")
            .addEventListener('click', function () {
                let toDelete = confirm("Delete this book?");
                //mainController.updateRating(book.id, i);
            });

        bookBlock.querySelector("img").setAttribute("alt", book.title);
        bookBlock.querySelector(".book_description a")
            .setAttribute("href", "#modal" + book.id);
        bookBlock.querySelector(".book_description a span").innerHTML =
            book.title;
        bookBlock.querySelector(".book_description a").innerHTML +=
            "by " + book.author;

        let ratingTemplate = window.document.querySelector(
            "#book_rating_template");

        for (let i = 5; i > 0; i--) {
            let rating = ratingTemplate.content.cloneNode(true);

            rating.querySelector("input")
                .setAttribute("id", "star" + book.id + "_" + i);
            rating.querySelector("input")
                .setAttribute("name", "rating_stars" + book.id);
            rating.querySelector("label")
                .setAttribute("for", "star" + book.id + "_" + i);

            rating.querySelector("input")
                .addEventListener('change', function () {
                    mainController.updateRating(book.id, i);
                });

            bookBlock.querySelector(".rating").appendChild(rating);
        }
        booksPage.appendChild(bookBlock);

        if (book.rating > 0) {
            document.getElementById("star" + book.id +
                "_" + book.rating).setAttribute("checked", "");
        }

        fillModal(book);
    }

    function fillModal(book) {

        let template = window.document.querySelector("#modal_template");
        let modalContainer = window.document.querySelector(".modal_container");

        let modal = template.content.cloneNode(true);

        let emptyDiv = modal.querySelector("div").cloneNode(false);
        emptyDiv.removeAttribute("class");

        modal.querySelector(".modal-body hr").before(emptyDiv);

        modal.querySelector("div").setAttribute("id", "modal" + book.id);

        modal.querySelector(".modal-title").innerHTML =
            book.author + ": " + book.title;

        modalContainer.appendChild(modal);

        changeModalBody(book);

        changeSelectTagList(book);

        window.document.querySelector(
            "#modal" + book.id + " .modal-body button")
            .addEventListener('click', function () {
                addBookTag(book.id);
            });

    }

    function changeModalBody(book) {
        let modalBody = window.document.querySelector(
            "#modal" + book.id + " .modal-body");
        let tagsDiv = modalBody.querySelector("div");

        let tagsCode = "";

        if (book.tags.length === 0) {
            tagsCode += "Empty";
        } else {
            tagsCode += "<ul>";
            for (let tag of book.tags) {
                tagsCode += "<li>" + tag + "</li>";
            }
            tagsCode += "</ul>";
        }
        tagsDiv.innerHTML = tagsCode;
    }

    function changeSelectTagList(book) {
        let modalBody = window.document.querySelector(
            "#modal" + book.id + " .modal-body");
        Utils.resetInnerHTML(modalBody.querySelector("optgroup"));

        for (let tag of booksModel.getTags()) {
            let tagOption = modalBody.querySelector("option").cloneNode(true);

            tagOption.removeAttribute("selected");
            tagOption.setAttribute("value", tag);
            tagOption.textContent = tag;

            modalBody.querySelector("optgroup").appendChild(tagOption);
        }
    }

    function search() {
        Utils.resetInnerHTML(window.document.querySelector(".main_content"));
        let searchText = window.document.querySelector("#search").value;
        let activeCategory = window.document.querySelector(
            ".main_sort .sort .active").innerText;

        return booksModel.search(searchText, activeCategory);
    }

    function addBook(bookFormData) {
        if (!Utils.isEmpty(bookFormData.get("title")) &&
            !Utils.isEmpty(bookFormData.get("author"))) {
            mainController.addBook(bookFormData);
        } else {
            alert("Fill \"Title\" and \"Author\" fields to add a new book");
        }
    }

    function showLoadedImage() {
        window.document.querySelector(".upload_button").classList
            .add("loadedImage");

        let input = window.document.querySelector("#add_book_image");

        window.document.querySelector("#loaded_image img")
            .setAttribute("src",
                window.URL.createObjectURL(input.files[0]));
        window.document.querySelector("#loaded_image").classList
            .remove("hidden");
    }

    function addBookTag(bookId) {
        let tagSelect =
            window.document.querySelector("#modal" + bookId + " select").value;
        let tagInput =
            window.document.querySelector("#modal" + bookId + " input").value;
        let newTag = "";

        if (tagSelect === "Not selected") {
            if (!Utils.isEmpty(tagInput)) {
                if (!booksModel.getTags().includes(tagInput)) {
                    Utils.resetValue(window.document.querySelector(
                        "#modal" + bookId + " .modal-body input"));
                    newTag = tagInput;
                } else {
                    alert('Tag \'' + tagInput + '\' already exists! \n' +
                        'Please, correct your tag or choose from the list!');
                }
            } else {
                alert('You trying to add empty tag! \n' +
                    'Please, correct your tag or choose from the list!');
            }
        } else {
            newTag = tagSelect;
        }

        mainController.addBookTag(bookId, newTag);
    }

    async function chooseCategory(category) {
        Utils.resetInnerHTML(window.document.querySelector(".main_content"));

        let modalContainer = window.document.querySelector(".modal_container");

        Utils.resetInnerHTML(modalContainer);

        let categories = window.document.querySelectorAll(
            ".main_sort .sort div a");
        for (let i = 0; i < categories.length; i++) {
            categories[i].classList.remove("active");
        }

        Utils.resetValue(window.document.querySelector("#search"));

        let elem = window.document.getElementById(category);
        elem.classList.add("active");

        await booksModel.refreshModel();
    }

    function showAllBooks() {
        chooseCategory("all_books")
            .then(function () {
                Utils.resetInnerHTML(window.document.querySelector(".main_content"));

                for (let i = 0; i < booksModel.getBooksStorage().length; i++) {
                    if (!booksModel.getBooksStorage()[i].deleted) {
                        createBlock(booksModel.getBooksStorage()[i]);
                    }
                }
            });
    }

    async function filter(filterMethod) {
        await booksModel.refreshModel();

        let result = filterMethod() || booksModel.getBooksStorage();

        if (result.length !== 0) {
            for (let i = 0; i < result.length; i++) {
                if (!result[i].deleted) {
                    createBlock(result[i]);
                }
            }
        } else {
            window.document.querySelector(".main_content").innerHTML =
                "<h2>Not found!</h2>";
        }
    }

    function browsePage() {
        for (let elem of window.document.querySelectorAll(".nav_menu a")) {
            elem.classList.remove("active")
        }
        window.document.querySelector(".nav_menu .browse").classList
            .add("active");

        window.document.querySelector(
            ".main > div").classList.remove("hidden");
        window.document.querySelector(
            ".main > div").classList.add("block");

        Utils.resetInnerHTML(window.document.querySelector(".history_content"));

        showAllBooks();
    }

    window.document.querySelector(".nav_menu .browse")
        .addEventListener("click", function () {
            browsePage();
        });

    window.document.querySelector("#add_book_image")
        .addEventListener("change", function () {
            showLoadedImage();
        });

    window.document.querySelector("#add_book")
        .addEventListener("click", function () {
            let bookForm = document.forms.namedItem("addBookForm");
            let bookFormData = new FormData(bookForm);

            addBook(bookFormData);
        });

    window.document.querySelector("#all_books")
        .addEventListener("click", function () {
            showAllBooks();
        });

    window.document.querySelector("#most_popular")
        .addEventListener("click", function () {
            chooseCategory("most_popular").then(function () {
                filter(booksModel.getMostPopular);
            });
        })
    ;

    window.document.querySelector("#search")
        .addEventListener("input", function () {
            filter(search);
        });

    window.document.querySelector("#search")
        .addEventListener("change", function () {
            filter(search);
        });

    model.onBookAdd.subscribe(function (title, author) {
        booksModel.refreshModel()
            .then(function () {
                alert(
                    "book \"" + author + " - " + title + "\" has been added!");

                window.document.querySelector("#add_image_label").classList
                    .remove("loadedImage");

                window.document.querySelector("#loaded_image").classList
                    .add("hidden");

                Utils.resetValue(
                    window.document.querySelector("#add_book_image"));
                Utils.resetValue(
                    window.document.querySelector("#add_book_title"));
                Utils.resetValue(
                    window.document.querySelector("#add_book_author"));

                if (Utils.isEmpty(
                    window.document.querySelector(
                        ".history_content").innerHTML)) {
                    let activeCategory = window.document.querySelector(
                        ".main_sort .sort .active");
                    if (window.document.getElementById("all_books") ===
                        activeCategory) {
                        showAllBooks();
                    }
                }
            });
    });

    model.onTagsChange.subscribe(function (updatedBook, userTagPushed) {
        booksModel.refreshModel().then(function () {
            changeModalBody(updatedBook);

            if (userTagPushed === true) {
                for (let book of booksModel.getBooksStorage()) {
                    if (!book.deleted) {
                        changeSelectTagList(book);
                    }
                }
            }
        });
    });

    return {
        browsePage,
        addBookTag
    };
}