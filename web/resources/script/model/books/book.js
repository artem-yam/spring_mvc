function Book(id, title, author, imagePath, rating, tags, isDeleted) {
    const DEFAULT_RATING = 0;

    this.id = id;
    this.title = title;
    this.author = author;

    this.imagePath = imagePath;

    this.rating = rating || DEFAULT_RATING;
    this.tags = tags || [];

    this.isDeleted = isDeleted || false;
}

