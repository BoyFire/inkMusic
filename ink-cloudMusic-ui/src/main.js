import * as getApi from "@api/http";
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
import store from "@store/index";
import util from "@utils/util";
import ElementPlus, { ElMessage } from "element-plus";
import "element-plus/dist/index.css";
import zhCn from "element-plus/dist/locale/zh-cn.mjs";
import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";

import "@assets/css/global.css";
import "@assets/fonts/fonts.css";
import "@assets/less/reset.less";

const app = createApp(App);
app.use(ElMessage);

app.config.globalProperties.$utils = util;
app.config.globalProperties.$http = getApi;
app.config.globalProperties.$msg = ElMessage;

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}
app.use(router).use(store).use(ElementPlus, { locale: zhCn });

app.mount("#app");
