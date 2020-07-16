package com.baizhi.serviceImpl.back;
import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.AddLog;
import com.baizhi.annotation.DelCache;
import com.baizhi.dao.CategoryMapper;
import com.baizhi.entity.Category;
import com.baizhi.entity.CategoryExample;
import com.baizhi.service.back.CategoryService;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /*
    * 删除类别
    * */
    @DelCache
    @AddLog("删除类别")
    public void deleteCategory(String id) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andIdEqualTo(id);
        categoryMapper.deleteByExample(categoryExample);
    }

    /**
     * 查询所有二级类别
     * @return
     */
    @AddCache
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> queryAllTwoCategory() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andLevelsEqualTo("2");
        List<Category> list = categoryMapper.selectByExample(categoryExample);
        return list;
    }

    /*
    *查找一级类别下的二级类别
    *
    */
    @AddCache
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> findOneCateUnderTwoCate(String id) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andParentIdEqualTo(id);
        //根据父id查询
        List<Category> list = categoryMapper.selectByExample(categoryExample);
        return list;
    }

    /*
    *  添加类别方法(一级类别和二级类别)
    * */
    @DelCache
    @AddLog("添加类别")
    public void addCategory(Category category) {
        //id赋值
        String uuid = UUIDUtil.getUUID();
        category.setId(uuid);
        categoryMapper.addCategory(category);
    }

    /*
     * 分页查询类别(一级类别和二级类别)
     * */
    @AddCache
    @Transactional(propagation =Propagation.SUPPORTS)
    public HashMap<String, Object> findCateByPage(Integer rows, Integer page, String parentId) {
        HashMap<String, Object> map = new HashMap<>();
        List<Category> list = null;
        //数据总条数
        Integer counts = null;
        //参数1 每次查询起始下标  参数2 每次查询多少条
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        if(parentId != null){
            //分页查询二级类别
            //根据父id查询二级类别的总数量
            CategoryExample categoryExample = new CategoryExample();
            categoryExample.createCriteria().andParentIdEqualTo(parentId);
            counts = categoryMapper.selectCountByExample(categoryExample);
            //计算总页数
            Integer total = counts%rows == 0?counts/rows:counts/rows+1;
            //分页二级类别查询
            list = categoryMapper.selectByExampleAndRowBounds(categoryExample, rowBounds);
        }else{
            //分页查询一次类别
            //查询数据总条数
            CategoryExample categoryExample = new CategoryExample();
            //根据级别为1 分页查询一级类别 查询一级类别的数量
            categoryExample.createCriteria().andLevelsEqualTo("1");
            counts = categoryMapper.selectCountByExample(categoryExample);
            //分页查询
            list = categoryMapper.selectByExampleAndRowBounds(categoryExample, rowBounds);
        }
        //计算总页数
        Integer total = counts%rows == 0?counts/rows:counts/rows+1;
        map.put("records",counts);  //数据总条数
        map.put("page",page);  //当前页数
        map.put("total",total); //总页数
        map.put("rows",list);  //数据
        return map;
    }


}
