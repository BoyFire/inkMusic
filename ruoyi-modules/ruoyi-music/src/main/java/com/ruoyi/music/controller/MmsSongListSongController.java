package com.ruoyi.music.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.music.entity.MmsSongListSong;
import com.ruoyi.music.service.IMmsSongListSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 歌单歌曲Controller
 * 
 * @author ruoyi
 * @date 2023-01-05
 */
@RestController
@RequestMapping("/songListSong")
public class MmsSongListSongController extends BaseController
{
    @Autowired
    private IMmsSongListSongService mmsSongListSongService;

    /**
     * 查询歌单歌曲列表
     */
    @RequiresPermissions("music:songListSong:list")
    @GetMapping("/list")
    public TableDataInfo list(MmsSongListSong mmsSongListSong)
    {
        startPage();
        List<MmsSongListSong> list = mmsSongListSongService.selectMmsSongListSongList(mmsSongListSong);
        return getDataTable(list);
    }


    /**
     * 导出歌单歌曲列表
     */
    @RequiresPermissions("music:songListSong:export")
    @Log(title = "歌单歌曲", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MmsSongListSong mmsSongListSong)
    {
        List<MmsSongListSong> list = mmsSongListSongService.selectMmsSongListSongList(mmsSongListSong);
        ExcelUtil<MmsSongListSong> util = new ExcelUtil<MmsSongListSong>(MmsSongListSong.class);
        util.exportExcel(response, list, "歌单歌曲数据");
    }

    /**
     * 获取歌单歌曲详细信息
     */
    @RequiresPermissions("music:songListSong:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mmsSongListSongService.selectMmsSongListSongById(id));
    }

    /**
     * 新增歌单歌曲
     */
    @RequiresPermissions("music:songListSong:add")
    @Log(title = "歌单歌曲", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MmsSongListSong mmsSongListSong)
    {
        return mmsSongListSongService.insertMmsSongListSong(mmsSongListSong);
    }

    /**
     * 修改歌单歌曲
     */
    @RequiresPermissions("music:songListSong:edit")
    @Log(title = "歌单歌曲", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MmsSongListSong mmsSongListSong)
    {

        return toAjax(mmsSongListSongService.updateMmsSongListSong(mmsSongListSong));
    }

    /**
     * 删除歌单歌曲
     */
    @RequiresPermissions("music:songListSong:remove")
    @Log(title = "歌单歌曲", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mmsSongListSongService.deleteMmsSongListSongByIds(ids));
    }

    @GetMapping("/detail")
    public AjaxResult detail(@RequestParam("songListId")Long songListId){
        return AjaxResult.success(mmsSongListSongService.selectMmsSongListSongBySongListId(songListId));
    }
}
