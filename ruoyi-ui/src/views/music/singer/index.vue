<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="歌手id" prop="singerId">
        <el-input
          v-model="queryParams.singerId"
          placeholder="请输入歌手id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="歌手姓名" prop="singerName">
        <el-input
          v-model="queryParams.singerName"
          placeholder="请输入歌手姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
     <el-form-item label="歌手类型" prop="singerType">
      <el-select 
        v-model="queryParams.singerType"
        placeholder="请选择歌手类型"
        @keyup.enter.native="handleQuery"
        clearable
      >
        <el-option v-for="(item,index) in singerTypeOptions"
        :key="index" :value="item.value" :label="item.label"/>
      </el-select>
     </el-form-item>
     <el-form-item label="国籍" prop="singerCountry">
      <el-select 
        v-model="queryParams.singerCountry"
        placeholder="请选择歌手国籍"
        @keyup.enter.native="handleQuery"
        clearable
      >
        <el-option v-for="(item,index) in singerCountryOptions"
        :key="index" :value="item.value" :label="item.label"/>
      </el-select>
     </el-form-item>
     <el-form-item label="歌手状态" prop="singerStatus">
      <el-select 
        v-model="queryParams.singerStatus"
        placeholder="请选择歌手状态"
        @keyup.enter.native="handleQuery"
        clearable
      >
        <el-option v-for="(item,index) in singerStatusOptions"
        :key="index" :value="item.value" :label="item.label"/>
      </el-select>
     </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['music:singer:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['music:singer:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['music:singer:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['music:singer:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="singerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="歌手id" align="center" prop="singerId" />
      <el-table-column label="歌手姓名" align="center" prop="singerName" />
      <el-table-column label="歌手头像" align="center" prop="singerImgUrl" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.singerImgUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="歌手状态" align="center" key="singerStatus" prop="singerStatus" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.singerStatus"
            :active-value=1
            :inactive-value=0
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="歌手类型" align="center" prop="singerType" :formatter="singerTypeFormatter"/>
      <el-table-column label="歌手国籍" align="center" prop="singerCountry" :formatter="singerCountryFormatter" />
      <el-table-column label="更新者" align="center" prop="editBy" />
      <el-table-column label="更新时间" align="center" prop="editTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.editTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['music:singer:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['music:singer:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改歌手对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="歌手姓名" prop="singerName">
          <el-input v-model="form.singerName" placeholder="请输入歌手姓名" />
        </el-form-item>
        <el-form-item label="歌手头像">
          <image-upload v-model="form.singerImgUrl"/>
        </el-form-item>
        <el-form-item label="歌手详情" prop="singerDetail">
          <el-input v-model="form.singerDetail" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label ="歌手类型" prop="singerType">
          <el-radio-group v-model="form.singerType">
            <el-radio  :label=1>男</el-radio>
            <el-radio  :label=2>女</el-radio>
            <el-radio  :label=3>乐团</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label ="歌手国籍" prop="singerCountry">
          <el-radio-group v-model="form.singerCountry">
            <el-radio  :label=1>华语</el-radio>
            <el-radio  :label=2>欧美</el-radio>
            <el-radio  :label=3>日本</el-radio>
            <el-radio  :label=4>韩国</el-radio>
            <el-radio  :label=0>其他</el-radio>
          </el-radio-group>          
        </el-form-item>
        <el-form-item label="歌手状态" prop="singerStatus">
          <el-switch
            v-model="form.singerStatus"
            :active-value=1
            :inactive-value=0
            active-text="启用"
            inactive-text="停用"
          ></el-switch>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSinger, getSinger, delSinger, addSinger, updateSinger } from "@/api/music/singer";

export default {
  name: "Singer",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 歌手表格数据
      singerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        singerId: null,
        singerName: null,
        singerImgUrl: null,
        singerDetail: null,
        singerType: null,
        singerStatus:null,
        singerCountry: null,
        editBy: null,
        editTime: null,
        revision: null,
        isDel: null
      },
      //歌手类型选项
      singerTypeOptions:[
        {
          'label':'男',
          "value":1
        },{
          'label':'女',
          "value":2
        },{
          'label':'乐团',
          "value":3
        },{
          'label':'未知',
          "value":0
        },
      ],
      //歌手国籍
      singerCountryOptions:[
           {
          'label':'华语',
          "value":1
        },{
          'label':'欧美',
          "value":2
        },{
          'label':'日本',
          "value":3
        },{
          'label':'韩国',
          "value":4
        },{
          'label':'其他',
          "value":0
        },
      ],
      //歌手状态
      singerStatusOptions: [{
        "label": "启用",
        "value": 1
      }, {
        "label": "停用",
        "value": 0
      }],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        singerName: [
          { required: true, message: "歌手姓名不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询歌手列表 */
    getList() {
      this.loading = true;
      listSinger(this.queryParams).then(response => {
        this.singerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        singerId: null,
        singerName: null,
        singerImgUrl: null,
        singerDetail: null,
        singerType: null,
        singerStatus:null,
        singerCountry: null,
        createBy: null,
        createTime: null,
        editBy: null,
        editTime: null,
        revision: null,
        isDel: 0
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加歌手";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSinger(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改歌手";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSinger(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSinger(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除歌手姓名为"' + row.singerName + '"的数据项？').then(function() {
        return delSinger(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('music/singer/export', {
        ...this.queryParams
      }, `singer_${new Date().getTime()}.xlsx`)
    },
    //歌手类型转换
    singerTypeFormatter(row){
        let text = row.singerType;
        switch (text) {
          case 1: return "男";
          case 2: return "女";
          case 3: return "乐团";
          default: return "未知";
        }
    },
    //歌手国家转换
    singerCountryFormatter(row){
        let text = row.singerCountry;
        switch (text) {
          case 1: return "华语";
          case 2: return "欧美";
          case 3: return "日本";
          case 4: return "韩国";
          default: return "其他";
        }
    },
    //修改歌手状态
    /** 修改用户状态 */
    handleStatusChange(row){      
      let text = row.singerStatus === 1 ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.singerName + '"吗？').then(function() {
        return updateSinger(row);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.singerStatus = row.singerStatus === 0 ? 1 : 0;
      });
    },
  }
};
</script>
