package com.luckin.coffee.config.log;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "coffee.log")
public class LogProperties {

    /**
     * 队列大小
     */
    private Integer queueSize = Integer.SIZE;

    /**
     * 是否采集 入参 true-采集 false-不采集
     * 默认不采集
     */
    private Boolean hasArgs = false;

    /**
     * 是否采取前后参数对比
     */
    private Boolean hasArgsContrast = false;

    public Integer getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }

    public Boolean getHasArgs() {
        return hasArgs;
    }

    public void setHasArgs(Boolean hasArgs) {
        this.hasArgs = hasArgs;
    }

    public Boolean getHasArgsContrast() {
        return hasArgsContrast;
    }

    public void setHasArgsContrast(Boolean hasArgsContrast) {
        this.hasArgsContrast = hasArgsContrast;
    }
}
