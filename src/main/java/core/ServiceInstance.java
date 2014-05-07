package core;

import common.DPSException;
import mdns.ResourceRecord;
import mdns.enums.RRType;
import mdns.rdata.PTRRData;
import util.CommonUtil;

import static common.Messages.SERVICE_INST_ERR;
import static common.Messages.NON_PTR_ERR;

/**
 * Class for service instance of format instanceName.serviceName.protocol.domain.
 * @author Shaofeng Chen
 * @since 3/29/14
 */
public class ServiceInstance {
    private final String instanceName;
    private final Service service;

    public ServiceInstance(String instanceName, Service service) {
        this.instanceName = instanceName;
        this.service = service;
    }

    /**
     * Construct a ServiceInstance object from it's string representation.
     * @param serviceInstance
     * @return ServiceInstance
     */
    public static ServiceInstance from(String serviceInstance) {
        String[] components = CommonUtil.getComponents(serviceInstance, 4);
        if (components.length != 4) {
            throw new DPSException(String.format(SERVICE_INST_ERR, serviceInstance));
        }
        return new ServiceInstance(components[0], new Service(components[1], components[2],
                components[3]));
    }

    /**
     * Construct a ServiceInstance object from a ResourceRecord type object.
     * @param resourceRecord a PTR type resource record
     * @return ServiceInstance
     */
    public static ServiceInstance from(ResourceRecord resourceRecord) {
        if (!resourceRecord.getRRType().equals(RRType.PTR)) {
            throw new DPSException(NON_PTR_ERR);
        }
        PTRRData ptrrData = (PTRRData) resourceRecord.getRData();
        return ServiceInstance.from(ptrrData.toString());
    }

    /**
     * Check if the given service was provided by service instance object.
     * @param service
     * @return boolean
     */
    public boolean provides(Service service) {
        return this.service.equals(service);
    }

    public Service getService() {
        return service;
    }

    public String getInstanceName() {
        return instanceName;
    }

    @Override
    public String toString() {
        return String.format("%s.%s", instanceName, service);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ServiceInstance)) {
            return false;
        }
        ServiceInstance serviceInstance = (ServiceInstance) object;
        if (object == this || serviceInstance.toString().equalsIgnoreCase(toString())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
