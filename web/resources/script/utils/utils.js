let Utils = function () {
    const AJAX_DATA_TYPE = "application/json";
    const AJAX_RETURN_TYPE = "json";

    const DATA_REFRESH_INTERVAL = 1000;

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

    function sendRequest(url, data, requestType) {
        let ajaxRequest;

        switch (requestType) {
            case 'POST':
                ajaxRequest = $.post({
                    url: url,
                    contentType: AJAX_DATA_TYPE,
                    data: JSON.stringify(data),
                    dataType: AJAX_RETURN_TYPE
                });
                break;
            default:
                ajaxRequest = $.get({
                    url: url,
                    contentType: AJAX_DATA_TYPE,
                    data: JSON.stringify(data),
                    dataType: AJAX_RETURN_TYPE
                });
                break;
        }

        return ajaxRequest;
    }

    return {
        resetInnerHTML,
        resetValue,
        isEmpty,
        sendRequest,
        DATA_REFRESH_INTERVAL
    }

}();

