package jira.integration;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/16/13
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import spring.integration.ProjectInfo;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/10/13
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */


public class IssueDataProvider implements IDataProvider<Issue> {

    JSONArray issuesJsonArray;
    Map<String, Issue> map = Collections.synchronizedMap(new HashMap<String, Issue>());
    LinkedList<Issue> issues = new LinkedList<Issue>();
    private static long nextId = 1;
    private String  projectKey="";

    @SpringBean
    protected ProjectInfo projectInfo;

    public IssueDataProvider(String projectKey) {
        this.projectKey = projectKey;
    }
    public IssueDataProvider(JSONArray issuesJsonArray) {
       this.issuesJsonArray = issuesJsonArray;
    }

    protected LinkedList<Issue> getIssues() {

       LinkedList<Issue> issues = new LinkedList<Issue>();
        try {
        for (int i = 0; i < issuesJsonArray.length(); i++) {


            JSONObject remoteIssue = issuesJsonArray.getJSONObject(i);
            Issue issue =   new Issue(remoteIssue.getString("self"),remoteIssue.getString("key"));
            issues.add(issue);
            map.put(issue.getKey(),issue);
        }

        System.out.println("issues size"+issues.size());

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return issues;
    }


    @Override
    public Iterator<Issue> iterator(long l, long l1) {
        return getIssues().iterator();
        //return  issues.iterator();
    }

    @Override
    public long size() {
        return getIssues().size();
        //return issues.size();
    }


    @Override
    public IModel<Issue> model(Issue issue) {
         return new DetachableIssueModel(issue,map);
        //return new IssueModel((IModel<Issue>) issue);
    }

    @Override
    public void detach() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
