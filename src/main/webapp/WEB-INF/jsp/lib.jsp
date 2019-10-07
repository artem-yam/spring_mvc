<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>LIBRARY</title>

    <%--rel="stylesheet" type="text/css"    --%>

    <link rel="stylesheet" type="text/css"
          href="static/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css"
          href="static/css/normalize.css"/>
    <link rel="stylesheet" type="text/css"
          href="static/css/style.css"/>

</head>
<body>
    <header class="header">
        <div class="header_links">
            <a href="#">Help Center</a>
            <span> &#8226; </span>
            <a href="#">Our Support</a>
        </div>
        <div class="header_user-profile">
            <a href="#">
                <c:choose>
                    <c:when test="${not empty sessionScope.get('scopedTarget.user').login}">
                        ${sessionScope.get('scopedTarget.user').login}
                    </c:when>
                    <c:otherwise>
                        John Doe
                    </c:otherwise>
                </c:choose>
            </a>
        </div>
        <div class="header_logout ${empty sessionScope.get('scopedTarget.user').login ? 'hidden':''} ">
            <a id="logout_user" href="#">LogOut</a>
        </div>
    </header>

    <aside class="aside">
        <div class="menu">

            <form:form method="post" enctype="multipart/form-data"
                       action="books" name="addBookForm"
                       modelAttribute="book">

                <a id="add_book">
                    <span>&#10010;</span>
                    ADD A BOOK
                </a>

                <form:input placeholder="Title" type="text"
                            name="title" path="title"/>
                <form:input placeholder="Author" type="text"
                            name="author" path="author"/>

                <label id="add_image_label" class="upload_button">Load Image
                    <input type="file" id="add_book_image" name="image"
                           accept=".jpg,.jpeg,.png"/>
                </label><br>
                <div id="loaded_image" class="hidden">
                    <img/>
                </div>
                <form:errors path="*" cssStyle="color: #ff0000;" element="div"/>
                ${book}
            </form:form>

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
                           src="<spring:url value="static/images/icons/search.png" />">
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
            src="static/script/utils/jquery-3.4.1.slim.min.js"></script>
    <script type="text/javascript"
            src="static/script/utils/jquery-3.4.1.min.js"></script>
    <script type="text/javascript"
            src="static/script/utils/bootstrap.min.js"></script>

    <script type="text/javascript"
            src="static/script/utils/eventEmitter.js"></script>
    <script type="text/javascript"
            src="static/script/utils/requestTypes.js"></script>
    <script type="text/javascript"
            src="static/script/utils/utils.js"></script>
    <!--Модели-->
    <script type="text/javascript"
            src="static/script/model/books/book.js"></script>
    <script type="text/javascript"
            src="static/script/model/books/booksModel.js"></script>
    <script type="text/javascript"
            src="static/script/model/notifications/notificationTypes.js"></script>
    <script type="text/javascript"
            src="static/script/model/notifications/notificationTO.js"></script>
    <script type="text/javascript"
            src="static/script/model/notifications/notificationsModel.js"></script>
    <!--Контроллеры-->
    <script type="text/javascript"
            src="static/script/controller/controller.js"></script>
    <!--Вьюхи-->
    <script type="text/javascript"
            src="static/script/view/notificationsView.js"></script>
    <script type="text/javascript"
            src="static/script/view/booksView.js"></script>

    <script type="text/javascript"
            src="static/script/view/loginView.js"></script>
    <!--Мэйн-->
    <script type="text/javascript"
            src="static/script/main.js"></script>

</body>
</html>
