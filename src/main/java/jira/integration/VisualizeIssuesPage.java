package jira.integration;

import com.atlassian.jira.rpc.soap.client.RemoteIssue;
import com.atlassian.jira.rpc.soap.client.RemoteProject;
import com.googlecode.wickedcharts.highcharts.options.*;
import com.googlecode.wickedcharts.highcharts.options.color.HexColor;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import com.googlecode.wickedcharts.wicket6.highcharts.Chart;
import com.ubi.uquasar.menu;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import spring.integration.ProjectInfo;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/9/13
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class VisualizeIssuesPage extends WebPage {

    //make Google selected by default
    private String selected = "project";
    IDataProvider<Issue> dataProvider;


    @SpringBean
    protected ProjectInfo projectInfo;

    public VisualizeIssuesPage(final PageParameters parameters) throws MalformedURLException, RemoteException {
        super(parameters);

        // add menu
        add(new menu("menu"));

        final WebMarkupContainer wmc = new WebMarkupContainer("wmc");
        wmc.setOutputMarkupId(true);
        add(wmc);


        // add dropdown Form

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

        listSites.add(new AjaxFormComponentUpdatingBehavior("change")
        {
            @Override
            protected void onUpdate(AjaxRequestTarget target)
            {
                //wmc.setOutputMarkupId(true);
                //System.out.println("lala"+ selected);
                info("Selected Project : " + selected);
                //dataProvider = new IssueDataProvider(selected);



                PageParameters pageParameters = new PageParameters();
                pageParameters.set("project", selected);

                setResponsePage(IssuesPage.class, pageParameters);
            }
        });
        RemoteIssue[] remoteIssues =  projectInfo.getIssuesByProjectKey(selected);

        dataProvider = new IssueDataProvider(remoteIssues);

        DataView<Issue> dataView = new DataView<Issue>("simple", dataProvider) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<Issue> item) {
                Issue issue = item.getModelObject();
                item.add(new Label("key", issue.getKey()));
                item.add(new Label("summary", issue.getSummary()));
                item.add(new Label("type", issue.getType()));
                item.add(new Label("status", issue.getStatus()));
                item.add(new Label("description", issue.getDescription()));
                item.add(new Label("environment", issue.getEnvironment()));
                item.add(new Label("asignee", issue.getAssignee()));
                // item.add(new Label("created", issue.getCreated()));
                // item.add(new Label("duedate", issue.getDuedate()));

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

        dataView.setItemsPerPage(30);
        wmc.add(dataView);
        //wmc.add(new PagingNavigator("navigator", dataView));
        wmc.add(listSites);

        /* Line Chart with wicket-charts

        LineChartPanel lineChartPanel = new LineChartPanel("lineChartPanel", Model.of(new Line()));
        PieChartPanel  pieChartPanel = new PieChartPanel("pieChartPanel", Model.of(new Pie()));
        //LineChartPanel lineChartPanel = new LineChartPanel("lineChartPanel", Model.of(new Line()));


        wmc.add(lineChartPanel);


        List<String> labels = new ArrayList<String>();
        labels.add("jan");
        labels.add("feb");
        labels.add("mar");
        labels.add("apr");

        List<Integer> values = new ArrayList<Integer>();
        values.add(4);
        values.add(2);
        values.add(6);
        values.add(7);

        LineChartData<LineDataSet> lineData = new LineChartData<LineDataSet>();
        lineData.getDatasets().add(new LineDataSet(values));

        lineChartPanel.getChart().setData(lineData);
        lineData.setLabels(labels);
        */
       //  With wicked Charts
        Options options = new Options();
        options.setTitle(new Title("My Chart"));
        options.setChartOptions(new ChartOptions()
                .setType(SeriesType.BAR));

        options.setGlobal(new Global()
                .setUseUTC(Boolean.TRUE));

        options.setTitle(new Title("Historic World Population by Region"));

        options.setSubtitle(new Title("Source: Wikipedia.org"));

        options.setxAxis(new Axis()
                .setCategories("Africa", "America", "Asia", "Europe", "Oceania")
                .setTitle(new Title(null)));

        options.setyAxis(new Axis()
                .setTitle(
                        new Title("Population (millions)")
                                .setAlign(HorizontalAlignment.HIGH))
                .setLabels(new Labels().setOverflow(Overflow.JUSTIFY)));

        options.setTooltip(new Tooltip()
                .setFormatter(new Function(
                        "return ''+this.series.name +': '+ this.y +' millions';")));

        options.setPlotOptions(new PlotOptionsChoice()
                .setBar(new PlotOptions()
                        .setDataLabels(new DataLabels()
                                .setEnabled(Boolean.TRUE))));

        options.setLegend(new Legend()
                .setLayout(LegendLayout.VERTICAL)
                .setAlign(HorizontalAlignment.RIGHT)
                .setVerticalAlign(VerticalAlignment.TOP)
                .setX(-100)
                .setY(100)
                .setFloating(Boolean.TRUE)
                .setBorderWidth(1)
                .setBackgroundColor(new HexColor("#ffffff"))
                .setShadow(Boolean.TRUE));

        options.setCredits(new CreditOptions()
                .setEnabled(Boolean.FALSE));

        options.addSeries(new SimpleSeries()
                .setName("Year 1800")
                .setData(107, 31, 635, 203, 2));

        options.addSeries(new SimpleSeries()
                .setName("Year 1900")
                .setData(133, 156, 947, 408, 6));

        options.addSeries(new SimpleSeries()
                .setName("Year 2008")
                .setData(973, 914, 4054, 732, 34));

        wmc.add(new Chart("chart", options));






    }

}



