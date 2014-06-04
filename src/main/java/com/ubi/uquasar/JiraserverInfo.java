package com.ubi.uquasar;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import spring.integration.ProjectInfo;

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
            add(new Label("baseurl", "http://95.211.223.9:8084"));


            add(new Label("username","soaptester"));
            add(new Label("email", "soaptester"));


            String version =  getApplication().getFrameworkSettings().getVersion();

            add(new Label("version1",version ));
    }

}
