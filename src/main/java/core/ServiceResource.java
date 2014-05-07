package core;

import common.DPSException;
import mdns.ResourceRecord;
import mdns.enums.RRType;
import mdns.metadata.Name;
import mdns.rdata.SRVRData;

import static common.Messages.NON_SRV_ERR;

/**
 * ServiceResource class to deal with SRV type DNS request.
 * @author Shaofeng Chen
 * @since 3/29/14
 */
public class ServiceResource {

    private final ServiceInstance serviceInstance;
    private final Name target;
    private final int port;
    private final int priority;
    private final int weight;

    public ServiceResource(ServiceInstance serviceInstance, Name target, int port, int priority,
            int weight) {
        this.serviceInstance = serviceInstance;
        this.target = target;
        this.port = port;
        this.priority = priority;
        this.weight = weight;
    }

    /**
     * Construct a ServiceResource type object from ResourceRecord type object.
     * @param resourceRecord a SRV type resource record
     * @return ServiceResource
     */
    public static ServiceResource from(ResourceRecord resourceRecord) {
        if (!(resourceRecord.getRRType().equals(RRType.SRV))) {
            throw new DPSException(NON_SRV_ERR);
        }
        ServiceInstance serviceInstance = ServiceInstance.from(resourceRecord.getName().toString
                ());
        SRVRData srvrData = (SRVRData) resourceRecord.getRData();
        return new ServiceResource(serviceInstance, srvrData.getTarget(), srvrData.getPort(),
                srvrData.getPriority(), srvrData.getWeight());
    }

    public ServiceInstance getServiceInstance() {
        return serviceInstance;
    }

    public Name getTarget() {
        return target;
    }

    public int getPort() {
        return port;
    }

    public int getPriority() {
        return priority;
    }

    public int getWeight() {
        return weight;
    }

    public boolean provides(Service service) {
        return serviceInstance.provides(service);
    }

    public boolean provides(ServiceInstance serviceInstance) {
        return this.serviceInstance.equals(serviceInstance);
    }
}
