package com.gensee.voddemo;

public class ChapterInfo {
	private int pageTimeStamp;
	private String pageTitle;
	private String docName;
	private int docPageNum;
	private int docId;
	private int docType;

	public int getPageTimeStamp() {
		return pageTimeStamp;
	}

	public void setPageTimeStamp(int pageTimeStamp) {
		this.pageTimeStamp = pageTimeStamp;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public int getDocPageNum() {
		return docPageNum;
	}

	public void setDocPageNum(int docPageNum) {
		this.docPageNum = docPageNum;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public int getDocType() {
		return docType;
	}

	public void setDocType(int docType) {
		this.docType = docType;
	}

	@Override
	public String toString() {
		return "ChapterInfo [pageTimeStamp=" + pageTimeStamp + ", pageTitle="
				+ pageTitle + ", docName=" + docName + ", docPageNum="
				+ docPageNum + ", docId=" + docId + ", docType=" + docType
				+ "]";
	}
}
