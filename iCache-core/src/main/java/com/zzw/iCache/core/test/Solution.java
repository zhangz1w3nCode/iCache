package com.zzw.iCache.core.test;

public class Solution {

    public static int handle(int[]numsA,int[]numsB,int k){
        int l1 = numsA.length;
        int l2 = numsB.length;

        int i=l1-1;
        int j=l2-1;

        if(k<=0||k>l1+l2) return -1;

        if(k==1) return Math.max(numsA[i],numsB[j]);

        while(k>=2){

            if(numsA[i]>numsB[j]){
                --i;
            }else{
                --j;
            }

            --k;
        }

        if(i>=0&&j>=0){
            return Math.max(numsA[i],numsB[j]);
        }else if(i>=0){
            return numsA[i];
        }else if(j>=0){
            return numsB[j];
        }else {
            return  -1;
        }
    }
    public static void main(String[] args) {
        int[] nums1 = {1};
        int[] nums2 = {2,3,4,6};
        int k = 1;
        System.out.println(handle(nums1, nums2, k));
    }
}