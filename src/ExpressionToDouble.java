import java.util.Stack;

/**
 * 将string的运算字符转化为可以惊醒实际运算的方法（即实现加减乘除，先乘除后加减）
 */
public class ExpressionToDouble {
    /**
     * 判断是不是一个数字
     * @param ch 待判断的字符
     * @return true or false
     */
    private static boolean isNumber(char ch){
        return ch == '.' || (ch >= '0' && ch <= '9');
    }

    /**
     * 计算表达式中加减
     * @param s 待计算的表达式
     * @param opration 操作符 （+ or -）
     */
    private static void hasAddOrSub(Stack<Character> s, char opration){
        StringBuilder number = new StringBuilder();
        //将符号前的数字提取出来
        while(true){
            char cur = s.peek();
            if (cur == opration)
                break;
            number.insert(0, cur);
            s.pop();
        }
        //将操作符出栈
        s.pop();
        //将字符串的数字转化为可计算的数字
        double right = Double.parseDouble(number.toString());
        number.replace(0, number.length(), "");

        //继续提取数字
        while(!s.isEmpty()){
            char cur = (char)s.peek();
            number.insert(0, cur);
            s.pop();
        }

        double left = Double.parseDouble(number.toString());
        number.replace(0, number.length(), "");

        double res = 0;
        //根据操作符来计算两个数的结果 将结果存回栈中
        if (opration == '+')
            res = left + right;
        else
            res = left -right;
        String str = res + "";
        for(char chr : str.toCharArray()){
            s.push(chr);
        }
    }

    /**
     * 计算乘除的情况 将表达式中的所有乘除先计算掉
     * @param s 待计算的表达式
     * @param opration 操作符 （* or /）
     */
    private static void hasMulOrDiv(Stack<Character> s, char opration){
        StringBuilder number = new StringBuilder();
        while(true){
            char cur = s.peek();
            if (cur == opration)
                break;
            number.insert(0, cur);
            s.pop();
        }

        s.pop();
        double right = Double.parseDouble(number.toString());
        number.replace(0, number.length(), "");

        while(!s.isEmpty()){
            char cur = s.peek();
            if (cur == '+' || cur == '-')
                break;
            number.insert(0, cur);
            s.pop();
        }

        double left = Double.parseDouble(number.toString());
        number.replace(0, number.length(), "");
        double res = 0;
        if (opration == '*')
            res = left * right;
        else
            res = left / right;

        String str = res + "";
        for(char chr : str.toCharArray()){
            s.push(chr);
        }
    }

    /**
     * 对字符串进行校验 确保是可计算的字符串
     * @param datas 代校验的字符串
     * @return True or False
     */
    private static boolean checkInfo(char []datas){
        for(int i = 0; i < datas.length-1; i++){
            if (!isNumber(datas[i]) && !isNumber(datas[i+1]))
                return false;
            if (datas[i] == '.' && datas[i+1] == '.')
                return false;
        }
        return true;
    }

    /**
     * 计算方法的主体 将一个字符串的表达式计算
     * @param info 待计算的字符串
     * @return 计算的结果
     */
    public static String StringToRes(String info){
        //3+3*3
        Stack<Character> s = new Stack<Character>();
        char []datas = info.toCharArray();
        //计算前先校验字符串的合法性
        if (!checkInfo(datas))
            return "";
        boolean hasAdd = false;
        boolean hasSub = false;
        boolean hasMul = false;
        boolean hasDiv = false;

        for (char ch : datas){
            if (isNumber(ch)){
                s.push(ch);
            }
            else{
                switch(ch){
                    case '+':
                        if (hasMul){
                            hasMulOrDiv(s, '*');
                            hasMul = false;
                        }

                        if (hasDiv){
                            hasMulOrDiv(s, '/');
                            hasDiv = false;
                        }

                        if (hasAdd){
                            hasAddOrSub(s, '+');
                            hasAdd = false;
                        }

                        if (hasSub){
                            hasAddOrSub(s, '-');
                            hasSub = false;
                        }
                        s.push(ch);
                        hasAdd = true;
                        break;
                    case '-':
                        if (hasMul){
                            hasMulOrDiv(s, '*');
                            hasMul = false;
                        }

                        if (hasDiv){
                            hasMulOrDiv(s, '/');
                            hasDiv = false;
                        }

                        if (hasAdd){
                            hasAddOrSub(s, '+');
                            hasAdd = false;
                        }

                        if (hasSub){
                            hasAddOrSub(s, '-');
                            hasSub = false;
                        }

                        s.push(ch);
                        hasSub = true;
                        break;
                    case '*':
                        if (hasMul){
                            hasMulOrDiv(s, '*');
                            hasMul = false;
                        }
                        if (hasDiv){
                            hasMulOrDiv(s, '/');
                            hasDiv = false;
                        }
                        s.push(ch);
                        hasMul = true;
                        break;
                    case '/':
                        if (hasMul){
                            hasMulOrDiv(s, '*');
                            hasMul = false;
                        }

                        if (hasDiv){
                            hasMulOrDiv(s, '/');
                            hasDiv = false;
                        }
                        s.push(ch);
                        hasDiv = true;
                        break;
                    case '=':
                        if (hasMul){
                            hasMulOrDiv(s, '*');
                            hasMul = false;
                        }

                        if (hasDiv){
                            hasMulOrDiv(s, '/');
                            hasDiv = false;
                        }

                        if (hasAdd){
                            hasAddOrSub(s, '+');
                            hasAdd = false;
                        }

                        if (hasSub){
                            hasAddOrSub(s, '-');
                            hasSub = false;
                        }

                        StringBuilder number = new StringBuilder();
                        while(!s.isEmpty()){
                            char cur = (char)s.peek();
                            number.insert(0, cur);
                            s.pop();
                        }
                        return number.toString();
                }
            }
        }
        return "";
    }
}