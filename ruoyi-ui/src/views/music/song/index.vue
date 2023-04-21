<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
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
          <el-option
            v-for="(item, index) in songStatusOptions"
            :key="index"
            :label="item.label"
            :value="item.value"
            :disabled="item.disabled"
          />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
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
          >新增</el-button
        >
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
          >修改</el-button
        >
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
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['music:song:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="songList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="歌曲id" align="center" prop="songId" />
      <el-table-column label="歌曲名" align="center" prop="songName" />
      <el-table-column label="歌手名" align="center" prop="singerName">
        <template slot-scope="scope">
          <span v-for="(item, index) in scope.row.singers">
            {{ (index != 0 ? "," : "") + item.singerName }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="专辑名" align="center" prop="albumName">
        <template slot-scope="scope">
          {{ scope.row.album }}
          <span v-for="(item, index) in scope.row.albums">
            {{ (index != 0 ? "," : "") + item.albumName }}
          </span>
        </template>
      </el-table-column>

      <el-table-column label="试听" align="center" prop="songUrl">
        <template slot-scope="scope">
          <el-link v-bind:href="scope.row.songUrl" target="_blank"
            >试听</el-link
          >
        </template>
      </el-table-column>

      <el-table-column
        label="歌曲封面"
        align="center"
        prop="songImgUrl"
        width="100"
      >
        <template slot-scope="scope">
          <image-preview :src="scope.row.songImgUrl" :width="50" :height="50" />
        </template>
      </el-table-column>

      <el-table-column label="标签" align="center" prop="songTag">
        <template slot-scope="scope">
          <el-button @click="openDrawer(scope.row.songId)" type="primary"
            >标签</el-button
          >
        </template>
      </el-table-column>

      <el-table-column
        label="歌曲状态"
        align="center"
        key="songStatus"
        prop="songStatus"
      >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.songStatus"
            :active-value="1"
            :inactive-value="0"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['music:song:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['music:song:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
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

        <el-form-item label="歌手名" required prop="singers">
          <el-select
            v-model="form.singers"
            multiple
            clearable
            filterable
            remote
            value-key="singerId"
            :remote-method="remoteMethodForSinger"
            :loading="remoteLoading"
            @change="getSimpleAlbum()"
            placeholder="请选择歌手"
          >
            <el-option
              v-for="(item, index) in simpleSinger"
              :key="index"
              :label="item.singerName"
              :value="item"
            >
              <span style="float: left">{{ item.singerName }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{
                item.singerId
              }}</span>
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="专辑" required prop="albums">
          <el-select
            v-model="form.albums"
            multiple
            clearable
            filterable
            value-key="albumId"
            :loading="remoteLoading"
            placeholder="请选择专辑"
          >
            <el-option
              v-for="(item, index) in simpleAlbum"
              :key="index"
              :label="item.albumName"
              :value="item"
            >
              <span style="float: left">{{ item.albumName }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{
                item.albumId
              }}</span>
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="音乐文件" required prop="songUrl">
          <file-upload
            v-model="form.songUrl"
            :uploadType="'music'"
            :fileSize="100"
            :fileType="['mp3', 'flac', 'MPEG', 'WAV']"
          />
        </el-form-item>
        <el-form-item label="歌曲图像" prop="songImgUrl">
          <image-upload v-model="form.songImgUrl" />
        </el-form-item>
        <el-form-item label="歌词" prop="songLyric">
          <el-input
            v-model="form.singerDetail"
            type="textarea"
            placeholder="[00:00.000] 作词 : 唐恬
            [00:00.156] 作曲 : 钱雷
            [00:00.312] 编曲 : 钱雷
            [00:00.468] 联合出品 : 腾讯游戏/拳头游戏
            [00:00.624] 出品监制 : 霍锦/卢泓宇
            [00:00.780][00:00.900] LIVE 版制作人：Clem Fung
            "
          />
        </el-form-item>

        <el-form-item label="歌曲状态" prop="songStatus">
          <el-switch
            v-model="form.songStatus"
            :active-value="1"
            :inactive-value="0"
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

    <!-- 歌曲标签查看及修改抽屉 -->
    <el-drawer
      title="歌曲标签展示及修改"
      :before-close="drawerHandleClose"
      :visible.sync="darwerDialog"
      ref="drawer"
    >
      <div class="demo-drawer__content">
        <!-- 显示当前歌曲已有标签 -->
        标签:<br />
        <el-tag
          v-for="tag in drawerSongTags"
          :key="tag.tagId"
          closable
          @close="deleteTag(tag)"
        >
          {{ tag.tagName }}
        </el-tag>

        <!-- 添加标签 -->
        <el-form :model="drawerForm" v-if="drawerFormVisible">
          <el-form-item label="标签类别" prop="tagParentsId" required>
            <el-select
              v-model="drawerForm.tagParentsId"
              filterable
              placeholder="请选择标签类别"
              @change="getChildrenTags()"
              width="100px"
            >
              <el-option
                v-for="(item, index) in songTagTypeList"
                :key="index"
                :label="item.tagName"
                :value="item.tagId"
              >
                <span style="float: left">{{ item.tagName }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{
                  item.tagId
                }}</span>
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="标签" prop="tagIds" required>
            <el-select
              v-model="drawerForm.tagIds"
              multiple
              clearable
              filterable
              placeholder="请选择标签"
            >
              <el-option
                v-for="(item, index) in songTagTypeChildrenList"
                :key="index"
                :label="item.tagName"
                :value="item.tagId"
              >
                <span style="float: left">{{ item.tagName }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{
                  item.tagId
                }}</span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <el-button
          v-else
          class="button-new-tag"
          size="small"
          @click="showDrawerForm"
          >+ New Tag</el-button
        >
        <div class="dialog-footer" v-if="drawerFormVisible">
          <el-button @click="cancelDrawerForm">取 消</el-button>
          <el-button
            type="primary"
            @click="$refs.drawer.closeDrawer()"
            :loading="loading"
            >{{ loading ? "提交中 ..." : "确 定" }}</el-button
          >
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import {listAlbumIdAndName} from "@/api/music/album";
import {listSingerIdAndSingerName, selectSimpleSingerByName,} from "@/api/music/singer";
import {addSong, delSong, getSong, listSong, updateSong,} from "@/api/music/song";
import {addSongTags, deleteSongTagByTagId, getSimpleTagsBySongId,} from "@/api/music/songTag";
import {getTagsByParentsId} from "@/api/music/tag";

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
        songStatus: null,
        singerName: null,
        albumName: null,
      },
      // 歌曲状态
      songStatusOptions: [
        {
          label: "启用",
          value: 1,
        },
        {
          label: "停用",
          value: 0,
        },
      ],
      //列举歌手姓名 及 id
      simpleSinger: [],
      //列举专辑名 及 id
      simpleAlbum: [],
      // 表单参数
      form: {
        singers: [],
        albums: [],
      },
      // 表单校验
      rules: {},
      // 歌曲标签抽屉显示
      darwerDialog: false,
      // 标签抽屉表单展示
      drawerFormVisible: false,
      // 当前选中的歌曲id
      selectSongId: null,
      // 标签抽屉表单参数
      drawerForm: {},
      // 标签抽屉列表
      drawerSongTags: [],
      // 标签抽屉加载
      drawerLoading: false,
      // 音乐标签列表
      songTagTypeList: [],
      // 音乐标签孩子列表
      songTagTypeChildrenList: [],
      // 音乐标签所属类别
      songTagType: 1,
      // 远程调用load
      remoteLoading: false,
    };
  },
  created() {
    this.getList();
    this.reset();
  },
  methods: {
    /** 查询歌曲列表 */
    getList() {
      this.loading = true;
      listSong(this.queryParams).then((response) => {
        this.songList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      getTagsByParentsId(this.songTagType).then((response) => {
        this.songTagTypeList = response.data;
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
        singers: [],
        albums: [],
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.simpleSinger = [];
      this.open = true;
      this.title = "添加歌曲";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getSong(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改歌曲";
      });
      listSingerIdAndSingerName(row.singerId).then((response) => {
        this.simpleSinger = response.data;
      });
      listAlbumIdAndName(row.singerId).then((response) => {
        this.simpleAlbum = response.data;
      });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateSong(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSong(this.form).then((response) => {
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
      this.$modal
        .confirm('是否确认删除歌曲编号为"' + ids + '"的数据项？')
        .then(function () {
          return delSong(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "music/song/export",
        {
          ...this.queryParams,
        },
        `song_${new Date().getTime()}.xlsx`
      );
    },
    /** 修改专辑状态 */
    handleStatusChange(row) {
      let text = row.songStatus === 1 ? "启用" : "停用";
      this.$modal
        .confirm('确认要"' + text + '"歌曲id为"' + row.songId + '"的歌曲吗？')
        .then(function () {
          return updateSong(row);
        })
        .then(() => {
          this.$modal.msgSuccess(text + "成功");
        })
        .catch(function () {
          row.songStatus = row.songStatus === 0 ? 1 : 0;
        });
    },
    /** 当选择歌手后 */
    getSimpleAlbum() {
      this.simpleAlbum = [];
      if (this.form.singers != []) {
        this.form.singers.forEach((singer) => {
          listAlbumIdAndName(singer.singerId).then((response) => {
            for (let index = 0; index < response.data.length; index++) {
              if (this.simpleAlbum.indexOf(response.data[index]) === -1) {
                this.simpleAlbum.push(response.data[index]);
              }
            }
          });
        });
      }
    },
    /** 当选择标签类别后 */
    getChildrenTags() {
      this.songTagTypeChildrenList = [];
      this.$set(this.drawerForm, "tagIds", "");
      var songTags = this.drawerSongTags;
      var tagNameList = [];
      // 剔除重复项
      songTags.forEach((item) => {
        tagNameList.push(item.tagName);
      });

      if (this.drawerForm.tagParentsId != null) {
        getTagsByParentsId(this.drawerForm.tagParentsId).then((response) => {
          var tagsList = response.data;
          tagsList.forEach((item) => {
            if (!tagNameList.includes(item.tagName)) {
              this.songTagTypeChildrenList.push(item);
            }
          });
        });
      }
    },
    /** 抽屉关闭提示 内包含抽屉表单提交*/
    drawerHandleClose() {
      if (this.drawerLoading) {
        return;
      }
      if (!this.drawerFormVisible) {
        this.darwerDialog = false;
        return;
      }

      this.$confirm("确定要提交表单吗？").then(() => {
        this.drawerLoading = true;
        addSongTags(this.drawerForm, this.selectSongId).then(() => {
          this.$modal.msgSuccess("新增成功");
          getSimpleTagsBySongId(this.selectSongId).then((response) => {
            this.drawerSongTags = response.data;
            this.drawerFormVisible = false;
            this.drawerLoading = false;
          });
          this.$set(this.drawerForm, "tagIds", "");
          this.$set(this.drawerForm, "tagParentsId", "");
        });
      });
    },
    /** 打开歌曲标签抽屉 */
    openDrawer(songId) {
      this.selectSongId = songId;
      this.darwerDialog = true;
      getSimpleTagsBySongId(songId).then((response) => {
        this.drawerSongTags = response.data;
      });
    },
    /** 取消提交表单 */
    cancelDrawerForm() {
      this.drawerLoading = false;
      this.drawerFormVisible = false;
    },
    // 展示抽屉添加标签表单
    showDrawerForm() {
      this.drawerFormVisible = true;
    },
    // 删除标签
    deleteTag(tag) {
      deleteSongTagByTagId(tag.tagId).then(() => {
        this.drawerSongTags.splice(this.drawerSongTags.indexOf(tag), 1);
      });
    },

    /** 远程方法 */
    remoteMethodForSinger(params) {
      this.remoteLoading = true;
      this.simpleSinger = this.form.simpleSinger;
      selectSimpleSingerByName(params).then((response) => {
        for (let index = 0; index < response.data.length; index++) {
          if (this.simpleSinger.indexOf(response.data[index]) === -1) {
            this.simpleSinger.push(response.data[index]);
          }
        }
      });
      this.remoteLoading = false;
    },
  },
};
</script>

<style>
.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
