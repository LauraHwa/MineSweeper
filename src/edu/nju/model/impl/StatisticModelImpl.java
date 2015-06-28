package edu.nju.model.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import edu.nju.model.data.StatisticData;
import edu.nju.model.po.StatisticPO;
import edu.nju.model.service.StatisticModelService;
import edu.nju.model.state.GameResultState;

public class StatisticModelImpl extends BaseModel implements StatisticModelService{
	
	private StatisticData statisticDao;
	
	private int level;
	/**
	 * 将游戏数据保存到save.dat文件中
	 */
	public StatisticModelImpl(){
		statisticDao = new StatisticData();
	}

	@Override
	public void recordStatistic(GameResultState result, int time) {
		StatisticPO statistic = this.statisticDao.getStatistic(this.level);
		int sum = statistic.getSum();
		int win = statistic.getWins();
		sum ++;
		statistic.setSum(sum);
		if(result == GameResultState.SUCCESS){	
			win ++;
			statistic.setWins(win);
		}
		double winRate = (double)win / (double)sum;
		BigDecimal b = new BigDecimal(winRate);
		winRate = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		statistic.setWinrate(winRate);
		this.statisticDao.saveStatistic(statistic);
	}

	@Override
	public void showStatistics() {
		List<StatisticPO> statistics = new ArrayList<StatisticPO>();
		statistics.add(this.statisticDao.getStatistic(0));
		statistics.add(this.statisticDao.getStatistic(1));
		statistics.add(this.statisticDao.getStatistic(2));
		statistics.add(this.statisticDao.getStatistic(3));
		
		super.updateChange(new UpdateMessage("record",statistics));		
	}

	@Override
	public void setLevel(String level) {
		switch (level) {
		case "小": this.level = 0; break;
		case "中": this.level = 1; break;
		case "大": this.level = 2; break;
		case "自定义": this.level = 3; break;
		}
	}
}