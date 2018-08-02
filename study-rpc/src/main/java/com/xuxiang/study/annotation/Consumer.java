package com.xuxiang.study.annotation;

import java.lang.annotation.*;

/**
 * Created by xuxiang on 18/8/1.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Consumer {
    String group();
    String topic();
    String subExpression();
    int consumeMessageBatchMaxSize() default 1;
    Class messageType();
}
