function NotificationsModel() {
    "use strict";

    const NEW_BOOK_CATEGORY = "Library";

    const AJAX_NOTIFICATION_URL = "notifications";

    let notificationStorage = [];
    let onNotificationAdd = new EventEmitter();

    function getFlowedTime(date) {
        let diffYear = new Date().getFullYear() - date.getFullYear();
        let diffMonth = new Date().getMonth() - date.getMonth();
        if (diffMonth !== 0) {
            diffMonth++;
        }
        let diffDay = new Date().getDate() - date.getDate();
        let diffHour = new Date().getHours() - date.getHours();
        let diffMinutes = new Date().getMinutes() - date.getMinutes();

        return {
            diffYear,
            diffMonth,
            diffDay,
            diffHour,
            diffMinutes
        }
    }

    function addNotification(bookId, content, category, type) {
        let newNotify = new NotificationTO(null,
            bookId, content, category, type);

        Utils.sendRequest(AJAX_NOTIFICATION_URL, newNotify,
            requestType.POST)
            .then(function () {
                onNotificationAdd.notify();
            });

    }

    function addSearchNotification(searchText, searchCategory) {
        addNotification(null, searchText, searchCategory,
            notificationType.SEARCH);
    }

    function addNewRatingNotification(bookId, rating) {
        addNotification(bookId, rating, null, notificationType.RATING);
    }

    function addNewBookNotification(bookId) {
        addNotification(bookId, null, NEW_BOOK_CATEGORY,
            notificationType.ADD_BOOK);
    }

    function getNotificationsStorage() {
        return notificationStorage;
    }

    function getAllNotifications() {
        return Utils.sendRequest(AJAX_NOTIFICATION_URL, null,
            requestType.GET)
            .then(function (data) {
                for (let note of data) {
                    note.date = new Date(note.date);
                }
                notificationStorage = data;
            });
    }

    /*async function initModel() {
        await getAllNotifications()
            .then(function () {
                setInterval(getAllNotifications, Utils.DATA_REFRESH_INTERVAL);
            });
    }*/

    async function refreshModel() {
        await getAllNotifications();
    }

    return {
        getFlowedTime,
        onNotificationAdd,
        refreshModel,
        getNotificationsStorage,
        addSearchNotification,
        addNewRatingNotification,
        addNewBookNotification,
        getAllNotifications
    }
}