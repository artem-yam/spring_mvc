function Book(id, title, author, imagePath, rating, tags) {
    //const DEFAULT_IMAGE = "resources/images/noCover.jpg";
    const DEFAULT_RATING = 0;

    this.id = id;
    this.title = title;
    this.author = author;

    this.imagePath = imagePath; //|| DEFAULT_IMAGE;

    this.rating = rating || DEFAULT_RATING;
    this.tags = tags || [];
}

