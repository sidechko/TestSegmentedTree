package ru.teamstarshine.one;

import ru.teamstarshine.Area;

import java.util.Arrays;

public class BinarySegmentedTree {
    private Area[] inputDate;
    private Area[] segmentValue;

    public BinarySegmentedTree(Area... inputDate) {
        this.inputDate = inputDate.clone();
        fullBuild();
    }

    public void appendDate(Area newDate){
        Area[] updated = new Area[this.inputDate.length+1];
        System.arraycopy(this.inputDate,0,updated,0,this.inputDate.length);
        updated[inputDate.length] = newDate;
        this.inputDate = updated;
        fullBuild();
    }

    public void removeAndRebuild(Area ... areas){
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
        Area[] afterRemove = new Area[inputDate.length-countOfFind];
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
        this.segmentValue = new Area[inputDate.length *4];
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
            segmentValue[nodeNum] = Area.sum(segmentValue[nodeNum * 2], segmentValue[nodeNum * 2 + 1]);
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

    private Area findAreaWithBlockInside(int x, int y, int z, int segmentPos){
        Area area = segmentValue[segmentPos];
        if(area.isInside(x,y,z)){
            if(!hasChild(segmentPos)){
                return area;
            }
            Area a = findAreaWithBlockInside(x,y,z,segmentPos*2);
            Area b = findAreaWithBlockInside(x,y,z,segmentPos*2+1);
            return (Area) selectOneOfArgs(a,b);
        }
        return null;
    }
}
