let Utils = function () {

    function setElementProperty(element, property, value) {
        element[property] = value;
    }

    function resetInnerHTML(element) {
        setElementProperty(element, "innerHTML", "");
    }

    function resetValue(element) {
        setElementProperty(element, "value", "");
    }

    function isEmpty(string) {
        return string.toString().trim() === "";
    }

    return {
        resetInnerHTML,
        resetValue,
        isEmpty
    }

}();

