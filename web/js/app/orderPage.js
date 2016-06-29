define([
    "app/editPage/editOrderPage",
    "dijit/layout/ContentPane",
    "dojo/data/ObjectStore",
    "dojo/store/JsonRest",
    "common/customizedGrid",
    "common/topMenu"
], function (EditOrderPage, ContentPane, ObjectStore, JsonRest, MyGrid, TopMenu) {

    var orderStructure = [
        {field: "orderId", name: 'id', width: '0px'},
        {field: "subject", name: "Предмет поручения"},
        {
            field: "author",
            name: "Автор поручения",
            formatter: function (rowData) {
                return rowData.author.lastName + " " + rowData.author.firstName + " " + rowData.author.patronymic
            }
        },
        {
            field: "executor",
            name: "Исполнитель поручения",
            formatter: function (rowData) {
                return rowData.executor.lastName + " " + rowData.executor.firstName + " " + rowData.executor.patronymic
            }
        },
        {field: "periodOfExecution", name: "Срок исполнения"},
        {field: "signOfControl", name: "Признак контрольности"},
        {field: "signOfExecution", name: "Признак исполнения"},
        {field: "text", name: "Тект поручения"},
        {
            field: "status", name: "Статус", formatter: function (rowData) {
            switch (rowData.status) {
                case "IN_EXECUTION":
                    return 'Исполнение';
                    break;
                case "IN_REWORK":
                    return 'Доработка';
                    break;
                case "IN_CONTROL":
                    return 'Контроль';
                    break;
                case "ACCEPTED":
                    return 'Принят';
                    break;
            }
        }
        }
    ];

    function openPage(tabContainer, mode) {
        var title;
        var restTarget;
        var namespace = 'rest/order';
        var rowDBClick;
        switch (mode) {
            case 'allOrders':
                title = 'Все поручени';
                restTarget = namespace;
                break;
            case 'myOrders':
                title = 'Мои поручения';
                restTarget = namespace + '/' + window.user.employeeId + '/my';
                rowDBClick = function (id, tabContainer) {
                    EditOrderPage.editMyOrder(id, tabContainer, grid);
                };
                break;
            case 'ordersToMe':
                title = 'Поручения мне';
                restTarget = namespace + '/' + window.user.employeeId + '/tome';
                rowDBClick = function (id, tabContainer) {
                    EditOrderPage.editOrderToMe(id, tabContainer, grid);
                };
                break;
            default:
                console.log('error: invalid order mode');
                break;
        }

        var tab = new ContentPane({
            title: title,
            closable: true
        });

        var grid;

        if (mode === 'myOrders') {
            var topMenu = TopMenu.createMenu([
                {
                    title: "Создать поручение",
                    onClick: function () {
                        EditOrderPage.create(tabContainer, grid)
                    }
                }]);
            tab.addChild(topMenu);
        }

        var store = new ObjectStore({
            objectStore: new JsonRest({
                target: restTarget,
                idProperty: "orderId"
            })
        });

        grid = MyGrid.getGrid(store, orderStructure);
        grid.on("rowDblClick", function (evt) {
            console.log("row clicked " + evt.rowId);
            rowDBClick(evt.rowId, tabContainer);
        });
        tab.addChild(grid);

        tabContainer.addChild(tab);
        tabContainer.selectChild(tab);
    }

    return {
        openAllOrdersPage: function (tabContainer) {
            openPage(tabContainer, 'allOrders');
        },

        openMyOrdersPage: function (tabContainer) {
            openPage(tabContainer, 'myOrders');
        },

        openOrdersToMePage: function (tabContainer) {
            openPage(tabContainer, 'ordersToMe');
        }
    }
});
