package com.sq.test;

import java.util.Scanner;

/**
 * @author JXY
 * @version 创建时间：2022年10月23日 下午9：06：28
 *
 */
public class StudentsQueuing {

    /**找到该学号p的当前位置*/
    public static int findPosition(int[] position,int p,int n)
    {
        int j=0;
        for(int i=1;i<=n;i++)
        {
            if(position[i]==p)
            {
                j=i;
            }
        }
        return j;
    }
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        int k;
        //表示学生的数量，学生的学号由1到number编号
        int number=in.nextInt();
        //学生位置数组
        int[] position=new int[number+1];
        //初始学号顺序
        for(int i=1;i<=number;i++)
        {
            position[i]=i;
        }
        //输入操作次数
        int n=in.nextInt();
        for(int j=0;j<n;j++)
        {

            //要操作的学号
            int p=in.nextInt();
            //要调整位置的移动数
            int q=in.nextInt();
            //q>0,该操作学号后移
            if(q>0)
            {
                //找到目标学号的顺序位置
                int temp=findPosition(position,p,number);

                for(k=temp;k<temp+q;k++)
                {
                    position[k]=position[k+1];
                }
                position[temp+q]=p;
            }
            //q<0,该操作学号前移
            else if(q<0)
            {
                int temp=findPosition(position,p,number);
                for( k=temp;k>temp+q;k--)
                {
                    position[k]=position[k-1];
                }
                position[temp+q]=p;
            }
        }
        //输出移动后学号顺序
        for(int h=1;h<=number;h++)
        {
             if(h!=number)
             {
                 System.out.print(position[h]+" ");
             }
             else
             {
                 System.out.print(position[h]);
             }
        }



    }
}
