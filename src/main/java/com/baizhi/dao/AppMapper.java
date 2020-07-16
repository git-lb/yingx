package com.baizhi.dao;
import com.baizhi.po.VideoPO;
import java.util.List;

public interface AppMapper {

    List<VideoPO> queryByReleaseTime();
}
