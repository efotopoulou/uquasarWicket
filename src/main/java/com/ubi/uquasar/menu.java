package com.ubi.uquasar;

import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.border.BoxBorder;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/9/13
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class menu extends Border {

    public menu(final String id)
    {
        super(id);
        //addToBorder(new Label("signedUser",this.getSession().getAttribute("username")));
        addToBorder(new BoxBorder("navigationBorder"));
        addToBorder(new BoxBorder("bodyBorder"));
    }
}
