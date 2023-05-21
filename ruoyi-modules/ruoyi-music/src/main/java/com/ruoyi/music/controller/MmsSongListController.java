package com.ruoyi.music.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.nacos.shaded.com.google.protobuf.ServiceException;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.music.entity.MmsSongList;
import com.ruoyi.music.service.IMmsSongListService;
import com.ruoyi.music.service.ITempSongListService;
import com.ruoyi.music.temp.TempSongList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 歌单Controller
 *
 * @author ruoyi
 * @date 2022-10-24
 */
@RestController
@RequestMapping("/songList")
public class MmsSongListController extends BaseController {
    @Autowired
    private IMmsSongListService mmsSongListService;
    @Resource
    private ITempSongListService tempSongListService;

    /**
     * 查询歌单列表
     */
    @RequiresPermissions("music:songList:list")
    @GetMapping("/list")
    public TableDataInfo list(MmsSongList mmsSongList) {
        startPage();
        List<MmsSongList> list = mmsSongListService.selectMmsSongListList(mmsSongList);
        return getDataTable(list);
    }

    /**
     * 获取简单歌单列表
     */
    @GetMapping("/listSimpleSongList")
    public AjaxResult listSimpleSongList() {
        return AjaxResult.success(mmsSongListService.listSimpleSongList());
    }

    /**
     * 导出歌单列表
     */
    @RequiresPermissions("music:songList:export")
    @Log(title = "歌单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MmsSongList mmsSongList) {
        List<MmsSongList> list = mmsSongListService.selectMmsSongListList(mmsSongList);
        ExcelUtil<MmsSongList> util = new ExcelUtil<MmsSongList>(MmsSongList.class);
        util.exportExcel(response, list, "歌单数据");
    }

    /**
     * 获取歌单详细信息
     */
    @RequiresPermissions("music:songList:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(mmsSongListService.selectMmsSongListById(id));
    }

    /**
     * 新增歌单
     */
    @Log(title = "歌单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MmsSongList mmsSongList) {
        return toAjax(mmsSongListService.insertMmsSongList(mmsSongList));
    }

    /**
     * 修改歌单
     */
    @RequiresPermissions("music:songList:edit")
    @Log(title = "歌单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MmsSongList mmsSongList) {
        return toAjax(mmsSongListService.updateMmsSongList(mmsSongList));
    }

    /**
     * 删除歌单
     */
    @RequiresPermissions("music:songList:remove")
    @Log(title = "歌单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(mmsSongListService.deleteMmsSongListByIds(ids));
    }

    @GetMapping("/getSimpleSongListsByName")
    public AjaxResult getSimpleSongListsByName(@RequestParam("songName") String songListName) {
        return AjaxResult.success(mmsSongListService.selectSimpleSongsBySongName(songListName));
    }

    @GetMapping("/getUserSongLists")
    public AjaxResult getUserSongLists(@RequestParam("userId") Long userId) {
        return AjaxResult.success(mmsSongListService.selectMmsSongListByUserId(userId));
    }

    @GetMapping("/getUserCollectSongLists")
    public AjaxResult getUserCollectSongLists(@RequestParam("userId") Long userId) {
        return AjaxResult.success(mmsSongListService.selectUserCollectSongListByUserId(userId));
    }

    @GetMapping("/getUserCollectApiSongLists")
    public AjaxResult getUserCollectApiSongLists(@RequestParam("userId") Long userId) {
        return AjaxResult.success(mmsSongListService.selectUserCollectApiSongListByUserId(userId));
    }


    @GetMapping("/getRelated")
    public AjaxResult getRelated(@RequestParam("songListId") Long songListId) {
        return AjaxResult.success(mmsSongListService.selectMmsSongLisRelatedBySongListId(songListId));
    }

    @GetMapping("/getMySongListDetail")
    public AjaxResult getSongListDetail(@RequestParam("songListId") Long songListId) {
        return AjaxResult.success(mmsSongListService.selectMmsSongListBySongListId(songListId));
    }


    @PostMapping("/collectApiSongList")
    public AjaxResult collectSong(@RequestBody Map<String, Object> params) throws ServiceException {
        return toAjax(mmsSongListService.collectApiSongList(params));
    }

    @PostMapping("/disCollectApiSongList")
    public AjaxResult disCollectSong(@RequestBody Map<String, Object> params) {
        return toAjax(mmsSongListService.disCollectApiSongList(params));
    }

    @PostMapping("/isCollectApi")
    public AjaxResult isCollectApi(@RequestBody Map<String, Object> params) {
        long userId = Long.parseLong(String.valueOf(params.get("userId")));
        String apiSongListId = String.valueOf(params.get("apiSongListId"));
        TempSongList tempSongList = tempSongListService.selectTempSongListBySongListIdAndUserId(userId, apiSongListId);
        return AjaxResult.success(tempSongList != null);
    }

    @PostMapping("/deleteSongLists")
    public AjaxResult deleteSongLists(@RequestBody Map<String,Object> params){
        String ids = String.valueOf(params.get("ids"));
        List<Long> longs = JSON.parseArray(ids, Long.class);
        if (CollectionUtils.isEmpty(longs)){
            return AjaxResult.error("请选择数据");
        }
        return AjaxResult.success(mmsSongListService.deleteMmsSongListByIds(longs));
    }

}
