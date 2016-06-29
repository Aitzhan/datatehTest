define([
    "models/orderModel",
    "models/employeeModel",
    "common/editPageTemplate",
    "dojo/dom-construct",
    "dijit/form/ValidationTextBox",
    "dijit/form/Button",
    "dijit/form/Select",
    "dijit/form/SimpleTextarea",
    "dijit/form/DateTextBox",
    "dijit/form/Form"
], function (OrderModel, EmployeeModel, EditPageTemplate, domConstruct, ValidationTextBox, Button, Select, SimpleTextarea, DateTextBox, Form) {
    function openPage(tabContainer, grid, mode, order) {
        var tabName;

        if (mode === 'create') {
            tabName = 'Создание поручения';
            order = {
                orderId: 0,
                subject: '',
                text: '',
                signOfControl: '',
                signOfExecution: '',
                author: window.user,
                status: 'IN_EXECUTION'
            }
        } else {
            tabName = 'Редактирование поручения';
        }
        var tab = EditPageTemplate.getEditTab(tabName);

        var form = new Form({});
        tab.addChild(form);
        var saveButton = new Button({
            label: 'Сохранить',
            onClick: function () {
                if (subjectInput.isValid() && signOfControlInput.isValid()
                    && signOfExecutionInput.isValid()) {
                    order.subject = subjectInput.value;
                    order.text = textInput.value;
                    order.signOfControl = signOfControlInput.value;
                    order.signOfExecution = signOfExecutionInput.value;
                    order.periodOfExecution = dateInput.value;
                    if (mode === 'editToMe') {
                        order.author = employees.find(function (element, index, array) {
                            if (element.employeeId === authorSelect.value) return element;
                        });
                    }
                    if (mode === 'editMy' || mode === 'create') {
                        order.executor = employees.find(function (element, index, array) {
                            if (element.employeeId === executorSelect.value) return element;
                        });
                    }
                    if (mode === 'create') {
                        OrderModel.store(order).then(function () {
                            var confirm = new dojo.dijit.Dialog({
                                content: "Поручение успешно создано"
                            });
                            confirm.show();
                            EditPageTemplate.refreshGrid(grid);
                            tabContainer.removeChild(tab);
                        })
                    }
                    else OrderModel.update(order).then(
                        function (updateResult) {
                            console.log("update result: " + updateResult);

                            var confirm = new dojo.dijit.Dialog({
                                content: "Изменения удачно сохранены"
                            });
                            confirm.show();
                            EditPageTemplate.refreshGrid(grid);
                            tabContainer.removeChild(tab);
                        }
                    );
                }
            }
        });

        domConstruct.place(saveButton.domNode, form.domNode);

        if (mode === 'editMy' && order.status === 'IN_CONTROL') {
            var acceptButton = new Button({
                label: 'Принять',
                onClick: function () {
                    OrderModel.accept(order.orderId).then(function () {
                        var confirm = new dojo.dijit.Dialog({
                            content: "Поручение принято"
                        });
                        confirm.show();
                        EditPageTemplate.refreshGrid(grid);
                        tabContainer.removeChild(tab);
                    })
                }
            });
            var sendToReworkButton = new Button({
                label: 'Отправить на доработку',
                onClick: function () {
                    OrderModel.sendToRework(order.orderId).then(function () {
                        var confirm = new dojo.dijit.Dialog({
                            content: "Поручение отправлено на доработку"
                        });
                        confirm.show();
                        EditPageTemplate.refreshGrid(grid);
                        tabContainer.removeChild(tab);
                    })
                }
            });
            domConstruct.place(acceptButton.domNode, form.domNode);
            domConstruct.place(sendToReworkButton.domNode, form.domNode);
        }
        if (mode === 'editToMe' && (order.status === 'IN_EXECUTION' || order.status === 'IN_REWORK')) {
            var sendToControlButton = new Button({
                label: 'Отправить на контроль',
                onClick: function () {
                    OrderModel.sendToControl(order.orderId).then(function () {
                        var confirm = new dojo.dijit.Dialog({
                            content: "Порчение отправлено на контроль"
                        });
                        confirm.show();
                        EditPageTemplate.refreshGrid(grid);
                        tabContainer.removeChild(tab);
                    });
                }
            });

            domConstruct.place(sendToControlButton.domNode, form.domNode);
        }

        var table = domConstruct.create("table", {}, form.domNode);

        var subjectInput = new ValidationTextBox({
            value: order.subject,
            pattern: ".{1,100}",
            required: true,
            invalidMessage: "Поле может содержать не более 100 символов"
        });

        var textInput = new SimpleTextarea({
            value: order.text
        });

        var signOfControlInput = new ValidationTextBox({
            value: order.signOfControl,
            pattern: ".{0,200}",
            invalidMessage: "Поле может содержать не более 200 символов"
        });

        var signOfExecutionInput = new ValidationTextBox({
            value: order.signOfExecution,
            pattern: ".{0,200}",
            invalidMessage: "Поле может содержать не более 200 символов"
        });

        var employees = [];

        var authorSelect;
        var executorSelect;
        EmployeeModel.getList().then(function (employeeList) {
            var authorOptions = [];

            employees = employeeList;

            if (mode === 'editMy' || mode === 'create') {
                order.author = window.user;
            } else {
                dojo.forEach(employeeList, function (employee) {
                    authorOptions.push({
                        label: employee.lastName + ' ' + employee.firstName,
                        value: employee.employeeId,
                        selected: employee.employeeId === order.author.employeeId
                    })
                });

                authorSelect = new Select({
                    options: authorOptions
                });

                EditPageTemplate.addInput('Автор поручения', authorSelect, table);
            }


            if (mode === 'editToMe') {
                order.executor = window.user;
            } else {
                var executorOptions = [];

                dojo.forEach(employeeList, function (employee) {
                    executorOptions.push({
                        label: employee.lastName + ' ' + employee.firstName,
                        value: employee.employeeId,
                        selected: order.executor !== undefined?employee.employeeId === order.executor.employeeId:false
                    })
                });

                executorSelect = new Select({
                    options: executorOptions
                });


                EditPageTemplate.addInput('Исполнитель поручения', executorSelect, table);
            }
        });

        var dateInput = new DateTextBox({
            value: order.periodOfExecution
        });


        EditPageTemplate.addInput('Предмет поручения', subjectInput, table);
        EditPageTemplate.addInput('Текст поручения', textInput, table);
        EditPageTemplate.addInput('Признак контрольности', signOfControlInput, table);
        EditPageTemplate.addInput('Признак выполненности', signOfExecutionInput, table);
        EditPageTemplate.addInput('Срок исполнения', dateInput, table);

        tabContainer.addChild(tab);
        tabContainer.selectChild(tab);
    }

    return {
        editMyOrder: function (id, tabContainer, grid) {
            OrderModel.get(id).then(function (order) {
                openPage(tabContainer, grid, 'editMy', order);
            });
        },

        editOrderToMe: function (id, tabContainer, grid) {
            OrderModel.get(id).then(function (order) {
                openPage(tabContainer, grid, 'editToMe', order);
            });
        },

        create: function (tabContainer, grid) {
            openPage(tabContainer, grid, 'create');
        }
    }
});
