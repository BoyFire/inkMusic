<!-- 用户详情展示 -->
<template>
  <div class="detail">
    <div class="detail-container">
      <div class="detail-main">
        <div class="cover">
          <!-- 头像展示 -->
          <div class="cover-img">
            <el-image fit="cover" :src="userInfo.userImgUrl">
              <div slot="placeholder" class="image-slot">
                <i class="iconfont icon-placeholder"></i>
              </div>
            </el-image>
          </div>
          <!-- 用户信息 -->
          <div class="cover-info">
            <div class="cover-header">
              <div class="cover-title">{{ userInfo.userNickname }}</div>
            </div>

            <div class="cover-digital">
              <span>
                性别:{{
                  userInfo.userGender === 1
                    ? "男"
                    : userInfo.userGender === 2
                    ? "女"
                    : "秘密"
                }}
              </span>
              <span class="cover-playCount"
                ><i class="iconfont icon-playnum"></i> 创建歌单:
                {{ songList_count }}首
              </span>
              <span class="cover-collect"
                ><i class="iconfont icon-collect"></i> 收藏歌单:
                {{ collectSongList_count }} 首</span
              >
            </div>

            <div class="cover-desc">
              <h5>
                用户简介
                <em
                  class="desc-close"
                  v-if="isShowDesc"
                  @click="isShowDesc = false"
                  ><i class="iconfont icon-closed"></i
                ></em>
              </h5>
              <p v-html="userInfo.description" @click="showAllDesc"></p>
              <!-- 展示完整的歌单描述 -->
              <pre class="cover-desc-all" v-if="isShowDesc">
                {{ userInfo.description }}
              </pre>
            </div>
          </div>
        </div>

        <el-tabs type="border-card">
          <el-tab-pane label="主题专栏">
            <div class="wrapper">
              <SongListShowRecommend
                :playList="recommend_list"
                :loading="recommendt_loading"
                :num="1">
              </SongListShowRecommend>
            </div>
          </el-tab-pane>
          <el-tab-pane label="创建的歌单">
            <div class="wrapper">
              <el-button type="primary" plain @click="handleAdd"
                >新增</el-button
              >
              <el-button type="danger" plain @click="handleDelete"
                >删除</el-button
              >

              <SongListShowCreate
                :playList="songList"
                :loading="creatLoding"
                :num="songList_count">
              </SongListShowCreate>
              <template v-if="creatLoding">
                <Loading />
              </template>
            </div>
          </el-tab-pane>
          <el-tab-pane label="收藏的歌单">
            <div class="wrapper infinite-list">
              <SongListShowCollected
                :playList="collectSongList"
                :loading="collectLoding"
                :num="collectSongList_count">
              </SongListShowCollected>
              <template v-if="creatLoding">
                <Loading />
              </template>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>

    <el-dialog title="新增歌单" v-model="addOpen">
      <el-form
        :model="songListAdd"
        ref="songListAddRef"
        :rules="songListAddRules">
        <el-form-item label="歌单名" required prop="songListTitle">
          <el-input
            v-model="songListAdd.songListTitle"
            placeholder="请输入歌单名" />
        </el-form-item>

        <el-form-item label="歌单图像" prop="songListImgUrl">
          <el-upload
            v-loading.fullscreen.lock="imageLoading"
            element-loading-text="拼命上传中"
            element-loading-spinner="el-icon-loading"
            element-loading-backgroud="rgba(0, 0, 0, 0.1)"
            :limit="1"
            class="avatar-uploader"
            action="http://localhost:8080/music/file/upload"
            :data="{ flieType: 'images' }"
            :on-success="uploadSuccess"
            :on-progress="uploadProgress"
            :accept="accept"
            :on-error="uploadError"
            :show-file-list="false">
            <el-image
              fit="cover"
              v-if="songListAdd.songListImgUrl"
              :src="songListAdd.songListImgUrl"
              class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="submitAddForm(songListAddRef)"
        >确 定</el-button
      >
    </el-dialog>

    <el-dialog title="删除歌单" v-model="deleteOpen">
      <el-select
        v-model="deleteIds"
        placeholder="请选择要删除的歌单"
        multiple
        clearable
        :filterable="true">
        <el-option
          v-for="item in deleteSongLists"
          :key="item.songListId"
          :label="item.songListTitle"
          :value="item.songListId"></el-option>
      </el-select>
      <el-button type="primary" @click="submitdelete">确 定</el-button>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
  import SongListShowCreate from "@/components/SongListShowCreate.vue";
  import SongListShowRecommend from "@/components/SongListShowRecommend.vue";
  import Loading from "@components/Loading.vue";
  import SongListShowCollected from "@components/SongListShowCollected.vue";
  import { Plus } from "@element-plus/icons-vue";
  import { FormInstance } from "element-plus";
  import { Ref, computed, getCurrentInstance, onMounted, ref } from "vue";
  import { useRoute } from "vue-router";
  import { useStore } from "vuex";
  const isShowDesc: Ref<boolean> = ref(false);
  const store = useStore();
  const route = useRoute();
  const { proxy } = getCurrentInstance();
  const id = ref("");
  const userInfo = computed(() => store.getters.userInfo);
  const songList: Ref<any> = ref([]);
  const creatLoding = ref(true);
  const songList_count: Ref<number> = ref();
  const collectSongList: Ref<any> = ref([]);
  const collectLoding = ref(true);
  const collectSongList_count: Ref<number> = ref();
  const imageLoading = ref(false);
  const recommend_list: Ref<any[]> = ref([]);
  const recommendt_loading: Ref<boolean> = ref(true);
  const accept = ".png, .jpg, .jpeg";

  //获取每日推荐歌单
  const getRecommend = async (params) => {
    const { data: res } = await proxy.$http.playList(params);
    if (res.code !== 200) {
      return proxy.$msg.error(res.message);
    }
    recommend_list.value.push(
      res.playlists[Number.parseInt(userInfo.value.userId)]
    );
    recommendt_loading.value = false;
  };

  //获取用户创建的歌单数据
  const getUserCreatSongList = async (id: string) => {
    const { data: res } = await proxy.$http.getUserCreatSongList({
      userId: id,
    });
    if (res.code != 200) {
      return proxy.$msg.error("数据请求失败");
    }
    songList.value = [...res.data];
    songList_count.value = res.data.length;

    creatLoding.value = false;
  };

  //获取用户收藏的歌单数据
  const getUserCollectSongList = async (id: string) => {
    const { data: res } = await proxy.$http.getUserCollectApiSongList({
      userId: id,
    });
    if (res.code != 200) {
      return proxy.$msg.error("数据请求失败");
    }
    collectSongList.value = [...res.data];
    collectSongList_count.value = res.data.length;

    collectLoding.value = false;
  };

  //展示更多描述
  const showAllDesc = () => {
    if (userInfo.value.description.length > 120) {
      isShowDesc.value = !isShowDesc.value;
    }
  };

  //新增歌单
  const addOpen = ref(false);
  const songListAdd: Ref<any> = ref({});
  const songListAddRef = ref<FormInstance>();
  const songListAddRules = {
    songListTitle: [
      { required: true, message: "歌单名不能为空", trigger: "blur" },
    ],
  };
  //重置新增表单
  const resetSongListForm = () => {
    songListAdd.value = {
      songListTitle: null,
      songListImgUrl: null,
      songListCreatorId: userInfo.value.userId,
      songListStatus: 1,
      createBy: userInfo.value.userNickname,
    };
  };
  const handleAdd = () => {
    resetSongListForm();
    addOpen.value = true;
  };
  const submitAddForm = (formEl: FormInstance | undefined) => {
    if (!formEl) {
      return;
    }
    formEl.validate(async (valid) => {
      if (valid) {
        const { data: res } = await proxy.$http.createSongList(
          songListAdd.value
        );
        if (res.code != 200) {
          return proxy.$msg.error("操作失败");
        }
        proxy.$msg.success("操作成功");
        addOpen.value = false;
        getUserCreatSongList(userInfo.value.userId);
      }
    });
  };

  //删除歌单
  const deleteOpen = ref(false);
  const deleteIds = ref([]);
  const deleteSongLists: Ref<any[]> = ref([]);
  const handleDelete = () => {
    deleteSongLists.value = songList.value.concat();
    deleteSongLists.value.shift();
    deleteOpen.value = true;
  };
  const submitdelete = async () => {
    console.log(deleteIds.value);
    const { data: res } = await proxy.$http.deleteSongList(deleteIds.value);
    if (res.code !== 200) {
      return proxy.$msg.error(res.msg);
    }
    deleteSongLists.value = [];
    deleteIds.value = [];
    proxy.$msg.success(res.msg);
    deleteOpen.value = false;
    getUserCreatSongList(userInfo.value.userId);
  };

  //图片上传
  const uploadProgress = () => {
    imageLoading.value = true;
  };
  const uploadSuccess = (obj, res: any, file) => {
    songListAdd.value.songListImgUrl = obj.data.url;
    imageLoading.value = false;
  };
  const uploadError = (obj, res: any, file) => {
    imageLoading.value = false;
  };
  9;

  const _initialize = (id: string) => {
    getRecommend({
      limit: Number.parseInt(userInfo.value.userId) + 3,
      order: "hot",
      offest: "0",
    });
    getUserCreatSongList(id);
    getUserCollectSongList(id);
  };

  onMounted(() => {
    id.value = route.query.id as string;
    if (id.value === null || id.value === undefined) {
      id.value = userInfo.value.userId;
    }
    _initialize(id.value);
  });
