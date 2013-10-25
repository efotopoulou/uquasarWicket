package jira.integration;

import org.apache.wicket.model.LoadableDetachableModel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/10/13
 * Time: 3:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class DetachableProjectModel extends LoadableDetachableModel<Project>
{

    private final String key;
    Map<String, Project> map = Collections.synchronizedMap(new HashMap<String, Project>());
    /**
     * @param c
     */
    public DetachableProjectModel(Project c, Map<String, Project>  map)
    {
        this(c.getKey());

        this.map=map;
    }

    /**
     * @param key
     */
    public DetachableProjectModel(String key)
    {
        if (key == null)
        {
            throw new IllegalArgumentException();
        }
        this.key = key;
    }

    /**
     * used for dataview with ReuseIfModelsEqualStrategy item reuse strategy
     *
     * @see org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(final Object obj)
    {
        if (obj == this)
        {
            return true;
        }
        else if (obj == null)
        {
            return false;
        }
        else if (obj instanceof DetachableProjectModel)
        {
            DetachableProjectModel other = (DetachableProjectModel)obj;
            return other.key == key;
        }
        return false;
    }

    /**
     * @see org.apache.wicket.model.LoadableDetachableModel#load()
     */
    @Override
    protected Project load()
    {
        // loads contact from the database
        return map.get(key);
    }



}


