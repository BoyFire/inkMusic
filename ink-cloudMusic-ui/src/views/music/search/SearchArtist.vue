<template>
  <div class="list-container">
    <template v-for="item in list">
      <artist-item :item="item"></artist-item>
    </template>
    <template v-if="loading">
      <Loading />
    </template>
  </div>
</template>

<script lang="ts" setup>
  import ArtistItem from "@components/ArtistItem.vue";
  import Loading from "@components/Loading.vue";

  const props = defineProps({
    list: {
      // 歌手信息
      type: Array,
      required: true,
    },
    loading: {
      type: Boolean,
      default: false,
    },
  });
</script>

<style lang="less" scoped>
  .list-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    padding-bottom: 40px;
    margin-right: -40px;

    &::after {
      content: "";
      width: 30%;
    }
  }
</style>
