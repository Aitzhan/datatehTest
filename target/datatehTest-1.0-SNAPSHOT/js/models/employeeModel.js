define(["dojo/_base/declare"], function (declare) {

    var namespace = "rest/employee";

    return {
        getList: function () {
            return dojo.xhrGet({
                url: namespace,
                handleAs: 'json',
                load: function (response, ioArgs) {
                    return response;
                },
                error: function (response, ioArgs) {
                    // заполняем бд тестовыми данными
                    dojo.xhrGet({
                       url: 'rest//test/create'
                    });
                    return response;
                }
            })
        },

        get: function (id) {
            return dojo.xhrGet({
                url: namespace + '/' + id,
                handleAs: 'json'
            })
        },

        store: function (data) {
            return dojo.xhrPost({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                url: namespace,
                postData: dojo.toJson(data)
            })
        },

        update: function (data) {
            return dojo.xhrPut({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                url: namespace,
                postData: dojo.toJson(data)
            })
        }
    };
});