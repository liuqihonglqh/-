<template>
  <div>
    <!-- 场地管理页面内容 -->
    <div v-if="activeTab === 'court'">
      <div class="app-container">
        <!-- 添加场地按钮 -->
        <el-row :gutter="20" class="mb-20" style="margin-bottom: 20px;">
          <el-col>
            <el-button type="primary" @click="handleAddCourt" v-hasPermi="['featherball:court:add']">新增场地</el-button>
          </el-col>
        </el-row>

        <!-- 场地列表 -->
        <el-table :data="courtList" v-loading="loading" style="width: 100%" border>
          <el-table-column label="场地ID" prop="courtId" align="center"></el-table-column>
          <el-table-column label="场馆名称" prop="venueName" align="center"></el-table-column>
          <el-table-column label="场地编号" prop="courtNumber" align="center"></el-table-column>
          <el-table-column label="教练名称" prop="trainerName" align="center"></el-table-column>
          <el-table-column label="场地状态" prop="courtStatus" align="center">
            <template slot-scope="scope">
              {{ scope.row.courtStatus === 0 ? '不可预约' : '可预约' }}
            </template>
          </el-table-column>
          <el-table-column label="是否VIP场地" prop="courtVip" align="center">
            <template slot-scope="scope">
              {{ scope.row.courtVip === 0 ? '否' : '是' }}
            </template>
          </el-table-column>
          <el-table-column label="场地费用" prop="courtFee" align="center"></el-table-column>
          <el-table-column label="操作" align="center" width="280px">
            <template slot-scope="scope">
              <el-button type="info" size="mini" @click="handleReservation(scope.row)" v-hasPermi="['featherball:court:reservation']">预约</el-button>
              <el-button type="success" size="mini" @click="handleView(scope.row)" v-hasPermi="['featherball:court:detail']">查看</el-button>
              <el-button type="primary" size="mini" @click="handleEdit(scope.row)" v-hasPermi="['featherball:court:edit']">编辑</el-button>
              <el-button type="danger" size="mini" @click="handleDelete(scope.row)" v-hasPermi="['featherball:court:delete']">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 分页组件 -->
        <pagination
          v-show="totalCourts > 0"
          :total="totalCourts"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="fetchCourts"
        />

        <!-- 添加/编辑场地对话框 -->
        <el-dialog :visible.sync="dialogVisible" :title="dialogTitle" width="30%" @close="handleCloseDialog">
          <!-- 对话框内容 -->
          <div>
            <el-form :model="courtForm" label-width="100px">
              <!-- 场馆名称下拉选择 -->
              <el-form-item label="场馆名称">
                <el-select v-model="courtForm.venueId" placeholder="请选择场馆" :disabled="isReadOnly">
                  <el-option
                    v-for="venue in venueOptions"
                    :key="venue.venueId"
                    :label="venue.venueName"
                    :value="venue.venueId"
                  ></el-option>
                </el-select>
              </el-form-item>
              <!-- 场地编号 -->
              <el-form-item label="场地编号">
                <el-input v-model="courtForm.courtNumber" :disabled="isReadOnly"></el-input>
              </el-form-item>
              <!-- 教练员下拉选择 -->
              <el-form-item label="选择教练员">
                <el-select v-model="courtForm.trainerId" placeholder="请选择教练员" :disabled="isReadOnly">
                  <el-option
                    v-for="trainer in trainerOptions"
                    :key="trainer.userId"
                    :label="trainer.nickName"
                    :value="trainer.userId"
                  ></el-option>
                </el-select>
              </el-form-item>
              <!-- 场地状态 -->
              <el-form-item label="场地状态">
                <el-radio-group v-model="courtForm.courtStatus" :disabled="isReadOnly">
                  <el-radio :label="0">不可预约</el-radio>
                  <el-radio :label="1">可预约</el-radio>
                </el-radio-group>
              </el-form-item>
              <!-- 是否为VIP场地 -->
              <el-form-item label="是否VIP场地">
                <el-select v-model="courtForm.courtVip" :disabled="isReadOnly">
                  <el-option label="是" :value="1"></el-option>
                  <el-option label="否" :value="0"></el-option>
                </el-select>
              </el-form-item>
              <!-- 场地费用 -->
              <el-form-item label="场地费用">
                <el-input v-model="courtForm.courtFee" :disabled="isReadOnly"></el-input>
              </el-form-item>
            </el-form>
          </div>
          <!-- 对话框按钮 -->
          <div slot="footer" class="dialog-footer" style="text-align: center;">
            <el-button @click="handleCloseDialog">取消</el-button>
            <el-button type="primary" @click="handleSubmit">{{ dialogButtonText }}</el-button>
          </div>
        </el-dialog>

        <!-- 预约对话框 -->
        <el-dialog :visible.sync="reserveDialogVisible" title="预约场地" width="30%" @close="handleCloseDialog">
          <!-- 预约对话框内容 -->
          <div>
            <!-- 预约起止时间选择器 -->
            <div class="block">
              <span class="demonstration" style="display: block; margin-bottom: 10px;">选择预约起止时间：</span>
              <el-date-picker
                v-model="reservationTime"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始日期时间"
                end-placeholder="结束日期时间"
                :picker-options="pickerOptions"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-ddTHH:mm:ssZ">
              </el-date-picker>
            </div>
          </div>
          <!-- 预约对话框底部按钮 -->
          <div slot="footer" class="dialog-footer" style="text-align: center;">
            <el-button @click="handleCloseDialog">取消</el-button>
            <el-button type="primary" @click="confirmReservation">确定预约</el-button>
          </div>
        </el-dialog>

      </div>
    </div>
  </div>
