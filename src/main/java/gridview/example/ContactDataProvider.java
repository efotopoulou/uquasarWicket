package gridview.example;

import core.Contact;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/10/13
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContactDataProvider  implements IDataProvider<Contact> {


    public ContactDataProvider() {

    }

    protected LinkedList<Contact> getcontacts(){
        LinkedList<Contact> contacts = new LinkedList<Contact>();

        //ContactGenerator cg = ContactGenerator.getInstance();

        //cg.generate(contacts,50);
        Contact ena = new Contact();
        ena.setId(1L);
        ena.setFirstName("Eleni");
        ena.setLastName("Fotopoulou");
        Contact dio = new Contact();
        dio.setId(2L);
        dio.setFirstName("Maria");
        dio.setLastName("zormpaki");
        Contact tria = new Contact();
        tria.setId(3L);
        tria.setFirstName("eftixia");
        tria.setLastName("Sirma");

        contacts.add(ena);
        contacts.add(dio);
        contacts.add(tria);

        return contacts;
    }

    @Override
    public Iterator<Contact> iterator(long l, long l1) {
        return getcontacts().iterator();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long size() {
        return getcontacts().size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IModel<Contact> model(Contact object) {
        return new DetachableContactModel(object);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void detach() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
