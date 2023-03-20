import {createRouter, createWebHistory} from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: "/", redirect: "/index" },
    {
      path: "/index",
      name: "index",
      component: () => import("@views/music/home/index.vue"),
    },
    {
      path: "/playlist",
      name: "playlist",
      component: () => import("@views/music/songList/index.vue"),
    },
    {
      path: "/playlist/detail",
      name: "playlistdetail",
      component: () => import("@views/music/songList/Detail.vue"),
    },
    {
      path: "/rank",
      name: "rank",
      component: () => import("@views/music/rank/index.vue"),
    },
    {
      path: "/song",
      name: "song",
      component: () => import("@views/music/song/index.vue"),
    },
    {
      path: "/artist/detail",
      name: "artistdetail",
      component: () => import("@views/music/singer/Detail.vue"),
    },
    {
      path: "/artist",
      name: "artist",
      component: () => import("@views/music/singer/index.vue"),
    },
    {
      path: "/album",
      name: "album",
      component: () => import("@views/music/album/index.vue"),
    },
    {
      path: "/mvlist",
      name: "mvlist",
      component: () => import("@views/music/mv/index.vue"),
    },
    {
      path: "/mvlist/mv",
      name: "mv",
      component: () => import("@views/music/mv/Detail.vue"),
    },
    {
      path: "/search",
      name: "search",
      component: () => import("@views/music/search/index.vue"),
    },
    {
      path: "/user",
      name: "user",
      component: () => import("@views/music/user/index.vue"),
    },
  ],
});

export default router;
