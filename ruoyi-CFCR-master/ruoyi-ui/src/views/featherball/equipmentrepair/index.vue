<template>
  <div>
    <!-- 商品维修管理页面内容 -->
    <div v-if="activeTab === 'equipmentRepair'">
      <div class="app-container">
        <!-- 添加维修商品按钮 -->
        <el-row :gutter="20" class="mb-20" style="margin-bottom: 20px;">
          <el-col>
            <el-button type="primary" @click="handleAddEquipmentRepair" v-hasPermi="['featherball:rep:add']">新增维修商品</el-button>
          </el-col>
        </el-row>

        <!-- 维修商品列表 -->
        <el-table :data="equipmentRepairList" v-loading="loading" style="width: 100%" border>
          <el-table-column label="维修商品ID" prop="equipmentRepairId" align="center"></el-table-column>
          <el-table-column label="场馆名称" prop="venueName" align="center"></el-table-column>
          <el-table-column label="维修商品类别" align="center">
            <template slot-scope="scope">
              {{ formatEquipmentType(scope.row.equipmentRepairType) }}
            </template>
          </el-table-column>
          <el-table-column label="维修单价" prop="equipmentRepairFee" align="center"></el-table-column>
          <el-table-column label="操作" align="center" width="280px">
            <template slot-scope="scope">
              <el-button type="success" size="mini" @click="handleRepair(scope.row)" v-hasPermi="['featherball:rep:rep']">维修</el-button>
              <el-button type="primary" size="mini" @click="handleEdit(scope.row)" v-hasPermi="['featherball:rep:edit']">修改</el-button>
              <el-button type="danger" size="mini" @click="confirmDelete(scope.row)" v-hasPermi="['featherball:rep:delete']">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 分页组件 -->
        <pagination
          v-show="totalEquipmentRepairs > 0"
          :total="totalEquipmentRepairs"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="fetchEquipmentRepairs"
        />

        <!-- 添加维修商品对话框 -->
        <el-dialog :visible.sync="dialogVisible" :title="dialogTitle" width="30%" @close="handleCloseDialog">
          <!-- 对话框内容 -->
          <div>
            <el-form :model="equipmentForm" label-width="100px">
              <!-- 场馆名称下拉选择 -->
              <el-form-item label="场馆名称">
                <el-select v-model="equipmentForm.venueId" placeholder="请选择场馆" :disabled="isReadOnly">
                  <el-option
                    v-for="venue in venueOptions"
                    :key="venue.venueId"
                    :label="venue.venueName"
                    :value="venue.venueId"
                  ></el-option>
                </el-select>
              </el-form-item>
              <!-- 维修商品类别 -->
              <el-form-item label="维修商品类别">
                <el-select v-model="equipmentForm.equipmentRepairType">
                  <el-option label="球拍" :value="0"></el-option>
                  <el-option label="球网" :value="1"></el-option>
                  <el-option label="球鞋" :value="2"></el-option>
                  <!-- 其他选项 -->
                </el-select>
              </el-form-item>
              <!-- 维修商品单价 -->
              <el-form-item label="维修商品单价">
                <el-input v-model="equipmentForm.equipmentRepairFee"></el-input>
              </el-form-item>
            </el-form>
          </div>
          <!-- 对话框按钮 -->
          <div slot="footer" class="dialog-footer" style="text-align: center;">
            <el-button @click="handleCloseDialog">取消</el-button>
            <el-button type="primary" @click="handleSubmit">{{ dialogButtonText }}</el-button>
          </div>
        </el-dialog>

      </div>
    </div>
  </div>
</template>

<script>
import { listEquipmentRepairs, addEquipmentRepair, updateEquipmentRepair, deleteEquipmentRepair } from '@/api/featherball/equipmentRepair'
import { MessageBox } from 'element-ui';
import {listVenues} from "@/api/featherball/venue";
import {addBalanceRecord} from "@/api/featherball/balanceRecord";

