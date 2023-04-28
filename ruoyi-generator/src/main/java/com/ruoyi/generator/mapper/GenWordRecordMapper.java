package com.ruoyi.generator.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.generator.domain.GenWordRecord;

/**
 * 中英文对应记录Mapper接口
 * 
 * @author wdf
 * @date 2023-04-28
 */
public interface GenWordRecordMapper extends BaseMapper<GenWordRecord>{
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
     * 删除中英文对应记录
     * 
     * @param id 中英文对应记录主键
     * @return 结果
     */
    public int deleteGenWordRecordById(Long id);

    /**
     * 批量删除中英文对应记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGenWordRecordByIds(Long[] ids);
}
