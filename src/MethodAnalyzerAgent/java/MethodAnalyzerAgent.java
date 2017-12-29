import java.lang.instrument.Instrumentation;

public class MethodAnalyzerAgent {

    public static int methodcounter = 0;

    public static void premain(String agentArgs,
                               Instrumentation inst) {
        inst.addTransformer(new RaytracerTransformer());
    }

}
