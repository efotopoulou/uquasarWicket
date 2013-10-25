package gridview.example;

import core.Contact;
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
public class DetachableContactModel extends LoadableDetachableModel<Contact>
{
    private final long id;


    protected Map<Long, Contact> getmap(){
        //ContactGenerator cg = ContactGenerator.getInstance();

        Map<Long, Contact> map = Collections.synchronizedMap(new HashMap<Long, Contact>());
        Contact ena = new Contact();
        ena.setFirstName("Eleni");
        ena.setLastName("Fotopoulou");
        Contact dio = new Contact();
        dio.setFirstName("Maria");
        dio.setLastName("zormpaki");
        Contact tria = new Contact();
        tria.setFirstName("eftixia");
        tria.setLastName("Sirma");

        map.put(1L,ena);
        map.put(2L,dio);
        map.put(3L,tria);



        //return cg.getmap();
        return  map;
    }



    /**
     * @param c
     */
    public DetachableContactModel(Contact c)
    {
        this(c.getId());
    }

    /**
     * @param id
     */
    public DetachableContactModel(long id)
    {
        if (id == 0)
        {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return Long.valueOf(id).hashCode();
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
        else if (obj instanceof DetachableContactModel)
        {
            DetachableContactModel other = (DetachableContactModel)obj;
            return other.id == id;
        }
        return false;
    }

    /**
     * @see org.apache.wicket.model.LoadableDetachableModel#load()
     */
    @Override
    protected Contact load()
    {
        // loads contact from the database
        return getmap().get(id);
    }



}


