import javax.security.auth.Subject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Subjects {

    private static Subjects subjects = null;
    private static int[] prime_num;

    int level = 100;
    int methor = 4;
    Random r;
    String subject;
    Integer answer = 0;

    private Subjects(Random r) {
        this.r = r;
    }

    public void subjects(){
        subject = "";
        ArrayList<Integer> numList = new ArrayList<>();
        ArrayList<String> signList = new ArrayList<>();

        for (int i = 0; i < methor; i++) {
            if (level == 0) {
                int sign = r.nextInt(20)/10;
                switch (sign){
                    case 0:signList.add("+");break;
                    case 1:signList.add("-");break;
                }
            }else {
                int sign = r.nextInt(40)/10;
                switch (sign){
                    case 0:signList.add("+");break;
                    case 1:signList.add("-");break;
                    case 2:signList.add("*");break;
                    case 3:signList.add("/");break;
                }
            }
        }

        for (int i = 0; i < methor + 1; i++) {
            if(level == 0)
                numList.add(r.nextInt(10)+1);
            else {
                numList.add(r.nextInt(level * 10)/10+1);
            }
        }

        subject += numList.get(0);
        for (int i = 1; i < methor + 1; i++) {
            subject += signList.get(i-1);
            subject += numList.get(i);
        }

//        System.out.println(subject);
        answer = compute(subject);
//        System.out.println(answer);
    }

    private int compute(String s){
        String t =  ExpressionToDouble.StringToRes(s + "=");
//        System.out.println(t);
        int value = (int)Math.round(Double.parseDouble(t));
        return value;
    }

    public static Subjects getInstance(){
        if (subjects == null) {
            long randomNum = System.currentTimeMillis();
            int ran3 = (int) (randomNum%(100-1)+1);
            return new Subjects(new Random(ran3));
        }else
            return subjects;
    }

//    public static void main(String[] args) {
//        Subjects subjects = Subjects.getInstance();
//        subjects.subjects();
//    }
}
