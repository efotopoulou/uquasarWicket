package spring.integration;

import com.atlassian.jira.rpc.soap.client.*;
import com.atlassian.jira_soapclient.SOAPClient;
import jira.integration.Issue;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/17/13
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("projectInfo")
public class ProjectInfo {


    public LinkedList<Issue> issues = new LinkedList<Issue>();
    public Map<String, Issue> map = Collections.synchronizedMap(new HashMap<String, Issue>());

    public RemoteServerInfo getJiraServerInfo() throws MalformedURLException, RemoteException {

        SOAPClient sc = SOAPClient.getInstance();

        RemoteServerInfo rsi = sc.getRemoteServerInfo();

        return rsi;
    }

    public RemoteProject[] getJiraProjects() throws MalformedURLException {

       SOAPClient sc= null;
       RemoteProject[] remoteProjects = null;


        try {
            sc =    SOAPClient.getInstance();
            remoteProjects = sc.getProjectsNoSchemes();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {

            try {
                sc = new SOAPClient();
                remoteProjects = sc.getProjectsNoSchemes();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }


        return remoteProjects;
    }


    public  RemoteIssue[] getIssuesByProjectKey (String projectKey) throws MalformedURLException {

        SOAPClient sc = null;
        RemoteIssue[] remoteIssues = null;
        try {
            sc = SOAPClient.getInstance();
             remoteIssues = sc.getIssues(projectKey);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {


            try {
                sc = new SOAPClient();
                remoteIssues = sc.getIssues(projectKey);
            } catch (RemoteException e1) {
              e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return remoteIssues;
    }

    public RemoteUser getUser (String username) throws MalformedURLException {

        SOAPClient sc = null;
        RemoteUser user = null;
        try {
            sc = SOAPClient.getInstance();
            user = sc.getUser(username);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {


            try {
                sc = new SOAPClient();
                user = sc.getUser(username);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        return user;
    }

    public  RemoteFilter[] getFavouriteFilters () throws MalformedURLException {

        SOAPClient sc = null;
        RemoteFilter[] remoteFilters = null;
        try {
            sc = SOAPClient.getInstance();
            remoteFilters = sc.getFavouriteFilters();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {


            try {
                sc = new SOAPClient();
                remoteFilters = sc.getFavouriteFilters();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return remoteFilters;
    }


}
