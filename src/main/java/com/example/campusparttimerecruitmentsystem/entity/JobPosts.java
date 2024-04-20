package com.example.campusparttimerecruitmentsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2024-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class JobPosts implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "job_post_id", type = IdType.AUTO)
    private Integer jobPostId;

    private Integer companyId;

    private String title;

    private String description;

    private String salaryRange;

    private String workLocation;

    private String workHours;

    private String requirements;

    private LocalDate postDate;

    private Integer version;

    private String companyName;


}
