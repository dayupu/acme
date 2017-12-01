package com.manage.kernel.core.anyone.view;

import com.manage.base.exception.CoreException;
import com.manage.kernel.core.anyone.service.ITopicService;
import com.manage.kernel.core.model.dto.NewsTopicDto;
import com.manage.kernel.core.model.vo.TopicHomeVo;
import com.manage.kernel.jpa.entity.NewsTopic;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by bert on 17-12-1.
 */
@Controller
@RequestMapping("/topic")
public class TopicController {

    private static final Logger LOGGER = LogManager.getLogger(TopicController.class);

    @Autowired
    private ITopicService topicService;

    @GetMapping("/{code}")
    public ModelAndView topicHome(Model model, @PathVariable("code") String code) {
        try {
            NewsTopicDto topicDto = topicService.getNewsTopic(code);
            model.addAttribute("code", topicDto.getCode());
            model.addAttribute("name", topicDto.getName());
            return new ModelAndView("anyone/topicIndex");
        } catch (Exception e) {
            LOGGER.info("Topic {} not Found", code, e);
            return new ModelAndView("redirect:/");
        }
    }

    @GetMapping("/{code}/detail")
    @ResponseBody
    public TopicHomeVo topicHomeDtail(@PathVariable("code") String code) {
        TopicHomeVo topicHomeVo = new TopicHomeVo();
        return topicHomeVo;
    }

}
