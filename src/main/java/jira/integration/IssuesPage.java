package jira.integration;

import com.ubi.uquasar.menu;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
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
public class IssuesPage extends WebPage {

    //make Google selected by default
    private String selected = "project";
    IDataProvider<Issue> dataProvider;


    @SpringBean
    protected ProjectInfo projectInfo;

    public IssuesPage(final PageParameters parameters) throws MalformedURLException, RemoteException, JSONException {
        super(parameters);

        // add menu
        add(new menu("menu"));

        final WebMarkupContainer wmc = new WebMarkupContainer("wmc");
        wmc.setOutputMarkupId(true);
        add(wmc);

        JSONArray IssuesJsonArray =  projectInfo.callJiraAdapter("ISSUES_PER_PROJECTS_PER_SYSTEM_INSTANCE");

        dataProvider = new IssueDataProvider(IssuesJsonArray);

        DataView<Issue> dataView = new DataView<Issue>("simple", dataProvider) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<Issue> item) {
                Issue issue = item.getModelObject();
                item.add(new Label("key", issue.getKey()));
                item.add(new Label("self", issue.getSelf()));


                item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public String getObject() {
                        return (item.getIndex() % 2 == 1) ? "even" : "odd";
                    }
                }));
            }
        };

        AjaxPagingNavigator navigator = new AjaxPagingNavigator("navigator", dataView) {
            @Override
            protected void onAjaxEvent(AjaxRequestTarget target) {
                target.add(wmc);
            }
        };
        navigator.setEnabled(true);
        wmc.add(navigator);

        dataView.setItemsPerPage(50);
        wmc.add(dataView);
        //wmc.add(new PagingNavigator("navigator", dataView));

       // wmc.add(listSites);



    }

}


