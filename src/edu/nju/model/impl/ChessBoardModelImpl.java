package edu.nju.model.impl;

import java.util.ArrayList;
import java.util.List;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.po.BlockPO;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;
import edu.nju.model.service.ParameterModelService;
import edu.nju.model.state.BlockState;
import edu.nju.model.state.GameResultState;
import edu.nju.model.state.GameState;
import edu.nju.model.vo.BlockVO;
import edu.nju.network.host.HostThread;

public class ChessBoardModelImpl extends BaseModel implements ChessBoardModelService{
	
	private GameModelService gameModel;
	private ParameterModelService parameterModel;
	
	private BlockPO[][] blockMatrix;
	private List<BlockPO> blocks;
	
	private GameState gameState;
	
	public ChessBoardModelImpl(ParameterModelService parameterModel){
		this.parameterModel = parameterModel;
	}

	@Override
	public boolean initialize(int width, int height, int mineNum) {
		// TODO Auto-generated method stub
		this.gameState = GameState.RUN;
		
		blockMatrix = new BlockPO[width][height];
		setBlock(mineNum);
		
		this.parameterModel.setMineNum(mineNum);

		this.blocks = new ArrayList<BlockPO>();
		if(this.blockMatrix != null){
			for (int i = 0; i < blockMatrix.length; i++) {
				for (int j = 0; j < blockMatrix[0].length; j++) {
					this.blocks.add(this.blockMatrix[i][j]);
				}
			}
		}
		return false;
	}

