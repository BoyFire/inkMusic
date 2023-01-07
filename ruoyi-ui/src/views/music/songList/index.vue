<template>
  <div class="app-container">

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="歌单id" prop="songListId">
        <el-select 
          v-model="queryParams.songListId"
          placeholder="请输入歌单id" 
          clearable 
          filterable
          @keyup.enter.native="handleQuery"
        >
        <el-option v-for="(item, index) in this.songListList" 
          :key="index" :label="item.songListId"
          :value="item.songListId" 
          :disabled="item.disabled"/>
        </el-select>
      </el-form-item>

      <el-form-item label="歌单名" prop="songListTitle">
        <el-select 
          v-model="queryParams.songListTitle"
          placeholder="请输入歌单名" 
          clearable 
          filterable
          @keyup.enter.native="handleQuery"
        >
        <el-option v-for="(item, index) in this.songListList" 
          :key="index" :label="item.songListTitle"
          :value="item.songListTitle" 
          :disabled="item.disabled"/>
        </el-select>
      </el-form-item>
     
      <el-form-item label="歌单创建者" prop="songListCreatorId" label-width="100px">
        <el-select 
          v-model="queryParams.songListCreatorId" 
          clearable 
          filterable 
          placeholder="请输入歌单创建者"
          @keyup.enter.native="handleQuery"
        >
        <el-option v-for="(item,index) in userIdAndNicknames"
          :key="index"
          :label="item.nickname"
          :value="item.userId">
        
          <span style="float: left">{{ item.nickname }}</span>
          <span style="float: right; color: #8492a6; font-size: 13px">{{ item.userId }}</span>            
        </el-option>
        </el-select>

      </el-form-item>

      <el-form-item label="歌单状态" prop="songListStatus">        
        <el-select 
          v-model="queryParams.songListStatus"
          placeholder="请选择歌曲状态" 
          clearable 
          @keyup.enter.native="handleQuery"
        >
        <el-option v-for="(item, index) in songListStatusOptions" 
          :key="index" :label="item.label"
          :value="item.value" 
          :disabled="item.disabled"/>
        </el-select>
      </el-form-item> 

      <el-form-item label="精品歌单" prop="isFine">        
        <el-select 
          v-model="queryParams.isFineStatus"
          placeholder="请选择是否为精品" 
          clearable 
          @keyup.enter.native="handleQuery"
        >
        <el-option v-for="(item, index) in isFineOptions" 
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
          v-hasPermi="['music:songList:add']"
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
          v-hasPermi="['music:songList:edit']"
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
          v-hasPermi="['music:songList:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['music:songList:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="songListList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="歌单id" align="center" prop="songListId" />
      <el-table-column label="歌单名" align="center" prop="songListTitle" >
        <template slot-scope="scope">
          <el-link @click="querySongListSongBySongListSongId(scope.row.songListId)">{{scope.row.songListTitle}}</el-link>
        </template>
      </el-table-column>
      <el-table-column label="歌单封面" align="center" prop="songListImgUrl" >
          <template slot-scope="scope">
            <image-preview :src="scope.row.songListImgUrl" :width="50" :height="50"/>
          </template>
      </el-table-column>
      <el-table-column label="歌单创建者" align="center" prop="songListCreatorName" />
      <el-table-column label="歌单状态" align="center" prop="songListStatus" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.songListStatus"
            :active-value=1
            :inactive-value=0
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>

      <el-table-column label="精品歌单" align="center" prop="isFine" >
        <template slot-scope="scope">
          <i class="el-icon-success" v-if="scope.row.isFine==1"/>
          <i class="el-icon-error" v-if="scope.row.isFine==0"/>
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
            v-hasPermi="['music:songList:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['music:songList:remove']"
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

    <!-- 添加或修改歌单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="歌单名" required prop="songListTitle">
          <el-input v-model="form.songListTitle" placeholder="请输入歌单名" />
        </el-form-item>
        <el-form-item label="歌单图像" prop="songListImgUrl">
          <image-upload v-model="form.songListImgUrl"/>
        </el-form-item>

        <el-form-item label="歌单创建者" prop="songListCreatorId" label-width="100px">          
          <el-select 
          v-model="form.songListCreatorId"          
          clearable 
          filterable 
          placeholder="请输入歌单创建者"
        >
          <el-option v-for="(item,index) in userIdAndNicknames"
            :key="index"
            :label="item.nickname"
            :value="item.userId">        
            <span style="float: left">{{ item.nickname }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">{{ item.userId }}</span>            
          </el-option>
        </el-select>
        </el-form-item>
        
        <el-form-item label="歌单状态" prop="songListStatus">
          <el-switch
            v-model="form.songListStatus"
            :active-value=1
            :inactive-value=0
            active-text="启用"
            inactive-text="停用"
          ></el-switch>
        </el-form-item>

        <el-form-item label="精品歌单" prop="isFine">
          <el-switch
            v-model="form.isFine"
            :active-value=1
            :inactive-value=0
            active-text="是"
            inactive-text="否"
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
import { listSongList, getSongList, delSongList, addSongList, updateSongList } from "@/api/music/songList";
import { listUserIdAndNickname } from "@/api/music/user";

export default {
  name: "SongList",
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
      // 歌单表格数据
      songListList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        songListId: null,
        songListTitle: null,
        songListImgUrl: null,
        songListCreatorId: null,
        songListStatus: null,
        editBy: null,
        editTime: null,
        revision: null,
        isDel: null,
        isFine:null,
        songListCreatorName:null
      },
      // 用户id及姓名
      userIdAndNicknames:[],
      // 歌单状态
      songListStatusOptions:[{
        "label": "启用",
        "value": 1
      }, {
        "label": "停用",
        "value": 0
      }],
      // 精品歌单
      isFineOptions:[{
        "label": "是",
        "value": 1
      }, {
        "label": "否",
        "value": 0
      }],
 
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        songListTitle: [
          { required: true, message: "歌单名不能为空", trigger: "blur" }
        ],
        songListCreatorId: [
          { required: true, message: "歌单创建者不能为空", trigger: "blur" }
        ],
        
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询歌单列表 */
    getList() {
      this.loading = true;
      listSongList(this.queryParams).then(response => {
        this.songListList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      listUserIdAndNickname().then(respose=>{  
        this.userIdAndNicknames = respose.data    
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
        songListId: null,
        songListTitle: null,
        songListImgUrl: null,
        songListCreatorId: null,
        songListStatus: 0,
        createBy: null,
        createTime: null,
        editBy: null,
        editTime: null,
        revision: null,
        isDel: null,
        isFine:null
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
      this.title = "添加歌单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSongList(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改歌单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSongList(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSongList(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除歌单编号为"' + ids + '"的数据项？').then(function() {
        return delSongList(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('music/songList/export', {
        ...this.queryParams
      }, `songList_${new Date().getTime()}.xlsx`)
    },
    /** 修改歌单状态 */
    handleStatusChange(row){
      let text = row.songListStatus === 1 ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '"歌单id为"' + row.songListId + '"的歌单吗？').then(function() {
        return updateSongList(row);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.songListStatus = row.songListStatus === 0 ? 1 : 0;
      });
    },
    /** 测试页面跳转 */
    querySongListSongBySongListSongId(songListId){
      this.$router.push({ path: "/mms/songListSong", query: {     
        songListId: songListId } })
    },
  }
};
</script>
