<template>
  <div class="search">
    <el-select
      v-model="keyVal"
      class="keyVal"
      clearable
      filterable
      remote
      placeholder="请输入歌名、歌词、歌手或专辑"
      :remote-method="remoteMethod"
      :fit-input-width="true"
      :loading="loading"
      loading-text="搜索中..."
      @focus="handleFocus"
      @keyup.enter.native="handleFocus">
      <template #prefix>
        <el-icon><Search /></el-icon>
      </template>
      <!-- 历史搜索 -->
      <div
        class="history-search"
        v-if="!keyVal && historySearchList.length > 0">
        <h5>
          历史搜索
          <el-button @click="deleteAllhistory">
            <el-icon><Delete /></el-icon>
          </el-button>
        </h5>
        <el-tag
          v-for="tag in historySearchList"
          :key="tag"
          closable
          @click.native="selectTag(tag)"
          @close="deleteHistory(tag)">
          {{ tag }}
        </el-tag>
      </div>

      <div class="hot-search" v-if="!keyVal">
        <h5>热门搜索</h5>
        <el-option
          v-for="(item, index) in searchHot"
          :key="index"
          :label="item.first"
          :value="item.first"
          @click="jumpSearch(item)">
          <span :class="[index < 3 ? 'top-' + index : '']">{{
            index + 1 + "."
          }}</span>
          {{ item.first }}
        </el-option>
      </div>

      <el-option-group
        v-else
        v-for="list in suggestInfo"
        :key="listType[list.label]"
        :label="listType[list.label]"
        class="aaa">
        <el-option
          v-for="(item, index) in list.info"
          :key="list.label + index"
          :label="item.name"
          :value="list.label + item.name"
          class="item"
          @click="jumpPage(item, list.label)">
          {{ item.name }}
          <template v-if="list.label === 'songs'">
            -
            <span class="artists" v-for="(a, i) in item.artists" :key="i">{{
              (i !== 0 ? " / " : "") + a.name
            }}</span>
          </template>
          <template v-else-if="list.label === 'albums'">
            - <span class="artists">{{ item.artist.name }}</span>
          </template>
        </el-option>
      </el-option-group>
    </el-select>
  </div>
