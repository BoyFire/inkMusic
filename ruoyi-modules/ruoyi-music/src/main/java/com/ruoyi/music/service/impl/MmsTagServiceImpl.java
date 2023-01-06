package com.ruoyi.music.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.music.entity.MmsTag;
import com.ruoyi.music.mapper.MmsTagMapper;
import com.ruoyi.music.service.IMmsTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 标签Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-10-25
 */
@Service
public class MmsTagServiceImpl implements IMmsTagService 
{
    @Autowired
    private MmsTagMapper mmsTagMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 查询标签
     * 
     * @param id 标签主键
     * @return 标签
     */
    @Override
    public MmsTag selectMmsTagById(Long id)
    {
        return mmsTagMapper.selectMmsTagById(id);
    }

    /**
     * 查询标签列表
     * 
     * @param mmsTag 标签
     * @return 标签
     */
    @Override
    public List<MmsTag> selectMmsTagList(MmsTag mmsTag)
    {
        return mmsTagMapper.selectMmsTagList(mmsTag);
    }

    /**
     * 新增标签
     * 
     * @param mmsTag 标签
     * @return 结果
     */
    @Override
    public int insertMmsTag(MmsTag mmsTag)
    {
        RedisAtomicLong atomicLong = new RedisAtomicLong("mms_dict:ids:mms_tag_id", redisTemplate.getConnectionFactory());
        long tagId = atomicLong.addAndGet(1);
        if (mmsTag.getTagParentsId() ==null){
            mmsTag.setTagParentsId(0L);
        }
        mmsTag.setIsDel(0);
        mmsTag.setTagId(tagId);
        mmsTag.setCreateTime(DateUtils.getNowDate());
        mmsTag.setCreateBy(SecurityUtils.getUsername());
        return mmsTagMapper.insertMmsTag(mmsTag);
    }

    /**
     * 修改标签
     * 
     * @param mmsTag 标签
     * @return 结果
     */
    @Override
    public int updateMmsTag(MmsTag mmsTag)
    {
        mmsTag.setEditBy(SecurityUtils.getUsername());
        mmsTag.setEditTime(DateUtils.getNowDate());
        return mmsTagMapper.updateMmsTag(mmsTag);
    }

    /**
     * 批量删除标签
     * 
     * @param tagIds 需要删除的标签主键
     * @return 结果
     */
    @Override
    public int deleteMmsTagByTagIds(Long[] tagIds) {
        return mmsTagMapper.deleteMmsTagByTagIds(tagIds);
    }

    /**
     * 删除标签信息
     * 
     * @param id 标签主键
     * @return 结果
     */
    @Override
    public int deleteMmsTagById(Long id)
    {
        return mmsTagMapper.deleteMmsTagById(id);
    }

    /**
     * 构建 标签树
     * @param mmsTags 标签列表
     * @return  树结构列表
     */
    @Override
    public List<MmsTag> buildMmsTagTree(List<MmsTag> mmsTags) {
        List<MmsTag> resultList = new ArrayList<>();
        List<Long> tempList = new ArrayList<>();
        for (MmsTag tag : mmsTags) {
            tempList.add(tag.getTagId());
        }

        Iterator<MmsTag> iterator = mmsTags.iterator();
        for (mmsTags.iterator(); iterator.hasNext();) {
            MmsTag mmsTag = iterator.next();
            if (!tempList.contains(mmsTag.getTagParentsId())){
                recursionFn(mmsTags,mmsTag);
                resultList.add(mmsTag);
            }
        }
        if (resultList.isEmpty()){
            resultList = mmsTags;
        }
        return resultList;
    }

    @Override
    public boolean hasChildByTagId(Long tagId) {
        int l = mmsTagMapper.hasChildByTagId(tagId);
        return l>0;
    }

    /**
     * 递归 列表
     * @param mmsTags 总列表
     * @param mmsTag 单个值
     */
    private void recursionFn(List<MmsTag> mmsTags, MmsTag mmsTag) {
        List<MmsTag> childList = getChildList(mmsTags,mmsTag);
        mmsTag.setChildren(childList);
        for (MmsTag tChild : childList) {
            if (hasChild(mmsTags,tChild)){
                recursionFn(mmsTags,tChild);
            }
        }
    }

    /**
     * 判断是否还有子节点
     *
     * @param mmsTags 总列表
     * @param tChild 子节点
     * @return 结果
     */
    private boolean hasChild(List<MmsTag> mmsTags, MmsTag tChild) {
        return getChildList(mmsTags,tChild).size()>0;
    }

    /**
     * 获取 子节点列表
     * @param mmsTags 总节点列表
     * @param tChild 子节点
     * @return 结果
     */
    private List<MmsTag> getChildList(List<MmsTag> mmsTags, MmsTag tChild) {
        List<MmsTag> tlist = new ArrayList<>();
        Iterator<MmsTag> iterator = mmsTags.iterator();
        while (iterator.hasNext()){
            MmsTag next = iterator.next();
            if (next.getTagParentsId().longValue() == tChild.getTagId().longValue()){
                tlist.add(next);
            }
        }
        return tlist;
    }
}
