import javax.security.auth.Subject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 随机生成数字和表达式以及计算方法
 */
public class Subjects {

    private static Subjects subjects = null;
    private static int[] prime_num;

    int level = 100;
    int methor = 4;
    String banMes = null;
    Random r;
    String subject;
    Integer answer = null;

    private Subjects(Random r) {
        this.r = r;
    }

    public void subjects(){
        subject = "";
        ArrayList<Integer> numList = new ArrayList<>();
        ArrayList<String> signList = new ArrayList<>();

        if (banMes == null) {
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
        }else {
            for (int i = 0; i < methor; i++) {
                int sign = r.nextInt(banMes.length()*10)/10;
                System.out.println(String.valueOf(banMes.charAt(sign)));
                signList.add(String.valueOf(banMes.charAt(sign)));
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

        System.out.println(subject);
        answer = compute(subject);
        System.out.println(answer);
    }

    /**
     * 将字符串的表达式进行计算
     * @param s 待计算的字符串
     * @return 计算结果 转换为int类型 将结果四舍五入
     */
    private int compute(String s){
        String t =  ExpressionToDouble.StringToRes(s + "=");
//        System.out.println(t);
        int value = (int)Math.round(Double.parseDouble(t));
        return value;
    }

    /**
     * 生成表达式
     * @return
     */
    public static Subjects getInstance(){
        if (subjects == null) {
            long randomNum = System.currentTimeMillis();
            int ran3 = (int) (randomNum%(100-1)+1);
            return new Subjects(new Random(ran3));
        }else
            return subjects;
    }

    public static void main(String[] args) {
        Subjects subjects = Subjects.getInstance();
        subjects.banMes = "+*";
        subjects.subjects();
    }
}
