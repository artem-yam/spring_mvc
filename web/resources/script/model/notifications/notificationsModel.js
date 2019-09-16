function NotificationsModel() {
    "use strict";

    const NEW_BOOK_CATEGORY = "Library";

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

    function addNotification(book, searchText, category, type) {
        let newNotify = new NotificationTO(getNextId(),
            Object.assign(new Book, book), searchText, category, type);

        notificationStorage.push(newNotify);
        onNotificationAdd.notify();
    }

    function addSearchNotification(searchText, searchCategory) {
        addNotification(null, searchText, searchCategory,
            notificationType.SEARCH);
    }

    function addNewRatingNotification(book) {
        addNotification(book, null, null, notificationType.RATING);
    }

    function addNewBookNotification(book) {
        addNotification(book, null, NEW_BOOK_CATEGORY,
            notificationType.ADD_BOOK);
    }

    function getNextId() {
        return notificationStorage.length + 1;
    }

    function getNotificationsStorage() {
        return notificationStorage;
    }

    function getAllNotifications() {
        return $.ajax({
            url: "notifications/getAll",
            dataType: "json"
        }).then(function (data) {
            alert(data);
            notificationStorage = data;
        });
    }

    async function initModel() {
        await getAllNotifications()
            .then(function () {
                setInterval(getAllNotifications, 1000);
            });
    }

    return {
        getFlowedTime,
        onNotificationAdd,
        initModel,
        getNotificationsStorage,
        addSearchNotification,
        addNewRatingNotification,
        addNewBookNotification
    }
}