package spring.integration;

import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/14/13
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("helloService")
public class HelloService {

    public String getMessage() {
        return "Hello World!";
    }

}
