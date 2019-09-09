function Book(id, title, author, image, rating, tags) {
    const DEFAULT_IMAGE = "images/noCover.jpg";
    const DEFAULT_RATING = 0;

    this.id = id;
    this.title = title;
    this.author = author;
    this.image = image || DEFAULT_IMAGE;
    this.rating = rating || DEFAULT_RATING;
    this.tags = tags || [];
}

