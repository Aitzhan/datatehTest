define(["dojo/_base/declare", "dojo/request/xhr"], function(declare, xhr){

    var namespace = "rest/subdivision";

    return {
        get: function (subdivisionId) {
            return dojo.xhrGet({
                url: namespace + '/' + subdivisionId,
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