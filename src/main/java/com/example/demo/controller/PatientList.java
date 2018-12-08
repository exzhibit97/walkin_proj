package com.example.demo.controller;

import com.example.demo.model.Patient;
import com.example.demo.repositories.PatientRepository;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * A Designer generated component for the patient-list.html template.
 * <p>
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Route("")
@Tag("patient-list")
@HtmlImport("patient-list.html")
public class PatientList extends PolymerTemplate<PatientList.PatientListModel> {

    @Autowired
    PatientRepository patientRepository;


    @Id("lastName")
    private TextField firstName;
    @Id("firstName")
    private TextField lastName;
    @Id("add")
    private Button add;
    @Id("giveId")
    private TextField giveId;
    @Id("confirmGiveId")
    private Button confirmGiveId;
    @Id("updateNameId")
    private TextField updateNameId;
    @Id("updateName")
    private TextField updateName;
    @Id("grid")
    private Grid<Patient> grid;


    @EventHandler
    public void add() {
        try {
            if (firstName.getValue().length() > 0 && lastName.getValue().length() > 0) {
                Patient p = new Patient(firstName.getValue(), lastName.getValue());
                patientRepository.save(p);
                grid.setItems(patientRepository.findAll());

            } else {
                Notification.show("Warning!" + "You need to fill all fields!", 5000, Notification.Position.MIDDLE);
            }
        } catch (Exception e) {

        }
    }

    @EventHandler
    public void delete() {
        patientRepository.deleteById(Long.parseLong(giveId.getValue()));
    }

    @EventHandler
    public void update() {
        Patient p = patientRepository.getOne(Long.parseLong(updateNameId.getValue()));
        p.setFirstName(updateName.getValue());
        patientRepository.save(p);
    }

    @PostConstruct
    public void displayGrid() {
        grid.setItems(patientRepository.findAll());
        grid.addColumn(Patient::getId).setHeader("id");
        grid.addColumn(Patient::getFirstName).setHeader("imie");
        grid.addColumn(Patient::getLastName).setHeader("nazwisko");
    }

    /**
     * Creates a new PatientList.
     */
    public PatientList() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between PatientList and patient-list.html
     */
    public interface PatientListModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
