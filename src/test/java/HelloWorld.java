public class HelloWorld {

	public void sayHello() {
		System.out.println("helloWorld....");
	}

	public static void main(String[] args) {
		AsmAopGenerator aag = new AsmAopGenerator();
		HelloWorld hw = (HelloWorld) aag.proxy(HelloWorld.class, "sayHello",
				"it's begin", "it's end");
		hw.sayHello();
	}
}