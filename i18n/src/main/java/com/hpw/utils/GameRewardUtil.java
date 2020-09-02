package com.hpw.utils;

import com.alibaba.fastjson.JSONArray;
import com.hpw.bean.GameReward;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 游戏奖励道具转换工具类
 */
public class GameRewardUtil {
	public static String rds2JsonString(List<GameReward> rds) {
		if(CollectionUtils.isEmpty(rds))
			return null;
		
		JSONArray rewardArray = new JSONArray();
		for(GameReward gr : rds) {
			JSONArray tmp = new JSONArray();
			tmp.add(gr.getGameFlag());
			tmp.add(gr.getItemId());
			tmp.add(gr.getItemType());
			tmp.add(gr.getItemNum());
			rewardArray.add(tmp);
		}
		
		return rewardArray.toJSONString();
	}

	/**
	 * @param jsonString 一般是 {@link GameRewardUtil#rds2JsonString(List)} 的返回值
	 */
	public static List<GameReward> jsonString2Rds(String jsonString) {
		if (Objects.isNull(jsonString) || "".equals(jsonString)) {
			return Collections.emptyList();
		}

		List<JSONArray> jsonArrays = JSONArray.parseArray(jsonString, JSONArray.class);
		if (CollectionUtils.isEmpty(jsonArrays)) {
			return Collections.emptyList();
		}

		List<GameReward> retList = new ArrayList<>(jsonArrays.size());
		GameReward gameReward;
		for (JSONArray jsonArray : jsonArrays) {
				gameReward = new GameReward();
				gameReward.setGameFlag(jsonArray.getInteger(0));
				gameReward.setItemId(jsonArray.getInteger(1));
				gameReward.setItemType(jsonArray.getInteger(2));
				gameReward.setItemNum(jsonArray.getLong(3));
				retList.add(gameReward);
		}

		return retList;
	}
}
