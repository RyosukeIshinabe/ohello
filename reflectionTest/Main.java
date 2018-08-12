package reflectionTest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
	public static void main(String[] args) {

		// Classクラスのインスタンスを取得
	    Class<ReflectionTestAll> clazz = ReflectionTestAll.class;

	    // ReflectionTestクラスに定義されているクラスを全て取得
	    Class[] classes = clazz.getClasses();

	    // ReflectionTestクラスのインスタンスを生成(このインスタンスのメソッドを呼び出すことになる)
	    ReflectionTestAll reflectionTestObj = new ReflectionTestAll();

	    // 取得したクラスを全て精査
	    for (Class oneClass : classes) {

	    	// もし指定した文字のクラス以外があったら無視
	    	if (!oneClass.getName().startsWith("ReflectionTest" + "A")) {
		        continue;
		    }

	    	// 指定した文字のクラスは以下処理を行う

	    	// クラスのメソッドを全て取得
	    	Method[] methods = oneClass.getDeclaredMethods();

	    	// 取得したメソッドを全て精査
		    for (Method oneMethod : methods) {

		    	// メソッド名「printMethod」で始まっていないなら無視
		    	if (!oneMethod.getName().startsWith("printMethod")) {
		    		continue;
		    	}

		    	// メソッド名「printMethod」で始まるもののみを実行
		    	try {
		    		// reflectionTestObjのメソッドの実行 引数はなし
		    		oneMethod.invoke(reflectionTestObj, null);
			    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			    	e.printStackTrace();
			    }
			}
		}

	}
}
