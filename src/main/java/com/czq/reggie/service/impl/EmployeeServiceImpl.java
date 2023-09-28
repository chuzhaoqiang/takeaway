package com.czq.reggie.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czq.reggie.entity.Employee;
import com.czq.reggie.mapper.EmployeeMapper;
import com.czq.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;
/***
 *  service实现类   继承mp提供通用的service基类 ServiceImpl
 *  ServiceImpl<EmployeeMapper, Employee>
 *      2个泛型 1.EmployeeMapper  Mapper接口
 *              2.Employee       对应entity(对象、实体)
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {
}

