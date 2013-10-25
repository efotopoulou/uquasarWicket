package gridview.example;

import com.ubi.uquasar.menu;
import core.Contact;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/9/13
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class GridViewPage extends WebPage {


    public GridViewPage() throws MalformedURLException, RemoteException {
        add(new menu("menu"));





        IDataProvider<Contact> dataProvider = new ContactDataProvider();

        System.out.println("dataprovider "+dataProvider.size());
        GridView<Contact> gridView = new GridView<Contact>("rows", dataProvider)
        {
            @Override
            protected void populateItem(Item<Contact> item)
            {
                final Contact contact = item.getModelObject();
                item.add(new Label("firstName", contact.getFirstName() + " " +
                        contact.getLastName()));
            }

            @Override
            protected void populateEmptyItem(Item<Contact> item)
            {
                item.add(new Label("firstName", "*empty*"));
            }
        };

        gridView.setRows(1);
        gridView.setColumns(1);

        add(gridView);
        add(new PagingNavigator("navigator", gridView));
    }
}
