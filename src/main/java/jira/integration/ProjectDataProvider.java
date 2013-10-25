package jira.integration;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/16/13
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */

import com.atlassian.jira.rpc.soap.client.RemoteProject;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/10/13
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectDataProvider implements IDataProvider<Project> {

    Map<String, Project> map = Collections.synchronizedMap(new HashMap<String, Project>());
    RemoteProject[] remoteProjects;

    public ProjectDataProvider(RemoteProject[] remoteProjects) {
        this.remoteProjects = remoteProjects;
        System.out.println("ProjectDataProvider size" + this.remoteProjects.length);
    }

    protected LinkedList<Project> getProjects() {
        LinkedList<Project> projects = new LinkedList<Project>();

        for (RemoteProject remoteProject : remoteProjects) {

            System.out.println("key"+remoteProject.getKey());
            System.out.println("description"+remoteProject.getDescription());
            System.out.println("lead"+remoteProject.getLead());


            System.out.println("Tora tha dimiourgiso to Project..." +remoteProject.getKey()+"  "+remoteProject.getDescription()+"  "+remoteProject.getProjectUrl()+"  "+remoteProject.getUrl()+"  "+remoteProject.getLead());

            Project p =   new Project(remoteProject.getKey(),remoteProject.getDescription(),remoteProject.getProjectUrl(),remoteProject.getUrl(),remoteProject.getLead());
            System.out.println("To dimiourgisa to Project??" +remoteProject.getKey()+"  "+remoteProject.getDescription()+"  "+remoteProject.getProjectUrl()+"  "+remoteProject.getUrl()+"  "+remoteProject.getLead());
            projects.add(p);
            map.put(p.getKey(),p);
        }
        System.out.println("projects size"+projects.size());

        return projects;
    }

    @Override
    public Iterator<Project> iterator(long l, long l1) {
        return getProjects().iterator();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long size() {
        return getProjects().size();  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public IModel<Project> model(Project project) {
        return new DetachableProjectModel(project,map);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void detach() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

