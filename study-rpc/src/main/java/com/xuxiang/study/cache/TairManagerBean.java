package com.xuxiang.study.cache;

import com.taobao.tair.impl.mc.MultiClusterTairManager;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;

/**
 * TairManagerBean
 *
 * @author xuxiang
 * @since 2018/8/6
 */
public class TairManagerBean implements InitializingBean{

    @Setter
    private String configId;
    @Setter
    private Boolean dynamicConfig = true;
    @Setter
    private int timeout = 500;

    @Getter
    private MultiClusterTairManager tairManager;

    public void afterPropertiesSet() throws Exception {
        tairManager = new MultiClusterTairManager();
        tairManager.setConfigID(configId);
        tairManager.setDynamicConfig(dynamicConfig);
        tairManager.setTimeout(timeout);
        tairManager.init();
    }
}
