package com.manage.kernel.core.admin.view.comm;


import com.manage.base.database.enums.FileSource;
import com.manage.base.ueditor.ActionMap;
import com.manage.base.ueditor.ConfigManager;
import com.manage.base.utils.JsonUtil;
import com.manage.kernel.core.admin.service.comm.IResourceService;
import com.manage.kernel.spring.UeditorSupplier;
import java.util.Map;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Created by bert on 2017/10/29.
 */
@Controller
@RequestMapping("/ueditor")
public class UeditorController {

    @Autowired
    private UeditorSupplier ueditorSupplier;

    @Autowired
    private IResourceService resourceService;

    @RequestMapping(value = "/config")
    public void config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        try {
            PrintWriter writer = response.getWriter();
            writer.write(executeAction(request));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String executeAction(HttpServletRequest request) {
        String actionType = request.getParameter("action");
        int actionCode = ActionMap.getType(actionType);
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        ConfigManager configManager = ConfigManager.instance(ueditorSupplier, rootPath);
        switch (actionCode) {
        case 0:
            return configManager.allConfig();
        case 1:
            return uploadImage(configManager, request, actionCode);
        default:
            return null;
        }

    }

    private String uploadImage(ConfigManager configManager, HttpServletRequest request, int actionCode) {
        Map conf = configManager.getConfig(actionCode);
        String filedName = (String) conf.get("fieldName");
        if (!ServletFileUpload.isMultipartContent(request)) {
            return null;
        }

        MultipartHttpServletRequest fileRequest = (MultipartHttpServletRequest) request;
        return JsonUtil.toJson(resourceService.uploadImage(fileRequest.getFile(filedName), FileSource.UEDITOR));
    }
}