export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      activeTab: 'equipmentRepair', // 当前激活的选项卡，默认为商品维修管理
      equipmentRepairList: [], // 维修商品列表数据
      venueOptions:[],
      dialogTitle: '', // 对话框标题
      dialogButtonText: '', // 对话框按钮文本
      // 总条数
      totalEquipmentRepairs: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      dialogVisible: false, // 控制添加维修商品对话框的显示与隐藏
      equipmentForm: { // 新增维修商品表单
        venueId: '',
        equipmentRepairType: '',
        equipmentRepairFee: ''
      }
    }
  },
  created() {
    // 在页面加载时获取维修商品列表
    this.fetchEquipmentRepairs()
    this.fetchVenues()
  },
  computed: {
    // 计算属性：将商品类别数字转换为对应的汉字
    formatEquipmentType() {
      return function (type) {
        switch (type) {
          case 0:
            return '球拍';
          case 1:
            return '球网';
          case 2:
            return '球鞋';
          default:
            return '';
        }
      }
    }
  },
  methods: {
    // 获取维修商品列表
    fetchEquipmentRepairs() {
      this.loading = true
      listEquipmentRepairs(this.queryParams).then(response => {
        this.equipmentRepairList = response.rows
        this.totalEquipmentRepairs = response.total
        this.loading = false
      })
    },

    // 获取场馆列表
    fetchVenues() {
      listVenues().then(response => {
        // 将后端返回的场馆列表赋值给 venueOptions
        this.venueOptions = response.rows.map(venue => ({
          venueId: venue.venueId,
          venueName: venue.venueName
        }));
      });
    },

    // 清空表单数据
    clearForm() {
      this.equipmentForm = {
        venueId: '',
        equipmentRepairType: '',
        equipmentRepairFee: ''
      }
    },

    // 提交表单
    handleSubmit() {
      if (this.dialogButtonText === '更新') {
        // 调用更新场地的方法
        this.updateEquipmentRepair()
      } else if (this.dialogButtonText === "添加") {
        // 调用添加场地的方法
        this.addEquipmentRepair()
      } else {
        this.handleCloseDialog()
      }
    },

    // 添加维修商品
    handleAddEquipmentRepair() {
      this.dialogVisible = true // 打开对话框
      this.dialogTitle = "新增维修配件"
      this.dialogButtonText = "添加"
    },

    // 添加维修商品
    addEquipmentRepair() {
      addEquipmentRepair(this.equipmentForm).then(response => {
        // 处理添加维修商品成功的情况
        // 添加成功后重新获取维修商品列表
        this.fetchEquipmentRepairs()
        this.dialogVisible = false // 关闭对话框
        // 清空表单数据
        this.clearForm()
      })
    },

    // 编辑按钮点击事件
    handleEdit(row) {
      // 将当前要编辑的维修商品的信息赋值给编辑表单
      this.equipmentForm = {
        equipmentRepairId: row.equipmentRepairId,
        venueId: row.venueId,
        equipmentRepairType: row.equipmentRepairType,
        equipmentRepairFee: row.equipmentRepairFee
      };
      // 打开编辑对话框
      this.dialogTitle = '编辑维修配件';
      this.dialogVisible = true;
      this.dialogButtonText = '更新'
    },

    // 更新维修配件
    updateEquipmentRepair() {
      updateEquipmentRepair(this.equipmentForm).then(response => {
        // 处理更新场地成功的情况
        // 更新成功后重新获取场地列表
        this.fetchEquipmentRepairs()
        this.dialogVisible = false // 关闭对话框
        // 清空表单数据
        this.clearForm()
        // 将对话框按钮文本设置为其他值，避免再次触发更新操作
        this.dialogButtonText = '更新成功'
      })
    },

    // 处理购买操作
    handleRepair(row) {
      // 获取购买商品信息
      const { venueId, venueName, equipmentRepairFee } = row;
      // 调用添加订单记录接口
      addBalanceRecord({
        venueId: venueId,
        venueName: venueName,
        transactionAmount: equipmentRepairFee, // 使用维修商品的费用作为交易金额
        transactionType: 2 // 假设维修为交易类型2
      }).then(() => {
        // 添加订单记录成功后的处理逻辑
        this.$message.success('维修成功！');
      });
    },

    // 删除按钮点击事件
    confirmDelete(row) {
      // 弹出确认框，确认删除后调用删除维修商品方法
      MessageBox.confirm('确认删除该维修商品吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteEquipmentRepair(row.equipmentRepairId)
      })
    },

    // 删除维修商品
    deleteEquipmentRepair(equipmentRepairId) {
      deleteEquipmentRepair(equipmentRepairId).then(response => {
        // 处理删除维修商品成功的情况
        // 删除成功后重新获取维修商品列表
        this.fetchEquipmentRepairs()
      })
    },

    // 关闭对话框
    handleCloseDialog() {
      this.dialogVisible = false
      // 清空表单数据
      this.clearForm()
    }
  }
}
</script>
