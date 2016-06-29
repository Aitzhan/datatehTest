define([
    "dijit/layout/ContentPane",
    "dojo/dom",
    "dojo/dom-construct"
], function (ContentPane, dom, domConstruct) {

    return {
        getEditTab: function (title) {
            return new ContentPane({
                title: title,
                closable: true
            });
        },

        addInput: function (title, input, table) {
            var tr = domConstruct.create("tr", {}, table);
            var tdLeft = domConstruct.create("td", {}, tr);
            var tdRight = domConstruct.create("td", {}, tr);

            var label = domConstruct.create("label", {
                innerHTML: title
            }, tdLeft);

            domConstruct.place(input.domNode, tdRight);
        },

        refreshGrid: function (grid) {
            if (grid !== undefined) {
                grid.model.clearCache();
                grid.body.refresh();
            }
        }
    };
});
