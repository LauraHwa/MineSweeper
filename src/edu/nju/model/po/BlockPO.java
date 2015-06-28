package edu.nju.model.po;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.state.BlockState;
import edu.nju.model.state.DisplayBlockState;
import edu.nju.model.state.GameState;
import edu.nju.model.vo.BlockVO;
/**
 * 后台处理的扫雷块单元
 * @author Wangy
 *
 */
public class BlockPO {
	private BlockState state;
	private int mineNum;
	private boolean isMine;
	private int x;
	private int y;
	private boolean hasChanged;
	private boolean isHost;
	
	public BlockPO(int x, int y){
		state = BlockState.UNCLICK;
		this.mineNum = 0;
		this.x = x;
		this.y = y;
		this.hasChanged = false;
		this.isHost = true;
	}

	/**
	 * 获得用于在界面上显示的扫雷块
	 * @return
	 */
	public BlockVO getDisplayBlock(GameState gameState){
		DisplayBlockState dbs = null;
			
		if((state == BlockState.FLAG)||(state == BlockState.CLICK)){
			if(!this.hasChanged){
				if(OperationQueue.isHost){
					this.isHost = true;
				}else{
					this.isHost = false;
				}
				this.hasChanged = true;
			}
		}
		
		if(gameState == GameState.RUN){
			if(state == BlockState.CLICK&&(!isMine)){
				dbs = DisplayBlockState.getClickState(mineNum);
			}
			else if(state == BlockState.UNCLICK){
				dbs = DisplayBlockState.UNCLICK;
			}
			else if(state == BlockState.FLAG){
				if(this.isHost){
					dbs = DisplayBlockState.FLAG;
				}else{
					dbs = DisplayBlockState.CLIENTFALG;
				}
			}
		}else if(gameState == GameState.OVER){//当游戏为结束状态时
			if(state == BlockState.CLICK&&isMine){
				if(this.isHost){
					dbs = DisplayBlockState.Bomb;
				}else{
					dbs = DisplayBlockState.CLIENTBomb;
				}
			}
			else if((state == BlockState.UNCLICK)&&isMine){
				dbs = DisplayBlockState.MINE;
			}
			else if(state == BlockState.FLAG&&(!isMine)){
				if(this.isHost){
					dbs = DisplayBlockState.ERROFLAG;
				}else{
					dbs = DisplayBlockState.CLIENTERRORFLAG;
				}
			}
		}
		
		BlockVO db = new BlockVO(dbs,x,y);

		return db;
	}

	public BlockState getState() {
		return state;
	}

	public void setState(BlockState state) {
		this.state = state;
	}

	public int getMineNum() {
		return mineNum;
	}

	public void setMineNum(int mineNum) {
		this.mineNum = mineNum;
	}

	public boolean isMine() {
		return isMine;
	}

	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void addMine(){
		this.mineNum++;
	}
}