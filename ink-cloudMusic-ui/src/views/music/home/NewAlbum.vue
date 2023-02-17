<template>
  <div class="album_list">
    <div class="h_title">
      <h3>新碟上架</h3>
      <span
        v-for="(item, index) in album_area"
        :key="index"
        :class="index == album_index ? 'active' : ''"
        @click="chooseAlbumType(index)"
        >{{ item.name }}</span
      >
    </div>
    <div class="wrapper">
      <AlbumList
        :albumList="album_list"
        :loading="album_loading"
        :num="album_count"></AlbumList>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import AlbumList from "@components/AlbumList.vue";
  import { onMounted, Ref, ref } from "vue";

  const album_area = [
    { name: "全部", code: "all" },
    { name: "华语", code: "zh" },
    { name: "欧美", code: "ea" },
    { name: "韩国", code: "kr" },
    { name: "日本", code: "jp" },
  ];
  const album_list: Ref<any[]> = ref([]);
  const album_index: Ref<number> = ref(0);
  const album_params: Ref<any> = ref({ limit: 12, area: "" });
  const album_count: number = 12;
  const album_loading: Ref<boolean> = ref(true);

  // 获取新碟
  const getAlbumList = async (params) => {
    //发送请求 获取数据
    const data = {
      monthData: [
        {
          songs: [],
          paid: false,
          onSale: false,
          status: 1,
          tags: "",
          copyrightId: 7002,
          artist: {
            img1v1Id: 18686200114669624,
            topicPerson: 0,
            picId: 109951165381201230,
            alias: [],
            trans: "林肯公园",
            albumSize: 134,
            img1v1Url:
              "http://p4.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
            picUrl:
              "http://p3.music.126.net/3BXE-18jym_4S12UIjVSXg==/109951165381201235.jpg",
            followed: false,
            briefDesc: "",
            musicSize: 1315,
            name: "Linkin Park",
            id: 95439,
            picId_str: "109951165381201235",
            transNames: ["林肯公园"],
            img1v1Id_str: "18686200114669622",
          },
          picId: 109951168292703200,
          alias: [],
          artists: [
            {
              img1v1Id: 18686200114669624,
              topicPerson: 0,
              picId: 0,
              alias: [],
              trans: "",
              albumSize: 0,
              img1v1Url:
                "http://p4.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
              picUrl:
                "http://p4.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
              followed: false,
              briefDesc: "",
              musicSize: 0,
              name: "Linkin Park",
              id: 95439,
              img1v1Id_str: "18686200114669622",
            },
          ],
          publishTime: 1675958400000,
          picUrl:
            "http://p4.music.126.net/44m8yQlT8GcIVGdWBsc4-A==/109951168292703205.jpg",
          commentThreadId: "R_AL_3_159633266",
          briefDesc: "",
          blurPicUrl:
            "http://p3.music.126.net/44m8yQlT8GcIVGdWBsc4-A==/109951168292703205.jpg",
          companyId: 0,
          pic: 109951168292703200,
          subType: "录音室版",
          description: "",
          company: "华纳音乐",
          name: "Lost",
          id: 159633266,
          type: "Single",
          size: 1,
          picId_str: "109951168292703205",
          areaId: 96,
          exclusive: false,
          isSub: false,
        },
        {
          songs: [],
          paid: false,
          onSale: false,
          status: 1,
          tags: "",
          copyrightId: 7002,
          artist: {
            img1v1Id: 18686200114669624,
            topicPerson: 0,
            picId: 109951168119341150,
            alias: [],
            trans: "莉佐",
            albumSize: 50,
            img1v1Url:
              "http://p4.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
            picUrl:
              "http://p4.music.126.net/a7BROYXY5N9O4K_XlD7iaQ==/109951168119341159.jpg",
            followed: false,
            briefDesc: "",
            musicSize: 386,
            name: "Lizzo",
            id: 840165,
            picId_str: "109951168119341159",
            transNames: ["莉佐"],
            img1v1Id_str: "18686200114669622",
          },
          picId: 109951168291643650,
          alias: [],
          artists: [
            {
              img1v1Id: 18686200114669624,
              topicPerson: 0,
              picId: 0,
              alias: [],
              trans: "",
              albumSize: 0,
              img1v1Url:
                "http://p3.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
              picUrl:
                "http://p3.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
              followed: false,
              briefDesc: "",
              musicSize: 0,
              name: "Lizzo",
              id: 840165,
              img1v1Id_str: "18686200114669622",
            },
            {
              img1v1Id: 18686200114669624,
              topicPerson: 0,
              picId: 0,
              alias: [],
              trans: "",
              albumSize: 0,
              img1v1Url:
                "http://p3.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
              picUrl:
                "http://p4.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
              followed: false,
              briefDesc: "",
              musicSize: 0,
              name: "SZA",
              id: 905232,
              img1v1Id_str: "18686200114669622",
            },
          ],
          publishTime: 1675872000000,
          picUrl:
            "http://p4.music.126.net/_74RdlKgZ7nmM34C-hbUag==/109951168291643651.jpg",
          commentThreadId: "R_AL_3_159591840",
          briefDesc: "",
          blurPicUrl:
            "http://p4.music.126.net/_74RdlKgZ7nmM34C-hbUag==/109951168291643651.jpg",
          companyId: 0,
          pic: 109951168291643650,
          subType: "录音室版",
          description: "",
          company: "华纳音乐",
          name: "Special (feat. SZA)",
          id: 159591840,
          type: "Single",
          size: 1,
          picId_str: "109951168291643651",
          areaId: 96,
          exclusive: false,
          isSub: false,
        },
        {
          songs: [],
          paid: false,
          onSale: false,
          status: 1,
          tags: "",
          copyrightId: 7002,
          artist: {
            img1v1Id: 18686200114669624,
            topicPerson: 0,
            picId: 109951168294939890,
            alias: ["Jam Hsiao"],
            trans: "",
            albumSize: 67,
            img1v1Url:
              "http://p4.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
            picUrl:
              "http://p4.music.126.net/zjrfLdvTFSwM-1fhNjkxzw==/109951168294939882.jpg",
            followed: false,
            briefDesc: "",
            musicSize: 731,
            name: "萧敬腾",
            id: 5768,
            picId_str: "109951168294939882",
            img1v1Id_str: "18686200114669622",
          },
          picId: 109951168292974180,
          alias: [],
          artists: [
            {
              img1v1Id: 18686200114669624,
              topicPerson: 0,
              picId: 0,
              alias: [],
              trans: "",
              albumSize: 0,
              img1v1Url:
                "http://p4.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
              picUrl:
                "http://p4.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
              followed: false,
              briefDesc: "",
              musicSize: 0,
              name: "萧敬腾",
              id: 5768,
              img1v1Id_str: "18686200114669622",
            },
          ],
          publishTime: 1675958400000,
          picUrl:
            "http://p3.music.126.net/B-7lquI7cyl8h-phCN5H9Q==/109951168292974172.jpg",
          commentThreadId: "R_AL_3_159640841",
          briefDesc: "",
          blurPicUrl:
            "http://p3.music.126.net/B-7lquI7cyl8h-phCN5H9Q==/109951168292974172.jpg",
          companyId: 0,
          pic: 109951168292974180,
          subType: "录音室版",
          description: "",
          company: "华纳音乐",
          name: "全世界在等你",
          id: 159640841,
          type: "Single",
          size: 1,
          picId_str: "109951168292974172",
          areaId: 7,
          exclusive: false,
          isSub: false,
        },
        {
          songs: [],
          paid: false,
          onSale: false,
          status: 1,
          tags: "",
          copyrightId: 1416336,
          artist: {
            img1v1Id: 18686200114669624,
            topicPerson: 0,
            picId: 109951165358345440,
            alias: ["MIREI"],
            trans: "当山真玲;当山美玲",
            albumSize: 52,
            img1v1Url:
              "http://p4.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
            picUrl:
              "http://p4.music.126.net/g0ZrdGpntdedKCSBcZvfVg==/109951165358345439.jpg",
            followed: false,
            briefDesc: "",
            musicSize: 134,
            name: "當山みれい",
            id: 954456,
            picId_str: "109951165358345439",
            transNames: ["当山真玲", "当山美玲"],
            img1v1Id_str: "18686200114669622",
          },
          picId: 109951168253803060,
          alias: [],
          artists: [
            {
              img1v1Id: 18686200114669624,
              topicPerson: 0,
              picId: 0,
              alias: [],
              trans: "",
              albumSize: 0,
              img1v1Url:
                "http://p4.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
              picUrl:
                "http://p3.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
              followed: false,
              briefDesc: "",
              musicSize: 0,
              name: "當山みれい",
              id: 954456,
              img1v1Id_str: "18686200114669622",
            },
          ],
          publishTime: 1675958400000,
          picUrl:
            "http://p4.music.126.net/bIRnXew9DbFMkVGEvgFi5g==/109951168253803057.jpg",
          commentThreadId: "R_AL_3_158701920",
          briefDesc: "",
          blurPicUrl:
            "http://p4.music.126.net/bIRnXew9DbFMkVGEvgFi5g==/109951168253803057.jpg",
          companyId: 0,
          pic: 109951168253803060,
          subType: "录音室版",
          description: "",
          company: "Cool Japan Music, Inc.",
          name: "Lolita Bonita",
          id: 158701920,
          type: "Single",
          size: 3,
          picId_str: "109951168253803057",
          areaId: 8,
          exclusive: false,
          isSub: false,
        },
        {
          songs: [],
          paid: false,
          onSale: false,
          status: 1,
          tags: "",
          copyrightId: 7002,
          artist: {
            img1v1Id: 18686200114669624,
            topicPerson: 0,
            picId: 109951164051352930,
            alias: [],
            trans: "Isaac Hong",
            albumSize: 18,
            img1v1Url:
              "http://p4.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
            picUrl:
              "http://p4.music.126.net/_m83nvrllroNEmZrYlnsxw==/109951164051352927.jpg",
            followed: false,
            briefDesc: "",
            musicSize: 51,
            name: "홍이삭",
            id: 12176024,
            picId_str: "109951164051352927",
            transNames: ["Isaac Hong"],
            img1v1Id_str: "18686200114669622",
          },
          picId: 109951168294316720,
          alias: [],
          artists: [
            {
              img1v1Id: 18686200114669624,
              topicPerson: 0,
              picId: 0,
              alias: [],
              trans: "",
              albumSize: 0,
              img1v1Url:
                "http://p3.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
              picUrl:
                "http://p3.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
              followed: false,
              briefDesc: "",
              musicSize: 0,
              name: "홍이삭",
              id: 12176024,
              img1v1Id_str: "18686200114669622",
            },
          ],
          publishTime: 1676044800000,
          picUrl:
            "http://p4.music.126.net/9ie-ZJFvIKV0NEdmr7QpIA==/109951168294316715.jpg",
          commentThreadId: "R_AL_3_159692811",
          briefDesc: "",
          blurPicUrl:
            "http://p4.music.126.net/9ie-ZJFvIKV0NEdmr7QpIA==/109951168294316715.jpg",
          companyId: 0,
          pic: 109951168294316720,
          subType: "录音室版",
          description: "",
          company: "华纳音乐",
          name: "꼭두의 계절 OST Part.2",
          id: 159692811,
          type: "Single",
          size: 2,
          picId_str: "109951168294316715",
          areaId: 16,
          transNames: ["顶斗的季节 OST Part.2"],
          exclusive: false,
          isSub: false,
        },
        {
          songs: [],
          paid: false,
          onSale: false,
          status: 1,
          tags: "",
          copyrightId: -1,
          artist: {
            img1v1Id: 18686200114669624,
            topicPerson: 0,
            picId: 0,
            alias: [],
            trans: "",
            albumSize: 26,
            img1v1Url:
              "http://p3.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
            picUrl:
              "http://p4.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
            followed: false,
            briefDesc: "",
            musicSize: 26,
            name: "茶鸣拾贰律",
            id: 54911645,
            img1v1Id_str: "18686200114669622",
          },
          picId: 109951168296549660,
          alias: [],
          artists: [
            {
              img1v1Id: 18686200114669624,
              topicPerson: 0,
              picId: 0,
              alias: [],
              trans: "",
              albumSize: 0,
              img1v1Url:
                "http://p4.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
              picUrl:
                "http://p3.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
              followed: false,
              briefDesc: "",
              musicSize: 0,
              name: "茶鸣拾贰律",
              id: 54911645,
              img1v1Id_str: "18686200114669622",
            },
          ],
          publishTime: 1675958400000,
          picUrl:
            "http://p4.music.126.net/TvYJfqoq2jIBF0tvevdhMg==/109951168296549660.jpg",
          commentThreadId: "R_AL_3_159762700",
          briefDesc: "",
          blurPicUrl:
            "http://p4.music.126.net/TvYJfqoq2jIBF0tvevdhMg==/109951168296549660.jpg",
          companyId: 0,
          pic: 109951168296549660,
          subType: "录音室版",
          description: "",
          company: "茶鸣拾贰律",
          name: "无光浮沉",
          id: 159762700,
          type: "Single",
          size: 1,
          picId_str: "109951168296549660",
          areaId: 7,
          exclusive: false,
          isSub: false,
        },
        {
          songs: [],
          paid: false,
          onSale: false,
          status: 1,
          tags: "",
          copyrightId: 1419026,
          artist: {
            img1v1Id: 18686200114669624,
            topicPerson: 0,
            picId: 109951164856155380,
            alias: [],
            trans: "",
            albumSize: 24,
            img1v1Url:
              "http://p4.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
            picUrl:
              "http://p4.music.126.net/qGBIol8skoSjxfSEYTc3Lw==/109951164856155372.jpg",
            followed: false,
            briefDesc: "",
            musicSize: 83,
            name: "CORSAK胡梦周",
            id: 28083351,
            picId_str: "109951164856155372",
            img1v1Id_str: "18686200114669622",
          },
          picId: 109951168297190930,
          alias: [],
          artists: [
            {
              img1v1Id: 18686200114669624,
              topicPerson: 0,
              picId: 0,
              alias: [],
              trans: "",
              albumSize: 0,
              img1v1Url:
                "http://p3.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
              picUrl:
                "http://p3.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
              followed: false,
              briefDesc: "",
              musicSize: 0,
              name: "CORSAK胡梦周",
              id: 28083351,
              img1v1Id_str: "18686200114669622",
            },
          ],
          publishTime: 1675958400000,
          picUrl:
            "http://p4.music.126.net/DbZ76-jdRVxRB4WOe10erw==/109951168297190932.jpg",
          commentThreadId: "R_AL_3_159780728",
          briefDesc: "",
          blurPicUrl:
            "http://p3.music.126.net/DbZ76-jdRVxRB4WOe10erw==/109951168297190932.jpg",
          companyId: 0,
          pic: 109951168297190930,
          subType: "录音室版",
          description: "",
          company: "鲸鱼向海",
          name: "一张专辑",
          id: 159780728,
          type: "Single",
          size: 10,
          picId_str: "109951168297190932",
          areaId: 7,
          exclusive: false,
          isSub: false,
        },
        {
          songs: [],
          paid: false,
          onSale: false,
          status: 1,
          tags: "",
          copyrightId: 457010,
          artist: {
            img1v1Id: 18686200114669624,
            topicPerson: 0,
            picId: 109951167997248400,
            alias: ["FANTASTICS from 放浪一族"],
            trans: "",
            albumSize: 30,
            img1v1Url:
              "http://p3.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
            picUrl:
              "http://p3.music.126.net/SssSj2cpDVo4UqDwwO4pJA==/109951167997248404.jpg",
            followed: false,
            briefDesc: "",
            musicSize: 137,
            name: "FANTASTICS from EXILE TRIBE",
            id: 30892288,
            picId_str: "109951167997248404",
            img1v1Id_str: "18686200114669622",
          },
          picId: 109951168282451380,
          alias: [],
          artists: [
            {
              img1v1Id: 18686200114669624,
              topicPerson: 0,
              picId: 0,
              alias: [],
              trans: "",
              albumSize: 0,
              img1v1Url:
                "http://p3.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
              picUrl:
                "http://p4.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
              followed: false,
              briefDesc: "",
              musicSize: 0,
              name: "FANTASTICS from EXILE TRIBE",
              id: 30892288,
              img1v1Id_str: "18686200114669622",
            },
          ],
          publishTime: 1675958400000,
          picUrl:
            "http://p3.music.126.net/iDMTomU_FaWceyKLMRUERQ==/109951168282451379.jpg",
          commentThreadId: "R_AL_3_159402008",
          briefDesc: "",
          blurPicUrl:
            "http://p4.music.126.net/iDMTomU_FaWceyKLMRUERQ==/109951168282451379.jpg",
          companyId: 0,
          pic: 109951168282451380,
          subType: "录音室版",
          description: "",
          company: "(P)2023 AVEX ENTERTAINMENT INC.",
          name: "PANORAMA JET",
          id: 159402008,
          type: "Single",
          size: 1,
          picId_str: "109951168282451379",
          areaId: 8,
          exclusive: false,
          isSub: false,
        },
        {
          songs: [],
          paid: false,
          onSale: false,
          status: 1,
          tags: "",
          copyrightId: 22036,
          artist: {
            img1v1Id: 18686200114669624,
            topicPerson: 0,
            picId: 109951165488991490,
            alias: [],
            trans: "",
            albumSize: 6,
            img1v1Url:
              "http://p3.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
            picUrl:
              "http://p3.music.126.net/ti037oWDEMtbR9hpw_wxcw==/109951165488991480.jpg",
            followed: false,
            briefDesc: "",
            musicSize: 72,
            name: "DiorGoFlex",
            id: 28869014,
            picId_str: "109951165488991480",
            img1v1Id_str: "18686200114669622",
          },
          picId: 109951168299540020,
          alias: [],
          artists: [
            {
              img1v1Id: 18686200114669624,
              topicPerson: 0,
              picId: 0,
              alias: [],
              trans: "",
              albumSize: 0,
              img1v1Url:
                "http://p3.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
              picUrl:
                "http://p4.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
              followed: false,
              briefDesc: "",
              musicSize: 0,
              name: "DiorGoFlex",
              id: 28869014,
              img1v1Id_str: "18686200114669622",
            },
          ],
          publishTime: 1676044800000,
          picUrl:
            "http://p4.music.126.net/wvPlgGneuj7FZUVJsEMN9w==/109951168299540013.jpg",
          commentThreadId: "R_AL_3_159837788",
          briefDesc: "",
          blurPicUrl:
            "http://p3.music.126.net/wvPlgGneuj7FZUVJsEMN9w==/109951168299540013.jpg",
          companyId: 0,
          pic: 109951168299540020,
          subType: "录音室版",
          description: "",
          company: "秀动发行",
          name: "Go Flex上岸",
          id: 159837788,
          type: "专辑",
          size: 10,
          picId_str: "109951168299540013",
          areaId: 7,
          exclusive: false,
          isSub: false,
        },
        {
          songs: [],
          paid: false,
          onSale: false,
          status: 1,
          tags: "",
          copyrightId: -1,
          artist: {
            img1v1Id: 18686200114669624,
            topicPerson: 0,
            picId: 109951164893046940,
            alias: [],
            trans: "",
            albumSize: 2,
            img1v1Url:
              "http://p4.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
            picUrl:
              "http://p4.music.126.net/7sTx1479jG1KKVxkzr1ERw==/109951164893046952.jpg",
            followed: false,
            briefDesc: "",
            musicSize: 47,
            name: "陈慧颖",
            id: 31163114,
            picId_str: "109951164893046952",
            img1v1Id_str: "18686200114669622",
          },
          picId: 109951168301309700,
          alias: [],
          artists: [
            {
              img1v1Id: 18686200114669624,
              topicPerson: 0,
              picId: 0,
              alias: [],
              trans: "",
              albumSize: 0,
              img1v1Url:
                "http://p3.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
              picUrl:
                "http://p3.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
              followed: false,
              briefDesc: "",
              musicSize: 0,
              name: "陈慧颖",
              id: 31163114,
              img1v1Id_str: "18686200114669622",
            },
          ],
          publishTime: 1676044800000,
          picUrl:
            "http://p4.music.126.net/XjMTVIsKPfYkZ6et_oyK5Q==/109951168301309689.jpg",
          commentThreadId: "R_AL_3_159893376",
          briefDesc: "",
          blurPicUrl:
            "http://p3.music.126.net/XjMTVIsKPfYkZ6et_oyK5Q==/109951168301309689.jpg",
          companyId: 0,
          pic: 109951168301309700,
          subType: "录音室版",
          description: "",
          company: "",
          name: "晴",
          id: 159893376,
          type: "专辑",
          size: 1,
          picId_str: "109951168301309689",
          areaId: 7,
          exclusive: false,
          isSub: false,
        },
        {
          songs: [],
          paid: false,
          onSale: false,
          status: 1,
          tags: "",
          copyrightId: -1,
          artist: {
            img1v1Id: 18686200114669624,
            topicPerson: 0,
            picId: 109951165854608940,
            alias: [],
            trans: "",
            albumSize: 30,
            img1v1Url:
              "http://p3.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
            picUrl:
              "http://p3.music.126.net/-7S3t42wtIskUupTs__gdg==/109951165854608937.jpg",
            followed: false,
            briefDesc: "",
            musicSize: 79,
            name: "张晓涵",
            id: 14588567,
            picId_str: "109951165854608937",
            img1v1Id_str: "18686200114669622",
          },
          picId: 109951168296655070,
          alias: [],
          artists: [
            {
              img1v1Id: 18686200114669624,
              topicPerson: 0,
              picId: 0,
              alias: [],
              trans: "",
              albumSize: 0,
              img1v1Url:
                "http://p4.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
              picUrl:
                "http://p3.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
              followed: false,
              briefDesc: "",
              musicSize: 0,
              name: "张晓涵",
              id: 14588567,
              img1v1Id_str: "18686200114669622",
            },
          ],
          publishTime: 1676044800000,
          picUrl:
            "http://p4.music.126.net/3m5kApbrubdMKh1CRpAvWg==/109951168296655071.jpg",
          commentThreadId: "R_AL_3_159765863",
          briefDesc: "",
          blurPicUrl:
            "http://p3.music.126.net/3m5kApbrubdMKh1CRpAvWg==/109951168296655071.jpg",
          companyId: 0,
          pic: 109951168296655070,
          subType: "录音室版",
          description: "",
          company: "北京红点星文化传媒有限公司",
          name: "玉琉璃",
          id: 159765863,
          type: "Single",
          size: 2,
          picId_str: "109951168296655071",
          areaId: 7,
          exclusive: false,
          isSub: false,
        },
        {
          songs: [],
          paid: false,
          onSale: false,
          status: 1,
          tags: "",
          copyrightId: -1,
          artist: {
            img1v1Id: 18686200114669624,
            topicPerson: 0,
            picId: 109951164897967000,
            alias: [],
            trans: "",
            albumSize: 39,
            img1v1Url:
              "http://p4.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
            picUrl:
              "http://p3.music.126.net/Bi3k69dJxvrx-JGojRs7Lw==/109951164897967016.jpg",
            followed: false,
            briefDesc: "",
            musicSize: 78,
            name: "王巨星",
            id: 32399227,
            picId_str: "109951164897967016",
            img1v1Id_str: "18686200114669622",
          },
          picId: 109951168297058030,
          alias: [],
          artists: [
            {
              img1v1Id: 18686200114669624,
              topicPerson: 0,
              picId: 0,
              alias: [],
              trans: "",
              albumSize: 0,
              img1v1Url:
                "http://p3.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
              picUrl:
                "http://p3.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
              followed: false,
              briefDesc: "",
              musicSize: 0,
              name: "王巨星",
              id: 32399227,
              img1v1Id_str: "18686200114669622",
            },
          ],
          publishTime: 1676044800000,
          picUrl:
            "http://p4.music.126.net/PJsbYPsvXgL1tFsabp_O6A==/109951168297058027.jpg",
          commentThreadId: "R_AL_3_158120616",
          briefDesc: "",
          blurPicUrl:
            "http://p3.music.126.net/PJsbYPsvXgL1tFsabp_O6A==/109951168297058027.jpg",
          companyId: 0,
          pic: 109951168297058030,
          subType: "录音室版",
          description: "",
          company: "网易飓风",
          name: "我没哭",
          id: 158120616,
          type: "Single",
          size: 2,
          picId_str: "109951168297058027",
          areaId: 7,
          exclusive: false,
          isSub: false,
        },
        {
          songs: [],
          paid: false,
          onSale: false,
          status: 1,
          tags: "",
          copyrightId: -1,
          artist: {
            img1v1Id: 18686200114669624,
            topicPerson: 0,
            picId: 109951165067845140,
            alias: [],
            trans: "",
            albumSize: 25,
            img1v1Url:
              "http://p3.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
            picUrl:
              "http://p3.music.126.net/DvUsgfZiPLoWztVd44qA9w==/109951165067845133.jpg",
            followed: false,
            briefDesc: "",
            musicSize: 104,
            name: "姚六一",
            id: 27909602,
            picId_str: "109951165067845133",
            img1v1Id_str: "18686200114669622",
          },
          picId: 109951168299440370,
          alias: [],
          artists: [
            {
              img1v1Id: 18686200114669624,
              topicPerson: 0,
              picId: 0,
              alias: [],
              trans: "",
              albumSize: 0,
              img1v1Url:
                "http://p4.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
              picUrl:
                "http://p3.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
              followed: false,
              briefDesc: "",
              musicSize: 0,
              name: "姚六一",
              id: 27909602,
              img1v1Id_str: "18686200114669622",
            },
            {
              img1v1Id: 18686200114669624,
              topicPerson: 0,
              picId: 0,
              alias: [],
              trans: "",
              albumSize: 0,
              img1v1Url:
                "http://p4.music.126.net/VnZiScyynLG7atLIZ2YPkw==/18686200114669622.jpg",
              picUrl:
                "http://p3.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg",
              followed: false,
              briefDesc: "",
              musicSize: 0,
              name: "Luya",
              id: 53709268,
              img1v1Id_str: "18686200114669622",
            },
          ],
          publishTime: 1676044800000,
          picUrl:
            "http://p3.music.126.net/lsh40r1QlU4OhDLbtnXtLQ==/109951168299440362.jpg",
          commentThreadId: "R_AL_3_159837273",
          briefDesc: "",
          blurPicUrl:
            "http://p3.music.126.net/lsh40r1QlU4OhDLbtnXtLQ==/109951168299440362.jpg",
          companyId: 0,
          pic: 109951168299440370,
          subType: "录音室版",
          description: "",
          company: "讯飞音乐",
          name: "怪星球",
          id: 159837273,
          type: "Single",
          size: 3,
          picId_str: "109951168299440362",
          areaId: 7,
          exclusive: false,
          isSub: false,
        },
      ],
      code: 200,
    };
    album_list.value = data.monthData.splice(0, album_count);
    album_loading.value = false;
  };

  //切换 类型
  const chooseAlbumType = (index) => {
    album_index.value = index;
    album_params.value.area = index != 0 ? album_area[index].code : "";
    getAlbumList(album_params);
  };

  onMounted(() => {
    getAlbumList(album_params);
  });
</script>

<style lang="less" scoped>
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
  .album_list {
    padding: 0 20px;
    margin-bottom: 25px;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
  }
</style>
