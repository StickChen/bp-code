package com.shallop.bpc.collection;

import java.util.List;

/**
 * @author chenxuanlong
 * @date 2016/1/6
 */
public class GNResult {

	private Integer status;
	private Integer count;
	private List<Note> notes;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
}