	@Override
	public boolean excavate(int x, int y) {
		// TODO Auto-generated method stub
		/********************简单示例挖开方法，待完善********************/
		if(blockMatrix == null)
			return false;
		
		List<BlockPO> blocks = new ArrayList<BlockPO>();
		BlockPO block = blockMatrix[x][y];
		
		if(block.getState() == BlockState.UNCLICK){
			block.setState(BlockState.CLICK);
		}
		blocks.add(block);
		
		GameState gameState = GameState.RUN;
		if(block.isMine()){
			gameState = GameState.OVER;
			this.gameModel.gameOver(GameResultState.FAIL);
		}
		
		super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, gameState)));			
		if((this.gameState == GameState.RUN)&&(block.getMineNum() == 0)){
			for (int i = x - 1; i < x + 2; i++) {
				if((i >= 0)&&(i < this.blockMatrix.length)){
					for (int j = y - 1; j < y + 2; j++) {
						if((j >= 0)&&(j < this.blockMatrix[0].length)){
							if(this.blockMatrix[i][j].getState() == BlockState.UNCLICK){
								this.excavate(i, j);
							}
						}
					}
				}
			}
		}
		/*
		if(isWin()){
			if(HostThread.isConnected){
				if(this.parameterModel.judgeWinner() == 1){
					this.gameModel.gameOver(GameResultState.SUCCESS);
				}else if(this.parameterModel.judgeWinner() == 0){
					this.gameModel.gameOver(GameResultState.EQUAL);
				}else if(this.parameterModel.judgeWinner() == -1){
					this.gameModel.gameOver(GameResultState.FAIL);
				}
			}else{
				this.gameModel.gameOver(GameResultState.SUCCESS);
			}
		}
		*/
		if(this.gameState == GameState.OVER){
			if(OperationQueue.isHost){
				this.gameModel.gameOver(GameResultState.FAIL);
			}else{
				this.gameModel.gameOver(GameResultState.SUCCESS);
			}	
		}
		return true;
	}
	
	@Override
	public boolean mark(int x, int y) {
		// TODO Auto-generated method stub
		if(blockMatrix == null){
			return false;
		}
		if(this.gameState != GameState.RUN){
			return false;
		}

		BlockPO block = this.blockMatrix[x][y];
		 
		BlockState state = block.getState();
		
		if(state == BlockState.UNCLICK){
			if(this.parameterModel.minusMineNum()){
				block.setState(BlockState.FLAG);
			}
		}else if(state == BlockState.FLAG){
			if(this.parameterModel.addMineNum()){
				block.setState(BlockState.UNCLICK);
			}
		}
		/*
		if(HostThread.isConnected){
			if((state == BlockState.UNCLICK)&&(!block.isMine())){
				this.gameState = GameState.OVER;
			}
		}
		*/
		super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, GameState.RUN)));
		
		if(isWin()){
			/*
			if(HostThread.isConnected){
				if(this.parameterModel.judgeWinner() == 1){
					this.gameModel.gameOver(GameResultState.SUCCESS);
				}else if(this.parameterModel.judgeWinner() == 0){
					this.gameModel.gameOver(GameResultState.EQUAL);
				}else if(this.parameterModel.judgeWinner() == -1){
					this.gameModel.gameOver(GameResultState.FAIL);
				}
			}else{
				this.gameModel.gameOver(GameResultState.SUCCESS);
			}
			*/
			this.gameModel.gameOver(GameResultState.SUCCESS);
		}
		
		if(this.gameState == GameState.OVER){
			if(OperationQueue.isHost){
				this.gameModel.gameOver(GameResultState.FAIL);
			}else{
				this.gameModel.gameOver(GameResultState.SUCCESS);
			}	
		}
		
		return true;
	}

	@Override
	public boolean quickExcavate(int x, int y) {
		// TODO Auto-generated method stub
		if(this.blockMatrix == null){
			return false;
		}
		
		BlockPO block = this.blockMatrix[x][y];
		
		if(block.getState() == BlockState.CLICK){		
			for (int i = x - 1; i < x + 2; i++) {
				if((i >= 0)&&(i < this.blockMatrix.length)){
					for (int j = y - 1; j < y + 2; j++) {
						if((j >= 0)&&(j < this.blockMatrix[0].length)){
							if(this.blockMatrix[i][j].getState() == BlockState.UNCLICK){
								this.excavate(i, j);
							}	
						}
					}
				}
			}
		}
		super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, gameState)));
		return true;
	}

	/**
	 * 示例方法，可选择是否保留该形式
	 * 
	 * 初始化BlockMatrix中的Block，并随机设置mineNum颗雷
	 * 同时可以为每个Block设定附近的雷数
	 * @param mineNum
	 * @return
	 */
	private boolean setBlock(int mineNum){
		int width = blockMatrix.length;
		int height = blockMatrix[0].length;
		
		int index = 0;
		
		//初始化及布雷
		for(int i = 0 ; i<width; i++){
			for (int j = 0 ; j< height; j++){
				blockMatrix[i][j] = new BlockPO(i,j);
			}
		}
		//随机布雷
		for (int i = 0; i < mineNum; i++) {
			while(true){
				int w = (int) (Math.random() * width);
				int h = (int) (Math.random() * height);
				if(!blockMatrix[w][h].isMine()){
					blockMatrix[w][h].setMine(true);
					this.addMineNum(w, h);
					break;
				}
			}	
		}
		
		return false;
	}
	
	
	/**
	 * 示例方法，可选择是否保留该形式
	 * 
	 * 将(i,j)位置附近的Block雷数加1
	 * @param i
	 * @param j
	 */
	private void addMineNum(int i, int j){
		int width = blockMatrix.length;
		int height = blockMatrix[0].length;
		
		int tempI = i-1;		
		
		for(;tempI<=i+1;tempI++){
			int tempJ = j-1;
			for(;tempJ<=j+1;tempJ++){
				if((tempI>-1&&tempI<width)&&(tempJ>-1&&tempJ<height)){
//					System.out.println(i+";"+j+":"+tempI+";"+tempJ+":");
					blockMatrix[tempI][tempJ].addMine();
				}
			}
		}
		
	}
	
	/**
	 * 将逻辑对象转化为显示对象
	 * @param blocks
	 * @param gameState
	 * @return
	 */
	private List<BlockVO> getDisplayList(List<BlockPO> blocks, GameState gameState){
		List<BlockVO> result = new ArrayList<BlockVO>();
		for(BlockPO block : blocks){
			if(block != null){
				BlockVO displayBlock = block.getDisplayBlock(gameState);
				if(displayBlock.getState() != null)
				result.add(displayBlock);
			}
		}
		return result;
	}

	@Override
	public void setGameModel(GameModelService gameModel) {
		// TODO Auto-generated method stub
		this.gameModel = gameModel;
	}
	
	
	private boolean isWin(){
		boolean gameWin = false;
		for (BlockPO blockPO : blocks) {
			if(blockPO.getState() != BlockState.UNCLICK){
				gameWin = true;
			}else{
				gameWin = false;
				break;
			}
		}
		return gameWin;
	}
	
	
	private void printBlockMatrix(){
		for(BlockPO[] blocks : this.blockMatrix){
			for(BlockPO b :blocks){
				String p = b.getMineNum()+"";
				if(b.isMine())
					p="*";
				System.out.print(p);
			}
			System.out.println();
		}
	}
}
