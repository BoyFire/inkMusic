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
                      :to="{ path: '/singer', query: { id: author.id } }"
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
    const data = {
      code: 200,
      list: [
        {
          subscribers: [],
          subscribed: null,
          creator: null,
          artists: null,
          tracks: null,
          updateFrequency: "更新75首",
          backgroundCoverId: 0,
          backgroundCoverUrl: null,
          titleImage: 0,
          titleImageUrl: null,
          englishTitle: null,
          opRecommend: false,
          recommendInfo: null,
          socialPlaylistCover: null,
          trackNumberUpdateTime: 1676247273616,
          subscribedCount: 4008961,
          cloudTrackCount: 0,
          userId: 1,
          adType: 0,
          createTime: 1404115136883,
          highQuality: false,
          newImported: false,
          specialType: 10,
          anonimous: false,
          updateTime: 1676247273739,
          coverImgId: 109951166952713760,
          totalDuration: 0,
          coverImgUrl:
            "https://p1.music.126.net/pcYHpMkdC69VVvWiynNklA==/109951166952713766.jpg",
          trackCount: 100,
          commentThreadId: "A_PL_0_19723756",
          trackUpdateTime: 1676270964618,
          privacy: 0,
          playCount: 5368477184,
          description: "云音乐中每天热度上升最快的100首单曲，每日更新。",
          ordered: true,
          tags: [],
          status: 0,
          name: "飙升榜",
          id: 19723756,
          coverImgId_str: "109951166952713766",
          ToplistType: "S",
        },
        {
          subscribers: [],
          subscribed: null,
          creator: null,
          artists: null,
          tracks: null,
          updateFrequency: "刚刚更新",
          backgroundCoverId: 0,
          backgroundCoverUrl: null,
          titleImage: 0,
          titleImageUrl: null,
          englishTitle: null,
          opRecommend: false,
          recommendInfo: null,
          socialPlaylistCover: null,
          trackNumberUpdateTime: 1676247981395,
          subscribedCount: 2721525,
          cloudTrackCount: 0,
          userId: 1,
          adType: 0,
          createTime: 1378721398225,
          highQuality: false,
          newImported: false,
          specialType: 10,
          anonimous: false,
          updateTime: 1676247981488,
          coverImgId: 109951166952686380,
          totalDuration: 0,
          coverImgUrl:
            "https://p1.music.126.net/wVmyNS6b_0Nn-y6AX8UbpQ==/109951166952686384.jpg",
          trackCount: 100,
          commentThreadId: "A_PL_0_3779629",
          trackUpdateTime: 1676274320627,
          privacy: 0,
          playCount: 2735086336,
          description:
            "云音乐新歌榜：云音乐用户一周内收听所有新歌（一月内最新发行） 官方TOP排行榜，每天更新。",
          ordered: true,
          tags: [],
          status: 0,
          name: "新歌榜",
          id: 3779629,
          coverImgId_str: "109951166952686384",
          ToplistType: "N",
        },
        {
          subscribers: [],
          subscribed: null,
          creator: null,
          artists: null,
          tracks: null,
          updateFrequency: "每周四更新",
          backgroundCoverId: 0,
          backgroundCoverUrl: null,
          titleImage: 0,
          titleImageUrl: null,
          englishTitle: null,
          opRecommend: false,
          recommendInfo: null,
          socialPlaylistCover: null,
          trackNumberUpdateTime: 1675926007277,
          subscribedCount: 707763,
          cloudTrackCount: 0,
          userId: 201586,
          adType: 0,
          createTime: 1374732325894,
          highQuality: false,
          newImported: false,
          specialType: 10,
          anonimous: false,
          updateTime: 1675926007448,
          coverImgId: 109951166961388700,
          totalDuration: 0,
          coverImgUrl:
            "https://p1.music.126.net/iFZ_nw2V86IFk90dc50kdQ==/109951166961388699.jpg",
          trackCount: 100,
          commentThreadId: "A_PL_0_2884035",
          trackUpdateTime: 1676273098283,
          privacy: 0,
          playCount: 556202944,
          description:
            "云音乐独立原创音乐人作品官方榜单，以推荐优秀原创作品为目的。每周四网易云音乐首发。申请网易音乐人：http://music.163.com/nmusician/",
          ordered: true,
          tags: [],
          status: 0,
          name: "原创榜",
          id: 2884035,
          coverImgId_str: "109951166961388699",
          ToplistType: "O",
        },
        {
          subscribers: [],
          subscribed: null,
          creator: null,
          artists: null,
          tracks: null,
          updateFrequency: "更新14首",
          backgroundCoverId: 0,
          backgroundCoverUrl: null,
          titleImage: 0,
          titleImageUrl: null,
          englishTitle: null,
          opRecommend: false,
          recommendInfo: null,
          socialPlaylistCover: null,
          trackNumberUpdateTime: 1676248061008,
          subscribedCount: 11741497,
          cloudTrackCount: 0,
          userId: 1,
          adType: 0,
          createTime: 1378721406014,
          highQuality: false,
          newImported: false,
          specialType: 10,
          anonimous: false,
          updateTime: 1676248061225,
          coverImgId: 109951166952706660,
          totalDuration: 0,
          coverImgUrl:
            "https://p1.music.126.net/ZyUjc7K_GDpD8MO1-GQkmA==/109951166952706664.jpg",
          trackCount: 200,
          commentThreadId: "A_PL_0_3778678",
          trackUpdateTime: 1676248061234,
          privacy: 0,
          playCount: 11185137664,
          description:
            "云音乐热歌榜：云音乐用户一周内收听所有线上歌曲官方TOP排行榜，每日更新。",
          ordered: true,
          tags: [],
          status: 0,
          name: "热歌榜",
          id: 3778678,
          coverImgId_str: "109951166952706664",
          ToplistType: "H",
        },
      ],
    };

    topList.value = data.list.splice(0, 4);
    topList.value.forEach(async (item) => {
      //获取榜单中详细歌曲信息
      const detailData = {
        code: 200,
        relatedVideos: null,
        playlist: {
          tracks: [
            {
              name: "乌梅子酱",
              id: 1997438791,
              pst: 0,
              t: 0,
              ar: [
                {
                  id: 4292,
                  name: "李荣浩",
                  tns: [],
                  alias: [],
                },
              ],
              alia: [],
              pop: 100,
              st: 0,
              rt: "",
              fee: 8,
              v: 6,
              crbt: null,
              cf: "",
              al: {
                id: 130148423,
                name: "纵横四海",
                picUrl:
                  "http://p3.music.126.net/dvBE3I5IYmDTmZq9SyKoRA==/109951168159576909.jpg",
                tns: [],
                pic_str: "109951168159576909",
                pic: 109951168159576910,
              },
              dt: 257251,
              h: {
                br: 320000,
                fid: 0,
                size: 10292205,
                vd: -55925,
                sr: 48000,
              },
              m: {
                br: 192000,
                fid: 0,
                size: 6175341,
                vd: -53336,
                sr: 48000,
              },
              l: {
                br: 128000,
                fid: 0,
                size: 4116909,
                vd: -51668,
                sr: 48000,
              },
              sq: {
                br: 994659,
                fid: 0,
                size: 31984723,
                vd: -56634,
                sr: 48000,
              },
              hr: {
                br: 1764737,
                fid: 0,
                size: 56747702,
                vd: -55901,
                sr: 48000,
              },
              a: null,
              cd: "01",
              no: 9,
              rtUrl: null,
              ftype: 0,
              rtUrls: [],
              djId: 0,
              copyright: 0,
              s_id: 0,
              mark: 536879104,
              originCoverType: 0,
              originSongSimpleData: null,
              tagPicList: null,
              resourceState: true,
              version: 6,
              songJumpInfo: null,
              entertainmentTags: null,
              single: 0,
              noCopyrightRcmd: null,
              mst: 9,
              cp: 1416891,
              mv: 14580827,
              rtype: 0,
              rurl: null,
              publishTime: 1668960000000,
            },
            {
              name: "入秋",
              id: 1477144603,
              pst: 0,
              t: 0,
              ar: [
                {
                  id: 31446467,
                  name: "RAMBO GANG",
                  tns: [],
                  alias: [],
                },
                {
                  id: 36288270,
                  name: "Trakin(T酱(*°∀°))",
                  tns: [],
                  alias: [],
                },
                {
                  id: 0,
                  name: "T-BONE",
                  tns: [],
                  alias: [],
                },
                {
                  id: 55066357,
                  name: "江楠江楠_",
                  tns: [],
                  alias: [],
                },
              ],
              alia: [],
              pop: 100,
              st: 0,
              rt: "",
              fee: 8,
              v: 10,
              crbt: null,
              cf: "",
              al: {
                id: 89633771,
                name: "RamLife1",
                picUrl:
                  "http://p4.music.126.net/UyUrqSp-GzCsqWgNm4F44Q==/109951165005286070.jpg",
                tns: [],
                pic_str: "109951165005286070",
                pic: 109951165005286060,
              },
              dt: 192000,
              h: {
                br: 320002,
                fid: 0,
                size: 7682133,
                vd: -50533,
                sr: 44100,
              },
              m: {
                br: 192002,
                fid: 0,
                size: 4609297,
                vd: -47899,
                sr: 44100,
              },
              l: {
                br: 128002,
                fid: 0,
                size: 3072879,
                vd: -46108,
                sr: 44100,
              },
              sq: {
                br: 1460378,
                fid: 0,
                size: 35049075,
                vd: -50533,
                sr: 44100,
              },
              hr: null,
              a: null,
              cd: "01",
              no: 1,
              rtUrl: null,
              ftype: 0,
              rtUrls: [],
              djId: 0,
              copyright: 0,
              s_id: 0,
              mark: 64,
              originCoverType: 0,
              originSongSimpleData: null,
              tagPicList: null,
              resourceState: true,
              version: 10,
              songJumpInfo: null,
              entertainmentTags: null,
              single: 0,
              noCopyrightRcmd: null,
              mst: 9,
              cp: 0,
              mv: 0,
              rtype: 0,
              rurl: null,
              publishTime: 0,
            },
            {
              name: "拉过勾的·2023",
              id: 1948277210,
              pst: 0,
              t: 0,
              ar: [
                {
                  id: 35309151,
                  name: "粉太狼",
                  tns: [],
                  alias: [],
                },
              ],
              alia: [],
              pop: 100,
              st: 0,
              rt: "",
              fee: 0,
              v: 18,
              crbt: null,
              cf: "",
              al: {
                id: 145127994,
                name: "拉过勾的",
                picUrl:
                  "http://p3.music.126.net/L5W4YnQZpLf7hFKEf_yO-g==/109951167475076166.jpg",
                tns: [],
                pic_str: "109951167475076166",
                pic: 109951167475076160,
              },
              dt: 143823,
              h: {
                br: 320000,
                fid: 0,
                size: 5755342,
                vd: -48816,
                sr: 44100,
              },
              m: {
                br: 192000,
                fid: 0,
                size: 3453222,
                vd: -46185,
                sr: 44100,
              },
              l: {
                br: 128000,
                fid: 0,
                size: 2302163,
                vd: -44498,
                sr: 44100,
              },
              sq: {
                br: 893794,
                fid: 0,
                size: 16068583,
                vd: -48809,
                sr: 44100,
              },
              hr: null,
              a: null,
              cd: "01",
              no: 0,
              rtUrl: null,
              ftype: 0,
              rtUrls: [],
              djId: 0,
              copyright: 0,
              s_id: 0,
              mark: 0,
              originCoverType: 2,
              originSongSimpleData: {
                songId: 26387325,
                name: "拉过勾的",
                artists: [
                  {
                    id: 3793,
                    name: "陆虎",
                  },
                ],
                albumMeta: {
                  id: 2453010,
                  name: "花吃泡面男",
                },
              },
              tagPicList: null,
              resourceState: true,
              version: 18,
              songJumpInfo: null,
              entertainmentTags: null,
              single: 0,
              noCopyrightRcmd: null,
              mst: 9,
              cp: 0,
              mv: 0,
              rtype: 0,
              rurl: null,
              publishTime: 1652716800000,
              tns: ["想在你身边"],
            },
            {
              name: "喜欢你",
              id: 2021178004,
              pst: 0,
              t: 0,
              ar: [
                {
                  id: 36032190,
                  name: "yihuik苡慧",
                  tns: [],
                  alias: [],
                },
              ],
              alia: [],
              pop: 100,
              st: 0,
              rt: "",
              fee: 8,
              v: 4,
              crbt: null,
              cf: "",
              al: {
                id: 159780907,
                name: "喜欢你（心动版）",
                picUrl:
                  "http://p4.music.126.net/TQmVlc8WptVrUfeZkVKvxg==/109951168299753302.jpg",
                tns: [],
                pic_str: "109951168299753302",
                pic: 109951168299753300,
              },
              dt: 215294,
              h: {
                br: 320000,
                fid: 0,
                size: 8614125,
                vd: -25520,
                sr: 48000,
              },
              m: {
                br: 192000,
                fid: 0,
                size: 5168493,
                vd: -22915,
                sr: 48000,
              },
              l: {
                br: 128000,
                fid: 0,
                size: 3445677,
                vd: -21210,
                sr: 48000,
              },
              sq: {
                br: 874986,
                fid: 0,
                size: 23547425,
                vd: -25165,
                sr: 48000,
              },
              hr: {
                br: 1644096,
                fid: 0,
                size: 44245537,
                vd: -25520,
                sr: 48000,
              },
              a: null,
              cd: "01",
              no: 1,
              rtUrl: null,
              ftype: 0,
              rtUrls: [],
              djId: 0,
              copyright: 0,
              s_id: 0,
              mark: 536870912,
              originCoverType: 2,
              originSongSimpleData: {
                songId: 210674,
                name: "喜欢你",
                artists: [
                  {
                    id: 7439,
                    name: "陈洁仪",
                  },
                ],
                albumMeta: {
                  id: 21388,
                  name: "陈洁仪的异想世界",
                },
              },
              tagPicList: null,
              resourceState: true,
              version: 4,
              songJumpInfo: null,
              entertainmentTags: null,
              single: 0,
              noCopyrightRcmd: null,
              mst: 9,
              cp: 0,
              mv: 0,
              rtype: 0,
              rurl: null,
              publishTime: 1676044800000,
              tns: ["心动版"],
            },
            {
              name: "就让这大雨全都落下",
              id: 1984475097,
              pst: 0,
              t: 0,
              ar: [
                {
                  id: 9269,
                  name: "容祖儿",
                  tns: [],
                  alias: [],
                },
              ],
              alia: ["汪苏泷概念创作集《联名》作品"],
              pop: 100,
              st: 0,
              rt: "",
              fee: 1,
              v: 6,
              crbt: null,
              cf: "",
              al: {
                id: 152222837,
                name: "联名",
                picUrl:
                  "http://p4.music.126.net/8z_iZXGnImAXLFAFTbu0iw==/109951168296440959.jpg",
                tns: [],
                pic_str: "109951168296440959",
                pic: 109951168296440960,
              },
              dt: 254117,
              h: {
                br: 320001,
                fid: 0,
                size: 10167405,
                vd: -53782,
                sr: 48000,
              },
              m: {
                br: 192001,
                fid: 0,
                size: 6100461,
                vd: -51222,
                sr: 48000,
              },
              l: {
                br: 128001,
                fid: 0,
                size: 4066989,
                vd: -49607,
                sr: 48000,
              },
              sq: {
                br: 972276,
                fid: 0,
                size: 30884068,
                vd: -55305,
                sr: 48000,
              },
              hr: null,
              a: null,
              cd: "01",
              no: 1,
              rtUrl: null,
              ftype: 0,
              rtUrls: [],
              djId: 0,
              copyright: 0,
              s_id: 0,
              mark: 8192,
              originCoverType: 0,
              originSongSimpleData: null,
              tagPicList: null,
              resourceState: true,
              version: 6,
              songJumpInfo: null,
              entertainmentTags: null,
              single: 0,
              noCopyrightRcmd: null,
              mst: 9,
              cp: 0,
              mv: 0,
              rtype: 0,
              rurl: null,
              publishTime: 1663862400000,
            },
            {
              name: "伴你去经历",
              id: 2021082769,
              pst: 0,
              t: 0,
              ar: [
                {
                  id: 1050282,
                  name: "房东的猫",
                  tns: [],
                  alias: [],
                },
              ],
              alia: ["乐而雅品牌主题曲"],
              pop: 100,
              st: 0,
              rt: "",
              fee: 8,
              v: 6,
              crbt: null,
              cf: "",
              al: {
                id: 159761086,
                name: "伴你去经历",
                picUrl:
                  "http://p3.music.126.net/LP50VrmCmt708JEALcc-sg==/109951168299442267.jpg",
                tns: [],
                pic_str: "109951168299442267",
                pic: 109951168299442270,
              },
              dt: 179142,
              h: {
                br: 320002,
                fid: 0,
                size: 7168365,
                vd: -48414,
                sr: 48000,
              },
              m: {
                br: 192002,
                fid: 0,
                size: 4301037,
                vd: -45788,
                sr: 48000,
              },
              l: {
                br: 128002,
                fid: 0,
                size: 2867373,
                vd: -44064,
                sr: 48000,
              },
              sq: {
                br: 949878,
                fid: 0,
                size: 21270495,
                vd: -48453,
                sr: 48000,
              },
              hr: {
                br: 2918190,
                fid: 0,
                size: 65346625,
                vd: -48475,
                sr: 96000,
              },
              a: null,
              cd: "01",
              no: 1,
              rtUrl: null,
              ftype: 0,
              rtUrls: [],
              djId: 0,
              copyright: 0,
              s_id: 0,
              mark: 536879104,
              originCoverType: 0,
              originSongSimpleData: null,
              tagPicList: null,
              resourceState: true,
              version: 6,
              songJumpInfo: null,
              entertainmentTags: null,
              single: 0,
              noCopyrightRcmd: null,
              mst: 9,
              cp: 7001,
              mv: 0,
              rtype: 0,
              rurl: null,
              publishTime: 0,
            },
            {
              name: "若把你",
              id: 1840700016,
              pst: 0,
              t: 0,
              ar: [
                {
                  id: 34201495,
                  name: "留小雨",
                  tns: [],
                  alias: [],
                },
              ],
              alia: [],
              pop: 100,
              st: 0,
              rt: "",
              fee: 8,
              v: 4,
              crbt: null,
              cf: "",
              al: {
                id: 126631526,
                name: "若把你",
                picUrl:
                  "http://p4.music.126.net/PyTjq22uOegIOGxoJ_T_iQ==/109951165928680369.jpg",
                tns: [],
                pic_str: "109951165928680369",
                pic: 109951165928680370,
              },
              dt: 199128,
              h: null,
              m: null,
              l: {
                br: 128002,
                fid: 0,
                size: 3186477,
                vd: -33079,
                sr: 48000,
              },
              sq: null,
              hr: null,
              a: null,
              cd: "01",
              no: 0,
              rtUrl: null,
              ftype: 0,
              rtUrls: [],
              djId: 0,
              copyright: 0,
              s_id: 0,
              mark: 0,
              originCoverType: 2,
              originSongSimpleData: {
                songId: 865632948,
                name: "若把你",
                artists: [
                  {
                    id: 12499721,
                    name: "Kirsty刘瑾睿",
                  },
                ],
                albumMeta: {
                  id: 71783637,
                  name: "若把你",
                },
              },
              tagPicList: null,
              resourceState: true,
              version: 4,
              songJumpInfo: null,
              entertainmentTags: null,
              single: 0,
              noCopyrightRcmd: null,
              mst: 9,
              cp: 0,
              mv: 0,
              rtype: 0,
              rurl: null,
              publishTime: 0,
            },
          ],
        },
        privileges: [
          {
            id: 1997438791,
            fee: 8,
            payed: 0,
            realPayed: 0,
            st: 0,
            pl: 128000,
            dl: 0,
            sp: 7,
            cp: 1,
            subp: 1,
            cs: false,
            maxbr: 999000,
            fl: 128000,
            pc: null,
            toast: false,
            flag: 4,
            paidBigBang: false,
            preSell: false,
            playMaxbr: 999000,
            downloadMaxbr: 999000,
            maxBrLevel: "hires",
            playMaxBrLevel: "hires",
            downloadMaxBrLevel: "hires",
            plLevel: "standard",
            dlLevel: "none",
            flLevel: "standard",
            rscl: null,
            freeTrialPrivilege: {
              resConsumable: false,
              userConsumable: false,
              listenType: null,
            },
            chargeInfoList: [
              {
                rate: 128000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 0,
              },
              {
                rate: 192000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
              {
                rate: 320000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
              {
                rate: 999000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
              {
                rate: 1999000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
            ],
          },
          {
            id: 1477144603,
            fee: 8,
            payed: 0,
            realPayed: 0,
            st: 0,
            pl: 128000,
            dl: 0,
            sp: 7,
            cp: 1,
            subp: 1,
            cs: false,
            maxbr: 999000,
            fl: 128000,
            pc: null,
            toast: false,
            flag: 2,
            paidBigBang: false,
            preSell: false,
            playMaxbr: 999000,
            downloadMaxbr: 999000,
            maxBrLevel: "lossless",
            playMaxBrLevel: "lossless",
            downloadMaxBrLevel: "lossless",
            plLevel: "standard",
            dlLevel: "none",
            flLevel: "standard",
            rscl: null,
            freeTrialPrivilege: {
              resConsumable: false,
              userConsumable: false,
              listenType: null,
            },
            chargeInfoList: [
              {
                rate: 128000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 0,
              },
              {
                rate: 192000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
              {
                rate: 320000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
              {
                rate: 999000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
            ],
          },
          {
            id: 1948277210,
            fee: 0,
            payed: 0,
            realPayed: 0,
            st: 0,
            pl: 320000,
            dl: 999000,
            sp: 7,
            cp: 1,
            subp: 1,
            cs: false,
            maxbr: 999000,
            fl: 320000,
            pc: null,
            toast: false,
            flag: 0,
            paidBigBang: false,
            preSell: false,
            playMaxbr: 999000,
            downloadMaxbr: 999000,
            maxBrLevel: "lossless",
            playMaxBrLevel: "lossless",
            downloadMaxBrLevel: "lossless",
            plLevel: "exhigh",
            dlLevel: "lossless",
            flLevel: "exhigh",
            rscl: null,
            freeTrialPrivilege: {
              resConsumable: false,
              userConsumable: false,
              listenType: null,
            },
            chargeInfoList: [
              {
                rate: 128000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 0,
              },
              {
                rate: 192000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 0,
              },
              {
                rate: 320000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 0,
              },
              {
                rate: 999000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
            ],
          },
          {
            id: 2021178004,
            fee: 8,
            payed: 0,
            realPayed: 0,
            st: 0,
            pl: 128000,
            dl: 0,
            sp: 7,
            cp: 1,
            subp: 1,
            cs: false,
            maxbr: 999000,
            fl: 128000,
            pc: null,
            toast: false,
            flag: 0,
            paidBigBang: false,
            preSell: false,
            playMaxbr: 999000,
            downloadMaxbr: 999000,
            maxBrLevel: "hires",
            playMaxBrLevel: "hires",
            downloadMaxBrLevel: "hires",
            plLevel: "standard",
            dlLevel: "none",
            flLevel: "standard",
            rscl: null,
            freeTrialPrivilege: {
              resConsumable: false,
              userConsumable: false,
              listenType: null,
            },
            chargeInfoList: [
              {
                rate: 128000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 0,
              },
              {
                rate: 192000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
              {
                rate: 320000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
              {
                rate: 999000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
              {
                rate: 1999000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
            ],
          },
          {
            id: 1984475097,
            fee: 1,
            payed: 0,
            realPayed: 0,
            st: 0,
            pl: 0,
            dl: 0,
            sp: 7,
            cp: 1,
            subp: 1,
            cs: false,
            maxbr: 999000,
            fl: 0,
            pc: null,
            toast: false,
            flag: 1028,
            paidBigBang: false,
            preSell: false,
            playMaxbr: 999000,
            downloadMaxbr: 999000,
            maxBrLevel: "lossless",
            playMaxBrLevel: "lossless",
            downloadMaxBrLevel: "lossless",
            plLevel: "none",
            dlLevel: "none",
            flLevel: "none",
            rscl: null,
            freeTrialPrivilege: {
              resConsumable: false,
              userConsumable: false,
              listenType: null,
            },
            chargeInfoList: [
              {
                rate: 128000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
              {
                rate: 192000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
              {
                rate: 320000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
              {
                rate: 999000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
            ],
          },
          {
            id: 2021082769,
            fee: 8,
            payed: 0,
            realPayed: 0,
            st: 0,
            pl: 128000,
            dl: 0,
            sp: 7,
            cp: 1,
            subp: 1,
            cs: false,
            maxbr: 999000,
            fl: 128000,
            pc: null,
            toast: false,
            flag: 4,
            paidBigBang: false,
            preSell: false,
            playMaxbr: 999000,
            downloadMaxbr: 999000,
            maxBrLevel: "hires",
            playMaxBrLevel: "hires",
            downloadMaxBrLevel: "hires",
            plLevel: "standard",
            dlLevel: "none",
            flLevel: "standard",
            rscl: null,
            freeTrialPrivilege: {
              resConsumable: false,
              userConsumable: false,
              listenType: null,
            },
            chargeInfoList: [
              {
                rate: 128000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 0,
              },
              {
                rate: 192000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
              {
                rate: 320000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
              {
                rate: 999000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
              {
                rate: 1999000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
            ],
          },
          {
            id: 1840700016,
            fee: 8,
            payed: 0,
            realPayed: 0,
            st: 0,
            pl: 128000,
            dl: 0,
            sp: 7,
            cp: 1,
            subp: 1,
            cs: false,
            maxbr: 128000,
            fl: 128000,
            pc: null,
            toast: false,
            flag: 0,
            paidBigBang: false,
            preSell: false,
            playMaxbr: 128000,
            downloadMaxbr: 128000,
            maxBrLevel: "standard",
            playMaxBrLevel: "standard",
            downloadMaxBrLevel: "standard",
            plLevel: "standard",
            dlLevel: "none",
            flLevel: "standard",
            rscl: null,
            freeTrialPrivilege: {
              resConsumable: false,
              userConsumable: false,
              listenType: null,
            },
            chargeInfoList: [
              {
                rate: 128000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 0,
              },
              {
                rate: 192000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
              {
                rate: 320000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
              {
                rate: 999000,
                chargeUrl: null,
                chargeMessage: null,
                chargeType: 1,
              },
            ],
          },
        ],
      };

      songList.value[item.id] = proxy.$utils.formatSongs(
        detailData.playlist.tracks.splice(0, listSize),
        detailData.privileges.splice(0, listSize)
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
