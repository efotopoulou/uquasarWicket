package jira.integration;

import com.googlecode.wickedcharts.highcharts.options.*;
import com.googlecode.wickedcharts.highcharts.options.color.HexColor;
import com.googlecode.wickedcharts.highcharts.options.color.NullColor;
import com.googlecode.wickedcharts.highcharts.options.color.RgbaColor;
import com.googlecode.wickedcharts.highcharts.options.functions.PercentageAndValueFormatter;
import com.googlecode.wickedcharts.highcharts.options.functions.PercentageFormatter;
import com.googlecode.wickedcharts.highcharts.options.functions.StackTotalFormatter;
import com.googlecode.wickedcharts.highcharts.options.series.Point;
import com.googlecode.wickedcharts.highcharts.options.series.PointSeries;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import com.googlecode.wickedcharts.wicket6.highcharts.Chart;
import com.ubi.uquasar.menu;
import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import spring.integration.ProjectInfo;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.*;

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

    public VisualizeIssuesPage(final PageParameters parameters) throws MalformedURLException, RemoteException, JSONException {
        super(parameters);

        // add menu
        add(new menu("menu"));

        final WebMarkupContainer wmc = new WebMarkupContainer("wmc");
        wmc.setOutputMarkupId(true);
        add(wmc);



        JSONArray totalProjects = projectInfo.callJiraAdapter("PROJECTS_PER_SYSTEM_INSTANCE");
        List<String> searchProjects =  getSearchProjects(totalProjects);

        JSONArray totalIssues =  projectInfo.callJiraAdapter("ISSUES_PER_PROJECTS_PER_SYSTEM_INSTANCE");
        Map totalIssuesCategorized = getIssuesCategorizedPerProject(searchProjects,totalIssues);


        JSONArray totalFixedIssues =  projectInfo.callJiraAdapter("FIXED_ISSUES_PER_PROJECT");
        Map totalFixedIssuesCategorized = getIssuesCategorizedPerProject(searchProjects,totalFixedIssues);


        JSONArray totalUnresolvedIssues =  projectInfo.callJiraAdapter("UNRESOLVED_ISSUES_PER_PROJECT");
        Map totalUnresolvedIssuesCategorized = getIssuesCategorizedPerProject(searchProjects,totalUnresolvedIssues);

        JSONArray totalUnresolvedBugIssues =  projectInfo.callJiraAdapter("UNRESOLVED_BUG_ISSUES_PER_PROJECT");
        Map totalUnresolvedBugIssuesCategorized = getIssuesCategorizedPerProject(searchProjects,totalUnresolvedBugIssues);

        JSONArray totalUnresolvedTaskIssues =  projectInfo.callJiraAdapter("UNRESOLVED_TASK_ISSUES_PER_PROJECT");
        Map totalUnresolvedTaskIssuesCategorized = getIssuesCategorizedPerProject(searchProjects,totalUnresolvedTaskIssues);


        double totalFixedIssuesSize =  totalFixedIssues.length();
        double totalUnresolvedBugIssuesSize =  totalUnresolvedBugIssues.length();
        double totalUnresolvedTaskIssuesSize =  totalUnresolvedTaskIssues.length();



       /*
       *  wicked Charts - Basic Bar
       *
       */
        Options barOptions = new Options();
        barOptions.setTitle(new Title("My Chart"));
        barOptions.setChartOptions(new ChartOptions()
                .setType(SeriesType.BAR));

        barOptions.setGlobal(new Global()
                .setUseUTC(Boolean.TRUE));

        barOptions.setTitle(new Title("UQASAR Analitics by Jira Instance (Bar Chart)"));

        barOptions.setSubtitle(new Title("Jira Instance at: http://95.211.223.9:8084"));



        barOptions.setxAxis(new Axis()
                .setCategories(searchProjects)
                .setTitle(new Title(null)));

        barOptions.setyAxis(new Axis()
                .setTitle(
                        new Title("Number of issues")
                                .setAlign(HorizontalAlignment.HIGH))
                .setLabels(new Labels().setOverflow(Overflow.JUSTIFY)));

        barOptions.setTooltip(new Tooltip()
                .setFormatter(new Function(
                        "return ''+this.series.name +': '+ this.y;")));

        barOptions.setPlotOptions(new PlotOptionsChoice()
                .setBar(new PlotOptions()
                        .setDataLabels(new DataLabels()
                                .setEnabled(Boolean.TRUE))));

        barOptions.setLegend(new Legend()
                .setLayout(LegendLayout.VERTICAL)
                .setAlign(HorizontalAlignment.RIGHT)
                .setVerticalAlign(VerticalAlignment.TOP)
                .setX(-50)
                .setY(180)
                .setFloating(Boolean.TRUE)
                .setBorderWidth(1)
                .setBackgroundColor(new HexColor("#ffffff"))
                .setShadow(Boolean.TRUE));

        barOptions.setCredits(new CreditOptions()
                .setEnabled(Boolean.FALSE));




        barOptions.addSeries(new SimpleSeries()
                .setName("ISSUES_PER_PROJECTS_PER_SYSTEM_INSTANCE")
                .setData(getValuesForChart(searchProjects, totalIssuesCategorized)));

        barOptions.addSeries(new SimpleSeries()
                .setName("FIXED_ISSUES_PER_PROJECT")
                .setData(getValuesForChart(searchProjects, totalFixedIssuesCategorized)));

        barOptions.addSeries(new SimpleSeries()
                .setName("UNRESOLVED_ISSUES_PER_PROJECT")
                .setData(getValuesForChart(searchProjects, totalUnresolvedIssuesCategorized)));

        barOptions.addSeries(new SimpleSeries()
                .setName("UNRESOLVED_BUG_ISSUES_PER_PROJECT")
                .setData(getValuesForChart(searchProjects, totalUnresolvedBugIssuesCategorized)));



        barOptions.addSeries(new SimpleSeries()
                .setName("UNRESOLVED_TASK_ISSUES_PER_PROJECT")
                .setData(getValuesForChart(searchProjects, totalUnresolvedTaskIssuesCategorized)));



        wmc.add(new Chart("chart", barOptions));


        /*
       *  wicked Charts - Pie
       *
       */
        Options PieOptions = new Options();
        PieOptions.setTitle(new Title("My pie Chart"));

        PieOptions.setChartOptions(new ChartOptions()
                .setPlotBackgroundColor(new NullColor())
                .setPlotBorderWidth(null)
                .setPlotShadow(Boolean.FALSE));

        PieOptions.setTitle(new Title("Total Status of issues in Jira Instance (Pie Chart)"));

        PieOptions.setTooltip(new Tooltip()
                .setFormatter(new PercentageFormatter())
                .setPercentageDecimals(1));

        PieOptions.setPlotOptions(new PlotOptionsChoice()
                .setPie(new PlotOptions()
                        .setAllowPointSelect(Boolean.TRUE)
                        .setCursor(Cursor.POINTER)
                        .setShowInLegend(Boolean.TRUE)
                        .setDataLabels(new DataLabels()
                                .setEnabled(Boolean.TRUE)
                                .setColor(new HexColor("#000000"))
                                .setConnectorColor(new HexColor("#000000"))
                                .setFormatter(new PercentageAndValueFormatter()))));

        PieOptions.addSeries(new PointSeries()
                .setType(SeriesType.PIE)
                .setName("Total Issues Status")
                .addPoint(new Point("FIXED_ISSUES", totalFixedIssuesSize))
                .addPoint(new Point("UNRESOLVED_BUG_ISSUES",totalUnresolvedBugIssuesSize))
                .addPoint(new Point("UNRESOLVED_TASK_ISSUES", totalUnresolvedTaskIssuesSize)
                        .setSliced(Boolean.TRUE)
                        .setSelected(Boolean.TRUE)));




        wmc.add(new Chart("piechart", PieOptions));


        /*
        *  wicked Charts - Stacked column chart
        *
        */
        Options stackedColumnOptions = new Options();
        stackedColumnOptions.setTitle(new Title("My stacked column Chart"));
        stackedColumnOptions.setChartOptions(new ChartOptions()
                .setType(SeriesType.COLUMN));

        stackedColumnOptions.setTitle(new Title("UQASAR Analitics by Jira Instance  (Stacked column chart)"));

        stackedColumnOptions.setxAxis(new Axis()
                .setCategories("TOTAL_ISSUES", "FIXED", "UNRESOLVED", "UNRESOLVED_BUG", "UNRESOLVED_TASK"));

        stackedColumnOptions.setyAxis(new Axis()
                .setMin(0)
                .setTitle(new Title("Number of issues"))
                .setStackLabels(new StackLabels()
                        .setEnabled(Boolean.TRUE)
                        .setStyle(new CssStyle()
                                .setProperty("font-weight", "bold")
                                .setProperty("color", "gray"))));

        stackedColumnOptions.setLegend(new Legend()
                .setAlign(HorizontalAlignment.RIGHT)
                .setX(-100)
                .setVerticalAlign(VerticalAlignment.TOP)
                .setY(20)
                .setFloating(Boolean.TRUE)
                .setBackgroundColor(new HexColor("#FFFFFF"))
                .setBorderColor(new HexColor("#CCCCCC"))
                .setBorderWidth(1)
                .setShadow(Boolean.FALSE));

        stackedColumnOptions.setTooltip(new Tooltip()
                .setFormatter(new StackTotalFormatter()));

        stackedColumnOptions.setPlotOptions(new PlotOptionsChoice()
                .setColumn(new PlotOptions()
                        .setStacking(Stacking.NORMAL)
                        .setDataLabels(new DataLabels()
                                .setEnabled(Boolean.TRUE)
                                .setColor(new HexColor("#FFFFFF")))));


        for (String searchProject : searchProjects) {

            stackedColumnOptions.addSeries(new SimpleSeries()
                    .setName(searchProject)
                    .setData(getLenghtOfMapValues(totalIssuesCategorized,searchProject),
                            getLenghtOfMapValues(totalFixedIssuesCategorized,searchProject),
                            getLenghtOfMapValues(totalUnresolvedIssuesCategorized,searchProject),
                            getLenghtOfMapValues(totalUnresolvedBugIssuesCategorized,searchProject),
                            getLenghtOfMapValues(totalUnresolvedTaskIssuesCategorized,searchProject)));
        }


        wmc.add(new Chart("stackedColumnChart", stackedColumnOptions));

        /*
        *  wicked Charts - Area Spline chart
        *
        */
        Options areaSplineOptions = new Options();
        areaSplineOptions.setChart(new ChartOptions()
                .setType(SeriesType.AREASPLINE));

        areaSplineOptions.setTitle(new Title("UQASAR Analitics by Jira Instance  (Area Spline chart)"));

        areaSplineOptions.setLegend(new Legend()
                .setLayout(LegendLayout.VERTICAL)
                .setAlign(HorizontalAlignment.LEFT)
                .setVerticalAlign(VerticalAlignment.TOP)
                .setX(150)
                .setY(100)
                .setFloating(Boolean.TRUE)
                .setBorderWidth(1)
                .setBackgroundColor(new HexColor("#FFFFFF")));

        areaSplineOptions.setxAxis(new Axis()
                .setCategories(
                        Arrays
                                .asList(new String[]{"TOTAL_ISSUES", "FIXED", "UNRESOLVED", "UNRESOLVED_BUG", "UNRESOLVED_TASK"}))
                .setPlotBands(Collections.singletonList(new PlotBand()
                        .setFrom(4.5f)
                        .setTo(6.5f)
                        .setColor(new RgbaColor(68, 170, 213, .2f)))));

        areaSplineOptions.setyAxis(new Axis()
                .setTitle(new Title("Number of issues")));

        areaSplineOptions.setTooltip(new Tooltip()
                .setFormatter(new Function(" return ''+this.x +': '+ this.y +' units';")));

        areaSplineOptions.setCredits(new CreditOptions()
                .setEnabled(Boolean.FALSE));

        areaSplineOptions.setPlotOptions(new PlotOptionsChoice()
                .setAreaspline(new PlotOptions()
                        .setFillOpacity(0.5f)));


        for (String searchProject : searchProjects) {

            areaSplineOptions.addSeries(new SimpleSeries()
                    .setName(searchProject)
                    .setData(Arrays.asList(new Number[]{getLenghtOfMapValues(totalIssuesCategorized,searchProject),
                            getLenghtOfMapValues(totalFixedIssuesCategorized,searchProject),
                            getLenghtOfMapValues(totalUnresolvedIssuesCategorized,searchProject),
                            getLenghtOfMapValues(totalUnresolvedBugIssuesCategorized,searchProject),
                            getLenghtOfMapValues(totalUnresolvedTaskIssuesCategorized,searchProject)})));

        }

        wmc.add(new Chart("areaSplineChart", areaSplineOptions));
    }

     public List<String> getSearchProjects(JSONArray totalProjects) throws JSONException {
         List<String> searchProjects =  new ArrayList<String>();
         for (int i = 0; i < totalProjects.length(); i++) {
             JSONObject projectName = totalProjects.getJSONObject(i);
             searchProjects.add(projectName.getString("key"));
         }

         return  searchProjects;

     }



    // Create a Map so as to categorize the JSONArray by project
    public Map getIssuesCategorizedPerProject(List<String> searchProjects, JSONArray totalIssues) throws JSONException {


        Map issuescategorized = new HashMap();
        for (String searchProject : searchProjects) {

            JSONArray issues  = new JSONArray();
            for (int i = 0; i < totalIssues.length(); i++) {

                JSONObject issue = totalIssues.getJSONObject(i);
                if (issue.getString("key").contains(searchProject)){

                    issues.put(issue);


                }

            }

            issuescategorized.put(searchProject,issues);

        }

        return issuescategorized;
    }

    // prepare values for charts
    public List<Number>  getValuesForChart(List<String> searchProjects, Map totalIssuesCategorized){

        List<Number> valuesForChart = new ArrayList<Number>();

        for (String project : searchProjects) {

            JSONArray categorizedIssues = (JSONArray) totalIssuesCategorized.get(project);
            valuesForChart.add(categorizedIssues.length());
        }


        return  valuesForChart;

    }

    public int getLenghtOfMapValues(Map IssuesCategorized , String searchProject){

      JSONArray jsonArray = (JSONArray) IssuesCategorized.get(searchProject);
        return jsonArray.length();
    }

}



