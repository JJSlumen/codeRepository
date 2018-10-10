package com.jjs.csdn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@Controller
public class UserController {

    @RequestMapping("/user/uploadAvatar")
    public void uploadAvatar(MultipartFile avatar, HttpSession session, HttpServletResponse response) {

        try (OutputStream os = response.getOutputStream()) {
            avatar.transferTo(new File("F:/csdn/"+1));
            os.write("<script>parent.showAvatar();</script>".getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/show/avatar")
    public void avatar(HttpSession session, HttpServletResponse response) {

        try (OutputStream os = response.getOutputStream();
             InputStream is = new FileInputStream("F:/csdn/"+1)) {
            byte[] buff = new byte[1024];
            int length = -1;
            while (-1 != (length = is.read(buff))) {
                os.write(buff, 0, length);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
