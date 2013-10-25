package jira.integration;

import org.apache.wicket.util.io.IClusterable;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/17/13
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Project implements IClusterable {

    private Long id;
    private String Key;
    private String Description;
    private String ProjectUrl;
    private String Url;
    private String Lead;

    public Project(String key, String description, String projectUrl, String url, String lead) {
        Key = key;
        Description = description;
        ProjectUrl = projectUrl;
        Url = url;
        Lead = lead;
    }




    @Override
    public String toString() {
        return "[Project key=" + " Descripiton=" + Description + " ProjectUrl " + ProjectUrl + "]";
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
        if (obj instanceof Project)
        {
            Project other = (Project)obj;
            return other.getKey().equals(getKey()) && other.getDescription().equals(getDescription())  && other.getProjectUrl().equals(getProjectUrl());

        }
        else
        {
            return false;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getProjectUrl() {
        return ProjectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        ProjectUrl = projectUrl;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getLead() {
        return Lead;
    }

    public void setLead(String lead) {
        Lead = lead;
    }







}
