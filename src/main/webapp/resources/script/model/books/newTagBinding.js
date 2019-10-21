function NewTagBinding(bookId, tag) {
    const DEFAULT_VALUES = "";

    this.bookId = bookId || DEFAULT_VALUES;
    this.tag = tag || DEFAULT_VALUES;
}

