package com.example.campusparttimerecruitmentsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.campusparttimerecruitmentsystem.Util.EmailService;
import com.example.campusparttimerecruitmentsystem.Util.JwtUtils;
import com.example.campusparttimerecruitmentsystem.entity.Users;
import com.example.campusparttimerecruitmentsystem.mapper.UsersMapper;
import com.example.campusparttimerecruitmentsystem.response.LoginResponse;
import com.example.campusparttimerecruitmentsystem.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 徐渝钦
 * @since 2024-04-10
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private StudentsServiceImpl studentsServiceimpl;
//    @Autowired
//    private StudentsMapper studentsMapper;
//    @Autowired
//    private CompaniesMapper companiesMapper;
//    @Autowired
//    private AdminsMapper adminsMapper;
    //注册
    @Override
    public String register(Users users) {
        // 检查用户是否已存在
        if (existsByEmail(users.getEmail())) {
            return "该邮箱已被使用";
        }

        if (users.getRole().equals("admin") || users.getRole().equals("company")) {
            users.setStatus("未审核");
//            if (users.getRole().equals("company")) {
//                Companies companies = new Companies();
//                int insert = companiesMapper.insert(companies);
//                if (insert <= 0) {
//                    return "企业信息初始化失败";
//                }
//            } else if (users.getRole().equals("admin")) {
//                Admins admins = new Admins();
//                int insert = adminsMapper.insert(admins);
//                if (insert <= 0) {
//                    return "管理员信息初始化失败";
//                }
//            }
        } else if (users.getRole().equals("student")) {
            users.setStatus("学生身份已通过");
//            Students students = new Students();
//            int insert = studentsMapper.insert(students);
//            if (insert <= 0) {
//                return "学生个人信息初始化失败";
//            }
        }
        // 插入新用户
        int result = usersMapper.insert(users);
        if (result <= 0) {
            return "注册失败";
        }
        // 发送注册成功邮件
        try {
            String textContent = "尊敬的" + users.getUserName() + "用户您好,\n" +
            "恭喜您成功注册我们的服务！\n" +
            "我们期待为您提供优质的服务体验。如需进一步操作,请按照我们网站上的指示进行。\n" +
            "如果您有任何疑问,随时欢迎联系我们的客服团队。\n" +
            "感谢您的信任与支持！\n" +
            "xyq外包团队敬上";
            emailService.sendEmail(users.getEmail(), "注册成功", textContent);
        } catch (MessagingException e) {
            e.printStackTrace();  // 在生产环境中应有更好的错误处理
        }
        return "注册成功";
    }
    //学生登录
    @Override
    public LoginResponse login(String email, String password) {
        Users user = this.findByEmail(email);

        // 检查用户是否存在
        if (user == null) {
            return new LoginResponse(false, null, "登录失败: 用户不存在");
        }

        // 检查用户角色
        if (user.getRole().equals("student")) {
            // 学生用户登录逻辑
            if (user.getPassword().equals(password)) {
                String token = JwtUtils.generateToken(email); // 生成JWT
                return new LoginResponse(true, token, user.getRole()+"登录成功");
            } else {
                return new LoginResponse(false, null, "登录失败: 用户名或密码错误");
            }
        } else if (user.getRole().equals("admin") || user.getRole().equals("company")) {
            // 管理员或企业用户登录逻辑
            if (!"已审核".equals(user.getStatus())) {
                // 检查状态是否为已审核
                return new LoginResponse(false, null, "你的账号未激活");
            } else {
                if (user.getPassword().equals(password)) {
                    String token = JwtUtils.generateToken(email); // 生成JWT
                    return new LoginResponse(true, token, user.getRole()+"登录成功");
                } else {
                    return new LoginResponse(false, null, "登录失败: 用户名或密码错误");
                }
            }
        }

        // 默认失败响应
        return new LoginResponse(false, null, "登录失败: 出现未知错误");
    }

    public Users findByEmail(String email) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return usersMapper.selectOne(queryWrapper);
    }

    public boolean existsByEmail(String email) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        Integer count = usersMapper.selectCount(queryWrapper);
        return count != null && count > 0;
    }
}
