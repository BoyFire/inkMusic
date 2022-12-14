package com.ruoyi.music.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.music.entity.MmsAlbum;
import com.ruoyi.music.service.IMmsAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 专辑Controller
 * 
 * @author ruoyi
 * @date 2022-10-20
 */
@RestController
@RequestMapping("/album")
public class MmsAlbumController extends BaseController
{
    @Autowired
    private IMmsAlbumService mmsAlbumService;

    /**
     * 查询专辑列表
     */
    @RequiresPermissions("music:album:list")
    @GetMapping("/list")
    public TableDataInfo list(MmsAlbum mmsAlbum)
    {
        startPage();
        List<MmsAlbum> list = mmsAlbumService.selectMmsAlbumList(mmsAlbum);
        return getDataTable(list);
    }

    /**
     * 导出专辑列表
     */
    @RequiresPermissions("music:album:export")
    @Log(title = "专辑", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MmsAlbum mmsAlbum)
    {
        List<MmsAlbum> list = mmsAlbumService.selectMmsAlbumList(mmsAlbum);
        ExcelUtil<MmsAlbum> util = new ExcelUtil<MmsAlbum>(MmsAlbum.class);
        util.exportExcel(response, list, "专辑数据");
    }

    /**
     * 获取专辑详细信息
     */
    @RequiresPermissions("music:album:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mmsAlbumService.selectMmsAlbumById(id));
    }

    /**
     * 新增专辑
     */
    @RequiresPermissions("music:album:add")
    @Log(title = "专辑", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MmsAlbum mmsAlbum)
    {
        return toAjax(mmsAlbumService.insertMmsAlbum(mmsAlbum));
    }

    /**
     * 修改专辑
     */
    @RequiresPermissions("music:album:edit")
    @Log(title = "专辑", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MmsAlbum mmsAlbum)
    {
        return toAjax(mmsAlbumService.updateMmsAlbum(mmsAlbum));
    }

    /**
     * 删除专辑
     */
    @RequiresPermissions("music:album:remove")
    @Log(title = "专辑", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mmsAlbumService.deleteMmsAlbumByIds(ids));
    }


    /**
     * 通过歌手id查询简单专辑列表
     */
    @RequiresPermissions("music:album:query")
    @GetMapping("/list/{singerId}")
    public AjaxResult list(@PathVariable("singerId") Long singerId)
    {
        return AjaxResult.success(mmsAlbumService.selectSimpleAlbumsBySingerId(singerId));
    }

    /**
     * 获取简单专辑列表
     */
    @GetMapping("/listSimpleAlbum")
    public AjaxResult listSimpleAlbum()
    {
        return AjaxResult.success(mmsAlbumService.listSimpleAlbums());
    }
}
