<template>
  <div class="lyrics-main" :style="{ maxHeight: maxH + 'px' }">
    <div class="lyrics" ref="lyrics" v-if="lyricObj.length" :style="transform">
      <p
        :class="[isCurLyric(index)]"
        v-for="(item, index) in lyricObj"
        :key="index">
        {{ item.txt }}
      </p>
    </div>

    <div v-else class="lyric-empty">
      <p v-if="lyric">纯音乐，无歌词</p>
      <p v-else>歌词加载中......</p>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import {
    computed,
    getCurrentInstance,
    onMounted,
    Ref,
    ref,
    watch,
  } from "vue";

  const props = defineProps({
    sId: {
      type: [Number, String],
      default: 0,
    },
    currentTime: {
      type: Number,
      default: 0,
    },
    maxH: {
      type: [Number, String],
      default: 390,
    },
  });
  const { proxy } = getCurrentInstance();
  // true为纯音乐
  const lyric = ref(true);
  const lyricObj: Ref<any> = ref([]);
  const curIndex = ref(0);
  const isFull = ref(false);

  const isCurLyric = computed(() => {
    return (index) => {
      return index === curIndex.value && props.currentTime ? "active" : "";
    };
  });

  // 获取歌词
  const getLyrics = async (id) => {
    const { data: res } = await proxy.$http.lyrics({ id: id });
    if (res.code !== 200) {
      return proxy.$msg.error(res.message);
    }
    formartLyric(res.lrc);
  };
  // 格式化歌词
  const formartLyric = (lrc) => {
    const lrcReg = /^\[(\d{2}):(\d{2}.\d{2,})\]\s*(.+)$/;

    if (!lrc.lyric) {
      lyric.value = true;
      return;
    }
    const lyricLis = lrc.lyric.split("\n");

    lyric.value = lrc.lyric;
    lyricLis.forEach((item) => {
      const arr = lrcReg.exec(item);
      if (!arr) {
        return;
      }

      lyricObj.value.push({
        t: Number.parseInt(arr[1]) * 60 * 1 + Number.parseInt(arr[2]) * 1,
        txt: arr[3],
      });
    });

    // 根据时间轴重排顺序
    lyricObj.value.sort((a, b) => {
      return a.t - b.t;
    });
  };

  // 歌词实时滚动
  const transform = computed(() => {
    if (curIndex.value > 6) {
      return `transform: translateY(-${30 * (curIndex.value - 6)}px)`;
    } else {
      return "transform: translateY(0)";
    }
  });

  // 根据时间轴重排顺序
  lyricObj.value.sort((a, b) => {
    return a.t - b.t;
  });

  // 页面歌词可以展开查看更多
  const fullLyric = () => {
    isFull.value = !isFull.value;
  };

  const findCurIndex = (t) => {
    for (let i = 0; i < lyricObj.value.length; i++) {
      if (t <= lyricObj.value[i].t) {
        return i;
      }
    }
    return lyricObj.value.length;
  };

  onMounted(() => {
    getLyrics(props.sId);
  });

  watch(
    () => props.sId,
    (newVal, oldVal) => {
      lyric.value = null;
      lyricObj.value = [];
      curIndex.value = 0;
      getLyrics(newVal);
    }
  );

  watch(
    () => props.currentTime,
    (newVal, oldVal) => {
      if (!lyricObj.value.length) {
        return;
      }
      curIndex.value = findCurIndex(newVal) - 1;
    }
  );
</script>

<style scoped lang="less">
  .lyrics-main {
    height: 100%;
    overflow-y: auto;
    transition: all 1.5s ease-in-out;
  }

  .lyrics {
    font-size: 0;
    transform: translateY(0);
    transition: transform 1.5s ease-in-out;

    :deep(p) {
      margin: 0;
      line-height: 30px;
      font-size: 14px;
      font-weight: 300;

      &.active {
        color: var(--color-text-height);
      }
    }
  }
  .lyric-empty {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
    min-height: 100px;
    font-size: 14px;
    color: var(--color-text-height);

    ~ .more-lyric {
      display: none;
    }
  }
  .page-lyrics {
    color: #666;
    overflow: hidden;

    .lyric-empty {
      justify-content: left;
    }

    &.fullLyrics {
      max-height: none;
    }
  }

  .lyric-more span {
    display: inline-block;
    line-height: 30px;
    cursor: pointer;
    color: var(--color-text-height);
  }
</style>
