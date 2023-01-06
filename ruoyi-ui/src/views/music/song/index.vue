<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="歌曲id" prop="songId">
        <el-input
          v-model="queryParams.songId"
          placeholder="请输入歌曲id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="歌曲名" prop="songName">
        <el-input
          v-model="queryParams.songName"
          placeholder="请输入歌曲名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
        <!-- 根据 专辑名, 根据 歌手名, 根据 歌曲状态 -->
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
      <el-form-item label="歌曲状态" prop="songStatus">        
        <el-select 
          v-model="queryParams.songStatus"
          placeholder="请选择歌曲状态" 
          clearable 
          @keyup.enter.native="handleQuery"
        >
        <el-option v-for="(item, index) in songStatusOptions" 
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
          v-hasPermi="['music:song:add']"
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
          v-hasPermi="['music:song:edit']"
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
          v-hasPermi="['music:song:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['music:song:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="songList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="歌曲id" align="center" prop="songId" />
      <el-table-column label="歌曲名" align="center" prop="songName" />
      <el-table-column label="歌手名" align="center" prop="singerName" />  
      <el-table-column label="专辑名" align="center" prop="albumName" />

      <el-table-column label="试听" align = center prop = "songUrl">
        <template slot-scope="scope">
          <el-link v-bind:href="scope.row.songUrl" target="_blank">试听</el-link>
        </template>
      </el-table-column>

      <el-table-column label="歌曲封面" align="center" prop="songImgUrl" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.songImgUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="歌曲状态" align="center" key="songStatus" prop="songStatus" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.songStatus"
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
            v-hasPermi="['music:song:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['music:song:remove']"
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

    <!-- 添加或修改歌曲对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">        
        <el-form-item label="歌曲名" required prop="songName">
          <el-input v-model="form.songName" placeholder="请输入歌曲名" />
        </el-form-item>

        <el-form-item label="歌手名" required prop="singerId">
          <el-select v-model="form.singerId" clearable filterable placeholder="请选择歌手" @change="getSimpleAlbum()">
            <el-option v-for="(item,index) in simpleSinger"
              :key="index"
              :label="item.singerName"
              :value="item.singerId">            
              <span style="float: left">{{ item.singerName }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.singerId }}</span>            
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="专辑" required prop="albumId">
          <el-select v-model="form.albumId" clearable filterable placeholder="请选择专辑">
            <el-option v-for="(item,index) in simpleAlbum"
              :key="index"
              :label="item.albumName"
              :value="item.albumId">            
              <span style="float: left">{{ item.albumName }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.albumId }}</span>            
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="音乐文件" required prop="songUrl">
          <file-upload v-model="form.songUrl" :uploadType="'music'" :fileSize=100 :fileType="['mp3','flac','MPEG','WAV']" />          
        </el-form-item>
        <el-form-item label="歌曲图像" prop="songImgUrl">
          <image-upload v-model="form.songImgUrl"/>
        </el-form-item>
        <el-form-item label="歌词" prop="songLyric">
          <file-upload v-model="form.songLyric" :uploadType="'lyric'"/>
        </el-form-item>

        <el-form-item label="歌曲状态" prop="songStatus">
          <el-switch
            v-model="form.songStatus"
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
import { listSong, getSong, delSong, addSong, updateSong } from "@/api/music/song";
import { listSingerIdAndSingerName } from "@/api/music/singer";
 import { listAlbumIdAndName } from "@/api/music/album";

export default {
  name: "Song",
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
      // 歌曲表格数据
      songList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        songId: null,
        songName: null,
        songUrl: null,
        songImgUrl: null,
        songLyric: null,
        songStatus: null,

        singerName: null,
        albumName: null
      },
      // 歌曲状态
      songStatusOptions:[{
        "label": "启用",
        "value": 1
      }, {
        "label": "停用",
        "value": 0
      }],
      //列举歌手姓名 及 id
      simpleSinger:null,
      //列举专辑名 及 id
      simpleAlbum:null,
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
    /** 查询歌曲列表 */
    getList() {
      this.loading = true;
      listSong(this.queryParams).then(response => {
        this.songList = response.rows;
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
        songId: null,
        songName: null,
        songUrl: null,
        songImgUrl: null,
        songLyric: null,
        songStatus: 0,
        createBy: null,
        createTime: null,
        editBy: null,
        editTime: null,
        revision: null,
        isDel: null,

        singerName: null,

        albumId:null,
        albumName: null
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
      });
      
      this.open = true;
      this.title = "添加歌曲";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSong(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改歌曲";
      });
      listSingerIdAndSingerName(row.singerId).then(response=>{
        this.simpleSinger = response.data;
      });
      listAlbumIdAndName(row.singerId).then(response=>{
          this.simpleAlbum = response.data
      });            
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSong(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSong(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除歌曲编号为"' + ids + '"的数据项？').then(function() {
        return delSong(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('music/song/export', {
        ...this.queryParams
      }, `song_${new Date().getTime()}.xlsx`)
    },
    /** 修改专辑状态 */
    handleStatusChange(row){
      let text = row.songStatus === 1 ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '"歌曲id为"' + row.songId + '"的歌曲吗？').then(function() {
        return updateSong(row);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.songStatus = row.songStatus === 0 ? 1 : 0;
      });
    },
    /** 当选择歌手后 */
    getSimpleAlbum(){
      this.simpleAlbum = null;
      if (this.form.singerId != null) {
        listAlbumIdAndName(this.form.singerId).then(response=>{
          this.simpleAlbum = response.data
      });
      }      
    }
  }
};
</script>
