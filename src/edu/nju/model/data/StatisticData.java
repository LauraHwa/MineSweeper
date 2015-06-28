package edu.nju.model.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import edu.nju.model.po.StatisticPO;

/**
 * 负责进行统计数据获取和记录操作
 * @author Wangy
 *
 */
public class StatisticData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final File data = new File("save.dat");
	private ArrayList<StatisticPO> statistics;
	
	public StatisticPO getStatistic(int level){
		statistics = null;
		StatisticPO statistic = null;
		try {
			FileInputStream fis = new FileInputStream(data);
			int size = fis.available();
			if(size != 0){
				ObjectInputStream ois = new ObjectInputStream(fis);
				statistics = (ArrayList<StatisticPO>) ois.readObject();
				ois.close();
			}
		} catch (ClassNotFoundException e) {
			statistics = null;
			System.out.println("文件中没有对象");
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		if(statistics == null){
			statistics = new ArrayList<StatisticPO>(4);
			statistics.add(new StatisticPO(0.0, 0, 0, 0, 9, 9));
			statistics.add(new StatisticPO(0.0, 0, 0, 1, 16, 16));
			statistics.add(new StatisticPO(0.0, 0, 0, 2, 30, 16));
			statistics.add(new StatisticPO(0.0, 0, 0, 3, 0, 0));
		}

		if(statistics.size() == 4){
			statistic = statistics.get(level);
		}
		
		return statistic;
	}
	
	public boolean saveStatistic(StatisticPO statistic){
		int level = statistic.getLevel();
		statistics.remove(level);
		statistics.add(level, statistic);
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(data ,false));
			oos.writeObject(statistics);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}