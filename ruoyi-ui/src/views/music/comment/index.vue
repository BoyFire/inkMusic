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
      <el-form-item label="评论id" prop="commentId">
        <el-input
          v-model="queryParams.commentId"
          placeholder="请输入评论id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="评论类型" prop="commentType" label-width="100px">        
        <el-select 
          v-model="queryParams.commentType"
          placeholder="请选择评论类型" 
          clearable 
          @clear="clearCommentType"
          @keyup.enter.native="handleQuery"                    
        >
        <el-option v-for="(item, index) in commentTypeOptions" 
          :key="index" :label="item.label"
          :value="item.value" 
          :disabled="item.disabled"/>
        </el-select>
      </el-form-item>

      <el-form-item label="评论目标" prop="movieUpId" label-width="100px" v-if="this.queryParams.commentType != null">
        <el-select
          v-if="this.queryParams.commentType == 1"
          v-model="queryParams.commentTargetId"
          placeholder="请选择评论目标"
          clearable
          filterable
          @keyup.enter.native="handleQuery"
        >
        <el-option
          v-for="(item, index) in this.queryComment" 
          :key="index" :label="item.targetName"
          :value="item.targetId" 
          :disabled="item.disabled">
          <span style="float: left">{{ item.targetName }}</span>
          <span style="float: right; color: #8492a6; font-size: 13px">{{ item.targetId }}</span>
        </el-option>
        </el-select>
         <el-select
          v-if="this.queryParams.commentType == 2"
          v-model="queryParams.commentTargetId"
          placeholder="请选择评论目标"
          clearable
          filterable
          @keyup.enter.native="handleQuery"
        >
        <el-option
          v-for="(item, index) in this.queryAlbum" 
          :key="index" :label="item.targetName"
          :value="item.targetId" 
          :disabled="item.disabled">
          <span style="float: left">{{ item.targetName }}</span>
          <span style="float: right; color: #8492a6; font-size: 13px">{{ item.targetId }}</span>
        </el-option>
        </el-select>
         <el-select
          v-if="this.queryParams.commentType == 3"
          v-model="queryParams.commentTargetId"
          placeholder="请选择评论目标"
          clearable
          filterable
          @keyup.enter.native="handleQuery"
        >
        <el-option
          v-for="(item, index) in this.querySong" 
          :key="index" :label="item.targetName"
          :value="item.targetId" 
          :disabled="item.disabled">
          <span style="float: left">{{ item.targetName }}</span>
          <span style="float: right; color: #8492a6; font-size: 13px">{{ item.targetId }}</span>
        </el-option>
        </el-select>
         <el-select
          v-if="this.queryParams.commentType == 4"
          v-model="queryParams.commentTargetId"
          placeholder="请选择评论目标"
          clearable
          filterable
          @keyup.enter.native="handleQuery"
        >
        <el-option
          v-for="(item, index) in this.querySongList" 
          :key="index" :label="item.targetName"
          :value="item.targetId" 
          :disabled="item.disabled">
          <span style="float: left">{{ item.targetName }}</span>
          <span style="float: right; color: #8492a6; font-size: 13px">{{ item.targetId }}</span>
        </el-option>
        </el-select>
         <el-select
          v-if="this.queryParams.commentType == 5"
          v-model="queryParams.commentTargetId"
          placeholder="请选择评论目标"
          clearable
          filterable
          @keyup.enter.native="handleQuery"
        >
        <el-option
          v-for="(item, index) in this.queryMovie" 
          :key="index" :label="item.targetName"
          :value="item.targetId" 
          :disabled="item.disabled">
          <span style="float: left">{{ item.targetName }}</span>
          <span style="float: right; color: #8492a6; font-size: 13px">{{ item.targetId }}</span>
        </el-option>
        </el-select>

      </el-form-item>
      
      <el-form-item label="标签状态" prop="commentStatus">        
        <el-select 
          v-model="queryParams.commentStatus"
          placeholder="请选择状态" 
          clearable 
          @keyup.enter.native="handleQuery"
        >
        <el-option v-for="(item, index) in commentStatusOptions" 
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
          v-hasPermi="['music:comment:add']"
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
          v-hasPermi="['music:comment:edit']"
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
          v-hasPermi="['music:comment:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['music:comment:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="commentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="评论id" align="center" prop="commentId" />
      <el-table-column label="用户id" align="center" prop="userId" />
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="评论目标类型" align="center" prop="commentType" :formatter="commentTypeFormatter"/>
      <el-table-column label="目标id" align="center" prop="commentTargetId" />
      <el-table-column label="评论内容" align="center" prop="commentDetail" />
      <el-table-column label="评论点赞数" align="center" prop="commentLike" />
      <el-table-column label="评论状态" align="center" prop="commentStatus" >
         <template slot-scope="scope">
          <el-switch
            v-model="scope.row.commentStatus"
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
            v-hasPermi="['music:comment:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['music:comment:remove']"
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

    <!-- 添加或修改评论对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        
        <el-form-item label="评论类型" required prop="commentType" label-width="100px">        
        <el-select 
          v-model="form.commentType"
          placeholder="请选择评论类型" 
          clearable 
          @clear="clearCommentType"
          @keyup.enter.native="handleQuery"                    
        >
        <el-option v-for="(item, index) in commentTypeOptions" 
          :key="index" :label="item.label"
          :value="item.value" 
          :disabled="item.disabled"/>
        </el-select>
      </el-form-item>

      <el-form-item label="评论目标" required prop="commentTargetId" label-width="100px" >
        <el-select
          v-if="this.form.commentType == 1"
          v-model="form.commentTargetId"
          placeholder="请选择评论目标"
          clearable
          filterable
          @keyup.enter.native="handleQuery"
        >
        <el-option
          v-for="(item, index) in this.commentList" 
          :key="index" :label="item.commentId"
          :value="item.commentId" 
          :disabled="item.disabled">
          <span style="float: left">{{ item.commentId }}</span>
          <span style="float: right; color: #8492a6; font-size: 13px">{{ item.commentId }}</span>
        </el-option>
        </el-select>
         <el-select
          v-if="this.form.commentType == 2"
          v-model="form.commentTargetId"
          placeholder="请选择评论目标"
          clearable
          filterable
          @keyup.enter.native="handleQuery"
        >
        <el-option
          v-for="(item, index) in this.formAlbum" 
          :key="index" :label="item.albumName"
          :value="item.albumId" 
          :disabled="item.disabled">
          <span style="float: left">{{ item.albumName }}</span>
          <span style="float: right; color: #8492a6; font-size: 13px">{{ item.albumId }}</span>
        </el-option>
        </el-select>
         <el-select
          v-if="this.form.commentType == 3"
          v-model="form.commentTargetId"
          placeholder="请选择评论目标"
          clearable
          filterable
          @keyup.enter.native="handleQuery"
        >
        <el-option
          v-for="(item, index) in this.formSong" 
          :key="index" :label="item.songName"
          :value="item.songId" 
          :disabled="item.disabled">
          <span style="float: left">{{ item.songName }}</span>
          <span style="float: right; color: #8492a6; font-size: 13px">{{ item.songId }}</span>
        </el-option>
        </el-select>
         <el-select
          v-if="this.form.commentType == 4"
          v-model="form.commentTargetId"
          placeholder="请选择评论目标"
          clearable
          filterable
          @keyup.enter.native="handleQuery"
        >
        <el-option
          v-for="(item, index) in this.formSongList" 
          :key="index" :label="item.songListName"
          :value="item.songListId" 
          :disabled="item.disabled">
          <span style="float: left">{{ item.songListName }}</span>
          <span style="float: right; color: #8492a6; font-size: 13px">{{ item.songListId }}</span>
        </el-option>
        </el-select>
         <el-select
          v-if="this.form.commentType == 5"
          v-model="form.commentTargetId"
          placeholder="请选择评论目标"
          clearable
          filterable
          @keyup.enter.native="handleQuery"
        >
        <el-option
          v-for="(item, index) in this.formMovie" 
          :key="index" :label="item.movieName"
          :value="item.movieId" 
          :disabled="item.disabled">
          <span style="float: left">{{ item.movieName }}</span>
          <span style="float: right; color: #8492a6; font-size: 13px">{{ item.movieId }}</span>
        </el-option>
        </el-select>

      </el-form-item>

        <el-form-item label="评论内容" required prop="commentDetail">
          <el-input v-model="form.commentDetail" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="状态" prop="commentStatus">
          <el-switch
            v-model="form.commentStatus"
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
import { listComment, getComment, delComment, addComment, updateComment } from "@/api/music/comment";
import { listSimpleAlbum } from "@/api/music/album";
import { listSimpleSong } from "@/api/music/song";
import { listSimpleSongList } from "@/api/music/songList";
import { listSimpleMovie } from "@/api/music/movie";

