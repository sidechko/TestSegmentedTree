package ru.teamstarshine.deprecated.one;
//
//public class SegmentedTreeArea {
//    private boolean inited = false;
//    private Area[] inputDate;
//    private Area[] segmentValue;
//
//    public SegmentedTreeArea(Area... inputDate) {
//        this.inputDate = inputDate.clone();
//        segmentValue = new Area[inputDate.length * 4];
//    }
//
//    public void initTree() {
//        quickSort(0, inputDate.length - 1);
//        build(1, 0, inputDate.length - 1);
//        inited = true;
//    }
//
//    public void appendDate(Area... newDate){
//        for (int i = 0; i < newDate.length; i++) {
//            appendDate(newDate);
//        }
//    }
//
//    private void appendDate(Area newDate) {
//        if(inited){
//            int posForNewDateInsideDateArray = findPosMegjdDvuh(newDate);
//            appendValueAtPos(posForNewDateInsideDateArray, newDate);
//        }else{
//            Area[] areasTMP = new Area[inputDate.length+1];
//            System.arraycopy(inputDate, 0, areasTMP, 0, inputDate.length);
//            areasTMP[inputDate.length] = newDate;
//            this.inputDate = areasTMP;
//        }
//    }
//
//    private void appendValueAtPos(int pos, Area value){
//        Area[] areasTMP = new Area[this.inputDate.length+1];
//        System.arraycopy(inputDate, 0, areasTMP, 0, pos);
//        areasTMP[pos]=value;
//        System.arraycopy(inputDate,pos,areasTMP,pos+1,inputDate.length-pos);
//        this.inputDate = areasTMP;
//    }
//    private int findPosMegjdDvuh(Area value){
//        AreaWeigh valueWeigh = value.getWeight();
//        if(valueWeigh < inputDate[0].getWeight())
//            return 0;
//        if(valueWeigh > inputDate[inputDate.length-1].getWeight())
//            return inputDate.length;
//        int midPos = (inputDate.length)/2;
//        int move = midPos;
//        while(true){
//            move /= 2;
//            AreaWeigh rightValue = inputDate[midPos].getWeight();
//            AreaWeigh leftValue = inputDate[midPos+1].getWeight();
//            if(rightValue < valueWeigh && leftValue > valueWeigh)
//                return midPos+1;
//            if(rightValue < valueWeigh && leftValue < valueWeigh)
//                midPos = midPos + move;
//            else if(rightValue > valueWeigh && leftValue > valueWeigh)
//                midPos = midPos - midPos/2;
//        }
//    }
//
//
//    private void build(int nodeNum, int arrLP, int arrRP) {
//        if (arrLP == arrRP)
//            segmentValue[nodeNum] = inputDate[arrLP];
//        else {
//            int arrMP = (arrLP + arrRP) / 2;
//            build(nodeNum * 2, arrLP, arrMP);
//            build(nodeNum * 2 + 1, arrMP + 1, arrRP);
//            segmentValue[nodeNum] = Area.sum(segmentValue[nodeNum * 2], segmentValue[nodeNum * 2 + 1]);
//        }
//    }
//
//    private void quickSort(int low, int high) {
//        if (inputDate.length == 0)
//            return;
//        if (low >= high)
//            return;
//        int middle = low + (high - low) / 2;
//        Area basis = inputDate[middle];
//        int i = low, j = high;
//        while (i <= j) {
//            while (basis.getWeight() > inputDate[i].getWeight())
//                i++;
//            while (basis.getWeight() < inputDate[j].getWeight())
//                j++;
//            if (i <= j) {
//                Area tmp = inputDate[i];
//                inputDate[i] = inputDate[j];
//                inputDate[j] = tmp;
//                i++;
//                j++;
//            }
//            if (low < j)
//                quickSort(low, j);
//            if (high > i)
//                quickSort(i, high);
//        }
//    }
//}