</template>

<script>
import {listCourts, addCourt, updateCourt, deleteCourt, getCourt, listTrainers} from '@/api/featherball/court'
import {listVenues} from "@/api/featherball/venue"
import {addReservation, getVIPUserNotification } from '@/api/featherball/reservation'
import {addBalanceRecord} from "@/api/featherball/balanceRecord";

export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      activeTab: 'court', // 当前激活的选项卡，默认为场地管理
      courtList: [], // 场地列表数据
      venueOptions: [], // 场馆选项列表
      trainerOptions: [], // 教练员选项列表
      // 总条数
      totalCourts: 0,
      dialogVisible: false, // 控制新增场地对话框的显示与隐藏
      dialogTitle: '', // 对话框标题
      dialogButtonText: '', // 对话框按钮文本
      courtForm: { // 新增场地表单
        venueId:'',
        trainerId: '',
        venueName: '',
        courtNumber: '',
        courtStatus: 1, // 默认可预约
        courtFee: '',
        courtVip: 0,//默认不是VIP场地
      },
      isReadOnly: false, // 是否只读模式
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      // 预约对话框
      reserveDialogVisible: false, // 控制预约对话框的显示与隐藏
      reservationTime: [],// 存储预约起止时间的数组，数组的第一个元素为开始时间，第二个元素为结束时间
      selectedCourtId: null,// 选中的场地ID，默认为null
      pickerOptions: {
        disabledDate(time) {
          // 获取当前日期
          const today = new Date();
          // 获取一周后的日期
          const oneWeekLater = new Date(today.getTime() + 8 * 24 * 60 * 60 * 1000);
          // 将时间戳转换为年月日格式的字符串
          const currentDate = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
          const oneWeekLaterDate = oneWeekLater.getFullYear() + '-' + (oneWeekLater.getMonth() + 1) + '-' + oneWeekLater.getDate();
          // 将当前日期转换为毫秒数
          const currentTime = new Date(currentDate).getTime();
          // 将一周后日期转换为毫秒数
          const oneWeekLaterTime = new Date(oneWeekLaterDate).getTime();
          // 将传入的时间参数转换为毫秒数
          const targetTime = time.getTime();
          // 如果传入时间小于等于当前时间或者大于一周后的时间，则禁用
          return targetTime <= currentTime || targetTime >= oneWeekLaterTime;
        }
      }
    }
  },
  created() {
    // 在页面加载时获取场地列表
    this.fetchCourts()
    this.fetchVenues()
    this.fetchTrainers()
  },
  methods: {
    // 获取场地列表
    fetchCourts() {
      this.loading = true
      listCourts(this.queryParams).then(response => {
        this.courtList = response.rows
        this.totalCourts = response.total
        this.loading = false
      })
    },

    // 获取教练员列表
    fetchTrainers() {
      listTrainers().then(response => {
        // 将后端返回的教练员列表赋值给 trainerOptions
        this.trainerOptions = response.data.map(trainer => ({
          userId: trainer.userId,
          nickName: trainer.nickName
        }));
      });
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
      this.courtForm = {
        venueName: '',
        courtNumber: '',
        courtStatus: 0,
        courtFee: ''
      }
    },

    // 添加场地
    handleAddCourt() {
      this.dialogTitle = "新增场地"
      this.dialogButtonText = "添加"
      this.isReadOnly = false // 设置为可编辑模式
      this.dialogVisible = true // 打开对话框
    },

    // 添加场地
    addCourt() {
      // 验证场地名称是否为空
      if (!this.courtForm.venueId) {
        this.$message.error('场馆名称不能为空')
        return
      }
      // 将布尔值转换为整数值
      this.courtForm.courtStatus = this.courtForm.courtStatus ? 1 : 0;
      addCourt(this.courtForm).then(response => {
        // 处理添加场地成功的情况
        // 添加成功后重新获取场地列表
        this.fetchCourts()
        this.dialogVisible = false // 关闭对话框
        // 清空表单数据
        this.clearForm()
      })
    },

    // 更新场地
    updateCourt() {
      updateCourt(this.courtForm).then(response => {
        // 处理更新场地成功的情况
        // 更新成功后重新获取场地列表
        this.fetchCourts()
        this.dialogVisible = false // 关闭对话框
        // 清空表单数据
        this.clearForm()
        // 将对话框按钮文本设置为其他值，避免再次触发更新操作
        this.dialogButtonText = '更新成功'
      })
    },

    // 删除场地
    deleteCourt(courtId) {
      deleteCourt(courtId).then(response => {
        // 处理删除场地成功的情况
        // 删除成功后重新获取场地列表
        this.fetchCourts()
      })
    },

    // 编辑按钮点击事件
    handleEdit(row) {
      // 将编辑的场地数据填充到表单中
      this.courtForm = Object.assign({}, row)
      this.dialogTitle = '编辑场地' // 设置对话框标题为编辑场地
      this.dialogButtonText = '更新' // 设置对话框按钮文本为更新
      this.isReadOnly = false // 设置为可编辑模式
      this.dialogVisible = true // 打开对话框
    },

    // 提交表单
    handleSubmit() {
      if (this.dialogButtonText === '更新') {
        // 调用更新场地的方法
        this.updateCourt()
      } else if (this.dialogButtonText === "添加") {
        // 调用添加场地的方法
        this.addCourt()
      } else {
        this.handleCloseDialog()
      }
    },

    // 删除按钮点击事件
    handleDelete(row) {
      // 弹出确认框，确认删除后调用删除场地方法
      this.$confirm('确认删除该场地吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log(row)
        this.deleteCourt(row.courtId)
      })
    },

    // 查看场地
    handleView(row) {
      this.dialogTitle = '查看场地' // 设置对话框标题为查看场地
      this.dialogButtonText = '关闭' // 设置对话框按钮文本为关闭
      this.isReadOnly = true // 设置为只读模式
      // 调用后端接口获取场地详细信息
      getCourt(row.courtId).then(response => {
        this.courtForm = response.data // 填充表单数据
        this.dialogVisible = true // 打开对话框
      })
    },

    // 关闭对话框
    handleCloseDialog() {
      this.clearForm()
      this.dialogVisible = false
    },

    // 处理预约按钮点击事件
    handleReservation(row) {
      // 获取场地ID
      const courtId = row.courtId;
      this.courtForm.courtFee = row.courtFee
      this.courtForm.venueName = row.venueName
      this.courtForm.venueId = row.venueId
      // 调用获取场地预约提示信息的方法
      getVIPUserNotification(courtId).then(response => {
        // 处理返回的提示信息
        const notification = response.message;
        if (notification) {
          // 如果有提示信息，则弹出提示
          this.$message.warning(notification);
        } else {
          // 如果没有提示信息，则继续执行预约操作
          this.dialogTitle = '预约场地'; // 设置对话框标题为预约场地
          this.reserveDialogVisible = true; // 打开预约对话框
          // 将选中的场地ID赋值给selectedCourtId
          this.selectedCourtId = courtId;
        }
      });
    },

    // 确定预约
    confirmReservation() {
      // 获取预约起止时间
      const [startTime, endTime] = this.reservationTime;
      // 执行预约操作，这里假设调用后端接口进行预约
      addReservation({
        courtId: this.selectedCourtId, // 传入场地ID
        startTime, // 传入预约开始时间
        endTime // 传入预约结束时间
      }).then(() => {
        // 添加订单记录
        addBalanceRecord({
          venueId:this.courtForm.venueId,
          venueName:this.courtForm.venueName,
          transactionAmount: this.courtForm.courtFee, // 场地费用作为交易金额
          transactionType: 3 // 假设预约为交易类型3
        }).then(() => {
          // 预约成功后的处理逻辑
          this.$message.success('预约成功！');
          // 添加订单记录成功的处理逻辑
          // 关闭预约对话框
          this.reserveDialogVisible = false;
          // 清空预约起止时间
          this.reservationTime = [];
        });
      })
    }
  }
}
</script>

