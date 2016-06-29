define([
    "dijit/layout/ContentPane",
    "dojo/store/JsonRest",
    "dojo/data/ObjectStore",
    "common/customizedGrid",
    "app/editPage/editSubdivisionPage",
    "common/topMenu"
], function (ContentPane, JsonRest, ObjectStore, MyGrid, EditSubdivisionPage, TopMenu) {
    return {
        openPage: function (tabContainer) {
            var tab = new ContentPane({
                    title: "Структура организации",
                    closable: true
                }
            );

            var grid;

            var topMenu = TopMenu.createMenu([
                {
                    title: "Создать",
                    onClick: function () {
                        EditSubdivisionPage.create(tabContainer, grid);
                    }
                },
                {
                    title: "Изменить",
                    onClick: function () {
                        if (selectedRowId != null) {
                            EditSubdivisionPage.edit(selectedRowId, tabContainer, grid);
                        }
                        else {
                            alert("Выберите строку для редактирования");
                        }
                    }
                }]);
            tab.addChild(topMenu);

            var store = new ObjectStore({
                objectStore: new JsonRest({
                    target: "rest/organization/0/subdivisions",
                    idProperty: "subdivisionId"
                })
            });

            var structure = [
                {field: "subdivisionId", name: 'id', width: '0px'},
                {field: "title", name: "Наименование"},
                {field: "contacts", name: "Контакты"},
                {
                    field: "leader}", name: "Руководитель", formatter: function (rowData) {
                    return rowData.leader.lastName + ' ' + rowData.leader.firstName + ' ' + rowData.leader.patronymic
                }
                }
            ];

            grid = MyGrid.getGrid(store, structure);
            grid.on("rowDblClick", function (evt) {
                console.log("row clicked " + evt.rowId);
                EditSubdivisionPage.edit(evt.rowId, tabContainer, grid);
            });
            var selectedRowId = null;
            grid.on("rowClick", function (evt) {
                selectedRowId = evt.rowId
            });
            tab.addChild(grid);

            tabContainer.addChild(tab);
            tabContainer.selectChild(tab);
        }
    }
});
