package com.example.backend.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.backend.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    //文件上传路径
    // System.getProperty("user.dir")：这是Java中的一个调用，用于获取系统属性user.dir的值，它通常指当前用户的工作目录（当前Java虚拟机启动时的目录
    private static final String filePath=System.getProperty("user.dir")+"\\back-end\\file\\";

    @PostMapping("/upload")
    public Result upload(MultipartFile file){
        synchronized (FileController.class){
            //获取时间戳
            String flag=System.currentTimeMillis()+"";
            System.out.println(filePath);
            //原始文件文件名
            String fileName=file.getOriginalFilename();
            try{
                //如果没有file文件夹，则创建一个
                if(FileUtil.isDirectory(filePath)){
                    FileUtil.mkdir(filePath);
                }
                //文件储存形式
                FileUtil.writeBytes(file.getBytes(),filePath+flag+"-"+fileName);
                System.out.println(fileName+"--上传成功");
                Thread.sleep(1L);
            } catch (Exception e) {
                System.err.println(fileName+"--文件上传失败");
            }
            return Result.success(flag);
        }
    }

    //@PathVariable必须和/{flag}一起用
    @GetMapping("/{flag}")
    public void  avatarPath(@PathVariable String flag, HttpServletResponse response){
        if(!FileUtil.isDirectory(filePath)){
            FileUtil.mkdir(filePath);
        }
        System.out.println(flag);
        OutputStream os;
        List<String> fileNames = FileUtil.listFileNames(filePath);
        String avatar=fileNames.stream().filter(name->name.contains(flag)).findAny().orElse("");
        try{
            if(StrUtil.isNotEmpty(avatar)){
                response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(avatar,"UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes=FileUtil.readBytes(filePath+avatar);
                os=response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        }catch (Exception e){
            System.out.println("文件下载失败");
        }
    }
}
