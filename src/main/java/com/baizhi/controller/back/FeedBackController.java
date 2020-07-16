package com.baizhi.controller.back;

import com.baizhi.entity.Feedback;
import com.baizhi.service.back.FeedbackService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("backFeedback")
public class FeedBackController {

    @Resource
    private FeedbackService feedbackService;

    /**
     * 添加反馈
     * @param
     * @return
     */
    @RequestMapping("addFeedback")
    @ResponseBody
    public Map<String,Object> addFeedback( Feedback feedback){
        Map<String,Object> map = feedbackService.addFeedback(feedback);
        return map;
    }

    /**
     * 分页查询所有数据
     * @param rows
     * @param page
     * @return
     */
    @RequestMapping("queryAllFeedbackByPage")
    @ResponseBody
    public Map<String,Object> queryAllFeedbackByPage(Integer rows, Integer page){
        Map<String,Object> map = feedbackService.queryAllFeedbackByPage(rows,page);
        return map;
    }

    /**
     * 删除数据
     * @param feedback
     * @return
     */
    @RequestMapping("deleteFeedback")
    @ResponseBody
    public Map<String,Object> deleteFeedback(Feedback feedback){
        Map<String,Object> map = feedbackService.deleteFeedback(feedback);
        return map;
    }
}
