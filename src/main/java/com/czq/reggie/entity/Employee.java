package com.czq.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 员工实体类
 */

@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;          //主键

    private String username;  //用户名

    private String name;      //账号

    private String password;  //密码

    private String phone;     //手机号

    private String sex;       //性别
    /**
     * 驼峰命名自动映射sql表中的下划线命名
     * application.yml配置文件中配置
     */
    private String idNumber;  // 身份证号

    private Integer status;   //状态 0:禁用，1:正常


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}

