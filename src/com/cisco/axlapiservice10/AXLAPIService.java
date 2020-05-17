
package com.cisco.axlapiservice10;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "AXLAPIService", targetNamespace = "http://www.cisco.com/AXLAPIService/", wsdlLocation = "/wsdl/10/AXLAPI.wsdl")
public class AXLAPIService
    extends Service
{

    private final static URL AXLAPISERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(com.cisco.axlapiservice10.AXLAPIService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = com.cisco.axlapiservice10.AXLAPIService.class.getResource("/wsdl/10/");
            url = new URL(baseUrl, "AXLAPI.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'file:/C:/JAVAOxygen/PHORKPE/bin/wsdl/10/AXLAPI.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        AXLAPISERVICE_WSDL_LOCATION = url;
    }

    public AXLAPIService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AXLAPIService() {
        super(AXLAPISERVICE_WSDL_LOCATION, new QName("http://www.cisco.com/AXLAPIService/", "AXLAPIService"));
    }

    /**
     * 
     * @return
     *     returns AXLPort
     */
    @WebEndpoint(name = "AXLPort")
    public AXLPort getAXLPort() {
        return super.getPort(new QName("http://www.cisco.com/AXLAPIService/", "AXLPort"), AXLPort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AXLPort
     */
    @WebEndpoint(name = "AXLPort")
    public AXLPort getAXLPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.cisco.com/AXLAPIService/", "AXLPort"), AXLPort.class, features);
    }

}
