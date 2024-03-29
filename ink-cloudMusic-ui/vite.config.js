import vue from "@vitejs/plugin-vue";
import { resolve } from "path";
import { defineConfig } from "vite";

function pathResolve(dir) {
  return resolve(__dirname, ".", dir);
}

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": pathResolve("src"),
      "@views": pathResolve("src/views"),
      "@components": pathResolve("src/components"),
      "@api": pathResolve("src/api"),
      "@styles": pathResolve("src/styles"),
      "@utils": pathResolve("src/utils"),
      "@router": pathResolve("src/router"),
      "@assets": pathResolve("src/assets"),
      "@store": pathResolve("src/store"),
    },
  },
  css: {
    preprocessorOptions: {
      less: {
        modifyVars: {
          hack: `true; @import (reference) "${resolve(
            "src/assets/less/main.less"
          )}";`,
        },
        javascriptEnabled: true,
      },
    },
  },
  server: {
    open: true,
    http: true,
    proxy: {
      "/api": {
        target: "http://localhost:3000",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ""),
      },
      "/music": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
    },
  },
});
