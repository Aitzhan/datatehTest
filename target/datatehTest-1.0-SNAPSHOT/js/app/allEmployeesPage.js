define([
    "dijit/layout/ContentPane",
    "dojo/data/ObjectStore",
    "dojo/store/JsonRest",
    "common/customizedGrid",
    "app/editPage/editEmployeePage",
    "common/topMenu"
], function (ContentPane, ObjectStore, JsonRest, MyGrid, EditEmployeePage, TopMenu) {
    return {
        openPage: function (tabContainer) {
            var tab = new ContentPane({
                title: "Все сотрудники",
                closable: true
            });

            var grid;

            var topMenu = TopMenu.createMenu([
                {
                    title: "Создать",
                    onClick: function () {
                        EditEmployeePage.create(tabContainer, grid)
                    }
                },
                {
                    title: "Изменить",
                    onClick: function () {
                        if (selectedRowId != null) {
                            EditEmployeePage.edit(selectedRowId, tabContainer, grid);
                        }
                        else {
                            alert("Выберите строку для редактирования");
                        }
                    }
                }]);
            tab.addChild(topMenu);

            var store = new ObjectStore({
                objectStore: new JsonRest({
                    target: "rest/employee",
                    idProperty: "employeeId"
                })
            });

            var structure = [
                {field: "employeeId", name: 'id', width: '0px'},
                {
                    field: "employee",
                    name: "ФИО",
                    formatter: function (rowData) {
                        return rowData.lastName + ' ' + rowData.firstName + ' ' + rowData.patronymic
                    }
                },
                {
                    field: "subdivisionTitle",
                    name: "Подразделение",
                    formatter: function (rowData) {
                        if (rowData.subdivision === undefined) return '';
                        else return rowData.subdivision.title;
                    }
                },
                {field: "position", name: "Должность"}
            ];

            grid = MyGrid.getGrid(store, structure);
            grid.on("rowDblClick", function (evt) {
                console.log("row clicked " + evt.rowId);
                EditEmployeePage.edit(evt.rowId, tabContainer, grid);
            });

            var selectedRowId = null;
            grid.on("rowClick", function (evt) {
                selectedRowId = evt.rowId
            });

            tab.addChild(grid);

            console.log("all employees open");

            tabContainer.addChild(tab);
            tabContainer.selectChild(tab);
        }
    }
});
