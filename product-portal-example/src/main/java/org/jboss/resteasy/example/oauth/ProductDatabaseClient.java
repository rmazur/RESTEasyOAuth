package org.jboss.resteasy.example.oauth;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.skeleton.key.SkeletonKeySession;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.List;
//Regis added logger
import org.jboss.logging.Logger;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @author <a href="mailto:rmazur@redhat.com">Regis Mazur</a>
 * @version $Revision: 2 $
 */
public class ProductDatabaseClient
{
	// Regis added logger
	private static final Logger log = Logger.getLogger(ProductDatabaseClient.class);
	
	public static List<String> getProducts(HttpServletRequest request)
   {
      SkeletonKeySession session = (SkeletonKeySession)request.getAttribute(SkeletonKeySession.class.getName());
      ResteasyProviderFactory factory = new ResteasyProviderFactory();
      RegisterBuiltin.register(factory);
      ResteasyClient client = new ResteasyClientBuilder()
//                 .providerFactory(factory)
                 .trustStore(session.getMetadata().getTruststore())
                 .hostnameVerification(ResteasyClientBuilder.HostnameVerificationPolicy.ANY).build();
      
      
      try
      {
    	  // Regis added logger
    	  log.info("-----------");
    	  log.info("--- OAuth2 Token = " + session.getToken());
    	  log.info("-----------");
    	  Response response = client.target("https://localhost:8443/database/products").request()
                 .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getToken()).get();
         return response.readEntity(new GenericType<List<String>>(){});
      }
      finally
      {
         client.close();
      }
   }
}
