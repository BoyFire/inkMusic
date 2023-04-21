package com.ruoyi.music.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.music.entity.MmsMovie;
import com.ruoyi.music.service.IMmsMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 视频Controller
 * 
 * @author ruoyi
 * @date 2022-10-16
 */
@RestController
@RequestMapping("/movie")
public class MmsMovieController extends BaseController
{
    @Autowired
    private IMmsMovieService mmsMovieService;

    /**
     * 查询视频列表
     */
    @RequiresPermissions("music:movie:list")
    @GetMapping("/list")
    public TableDataInfo list(MmsMovie mmsMovie)
    {
        startPage();
        List<MmsMovie> list = mmsMovieService.selectMmsMovieList(mmsMovie);
        return getDataTable(list);
    }

    /**
     * 获取简单视频列表
     */
    @GetMapping("/listSimpleMovie")
    public AjaxResult listSimpleMovie()
    {
        return AjaxResult.success(mmsMovieService.listSimpleMovie());
    }

    /**
     * 导出视频列表
     */
    @RequiresPermissions("music:movie:export")
    @Log(title = "视频", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MmsMovie mmsMovie)
    {
        List<MmsMovie> list = mmsMovieService.selectMmsMovieList(mmsMovie);
        ExcelUtil<MmsMovie> util = new ExcelUtil<MmsMovie>(MmsMovie.class);
        util.exportExcel(response, list, "视频数据");
    }

    /**
     * 获取视频详细信息
     */
    @RequiresPermissions("music:movie:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mmsMovieService.selectMmsMovieById(id));
    }

    /**
     * 新增视频
     */
    @RequiresPermissions("music:movie:add")
    @Log(title = "视频", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MmsMovie mmsMovie)
    {
        return toAjax(mmsMovieService.insertMmsMovie(mmsMovie));
    }

    /**
     * 修改视频
     */
    @RequiresPermissions("music:movie:edit")
    @Log(title = "视频", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MmsMovie mmsMovie)
    {
        return toAjax(mmsMovieService.updateMmsMovie(mmsMovie));
    }

    /**
     * 删除视频
     */
    @RequiresPermissions("music:movie:remove")
    @Log(title = "视频", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mmsMovieService.deleteMmsMovieByIds(ids));
    }

    //getSimpleMvsByName
    @GetMapping("/getSimpleMvsByName")
    public AjaxResult getSimpleMvsByName(@RequestParam("query")String mvName) {
        return AjaxResult.success(mmsMovieService.getSimpleMvsByName(mvName));
    }


}
