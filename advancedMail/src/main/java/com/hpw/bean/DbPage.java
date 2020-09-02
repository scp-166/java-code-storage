package com.hpw.bean;

import java.util.List;

/**
 * 简单仿照 pageHelper page
 */
public class DbPage<E> {
    /**
     * 当前为第几页
     */
    private Integer currentPageNum;
    /**
     * 总共多少页
     */
    private Integer totalPageSize;

    /**
     * 当前页元素个数
     */
    private Integer currentPageItemCount;

    /**
     * 整体元素个数
     */
    private Integer totalPageItemCount;

    /**
     * 当前页的元素内容
     */
    private List<E> currentPageRecordList;

    public Integer getTotalPageSize() {
        return totalPageSize;
    }

    public void setTotalPageSize(Integer totalPageSize) {
        this.totalPageSize = totalPageSize;
    }

    public Integer getCurrentPageItemCount() {
        return currentPageItemCount;
    }

    public void setCurrentPageItemCount(Integer currentPageItemCount) {
        this.currentPageItemCount = currentPageItemCount;
    }

    public List<E> getCurrentPageRecordList() {
        return currentPageRecordList;
    }

    public void setCurrentPageRecordList(List<E> currentPageRecordList) {
        this.currentPageRecordList = currentPageRecordList;
    }

    public Integer getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(Integer currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public Integer getTotalPageItemCount() {
        return totalPageItemCount;
    }

    public void setTotalPageItemCount(Integer totalPageItemCount) {
        this.totalPageItemCount = totalPageItemCount;
    }

    @Override
    public String toString() {
        return "DbPage{" +
                "currentPageNum=" + currentPageNum +
                ", totalPageSize=" + totalPageSize +
                ", currentPageItemCount=" + currentPageItemCount +
                ", totalPageItemCount=" + totalPageItemCount +
                ", recordList=" + currentPageRecordList +
                '}';
    }
}
