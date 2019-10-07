function Book(id, title, author, image, rating, tags, deleted) {
    const DEFAULT_RATING = 0;

    this.id = id;
    this.title = title;
    this.author = author;

    this.image = image || [];

    this.rating = rating || DEFAULT_RATING;
    this.tags = tags || [];

    this.deleted = deleted || false;
}

