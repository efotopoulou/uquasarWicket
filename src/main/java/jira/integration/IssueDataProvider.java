package jira.integration;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/16/13
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */

import com.atlassian.jira.rpc.soap.client.RemoteIssue;
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
public class IssueDataProvider implements  IDataProvider<Issue> {

    RemoteIssue[] remoteIssues;
    Map<String, Issue> map = Collections.synchronizedMap(new HashMap<String, Issue>());
    LinkedList<Issue> issues = new LinkedList<Issue>();
    private static long nextId = 1;
    private String  projectKey="";

    @SpringBean
    protected ProjectInfo projectInfo;

    public IssueDataProvider(String projectKey) {
        this.projectKey = projectKey;
    }
    public IssueDataProvider(RemoteIssue[] remoteIssues) {
       this.remoteIssues = remoteIssues;
    }

    protected LinkedList<Issue> getIssues() {

       LinkedList<Issue> issues = new LinkedList<Issue>();

        for (RemoteIssue remoteIssue : remoteIssues) {

          Issue i =   new Issue( remoteIssue.getKey(),remoteIssue.getSummary(),remoteIssue.getType(),remoteIssue.getStatus(),remoteIssue.getDescription(),
                  remoteIssue.getEnvironment(),remoteIssue.getAssignee(),remoteIssue.getPriority(), remoteIssue.getProject(), remoteIssue.getCreated(),
                  remoteIssue.getDuedate());
          issues.add(i);
          map.put(i.getKey(),i);
        }
        System.out.println("issues size"+issues.size());



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
