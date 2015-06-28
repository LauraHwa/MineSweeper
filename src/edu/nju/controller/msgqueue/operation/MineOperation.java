package edu.nju.controller.msgqueue.operation;

import java.io.Serializable;

public abstract class MineOperation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract void execute();
}
