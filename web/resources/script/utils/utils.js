let Utils = function () {
    const AJAX_DATA_TYPE = "application/json";

    const DATA_REFRESH_INTERVAL = 1000;

    let onLoginError = new EventEmitter();

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

        if (!requestType) {
            requestType = requestType.GET;
        }

        if (data instanceof FormData) {
            ajaxRequest = $.ajax({
                url: url,
                type: requestType,
                contentType: false,
                processData: false,
                data: data
            });
        } else {

            ajaxRequest = $.ajax({
                url: url,
                type: requestType,
                contentType: AJAX_DATA_TYPE,
                data: JSON.stringify(data)
            });
        }

        ajaxRequest.then(function (data) {
            return data;
        }, function (error) {
            if (error.status) {
                alert("Error 401. User not logged in");
                onLoginError.notify();
            }
            throw error;
        });

        return ajaxRequest;
    }

    return {
        resetInnerHTML,
        resetValue,
        isEmpty,
        sendRequest,
        DATA_REFRESH_INTERVAL,
        onLoginError
    }

}();

