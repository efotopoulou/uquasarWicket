package com.ubi.uquasar;

import com.atlassian.jira.rpc.soap.client.RemoteServerInfo;
import com.atlassian.jira.rpc.soap.client.RemoteUser;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import spring.integration.ProjectInfo;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/17/13
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class JiraserverInfo  extends WebPage{

    @SpringBean
    protected ProjectInfo projectInfo;

    public JiraserverInfo(final PageParameters parameters) {
        super(parameters);

        add(new menu("menu"));
        try {

            RemoteServerInfo pi = projectInfo.getJiraServerInfo();

            add(new Label("baseurl", pi.getBaseUrl()));
            add(new Label("version", pi.getVersion()));
            add(new Label("servertime", pi.getServerTime().getServerTime()));

            add(new ExternalLink("externalLink1", pi.getBaseUrl()+"/rpc/soap/jirasoapservice-v2?wsdl", "jirasoapservice-v2.wsdl"));

            RemoteUser remoteUser =  projectInfo.getUser("soaptester");
            add(new Label("username", remoteUser.getName()));
            add(new Label("email", remoteUser.getEmail()));


            String version =  getApplication().getFrameworkSettings().getVersion();
            System.out.println("versioooooooooooonn"+version);
            add(new Label("version1",version ));

        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (RemoteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

}
