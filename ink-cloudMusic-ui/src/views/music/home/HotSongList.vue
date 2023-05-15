<template>
  <div class="hot-list">
    <div class="h_title">
      <h3>热门推荐</h3>

      <span
        :class="7 == playlist_index ? 'active' : ''"
        @click="choosePlayListType(7)"
        >为你推荐</span
      >
      <span
        v-for="(item, index) in playlist_tags"
        :key="item.id"
        :class="index == playlist_index ? 'active' : ''"
        @click="choosePlayListType(index)"
        >{{ item.name }}</span
      >
    </div>

    <div class="wrapper">
      <SongListShow
        :playList="playlist_list"
        :loading="playlist_loading"
        :num="playlist_count"></SongListShow>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import SongListShow from "@components/SongListShow.vue";
  import { getCurrentInstance, onMounted, Ref, ref } from "vue";

  const { proxy } = getCurrentInstance();

  const playlist_tags: Ref<any[]> = ref([]);
  const playlist_list: Ref<any[]> = ref([]);
  const playlist_index: Ref<number> = ref(7);
  const playlist_params: Ref<any> = ref({ limit: 6, offset: 0, cat: "" });
  const playlist_count: Ref<number> = ref(6);
  const playlist_loading: Ref<boolean> = ref(true);

  // 获取热门推荐歌单标签
  const getHotTags = async () => {
    const { data: res } = await proxy.$http.hotList();
    if (res.code !== 200) {
      return proxy.$msg.error(res.message);
    }
    playlist_tags.value = res.tags.splice(0, 6);
  };

  // 获取分类歌单列表
  const getPlayList = async (params) => {
    const { data: res } = await proxy.$http.playList(params);

    if (res.code !== 200) {
      return proxy.$msg.error(res.message);
    }
    playlist_list.value = res.playlists;
    playlist_loading.value = false;
  };

  // 切换歌单类别
  const choosePlayListType = (index) => {
    playlist_index.value = index;
    playlist_params.value.cat =
      index !== 7 ? playlist_tags.value[index].name : "";
    playlist_loading.value = true;
    getPlayList(playlist_params.value);
  };

  onMounted(() => {
    getHotTags();
    getPlayList(playlist_params.value);
  });
</script>

<style lang="less" scoped>
  .hot-list {
    padding: 0 20px;
    margin-bottom: 25px;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
  }
  .h_title {
    padding: 20px 0 10px;

    h3 {
      display: inline-block;
      padding-right: 50px;
      font-size: 28px;
      font-weight: 700;
    }

    span {
      display: inline-block;
      font-size: 16px;
      margin: 0 40px 0 0;
      color: var(--color-text-main);
      cursor: pointer;

      &.active {
        position: relative;
        z-index: 1;
        font-weight: 600;
        color: var(--color-text-main);

        &:after {
          position: absolute;
          content: "";
          left: 0;
          bottom: 1px;
          width: 100%;
          height: 6px;
          background: var(--color-text-height);
          z-index: -1;
        }
      }
    }
  }
</style>
