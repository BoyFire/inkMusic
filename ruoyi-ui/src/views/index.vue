<template>
  <div class="app-container home">

    <panel-group @handleSetLineChartData="handleSetLineChartData" />

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData" />
    </el-row>
    
  </div>
</template>

<script>
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'

const lineChartData = {
  newVisitis: {
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145]
  },
  messages: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
}

export default {
  name: "Index",
  components:{
    PanelGroup,
    LineChart,
  },
  mounted(){
    
  },
  data() {
    return {
      // 版本号
      version: "3.6.1",
       lineChartData: lineChartData.newVisitis
    };
  },   
  methods: {    
     handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type]
    }  
  },
};
</script>

<style scoped lang="scss">
.home {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;
  
}
</style>

