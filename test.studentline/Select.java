import java.util.*;
public class Select {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        int n=in.nextInt();
        int m=in.nextInt();
        in.nextLine();
        ArrayList<Label> parents=new ArrayList<>();
        ArrayList<Label> list=new ArrayList<>(n);
        String title=in.nextLine();
        Label newLabel=new Label(title,"",1,null);
        list.add(newLabel);
        parents.add(newLabel);
        for(int i=1;i<n;i++) {
            String str=in.nextLine();
            String[] parts=str.split(" ");
            title=parts[0].substring(parts[0].lastIndexOf(".")+1).toLowerCase();
            String id=parts.length==2?parts[1]:"";
            newLabel=new Label(title,id,i+1,parents.get((str.lastIndexOf(".")+1)/2-1));
            list.add(newLabel);
            if(parents.size()==(str.lastIndexOf(".")+1)/2) {
                parents.add(newLabel);
            }else {
                parents.set((str.lastIndexOf(".")+1)/2, newLabel);
            }
        }
        String css[][]=new String[m][];
        for(int i=0;i<m;i++) {
            String parts[]=in.nextLine().split(" ");
            for(int j=0;j<parts.length;j++) {
                if(parts[j].charAt(0)!='#') {
                    parts[j]=parts[j].toLowerCase();
                }
            }
            css[i]=parts;
        }
        for(int i=0;i<m;i++) {
            ArrayList<Integer> lines=new ArrayList<Integer>();
            for(int j=0;j<list.size();j++) {
                int index=css[i].length-1;
                if(list.get(j).title.equals(css[i][index])||list.get(j).id.equals(css[i][index])) {
                    Label temp=list.get(j).parent;
                    index--;
                    while(temp!=null&&index>=0) {
                        if(temp.title.equals(css[i][index])||temp.id.equals(css[i][index])) {
                            index--;
                        }
                        temp=temp.parent;
                    }
                    if(index==-1)
                        lines.add(list.get(j).line);
                }
            }
            Collections.sort(lines);//还需对行号排序
            System.out.print(lines.size());
            for(int j=0;j<lines.size();j++) {
                System.out.print(" "+lines.get(j));
            }
            System.out.println();
        }
    }
}
class Label{
    String title="";
    String id="";
    int line=0;
    Label parent=null;
    public Label(String title,String id,int line,Label parent) {
        this.title=title;
        this.id=id;
        this.line=line;
        this.parent=parent;
    }
}
