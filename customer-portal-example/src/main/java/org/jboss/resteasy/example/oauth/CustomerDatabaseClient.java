package org.jboss.resteasy.example.oauth;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.skeleton.key.SkeletonKeySession;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @author <a href="mailto:rmazur@redhat.com">Regis Mazur</a>
 * @version $Revision: 2 $
 */
public class CustomerDatabaseClient
{
   public static List<String> getCustomers(HttpServletRequest request)
   {
      SkeletonKeySession session = (SkeletonKeySession)request.getAttribute(SkeletonKeySession.class.getName());
      ResteasyClient client = new ResteasyClientBuilder()
                 .trustStore(session.getMetadata().getTruststore())
                 .hostnameVerification(ResteasyClientBuilder.HostnameVerificationPolicy.ANY).build();
      try
      {
         Response response = client.target("https://localhost:8443/database/customers").request()
                 .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getToken()).get();
         return response.readEntity(new GenericType<List<String>>(){});
      }
      finally
      {
         client.close();
      }
   }
}
