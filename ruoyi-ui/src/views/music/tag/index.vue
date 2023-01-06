<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="标签名" prop="tagName">
        <el-input
          v-model="queryParams.tagName"
          placeholder="请输入标签id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="标签状态" prop="tagStatus">        
        <el-select 
          v-model="queryParams.tagStatusStatus"
          placeholder="请选择标签状态" 
          clearable 
          @keyup.enter.native="handleQuery"
        >
        <el-option v-for="(item, index) in tagStatusOptions" 
          :key="index" :label="item.label"
          :value="item.value" 
          :disabled="item.disabled"/>
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
          v-hasPermi="['music:tag:add']"
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
          v-hasPermi="['music:tag:edit']"
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
          v-hasPermi="['music:tag:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['music:tag:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table   
      v-if="refreshTable"   
      v-loading="loading"
      :data="tagList"
      row-key="tagId"      
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="标签名" align="center" :show-overflow-tooltip="true" prop="tagName" />
      <el-table-column label="标签状态" align="center" prop="tagStatus" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.tagStatus"
            :active-value=1
            :inactive-value=0
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['music:tag:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:menu:add']"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['music:tag:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改标签对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标签名" prop="tagName">
          <el-input v-model="form.tagName" placeholder="请输入标签名" />
        </el-form-item>

        <el-form-item label="上级标签" prop="tagParentsId">
          <treeselect
                v-model="form.tagParentsId"
                :options="tagOptions"
                :normalizer="normalizer"
                :show-count="true"
                placeholder="选择上级标签"
              />
        </el-form-item>

        <el-form-item label="状态" prop="tagStatus">
          <el-switch
            v-model="form.tagStatus"
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
import { listTag, getTag, delTag, addTag, updateTag ,treeselect} from "@/api/music/tag";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Tag",
  components: { Treeselect },
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
      // 标签表格数据树
      tagList: [],
      // 标签树选项
      tagOptions: [],
      // 重新渲染表格状态
      refreshTable: true,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        tagId: null,
        tagName: null,
        tagParentsId: null,
        tagStatus: null,
        editBy: null,
        editTime: null,
        revision: null,
        isDel: null
      },
      // 标签状态
      tagStatusOptions:[{
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
        tagName: [
          { required: true, message: "标签名称不能为空", trigger: "blur" }
        ],
        tagParentsId: [
          {required:true , message: "上级标签不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询标签列表 */
    getList() {
      this.loading = true;
      treeselect(this.queryParams).then(response => {
        this.tagList =response.data;
        this.loading = false;
      });
    },
    /** 转换菜单数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.tagId,
        label: node.tagName,
        children: node.children
      };
    },
    /** 查询菜单下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.tagOptions = [];
        const menu = { tagId: 0, tagName: '主类目', children: [] };
        menu.children = response.data;
        this.tagOptions.push(menu);
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
        tagId: null,
        tagName: null,
        tagParentsId: null,
        tagStatus: 0,
        createBy: null,
        createTime: null,
        editBy: null,
        editTime: null,
        revision: null,
        isDel: null
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
      this.ids = selection.map(item => item.tagId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.tagId) {
        this.form.tagParentsId = row.tagId;
      } else {
        this.form.tagParentsId = 0;
      }
      this.open = true;
      this.title = "添加标签";
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      getTag(row.mtagId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改标签";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTag(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTag(this.form).then(response => {
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
      const ids = row.tagId || this.ids;
      console.log(ids);
      this.$modal.confirm('是否确认删除标签吗？').then(function() {
        return delTag(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('music/tag/export', {
        ...this.queryParams
      }, `tag_${new Date().getTime()}.xlsx`)
    },
    /** 修改标签状态 */
    handleStatusChange(row){
      let text = row.tagStatus === 1 ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.tagName + '"吗？').then(function() {
        return updateTag(row);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.tagStatus = row.tagStatus === 0 ? 1 : 0;
      });
    },
  }
};
</script>
