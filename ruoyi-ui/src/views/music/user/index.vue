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
      <el-form-item label="用户昵称" prop="userNickname">
        <el-input
          v-model="queryParams.userNickname"
          placeholder="请输入用户昵称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户状态" prop="userStatus">        
        <el-select 
          v-model="queryParams.userStatus"
          placeholder="请选择用户状态" 
          clearable 
          @keyup.enter.native="handleQuery"
        >
        <el-option v-for="(item, index) in userStatusOptions" 
          :key="index" :label="item.label"
          :value="item.value" 
          :disabled="item.disabled"/>
        </el-select>
      </el-form-item>

      <el-form-item label="用户性别" prop="userGender">
        <el-select
          v-model="queryParams.userGender"
          placeholder="请选择用户性别"
          clearable
          @keyup.enter.native="handleQuery"
        >
        <el-option v-for="(item, index) in userGenderOptions" :key="index" :label="item.label"
            :value="item.value" :disabled="item.disabled"/>
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
          v-hasPermi="['music:user:add']"
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
          v-hasPermi="['music:user:edit']"
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
          v-hasPermi="['music:user:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['music:user:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户id" align="center" prop="userId" />
      <el-table-column label="用户昵称" align="center" prop="userNickname" />
      <el-table-column label="用户头像" align="center" prop="userImgUrl" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.userImgUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="用户性别" align="center" prop="userGender" :formatter="genderFormat" />
      <el-table-column label="用户状态" align="center" key="userStatus" prop="userStatus" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.userStatus"
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
            v-hasPermi="['music:user:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['music:user:remove']"
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

    <!-- 添加或修改用户对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户昵称" prop="userNickname">
          <el-input v-model="form.userNickname" placeholder="请输入用户昵称" />
        </el-form-item>
        <el-form-item label="用户头像">
          <image-upload v-model="form.userImgUrl"/>
        </el-form-item>

        <el-form-item label ="用户性别" prop="userGender">
          <el-radio-group v-model="form.userGender">
            <el-radio  :label=1>男</el-radio>
            <el-radio  :label=2>女</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="用户状态" prop="userStatus">
          <el-switch
            v-model="form.userStatus"
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
import { listUser, getUser, delUser, addUser, updateUser } from "@/api/music/user";

export default {
  name: "User",
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
      // 用户表格数据
      userList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        userNickname: null,
        userImgUrl: null,
        userGender: null,
        userStatus: null,
        editBy: null,
        editTime: null,
        revision: null,
        isDel: null
      },userStatusOptions: [{
        "label": "启用",
        "value": 1
      }, {
        "label": "停用",
        "value": 0
      }],userGenderOptions: [{
        "label": "未知",
        "value": 0
      }, {
        "label": "男",
        "value": 1
      }, {
        "label": "女",
        "value": 2
      }],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userNickname: [
          { required: true, message: "用户昵称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询用户列表 */
    getList() {
      this.loading = true;
      listUser(this.queryParams).then(response => {
        this.userList = response.rows;
        console.log(response.rows);
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
        userNickname: null,
        userImgUrl: null,
        userGender: null,
        userStatus: null,
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
      this.open = true;
      this.title = "添加用户";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getUser(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {            
            updateUser(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            console.log(this.form);
            addUser(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除用户编号为"' + row.userId + '"的数据项？').then(function() {
        return delUser(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('music/user/export', {
        ...this.queryParams
      }, `user_${new Date().getTime()}.xlsx`)
    },
    /** 修改用户性别显示 */
    genderFormat(row){
      if(row.userGender === 0 || row.userGender === null){
        return '未知'
      }else if(row.userGender === 1){
        return '男'
      }else{
        return '女'
      }
    },
    /** 修改用户状态 */
    handleStatusChange(row){      
      let text = row.userStatus === 1 ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.userNickname + '"用户吗？').then(function() {
        return updateUser(row);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.userStatus = row.userStatus === 0 ? 1 : 0;
      });
    }
  }
};
</script>
