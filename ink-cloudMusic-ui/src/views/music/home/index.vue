<template>
  <div class="home">
    <Banner />
    <HotRecom />
    <NewAlbum />
    <RankList />
    <NewMv />
  </div>
</template>

<script lang="ts" setup>
  import Banner from "./Banner.vue";
  import HotRecom from "./HotSongList.vue";
  import NewAlbum from "./NewAlbum.vue";
  import NewMv from "./NewMv.vue";
  import RankList from "./RankList.vue";
</script>

<style lang="less" scoped></style>
