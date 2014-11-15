package com.bjsxt.softrc.util;

import java.util.List;

import com.bjsxt.softrc.vo.UserInfo;
/*
 * 通用分页类
 */
public class Pagenation {
	//当前页号
	private int pageNum;
	//记录总页
	private int rowCount;
	//数据内容
	private List list;
	//页面显示记录的个数
	private int size;
	//总页数
	private int pageCount;
	//本页起始记录
	private int startRow;
	//页号导航
	private int first = 1;//第一页
	private int last;//最后页号
	private int previous;//上一页
	private int next; //下一页
	private int startNav; //导航起始页号
	private int endNav; //导航结束页号
	private int navCount = 10; //最多显示页号数量
	
	public Pagenation(String str_pageNum, int rowCount, int size) {
		this.pageNum = (str_pageNum==null)?1:(Integer.parseInt(str_pageNum));
		
		this.rowCount = rowCount;
		this.size = size;
		
		this.pageCount = (int) Math.ceil(this.rowCount/10.0);
		this.pageNum = Math.min(this.pageNum, pageCount);
		this.pageNum = Math.max(1, this.pageNum);
		this.startRow = (this.pageNum-1) * size;
		this.last = this.pageCount;
		this.previous = Math.max(1, this.pageNum-1);
		this.next = Math.min(this.pageCount, this.pageNum+1);
		
		//导航处理
		this.startNav = (this.pageNum-navCount/2<1)?1:this.pageNum-navCount/2;
		this.endNav = this.startNav+this.navCount;
		this.endNav = Math.min(this.endNav, this.pageCount);
		//保证最后一页也有10个数据
		if(this.endNav-this.startNav < this.navCount){
			this.startNav = Math.max(this.endNav-this.navCount,1);
		}
	}

	public Pagenation(){
	}
	
	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

	public int getPrevious() {
		return previous;
	}

	public void setPrevious(int previous) {
		this.previous = previous;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getStartNav() {
		return startNav;
	}

	public void setStartNav(int startNav) {
		this.startNav = startNav;
	}

	public int getEndNav() {
		return endNav;
	}

	public void setEndNav(int endNav) {
		this.endNav = endNav;
	}

	public int getNavCount() {
		return navCount;
	}

	public void setNavCount(int navCount) {
		this.navCount = navCount;
	}
	
		
	
}
