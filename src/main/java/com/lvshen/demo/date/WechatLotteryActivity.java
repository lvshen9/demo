package com.lvshen.demo.date;

import lombok.Data;

/**
 * wechatLotteryActivityEntity
 *
 * @author yuanma
 * @version 2018-05-29
 */
@Data
public class WechatLotteryActivity{

	private static final long serialVersionUID = 1L;

	private String id;

	/** 抽奖活动名称 */
	private String name;

	/** 活动开始时间 */
	private java.util.Date startDate;

	/** 活动结束时间 */
	private java.util.Date endDate;

	/** 是否启用 */
	private String isEnable;

	/** 抽奖条件类型，积分抽奖-CREDIT_DRAW，订单数抽奖-ORDER_NUM_DRAW，无门槛-NO_THRESHOLD */
	private String lotteryThresholdType;

	/** 抽奖条件结果，积分抽奖-消耗积分值，订单数抽奖-(CURRENT)当前商城&(ALL)全渠道 */
	private String lotteryThresholdResult;

	/** 是否允许分享 */
	private String isAllowShare;

	/** 分享文案 */
	private String shareTitle;

	/** 分享图片 */
	private String sharePicture;

	/** 抽奖次数类型，BY_DAY-一天N次，BY_USER-一人N次 */
	private String lotteryNumberType;

	/** 限制抽奖次数 */
	private Integer limitNum;

	/** 分享额外限制抽奖次数 */
	private Integer shareLimitNum;

	/** 是否关注了公众号才能抽奖 */
	private String isFollowPublicAccounts;

	/** 是否首次抽奖必中红包 */
	private String isFirstRedpacket;

	/** 规则说明 */
	private String description;

	/** 业务身份，一级租户ID */
	private String tenantId;

	/** 业务身份，二级租户ID */
	private String extTenantId;


}