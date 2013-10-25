package core;

import com.ubi.uquasar.JiraserverInfo;
import login.example.WicketHttpsSession;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.https.HttpsConfig;
import org.apache.wicket.protocol.https.HttpsMapper;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.stereotype.Component;


/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see  com . ubi. uquasar. Start #main(String[])
 */
@Component("wicketApplication")
public class WicketApplication extends WebApplication
{    	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return JiraserverInfo.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

        setRootRequestMapper(new HttpsMapper(getRootRequestMapper(), new HttpsConfig(8080,8443)));

        getComponentInstantiationListeners().add(new SpringComponentInjector(this));



		// add your configuration here
	}



    @Override
    public Session newSession(Request request, Response response) {
        return new WicketHttpsSession(request);
    }



}
