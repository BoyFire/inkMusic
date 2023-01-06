<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户id" prop="userId">
         <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="认证方式" prop="userAuthType">
         <el-select 
          v-model="queryParams.userAuthType"
          placeholder="请选择账号方式" 
          clearable 
          @keyup.enter.native="handleQuery"
        >
        <el-option v-for="(item, index) in userAuthTypeOptions" 
          :key="index" :label="item.label"
          :value="item.value" 
          :disabled="item.disabled"/>
        </el-select>
      </el-form-item>      
      
      <el-form-item label="账号状态" prop="userAuthStatus">
        <el-select 
          v-model="queryParams.userAuthStatus"
          placeholder="请选择账号状态" 
          clearable 
          @keyup.enter.native="handleQuery"
        >
        <el-option v-for="(item, index) in userAuthStatusOptions" 
          :key="index" :label="item.label"
          :value="item.value" 
          :disabled="item.disabled"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['music:auth:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['music:auth:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['music:auth:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['music:auth:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="authList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />  
      <el-table-column label="用户id" align="center" prop="userId" />
      <el-table-column label="认证方式" align="center" prop="userAuthType" :formatter="userAuthTypeFormat" />
      <el-table-column label="认证账号" align="center" prop="userAuthLogin" />      
      <el-table-column label="认证状态" align="center" prop="userAuthStatus" >
          <template slot-scope="scope">
          <el-switch
            v-model="scope.row.userAuthStatus"
            :active-value=1
            :inactive-value=0
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>

      </el-table-column>
      <el-table-column label="更新者" align="center" prop="editBy" />
      <el-table-column label="更新时间" align="center" prop="editTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.editTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['music:auth:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['music:auth:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改用户认证对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item  label="用户id" prop="userId">
          <el-select v-model="form.userId"
            placeholder="请选择用户id"
            :disabled="form.userIdDisabled"
          >
            <el-option v-for="item in userAuth"
              :key="item" :value="item" :label="item"/>
          </el-select>
        </el-form-item>
        <el-form-item label="认证方式" prop="userAuthType">
         <el-select 
            :disabled="form.userAuthTypeDisabled"
            v-model="form.userAuthType"
            placeholder="请选择认证方式" 
            clearable
        >
        <el-option v-for="(item, index) in userAuthTypeOptions" 
          :key="index" :label="item.label"
          :value="item.value" 
          :disabled="item.disabled"/>
        </el-select>
      </el-form-item>

        <el-form-item label="认证账号" prop="userAuthLogin" >
          <el-input v-model="form.userAuthLogin" placeholder="请输入用户认证账号"  :disabled="form.userAuthLoginDisabled"/>
        </el-form-item>
        <el-form-item label="认证密码" prop="userAuthPassword">
          <el-input v-model="form.userAuthPassword" placeholder="请输入用户认证密码" />
        </el-form-item>

      <el-form-item label="认证状态" prop="userAuthStatus">
          <el-switch
            v-model="form.userAuthStatus"
            :active-value=1
            :inactive-value=0
            active-text="启用"
            inactive-text="停用"
          ></el-switch>
        </el-form-item>  

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAuth, getAuth, delAuth, addAuth, updateAuth,getUserIds } from "@/api/music/auth";

export default {
  name: "Auth",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户认证表格数据
      authList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        userAuthType: null,
        userAuthLogin: null,
        userAuthPassword: null,
        userAuthStatus: null,
        editBy: null,
        editTime: null,
        revision: null,
        isDel: null
      },
      // 添加用户认证中 用户id 列表
      userAuth:null,
      // 用户账号状态
      userAuthStatusOptions: [{
        "label": "启用",
        "value": 1
      }, {
        "label": "停用",
        "value": 0
      }],
      // 用户认证方式
      userAuthTypeOptions: [{
        "label": "账号",
        "value": 0
      }, {
        "label": "邮箱",
        "value": 1
      }, {
        "label": "手机",
        "value": 2
      }],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "用户id", trigger: "change" }
        ],
        userAuthType: [
          { required: true, message: "用户认证方式", trigger: "change" }
        ],
        userAuthLogin: [
          { required: true, message: "用户认证账号不能为空", trigger: "blur" }
        ],
        userAuthPassword: [
          { required: true, message: "用户认证密码", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询用户认证列表 */
    getList() {
      this.loading = true;
      listAuth(this.queryParams).then(response => {
        this.authList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        userId: null,
        userAuthType: null,
        userAuthLogin: null,
        userAuthPassword: null,
        userAuthStatus: 0,
        createBy: null,
        createTime: null,
        editBy: null,
        editTime: null,
        revision: null,
        isDel: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      getUserIds().then(response =>{
        console.log(response);
        this.userAuth = response.data;
      })
      this.open = true;
      this.title = "添加用户认证";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAuth(id).then(response => {
        this.form = response.data;
        this.form.userAuthTypeDisabled = true;
        this.form.userIdDisabled = true;
        this.form.userAuthLoginDisabled = true;
        this.open = true;
        this.title = "修改用户认证";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAuth(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAuth(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除用户认证编号为"' + ids + '"的数据项？').then(function() {
        return delAuth(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('music/auth/export', {
        ...this.queryParams
      }, `auth_${new Date().getTime()}.xlsx`)
    },  
    /** 用户认证方式转化 */
    userAuthTypeFormat(row){
      let type = row.userAuthType
      if(type === 0){
          return '账号'
        }else if(type === 1){
          return '邮箱'
        }else{
          return '手机'
        }
    },
    /** 修改用户认证状态 */
    handleStatusChange(row){
      let text = row.userAuthStatus === 1 ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '"用户id为"' + row.userId + '"用户吗？').then(function() {
        return updateAuth(row);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.userAuthStatus = row.userAuthStatus === 0 ? 1 : 0;
      });
    },
  },
};
</script>
