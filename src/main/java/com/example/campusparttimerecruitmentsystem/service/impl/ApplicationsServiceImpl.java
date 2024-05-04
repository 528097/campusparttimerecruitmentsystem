package com.example.campusparttimerecruitmentsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.campusparttimerecruitmentsystem.Util.EmailService;
import com.example.campusparttimerecruitmentsystem.entity.*;
import com.example.campusparttimerecruitmentsystem.mapper.*;
import com.example.campusparttimerecruitmentsystem.request.JobPostRequest;
import com.example.campusparttimerecruitmentsystem.request.ResumesRequest;
import com.example.campusparttimerecruitmentsystem.response.ApplicationResponse;
import com.example.campusparttimerecruitmentsystem.response.Response;
import com.example.campusparttimerecruitmentsystem.service.IApplicationsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.glass.ui.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 徐渝钦
 * @since 2024-04-10
 */
@Service
public class ApplicationsServiceImpl extends ServiceImpl<ApplicationsMapper, Applications> implements IApplicationsService {

    @Autowired
    private StudentsServiceImpl studentsServiceimpl;
    @Autowired
    private ApplicationsMapper applicationsMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private CompaniesMapper companiesMapper;
    @Autowired
    private EmailService emailService;
    @Override
    public Response create(JobPosts jobPosts, int resumesId) {
        Users users = studentsServiceimpl.userInfo();
        QueryWrapper<Applications> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",users.getUserId()).eq("job_post_id",jobPosts.getJobPostId()).eq("status","pending").eq("version",1);
        Applications applications1 = applicationsMapper.selectOne(queryWrapper);
        if (applications1 != null) {
            return new Response(false,applications1,"你已经投递过简历，请等待企业反馈");
        }
        Applications applications = new Applications();
        applications.setStudentId(users.getUserId());
        applications.setJobPostId(jobPosts.getJobPostId());
        applications.setResumesId(resumesId);
        applications.setCompanyId(jobPosts.getCompanyId());
        int insert = applicationsMapper.insert(applications);
        if (insert <= 0) {
            return new Response(false,applications,"应聘请求出现未知错误");
        }
        return new Response(true,applications,"应聘请求成功");
    }

    @Override
    public IPage<ApplicationResponse> findApplications(int pageNum, int pageSize,
                                                       String realName, String title, String gender,String status,String result,String interviewRecords,
                                                       LocalDateTime applyTimeStart, LocalDateTime applyTimeEnd,
                                                       LocalDate postDateStart, LocalDate postDateEnd) {
        // 创建分页对象
        Page<ApplicationResponse> page = new Page<>(pageNum, pageSize);
        Users users = studentsServiceimpl.userInfo();
        // 调用Mapper接口的方法，传入查询参数
        IPage<ApplicationResponse> applicationResponsesPage = applicationsMapper.selectApplicationResponses(
                page,
                users.getUserId(),
                realName,
                title,
                gender,
                status,
                result,
                interviewRecords,
                applyTimeStart,
                applyTimeEnd,
                postDateStart,
                postDateEnd
        );

        return applicationResponsesPage;
    }

    @Override
    public IPage<ApplicationResponse> findApplicationsStudent(int pageNum, int pageSize, String title, LocalDateTime applyTimeStart, LocalDateTime applyTimeEnd, LocalDate postDateStart, LocalDate postDateEnd) {
        // 创建分页对象
        Page<ApplicationResponse> page = new Page<>(pageNum, pageSize);
        Users users = studentsServiceimpl.userInfo();
        // 调用Mapper接口的方法，传入查询参数
        IPage<ApplicationResponse> applicationResponsesPage = applicationsMapper.selectStudentApplicationResponses(
                page,
                users.getUserId(),
                title,
                applyTimeStart,
                applyTimeEnd,
                postDateStart,
                postDateEnd
        );

        return applicationResponsesPage;
    }

    @Override
    public Response updateResume(Applications applications, int resumesId) {
        QueryWrapper<Applications> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("application_id",applications.getApplicationId());
        Applications applications1 = applicationsMapper.selectOne(queryWrapper);
        applications1.setResumesId(resumesId);
        boolean isUpdated = applicationsMapper.update(applications1, queryWrapper) > 0;
        // 如果更新成功，返回新的更新后的考勤记录
        if (isUpdated) {
            return new Response(true, applications1, "更新成功");
        } else {
            return new Response(false, applications1, "更新失败");
        }

    }

    @Override
    public Response updateStatus(Applications applications) {
        QueryWrapper<Applications> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("application_id", applications.getApplicationId());
        Applications applications1 = applicationsMapper.selectOne(queryWrapper);
        applications1.setStatus(applications.getStatus());

        QueryWrapper<Users> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id", applications1.getStudentId());
        Users users = usersMapper.selectOne(queryWrapper1);

        QueryWrapper<Companies> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("company_id",applications1.getCompanyId());
        Companies company = companiesMapper.selectOne(queryWrapper2);

        boolean isUpdated = applicationsMapper.update(applications1, queryWrapper) > 0;
        if (isUpdated) {
            String emailSubject = "";
            String emailContent = "";

            switch (applications.getStatus()) {
                case "accepted":
                    emailSubject = "简历投递更新通知";
                    emailContent = String.format("尊敬的" + users.getUserName() + "用户您好, \n" +

                            "恭喜您的简历已被接受！请您前往公司地点或拨打我们的联系电话咨询更多面试信息。我们期待与您的进一步沟通，并希望在未来与您共同发展。\n"+

                            "如果您有任何疑问或需要帮助，请随时联系我们的客服团队。\n" +

                            "感谢您对我们的信任，祝您前程似锦！ \n" +

                            company.getCompanyName() + "团队敬上");
                    break;
                case "rejected":
                    emailSubject = "简历投递结果通知";
                    emailContent = String.format("尊敬的" + users.getUserName() + "用户您好, \n" +

                            "感谢您对" + company.getCompanyName() + "的兴趣和所做的努力。我们遗憾地通知您，经过慎重考虑，我们决定不继续您的申请流程。每一次的申请都是对我们团队极大的信任，我们深感荣幸。\n"+

                            "请您不要气馁，我们鼓励您关注我们未来的职位发布，希望未来有机会与您合作。再次感谢您的申请和理解。 \n" +

                            "祝您职业生涯顺利，\n" +

                            company.getCompanyName() + "团队敬上");
                    break;
                case "pending":
                    // Do not send email.
                    return new Response(true, applications1, "状态更新为待定，未发送邮件");
            }

            try {
                emailService.sendEmail(users.getEmail(), emailSubject, emailContent);
                return new Response(true, applications1, "更新成功，邮件已发送");
            } catch (MessagingException e) {
                e.printStackTrace();  // 在生产环境中应有更好的错误处理
                return new Response(false, applications1, "邮件发送失败");
            }
        } else {
            return new Response(false, applications1, "更新失败");
        }
    }
}
