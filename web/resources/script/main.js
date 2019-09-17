(function () {
    "use strict";

    async function pageLoad() {
        let notificationsModel = new NotificationsModel();
        let booksModel = new BooksModel();

        let controller = new Controller(booksModel, notificationsModel);

        let notificationsView = new NotificationsView(controller,
            notificationsModel);
        let booksView = new BooksView(controller, booksModel);

        await booksModel.initModel();
        await notificationsModel.initModel();

        booksView.browsePage();
        notificationsView.loadHistoryBar();

        //--------------------------------------------------------------------
        // ${pageContext.request.contextPath}

        /*fetch('test', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            }
        }).then(function (response) {
            //alert(response.body);
            //alert(response.json());

            let result = response.json();
            //alert(result);

            return result;
        }).then(function (data) {
            alert(data);
        });*/

        /*let xhr = new XMLHttpRequest();
        xhr.onload = function () {
            if (xhr.status !== 200) { // анализируем HTTP-статус ответа, если статус не 200, то произошла ошибка
                alert(`Ошибка ${xhr.status}: ${xhr.statusText}`); // Например, 404: Not Found
            } else { // если всё прошло гладко, выводим результат
                alert(`Готово, получили ${xhr.response.length} байт`); // response -- это ответ сервера
            }
        };
        xhr.onerror = function () {
            alert("Запрос не удался");
        };
        xhr.open('GET', 'http://localhost:8080/SpringMVC/test');
        xhr.send();
        let resp = xhr.response;*/

    }

    window.addEventListener("load", pageLoad);

    /* $('#buttonDemo3').click(function() {
         $.ajax({
             type : 'GET',
             url : '/api/ajaxrest/demo3',
             dataType : 'json',
             contentType : 'application/json',
             success : function(result) {
                 var s = 'Id: ' + result.id;
                 s += '<br/>Name: ' + result.name;
                 s += '<br/>Price: ' + result.price;
                 $('#result3').html(s);
             }
         });
     });*/

}());