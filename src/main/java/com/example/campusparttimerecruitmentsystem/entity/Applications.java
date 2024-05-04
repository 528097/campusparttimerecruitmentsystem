package com.example.campusparttimerecruitmentsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 徐渝钦
 * @since 2024-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Applications implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "application_id", type = IdType.AUTO)
    private Integer applicationId;

    private Integer jobPostId;

    private Integer studentId;

    private LocalDateTime applyTime;

    private String status;

    private Integer resumesId;

    private Integer companyId;

    private Integer version;

    private String interviewRecords;

    private String result;


}
