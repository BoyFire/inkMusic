<template>
  <div class="top_list">
    <el-skeleton :loading="loading" animated :throttle="500" :count="4">
      <template #template>
        <div class="ske-item">
          <el-skeleton-item class="ske-h4" variant="text" />
          <el-skeleton-item class="ske-h5" variant="text" />
          <div class="toplist_wrapper">
            <div class="songitem" v-for="item in listSize">
              <el-skeleton-item class="ske-img" variant="image" />
              <div class="songinfo">
                <el-skeleton-item class="ske-title" variant="text" />
                <el-skeleton-item class="ske-author" variant="text" />
              </div>
            </div>
          </div>
        </div>
      </template>

      <template #default>
        <div class="wrapper">
          <div v-for="item in topList" :key="item.id" class="toplist_item">
            <div class="toplist_hd">
              <router-link
                :to="{ path: '/rank', query: { rId: item.id, type: 'Top' } }"
                class="toplist_name"
                >{{ item.name }}</router-link
              >
              <h5 class="toplist_update">
                最近更新：{{ $utils.formartDate(item.updateTime, "MM月dd日")
                }}<span>（{{ item.updateFrequency }}）</span>
              </h5>
            </div>

            <div class="toplist_wrapper">
              <div
                class="songitem"
                :class="{ active: songItem.vip || songItem.license }"
                v-for="(songItem, index) in songList[item.id]"
                :key="songItem.id">
                <div class="songimg">
                  <el-image :src="songItem.album.picUrl + '?param=120y120'">
                    <div slot="placeholder" class="image-slot">
                      <i class="iconfont icon-placeholder"></i>
                    </div>
                  </el-image>
                </div>

                <div class="songinfo">
                  <routerLink
                    :to="{ path: '/song', query: { id: songItem.id } }"
                    class="song_title"
                    >{{ songItem.name }}</routerLink
                  >
                  <div class="song_author">
                    <routerLink
                      :to="{ path: '/artist/detail', query: { id: author.id } }"
                      class="song_name"
                      v-for="(author, k) in songItem.singer"
                      :key="k"
                      >{{
                        k !== 0 ? "/ " + author.name : author.name
                      }}</routerLink
                    >
                  </div>
                </div>

                <div
                  class="songoperate"
                  v-if="!songItem.vip && !songItem.license">
                  <i
                    class="iconfont icon-add"
                    title="添加到列表"
                    @click="addSongList(songItem)"></i>
                  <i class="iconfont icon-fav" title="添加到收藏"></i>
                </div>
              </div>
            </div>
          </div>
        </div>
      </template>
    </el-skeleton>
  </div>
</template>

<script lang="ts" setup>
  import { getCurrentInstance, onMounted, Ref, ref } from "vue";
  import { RouterLink } from "vue-router";
  import { useStore } from "vuex";

  const { proxy } = getCurrentInstance();
  const store = useStore();
  const topList: Ref<any[]> = ref([]);
  const songList: Ref<any> = ref({});
  const listSize: number = 6;
  const loading: Ref<boolean> = ref(true);

  //获取榜单列表
  const getTopList = async () => {
    //获取数据 榜单信息
    const { data: res } = await proxy.$http.toplist();

    if (res.code !== 200) {
      return proxy.$msg.error(res.message);
    }

    topList.value = res.list.splice(0, 4);
    topList.value.forEach(async (item) => {
      //获取榜单中详细歌曲信息
      const { data: res } = await proxy.$http.topRankList({ id: item.id });

      songList.value[item.id] = proxy.$utils.formatSongs(
        res.playlist.tracks.splice(0, listSize),
        res.privileges.splice(0, listSize)
      );
    });
    loading.value = false;
  };

  //添加当前歌曲到播放列表
  const addSongList = (item) => {
    store.dispatch("addList", { list: [item] });
  };

  onMounted(() => {
    getTopList();
  });
</script>

<style lang="less" scoped>
  .top_list {
    margin-bottom: 25px;
  }

  .wrapper {
    display: flex;
    justify-content: space-between;
    margin: 0 -10px;
  }
  .toplist_item {
    position: relative;
    flex: 1;
    margin: 0 10px;
    padding: 20px;
    border-radius: 12px;
    box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
    background: #fff;

    .toplist_name {
      display: block;
      font-size: 22px;
      line-height: 30px;
      font-weight: 700;
      padding-bottom: 10px;
    }

    .toplist_update {
      line-height: 18px;
      font-weight: 300;
      color: var(--color-text);
    }
  }

  .toplist_wrapper {
    padding-top: 30px;

    .songitem {
      position: relative;
      display: flex;
      padding-bottom: 20px;

      &.active {
        opacity: 0.6;
      }

      .songimg {
        flex-shrink: 0;
        width: 48px;
        height: 48px;
        line-height: 48px;
        font-size: 18px;
        border-radius: 8px;
        margin-right: 20px;
        box-shadow: 0 4px 6px rgb(0 0 0 / 12%);
        overflow: hidden;
      }

      .songinfo {
        flex: 1;
      }

      .song_title {
        line-height: 24px;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 1;
        -webkit-box-orient: vertical;
        word-break: break-all;
        padding-bottom: 5px;
        font-size: 14px;
        transition: all 0.3s ease-in;
      }

      .song_author {
        display: block;
        font-size: 0;
        height: 20px;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 1;
        -webkit-box-orient: vertical;
        word-break: break-all;

        a {
          display: inline-block;
          line-height: 20px;
          font-size: 14px;
          color: var(--color-text);
        }
      }

      &:hover {
        .song_title {
          transform: translate(15px, 0);
          transition: all 0.3s ease-in;
        }

        .songoperate {
          width: 80px;
          transition: all 0.3s ease-in;
        }
      }

      &:last-child {
        padding-bottom: 0;
      }
    }

    .songoperate {
      display: flex;
      justify-content: flex-end;
      align-items: center;
      width: 0;
      line-height: 48px;
      transition: all 0.3s ease-in;
      overflow: hidden;

      i {
        margin-left: 10px;
        cursor: pointer;
      }
    }
  }

  .el-skeleton {
    width: auto;
    display: flex;
    flex-wrap: nowrap;
    justify-content: space-between;
    margin: 0 -10px;
    box-sizing: border-box;

    .ske-item {
      position: relative;
      flex: 1;
      margin: 0 10px;
      padding: 20px;
      border-radius: 12px;
      font-size: 0;
      box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
      background: #fff;
    }

    .ske-h4 {
      height: 30px;
      margin-bottom: 10px;
    }

    .ske-h5 {
      height: 18px;
    }

    .ske-img {
      flex-shrink: 0;
      width: 48px;
      height: 48px;
      line-height: 48px;
      font-size: 18px;
      border-radius: 8px;
      margin-right: 20px;
      box-shadow: 0 4px 6px rgb(0 0 0 / 12%);
      overflow: hidden;
    }

    .ske-title {
      display: block;
      width: 60%;
      height: 24px;
      margin-bottom: 5px;
    }
    .ske-author {
      width: 40%;
      height: 20px;
    }
  }
</style>
