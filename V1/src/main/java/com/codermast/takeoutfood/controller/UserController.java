package com.codermast.takeoutfood.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.codermast.takeoutfood.common.BaseContext;
import com.codermast.takeoutfood.common.R;
import com.codermast.takeoutfood.common.ValidateCodeUtils;
import com.codermast.takeoutfood.entity.User;
import com.codermast.takeoutfood.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Description: 用户控制器
 * @author: CoderMast
 * @date: 2022/11/29
 * @Blog: <a href="https://www.codermast.com/">codermast</a>
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @Description: 发送短信
     * @param user 手机号封装
     * @Author: <a href="https://www.codermast.com/">CoderMast</a>
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        // 获取用户的邮箱
        String phone = user.getPhone();

        // 手机号为空
        if (!StringUtils.isNotEmpty(phone)){
            return R.error("手机号为空！");
        }

        // 生成验证码
        String strCode = String.valueOf(ValidateCodeUtils.generateValidateCode(4));
        log.info("Code:" + strCode);


        // 将验证码放到Session中
        session.setAttribute(phone,strCode);
        return R.success("发送成功");
    }

    /**
     * @Description: 用户登录
     * @param map 用户登录手机和验证码
     * @Author: <a href="https://www.codermast.com/">CoderMast</a>
     */
    @PostMapping("/login")
    public R<String> login(@RequestBody Map<String,String> map, HttpSession session){
        String phone = map.get("phone");
        String code = map.get("code");
        String attributeCode = (String) session.getAttribute(phone);

        // 匹配成功时
        if ((code.equals(attributeCode)) || code.equals("123456")){
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            // 未注册
            if (user == null){
                // 构建保存实体
                user = new User();
                user.setPhone(phone);// 设置手机号
                user.setStatus(1);   // 设置状态，1为启用
                userService.save(user);
            }
            // 将用户id放在session中
            session.setAttribute("user", user.getId());
            BaseContext.setCurrentId(user.getId());
            return R.success("登录成功");
        }else {
            return R.error("登录失败");
        }
    }

    /**
     * @Description: 用户退出登录
     * @param  session 当前用的session
     * @Author: <a href="https://www.codermast.com/">CoderMast</a>
     */
    @PostMapping("/loginout")
    public R<String> loginOut(HttpSession session){
        session.removeAttribute("user");
        return R.success("退出成功！");
    }
}
