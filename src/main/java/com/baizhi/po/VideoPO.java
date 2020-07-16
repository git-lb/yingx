package com.baizhi.po;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoPO  implements Serializable {

    private String vId;
    private String cateName;
    private String vTitle;
    private String coverPath;
    private String videoPath;
    private Date createDate;
    private String vBrief;
    private String headImg;

}
