define([
    "models/employeeModel",
    "common/editPageTemplate",
    "dojo/dom-construct",
    "dijit/form/ValidationTextBox",
    "dijit/form/Button",
    "dijit/form/Form"
], function (EmployeeModel, EditPageTemplate, domConstruct, ValidationTextBox, Button, Form) {
    function openPage(tabContainer, grid, mode, employee) {
        var tabName;
        if (mode === 'edit') {
            tabName = 'Редактирование данных пользователя';
        } else {
            tabName = 'Добавление нового пользователя';
            employee = {
                firstName: '',
                secondName: '',
                patronymic: '',
                position: '',
                subdivision: window.user.subdivision
            }
        }

        var tab = EditPageTemplate.getEditTab(tabName);

        var form = new Form({});
        tab.addChild(form);

        var table = domConstruct.create("table", {}, form.domNode);

        var firstNameInput = new ValidationTextBox({
            value: employee.firstName,
            pattern: ".{1,20}",
            required: true,
            invalidMessage: "Поле может содержать не более 20 символов"
        });

        var lastNameInput = new ValidationTextBox({
            value: employee.lastName,
            pattern: ".{0,20}",
            invalidMessage: "Поле может содержать не более 20 символов"
        });

        var patronymicInput = new ValidationTextBox({
            value: employee.patronymic,
            pattern: ".{0,20}",
            invalidMessage: "Поле может содержать не более 20 символов"
        });

        var positionInput = new ValidationTextBox({
            value: employee.position,
            pattern: ".{0,20}",
            invalidMessage: "Поле может содержать не более 20 символов"
        });

        EditPageTemplate.addInput('Имя', firstNameInput, table);
        EditPageTemplate.addInput('Фамилия', lastNameInput, table);
        EditPageTemplate.addInput('Отчество', patronymicInput, table);
        EditPageTemplate.addInput('Должность', positionInput, table);

        var saveButton = new Button({
            label: 'Сохранить',
            onClick: function () {
                if (firstNameInput.isValid() && lastNameInput.isValid() && patronymicInput.isValid()
                    && positionInput.isValid()) {
                    employee.firstName = firstNameInput.value;
                    employee.lastName = lastNameInput.value;
                    employee.patronymic = patronymicInput.value;
                    employee.position = positionInput.value;

                    if (mode === 'edit') {
                        EmployeeModel.update(employee).then(
                            function (updateResult) {
                                console.log("employee updated");
                                var confirm = new dojo.dijit.Dialog({
                                    content: "Изменения удачно сохранены"
                                });
                                confirm.show();
                                tabContainer.removeChild(tab)
                                EditPageTemplate.refreshGrid(grid);
                            }
                        );
                    }
                    else {
                        EmployeeModel.store(employee).then(
                            function () {
                                console.log("Employee created");
                                var confirm = new dojo.dijit.Dialog({
                                    content: "Новый сотрудник успешно добавлен"
                                });
                                confirm.show();
                                tabContainer.removeChild(tab);
                                EditPageTemplate.refreshGrid(grid);
                            }
                        );
                    }
                }
            }
        });

        domConstruct.place(saveButton.domNode, form.domNode);

        tabContainer.addChild(tab);
        tabContainer.selectChild(tab);
    }

    return {
        edit: function (id, tabContainer) {
            EmployeeModel.get(id).then(function (employee) {
                openPage(tabContainer, grid, 'edit', employee)
            });
        },

        create: function (tabContainer) {
            openPage(tabContainer, grid, 'create');
        }
    }
});
