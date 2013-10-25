package jira.integration;

import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import spring.integration.ProjectInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/21/13
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class IssueModel extends LoadableDetachableModel<Issue> {


    @SpringBean
    protected ProjectInfo projectInfo;

    String key;
    Map<String, Issue> map = Collections.synchronizedMap(new HashMap<String, Issue>());


    IssueModel(String id,Map<String, Issue>map)     {
        this.key = key;
        this.map =map;
    }

    @Override
    protected Issue load() {
        return map.get(key);
    }


}
