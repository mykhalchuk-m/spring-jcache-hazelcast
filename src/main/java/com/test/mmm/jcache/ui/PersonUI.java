package com.test.mmm.jcache.ui;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.test.mmm.jcache.entity.Person;
import com.test.mmm.jcache.service.PersonService;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * Created by Marian_Mykhalchuk on 7/22/2016.
 */
@SpringUI
@Theme("valo")
public class PersonUI extends UI {

	private final PersonService personService;
	private final PersonEditor editor;
	private final Grid grid;
	private final TextField filter;
	private final Button addNewBtn;


	@Override
	protected void init(VaadinRequest vaadinRequest) {
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		setContent(mainLayout);

		// Configure layouts and components
		actions.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		grid.setHeight(300, Unit.PIXELS);
		grid.setColumns("id", "firstName", "lastName");

		filter.setInputPrompt("Filter by last name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.addTextChangeListener(e -> listPersons(e.getText()));

		// Connect selected Customer to editor or hide if none is selected
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				editor.setVisible(false);
			} else {
				editor.editPerson((Person) grid.getSelectedRow());
			}
		});

		String idStr = vaadinRequest.getParameter("id");
		if (!StringUtils.isEmpty(idStr)) {
			Long id = Long.parseLong(idStr);
			editor.editPerson(personService.findOne(id));
		}

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editPerson(new Person("", "")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listPersons(filter.getValue());
		});

		// Initialize listing
		listPersons(null);
	}

	private void listPersons(String text) {
		if (StringUtils.isEmpty(text)) {
			grid.setContainerDataSource(new BeanItemContainer<>(Person.class, personService.findAll()));
		} else {
			grid.setContainerDataSource(new BeanItemContainer<>(Person.class, personService.findByLastName(text)));
		}
	}

	@Autowired
	PersonUI(PersonService personService, PersonEditor personEditor) {
		this.personService = personService;
		this.editor = personEditor;
		this.grid = new Grid();
		this.filter = new TextField();
		this.addNewBtn = new Button("New customer", FontAwesome.PLUS);
	}
}
