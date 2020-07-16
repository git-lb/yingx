package com.baizhi.serviceImpl.back;

import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.AddLog;
import com.baizhi.annotation.DelCache;
import com.baizhi.dao.FeedbackMapper;
import com.baizhi.entity.Feedback;
import com.baizhi.service.back.FeedbackService;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackMapper feedbackMapper;


    /**
     * 分页查询
     * @param rows
     * @param page
     * @return
     */
    @AddCache
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String,Object> queryAllFeedbackByPage(Integer rows, Integer page) {

        HashMap<String, Object> map = new HashMap<>();
        Feedback feedback = new Feedback();

        //查询视频总条数
        int counts = feedbackMapper.selectCount(feedback);

        //计算总页数
        Integer total = counts%rows == 0?counts/rows:counts/rows+1;

        //分页查询
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Feedback> list = feedbackMapper.selectByRowBounds(feedback, rowBounds);

        //返回数据
        map.put("page",page);  //当前页数
        map.put("list",list);  //查询的数据
        map.put("records",counts); //数据总条数
        map.put("total",total);   //数据总页数

        return map;
    }

    /**
     * 删除数据
     * @param feedback
     * @return
     */
    @DelCache
    @AddLog(value = "删除反馈")
    public Map<String, Object> deleteFeedback(Feedback feedback) {
        Map<String, Object> map = new HashMap<>();

        try {
            feedbackMapper.deleteByPrimaryKey(feedback.getId());
            map.put("msg","删除成功");
            return map;
        } catch (Exception e) {
            map.put("msg","删除失败");
            e.printStackTrace();
            return map;
        }
    }

    /**
     *添加反馈
     * @param feedback
     * @return
     */
    @DelCache
    @AddLog(value = "添加反馈")
    public Map<String,Object> addFeedback(Feedback feedback){
        Map<String, Object> map = new HashMap<>();
        //添加反馈信息
        feedback.setId(UUIDUtil.getUUID());
        //判断内容和标题不能为空
        if(feedback.getContent() == null || feedback.getTitle() == null){
            map.put("msg","添加失败");
            return map;
        }else {
            feedbackMapper.insertSelective(feedback);
            map.put("msg","添加成功");
            return map;
        }
    }
}
