
package jira.integration;

import com.atlassian.jira.rpc.soap.client.RemoteProject;
import com.ubi.uquasar.menu;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.spring.injection.annot.SpringBean;
import spring.integration.ProjectInfo;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/9/13
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectsGridViewPage extends WebPage {

    //make Google selected by default
    private String selected = "Google";


    @SpringBean
    protected ProjectInfo projectInfo;

    public ProjectsGridViewPage() throws MalformedURLException, RemoteException {

        // add menu
        add(new menu("menu"));

      //show projects griedView

        RemoteProject[] remoteProjects = projectInfo.getJiraProjects();
        System.out.println("Debugging" + remoteProjects.length);

        IDataProvider<Project> dataProvider = new ProjectDataProvider(remoteProjects);
        System.out.println("dataprovider "+dataProvider.size());
        GridView<Project> gridView = new GridView<Project>("rows", dataProvider)
        {
            @Override
            protected void populateItem(Item<Project> item)
            {
                final Project project =  item.getModelObject();
                item.add(new Label("project", "Project Key " + project.getKey() + "  Description " + project.getDescription() + " Project URL " + project.getProjectUrl() ));
            }

            @Override
            protected void populateEmptyItem(Item<Project> item)
            {
                item.add(new Label("project", "*empty*"));
            }
        };

        gridView.setRows(4);
        gridView.setColumns(1);

        add(gridView);
        add(new PagingNavigator("navigator", gridView));



    }



}



