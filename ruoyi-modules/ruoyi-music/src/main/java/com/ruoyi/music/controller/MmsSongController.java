package com.ruoyi.music.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.music.entity.MmsSong;
import com.ruoyi.music.service.IMmsSongService;
import com.ruoyi.music.vo.front.SongParamsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 歌曲Controller
 *
 * @author ruoyi
 * @date 2022-10-20
 */
@RestController
@RequestMapping("/song")
public class MmsSongController extends BaseController {
    @Autowired
    private IMmsSongService mmsSongService;

    /**
     * 查询歌曲列表
     */
    @RequiresPermissions("music:song:list")
    @GetMapping("/list")
    public TableDataInfo list(SongParamsVo songParams) {
        startPage();
        List<MmsSong> list = mmsSongService.selectMmsSongList(songParams);
        return getDataTable(list);
    }

    /**
     * 获取简单歌曲列表
     */
    @GetMapping("/listSimpleSong")
    public AjaxResult listSimpleSong() {
        return AjaxResult.success(mmsSongService.listSimpleSong());
    }

    /**
     * 导出歌曲列表
     */
    @RequiresPermissions("music:song:export")
    @Log(title = "歌曲", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SongParamsVo songParams) {
        List<MmsSong> list = mmsSongService.selectMmsSongList(songParams);
        ExcelUtil<MmsSong> util = new ExcelUtil<MmsSong>(MmsSong.class);
        util.exportExcel(response, list, "歌曲数据");
    }

    /**
     * 获取歌曲详细信息
     */
    @RequiresPermissions("music:song:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(mmsSongService.selectMmsSongById(id));
    }

    /**
     * 新增歌曲
     */
    @RequiresPermissions("music:song:add")
    @Log(title = "歌曲", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MmsSong mmsSong) {
        return toAjax(mmsSongService.insertMmsSong(mmsSong));
    }

    /**
     * 修改歌曲
     */
    @RequiresPermissions("music:song:edit")
    @Log(title = "歌曲", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MmsSong mmsSong) {
        return toAjax(mmsSongService.updateMmsSong(mmsSong));
    }

    /**
     * 删除歌曲
     */
    @RequiresPermissions("music:song:remove")
    @Log(title = "歌曲", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(mmsSongService.deleteMmsSongByIds(ids));
    }


    @GetMapping("/getSimpleSongByName")
    public AjaxResult getSimpleSingerByName(@RequestParam("songName") String songName) {
        return AjaxResult.success(mmsSongService.selectSimpleSongsBySongName(songName));
    }

    @GetMapping("/getRedirectUrl")
    public AjaxResult getRedirectUrl(@RequestParam("url") String url, HttpServletRequest request) {
        return AjaxResult.success().put("url",mmsSongService.getRedirectUrl(url, request));
    }




}
