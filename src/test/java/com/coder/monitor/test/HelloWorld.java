//1.目的：实现对函数执行监听，在函数调用前，后得到通知。考虑用asm来实现。 
//2.资料：在网上看到关于asm的技术资料，写了一个简单的实现。参考链接如下： 
//http://www.cnblogs.com/eafy/archive/2008/06/18/1224633.html 
//http://alvinqq.iteye.com/blog/940965 
//http://www.ibm.com/developerworks/cn/java/j-lo-asm30/ 
//http://ayufox.iteye.com/blog/668917 
package com.coder.monitor.test;
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
