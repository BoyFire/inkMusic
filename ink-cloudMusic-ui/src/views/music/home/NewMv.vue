<template>
  <div class="mv_list">
    <div class="h_title">
      <h3>最新MV</h3>
      <span
        v-for="(item, index) in mv_area"
        :key="index"
        :class="index == mv_index ? 'active' : ''"
        @click="chooseMvType(index)"
        >{{ item }}</span
      >
    </div>

    <div class="wrapper">
      <mvList :mvList="mv_list" :loading="mv_loading" :num="mv_count"></mvList>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import mvList from "@components/MvList.vue";
  import { onMounted, Ref, ref } from "vue";

  const mv_area = ["全部", "内地", "港台", "欧美", "日本", "韩国"];
  const mv_type = ["全部", "官方版", "原生", "现场版", "网易出品"];
  const mv_list: Ref<any[]> = ref([]);
  const mv_index: Ref<number> = ref(0);
  const mv_params: Ref<any> = ref({ limit: 10, area: "" });
  const mv_count: Ref<number> = ref(12);
  const mv_loading: Ref<boolean> = ref(true);

  // 获取最新mv
  const getMV = async (params) => {
    //获取数据
    const data = {
      count: 5000,
      hasMore: true,
      data: [
        {
          id: 10928277,
          cover:
            "http://p1.music.126.net/MB9iRQKbhgnXANbI0QTJRA==/109951164909106319.jpg",
          name: "那年初夏",
          playCount: 291467,
          briefDesc: null,
          desc: null,
          artistName: "任然",
          artistId: 9255,
          duration: 309000,
          mark: 0,
          subed: false,
          artists: [
            {
              id: 9255,
              name: "任然",
              alias: [],
              transNames: null,
            },
          ],
        },
        {
          id: 10924941,
          cover:
            "http://p1.music.126.net/T6znsUgKcRxLZMG1VtCY8A==/109951164877420868.jpg",
          name: "Oh my god",
          playCount: 2487774,
          briefDesc: "(G)I-DLE最新MV公开！",
          desc: null,
          artistName: "(G)I-DLE",
          artistId: 14055085,
          duration: 200000,
          mark: 0,
          subed: false,
          artists: [
            {
              id: 14055085,
              name: "(G)I-DLE",
              alias: [
                "(여자)아이들",
                "Cube新女团",
                "女孩子",
                "女孩子们",
                "여자 아이들",
                "女娃",
                "gidle",
                "娃",
              ],
              transNames: null,
            },
          ],
        },
        {
          id: 393006,
          cover:
            "http://p1.music.126.net/_jIILNuwiBkHc6TWeR_rMQ==/2901611185772972.jpg",
          name: "See You Again",
          playCount: 28741757,
          briefDesc: "《速度与激情7》催泪片尾曲，鲜肉深情演唱挥别保罗",
          desc: null,
          artistName: "Wiz Khalifa",
          artistId: 46006,
          duration: 237590,
          mark: 0,
          subed: false,
          artists: [
            {
              id: 46006,
              name: "Wiz Khalifa",
              alias: [
                "维兹.卡利法",
                "Cameron Jibril Thomaz",
                "卡梅伦.贾布里勒.托马斯",
                "麻神",
              ],
              transNames: ["维兹.卡利法"],
            },
            {
              id: 90331,
              name: "Charlie Puth",
              alias: ["查理·普斯", "断眉", "猹", "Charles Otto Puth Jr."],
              transNames: ["查理·普斯"],
            },
          ],
        },
        {
          id: 10879100,
          cover:
            "http://p1.music.126.net/DCY2vbe-uyzRM6firT9o4w==/109951164224362934.jpg",
          name: "吹梦到西洲",
          playCount: 5350217,
          briefDesc: null,
          desc: null,
          artistName: "恋恋故人难",
          artistId: 32041318,
          duration: 316000,
          mark: 0,
          subed: false,
          artists: [
            {
              id: 32041318,
              name: "恋恋故人难",
              alias: [],
              transNames: null,
            },
            {
              id: 12308369,
              name: "黄诗扶",
              alias: null,
              transNames: null,
            },
            {
              id: 12084589,
              name: "妖扬",
              alias: [],
              transNames: null,
            },
          ],
        },
        {
          id: 10919322,
          cover:
            "http://p1.music.126.net/ZQ-gtRQ9V62nsMV9HKfweg==/109951164771170702.jpg",
          name: "BTS (방탄소년단) 'Black Swan' Official MV",
          playCount: 1991717,
          briefDesc: null,
          desc: null,
          artistName: "BTS (防弹少年团)",
          artistId: 783124,
          duration: 217000,
          mark: 0,
          subed: false,
          artists: [
            {
              id: 783124,
              name: "BTS (防弹少年团)",
              alias: [
                "방탄소년단",
                "BTS",
                "Bangtan Boys",
                "胖蛋",
                "防弹少年团",
                "bts 防弹少年团",
                "桃",
                "弹",
                "包头市",
              ],
              transNames: null,
            },
          ],
        },
        {
          id: 5359212,
          cover:
            "http://p1.music.126.net/8psrmKaW9WEY8boqbp1KSA==/3402988500122446.jpg",
          name: "电影《从你的全世界路过》“猪头的爱情”特辑",
          playCount: 2106125,
          briefDesc: null,
          desc: null,
          artistName: "群星",
          artistId: 122455,
          duration: 186670,
          mark: 0,
          subed: false,
          artists: [
            {
              id: 122455,
              name: "群星",
              alias: ["原声带", "Various Artists", "华语群星"],
              transNames: null,
            },
          ],
        },
        {
          id: 5647227,
          cover:
            "http://p1.music.126.net/_n5bnOjfCWI5USfGE8cerg==/109951163018611090.jpg",
          name: "Look What You Made Me Do",
          playCount: 33438072,
          briefDesc: "暗黑Taylor惊艳上线",
          desc: null,
          artistName: "Taylor Swift",
          artistId: 44266,
          duration: 255620,
          mark: 0,
          subed: false,
          artists: [
            {
              id: 44266,
              name: "Taylor Swift",
              alias: ["泰勒斯威夫特", "泰勒史薇芙特", "泰勒斯威芙特", "霉霉"],
              transNames: ["泰勒·斯威夫特"],
            },
          ],
        },
        {
          id: 522348,
          cover:
            "http://p1.music.126.net/Z_uwFVHAA2dMapgPKNHykw==/2880720468072997.jpg",
          name: "不潮不用花钱",
          playCount: 4366440,
          briefDesc: null,
          desc: null,
          artistName: "林俊杰",
          artistId: 3684,
          duration: 241860,
          mark: 0,
          subed: false,
          artists: [
            {
              id: 3684,
              name: "林俊杰",
              alias: ["JJ", "JJ Lin", "林三岁", "Wayne Lim"],
              transNames: null,
            },
          ],
        },
        {
          id: 10925738,
          cover:
            "http://p1.music.126.net/nHfViSNHNrWRp1jnQcp1wA==/109951164883476649.jpg",
          name: "mmexport1586322850541",
          playCount: 443619,
          briefDesc: null,
          desc: null,
          artistName: "KnightAe",
          artistId: 34598718,
          duration: 43000,
          mark: 0,
          subed: false,
          artists: [
            {
              id: 34598718,
              name: "KnightAe",
              alias: null,
              transNames: null,
            },
          ],
        },
        {
          id: 5496541,
          cover:
            "http://p1.music.126.net/AWeQgAOm0I5gEKIchQ4YCA==/19073228207168148.jpg",
          name: "红色高跟鞋",
          playCount: 18077780,
          briefDesc: null,
          desc: null,
          artistName: "蔡健雅",
          artistId: 7214,
          duration: 205820,
          mark: 0,
          subed: false,
          artists: [
            {
              id: 7214,
              name: "蔡健雅",
              alias: ["Tanya Chua"],
              transNames: null,
            },
          ],
        },
        {
          id: 424068,
          cover:
            "http://p1.music.126.net/8l7DLDaFc-Z3YPzA8hOPmA==/8002245627607734.jpg",
          name: "口是心非",
          playCount: 2428784,
          briefDesc: null,
          desc: null,
          artistName: "张雨生",
          artistId: 6459,
          duration: 282840,
          mark: 0,
          subed: false,
          artists: [
            {
              id: 6459,
              name: "张雨生",
              alias: ["小宝"],
              transNames: null,
            },
          ],
        },
        {
          id: 10798487,
          cover:
            "http://p1.music.126.net/4mJTA6CyzxwERSJ-KqHPHA==/109951163573107393.jpg",
          name: "RISE 登峰造极境",
          playCount: 15085716,
          briefDesc: null,
          desc: null,
          artistName: "英雄联盟",
          artistId: 1047337,
          duration: 210000,
          mark: 0,
          subed: false,
          artists: [
            {
              id: 1047337,
              name: "英雄联盟",
              alias: ["英雄联盟", "撸啊撸", "lol", "League of Legends"],
              transNames: null,
            },
          ],
          alias: ["2018《英雄联盟》S8全球总决赛主题曲MV"],
        },
      ],
      code: 200,
    };
    mv_list.value = data.data;
    mv_loading.value = false;
  };

  //切换mv类别
  const chooseMvType = (index) => {
    mv_index.value = index;
    mv_params.value.area = index !== 0 ? mv_area[index] : "";
    mv_loading.value = true;
    getMV(mv_params);
  };

  onMounted(() => {
    getMV(mv_params);
  });
</script>

<style lang="less" scoped>
  .mv_list {
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
