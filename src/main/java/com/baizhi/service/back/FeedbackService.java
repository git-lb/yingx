package com.baizhi.service.back;

import com.baizhi.entity.Feedback;

import java.util.Map;

public interface FeedbackService {
    /**
     * 添加反馈
     * @return
     */
    Map<String, Object> addFeedback(Feedback feedback);

    /**
     * 分页查询数据
     * @param rows
     * @param page
     * @return
     */
    Map<String, Object> queryAllFeedbackByPage(Integer rows, Integer page);

    /**
     * 删除数据
     * @param feedback
     * @return
     */
    Map<String, Object> deleteFeedback(Feedback feedback);
}
