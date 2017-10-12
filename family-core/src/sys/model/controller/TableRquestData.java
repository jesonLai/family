package sys.model.controller;

import java.io.Serializable;

public class TableRquestData implements Serializable {

    // 参数
    private int draw;
    private int start;
    private int length;

    // 返回
    private long recordsTotal;
    private long recordsFiltered;
    private Object data;

    public int getDraw() {
	return draw;
    }

    public void setDraw(int draw) {
	this.draw = draw;
    }

    public int getStart() {
	return start;
    }

    public void setStart(int start) {
	this.start = start;
    }

    public int getLength() {
	return length;
    }

    public void setLength(int length) {
	this.length = length;
    }

    public long getRecordsTotal() {
	return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
	this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
	return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
	this.recordsFiltered = recordsFiltered;
    }

    public Object getData() {
	return data;
    }

    public void setData(Object data) {
	this.data = data;
    }

    @Override
    public String toString() {
	return "TableRquestData [draw=" + draw + ", start=" + start + ", length=" + length + ", recordsTotal="
		+ recordsTotal + ", recordsFiltered=" + recordsFiltered + ", data=" + data + "]";
    }

}
