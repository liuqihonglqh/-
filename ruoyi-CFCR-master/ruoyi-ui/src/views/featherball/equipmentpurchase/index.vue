<template>
  <div>
    <!-- 商品购买管理页面内容 -->
    <div v-if="activeTab === 'equipmentPurchase'">
      <div class="app-container">
        <!-- 添加购买商品按钮 -->
        <el-row :gutter="20" class="mb-20" style="margin-bottom: 20px;">
          <el-col>
            <el-button type="primary" @click="handleAddEquipmentPurchase" v-hasPermi="['featherball:pur:add']">新增购买商品</el-button>
          </el-col>
        </el-row>

        <!-- 购买商品列表 -->
        <el-table :data="equipmentPurchaseList" v-loading="loading" style="width: 100%" border>
          <el-table-column label="购买商品ID" prop="equipmentPurchaseId" align="center"></el-table-column>
          <el-table-column label="场馆名称" prop="venueName" align="center"></el-table-column>
          <el-table-column label="商品类别" align="center">
            <template slot-scope="scope">
              {{ formatEquipmentType(scope.row.equipmentPurchaseType) }}
            </template>
          </el-table-column>
<!--          <el-table-column label="商品个数" prop="equipmentPurchaseNumber" align="center"></el-table-column>-->
          <el-table-column label="商品单价" prop="equipmentPurchaseFee" align="center"></el-table-column>
          <el-table-column label="操作" align="center" width="280px">
            <template slot-scope="scope">
              <el-button type="success" size="mini" @click="handlePurchase(scope.row)" v-hasPermi="['featherball:pur:buy']">购买</el-button>
              <el-button type="primary" size="mini" @click="handleEdit(scope.row)" v-hasPermi="['featherball:pur:edit']">修改</el-button>
              <el-button type="danger" size="mini" @click="confirmDelete(scope.row)" v-hasPermi="['featherball:pur:delete']">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 分页组件 -->
        <pagination
          v-show="totalEquipmentPurchases > 0"
          :total="totalEquipmentPurchases"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="fetchEquipmentPurchases"
        />

        <!-- 添加购买商品对话框 -->
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
              <!-- 商品类别 -->
              <el-form-item label="商品类别">
                <el-select v-model="equipmentForm.equipmentPurchaseType">
                  <el-option label="球拍" :value="0"></el-option>
                  <el-option label="羽毛球" :value="1"></el-option>
                  <el-option label="球鞋" :value="2"></el-option>
                  <el-option label="饮料" :value="3"></el-option>
                  <el-option label="运动服" :value="4"></el-option>
                </el-select>
              </el-form-item>
<!--              &lt;!&ndash; 商品个数 &ndash;&gt;-->
<!--              <el-form-item label="商品个数">-->
<!--                <el-input v-model="equipmentForm.equipmentPurchaseNumber"></el-input>-->
<!--              </el-form-item>-->
              <!-- 商品单价 -->
              <el-form-item label="商品单价">
                <el-input v-model="equipmentForm.equipmentPurchaseFee"></el-input>
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
import { listEquipmentPurchases, addEquipmentPurchase, updateEquipmentPurchase, deleteEquipmentPurchase } from '@/api/featherball/equipmentPurchase'
import { MessageBox } from 'element-ui';
import {listVenues} from "@/api/featherball/venue"
import {addBalanceRecord} from "@/api/featherball/balanceRecord";

