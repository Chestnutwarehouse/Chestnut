package com.hs.test;
import java.util.Scanner;

public class HearthStone {
    static class Entourage {
        public int attack;
        public int health;

        Entourage() {
            attack = 0;
            health = 0;
        }

        public void setAttack(int a) {
            attack = a;
        }

        public void setHealth(int h) {
            health = h;
        }

        public void doAttack(Entourage opponent) //攻击操作，计算攻击后双方血量
        {
            health = health - opponent.attack;
            opponent.health = opponent.health - attack;
        }
    }

    /**
     * 放置随从，随从位置整体右移
     */
    public static void moveSummon(int position, Entourage[] p) {
        for (int i = 7; i > position; i--) {
            p[i].setAttack(p[i - 1].attack);
            p[i].setHealth(p[i - 1].health);
        }
    }

    /**
     * 随从死亡，随从位置整体左移
     */
    public static void removeSummon(int position, Entourage[] p) {
        for (int i = position; i < 7; i++) {
            p[i].setAttack(p[i + 1].attack);
            p[i].setHealth(p[i + 1].health);
        }
        p[7].setAttack(0);
        p[7].setHealth(0);
    }

    /**
     * 计算还剩多少随从
     */
    public static int countSummon(Entourage[] p) {
        int count = 0;
        for (int j = 1; j <= 7; j++) {
            if (p[j].health > 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * 计算随从血量
     */
    public static void findHealth(Entourage[] p, int[] q) {
        int i = 0;
        for (int j = 1; j <= 7; j++) {
            if (p[j].health > 0) {
                q[i] = p[j].health;
                i++;
            }
        }

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int Role_first = 30;
        int Role_back = 30;
        int judge = 0; //判断先后手谁操作
        String instruction; //指令字符串
        int time = in.nextInt(); //回合数
        in.nextLine();
        int Have_ended = 0; //判断游戏是否结束
        Entourage[] first = new Entourage[8]; //先手随从类数组
        for (int i = 1; i <= 7; i++) {
            first[i] = new Entourage();

        }
        Entourage[] back = new Entourage[8]; //后手随从类数组
        for (int i = 1; i <= 7; i++) {
            back[i] = new Entourage();

        }
        int[] Surplus_first = new int[8]; //先手随从剩余血量
        int[] Surplus_back = new int[8]; //后手随从剩余血量
        for (int i = 0; i < time; i++)  //每个指令操作
        {
            instruction = in.next();

            switch (instruction) //召唤随从操作
            {
                case "summon": {
                    int position = in.nextInt();
                    int attack = in.nextInt();
                    int health = in.nextInt();
                    if (judge == 0) //判断为先手
                    {
                        moveSummon(position, first);
                        first[position].setAttack(attack);
                        first[position].setHealth(health);

                    } else  //判断为后手
                    {
                        moveSummon(position, back);
                        back[position].setAttack(attack);
                        back[position].setHealth(health);
                    }
                }
                case "attack"://攻击操作
                {
                    int attacker = in.nextInt();
                    int defender = in.nextInt();
                    if (judge == 0) {
                        if (defender != 0) //攻击随从
                        {
                            first[attacker].doAttack(back[defender]);
                            if (back[defender].health <= 0) {
                                removeSummon(defender, back);
                            }
                            if (first[attacker].health <= 0) {
                                removeSummon(attacker, first);
                            }
                        } else  //攻击对方角色
                        {
                            Role_back = Role_back - first[attacker].attack;
                            if (Role_back <= 0) //后手角色血量小于等于0，先手获胜
                            {

                                System.out.println("1");
                                System.out.println(Role_first);
                                System.out.print(countSummon(first));
                                findHealth(first, Surplus_first);
                                for (int k = 0; k < countSummon(first); k++) {
                                    System.out.print(" " + Surplus_first[k]);
                                }
                                System.out.print("\n");
                                System.out.println(Role_back);
                                System.out.print(countSummon(back));
                                findHealth(back, Surplus_back);
                                for (int k = 0; k < countSummon(back); k++) {
                                    System.out.print(" " + Surplus_back[k]);
                                }
                                Have_ended = 1;
                                break;
                            }
                        }
                    } else {
                        if (defender != 0) {
                            back[attacker].doAttack(first[defender]);
                            if (first[defender].health <= 0) {
                                removeSummon(defender, first);
                            }
                            if (back[attacker].health <= 0) {
                                removeSummon(attacker, back);
                            }
                        } else {
                            Role_first = Role_first - back[attacker].attack;
                            if (Role_first <= 0)//先手角色血量小于等于0，后手获胜
                            {

                                System.out.println("-1");
                                System.out.println(Role_first);
                                System.out.print(countSummon(first));
                                findHealth(first, Surplus_first);
                                for (int k = 0; k < countSummon(first); k++) {
                                    System.out.print(" " + Surplus_first[k]);
                                }
                                System.out.print("\n");
                                System.out.println(Role_back);
                                System.out.print(countSummon(back));
                                findHealth(back, Surplus_back);
                                for (int k = 0; k < countSummon(back); k++) {
                                    System.out.print(" " + Surplus_back[k]);
                                }
                                Have_ended = 1;
                                break;
                            }
                        }
                    }
                }
                case "end": //操作方改变
                {
                    if (judge == 0) {
                        judge = 1;
                    } else {
                        judge = 0;
                    }
                }
            }
            if (Have_ended == 0) //没有角色的血量小于等于0，战斗未结束
            {

                System.out.println("0");
                System.out.println(Role_first);
                System.out.print(countSummon(first));
                findHealth(first, Surplus_first);
                for (int k = 0; k < countSummon(first); k++) {
                    System.out.print(" " + Surplus_first[k]);
                }
                System.out.print("\n");
                System.out.println(Role_back);
                System.out.print(countSummon(back));
                findHealth(back, Surplus_back);
                for (int k = 0; k < countSummon(back); k++) {
                    System.out.print(" " + Surplus_back[k]);
                }
            }


        }


    }
}





