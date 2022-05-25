package com.zyy.sport.mapper;

import com.github.pagehelper.Page;
import com.zyy.sport.entity.Sport;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SportMapper {
    @Insert("insert into sport(title, content, create_time, create_name) values(#{title}, #{content}, #{createTime}, #{createName})")
    void insert(Sport sport);

    /**
     * 删除
     */
    void delete(Long id);

    /**
     * 删除
     */
    Sport findInfo(Long id);

    /**
     * 修改
     */
    void update(Sport sport);

    /**
     * 分页查询
     */
    Page<Sport> findPage(String queryParam);

//    /**
//     * 添加微信运动步数
//     * @param runs
//     */
//    void insertStep(@Param("runs") List<WxRun> runs);
//
//    /**
//     * 添加微信运动步数
//     * @param openid 用户唯一标志
//     * @param time 时间
//     */
//    @Select("select * from wx_run where openid=#{openid} and time=#{time}")
//    WxRun findStepByTime(@Param("openid") String openid, @Param("time") String time);
//
//    @Update("update wx_run set step=#{step} where openid=#{openid} and time=#{time}")
//    void updateStep(@Param("openid") String openid, @Param("time") String time, @Param("step") Integer step);
//
//    @Select("select * from wx_run where openid=#{openid} and (time<=#{beginTime} and time>=#{endTime})")
//    List<WxRun> findByTime(@Param("beginTime") String dateTime, @Param("endTime") String date1, @Param("openid") String openId);
}
