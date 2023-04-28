package com.ruoyi.generator.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.generator.domain.GenWordRecord;

import java.util.List;

/**
 * 中英文对应记录Service接口
 * 
 * @author wdf
 * @date 2023-04-28
 */
public interface IGenWordRecordService extends IService<GenWordRecord> {
    /**
     * 查询中英文对应记录
     * 
     * @param id 中英文对应记录主键
     * @return 中英文对应记录
     */
    public GenWordRecord selectGenWordRecordById(Long id);

    /**
     * 查询中英文对应记录列表
     * 
     * @param genWordRecord 中英文对应记录
     * @return 中英文对应记录集合
     */
    public List<GenWordRecord> selectGenWordRecordList(GenWordRecord genWordRecord);

    /**
     * 新增中英文对应记录
     * 
     * @param genWordRecord 中英文对应记录
     * @return 结果
     */
    public int insertGenWordRecord(GenWordRecord genWordRecord);

    /**
     * 修改中英文对应记录
     * 
     * @param genWordRecord 中英文对应记录
     * @return 结果
     */
    public int updateGenWordRecord(GenWordRecord genWordRecord);

    /**
     * 批量删除中英文对应记录
     * 
     * @param ids 需要删除的中英文对应记录主键集合
     * @return 结果
     */
    public int deleteGenWordRecordByIds(Long[] ids);

    /**
     * 删除中英文对应记录信息
     * 
     * @param id 中英文对应记录主键
     * @return 结果
     */
    public int deleteGenWordRecordById(Long id);
}
