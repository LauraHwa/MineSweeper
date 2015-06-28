package edu.nju.model.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.ParameterModelService;
import edu.nju.network.host.HostThread;
import edu.nju.view.Images;

public class ParameterModelImpl extends BaseModel implements ParameterModelService{
	
	private int maxMine;
	private int mineNum;
	private int nowMaxMine;
	
	@Override
	public boolean setMineNum(int num) {
		// TODO Auto-generated method stub
		mineNum = num;
		maxMine = num;
		nowMaxMine = num;
		if(HostThread.isConnected){
			super.updateChange(new UpdateMessage("mineNum", maxMine + " " + nowMaxMine + " " + mineNum));
		}else{
			super.updateChange(new UpdateMessage("mineNum", mineNum));
		}
//		if(Images.ISCLIENT){
//			super.updateChange(new UpdateMessage("mineNum", maxMine - mineNum));
//		}else{
//		}
		return true;
	}

	@Override
	public boolean addMineNum() {
		// TODO Auto-generated method stub
		mineNum++;
		
		if(mineNum>maxMine){
			mineNum--;
			return false;
		}
		super.updateChange(new UpdateMessage("mineNum", mineNum));

		return true;
	}

	@Override
	public boolean minusMineNum() {
		mineNum--;
		if(HostThread.isConnected){
			if(!OperationQueue.isHost){
				nowMaxMine --;
			}
			super.updateChange(new UpdateMessage("mineNum", maxMine + " " + nowMaxMine + " " + mineNum));
			return true;
		}
		
		if(mineNum<0){
			mineNum++;
			return false;
		}
		
		super.updateChange(new UpdateMessage("mineNum", mineNum));
		return true;
	}

	@Override
	public byte judgeWinner() {
		if((this.nowMaxMine<<1) > this.maxMine){
			return 1;
		}else if((this.nowMaxMine<<1) == this.maxMine){
			return 0;
		}else{
			return -1;
		}
	}
}
