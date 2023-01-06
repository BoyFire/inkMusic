<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="专辑名" prop="albumName">
        <el-input
          v-model="queryParams.albumName"
          placeholder="请输入专辑名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="歌手名" prop="singerName">
        <el-input
          v-model="queryParams.singerName"
          placeholder="请输入歌手名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="专辑状态" prop="albumStatus">        
        <el-select 
          v-model="queryParams.albumStatus"
          placeholder="请选择专辑状态" 
          clearable 
          @keyup.enter.native="handleQuery"
        >
        <el-option v-for="(item, index) in albumStatusOptions" 
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
          v-hasPermi="['music:album:add']"
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
          v-hasPermi="['music:album:edit']"
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
          v-hasPermi="['music:album:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['music:album:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="albumList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="专辑id" align="center" prop="albumId" />
      <el-table-column label="专辑名" align="center" prop="albumName" />
      <el-table-column label="歌手名" align="center" prop="singerName" />    
      <el-table-column label="专辑封面" align="center" prop="albumImgUrl" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.albumImgUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="专辑状态" align="center" key="albumStatus" prop="albumStatus" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.albumStatus"
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
            v-hasPermi="['music:album:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['music:album:remove']"
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

    <!-- 添加或修改专辑对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="专辑名" required prop="albumName">
          <el-input v-model="form.albumName" placeholder="请输入专辑名" />
        </el-form-item>

        <el-form-item label="歌手名" required prop="singerId">
          <el-select v-model="form.singerId" clearable filterable placeholder="请选择歌手">
            <el-option v-for="(item,index) in simpleSinger"
              :key="index"
              :label="item.singerName"
              :value="item.singerId">
            
              <span style="float: left">{{ item.singerName }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.singerId }}</span>            
            </el-option>

          </el-select>
        </el-form-item>
        <el-form-item label="专辑封面">
          <image-upload v-model="form.albumImgUrl"/>
        </el-form-item>        
        <el-form-item label="认证状态" prop="albumStatus">
          <el-switch
            v-model="form.albumStatus"
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
import { listAlbum, getAlbum, delAlbum, addAlbum, updateAlbum } from "@/api/music/album";
import { listSingerIdAndSingerName } from "@/api/music/singer";

export default {
  name: "Album",
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
      // 专辑表格数据
      albumList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        albumId: null,
        albumName: null,
        singerId: null,
        singerName: null,
        albumImgUrl: null,
        albumStatus: null,
      },
      //专辑状态
      albumStatusOptions:[{
        "label": "启用",
        "value": 1
      }, {
        "label": "停用",
        "value": 0
      }],
      // 歌手姓名及id 列表 
      simpleSinger: null,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询专辑列表 */
    getList() {
      this.loading = true;
      listAlbum(this.queryParams).then(response => {
        this.albumList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      listSingerIdAndSingerName().then(response=>{
        this.simpleSinger = response.data
      })
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
        albumId: null,
        albumName: null,
        singerId: null,
        albumImgUrl: null,
        albumStatus: 0,
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
      listSingerIdAndSingerName().then(response=>{
        this.simpleSinger = response.data
      })
      this.open = true;
      this.title = "添加专辑";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAlbum(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改专辑";
      })
      listSingerIdAndSingerName().then(response=>{
        this.simpleSinger = response.data;
      })
      ;
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAlbum(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAlbum(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除专辑编号为"' + row.albumId + '"的数据项？').then(function() {
        return delAlbum(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('music/album/export', {
        ...this.queryParams
      }, `album_${new Date().getTime()}.xlsx`)
    },
    /** 修改专辑状态 */
    handleStatusChange(row){
      let text = row.albumStatus === 1 ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '"专辑id为"' + row.albumId + '"的专辑吗？').then(function() {
        return updateAlbum(row);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.albumStatus = row.albumStatus === 0 ? 1 : 0;
      });
    }
  }
};
</script>
