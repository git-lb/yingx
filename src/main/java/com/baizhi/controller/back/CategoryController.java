package com.baizhi.controller.back;


import com.baizhi.entity.Category;
import com.baizhi.service.back.CategoryService;
import com.baizhi.service.back.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("backCategory")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private VideoService videoService;

    /*
    * 查询二级类别是否含有视屏
    * */
    @ResponseBody
    @RequestMapping("findTwoCateUnderVideo")
    public HashMap<String,String> findTwoCateUnderVideo(String id){
        //id 为此类别的id
        //查询此类别下的视频
        HashMap<String,String> map = videoService.queryVideoByCateId(id);
        return map;
    }


    /*
     * 查询一级类别下的二级类别
     * */
    @ResponseBody
    @RequestMapping("findOneCateUnderTwoCate")
    public HashMap<String, String> findOneCateUnderTwoCate(String id){
        HashMap<String, String> map = new HashMap<>();
        //查询此类别下的二级类别
        List<Category> list = categoryService.findOneCateUnderTwoCate(id);
        if(list.size() == 0){
            map.put("status","1");  //此类别下没有二级类别
            return map;
        }else{
            map.put("status","2"); //此类别下有二级类别
            return map;
        }
    }


    /*
    * 类别分页查询(一级和二级)
    * */
    @RequestMapping("findCateByPage")
    @ResponseBody
    public HashMap<String,Object> findCateByPage(Integer rows,Integer page,String parentId){
        HashMap<String,Object> map = categoryService.findCateByPage(rows,page,parentId);
        return  map;
    }

    /*
     * 类别操作(一级和二级)
     * */
    @RequestMapping("operCategory")
    @ResponseBody
    public void operCategory(Category category, String oper,String id){

            if("add".equals(oper)) {
                //判断是否为添加
                categoryService.addCategory(category);

            }else if("del".equals(oper)){
                //判断是否为删除
                categoryService.deleteCategory(id);
            }
    }

    /**
     * 查询所有二级类别
     */
    @RequestMapping("queryAllTwoCategory")
    @ResponseBody
    public List<Category> queryAllTwoCategory(){
        List<Category> list = categoryService.queryAllTwoCategory();
        return list;
    }
}
