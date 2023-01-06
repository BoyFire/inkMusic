package com.ruoyi.music.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.music.entity.MmsUserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.music.service.IMmsUserAuthService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 用户认证Controller
 * 
 * @author ruoyi
 * @date 2022-10-19
 */
@RestController
@RequestMapping("/auth")
public class MmsUserAuthController extends BaseController
{
    @Autowired
    private IMmsUserAuthService mmsUserAuthService;

    /**
     * 查询用户认证列表
     */
    @RequiresPermissions("music:auth:list")
    @GetMapping("/list")
    public TableDataInfo list(MmsUserAuth mmsUserAuth)
    {
        startPage();
        List<MmsUserAuth> list = mmsUserAuthService.selectMmsUserAuthList(mmsUserAuth);
        return getDataTable(list);
    }

    /**
     * 导出用户认证列表
     */
    @RequiresPermissions("music:auth:export")
    @Log(title = "用户认证", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MmsUserAuth mmsUserAuth)
    {
        List<MmsUserAuth> list = mmsUserAuthService.selectMmsUserAuthList(mmsUserAuth);
        ExcelUtil<MmsUserAuth> util = new ExcelUtil<MmsUserAuth>(MmsUserAuth.class);
        util.exportExcel(response, list, "用户认证数据");
    }

    /**
     * 获取用户认证详细信息
     */
    @RequiresPermissions("music:auth:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mmsUserAuthService.selectMmsUserAuthById(id));
    }

    /**
     * 新增用户认证
     */
    @RequiresPermissions("music:auth:add")
    @Log(title = "用户认证", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MmsUserAuth mmsUserAuth)
    {
        if (mmsUserAuthService.insertMmsUserAuth(mmsUserAuth)==0) {
            return AjaxResult.error("该用户账号方式已存在,请选择其他");
        }
        return AjaxResult.success();
    }

    /**
     * 修改用户认证
     */
    @RequiresPermissions("music:auth:edit")
    @Log(title = "用户认证", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MmsUserAuth mmsUserAuth)
    {
        return toAjax(mmsUserAuthService.updateMmsUserAuth(mmsUserAuth));
    }

    /**
     * 删除用户认证
     */
    @RequiresPermissions("music:auth:remove")
    @Log(title = "用户认证", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mmsUserAuthService.deleteMmsUserAuthByIds(ids));
    }

    /**
     * 查询用户id列表 去重
     */
    @RequiresPermissions("music:auth:userIdList")
    @GetMapping("/userIdList")
    public AjaxResult userIdList()
    {
        return AjaxResult.success(mmsUserAuthService.selectMmsUserIds());
    }

}
