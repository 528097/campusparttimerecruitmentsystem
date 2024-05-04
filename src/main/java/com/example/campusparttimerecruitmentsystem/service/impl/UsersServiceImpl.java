package com.example.campusparttimerecruitmentsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.campusparttimerecruitmentsystem.Util.EmailService;
import com.example.campusparttimerecruitmentsystem.Util.JwtUtils;
import com.example.campusparttimerecruitmentsystem.entity.JobPosts;
import com.example.campusparttimerecruitmentsystem.entity.Resumes;
import com.example.campusparttimerecruitmentsystem.entity.Users;
import com.example.campusparttimerecruitmentsystem.mapper.UsersMapper;
import com.example.campusparttimerecruitmentsystem.request.UserRequest;
import com.example.campusparttimerecruitmentsystem.response.LoginResponse;
import com.example.campusparttimerecruitmentsystem.response.Response;
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
                String token = JwtUtils.generateToken(email);
                return new LoginResponse(true, token, user.getRole()+ "你的公司账号账号未审核" );
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

    @Override
    public IPage<Users> findAll(UserRequest request, int pageNum, int pageSize) {
        Users users = studentsServiceimpl.userInfo();
        Page<Users> page =new Page<>(request.getPageNum(),request.getPageSize());
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("version",1);
        if (StringUtils.isNotEmpty(request.getUserName())) {
            // 如果状态非空，则添加状态查询条件
            queryWrapper.like("userName", request.getUserName());
        }
        if (StringUtils.isNotEmpty(request.getRole())) {
            // 如果审核人非空，则添加状态查询条件
            queryWrapper.like("role", request.getRole());
        }
        if (StringUtils.isNotEmpty(request.getEmail())) {
            // 如果姓名非空，则添加状态查询条件
            queryWrapper.like("email", request.getEmail());
        }
        // 使用 MyBatis-Plus 提供的分页查询方法，传入 Page 对象和 QueryWrapper 对象
        IPage<Users> usersIPage = usersMapper.selectPage(page, queryWrapper);
        return usersIPage;
    }

    @Override
    public Response photo(Users users) {
        Users users1 = studentsServiceimpl.userInfo();
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",users1.getUserId());
        Users users2 = usersMapper.selectOne(queryWrapper);
        users2.setPhoto(users.getPhoto());
        boolean isUpdated = usersMapper.update(users2, queryWrapper) > 0;
        if (isUpdated) {
            return new Response(true,users2,"更新成功");
        } else {
            return new Response(false,null,"更新失败");
        }
    }

    @Override
    public Response deleteById(Long id, Users users) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",users.getUserId()).eq("version",1);
        Users users1 = usersMapper.selectOne(queryWrapper);
        users1.setVersion(0);
        boolean isUpdated = usersMapper.update(users1, queryWrapper) > 0;
        if (isUpdated) {
            return new Response(true, users1, "删除成功");
        } else {
            return new Response(false, users1, "删除失败");
        }
    }

    @Override
    public Response updateUser(Long id, Users users) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",users.getUserId()).eq("version",1);
        Users users1 = usersMapper.selectOne(queryWrapper);
        users1.setRole(users.getRole());
        users1.setPassword(users.getPassword());
        users1.setStatus(users.getStatus());
        users1.setUserName(users.getUserName());
        users1.setEmail(users.getEmail());
        boolean isUpdated = usersMapper.update(users1, queryWrapper) > 0;
        if (isUpdated) {
            return new Response(true, users1, "更新成功");
        } else {
            return new Response(false, users1, "更新失败");
        }
    }

    @Override
    public Response createUser(Users users) {
        int insert = usersMapper.insert(users);
        if (insert <= 0 ) {
            return new Response(false,users,"创建账号失败");
        }
        return new Response(true,users,"创建账号成功");
    }

    public Users findByEmail(String email) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email).eq("version",1);
        return usersMapper.selectOne(queryWrapper);
    }

    public boolean existsByEmail(String email) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email).eq("version",1);
        Integer count = usersMapper.selectCount(queryWrapper);
        return count != null && count > 0;
    }
}
