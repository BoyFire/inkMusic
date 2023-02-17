<template>
  <div class="artist">
    <div class="artist-container">
      <!-- 歌手主体 -->
      <div class="artist-main">
        <div class="list-container" v-infinite-scroll="loadMore">
          <template v-for="item in list">
            <ArtistItem :item="item" />
          </template>
          <template v-if="isLoading">
            <Loading />
          </template>
        </div>
      </div>
      <!-- 侧边条件选择栏 -->
      <div class="aside-box">
        <el-affix :offset="140">
          <div class="aside-menu">
            <div class="filter">
              <!-- 热门+ 首字母条件 -->
              <div class="filter-item">
                <span
                  v-for="(item, index) in initial"
                  :key="index"
                  :class="index === initialIndex ? 'active' : ''"
                  @click="selectType('initial', index)"
                  >{{ item.label }}</span
                >
              </div>
              <!-- 语种 -->
              <div class="filter-item">
                <span
                  v-for="(item, index) in area"
                  :key="index"
                  :class="index === areaIndex ? 'active' : ''"
                  @click="selectType('area', index)"
                  >{{ item.label }}</span
                >
              </div>
              <!-- 歌手类型 -->
              <div class="filter-item">
                <span
                  v-for="(item, index) in singerType"
                  :key="index"
                  :class="index === typeIndex ? 'active' : ''"
                  @click="selectType('type', index)"
                  >{{ item.label }}</span
                >
              </div>
            </div>
          </div>
        </el-affix>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import ArtistItem from "@components/ArtistItem.vue";
  import Loading from "@components/Loading.vue";
  import { onMounted, ref, Ref, watchEffect } from "vue";

  const singerType = [
    { label: "全部", val: -1 },
    { label: "男歌手", val: 1 },
    { label: "女歌手", val: 2 },
    { label: "乐队", val: 3 },
  ];

  const area = [
    { label: "全部", val: -1 },
    { label: "华语", val: 7 },
    { label: "欧美", val: 96 },
    { label: "日本", val: 8 },
    { label: "韩国", val: 16 },
    { label: "其他", val: 0 },
  ];

  const initial: Ref<any> = ref([
    { label: "热门", val: -1 },
    { label: "#", val: 0 },
  ]);

  const typeIndex: Ref<number> = ref(0);
  const areaIndex: Ref<number> = ref(0);
  const initialIndex: Ref<number> = ref(0);
  const params: Ref<any> = ref({
    area: "",
    type: "",
    initial: "",
    limit: 30,
    offset: 0,
  });
  const list: Ref<any[]> = ref([]);
  const isLoading: Ref<boolean> = ref(true);
  const busy: Ref<boolean> = ref(true);

  //  初始化并填充 第一个选择条件
  const renderInitial = () => {
    const alphabet = [];
    for (let i = 0; i < 26; i++) {
      alphabet.push({
        label: String.fromCharCode(65 + i),
        val: String.fromCharCode(97 + i),
      });
    }
    initial.value = [initial.value[0], ...alphabet, initial.value[1]];
  };

  const selectType = (type: string, index: number) => {
    switch (type) {
      case "type":
        typeIndex.value = index;
        break;
      case "area":
        areaIndex.value = index;
        break;
      default:
        initialIndex.value = index;
        break;
    }
    list.value = [];
    params.value.offset = 0;
    params.value.type = singerType[typeIndex.value].val;
  };

  const getArtist = async (params) => {
    //获取数据
    const data = {
      artists: [
        {
          albumSize: 127,
          alias: ["Eason Chan", "Chan Yick Shun"],
          briefDesc: "",
          fansCount: 8493957,
          followed: false,
          id: 2116,
          img1v1Id: 109951166115911710,
          img1v1Id_str: "109951166115911716",
          img1v1Url:
            "http://p2.music.126.net/rYYhHXZHwCfizE0N46F37Q==/109951166115911716.jpg",
          musicSize: 1838,
          name: "陈奕迅",
          picId: 109951166115915090,
          picId_str: "109951166115915081",
          picUrl:
            "http://p2.music.126.net/w_vuv9hBWq2hlJxJcmJrjg==/109951166115915081.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          albumSize: 61,
          alias: ["JJ Lin", "Wayne Lim"],
          briefDesc: "",
          fansCount: 9262667,
          followed: false,
          id: 3684,
          img1v1Id: 109951167878713400,
          img1v1Id_str: "109951167878713410",
          img1v1Url:
            "http://p2.music.126.net/thrEGQSfLQp0Kd6M5yBEEg==/109951167878713410.jpg",
          musicSize: 553,
          name: "林俊杰",
          picId: 109951167878710660,
          picId_str: "109951167878710661",
          picUrl:
            "http://p2.music.126.net/7636PzUiFMETHU7SAr05FA==/109951167878710661.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 97137413,
          albumSize: 19,
          alias: ["Joker Xue", "Jacky Xue"],
          briefDesc: "",
          fansCount: 15480880,
          followed: false,
          id: 5781,
          img1v1Id: 109951165034950660,
          img1v1Id_str: "109951165034950656",
          img1v1Url:
            "http://p2.music.126.net/1tSJODTpcbZvNTCdsn4RYA==/109951165034950656.jpg",
          musicSize: 259,
          name: "薛之谦",
          picId: 109951165034938860,
          picId_str: "109951165034938865",
          picUrl:
            "http://p2.music.126.net/LCWqYYKoCEZKuAC3S3lIeg==/109951165034938865.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 93504818,
          albumSize: 13,
          alias: ["雷子"],
          briefDesc: "",
          fansCount: 3323061,
          followed: false,
          id: 6731,
          img1v1Id: 18806046882229308,
          img1v1Id_str: "18806046882229308",
          img1v1Url:
            "http://p2.music.126.net/w_UWOls2uCkFN_U62788Xg==/18806046882229308.jpg",
          musicSize: 96,
          name: "赵雷",
          picId: 3300733912483912,
          picUrl:
            "http://p2.music.126.net/j6KWzSdlRKzZUwJnQx3vBA==/3300733912483912.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 281382,
          albumSize: 53,
          alias: ["G.E.M."],
          briefDesc: "",
          fansCount: 8290139,
          followed: false,
          id: 7763,
          img1v1Id: 109951167771736530,
          img1v1Id_str: "109951167771736533",
          img1v1Url:
            "http://p2.music.126.net/oJorrgJ3IotZUAbZkBMuFw==/109951167771736533.jpg",
          musicSize: 373,
          name: "G.E.M.邓紫棋",
          picId: 109951167773880640,
          picId_str: "109951167773880633",
          picUrl:
            "http://p2.music.126.net/fq1O8ZRT5_FHzg_uLEtUQA==/109951167773880633.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 6228671,
          albumSize: 30,
          alias: ["Ronghao Li"],
          briefDesc: "",
          fansCount: 7467726,
          followed: false,
          id: 4292,
          img1v1Id: 109951165693670160,
          img1v1Id_str: "109951165693670160",
          img1v1Url:
            "http://p2.music.126.net/6qELnB2Zk0rPqczY9LbSoQ==/109951165693670160.jpg",
          musicSize: 226,
          name: "李荣浩",
          picId: 109951165693668660,
          picId_str: "109951165693668650",
          picUrl:
            "http://p2.music.126.net/mkWDxcXZk8W3NwdCEMg00A==/109951165693668650.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 3301779206,
          albumSize: 23,
          alias: ["Accusefive"],
          briefDesc: "",
          fansCount: 3610385,
          followed: false,
          id: 12676697,
          img1v1Id: 109951168306621490,
          img1v1Id_str: "109951168306621485",
          img1v1Url:
            "http://p2.music.126.net/MbhzNEtk-c3KybdGtq3ueQ==/109951168306621485.jpg",
          musicSize: 64,
          name: "告五人",
          picId: 109951168306629780,
          picId_str: "109951168306629780",
          picUrl:
            "http://p2.music.126.net/Xyoa72EqbaHGHiSfL5D_qA==/109951168306629780.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 31265745,
          albumSize: 48,
          alias: ["Vae"],
          briefDesc: "",
          fansCount: 7047353,
          followed: false,
          id: 5771,
          img1v1Id: 109951163536269820,
          img1v1Id_str: "109951163536269820",
          img1v1Url:
            "http://p2.music.126.net/ATZ8-mOxophKXrLC0iXMZw==/109951163536269820.jpg",
          musicSize: 183,
          name: "许嵩",
          picId: 109951163536274580,
          picId_str: "109951163536274581",
          picUrl:
            "http://p2.music.126.net/_D9P0JKRDYm3jEay9EfhRw==/109951163536274581.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 328199093,
          albumSize: 41,
          alias: [],
          briefDesc: "",
          fansCount: 3458348,
          followed: false,
          id: 14312549,
          img1v1Id: 109951165907833550,
          img1v1Id_str: "109951165907833550",
          img1v1Url:
            "http://p2.music.126.net/FRmMNIYCEBkzd0KcSvFW3Q==/109951165907833550.jpg",
          musicSize: 131,
          name: "王贰浪",
          picId: 109951163303426320,
          picId_str: "109951163303426318",
          picUrl:
            "http://p2.music.126.net/Kskaga6PwyOIvE7id7_J9A==/109951163303426318.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 90467349,
          albumSize: 18,
          alias: ["刘清云"],
          briefDesc: "",
          fansCount: 1339073,
          followed: false,
          id: 12631485,
          img1v1Id: 109951168064680450,
          img1v1Id_str: "109951168064680452",
          img1v1Url:
            "http://p2.music.126.net/VvralC98cfciOPJmXijOkg==/109951168064680452.jpg",
          musicSize: 51,
          name: "h3R3",
          picId: 109951165830678580,
          picId_str: "109951165830678577",
          picUrl:
            "http://p2.music.126.net/iHVX4p12jdQsyEosLhDGQg==/109951165830678577.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 70436671,
          albumSize: 41,
          alias: ["杨浪，youngcaptain"],
          briefDesc: "",
          fansCount: 3434366,
          followed: false,
          id: 1143033,
          img1v1Id: 109951167573473140,
          img1v1Id_str: "109951167573473133",
          img1v1Url:
            "http://p2.music.126.net/i602BtKvl61sUsTY5tqnnA==/109951167573473133.jpg",
          musicSize: 109,
          name: "队长",
          picId: 109951164546544260,
          picId_str: "109951164546544248",
          picUrl:
            "http://p2.music.126.net/CsGY6JmX21cxa-5DZKIqpQ==/109951164546544248.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 1461695826,
          albumSize: 53,
          alias: [],
          briefDesc: "",
          fansCount: 8397638,
          followed: false,
          id: 12138269,
          img1v1Id: 109951166048163330,
          img1v1Id_str: "109951166048163326",
          img1v1Url:
            "http://p2.music.126.net/6dylPpmQsQZaSlDkSoiv9g==/109951166048163326.jpg",
          musicSize: 282,
          name: "毛不易",
          picId: 109951165787072460,
          picId_str: "109951165787072463",
          picUrl:
            "http://p2.music.126.net/W5LXS0-I-P9Wk6lQpS6HGA==/109951165787072463.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          albumSize: 38,
          alias: ["Jay Chou", "周董"],
          briefDesc: "",
          fansCount: 7470788,
          followed: false,
          id: 6452,
          img1v1Id: 109951165793871060,
          img1v1Id_str: "109951165793871057",
          img1v1Url:
            "http://p2.music.126.net/Esjm32Q05PQoX8pF008u7w==/109951165793871057.jpg",
          musicSize: 527,
          name: "周杰伦",
          picId: 109951165793869650,
          picId_str: "109951165793869641",
          picUrl:
            "http://p2.music.126.net/BbR3TuhPULMLDV0MjczI4g==/109951165793869641.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 2287435,
          albumSize: 36,
          alias: ["Tanya Chua"],
          briefDesc: "",
          fansCount: 2239510,
          followed: false,
          id: 7214,
          img1v1Id: 109951166229071730,
          img1v1Id_str: "109951166229071726",
          img1v1Url:
            "http://p2.music.126.net/36WeG5-ykSvhlzujVMtWyw==/109951166229071726.jpg",
          musicSize: 470,
          name: "蔡健雅",
          picId: 109951166229097650,
          picId_str: "109951166229097647",
          picUrl:
            "http://p2.music.126.net/Uz3kQexeEK-HY_pNk3V0UA==/109951166229097647.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 1750344369,
          albumSize: 38,
          alias: [],
          briefDesc: "",
          fansCount: 3959654,
          followed: false,
          id: 31376161,
          img1v1Id: 109951165122695490,
          img1v1Id_str: "109951165122695489",
          img1v1Url:
            "http://p2.music.126.net/ei0o1W2N_SX1vpkXr2DDtA==/109951165122695489.jpg",
          musicSize: 139,
          name: "颜人中",
          picId: 109951165122696430,
          picId_str: "109951165122696427",
          picUrl:
            "http://p2.music.126.net/M9GvSuKJQyfPKprZaLKt7A==/109951165122696427.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 272502907,
          albumSize: 28,
          alias: [],
          briefDesc: "",
          fansCount: 322065,
          followed: false,
          id: 29588305,
          img1v1Id: 109951167831092200,
          img1v1Id_str: "109951167831092197",
          img1v1Url:
            "http://p2.music.126.net/2Q69motEVHijKKck-Eb1-w==/109951167831092197.jpg",
          musicSize: 53,
          name: "刘大拿",
          picId: 109951163534426100,
          picId_str: "109951163534426092",
          picUrl:
            "http://p2.music.126.net/nDHmzzJFCPnqbdFEkZh9eA==/109951163534426092.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 3492944,
          albumSize: 110,
          alias: ["Jason Zhang"],
          briefDesc: "",
          fansCount: 3256829,
          followed: false,
          id: 6472,
          img1v1Id: 109951168234197890,
          img1v1Id_str: "109951168234197887",
          img1v1Url:
            "http://p2.music.126.net/z2HqHy_CXghF0ehV-CzL9g==/109951168234197887.jpg",
          musicSize: 742,
          name: "张杰",
          picId: 109951168167006620,
          picId_str: "109951168167006631",
          picUrl:
            "http://p2.music.126.net/-Of37SoslyjWsPhLKTogqQ==/109951168167006631.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 7890760922,
          albumSize: 131,
          alias: ["Joey Yung"],
          briefDesc: "",
          fansCount: 323172,
          followed: false,
          id: 9269,
          img1v1Id: 109951165577493780,
          img1v1Id_str: "109951165577493779",
          img1v1Url:
            "http://p2.music.126.net/k6tjTQYfnwjAMJIwlL5Bhw==/109951165577493779.jpg",
          musicSize: 1897,
          name: "容祖儿",
          picId: 109951165577498100,
          picId_str: "109951165577498093",
          picUrl:
            "http://p2.music.126.net/eFFk_q2gM-Zl6q6q9q1HGw==/109951165577498093.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 2094263743,
          albumSize: 93,
          alias: [],
          briefDesc: "",
          fansCount: 4984248,
          followed: false,
          id: 35531,
          img1v1Id: 109951166316765740,
          img1v1Id_str: "109951166316765749",
          img1v1Url:
            "http://p2.music.126.net/-rGhV4uUUaRsvjG7Phw_IA==/109951166316765749.jpg",
          musicSize: 5860,
          name: "Justin Bieber",
          picId: 109951166316759380,
          picId_str: "109951166316759373",
          picUrl:
            "http://p2.music.126.net/rQnFyWZt9yhg9MON2lH2UQ==/109951166316759373.jpg",
          topicPerson: 0,
          trans: "贾斯汀·比伯",
          transNames: ["贾斯汀·比伯"],
        },
        {
          accountId: 349660440,
          albumSize: 8,
          alias: [],
          briefDesc: "",
          fansCount: 2919545,
          followed: false,
          id: 2843,
          img1v1Id: 109951165912271970,
          img1v1Id_str: "109951165912271974",
          img1v1Url:
            "http://p2.music.126.net/7uBClWo-0s4QFusH3q4HAg==/109951165912271974.jpg",
          musicSize: 42,
          name: "郭顶",
          picId: 109951165912271970,
          picId_str: "109951165912271970",
          picUrl:
            "http://p2.music.126.net/7OoAuH2Iqjr3Owmqf4pNFQ==/109951165912271970.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          albumSize: 245,
          alias: [],
          briefDesc: "",
          fansCount: 188944,
          followed: false,
          id: 46966451,
          img1v1Id: 109951166004602580,
          img1v1Id_str: "109951166004602570",
          img1v1Url:
            "http://p2.music.126.net/9RpZgMal5PCItO1V3BW3Dw==/109951166004602570.jpg",
          musicSize: 426,
          name: "苏星婕",
          picId: 109951166004592930,
          picId_str: "109951166004592927",
          picUrl:
            "http://p2.music.126.net/yyIv9i7uJVyBcqgC9uszLw==/109951166004592927.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          albumSize: 62,
          alias: ["Mayday"],
          briefDesc: "",
          fansCount: 2264231,
          followed: false,
          id: 13193,
          img1v1Id: 109951168184993800,
          img1v1Id_str: "109951168184993797",
          img1v1Url:
            "http://p2.music.126.net/9BIarXz2Rosvjt26Jb-OSg==/109951168184993797.jpg",
          musicSize: 790,
          name: "五月天",
          picId: 109951168184990900,
          picId_str: "109951168184990889",
          picUrl:
            "http://p2.music.126.net/jJtHjeXXLNofowo6CXlPIA==/109951168184990889.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          albumSize: 125,
          alias: [],
          briefDesc: "",
          fansCount: 3077110,
          followed: false,
          id: 11127,
          img1v1Id: 109951165566993820,
          img1v1Id_str: "109951165566993818",
          img1v1Url:
            "http://p2.music.126.net/9aCJYVD0JZ0NckqAn3a_3w==/109951165566993818.jpg",
          musicSize: 2236,
          name: "Beyond",
          picId: 109951165566992340,
          picId_str: "109951165566992331",
          picUrl:
            "http://p2.music.126.net/EawqbkXCxGmxZ6nnqTKxKw==/109951165566992331.jpg",
          topicPerson: 0,
          trans: "超越",
          transNames: ["超越"],
        },
        {
          accountId: 1423793964,
          albumSize: 36,
          alias: [],
          briefDesc: "",
          fansCount: 943847,
          followed: false,
          id: 36032190,
          img1v1Id: 109951165864168930,
          img1v1Id_str: "109951165864168923",
          img1v1Url:
            "http://p2.music.126.net/JOTZFdAoMXr_nVstcc2TGw==/109951165864168923.jpg",
          musicSize: 81,
          name: "yihuik苡慧",
          picId: 109951164831722510,
          picId_str: "109951164831722513",
          picUrl:
            "http://p2.music.126.net/SJnVAjDS4GCSxv7-hPu5uw==/109951164831722513.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 1840894990,
          albumSize: 12,
          alias: ["顶呱呱"],
          briefDesc: "",
          fansCount: 187161,
          followed: false,
          id: 32099752,
          img1v1Id: 109951166445804420,
          img1v1Id_str: "109951166445804420",
          img1v1Url:
            "http://p2.music.126.net/9tr3r7-UNsf9txG1gvnnpg==/109951166445804420.jpg",
          musicSize: 59,
          name: "30年前，50年后",
          picId: 109951164537785150,
          picId_str: "109951164537785144",
          picUrl:
            "http://p2.music.126.net/ZUG0iTXPEIXFEYg5LgSxSQ==/109951164537785144.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          albumSize: 57,
          alias: [],
          briefDesc: "",
          fansCount: 958152,
          followed: false,
          id: 14714082,
          img1v1Id: 109951163438785710,
          img1v1Id_str: "109951163438785715",
          img1v1Url:
            "http://p2.music.126.net/Fp2l7_9h12895Q22lIqzXQ==/109951163438785715.jpg",
          musicSize: 1857,
          name: "宝宝巴士",
          picId: 109951164255331250,
          picId_str: "109951164255331245",
          picUrl:
            "http://p2.music.126.net/hThCMr9Pm5fW9LLuD-M_xw==/109951164255331245.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 5700063,
          albumSize: 32,
          alias: [],
          briefDesc: "",
          fansCount: 3529170,
          followed: false,
          id: 12002248,
          img1v1Id: 109951168007866160,
          img1v1Id_str: "109951168007866165",
          img1v1Url:
            "http://p2.music.126.net/3kORMwHAvsaHheopIt2wlw==/109951168007866165.jpg",
          musicSize: 146,
          name: "沈以诚",
          picId: 109951165443367330,
          picId_str: "109951165443367321",
          picUrl:
            "http://p2.music.126.net/EL2R9tzkaLvpAkDpNTlxIQ==/109951165443367321.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 371143242,
          albumSize: 164,
          alias: [],
          briefDesc: "",
          fansCount: 4062617,
          followed: false,
          id: 1030001,
          img1v1Id: 109951167431002720,
          img1v1Id_str: "109951167431002717",
          img1v1Url:
            "http://p2.music.126.net/ExCdjHZtgV5HZryfG8hBUw==/109951167431002717.jpg",
          musicSize: 597,
          name: "周深",
          picId: 109951167431118880,
          picId_str: "109951167431118886",
          picUrl:
            "http://p2.music.126.net/UHE1Yoj23HcNvxMjKaq8TA==/109951167431118886.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 582053182,
          albumSize: 26,
          alias: [],
          briefDesc: "",
          fansCount: 391918,
          followed: false,
          id: 49937403,
          img1v1Id: 109951166501988720,
          img1v1Id_str: "109951166501988717",
          img1v1Url:
            "http://p2.music.126.net/oDmmsqtO5T3OxEQHc71kKA==/109951166501988717.jpg",
          musicSize: 46,
          name: "烟(许佳豪)",
          picId: 109951166501994780,
          picId_str: "109951166501994786",
          picUrl:
            "http://p2.music.126.net/jZIXAvWz0o-kE-U8Ootskg==/109951166501994786.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 18378979,
          albumSize: 82,
          alias: ["Silence Wang"],
          briefDesc: "",
          fansCount: 3154243,
          followed: false,
          id: 5538,
          img1v1Id: 109951168242038990,
          img1v1Id_str: "109951168242038988",
          img1v1Url:
            "http://p2.music.126.net/XGVlz2pWOvYjNTYNp9Jinw==/109951168242038988.jpg",
          musicSize: 501,
          name: "汪苏泷",
          picId: 109951168242041920,
          picId_str: "109951168242041913",
          picUrl:
            "http://p2.music.126.net/stcFdXbN2c21hzWqpDgTQA==/109951168242041913.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          albumSize: 62,
          alias: ["Karen Mok", "Karen Joy Morris"],
          briefDesc: "",
          fansCount: 1811555,
          followed: false,
          id: 8926,
          img1v1Id: 109951168281866720,
          img1v1Id_str: "109951168281866722",
          img1v1Url:
            "http://p2.music.126.net/DyfLDADCiwvaHJ-qMIRyZw==/109951168281866722.jpg",
          musicSize: 977,
          name: "莫文蔚",
          picId: 109951168281868180,
          picId_str: "109951168281868181",
          picUrl:
            "http://p2.music.126.net/WN6DiyAjveSNB6jaz8ko1g==/109951168281868181.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 1880071427,
          albumSize: 133,
          alias: [],
          briefDesc: "",
          fansCount: 6552384,
          followed: false,
          id: 44266,
          img1v1Id: 109951167881965220,
          img1v1Id_str: "109951167881965224",
          img1v1Url:
            "http://p2.music.126.net/XRfyWFfOEZq9WXWrW2drew==/109951167881965224.jpg",
          musicSize: 2065,
          name: "Taylor Swift",
          picId: 109951167881974530,
          picId_str: "109951167881974520",
          picUrl:
            "http://p2.music.126.net/J9vY1-_n5msBf8nyCKdL_w==/109951167881974520.jpg",
          topicPerson: 0,
          trans: "泰勒·斯威夫特",
          transNames: ["泰勒·斯威夫特"],
        },
        {
          accountId: 50094890,
          albumSize: 6,
          alias: [],
          briefDesc: "",
          fansCount: 242848,
          followed: false,
          id: 12499721,
          img1v1Id: 109951163881187440,
          img1v1Id_str: "109951163881187446",
          img1v1Url:
            "http://p2.music.126.net/8u4GmCvxmqRT5oxRdjloWw==/109951163881187446.jpg",
          musicSize: 28,
          name: "Kirsty刘瑾睿",
          picId: 109951165738539020,
          picId_str: "109951165738539025",
          picUrl:
            "http://p2.music.126.net/nmNsl9BXuwoAgB1mQtXCFw==/109951165738539025.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          albumSize: 90,
          alias: ["王靖雯", "Faye Wong", "Shirley Wong"],
          briefDesc: "",
          fansCount: 2066197,
          followed: false,
          id: 9621,
          img1v1Id: 18727981557622764,
          img1v1Id_str: "18727981557622763",
          img1v1Url:
            "http://p2.music.126.net/GKhjgN1ltQYK3eYf9DdJwg==/18727981557622763.jpg",
          musicSize: 1555,
          name: "王菲",
          picId: 18764265441342020,
          picId_str: "18764265441342019",
          picUrl:
            "http://p2.music.126.net/1KQVD6XWbs5IAV0xOj1ZIA==/18764265441342019.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          albumSize: 1,
          alias: [],
          briefDesc: "",
          fansCount: 27184,
          followed: false,
          id: 54743524,
          img1v1Id: 109951168272077570,
          img1v1Id_str: "109951168272077564",
          img1v1Url:
            "http://p2.music.126.net/duZ4prisG32oyzOv7BJk5A==/109951168272077564.jpg",
          musicSize: 1,
          name: "那艺娜",
          picId: 109951168272081010,
          picId_str: "109951168272081011",
          picUrl:
            "http://p2.music.126.net/GAInzw41HYSRjrNvLkpjLA==/109951168272081011.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 6966840,
          albumSize: 20,
          alias: [],
          briefDesc: "",
          fansCount: 3217464,
          followed: false,
          id: 865007,
          img1v1Id: 109951167711025920,
          img1v1Id_str: "109951167711025915",
          img1v1Url:
            "http://p2.music.126.net/cW_RNnyKMZfpRe49fv9Ozg==/109951167711025915.jpg",
          musicSize: 193,
          name: "法老",
          picId: 109951167728753950,
          picId_str: "109951167728753946",
          picUrl:
            "http://p2.music.126.net/HTXB834S72ulGPL1oM-RCg==/109951167728753946.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 357377769,
          albumSize: 10,
          alias: [],
          briefDesc: "",
          fansCount: 286999,
          followed: false,
          id: 12395355,
          img1v1Id: 109951168045089660,
          img1v1Id_str: "109951168045089671",
          img1v1Url:
            "http://p2.music.126.net/gGzsWV3KEW9QKWqfU5WPCQ==/109951168045089671.jpg",
          musicSize: 23,
          name: "盛哲",
          picId: 109951168041919300,
          picId_str: "109951168041919302",
          picUrl:
            "http://p2.music.126.net/nvS4FjQeMXeff6iFncyeyg==/109951168041919302.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          albumSize: 124,
          alias: ["Hatsune Miku"],
          briefDesc: "",
          fansCount: 1525392,
          followed: false,
          id: 159692,
          img1v1Id: 109951164605977710,
          img1v1Id_str: "109951164605977706",
          img1v1Url:
            "http://p2.music.126.net/GTrpKziMFTfaQnl2YzUZtg==/109951164605977706.jpg",
          musicSize: 1427,
          name: "初音ミク",
          picId: 109951164605971340,
          picId_str: "109951164605971338",
          picUrl:
            "http://p2.music.126.net/-QWJunQEsgiYvEoF4VITgQ==/109951164605971338.jpg",
          topicPerson: 0,
          trans: "初音未来",
          transNames: ["初音未来"],
        },
        {
          albumSize: 123,
          alias: ["Jacky Cheung"],
          briefDesc: "",
          fansCount: 1847845,
          followed: false,
          id: 6460,
          img1v1Id: 109951166958312100,
          img1v1Id_str: "109951166958312104",
          img1v1Url:
            "http://p2.music.126.net/LJ10D1f0AmqT_lODhhlAZg==/109951166958312104.jpg",
          musicSize: 2425,
          name: "张学友",
          picId: 109951166958310160,
          picId_str: "109951166958310165",
          picUrl:
            "http://p2.music.126.net/bGTTVbPYHT24w2HkHrdXmQ==/109951166958310165.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 122596992,
          albumSize: 19,
          alias: [],
          briefDesc: "",
          fansCount: 1398656,
          followed: false,
          id: 12236125,
          img1v1Id: 109951166116634930,
          img1v1Id_str: "109951166116634923",
          img1v1Url:
            "http://p2.music.126.net/_kaR9yv0ksu1CQifP4Thzw==/109951166116634923.jpg",
          musicSize: 139,
          name: "王以太",
          picId: 109951166114595170,
          picId_str: "109951166114595166",
          picUrl:
            "http://p2.music.126.net/mf_zWF3REZJQU5Ge38KX_g==/109951166114595166.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 300806208,
          albumSize: 21,
          alias: [],
          briefDesc: "",
          fansCount: 1121671,
          followed: false,
          id: 12193174,
          img1v1Id: 109951167649746850,
          img1v1Id_str: "109951167649746852",
          img1v1Url:
            "http://p2.music.126.net/qM6Qf4aYWWn2Q6kf4Rc7Uw==/109951167649746852.jpg",
          musicSize: 106,
          name: "Capper",
          picId: 109951167140367490,
          picId_str: "109951167140367485",
          picUrl:
            "http://p2.music.126.net/htZBUaMOnJqHLKsUL5Mkrw==/109951167140367485.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          albumSize: 34,
          alias: ["Stefanie Sun"],
          briefDesc: "",
          fansCount: 2162972,
          followed: false,
          id: 9272,
          img1v1Id: 109951166566540910,
          img1v1Id_str: "109951166566540916",
          img1v1Url:
            "http://p2.music.126.net/7nG1El9nN9875yl28bjfoA==/109951166566540916.jpg",
          musicSize: 531,
          name: "孙燕姿",
          picId: 109951166566542420,
          picId_str: "109951166566542419",
          picUrl:
            "http://p2.music.126.net/xQHd2jGf6z0thNcNamwBhQ==/109951166566542419.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 324958565,
          albumSize: 21,
          alias: [],
          briefDesc: "",
          fansCount: 32861,
          followed: false,
          id: 31002901,
          img1v1Id: 109951168287943330,
          img1v1Id_str: "109951168287943320",
          img1v1Url:
            "http://p1.music.126.net/UdAXRg4hpJUY5UsEMmVT5Q==/109951168287943320.jpg",
          musicSize: 50,
          name: "MoreLearn 27",
          picId: 109951164815330980,
          picId_str: "109951164815330982",
          picUrl:
            "http://p1.music.126.net/k26XLBKDtkKBgYnuzu7T_w==/109951164815330982.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 105753794,
          albumSize: 103,
          alias: [],
          briefDesc: "",
          fansCount: 2565529,
          followed: false,
          id: 9255,
          img1v1Id: 109951167124899790,
          img1v1Id_str: "109951167124899789",
          img1v1Url:
            "http://p1.music.126.net/MetP2jvd6gjbUGG6NIp5_A==/109951167124899789.jpg",
          musicSize: 292,
          name: "任然",
          picId: 109951163137467400,
          picId_str: "109951163137467389",
          picUrl:
            "http://p1.music.126.net/GL0xcgu4OUml509BbW2qAQ==/109951163137467389.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 542203503,
          albumSize: 41,
          alias: [],
          briefDesc: "",
          fansCount: 310124,
          followed: false,
          id: 29051613,
          img1v1Id: 109951167532348640,
          img1v1Id_str: "109951167532348635",
          img1v1Url:
            "http://p1.music.126.net/Vg57T7r5YslJtsEDnvwDZw==/109951167532348635.jpg",
          musicSize: 98,
          name: "郑润泽",
          picId: 109951164458656130,
          picId_str: "109951164458656122",
          picUrl:
            "http://p1.music.126.net/BtXjoRNLCZjoSV-3Ag3M0Q==/109951164458656122.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 550065289,
          albumSize: 50,
          alias: [],
          briefDesc: "",
          fansCount: 2043531,
          followed: false,
          id: 33435854,
          img1v1Id: 109951166986890000,
          img1v1Id_str: "109951166986889997",
          img1v1Url:
            "http://p1.music.126.net/ZnHLCrzPu4cdBz1XPJiqGw==/109951166986889997.jpg",
          musicSize: 104,
          name: "要不要买菜",
          picId: 109951165049373950,
          picId_str: "109951165049373951",
          picUrl:
            "http://p1.music.126.net/6a0C2oob57RHQ-fmpjIw3Q==/109951165049373951.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          albumSize: 94,
          alias: ["Leo Ku"],
          briefDesc: "",
          fansCount: 425903,
          followed: false,
          id: 2849,
          img1v1Id: 5965950092650438,
          img1v1Url:
            "http://p1.music.126.net/dc7JiLEiMpM_MgX0kPPImQ==/5965950092650438.jpg",
          musicSize: 1150,
          name: "古巨基",
          picId: 5927467185648928,
          picUrl:
            "http://p1.music.126.net/OZh9Xp2iiyu93HOCXtxwqA==/5927467185648928.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 481214790,
          albumSize: 39,
          alias: [],
          briefDesc: "",
          fansCount: 2390472,
          followed: false,
          id: 31165848,
          img1v1Id: 109951163780408940,
          img1v1Id_str: "109951163780408942",
          img1v1Url:
            "http://p1.music.126.net/-vAAAt4wZ8bAJCkilZVbcQ==/109951163780408942.jpg",
          musicSize: 107,
          name: "刘大壮",
          picId: 109951163780702430,
          picId_str: "109951163780702427",
          picUrl:
            "http://p1.music.126.net/H70ceB4g0VAgWeLYrZEiWQ==/109951163780702427.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          accountId: 3345968860,
          albumSize: 85,
          alias: [],
          briefDesc: "",
          fansCount: 792803,
          followed: false,
          id: 7424,
          img1v1Id: 109951165134946640,
          img1v1Id_str: "109951165134946647",
          img1v1Url:
            "http://p1.music.126.net/la7enmQXserHhZEs_PWi4A==/109951165134946647.jpg",
          musicSize: 263,
          name: "程响",
          picId: 109951165134947140,
          picId_str: "109951165134947137",
          picUrl:
            "http://p1.music.126.net/T-HudMiTDp3iSgSfkkhqmQ==/109951165134947137.jpg",
          topicPerson: 0,
          trans: "",
        },
        {
          albumSize: 31,
          alias: ["Masiwei"],
          briefDesc: "",
          fansCount: 2610241,
          followed: false,
          id: 1132392,
          img1v1Id: 109951167626602400,
          img1v1Id_str: "109951167626602403",
          img1v1Url:
            "http://p1.music.126.net/tsAUi83ifOACTngHHi7mUw==/109951167626602403.jpg",
          musicSize: 189,
          name: "马思唯",
          picId: 109951167626611060,
          picId_str: "109951167626611050",
          picUrl:
            "http://p1.music.126.net/Kwr9wbqqncajark2P3bCGg==/109951167626611050.jpg",
          topicPerson: 0,
          trans: "",
        },
      ],
      more: true,
      code: 200,
    };

    list.value =
      params.value.offset !== 0
        ? [...list.value, ...data.artists]
        : data.artists;
    busy.value = !data.more;
    isLoading.value = data.more;
  };

  const loadMore = () => {
    busy.value = true;
    params.value.offset = list.value.length;
  };

  onMounted(() => {
    params.value.area = area[areaIndex.value].val;
    params.value.type = singerType[typeIndex.value].val;
    params.value.initial = initial.value[initialIndex.value].val;
    renderInitial();
  });

  watchEffect(() => {
    getArtist(params);
  });
