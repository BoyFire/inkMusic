import {defineConfig} from 'vite'
import {resolve} from "path";
import vue from '@vitejs/plugin-vue'

function pathResolve(dir) {
  return resolve(__dirname, ".", dir);
  
}


// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': pathResolve('src'),
      '@views': pathResolve('src/views'),
      '@components': pathResolve('src/components'),
      '@api': pathResolve('src/api'),
      '@styles': pathResolve('src/styles'),
      '@utils': pathResolve('src/utils')
    }
  },
  server:{
    open: true,
  }
  
})
