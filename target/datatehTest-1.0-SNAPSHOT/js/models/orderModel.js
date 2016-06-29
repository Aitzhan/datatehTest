define(["dojo/_base/declare"], function (declare) {

    var namespace = "rest/order";

    return {
        getList: function () {
            return dojo.xhrGet({
                url: namespace,
                handleAs: 'json'
            })
        },

        get: function (id) {
            return dojo.xhrGet({
                url: namespace + '/' + id,
                handleAs: 'json'
            })
        },

        getMyOders: function (id) {
            return dojo.xhrGet({
                url: namespace + '/' + id + '/my',
                handleAs: 'json'
            })
        },

        getOdersToMe: function (id) {
            return dojo.xhrGet({
                url: namespace + '/' + id + '/tome',
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
        },

        sendToControl: function (id) {
            return dojo.xhrGet({
                url: namespace + '/' + id + '/sendToControl'
            })
        },

        sendToRework: function (id) {
            return dojo.xhrGet({
                url: namespace + '/' + id + '/sendToRework'
            })
        },

        accept: function(id) {
            return dojo.xhrGet({
                url: namespace + '/' + id + '/accept'
            })
        }
    };
});
