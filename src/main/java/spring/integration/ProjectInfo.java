package spring.integration;

import eu.uqasar.adapter.exception.uQasarException;
import eu.uqasar.adapter.model.Measurement;
import eu.uqasar.jira.adapter.JiraAdapter;
import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.ajax.json.JSONException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/17/13
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("projectInfo")
public class ProjectInfo {


    JiraAdapter jiraAdapter = new JiraAdapter();
    String bindedSystemURL = "http://95.211.223.9:8084";
    String credentials = "soaptester:soaptester";


    JSONArray    jsonArrayPROJECTS_PER_SYSTEM_INSTANCE = new JSONArray() ;
    JSONArray    jsonArrayISSUES_PER_PROJECTS_PER_SYSTEM_INSTANCE = new JSONArray() ;
    JSONArray    jsonArrayFIXED_ISSUES_PER_PROJECT  = new JSONArray() ;
    JSONArray    jsonArrayUNRESOLVED_ISSUES_PER_PROJECT  = new JSONArray() ;
    JSONArray    jsonArrayUNRESOLVED_BUG_ISSUES_PER_PROJECT  = new JSONArray() ;
    JSONArray    jsonArrayUNRESOLVED_TASK_ISSUES_PER_PROJECT  = new JSONArray() ;



        private static ProjectInfo instance = null;
        protected ProjectInfo() {
            // Exists only to defeat instantiation.
        }
        public static ProjectInfo getInstance() {
            if(instance == null) {
                instance = new ProjectInfo();
            }
            return instance;
        }




    public JSONArray callJiraAdapter(String metric) {

        if (metric.equalsIgnoreCase("PROJECTS_PER_SYSTEM_INSTANCE"))
        {
           if (jsonArrayPROJECTS_PER_SYSTEM_INSTANCE.length()==0) {
               JSONArray toReturn =  fetchMetric(metric);
               jsonArrayPROJECTS_PER_SYSTEM_INSTANCE =toReturn;
               return toReturn;
           }
           else  return  jsonArrayPROJECTS_PER_SYSTEM_INSTANCE;
        }

       else if (metric.equalsIgnoreCase("ISSUES_PER_PROJECTS_PER_SYSTEM_INSTANCE"))
        {
            if (jsonArrayISSUES_PER_PROJECTS_PER_SYSTEM_INSTANCE.length()==0) {
                JSONArray toReturn =  fetchMetric(metric);
                jsonArrayISSUES_PER_PROJECTS_PER_SYSTEM_INSTANCE =toReturn;
                return toReturn;
            }
            else  return  jsonArrayISSUES_PER_PROJECTS_PER_SYSTEM_INSTANCE;
        }

        else if (metric.equalsIgnoreCase("FIXED_ISSUES_PER_PROJECT"))
        {
            if (jsonArrayFIXED_ISSUES_PER_PROJECT.length()==0) {
                JSONArray toReturn =  fetchMetric(metric);
                jsonArrayFIXED_ISSUES_PER_PROJECT =toReturn;
                return toReturn;
            }
            else  return  jsonArrayFIXED_ISSUES_PER_PROJECT;
        }

        else if (metric.equalsIgnoreCase("UNRESOLVED_ISSUES_PER_PROJECT"))
        {
            if (jsonArrayUNRESOLVED_ISSUES_PER_PROJECT.length()==0) {
                JSONArray toReturn =  fetchMetric(metric);
                jsonArrayUNRESOLVED_ISSUES_PER_PROJECT =toReturn;
                return toReturn;
            }
            else  return  jsonArrayUNRESOLVED_ISSUES_PER_PROJECT;
        }

        else  if (metric.equalsIgnoreCase("UNRESOLVED_BUG_ISSUES_PER_PROJECT"))
        {
            if (jsonArrayUNRESOLVED_BUG_ISSUES_PER_PROJECT.length()==0) {
                JSONArray toReturn =  fetchMetric(metric);
                jsonArrayUNRESOLVED_BUG_ISSUES_PER_PROJECT =toReturn;
                return toReturn;
            }
            else  return  jsonArrayUNRESOLVED_BUG_ISSUES_PER_PROJECT;
        }

        else  if (metric.equalsIgnoreCase("UNRESOLVED_TASK_ISSUES_PER_PROJECT"))
        {
            if (jsonArrayUNRESOLVED_TASK_ISSUES_PER_PROJECT.length()==0) {
                JSONArray toReturn =  fetchMetric(metric);
                jsonArrayUNRESOLVED_TASK_ISSUES_PER_PROJECT =toReturn;
                return toReturn;
            }
            else  return  jsonArrayUNRESOLVED_TASK_ISSUES_PER_PROJECT;
        }

        else return fetchMetric(metric);


    }



    public JSONArray fetchMetric(String metric){


        JSONArray measurementResultJSONArray = null;

        List<Measurement>  measurements = null;
        try {
            measurements = jiraAdapter.query(bindedSystemURL,credentials,metric);

            measurementResultJSONArray = new   JSONArray(measurements.get(0).getMeasurement());
        } catch (uQasarException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return  measurementResultJSONArray;

    }
}
