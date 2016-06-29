define([
    "app/organizationStructurePage",
    "app/allEmployeesPage",
    "app/orderPage",
    "models/employeeModel",
    "dojo/dom",
    "dojo/dom-construct",
    "dijit/layout/BorderContainer",
    "dijit/layout/TabContainer",
    "dijit/layout/ContentPane",
    "dijit/Tree",
    "dojo/store/Memory",
    "dijit/tree/ObjectStoreModel",

    "dojo/domReady!"
], function (OrganizationStructurePage, AllEmployeesPage, OrderPage, EmployeeModel, dom, domConstruct, BorderContainer,
             TabContainer, ContentPane, Tree, Memory, ObjectStoreModel) {
    return {
        run: function (user) {
            window.user = user;

            var appLayout = new BorderContainer({
                design: "headline"
            }, "appLayout");

            var leftPanel = new ContentPane({
                region: "left",
                id: "leftWindow",
                "class": "leftPanel",
                splitter: true
            });

            var tabContainer = new TabContainer({
                region: "center",
                id: "tabContainer",
                tabPosition: "top",
                "class": "rightPanel"
            });

            var treeStore = new Memory({
                data: [
                    {id: 'organizationDirectory', name: 'Справочник организации'},
                    {
                        id: 'organizationStructure',
                        name: 'Оргструктура',
                        parent: 'organizationDirectory'
                    },
                    {
                        id: 'allEmployees',
                        name: 'Все сотрудники',
                        parent: 'organizationDirectory'
                    },

                    {id: 'orders', name: 'Поручения'},
                    {id: 'allOrders', name: 'Все поручения', parent: 'orders'},
                    {id: 'myOrders', name: 'Мои поручения', parent: 'orders'},
                    {id: 'ordersToMe', name: 'Поручения мне', parent: 'orders'}
                ],
                getChildren: function (object) {
                    return this.query({parent: object.id});
                }
            });

            var organizationModel = new ObjectStoreModel({
                store: treeStore,
                query: {id: 'organizationDirectory'}
            });

            var orderModel = new ObjectStoreModel({
                store: treeStore,
                query: {id: 'orders'}
            });

            var organizationTree = new Tree({
                model: organizationModel,
                onClick: function (item) {
                    if (item.id === "organizationStructure") {
                        OrganizationStructurePage.openPage(tabContainer);
                    } else if (item.id === "allEmployees") {
                        AllEmployeesPage.openPage(tabContainer);
                    }
                }
            });

            var ordersTree = new Tree({
                model: orderModel,
                onClick: function (item) {
                    if (item.id === "allOrders") {
                        OrderPage.openAllOrdersPage(tabContainer);
                    } else if (item.id === "myOrders") {
                        OrderPage.openMyOrdersPage(tabContainer);
                    } else if (item.id === "ordersToMe") {
                        OrderPage.openOrdersToMePage(tabContainer);
                    }
                }
            });

            leftPanel.addChild(organizationTree);
            leftPanel.addChild(ordersTree);

            appLayout.addChild(leftPanel);
            appLayout.addChild(tabContainer);
            appLayout.startup();
        }
    }
});
