package com.fushun.framework.mybatis.config.batchConfig;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 根Mapper，给表Mapper继承用的，可以自定义通用方法
 * {@link BaseMapper}
 * {@link com.baomidou.mybatisplus.extension.service.IService}
 * {@link com.baomidou.mybatisplus.extension.service.impl.ServiceImpl}
 */
public interface RootMapper<T> extends BaseMapper<T> {

    /**
     * 自定义批量插入
     * 如果要自动填充，@Param(xx) xx参数名必须是 list/collection/array 3个的其中之一
     */
    int insertBatch(@Param("list") Collection<T> list);

    /**
     * 自定义批量新增或更新
     * 如果要自动填充，@Param(xx) xx参数名必须是 list/collection/array 3个的其中之一
     */
    int mysqlInsertOrUpdateBath(@Param("list") Collection<T> list);

    /**
     * 物理删除
     *
     * @param id
     * @return
     */
    int deleteAbsoluteById(Serializable id);

    /**
     * 批量物理删除
     *
     * @param idList
     * @return
     */
    int deleteAbsoluteByIds(@Param(Constants.COLL) Collection<Serializable> idList);

}
