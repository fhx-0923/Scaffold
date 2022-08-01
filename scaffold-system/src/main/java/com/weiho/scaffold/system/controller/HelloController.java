package com.weiho.scaffold.system.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Controller测试")
@RestController
@SuppressWarnings("all")
@Slf4j
public class HelloController {
//    @Autowired
//    private RedisUtils utils;
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @ApiOperation(value = "测试RedisUtils类", notes = "查询Redis中匹配字段")
//    @GetMapping("/hello")
//    @NotControllerResponseAdvice
//    public String hello(String key) {
//        return (String) utils.get("Scaffold-VerifyCode-key-b40ad91ab4034ee5af0d0708cd3f1fa8");
//    }
//
//    @ApiOperation(value = "测试限流注解", notes = "测试asdadasdasd")
//    @GetMapping("/rate")
//    @RateLimiter(limitType = LimitType.IP)
////    @NotControllerResponseAdvice
//    public String test() {
//        return "访问成功!";
//    }
//
////    @ApiOperation(value = "测试系统的分页插件集成情况", notes = "测试")
////    @GetMapping("/test")
////    public List<User> test(Pageable pageable) {
////        System.out.println(pageable.getPageNumber());
////        System.out.println(pageable.getPageSize());
////        System.out.println(pageable.getSort().toString());
////        getPage(pageable);
////        PageInfo<User> pageInfo = new PageInfo<>(userMapper.selectList(null));
////        return pageInfo.getList();
////    }
//
//    @Async
//    @ApiOperation(value = "测试系统的线程池情况", notes = "测试线程池")
//    @GetMapping("/async")
//    public void testAsync() {
//        log.info("执行耗时操作");
//    }
//
//    protected void getPage(Pageable pageable) {
//        String order;
//        order = pageable.getSort().toString();//获取排序信息
//        order = order.replace(":", "");//拆分前端传入的字段 id:asc -> id asc
//        if ("UNSORTED".equals(order)) {
//            order = "id asc";//若没填写sort参数,则默认按照id升序
//        }
//        PageHelper.startPage(pageable.getPageNumber() + 1, pageable.getPageSize(), order);
//    }
//
//    @ApiOperation(value = "测试", notes = "测试1")
//    @PostMapping("/vvvv")
//    public Testsss tttt(@RequestBody @Validated Testsss testsss) {
//        return testsss;
//    }
}
