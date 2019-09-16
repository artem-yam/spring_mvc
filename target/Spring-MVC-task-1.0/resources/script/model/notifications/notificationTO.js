function NotificationTO(id, bookId, searchText, category, type, date) {
    const DEFAULT_DATE = new Date();

    this.id = id;

    this.bookId = bookId;
    this.searchText = searchText;
    this.category = category;

    this.type = type;

    this.date = date || DEFAULT_DATE;
}

