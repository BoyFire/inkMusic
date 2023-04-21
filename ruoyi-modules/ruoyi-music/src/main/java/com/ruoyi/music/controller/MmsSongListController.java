package com.ruoyi.music.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.music.entity.MmsSongList;
import com.ruoyi.music.service.IMmsSongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 歌单Controller
 * 
 * @author ruoyi
 * @date 2022-10-24
 */
@RestController
@RequestMapping("/songList")
public class MmsSongListController extends BaseController
{
    @Autowired
    private IMmsSongListService mmsSongListService;

    /**
     * 查询歌单列表
     */
    @RequiresPermissions("music:songList:list")
    @GetMapping("/list")
    public TableDataInfo list(MmsSongList mmsSongList)
    {
        startPage();
        List<MmsSongList> list = mmsSongListService.selectMmsSongListList(mmsSongList);
        return getDataTable(list);
    }

    /**
     * 获取简单歌单列表
     */
    @GetMapping("/listSimpleSongList")
    public AjaxResult listSimpleSongList() {
        return AjaxResult.success(mmsSongListService.listSimpleSongList()) ;
    }

    /**
     * 导出歌单列表
     */
    @RequiresPermissions("music:songList:export")
    @Log(title = "歌单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MmsSongList mmsSongList)
    {
        List<MmsSongList> list = mmsSongListService.selectMmsSongListList(mmsSongList);
        ExcelUtil<MmsSongList> util = new ExcelUtil<MmsSongList>(MmsSongList.class);
        util.exportExcel(response, list, "歌单数据");
    }

    /**
     * 获取歌单详细信息
     */
    @RequiresPermissions("music:songList:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mmsSongListService.selectMmsSongListById(id));
    }

    /**
     * 新增歌单
     */
    @RequiresPermissions("music:songList:add")
    @Log(title = "歌单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MmsSongList mmsSongList)
    {
        return toAjax(mmsSongListService.insertMmsSongList(mmsSongList));
    }

    /**
     * 修改歌单
     */
    @RequiresPermissions("music:songList:edit")
    @Log(title = "歌单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MmsSongList mmsSongList)
    {
        return toAjax(mmsSongListService.updateMmsSongList(mmsSongList));
    }

    /**
     * 删除歌单
     */
    @RequiresPermissions("music:songList:remove")
    @Log(title = "歌单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mmsSongListService.deleteMmsSongListByIds(ids));
    }

    @GetMapping("/getSimpleSongListsByName")
    public AjaxResult getSimpleSongListsByName(@RequestParam("songName")String songListName) {
        return AjaxResult.success(mmsSongListService.selectSimpleSongsBySongName(songListName));
    }

}
