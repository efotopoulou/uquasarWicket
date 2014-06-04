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

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/10/13
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectDataProvider      implements IDataProvider<Project> {

    Map<String, Project> map = Collections.synchronizedMap(new HashMap<String, Project>());
    JSONArray projectsJSONArray;

    public ProjectDataProvider(JSONArray projectsJSONArray) {
        this.projectsJSONArray = projectsJSONArray;
       System.out.println("ProjectDataProvider size" + this.projectsJSONArray.length());
    }



    protected LinkedList<Project> getProjects() {
        LinkedList<Project> projects = new LinkedList<Project>();

        try {
            for (int i = 0; i < projectsJSONArray.length(); i++) {
                JSONObject remoteProject = null;

                    remoteProject = projectsJSONArray.getJSONObject(i);


                System.out.println("key"+remoteProject.getString("key"));



               // System.out.println("Tora tha dimiourgiso to Project..." +remoteProject.getKey()+"  "+remoteProject.getDescription()+"  "+remoteProject.getProjectUrl()+"  "+remoteProject.getUrl()+"  "+remoteProject.getLead());

                Project p =   new Project(remoteProject.getString("key"),remoteProject.getString("self"),remoteProject.getString("name"));
                //System.out.println("To dimiourgisa to Project??" +remoteProject.getKey()+"  "+remoteProject.getDescription()+"  "+remoteProject.getProjectUrl()+"  "+remoteProject.getUrl()+"  "+remoteProject.getLead());
                projects.add(p);
                map.put(p.getKey(),p);

            }
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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

