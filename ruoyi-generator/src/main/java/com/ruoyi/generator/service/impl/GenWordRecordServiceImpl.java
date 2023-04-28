package com.ruoyi.generator.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.generator.domain.GenWordRecord;
import com.ruoyi.generator.mapper.GenWordRecordMapper;
import com.ruoyi.generator.service.IGenWordRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 中英文对应记录Service业务层处理
 * 
 * @author wdf
 * @date 2023-04-28
 */
@Service
public class GenWordRecordServiceImpl extends ServiceImpl<GenWordRecordMapper,GenWordRecord> implements IGenWordRecordService {
    
    @Autowired
    private GenWordRecordMapper genWordRecordMapper;
    /**
     * 查询中英文对应记录
     * 
     * @param id 中英文对应记录主键
     * @return 中英文对应记录
     */
    @Override
    public GenWordRecord selectGenWordRecordById(Long id){
        return genWordRecordMapper.selectById(id);
    }

    /**
     * 查询中英文对应记录列表
     * 
     * @param genWordRecord 中英文对应记录
     * @return 中英文对应记录
     */
    @Override
    public List<GenWordRecord> selectGenWordRecordList(GenWordRecord genWordRecord){
        return genWordRecordMapper.selectList(Wrappers.lambdaQuery(genWordRecord));
    }

    /**
     * 新增中英文对应记录
     * 
     * @param genWordRecord 中英文对应记录
     * @return 结果
     */
    @Override
    public int insertGenWordRecord(GenWordRecord genWordRecord){
        genWordRecord.setCreateTime(DateUtils.getNowDate());
        return genWordRecordMapper.insert(genWordRecord);
    }

    /**
     * 修改中英文对应记录
     * 
     * @param genWordRecord 中英文对应记录
     * @return 结果
     */
    @Override
    public int updateGenWordRecord(GenWordRecord genWordRecord){
        genWordRecord.setUpdateTime(DateUtils.getNowDate());
        return genWordRecordMapper.updateById(genWordRecord);
    }

    /**
     * 批量删除中英文对应记录
     * 
     * @param ids 需要删除的中英文对应记录主键
     * @return 结果
     */
    @Override
    public int deleteGenWordRecordByIds(Long[] ids){
        return genWordRecordMapper.deleteGenWordRecordByIds(ids);
    }

    /**
     * 删除中英文对应记录信息
     * 
     * @param id 中英文对应记录主键
     * @return 结果
     */
    @Override
    public int deleteGenWordRecordById(Long id){
        return genWordRecordMapper.deleteById(id);
    }
}
