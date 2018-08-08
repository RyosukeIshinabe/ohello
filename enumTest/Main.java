package enumTest;
import java.time.MonthDay;
import java.util.Scanner;

// 入力された月と日から星座を判別するプログラムです。

public class Main {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);   // スキャナークラス作成

        // ユーザーの入力を受け取る
        int inputMonth;
        do {
        	System.out.println("月を入力してください。> ");
            inputMonth = scanner.nextInt();
        } while ( !checkInputMonth(inputMonth) );

        int inputDay;
        do {
        	System.out.println("日を入力してください。> ");
            inputDay = scanner.nextInt();
        } while ( !checkInputDay(inputDay) );

   		// 入力のあった日付をMonthDay型（--mm-dd）の形式で保存
   		MonthDay inputMonthDay = MonthDay.of(inputMonth, inputDay);

   		// 星座を特定して表示
        System.out.print(inputMonth + "月" + inputDay + "日は、");
        System.out.println(Constellation.getType(inputMonthDay) + "です。");

        scanner.close();
    }

    // 入力された月が正しいかチェック
    public static boolean checkInputMonth(int month) {
    	if ( 1 <= month && month <= 12 ) {
    		return true;
    	}
    	return false;
    }

    // 入力された日が正しいかチェック
    public static boolean checkInputDay(int day) {
    	if ( 1 <= day && day <= 31 ) {
    		return true;
    	}
    	return false;
    }

}
