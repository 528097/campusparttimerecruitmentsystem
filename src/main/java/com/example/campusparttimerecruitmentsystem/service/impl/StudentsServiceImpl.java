package com.example.campusparttimerecruitmentsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.campusparttimerecruitmentsystem.Util.JwtUtils;
import com.example.campusparttimerecruitmentsystem.entity.Students;
import com.example.campusparttimerecruitmentsystem.entity.Users;
import com.example.campusparttimerecruitmentsystem.mapper.StudentsMapper;
import com.example.campusparttimerecruitmentsystem.mapper.UsersMapper;
import com.example.campusparttimerecruitmentsystem.response.StudentResponse;
import com.example.campusparttimerecruitmentsystem.service.IStudentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 徐渝钦
 * @since 2024-04-10
 */
@Service
public class StudentsServiceImpl extends ServiceImpl<StudentsMapper, Students> implements IStudentsService {

    @Autowired
    private StudentsMapper studentsMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Override
    public Students show() {
        return null;
    }

    @Override
    public StudentResponse bean() {
        Users users = userInfo();
        if (!Objects.equals(users.getRole(), "student")&&!Objects.equals(users.getRole(), "admin")) {
            return new StudentResponse(false,null,"当前非学生账号");
        }
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", users.getUserId());
        Students students1 = studentsMapper.selectOne(queryWrapper);
        Students students;
        if (students1 == null) {
            students = new Students();
            students.setStudentId(users.getUserId());
            int insert = studentsMapper.insert(students);
            if (insert <= 0) {
                return new StudentResponse(false, students, "学生个人信息初始化失败");
            }
            return new StudentResponse(true, students, "学生个人信息初始化成功");
        } else {
            return new StudentResponse(true,students1,"学生个人信息查询成功");
        }
    }

    @Override
    public StudentResponse updateBean(Students students) {
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",students.getStudentId());
        Students students1 = studentsMapper.selectOne(queryWrapper);
        students1.setEducation(students.getEducation());
        students1.setMajor(students.getMajor());
        students1.setCollege(students.getCollege());
        students1.setGender(students.getGender());
        students1.setPhone(students.getPhone());
        students1.setGraduateYear(students.getGraduateYear());
        students1.setRealName(students.getRealName());
        boolean isUpdated = studentsMapper.update(students1,queryWrapper) > 0;
        if (isUpdated) {
            return new StudentResponse(true,studentsMapper.selectOne(queryWrapper),"更新成功");
        } else {
            return new StudentResponse(false,studentsMapper.selectOne(queryWrapper),"更新失败");
        }
    }

    @Override
    public Users userInfo(String token) {
        String email = JwtUtils.getClaimByToken(token).getSubject();
        return findByEmail(email);
    }

    public String getToken() {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest()
                .getHeader("Authorization");
        // 假设Token是Bearer类型的，需要去掉前缀
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return token;
    }
    public Users userInfo() {
        String email = JwtUtils.getClaimByToken(getToken()).getSubject();
        return findByEmail(email);
    }
    public Users findByEmail(String email) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return usersMapper.selectOne(queryWrapper);
    }
}
