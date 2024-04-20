package com.example.campusparttimerecruitmentsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * <p>
 * 
 * </p>
 *
 * @author 徐渝钦
 * @since 2024-04-11
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Resumes implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "resume_id", type = IdType.AUTO)
    private Integer resumeId;

    private Integer studentId;

    /**
     * 工作/实习经验
     */
    private String experience;

    /**
     * 技能
     */
    private String skills;

    /**
     * 工作意向
     */
    private String objectives;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer version;


}
