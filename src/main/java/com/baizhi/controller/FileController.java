package com.baizhi.controller;

import com.baizhi.dto.FileDto;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class FileController {

    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    @ResponseBody
    public FileDto upload(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file,HttpServletRequest request ,HttpServletResponse response) throws IOException {
        FileDto fileDto = new FileDto();
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            String rootPath = request.getServletContext().getRealPath("/upload");
            System.out.println("editormd上传图片："+rootPath);
            File filePath = new File(rootPath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            // 最终文件名
            File realFile = new File(rootPath + File.separator + file.getOriginalFilename());
            FileCopyUtils.copy(file.getInputStream(),new FileOutputStream(realFile));
            // 下面response返回的json格式是editor.md所限制的，规范输出就OK
            fileDto.setSuccess(1);
            fileDto.setMessage("上传成功");
            fileDto.setUrl(request.getServletContext().getContextPath()+"/upload/"+file.getOriginalFilename());
        } catch (Exception e) {
            fileDto.setSuccess(0);
        }
        return fileDto;
    }
}
