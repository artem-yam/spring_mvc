function Filter(filterName, searchText) {
    const DEFAULT_VALUES = "";

    this.filterName = filterName || DEFAULT_VALUES;
    this.searchText = searchText || DEFAULT_VALUES;
}

