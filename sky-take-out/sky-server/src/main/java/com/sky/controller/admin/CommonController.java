package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Slf4j
@Api(tags = "通用接口")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) throws IOException {
        log.info("文件上传:{}", file);

        //原文件名
        String originalFilename = file.getOriginalFilename();
        //添加后缀
        String extension = null;
        if (originalFilename != null) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        //将文件上传到阿里云
        String fileName = UUID.randomUUID().toString()  + extension;
        try {
            String filePath = aliOssUtil.upload(file.getBytes(), fileName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败:{}", e.getMessage());
        }
       return Result.error(MessageConstant.UPLOAD_FAILED);
    }


}
