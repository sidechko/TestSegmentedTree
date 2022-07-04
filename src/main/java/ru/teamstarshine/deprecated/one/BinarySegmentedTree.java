package ru.teamstarshine.deprecated.one;

import ru.teamstarshine.main.area.DefaultArea;

import java.util.Arrays;

public class BinarySegmentedTree {
    private DefaultArea[] inputDate;
    private DefaultArea[] segmentValue;

    public BinarySegmentedTree(DefaultArea... inputDate) {
        this.inputDate = inputDate.clone();
        fullBuild();
    }

    public void appendDate(DefaultArea newDate){
        DefaultArea[] updated = new DefaultArea[this.inputDate.length+1];
        System.arraycopy(this.inputDate,0,updated,0,this.inputDate.length);
        updated[inputDate.length] = newDate;
        this.inputDate = updated;
        fullBuild();
    }

    public void removeAndRebuild(DefaultArea... areas){
        int[] toRm = new int[areas.length];
        int countOfFind = 0;
        for (int i = 0; i < inputDate.length; i++) {
            for (int j = 0; j < areas.length; j++) {
                if(inputDate[i].equals(areas[j])){
                    toRm[countOfFind] = i;
                    countOfFind++;
                    break;
                }
            }
        }
        DefaultArea[] afterRemove = new DefaultArea[inputDate.length-countOfFind];
        int mover = 0;

        for (int i = 0; i < inputDate.length; i++) {
            for (int j = 0; j < countOfFind; j++) {
                if(i == toRm[j]){
                    mover++;
                    break;
                }
            }
            afterRemove[i] = inputDate[i+mover];
        }

        this.inputDate = afterRemove;
        fullBuild();
    }
    private void fullBuild(){
        this.segmentValue = new DefaultArea[inputDate.length *4];
        Arrays.fill(segmentValue,null);
        build(1,0, inputDate.length-1);
    }

    private void build(int nodeNum, int arrLP, int arrRP) {
        if (arrLP == arrRP)
            segmentValue[nodeNum] = inputDate[arrLP];
        else {
            int arrMP = (arrLP + arrRP) / 2;
            build(nodeNum * 2, arrLP, arrMP);
            build(nodeNum * 2 + 1, arrMP + 1, arrRP);
            segmentValue[nodeNum] = DefaultArea.sum(segmentValue[nodeNum * 2], segmentValue[nodeNum * 2 + 1]);
        }
    }

    private Object selectOneOfArgs(Object o1, Object o2){
        if(o1== null)
            return o2;
        return o1;
    }

    private boolean hasChild(int index){
        return segmentValue[index*2]!= null || segmentValue[index*2+1] != null;
    }

    private DefaultArea findAreaWithBlockInside(int x, int y, int z, int segmentPos){
        DefaultArea area = segmentValue[segmentPos];
        if(area.isInside(x,y,z)){
            if(!hasChild(segmentPos)){
                return area;
            }
            DefaultArea a = findAreaWithBlockInside(x,y,z,segmentPos*2);
            DefaultArea b = findAreaWithBlockInside(x,y,z,segmentPos*2+1);
            return (DefaultArea) selectOneOfArgs(a,b);
        }
        return null;
    }
}
