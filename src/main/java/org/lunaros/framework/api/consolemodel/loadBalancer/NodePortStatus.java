package org.Lunaros.framework.api.consolemodel.loadBalancer;

import org.Lunaros.framework.api.model.loadBalancer.related.PortStatus;

public class NodePortStatus {
    private String ip;
    private int port;
    private PortStatus status;
    
    public String getIp() {
        return ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public int getPort() {
        return port;
    }
    
    public void setPort(int port) {
        this.port = port;
    }
    
    public PortStatus getStatus() {
        return status;
    }
    
    public void setStatus(PortStatus status) {
        this.status = status;
    }
}
