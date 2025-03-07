package com.ohgiraffers.semi.common.paging;

public class Pagenation implements java.io.Serializable {

    private int page = 1;              // 현재 페이지(1페이지부터 시작)
    private int size = 8;              // 한 페이지당 보여줄 레코드(상품) 개수

    private int totalItems;            // 전체 레코드드 수
    private int totalPages;            // 전체 페이지 수
    private int startPage;             // 시작 페이지 번호
    private int endPage;               // 끝 페이지 번호
    private boolean hasPrevious;       // 이전 페이지 존재 여부
    private boolean hasNext;           // 다음 페이지 존재 여부

	// 페이징 정보 설정
    public void setPageInfo(int totalItems) {

		/* 전체 레코드 수 설정(무조건 정수) */
        this.totalItems = totalItems;
		/* 전체 페이지 수 계산(무조건 정수) */
        this.totalPages = (int) Math.ceil((double) totalItems / size);
        
		/* 현재 페이지 기준으로 화면에 보여줄 시작 페이지와 끝 페이지 계산 */
        this.startPage = ((page - 1) / 10) * 10 + 1;
        this.endPage = Math.min(startPage + 9, totalPages);
        
		/* 이전 페이지 존재 여부 설정 */
        this.hasPrevious = page > 1;
		/* 다음 페이지 존재 여부 설정 */
        this.hasNext = page < totalPages;
    }

	/* 데이터베이스 조회 시 사용할 오프셋 계산 */
    public int getOffset() {
        return (page - 1) * size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
}