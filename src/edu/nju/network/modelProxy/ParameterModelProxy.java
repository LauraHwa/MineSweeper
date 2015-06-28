package edu.nju.network.modelProxy;

import java.util.Observable;

import edu.nju.model.impl.UpdateMessage;
import edu.nju.model.service.ParameterModelService;
import edu.nju.network.TransformObject;

public class ParameterModelProxy extends ModelProxy implements ParameterModelService {
	
	
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		TransformObject obj = (TransformObject) arg;
		String trigger_class = obj.getSource();
		UpdateMessage msg = obj.getMsg();
		Class<?> super_class = this.getClass().getInterfaces()[0];
		try {
			if(super_class.isAssignableFrom(Class.forName(trigger_class))){
				int remainMinesNum = this.getClientMinesNumber((String)msg.getValue());
				msg.setValue(remainMinesNum);
				this.updateChange(msg);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public boolean setMineNum(int num) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean minusMineNum() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addMineNum() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private int getClientMinesNumber(String str){
		String[] nums = str.split(" ");
		int maxNum = Integer.parseInt(nums[0]);
		int nowMaxNum = Integer.parseInt(nums[1]);
		return maxNum - nowMaxNum;
	}
	@Override
	public byte judgeWinner() {
		return 0;
	}
}
