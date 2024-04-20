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
 * @since 2024-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Favorites implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "favorite_id", type = IdType.AUTO)
    private Integer favoriteId;

    private Integer studentId;

    private Integer jobPostId;

    private LocalDateTime favoriteTime;

    private Integer version;


}
