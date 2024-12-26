package com.ruoyi.featherball.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.featherball.domain.BalanceRecord;
import com.ruoyi.featherball.domain.Reservation;
import com.ruoyi.featherball.mapper.BalanceRecordMapper;
import com.ruoyi.featherball.mapper.EquipmentPurchaseMapper;
import com.ruoyi.featherball.service.BalanceRecordService;
import com.ruoyi.featherball.service.VenueService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * 订单记录service接口实现类
 *
 * @Author fanjaixing
 * @Date 2024/3/9 12:11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceRecordServiceImpl implements BalanceRecordService {

    private final BalanceRecordMapper balanceRecordMapper;

    private final ISysUserService iSysUserService;

    private final ISysRoleService iSysRoleService;

    private final VenueService venueService;

    private final EquipmentPurchaseMapper equipmentPurchaseMapper;

    /**
     * 获取所有订单记录信息
     *
     * @return 所有订单记录信息列表
     */
    @Override
    public List<BalanceRecord> getAllBalanceRecords() {
        // 获取当前登录用户ID
        Long userId = SecurityUtils.getUserId();
        String role = iSysRoleService.selectStringRoleByUserId(userId);
        if (role.equalsIgnoreCase("admin")) {
            startPage();
            List<BalanceRecord> allBalanceRecords = balanceRecordMapper.getAllBalanceRecords();
            fillVenueAndUserName(allBalanceRecords);
            return allBalanceRecords;
        } else {
            startPage();
            List<BalanceRecord> balanceRecordsByUserId = balanceRecordMapper.getBalanceRecordsByUserId(userId);
            fillVenueAndUserName(balanceRecordsByUserId);
            return balanceRecordsByUserId;
        }
    }

    /**
     * 根据记录ID获取订单记录信息
     *
     * @param recordId 记录ID
     * @return 订单记录信息
     */
    @Override
    public BalanceRecord getBalanceRecordById(Long recordId) {
        return balanceRecordMapper.getBalanceRecordById(recordId);
    }

    /**
     * 添加订单记录信息
     *
     * @param userBalanceRecord 待添加的订单记录信息
     * @return 添加成功返回 true，否则返回 false
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addBalanceRecord(BalanceRecord userBalanceRecord) {

        // 获取当前登录用户ID
        Long userId = SecurityUtils.getUserId();
        // 获取当前登录用户姓名
        String userName = SecurityUtils.getUsername();
        // 获取当前用户的余额
        BigDecimal userBalance = iSysUserService.selectUserById(userId).getBalance();
        // 获取当前用户的角色
        String role = iSysRoleService.selectStringRoleByUserId(userId);

        if ("admin".equals(role) || "trainer".equals(role)) {
            throw new RuntimeException("管理员或教练员无法预约/购买/维修");
        }

        BigDecimal transactionAmount = userBalanceRecord.getTransactionAmount();
        // 判断余额是否足够支付课程费用
        if (userBalance.compareTo(transactionAmount) < 0) {
            throw new RuntimeException("您的余额不足");
        }
        // 获取管理员当前余额
        BigDecimal adminBalance = iSysUserService.selectUserById(1L).getBalance();
        log.info("adminBalance: " + adminBalance);
        BigDecimal actualTransactionAmount;
        BigDecimal newBalance;
        //根据交易类型计算实际金额
        if (userBalanceRecord.getTransactionType() == 3) {
            // 计算用户实际交易金额（考虑VIP用户）
            actualTransactionAmount = "common".equals(role) ? transactionAmount : transactionAmount.multiply(
                    BigDecimal.valueOf(0.8)).setScale(2, RoundingMode.HALF_UP);
            // 将用户实际消费的金额从用户余额中扣除
            newBalance = userBalance.subtract(actualTransactionAmount);
        } else if (userBalanceRecord.getTransactionType() == 2) {
            actualTransactionAmount = "common".equals(role) ? transactionAmount : transactionAmount.multiply(
                    BigDecimal.valueOf(0.5)).setScale(2, RoundingMode.HALF_UP);
            newBalance = userBalance.subtract(actualTransactionAmount);
        } else {
            actualTransactionAmount = "common".equals(role) ? transactionAmount : transactionAmount.multiply(
                    BigDecimal.valueOf(0.7)).setScale(2, RoundingMode.HALF_UP);
            newBalance = userBalance.subtract(actualTransactionAmount);
        }

        // 更新用户余额信息
        SysUser currentUser = iSysUserService.selectUserById(userId);
        currentUser.setBalance(newBalance);
        iSysUserService.updateUserBalance(currentUser);
        // 设置用户消费记录信息
        userBalanceRecord.setUserId(userId);
        userBalanceRecord.setTransactionAmount(actualTransactionAmount);
        userBalanceRecord.setTransactionTime(LocalDateTime.now());
        userBalanceRecord.setBalance(newBalance);
        // 添加用户消费记录
        balanceRecordMapper.addBalanceRecord(userBalanceRecord);

        // 更新管理员的余额信息
        BigDecimal newAdminBalance = adminBalance.add(actualTransactionAmount);
        SysUser admin = iSysUserService.selectUserById(1L);
        admin.setBalance(newAdminBalance);
        iSysUserService.updateUserBalance(admin);
        // 设置管理员收益记录信息
        BalanceRecord adminBalanceRecord = new BalanceRecord();
        adminBalanceRecord.setUserId(1L);
        adminBalanceRecord.setTransactionAmount(actualTransactionAmount);
        adminBalanceRecord.setTransactionTime(LocalDateTime.now());
        adminBalanceRecord.setBalance(newAdminBalance);
        adminBalanceRecord.setTransactionType(0);
        adminBalanceRecord.setVenueId(userBalanceRecord.getVenueId());
        // 添加管理员收益记录
        balanceRecordMapper.addBalanceRecord(adminBalanceRecord);

        return true;
    }

    private void fillVenueAndUserName(List<BalanceRecord> balanceRecords) {
        for (BalanceRecord balanceRecord : balanceRecords) {
            Long userId = balanceRecord.getUserId();
            SysUser user = iSysUserService.selectUserById(userId);
            if (user != null) {
                balanceRecord.setUserName(user.getNickName());
            }
            Long venueId = balanceRecord.getVenueId();
            String venueName = venueService.getVenueById(venueId).getVenueName();
            balanceRecord.setVenueName(venueName);
        }
    }

    @Override
    public Map<Integer, BigDecimal> getBalanceTotalByType() {
        Map<Integer, BigDecimal> result = new HashMap<>();
        List<BalanceRecord> records = balanceRecordMapper.getAllBalanceRecords();
        for (BalanceRecord record : records) {
            Integer transactionType = record.getTransactionType();
            if (transactionType != 0) {
                BigDecimal transactionAmount = balanceRecordMapper.getBalanceTotalByType(transactionType);
                result.put(transactionType,transactionAmount);
            }
        }
        return result;
    }
}
