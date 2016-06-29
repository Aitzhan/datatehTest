define(["dojo/_base/declare", "dojo/request/xhr"], function(declare, xhr){
    var namespace = "rest/organization";

    return {
        get: function(organizationId) {
             return xhr(namespace + "/" + organizationId, {
                 handleAs: "json"
             });
        },
        getSubdivisions: function (organizationId) {
            return dojo.xhrGet({
                url: namespace + '/' + organizationId + "/subdivisions" ,
                 handleAs: "json",
                 load: function(response, ioArgs) {
                     return response;
                 },
                 error: function(response, ioArgs) {
                     return response;
                 }
            })
        }
    };
});
