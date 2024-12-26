<template>
  <div>
    <!-- 订单记录页面内容 -->
    <div v-if="activeTab === 'balanceRecord'">
      <div class="app-container">
        <!-- 订单记录列表 -->
        <el-table :data="balanceRecordList" v-loading="loading" style="width: 100%" border>
          <el-table-column label="记录ID" prop="recordId" align="center"></el-table-column>
          <el-table-column label="用户名称" prop="userName" align="center"></el-table-column>
          <el-table-column label="场馆名称" prop="venueName" align="center"></el-table-column>
          <el-table-column label="交易类型" prop="transactionType" align="center">
            <template slot-scope="scope">
              {{ scope.row.transactionType === 0 ? '收入' : scope.row.transactionType === 1 ? '购买' : '维修' }}
            </template>
          </el-table-column>
          <el-table-column label="交易金额" prop="transactionAmount" align="center"></el-table-column>
          <el-table-column label="交易时间" prop="transactionTime" align="center"></el-table-column>
          <el-table-column label="交易后余额" prop="balance" align="center"></el-table-column>
        </el-table>

        <!-- 分页组件 -->
        <pagination
          v-show="totalBalanceRecords > 0"
          :total="totalBalanceRecords"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="fetchBalanceRecords"
        />

      </div>
    </div>
  </div>
</template>

<script>
import { listBalanceRecords } from '@/api/featherball/balanceRecord'

export default {
  data() {
    return {
      loading: true,
      activeTab: 'balanceRecord',
      balanceRecordList: [],
      totalBalanceRecords: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10
      }
    };
  },
  created() {
    this.fetchBalanceRecords();
  },
  methods: {
    fetchBalanceRecords() {
      this.loading = true;
      listBalanceRecords(this.queryParams).then(response => {
        this.balanceRecordList = response.rows;
        this.totalBalanceRecords = response.total;
        this.loading = false;
      });
    }
  }
};
</script>

<style scoped>

</style>

