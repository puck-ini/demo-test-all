package org.zchzh.javaagent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Arrays;

/**
 * @author zengchzh
 * @date 2021/8/27
 */
public class HelloAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("agentArgs : " + agentArgs);
        inst.addTransformer(new DefineTransformer(), true);
    }

    static class DefineTransformer implements ClassFileTransformer {

        @Override
        public byte[] transform(ClassLoader loader,
                                String className,
                                Class<?> classBeingRedefined,
                                ProtectionDomain protectionDomain,
                                byte[] classfileBuffer) throws IllegalClassFormatException {
            System.out.println("loader = " + loader
                    + ", className = " + className
                    + ", classBeingRedefined = " + classBeingRedefined
                    + ", protectionDomain = " + protectionDomain);
//                    + ", classfileBuffer = " + Arrays.toString(classfileBuffer));
            return classfileBuffer;
        }
    }
}
