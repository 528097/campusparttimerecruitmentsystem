package com.example.campusparttimerecruitmentsystem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.campusparttimerecruitmentsystem.entity.Applications;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.campusparttimerecruitmentsystem.response.ApplicationResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 徐渝钦
 * @since 2024-04-10
 */
@Mapper
public interface ApplicationsMapper extends BaseMapper<Applications> {
    @Select({
            "<script>",
            "SELECT a.*, s.*, r.*, j.* ",
            "FROM applications AS a ",
            "INNER JOIN students AS s ON a.student_id = s.student_id ",
            "INNER JOIN resumes AS r ON a.resumes_id = r.resume_id ",
            "INNER JOIN job_posts AS j ON a.job_post_id = j.job_post_id ",
            "WHERE a.company_id = #{companyId} AND a.version = 1 ",
            "<if test='realName != null and realName != \"\"'>",
            "AND s.real_name LIKE CONCAT('%', #{realName}, '%') ",
            "</if>",
            "<if test='title != null and title != \"\"'>",
            "AND j.title LIKE CONCAT('%', #{title}, '%') ",
            "</if>",
            "<if test='gender != null and gender != \"\"'>",
            "AND s.gender = #{gender} ",
            "</if>",
            "<if test='status != null and status != \"\"'>",
            "AND a.status = #{status} ",
            "</if>",
            "<if test='applyTimeStart != null and applyTimeEnd != null'>",
            "AND a.apply_time BETWEEN #{applyTimeStart} AND #{applyTimeEnd} ",
            "</if>",
            "<if test='result != null and result != \"\"'>",
            "AND a.result LIKE CONCAT('%', #{result}, '%') ",
            "</if>",
            "<if test='interviewRecords != null and interviewRecords != \"\"'>",
            "AND a.interview_records LIKE CONCAT('%', #{interviewRecords}, '%') ",
            "</if>",
            "<if test='postDateStart != null and postDateEnd != null'>",
            "AND j.post_date BETWEEN #{postDateStart} AND #{postDateEnd} ",
            "</if>",
            "ORDER BY a.apply_time DESC",
            "</script>"
    })
    IPage<ApplicationResponse> selectApplicationResponses(
            Page<ApplicationResponse> page,
            @Param("companyId") Integer companyId,
            @Param("realName") String realName,
            @Param("title") String title,
            @Param("gender") String gender,
            @Param("status") String status,
            @Param("result") String result,
            @Param("interviewRecords") String interviewRecords,
            @Param("applyTimeStart") LocalDateTime applyTimeStart,
            @Param("applyTimeEnd") LocalDateTime applyTimeEnd,
            @Param("postDateStart") LocalDate postDateStart,
            @Param("postDateEnd") LocalDate postDateEnd
    );

    @Select({
            "<script>",
            "SELECT a.*, s.*, r.*, j.* ",
            "FROM applications AS a ",
            "INNER JOIN students AS s ON a.student_id = s.student_id ",
            "INNER JOIN resumes AS r ON a.resumes_id = r.resume_id ",
            "INNER JOIN job_posts AS j ON a.job_post_id = j.job_post_id ",
            "WHERE a.student_id = #{studentId} AND a.version = 1",
            "<if test='title != null and title != \"\"'>",
            "AND j.title LIKE CONCAT('%', #{title}, '%') ",
            "</if>",
            "<if test='applyTimeStart != null and applyTimeEnd != null'>",
            "AND a.apply_time BETWEEN #{applyTimeStart} AND #{applyTimeEnd} ",
            "</if>",
            "<if test='postDateStart != null and postDateEnd != null'>",
            "AND j.post_date BETWEEN #{postDateStart} AND #{postDateEnd} ",
            "</if>",
            "ORDER BY a.apply_time DESC",
            "</script>"
    })
    IPage<ApplicationResponse> selectStudentApplicationResponses(
            Page<ApplicationResponse> page,
            @Param("studentId") Integer studentId,
            @Param("title") String title,
            @Param("applyTimeStart") LocalDateTime applyTimeStart,
            @Param("applyTimeEnd") LocalDateTime applyTimeEnd,
            @Param("postDateStart") LocalDate postDateStart,
            @Param("postDateEnd") LocalDate postDateEnd);


}
