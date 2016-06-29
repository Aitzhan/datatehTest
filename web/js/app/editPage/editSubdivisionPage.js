define([
    "models/subdivisionModel",
    "models/employeeModel",
    "common/editPageTemplate",
    "dojo/dom-construct",
    "dijit/form/ValidationTextBox",
    "dijit/form/Button",
    "dijit/form/Select",
    "dijit/form/Form"
], function (SubdivisionModel, EmployeeModel, EditPageTemplate, domConstruct, ValidationTextBox, Button, Select, Form) {
    function openPage(tabContainer, grid, mode, subdivision) {
        var tabTitle;
        if (mode == 'edit') {
            tabTitle = 'Редактирование подразделения ' + subdivision.title
        }
        else {
            tabTitle = 'Добавление нового подразделения';
                subdivision = {
                    title: '',
                    contacts: ''
                }
        }
        var tab = EditPageTemplate.getEditTab(tabTitle);
        var form = new Form({});
        tab.addChild(form);

        var table = domConstruct.create("table", {}, form.domNode);

        var titleInput = new ValidationTextBox({
            value: subdivision.title,
            pattern: ".{1,20}",
            required: true,
            invalidMessage: "Поле может содержать не более 20 символов"
        });

        var contactsInput = new ValidationTextBox({
            value: subdivision.contacts,
            pattern: ".{0,100}",
            invalidMessage: "Поле может содержать не более 100 символов"
        });

        var employees = [];

        var leaderSelect;
        EmployeeModel.getList().then(function (employeeList) {
            var options = [];

            employees = employeeList;

            dojo.forEach(employeeList, function (employee) {
                options.push({
                    label: employee.lastName + ' ' + employee.firstName,
                    value: employee.employeeId,
                    selected: function () {
                        if (mode === 'create') return false;
                        return employee.employeeId === subdivision.leader.employeeId
                    }
                })
            });

            leaderSelect = new Select({
                options: options
            });

            EditPageTemplate.addInput('Руководитель', leaderSelect, table);
        });


        EditPageTemplate.addInput('Название подразделения', titleInput, table);
        EditPageTemplate.addInput('Контакты', contactsInput, table);

        var saveButton = new Button({
            label: 'Сохранить',
            onClick: function () {
                if (titleInput.isValid() && contactsInput.isValid()) {
                    subdivision.title = titleInput.value;
                    subdivision.contacts = contactsInput.value;
                    subdivision.leader = employees.find(function (element, index, array) {
                        if (element.employeeId === leaderSelect.value) return element;
                    });
                    if (mode === 'edit') {
                        SubdivisionModel.update(subdivision).then(
                            function (updateResult) {
                                console.log("update result: " + updateResult);
                                var confirm = new dojo.dijit.Dialog({
                                    content: "Изменения удачно сохранены"
                                });
                                confirm.show();
                                tabContainer.removeChild(tab);
                                EditPageTemplate.refreshGrid(grid);
                            }
                        );
                    } else {
                        subdivision.organization = window.user.subdivision.organization;
                        SubdivisionModel.store(subdivision).then(
                            function () {
                                console.log("subdivision created");
                                var confirm = new dojo.dijit.Dialog({
                                    content: "Новое подразделение добавлено"
                                });
                                confirm.show();
                                tabContainer.removeChild(tab);
                                EditPageTemplate.refreshGrid(grid);
                            }
                        )
                    }
                }
            }
        });

        domConstruct.place(saveButton.domNode, form.domNode);

        tabContainer.addChild(tab);
        tabContainer.selectChild(tab);
    }

    return {
        edit: function (subdivisionId, tabContainer, grid) {
            SubdivisionModel.get(subdivisionId).then(function (subdivision) {
                openPage(tabContainer, grid, 'edit', subdivision);
            });
        },
        create: function (tabContainer, grid) {
            openPage(tabContainer, grid, 'create');
        }
    }
});
