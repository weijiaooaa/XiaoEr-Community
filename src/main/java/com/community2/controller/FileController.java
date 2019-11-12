package com.community2.controller;

import com.community2.dto.FileDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author weijia
 * @Date 2019/10/24 12:52
 * @Version 1.0
 */
@RestController
public class FileController {

    @GetMapping("/file/upload")
    public FileDTO upload(){
        FileDTO fileDTO = new FileDTO();
        return fileDTO;
    }
}
