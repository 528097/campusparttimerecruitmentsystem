package com.example.campusparttimerecruitmentsystem.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 徐渝钦
 * @since 2024-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Companies implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "company_id", type = IdType.AUTO)
    private Integer companyId;

    private String companyName;

    private String industry;

    private String location;

    private String contactPerson;

    private String phone;

    private String description;

    private String qualifications;


}
