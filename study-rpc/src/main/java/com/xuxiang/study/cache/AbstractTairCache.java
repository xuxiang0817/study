package com.xuxiang.study.cache;

import com.taobao.tair.DataEntry;
import com.taobao.tair.Result;
import com.taobao.tair.ResultCode;
import com.taobao.tair.TairManager;
import com.xuxiang.study.cache.serializer.TairSerializer;
import lombok.Setter;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * AbstractTairCache
 *
 * @author xuxiang
 * @since 2018/8/6
 */
public abstract class AbstractTairCache<K extends Serializable, V>
        implements TairCache<K, V>, InitializingBean {

    @Setter
    @Resource
    private TairManagerBean tairManagerBean;

    protected final int TAIR_NAME_SPACE;
    protected final String prefix;
    protected final String name;
    protected long expires = 5;
    protected TimeUnit timeUnit = TimeUnit.MINUTES;

    protected TairSerializer<K> keySerializer;
    protected TairSerializer<V> valueSerializer;

    public AbstractTairCache(int tairNameSpace, String prefix, String name,
                             long expires, TimeUnit timeUnit) {
        this.TAIR_NAME_SPACE = tairNameSpace;
        this.prefix = prefix;
        this.name = name;
        this.expires = expires;
        this.timeUnit = timeUnit;
    }

    public abstract TairSerializer<K> keySerializer();

    public abstract TairSerializer<V> valueSerializer();

    protected int namespace() {
        return TAIR_NAME_SPACE;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.keySerializer = keySerializer();
        this.valueSerializer = valueSerializer();

        if (null == this.keySerializer || null == this.valueSerializer) {
            throw new BeanInitializationException("no keySerializer,valueSerializer");
        }
    }

    protected TairManager getTairManager() {
        return tairManagerBean.getTairManager();
    }

    protected String genRealKey(K key) {
        final String k = keySerializer.serialize(key);
        return String.format("%s:%s:%s", prefix, name, k);
    }

    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }
        final Result<DataEntry> result = getTairManager().get(namespace(), genRealKey(key));
        if (result != null && ResultCode.SUCCESS.equals(result.getRc())) {
            try {
                String content = (String)result.getValue().getValue();
                return valueSerializer.deserialize(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
