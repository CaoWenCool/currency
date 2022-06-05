/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.model.mapstruct;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Stream;

/**
 * Comments for class.
 *
 * @author liucunliang
 * @version 1.0.0
 * @create 2021/3/18 上午10:04
 * @since 1.0.0
 */
@MapperConfig(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BaseMapping<S, T> {

    /**
     * 映射同名属性
     * @param source 源bean
     * @return 目标bean
     */
    T sourceToTarget(S source);

    /**
     * 映射同名属性，集合形式
     * @param sList 源bean列表
     * @return 目标bean列表
     */
    @InheritConfiguration(name = "sourceToTarget")
    List<T> sourceToTarget(List<S> sList);

    /**
     * 映射同名属性，集合流形式
     * @param sStream 源bean流
     * @return 目标bean列表
     */
    List<T> sourceToTarget(Stream<S> sStream);

    /**
     * 反向，映射同名属性
     * @param target 目标bean
     * @return 源bean
     */
    @InheritInverseConfiguration(name = "sourceToTarget")
    S targetToSource(T target);

    /**
     * 反向，映射同名属性，集合形式
     * @param tList 目标bean列表
     * @return 源bean列表
     */
    @InheritConfiguration(name = "targetToSource")
    List< S> targetToSource(List<T> tList);

    /**
     * 反向，映射同名属性，集合流形式
     * @param tStream 目标bean流
     * @return 源bean列表
     */
    List<S> targetToSource(Stream<T> tStream);

    /**
     * 对源进行 clone
     * @param source 源
     * @return clone结果
     */
    S cloneSource(S source);

    /**
     * 对目标进行 clone
     * @param target 目标bean
     * @return clone结果
     */
    T cloneTarget(T target);
}
