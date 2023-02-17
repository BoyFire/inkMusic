<template>
  <div class="carousel-container">
    <!-- 推荐栏 -->
    <el-carousel :interval="4000" type="card" height="200px">
      <el-carousel-item v-for="item in lists" :key="item">
        <el-image
          :src="item.imageUrl"
          :alt="item.typeTitle"
          @click="pathHandler(item)">
        </el-image>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script lang="ts" setup>
  import { onMounted, ref, Ref } from "vue";
  import { useRouter } from "vue-router";

  const router = useRouter();
  const lists: Ref<any[]> = ref([]);

  // 获取轮播图
  const getBanner = async () => {
    // 接口获取推荐栏信息
    const data = {
      banners: [
        {
          imageUrl:
            "http://p1.music.126.net/lSnOghxDHhLGaANnkUariw==/109951168294989786.jpg",
          targetId: 14602554,
          adid: null,
          targetType: 1004,
          titleColor: "red",
          typeTitle: "MV首发",
          url: null,
          exclusive: false,
          monitorImpress: null,
          monitorClick: null,
          monitorType: null,
          monitorImpressList: null,
          monitorClickList: null,
          monitorBlackList: null,
          extMonitor: null,
          extMonitorInfo: null,
          adSource: null,
          adLocation: null,
          adDispatchJson: null,
          encodeId: "14602554",
          program: null,
          event: null,
          video: null,
          song: null,
          scm: "1.music-homepage.homepage_banner_force.banner.6659038.861894912.null",
          bannerBizType: "force_banner",
        },
        {
          imageUrl:
            "http://p1.music.126.net/WuHpSNll0OgGPLck1gR-cg==/109951168294974363.jpg",
          targetId: 0,
          adid: null,
          targetType: 3000,
          titleColor: "blue",
          typeTitle: "独家策划",
          url: "https://y.music.163.com/m/at/63e0b9a91c6a6f09ee02ea20",
          exclusive: false,
          monitorImpress: null,
          monitorClick: null,
          monitorType: null,
          monitorImpressList: null,
          monitorClickList: null,
          monitorBlackList: null,
          extMonitor: null,
          extMonitorInfo: null,
          adSource: null,
          adLocation: null,
          adDispatchJson: null,
          encodeId: "0",
          program: null,
          event: null,
          video: null,
          song: null,
          scm: "1.music-homepage.homepage_banner_force.banner.6659039.861773986.null",
          bannerBizType: "force_banner",
        },
        {
          imageUrl:
            "http://p1.music.126.net/VXSigbMJW7f8y8x8YBpdLw==/109951168295003511.jpg",
          targetId: 1939409393,
          adid: null,
          targetType: 1,
          titleColor: "red",
          typeTitle: "热歌推荐",
          url: null,
          exclusive: false,
          monitorImpress: null,
          monitorClick: null,
          monitorType: null,
          monitorImpressList: null,
          monitorClickList: null,
          monitorBlackList: null,
          extMonitor: null,
          extMonitorInfo: null,
          adSource: null,
          adLocation: null,
          adDispatchJson: null,
          encodeId: "1939409393",
          program: null,
          event: null,
          video: null,
          song: null,
          scm: "1.music-homepage.homepage_banner_force.banner.6659040.861894979.null",
          bannerBizType: "force_banner",
        },
        {
          imageUrl:
            "http://p1.music.126.net/2ZEfCo15bGKlf-CmvvvUHQ==/109951168295001702.jpg",
          targetId: 2020525092,
          adid: null,
          targetType: 1,
          titleColor: "red",
          typeTitle: "新歌首发",
          url: null,
          exclusive: false,
          monitorImpress: null,
          monitorClick: null,
          monitorType: null,
          monitorImpressList: null,
          monitorClickList: null,
          monitorBlackList: null,
          extMonitor: null,
          extMonitorInfo: null,
          adSource: null,
          adLocation: null,
          adDispatchJson: null,
          encodeId: "2020525092",
          program: null,
          event: null,
          video: null,
          song: null,
          scm: "1.music-homepage.homepage_banner_force.banner.6659041.861887194.null",
          bannerBizType: "force_banner",
        },
        {
          imageUrl:
            "http://p1.music.126.net/kseLp5dWdfnsNxjQigS4Zg==/109951168294977105.jpg",
          targetId: 1984475097,
          adid: null,
          targetType: 1,
          titleColor: "red",
          typeTitle: "热歌推荐",
          url: null,
          exclusive: false,
          monitorImpress: null,
          monitorClick: null,
          monitorType: null,
          monitorImpressList: null,
          monitorClickList: null,
          monitorBlackList: null,
          extMonitor: null,
          extMonitorInfo: null,
          adSource: null,
          adLocation: null,
          adDispatchJson: null,
          encodeId: "1984475097",
          program: null,
          event: null,
          video: null,
          song: null,
          scm: "1.music-homepage.homepage_banner_force.banner.6659042.861922008.null",
          bannerBizType: "force_banner",
        },
        {
          imageUrl:
            "http://p1.music.126.net/S6Rr1j587q0SP_aOMelZcA==/109951168296442945.jpg",
          targetId: 0,
          adid: null,
          targetType: 3000,
          titleColor: "blue",
          typeTitle: "数字专辑",
          url: "https://music.163.com/store/newalbum/detail?id=153345099",
          exclusive: false,
          monitorImpress: null,
          monitorClick: null,
          monitorType: null,
          monitorImpressList: null,
          monitorClickList: null,
          monitorBlackList: null,
          extMonitor: null,
          extMonitorInfo: null,
          adSource: null,
          adLocation: null,
          adDispatchJson: null,
          encodeId: "0",
          program: null,
          event: null,
          video: null,
          song: null,
          scm: "1.music-homepage.homepage_banner_force.banner.6677008.861924862.null",
          bannerBizType: "force_banner",
        },
        {
          imageUrl:
            "http://p1.music.126.net/G1VBqDKEOCZ1FGBudz8ZJg==/109951168295008574.jpg",
          targetId: 2018616626,
          adid: null,
          targetType: 1,
          titleColor: "red",
          typeTitle: "新歌首发",
          url: null,
          exclusive: false,
          monitorImpress: null,
          monitorClick: null,
          monitorType: null,
          monitorImpressList: null,
          monitorClickList: null,
          monitorBlackList: null,
          extMonitor: null,
          extMonitorInfo: null,
          adSource: null,
          adLocation: null,
          adDispatchJson: null,
          encodeId: "2018616626",
          program: null,
          event: null,
          video: null,
          song: null,
          scm: "1.music-homepage.homepage_banner_force.banner.6659044.861894014.null",
          bannerBizType: "force_banner",
        },
        {
          imageUrl:
            "http://p1.music.126.net/IKXSDPDgiFg7axaUK3CuRA==/109951168295013325.jpg",
          targetId: 2020581089,
          adid: null,
          targetType: 1,
          titleColor: "red",
          typeTitle: "新歌首发",
          url: null,
          exclusive: false,
          monitorImpress: null,
          monitorClick: null,
          monitorType: null,
          monitorImpressList: null,
          monitorClickList: null,
          monitorBlackList: null,
          extMonitor: null,
          extMonitorInfo: null,
          adSource: null,
          adLocation: null,
          adDispatchJson: null,
          encodeId: "2020581089",
          program: null,
          event: null,
          video: null,
          song: null,
          scm: "1.music-homepage.homepage_banner_force.banner.6659045.861835328.null",
          bannerBizType: "force_banner",
        },
      ],
      code: 200,
    };
    lists.value = data.banners;
  };
  // 页面跳转
  const pathHandler = (params) => {
    switch (params.targetType) {
      case 1: // 单曲
        router.push({ path: "/song", query: { id: params.targetId } });
        break;
      case 10: // 专辑
        router.push({ path: "/album", query: { id: params.targetId } });
        break;
      case 1000: // 歌单
        router.push({ path: "/playlist", query: { id: params.targetId } });
        break;
      case 1004: // MV
        router.push({ path: "/mvlist/mv", query: { id: params.targetId } });
        break;
      case 3000: // 外链
        window.open(params.url, "_blank");
        break;
    }
  };

  onMounted(() => {
    getBanner();
  });
</script>

<style scoped>
  .carousel-container {
    display: flex;
    justify-content: center;
  }
  .el-carousel {
    width: 70%;
  }
</style>
