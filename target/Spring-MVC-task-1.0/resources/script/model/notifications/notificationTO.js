function NotificationTO(id, bookId, content, category, type, date) {
    const DEFAULT_DATE = new Date();

    this.id = id;

    this.bookId = bookId;
    this.content = content;
    this.category = category;

    this.type = type;

    this.date = date || DEFAULT_DATE;
}

