package com.ruoyi.music.controller;

import com.alibaba.nacos.shaded.com.google.protobuf.ServiceException;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.JwtUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.auth.AuthUtil;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.music.entity.MmsUser;
import com.ruoyi.music.model.MmsLoginUser;
import com.ruoyi.music.model.dto.UserLoginDTO;
import com.ruoyi.music.model.dto.UserRegisterDTO;
import com.ruoyi.music.service.IMmsUserService;
import com.ruoyi.music.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户Controller
 *
 * @author ruoyi
 * @date 2022-10-16
 */
@RestController
@RequestMapping("/user")
public class MmsUserController extends BaseController
{
    @Autowired
    private IMmsUserService mmsUserService;
    @Autowired
    private ITokenService tokenService;

    /**
     * 查询用户列表
     */
    @RequiresPermissions("music:user:list")
    @GetMapping("/list")
    public TableDataInfo list(MmsUser mmsUser)
    {
        startPage();
        List<MmsUser> list = mmsUserService.selectMmsUserList(mmsUser);
        return getDataTable(list);
    }

    /**
     * 导出用户列表
     */
    @RequiresPermissions("music:user:export")
    @Log(title = "用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MmsUser mmsUser)
    {
        List<MmsUser> list = mmsUserService.selectMmsUserList(mmsUser);
        ExcelUtil<MmsUser> util = new ExcelUtil<MmsUser>(MmsUser.class);
        util.exportExcel(response, list, "用户数据");
    }

    /**
     * 获取用户详细信息
     */
    @RequiresPermissions("music:user:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mmsUserService.selectMmsUserById(id));
    }

    /**
     * 获取用户姓名及id
     */
    @RequiresPermissions("music:user:query")
    @GetMapping("/listUserIdAndNickName")
    public AjaxResult listUserIdAndNickname()
    {
        return AjaxResult.success(mmsUserService.selectSimpleUsers());
    }

    /**
     * 新增用户
     */
    @RequiresPermissions("music:user:add")
    @Log(title = "用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MmsUser mmsUser)
    {
        return toAjax(mmsUserService.insertMmsUser(mmsUser));
    }

    /**
     * 修改用户
     */
    @RequiresPermissions("music:user:edit")
    @Log(title = "用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MmsUser mmsUser)
    {
        return toAjax(mmsUserService.updateMmsUser(mmsUser));
    }

    /**
     * 删除用户
     */
    @RequiresPermissions("music:user:remove")
    @Log(title = "用户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mmsUserService.deleteMmsUserByIds(ids));
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("music:user:edit")
    @Log(title = "用户", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody MmsUser mmsUser)
    {
        mmsUser.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(mmsUserService.updateUserStatus(mmsUser));
    }

    @GetMapping("/getSimpleUserByName")
    public AjaxResult getSimpleSingerByName(@RequestParam("query")String userName){
        return AjaxResult.success(mmsUserService.selectSimpleUserListByName(userName));
    }

    /**
     * 客户端账号登录
     *
     */
    @PostMapping("/login")
    public R login(@RequestBody @Validated UserLoginDTO userLoginDTO){
        MmsLoginUser mmsLoginUser;
        try {
            mmsLoginUser =  mmsUserService.login(userLoginDTO);
        } catch (ServiceException e) {
            return R.fail(e.getMessage());
        }
        return R.ok(tokenService.createToken(mmsLoginUser)) ;
    }

    /**
     * 客户端账号注册
     */
    @PostMapping("/register")
    public AjaxResult register(@RequestBody @Validated UserRegisterDTO userRegisterDTO){
        int rows ;
        try {
            rows =  mmsUserService.insertMmsUser(userRegisterDTO);
        } catch (ServiceException e) {
            return AjaxResult.error(e.getMessage());
        }
        return toAjax(rows) ;
    }

    @DeleteMapping("/logout")
    public R<?> logout(HttpServletRequest request){
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token)){
            JwtUtils.getUserName(token);
            AuthUtil.logoutByToken(token);
        }
        return R.ok();
    }

    // TODO 待测试
    @GetMapping("/id={id}")
    public AjaxResult getUserInfoById(@PathVariable("id")Long userId){
        return AjaxResult.success(mmsUserService.selectMmsUserByUserId(userId));
    }


}
