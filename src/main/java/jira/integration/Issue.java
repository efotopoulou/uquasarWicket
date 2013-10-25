package jira.integration;

import org.apache.wicket.util.io.IClusterable;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/16/13
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class Issue implements IClusterable {


    private String Key;
    private  String Summary;
    private  String Type;
    private  String Status;
    private  String Description;
    private  String Environment;
    private  String Assignee;
    private  String Priority;
    private  String Project;
    private  String Created;
    private  String Duedate;


    public Issue(String key, String summary, String type, String status, String description, String environment, String assignee, String priority, String project, Calendar created, Calendar duedate) {
        Key = key;
        Summary = summary;
        Type = type;
        Status = status;
        Description = description;
        Environment = environment;
        Assignee = assignee;
        Priority = priority;
        Project = project;
        //Created = created.getTime().toString();
        //Duedate = duedate.getTime().toString();
    }

    public Issue(String key,  String description) {
        this.Key = key;
        this.Description=description;
    }

    public Issue() {

    }

    @Override
    public String toString() {
        return "[Issue key=" + Key + " Summary=" + Summary + " Descripiton=" + Description+ "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (obj instanceof Issue)
        {
            Issue other = (Issue)obj;
            return other.getKey().equals(getKey()) && other.getDescription().equals(getDescription())  && other.getSummary().equals(getSummary());

        }
        else
        {
            return false;
        }
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getEnvironment() {
        return Environment;
    }

    public void setEnvironment(String environment) {
        Environment = environment;
    }

    public String getAssignee() {
        return Assignee;
    }

    public void setAssignee(String assignee) {
        Assignee = assignee;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public String getProject() {
        return Project;
    }

    public void setProject(String project) {
        Project = project;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    public String getDuedate() {
        return Duedate;
    }

    public void setDuedate(String duedate) {
        Duedate = duedate;
    }

}
