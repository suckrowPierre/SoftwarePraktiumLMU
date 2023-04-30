package test;

import java.util.Arrays;

public class arrtest {

    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8};
        int id = 5;
        System.out.println(Arrays.toString(arr));
        arr = pushToArray(arr,id);
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(pushToArray(arr,7)));
    }

   static private int[] pushToArray(int[] arr, int ID){
        int[] arrReturn = new int[arr.length];
        arrReturn[0] = ID;
        int i = 0;

        while (arr[i] != ID){
            arrReturn[i+1] = arr[i];
            i++;
        }
        i++;
        while (i < arr.length){
            arrReturn[i] = arr[i];
            i++;
        }


        return arrReturn;
    }

}