</script>

<style lang="less" scoped>
  .detail-container {
    display: flex;
    padding: 40px 0 0 0;
  }
  .detail-main {
    flex: 1;
    padding-bottom: 45px;
    .el-tabs {
      border: 0px;
      margin-top: 25px;
      border-radius: 12px;
      box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
    }
  }

  .cover {
    display: flex;
  }
  .cover-img {
    display: flex;
    align-items: center;
    width: 250px;
    height: 250px;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
    flex-shrink: 0;

    &::after {
      position: absolute;
      top: 0;
      left: 0;
      z-index: -1;
      width: 90%;
      height: 100%;
      content: "";
      transform: scale(0.95) translateX(5%);
      background: #f0f0f0;
    }
  }

  .cover-info {
    flex: 1;
    padding: 20px;
    margin-left: 20px;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 20px 27px rgb(0 0 0 / 5%);

    .cover-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .cover-title {
      flex: 1;
      font-size: 24px;
      font-weight: bold;
      height: 34px;
      line-height: 34px;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 1;
      -webkit-box-orient: vertical;
      word-break: break-all;
    }

    .cover-collect-author {
      width: 200px;
      text-align: right;
    }

    .collect-author {
      position: relative;
      display: inline-block;
      width: 25px;
      height: 25px;
      border-radius: 100%;
      overflow: hidden;
      margin: 3px 0 0 -15px;
      border: 1px solid #fff;
      vertical-align: top;

      &:hover {
        z-index: 1;
        border: 1px solid var(--color-text-height);
      }
    }
  }
  .cover-desc {
    position: relative;

    h5 {
      padding: 30px 0 5px;
      line-height: 20px;
      font-size: 14px;
      color: #333;
    }

    .desc-close {
      position: absolute;
      top: 30px;
      right: 0;
      cursor: pointer;
    }

    p {
      line-height: 22px;
      font-size: 14px;
      color: var(--color-text);
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      word-break: break-all;
      cursor: pointer;
    }

    .cover-desc-all {
      position: absolute;
      top: 70px;
      left: 10px;
      z-index: 1;
      width: calc(~"100% - 20px");
      padding: 10px;
      margin: -10px;
      max-height: 250px;
      line-height: 22px;
      font-size: 14px;
      white-space: pre-line;
      font-family: inherit;
      color: var(--color-text);
      background: #fff;
      overflow-y: scroll;
      box-shadow: 0 2px 10px 0 rgba(52, 52, 52, 0.1);
    }
  }

  .avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
  }

  .avatar-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
  }
  .avatar-uploader .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }

  .el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    text-align: center;
  }
</style>
