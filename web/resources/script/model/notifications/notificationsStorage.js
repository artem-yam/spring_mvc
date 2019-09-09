function NotificationsStorage() {
    "use strict";

    return [
        new NotificationTO(1, new Book(null, "Fight Club", "Chuck Palahniuk"),
            null, "Must Read Titles", notificationType.ADD_BOOK,
            new Date(2019, 0, 1)
        ),
        new NotificationTO(2, new Book(null, "The Trial", "Franz Kafka"),
            null, "Must Read Titles", notificationType.ADD_BOOK,
            new Date(2019, 0, 1))
    ];
}