export default {
  name: "Comment",
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
      // 评论表格数据
      commentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        commentId: null,
        commentType: null,
        commentDetail: null,
        commentLike: null,
        commentStatus: null,
        editBy: null,
        editTime: null,
        revision: null,
        isDel: null,        
        commentTargetId:null,
        userName:null,
        albumName:null,
        songListName:null,
        songName:null,
        movieName:null
      },
      // 状态参数
      commentStatusOptions:[{
        "label": "启用",
        "value": 1
      }, {
        "label": "停用",
        "value": 0
      }],
      //评论类型查询参数
      commentTypeOptions:[{
        "label": "评论",
        "value": 1
      }, {
        "label": "专辑",
        "value": 2
      }, {
        "label": "歌曲",
        "value": 3
      }, {
        "label": "歌单",
        "value": 4
      }, {
        "label": "视频",
        "value": 5
      }],
      // 存在评论的专辑列表
      queryAlbum:[],
      // 存在评论的歌单列表
      querySongList:[],
      // 存在评论的歌曲列表
      querySong:[],
      // 存在评论的视频列表
      queryMovie:[],
      // 存在评论的评论列表
      queryComment:[],
      // 表单参数
      form: {},
      // 表单专辑
      formAlbum:[],
      // 表单歌单
      formSongList:[],
      // 表单歌曲
      formSong:[],
      // 表单视频 
      formMovie:[],  
      // 表单校验      
      rules: {       
        commentType: [
          { required: true, message: "评论目标不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询评论列表 */
    getList() {
      this.loading = true;
      listComment(this.queryParams).then(response => {
        this.commentList = response.rows;
        this.commentList.forEach(item=>{
          switch (item.commentType) {
            case 1:
              this.queryComment.push({'targetId':item.targetId,'targetName':item.targetId})
              break;
            case 2:
              this.queryAlbum.push({'targetId':item.targetId,'targetName':item.albumName})
              break;
            case 3:
              this.querySong.push({'targetId':item.targetId,'targetName':item.songName})
              break;
            case 4:
              this.querySongList.push({'targetId':item.targetId,'targetName':item.songListName})
              break;
            case 5:
              this.queryMovie.push({'targetId':item.targetId,'targetName':item.movieName})
              break;
            default:
              break;
          }          
          
        });
        this.total = response.total;
        this.loading = false;
      });
      listSimpleAlbum().then(response=>{
        this.formAlbum = response.data;
      });
      listSimpleSong().then(response=>{
        this.formSong = response.data;
      });
      listSimpleSongList().then(response=>{
        this.formSongList = response.data;
      });
      listSimpleMovie().then(response=>{
        this.formMovie = response.data;
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
        commentId: null,
        commentType: null,
        commentDetail: null,
        commentLike: null,
        commentStatus: 0,
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
      this.form.commentType =1;
      this.open = true;
      this.title = "添加评论";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getComment(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改评论";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateComment(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addComment(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除评论编号为"' + ids + '"的数据项？').then(function() {
        return delComment(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('music/comment/export', {
        ...this.queryParams
      }, `comment_${new Date().getTime()}.xlsx`)
    },
    // 当点击清空时
    clearCommentType(){
      this.queryParams.commentType = null;
      this.form.commentType = null;
    },
    // 评论类型转换
    commentTypeFormatter(row){
      let text = row.commentType
      switch (text) {
        case 1:
          return '评论';          
        case 2:
          return '专辑';          
        case 3:
          return '歌曲';
        case 4:
          return '歌单';
        default:
          return '视频';
      }
    },
     /** 修改视频状态 */
    handleStatusChange(row){
      let text = row.commentStatus === 1 ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '"编号为"' + row.commentId + '"的评论吗？').then(function() {
        return updateComment(row);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.commentStatus = row.commentStatus === 0 ? 1 : 0;
      });
    }
  }
};
</script>
