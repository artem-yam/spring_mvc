<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>LIBRARY</title>

    <link rel="stylesheet" type="text/css"
          href="resources/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="resources/css/normalize.css">
    <link rel="stylesheet" type="text/css" href="resources/css/style.css">

</head>
<body>
    <header class="header">
        <div class="header_links">
            <a href="#">Help Center</a>
            <span> &bull; </span>
            <a href="#">Our Support</a>
        </div>
        <div class="header_user-profile">
            <a href="#">
                ${(empty logged_user) ? "John Doe": logged_user}
            </a>
        </div>
        <div class="header_logout ${(empty logged_user) ? "hidden": ""} ">
            <a id="logout_user" href="#">LogOut</a>
        </div>
    </header>

    <aside class="aside">
        <div class="menu">
            <form enctype="multipart/form-data" method="post"
                  name="addBookForm">

                <a id="add_book">
                    <span>&#10010;</span>
                    ADD A BOOK
                </a>

                <input placeholder="Title" type="text" id="add_book_title"
                       name="title"/>
                <input placeholder="Author" type="text"
                       id="add_book_author"
                       name="author"/>
                <label id="add_image_label" class="upload_button">Load Image
                    <input type="file" id="add_book_image"
                           name="image" accept="image/*"/>
                </label><br>
                <div id="loaded_image" class="hidden">
                    <img/>
                </div>
            </form>
        </div>

        <div class="menu nav_menu">
            <a href="#" class="reading ">
                <div></div>
                Now Reading
            </a>
            <a href="#" class="browse active">
                <div></div>
                Browse
            </a>
            <a href="#" class="buy_books">
                <div></div>
                Buy Books
            </a>
            <a href="#" class="fav_books">
                <div></div>
                Favourite Books
            </a>
            <a href="#" class="wishlist">
                <div></div>
                Wishlist
            </a>
            <a href="#" class="history">
                <div></div>
                History
            </a>
        </div>

        <div class="menu topics_menu">
            <ul>
                <li class="red">
                    <a href="#">
                        Must Read Titles
                    </a>
                </li>
                <li class="orange">
                    <a href="#">
                        Best Of List
                    </a>
                </li>
                <li class="blue">
                    <a href="#">
                        Classic Novels
                    </a>
                </li>
                <li class="purple">
                    <a href="#">
                        Non Fiction
                    </a>
                </li>
            </ul>
        </div>

        <div class="menu history_block"></div>
    </aside>

    <main class="main">
        <div class="browse">
            <div class="main_header">
                Browse Available Books
            </div>
            <div class="main_sort">
                <div class="sort">
                    <div>
                        <a id="all_books" class="category_link" href="#">All
                            Books</a>
                    </div>
                    <div>
                        <a id="most_recent" class="category_link" href="#">Most
                            Recent</a>
                    </div>
                    <div>
                        <a id="most_popular" class="category_link" href="#">Most
                            Popular</a>
                    </div>
                    <div>
                        <a id="free_books" class="category_link" href="#">Free
                            Books</a>
                    </div>
                </div>
                <div class="keywords">
                    <input id="search" type="search"
                           placeholder="Enter Keywords"/>
                    <input type="image" alt="Search"
                           src="resources/images/icons/search.png">
                </div>
            </div>

            <div class="main_content"></div>
        </div>

        <div class="history_content"></div>
    </main>

    <footer class="footer">
        <a id="help" href="#">
            <div></div>
        </a>
        <a id="settings" href="#">
            <div></div>
        </a>

    </footer>

    <div class="modal_container">
    </div>

    <div class="modal_container">

        <div class="modal fade" id="user-authentication">
            <div class="modal-dialog">
                <div class="modal-content">

                    <div class="modal-header">
                        <h5 class="modal-title">User authentication</h5>
                    </div>

                    <div class="modal-body">
                        <form enctype="multipart/form-data" method="post"
                              name="loginForm">

                            <input placeholder="Login" type="text"
                                   id="user_login" name="login"/>
                            <input placeholder="Password" type="text"
                                   id="user_password" name="password"/>

                        </form>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary"
                                id="login_user">
                            Login
                        </button>
                    </div>

                </div>
            </div>
        </div>

    </div>


    <template id="history_bar_template">
        <div class="history_log">
            <div class="history_pic"></div>
            <div class="history_text">
                <p></p>
            </div>
        </div>
    </template>

    <template id="history_page_template">
        <div class="history_log">
            <p></p>
        </div>
    </template>

    <template id="book_template">
        <div class="book">
            <div class="delete-book">&#10008;</div>
            <img src="" alt=""/>
            <div class="book_description">
                <a data-toggle="modal" href="#"><span></span><br></a>
            </div>
            <div class="rating"></div>
        </div>
    </template>

    <template id="book_rating_template">
        <input type="radio"
               name=""/>
        <label class="star_label">
        </label>
    </template>

    <template id="modal_template">
        <div class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">

                    <div class="modal-header">
                        <h5 class="modal-title"></h5>
                    </div>

                    <div class="modal-body">
                        Tags:
                        <hr>
                        Add new tag:<br>
                        Choose from the list:
                        <select name="defaultTags">
                            <optgroup label="Choose one of these">
                            </optgroup>

                            <optgroup label="None of above">
                                <option value="Not selected"
                                        selected>
                                    Not selected
                                </option>
                            </optgroup>
                        </select>
                        <br>Or type your tag
                        <input type="text"/>
                        <br>
                        <button>Confirm</button>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary"
                                data-dismiss="modal">Close
                        </button>
                    </div>

                </div>
            </div>
        </div>
    </template>

    <script type="text/javascript"
            src="resources/script/utils/jquery-3.4.1.slim.min.js"></script>
    <script type="text/javascript"
            src="resources/script/utils/jquery-3.4.1.min.js"></script>
    <script type="text/javascript"
            src="resources/script/utils/bootstrap.min.js"></script>

    <script type="text/javascript"
            src="resources/script/utils/eventEmitter.js"></script>
    <script type="text/javascript"
            src="resources/script/utils/requestTypes.js"></script>
    <script type="text/javascript"
            src="resources/script/utils/utils.js"></script>
    <!--Модели-->
    <script type="text/javascript"
            src="resources/script/model/books/book.js"></script>
    <script type="text/javascript"
            src="resources/script/model/books/booksModel.js"></script>
    <script type="text/javascript"
            src="resources/script/model/notifications/notificationTypes.js"></script>
    <script type="text/javascript"
            src="resources/script/model/notifications/notificationTO.js"></script>

    <script type="text/javascript"
            src="resources/script/model/notifications/notificationsModel.js"></script>

    <!--Контроллеры-->
    <script type="text/javascript"
            src="resources/script/controller/controller.js"></script>

    <!--Вьюхи-->
    <script type="text/javascript"
            src="resources/script/view/notificationsView.js"></script>
    <script type="text/javascript"
            src="resources/script/view/booksView.js"></script>

    <script type="text/javascript"
            src="resources/script/view/loginView.js"></script>

    <!--Мэйн-->
    <script type="text/javascript" src="resources/script/main.js"></script>

</body>
</html>