export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      activeTab: 'equipmentPurchase', // 当前激活的选项卡，默认为商品购买管理
      equipmentPurchaseList: [], // 购买商品列表数据
      venueOptions: [], // 场馆选项列表
      dialogTitle: '', // 对话框标题
      dialogButtonText: '', // 对话框按钮文本
      // 总条数
      totalEquipmentPurchases: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      dialogVisible: false, // 控制添加购买商品对话框的显示与隐藏
      equipmentForm: { // 新增购买商品表单
        venueId: '',
        equipmentPurchaseType: '',
        equipmentPurchaseNumber: '',
        equipmentPurchaseFee: ''
      }
    }
  },
  computed: {
    // 计算属性：将商品类别数字转换为对应的汉字
    formatEquipmentType() {
      return function (type) {
        switch (type) {
          case 0:
            return '球拍';
          case 1:
            return '羽毛球';
          case 2:
            return '球鞋';
          case 3:
            return '饮料';
          case 4:
            return '运动服';
          default:
            return '';
        }
      }
    }
  },
  created() {
    // 在页面加载时获取购买商品列表
    this.fetchEquipmentPurchases()
    this.fetchVenues()
  },
  methods: {
    // 获取购买商品列表
    fetchEquipmentPurchases() {
      this.loading = false
      listEquipmentPurchases(this.queryParams).then(response => {
        this.equipmentPurchaseList = response.rows
        this.totalEquipmentPurchases = response.total
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
        equipmentPurchaseType: '',
        equipmentPurchaseNumber: '',
        equipmentPurchaseFee: ''
      }
    },

    // 提交表单
    handleSubmit() {
      if (this.dialogButtonText === '更新') {
        // 调用更新场地的方法
        this.updateEquipmentPurchase()
      } else if (this.dialogButtonText === "添加") {
        // 调用添加场地的方法
        this.addEquipmentPurchase()
      } else {
        this.handleCloseDialog()
      }
    },

    // 添加购买商品
    handleAddEquipmentPurchase() {
      this.dialogTitle = "新增购买商品"
      this.dialogButtonText = "添加"
      this.dialogVisible = true // 打开对话框
    },
    // 添加购买商品
    addEquipmentPurchase() {
      addEquipmentPurchase(this.equipmentForm).then(response => {
        // 处理添加购买商品成功的情况
        // 添加成功后重新获取购买商品列表
        this.fetchEquipmentPurchases()
        this.dialogVisible = false // 关闭对话框
        // 清空表单数据
        this.clearForm()
      })
    },

    // 更新购买商品
    updateEquipmentPurchase() {
      updateEquipmentPurchase(this.equipmentForm).then(response => {
        // 处理更新场地成功的情况
        // 更新成功后重新获取场地列表
        this.fetchEquipmentPurchases()
        this.dialogVisible = false // 关闭对话框
        // 清空表单数据
        this.clearForm()
        // 将对话框按钮文本设置为其他值，避免再次触发更新操作
        this.dialogButtonText = '更新成功'
      })
    },

    // 编辑按钮点击事件
    handleEdit(row) {
      // 将当前要编辑的购买商品的信息赋值给编辑表单
      this.equipmentForm = {
        equipmentPurchaseId: row.equipmentPurchaseId,
        venueId: row.venueId,
        equipmentPurchaseType: row.equipmentPurchaseType,
        equipmentPurchaseNumber: row.equipmentPurchaseNumber,
        equipmentPurchaseFee: row.equipmentPurchaseFee
      };
      // 打开编辑对话框
      this.dialogTitle = '编辑购买商品';
      this.dialogVisible = true;
      this.dialogButtonText = '更新'
    },

    // 处理购买操作
    handlePurchase(row) {
      // 获取购买商品信息
      const { venueId, venueName, equipmentPurchaseFee } = row;
      // 调用添加订单记录接口
      addBalanceRecord({
        venueId: venueId,
        venueName: venueName,
        transactionAmount: equipmentPurchaseFee, // 使用购买商品的费用作为交易金额
        transactionType: 1 // 假设购买为交易类型5
      }).then(() => {
        // 添加订单记录成功后的处理逻辑
        this.$message.success('购买成功！');
      });
    },

    // 删除购买商品
    confirmDelete(row) {
      MessageBox.confirm('确认删除该购买商品吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteEquipmentPurchase(row.equipmentPurchaseId).then(response => {
          // 处理删除购买商品成功的情况
          // 删除成功后重新获取购买商品列表
          this.fetchEquipmentPurchases()
        })
      }).catch(() => {
        // 用户点击了取消按钮
      });
    }
  }
}
</script>
