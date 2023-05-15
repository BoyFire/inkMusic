package com.ruoyi.music.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.music.entity.MmsComment;
import com.ruoyi.music.service.IMmsCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 评论Controller
 * 
 * @author ruoyi
 * @date 2022-10-24
 */
@RestController
@RequestMapping("/comment")
public class MmsCommentController extends BaseController
{
    @Autowired
    private IMmsCommentService mmsCommentService;

    /**
     * 查询评论列表
     */
    @RequiresPermissions("music:comment:list")
    @GetMapping("/list")
    public TableDataInfo list(MmsComment mmsComment)
    {
        startPage();
        List<MmsComment> list = mmsCommentService.selectMmsCommentList(mmsComment);
        return getDataTable(list);
    }

    /**
     * 导出评论列表
     */
    @RequiresPermissions("music:comment:export")
    @Log(title = "评论", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MmsComment mmsComment)
    {
        List<MmsComment> list = mmsCommentService.selectMmsCommentList(mmsComment);
        ExcelUtil<MmsComment> util = new ExcelUtil<MmsComment>(MmsComment.class);
        util.exportExcel(response, list, "评论数据");
    }

    /**
     * 获取评论详细信息
     */
    @RequiresPermissions("music:comment:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mmsCommentService.selectMmsCommentById(id));
    }

    /**
     * 新增评论
     */
    @RequiresPermissions("music:comment:add")
    @Log(title = "评论", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MmsComment mmsComment)
    {
        return toAjax(mmsCommentService.insertMmsComment(mmsComment));
    }

    /**
     * 修改评论
     */
    @RequiresPermissions("music:comment:edit")
    @Log(title = "评论", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MmsComment mmsComment)
    {
        return toAjax(mmsCommentService.updateMmsComment(mmsComment));
    }

    /**
     * 删除评论
     */
    @RequiresPermissions("music:comment:remove")
    @Log(title = "评论", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mmsCommentService.deleteMmsCommentByIds(ids));
    }

    @GetMapping("/detail")
    public AjaxResult getComment(@RequestParam("type")Integer type, @RequestParam("id")Long id){
        return AjaxResult.success(mmsCommentService.selectMmsCommentListByTypeAndId(type,id));
    }
}
