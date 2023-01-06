<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="视频id" prop="movieId">
        <el-input
          v-model="queryParams.movieId"
          placeholder="请输入视频id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="视频名称" prop="movieName">
        <el-input
          v-model="queryParams.movieName"
          placeholder="请输入视频名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="上传者类型" prop="movieUpType" label-width="100px">        
        <el-select 
          v-model="queryParams.movieUpType"
          placeholder="请选择上传者类型" 
          clearable 
          @clear="updateUpType"
          @keyup.enter.native="handleQuery"                    
        >
        <el-option v-for="(item, index) in upTypeOptions" 
          :key="index" :label="item.label"
          :value="item.value" 
          :disabled="item.disabled"/>
        </el-select>
      </el-form-item>

      <el-form-item label="视频上传者" prop="movieUpId" label-width="100px" v-if="this.queryParams.movieUpType != null">
        <el-select
          v-if="this.queryParams.movieUpType == 1"
          v-model="queryParams.movieUpId"
          placeholder="请输入视频上传者"
          clearable
          @keyup.enter.native="handleQuery"
        >
        <el-option
          v-for="(item, index) in this.queryUser" 
          :key="index" :label="item.movieUpName"
          :value="item.movieUpId" 
          :disabled="item.disabled">
          <span style="float: left">{{ item.movieUpName }}</span>
          <span style="float: right; color: #8492a6; font-size: 13px">{{ item.movieUpId }}</span>
        </el-option>
        </el-select>

        <el-select
          v-if="this.queryParams.movieUpType == 0"
          v-model="queryParams.movieUpId"
          placeholder="请输入视频上传者"
          clearable
          @keyup.enter.native="handleQuery"
        >
        <el-option
          v-for="(item, index) in this.querySinger" 
          :key="index" :label="item.movieUpName"
          :value="item.movieUpId" 
          :disabled="item.disabled">
          <span style="float: left">{{ item.movieUpName }}</span>
          <span style="float: right; color: #8492a6; font-size: 13px">{{ item.movieUpId }}</span>
        </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="标签状态" prop="movieStatus">        
        <el-select 
          v-model="queryParams.movieStatus"
          placeholder="请选择状态" 
          clearable 
          @keyup.enter.native="handleQuery"
        >
        <el-option v-for="(item, index) in movieStatusOptions" 
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
          v-hasPermi="['music:movie:add']"
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
          v-hasPermi="['music:movie:edit']"
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
          v-hasPermi="['music:movie:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['music:movie:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="movieList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="视频id" align="center" prop="movieId" />
      <el-table-column label="视频名称" align="center" prop="movieName" />
      <el-table-column label="上传者类型" align="center" prop="movieUpType" :formatter="upTypeFormatter"/>
      <el-table-column label="上传者id" align="center" prop="movieUpId" />
      <el-table-column label="视频封面" align="center" prop="movieImageUrl" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.movieImageUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="视频" align = center prop = "movieUrl">
        <template slot-scope="scope">
          <el-link v-bind:href="scope.row.movieUrl" target="_blank">试看</el-link>
        </template>
      </el-table-column>
      <el-table-column label="视频状态" align="center" key="movieStatus" prop="movieStatus" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.movieStatus"
            :active-value=1
            :inactive-value=0
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['music:movie:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['music:movie:remove']"
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

    <!-- 添加或修改视频对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="视频名称" prop="movieName">
          <el-input v-model="form.movieName" placeholder="请输入视频名称" />
        </el-form-item>

        <!-- 选择器 选择用户id -->
        <el-form-item label="上传者类型" prop="movieUpType" label-width="100px">        
        <el-select 
          v-model="form.movieUpType"
          placeholder="请选择状态" 
          clearable 
          @clear="updateUpType"
          @keyup.enter.native="handleQuery"                    
        >
        <el-option v-for="(item, index) in upTypeOptions" 
          :key="index" :label="item.label"
          :value="item.value" 
          :disabled="item.disabled"/>
        </el-select>
      </el-form-item>

      <el-form-item label="视频上传者" prop="movieUpId" label-width="100px" v-if="this.form.movieUpType != null">
        <el-select
          v-if="this.form.movieUpType == 1"
          v-model="form.movieUpId"
          placeholder="请输入视频上传者"
          clearable
          @keyup.enter.native="handleQuery"
        >
        <el-option
          v-for="(item, index) in this.formUser" 
          :key="index" :label="item.nickname"
          :value="item.userId" 
          :disabled="item.disabled">
          <span style="float: left">{{ item.nickname }}</span>
          <span style="float: right; color: #8492a6; font-size: 13px">{{ item.userId }}</span>
        </el-option>
        </el-select>

        <el-select
          v-if="this.form.movieUpType == 0"
          v-model="form.movieUpId"
          placeholder="请输入视频上传者"
          clearable
          @keyup.enter.native="handleQuery"
        >
        <el-option
          v-for="(item, index) in this.formSinger" 
          :key="index" :label="item.singerName"
          :value="item.singerId" 
          :disabled="item.disabled">
          <span style="float: left">{{ item.singerName }}</span>
          <span style="float: right; color: #8492a6; font-size: 13px">{{ item.singerId }}</span>
        </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="视频封面" required>
          <image-upload v-model="form.movieImageUrl"/>
        </el-form-item>    
        <el-form-item label="视频" required="">
          <file-upload v-model="form.movieUrl" :uploadType="'video'" :fileSize=1024 :fileType="['wmv','mpg','mpeg','mov','mp4','m4v','avi','flv','mkv']"/>
        </el-form-item>
        <el-form-item label="状态" prop="movieStatus">
          <el-switch
            v-model="form.movieStatus"
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
import { listMovie, getMovie, delMovie, addMovie, updateMovie } from "@/api/music/movie";
import { listSingerIdAndSingerName } from "@/api/music/singer";
import { listUserIdAndNickname } from "@/api/music/user";
export default {
  name: "Movie",
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
      // 视频表格数据
      movieList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        movieId: null,
        movieName: null,
        movieUpType: null,
        movieUpId: null,
        movieImageUrl: null,
        movieUrl: null,
        movieStatus: null,
        editBy: null,
        editTime: null,
        revision: null,
        isDel: null
      },
      // 存在视频的歌手列表
      querySinger:[],
      // 存在视频的用户列表
      queryUser:[],
      // 状态参数
      movieStatusOptions:[{
        "label": "启用",
        "value": 1
      }, {
        "label": "停用",
        "value": 0
      }],
      // 上传者类型参数
      upTypeOptions:[{
        "label": "用户",
        "value": 1
      }, {
        "label": "歌手",
        "value": 0
      }],
      // 表单参数
      form: {},
      // 表单歌手列表
      formSinger:[],
      //表单用户列表
      formUser:[],
      // 表单校验
      rules: {
        movieName: [
          { required: true, message: "视频名称不能为空", trigger: "blur" }
        ],
        movieUpType: [
          { required: true, message: "视频上传者类型;0-歌手, 1-用户不能为空", trigger: "blur" }
        ],
        movieUpId: [
          { required: true, message: "视频上传者id不能为空", trigger: "blur" }
        ],
        movieUrl: [
          { required: true, message: "视频url不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询视频列表 */
    getList() {
      this.loading = true;
      listMovie(this.queryParams).then(response => {
        this.movieList = response.rows;
        this.movieList.forEach(item =>{          
          if (item.movieUpType == 1) {
            this.queryUser.push({"movieUpId":item.movieUpId,"movieUpName":item.userName})
          } else {
             this.querySinger.push({"movieUpId":item.movieUpId,"movieUpName":item.singerName})
          }
        })
        this.total = response.total;
        this.loading = false;
      });
      listSingerIdAndSingerName().then(response=>{
        this.formSinger = response.data;
      });
      listUserIdAndNickname().then(response=>{
        this.formUser = response.data;
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
        movieId: null,
        movieName: null,
        movieUpType: 0,
        movieUpId: null,
        movieUrl: null,
        createBy: null,
        createTime: null,
        editBy: null,
        editTime: null,
        revision: null,
        isDel: 0
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
      this.title = "添加视频";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMovie(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改视频";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMovie(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMovie(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除视频编号为"' + ids + '"的数据项？').then(function() {
        return delMovie(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('music/movie/export', {
        ...this.queryParams
      }, `movie_${new Date().getTime()}.xlsx`)
    },
    /** 上传者类型转换 */
    upTypeFormatter(row){
      let text = row.movieUpType;
      switch (text) {
        case 0: return "歌手";
        case 1: return "用户";
        default: return "未知"
      }
    },
    // 当点击清空时
    updateUpType(){
      this.queryParams.movieUpType = null;
      this.form.movieUpType = null;
    },
     /** 修改视频状态 */
    handleStatusChange(row){
      let text = row.movieStatus === 1 ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.movieName + '"吗？').then(function() {
        return updateMovie(row);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.movieStatus = row.movieStatus === 0 ? 1 : 0;
      });
    }
  }
};
</script>
