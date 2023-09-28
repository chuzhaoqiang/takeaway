package com.czq.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czq.reggie.common.Result;
import com.czq.reggie.entity.Employee;
import com.czq.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")   //请求路径url
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param request
     * @param employee
     * @return
     */

    @PostMapping("/login")              //    @RequestBody接收前端 发送过来的JSON风格的数据，将其转化为相应的对象
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //1.将页面提交的密码password进行md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2.根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);//数据唯一（表中属性为Unique）

        //3.如果没有查询到则返回登录失败结果
        if (emp == null) {
            return Result.error("用户名不存在！");
        }

        //4.密码比对，如果不一样则返回登录失败结果
        if (!emp.getPassword().equals(password)) {
            return Result.error("用户名或密码错误！");
        }

        //5.查看员工状态，如果已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() != 1) {  // 账号被禁用，status == 1,账号可以正常登录
            return Result.error("账号被禁用，请联系管理员或客服！");
        }

        //6.登录成功，将用户id存入Session并返回登录成功
        request.getSession().setAttribute("employee", emp.getId());
        return Result.success(emp);
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        //清理用户id
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }

    /**
     * 新增员工
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping
    public Result<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工，员工信息:{}", employee.toString());

        // 在新增员工操作中，对员工的密码进行初始化( MD5加密 )
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        //下面设置 公共属性的值(createTime、updateTime、createUser、updateUser)交给 MyMetaObjectHandler类处理
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        Long empId = (Long) request.getSession().getAttribute("employee");

        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);
        try {
            employeeService.save(employee);
        }catch (Exception e){
            Result.error("新增员工失败");
        }
        return Result.success("成功新增员工");
    }
    /**
     * 分页展示功能
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    //     1、页面发送Ajax请求，将分页查询参数(page、pageSize、name)提交到服务端
    //     2、服务端Controller接收页面提交的数据 并调用Service查询数据
    //     3、Service调用Mapper操作数据库，查询分页的数据
    //     4、Controller将查询的分页数据 响应给页面
    //     5、页面接收到分页数据并通过前端(ElementUI)的table组件展示到页面上
    @GetMapping("/page")
    public Result<Page> pageShow(int page, int pageSize, String name){
        log.info("page = {},pageSize = {},name = {}",page,pageSize,name);

        // 创建分页构造器对象
        Page pageInfo = new Page(page,pageSize);

        //  构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //   name不为null，才会 比较 getUsername方法和前端传入的name是否匹配 的过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getUsername,name);

        // 去数据库查询
        employeeService.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }
    @PutMapping
    //                     ！！！！ @RequestBody不要忘
    public Result<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());

        // 下面设置 公共属性的值(createTime、updateTime、createUser、updateUser)交给 MyMetaObjectHandler类处理
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);

        employeeService.updateById(employee);
        return Result.success("员工信息修改成功！");

    }
    /**
     * 根据id查询员工信息(编辑接口)
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Employee> getById (@PathVariable Long id) {
        log.info("根据id查询员工信息...");

        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return Result.success(employee);
        }

        return Result.error("没有查询到员工信息");
    }

}


