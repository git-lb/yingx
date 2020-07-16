package com.baizhi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoVO  implements Serializable {
    private String id;
    private String videoTitle;
    private String cover;
    private String path;
    private Date uploadTime;
    private String description; //描述
    private String likeCount;  //点赞数
    private String cateName;
    private String userPhoto;

}
