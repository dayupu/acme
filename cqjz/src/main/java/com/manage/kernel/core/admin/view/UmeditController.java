package com.manage.kernel.core.admin.view;

import com.manage.base.supplier.ResponseInfo;
import com.manage.base.utils.JsonUtil;
import com.manage.kernel.core.admin.dto.FileDto;
import com.manage.kernel.core.admin.model.UmeditResponse;
import com.manage.kernel.core.admin.service.IResourceFileService;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Created by bert on 17-8-25.
 */

@Controller
@RequestMapping("/umedit")
public class UmeditController {

    @PostMapping(value = "/imageUp")
    public void imageUpload(HttpServletRequest request, HttpServletResponse response) {

        UmeditResponse umeditResponse = new UmeditResponse();
        umeditResponse.setState("SUCCESS");
        umeditResponse.setUrl("test.com");
        umeditResponse.setSize(100L);
        umeditResponse.setType("jpeg");
        umeditResponse.setName("aa");
        umeditResponse.setOriginalName("bb");

        try {
            response.setContentType("text/html");
            response.setHeader("X-Frame-Options", "SAMEORIGIN");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(JsonUtil.toJson(umeditResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}