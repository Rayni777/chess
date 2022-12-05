package Chess;

import java.util.Scanner;

public class Reader {

    public String readLine() {
        Scanner sc = new Scanner(System.in);
        String goTo;

        while (true) {
            System.out.println("Введите ваш ход");
            goTo = sc.nextLine();
            if (goTo.length() == 5
                    && isNum(goTo.substring(1, 2)) && isNum(goTo.substring(4, 5))
                    && goTo.charAt(0) >= 'a' && goTo.charAt(0) <= 'h'
                    && goTo.charAt(3) >= 'a' && goTo.charAt(3) <= 'h') {
                //System.out.println("Reader.readLine, the input move is correct");
                break;
            }
            System.out.println("Ваш ввод не является правильным, попытайтесь снова.");
        }
        return goTo;
    }

    protected String changePawn(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Вы можете поменять вашу пешку на ЛАДЬЯ, КОНЬ, СЛОН или КОРОЛЕВА");
        String name;

        while(true){
            System.out.println("Пожалуйста, введите название желаемой фигуры");
            name = sc.nextLine();

            if ("ЛАДЬЯ".equals(name)) return name;
            if ("КОНЬ".equals(name)) return name;
            if ("СЛОН".equals(name)) return name;
            if ("КОРОЛЕВА".equals(name)) return name;

            System.out.println("Ваш ввод не является правильным, попытайтесь снова.");
        }
    }

    protected int getToX(String line) {
        return line.charAt(3) - 96;
    }

    protected int getToY(String line) {
        return Integer.parseInt(line.substring(4, 5));
    }

    protected int getFromX(String line) {
        return line.charAt(0) - 96;
    }

    protected int getFromY(String line) {
        return Integer.parseInt(line.substring(1, 2));
    }

    public int decodeLine(String line, boolean first) {
        if (first) {
            return Integer.parseInt(line.substring(1, 2)) * 10 + line.charAt(0) - 96;
        } else {
            return Integer.parseInt(line.substring(4, 5)) * 10 + line.charAt(3) - 96;
        }
    }

    private boolean isNum(String a) {
        try {
            int num = Integer.parseInt(a);

            if (num >= 1 && num <= 8)
                return true;
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }


}