</script>

<style scoped lang="less">
  .artist-container {
    display: flex;
    padding-top: 40px;

    .artist-main {
      flex: 1;
    }
  }
  .aside-menu {
    padding: 20px 20px 1px;
    margin-bottom: 25px;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
  }

  .filter-item {
    font-size: 0;
    padding-bottom: 20px;

    span {
      display: inline-block;
      padding: 0 12px;
      font-size: 14px;
      line-height: 30px;
      margin-right: 6px;
      vertical-align: top;
      cursor: pointer;
      border-radius: 3px;

      &.active {
        color: #fff;
        background: #ff641e;
      }
    }
  }

  .list-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    padding-bottom: 40px;
    margin-right: -40px;

    .item {
      position: relative;
      display: flex;
      width: calc(100% / 3 - 80px);
      padding: 10px 0;
      margin: 0 40px 20px;
      border-radius: 4px;
      background: #fff;
      box-shadow: 0 20px 27px rgb(0 0 0 / 5%);
      transition: all 0.3s ease-in-out;

      &:hover {
        margin-left: 0;
        width: calc(100% / 3 - 40px);
        border-radius: 60px 12px 12px 60px;
        transition: all 0.3s ease-in-out;
        background: -moz-linear-gradient(
          -45deg,
          #ffffff 20%,
          #ffb08e 100%
        ); /* FF3.6-15 */
        background: -webkit-linear-gradient(
          -45deg,
          #ffffff 20%,
          #ffb08e 100%
        ); /* Chrome10-25,Safari5.1-6 */
        background: linear-gradient(
          135deg,
          #ffffff 20%,
          #ffb08e 100%
        ); /* W3C, IE10+, FF16+, Chrome26+, Opera12+, Safari7+ */

        .faceImg {
          margin-left: 10px;
          border-radius: 100%;
          transition: all 0.3s ease-in-out;
        }
      }

      .circle {
        position: absolute;
        right: 10px;
        display: inline-block;
        padding: 10px;
        border-radius: 100%;
        border: 1px solid transparent;
        background: #fafafa;
        box-shadow: 0 2px 7px rgb(0 0 0 / 10%) inset;

        &::before {
          position: absolute;
          top: 6px;
          left: 6px;
          display: inline-block;
          content: "";
          width: 8px;
          height: 8px;
          border-radius: 100%;
          background: #ddd;
          box-shadow: 0 2px 7px rgb(0 0 0 / 10%);
        }
      }
    }

    .faceImg {
      display: block;
      width: 100px;
      margin-left: -40px;
      font-size: 0;
      border-radius: 12px;
      overflow: hidden;
      flex-shrink: 0;
      transition: all 0.3s ease-in-out;
    }

    .info {
      flex: 1;
      padding: 0 20px;

      .name {
        display: inline-block;
        white-space: nowrap;
        max-width: 80%;
        margin-right: 10px;
        line-height: 40px;
        font-size: 20px;
        overflow: hidden;
        text-overflow: ellipsis;
        vertical-align: middle;
      }

      .info-num {
        display: flex;
        padding-top: 10px;
        color: var(--color-text);

        span {
          flex: 1;
          display: inline-block;
          font-weight: 600;
          color: var(--color-text-main);

          em {
            display: block;
            font-style: normal;
            font-size: 12px;
            font-weight: 300;
            color: var(--color-text);
          }
        }
      }

      .icon-collect {
        cursor: pointer;

        &.active {
          color: var(--color-text-height);
        }
      }
    }
  }
</style>
