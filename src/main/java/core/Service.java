package core;

import common.DPSException;
import util.CommonUtil;

import static common.Messages.SERVICE_TYPE_ERR;

/**
 * Class for service of format serviceName.protocal.domain.
 * @author Shaofeng Chen
 * @since 4/4/14
 */
public class Service {
    private final String serviceName;
    private final String protocal;
    private final String domain;

    public Service(String serviceName, String protocal, String domain) {
        this.serviceName = serviceName;
        this.protocal = protocal;
        CommonUtil.validateDomain(domain);
        this.domain = domain;
    }

    public static Service from(String service) {
        String[] components = CommonUtil.getComponents(service, 3);
        if (components.length != 3) {
            throw new DPSException(String.format(SERVICE_TYPE_ERR, service));
        }
        return new Service(components[0], components[1], components[2]);
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getProtocal() {
        return protocal;
    }

    public String getDomain() {
        return domain;
    }

    @Override
    public String toString() {
        return String.format("%s.%s.%s", serviceName, protocal, domain);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Service)) {
            return false;
        }
        Service service = (Service) object;
        if (service == this || service.toString().equalsIgnoreCase(toString())) {
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
