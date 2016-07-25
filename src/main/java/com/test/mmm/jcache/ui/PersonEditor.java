package com.test.mmm.jcache.ui;

import com.test.mmm.jcache.entity.Person;
import com.test.mmm.jcache.repository.PersonRepository;
import com.test.mmm.jcache.service.PersonService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Marian_Mykhalchuk on 7/22/2016.
 */
@SpringComponent
@UIScope
public class PersonEditor extends VerticalLayout {

	private final PersonService personService;
	private Person person;

	private TextField firstName = new TextField("First name");
	private TextField lastName = new TextField("Last name");

	private Button save = new Button("Save", FontAwesome.SAVE);
	private Button cancel = new Button("Cancel");
	private Button delete = new Button("Delete", FontAwesome.TRASH_O);
	private CssLayout actions = new CssLayout(save, cancel, delete);

	@Autowired
	public PersonEditor(PersonService personService) {
		this.personService = personService;

		addComponents(firstName, lastName, actions);

		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		save.addClickListener(e -> personService.save(person));
		delete.addClickListener(e -> personService.delete(person));
		cancel.addClickListener(e -> setVisible(false));
		setVisible(false);
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editPerson(Person person) {
		if (person == null) return;
		final boolean persisted = person.getId() != null;
		if (persisted) {
			this.person = personService.findOne(person.getId());
		} else {
			this.person = person;
		}
		cancel.setVisible(persisted);

		BeanFieldGroup.bindFieldsUnbuffered(this.person, this);

		setVisible(true);

		save.focus();
		firstName.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}