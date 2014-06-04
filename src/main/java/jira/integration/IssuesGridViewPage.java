package jira.integration;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.repeater.data.IDataProvider;
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
public class IssuesGridViewPage extends WebPage {

    //make Google selected by default
    private String selected = "project";
    IDataProvider<Issue> dataProvider;


    @SpringBean
    protected ProjectInfo projectInfo;

    public IssuesGridViewPage(final PageParameters parameters) throws MalformedURLException, RemoteException {
            super(parameters);
    /*
        // add menu
        add(new menu("menu"));

        final WebMarkupContainer wmc = new WebMarkupContainer("wmc");
        add(wmc);


        // add dropdown Form
        //add(new FeedbackPanel("feedback"));

        List<String> searchProjects =  new ArrayList<String>();

        RemoteProject[] remoteProjects = projectInfo.getJiraProjects();

        for (RemoteProject remoteProject : remoteProjects) {
            searchProjects.add(remoteProject.getKey());
        }


        if (!parameters.get("project").isNull()){
            selected = parameters.get("project").toString();
        } else  if (searchProjects.size() != 0 ) selected = searchProjects.get(0);

        DropDownChoice<String> listSites = new DropDownChoice<String>(
                "projects", new PropertyModel<String>(this, "selected"), searchProjects);



        Form<?> form = new Form<Void>("form") {
            @Override
            protected void onSubmit() {

                info("Selected Project : " + selected);
                PageParameters pageParameters = new PageParameters();
                pageParameters.set("project", selected);

                setResponsePage(IssuesGridViewPage.class, pageParameters);

            }

        };

         //show projects griedView
        //dataProvider = new IssueDataProvider(selected);
        RemoteIssue[] remoteIssues =  projectInfo.getIssuesByProjectKey(selected);

        dataProvider = new IssueDataProvider(remoteIssues);

        //System.out.println("dataprovider "+dataProvider.size());
        GridView<Issue> gridView = new GridView<Issue>("rows", dataProvider)
        {
            @Override
            protected void populateItem(Item<Issue> item)
            {
                final Issue issue =  item.getModelObject();
                item.add(new Label("issueKey", " Key: "+issue.getKey() +" | Description:  "+ issue.getDescription()));
            }

            @Override
            protected void populateEmptyItem(Item<Issue> item)
            {
                item.add(new Label("issueKey", "*empty*"));
            }
        };

        gridView.setRows(4);
        gridView.setColumns(3);

        //add(gridView);
        //add(new PagingNavigator("navigator", gridView));



        listSites.add(new AjaxFormComponentUpdatingBehavior("change")
        {
            @Override
            protected void onUpdate(AjaxRequestTarget target)
            {
                //wmc.setOutputMarkupId(true);
                //System.out.println("lala"+ selected);
                info("lala Selected Project : " + selected);
                //dataProvider = new IssueDataProvider(selected);



                PageParameters pageParameters = new PageParameters();
                pageParameters.set("project", selected);

                setResponsePage(IssuesGridViewPage.class, pageParameters);




               // target.add(wmc);
            }
        });

        //wmc.add(form);
        wmc.add(listSites);
        wmc.add(gridView);
        wmc.add(new PagingNavigator("navigator", gridView));
        wmc.add(new FeedbackPanel("feedback"));
         */
    }
























}
