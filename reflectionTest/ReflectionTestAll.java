package reflectionTest;

// 内部クラスで分けた場合

public class ReflectionTestAll {

	public class ReflectionTestA {
		public void printMethod() {
			System.out.println("ReflectionTestクラスAのprintメソッドが実行されました。");
		}
	}

	public class ReflectionTestB {
		public void printMethod() {
			System.out.println("ReflectionTestクラスBのprintメソッドが実行されました。");
		}
	}

	public class ReflectionTestC {
		public void printMethod() {
			System.out.println("ReflectionTestクラスCのprintメソッドが実行されました。");
		}
	}


}
