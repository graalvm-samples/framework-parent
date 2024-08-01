package com.fushun.framework.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 东语基础entity
 * zhangyd
 * 2020/5/16 9:58
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 5334699463711160921L;

    /**
     * 用户编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 修改人
     */
    @TableField(value = "modify_by")
    private String modifyBy;

    /**
     * 修改时间
     */
    @TableField(value = "modify_date", fill = FieldFill.UPDATE)
    private LocalDateTime modifyDate;

    /**
     * 版本号
     */
    @TableField(value = "version")
    private Integer version;
}
