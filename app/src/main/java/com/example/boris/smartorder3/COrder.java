package com.example.boris.smartorder3;

import java.io.Serializable;

public class COrder implements Serializable {
    private int tableID;
    private int orderItem1;
    private int orderItem2;
    private int orderItem3;
    private int orderItem4;
    private int orderItem5;
    private int orderItem6;
    private int orderItem7;
    private String level;

    public COrder(int tableID, int orderItem1, int orderItem2, int orderItem3, int orderItem4, int orderItem5, int orderItem6, int orderItem7) {
        this.tableID = tableID;
        this.orderItem1 = orderItem1;
        this.orderItem2 = orderItem2;
        this.orderItem3 = orderItem3;
        this.orderItem4 = orderItem4;
        this.orderItem5 = orderItem5;
        this.orderItem6 = orderItem6;
        this.orderItem7 = orderItem7;
    }

    public int getTableID() {
        return tableID;
    }

    //口味濃淡
    public String getOrderItem1() {
        switch (orderItem1) {
            case 1:
                level = "淡味";
                break;
            case 2:
                level = "普通";
                break;
            case 3:
                level = "濃味";
                break;
            default:
                level = "普通";
                break;
        }
        return level;
    }

    //油濃郁度
    public String getOrderItem2() {
        switch (orderItem2) {
            case 1:
                level = "無";
                break;
            case 2:
                level = "清淡";
                break;
            case 3:
                level = "普通";
                break;
            case 4:
                level = "濃郁";
                break;
            case 5:
                level = "超濃郁";
                break;
            default:
                level = "普通";
                break;
        }
        return level;
    }

    //蒜泥
    public String getOrderItem3() {
        switch (orderItem3) {
            case 1:
                level = "無";
                break;
            case 2:
                level = "少許";
                break;
            case 3:
                level = "普通";
                break;
            case 4:
                level = "0.5份";
                break;
            case 5:
                level = "1份";
                break;
            default:
                level = "普通";
                break;
        }
        return level;
    }

    //蔥
    public String getOrderItem4() {
        switch (orderItem4) {
            case 1:
                level = "無";
                break;
            case 2:
                level = "有";
                break;
            default:
                level = "普通";
                break;
        }
        return level;
    }

    //叉燒
    public String getOrderItem5() {
        switch (orderItem5) {
            case 1:
                level = "無";
                break;
            case 2:
                level = "有";
                break;
            default:
                level = "普通";
                break;
        }
        return level;
    }

    //醬汁
    public String getOrderItem6() {
        switch (orderItem6) {
            case 1:
                level = "無";
                break;
            case 2:
                level = "0.5倍";
                break;
            case 3:
                level = "普通";
                break;
            case 4:
                level = "2倍";
                break;
            case 5:
                level = "自訂";
                break;
            default:
                level = "普通";
                break;
        }
        return level;
    }

    //硬度
    public String getOrderItem7() {
        switch (orderItem7) {
            case 1:
                level = "超硬";
                break;
            case 2:
                level = "硬";
                break;
            case 3:
                level = "普通";
                break;
            case 4:
                level = "軟";
                break;
            case 5:
                level = "超軟";
                break;
            default:
                level = "普通";
                break;
        }
        return level;
    }


}
