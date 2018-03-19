package com.doubles.mvcboard.tutorial.controller;

import com.doubles.mvcboard.commons.util.UploadFileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file/ajax")
public class UploadAjaxController {

    private static final Logger logger = LoggerFactory.getLogger(UploadAjaxController.class);

    // AJAX 파일업로드 페이지 매핑
    @RequestMapping(value = "/uploadPage", method = RequestMethod.GET)
    public ModelAndView uploadPage() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tutorial/upload_ajax");

        return modelAndView;

    }

    // 파일 업로드 처리
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> uploadFile(MultipartFile file, HttpServletRequest request) {

        ResponseEntity<String> entity = null;

        try {
            String uploadedFileName = UploadFileUtils.uploadFile(file.getOriginalFilename(), file.getBytes(), request);
            entity = new ResponseEntity<>(uploadedFileName, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;

    }

    // 업로드 파일 출력 처리
    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ResponseEntity<byte[]> displayFile(String fileName, HttpServletRequest request) throws IOException {

        ResponseEntity<byte[]> entity = null;
        InputStream inputStream = null;

        try {
            HttpHeaders httpHeaders = UploadFileUtils.getHttpHeaders(fileName); // 헤더
            String uploadRootPath = UploadFileUtils.getUploadRootPath(fileName, request); // 업로드 경로
            inputStream = new FileInputStream(uploadRootPath + fileName); // 읽어오기
            entity = new ResponseEntity<>(IOUtils.toByteArray(inputStream), httpHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            inputStream.close();
        }

        return entity;

    }

    // 업로드 파일 삭제 처리
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<String> deleteFile(String fileName, HttpServletRequest request) {

        ResponseEntity<String> entity = null;

        try {
            UploadFileUtils.deleteFile(fileName, request);
            entity = new ResponseEntity<>("DELETED", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

}
