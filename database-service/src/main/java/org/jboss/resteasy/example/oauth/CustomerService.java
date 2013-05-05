package org.jboss.resteasy.example.oauth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @author <a href="mailto:rmazur@redhat.com">Regis Mazur</a>
 * @version $Revision: 2 $
 */
@Path("customers")
public class CustomerService
{
   @GET
   @Produces("application/json")
   public List<String> getCustomers()
   {
      ArrayList<String> rtn = new ArrayList<String>();
      rtn.add("Regis Mazur");
      rtn.add("Niraj Patel");
      rtn.add("Duncan Doyle");
      return rtn;
   }
}
