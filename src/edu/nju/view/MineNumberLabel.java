package edu.nju.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import edu.nju.model.impl.UpdateMessage;
import edu.nju.network.host.HostThread;

public class MineNumberLabel extends JLabel implements Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MineNumberLabel(){
	}
	
	private int reamainMinesNumber;
	@Override
	public void update(Observable o, Object arg) {
		UpdateMessage updateMessage = (UpdateMessage) arg;
		if(updateMessage.getKey().equals("mineNum")){
			int remainMines = 0;
			if(HostThread.isConnected){
				remainMines = this.getHostMinesNumber((String)updateMessage.getValue());
			}else{
				remainMines = (Integer) updateMessage.getValue();
			}
			this.setReamainMinesNumber(remainMines);
			this.setText(remainMines+"");
		}

	}
	private int getHostMinesNumber(String str){
		String[] nums = str.split(" ");
		int nowMaxNum = Integer.parseInt(nums[1]);
		int mineNum = Integer.parseInt(nums[2]);
		return nowMaxNum - mineNum;
	}
	public int getReamainMinesNumber() {
		return reamainMinesNumber;
	}
	public void setReamainMinesNumber(int reamainMinesNumber) {
		this.reamainMinesNumber = reamainMinesNumber;
	}
}
