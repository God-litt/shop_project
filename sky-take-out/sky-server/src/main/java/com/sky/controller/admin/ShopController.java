package com.sky.controller.admin;


import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags = "店铺相关接口")
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;

    public static final String KEY = "disable_shop_status";

    @PutMapping("/{status}")
    @ApiOperation("设置店铺状态")
    public Result<String> setStatus(@PathVariable Integer status) {
        log.info("设置店铺状态：{}", status == 1 ? "营业中" : "打烊中");
        // 将状态值保存到Redis中
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation("查询店铺营业状态")
    public Result<Integer> getStatus() {
        log.info("获取店铺状态");
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("查询店铺营业状态为：{}", status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}
