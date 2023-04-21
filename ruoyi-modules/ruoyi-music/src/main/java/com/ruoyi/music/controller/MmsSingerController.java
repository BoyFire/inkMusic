package com.ruoyi.music.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.music.entity.MmsSinger;
import com.ruoyi.music.service.IMmsSingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 歌手Controller
 * 
 * @author ruoyi
 * @date 2022-10-16
 */
@RestController
@RequestMapping("/singer")
public class MmsSingerController extends BaseController
{
    @Autowired
    private IMmsSingerService mmsSingerService;

    /**
     * 查询歌手列表
     */
    @RequiresPermissions("music:singer:list")
    @GetMapping("/list")
    public TableDataInfo list(MmsSinger mmsSinger)
    {
        startPage();
        List<MmsSinger> list = mmsSingerService.selectMmsSingerList(mmsSinger);
        return getDataTable(list);
    }

    /**
     * 导出歌手列表
     */
    @RequiresPermissions("music:singer:export")
    @Log(title = "歌手", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MmsSinger mmsSinger)
    {
        List<MmsSinger> list = mmsSingerService.selectMmsSingerList(mmsSinger);
        ExcelUtil<MmsSinger> util = new ExcelUtil<MmsSinger>(MmsSinger.class);
        util.exportExcel(response, list, "歌手数据");
    }

    /**
     * 获取歌手详细信息
     */
    @RequiresPermissions("music:singer:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mmsSingerService.selectMmsSingerById(id));
    }

    /**
     * 新增歌手
     */
    @RequiresPermissions("music:singer:add")
    @Log(title = "歌手", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MmsSinger mmsSinger)
    {
        return toAjax(mmsSingerService.insertMmsSinger(mmsSinger));
    }

    /**
     * 修改歌手
     */
    @RequiresPermissions("music:singer:edit")
    @Log(title = "歌手", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MmsSinger mmsSinger)
    {
        return toAjax(mmsSingerService.updateMmsSinger(mmsSinger));
    }

    /**
     * 删除歌手
     */
    @RequiresPermissions("music:singer:remove")
    @Log(title = "歌手", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mmsSingerService.deleteMmsSingerByIds(ids));
    }

    /**
     * 查询歌手列表
     */
    @RequiresPermissions("music:singer:list")
    @GetMapping("/listIdAndName")
    public AjaxResult listIdAndName()
    {
        return AjaxResult.success(mmsSingerService.selectSimpleSingerList());
    }

    @GetMapping("/getSimpleSingerByName")
    public AjaxResult getSimpleSingerByName(@RequestParam("query")String singerName){
        return AjaxResult.success(mmsSingerService.selectSimpleSingerListBySingerName(singerName));
    }
}
