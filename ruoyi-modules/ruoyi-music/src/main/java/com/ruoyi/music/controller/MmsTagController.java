package com.ruoyi.music.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.music.entity.MmsTag;
import com.ruoyi.music.service.IMmsSongTagService;
import com.ruoyi.music.service.IMmsTagService;
import com.ruoyi.music.vo.front.AddSongTagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 标签Controller
 *
 * @author ruoyi
 * @date 2022-10-25
 */
@RestController
@RequestMapping("/tag")
public class MmsTagController extends BaseController {
    @Autowired
    private IMmsTagService mmsTagService;

    @Resource
    private IMmsSongTagService mmsSongTagService;

    /**
     * 查询标签列表
     */
    @RequiresPermissions("music:tag:list")
    @GetMapping("/list")
    public AjaxResult list(MmsTag mmsTag) {
        return AjaxResult.success(mmsTagService.selectMmsTagList(mmsTag));
    }

    /**
     * 导出标签列表
     */
    @RequiresPermissions("music:tag:export")
    @Log(title = "标签", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MmsTag mmsTag) {
        List<MmsTag> list = mmsTagService.selectMmsTagList(mmsTag);
        ExcelUtil<MmsTag> util = new ExcelUtil<MmsTag>(MmsTag.class);
        util.exportExcel(response, list, "标签数据");
    }

    /**
     * 获取标签详细信息
     */
    @RequiresPermissions("music:tag:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(mmsTagService.selectMmsTagById(id));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeSelect")
    public AjaxResult treeSelect(MmsTag mmsTag) {
        return AjaxResult.success(mmsTagService.buildMmsTagTree(mmsTagService.selectMmsTagList(mmsTag)));
    }

    /**
     * 新增标签
     */
    @RequiresPermissions("music:tag:add")
    @Log(title = "标签", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MmsTag mmsTag) {
        return toAjax(mmsTagService.insertMmsTag(mmsTag));
    }

    /**
     * 修改标签
     */
    @RequiresPermissions("music:tag:edit")
    @Log(title = "标签", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MmsTag mmsTag) {
        return toAjax(mmsTagService.updateMmsTag(mmsTag));
    }

    /**
     * 删除标签
     */
    @RequiresPermissions("music:tag:remove")
    @Log(title = "标签", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tagIds}")
    public AjaxResult remove(@PathVariable Long[] tagIds) {
        for (Long tagId : tagIds) {
            if (mmsTagService.hasChildByTagId(tagId))
            {
                return AjaxResult.error("存在子标签,不允许删除");
            }
        }
        return toAjax(mmsTagService.deleteMmsTagByTagIds(tagIds));
    }

    /**
     * 根据歌曲id 查询标签
     */
    @RequiresPermissions("music:tag:query")
    @Log(title = "标签", businessType = BusinessType.DELETE)
    @GetMapping("/getMmsTags/{songId}")
    public AjaxResult getMmsTagsBySongId(@PathVariable("songId") Long songId) {
        return AjaxResult.success(mmsTagService.selectMmsTagBySongId(songId));
    }

    /**
     * 根据歌曲id 查询标签列表
     */
    @RequiresPermissions("music:tag:query")
    @GetMapping("/getSimpleTags/{songId}")
    public AjaxResult getSimpleTagsBySongId(@PathVariable("songId") Long songId) {
        return AjaxResult.success(mmsTagService.selectSimpleTagsBySongId(songId));
    }

    /**
     * 根据标签父类id 查询标签列表
     */
    @RequiresPermissions("music:tag:query")
    @GetMapping("/getSimpleTagsByTagParentsId/{parentsId}")
    public AjaxResult getSimpleTagsByTagParentsId(@PathVariable("parentsId") Long parentsId) {
        return AjaxResult.success(mmsTagService.selectSimpleTagsByParentsId(parentsId));
    }

    /**
     * 删除标签
     */
    @RequiresPermissions("music:tag:remove")
    @Log(title = "标签", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteSongTagByTagId/{tagId}")
    public AjaxResult deleteSongTagByTagId(@PathVariable("tagId") Long tagId) {
        return toAjax(mmsSongTagService.deleteSongTagByTagId(tagId));
    }

    /**
     * 新增标签
     */
    @RequiresPermissions("music:tag:add")
    @Log(title = "标签", businessType = BusinessType.INSERT)
    @PostMapping("/addSongTags/{songId}")
    public AjaxResult addSongTags(@RequestBody AddSongTagVo addSongTagVo, @PathVariable("songId")Long songId) {
        return toAjax(mmsSongTagService.insertSongTags(addSongTagVo, songId));
    }
}