</template>
<script lang="ts" setup>
  import { getCurrentInstance, onMounted, Ref, ref } from "vue";
  import { useRoute, useRouter } from "vue-router";
  import { useStore } from "vuex";

  const { proxy } = getCurrentInstance();
  const route = useRoute();
  const router = useRouter();
  const store = useStore();

  const historySearchListSize = 10;

  // 搜索框关键字
  const keyVal = ref("");
  const oldKeyVal = ref("");
  const loading: Ref<Boolean> = ref(false);
  const suggestInfo: Ref<any> = ref([]);
  const searchHot: Ref<any> = ref([]);
  const historySearchList: Ref<string[]> = ref([]);

  // TODO 后期根据后端返回值进行修改
  const listType = {
    songs: "单曲",
    singer: "歌手",
    albums: "专辑",
    songlists: "歌单",
  };

  // 远程搜索方法
  const remoteMethod = (query) => {
    keyVal.value = query;
    if (keyVal.value) {
      loading.value = true;
      suggestInfo.value = [];
      getSearchSuggest();
    }
  };

  //搜索框，获取焦点时，请求搜索列表接口
  const handleFocus = () => {
    if (keyVal.value) {
      loading.value = true;
      suggestInfo.value = [];
      getSearchSuggest();
    }
  };

  // 获取搜索结果
  const getSearchSuggest = async () => {
    // 将搜索值存入浏览器记录中
    if (historySearchList.value.indexOf(keyVal.value) === -1) {
      historySearchList.value.unshift(keyVal.value);
    }
    if (historySearchList.value.length > historySearchListSize) {
      historySearchList.value.splice(historySearchListSize, 1);
    }
    window.localStorage.setItem(
      "historySearchList",
      JSON.stringify(historySearchList.value)
    );

    // 访问后台 获取搜索结果
    const { data: res } = await proxy.$http.serachSuggest({
      keywords: keyVal.value,
    });

    loading.value = false;

    if (res.code !== 200) {
      return console.log("数据请求失败");
    }
    if (res.result.order) {
      suggestInfo.value = res.result.order.map((item) => {
        return {
          label: item,
          info: res.result[item],
        };
      });
    }
  };

  // 热门搜索
  const getSearchHot = async () => {
    const { data: res } = await proxy.$http.serachHot();

    if (res.code !== 200) {
      return console.log("数据请求失败");
    }
    searchHot.value = res.result.hots;
  };

  // 默认热门搜索列表，点击后台跳转到搜索结果页面
  const jumpSearch = (item) => {
    keyVal.value = item.first;
    if (item.first === route.query.key) {
      return;
    }
    router.push({ path: "/search", query: { key: item.first } });
  };

  // 搜索建议列表，点击后跳转到相对应的落地页
  const jumpPage = (item, type) => {
    switch (type) {
      case "songs":
        router.push({ path: "/song", query: { id: item.id } });
        break;
      case "artists":
        router.push({ path: "/artist/detail", query: { id: item.id } });
        break;
      case "albums":
        router.push({ path: "/album", query: { id: item.id } });
        break;
      case "playlists":
        router.push({ path: "/playlist/detail", query: { id: item.id } });
        break;
    }
  };

  // 删除历史记录
  const deleteHistory = (tag: string) => {
    historySearchList.value.splice(historySearchList.value.indexOf(tag), 1);
    window.localStorage.setItem(
      "historySearchList",
      JSON.stringify(historySearchList.value)
    );
  };
  // 删除历史所有记录
  const deleteAllhistory = () => {
    historySearchList.value = [];
    window.localStorage.setItem(
      "historySearchList",
      JSON.stringify(historySearchList.value)
    );
  };

  //获取历史搜索记录
  const getHistorySearchList = () => {
    const oldVal = window.localStorage.getItem("historySearchList");
    if (oldVal != null) {
      historySearchList.value = JSON.parse(oldVal);
    } else {
      historySearchList.value = [];
    }
  };
  // 点击历史搜索
  const selectTag = (tag: string) => {
    keyVal.value = tag;
    if (tag === route.query.key) {
      return;
    }
    router.push({ path: "/search", query: { key: tag } });
  };

  onMounted(() => {
    getSearchHot();
    getHistorySearchList();
  });
</script>

<style>
  .el-select-dropdown__wrap {
    max-height: 600px;
  }
  .el-tag {
    margin-left: 10px;
  }
</style>
<style lang="less" scoped>
  .search {
    position: relative;
    display: flex;
    text-align: right;
    align-items: center;
    background: #fff;
    width: 600px;
    min-width: 300px;

    .keyVal {
      width: 100%;
      border: 0;

      :deep(input) {
        height: 40px;
        line-height: 40px;
        padding: 0 10px;
        border: none;
      }
    }
  }
  .history-search {
    h5 {
      padding: 5px 0 5px 20px;
      font-size: 18px;
    }
    .el-button {
      height: 30px;
    }
  }
  .hot-search {
    h5 {
      padding: 5px 0 5px 20px;
      font-size: 18px;
    }

    .top-0 {
      font-weight: bold;
      color: rgba(255, 0, 0, 1);
    }

    .top-1 {
      font-weight: bold;
      color: rgba(255, 0, 0, 0.6);
    }

    .top-2 {
      font-weight: bold;
      color: rgba(255, 0, 0, 0.4);
    }
  }

  .item {
    padding-right: 20px;

    .artists {
      font-size: 12px;
      color: var(--color-text);
    }

    &.selected {
      color: var(--color-text-height);

      .artists {
        color: var(--color-text-height);
      }
    }
  }
</style>
