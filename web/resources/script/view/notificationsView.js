function NotificationsView(controller, model) {
    "use strict";

    const HISTORY_BAR_LENGTH = 5;

    let notificationsController = controller;
    let notificationsModel = model;

    function createHistoryBarBlock(notification) {

        let template = window.document.querySelector("#history_bar_template");
        let historyBar = window.document.querySelector(".history_block");

        let historyBlock = template.content.cloneNode(true);

        historyBlock.querySelector("p").innerHTML =
            formNotificationMessage(notification);
        historyBlock.querySelector(".history_text").innerHTML +=
            formTimeMessage(notificationsModel.getFlowedTime(notification.date));

        historyBar.append(historyBlock);
    }

    function formNotificationMessage(notification) {
        let message = "";

        switch (notification.type) {
            case 'ADD_BOOK':
                message += "You added <b>" +
                    notification.book.title + "</b> by <b>" +
                    notification.book.author + "</b>";

                if (notification.category) {
                    message += " to your <b>" + notification.category + "</b>";
                }
                break;
            case 'SEARCH':
                message += "You searched <b>" +
                    notification.searchText + "</b>";
                if (notification.category) {
                    message += " in <b>" + notification.category + "</b>";
                }
                break;
            case 'RATING':
                message += "You rated <b>" +
                    notification.book.title + "</b> by <b>" +
                    notification.book.author + "</b>" +
                    " with " + notification.book.rating + " stars";
                break;
        }

        return message;
    }

    function formTimeMessage(timeDiff) {
        let result = "";

        if (timeDiff.diffYear > 0) {
            result = timeDiff.diffYear + " years";
        } else if (timeDiff.diffMonth > 0) {
            result = timeDiff.diffMonth + " months";
        } else if (timeDiff.diffDay > 0) {
            result = timeDiff.diffDay + " days";
        } else if (timeDiff.diffHour > 0) {
            result = timeDiff.diffHour + " hours";
        } else if (timeDiff.diffMinutes > 0) {
            result = timeDiff.diffMinutes + " minutes";
        } else {
            result = "less a minute";
        }

        return result + " ago";
    }

    function loadHistoryBar() {
        Utils.resetInnerHTML(window.document.querySelector(".history_block"));

        for (let i = notificationsModel.storage.length - 1;
             i > notificationsModel.storage.length - HISTORY_BAR_LENGTH - 1 && i >= 0; i--) {
            createHistoryBarBlock(notificationsModel.storage[i]);
        }
    }

    function loadHistoryPage() {
        Utils.resetInnerHTML(window.document.querySelector(".history_content"));

        for (let elem of window.document.querySelectorAll(".nav_menu a")) {
            elem.classList.remove("active")
        }
        window.document.querySelector(".nav_menu .history").classList
            .add("active");

        window.document.querySelector(
            ".main .browse").classList.remove("block");
        window.document.querySelector(
            ".main .browse").classList.add("hidden");

        for (let notification of notificationsModel.storage) {
            createHistoryPageBlock(notification);
        }
    }

    function createHistoryPageBlock(notification) {
        let template = window.document.querySelector("#history_page_template");
        let historyPage = window.document.querySelector(".history_content");

        let historyBlock = template.content.cloneNode(true);

        historyBlock.querySelector("p").innerHTML =
            formNotificationMessage(notification);

        let date = notification.date;
        let dateString = date.getFullYear() + "." + (date.getMonth() + 1) +
            "." + date.getDate() + " " + date.getHours() + ":" +
            date.getMinutes() +
            ":" + date.getSeconds();

        historyBlock.querySelector(".history_log").innerHTML +=
            dateString;

        historyPage.appendChild(historyBlock);
    }

    window.document.querySelector(".nav_menu .history")
        .addEventListener("click", function () {
            loadHistoryPage();
        });

    window.document.querySelector("#search")
        .addEventListener("input", function () {
            let searchText = window.document.querySelector("#search").value;
            let activeCategory = window.document.querySelector(
                ".main_sort .sort .active");
            notificationsController.addSearchNotification(searchText, activeCategory.innerHTML);
        });

    model.onNotificationAdd.subscribe(function () {
        loadHistoryBar();

        if (window.document.querySelector(".history_content").innerHTML !== "") {
            loadHistoryPage();
        }
    });

    return {
        loadHistoryBar
    };
}