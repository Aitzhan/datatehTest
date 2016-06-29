define([
    "dojo/dom",
    "dojo/dom-construct",
    "dijit/form/Form",
    "dijit/form/Button"
], function (dom, domConstruct, Form, Button) {
    function getButton(title, onClick) {
        return new Button({
            label: title,
            onClick: onClick
        }
        );
    }
    return {
        createMenu: function (items) {
            var form = new Form();
            dojo.forEach(items, function (item) {
                var button = getButton(item.title, item.onClick);
                domConstruct.place(button.domNode, form.domNode);
            });

            return form;
        }
    }
});
