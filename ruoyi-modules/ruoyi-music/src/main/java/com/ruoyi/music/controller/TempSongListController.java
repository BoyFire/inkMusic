package com.ruoyi.music.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.music.service.ITempSongListService;
import com.ruoyi.music.temp.TempSongList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * api歌单信息Controller
 * 
 * @author ruoyi
 * @date 2023-05-20
 */
@RestController
@RequestMapping("/tempSongList")
public class TempSongListController extends BaseController
{
    @Autowired
    private ITempSongListService tempSongListService;

    /**
     * 查询api歌单信息列表
     */
    @RequiresPermissions("music:tempSongList:list")
    @GetMapping("/list")
    public TableDataInfo list(TempSongList tempSongList)
    {
        startPage();
        List<TempSongList> list = tempSongListService.selectTempSongListList(tempSongList);
        return getDataTable(list);
    }

    /**
     * 导出api歌单信息列表
     */
    @RequiresPermissions("music:tempSongList:export")
    @Log(title = "api歌单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TempSongList tempSongList)
    {
        List<TempSongList> list = tempSongListService.selectTempSongListList(tempSongList);
        ExcelUtil<TempSongList> util = new ExcelUtil<TempSongList>(TempSongList.class);
        util.exportExcel(response, list, "api歌单信息数据");
    }

    /**
     * 获取api歌单信息详细信息
     */
    @RequiresPermissions("music:tempSongList:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tempSongListService.selectTempSongListById(id));
    }

    /**
     * 新增api歌单信息
     */
    @RequiresPermissions("music:tempSongList:add")
    @Log(title = "api歌单信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TempSongList tempSongList)
    {
        return toAjax(tempSongListService.insertTempSongList(tempSongList));
    }

    /**
     * 修改api歌单信息
     */
    @RequiresPermissions("music:tempSongList:edit")
    @Log(title = "api歌单信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TempSongList tempSongList)
    {
        return toAjax(tempSongListService.updateTempSongList(tempSongList));
    }

    /**
     * 删除api歌单信息
     */
    @RequiresPermissions("music:tempSongList:remove")
    @Log(title = "api歌单信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tempSongListService.deleteTempSongListByIds(ids));
    }
}
